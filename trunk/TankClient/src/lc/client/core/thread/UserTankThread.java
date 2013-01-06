package lc.client.core.thread;

import java.lang.Thread.State;

import lc.client.core.components.TankComp;
import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.util.Debug;

/**
 * 游戏主线程
 * 
 * 考虑使用双线程来缓解卡顿问题
 * @author LUCKY
 *
 */
public class UserTankThread implements ITimeCtrl{
	private static UserTankThread gameThread;
	private Thread thread;
	
	private TankComp tank;
	
	public long thisLine=0;//本线程计步器
	public long timeLine=0;//时间线计步器
	
	private UserTankThread(){
		this.tank=TankFactory.getInstance().getUserTank();
		if(this.tank==null){
			throw new NullPointerException();
		}
	}
	
	/**
	 * 初始化主线程
	 * @return
	 */
	public static UserTankThread getInstance(){
		if(gameThread==null){
			gameThread=new UserTankThread();
		}
		return gameThread;
	}
	
	/**
	 * 启动主线程
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
		TimerThread.getInstance().removeThread(this);//从时间控制线程中移除
		Debug.debug("用户坦克线程结束");
	}

	/**
	 * 当线程速度高于时间轴速度的时候，等待
	 */
	private void pause() {
		// TODO Auto-generated method stub
		if(this.thisLine>=this.timeLine&&GameController.gameSwitch){//只有线程跑快了才等待
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
	 * 获得线程当前状态
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
