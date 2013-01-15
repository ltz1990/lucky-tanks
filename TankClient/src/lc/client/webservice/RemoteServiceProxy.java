/**
 * 2013-1-7
 */
/**
 * @author LUCKY
 *
 */
package lc.client.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import lc.client.start.ClientStart;
import lc.client.util.ClientConstant;
import lc.client.webservice.wscode.ServerWebService;
import lc.client.webservice.wscode.ServerWebServiceImplService;

/**
 * Զ�̷���������
 * @author LUCKY
 *	����webservice��ַ�Զ����ɿͻ��˹��ߴ��룺wsimport -p [����] -keep [WEBSERVICE·��]
 * 	���ӣ�wsimport -p my.client -keep http://localhost:9998/service?wsdl
 */
public class RemoteServiceProxy{
	private static RemoteServiceProxy remoteService;
	private static ServerWebService proxy;
	private RemoteServiceProxy() throws Exception{
			String url = "http://"+ClientConstant.SERVER_ADDRESS+":"+(ClientConstant.SERVER_PORT-1)+"/WebService?wsdl";
			//ClientStart.class.getClassLoader().loadClass("lc.client.webservice.wscode.ServerWebServiceImplService");
			Class.forName("lc.client.webservice.wscode.ServerWebServiceImplService", false, ClientStart.class.getClassLoader());
			Class.forName("javax.xml.ws.spi.Provider", false, ClientStart.class.getClassLoader());
			ServerWebServiceImplService service=new ServerWebServiceImplService(new URL(url), new QName("http://webservice.service.server.lc/", "ServerWebServiceImplService"));
			proxy=service.getServerWebServiceImplPort();
	}
	
	/**
	 * ���Զ�̴���
	 * @return
	 */
	public static synchronized ServerWebService getInstance() throws Exception{
		if(remoteService==null||proxy==null){
			remoteService=new RemoteServiceProxy();
		}
		return proxy;
	}
}