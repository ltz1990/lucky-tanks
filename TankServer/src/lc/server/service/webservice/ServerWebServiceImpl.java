package lc.server.service.webservice;

import javax.jws.WebService;

import lc.server.log.Debug;

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
		MsgEntry msg=new MsgEntry();
		msg.result=false;
		Debug.debug("登陆");
		return msg;
	}
	@Override
	public MsgEntry register(String username, String password) {
		// TODO Auto-generated method stub
		MsgEntry msg=new MsgEntry();
		msg.result=true;
		msg.resultMessage="成功";
		Debug.debug("注册");
		return msg;
	}	
}
