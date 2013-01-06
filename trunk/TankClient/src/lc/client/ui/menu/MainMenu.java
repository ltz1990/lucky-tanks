package lc.client.ui.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import lc.client.ui.components.LMenuItem;

/**
 * �˵���
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
		//��ʼ
		JMenu start=new JMenu("��ʼ");
		login=new LMenuItem("��½",LMenuItem.NO_CONN);
		register=new LMenuItem("ע��",LMenuItem.NO_CONN|LMenuItem.NOT_IN_GAME);
		exit=new LMenuItem("�˳�",LMenuItem.NO_CONN|LMenuItem.NOT_IN_GAME);
		//��Ϸ
		JMenu game=new JMenu("��Ϸ");
		create=new LMenuItem("�½���Ϸ",LMenuItem.NOT_IN_GAME);
		join=new LMenuItem("������Ϸ",LMenuItem.NOT_IN_GAME);
		leave=new LMenuItem("�뿪��Ϸ",LMenuItem.IN_GAME);
		//����
		JMenu config=new JMenu("����");
		serverConfig=new LMenuItem("����������");
		keySetting=new LMenuItem("��������");
		//����
		JMenu help=new JMenu("����");
		website=new LMenuItem("������ҳ");
		description=new LMenuItem("��Ϸ����");
		about=new LMenuItem("����");
		//��Ӷ���
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
		//��Ӽ�����
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
	 * ���ò˵���״̬
	 * @param state
	 */
	public void setState(int state){
		for(Component comp:this.getComponents()){
			if(comp instanceof JMenu){
				for(Component item:((JMenu)comp).getMenuComponents()){
					if(item instanceof LMenuItem){
						LMenuItem lMenuItem = (LMenuItem)item;
						lMenuItem.setEnabled(lMenuItem.isInThisState(state));//���ò˵���ť�Ƿ�����
					}
				}
			}
		}
	}
	
	/**
	 * �˵�����������
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
