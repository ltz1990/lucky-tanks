package lc.client.ui.components;

import java.awt.event.ActionListener;

/**
 * 窗口或者容器控件接口
 * @author LUCKY
 *
 */
public interface ILWindow extends ActionListener{
	/**
	 * 添加私有组件
	 * @param comp
	 */
	public void addComponent(ILComponent comp);
}
