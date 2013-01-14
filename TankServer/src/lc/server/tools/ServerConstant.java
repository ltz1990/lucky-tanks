package lc.server.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import lc.server.log.LogUtil;

/**
 * ����������
 * @author LUCKY
 *
 */
public class ServerConstant {
	public static String SERVER_PATH=new File(System.getProperty("user.dir"))+"\\";
	
	/**����������**/
	public static int SERVER_PORT;//�������˿�
	public static String DB_DRIVER;//������
	public static String DB_CONN_URL;//���ݿ������ַ���
	public static String DB_JAR_URL;//����JAR·��
	public static String DB_USER;//�û���
	public static String DB_PASSWORD;//����
	public static int BUFFER_SIZE;//��������С
	
	/**���ݿ�ű�����**/
	public static String SQL_USER_INSERT;//�û�����SQL
	public static String SQL_USER_SELECT;//�û���ѯSQL
	
	
	/**
	 * ���ط���������
	 */
	public static void loadProperties(){
		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream(SERVER_PATH+"serverconfig.properties"));
			SERVER_PORT=new Integer((String)prop.get("SERVER_PORT"));
			DB_DRIVER=(String)prop.get("DB_DRIVER");
			DB_CONN_URL=(String)prop.get("DB_CONN_URL");
			DB_JAR_URL=(String)prop.get("DB_JAR_URL");
			DB_USER=(String)prop.get("DB_USER");
			DB_PASSWORD=(String)prop.get("DB_PASSWORD");
			BUFFER_SIZE=new Integer((String)prop.get("BUFFER_SIZE"));
			SQL_USER_INSERT=(String)prop.get("SQL_USER_INSERT");
			SQL_USER_SELECT=(String)prop.get("SQL_USER_SELECT");
			LogUtil.logger.info("���������ü������");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LogUtil.logger.error("�޷��ҵ������������ļ�",e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogUtil.logger.error("IO����",e);
		}
	}
	
}
