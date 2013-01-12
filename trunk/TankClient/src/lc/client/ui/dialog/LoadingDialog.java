package lc.client.ui.dialog;

import java.awt.Font;
import java.lang.Thread.State;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import lc.client.core.task.LoadingTask;
import lc.client.start.ClientStart;
import lc.client.ui.components.LDialog;
import lc.client.ui.frame.MainFrame;
import lc.client.util.ClientConstant;
import lc.client.util.Debug;
import lc.client.util.FontSetting;
import lc.client.webservice.wscode.MsgEntry;

/**
 * Loading��ʾ��
 * ����������л��ƣ�ÿ��չʾLOADING��ʱ����һ��������֮��Ӧ
 * @author LUCKY
 *
 */
public class LoadingDialog extends LDialog {
	private static LoadingDialog loadingDialog;
	private JLabel text;
	private List<LoadingTask> taskList;//�����б�
	private RunLoadingTask loadingTask;//�����߳�
	MsgEntry loadingResult; //ִ�н��
	private static final long TIMEOUT=10;//��ʱʱ�� 10S
	/**
	 * Loading����
	 * @return
	 */
	public static synchronized LoadingDialog getInstance(){
		if(loadingDialog==null){
			loadingDialog=new LoadingDialog();
		}
		return loadingDialog;
	}

	private LoadingDialog() {
		super(MainFrame.getInstance(), "Loading", 200, 100);
		/**��ʼ������**/
		text=new JLabel();
		int labelWidth = ClientConstant.LABLE_WIDTH;
		int labelHeight = ClientConstant.LABLE_HEIGHT;
		text.setBounds((this.getWidth()-labelWidth>>1)-15,(this.getHeight()-labelHeight>>1)-19, labelWidth, labelHeight+10);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
		text.setIcon(new ImageIcon(ClientStart.class.getResource("/images/loading.gif")));
		FontSetting.setChildrenFont(this,new Font("΢���ź�", Font.PLAIN, 12));
		/**��ʼ�������̡߳�����**/
		taskList=new LinkedList<LoadingTask>();
		loadingTask = new RunLoadingTask();
		loadingTask.start();
	}

	@Override
	public void onOkBtn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPopUp() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ��������򣬶�Ӧ����
	 * @param task
	 * @return ִ���Ƿ�ɹ�
	 */
	public MsgEntry popUpLoadingDialog(LoadingTask task){
		if(!this.isShowing()){
			text.setText(task.getLoadingMsg());
			taskList.add(task);
			//�ȴ�ֱ����һ������ִ����ɣ������߳�Ϊwaitingʱ�ż���
			while(!Thread.State.WAITING.equals(loadingTask.getState())){
				try {				
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}		
			synchronized(loadingTask){
				loadingTask.notify();//�������߳������ɻ�
			}
			super.popUp(); //����ģ̬��ֹͣ
			return loadingResult;
		}
		return null;
	}
	
	/**
	 * ��ִ�������б�
	 * @return
	 */
	List<LoadingTask> getTaskList(){
		return taskList;
	}
	
	/**
	 * Loading�Ի�����Ե����������߳�
	 * �������ִ�гɹ����򵯳��ɹ���Ϣ
	 * �������ִ��ʧ�ܣ��򵯳�ʧ����Ϣ�����ص�֮ǰҳ��
	 * 
	 * �������߳��д򿪴��ڣ���Ӱ�쵽ģ̬
	 * @author LUCKY
	 */
	private class RunLoadingTask implements Runnable{
		private Thread thread;
		private ExecutorService exec;
		public RunLoadingTask(){
			exec=Executors.newSingleThreadExecutor();//������Callable
		}
		public void start(){
			if(thread==null){
				thread=new Thread(this);
			}
			thread.start();
		}
		public void run() {
			while(true){
				try {
					while(getTaskList().size()>0){
						/**
						 * ������̲߳��ǵȴ�״̬��˵����ǰ���ڻ�û�б�����Ϊģ̬
						 * ����˵����ǰloading�Ի����Ѿ�����ģ̬���Ѿ�������ɣ����Կ�ʼִ�б���loading��Ӧ������
						 */
						Debug.debug("TaskThread"+getLastPopUpThread());
						/*if(!Thread.State.WAITING.equals(getLastPopUpThread().getState())){
							Thread.sleep(100);
							Debug.debug("TaskThread���̴߳��ڷǵȴ�״̬,��Ϊģ̬δ��ʼ"+getLastPopUpThread().getState());
							continue;
						}*/
						LoadingTask task=getTaskList().get(0);
						try{	
							Future<MsgEntry> rs=exec.submit(task);
							//�õ�����ķ���ֵ����ʱ�ȴ�ʱ��ΪTIMEOUT�����׳���ʱ�쳣��ִ�й����г����쳣�򷵻�null
							MsgEntry msg=rs.get(TIMEOUT, TimeUnit.SECONDS);
							if(msg==null){
								throw new NullPointerException("WebService������ϢΪ��");
							}
							if(msg.isResult()){//���ؽ��Ϊ�ɹ�
								Debug.debug(msg.getResultMessage());
								loadingResult=msg;
							}else{
								Debug.error(msg.getResultMessage(), null);
								loadingResult=msg;
							}
						}catch(TimeoutException e){
							loadingResult=new MsgEntry(false, "�ȴ���ʱ");
							Debug.error("�ȴ���ʱ", e);
						}catch(Exception e){//����ִ�й����г���δ֪�쳣
							loadingResult=new MsgEntry(false, "δ֪�쳣");
							Debug.error("δ֪�쳣:", e);
						}finally{
							getTaskList().remove(task);
						}
					}
					if(Thread.State.RUNNABLE.equals(thread.getState())){
						Debug.debug("�����̵߳ȴ�");
						closeDialog();//�ر�loading��
						synchronized(this){//�����ͬ�������������IllegalMonitorStateException - �����ǰ�̲߳��Ǵ˶���������������ߡ�
							this.wait();
						}
					}
				/**�����쳣����**/
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					synchronized(this){
						this.notify();
					}
				}
			}
		}
		
		public State getState(){
			return thread.getState();
		}
	}

}
