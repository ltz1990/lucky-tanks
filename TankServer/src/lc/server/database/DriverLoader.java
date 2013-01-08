package lc.server.database;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.jar.JarFile;

import javax.management.ReflectionException;

import lc.server.log.Debug;

/**
 * 外部JAR加载类
 * @author LUCKY
 *
 */
public class DriverLoader extends URLClassLoader{
	private static DriverLoader loader;
	private DriverLoader() {
		super(new URL[0], DriverLoader.class.getClassLoader());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 得到JAR加载器
	 * @return
	 */
	public static synchronized DriverLoader getInstance(){
		if(loader==null){
			loader=new DriverLoader();
		}
		return loader;
	}
	
	/**
	 * 添加JAR URL
	 * @param url
	 */
	private void add(URL url){
		super.addURL(url);
	}
	
	/**
	 * 加载数据库连接驱动
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws MalformedURLException 
	 * @throws SQLException 
	 */
	public void loadDatabaseDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException, SQLException{
			File file=new File(new File(System.getProperty("java.class.path")).getParent()+"\\driver\\mysql-connector-java-5.1.13-bin.jar");
			URL url=null;
			url = file.toURI().toURL();
			add(url);//加载JAR
			/**
			 * 执行驱动之前必须先将驱动的JAR加载到JVM中，但由于使用的是URLCLASSLOADER，所以此时JAR中的类不是运行的。具体参照文档
			 * 加载完JAR之后，就可以用LOADCLASS得到驱动类，然后需要getClass一下（这里我也没弄明白，
			 * 网上也没找到相关的资料，按理来说loadClass和getClass都是返回的这个类。）
			 * 后来DEBUG看了一下，loadClass返回的CLASS中genericinfo为NULL，而getClass返回的类中
			 * 是有genericinfo的信息的。所以估计问题出在这里，但是至于这个属性是干嘛的百度也没有结果。
			 * 
			 * 可以得出的结论是，如果直接使用loadClass返回的CLASS去实例化是会失败的，然后就造成
			 * 在进行数据连接时，由于驱动没有运行，所以无法启动数据库连接的情况。
			 * 然而如果使用getClass去实例化，则可以成功实例化类。
			 * 
			 * PS：在网上看到一种说法，可能是由于类加载器不同造成的，全文如下：
			 * 第一个是启动类加载器,它负责加载Java的核心类.它是JVM实现的一部分,不是ClassLoader的子类.
			 * 是用C代码实现的.
			 * 第二个类加载器是扩展类加载器,它负责加载JDK的扩展类,也就是目录配置属性.
			 * 第三个是APP的类加载器,通常用ClassLoader.getSystemClassLoader()可以获得,负责加载CLASSPATH下的类.
			 * 
			 * 但是貌似解决不了这个疑问，并没有指明loadClass和Class.forName()用的类加载器有什么不同先留着吧。
			 * 
			 * =============update===================
			 * 之前的加载是在DBConnection里进行的，语句是
			 * DriverLoader.getInstance().loadClass(xxx).getClass().newInstance();
			 * 但是换过来就不行了，不能有getClass 奇怪。
			 * ==================update=====================
			 * 前面的都是幻觉。。。 其实从来都没有加载成功过，之前以为加载成功 ，其实是没抓异常直接跑了finnally...
			 * 
			 * 下面的就是正儿八经的成功了！其实问题的关键在于Class.forName调用的getCallerClassLoader加载不到后来添加进去的JAR,
			 * 而之前改动的时候都没有想到实例化这个驱动会怎么样！！！ 其实刚才是把驱动实例化的，但是不了解JDBC驱动调用的本质，以为
			 * 只要实例化了驱动就能在DriverManager拿到连接。。其实DriverManager用的也是getCallerClassLoader，所以也拿不到驱动的实例化对象。
			 * 但是我们可以绕过DriverManager，手动将这个实例化对象赋给jdbc的driver接口，然后调用接口的connect方法去获得数据库连接！！！
			 * Connection result = di.driver.connect(url, info); info=prop{user,password};
			 * 参考DriverManager的getConnection方法;
			 */
			Driver driver=(Driver)this.loadClass("com.mysql.jdbc.Driver").newInstance();
			Properties prop = new Properties();
			prop.put("user","luckyta1nk");
			prop.put("password","luckytank");
			driver.connect("jdbc:mysql://localhost:3306/lucky_tank?useUnicode=true&characterEncoding=UTF-8", prop);
		 	//clazz.forName(clazz.getName());
		 	//this.getParent().getParent().loadClass("com.mysql.jdbc.Driver");
			Debug.debug("数据库驱动加载成功!");
	}
	
}
