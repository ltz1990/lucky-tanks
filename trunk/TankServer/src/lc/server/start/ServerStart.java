package lc.server.start;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import lc.server.core.thread.ServerThread;
import lc.server.database.DBConnection;
import lc.server.database.DriverLoader;
import lc.server.tools.ServerConstant;
import lc.server.tools.ServerRunTimeEnvironment;
import lc.server.webservice.ServiceController;

public class ServerStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerConstant.loadProperties();
		ServerThread thread=new ServerThread();
		thread.start();
		ServiceController.publishWebService();
		DBConnection.getConnection();
	}

}
