package lc.client.ui.dialog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.ConnectException;

import javax.swing.JFrame;
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
public class LoginDialog extends LDialog{
	private static final long serialVersionUID = 1L;
	private static LoginDialog loginDialog;
	private static LTextField username;
	private static LPassWordField password;
	
	private LoginDialog() {
		super(MainFrame.getInstance(),"登陆", 300, 180);
		username=(LTextField)this.add(new LTextField("用户名 :",-10, 30));
		password=(LPassWordField)this.add(new LPassWordField("密码 :",-10,60));
		this.add(new LOkCancelButton());
		FontSetting.setChildrenFont(this,new Font("微软雅黑", Font.PLAIN, 12));
	}
	
	public static synchronized LoginDialog getInstance(){
		if(loginDialog==null){
			loginDialog=new LoginDialog();
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
			MainMenu.getInstance().setState(LMenuItem.NOT_IN_GAME);
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
	 * 登陆任务
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
				msg=RemoteServiceProxy.getInstance().login(username.getValue(), password.getValue());
			}catch(WebServiceException e){
				if(e.getCause() instanceof ConnectException){
					msg=new MsgEntry(false, "无法连接到服务器!\n"+e.getCause().getMessage());
				}
			}
			return msg;
		}

		
	}

}
