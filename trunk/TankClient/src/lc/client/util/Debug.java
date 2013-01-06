package lc.client.util;

/**
 * 调试类 用于打印服务端信息
 * @author LUCKY
 *
 */
public class Debug {
	
	/**
	 * 输出服务端信息
	 * @param obj
	 */
	public static void debug(Object obj){	
		System.out.println(obj);
	}
	
	/**
	 * 输出错误信息
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		System.out.println(obj);
	}
	
}