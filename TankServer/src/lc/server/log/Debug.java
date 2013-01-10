package lc.server.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import lc.server.tools.UtilMethod;

/**
 * 调试类 用于打印服务端信息
 * @author LUCKY
 *
 */
public class Debug extends Logger{
	protected Debug() {
		super(null, null);
		// TODO Auto-generated constructor stub
	}

	public static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static{
		logger.setLevel(Level.CONFIG);
	}
	/**
	 * 输出服务端信息
	 * @param obj
	 */
	public static void debug(Object obj){	
		//logger.setLevel(Level.ALL);
		logger.info((String)obj);
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
