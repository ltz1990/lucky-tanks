package lc.client.core.thread;

import java.lang.Thread.State;

import lc.client.core.components.TankComp;
import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.util.Debug;

/**
 * ��Ϸ���߳�
 * 
 * ����ʹ��˫�߳������⿨������
 * @author LUCKY
 *
 */
public class UserTankThread implements ITimeCtrl{
	private static UserTankThread gameThread;
	private Thread thread;
	
	private TankComp tank;
	
	public long thisLine=0;//���̼߳Ʋ���
	public long timeLine=0;//ʱ���߼Ʋ���
	
	private UserTankThread(){
		this.tank=TankFactory.getInstance().getUserTank();
		if(this.tank==null){
			throw new NullPointerException();
		}
	}
	
	/**
	 * ��ʼ�����߳�
	 * @return
	 */
	public static UserTankThread getInstance(){
		if(gameThread==null){
			gameThread=new UserTankThread();
		}
		return gameThread;
	}
	
	/**
	 * �������߳�
	 */
	public void start(){
		thread=new Thread(this);
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(GameController.gameSwitch){
			tank.run();
			this.pause();
		}
		TimerThread.getInstance().removeThread(this);//��ʱ������߳����Ƴ�
		Debug.debug("�û�̹���߳̽���");
	}

	/**
	 * ���߳��ٶȸ���ʱ�����ٶȵ�ʱ�򣬵ȴ�
	 */
	private void pause() {
		// TODO Auto-generated method stub
		if(this.thisLine>=this.timeLine&&GameController.gameSwitch){//ֻ���߳��ܿ��˲ŵȴ�
			synchronized(this){
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		this.thisLine++;
		
	}
	
	/**
	 * ����̵߳�ǰ״̬
	 * @return
	 */
	public State getState(){
		return thread.getState();
	}

	@Override
	public void runThisLine() {
		// TODO Auto-generated method stub
		this.thisLine++;
	}

	@Override
	public void runTimeLine() {
		// TODO Auto-generated method stub
		this.timeLine++;
	}

	@Override
	public long getTimeLine() {
		// TODO Auto-generated method stub
		return this.timeLine;
	}

}
