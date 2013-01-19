package lc.client.environment;

/**
 * �ͻ������л�����
 * @author LUCKY 2013-1-15
 */
public class RuntimeEnvironment {
	private static UserInfo user;
	
	/**
	 * �����û���Ϣ
	 * @author LUCKY 2013-1-15
	 * @param user
	 */
	public static void setUserInfo(UserInfo user){
		RuntimeEnvironment.user=user;
	}
	
	/**
	 * �õ��û���Ϣ
	 * @author LUCKY 2013-1-15
	 * @return
	 */
	public static UserInfo getUserInfo(){
		return RuntimeEnvironment.user;
	}
}
