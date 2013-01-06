package lc.client.ui.dialog;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTextField;

import lc.client.ui.components.LDialog;
import lc.client.ui.components.LOkCancelButton;
import lc.client.ui.components.LTextField;
import lc.client.ui.frame.MainFrame;
import lc.client.util.ClientConstant;
import lc.client.util.FontSetting;

/**
 * ��������
 * @author LUCKY
 *
 */
public class KeySettingDialog extends LDialog implements KeyListener{
	private static final long serialVersionUID = 1L;
	private static KeySettingDialog settingDialog;
	private static final String TITLE="��������";
	private LTextField keyUp;
	private LTextField keyDown;
	private LTextField keyLeft;
	private LTextField keyRight;
	private LTextField keyShot;

	private KeySettingDialog(JFrame jframe) {
		super(jframe,TITLE,300,260);
		this.add(new LOkCancelButton());
		keyUp=(LTextField) this.add(new LTextField("����", -10, 30));
		keyDown=(LTextField) this.add(new LTextField("����", -10, 60));
		keyLeft=(LTextField) this.add(new LTextField("����", -10, 90));
		keyRight=(LTextField) this.add(new LTextField("����", -10, 120));
		keyShot=(LTextField) this.add(new LTextField("��", -10, 150));
		keyUp.addKeyListener(this);
		keyUp.setEditable(false);
		keyDown.addKeyListener(this);
		keyDown.setEditable(false);
		keyLeft.addKeyListener(this);
		keyLeft.setEditable(false);
		keyRight.addKeyListener(this);
		keyRight.setEditable(false);
		keyShot.addKeyListener(this);
		keyShot.setEditable(false);
		FontSetting.setChildrenFont(this,new Font("΢���ź�", Font.PLAIN, 12));
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * �õ��������ô���
	 * @return
	 */
	public static synchronized KeySettingDialog getInstance(){
		MainFrame frame=MainFrame.getInstance();
		if(frame==null){
			throw new NullPointerException();
		}
		if(settingDialog==null){
			settingDialog=new KeySettingDialog(frame);
		}
		return settingDialog;
	}

	/**
	 * ��������ֵ
	 */
	@Override
	public void onOkBtn() {
		// TODO Auto-generated method stub
		Properties prop=ClientConstant.getProperties();
		prop.put("KEY_UP", keyUp.getHiddenValue());
		prop.put("KEY_DOWN", keyDown.getHiddenValue());
		prop.put("KEY_RIGHT", keyRight.getHiddenValue());
		prop.put("KEY_LEFT", keyLeft.getHiddenValue());
		prop.put("KEY_SHOT", keyShot.getHiddenValue());
		ClientConstant.saveProperties(prop);
		ClientConstant.loadKeySetting();//���ؼ�������
	}

	/**
	 * ��������ʱ��������Ͷ�Ӧ�İ������������
	 */
	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub
		Properties prop=ClientConstant.getProperties();
		keyUp.setHiddenValue(prop.get("KEY_UP"));
		keyUp.setValue(KeyEvent.getKeyText(Integer.valueOf((String)prop.get("KEY_UP"))));
		keyDown.setHiddenValue(prop.get("KEY_DOWN"));
		keyDown.setValue(KeyEvent.getKeyText(Integer.valueOf((String)prop.get("KEY_DOWN"))));
		keyLeft.setHiddenValue(prop.get("KEY_LEFT"));
		keyLeft.setValue(KeyEvent.getKeyText(Integer.valueOf((String)prop.get("KEY_LEFT"))));
		keyRight.setHiddenValue(prop.get("KEY_RIGHT"));
		keyRight.setValue(KeyEvent.getKeyText(Integer.valueOf((String)prop.get("KEY_RIGHT"))));
		keyShot.setHiddenValue(prop.get("KEY_SHOT"));
		keyShot.setValue(KeyEvent.getKeyText(Integer.valueOf((String)prop.get("KEY_SHOT"))));
		keyUp.getJTextField().requestFocus();//�����ڵ�һ�������
	}
	
	/**
	 * ����̧���ʱ����д�����룬������������������ֵ
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		JTextField fComp=(JTextField)this.getFocusOwner();
		fComp.setText(KeyEvent.getKeyText(e.getKeyCode()));//������
		//���뽹�����,���������������ֵ���л�����һ����������
		if(fComp==keyUp.getJTextField()){
			keyUp.setHiddenValue((String)Integer.toString(e.getKeyCode()));
			keyDown.getJTextField().requestFocus();
		}else if(fComp==keyDown.getJTextField()){
			keyDown.setHiddenValue((String)Integer.toString(e.getKeyCode()));
			keyLeft.getJTextField().requestFocus();
		}else if(fComp==keyLeft.getJTextField()){
			keyLeft.setHiddenValue((String)Integer.toString(e.getKeyCode()));
			keyRight.getJTextField().requestFocus();
		}else if(fComp==keyRight.getJTextField()){
			keyRight.setHiddenValue((String)Integer.toString(e.getKeyCode()));
			keyShot.getJTextField().requestFocus();
		}else{
			keyShot.setHiddenValue((String)Integer.toString(e.getKeyCode()));
			keyUp.getJTextField().requestFocus();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
