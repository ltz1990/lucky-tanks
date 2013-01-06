package lc.client.util;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import lc.client.ui.frame.MainFrame;

/**
 * 调试类 用于打印服务端信息
 * @author LUCKY
 *
 */
public class Debug {
	public static final ImageIcon errorIcon=new ImageIcon(Toolkit.getDefaultToolkit().createImage(Class.class.getResource("/images/error_dialog_icon.png")).getScaledInstance(73, 82, 1));
	
	/**
	 * 输出服务端信息
	 * @param obj
	 */
	public static void debug(Object obj){	
		System.out.println(obj);
	}
	
	/**
	 * 输出错误信息
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), obj+"\n"+e.getMessage(),"提示",JOptionPane.ERROR_MESSAGE,errorIcon);
		System.out.println(obj);
		e.printStackTrace();
	}
	
}