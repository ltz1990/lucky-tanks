package lc.client.core.net;

import java.io.IOException;
import java.lang.Thread.State;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lc.client.core.controller.GameController;
import lc.client.util.ClientConstant;
import lc.client.util.Debug;
/**
 * 数据发送线程
 * @author LUCKY
 *
 */
public class DataSendThread implements Runnable{
	private static DataSendThread dataSendThread;
	private SocketChannel client;
	private Thread thread;
	private List<String> msgList;

	private DataSendThread(){
		super();
		msgList=Collections.synchronizedList(new LinkedList<String>());//创建同步集合
	}
	
	/**
	 * 得到数据发送线程对象
	 * @param conn
	 * @return
	 */
	public static synchronized DataSendThread getInstance(){
		if(dataSendThread==null){
			dataSendThread=new DataSendThread();
		}
		return dataSendThread;
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		client=NetConnection.getSocketChannel();
		while(NetConnection.isRun){
			int size = this.msgList.size();
			if(0<size){
				sendMessage(this.msgList.get(0));
				this.msgList.remove(0);
			}
			if(size<5){
				this.pause();
			}
		}
		msgList.clear();
		Debug.debug("数据发送线程结束");
	}
	
	public State getState() {
		return thread.getState();
	}
	
	/**
	 * 固定平率
	 */
	private void pause() {
		synchronized (this) {
			try {
				Thread.sleep(ClientConstant.MAIN_SPEED >> 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 添加消息到发送队列
	 * @param msg
	 */
	public void addMessage(String msg){
		msgList.add(msg);
	}
	

	/**
	 * 发送对象到服务器
	 * @param object
	 */
	private void sendMessage(String msg) {
		try {
		ByteBuffer buffer=ByteBuffer.wrap(msg.getBytes("UTF-8"));
		client.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
