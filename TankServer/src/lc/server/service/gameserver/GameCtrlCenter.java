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
 * 游戏控制中心
 * @author LUCKY 2013-1-14
 */
public class GameCtrlCenter {
	private static GameCtrlCenter gameCtrlCenter;
	@XmlElementWrapper(name = "list")
	@XmlElement(name = "entry")
	private Map<String,GameHouse> gameHouses;//房间集合
	private Map<String,Object> playerConnMate;//玩家连接匹配，用来匹配同一个玩家的WEBSERVICE和NIO

	private GameCtrlCenter(){
		gameHouses=new HashMap<String,GameHouse>();
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
		gameHouses.put(house.getHouseId(), house);//将房间添加到列表中
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
	 * @throws ClosedChannelException 
	 */
	public synchronized void searchAnotherConnInfo(String address,Object obj) throws ClosedChannelException{
		Object object = this.playerConnMate.get(address);
		if(object==null){
			this.playerConnMate.put(address, obj);
		}else{//玩家加入房间
			UserInfo userInfo=null;
			if(object instanceof UserInfo){//房间的UUID，说明MAP原来保存的是玩家选择房间
				userInfo = (UserInfo)object;
				GameThread gameThread = gameHouses.get(userInfo.getHouseId()).getGameThread();
				gameThread.register((SocketChannel)obj);
				userInfo.setSocketChannel((SocketChannel)obj);//添加通道到玩家信息中
				gameThread.getPlayers().put(userInfo.getUserId(), userInfo);//将玩家数据（含通道）添加到线程的玩家集合中
			}else{//说明原来保存的是socketchannel，NIO先连接上
				userInfo = (UserInfo)obj;
				GameThread gameThread = gameHouses.get(userInfo.getHouseId()).getGameThread();
				gameThread.register((SocketChannel)object);
				userInfo.setSocketChannel((SocketChannel)object);
				gameThread.getPlayers().put(userInfo.getUserId(), userInfo);
			}
			this.playerConnMate.remove(address);//配对成功，移除数据
			LogUtil.logger.info("[加入房间]"+userInfo.getUsername()+address);
		}
	}

	/**
	 * 得到放假集合
	 * @author LUCKY 2013-1-15
	 * @return
	 */
	public Map<String, GameHouse> getGameHouses() {
		return gameHouses;
	}	
	
}
