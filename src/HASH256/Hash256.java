package HASH256;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash256 {
	
	public Hash256(){}

	/**
	 * ����hash256 ���ܺ���ֽ�����
	 * @param text
	 * @return byte[]
	 */
	public static  byte[] Encrypt(String text){
		String result = null;
		byte [] data =null;
		try {
			//�������ܶ���
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			//�����������
			messageDigest.update(text.getBytes());
			//���ؼ��ܺ���ֽ�����
			data = messageDigest.digest();
			
			StringBuilder str = new StringBuilder();
			for(int i = 0 ; i < data.length ; i++){
				int v = 0xff & data[i];
				String s = Integer.toHexString(v);
				if(s.length()==1){
					str.append("0");
				}
				str.append(s);
			}
			result = str.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.getBytes();
//		return data;
	}
	
	//-------------------------------------------------
	
	public static void main(String[] args) {
		Hash256 temp = new Hash256();
		System.out.println("hello"+"-->"+new String(temp.Encrypt("hello world")));
	}
}
