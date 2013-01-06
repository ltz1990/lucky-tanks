package lc.server.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import lc.server.tools.UtilMethod;

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
		System.out.println(UtilMethod.getNow()+obj);
	}
	
	/**
	 * 输出错误信息
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		System.out.println(UtilMethod.getNow()+obj);
	}
	
}
