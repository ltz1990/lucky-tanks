package lc.server.service.webservice;

/**
 * 消息实体
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
