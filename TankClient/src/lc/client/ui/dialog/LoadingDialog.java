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
 * ������ʾ��
 * ����������л��ƣ�ÿ��չʾLOADING��ʱ����һ��������֮��Ӧ
 * @author LUCKY
 *
 */
public class LoadingDialog extends LDialog {
	private static LoadingDialog loadingDialog;
	private JLabel text;
	private List<LoadingTask> taskList;//�����б�
	private RunLoadingTask loadingTask;//�����߳�
	boolean loadingResult; //ִ�н��
	/**
	 * �õ��������
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
		FontSetting.setChildrenFont(this,new Font("΢���ź�", Font.PLAIN, 12));
		
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
	 * @param msg
	 */
	public boolean popUpMessageDialog(LoadingTask task){
		if(!this.isShowing()){
			text.setText(task.getLoadingMsg());
			taskList.add(task);
			synchronized(loadingTask){
				loadingTask.notify();//�������߳������ɻ�
			}
			super.popUp(); //����ģ̬��ֹͣ
			return loadingResult;
		}
		return false;
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
						 * ������̲߳��ǵȴ�״̬��˵����ǰ���ڻ�û�б�����Ϊģ̬
						 * ����˵����ǰloading�Ի����Ѿ�����ģ̬���Ѿ�������ɣ����Կ�ʼִ�б���չʾ��Ӧ������
						 */
						if(!Thread.State.WAITING.equals(getLastPopUpThread().getState())){
							Thread.sleep(100);
							Debug.debug("���̴߳��ڷǵȴ�״̬,��Ϊģ̬δ��ʼ"+getLastPopUpThread().getState());
							continue;
						}
						LoadingTask task=getTaskList().get(0);
						try{
							task.run();//ִ������
							loadingResult=true;//Loading����ִ�гɹ�
							LoadingDialog.getInstance().closeDialog();//�ر�Loading
							if(task.getSuccessResultMsg()!=null){
								Debug.showMessageDialog(task.getSuccessResultMsg());
							}//��ʾ�ɹ���Ϣ							
						}catch(Exception e){
							loadingResult=false;//loading����ִ��ʧ��
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
						Debug.debug("�����̵߳ȴ�");
						synchronized(this){//�����ͬ�������������IllegalMonitorStateException - �����ǰ�̲߳��Ǵ˶���������������ߡ�
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
