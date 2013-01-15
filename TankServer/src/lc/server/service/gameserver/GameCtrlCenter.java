package lc.server.service.gameserver;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lc.server.gamecomp.GameHouse;
import lc.server.log.LogUtil;

/**
 * ��Ϸ��������
 * @author LUCKY 2013-1-14
 */
public class GameCtrlCenter {
	private static GameCtrlCenter gameCtrlCenter;
	private Map<String,GameHouse> gameHouses;//���伯��
	private Map<String,Object> playerConnMate;//�������ƥ�䣬����ƥ��ͬһ����ҵ�WEBSERVICE��NIO

	private GameCtrlCenter(){
		gameHouses=Collections.synchronizedMap(new HashMap<String,GameHouse>());
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
		gameHouses.put(house.getHouseId().toString(), house);//��������ӵ��б���
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
	 */
	public synchronized void searchAnotherConnInfo(String address,Object obj){
		Object object = this.playerConnMate.get(address);
		if(object==null){
			this.playerConnMate.put(address, obj);
		}else{
			if(object instanceof String){//�����UUID��˵��MAPԭ������������ѡ�񷿼�
				gameHouses.get((String)object).getGameThread().register((SocketChannel)obj);
			}else{//˵��ԭ���������socketchannel��NIO��������
				gameHouses.get((String)obj).getGameThread().register((SocketChannel)object);
			}
			this.playerConnMate.remove(address);//��Գɹ����Ƴ�����
			LogUtil.logger.info("[���뷿��]"+address);
		}
	}
	
}
