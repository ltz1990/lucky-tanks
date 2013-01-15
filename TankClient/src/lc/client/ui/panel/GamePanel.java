package lc.client.ui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import lc.client.core.components.TankComp;
import lc.client.core.factory.TankFactory;
import lc.client.environment.ClientConstant;

/**
 * 游戏界面容器
 * @author LUCKY
 *
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static GamePanel gamePanel;
	private Map<String,TankComp> otherTanks;
	private TankComp myTank;//用户坦克
	
	/**
	 * 获得游戏界面容器
	 * @return
	 */
	public static synchronized GamePanel getInstance(){
		if(gamePanel==null){
			gamePanel=new GamePanel();
		}
		return gamePanel;
	}

	/**
	 * 构造方法
	 */
	private GamePanel(){
		super();
		this.setBounds(0, 0, ClientConstant.PANEL_WIDTH, ClientConstant.PANEL_HEIGHT);
		this.setVisible(true);
		this.setBackground(Color.black);	
		
		//容器中添加坦克
		TankFactory factory=TankFactory.getInstance();
		this.myTank=factory.getUserTank();
		this.otherTanks=factory.getTankList();
		if(myTank==null||otherTanks==null){
			throw new NullPointerException();
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		//if(getMyTank()!=null){
		myTank.paint(g);
		for(String key:otherTanks.keySet()){
			otherTanks.get(key).paint(g);
		}
		//}
		//g.drawImage(getMyTank().getTankImg(),0,0,null);
		//repaint();//应当放到游戏线程中去调用 否则循环调用CPU占用过高
	}
	
}
