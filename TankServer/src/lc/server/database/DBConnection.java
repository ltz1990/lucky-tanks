package lc.server.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import lc.server.tools.ServerConstant;

/**
 * 数据库连接
 * @author LUCKY
 *
 */
public class DBConnection {
	static Driver driver;
	static Properties prop;
	@SuppressWarnings("finally")
	public static Connection getConnection(){
		Connection conn=null;
		try {			 
			if(driver==null){	
				prop=new Properties();
				prop.put("user",ServerConstant.DB_USER);
				prop.put("password",ServerConstant.DB_PASSWORD);
				driver=DriverLoader.getInstance().loadDatabaseDriver();
			}
			conn=driver.connect(ServerConstant.DB_CONN_URL, prop);		
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		finally{
			return conn;
		}
	}
}
