package lc.server.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���߷�����
 * @author LUCKY
 *
 */
public class UtilMethod {
	
	/**
	 * ��õ�ǰʱ��DateTime
	 * @return
	 */
	public static String getNow(){
		return "["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"] ";
	}
}
