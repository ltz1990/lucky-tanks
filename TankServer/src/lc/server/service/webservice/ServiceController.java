package lc.server.service.webservice;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import lc.server.log.Debug;
import lc.server.service.gameserver.ServerThread;
import lc.server.service.httpserver.LuckyHttpServer;
import lc.server.tools.ServerConstant;

/**
 * WEBSERVICE������
 * 
 * @author LUCKY
 * 
 */
public class ServiceController {
	
	/**
	 * ����webservice
	 */
	public static void publishWebService() {
		ServerWebServiceImpl service = new ServerWebServiceImpl();
		//������httpContext��ȥ
		Endpoint.create(service).publish(LuckyHttpServer.getInstance().getWebServiceContext());
		Debug.debug("WebService �������");
	}
}
