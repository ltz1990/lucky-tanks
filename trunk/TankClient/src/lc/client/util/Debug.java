package lc.client.util;

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
		System.out.println(obj);
	}
	
	/**
	 * ���������Ϣ
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		System.out.println(obj);
	}
	
}