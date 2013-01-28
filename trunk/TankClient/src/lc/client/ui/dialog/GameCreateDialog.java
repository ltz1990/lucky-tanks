package lc.client.ui.dialog;

import java.awt.Font;
import java.awt.Point;
import java.io.IOException;
import java.net.SocketAddress;

import javax.swing.JFrame;

import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.core.net.NetConnection;
import lc.client.environment.ClientConstant;
import lc.client.environment.RuntimeEnvironment;
import lc.client.environment.UserInfo;
import lc.client.ui.components.LComboBox;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.components.LTextField;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.menu.MainMenu;
import lc.client.util.FontSetting;
import lc.client.webservice.RemoteServiceProxy;
import lc.client.webservice.wscode.GameHouse;
import lc.client.webservice.wscode.MsgEntry;

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
		gameType.addItem(ClientConstant.GAMETYPE_NAME.get(ClientConstant.GAMETYPE_FLAG),ClientConstant.GAMETYPE_FLAG);
		gameType.addItem(ClientConstant.GAMETYPE_NAME.get(ClientConstant.GAMETYPE_FIGHT),ClientConstant.GAMETYPE_FIGHT);
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
			UserInfo userInfo = RuntimeEnvironment.getUserInfo();
			GameHouse house=new GameHouse();
			initHouse(house, userInfo);
			MsgEntry msg=RemoteServiceProxy.getInstance().createGame(house,address.toString());
			userInfo.setHouseId(msg.getObject().toString());
			TankFactory factory = TankFactory.getInstance();
			factory.createTank(userInfo.getName(),userInfo.getUserId(), ClientConstant.USER,new Point(100,100));
			GameController.startGame();
			NetConnection.startNetThread();
			MainMenu.getInstance().setState(LMenuItem.IN_GAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���÷�������
	 * @author LUCKY 2013-1-19
	 * @param house
	 * @param userInfo
	 */
	private void initHouse(GameHouse house, UserInfo userInfo) {
		house.setCreator(userInfo);
		house.setGameType(gameType.getSelectedValue());
		house.setName(gameId.getValue());
		house.setPlayerCount(playerNum.getSelectedValue());
	}

	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub

	}
}
