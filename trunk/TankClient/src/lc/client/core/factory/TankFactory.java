package lc.client.core.factory;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import lc.client.core.components.TankComp;
import lc.client.util.ClientConstant;

/**
 * ̹�˹���
 * ���ݷ���˵���Ϣ����̹�ˣ���������
 * ����Ҫһ������MAP��
 */
public class TankFactory {
	private static TankFactory factory;
	private Map<String,TankComp> tankList=null;
	private TankComp userTank=null;
	
	private TankFactory(){
		//tankList=Collections.synchronizedList(new LinkedList<TankComp>());
		tankList=new HashMap<String,TankComp>(16);//��Ҫ����������ʣ�ͬʱ��Ҫ�����ٶ�
	}
	
	/**
	 * ��ʼ��̹�˹���
	 * @return
	 */
	public static synchronized TankFactory getInstance(){
		if(factory==null){
			factory=new TankFactory();
		}
		return factory;
	}
	
	/**
	 * ����̹��
	 * �˷������޸ģ�����������</br>
	 * ����Ĭ�����꣬������ʹ��
	 * @param user
	 * @return
	 * @deprecated
	 */
	public void createTank(String name,int user){
		switch(user){
		case 0:userTank=new TankComp(new Point(100,100),ClientConstant.USER,name);break;
		case 1:tankList.put(name,new TankComp(new Point(100,100),ClientConstant.OTHER,name));break;
		default:break;
		}
	}
	
	/**
	 * ����̹��,������������
	 * @param user
	 * @return
	 */
	public void createTank(String name,int user,Point p){
		switch(user){
		case 0:userTank=new TankComp(p,ClientConstant.USER,name);break;
		case 1:tankList.put(name,new TankComp(p,ClientConstant.OTHER,name));break;
		default:break;
		}
	}
	
	/**
	 * �õ�̹�˼���
	 * @return
	 */
	public Map<String,TankComp> getTankList(){
		return tankList;
	}
	
	/**
	 * �õ���ǰ���̹��
	 * @return
	 */
	public TankComp getUserTank(){
		return userTank;
	}
	
	/**
	 * ���̹�˹��������̹�˳���
	 */
	public void clear(){
		this.tankList.clear();
	}

}
