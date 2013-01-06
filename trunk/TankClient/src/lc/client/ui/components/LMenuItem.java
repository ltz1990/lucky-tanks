package lc.client.ui.components;

import javax.swing.JMenuItem;

/**
 * ��״̬�Ĳ˵���
 * ʵ�ַ�ʽ�ο�SelectionKey
 * @author LUCKY
 *
 */
public class LMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	public static final int NO_CONN=1<<0;//������
	public static final int NOT_IN_GAME=1<<1;//����������Ϸ
	public static final int IN_GAME=1<<2;//��Ϸ��
	
	private int state=1;
	
	/**
	 * Ĭ��ʵ�֣�ע������״̬
	 * @param string
	 */
	public LMenuItem(String string) {
		super(string);
		setStates(NO_CONN|NOT_IN_GAME|IN_GAME);
	}
	
	/**
	 * ָ��״̬��JMENUITEM
	 * @param string
	 * @param state
	 */
	public LMenuItem(String string,int state) {
		super(string);
		setStates(state);
	}

	/**
	 * ���ð�λ��Ͱ�λ�����㣬���Կ���"��λ��"���ö������
	 * @param states
	 */
	public void setStates(int states){
		this.state=states;
	}
	
	/**
	 * �Ƿ����ڴ�״̬
	 * @param state
	 * @return
	 */
	public boolean isInThisState(int state){
		return (this.state&state)!=0;
	}
}
