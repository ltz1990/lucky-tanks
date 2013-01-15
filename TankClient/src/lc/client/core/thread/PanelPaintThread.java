package lc.client.core.thread;

import lc.client.core.controller.GameController;
import lc.client.environment.ClientConstant;
import lc.client.ui.panel.GamePanel;
import lc.client.util.Debug;

/**
 * ��ͼ�߳�
 * @author LUCKY
 *
 */
public class PanelPaintThread implements Runnable{
	private GamePanel gamePanel;
	
	public PanelPaintThread(GamePanel gamePanel){
		this.gamePanel=gamePanel;
	}
	
	/**
	 * �����߳�
	 */
	public void start(){
		Thread thread=new Thread(this);
		//thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	@Override
	public void run() {
		while(GameController.gameSwitch){
			gamePanel.repaint();
			try {
				Thread.sleep(ClientConstant.MAIN_SPEED>>1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Debug.debug("��ͼ�߳̽���");
	}
	
}
