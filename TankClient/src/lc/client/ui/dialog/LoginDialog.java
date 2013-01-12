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
 * ��½����
 * @author LUCKY 2013-1-12
 */
public class LoginDialog extends LDialog{
	private static final long serialVersionUID = 1L;
	private static LoginDialog loginDialog;
	private static LTextField username;
	private static LPassWordField password;
	
	private LoginDialog() {
		super(MainFrame.getInstance(),"��½", 300, 180);
		username=(LTextField)this.add(new LTextField("�û��� :",-10, 30));
		password=(LPassWordField)this.add(new LPassWordField("���� :",-10,60));
		this.add(new LOkCancelButton());
		FontSetting.setChildrenFont(this,new Font("΢���ź�", Font.PLAIN, 12));
	}
	
	public static synchronized LoginDialog getInstance(){
		if(loginDialog==null){
			loginDialog=new LoginDialog();
		}
		return loginDialog;
	}

	/**
	 * ��ť��������
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
		this.closeDialog();//�������ʵ���ϲ�û�н���ģ̬���̣߳������ڴ��ڳ����˳���Ż���������ѭ����Ӧ���ǽ������˵�
		MsgEntry rs = LoadingDialog.getInstance().popUpLoadingDialog(new LoginTask());
		if(rs.isResult()){//�Ƿ�ɹ�
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
	 * ��½����
	 * @author LUCKY 2013-1-11
	 */
	class LoginTask implements LoadingTask{
		@Override
		public String getLoadingMsg() {
			// TODO Auto-generated method stub
			return "��½��...";
		}

		@Override
		public MsgEntry call() throws Exception {
			// TODO Auto-generated method stub
			MsgEntry msg=null;
			try{
				msg=RemoteServiceProxy.getInstance().login(username.getValue(), password.getValue());
			}catch(WebServiceException e){
				if(e.getCause() instanceof ConnectException){
					msg=new MsgEntry(false, "�޷����ӵ�������!\n"+e.getCause().getMessage());
				}
			}
			return msg;
		}

		
	}

}
