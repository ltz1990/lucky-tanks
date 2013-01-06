

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
 * 服务端游戏主线程
 * 
 * 垃圾线程！拆！
 * @author LUCKY
 *
 */
public class GameServerThread implements Runnable {
	private Thread thread;
	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private int activeSockets;//活动链接数
	private static int BUFFER_SIZE=5120;
	private static int PORT=9999;
	ByteBuffer buffer=null;
	
	private MsgCenter msgCenter;//一个房间一个消息中心
		
	/**
	 * 构造方法，初始化数据
	 */
	public GameServerThread(){
		try {
			selector=Selector.open();//打开选择器
			serverSocketChannel=ServerSocketChannel.open();//打开服务器通道
			serverSocketChannel.configureBlocking(false);//非阻塞式
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册服务器通道为接受
			ServerSocket socket=serverSocketChannel.socket();//服务器对应的SOCKET
			socket.bind(new InetSocketAddress(PORT));//绑定端口
			msgCenter=new MsgCenter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.error("初始化服务器出现异常",e);
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
		Debug.debug("游戏线程启动");
		buffer=ByteBuffer.allocate(BUFFER_SIZE);//一个新的缓冲区 POS=0，LIM=1024
		while(true){
			try {
				selector.select();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Debug.error("选择器在调select()方法时出错", e);
				e.printStackTrace();
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
	 * 服务器读取通道事件
	 * 处理多播
	 * @param key
	 */
	private void doReadEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel client=(SocketChannel)key.channel();//一个客户端通道
		try {
			int count=client.read(buffer);//从通道中读入缓冲区pos=N,lim=1024
			if(count==-1){
				Debug.debug("退出"+client.socket().getRemoteSocketAddress());
				client.close();
			}else if(buffer.position()==0){
				return;//如果BUFFER为空，则返回
			}
			buffer.flip();//pos=0,lim=N	
			//System.out.println(buffer);
			msgCenter.msgProcess(buffer,client);
			//System.out.println(buffer);
			for(SelectionKey other:selector.selectedKeys()){//多播 key是所有的，包括cancel的 。selectionKey是活动的				
				if(!other.isValid()||other==key||other.isAcceptable()){//如果是当前客户端或者如果是服务器通道则不可读
					continue;
				}
				SocketChannel otherChannel=(SocketChannel)other.channel();
				otherChannel.write(buffer);
				//System.out.println(buffer);
				buffer.position(0);//重置BUFFER游标
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.error("从客户端Channel读入缓冲区出现异常。"+e.getMessage(), e);
			try {
				client.close();
				//msgCenter.allTanks.re
				Debug.debug("退出"+client.socket().getRemoteSocketAddress());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				Debug.error("客户端退出时出现异常", e1);
				e1.printStackTrace();
			}
		}finally{
			buffer.clear();
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
				clientChannel.register(selector, SelectionKey.OP_READ);
				Debug.debug("加入:"+clientChannel.socket().getRemoteSocketAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.error("服务器游戏线程通道在接受连接时出错", e);
			e.printStackTrace();
		}
	}
	
	private void doWriteEvent(SelectionKey key) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 获得活动链接数
	 * @return
	 */
	public int getActiveSockets(){
		return this.activeSockets;
	}
}
