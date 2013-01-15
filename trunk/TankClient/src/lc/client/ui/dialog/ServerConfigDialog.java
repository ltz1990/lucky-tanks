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
 * 设置窗口
 * @author LUCKY
 *
 */
public class ServerConfigDialog extends LDialog{
	private static final long serialVersionUID = 1L;
	private static ServerConfigDialog configDialog;
	private static final int DIALOG_WIDTH=300;
	private static final int DIALOG_HEIGHT=170;
	private static final String TITLE="服务器设置";
	
	private LTextField ip;
	private LTextField port;
	
	private ServerConfigDialog(JFrame frame){
		super(frame,TITLE,DIALOG_WIDTH,DIALOG_HEIGHT);//模态窗口	
		ip=new LTextField("服务器地址",10,30);
		port=new LTextField("服务器端口",10,60);
		this.add(ip);
		this.add(port);
		this.add(new LOkCancelButton());//添加确定取消按钮组
		FontSetting.setChildrenFont(this,new Font("微软雅黑", Font.PLAIN, 12));
	}
	
	/**
	 * 获得设置窗口
	 */
	public static synchronized ServerConfigDialog getInstance(){
		MainFrame frame=MainFrame.getInstance();
		if(frame==null){
			throw new NullPointerException();
		}
		if(configDialog==null){
			configDialog=new ServerConfigDialog(frame);//模态窗口
		}
		return configDialog;
	}

	/**
	 * 确定事件
	 */
	public void onOkBtn() {
		Properties prop=ClientConstant.getProperties();
		prop.put("SERVER_ADDRESS", ip.getValue());
		prop.put("SERVER_PORT", port.getValue());
		ClientConstant.saveProperties(prop);
		ClientConstant.loadServerConfig();
	}

	/**
	 * 弹出事件
	 */
	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub
		Properties prp = ClientConstant.getProperties();
		ip.setValue(prp.get("SERVER_ADDRESS"));
		port.setValue(prp.getProperty("SERVER_PORT"));
	}
}
