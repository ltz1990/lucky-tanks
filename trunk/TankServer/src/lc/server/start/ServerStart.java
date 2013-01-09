package lc.server.start;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import lc.server.database.DBConnection;
import lc.server.database.DriverLoader;
import lc.server.service.gameserver.ServerThread;
import lc.server.service.httpserver.LuckyHttpServer;
import lc.server.service.webservice.ServiceController;
import lc.server.tools.ServerConstant;
import lc.server.tools.ServerRunTimeEnvironment;

public class ServerStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerConstant.loadProperties();
		LuckyHttpServer.getInstance().start();
		ServiceController.publishWebService();
		ServerThread thread=new ServerThread();
		thread.start();
	}

}
