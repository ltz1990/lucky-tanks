package lc.server.webservice;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import lc.server.core.thread.ServerThread;
import lc.server.log.Debug;
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
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			InetAddress[] ips = InetAddress.getAllByName(hostName);
			for (InetAddress ip : ips) {
				try {
					Endpoint.publish("http://" + ip.getHostAddress()+ ":"+(ServerConstant.SERVER_PORT-1)+"/service", service);
					Debug.debug("已发布：" + ip.getHostAddress() + "|" + ip);
				} catch (IllegalArgumentException e) {
					Debug.debug("发布失败：" + ip.getHostAddress() + "|" + ip);
				} 
			}
			Endpoint.publish("http://localhost:"+(ServerConstant.SERVER_PORT-1)+"/service", service);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
