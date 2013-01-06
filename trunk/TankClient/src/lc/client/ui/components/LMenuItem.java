package lc.client.ui.components;

import javax.swing.JMenuItem;

/**
 * 带状态的菜单项
 * 实现方式参考SelectionKey
 * @author LUCKY
 *
 */
public class LMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	public static final int NO_CONN=1<<0;//无连接
	public static final int NOT_IN_GAME=1<<1;//有链接无游戏
	public static final int IN_GAME=1<<2;//游戏中
	
	private int state=1;
	
	/**
	 * 默认实现，注册所有状态
	 * @param string
	 */
	public LMenuItem(String string) {
		super(string);
		setStates(NO_CONN|NOT_IN_GAME|IN_GAME);
	}
	
	/**
	 * 指定状态的JMENUITEM
	 * @param string
	 * @param state
	 */
	public LMenuItem(String string,int state) {
		super(string);
		setStates(state);
	}

	/**
	 * 采用按位与和按位或运算，所以可用"按位或"设置多个属性
	 * @param states
	 */
	public void setStates(int states){
		this.state=states;
	}
	
	/**
	 * 是否属于此状态
	 * @param state
	 * @return
	 */
	public boolean isInThisState(int state){
		return (this.state&state)!=0;
	}
}
