package lc.server.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import lc.server.database.DBConnection;
import lc.server.log.LogUtil;
import lc.server.tools.ServerConstant;

public class UserDAO {
	private Connection conn;
	public UserDAO(){
		conn=DBConnection.getConnection();
	}
	
	/**
	 * ע���û�
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	public void register(String username,String password) throws SQLException{
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(ServerConstant.SQL_USER_INSERT);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}
	}
	
	/**
	 * ��½
	 * @author LUCKY 2013-1-12
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean login(String username,String password) throws SQLException{
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(ServerConstant.SQL_USER_SELECT);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}finally{//��return֮��ִ��
			if(pstmt!=null){
				pstmt.close();
			}			
		}
	}
	
	/**
	 * �ر�����
	 */
	public void close(){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.logger.error("�ر����ݿ�����ʱ�����쳣",e);
		}
	}
}
