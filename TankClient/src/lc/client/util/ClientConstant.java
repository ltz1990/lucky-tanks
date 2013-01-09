package lc.client.util;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 常量类
 * @author LUCKY
 *
 */
public class ClientConstant {
	public static String TITLE="坦克大战";
	public static int PANEL_WIDTH=683;//游戏容器宽度
	public static int PANEL_HEIGHT=384;//游戏容器高度
	public static int FRAME_WIDTH=PANEL_WIDTH+6;//窗口宽度
	public static int FRAME_HEIGHT=PANEL_HEIGHT+54;//窗口高度
	
	public static final int USER=0;//用户坦克
	public static final int OTHER=1;//其它人坦克
	
	public static int MAIN_SPEED=10;//游戏速度
	
	public static int KEY_UP=KeyEvent.VK_UP;//↑
	public static int KEY_DOWN=KeyEvent.VK_DOWN;//↓
	public static int KEY_LEFT=KeyEvent.VK_LEFT;//←
	public static int KEY_RIGHT=KeyEvent.VK_RIGHT;//→
	public static int KEY_SHOT=KeyEvent.VK_Z;//发射
	public static int KEY_STOP=0;
	
	public static int TankSpeed=1;//坦克速度
	public static int TankWidth=15;//坦克宽度/2
	public static int TankHeight=15;//坦克高度/2
	
	public static int BULLET_SPEED=5;//子弹速度
	public static int BULLET_MAXSTEP=100;//子弹最大步数
	public static int BULLET_RAD=6;//炮弹半径
	public static int BULLET_MAX_AMOUNT=3;//一个坦克拥有的炮弹数量
	
	public static String SERVER_ADDRESS="192.168.52.1";
	public static int SERVER_PORT=9999;
	
	public static int BTN_WIDTH=55;//按钮
	public static int BTN_HEIGHT=25;
	public static int LABLE_WIDTH=90;//标签
	public static int LABLE_HEIGHT=25;
	public static int TEXTFIELD_WIDTH=150;//文本框
	public static int TEXTFIELD_HEIGHT=25;
	
	private static String FOLDER_PATH=new File(System.getProperty("java.class.path")).getParent();//文件夹路径
	private static String PROP_PATH=null;//配置文件路径	
	private static Properties prop; //配置文件
	
	public static final int GAMETYPE_FLAG=0;//抢旗
	public static final int GAMETYPE_FIGHT=1;//对战
	
	/**
	 * 加载系统变量
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
	 * 加载服务器设置
	 */
	public static void loadServerConfig() {
		SERVER_ADDRESS=(String)prop.get("SERVER_ADDRESS");
		SERVER_PORT=Integer.parseInt((String)prop.get("SERVER_PORT"));
	}

	/**
	 * 重载键盘设置
	 */
	public static void loadKeySetting() {
		KEY_UP=Integer.parseInt((String)prop.get("KEY_UP"));
		KEY_DOWN=Integer.parseInt((String)prop.get("KEY_DOWN"));
		KEY_LEFT=Integer.parseInt((String)prop.get("KEY_LEFT"));
		KEY_RIGHT=Integer.parseInt((String)prop.get("KEY_RIGHT"));
		KEY_SHOT=Integer.parseInt((String)prop.get("KEY_SHOT"));
	}
	
	/**
	 * 得到配置文件
	 * @return
	 */
	public static Properties getProperties(){
		return prop;
	}
	
	/**
	 * 保存配置
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
