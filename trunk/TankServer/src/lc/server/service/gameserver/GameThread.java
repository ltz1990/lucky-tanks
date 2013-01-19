package lc.server.service.gameserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lc.server.gamecomp.UserInfo;
import lc.server.log.LogUtil;

/**
 * ��Ϸ�����߳�
 * 
 * @author LUCKY
 * 
 */
public class GameThread implements Runnable {
	private Thread thread;
	private Selector selector;
	private MsgCenter msgCenter;
	private Object lock=new Object();//��������selector,ע����
	
	private int activeSockets=0;
	private ByteBuffer buffer;
	private static int BUFFER_SIZE=5120;
	
	private Map<Integer,UserInfo> players;

	protected GameThread() {
		msgCenter=new MsgCenter();
		players=new HashMap<Integer,UserInfo>();
		buffer=ByteBuffer.allocate(BUFFER_SIZE);//һ���µĻ����� POS=0��LIM=1024
		try {
			selector = Selector.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �����߳�
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
			while (true) {
				selector.select(1000);//���ȴ�2����һ����ѯ����ֹע��һֱ�ȴ�)
				Set<SelectionKey> keys=null;
				synchronized(lock){//��������ҪĿ���ǵ�SELECTOR����֮�󣬽�����ס�����ע��
					keys= selector.selectedKeys();// ��û����
				}
				activeSockets = keys.size();
				//LogUtil.logger.info("��Ϸ�߳���");
				if (activeSockets == 0) {
					continue;// ����������Ϊ0��������ȴ�
				}
				for (SelectionKey key : keys) {
					if (key.isReadable()) {
						doReadEvent(key);
						continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doReadEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel client=(SocketChannel)key.channel();//һ���ͻ���ͨ��
		try {
			int count=client.read(buffer);//��ͨ���ж��뻺����pos=N,lim=1024
			if(count==-1){
				LogUtil.logger.info("�˳�"+client.socket().getRemoteSocketAddress());
				client.close();
			}else if(buffer.position()==0){
				return;//���BUFFERΪ�գ��򷵻�
			}
			buffer.flip();//pos=0,lim=N	
			//System.out.println(buffer);
			msgCenter.msgProcess(buffer,client);
			//System.out.println(buffer);
			for(SelectionKey other:selector.selectedKeys()){//�ಥ key�����еģ�����cancel�� ��selectionKey�ǻ��				
				if(!other.isValid()||other==key||other.isAcceptable()){//����ǵ�ǰ�ͻ��˻�������Ƿ�����ͨ���򲻿ɶ�
					continue;
				}
				SocketChannel otherChannel=(SocketChannel)other.channel();
				otherChannel.write(buffer);
				//System.out.println(buffer);
				buffer.position(0);//����BUFFER�α�
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogUtil.logger.error("�ӿͻ���Channel���뻺���������쳣��"+e.getMessage(), e);
			try {
				client.close();
				//msgCenter.allTanks.re
				LogUtil.logger.info("�˳�"+client.socket().getRemoteSocketAddress());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				LogUtil.logger.error("�ͻ����˳�ʱ�����쳣", e1);
				e1.printStackTrace();
			}
		}finally{
			buffer.clear();
		}
	}
	
	/**
	 * ע��ͨ������ǰ����
	 * @param channel
	 */
	public void register(SocketChannel channel) {
		try {
			synchronized (lock) {				
				selector.wakeup();// ��Ҫ���ѣ���Ȼselector������ʱ���޷�ע��
				channel.register(this.selector, SelectionKey.OP_READ);
			}
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			LogUtil.logger.error("ע��ͨ��ʧ�ܣ�", e);
		}
	}

	/**
	 * ��Ҽ���
	 * @author LUCKY 2013-1-15
	 * @return
	 */
	public Map<Integer, UserInfo> getPlayers() {
		return players;
	}

}
