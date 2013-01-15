package lc.server.service.webservice;

import java.net.SocketAddress;

import javax.jws.WebService;

import lc.server.gamecomp.GameHouse;

/**
 * webservice接口
 * @author LUCKY
 *
 */
@WebService
public interface ServerWebService {
	
	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @return
	 */
	public MsgEntry login(String username,String password);
	
	/**
	 * 注册
	 * @param username
	 * @param password
	 * @return
	 */
	public MsgEntry register(String username,String password);
	
	/**
	 * 创建游戏房间
	 * @author LUCKY 2013-1-14
	 * @param house
	 * @return
	 */
	public MsgEntry createGame(GameHouse house,String address);

}
