package lc.server.service.webservice;

import javax.jws.WebService;

import lc.server.gamecomp.GameHouse;
import lc.server.gamecomp.UserInfo;

/**
 * webservice�ӿ�
 * @author LUCKY
 *
 */
@WebService
public interface ServerWebService {
	
	/**
	 * ��½
	 * @param username
	 * @param password
	 * @return
	 */
	public MsgEntry login(String username,String password);
	
	/**
	 * ע��
	 * @param username
	 * @param password
	 * @return
	 */
	public MsgEntry register(String username,String password,String name);
	
	/**
	 * ������Ϸ����</br>
	 * �ȴ������䣬�õ������ID
	 * Ȼ�󽫷����ID�������䴴���ߣ������䴴���߽�����������ȥƥ���Ӧ��soketͨ��
	 * @author LUCKY 2013-1-14
	 * @param house
	 * @return
	 */
	public MsgEntry createGame(GameHouse house,String address);
	
	/**
	 * �õ���ǰϵͳ�д��ڵ����з���
	 * @author LUCKY 2013-1-15
	 * @return
	 */
	public GameHouse[] getGameHouses();
	
	/**
	 * ������Ϸ
	 * @author LUCKY 2013-1-15
	 * @param userInfo �û���Ϣ����ϣ������ķ���ID
	 * @param address �ͻ��˵�ַ+�˿�
	 * @return
	 */
	public MsgEntry joinGame(UserInfo userInfo,String address);
	
	/**
	 * ��������Ϣ
	 * @author LUCKY 2013-1-22
	 * @param id
	 * @return
	 */
	public UserInfo getPlayer(int id,String houseId);

}
