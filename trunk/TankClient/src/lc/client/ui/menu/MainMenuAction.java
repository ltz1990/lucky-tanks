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
	 * 登陆事件
	 */
	@SuppressWarnings("deprecation")
	protected static void onLogin(){
		final String name=JOptionPane.showInternalInputDialog(MainFrame.getInstance().getContentPane(),"起个名字");
		LoadingTask task=new LoadingTask() {			
			@Override
			public void run() throws Exception {
				// TODO Auto-generated method stub
				TankFactory factory = TankFactory.getInstance();
				factory.createTank(name, ClientConstant.USER);
				if (name != null) {
					System.out.println(name);
					NetConnection.openConnect();// 初始化连接 连接到服务器但是不通信
					NetConnection.startNetThread();// 启动接受和发送数据的连接					
				}
			}
			
			@Override
			public String getSuccessResultMsg() {
				// TODO Auto-generated method stub
				return "连接成功";
			}
			
			@Override
			public String getLoadingMsg() {
				// TODO Auto-generated method stub
				return "连接中...";
			}
			
			@Override
			public String getFailedResultMsg() {
				// TODO Auto-generated method stub
				return "无法连接到服务器！";
			}
		};
		if(LoadingDialog.getInstance().popUpMessageDialog(task)){
			MainMenu.getInstance().setState(LMenuItem.NOT_IN_GAME);
		}
	}
	
	/**
	 * 注册
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
	 * 退出
	 */
	protected static void onExit(){
		NetConnection.disConnect();
		System.exit(0);
	}
	
	/**
	 * 创建房间
	 */
	protected static void onCreate(){
		GameCreateDialog.getInstance().popUp();
	}
	
	/**
	 * 加入游戏
	 */
	protected static final void onJoin(){
		GameJoinDialog.getInstance().popUp();
	}
	
	/**
	 * 离开游戏
	 */
	protected static void onLeave() {
		// TODO Auto-generated method stub
		int state=JOptionPane.showInternalConfirmDialog(MainFrame.getInstance().getContentPane(), "确认退出？","提示",JOptionPane.YES_NO_OPTION);
		if(state==0){
		//JOptionPane.showConfirmDialog(MainFrame.getInstance().getContentPane(), "123");
			GameController.exitGame();
		}
	}
	
	/**
	 * 服务器设置
	 */
	protected static void onServerConfig(){
		ServerConfigDialog.getInstance().popUp();
	}
	
	/**
	 * 按键设置
	 */
	protected static void onKeySetting(){
		KeySettingDialog.getInstance().popUp();
	}
	
	/**
	 * 说明
	 */
	protected static void onDescription(){
		
	}
	
	/**
	 * 关于
	 */
	protected static void onAbout(){
		
	}

	/**
	 * 打开作者首页
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
