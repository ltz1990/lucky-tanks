package lc.server.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * 对象字节数组转换类
 * 
 * @author LUCKY
 * 
 */
public class ObjectConvert {

	/**
	 * 将对象转换成字节数组
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] switchObjectToByte(Object object) throws IOException {
		byte[] bs = null;
		try{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		bs = baos.toByteArray();
		oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return bs;
		}
	}

	/**
	 * 将字节数组还原成对象
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object switchByteToObject(byte[] src) {
		Object o = null;
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(src);
			ObjectInputStream objIn = new ObjectInputStream(byteIn);
			o = objIn.readObject();
			objIn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return o;
		}
	}

}
