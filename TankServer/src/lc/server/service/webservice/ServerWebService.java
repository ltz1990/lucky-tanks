package lc.server.service.webservice;

import javax.jws.WebService;

import lc.server.gamecomp.GameHouse;
import lc.server.gamecomp.UserInfo;

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
	public MsgEntry register(String username,String password,String name);
	
	/**
	 * 创建游戏房间</br>
	 * 先创建房间，得到房间的ID
	 * 然后将房间的ID赋给房间创建者，将房间创建者交给控制中心去匹配对应的soket通道
	 * @author LUCKY 2013-1-14
	 * @param house
	 * @return
	 */
	public MsgEntry createGame(GameHouse house,String address);
	
	/**
	 * 得到当前系统中存在的所有房间
	 * @author LUCKY 2013-1-15
	 * @return
	 */
	public GameHouse[] getGameHouses();
	
	/**
	 * 加入游戏
	 * @author LUCKY 2013-1-15
	 * @param userInfo 用户信息，及希望加入的房间ID
	 * @param address 客户端地址+端口
	 * @return
	 */
	public MsgEntry joinGame(UserInfo userInfo,String address);
	
	/**
	 * 获得玩家信息
	 * @author LUCKY 2013-1-22
	 * @param id
	 * @return
	 */
	public UserInfo getPlayer(int id,String houseId);

}
