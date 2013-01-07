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

import lc.client.webservice.wscode.ServerWebService;
import lc.client.webservice.wscode.ServerWebServiceImplService;

/**
 * 远程方法代理类
 * @author LUCKY
 *
 */
public class RemoteServiceProxy{
	private static RemoteServiceProxy remoteService;
	private static ServerWebService proxy;
	private RemoteServiceProxy(){
		try {
			String url = "http://localhost:9998/service?wsdl";
			ServerWebServiceImplService service=new ServerWebServiceImplService();//(new URL(url), new QName("http://webservice.server.lc/", "ServerWebServiceImplService"));
			proxy=service.getServerWebServiceImplPort();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得远程代理
	 * @return
	 */
	public static synchronized ServerWebService getInstance(){
		if(remoteService==null||proxy==null){
			remoteService=new RemoteServiceProxy();
		}
		return proxy;
	}
}