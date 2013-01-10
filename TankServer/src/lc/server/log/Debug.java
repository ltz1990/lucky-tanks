package lc.server.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import lc.server.tools.UtilMethod;

/**
 * ������ ���ڴ�ӡ�������Ϣ
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
	 * ����������Ϣ
	 * @param obj
	 */
	public static void debug(Object obj){	
		//logger.setLevel(Level.ALL);
		logger.info((String)obj);
	}
	
	/**
	 * ���������Ϣ
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		logger.info((String)obj);
		}
	
}
