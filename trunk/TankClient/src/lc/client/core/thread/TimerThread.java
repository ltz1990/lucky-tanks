package lc.client.core.thread;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import lc.client.util.Debug;

/**
 * �߳��ٶȿ�����
 * @author LUCKY
 *
 */
public class TimerThread extends TimerTask {
	private static TimerThread timeThread;
	private List<ITimeCtrl> threadList;//������߳�

	private TimerThread(){
		threadList=new ArrayList<ITimeCtrl>();
	}
	
	/**
	 * �õ��ٶȿ����߳� ����
	 * @return
	 */
	public static synchronized TimerThread getInstance(){
		if(timeThread==null){
			timeThread=new TimerThread();
		}
		return timeThread;
	}
	/**
	 * ���������߼�������ϵͳʱ������һ��֡��ʱ��
	 * ��������
	 */
	@Override
	public void run() {
		try {
			for (ITimeCtrl thread : threadList) {
				thread.runTimeLine();// �ܱ�����̵߳�ʱ���߼Ʋ���
				if (Thread.State.WAITING.equals(thread.getState())) {
					synchronized (thread) {
						thread.notify();
					}
				}
			}
		} finally {
			if (threadList.size() == 0) {// û���߳��ˣ�����ֹ��ʱ��
				this.cancel();
				this.threadList.clear();
				Debug.debug("��ʱ��ֹͣ");
			}
		}
	}
	
	/**
	 * �����߳�
	 * @param gameThread
	 */
	public void addThread(ITimeCtrl ctrlThread){
		this.threadList.add(ctrlThread);
	}
	
	/**
	 * �Ƴ��߳�
	 * @param ctrlThread
	 */
	public void removeThread(ITimeCtrl ctrlThread){
		this.threadList.remove(ctrlThread);
	}
	
	/**
	 * ���ö�ʱ����״̬
	 * ��Ϊstate�Ǹ�Ĭ������ ���Բ���ֱ�Ӹ�
	 * �÷���ķ�ʽ�޸�
	 */
	public void resetState(){		
		try {
			Field state=this.getClass().getSuperclass().getDeclaredField("state");
			state.setAccessible(true);//ȡ����ȫ���
			state.set(this,0);//���õ�ǰ��ʱ����Ϊ δ����
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
