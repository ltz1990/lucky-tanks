package lc.client.core.controller;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Timer;

import lc.client.core.factory.TankFactory;
import lc.client.core.net.DataAcceptThread;
import lc.client.core.net.DataSendThread;
import lc.client.core.net.MsgCenter;
import lc.client.core.thread.OtherTankThread;
import lc.client.core.thread.PanelPaintThread;
import lc.client.core.thread.TimerThread;
import lc.client.core.thread.UserTankThread;
import lc.client.environment.ClientConstant;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.menu.MainMenu;
import lc.client.ui.panel.GamePanel;
import lc.client.ui.panel.HomePanel;

/**
 * 游戏状态控制器，加入退出等等
 * 
 * @author LUCKY
 * 
 */
public class GameController {
	public static boolean gameSwitch = true;//线程开关
	private static Timer timer;

	/**
	 * 开始游戏</br>
	 * 需修改，移除连接线程，连接在登陆之后始终保持，由服务器分配通道所属位置
	 */
	public static void startGame() {
		GameController.gameSwitch=true;
		
		// 初始化工厂,目前本地坦克的创建是在起名字的时候，需修改
		initFactory();
		// 初始化游戏控制器
		initPlayerController();
		// 启动坦克控制线程
		startGameThread();
		// 启动速度控制线程
		startTimeThread();
		// 初始化画面容器
		initGamePanel();
		// 启动绘图线程
		startScreenPaintThread();
		MainMenu.getInstance().setState(LMenuItem.IN_GAME);
	}
	
	/**
	 * 离开游戏
	 */
	public static void exitGame(){
		/**
		 * gameSwitch会关闭:
		 * 1.启动和发送数据的连接 DataSendThread
		 * 2.用户和其它坦克控制线程 UserTankThread,OtherTankThread 
		 * 3.绘图线程 PanelPaintThread
		 */
		GameController.gameSwitch=false;
		TankFactory.getInstance().clear();//清空工厂
		MainFrame mainFrame = MainFrame.getInstance();
		PlayerController.getInstance().removeListener(mainFrame);//移除控制器
		/** 替换界面 */
		mainFrame.remove(GamePanel.getInstance());// 移除首页容器
		mainFrame.add(HomePanel.getInstance());// 放入游戏容器到窗口
		mainFrame.repaint();
		mainFrame.setVisible(true);// 重要，起到刷新作用
		mainFrame.requestFocus();// 获得窗口界面焦点
		MainMenu.getInstance().setState(LMenuItem.NOT_IN_GAME);
	}
	
	

	/**
	 * 初始化绘图线程
	 */
	private static void startScreenPaintThread() {
		PanelPaintThread paintThread = new PanelPaintThread(
				GamePanel.getInstance());
		paintThread.start();
	}

	/**
	 * 初始化游戏画面容器
	 */
	private static void initGamePanel() {
		GamePanel gamePanel = GamePanel.getInstance();// 初始化游戏容器，必须带用户坦克
		MainFrame.getInstance().remove(HomePanel.getInstance());// 移除首页容器
		MainFrame.getInstance().add(gamePanel);// 放入游戏容器到窗口
		MainFrame.getInstance().setVisible(true);// 重要，起到刷新作用
		MainFrame.getInstance().requestFocus();// 获得窗口界面
	}

	/**
	 * 启动游戏主线程
	 */
	private static void startGameThread() {
		UserTankThread gameThread = UserTankThread.getInstance();// 初始化游戏线程
		gameThread.start();
		TimerThread.getInstance().addThread(gameThread);
		OtherTankThread otherThread = OtherTankThread.getInstance();// 其它玩家坦克
		otherThread.start();
		TimerThread.getInstance().addThread(otherThread);
	}

	/**
	 * 启动速度控制线程
	 */
	private static void startTimeThread() {
		TimerThread timeCtrl = TimerThread.getInstance();// 主时间轴
		timeCtrl.resetState();
		if(timer!=null){//退出旧计时器
			timer.cancel();
		}
		timer=new Timer();
		timer.scheduleAtFixedRate(timeCtrl, new Date(),
				ClientConstant.MAIN_SPEED);// 精确定时,带修正
	}

	/**
	 * 初始化游戏控制器
	 */
	private static void initPlayerController() {
		PlayerController controller = PlayerController.getInstance();// 初始化游戏控制器
		controller.registerListener(MainFrame.getInstance());
	}

	/**
	 * 初始化坦克工厂
	 * 应加入逻辑：在startGame的时候就建立好所有物体
	 */
	private static void initFactory() {

	}

}
