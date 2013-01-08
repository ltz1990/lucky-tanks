package lc.server.start;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import lc.server.core.thread.ServerThread;
import lc.server.database.DBConnection;
import lc.server.database.DriverLoader;
import lc.server.tools.ServerRunTimeEnvironment;
import lc.server.webservice.ServiceController;

public class ServerStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerThread thread=new ServerThread();
		thread.start();
		ServiceController.publishWebService();
		try {
			DriverLoader.getInstance().loadDatabaseDriver();
			//DBConnection.getConnection();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
