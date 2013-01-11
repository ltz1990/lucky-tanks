package lc.server.service.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;

import lc.server.log.LogUtil;
import lc.server.tools.ServerConstant;

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
			/**
			 * ΪʲôҪ����������� 
			 * ��Ϊ�ͻ�������webservice��ʱ����õ�javax.xml.ws.spi.Provider
			 * �������provider()������ע���������ġ� 
			 * 1.���������ΪMETA-INF/services/javax.xml.ws.spi.Provider����Դ�������ĵ�һ�У�������ڣ�������ʵ����� UTF-8 �������ơ� 
			 * 2.�������$java.home/lib/jaxws.properties �ļ�������ͨ�� java.util.Properties.load(InputStream) ������ȡ�������Ҹ��ļ�������Ϊ
			 * javax.xml.ws.spi.Provider ����Ŀ�������Ŀ��ֵ������ʵ��������ơ� �����������Ϊ
			 * javax.xml.ws.spi.Provider ��ϵͳ���ԣ�������ֵ������ʵ��������ơ� ���ʹ��Ĭ�ϵ�ʵ�������ơ�
			 * 
			 * ���Կ�����������ǻ�������META-INF/services/javax.xml.ws.spi.Provider����Դ��������Դ�����Ƿ���˵İ������ǲ����ڵ�
			 * ��ʱ�����Ƿ�������û�л�д404��Ӧ������û�����κδ����Ҳ���д404��Ӧ�������Կͻ��˾�һֱ�ȴ���
			 * ������META-INF��������Ļ������������ͻ�ȥ�Լ��������������,��д404
			 */
			server.createContext("/META-INF");
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
		LogUtil.logger.info("http�����������");
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
					LogUtil.logger.info("HTTP����,����:"+ex.getRemoteAddress()+" "+requestURI);
					if("/".equals(requestURI.getPath())){//ֱ�ӷ�������
						ex.getResponseHeaders().set("Location", "./index.html");
						//ҳ���ض���ע��������301 ���� 300
						ex.sendResponseHeaders(301, 0);
					}
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
					}else{
						LogUtil.logger.info("�Ҳ��������ļ�:"+file.getName());
						File file404 = new File(".\\error404.html");
						file404.exists();
						FileInputStream fis=new FileInputStream(file404);
						byte[] bf=new byte[(int)file404.length()];
						fis.read(bf);
						fis.close();
						ex.sendResponseHeaders(404, bf.length);
						OutputStream responseBody = ex.getResponseBody();
						responseBody.write(bf);
						responseBody.close();
					}
				}else{
					LogUtil.logger.info("δ֪����:"+ex.getRequestURI());
				}
			}else{
				LogUtil.logger.info("WebService����,����:"+ex.getRemoteAddress());
			}
		}
		
	}
}
