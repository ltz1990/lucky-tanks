package lc.client.ui.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import lc.client.ui.components.LMenuItem;

/**
 * 菜单栏
 * @author LUCKY
 *
 */
public class MainMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private static MainMenu mainMenu;
	private LMenuItem login;
	private LMenuItem register;
	private LMenuItem exit;
	private LMenuItem create;
	private LMenuItem join;
	private LMenuItem leave;
	private LMenuItem serverConfig;
	private LMenuItem keySetting;
	private LMenuItem website;
	private LMenuItem description;
	private LMenuItem about;
	
	public static synchronized MainMenu getInstance(){
		if(mainMenu==null){
			mainMenu=new MainMenu();
		}
		return mainMenu;
	}
	private MainMenu(){
		//开始
		JMenu start=new JMenu("开始");
		login=new LMenuItem("登陆",LMenuItem.NO_CONN);
		register=new LMenuItem("注册",LMenuItem.NO_CONN|LMenuItem.NOT_IN_GAME);
		exit=new LMenuItem("退出",LMenuItem.NO_CONN|LMenuItem.NOT_IN_GAME);
		//游戏
		JMenu game=new JMenu("游戏");
		create=new LMenuItem("新建游戏",LMenuItem.NOT_IN_GAME);
		join=new LMenuItem("加入游戏",LMenuItem.NOT_IN_GAME);
		leave=new LMenuItem("离开游戏",LMenuItem.IN_GAME);
		//设置
		JMenu config=new JMenu("设置");
		serverConfig=new LMenuItem("服务器设置");
		keySetting=new LMenuItem("按键设置");
		//帮助
		JMenu help=new JMenu("帮助");
		website=new LMenuItem("作者首页");
		description=new LMenuItem("游戏介绍");
		about=new LMenuItem("关于");
		//添加对象
		start.add(login);
		start.add(register);
		start.addSeparator();
		start.add(exit);
		game.add(create);
		game.add(join);
		game.add(leave);
		config.add(serverConfig);
		config.add(keySetting);
		help.add(website);
		help.add(description);
		help.addSeparator();
		help.add(about);
		this.add(start);
		this.add(game);
		this.add(config);
		this.add(help);
		this.setVisible(true);
		//添加监听器
		ActionListener listener=new MainMenuActionListener();
		login.addActionListener(listener);
		register.addActionListener(listener);
		exit.addActionListener(listener);
		create.addActionListener(listener);
		join.addActionListener(listener);
		leave.addActionListener(listener);
		serverConfig.addActionListener(listener);
		keySetting.addActionListener(listener);
		website.addActionListener(listener);
		description.addActionListener(listener);
		about.addActionListener(listener);
	}
	
	/**
	 * 设置菜单栏状态
	 * @param state
	 */
	public void setState(int state){
		for(Component comp:this.getComponents()){
			if(comp instanceof JMenu){
				for(Component item:((JMenu)comp).getMenuComponents()){
					if(item instanceof LMenuItem){
						LMenuItem lMenuItem = (LMenuItem)item;
						lMenuItem.setEnabled(lMenuItem.isInThisState(state));//设置菜单按钮是否启用
					}
				}
			}
		}
	}
	
	/**
	 * 菜单按键监听器
	 * @author LUCKY
	 *
	 */
	class MainMenuActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object object=e.getSource();
			if(login==object){
				MainMenuAction.onLogin();
			}else if(register==object){
				MainMenuAction.onRegister();
			}else if(exit==object){
				MainMenuAction.onExit();
			}else if(create==object){
				MainMenuAction.onCreate();
			}else if(join==object){
				MainMenuAction.onJoin();
			}else if(leave==object){
				MainMenuAction.onLeave();
			}else if(serverConfig==object){
				MainMenuAction.onServerConfig();
			}else if(keySetting==object){
				MainMenuAction.onKeySetting();
			}else if(website==object){
				MainMenuAction.onWebSite();
			}else if(description==object){
				MainMenuAction.onDescription();
			}else if(about==object){
				MainMenuAction.onAbout();
			}
		}
	}
}
