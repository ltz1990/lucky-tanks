package lc.server.service.gameserver;

import java.awt.Point;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import lc.server.log.LogUtil;
import lc.server.tools.ServerConstant;

/**
 * �������Ϸ���߳�
 * 
 * �����̣߳���
 * @author LUCKY
 *
 */
public class ServerThread implements Runnable {
	
	private Thread thread;
	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private int activeSockets;//�������
	ByteBuffer buffer=null;
	
	private MsgCenter msgCenter;//һ������һ����Ϣ����
		
	/**
	 * ���췽������ʼ������
	 */
	public ServerThread(){
		try {
			selector=Selector.open();//��ѡ����
			serverSocketChannel=ServerSocketChannel.open();//�򿪷�����ͨ��
			serverSocketChannel.configureBlocking(false);//������ʽ
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//ע�������ͨ��Ϊ����
			ServerSocket socket=serverSocketChannel.socket();//��������Ӧ��SOCKET
			socket.bind(new InetSocketAddress(ServerConstant.SERVER_PORT+1));//�󶨶˿�
			msgCenter=new MsgCenter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogUtil.logger.error("��ʼ�������������쳣",e);
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
		LogUtil.logger.info("�������߳�����");
		buffer=ByteBuffer.allocate(ServerConstant.BUFFER_SIZE);//һ���µĻ����� POS=0��LIM=1024
		while(true){
			try {
				selector.select();
			} catch (IOException e) {
				LogUtil.logger.error("ѡ�����ڵ�select()����ʱ����", e);
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
				}
			}
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
				//ƥ��WEBSERVICE�������ģ����ڴ˿ͻ���ͨ������ϸ��Ϣ
				GameCtrlCenter.getInstance().searchAnotherConnInfo(clientChannel.socket().getRemoteSocketAddress().toString(), clientChannel);	
			}
		} catch (IOException e) {
			LogUtil.logger.error("��������Ϸ�߳�ͨ���ڽ�������ʱ����", e);
		}
	}
	
	/**
	 * ��û������
	 * @return
	 */
	public int getActiveSockets(){
		return this.activeSockets;
	}
}
