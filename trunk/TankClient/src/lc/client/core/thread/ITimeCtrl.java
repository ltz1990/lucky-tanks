package lc.client.core.thread;

import java.lang.Thread.State;

/**
 * ��ʱ����ӿ�-�۲���
 * @author LUCKY
 *
 */
public interface ITimeCtrl extends Runnable{
	
	/**
	 * �ܱ����̼߳�����
	 */
	public void runThisLine();
	
	/**
	 * ��ʱ���̼߳�����
	 */
	public void runTimeLine();
	
	/**
	 * �õ�ʱ����
	 */
	public long getTimeLine();
	
	/**
	 * ����߳�״̬
	 * @return
	 */
	public State getState();
}
