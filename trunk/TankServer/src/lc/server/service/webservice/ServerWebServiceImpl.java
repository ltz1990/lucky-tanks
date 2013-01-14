package lc.server.service.webservice;

import java.sql.SQLException;

import javax.jws.WebService;

import lc.server.database.dao.UserDAO;
import lc.server.log.LogUtil;

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
			if(dao.login(username, password)){
				msg=new MsgEntry(true, "登陆成功!");
				LogUtil.logger.info(username+"登陆成功");
			}else{
				msg=new MsgEntry(false,"登陆失败!用户名密码错误!");
				LogUtil.logger.warn(username+"登陆失败，用户名密码错误");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			msg=new MsgEntry(false, "登陆过程中出现了一个数据库异常!");
			LogUtil.logger.error(username+"登陆出现异常!",e);
		} catch (Exception e) {
			msg=new MsgEntry(false, "登陆出现未知异常!");
			LogUtil.logger.error(username+"登陆出现未知异常!",e);
		} finally{
			dao.close();
		}
		return msg;
	}
	@Override
	public MsgEntry register(String username, String password) {
		// TODO Auto-generated method stub
		UserDAO dao=new UserDAO();
		MsgEntry msg=null;
		try {
			dao.register(username, password);
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
}
