package lc.server.service.webservice;

import java.nio.channels.ClosedChannelException;
import java.sql.SQLException;
import java.util.Collection;

import javax.jws.WebService;

import lc.server.database.dao.UserDAO;
import lc.server.gamecomp.GameHouse;
import lc.server.gamecomp.UserInfo;
import lc.server.log.LogUtil;
import lc.server.service.gameserver.GameCtrlCenter;

/**
 * webservice实现类，依照接口的定义来发布公开的方法
 * @author LUCKY
 *
 */
//如果采用接口+实现类的方式，必须指定目标命名空间
@WebService(targetNamespace="http://webservice.service.server.lc/",endpointInterface="lc.server.service.webservice.ServerWebService")
public class ServerWebServiceImpl implements ServerWebService{

	@Override
	public MsgEntry login(String username, String password) {
		// TODO Auto-generated method stub.
		MsgEntry msg=null;
		UserDAO dao=new UserDAO();
		try {
			UserInfo userInfo = dao.login(username, password);
			if(userInfo!=null){
				msg=new MsgEntry(true, "登陆成功!");
				msg.object=userInfo;
				LogUtil.logger.info("[登陆成功]"+username);
			}else{
				msg=new MsgEntry(false,"登陆失败!用户名密码错误!");
				LogUtil.logger.warn("[登陆失败]用户名密码错误"+username);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			msg=new MsgEntry(false, "登陆过程中出现了一个数据库异常!");
			LogUtil.logger.error("[登陆出现异常]"+username,e);
		} catch (Exception e) {
			msg=new MsgEntry(false, "登陆出现未知异常!");
			LogUtil.logger.error("[登陆出现未知异常]"+username,e);
		} finally{
			dao.close();
		}
		return msg;
	}
	@Override
	public MsgEntry register(String username, String password,String name) {
		// TODO Auto-generated method stub
		UserDAO dao=new UserDAO();
		MsgEntry msg=null;
		try {
			dao.register(username, password,name);
			String resultMessage = "用户"+username+"注册成功";
			msg=new MsgEntry(true, resultMessage);
			LogUtil.logger.info(resultMessage);
			return msg;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String message = "用户"+username+"注册失败,用户名已存在";
			msg=new MsgEntry(false, message);
			LogUtil.logger.error(message, e);
			return msg;
		} catch(Exception e) {
			String message = "用户"+username+"注册失败，服务器出现未知异常";
			msg=new MsgEntry(false, message);
			LogUtil.logger.error(message, e);
			return msg;
		} finally{
			dao.close();	
		}
	}
	@Override
	public MsgEntry createGame(GameHouse house,String address) {
		// TODO Auto-generated method stub
		MsgEntry msg=new MsgEntry();
		try{
			GameCtrlCenter center = GameCtrlCenter.getInstance();
			String houseId=center.createGameHouse(house);//得到房间ID
			house.getCreator().setHouseId(houseId);
			center.searchAnotherConnInfo(address, house.getCreator());
			msg.result=true;
			msg.resultMessage="创建成功";
			msg.object=houseId;
		}catch(ClosedChannelException e){
			msg.result=false;
			msg.resultMessage="通道注册失败\n"+e.getMessage();
		}
		return msg;
	}
	@Override
	public GameHouse[] getGameHouses() {
		// TODO Auto-generated method stub
		Collection<GameHouse> list=GameCtrlCenter.getInstance().getGameHouses().values();
		return list.toArray(new GameHouse[0]);
	}
	@Override
	public MsgEntry joinGame(UserInfo userInfo, String address) {
		// TODO Auto-generated method stub
		MsgEntry msg=new MsgEntry();
		try {
			GameCtrlCenter.getInstance().searchAnotherConnInfo(address, userInfo);
			msg.result=true;
			msg.resultMessage="加入成功";
			msg.objectList=GameCtrlCenter.getInstance().getGameHouses().get(userInfo.getHouseId()).getGameThread().getPlayers().values().toArray(new UserInfo[0]);			
		}catch(ClosedChannelException e){
			msg.result=false;
			msg.resultMessage="通道注册失败\n"+e.getMessage();
		}
		return msg;
	}
	@Override
	public UserInfo getPlayer(int id,String houseId) {
		// TODO Auto-generated method stub
		GameHouse house=GameCtrlCenter.getInstance().getGameHouses().get(houseId);
		UserInfo user=house.getGameThread().getPlayers().get(id);
		return user;
	}	
}
