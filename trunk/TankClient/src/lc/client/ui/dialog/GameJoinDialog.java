package lc.client.ui.dialog;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lc.client.core.controller.GameController;
import lc.client.core.controller.PlayerController;
import lc.client.core.net.DataAcceptThread;
import lc.client.core.net.DataSendThread;
import lc.client.core.net.MsgCenter;
import lc.client.core.thread.OtherTankThread;
import lc.client.core.thread.PanelPaintThread;
import lc.client.core.thread.TimerThread;
import lc.client.core.thread.UserTankThread;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LMenuItem;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.frame.MainFrame;
import lc.client.ui.menu.MainMenu;
import lc.client.ui.panel.GamePanel;
import lc.client.ui.panel.HomePanel;
import lc.client.util.ClientConstant;
import lc.client.util.FontSetting;

public class GameJoinDialog extends LDialog implements MouseListener{
	private static final long serialVersionUID = 1L;
	private static GameJoinDialog gameJoinDialog;
	private static String TITLE="加入游戏";
	private static final int DIALOG_WIDTH=300;
	private static final int DIALOG_HEIGHT=250;
	private LOkCancelButton okAndCancel;
	
	private GameJoinDialog(JFrame jframe, String title, int dialogWidht,
			int dialogHeight) {
		super(jframe, title, dialogWidht, dialogHeight);
		okAndCancel = new LOkCancelButton();
		this.add(okAndCancel);
		DefaultTableModel tableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		tableModel.addColumn("名称");
		tableModel.addColumn("创建者");
		tableModel.addColumn("人数");
		tableModel.addRow(new String[]{"111","1/2","222"});
		JTable table=new JTable(tableModel);//放在JSCROLLPANEL里才能显示表头
		table.addMouseListener(this);
		table.getColumn("名称").setPreferredWidth(130);
		JScrollPane jsp=new JScrollPane(table);
		jsp.setBounds(10, 10, DIALOG_WIDTH-20, DIALOG_HEIGHT-90);
		jsp.setVisible(true);
		this.add(jsp);
		//table.set
		// TODO Auto-generated constructor stub
		FontSetting.setChildrenFont(this, new Font("微软雅黑",Font.PLAIN,12));
	}

	/**
	 * 获得加入游戏窗口s
	 * @return
	 */
	public static synchronized GameJoinDialog getInstance(){
		if(gameJoinDialog==null){
			gameJoinDialog=new GameJoinDialog(MainFrame.getInstance(), TITLE, DIALOG_WIDTH, DIALOG_HEIGHT);
		}
		return gameJoinDialog;
	}

	/**
	 * 加入游戏
	 */
	@Override
	public void onOkBtn() {
		// TODO Auto-generated method stub
		MsgCenter.addJoinGameMessage(null);//需改动
	}

	/**
	 * 打开时从服务器获得游戏房间列表
	 */
	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){
			MsgCenter.addJoinGameMessage(null);//需改动
			this.closeDialog();
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
