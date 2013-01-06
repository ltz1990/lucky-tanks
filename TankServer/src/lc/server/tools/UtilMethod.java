package lc.server.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具方法类
 * @author LUCKY
 *
 */
public class UtilMethod {
	
	/**
	 * 获得当前时间DateTime
	 * @return
	 */
	public static String getNow(){
		return "["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"] ";
	}
}
