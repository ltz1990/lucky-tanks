package lc.server.gamecomp;

import java.util.UUID;

import lc.server.service.gameserver.GameThread;

/**
 * ��Ϸ����
 * @author LUCKY 2013-1-14
 */
public class GameHouse {
	private String houseId;//����Ψһ��ʶ UUID
	private String name;//��������
	private String creator;//������
	private String gameType;//��Ϸ����
	private String playerCount;//�������
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
		return "�������ƣ�"+name+",�����ߣ�"+creator+",����ID��"+houseId;
	}
}
