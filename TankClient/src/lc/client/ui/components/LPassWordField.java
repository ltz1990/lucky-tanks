package lc.client.ui.components;

import java.awt.Component;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lc.client.environment.ClientConstant;

/**
 * 带标签的密码
 * @author LUCKY
 *
 */
public class LPassWordField extends JComponent implements ILComponent{
	private static final long serialVersionUID = 1L;
	private Component[] comps;
	private JTextField textField;
	private JLabel label;
	private String hiddenValue;
	
	/**
	 * 初始化一个带标签的文本框
	 * @param name
	 */
	public LPassWordField(String name){
		label=new JLabel(name);
		label.setSize(ClientConstant.LABLE_WIDTH,ClientConstant.LABLE_HEIGHT);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		textField=new JPasswordField();
		textField.setSize(ClientConstant.TEXTFIELD_WIDTH, ClientConstant.TEXTFIELD_HEIGHT);
		comps=new Component[]{label,textField};
	}
	
	/**
	 * 初始化一个带标签的文本框
	 * @param name
	 * @param x
	 * @param y
	 */
	public LPassWordField(String name,int x,int y){
		label=new JLabel(name);
		label.setBounds(x,y,ClientConstant.LABLE_WIDTH,ClientConstant.LABLE_HEIGHT);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		textField=new JPasswordField();
		textField.setBounds(x+ClientConstant.LABLE_WIDTH+10,y,ClientConstant.TEXTFIELD_WIDTH, ClientConstant.TEXTFIELD_HEIGHT);
		comps=new Component[]{label,textField};
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
	public String getValue(){
		return this.textField.getText();
	}
	
	/**
	 * 设置文本框的值
	 * @param value
	 */
	public void setValue(Object value){
		this.textField.setText((String)value);
	}
	
	/**
	 * 设置隐藏值
	 * @param value
	 */
	public void setHiddenValue(Object value){
		this.hiddenValue=(String)value;
	}
	
	/**
	 * 得到隐藏值  
	 * @return
	 */
	public String getHiddenValue(){
		return this.hiddenValue;
	}
	
	/**
	 * 添加文本输入监听器
	 */
	public void addKeyListener(KeyListener listener){
		textField.addKeyListener(listener);
	}
	
	/**
	 * 设置是否可编辑
	 * @param flag
	 */
	public void setEditable(boolean flag){
		textField.setEditable(false);
	}
	
	/**
	 * 得到文本框对象
	 * @return
	 */
	public JTextField getJTextField(){
		return textField;
	}
	
}
