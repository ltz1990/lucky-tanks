package lc.client.core.factory;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import lc.client.core.components.TankComp;
import lc.client.environment.ClientConstant;

/**
 * ̹�˹���
 * ���ݷ���˵���Ϣ����̹�ˣ���������
 * ����Ҫһ������MAP��
 */
public class TankFactory {
	private static TankFactory factory;
	private Map<Integer,TankComp> tankList=null;
	private TankComp userTank=null;
	
	private TankFactory(){
		//tankList=Collections.synchronizedList(new LinkedList<TankComp>());
		tankList=new HashMap<Integer,TankComp>(16);//��Ҫ����������ʣ�ͬʱ��Ҫ�����ٶ�
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
	
/*	*//**
	 * ����̹��
	 * �˷������޸ģ�����������</br>
	 * ����Ĭ�����꣬������ʹ��
	 * @param user
	 * @return
	 * @deprecated
	 *//*
	public void createTank(String name,int user){
		switch(user){
		//case 0:userTank=new TankComp(name,id,ClientConstant.USER,new Point(100,100));break;
		//case 1:tankList.put(name,new TankComp(new Point(100,100),ClientConstant.OTHER,name));break;
		default:break;
		}
	}*/
	
	/**
	 * ����̹��,������������
	 * @param user
	 * @return
	 */
	public void createTank(String name,int id,int user,Point p){
		switch(user){
		case 0:userTank=new TankComp(name,id,ClientConstant.USER,p);break;
		case 1:tankList.put(id,new TankComp(name,id,user,p));break;
		default:break;
		}
	}
	
	/**
	 * �õ�̹�˼���
	 * @return
	 */
	public Map<Integer,TankComp> getTankList(){
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
