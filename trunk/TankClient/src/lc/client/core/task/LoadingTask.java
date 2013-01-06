package lc.client.core.task;

/**
 * ����һ��Loading�Ի���ʱ�����Ӧ�������¼�
 * @author LUCKY
 *
 */
public interface LoadingTask {
	
	/**
	 * ������
	 * @throws Exception
	 */
	public void run() throws Exception;
	
	/**
	 * ����ʱ����
	 * @return
	 */
	public String getLoadingMsg();
	
	/**
	 * ����ɹ�����
	 * @return
	 */
	public String getSuccessResultMsg();
	
	/**
	 * ����ʧ������
	 * @return
	 */
	public String getFailedResultMsg();
	
	
}
