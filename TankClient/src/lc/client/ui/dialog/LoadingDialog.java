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
 * Loading��ʾ��
 * ����������л��ƣ�ÿ��չʾLOADING��ʱ����һ��������֮��Ӧ
 * @author LUCKY
 *
 */
public class LoadingDialog extends LDialog implements AncestorListener{
	private static LoadingDialog loadingDialog;
	private static final long TIMEOUT=10;//��ʱʱ�� 10S
	private JLabel text;
	MsgEntry loadingResult; //ִ�н��
	LoadingTask task;//ִ������
	private ExecutorService exec;
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
		super(MainFrame.getInstance(), "Loading", 200, 100,true);
		/**��ʼ������**/
		text=new JLabel();
		int labelWidth = ClientConstant.LABLE_WIDTH;
		int labelHeight = ClientConstant.LABLE_HEIGHT;
		text.setBounds((this.getWidth()-labelWidth>>1)-15,(this.getHeight()-labelHeight>>1)-19, labelWidth, labelHeight+10);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
		text.addAncestorListener(this);
		text.setIcon(new ImageIcon(ClientStart.class.getResource("/images/loading.gif")));
		FontSetting.setChildrenFont(this,new Font("΢���ź�", Font.PLAIN, 12));
		/**��ʼ�������̡߳�����**/
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
	 * ��������򣬶�Ӧ����
	 * @param task
	 * @return ִ���Ƿ�ɹ�
	 */
	public MsgEntry popUpLoadingDialog(LoadingTask task){
		/**
		 * �޸ģ��ڴ��ڵ�showing�¼�����
		 */
		if(!this.isShowing()){
			text.setText(task.getLoadingMsg());
			this.task=task;
			super.popUp(); //����ģ̬��ֹͣ						
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
