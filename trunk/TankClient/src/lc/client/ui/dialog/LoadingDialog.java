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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import lc.client.core.task.LoadingTask;
import lc.client.environment.ClientConstant;
import lc.client.start.ClientStart;
import lc.client.ui.components.LDialog;
import lc.client.ui.frame.MainFrame;
import lc.client.util.Debug;
import lc.client.util.FontSetting;
import lc.client.webservice.wscode.MsgEntry;

/**
 * Loading提示框
 * 含有任务队列机制，每次展示LOADING的时候都有一个任务与之对应
 * @author LUCKY
 *
 */
public class LoadingDialog extends LDialog implements AncestorListener{
	private static LoadingDialog loadingDialog;
	private static final long TIMEOUT=10;//超时时间 10S
	private JLabel text;
	MsgEntry loadingResult; //执行结果
	LoadingTask task;//执行任务
	private ExecutorService exec;
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
		super(MainFrame.getInstance(), "Loading", 200, 100,true);
		/**初始化界面**/
		text=new JLabel();
		int labelWidth = ClientConstant.LABLE_WIDTH;
		int labelHeight = ClientConstant.LABLE_HEIGHT;
		text.setBounds((this.getWidth()-labelWidth>>1)-15,(this.getHeight()-labelHeight>>1)-19, labelWidth, labelHeight+10);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
		text.addAncestorListener(this);
		text.setIcon(new ImageIcon(ClientStart.class.getResource("/images/loading.gif")));
		FontSetting.setChildrenFont(this,new Font("微软雅黑", Font.PLAIN, 12));
		/**初始化任务线程、队列**/
		exec=Executors.newCachedThreadPool();
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
		/**
		 * 修改，在窗口的showing事件里跑
		 */
		if(!this.isShowing()){
			text.setText(task.getLoadingMsg());
			this.task=task;
			super.popUp(); //弹出模态，停止						
			return loadingResult;
		}
		return null;
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub
		Runnable mainRun=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
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
					closeDialog();
				}				
			}
		};
		exec.execute(mainRun);
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
}
