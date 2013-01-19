package lc.client.ui.dialog;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lc.client.core.net.MsgCenter;
import lc.client.environment.ClientConstant;
import lc.client.ui.components.LDialog;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.frame.MainFrame;
import lc.client.util.FontSetting;
import lc.client.webservice.RemoteServiceProxy;
import lc.client.webservice.wscode.GameHouse;

public class GameJoinDialog extends LDialog implements MouseListener{
	private static final long serialVersionUID = 1L;
	private static GameJoinDialog gameJoinDialog;
	private static String TITLE="������Ϸ";
	private static final int DIALOG_WIDTH=300;
	private static final int DIALOG_HEIGHT=250;
	private LOkCancelButton okAndCancel;
	private DefaultTableModel tableModel;
	
	private GameJoinDialog(JFrame jframe, String title, int dialogWidht,
			int dialogHeight) {
		super(jframe, title, dialogWidht, dialogHeight);
		okAndCancel = new LOkCancelButton();
		this.add(okAndCancel);
		tableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		tableModel.addColumn("id");
		tableModel.addColumn("��������");
		tableModel.addColumn("����");
		tableModel.addColumn("������");
		tableModel.addColumn("����");
		//tableModel.addRow(new String[]{"111","1/2","222"});
		JTable table=new JTable(tableModel);//����JSCROLLPANEL�������ʾ��ͷ
		table.addMouseListener(this);
		hiddenColumn(table,0); 
		JScrollPane jsp=new JScrollPane(table);
		jsp.setBounds(10, 10, DIALOG_WIDTH-20, DIALOG_HEIGHT-90);
		jsp.setVisible(true);
		this.add(jsp);
		//table.set
		// TODO Auto-generated constructor stub
		FontSetting.setChildrenFont(this, new Font("΢���ź�",Font.PLAIN,12));
	}

	/**
	 * ������
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
	 * ��ü�����Ϸ����s
	 * @return
	 */
	public static synchronized GameJoinDialog getInstance(){
		if(gameJoinDialog==null){
			gameJoinDialog=new GameJoinDialog(MainFrame.getInstance(), TITLE, DIALOG_WIDTH, DIALOG_HEIGHT);
		}
		return gameJoinDialog;
	}

	/**
	 * ������Ϸ
	 */
	@Override
	public void onOkBtn() {
		// TODO Auto-generated method stub
		MsgCenter.addJoinGameMessage(null);//��Ķ�
	}

	/**
	 * ��ʱ�ӷ����������Ϸ�����б�
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
	 * ˢ�·����б�
	 * @author LUCKY 2013-1-19
	 * @throws Exception
	 */
	private void refreshHouseList() throws Exception {
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
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){
			MsgCenter.addJoinGameMessage(null);//��Ķ�
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
