package lc.client.core.factory;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import lc.client.core.components.TankComp;
import lc.client.util.ClientConstant;

/**
 * 坦克工厂
 * 根据服务端的消息建立坦克，分配坐标
 * 还需要一个坐标MAP；
 */
public class TankFactory {
	private static TankFactory factory;
	private Map<String,TankComp> tankList=null;
	private TankComp userTank=null;
	
	private TankFactory(){
		//tankList=Collections.synchronizedList(new LinkedList<TankComp>());
		tankList=new HashMap<String,TankComp>(16);//需要大量随机访问，同时需要遍历速度
	}
	
	/**
	 * 初始化坦克工厂
	 * @return
	 */
	public static synchronized TankFactory getInstance(){
		if(factory==null){
			factory=new TankFactory();
		}
		return factory;
	}
	
	/**
	 * 创建坦克
	 * 此方法需修改，关联服务器</br>
	 * 采用默认坐标，不建议使用
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
	 * 创建坦克,服务器给坐标
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
	 * 得到坦克集合
	 * @return
	 */
	public Map<String,TankComp> getTankList(){
		return tankList;
	}
	
	/**
	 * 得到当前玩家坦克
	 * @return
	 */
	public TankComp getUserTank(){
		return userTank;
	}
	
	/**
	 * 清除坦克工厂，玩家坦克除外
	 */
	public void clear(){
		this.tankList.clear();
	}

}
