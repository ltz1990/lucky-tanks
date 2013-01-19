package lc.client.ui.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.ConnectException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.xml.ws.WebServiceException;

import lc.client.core.task.LoadingTask;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.components.LPassWordField;
import lc.client.ui.components.LTextField;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.menu.MainMenu;
import lc.client.util.Debug;
import lc.client.util.FontSetting;
import lc.client.webservice.RemoteServiceProxy;
import lc.client.webservice.wscode.MsgEntry;

/**
 * 登陆窗口
 * @author LUCKY 2013-1-12
 */
public class RegisterDialog extends LDialog{
	private static final long serialVersionUID = 1L;
	private static RegisterDialog loginDialog;
	private LOkCancelButton buttons;
	private LTextField username;
	private LTextField name;
	private LPassWordField password;
	private LPassWordField password2;
	private JLabel tips; 
	
	private RegisterDialog() {
		super(MainFrame.getInstance(),"登陆", 300, 240);
		TextInputVerifier textInputVerifier = new TextInputVerifier();
		username=(LTextField)this.add(new LTextField("用户名 :",-10, 30));
		username.getJTextField().addCaretListener(textInputVerifier);
		name=(LTextField)this.add(new LTextField("昵称 :",-10,60));
		name.getJTextField().addCaretListener(textInputVerifier);
		password=(LPassWordField)this.add(new LPassWordField("密码 :",-10,90));
		password.getJTextField().addCaretListener(textInputVerifier);
		password2=(LPassWordField)this.add(new LPassWordField("确认密码 :",-10,120));
		password2.getJTextField().addCaretListener(textInputVerifier);
		tips=(JLabel)this.add(new JLabel());
		tips.setBounds(10, 5, 280, 30);
		tips.setHorizontalAlignment(JLabel.CENTER);
		buttons = new LOkCancelButton();
		this.add(buttons);
		buttons.getOkButton().setEnabled(false);
		FontSetting.setChildrenFont(this,new Font("微软雅黑", Font.PLAIN, 12));
		tips.setForeground(Color.red);
	}
	
	public static synchronized RegisterDialog getInstance(){
		if(loginDialog==null){
			loginDialog=new RegisterDialog();
		}
		return loginDialog;
	}

	/**
	 * 按钮动作监听
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		String actionCommand = e.getActionCommand();
		if("OK".equals(actionCommand)){
			onOkBtn();
			//this.closeDialog();
		}else if("CANCEL".equals(actionCommand)){
			this.closeDialog();
		}
	}
	
	@Override
	public void onOkBtn() {
		// TODO Auto-generated method stub
		this.closeDialog();//这里可能实际上并没有结束模态的线程，而是在窗口彻底退出后才会结束，如果循环打开应该是结束不了的
		MsgEntry rs = LoadingDialog.getInstance().popUpLoadingDialog(new LoginTask());
		if(rs.isResult()){//是否成功
			Debug.showMessageDialog(rs.getResultMessage());
			//MainMenu.getInstance().setState(LMenuItem.NOT_IN_GAME);
		}else{
			Debug.errorDialog(rs.getResultMessage(), null);
			this.popUp();
		}
	}
	

	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 用户输入框校验
	 * @author LUCKY 2013-1-13
	 */
	private class TextInputVerifier implements CaretListener{
		
		/**
		 * 输入检查，限定英文大小写和数字下划线，4-10个字符
		 * @author LUCKY 2013-1-13
		 * @param value
		 * @return
		 */
		private boolean checkInput(String value){
			Pattern pt=Pattern.compile("[a-zA-Z0-9_]{4,10}");
			Matcher mc=pt.matcher(value);
			return mc.matches();
		}

		@Override
		public void caretUpdate(CaretEvent e) {
			// TODO Auto-generated method stub
			Object input=e.getSource();
			boolean flag=true;
			if(input==username.getJTextField()){
				flag = checkUsername();
			}else if(input==password.getJTextField()){
				flag = checkPassword();
			}else if(input==password2.getJTextField()){
				flag = checkPassword2();
			}else if(input==name.getJTextField()){
				flag = checkName();
			}
			if(flag&&username.getValue().length()>0
					&&password.getValue().length()>0
					&&password2.getValue().length()>0
					&&name.getValue().length()>0){
				if(checkUsername()&&checkPassword()&&checkPassword2()&&checkName()){//再次确认
					buttons.getOkButton().setEnabled(true);
				}
			}else{
				buttons.getOkButton().setEnabled(false);
			}
		}

		private boolean checkUsername() {
			boolean flag;
			String value = username.getValue()==null?"":username.getValue();
			if(value.length()>10||value.length()<4){
				flag=false;
				tips.setText("用户名长度必须在4~10个字符之间");
			}else{
				flag=checkInput(value);
				if(flag){
					tips.setText("");
				}else{
					tips.setText("用户名只能是大小写字母、数字和下划线");
				}
			}
			return flag;
		}
		
		private boolean checkName() {
			boolean flag;
			byte[] value = name.getValue()==null?"".getBytes():name.getValue().getBytes();
			if(value.length>20||value.length<4){
				flag=false;
				tips.setText("昵称长度必须在4~20个字符之间");
			}else{
				flag=true;
				tips.setText("");
			}
			return flag;
		}

		private boolean checkPassword() {
			boolean flag;
			if(!password.getValue().equals(password2.getValue())){
				password2.setValue("");
			}
			String value = password.getValue()==null?"":password.getValue();
			if(value.length()>10||value.length()<4){
				flag=false;
				tips.setText("密码长度必须在4~10个字符之间");
			}else{
				flag=checkInput(value);
				if(flag){
					tips.setText("");
				}else{
					tips.setText("密码只能是大小写字母、数字和下划线");
				}
			}
			return flag;
		}

		private boolean checkPassword2() {
			boolean flag;
			String value = password2.getValue();
			if(value!=null&&value.equals(password.getValue())){
				flag=true;
				tips.setText("");
			}else{
				flag=false;
				tips.setText("两次输入的密码不一致");
			}
			return flag;
		}
		
	}
	
	/**
	 * 注册任务
	 * @author LUCKY 2013-1-11
	 */
	class LoginTask implements LoadingTask{
		@Override
		public String getLoadingMsg() {
			// TODO Auto-generated method stub
			return "登陆中...";
		}

		@Override
		public MsgEntry call() throws Exception {
			// TODO Auto-generated method stub
			MsgEntry msg=null;
			try{
				msg=RemoteServiceProxy.getInstance().register(username.getValue(), password.getValue(),name.getValue());
			}catch(WebServiceException e){
				if(e.getCause() instanceof ConnectException){
					msg=new MsgEntry(false, "无法连接到服务器!\n"+e.getCause().getMessage());
				}
			}
			return msg;
		}		
	}
}
