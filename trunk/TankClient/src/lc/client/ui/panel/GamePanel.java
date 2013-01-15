package lc.client.ui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import lc.client.core.components.TankComp;
import lc.client.core.factory.TankFactory;
import lc.client.environment.ClientConstant;

/**
 * ��Ϸ��������
 * @author LUCKY
 *
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static GamePanel gamePanel;
	private Map<String,TankComp> otherTanks;
	private TankComp myTank;//�û�̹��
	
	/**
	 * �����Ϸ��������
	 * @return
	 */
	public static synchronized GamePanel getInstance(){
		if(gamePanel==null){
			gamePanel=new GamePanel();
		}
		return gamePanel;
	}

	/**
	 * ���췽��
	 */
	private GamePanel(){
		super();
		this.setBounds(0, 0, ClientConstant.PANEL_WIDTH, ClientConstant.PANEL_HEIGHT);
		this.setVisible(true);
		this.setBackground(Color.black);	
		
		//���������̹��
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
		//repaint();//Ӧ���ŵ���Ϸ�߳���ȥ���� ����ѭ������CPUռ�ù���
	}
	
}
