package lc.client.ui.components;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import lc.client.environment.ClientConstant;

/**
 * ����ǩ�������
 * @author LUCKY
 *
 */
public class LComboBox extends JComponent implements ILComponent{
	private static final long serialVersionUID = 1L;
	private Component[] comps;
	private JComboBox comboBox;
	private JLabel label;
	/**
	 * ��ʼ��һ������ǩ���ı���
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
	 * ��ʼ��һ������ǩ���ı���
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
	 * �õ��ӿؼ�����
	 */
	public Component[] getChildrenComponents(){
		return comps;
	}
	
	/**
	 * �õ��ؼ�ֵ
	 * @return
	 */
	public int getSelectedValue(){
		return ((ComboItem)this.comboBox.getSelectedItem()).getType();
	}
	
	/**
	 * �����ı����ֵ
	 * @param value
	 */
	public void addItem(String name,int value){
		this.comboBox.addItem(new ComboItem(name, value));
	}
	
	/**
	 * �����Ƿ�ɱ༭
	 * @param flag
	 */
	public void setEditable(boolean flag){
		this.comboBox.setEditable(false);
	}
	
	/**
	 * �õ��ı������
	 * @return
	 */
	public JComboBox getJComboBox(){
		return this.comboBox;
	}
	
	/**
	 * ����Ԫ��
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
