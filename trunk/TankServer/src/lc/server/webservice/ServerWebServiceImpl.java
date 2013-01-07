package lc.server.webservice;

import javax.jws.WebService;

/**
 * webservice实现类，依照接口的定义来发布公开的方法
 * @author LUCKY
 *
 */
@WebService(endpointInterface="lc.server.webservice.ServerWebService")
public class ServerWebServiceImpl implements ServerWebService{

	@Override
	public MsgEntry login(String username, String password) {
		// TODO Auto-generated method stub
		MsgEntry msg=new MsgEntry();
		msg.result=false;
		return msg;
	}

	@Override
	public MsgEntry register(String username, String password) {
		// TODO Auto-generated method stub
		MsgEntry msg=new MsgEntry();
		msg.result=true;
		return msg;
	}	
}
