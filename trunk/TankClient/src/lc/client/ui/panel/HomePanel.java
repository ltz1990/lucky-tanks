package lc.client.ui.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import lc.client.environment.ClientConstant;

/**
 * 首页 用来放图片
 * @author LUCKY
 *
 */
public class HomePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private static HomePanel homePanel;
	private Image image;
	
	private HomePanel(){
		super();
		this.setBounds(0, 0, ClientConstant.PANEL_WIDTH, ClientConstant.PANEL_HEIGHT);
		image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/homepage.png"));
		this.setVisible(true);
	}
	
	/**
	 * 获得首页
	 * @return
	 */
	public static synchronized HomePanel getInstance(){
		if(homePanel==null){
			homePanel=new HomePanel();
		}
		return homePanel;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(image,0,0,ClientConstant.PANEL_WIDTH,ClientConstant.PANEL_HEIGHT, 0, 0,948,537,this);
	}
	
	
}
