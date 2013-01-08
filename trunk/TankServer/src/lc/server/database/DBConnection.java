package lc.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接
 * @author LUCKY
 *
 */
public class DBConnection {
	
	@SuppressWarnings("finally")
	public static Connection getConnection(){
		Connection conn=null;
		try {
			//ClassLoader.getSystemClassLoader().loadClass("com.mysql.jdbc.Driver");
			//DriverLoader.getInstance().loadClass().newInstance();
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/lucky_tank?useUnicode=true&characterEncoding=UTF-8", "luck1ytank", "luckytank");		
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
