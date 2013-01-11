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
			/**
			 * 为什么要配这个环境？ 
			 * 因为客户端请求webservice的时候会用到javax.xml.ws.spi.Provider
			 * 而此类的provider()方法的注释是这样的。 
			 * 1.如果存在名为META-INF/services/javax.xml.ws.spi.Provider的资源，则它的第一行（如果存在）被用作实现类的 UTF-8 编码名称。 
			 * 2.如果存在$java.home/lib/jaxws.properties 文件，可以通过 java.util.Properties.load(InputStream) 方法读取它，并且该文件包含键为
			 * javax.xml.ws.spi.Provider 的条目，则该条目的值被用作实现类的名称。 如果定义了名为
			 * javax.xml.ws.spi.Provider 的系统属性，则它的值被用作实现类的名称。 最后，使用默认的实现类名称。
			 * 
			 * 可以看到这个方法是会先请求META-INF/services/javax.xml.ws.spi.Provider的资源，而该资源在我们服务端的包下面是不存在的
			 * 这时候我们服务器并没有回写404响应，而且没有做任何处理（我不会写404响应），所以客户端就一直等待。
			 * 而加入META-INF这个上下文环境，服务器就会去自己处理这个上下文,回写404
			 */
			server.createContext("/META-INF");
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
		LogUtil.logger.info("http服务启动完成");
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
					LogUtil.logger.info("HTTP请求,来自:"+ex.getRemoteAddress()+" "+requestURI);
					if("/".equals(requestURI.getPath())){//直接访问域名
						ex.getResponseHeaders().set("Location", "./index.html");
						//页面重定向，注意这里是301 不是 300
						ex.sendResponseHeaders(301, 0);
					}
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
					}else{
						LogUtil.logger.info("找不到请求文件:"+file.getName());
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
					LogUtil.logger.info("未知请求:"+ex.getRequestURI());
				}
			}else{
				LogUtil.logger.info("WebService请求,来自:"+ex.getRemoteAddress());
			}
		}
		
	}
}
