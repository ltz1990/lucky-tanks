package lc.client.core.task;

import java.util.concurrent.Callable;

import lc.client.webservice.wscode.MsgEntry;

/**
 * ����һ��Loading�Ի���ʱ�����Ӧ�������¼�
 * @author LUCKY
 */
public interface LoadingTask extends Callable<MsgEntry>{
	
	/**
	 * ����ʱ����
	 * @return
	 */
	public String getLoadingMsg();
	
}
