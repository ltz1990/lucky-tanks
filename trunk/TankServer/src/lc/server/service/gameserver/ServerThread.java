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
 * 服务端游戏主线程
 * 
 * 垃圾线程！拆！
 * @author LUCKY
 *
 */
public class ServerThread implements Runnable {
	
	private Thread thread;
	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private int activeSockets;//活动链接数
	ByteBuffer buffer=null;
	
	private MsgCenter msgCenter;//一个房间一个消息中心
		
	/**
	 * 构造方法，初始化数据
	 */
	public ServerThread(){
		try {
			selector=Selector.open();//打开选择器
			serverSocketChannel=ServerSocketChannel.open();//打开服务器通道
			serverSocketChannel.configureBlocking(false);//非阻塞式
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册服务器通道为接受
			ServerSocket socket=serverSocketChannel.socket();//服务器对应的SOCKET
			socket.bind(new InetSocketAddress(ServerConstant.SERVER_PORT+1));//绑定端口
			msgCenter=new MsgCenter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogUtil.logger.error("初始化服务器出现异常",e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 线程启动
	 */
	public void start(){
		if(thread==null){
			thread=new Thread(this);
		}
		thread.start();
	}
	
	/**
	 * 游戏线程
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		LogUtil.logger.info("服务主线程启动");
		buffer=ByteBuffer.allocate(ServerConstant.BUFFER_SIZE);//一个新的缓冲区 POS=0，LIM=1024
		while(true){
			try {
				selector.select();
			} catch (IOException e) {
				LogUtil.logger.error("选择器在调select()方法时出错", e);
			}
			Set<SelectionKey> keys=selector.selectedKeys();//获得活动链接
			activeSockets=keys.size();
			if(activeSockets==0){
				continue;//如果活动链接数为0，则继续等待
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
	 * 服务器通道接受事件
	 * @param key
	 */
	private void doAcceptEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		try {
			SocketChannel clientChannel=serverSocketChannel.accept();
			if(clientChannel!=null){
				clientChannel.configureBlocking(false);
				//匹配WEBSERVICE发送来的，关于此客户端通道的详细信息
				GameCtrlCenter.getInstance().searchAnotherConnInfo(clientChannel.socket().getRemoteSocketAddress().toString(), clientChannel);	
			}
		} catch (IOException e) {
			LogUtil.logger.error("服务器游戏线程通道在接受连接时出错", e);
		}
	}
	
	/**
	 * 获得活动链接数
	 * @return
	 */
	public int getActiveSockets(){
		return this.activeSockets;
	}
}
