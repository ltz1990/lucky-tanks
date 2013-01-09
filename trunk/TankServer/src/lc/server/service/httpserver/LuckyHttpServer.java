package lc.server.service.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.xml.ws.Response;

import lc.server.log.Debug;
import lc.server.tools.ServerConstant;

import sun.net.httpserver.HttpServerImpl;
import sun.security.provider.certpath.OCSPResponse.ResponseStatus;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * ��HTTP����Ŀ���Ƿ���һ��HTMLҳ�湩�û�ͨ�����������
 * ��TOMCAT���ܵĻ�û�����ˣ����ܵ���HTMLû��Ҫ��Ӧ�÷�����
 * ��������һ���򵥵�HTTP���񵽷����
 * @author LUCKY
 *
 */
public class LuckyHttpServer{
	private static LuckyHttpServer luckyServer;
	private HttpServer server;
	private HttpContext webService;//��������webService�������Ļ���
	
	private LuckyHttpServer(){
		try {
			server=HttpServer.create(new InetSocketAddress(ServerConstant.SERVER_PORT), 0);
			webService=server.createContext("/WebService");
			server.createContext("/", new TankHttpHandle());//Ϊ���з�������¼�������
			server.setExecutor(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �õ�HTTP������
	 * @return
	 */
	public static synchronized LuckyHttpServer getInstance(){
		if(luckyServer==null){
			luckyServer=new LuckyHttpServer();
		}
		return luckyServer;
	}
	
	/**
	 * ����HTTP SERVER
	 */
	public void start(){		
		server.start();
		Debug.debug("http�����������");
	}
	
	/**
	 * �õ���������webservice�������Ļ���
	 * @return
	 */
	public HttpContext getWebServiceContext(){
		return webService;
	}
	
	/**
	 * �����¼���Ӧ��
	 * @author LUCKY
	 *
	 */
	private class TankHttpHandle implements HttpHandler{
		@Override
		public void handle(HttpExchange ex) throws IOException {
			// TODO Auto-generated method stub
			if(ex.getHttpContext()!=getWebServiceContext()){//��webservice����
				if("GET".equals(ex.getRequestMethod())){//�����GET����
					URI requestURI = ex.getRequestURI();//�����·��
					Debug.debug("HTTP����,����:"+ex.getRemoteAddress()+" "+requestURI);
					File file=new File("."+requestURI.getPath());
					if(file.isFile()&&file.exists()){//���������Ǹ��ļ����Ҹ��ļ�����
						FileInputStream fis=new FileInputStream(file);
						byte[] bf=new byte[(int)file.length()];
						fis.read(bf);
						fis.close();
						ex.sendResponseHeaders(200, bf.length);
						OutputStream responseBody = ex.getResponseBody();
						responseBody.write(bf);
						responseBody.close();
					}
				}
			}else{
				Debug.debug("WebService����,����:"+ex.getRemoteAddress());
			}
		}
		
	}
}
