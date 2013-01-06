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
 * ��Ϸ������
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
	 * ��ÿ�����
	 * @return
	 */
	public static PlayerController getInstance(){
		if(gameCtrl==null){
			gameCtrl=new PlayerController();
		}
		return gameCtrl;
	}
	
	/**
	 * ע����ƶ���
	 * @param frame
	 */
	public void registerListener(MainFrame frame){
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addWindowListener(this);
	}
	
	/**
	 * �Ƴ�������
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
		if(ClientConstant.KEY_SHOT==keyCode){//������µ��ǹ�����
			tank.shot();//�䣡��
		}else if(keyCode==ClientConstant.KEY_DOWN||
				keyCode==ClientConstant.KEY_LEFT||
				keyCode==ClientConstant.KEY_RIGHT||
				keyCode==ClientConstant.KEY_UP){//����Ƿ����
			if (pressStatus[0] == 0 && pressStatus[1] == 0) {//һ������û����
				pressStatus[0] = keyCode;
				tank.setStatu(pressStatus[0]);
			} else if (pressStatus[0] != 0 && pressStatus[1] == 0) {//�Ѿ�����һ������ʱ��
				if (pressStatus[0] != keyCode) {//�°������£��ŵ��ڶ���λ����
					pressStatus[1] = keyCode;
					tank.setStatu(pressStatus[1]);
				}
			} 
		}
		
		//�Զ����߰��� ��������
		if(KeyEvent.VK_1==arg0.getKeyCode()){
			TestAutoTankThread t=new TestAutoTankThread();
			t.start();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode=arg0.getKeyCode();
		if(pressStatus[0]!=0&&pressStatus[1]==0){//���ֻ����ȥ��һ����
			if (keyCode == pressStatus[0]) {
				pressStatus[0] = 0;
				tank.setStatu(ClientConstant.KEY_STOP);
			}
		}else if(pressStatus[0]!=0&&pressStatus[1]!=0){//���������������ȥ��
			if(keyCode==pressStatus[0]){//�ɵ����ǵ�һ������ѵڶ������ŵ���һ��λ�ã��ڶ�������
				tank.setStatu(pressStatus[1]);
				pressStatus[0]=pressStatus[1];
				pressStatus[1]=0;
			}else if(keyCode==pressStatus[1]){//�ɵ����ǵڶ���
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
			NetConnection.disConnect();// �ر�����
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
			NetConnection.disConnect();// �ر�����
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
