package lc.server.log;

import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * 调试类 用于打印服务端信息
 * @author LUCKY
 *
 */
public class LogUtil{
	public static final Logger logger=Logger.getRootLogger();

	/**
	 * 输出服务端信息
	 * @param obj
	 */
	public static void debugo(Object obj){	
		//logger.setLevel(Level.ALL);
		logger.error((String)obj);
		//System.out.println(obj);
	}
	
	/**
	 * 输出错误信息
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		logger.info((String)obj);
		}
	
}
