package lc.server.log;

import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * ������ ���ڴ�ӡ�������Ϣ
 * @author LUCKY
 *
 */
public class LogUtil{
	public static final Logger logger=Logger.getRootLogger();

	/**
	 * ����������Ϣ
	 * @param obj
	 */
	public static void debugo(Object obj){	
		//logger.setLevel(Level.ALL);
		logger.error((String)obj);
		//System.out.println(obj);
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
