package lc.server.start;

import lc.server.core.thread.ServerThread;
import lc.server.tools.ServerRunTimeEnvironment;

public class ServerStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerThread thread=new ServerThread();
		thread.start();
		ServerRunTimeEnvironment.getActiveGameThread();
	}

}
