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
 * ����̹�����߷�����
 * 1.�ڿͻ��ˣ����ݷ���˷�����̹�˷����Զ����ߣ�ת���ʱ����������λ��
 * 2.�Է���˷���������ΪĿ���Զ�����
 * 3.�Զ��з�ʽ�������꣬��һ���ٶȱ���,��������
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
	 * �����û�̹���߳�
	 * @return
	 */
	public static synchronized OtherTankThread getInstance(){
		if(otherTankThread==null){
			otherTankThread=new OtherTankThread();
		}
		return otherTankThread;
	}
	
	/**
	 * ����
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
					bullets[i].run();//���ӵ�
				}
			}
			pause();
		}
		TimerThread.getInstance().removeThread(this);//��ʱ������߳����Ƴ�
		Debug.debug("����̹���߳̽���");
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
