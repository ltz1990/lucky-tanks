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
 * 游戏控制中心
 * @author LUCKY 2013-1-14
 */
public class GameCtrlCenter {
	private static GameCtrlCenter gameCtrlCenter;
	private Map<String,GameHouse> gameHouses;//房间集合
	private Map<String,Object> playerConnMate;//玩家连接匹配，用来匹配同一个玩家的WEBSERVICE和NIO

	private GameCtrlCenter(){
		gameHouses=Collections.synchronizedMap(new HashMap<String,GameHouse>());
		playerConnMate=new HashMap<String, Object>();
	}
	
	/**
	 * 得到控制中心
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
	 * 创建游戏房间
	 * @author LUCKY 2013-1-14
	 * @param house
	 */
	public String createGameHouse(GameHouse house){
		String houseId = UUID.randomUUID().toString();
		house.setHouseId(houseId);
		GameThread gameThread=new GameThread();
		house.setGameThread(gameThread);
		gameThread.start();
		gameHouses.put(house.getHouseId().toString(), house);//将房间添加到列表中
		LogUtil.logger.info("[创建房间]"+house.toString());
		return houseId;
	}
	
	/**
	 * 寻找此连接的匹配信息</br>
	 * NIO或WEBSERVICE调用此部分，寻找另一边连接来匹配。NIO由此得到WEBSERVICE的信息
	 * WEBSERVICE由此得到NIO连接的信息，来分配连接
	 * @author LUCKY 2013-1-14
	 * @param address
	 * @param obj
	 */
	public synchronized void searchAnotherConnInfo(String address,Object obj){
		Object object = this.playerConnMate.get(address);
		if(object==null){
			this.playerConnMate.put(address, obj);
		}else{
			if(object instanceof String){//房间的UUID，说明MAP原来保存的是玩家选择房间
				gameHouses.get((String)object).getGameThread().register((SocketChannel)obj);
			}else{//说明原来保存的是socketchannel，NIO先连接上
				gameHouses.get((String)obj).getGameThread().register((SocketChannel)object);
			}
			this.playerConnMate.remove(address);//配对成功，移除数据
			LogUtil.logger.info("[加入房间]"+address);
		}
	}
	
}
