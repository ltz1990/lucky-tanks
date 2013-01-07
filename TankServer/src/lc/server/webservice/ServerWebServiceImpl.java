package lc.server.webservice;

import javax.jws.WebService;

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
