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
 * ��Ϸ״̬�������������˳��ȵ�
 * 
 * @author LUCKY
 * 
 */
public class GameController {
	public static boolean gameSwitch = true;//�߳̿���
	private static Timer timer;

	/**
	 * ��ʼ��Ϸ</br>
	 * ���޸ģ��Ƴ������̣߳������ڵ�½֮��ʼ�ձ��֣��ɷ���������ͨ������λ��
	 */
	public static void startGame() {
		GameController.gameSwitch=true;
		
		// ��ʼ������,Ŀǰ����̹�˵Ĵ������������ֵ�ʱ�����޸�
		initFactory();
		// ��ʼ����Ϸ������
		initPlayerController();
		// ����̹�˿����߳�
		startGameThread();
		// �����ٶȿ����߳�
		startTimeThread();
		// ��ʼ����������
		initGamePanel();
		// ������ͼ�߳�
		startScreenPaintThread();
		MainMenu.getInstance().setState(LMenuItem.IN_GAME);
	}
	
	/**
	 * �뿪��Ϸ
	 */
	public static void exitGame(){
		/**
		 * gameSwitch��ر�:
		 * 1.�����ͷ������ݵ����� DataSendThread
		 * 2.�û�������̹�˿����߳� UserTankThread,OtherTankThread 
		 * 3.��ͼ�߳� PanelPaintThread
		 */
		GameController.gameSwitch=false;
		TankFactory.getInstance().clear();//��չ���
		MainFrame mainFrame = MainFrame.getInstance();
		PlayerController.getInstance().removeListener(mainFrame);//�Ƴ�������
		/** �滻���� */
		mainFrame.remove(GamePanel.getInstance());// �Ƴ���ҳ����
		mainFrame.add(HomePanel.getInstance());// ������Ϸ����������
		mainFrame.repaint();
		mainFrame.setVisible(true);// ��Ҫ����ˢ������
		mainFrame.requestFocus();// ��ô��ڽ��潹��
		MainMenu.getInstance().setState(LMenuItem.NOT_IN_GAME);
	}
	
	

	/**
	 * ��ʼ����ͼ�߳�
	 */
	private static void startScreenPaintThread() {
		PanelPaintThread paintThread = new PanelPaintThread(
				GamePanel.getInstance());
		paintThread.start();
	}

	/**
	 * ��ʼ����Ϸ��������
	 */
	private static void initGamePanel() {
		GamePanel gamePanel = GamePanel.getInstance();// ��ʼ����Ϸ������������û�̹��
		MainFrame.getInstance().remove(HomePanel.getInstance());// �Ƴ���ҳ����
		MainFrame.getInstance().add(gamePanel);// ������Ϸ����������
		MainFrame.getInstance().setVisible(true);// ��Ҫ����ˢ������
		MainFrame.getInstance().requestFocus();// ��ô��ڽ���
	}

	/**
	 * ������Ϸ���߳�
	 */
	private static void startGameThread() {
		UserTankThread gameThread = UserTankThread.getInstance();// ��ʼ����Ϸ�߳�
		gameThread.start();
		TimerThread.getInstance().addThread(gameThread);
		OtherTankThread otherThread = OtherTankThread.getInstance();// �������̹��
		otherThread.start();
		TimerThread.getInstance().addThread(otherThread);
	}

	/**
	 * �����ٶȿ����߳�
	 */
	private static void startTimeThread() {
		TimerThread timeCtrl = TimerThread.getInstance();// ��ʱ����
		timeCtrl.resetState();
		if(timer!=null){//�˳��ɼ�ʱ��
			timer.cancel();
		}
		timer=new Timer();
		timer.scheduleAtFixedRate(timeCtrl, new Date(),
				ClientConstant.MAIN_SPEED);// ��ȷ��ʱ,������
	}

	/**
	 * ��ʼ����Ϸ������
	 */
	private static void initPlayerController() {
		PlayerController controller = PlayerController.getInstance();// ��ʼ����Ϸ������
		controller.registerListener(MainFrame.getInstance());
	}

	/**
	 * ��ʼ��̹�˹���
	 * Ӧ�����߼�����startGame��ʱ��ͽ�������������
	 */
	private static void initFactory() {

	}

}
