package lc.client.core.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

import lc.client.core.controller.GameController;
import lc.client.util.Debug;

/**
 * 数据接收线程
 * @author LUCKY
 *
 */
public class DataAcceptThread implements Runnable{
	private static DataAcceptThread acceptThread;
	private static final int BUFFER_SIZE=5120;
	private SocketChannel client;
	private Selector selector;
	private ByteBuffer buffer;
	
	private DataAcceptThread(){
		super();
	}
	
	/**
	 * 初始化数据接收线程
	 * @return
	 */
	public static DataAcceptThread getInstance(){
		if(acceptThread==null){
			acceptThread=new DataAcceptThread();
		}
		return acceptThread;
	}
	/**
	 * 启动线程
	 */
	public void start(){
		Thread thread=new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		initSocketChannel();
		buffer=ByteBuffer.allocate(BUFFER_SIZE);
		while (GameController.gameSwitch) {
			try {
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				if (keys.size() == 0) {
					continue;
				}
				for (SelectionKey key : keys) {
					if (key.isReadable()) {
						doReadableEvent(key);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (CancelledKeyException e){
				e.printStackTrace();
				break;
			} catch (ClosedSelectorException e){
				Debug.error("选择器关闭", e);
				break;
			}
		}
		threadFinished();
		Debug.debug("数据接收线程结束");
	}

	/**
	 * 线程结束
	 */
	public void threadFinished() {
		try {
			selector.close();
			buffer.clear();//清空缓冲区
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化通道和选择器
	 */
	private void initSocketChannel() {
		client=NetConnection.getSocketChannel();
		try {
			selector=Selector.open();
			client.register(selector, SelectionKey.OP_READ);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 读入事件
	 * @param key
	 */
	private void doReadableEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel server=(SocketChannel)key.channel();
		try {
			int count=server.read(buffer);
			if(count==-1){
				server.close();
			}else if(buffer.position()==0){
				return;//如果BUFFER为空，则返回
			}
			buffer.flip();
			MsgCenter.MsgProcess(Charset.forName("UTF-8").newDecoder().decode(buffer).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			buffer.clear();//清空BUFFER
		}
	}
}
