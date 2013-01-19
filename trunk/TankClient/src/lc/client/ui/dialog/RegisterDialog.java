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
 * ��½����
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
		super(MainFrame.getInstance(),"��½", 300, 240);
		TextInputVerifier textInputVerifier = new TextInputVerifier();
		username=(LTextField)this.add(new LTextField("�û��� :",-10, 30));
		username.getJTextField().addCaretListener(textInputVerifier);
		name=(LTextField)this.add(new LTextField("�ǳ� :",-10,60));
		name.getJTextField().addCaretListener(textInputVerifier);
		password=(LPassWordField)this.add(new LPassWordField("���� :",-10,90));
		password.getJTextField().addCaretListener(textInputVerifier);
		password2=(LPassWordField)this.add(new LPassWordField("ȷ������ :",-10,120));
		password2.getJTextField().addCaretListener(textInputVerifier);
		tips=(JLabel)this.add(new JLabel());
		tips.setBounds(10, 5, 280, 30);
		tips.setHorizontalAlignment(JLabel.CENTER);
		buttons = new LOkCancelButton();
		this.add(buttons);
		buttons.getOkButton().setEnabled(false);
		FontSetting.setChildrenFont(this,new Font("΢���ź�", Font.PLAIN, 12));
		tips.setForeground(Color.red);
	}
	
	public static synchronized RegisterDialog getInstance(){
		if(loginDialog==null){
			loginDialog=new RegisterDialog();
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
	 * �û������У��
	 * @author LUCKY 2013-1-13
	 */
	private class TextInputVerifier implements CaretListener{
		
		/**
		 * �����飬�޶�Ӣ�Ĵ�Сд�������»��ߣ�4-10���ַ�
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
				if(checkUsername()&&checkPassword()&&checkPassword2()&&checkName()){//�ٴ�ȷ��
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
				tips.setText("�û������ȱ�����4~10���ַ�֮��");
			}else{
				flag=checkInput(value);
				if(flag){
					tips.setText("");
				}else{
					tips.setText("�û���ֻ���Ǵ�Сд��ĸ�����ֺ��»���");
				}
			}
			return flag;
		}
		
		private boolean checkName() {
			boolean flag;
			byte[] value = name.getValue()==null?"".getBytes():name.getValue().getBytes();
			if(value.length>20||value.length<4){
				flag=false;
				tips.setText("�ǳƳ��ȱ�����4~20���ַ�֮��");
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
				tips.setText("���볤�ȱ�����4~10���ַ�֮��");
			}else{
				flag=checkInput(value);
				if(flag){
					tips.setText("");
				}else{
					tips.setText("����ֻ���Ǵ�Сд��ĸ�����ֺ��»���");
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
				tips.setText("������������벻һ��");
			}
			return flag;
		}
		
	}
	
	/**
	 * ע������
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
				msg=RemoteServiceProxy.getInstance().register(username.getValue(), password.getValue(),name.getValue());
			}catch(WebServiceException e){
				if(e.getCause() instanceof ConnectException){
					msg=new MsgEntry(false, "�޷����ӵ�������!\n"+e.getCause().getMessage());
				}
			}
			return msg;
		}		
	}
}
