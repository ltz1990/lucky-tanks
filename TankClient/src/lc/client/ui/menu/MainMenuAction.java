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
import lc.client.ui.components.LMenuItem;
import lc.client.ui.dialog.GameCreateDialog;
import lc.client.ui.dialog.GameJoinDialog;
import lc.client.ui.dialog.KeySettingDialog;
import lc.client.ui.dialog.ServerConfigDialog;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.panel.HomePanel;
import lc.client.util.ClientConstant;

public class MainMenuAction {

	/**
	 * ��½�¼�
	 */
	protected static void onLogin(){
		String name=JOptionPane.showInternalInputDialog(MainFrame.getInstance().getContentPane(),"�������");
		TankFactory factory=TankFactory.getInstance();
		factory.createTank(name,ClientConstant.USER);
		if(name!=null){
			NetConnection.openConnect();//��ʼ������ ���ӵ����������ǲ�ͨ��
			MainMenu.getInstance().setState(LMenuItem.NOT_IN_GAME);
		}
	}
	
	/**
	 * ע��
	 */
	protected static void onRegister(){
		JInternalFrame iFrame=new JInternalFrame("123", false, true,
                false, false);
		MainFrame parent = MainFrame.getInstance();
		//parent.remove(HomePanel.getInstance());
		Dimension iFrameSize = new Dimension(100, 100);
        Dimension rootSize = parent.getSize();
        iFrame.setBounds((rootSize.width - iFrameSize.width) / 2,
                (rootSize.height - iFrameSize.height) / 2,
                iFrameSize.width, iFrameSize.height); 
		parent.getLayeredPane().add(iFrame,0);
		
		try {
			iFrame.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iFrame.setVisible(true);
		
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
