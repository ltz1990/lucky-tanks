package lc.client.util;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import lc.client.ui.frame.MainFrame;

/**
 * ������ ���ڴ�ӡ�������Ϣ
 * @author LUCKY
 *
 */
public class Debug {
	public static final ImageIcon errorIcon=new ImageIcon(Toolkit.getDefaultToolkit().createImage(Class.class.getResource("/images/error_dialog_icon.png")).getScaledInstance(73, 82, 1));
	
	/**
	 * ����������Ϣ
	 * @param obj
	 */
	public static void debug(Object obj){	
		System.out.println(obj);
	}
	
	/**
	 * ���������Ϣ
	 * @param obj
	 * @param e
	 */
	public static void error(Object obj,Exception e){
		JOptionPane.showInternalMessageDialog(MainFrame.getInstance().getContentPane(), obj+"\n"+e.getMessage(),"��ʾ",JOptionPane.ERROR_MESSAGE,errorIcon);
		System.out.println(obj);
		e.printStackTrace();
	}
	
}