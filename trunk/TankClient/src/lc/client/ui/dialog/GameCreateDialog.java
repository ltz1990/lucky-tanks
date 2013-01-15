package lc.client.ui.dialog;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.SocketAddress;

import javax.swing.JFrame;

import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.core.net.MsgCenter;
import lc.client.core.net.NetConnection;
import lc.client.ui.components.LComboBox;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.components.LTextField;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.menu.MainMenu;
import lc.client.util.ClientConstant;
import lc.client.util.Debug;
import lc.client.util.FontSetting;
import lc.client.webservice.RemoteServiceProxy;
import lc.client.webservice.wscode.GameHouse;

/**
 * ������Ϸ����
 * @author LUCKY
 *
 */
public class GameCreateDialog extends LDialog {
	private static final long serialVersionUID = 1L;
	private static GameCreateDialog gameCreateDialog;
	private static String TITLE="������Ϸ";
	private static int DIALOG_WIDTH=300;
	private static int DIALOG_HEIGHT=200;
	private LTextField gameId;//��Ϸ����
	private LComboBox gameType;//��Ϸ����
	private LComboBox playerNum;//��Ϸ����

	private GameCreateDialog(JFrame jframe, String title, int dialogWidht,
			int dialogHeight) {
		super(jframe, title, dialogWidht, dialogHeight,false);//��ʱ����Ϊ��ģ̬
		this.add(new LOkCancelButton());
		gameId=(LTextField) this.add(new LTextField("��������",5,30));
		gameType=(LComboBox)this.add(new LComboBox("��Ϸģʽ",5,60));
		gameType.addItem("����ģʽ",ClientConstant.GAMETYPE_FLAG);
		gameType.addItem("��սģʽ",ClientConstant.GAMETYPE_FIGHT);
		playerNum=(LComboBox)this.add(new LComboBox("��Ϸ����",5,90));
		playerNum.addItem("2��", 2);
		playerNum.addItem("4��", 4);
		playerNum.addItem("8��", 8);
		playerNum.addItem("16��", 16);
		// TODO Auto-generated constructor stub
		FontSetting.setChildrenFont(this, new Font("΢���ź�",Font.PLAIN,12));
	}
	
	/**
	 * �����Ϸ��������
	 * @return
	 */
	public static synchronized GameCreateDialog getInstance(){
		if(gameCreateDialog==null){
			gameCreateDialog=new GameCreateDialog(MainFrame.getInstance(), TITLE, DIALOG_WIDTH, DIALOG_HEIGHT);
		}
		return gameCreateDialog;
	}

	@Override
	public void onOkBtn() {
		// TODO Auto-generated method stub
		try {
			NetConnection.openConnect();
			SocketAddress address=NetConnection.getSocketChannel().socket().getLocalSocketAddress();
			GameHouse house=new GameHouse();
			house.setCreator("liutianzhi");
			house.setGameType("test");
			house.setName("�ҵķ���");
			house.setPlayerCount("10");
			RemoteServiceProxy.getInstance().createGame(house,address.toString());
			TankFactory factory = TankFactory.getInstance();
			factory.createTank("������ɵ��", ClientConstant.USER);
			GameController.startGame();
			NetConnection.startNetThread();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainMenu.getInstance().setState(LMenuItem.IN_GAME);
	}

	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub

	}
}
