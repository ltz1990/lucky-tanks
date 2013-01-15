package lc.client.core.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import lc.client.environment.ClientConstant;
import lc.client.util.Debug;

/**
 * ����������
 * @author LUCKY
 *
 */
public class NetConnection {
	private static SocketChannel client;
	public static boolean isRun=true;

	/**
	 * ������
	 * @throws IOException 
	 */
	public static void openConnect() throws IOException {
		SocketAddress serverAddress = new InetSocketAddress(ClientConstant.SERVER_ADDRESS, ClientConstant.SERVER_PORT);
		client = SocketChannel.open(serverAddress);
		client.configureBlocking(false);
	}

	/**
	 * �ر�����
	 */
	public static void disConnect() {
		try {
			if(client!=null){
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �õ�ͨ��
	 * @return
	 */
	public static SocketChannel getSocketChannel(){
		return client;
	}
	
	/**
	 * �������������߳�
	 */
	public static void startNetThread() {
		NetConnection.isRun=true;
		DataSendThread sender = DataSendThread.getInstance();// ���ݷ����߳�
		DataAcceptThread accept = DataAcceptThread.getInstance();// ���ݽ����߳�
		sender.start();
		accept.start();
	}
	
	/**
	 * ֹͣ���������߳�
	 */
	public static void stopNetThread(){
		NetConnection.isRun=false;
		DataAcceptThread.getInstance().threadFinished();
	}

}
