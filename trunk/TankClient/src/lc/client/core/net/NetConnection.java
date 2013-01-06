package lc.client.core.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import lc.client.util.ClientConstant;

/**
 * 网络连接类
 * @author LUCKY
 *
 */
public class NetConnection {
	private static SocketChannel client;

	/**
	 * 打开连接
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
	 * 关闭连接
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
	 * 得到通道
	 * @return
	 */
	public static SocketChannel getSocketChannel(){
		return client;
	}

}
