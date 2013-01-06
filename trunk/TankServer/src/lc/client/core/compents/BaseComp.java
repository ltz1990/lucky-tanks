package lc.client.core.compents;

import java.io.Serializable;

public abstract class BaseComp implements Serializable{
	private static final long serialVersionUID = 1L;
	public int x;//当前位置，用于移动
	public int y;
	private int startX;//起始位置，用于重置
	private int startY;
}
