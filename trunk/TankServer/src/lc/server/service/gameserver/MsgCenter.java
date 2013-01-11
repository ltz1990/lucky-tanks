package lc.server.service.gameserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import lc.server.log.LogUtil;

/**
 * ��������Ϣ���� �ǵ�����ÿ������һ��
 */
public class MsgCenter {
	public static final String TYPE_JOIN="0";//������Ϸ
	public static final String TYPE_JOIN_SUCCESS="1";//����ɹ�
	public static final String TYPE_JOIN_FAILED="2";//����ʧ��
	public static final String TYPE_CREATE="3";//������Ϸ
	public static final String TYPE_CREATE_SUCCESS="4";//�����ɹ�
	public static final String TYPE_CREATE_FAILED="5";//����ʧ��
	public static final String TYPE_TANK_MOVE="6";//̹���ƶ�
	public static final String TYPE_BULLET="7";//�ڵ�����	
	public static final String TYPE_CREATE_OTHER_TANK="8"; //��������̹��
	
	public static final int GAMETYPE_FLAG=0;//����
	public static final int GAMETYPE_FIGHT=1;//��ս
	
	final static String CRLF = "\r\n";

	protected Map<String, SocketChannel> allTanks;

	public MsgCenter(){
		this.allTanks=new HashMap<String, SocketChannel>();
	}
	/**
	 * ������Ϣ������
	 * 
	 * �˷�����Ҫ�ú�������������
	 * @param msg
	 */
	public synchronized void msgProcess(ByteBuffer buffer, SocketChannel client) {
		try {
			String msg = null;
			msg = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
			buffer.position(0);//�������� 
			int endIndex = 0;
			int index = 0;
			while ((index = msg.indexOf('[', endIndex)) != -1) {
				endIndex = msg.indexOf(']', index);
				String[] strs = msg.substring(index + 1, endIndex).split(",");
				if (TYPE_JOIN.equals(strs[0])) {// �����������������Ϸ
					// ����ɹ�			
					client.write(ByteBuffer.wrap(("[" + TYPE_JOIN_SUCCESS + ",200,200]").getBytes("UTF-8")));//���ͱ���̹������
					for(String key:allTanks.keySet()){//���������û�̹������
						client.write(ByteBuffer.wrap(("[" + TYPE_CREATE_OTHER_TANK + ","+key+","+100+","+100+"]").getBytes("UTF-8")));//�����˵ĸ���
						try{
						allTanks.get(key).write(ByteBuffer.wrap(("[" + TYPE_CREATE_OTHER_TANK + ","+strs[1]+","+100+","+100+"]").getBytes("UTF-8")));//����ĸ�����
						}catch(ClosedChannelException e){
							e.printStackTrace();
							allTanks.remove(key);//�������˳�
							continue;
						}
					}
					allTanks.put(strs[1], client);//������ʱ���һ��
				}
			}
			if(msg.indexOf("HTTP/1.1")>0){//HTTP����
				BufferedReader br=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer.array())));
				String headerLine=br.readLine();
				LogUtil.logger.info("�õ�һ��HTTP����" + headerLine);
			      if (headerLine.equals(CRLF) || headerLine.equals("")){
			        return;
			      }
			      StringTokenizer s = new StringTokenizer(headerLine);
			      String temp = s.nextToken();
			      if (temp.equals("GET")) {
			    	  String fileName = s.nextToken();
			          fileName = "." + fileName;
			          FileInputStream fis = null;
			          boolean fileExists = true;
			          try {		        	  
			            fis = new FileInputStream(System.getProperty("user.dir")+fileName);
			          } catch (FileNotFoundException e) {
			            fileExists = false;
			          }
			          // ��ɻ�Ӧ��Ϣ
			          String serverLine = "Server: a simple java httpServer";
			          String statusLine = null;
			          String contentTypeLine = null;
			          String entityBody = null;
			          String contentLengthLine = "error";
			          if (fileExists) {
			            statusLine = "HTTP/1.0 200 OK" + CRLF;
			            contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
			            contentLengthLine = "Content-Length: " + (new Integer(fis.available())).toString() + CRLF;
			          } else {
			            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			            contentTypeLine = "text/html";
			            entityBody = "<HTML><HEAD><TITLE>404 Not Found</TITLE></HEAD><BODY>404 Not Found <br>usage:http://localhost:9999/"
			                + "index.html</BODY></HTML>";
			          }
			          client.write(ByteBuffer.wrap(statusLine.getBytes()));
			          client.write(ByteBuffer.wrap(serverLine.getBytes()));
			          client.write(ByteBuffer.wrap(contentTypeLine.getBytes()));
			          client.write(ByteBuffer.wrap(contentLengthLine.getBytes()));
			          client.write(ByteBuffer.wrap(CRLF.getBytes()));
			          // ������Ϣ����
			          if (fileExists) {
			            byte[] fileByte=new byte[1024];
			            fis.read(fileByte);
			            client.write(ByteBuffer.wrap(fileByte));
			          } else {
			        	client.write(ByteBuffer.wrap(entityBody.getBytes()));
			          }
			      }
			}
		} catch (CharacterCodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String contentType(String fileName) {
	    if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
	      return "text/html";
	    }
	    return "fileName";
	}
}
