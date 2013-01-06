package lc.client.util;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import lc.client.ui.dialog.LoadingDialog;
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
	
	public static void showMessageDialog(Object obj){
		System.out.println(obj);
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), obj, "消息", JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	/**
	 * 输出错误信息
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		LoadingDialog.getInstance().closeDialog();//关闭LOADING
		String msg = obj+"\n"+e.getMessage();
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), msg,"提示",JOptionPane.ERROR_MESSAGE,errorIcon);
		System.out.println(getNow()+obj+e.getMessage());
		e.printStackTrace();
	}
	
	/**
	 * 获得当前时间DateTime
	 * @return
	 */
	public static String getNow(){
		return "["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"] ";
	}
	
}