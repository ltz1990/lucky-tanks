package lc.client.core.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import lc.client.util.ClientConstant;

/**
 * ����������
 * @author LUCKY
 *
 */
public class NetConnection {
	private static SocketChannel client;

	/**
	 * ������
	 */
	public static void openConnect() {
		try {
			SocketAddress serverAddress = new InetSocketAddress(
					ClientConstant.SERVER_ADDRESS, ClientConstant.SERVER_PORT);
			client = SocketChannel.open(serverAddress);
			client.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
		} 
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

}
