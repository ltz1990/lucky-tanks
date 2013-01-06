package lc.server.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import lc.server.tools.UtilMethod;

/**
 * ������ ���ڴ�ӡ�������Ϣ
 * @author LUCKY
 *
 */
public class Debug {
	
	/**
	 * ����������Ϣ
	 * @param obj
	 */
	public static void debug(Object obj){	
		System.out.println(UtilMethod.getNow()+obj);
	}
	
	/**
	 * ���������Ϣ
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		System.out.println(UtilMethod.getNow()+obj);
	}
	
}
