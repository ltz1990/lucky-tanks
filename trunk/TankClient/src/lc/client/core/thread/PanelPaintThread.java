package lc.client.core.thread;

import lc.client.core.controller.GameController;
import lc.client.environment.ClientConstant;
import lc.client.ui.panel.GamePanel;
import lc.client.util.Debug;

/**
 * 绘图线程
 * @author LUCKY
 *
 */
public class PanelPaintThread implements Runnable{
	private GamePanel gamePanel;
	
	public PanelPaintThread(GamePanel gamePanel){
		this.gamePanel=gamePanel;
	}
	
	/**
	 * 启动线程
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
		Debug.debug("绘图线程结束");
	}
	
}
