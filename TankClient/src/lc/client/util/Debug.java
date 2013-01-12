package lc.client.util;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import lc.client.start.ClientStart;
import lc.client.ui.dialog.LoadingDialog;
import lc.client.ui.frame.MainFrame;

/**
 * 调试类 用于打印服务端信息
 * @author LUCKY
 *
 */
public class Debug {
	/**
	 * 为什么在applet上，此处会出异常？
	 * 因为拿到的图片是从Class.class去拿的，而Class是元类型，此类型是由applet的加载器去加载的，
	 * 而applet的JAR包下并没有我的图片资源，所以加载失败、
	 * 这里要用到我启动方法的ClassLoader去加载图片，才能在客户端包下正常加载
	 */
	public static final ImageIcon errorIcon=new ImageIcon(Toolkit.getDefaultToolkit().createImage(ClientStart.class.getResource("/images/error_dialog_icon.png")).getScaledInstance(73, 82, 1));
	
	/**
	 * 输出服务端信息
	 * @param obj
	 */
	public static void debug(Object obj){	
		System.out.println(obj);
	}
	
	/**
	 * 打开消息提示框
	 * @author LUCKY 2013-1-11
	 * @param obj
	 */
	public static void showMessageDialog(Object obj){
		LoadingDialog.getInstance().closeDialog();//关闭LOADING
		System.out.println(obj);
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), obj, "消息", JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	/**
	 * 输出错误信息,弹错误框
	 * @param obj
	 * @param e
	 */
	public static void errorDialog(Object obj,Exception e){
		if(LoadingDialog.getInstance().isShowing())
		LoadingDialog.getInstance().closeDialog();//关闭LOADING
		String errorMsg = e==null?"":e.getMessage();
		String msg = obj+"\n"+errorMsg;
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), msg,"提示",JOptionPane.ERROR_MESSAGE,errorIcon);
		System.out.println(getNow()+obj+errorMsg);
		if(e!=null){
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出错误信息 不弹框
	 * @author LUCKY 2013-1-12
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		String errorMsg = e==null?"":e.getMessage();
		System.out.println(getNow()+obj+errorMsg);
		if(e!=null){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得当前时间DateTime
	 * @return
	 */
	public static String getNow(){
		return "["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"] ";
	}
	
}