package lc.server.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 服务器常量
 * @author LUCKY
 *
 */
public class ServerConstant {
	public static String SERVER_PATH=new File(System.getProperty("user.dir"))+"\\";
	
	public static int SERVER_PORT;//服务器端口
	public static String DB_DRIVER;//驱动类
	public static String DB_CONN_URL;//数据库连接字符串
	public static String DB_JAR_URL;//驱动JAR路径
	public static String DB_USER;//用户名
	public static String DB_PASSWORD;//密码
	public static int BUFFER_SIZE;//缓冲区大小
	
	
	/**
	 * 加载服务器配置
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
