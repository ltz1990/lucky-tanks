package lc.server.applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.Permission;

import javax.swing.JApplet;
import javax.swing.JButton;

public class TankAppet extends JApplet {
	private static final long serialVersionUID = 1L;
	public String clientURL;//客户端URL
	public String startClass;//启动类
	public String startMethod;//启动方法
	
	/**
	 * 取消安全管理器
	 * @author LUCKY
	 *
	 */
	private class DefaultSecurityManager extends SecurityManager {
		@Override
		public void checkPermission(Permission perm, Object context) {
		}

		@Override
		public void checkPermission(Permission perTm) {
		}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		super.start();
		// name="abc11";
		JButton jButton = new JButton("走你！");
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e1) {
				try {
					System.setSecurityManager(new DefaultSecurityManager());
					URLClassLoader loader = new URLClassLoader(
							new URL[] { new URL(
									"http://10.162.0.42:8080/YBFrame/TANKCLIENT.jar") });
					Class<?> loadClass = loader
							.loadClass("lc.client.start.ClientStart");
					Method main = loadClass.getMethod("main", String[].class);
					main.invoke(loadClass.newInstance(),
							new Object[] { new String[0] });
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// JOptionPane.showMessageDialog(null, "123");
			}
		});
		this.add(jButton);
	}

	public String getClientURL() {
		return clientURL;
	}

	public void setClientURL(String clientURL) {
		this.clientURL = clientURL;
	}

	public String getStartClass() {
		return startClass;
	}

	public void setStartClass(String startClass) {
		this.startClass = startClass;
	}

	public String getStartMethod() {
		return startMethod;
	}

	public void setStartMethod(String startMethod) {
		this.startMethod = startMethod;
	}
	
	
}
