package lc.server.gamecomp;

import java.util.UUID;

import lc.server.service.gameserver.GameThread;

/**
 * 游戏房间
 * @author LUCKY 2013-1-14
 */
public class GameHouse {
	private String houseId;//房间唯一标识 UUID
	private String name;//房间名称
	private String creator;//创建者
	private String gameType;//游戏类型
	private String playerCount;//玩家数量
	private GameThread gameThread;//游戏线程
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getPlayerCount() {
		return playerCount;
	}
	public void setPlayerCount(String playerCount) {
		this.playerCount = playerCount;
	}
	public GameThread getGameThread() {
		return gameThread;
	}
	public void setGameThread(GameThread gameThread) {
		this.gameThread = gameThread;
	}
	@Override
	public String toString(){
		return "房间名称："+name+",创建者："+creator+",房间ID："+houseId;
	}
}
