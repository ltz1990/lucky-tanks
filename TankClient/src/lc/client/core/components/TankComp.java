package lc.client.core.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Map;

import lc.client.core.factory.TankFactory;
import lc.client.core.net.MsgCenter;
import lc.client.util.ClientConstant;

/**
 * 坦克控件
 * 可以优化的地方：Graphics可以在初始化的时候放入
 * @author LUCKY
 *
 */
public class TankComp extends BaseComp{
	private static final long serialVersionUID = 1L;
	private transient int statu=ClientConstant.KEY_STOP;//状态:停止，移动
	private int tankType=1;
	private String name;
	private Map<String, TankComp> tankList;
	/**
	 * 不序列化
	 */
	private int dirStatu=ClientConstant.KEY_UP;//最后朝向
	private transient Image tankImg_up;
	private transient Image tankImg_down;
	private transient Image tankImg_left;
	private transient Image tankImg_right;
	
	private transient BulletComp[] bullet;
	
	public TankComp(Point p,int tankType,String name) {
		super(p);
		// TODO Auto-generated constructor stub
		tankImg_up=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/tank_u.gif"));
		tankImg_down=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/tank_d.gif"));
		tankImg_left=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/tank_l.gif"));
		tankImg_right=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/tank_r.gif"));
		//初始化尺寸
		setSize(new Dimension(ClientConstant.TankWidth<<1, ClientConstant.TankHeight<<1));
		//初始化炮弹 
		bullet=new BulletComp[ClientConstant.BULLET_MAX_AMOUNT];
		for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
			bullet[i]=new BulletComp(new Point(0,0),this);
		}
		this.name=name;
		this.tankType=tankType;
		tankList= TankFactory.getInstance().getTankList();
	}
	
	/**
	 * 根据状态来移动
	 */
	public synchronized void run(){
		if(this.tankType==ClientConstant.USER){
			checkThenMove();//带碰撞检测的移动
		}else{//非用户坦克不检测碰撞
			move(statu);
		}
		for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
			bullet[i].run();//跑子弹
		}
		if(statu!=ClientConstant.KEY_STOP){
			this.dirStatu=statu;//记录最后朝向
			if(tankType==ClientConstant.USER){//用户坦克
				MsgCenter.addTankMoveMsg(name, getX(), getY(), dirStatu);
			}
		}
	}

	/**
	 * 带碰撞监测的移动
	 */
	private void checkThenMove() {
		boolean collide=false;//碰撞
		if(statu==ClientConstant.KEY_RIGHT){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getRightLimit()>tank.getLeftLimit()
					&&this.getDownLimit()>tank.getUpLimit()
					&&this.getUpLimit()<tank.getDownLimit()
					&&this.getRightLimit()<tank.getX()){//只在坦克插进另外一个坦克一半之前做此限制，防止锁死
					collide=true;//碰撞
				}
			}
		}else if(statu==ClientConstant.KEY_LEFT){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getLeftLimit()<tank.getRightLimit()
					&&this.getDownLimit()>tank.getUpLimit()
					&&this.getUpLimit()<tank.getDownLimit()
					&&this.getLeftLimit()>tank.getX()){//防死锁
					collide=true;//碰撞
				}
			}
		}else if(statu==ClientConstant.KEY_UP){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getRightLimit()>tank.getLeftLimit()
					&&this.getLeftLimit()<tank.getRightLimit()
					//&&this.getDownLimit()>tank.getUpLimit()
					&&this.getUpLimit()<tank.getDownLimit()
					&&this.getUpLimit()>tank.getY()){//防死锁
					collide=true;//碰撞
				}
			}
		}else if(statu==ClientConstant.KEY_DOWN){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getRightLimit()>tank.getLeftLimit()
					&&this.getLeftLimit()<tank.getRightLimit()
					&&this.getDownLimit()>tank.getUpLimit()
					//&&this.getUpLimit()<tank.getDownLimit()
					&&this.getDownLimit()<tank.getY()){//防死锁
					collide=true;//碰撞
				}
			}
		}
		if(!collide){
			move(statu);//跑坦克
		}
	}
	
	/**
	 * 射
	 */
	public void shot(){
		for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
			if(!bullet[i].isAlive()){//只射生存态的
				bullet[i].beShot(getCompPoint(), dirStatu);
				if(tankType==ClientConstant.USER){//用户坦克先发
					MsgCenter.addBulletShotMsg(name, getX(),getY(),dirStatu);
				}
				break;
			}
		}
	}

	@Override
	public boolean isMoveAble() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return ClientConstant.TankSpeed;		
	}

	/**
	 * 绘制坦克
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2=(Graphics2D)g;
		if(dirStatu==ClientConstant.KEY_LEFT){
			drawImage(tankImg_left, g2);
		}else if(dirStatu==ClientConstant.KEY_RIGHT){
			drawImage(tankImg_right, g2);
		}else if(dirStatu==ClientConstant.KEY_UP){
			drawImage(tankImg_up, g2);
		}else if(dirStatu==ClientConstant.KEY_DOWN){
			drawImage(tankImg_down, g2);
		}
		if(this.tankType==ClientConstant.USER){
			g2.setColor(Color.yellow);
		}else{
			g2.setColor(Color.white);
		}
		g2.drawString(this.name, this.getX()-ClientConstant.TankWidth, this.getY()-ClientConstant.TankHeight-5);
		for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
			bullet[i].paint(g2);
		}
	}
	
	/**
	 * 画图-坦克
	 * @param img
	 * @param g2
	 */
	private void drawImage(Image img,Graphics2D g2){
		g2.drawImage(img,getX()-ClientConstant.TankWidth,getY()-ClientConstant.TankHeight,getX()+ClientConstant.TankWidth,getY()+ClientConstant.TankHeight,0,0,513,513,null);
	}

	/**
	 * 坦克当前状态,移动方向
	 * @return
	 */
	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}
	
	/**
	 * 设置朝向
	 * @param dirStatu
	 */
	public void setDirStatus(int dirStatu){
		this.dirStatu=dirStatu;
	}
	
	public int getDirStatus(){
		return this.dirStatu;
	}
	
	/**
	 * 返回子弹数组
	 * @return
	 */
	public BulletComp[] getBullets(){
		return this.bullet;
	}
	
	/**
	 * 左边界
	 * @return
	 */
	public int getLeftLimit(){
		return this.getX()-ClientConstant.TankWidth;
	}
	
	/**
	 * 右边界
	 * @return
	 */
	public int getRightLimit(){
		return this.getX()+ClientConstant.TankWidth;
	}
	
	/**
	 * 上边界
	 * @return
	 */
	public int getUpLimit(){
		return this.getY()-ClientConstant.TankHeight;
	}
	
	/**
	 * 下边界
	 * @return
	 */
	public int getDownLimit(){
		return this.getY()+ClientConstant.TankHeight;
	}
	
	/**
	 * 坦克名字
	 * @return
	 */
	public String getName(){
		return this.name;
	}

}
