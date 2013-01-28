package lc.client.ui.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;

import lc.client.ui.frame.MainFrame;
import lc.client.util.Debug;

/**
 * ˽��Dialog
 * @author LUCKY
 *
 */
public abstract class LDialog extends JInternalFrame implements ILWindow{

	private static final long serialVersionUID = 1L;
	private static final String START_LWMODAL="startLWModal";
	private static final String STOP_LWMODAL="stopLWModal";
	private boolean isModal;//�Ƿ�ģ̬
	private Thread popUpThread;

	/**
	 * ˽��DIALOG
	 * @param jframe
	 * @param title
	 * @param dialogWidht
	 * @param dialogHeight
	 */
	public LDialog(JFrame jframe,String title,int dialogWidth,int dialogHeight){
		this(jframe,title,dialogWidth,dialogHeight,true);
	}
	
	/**
	 * ˽��DIALOG
	 * @param jframe
	 * @param title
	 * @param dialogWidht
	 * @param dialogHeight
	 */
	public LDialog(JFrame jframe,String title,int dialogWidht,int dialogHeight,boolean isModal){
		super(title,false,false);
		this.setTitle(title);
		this.setLocation((jframe.getSize().width-dialogWidht)/2, (jframe.getSize().height-dialogHeight)/2);
		this.setSize(dialogWidht,dialogHeight);
		this.setResizable(false);
		this.setLayout(null);
		this.isModal=isModal;
		jframe.getLayeredPane().add(this,JLayeredPane.POPUP_LAYER);//��ӵ���һ����൱��ռ����������
	}
	
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		Component cp=null;
		if(comp instanceof ILComponent){
			this.addComponent((ILComponent)comp);
			cp=comp;
		}else{
			cp=super.add(comp);
		}
		return cp;
	}


	/**
	 * ����Զ���ؼ�
	 */
	@Override
	public void addComponent(ILComponent comp) {
		// TODO Auto-generated method stub
		Component[] cps = comp.getChildrenComponents();
		for(Component cp:cps){
			this.add(cp);
		}
	}
	
	/**
	 * �򿪴���
	 */
	public void popUp() {
		popUpThread=Thread.currentThread();//�ϴδ������̣߳��������ģ̬�Ƿ������
		onPopUp();
		this.setVisible(true);
		if(isModal){
			setLWModal(true);//����ģ̬
		}
	}

	/**
	 * �رմ���
	 */
	public void closeDialog(){
		//ĳЩ����¿��ܻ���ֻ���ʧ�ܵ����⣡
		if (isModal) {
			setLWModal(false);// �ر�ģ̬
			/**
			 * ���ڹر�ģ̬��ʱ����е����߳���صĲ���
			 * �����п��ܳ���������������״������ģ̬�ںܳ�ʱ�����޷�������������߳�����WAITING���������޷�������
			 * ���������������߼���ÿ0.1����һ�����߳��ǲ���������̬�����û����������
			 * ��������30�Σ�3���ӣ����̻߳�û�������������У�����Ϊģ̬�ر�ʧ�ܡ�
			 * ��ʱ���ֶ��������̣߳�Ȼ���ٴε��ùرմ��ڷ������ر�LOADING��ģ̬����ֹ���߳�����WAITING��
			 */
			int count = 0;
			while (popUpThread != null
					&& !Thread.State.RUNNABLE.equals(popUpThread.getState())) {
				try {
					Thread.sleep(100);
					if (count++ == 10) {// ��������Ӷ�û�н���ģ̬�������µ��� ģֹ̬ͣ����
						synchronized (popUpThread) {
							popUpThread.notify();
						}
						closeDialog();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.setVisible(false);
	}
	
	/**
	 * ������һ�ε�����ģ̬���ڵ��߳�
	 * @return
	 */
	protected Thread getLastPopUpThread(){
		return popUpThread;
	}
	
	/**
	 * ��ť��������
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		String actionCommand = e.getActionCommand();
		if("OK".equals(actionCommand)){
			onOkBtn();
			this.closeDialog();
		}else if("CANCEL".equals(actionCommand)){
			this.closeDialog();
		}
		btnElse(actionCommand);
	}

	/**
	 * ������ť�¼�
	 * @author LUCKY 2013-1-19
	 * @param e
	 */
	protected void btnElse(String actionCommand) {}

	/**
	 * ����ģ̬���ο�JAVAԴ��</br>
	 * ԭ���޸�JAVA����Ȩ�ޣ�����������ģ̬�����رշ���
	 * @see javax.swing.JOptionPanel#showInternalInputDialog(Component, Object)
	 * @see Container#startLWModal()
	 * @see Container#stopLWModal()
	 * @param isModal �Ƿ�����ģ̬
	 */
	private void setLWModal(boolean isModal) {		
		String methodName;
		if(isModal){
			methodName=START_LWMODAL;
			this.setDialogSelected(true);
		}else{
			methodName=STOP_LWMODAL;
			this.setDialogSelected(false);
		}
		try {
            Object obj;
			obj = AccessController.doPrivileged(new ModalPrivilegedAction(Container.class, methodName));//������Ȩ
            if (obj != null) {
            	Debug.debug(this.getClass().getName()+"��ʼ:"+methodName);
            	Debug.debug("MODELTHREAD:"+popUpThread);
            	if(START_LWMODAL.equals(methodName)){
            		((Method)obj).invoke(this, (Object[])null);
            	}else{
            		((Method)obj).invoke(this, (Object[])null);
            	}
            	Debug.debug(this.getClass().getName()+"����:"+methodName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * ���ô����Ƿ�ѡ��
	 * @param flag
	 */
	private void setDialogSelected(boolean flag) {
		try {
			this.setSelected(flag);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * ������Ȩʵ���� �ο� 
     * @see javax.swing.JOptionPane.ModalPrivilegedAction
     */
    private static class ModalPrivilegedAction implements PrivilegedAction<Object> {
        private Class clazz;
        private String methodName;
        public ModalPrivilegedAction(Class clazz, String methodName) {
            this.clazz = clazz;
            this.methodName = methodName;
        }
        public Object run() {
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(methodName, null);
            } catch (NoSuchMethodException ex) {
            }
            if (method != null) {
                method.setAccessible(true);
            }
            return method;
        }
    }

	/**
	 * ȷ��ʱ����߼��������
	 */
	public abstract void onOkBtn();
	
	/**
	 * �����Ի���ʱ����߼������ص�
	 */
	public abstract void onPopUp();

}
