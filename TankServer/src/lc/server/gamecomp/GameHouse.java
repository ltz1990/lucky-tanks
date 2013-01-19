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
 * ��Ϸ����
 * ֪ʶ�㣺XmlType ���  XmlTransient ������ֹ��Ԫ��ӳ�䵽XML
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
	private String houseId;//����Ψһ��ʶ UUID
	private String name;//��������
	private UserInfo creator;//������
	private int gameType;//��Ϸ����
	private int playerCount;//�޶��������
	@XmlTransient 
	private GameThread gameThread;//��Ϸ�߳�
	
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
		return "�������ƣ�"+name+",�����ߣ�"+creator.getUsername()+",����ID��"+houseId;
	}
}
