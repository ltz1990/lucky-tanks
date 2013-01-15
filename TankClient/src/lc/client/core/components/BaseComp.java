package lc.client.core.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import lc.client.environment.ClientConstant;

/**
 * 基本控件
 * 
 * @author LUCKY
 * 
 */
public abstract class BaseComp implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int x;//当前位置，用于移动
	private int y;
	private int startX;//起始位置，用于重置
	private int startY;
	public transient int width;//宽度
	public transient int height;//高度

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
	 * 重置控件位置
	 */
	public final void resetPosition(){
		this.x=this.startX;
		this.y=this.startY;
	}
	
	/**
	 * 重置起始位置到
	 * @param p
	 */
	public final void resetStartPoint(Point p){
		this.startX=(int)p.getX();
		this.startY=(int)p.getY();
		this.x=this.startX;
		this.y=this.startY;
	}

	/**
	 * 移动,不可重写
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
	 * 右移
	 */
	private void moveRight() {
		// TODO Auto-generated method stub
		if(x+(width>>1)<=ClientConstant.PANEL_WIDTH){
			this.x+=this.getSpeed();
		}
	}

	/**
	 * 左移
	 */
	private void moveLeft() {
		// TODO Auto-generated method stub
		if(x-(width>>1)>=0){
			this.x-=this.getSpeed();
		}
	}

	/**
	 * 下移
	 */
	private void moveDown() {
		// TODO Auto-generated method stub
		if(y+(height>>1)<=ClientConstant.PANEL_HEIGHT){
			this.y+=this.getSpeed();
		}
	}

	/**
	 * 上移
	 */
	private void moveUp() {
		// TODO Auto-generated method stub
		if(y-(height>>1)>=0){
			this.y-=this.getSpeed();
		}
	}

	/**
	 * 移动到
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
	 * 获得当前控件的横坐标
	 * @return
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * 获得当前控件的纵坐标
	 * @return
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * 获得当前控件的坐标
	 * @return
	 */
	public Point getCompPoint(){
		return new Point(this.x,this.y);
	}
	
	/**
	 * 获得距离原点的距离，横向
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
	 * 是否可移动
	 * 
	 * @return
	 */
	public abstract boolean isMoveAble();

	/**
	 * 移动速度
	 */
	public abstract int getSpeed();
	
	/**
	 * 绘制控件
	 * @param g
	 */
	public abstract void paint(Graphics g);
	
}
