package lc.client.ui.dialog;

import java.awt.Font;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

import javax.swing.JFrame;

import lc.client.environment.ClientConstant;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.components.LTextField;
import lc.client.ui.frame.MainFrame;
import lc.client.util.FontSetting;

/**
 * ���ô���
 * @author LUCKY
 *
 */
public class ServerConfigDialog extends LDialog{
	private static final long serialVersionUID = 1L;
	private static ServerConfigDialog configDialog;
	private static final int DIALOG_WIDTH=300;
	private static final int DIALOG_HEIGHT=170;
	private static final String TITLE="����������";
	
	private LTextField ip;
	private LTextField port;
	
	private ServerConfigDialog(JFrame frame){
		super(frame,TITLE,DIALOG_WIDTH,DIALOG_HEIGHT);//ģ̬����	
		ip=new LTextField("��������ַ",10,30);
		port=new LTextField("�������˿�",10,60);
		this.add(ip);
		this.add(port);
		this.add(new LOkCancelButton());//���ȷ��ȡ����ť��
		FontSetting.setChildrenFont(this,new Font("΢���ź�", Font.PLAIN, 12));
	}
	
	/**
	 * ������ô���
	 */
	public static synchronized ServerConfigDialog getInstance(){
		MainFrame frame=MainFrame.getInstance();
		if(frame==null){
			throw new NullPointerException();
		}
		if(configDialog==null){
			configDialog=new ServerConfigDialog(frame);//ģ̬����
		}
		return configDialog;
	}

	/**
	 * ȷ���¼�
	 */
	public void onOkBtn() {
		Properties prop=ClientConstant.getProperties();
		prop.put("SERVER_ADDRESS", ip.getValue());
		prop.put("SERVER_PORT", port.getValue());
		ClientConstant.saveProperties(prop);
		ClientConstant.loadServerConfig();
	}

	/**
	 * �����¼�
	 */
	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub
		Properties prp = ClientConstant.getProperties();
		ip.setValue(prp.get("SERVER_ADDRESS"));
		port.setValue(prp.getProperty("SERVER_PORT"));
	}
}
