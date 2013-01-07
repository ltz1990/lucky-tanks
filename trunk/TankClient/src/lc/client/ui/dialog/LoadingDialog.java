package lc.client.ui.dialog;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import lc.client.core.task.LoadingTask;
import lc.client.ui.components.LDialog;
import lc.client.ui.frame.MainFrame;
import lc.client.util.ClientConstant;
import lc.client.util.Debug;
import lc.client.util.FontSetting;

/**
 * 载入提示框
 * 含有任务队列机制，每次展示LOADING的时候都有一个任务与之对应
 * @author LUCKY
 *
 */
public class LoadingDialog extends LDialog {
	private static LoadingDialog loadingDialog;
	private JLabel text;
	private List<LoadingTask> taskList;//任务列表
	private RunLoadingTask loadingTask;//任务线程
	boolean loadingResult; //执行结果
	/**
	 * 得到载入界面
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
		text=new JLabel();
		int labelWidth = ClientConstant.LABLE_WIDTH;
		int labelHeight = ClientConstant.LABLE_HEIGHT;
		text.setBounds((this.getWidth()-labelWidth>>1)-15,(this.getHeight()-labelHeight>>1)-19, labelWidth, labelHeight+10);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
		text.setIcon(new ImageIcon(Class.class.getResource("/images/loading.gif")));
		FontSetting.setChildrenFont(this,new Font("微软雅黑", Font.PLAIN, 12));
		
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
	 * @param msg
	 */
	public boolean popUpMessageDialog(LoadingTask task){
		if(!this.isShowing()){
			text.setText(task.getLoadingMsg());
			taskList.add(task);
			synchronized(loadingTask){
				loadingTask.notify();//叫任务线程起来干活
			}
			super.popUp(); //弹出模态，停止
			return loadingResult;
		}
		return false;
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
	 * @author LUCKY
	 */
	private class RunLoadingTask implements Runnable{
		private Thread thread;
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
						 * 否则说明当前loading对话框已经处于模态，已经创建完成，可以开始执行本次展示对应的任务
						 */
						if(!Thread.State.WAITING.equals(getLastPopUpThread().getState())){
							Thread.sleep(100);
							Debug.debug("主线程处于非等待状态,认为模态未开始"+getLastPopUpThread().getState());
							continue;
						}
						LoadingTask task=getTaskList().get(0);
						try{
							task.run();//执行任务
							loadingResult=true;//Loading任务执行成功
							LoadingDialog.getInstance().closeDialog();//关闭Loading
							if(task.getSuccessResultMsg()!=null){
								Debug.showMessageDialog(task.getSuccessResultMsg());
							}//显示成功消息							
						}catch(Exception e){
							loadingResult=false;//loading任务执行失败
							if(task.getFailedResultMsg()!=null){
								Debug.error(task.getFailedResultMsg(), e);
							}	
							synchronized (this) {								
								this.notify();
							}
						}finally{
							getTaskList().remove(task);
						}
					}
					if(Thread.State.RUNNABLE.equals(thread.getState())){
						Debug.debug("任务线程等待");
						synchronized(this){//必须加同步锁，否则会抛IllegalMonitorStateException - 如果当前线程不是此对象监视器的所有者。
							this.wait();
						}
					}
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
	}

}
