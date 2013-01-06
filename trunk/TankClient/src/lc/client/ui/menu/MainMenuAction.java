package lc.client.ui.menu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.core.net.NetConnection;
import lc.client.core.task.LoadingTask;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.dialog.GameCreateDialog;
import lc.client.ui.dialog.GameJoinDialog;
import lc.client.ui.dialog.KeySettingDialog;
import lc.client.ui.dialog.LoadingDialog;
import lc.client.ui.dialog.ServerConfigDialog;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.panel.HomePanel;
import lc.client.util.ClientConstant;
import lc.client.util.Debug;

public class MainMenuAction {

	/**
	 * ��½�¼�
	 */
	@SuppressWarnings("deprecation")
	protected static void onLogin(){
		final String name=JOptionPane.showInternalInputDialog(MainFrame.getInstance().getContentPane(),"�������");
		LoadingTask task=new LoadingTask() {			
			@Override
			public void run() throws Exception {
				// TODO Auto-generated method stub
				TankFactory factory = TankFactory.getInstance();
				factory.createTank(name, ClientConstant.USER);
				if (name != null) {
					System.out.println(name);
					NetConnection.openConnect();// ��ʼ������ ���ӵ����������ǲ�ͨ��
					NetConnection.startNetThread();// �������ܺͷ������ݵ�����					
				}
			}
			
			@Override
			public String getSuccessResultMsg() {
				// TODO Auto-generated method stub
				return "���ӳɹ�";
			}
			
			@Override
			public String getLoadingMsg() {
				// TODO Auto-generated method stub
				return "������...";
			}
			
			@Override
			public String getFailedResultMsg() {
				// TODO Auto-generated method stub
				return "�޷����ӵ���������";
			}
		};
		if(LoadingDialog.getInstance().popUpMessageDialog(task)){
			MainMenu.getInstance().setState(LMenuItem.NOT_IN_GAME);
		}
	}
	
	/**
	 * ע��
	 */
	protected static void onRegister(){
		
	}
	
	public static JDesktopPane getDesktopPaneForComponent(Component parentComponent) {
        if(parentComponent == null)
            return null;
        if(parentComponent instanceof JDesktopPane)
            return (JDesktopPane)parentComponent;
        return getDesktopPaneForComponent(parentComponent.getParent());
    }
	
	/**
	 * �˳�
	 */
	protected static void onExit(){
		NetConnection.disConnect();
		System.exit(0);
	}
	
	/**
	 * ��������
	 */
	protected static void onCreate(){
		GameCreateDialog.getInstance().popUp();
	}
	
	/**
	 * ������Ϸ
	 */
	protected static final void onJoin(){
		GameJoinDialog.getInstance().popUp();
	}
	
	/**
	 * �뿪��Ϸ
	 */
	protected static void onLeave() {
		// TODO Auto-generated method stub
		int state=JOptionPane.showInternalConfirmDialog(MainFrame.getInstance().getContentPane(), "ȷ���˳���","��ʾ",JOptionPane.YES_NO_OPTION);
		if(state==0){
		//JOptionPane.showConfirmDialog(MainFrame.getInstance().getContentPane(), "123");
			GameController.exitGame();
		}
	}
	
	/**
	 * ����������
	 */
	protected static void onServerConfig(){
		ServerConfigDialog.getInstance().popUp();
	}
	
	/**
	 * ��������
	 */
	protected static void onKeySetting(){
		KeySettingDialog.getInstance().popUp();
	}
	
	/**
	 * ˵��
	 */
	protected static void onDescription(){
		
	}
	
	/**
	 * ����
	 */
	protected static void onAbout(){
		
	}

	/**
	 * ��������ҳ
	 */
	protected static void onWebSite() {
		// TODO Auto-generated method stub
		Desktop desk=Desktop.getDesktop();
		try {
			desk.browse(new URI("http://www.luckyzz.com"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
