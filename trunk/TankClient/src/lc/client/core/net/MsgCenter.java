package lc.client.core.net;

import java.awt.Point;
import java.util.Map;

import lc.client.core.components.TankComp;
import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.util.ClientConstant;

/**
 * 消息中心
 * @author LUCKY
 *
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
	
	private static Map<String, TankComp> tankList=TankFactory.getInstance().getTankList();
	
	/**
	 * 创建游戏
	 * [类型，房间ID，创建者，游戏类型，玩家数量]
	 * @param gameId
	 * @param creater
	 * @param gameType
	 * @param playerNum
	 */
	public static final void addCreateGameMsg(String gameId,String creater,int gameType,int playerNum){
		String msg= "["+TYPE_CREATE+","+gameId+","+creater+","+gameType+","+playerNum+"]";
		DataSendThread.getInstance().addMessage(msg);
	}
	
	/**
	 * 加入游戏
	 * [类型，玩家编号]
	 * @param name
	 * @param gameId
	 */
	public static final void addJoinGameMessage(String gameId){
		String msg="["+TYPE_JOIN+","+TankFactory.getInstance().getUserTank().getName()+"]";
		DataSendThread.getInstance().addMessage(msg);
	}
	/**
	 * 发送坦克移动的消息字符串
	 * [类型，玩家编号，X,Y,方向]
	 * @param x
	 * @param y
	 * @param dirStatus
	 * @return
	 */
	public static final void addTankMoveMsg(String name,int x,int y,int dirStatus){
		String msg= "["+TYPE_TANK_MOVE+","+name+","+x+","+y+","+dirStatus+"]";
		DataSendThread.getInstance().addMessage(msg);
	}
	
	/**
	 * 子弹发射消息
	 * [类型，玩家编号，X,Y,SX,SY,方向]
	 * @param x
	 * @param y
	 * @param startX
	 * @param startY
	 * @param dirStatus
	 * @return
	 */
	public static final void addBulletShotMsg(String id,int startX,int startY,int dirStatus){
		String msg= "["+TYPE_BULLET+","+id+","+startX+","+startY+","+dirStatus+"]";
		DataSendThread.getInstance().addMessage(msg);
	}
	
	/**
	 * 接收消息处理方法
	 * @param msg
	 */
	public static final synchronized void MsgProcess(String msg) {
			int endIndex = 0;
			int index = 0;
			while ((index = msg.indexOf('[', endIndex)) != -1) {
				endIndex = msg.indexOf(']', index);
				if(endIndex==-1) continue;
				String[] strs = msg.substring(index + 1, endIndex).split(",");
				try {
				String msgType = strs[0];//消息类型
				if (TYPE_TANK_MOVE.equals(msgType)) {//移动
					TankComp tank = tankList.get(strs[1]);
					if(tank==null) continue;
					tank.moveTo(new Point(Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
					tank.setDirStatus(Integer.parseInt(strs[4]));
				} else if (TYPE_BULLET.equals(msgType)) {//发射
					TankComp tank = tankList.get(strs[1]);
					tank.moveTo(new Point(Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
					tank.setDirStatus(Integer.parseInt(strs[4]));
					tank.shot();
				} else if (TYPE_JOIN_SUCCESS.equals(msgType)) {//加入成功
					TankFactory.getInstance().getUserTank().moveTo(new Point(Integer.parseInt(strs[1]),Integer.parseInt(strs[2])));
					GameController.startGame();
				} else if (TYPE_CREATE_OTHER_TANK.equals(msgType)) {//创建其它坦克
					TankFactory.getInstance().createTank(strs[1],ClientConstant.OTHER,new Point(Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
					System.out.println(msg);
				} else if (TYPE_CREATE_SUCCESS.equals(msgType)){//创建成功
					
				}
				} catch (NullPointerException e) {
					/**
					 * 第79行抛出此异常，是因为此时服务端在往客户端发其它坦克数据， 而此时本地客户端列表中，其它坦克还没完成注册，所以抛出空指针异常
					 */
					System.out.println(msg);
					e.printStackTrace();
				} 
			}
	}
}
