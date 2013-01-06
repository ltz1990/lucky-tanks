package lc.client.core.thread;

import java.lang.Thread.State;
import java.util.Map;

import lc.client.core.components.BulletComp;
import lc.client.core.components.TankComp;
import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.util.ClientConstant;
import lc.client.util.Debug;

/**
 * 其它坦克行走方案：
 * 1.在客户端，根据服务端发来的坦克方向自动想走，转弯的时候修正坐标位置
 * 2.以服务端发来的坐标为目标自动行走
 * 3.以队列方式保存坐标，以一定速度遍历,定期修正
 * @author LUCKY
 *
 */
public class OtherTankThread implements ITimeCtrl{
	private static OtherTankThread otherTankThread;
	private Map<String,TankComp> tankList;
	private Thread thread;
	private int timeLine=0;
	private int thisLine=0;
	
	private OtherTankThread(){
		tankList=TankFactory.getInstance().getTankList();
		if(tankList==null){
			throw new NullPointerException();
		}
	}
	
	/**
	 * 其它用户坦克线程
	 * @return
	 */
	public static synchronized OtherTankThread getInstance(){
		if(otherTankThread==null){
			otherTankThread=new OtherTankThread();
		}
		return otherTankThread;
	}
	
	/**
	 * 启动
	 */
	public void start(){
		thread=new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(GameController.gameSwitch){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				BulletComp[] bullets = tank.getBullets();
				for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
					bullets[i].run();//跑子弹
				}
			}
			pause();
		}
		TimerThread.getInstance().removeThread(this);//从时间控制线程中移除
		Debug.debug("其它坦克线程结束");
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
