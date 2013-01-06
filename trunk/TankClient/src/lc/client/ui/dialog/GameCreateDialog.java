package lc.client.ui.dialog;

import java.awt.Font;

import javax.swing.JFrame;

import lc.client.core.factory.TankFactory;
import lc.client.core.net.MsgCenter;
import lc.client.ui.components.LComboBox;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.components.LTextField;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.menu.MainMenu;
import lc.client.util.ClientConstant;
import lc.client.util.FontSetting;

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
		super(jframe, title, dialogWidht, dialogHeight);
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
		MsgCenter.addCreateGameMsg(this.gameId.getValue(), TankFactory.getInstance().getUserTank().getName(), this.gameType.getSelectedValue(), this.playerNum.getSelectedValue());
		MainMenu.getInstance().setState(LMenuItem.IN_GAME);
	}

	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub

	}
	
	

}
