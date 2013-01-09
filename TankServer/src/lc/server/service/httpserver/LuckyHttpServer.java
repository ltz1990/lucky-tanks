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
 * 简单HTTP服务，目的是返回一个HTML页面供用户通过浏览器访问
 * 用TOMCAT来跑的话没意义了，就跑单个HTML没必要上应用服务器
 * 所以整合一个简单的HTTP服务到服务端
 * @author LUCKY
 *
 */
public class LuckyHttpServer{
	private static LuckyHttpServer luckyServer;
	private HttpServer server;
	private HttpContext webService;//用来部署webService的上下文环境
	
	private LuckyHttpServer(){
		try {
			server=HttpServer.create(new InetSocketAddress(ServerConstant.SERVER_PORT), 0);
			webService=server.createContext("/WebService");
			server.createContext("/", new TankHttpHandle());//为所有访问添加事件管理器
			server.setExecutor(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到HTTP服务器
	 * @return
	 */
	public static synchronized LuckyHttpServer getInstance(){
		if(luckyServer==null){
			luckyServer=new LuckyHttpServer();
		}
		return luckyServer;
	}
	
	/**
	 * 启动HTTP SERVER
	 */
	public void start(){		
		server.start();
		Debug.debug("http服务启动完成");
	}
	
	/**
	 * 得到用来部署webservice的上下文环境
	 * @return
	 */
	public HttpContext getWebServiceContext(){
		return webService;
	}
	
	/**
	 * 访问事件响应类
	 * @author LUCKY
	 *
	 */
	private class TankHttpHandle implements HttpHandler{
		@Override
		public void handle(HttpExchange ex) throws IOException {
			// TODO Auto-generated method stub
			if(ex.getHttpContext()!=getWebServiceContext()){//非webservice请求
				if("GET".equals(ex.getRequestMethod())){//如果是GET请求
					URI requestURI = ex.getRequestURI();//请求的路径
					Debug.debug("HTTP请求,来自:"+ex.getRemoteAddress()+" "+requestURI);
					File file=new File("."+requestURI.getPath());
					if(file.isFile()&&file.exists()){//如果请求的是个文件，且该文件存在
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
				Debug.debug("WebService请求,来自:"+ex.getRemoteAddress());
			}
		}
		
	}
}
