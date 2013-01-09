package lc.client.util;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ������
 * @author LUCKY
 *
 */
public class ClientConstant {
	public static String TITLE="̹�˴�ս";
	public static int PANEL_WIDTH=683;//��Ϸ�������
	public static int PANEL_HEIGHT=384;//��Ϸ�����߶�
	public static int FRAME_WIDTH=PANEL_WIDTH+6;//���ڿ��
	public static int FRAME_HEIGHT=PANEL_HEIGHT+54;//���ڸ߶�
	
	public static final int USER=0;//�û�̹��
	public static final int OTHER=1;//������̹��
	
	public static int MAIN_SPEED=10;//��Ϸ�ٶ�
	
	public static int KEY_UP=KeyEvent.VK_UP;//��
	public static int KEY_DOWN=KeyEvent.VK_DOWN;//��
	public static int KEY_LEFT=KeyEvent.VK_LEFT;//��
	public static int KEY_RIGHT=KeyEvent.VK_RIGHT;//��
	public static int KEY_SHOT=KeyEvent.VK_Z;//����
	public static int KEY_STOP=0;
	
	public static int TankSpeed=1;//̹���ٶ�
	public static int TankWidth=15;//̹�˿��/2
	public static int TankHeight=15;//̹�˸߶�/2
	
	public static int BULLET_SPEED=5;//�ӵ��ٶ�
	public static int BULLET_MAXSTEP=100;//�ӵ������
	public static int BULLET_RAD=6;//�ڵ��뾶
	public static int BULLET_MAX_AMOUNT=3;//һ��̹��ӵ�е��ڵ�����
	
	public static String SERVER_ADDRESS="192.168.52.1";
	public static int SERVER_PORT=9999;
	
	public static int BTN_WIDTH=55;//��ť
	public static int BTN_HEIGHT=25;
	public static int LABLE_WIDTH=90;//��ǩ
	public static int LABLE_HEIGHT=25;
	public static int TEXTFIELD_WIDTH=150;//�ı���
	public static int TEXTFIELD_HEIGHT=25;
	
	private static String FOLDER_PATH=new File(System.getProperty("java.class.path")).getParent();//�ļ���·��
	private static String PROP_PATH=null;//�����ļ�·��	
	private static Properties prop; //�����ļ�
	
	public static final int GAMETYPE_FLAG=0;//����
	public static final int GAMETYPE_FIGHT=1;//��ս
	
	/**
	 * ����ϵͳ����
	 */
	public static void loadProperties(){
		PROP_PATH=FOLDER_PATH+"\\config.properties";
		try {
			if(prop==null){
				prop=new Properties();
			}
			prop.load(new FileInputStream(PROP_PATH));
			loadServerConfig();
			loadKeySetting();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * ���ط���������
	 */
	public static void loadServerConfig() {
		SERVER_ADDRESS=(String)prop.get("SERVER_ADDRESS");
		SERVER_PORT=Integer.parseInt((String)prop.get("SERVER_PORT"));
	}

	/**
	 * ���ؼ�������
	 */
	public static void loadKeySetting() {
		KEY_UP=Integer.parseInt((String)prop.get("KEY_UP"));
		KEY_DOWN=Integer.parseInt((String)prop.get("KEY_DOWN"));
		KEY_LEFT=Integer.parseInt((String)prop.get("KEY_LEFT"));
		KEY_RIGHT=Integer.parseInt((String)prop.get("KEY_RIGHT"));
		KEY_SHOT=Integer.parseInt((String)prop.get("KEY_SHOT"));
	}
	
	/**
	 * �õ������ļ�
	 * @return
	 */
	public static Properties getProperties(){
		return prop;
	}
	
	/**
	 * ��������
	 * @param prop
	 */
	public static void saveProperties(Properties prop){
		try {
			prop.store(new FileOutputStream(ClientConstant.PROP_PATH), "@author LUCKY");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
