package lc.server.service.gameserver;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import lc.server.gamecomp.GameHouse;
import lc.server.gamecomp.UserInfo;
import lc.server.log.LogUtil;

/**
 * ��Ϸ��������
 * @author LUCKY 2013-1-14
 */
public class GameCtrlCenter {
	private static GameCtrlCenter gameCtrlCenter;
	@XmlElementWrapper(name = "list")
	@XmlElement(name = "entry")
	private Map<String,GameHouse> gameHouses;//���伯��
	private Map<String,Object> playerConnMate;//�������ƥ�䣬����ƥ��ͬһ����ҵ�WEBSERVICE��NIO

	private GameCtrlCenter(){
		gameHouses=new HashMap<String,GameHouse>();
		playerConnMate=new HashMap<String, Object>();
	}
	
	/**
	 * �õ���������
	 * @author LUCKY 2013-1-14
	 * @return
	 */
	public static synchronized GameCtrlCenter getInstance(){
		if(gameCtrlCenter==null){
			gameCtrlCenter=new GameCtrlCenter();
		}
		return gameCtrlCenter;
	}
	
	/**
	 * ������Ϸ����
	 * @author LUCKY 2013-1-14
	 * @param house
	 */
	public String createGameHouse(GameHouse house){
		String houseId = UUID.randomUUID().toString();
		house.setHouseId(houseId);
		GameThread gameThread=new GameThread();
		house.setGameThread(gameThread);
		gameThread.start();
		gameHouses.put(house.getHouseId(), house);//��������ӵ��б���
		LogUtil.logger.info("[��������]"+house.toString());
		return houseId;
	} 
	
	/**
	 * Ѱ�Ҵ����ӵ�ƥ����Ϣ</br>
	 * NIO��WEBSERVICE���ô˲��֣�Ѱ����һ��������ƥ�䡣NIO�ɴ˵õ�WEBSERVICE����Ϣ
	 * WEBSERVICE�ɴ˵õ�NIO���ӵ���Ϣ������������
	 * @author LUCKY 2013-1-14
	 * @param address
	 * @param obj
	 * @throws ClosedChannelException 
	 */
	public synchronized void searchAnotherConnInfo(String address,Object obj) throws ClosedChannelException{
		Object object = this.playerConnMate.get(address);
		if(object==null){
			this.playerConnMate.put(address, obj);
		}else{//��Ҽ��뷿��
			UserInfo userInfo=null;
			if(object instanceof UserInfo){//�����UUID��˵��MAPԭ������������ѡ�񷿼�
				userInfo = (UserInfo)object;
				GameThread gameThread = gameHouses.get(userInfo.getHouseId()).getGameThread();
				gameThread.register((SocketChannel)obj);
				userInfo.setSocketChannel((SocketChannel)obj);//���ͨ���������Ϣ��
				gameThread.getPlayers().put(userInfo.getUserId(), userInfo);//��������ݣ���ͨ������ӵ��̵߳���Ҽ�����
			}else{//˵��ԭ���������socketchannel��NIO��������
				userInfo = (UserInfo)obj;
				GameThread gameThread = gameHouses.get(userInfo.getHouseId()).getGameThread();
				gameThread.register((SocketChannel)object);
				userInfo.setSocketChannel((SocketChannel)object);
				gameThread.getPlayers().put(userInfo.getUserId(), userInfo);
			}
			this.playerConnMate.remove(address);//��Գɹ����Ƴ�����
			LogUtil.logger.info("[���뷿��]"+userInfo.getUsername()+address);
		}
	}

	/**
	 * �õ��żټ���
	 * @author LUCKY 2013-1-15
	 * @return
	 */
	public Map<String, GameHouse> getGameHouses() {
		return gameHouses;
	}	
	
}
