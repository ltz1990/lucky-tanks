package lc.client.core.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import lc.client.environment.ClientConstant;

/**
 * �����ؼ�
 * 
 * @author LUCKY
 * 
 */
public abstract class BaseComp implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int x;//��ǰλ�ã������ƶ�
	private int y;
	private int startX;//��ʼλ�ã���������
	private int startY;
	public transient int width;//���
	public transient int height;//�߶�

	public BaseComp(Point p) {		
		this.startX = (int) p.getX();
		this.startY = (int) p.getY();
		this.x=this.startX;
		this.y=this.startY;
	}

	public BaseComp(int x, int y) {
		this.startX=x;
		this.startY=y;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * ���ÿؼ�λ��
	 */
	public final void resetPosition(){
		this.x=this.startX;
		this.y=this.startY;
	}
	
	/**
	 * ������ʼλ�õ�
	 * @param p
	 */
	public final void resetStartPoint(Point p){
		this.startX=(int)p.getX();
		this.startY=(int)p.getY();
		this.x=this.startX;
		this.y=this.startY;
	}

	/**
	 * �ƶ�,������д
	 * 
	 * @param i
	 */
	public final void move(int i) {
		if (isMoveAble()) {
			if(i==ClientConstant.KEY_UP){
				moveUp();
			}else if(i==ClientConstant.KEY_DOWN){
				moveDown();
			}else if(i==ClientConstant.KEY_LEFT){
				moveLeft();
			}else if(i==ClientConstant.KEY_RIGHT){
				moveRight();
			}
		}
	}

	/**
	 * ����
	 */
	private void moveRight() {
		// TODO Auto-generated method stub
		if(x+(width>>1)<=ClientConstant.PANEL_WIDTH){
			this.x+=this.getSpeed();
		}
	}

	/**
	 * ����
	 */
	private void moveLeft() {
		// TODO Auto-generated method stub
		if(x-(width>>1)>=0){
			this.x-=this.getSpeed();
		}
	}

	/**
	 * ����
	 */
	private void moveDown() {
		// TODO Auto-generated method stub
		if(y+(height>>1)<=ClientConstant.PANEL_HEIGHT){
			this.y+=this.getSpeed();
		}
	}

	/**
	 * ����
	 */
	private void moveUp() {
		// TODO Auto-generated method stub
		if(y-(height>>1)>=0){
			this.y-=this.getSpeed();
		}
	}

	/**
	 * �ƶ���
	 * 
	 * @param p
	 */
	public final void moveTo(Point p){
		if(isMoveAble()){
			this.x=(int)p.getX();
			this.y=(int)p.getY();
		}
	}
	
	/**
	 * ��õ�ǰ�ؼ��ĺ�����
	 * @return
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * ��õ�ǰ�ؼ���������
	 * @return
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * ��õ�ǰ�ؼ�������
	 * @return
	 */
	public Point getCompPoint(){
		return new Point(this.x,this.y);
	}
	
	/**
	 * ��þ���ԭ��ľ��룬����
	 * @return
	 */
	public int getRange(){
		return Math.abs((x-startX)+(y-startY));
	}
	
	public void setSize(Dimension d){
		this.height=d.height;
		this.width=d.width;
	}
	
	public Dimension getSize(){
		return new Dimension(this.width,this.height);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * �Ƿ���ƶ�
	 * 
	 * @return
	 */
	public abstract boolean isMoveAble();

	/**
	 * �ƶ��ٶ�
	 */
	public abstract int getSpeed();
	
	/**
	 * ���ƿؼ�
	 * @param g
	 */
	public abstract void paint(Graphics g);
	
}
