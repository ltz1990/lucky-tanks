package lc.server.service.webservice;

import javax.jws.WebService;

import lc.server.log.Debug;

/**
 * webserviceʵ���࣬���սӿڵĶ��������������ķ���
 * @author LUCKY
 *
 */
//������ýӿ�+ʵ����ķ�ʽ������ָ��Ŀ�������ռ�
@WebService(targetNamespace="http://webservice.service.server.lc/",endpointInterface="lc.server.service.webservice.ServerWebService")
public class ServerWebServiceImpl implements ServerWebService{

	@Override
	public MsgEntry login(String username, String password) {
		// TODO Auto-generated method stub.
		MsgEntry msg=new MsgEntry();
		msg.result=false;
		Debug.debug("��½");
		return msg;
	}
	@Override
	public MsgEntry register(String username, String password) {
		// TODO Auto-generated method stub
		MsgEntry msg=new MsgEntry();
		msg.result=true;
		msg.resultMessage="�ɹ�";
		Debug.debug("ע��");
		return msg;
	}	
}
