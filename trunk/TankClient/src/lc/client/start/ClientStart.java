package lc.client.start;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lc.client.ui.frame.MainFrame;
import lc.client.ui.panel.HomePanel;
import lc.client.util.ClientConstant;
import lc.client.util.FontSetting;

/**
 * Main-Class
 * @author LUCKY
 *
 */
public class ClientStart {
	public static void main(String [] args){
		ClientStart start=new ClientStart();
		//appletInit();
		start.setLookAndFeel();	
		start.initClient();
	}

	private static void appletInit() {
		try {
			ClassLoader classLoader = ClientStart.class.getClassLoader();
			//Class.forName("lc.client.util.Debug",false,classLoader);
			Class.forName("javax.swing.ImageIcon",false,classLoader);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����Ƥ��
	 */
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//����Ƥ��
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//initGlobalFontSetting(new Font("΢���ź�", Font.PLAIN, 12));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʼ���ͻ���
	 */
	private void initClient(){
		ClientConstant.loadProperties();//��ʼ��ϵͳ����
		MainFrame mf=MainFrame.getInstance();//׼������
		mf.add(HomePanel.getInstance());
		FontSetting.setChildrenFont(mf,new Font("΢���ź�", Font.PLAIN, 12));
	}
	
	
}
