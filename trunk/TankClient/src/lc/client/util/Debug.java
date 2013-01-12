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
 * ������ ���ڴ�ӡ�������Ϣ
 * @author LUCKY
 *
 */
public class Debug {
	/**
	 * Ϊʲô��applet�ϣ��˴�����쳣��
	 * ��Ϊ�õ���ͼƬ�Ǵ�Class.classȥ�õģ���Class��Ԫ���ͣ�����������applet�ļ�����ȥ���صģ�
	 * ��applet��JAR���²�û���ҵ�ͼƬ��Դ�����Լ���ʧ�ܡ�
	 * ����Ҫ�õ�������������ClassLoaderȥ����ͼƬ�������ڿͻ��˰�����������
	 */
	public static final ImageIcon errorIcon=new ImageIcon(Toolkit.getDefaultToolkit().createImage(ClientStart.class.getResource("/images/error_dialog_icon.png")).getScaledInstance(73, 82, 1));
	
	/**
	 * ����������Ϣ
	 * @param obj
	 */
	public static void debug(Object obj){	
		System.out.println(obj);
	}
	
	/**
	 * ����Ϣ��ʾ��
	 * @author LUCKY 2013-1-11
	 * @param obj
	 */
	public static void showMessageDialog(Object obj){
		LoadingDialog.getInstance().closeDialog();//�ر�LOADING
		System.out.println(obj);
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), obj, "��Ϣ", JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	/**
	 * ���������Ϣ,�������
	 * @param obj
	 * @param e
	 */
	public static void errorDialog(Object obj,Exception e){
		if(LoadingDialog.getInstance().isShowing())
		LoadingDialog.getInstance().closeDialog();//�ر�LOADING
		String errorMsg = e==null?"":e.getMessage();
		String msg = obj+"\n"+errorMsg;
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), msg,"��ʾ",JOptionPane.ERROR_MESSAGE,errorIcon);
		System.out.println(getNow()+obj+errorMsg);
		if(e!=null){
			e.printStackTrace();
		}
	}
	
	/**
	 * ���������Ϣ ������
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
	 * ��õ�ǰʱ��DateTime
	 * @return
	 */
	public static String getNow(){
		return "["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"] ";
	}
	
}