package lc.client.ui.frame;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import lc.client.environment.ClientConstant;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.menu.MainMenu;
import lc.client.ui.panel.GamePanel;

public class MainFrame extends JFrame {	
	private static final long serialVersionUID = 1L;
	private static MainFrame mainFrame;
	private GamePanel gamePanel;
	private MainMenu mainMenu;
	
	/**
	 * 获得主FRAME的单例对象
	 * @return
	 */
	public static MainFrame getInstance(){
		if(mainFrame==null){
			mainFrame=new MainFrame();
		}
		return mainFrame;
	}
	
	private MainFrame(){
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出系统
		initUI();
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icon=tk.createImage(this.getClass().getResource("/images/icon.jpg"));
		this.setIconImage(icon);//标题栏图标
		this.setTitle(ClientConstant.TITLE);
		this.setLocation((tk.getScreenSize().width-ClientConstant.FRAME_WIDTH)/2, (tk.getScreenSize().height-ClientConstant.FRAME_HEIGHT)/2);
		this.setSize(ClientConstant.FRAME_WIDTH,ClientConstant.FRAME_HEIGHT);
		this.setResizable(false);// 大小不可更改
		mainMenu=MainMenu.getInstance();
		mainMenu.setState(LMenuItem.NO_CONN);
		this.setJMenuBar(mainMenu);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
	}
	
	public GamePanel getGamePanel(){
		return this.gamePanel;
	}
	
}
