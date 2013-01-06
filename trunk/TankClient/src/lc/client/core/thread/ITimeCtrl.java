package lc.client.core.thread;

import java.lang.Thread.State;

/**
 * 定时任务接口-观察者
 * @author LUCKY
 *
 */
public interface ITimeCtrl extends Runnable{
	
	/**
	 * 跑本地线程计数器
	 */
	public void runThisLine();
	
	/**
	 * 跑时间线程计数器
	 */
	public void runTimeLine();
	
	/**
	 * 得到时间线
	 */
	public long getTimeLine();
	
	/**
	 * 获得线程状态
	 * @return
	 */
	public State getState();
}
