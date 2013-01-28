package lc.client.ui.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import lc.client.core.controller.GameController;
import lc.client.core.factory.TankFactory;
import lc.client.core.net.NetConnection;
import lc.client.environment.ClientConstant;
import lc.client.environment.RuntimeEnvironment;
import lc.client.environment.UserInfo;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.frame.MainFrame;
import lc.client.util.FontSetting;
import lc.client.webservice.RemoteServiceProxy;
import lc.client.webservice.wscode.GameHouse;
import lc.client.webservice.wscode.MsgEntry;

public class GameJoinDialog extends LDialog implements MouseListener{
	private static final long serialVersionUID = 1L;
	private static GameJoinDialog gameJoinDialog;
	private static String TITLE="加入游戏";
	private static final int DIALOG_WIDTH=300;
	private static final int DIALOG_HEIGHT=300;
	private JLabel tips;
	private LOkCancelButton okAndCancel;
	private DefaultTableModel tableModel;
	private JTable table;
	
	private GameJoinDialog(JFrame jframe, String title, int dialogWidht,
			int dialogHeight) {
		super(jframe, title, dialogWidht, dialogHeight);
		okAndCancel = new LOkCancelButton();
		this.add(okAndCancel);
		JScrollPane jsp = initTable();
		/** 提示信息 **/
		tips=new JLabel("正在刷新房间列表");
		tips.setBounds(0, 1, DIALOG_WIDTH-10, 30);
		tips.setHorizontalAlignment(JLabel.CENTER);
		/** 刷新房间列表 **/
		JButton refresh=new JButton("刷新");
		refresh.setBounds(10,1,ClientConstant.BTN_WIDTH, ClientConstant.BTN_HEIGHT);
		refresh.setActionCommand("REFRESH");
		refresh.addActionListener(this);
		this.add(refresh);
		this.add(jsp);
		this.add(tips);
		FontSetting.setChildrenFont(this, new Font("微软雅黑",Font.PLAIN,12));
		tips.setForeground(Color.green);
	}

	/**
	 * 初始化房间列表
	 * @author LUCKY 2013-1-19
	 * @return
	 */
	private JScrollPane initTable() {
		tableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		tableModel.addColumn("id");
		tableModel.addColumn("房间名称");
		tableModel.addColumn("类型");
		tableModel.addColumn("创建者");
		tableModel.addColumn("人数");
		table=new JTable(tableModel);//放在JSCROLLPANEL里才能显示表头
		table.addMouseListener(this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置只能单选
		hiddenColumn(table,0); 
		JScrollPane jsp=new JScrollPane(table);
		jsp.setBounds(10, 30, DIALOG_WIDTH-30, DIALOG_HEIGHT-110);
		jsp.setVisible(true);
		return jsp;
	}

	/**
	 * 隐藏列
	 * @author LUCKY 2013-1-19
	 * @param table
	 * @param columnIndex
	 */
	private void hiddenColumn(JTable table,int columnIndex) {
		table.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
		table.getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
        table.getColumnModel().getColumn(columnIndex).setWidth(0);
        table.getColumnModel().getColumn(columnIndex).setMinWidth(0);
        table.getTableHeader().getColumnModel().getColumn(columnIndex).setMaxWidth(0); 
        table.getTableHeader().getColumnModel().getColumn(columnIndex).setMaxWidth(0);
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

	
	@Override
	protected void btnElse(String actionCommand) {
		// TODO Auto-generated method stub
		if("REFRESH".equals(actionCommand)){//刷新
			try {
				refreshHouseList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onOkBtn() {
		// TODO Auto-generated method stub
		joinSelectedHouse();
	}

	/**
	 * 加入游戏
	 * @author LUCKY 2013-1-19
	 */
	private void joinSelectedHouse() {
		try {
			NetConnection.openConnect();
			UserInfo userInfo = RuntimeEnvironment.getUserInfo();
			int selIndex=table.getSelectedRow();
			String houseId=(String) tableModel.getValueAt(selIndex, 0);
			userInfo.setHouseId(houseId);
			String address=NetConnection.getSocketChannel().socket().getLocalSocketAddress().toString();
			MsgEntry msg=RemoteServiceProxy.getInstance().joinGame(userInfo, address);
			if(msg.isResult()){//创建坦克
				TankFactory.getInstance().createTank(userInfo.getName(),userInfo.getUserId(), ClientConstant.USER, new Point(100,100));
				List<Object> users=msg.getObjectList();
				for(Object obj:users){
					UserInfo user=(UserInfo)obj;
					if(userInfo.getUserId()==user.getUserId()) continue;//不创建自己
					TankFactory.getInstance().createTank(user.getName(),user.getUserId(), ClientConstant.OTHER, new Point(50,50));
				}
				GameController.startGame();
				NetConnection.startNetThread();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 打开时从服务器获得游戏房间列表
	 */
	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub
		try {
			refreshHouseList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 刷新房间列表
	 * @author LUCKY 2013-1-19
	 * @throws Exception
	 */
	private void refreshHouseList() throws Exception {
		tips.setText("正在刷新房间列表...");
		for(int i=0,n=tableModel.getRowCount();i<n;i++){
			tableModel.removeRow(0);
		}
		List<GameHouse> list=RemoteServiceProxy.getInstance().getGameHouses();
		for(GameHouse house:list){
			String[] data=new String[]{house.getHouseId(),
					house.getName(),
					ClientConstant.GAMETYPE_NAME.get(house.getGameType()),
					house.getCreator().getName(),
					String.valueOf(house.getPlayerCount())};
			tableModel.addRow(data);
		}
		tips.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){
			joinSelectedHouse();
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
