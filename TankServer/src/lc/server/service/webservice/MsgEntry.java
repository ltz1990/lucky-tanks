package lc.server.service.webservice;

/**
 * ��Ϣʵ��
 * @author LUCKY
 *
 */
public class MsgEntry {
	public boolean result;
	public String resultMessage;
	
	public MsgEntry(){
		
	}
	
	public MsgEntry(boolean result,String resultMessage){
		this.result=result;
		this.resultMessage=resultMessage;
	}
}
