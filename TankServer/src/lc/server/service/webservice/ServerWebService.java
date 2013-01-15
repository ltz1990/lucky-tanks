package lc.server.service.webservice;

import java.net.SocketAddress;

import javax.jws.WebService;

import lc.server.gamecomp.GameHouse;

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
	public MsgEntry register(String username,String password);
	
	/**
	 * ������Ϸ����
	 * @author LUCKY 2013-1-14
	 * @param house
	 * @return
	 */
	public MsgEntry createGame(GameHouse house,String address);

}
