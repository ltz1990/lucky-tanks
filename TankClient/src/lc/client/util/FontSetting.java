package lc.client.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JMenu;

/**
 * �������ù���
 * @author LUCKY
 *
 */
public class FontSetting {
	
	/**
	 * ���������ӽڵ������
	 * @param parent
	 * @param font
	 */
	public static void setChildrenFont(Container parent,Font font){
		Component[] children=null;
		if(parent instanceof JMenu){
			children=((JMenu)parent).getMenuComponents();
		}else{
			children=parent.getComponents();
		}
		for(Component child:children){
			child.setFont(font);
			setChildrenFont((Container)child, font);
		}
	}
}
