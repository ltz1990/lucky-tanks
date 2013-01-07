package lc.server.webservice;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import lc.server.log.Debug;

/**
 * WEBSERVICE������
 * 
 * @author LUCKY
 * 
 */
public class ServiceController {
	public static void publishWebService() {
		ServerWebServiceImpl service = new ServerWebServiceImpl();
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			InetAddress[] ips = InetAddress.getAllByName(hostName);
			for (InetAddress ip : ips) {
				try {
					Endpoint.publish("http://" + ip.getHostAddress()+ ":8080/service", service);
					Debug.debug("�ѷ�����" + ip.getHostAddress() + "|" + ip);
				} catch (IllegalArgumentException e) {
					Debug.debug("����ʧ�ܣ�" + ip.getHostAddress() + "|" + ip);
				} 
			}
			Endpoint.publish("http://localhost:8080/service", service);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
