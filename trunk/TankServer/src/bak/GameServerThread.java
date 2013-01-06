

import java.awt.Point;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import lc.server.log.Debug;

/**
 * �������Ϸ���߳�
 * 
 * �����̣߳���
 * @author LUCKY
 *
 */
public class GameServerThread implements Runnable {
	private Thread thread;
	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private int activeSockets;//�������
	private static int BUFFER_SIZE=5120;
	private static int PORT=9999;
	ByteBuffer buffer=null;
	
	private MsgCenter msgCenter;//һ������һ����Ϣ����
		
	/**
	 * ���췽������ʼ������
	 */
	public GameServerThread(){
		try {
			selector=Selector.open();//��ѡ����
			serverSocketChannel=ServerSocketChannel.open();//�򿪷�����ͨ��
			serverSocketChannel.configureBlocking(false);//������ʽ
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//ע�������ͨ��Ϊ����
			ServerSocket socket=serverSocketChannel.socket();//��������Ӧ��SOCKET
			socket.bind(new InetSocketAddress(PORT));//�󶨶˿�
			msgCenter=new MsgCenter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.error("��ʼ�������������쳣",e);
			e.printStackTrace();
		}
	}
	
	/**
	 * �߳�����
	 */
	public void start(){
		if(thread==null){
			thread=new Thread(this);
		}
		thread.start();
	}
	
	/**
	 * ��Ϸ�߳�
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Debug.debug("��Ϸ�߳�����");
		buffer=ByteBuffer.allocate(BUFFER_SIZE);//һ���µĻ����� POS=0��LIM=1024
		while(true){
			try {
				selector.select();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Debug.error("ѡ�����ڵ�select()����ʱ����", e);
				e.printStackTrace();
			}
			Set<SelectionKey> keys=selector.selectedKeys();//��û����
			activeSockets=keys.size();
			if(activeSockets==0){
				continue;//����������Ϊ0��������ȴ�
			}
			for(SelectionKey key:keys){
				if(key.isAcceptable()){
					doAcceptEvent(key);
					continue;
				}else if(key.isReadable()){
					doReadEvent(key);
					continue;
				}else if(key.isWritable()){
					doWriteEvent(key);
					continue;
				}
			}
		}
	}

	/**
	 * ��������ȡͨ���¼�
	 * ����ಥ
	 * @param key
	 */
	private void doReadEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel client=(SocketChannel)key.channel();//һ���ͻ���ͨ��
		try {
			int count=client.read(buffer);//��ͨ���ж��뻺����pos=N,lim=1024
			if(count==-1){
				Debug.debug("�˳�"+client.socket().getRemoteSocketAddress());
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
			Debug.error("�ӿͻ���Channel���뻺���������쳣��"+e.getMessage(), e);
			try {
				client.close();
				//msgCenter.allTanks.re
				Debug.debug("�˳�"+client.socket().getRemoteSocketAddress());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				Debug.error("�ͻ����˳�ʱ�����쳣", e1);
				e1.printStackTrace();
			}
		}finally{
			buffer.clear();
		}
	}

	/**
	 * ������ͨ�������¼�
	 * @param key
	 */
	private void doAcceptEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		try {
			SocketChannel clientChannel=serverSocketChannel.accept();
			if(clientChannel!=null){
				clientChannel.configureBlocking(false);
				clientChannel.register(selector, SelectionKey.OP_READ);
				Debug.debug("����:"+clientChannel.socket().getRemoteSocketAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.error("��������Ϸ�߳�ͨ���ڽ�������ʱ����", e);
			e.printStackTrace();
		}
	}
	
	private void doWriteEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ��û������
	 * @return
	 */
	public int getActiveSockets(){
		return this.activeSockets;
	}
}
