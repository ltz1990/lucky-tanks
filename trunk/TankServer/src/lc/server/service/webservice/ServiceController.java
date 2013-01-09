package lc.server.service.webservice;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import lc.server.log.Debug;
import lc.server.service.gameserver.ServerThread;
import lc.server.service.httpserver.LuckyHttpServer;
import lc.server.tools.ServerConstant;

/**
 * WEBSERVICE控制类
 * 
 * @author LUCKY
 * 
 */
public class ServiceController {
	
	/**
	 * 发布webservice
	 */
	public static void publishWebService() {
		ServerWebServiceImpl service = new ServerWebServiceImpl();
		//发布到httpContext中去
		Endpoint.create(service).publish(LuckyHttpServer.getInstance().getWebServiceContext());
		Debug.debug("WebService 发布完成");
	}
}
