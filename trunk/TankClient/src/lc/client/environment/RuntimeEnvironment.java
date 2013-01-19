package lc.client.environment;

/**
 * 客户端运行环境类
 * @author LUCKY 2013-1-15
 */
public class RuntimeEnvironment {
	private static UserInfo user;
	
	/**
	 * 设置用户信息
	 * @author LUCKY 2013-1-15
	 * @param user
	 */
	public static void setUserInfo(UserInfo user){
		RuntimeEnvironment.user=user;
	}
	
	/**
	 * 得到用户信息
	 * @author LUCKY 2013-1-15
	 * @return
	 */
	public static UserInfo getUserInfo(){
		return RuntimeEnvironment.user;
	}
}
