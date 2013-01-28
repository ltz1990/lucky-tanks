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
 * 私有Dialog
 * @author LUCKY
 *
 */
public abstract class LDialog extends JInternalFrame implements ILWindow{

	private static final long serialVersionUID = 1L;
	private static final String START_LWMODAL="startLWModal";
	private static final String STOP_LWMODAL="stopLWModal";
	private boolean isModal;//是否模态
	private Thread popUpThread;

	/**
	 * 私有DIALOG
	 * @param jframe
	 * @param title
	 * @param dialogWidht
	 * @param dialogHeight
	 */
	public LDialog(JFrame jframe,String title,int dialogWidth,int dialogHeight){
		this(jframe,title,dialogWidth,dialogHeight,true);
	}
	
	/**
	 * 私有DIALOG
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
		jframe.getLayeredPane().add(this,JLayeredPane.POPUP_LAYER);//添加到这一层就相当于占满整个窗口
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
	 * 添加自定义控件
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
	 * 打开窗口
	 */
	public void popUp() {
		popUpThread=Thread.currentThread();//上次打开它的线程（用来检测模态是否结束）
		onPopUp();
		this.setVisible(true);
		if(isModal){
			setLWModal(true);//启动模态
		}
	}

	/**
	 * 关闭窗口
	 */
	public void closeDialog(){
		//某些情况下可能会出现唤醒失败的问题！
		if (isModal) {
			setLWModal(false);// 关闭模态
			/**
			 * 由于关闭模态的时候进行的是线程相关的操作
			 * 所以有可能出现阻塞，死锁等状况导致模态在很长时间里无法结束，造成主线程无限WAITING，主界面无法操作。
			 * 所以在这里增加逻辑，每0.1秒监测一下主线程是不是在运行态，如果没有则继续监测
			 * 如果监测了30次（3秒钟）主线程还没有重新启动运行，则认为模态关闭失败。
			 * 此时就手动唤醒主线程，然后再次调用关闭窗口方法来关闭LOADING的模态，防止主线程无限WAITING。
			 */
			int count = 0;
			while (popUpThread != null
					&& !Thread.State.RUNNABLE.equals(popUpThread.getState())) {
				try {
					Thread.sleep(100);
					if (count++ == 10) {// 如果三秒钟都没有结束模态，则重新调用 模态停止方法
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
	 * 获得最近一次弹出该模态窗口的线程
	 * @return
	 */
	protected Thread getLastPopUpThread(){
		return popUpThread;
	}
	
	/**
	 * 按钮动作监听
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
	 * 其它按钮事件
	 * @author LUCKY 2013-1-19
	 * @param e
	 */
	protected void btnElse(String actionCommand) {}

	/**
	 * 设置模态，参考JAVA源码</br>
	 * 原理：修改JAVA访问权限，调用容器的模态启动关闭方法
	 * @see javax.swing.JOptionPanel#showInternalInputDialog(Component, Object)
	 * @see Container#startLWModal()
	 * @see Container#stopLWModal()
	 * @param isModal 是否启用模态
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
			obj = AccessController.doPrivileged(new ModalPrivilegedAction(Container.class, methodName));//启用特权
            if (obj != null) {
            	Debug.debug(this.getClass().getName()+"开始:"+methodName);
            	Debug.debug("MODELTHREAD:"+popUpThread);
            	if(START_LWMODAL.equals(methodName)){
            		((Method)obj).invoke(this, (Object[])null);
            	}else{
            		((Method)obj).invoke(this, (Object[])null);
            	}
            	Debug.debug(this.getClass().getName()+"结束:"+methodName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * 设置窗口是否被选择
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
     * 方法特权实现类 参考 
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
	 * 确定时候的逻辑：保存等
	 */
	public abstract void onOkBtn();
	
	/**
	 * 弹出对话框时候的逻辑：加载等
	 */
	public abstract void onPopUp();

}
