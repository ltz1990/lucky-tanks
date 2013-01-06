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
 * ̹�˿ؼ�
 * �����Ż��ĵط���Graphics�����ڳ�ʼ����ʱ�����
 * @author LUCKY
 *
 */
public class TankComp extends BaseComp{
	private static final long serialVersionUID = 1L;
	private transient int statu=ClientConstant.KEY_STOP;//״̬:ֹͣ���ƶ�
	private int tankType=1;
	private String name;
	private Map<String, TankComp> tankList;
	/**
	 * �����л�
	 */
	private int dirStatu=ClientConstant.KEY_UP;//�����
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
		//��ʼ���ߴ�
		setSize(new Dimension(ClientConstant.TankWidth<<1, ClientConstant.TankHeight<<1));
		//��ʼ���ڵ� 
		bullet=new BulletComp[ClientConstant.BULLET_MAX_AMOUNT];
		for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
			bullet[i]=new BulletComp(new Point(0,0),this);
		}
		this.name=name;
		this.tankType=tankType;
		tankList= TankFactory.getInstance().getTankList();
	}
	
	/**
	 * ����״̬���ƶ�
	 */
	public synchronized void run(){
		if(this.tankType==ClientConstant.USER){
			checkThenMove();//����ײ�����ƶ�
		}else{//���û�̹�˲������ײ
			move(statu);
		}
		for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
			bullet[i].run();//���ӵ�
		}
		if(statu!=ClientConstant.KEY_STOP){
			this.dirStatu=statu;//��¼�����
			if(tankType==ClientConstant.USER){//�û�̹��
				MsgCenter.addTankMoveMsg(name, getX(), getY(), dirStatu);
			}
		}
	}

	/**
	 * ����ײ�����ƶ�
	 */
	private void checkThenMove() {
		boolean collide=false;//��ײ
		if(statu==ClientConstant.KEY_RIGHT){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getRightLimit()>tank.getLeftLimit()
					&&this.getDownLimit()>tank.getUpLimit()
					&&this.getUpLimit()<tank.getDownLimit()
					&&this.getRightLimit()<tank.getX()){//ֻ��̹�˲������һ��̹��һ��֮ǰ�������ƣ���ֹ����
					collide=true;//��ײ
				}
			}
		}else if(statu==ClientConstant.KEY_LEFT){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getLeftLimit()<tank.getRightLimit()
					&&this.getDownLimit()>tank.getUpLimit()
					&&this.getUpLimit()<tank.getDownLimit()
					&&this.getLeftLimit()>tank.getX()){//������
					collide=true;//��ײ
				}
			}
		}else if(statu==ClientConstant.KEY_UP){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getRightLimit()>tank.getLeftLimit()
					&&this.getLeftLimit()<tank.getRightLimit()
					//&&this.getDownLimit()>tank.getUpLimit()
					&&this.getUpLimit()<tank.getDownLimit()
					&&this.getUpLimit()>tank.getY()){//������
					collide=true;//��ײ
				}
			}
		}else if(statu==ClientConstant.KEY_DOWN){
			for(String key:tankList.keySet()){
				TankComp tank=tankList.get(key);
				if(this.getRightLimit()>tank.getLeftLimit()
					&&this.getLeftLimit()<tank.getRightLimit()
					&&this.getDownLimit()>tank.getUpLimit()
					//&&this.getUpLimit()<tank.getDownLimit()
					&&this.getDownLimit()<tank.getY()){//������
					collide=true;//��ײ
				}
			}
		}
		if(!collide){
			move(statu);//��̹��
		}
	}
	
	/**
	 * ��
	 */
	public void shot(){
		for(int i=0;i<ClientConstant.BULLET_MAX_AMOUNT;i++){
			if(!bullet[i].isAlive()){//ֻ������̬��
				bullet[i].beShot(getCompPoint(), dirStatu);
				if(tankType==ClientConstant.USER){//�û�̹���ȷ�
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
	 * ����̹��
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
	 * ��ͼ-̹��
	 * @param img
	 * @param g2
	 */
	private void drawImage(Image img,Graphics2D g2){
		g2.drawImage(img,getX()-ClientConstant.TankWidth,getY()-ClientConstant.TankHeight,getX()+ClientConstant.TankWidth,getY()+ClientConstant.TankHeight,0,0,513,513,null);
	}

	/**
	 * ̹�˵�ǰ״̬,�ƶ�����
	 * @return
	 */
	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}
	
	/**
	 * ���ó���
	 * @param dirStatu
	 */
	public void setDirStatus(int dirStatu){
		this.dirStatu=dirStatu;
	}
	
	public int getDirStatus(){
		return this.dirStatu;
	}
	
	/**
	 * �����ӵ�����
	 * @return
	 */
	public BulletComp[] getBullets(){
		return this.bullet;
	}
	
	/**
	 * ��߽�
	 * @return
	 */
	public int getLeftLimit(){
		return this.getX()-ClientConstant.TankWidth;
	}
	
	/**
	 * �ұ߽�
	 * @return
	 */
	public int getRightLimit(){
		return this.getX()+ClientConstant.TankWidth;
	}
	
	/**
	 * �ϱ߽�
	 * @return
	 */
	public int getUpLimit(){
		return this.getY()-ClientConstant.TankHeight;
	}
	
	/**
	 * �±߽�
	 * @return
	 */
	public int getDownLimit(){
		return this.getY()+ClientConstant.TankHeight;
	}
	
	/**
	 * ̹������
	 * @return
	 */
	public String getName(){
		return this.name;
	}

}
