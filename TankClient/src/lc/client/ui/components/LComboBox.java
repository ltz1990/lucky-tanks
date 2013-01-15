package lc.client.ui.components;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import lc.client.environment.ClientConstant;

/**
 * 带标签的输入框
 * @author LUCKY
 *
 */
public class LComboBox extends JComponent implements ILComponent{
	private static final long serialVersionUID = 1L;
	private Component[] comps;
	private JComboBox comboBox;
	private JLabel label;
	/**
	 * 初始化一个带标签的文本框
	 * @param name
	 */
	public LComboBox(String name){
		label=new JLabel(name);
		label.setSize(ClientConstant.LABLE_WIDTH,ClientConstant.LABLE_HEIGHT);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		comboBox=new JComboBox();
		comboBox.setSize(ClientConstant.TEXTFIELD_WIDTH, ClientConstant.TEXTFIELD_HEIGHT);
		comps=new Component[]{label,comboBox};
	}
	
	/**
	 * 初始化一个带标签的文本框
	 * @param name
	 * @param x
	 * @param y
	 */
	public LComboBox(String name,int x,int y){
		label=new JLabel(name);
		label.setBounds(x,y,ClientConstant.LABLE_WIDTH,ClientConstant.LABLE_HEIGHT);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		comboBox=new JComboBox();
		comboBox.setBounds(x+ClientConstant.LABLE_WIDTH+10,y,ClientConstant.TEXTFIELD_WIDTH, ClientConstant.TEXTFIELD_HEIGHT);
		comps=new Component[]{label,comboBox};
	}
	
	/**
	 * 得到子控件数组
	 */
	public Component[] getChildrenComponents(){
		return comps;
	}
	
	/**
	 * 得到控件值
	 * @return
	 */
	public int getSelectedValue(){
		return ((ComboItem)this.comboBox.getSelectedItem()).getType();
	}
	
	/**
	 * 设置文本框的值
	 * @param value
	 */
	public void addItem(String name,int value){
		this.comboBox.addItem(new ComboItem(name, value));
	}
	
	/**
	 * 设置是否可编辑
	 * @param flag
	 */
	public void setEditable(boolean flag){
		this.comboBox.setEditable(false);
	}
	
	/**
	 * 得到文本框对象
	 * @return
	 */
	public JComboBox getJComboBox(){
		return this.comboBox;
	}
	
	/**
	 * 下拉元素
	 * @author LUCKY
	 *
	 */
	class ComboItem {
		private int type;
		private String name;

		ComboItem(String name,int type){
			this.type=type;
			this.name=name;
		}
		
		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
	
}
