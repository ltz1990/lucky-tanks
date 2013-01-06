package lc.client.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JMenu;

/**
 * 字体设置工具
 * @author LUCKY
 *
 */
public class FontSetting {
	
	/**
	 * 设置所有子节点的字体
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
