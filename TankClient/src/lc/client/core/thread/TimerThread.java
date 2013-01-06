package lc.client.core.thread;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import lc.client.util.Debug;

/**
 * 线程速度控制器
 * @author LUCKY
 *
 */
public class TimerThread extends TimerTask {
	private static TimerThread timeThread;
	private List<ITimeCtrl> threadList;//被监控线程

	private TimerThread(){
		threadList=new ArrayList<ITimeCtrl>();
	}
	
	/**
	 * 得到速度控制线程 对象
	 * @return
	 */
	public static synchronized TimerThread getInstance(){
		if(timeThread==null){
			timeThread=new TimerThread();
		}
		return timeThread;
	}
	/**
	 * 可以增加逻辑，当和系统时间误差超过一个帧的时候
	 * 修正精度
	 */
	@Override
	public void run() {
		try {
			for (ITimeCtrl thread : threadList) {
				thread.runTimeLine();// 跑被监控线程的时间线计步器
				if (Thread.State.WAITING.equals(thread.getState())) {
					synchronized (thread) {
						thread.notify();
					}
				}
			}
		} finally {
			if (threadList.size() == 0) {// 没有线程了，则终止计时器
				this.cancel();
				this.threadList.clear();
				Debug.debug("计时器停止");
			}
		}
	}
	
	/**
	 * 增加线程
	 * @param gameThread
	 */
	public void addThread(ITimeCtrl ctrlThread){
		this.threadList.add(ctrlThread);
	}
	
	/**
	 * 移除线程
	 * @param ctrlThread
	 */
	public void removeThread(ITimeCtrl ctrlThread){
		this.threadList.remove(ctrlThread);
	}
	
	/**
	 * 重置定时任务状态
	 * 因为state是个默认属性 所以不能直接改
	 * 用反射的方式修改
	 */
	public void resetState(){		
		try {
			Field state=this.getClass().getSuperclass().getDeclaredField("state");
			state.setAccessible(true);//取消安全监察
			state.set(this,0);//设置当前定时任务为 未调度
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
