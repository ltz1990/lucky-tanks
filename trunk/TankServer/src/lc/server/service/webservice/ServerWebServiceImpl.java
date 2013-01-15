package lc.server.service.webservice;

import java.net.SocketAddress;
import java.sql.SQLException;

import javax.jws.WebService;

import lc.server.database.dao.UserDAO;
import lc.server.gamecomp.GameHouse;
import lc.server.log.LogUtil;
import lc.server.service.gameserver.GameCtrlCenter;

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
		MsgEntry msg=null;
		UserDAO dao=new UserDAO();
		try {
			if(dao.login(username, password)){
				msg=new MsgEntry(true, "��½�ɹ�!");
				LogUtil.logger.info("[��½�ɹ�]"+username);
			}else{
				msg=new MsgEntry(false,"��½ʧ��!�û����������!");
				LogUtil.logger.warn("[��½ʧ��]�û����������"+username);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			msg=new MsgEntry(false, "��½�����г�����һ�����ݿ��쳣!");
			LogUtil.logger.error("[��½�����쳣]"+username,e);
		} catch (Exception e) {
			msg=new MsgEntry(false, "��½����δ֪�쳣!");
			LogUtil.logger.error("[��½����δ֪�쳣]"+username,e);
		} finally{
			dao.close();
		}
		return msg;
	}
	@Override
	public MsgEntry register(String username, String password) {
		// TODO Auto-generated method stub
		UserDAO dao=new UserDAO();
		MsgEntry msg=null;
		try {
			dao.register(username, password);
			String resultMessage = "�û�"+username+"ע��ɹ�";
			msg=new MsgEntry(true, resultMessage);
			LogUtil.logger.info(resultMessage);
			return msg;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String message = "�û�"+username+"ע��ʧ��,�û����Ѵ���";
			msg=new MsgEntry(false, message);
			LogUtil.logger.error(message, e);
			return msg;
		} catch(Exception e) {
			String message = "�û�"+username+"ע��ʧ�ܣ�����������δ֪�쳣";
			msg=new MsgEntry(false, message);
			LogUtil.logger.error(message, e);
			return msg;
		} finally{
			dao.close();	
		}
	}
	@Override
	public MsgEntry createGame(GameHouse house,String address) {
		// TODO Auto-generated method stub
		MsgEntry msg=new MsgEntry(true, "�����ɹ�");
		GameCtrlCenter center = GameCtrlCenter.getInstance();
		String houseId=center.createGameHouse(house);
		center.searchAnotherConnInfo(address, houseId);
		return msg;
	}	
}
