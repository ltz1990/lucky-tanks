package lc.server.tools;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;

import lc.server.core.thread.ServerThread;

public class ServerRunTimeEnvironment {
	
	/**
	 * ��û�߳���
	 */
	public static void getActiveGameThread(){
		Thread.activeCount();
	}
}
