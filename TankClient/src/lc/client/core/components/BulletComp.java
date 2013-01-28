package lc.client.core.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Map;

import lc.client.core.factory.TankFactory;
import lc.client.environment.ClientConstant;

/**
 * 炮弹
 * @author LUCKY
 *
 */
public class BulletComp extends BaseComp{
	private static final long serialVersionUID = 1L;
	private Map<Integer, TankComp> tankList;
	private BaseComp parent;
	private boolean alive=false;
	private int dir=0;//方向
	private int pedometer=0;//计步器
	
	public BulletComp(Point p) {
		super(p);
		setSize(new Dimension(ClientConstant.BULLET_RAD, ClientConstant.BULLET_RAD));
		tankList=TankFactory.getInstance().getTankList();
	}
	
	public BulletComp(Point p,BaseComp parent){
		super(p);
		setSize(new Dimension(ClientConstant.BULLET_RAD, ClientConstant.BULLET_RAD));
		this.parent=parent;
		tankList=TankFactory.getInstance().getTankList();
	}
	
	/**
	 * 发射状态
	 */
	public void beShot(Point p,int dir){
		this.alive=true;
		this.dir=dir;
		this.pedometer=0;//计步器归零
		this.resetStartPoint(p);//重置位置
	}
	
	/**
	 * 子弹运行
	 */
	public void run(){
		if(!isAlive()) return;
		move(dir);//炮弹运行
		collideCheck();//碰撞监测
		plusPedometer();//计步器增加
	}
	
	/**
	 * 碰撞监测
	 */
	private void collideCheck(){
		for(Integer key:tankList.keySet()){
			TankComp otherTank=tankList.get(key);
			if(otherTank==this.parent) continue;//不跟自己做碰撞监测
			if(this.getX()>otherTank.getLeftLimit()
					&&this.getX()<otherTank.getRightLimit()
					&&this.getY()>otherTank.getUpLimit()
					&&this.getY()<otherTank.getDownLimit()){
				this.alive=false;//子弹死
			}
		}
		//监测当前用户坦克是否被命中	
		TankComp userTank = TankFactory.getInstance().getUserTank();
		if(userTank!=this.parent){
			if(this.getX()>userTank.getLeftLimit()
					&&this.getX()<userTank.getRightLimit()
					&&this.getY()>userTank.getUpLimit()
					&&this.getY()<userTank.getDownLimit()){
				this.alive=false;
			}
		}
	}
	
	/**
	 * 运行计步器
	 */
	public void plusPedometer(){
		if(this.pedometer<ClientConstant.BULLET_MAXSTEP){
			this.pedometer++;
		}else{
			this.alive=false;//超出步数，炮弹结束
		}
	}
	
	/**
	 * 是否活动态
	 * @return
	 */
	public boolean isAlive(){
		return this.alive;
	}
	
	@Override
	public boolean isMoveAble() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return ClientConstant.BULLET_SPEED;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if(!this.alive) return;//死的，就不画
		Graphics2D g2=(Graphics2D)g;
		Ellipse2D el=new Ellipse2D.Double(this.getX()-(ClientConstant.BULLET_RAD>>1),this.getY()-(ClientConstant.BULLET_RAD>>1),ClientConstant.BULLET_RAD,ClientConstant.BULLET_RAD);
		g2.setColor(Color.white);
		g2.fill(el);
	}
	
}
