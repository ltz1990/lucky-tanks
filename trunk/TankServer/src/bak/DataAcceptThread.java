

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

import lc.server.log.Debug;

/**
 * 游戏房间线程
 * 
 * @author LUCKY
 * 
 */
public class DataAcceptThread implements Runnable {
	private Thread thread;
	private Selector selector;
	private int activeSockets=0;

	protected DataAcceptThread() {

	}

	/**
	 * 启动线程
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
		}
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			selector = Selector.open();
			while (true) {
				Set<SelectionKey> keys=selector.selectedKeys();//获得活动链接
				activeSockets=keys.size();
				if(activeSockets==0){
					continue;//如果活动链接数为0，则继续等待
				}
				for(SelectionKey key:keys){
					if(key.isReadable()){
						doReadEvent(key);
						continue;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doReadEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 注册通道到当前房间
	 * @param channel
	 */
	public void register(SocketChannel channel){
		try {
			channel.register(this.selector, SelectionKey.OP_READ);
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			Debug.debug("注册通道失败！");
		}
	}

}
