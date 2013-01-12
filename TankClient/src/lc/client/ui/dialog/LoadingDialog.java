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
 * Loading提示框
 * 含有任务队列机制，每次展示LOADING的时候都有一个任务与之对应
 * @author LUCKY
 *
 */
public class LoadingDialog extends LDialog {
	private static LoadingDialog loadingDialog;
	private JLabel text;
	private List<LoadingTask> taskList;//任务列表
	private RunLoadingTask loadingTask;//任务线程
	MsgEntry loadingResult; //执行结果
	private static final long TIMEOUT=10;//超时时间 10S
	/**
	 * Loading界面
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
		/**初始化界面**/
		text=new JLabel();
		int labelWidth = ClientConstant.LABLE_WIDTH;
		int labelHeight = ClientConstant.LABLE_HEIGHT;
		text.setBounds((this.getWidth()-labelWidth>>1)-15,(this.getHeight()-labelHeight>>1)-19, labelWidth, labelHeight+10);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
		text.setIcon(new ImageIcon(ClientStart.class.getResource("/images/loading.gif")));
		FontSetting.setChildrenFont(this,new Font("微软雅黑", Font.PLAIN, 12));
		/**初始化任务线程、队列**/
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
	 * 弹出载入框，对应任务
	 * @param task
	 * @return 执行是否成功
	 */
	public MsgEntry popUpLoadingDialog(LoadingTask task){
		if(!this.isShowing()){
			text.setText(task.getLoadingMsg());
			taskList.add(task);
			//等待直到上一个任务执行完成，任务线程为waiting时才继续
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
				loadingTask.notify();//叫任务线程起来干活
			}
			super.popUp(); //弹出模态，停止
			return loadingResult;
		}
		return null;
	}
	
	/**
	 * 待执行任务列表
	 * @return
	 */
	List<LoadingTask> getTaskList(){
		return taskList;
	}
	
	/**
	 * Loading对话框相对的载入任务线程
	 * 如果任务执行成功，则弹出成功消息
	 * 如果任务执行失败，则弹出失败消息，返回到之前页面
	 * 
	 * 不能在线程中打开窗口，会影响到模态
	 * @author LUCKY
	 */
	private class RunLoadingTask implements Runnable{
		private Thread thread;
		private ExecutorService exec;
		public RunLoadingTask(){
			exec=Executors.newSingleThreadExecutor();//用来跑Callable
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
						 * 如果主线程不是等待状态，说明当前窗口还没有被设置为模态
						 * 否则说明当前loading对话框已经处于模态，已经创建完成，可以开始执行本次loading对应的任务
						 */
						Debug.debug("TaskThread"+getLastPopUpThread());
						/*if(!Thread.State.WAITING.equals(getLastPopUpThread().getState())){
							Thread.sleep(100);
							Debug.debug("TaskThread主线程处于非等待状态,认为模态未开始"+getLastPopUpThread().getState());
							continue;
						}*/
						LoadingTask task=getTaskList().get(0);
						try{	
							Future<MsgEntry> rs=exec.submit(task);
							//得到任务的返回值，超时等待时间为TIMEOUT秒则抛出超时异常，执行过程中出现异常则返回null
							MsgEntry msg=rs.get(TIMEOUT, TimeUnit.SECONDS);
							if(msg==null){
								throw new NullPointerException("WebService返回消息为空");
							}
							if(msg.isResult()){//返回结果为成功
								Debug.debug(msg.getResultMessage());
								loadingResult=msg;
							}else{
								Debug.error(msg.getResultMessage(), null);
								loadingResult=msg;
							}
						}catch(TimeoutException e){
							loadingResult=new MsgEntry(false, "等待超时");
							Debug.error("等待超时", e);
						}catch(Exception e){//任务执行过程中出现未知异常
							loadingResult=new MsgEntry(false, "未知异常");
							Debug.error("未知异常:", e);
						}finally{
							getTaskList().remove(task);
						}
					}
					if(Thread.State.RUNNABLE.equals(thread.getState())){
						Debug.debug("任务线程等待");
						closeDialog();//关闭loading框
						synchronized(this){//必须加同步锁，否则会抛IllegalMonitorStateException - 如果当前线程不是此对象监视器的所有者。
							this.wait();
						}
					}
				/**其它异常处理**/
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
