package lc.client.core.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import lc.client.core.components.TankComp;
import lc.client.core.factory.TankFactory;
import lc.client.core.net.NetConnection;
import lc.client.core.task.TestAutoTankThread;
import lc.client.core.thread.UserTankThread;
import lc.client.ui.frame.MainFrame;
import lc.client.util.ClientConstant;

/**
 * 游戏控制器
 * 
 * @author LUCKY
 * 
 */
public class PlayerController implements KeyListener, MouseListener,WindowListener {
	private static PlayerController gameCtrl;
	private TankComp tank;
	private int[] pressStatus = new int[3];

	private PlayerController() {
		this.tank=TankFactory.getInstance().getUserTank();
		if(this.tank==null){
			throw new NullPointerException();
		}
	}
	
	/**
	 * 获得控制器
	 * @return
	 */
	public static PlayerController getInstance(){
		if(gameCtrl==null){
			gameCtrl=new PlayerController();
		}
		return gameCtrl;
	}
	
	/**
	 * 注册控制对象
	 * @param frame
	 */
	public void registerListener(MainFrame frame){
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addWindowListener(this);
	}
	
	/**
	 * 移除控制器
	 * @param frame
	 */
	public void removeListener(MainFrame frame){
		frame.removeKeyListener(this);
		frame.removeMouseListener(this);
		frame.removeWindowListener(this);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(ClientConstant.KEY_SHOT==keyCode){//如果按下的是攻击键
			tank.shot();//射！！
		}else if(keyCode==ClientConstant.KEY_DOWN||
				keyCode==ClientConstant.KEY_LEFT||
				keyCode==ClientConstant.KEY_RIGHT||
				keyCode==ClientConstant.KEY_UP){//如果是方向键
			if (pressStatus[0] == 0 && pressStatus[1] == 0) {//一个键都没按的
				pressStatus[0] = keyCode;
				tank.setStatu(pressStatus[0]);
			} else if (pressStatus[0] != 0 && pressStatus[1] == 0) {//已经按下一个键的时候
				if (pressStatus[0] != keyCode) {//新按键按下，放到第二个位置上
					pressStatus[1] = keyCode;
					tank.setStatu(pressStatus[1]);
				}
			} 
		}
		
		//自动行走按键 用来测试
		if(KeyEvent.VK_1==arg0.getKeyCode()){
			TestAutoTankThread t=new TestAutoTankThread();
			t.start();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode=arg0.getKeyCode();
		if(pressStatus[0]!=0&&pressStatus[1]==0){//如果只按下去了一个键
			if (keyCode == pressStatus[0]) {
				pressStatus[0] = 0;
				tank.setStatu(ClientConstant.KEY_STOP);
			}
		}else if(pressStatus[0]!=0&&pressStatus[1]!=0){//如果两个键都按下去了
			if(keyCode==pressStatus[0]){//松掉的是第一个，则把第二个键放到第一个位置，第二个归零
				tank.setStatu(pressStatus[1]);
				pressStatus[0]=pressStatus[1];
				pressStatus[1]=0;
			}else if(keyCode==pressStatus[1]){//松掉的是第二个
				tank.setStatu(pressStatus[0]);
				pressStatus[1]=0;
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		try {
			NetConnection.disConnect();// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		try {
			NetConnection.disConnect();// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(System.currentTimeMillis()+"|"+UserTankThread.getInstance().thisLine+"|"+UserTankThread.getInstance().timeLine);
			System.exit(0);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
