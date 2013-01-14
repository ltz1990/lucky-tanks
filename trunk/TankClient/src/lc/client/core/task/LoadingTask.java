package lc.client.core.task;

import java.util.concurrent.Callable;

import lc.client.webservice.wscode.MsgEntry;

/**
 * 启动一个Loading对话框时，其对应的任务事件
 * @author LUCKY
 */
public interface LoadingTask extends Callable<MsgEntry>{
	
	/**
	 * 载入时文字
	 * @return
	 */
	public String getLoadingMsg();
	
}
