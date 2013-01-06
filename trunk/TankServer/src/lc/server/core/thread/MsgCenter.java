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
 * ��������Ϣ���� �ǵ�����ÿ������һ��
 */
public class MsgCenter {
	public static final String TYPE_JOIN="0";//������Ϸ
	public static final String TYPE_JOIN_SUCCESS="1";//����ɹ�
	public static final String TYPE_JOIN_FAILED="2";//����ʧ��
	public static final String TYPE_CREATE="3";//������Ϸ
	public static final String TYPE_CREATE_SUCCESS="4";//�����ɹ�
	public static final String TYPE_CREATE_FAILED="5";//����ʧ��
	public static final String TYPE_TANK_MOVE="6";//̹���ƶ�
	public static final String TYPE_BULLET="7";//�ڵ�����	
	public static final String TYPE_CREATE_OTHER_TANK="8"; //��������̹��
	
	public static final int GAMETYPE_FLAG=0;//����
	public static final int GAMETYPE_FIGHT=1;//��ս

	protected Map<String, SocketChannel> allTanks;

	public MsgCenter(){
		this.allTanks=new HashMap<String, SocketChannel>();
	}
	/**
	 * ������Ϣ������
	 * 
	 * �˷�����Ҫ�ú�������������
	 * @param msg
	 */
	public synchronized void msgProcess(ByteBuffer buffer, SocketChannel client) {
		try {
			String msg = null;
			msg = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
			buffer.position(0);//��������
			int endIndex = 0;
			int index = 0;
			while ((index = msg.indexOf('[', endIndex)) != -1) {
				endIndex = msg.indexOf(']', index);
				String[] strs = msg.substring(index + 1, endIndex).split(",");
				if (TYPE_JOIN.equals(strs[0])) {// �����������������Ϸ
					// ����ɹ�			
					client.write(ByteBuffer.wrap(("[" + TYPE_JOIN_SUCCESS + ",200,200]").getBytes("UTF-8")));//���ͱ���̹������
					for(String key:allTanks.keySet()){//���������û�̹������
						client.write(ByteBuffer.wrap(("[" + TYPE_CREATE_OTHER_TANK + ","+key+","+100+","+100+"]").getBytes("UTF-8")));//�����˵ĸ���
						try{
						allTanks.get(key).write(ByteBuffer.wrap(("[" + TYPE_CREATE_OTHER_TANK + ","+strs[1]+","+100+","+100+"]").getBytes("UTF-8")));//����ĸ�����
						}catch(ClosedChannelException e){
							e.printStackTrace();
							allTanks.remove(key);//�������˳�
							continue;
						}
					}
					allTanks.put(strs[1], client);//������ʱ���һ��
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
