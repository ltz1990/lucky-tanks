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
 * ����ǩ������
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
	 * ��ʼ��һ������ǩ���ı���
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
	 * ��ʼ��һ������ǩ���ı���
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
	 * �õ��ӿؼ�����
	 */
	public Component[] getChildrenComponents(){
		return comps;
	}
	
	/**
	 * �õ��ؼ�ֵ
	 * @return
	 */
	public String getValue(){
		return this.textField.getText();
	}
	
	/**
	 * �����ı����ֵ
	 * @param value
	 */
	public void setValue(Object value){
		this.textField.setText((String)value);
	}
	
	/**
	 * ��������ֵ
	 * @param value
	 */
	public void setHiddenValue(Object value){
		this.hiddenValue=(String)value;
	}
	
	/**
	 * �õ�����ֵ  
	 * @return
	 */
	public String getHiddenValue(){
		return this.hiddenValue;
	}
	
	/**
	 * ����ı����������
	 */
	public void addKeyListener(KeyListener listener){
		textField.addKeyListener(listener);
	}
	
	/**
	 * �����Ƿ�ɱ༭
	 * @param flag
	 */
	public void setEditable(boolean flag){
		textField.setEditable(false);
	}
	
	/**
	 * �õ��ı������
	 * @return
	 */
	public JTextField getJTextField(){
		return textField;
	}
	
}
