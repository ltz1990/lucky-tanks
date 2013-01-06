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

/**
 * 私有Dialog
 * @author LUCKY
 *
 */
public abstract class LDialog extends JInternalFrame implements ILWindow{

	private static final long serialVersionUID = 1L;
	private static final String START_LWMODAL="startLWModal";
	private static final String STOP_LWMODAL="stopLWModal";

	/**
	 * 私有DIALOG
	 * @param jframe
	 * @param title
	 * @param dialogWidht
	 * @param dialogHeight
	 */
	public LDialog(JFrame jframe,String title,int dialogWidht,int dialogHeight){
		super(title,false,false);
		this.setTitle(title);
		this.setLocation((jframe.getSize().width-dialogWidht)/2, (jframe.getSize().height-dialogHeight)/2);
		this.setSize(dialogWidht,dialogHeight);
		this.setResizable(false);
		this.setLayout(null);
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
		onPopUp();
		this.setVisible(true);
		setLWModal(true);//启动模态
	}

	/**
	 * 关闭窗口
	 */
	public void closeDialog(){
		setLWModal(false);//关闭模态
		this.setVisible(false);
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
	}

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
		}else{
			methodName=STOP_LWMODAL;
		}
		try {
            Object obj;
			obj = AccessController.doPrivileged(new ModalPrivilegedAction(Container.class, methodName));//启用特权
            if (obj != null) {
            	((Method)obj).invoke(this, (Object[])null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		if(isModal){//选中
			this.setDialogSelected(true);
		}else{
			this.setDialogSelected(false);
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
