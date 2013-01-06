package lc.client.core.net;

import java.awt.Point;
import java.util.Map;

import lc.client.core.components.TankComp;
import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.util.ClientConstant;

/**
 * ��Ϣ����
 * @author LUCKY
 *
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
	
	private static Map<String, TankComp> tankList=TankFactory.getInstance().getTankList();
	
	/**
	 * ������Ϸ
	 * [���ͣ�����ID�������ߣ���Ϸ���ͣ��������]
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
	 * ������Ϸ
	 * [���ͣ���ұ��]
	 * @param name
	 * @param gameId
	 */
	public static final void addJoinGameMessage(String gameId){
		String msg="["+TYPE_JOIN+","+TankFactory.getInstance().getUserTank().getName()+"]";
		DataSendThread.getInstance().addMessage(msg);
	}
	/**
	 * ����̹���ƶ�����Ϣ�ַ���
	 * [���ͣ���ұ�ţ�X,Y,����]
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
	 * �ӵ�������Ϣ
	 * [���ͣ���ұ�ţ�X,Y,SX,SY,����]
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
	 * ������Ϣ������
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
				String msgType = strs[0];//��Ϣ����
				if (TYPE_TANK_MOVE.equals(msgType)) {//�ƶ�
					TankComp tank = tankList.get(strs[1]);
					if(tank==null) continue;
					tank.moveTo(new Point(Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
					tank.setDirStatus(Integer.parseInt(strs[4]));
				} else if (TYPE_BULLET.equals(msgType)) {//����
					TankComp tank = tankList.get(strs[1]);
					tank.moveTo(new Point(Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
					tank.setDirStatus(Integer.parseInt(strs[4]));
					tank.shot();
				} else if (TYPE_JOIN_SUCCESS.equals(msgType)) {//����ɹ�
					TankFactory.getInstance().getUserTank().moveTo(new Point(Integer.parseInt(strs[1]),Integer.parseInt(strs[2])));
					GameController.startGame();
				} else if (TYPE_CREATE_OTHER_TANK.equals(msgType)) {//��������̹��
					TankFactory.getInstance().createTank(strs[1],ClientConstant.OTHER,new Point(Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
					System.out.println(msg);
				} else if (TYPE_CREATE_SUCCESS.equals(msgType)){//�����ɹ�
					
				}
				} catch (NullPointerException e) {
					/**
					 * ��79���׳����쳣������Ϊ��ʱ����������ͻ��˷�����̹�����ݣ� ����ʱ���ؿͻ����б��У�����̹�˻�û���ע�ᣬ�����׳���ָ���쳣
					 */
					System.out.println(msg);
					e.printStackTrace();
				} 
			}
	}
}
