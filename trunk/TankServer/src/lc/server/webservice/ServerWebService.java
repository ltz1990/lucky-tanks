package lc.server.webservice;

import javax.jws.WebService;

@WebService
public interface ServerWebService {
	public MsgEntry login(String username,String password);
	
	public MsgEntry register(String username,String password);

}
