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
 * �ⲿJAR������
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
	 * �õ�JAR������
	 * @return
	 */
	public static synchronized DriverLoader getInstance(){
		if(loader==null){
			loader=new DriverLoader();
		}
		return loader;
	}
	
	/**
	 * ���JAR URL
	 * @param url
	 */
	private void add(URL url){
		super.addURL(url);
	}
	
	/**
	 * �������ݿ���������
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
			add(url);//����JAR
			/**
			 * ִ������֮ǰ�����Ƚ�������JAR���ص�JVM�У�������ʹ�õ���URLCLASSLOADER�����Դ�ʱJAR�е��಻�����еġ���������ĵ�
			 * ������JAR֮�󣬾Ϳ�����LOADCLASS�õ������࣬Ȼ����ҪgetClassһ�£�������ҲûŪ���ף�
			 * ����Ҳû�ҵ���ص����ϣ�������˵loadClass��getClass���Ƿ��ص�����ࡣ��
			 * ����DEBUG����һ�£�loadClass���ص�CLASS��genericinfoΪNULL����getClass���ص�����
			 * ����genericinfo����Ϣ�ġ����Թ�����������������������������Ǹ���İٶ�Ҳû�н����
			 * 
			 * ���Եó��Ľ����ǣ����ֱ��ʹ��loadClass���ص�CLASSȥʵ�����ǻ�ʧ�ܵģ�Ȼ������
			 * �ڽ�����������ʱ����������û�����У������޷��������ݿ����ӵ������
			 * Ȼ�����ʹ��getClassȥʵ����������Գɹ�ʵ�����ࡣ
			 * 
			 * PS�������Ͽ���һ��˵�����������������������ͬ��ɵģ�ȫ�����£�
			 * ��һ���������������,���������Java�ĺ�����.����JVMʵ�ֵ�һ����,����ClassLoader������.
			 * ����C����ʵ�ֵ�.
			 * �ڶ��������������չ�������,���������JDK����չ��,Ҳ����Ŀ¼��������.
			 * ��������APP���������,ͨ����ClassLoader.getSystemClassLoader()���Ի��,�������CLASSPATH�µ���.
			 * 
			 * ����ò�ƽ������������ʣ���û��ָ��loadClass��Class.forName()�õ����������ʲô��ͬ�����Űɡ�
			 * 
			 * =============update===================
			 * ֮ǰ�ļ�������DBConnection����еģ������
			 * DriverLoader.getInstance().loadClass(xxx).getClass().newInstance();
			 * ���ǻ������Ͳ����ˣ�������getClass ��֡�
			 * ==================update=====================
			 * ǰ��Ķ��ǻþ������� ��ʵ������û�м��سɹ�����֮ǰ��Ϊ���سɹ� ����ʵ��ûץ�쳣ֱ������finnally...
			 * 
			 * ����ľ��������˾��ĳɹ��ˣ���ʵ����Ĺؼ�����Class.forName���õ�getCallerClassLoader���ز���������ӽ�ȥ��JAR,
			 * ��֮ǰ�Ķ���ʱ��û���뵽ʵ���������������ô�������� ��ʵ�ղ��ǰ�����ʵ�����ģ����ǲ��˽�JDBC�������õı��ʣ���Ϊ
			 * ֻҪʵ����������������DriverManager�õ����ӡ�����ʵDriverManager�õ�Ҳ��getCallerClassLoader������Ҳ�ò���������ʵ��������
			 * �������ǿ����ƹ�DriverManager���ֶ������ʵ�������󸳸�jdbc��driver�ӿڣ�Ȼ����ýӿڵ�connect����ȥ������ݿ����ӣ�����
			 * Connection result = di.driver.connect(url, info); info=prop{user,password};
			 * �ο�DriverManager��getConnection����;
			 */
			Driver driver=(Driver)this.loadClass("com.mysql.jdbc.Driver").newInstance();
			Properties prop = new Properties();
			prop.put("user","luckyta1nk");
			prop.put("password","luckytank");
			driver.connect("jdbc:mysql://localhost:3306/lucky_tank?useUnicode=true&characterEncoding=UTF-8", prop);
		 	//clazz.forName(clazz.getName());
		 	//this.getParent().getParent().loadClass("com.mysql.jdbc.Driver");
			Debug.debug("���ݿ��������سɹ�!");
	}
	
}
