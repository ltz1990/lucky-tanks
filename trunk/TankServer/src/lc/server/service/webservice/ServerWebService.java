package lc.server.service.webservice;

import javax.jws.WebService;

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

}
