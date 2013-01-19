package lc.server.gamecomp;

import java.util.UUID;

import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import lc.server.service.gameserver.GameThread;

/**
 * 游戏房间
 * 知识点：XmlType 配合  XmlTransient 可以阻止把元素映射到XML
 * @author LUCKY 2013-1-14
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gameHouse", propOrder = {
    "creator",
    "gameType",
    "houseId",
    "name",
    "playerCount"
})
public class GameHouse {
	private String houseId;//房间唯一标识 UUID
	private String name;//房间名称
	private UserInfo creator;//创建者
	private int gameType;//游戏类型
	private int playerCount;//限定玩家数量
	@XmlTransient 
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

	public UserInfo getCreator() {
		return creator;
	}

	public void setCreator(UserInfo creator) {
		this.creator = creator;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
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
		return "房间名称："+name+",创建者："+creator.getUsername()+",房间ID："+houseId;
	}
}
