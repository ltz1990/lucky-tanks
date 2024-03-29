package lc.server.core.thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

import lc.server.log.Debug;

/**
 * 游戏房间线程
 * 
 * @author LUCKY
 * 
 */
public class GameThread implements Runnable {
	private Thread thread;
	private Selector selector;
	private MsgCenter msgCenter;
	
	private int activeSockets=0;
	private ByteBuffer buffer;
	private static int BUFFER_SIZE=5120;

	protected GameThread() {
		msgCenter=new MsgCenter();
	}

	/**
	 * 启动线程
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
			buffer=ByteBuffer.allocate(BUFFER_SIZE);//一个新的缓冲区 POS=0，LIM=1024
			selector = Selector.open();
			while (true) {
				Set<SelectionKey> keys=selector.selectedKeys();//获得活动链接
				activeSockets=keys.size();
				if(activeSockets==0){
					continue;//如果活动链接数为0，则继续等待
				}
				for(SelectionKey key:keys){
					if(key.isReadable()){
						doReadEvent(key);
						continue;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	 * 注册通道到当前房间
	 * @param channel
	 */
	public void register(SocketChannel channel){
		try {
			channel.register(this.selector, SelectionKey.OP_READ);
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			Debug.error("注册通道失败！",e);
		}
	}

}
