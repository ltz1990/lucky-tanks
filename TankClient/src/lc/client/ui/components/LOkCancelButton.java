package lc.client.ui.components;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import lc.client.util.ClientConstant;

/**
 * ȷ��ȡ����ť�飬�Զ���Ӱ�ť��DIALOGĩβ
 * @author LUCKY
 *
 */
public class LOkCancelButton extends JComponent implements ILComponent,AncestorListener{
	private static final long serialVersionUID = 1L;
	private Component comps[];
	private JButton okBtn;
	private JButton cancelBtn;
	private ActionListener listener;
	
	/**
	 * ȷ��ȡ����ť�飬�Զ���Ӱ�ť��DIALOGĩβ
	 * @author LUCKY
	 *
	 */
	public LOkCancelButton(){
		okBtn=new JButton("ȷ��");
		okBtn.setSize( ClientConstant.BTN_WIDTH, ClientConstant.BTN_HEIGHT);
		okBtn.setActionCommand("OK");
		cancelBtn=new JButton("ȡ��");
		cancelBtn.setSize( ClientConstant.BTN_WIDTH, ClientConstant.BTN_HEIGHT);
		cancelBtn.setActionCommand("CANCEL");
		okBtn.addAncestorListener(this);//���������ӵ��������д���
		comps=new Component[]{okBtn,cancelBtn};
	}

	/**
	 * ��ÿؼ��ڲ����ӿؼ�
	 */
	@Override
	public Component[] getChildrenComponents() {
		// TODO Auto-generated method stub
		return this.comps;
	}


	/**
	 * ���������ʱ�̶���λ��
	 */
	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub
		okBtn.setLocation(((okBtn.getParent().getWidth())>>1)-5-ClientConstant.BTN_WIDTH, okBtn.getParent().getHeight()-40);
		//okBtn.addActionListener((JDialog)okBtn.getParent().);
		cancelBtn.setLocation(((cancelBtn.getParent().getWidth())>>1)+5, cancelBtn.getParent().getHeight()-40);
		//cancelBtn.addActionListener((JDialog)cancelBtn.getParent());
		if(listener==null){//��ֹ�ظ����
			listener =(ActionListener)okBtn.getParent().getParent().getParent().getParent();
			okBtn.addActionListener(listener);
			cancelBtn.addActionListener(listener);
		}

	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}


}
