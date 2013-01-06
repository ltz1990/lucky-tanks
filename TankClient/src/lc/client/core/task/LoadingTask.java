package lc.client.core.task;

/**
 * 启动一个Loading对话框时，其对应的任务事件
 * @author LUCKY
 *
 */
public interface LoadingTask {
	
	/**
	 * 主任务
	 * @throws Exception
	 */
	public void run() throws Exception;
	
	/**
	 * 载入时文字
	 * @return
	 */
	public String getLoadingMsg();
	
	/**
	 * 载入成功文字
	 * @return
	 */
	public String getSuccessResultMsg();
	
	/**
	 * 载入失败文字
	 * @return
	 */
	public String getFailedResultMsg();
	
	
}
