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
 * ���ݷ����߳�
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
		msgList=Collections.synchronizedList(new LinkedList<String>());//����ͬ������
	}
	
	/**
	 * �õ����ݷ����̶߳���
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
		Debug.debug("���ݷ����߳̽���");
	}
	
	public State getState() {
		return thread.getState();
	}
	
	/**
	 * �̶�ƽ��
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
	 * �����Ϣ�����Ͷ���
	 * @param msg
	 */
	public void addMessage(String msg){
		msgList.add(msg);
	}
	

	/**
	 * ���Ͷ��󵽷�����
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
