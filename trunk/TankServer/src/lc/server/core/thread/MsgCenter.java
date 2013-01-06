package lc.server.core.thread;

import java.awt.Point;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import lc.client.core.compents.TankComp;

/**
 * 服务器消息中心 非单例，每个房间一个
 */
public class MsgCenter {
	public static final String TYPE_JOIN="0";//加入游戏
	public static final String TYPE_JOIN_SUCCESS="1";//加入成功
	public static final String TYPE_JOIN_FAILED="2";//加入失败
	public static final String TYPE_CREATE="3";//创建游戏
	public static final String TYPE_CREATE_SUCCESS="4";//创建成功
	public static final String TYPE_CREATE_FAILED="5";//创建失败
	public static final String TYPE_TANK_MOVE="6";//坦克移动
	public static final String TYPE_BULLET="7";//炮弹发射	
	public static final String TYPE_CREATE_OTHER_TANK="8"; //创建其它坦克
	
	public static final int GAMETYPE_FLAG=0;//抢旗
	public static final int GAMETYPE_FIGHT=1;//对战

	protected Map<String, SocketChannel> allTanks;

	public MsgCenter(){
		this.allTanks=new HashMap<String, SocketChannel>();
	}
	/**
	 * 接收消息处理方法
	 * 
	 * 此方法需要好好整理！！！！！
	 * @param msg
	 */
	public synchronized void msgProcess(ByteBuffer buffer, SocketChannel client) {
		try {
			String msg = null;
			msg = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
			buffer.position(0);//必须重置
			int endIndex = 0;
			int index = 0;
			while ((index = msg.indexOf('[', endIndex)) != -1) {
				endIndex = msg.indexOf(']', index);
				String[] strs = msg.substring(index + 1, endIndex).split(",");
				if (TYPE_JOIN.equals(strs[0])) {// 有新连接请求加入游戏
					// 加入成功			
					client.write(ByteBuffer.wrap(("[" + TYPE_JOIN_SUCCESS + ",200,200]").getBytes("UTF-8")));//发送本人坦克坐标
					for(String key:allTanks.keySet()){//发送其它用户坦克坐标
						client.write(ByteBuffer.wrap(("[" + TYPE_CREATE_OTHER_TANK + ","+key+","+100+","+100+"]").getBytes("UTF-8")));//发别人的给你
						try{
						allTanks.get(key).write(ByteBuffer.wrap(("[" + TYPE_CREATE_OTHER_TANK + ","+strs[1]+","+100+","+100+"]").getBytes("UTF-8")));//发你的给别人
						}catch(ClosedChannelException e){
							e.printStackTrace();
							allTanks.remove(key);//有连接退出
							continue;
						}
					}
					allTanks.put(strs[1], client);//可以暂时随机一下
				}
			}
		} catch (CharacterCodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
