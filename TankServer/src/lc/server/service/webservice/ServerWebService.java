package lc.server.service.webservice;

import javax.jws.WebService;

/**
 * webservice½Ó¿Ú
 * @author LUCKY
 *
 */
@WebService
public interface ServerWebService {
	
	/**
	 * µÇÂ½
	 * @param username
	 * @param password
	 * @return
	 */
	public MsgEntry login(String username,String password);
	
	/**
	 * ×¢²á
	 * @param username
	 * @param password
	 * @return
	 */
	public MsgEntry register(String username,String password);

}
