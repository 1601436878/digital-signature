package RSA;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSA_0 {
	Map map;
	public RSA_0(){
		this.map = get();
	}
	/**
	 * ���ɹ�Կ��˽Կ
	 * @return Map&lt;String,Object&gt;
	 */
	private Map<String,Object> get(){
		Map<String ,Object> map = new HashMap();
		try {
			// ��������ָ���㷨�� public/private ��Կ�Ե� KeyPairGenerator ����
			KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
			// ʹ�ø��������Դ����Ĭ�ϵĲ������ϣ���ʼ��ȷ����Կ��С����Կ��������
			keyPair.initialize(1024);
			
			KeyPair keys = keyPair.generateKeyPair();
			PrivateKey privateKey = keys.getPrivate();
			PublicKey publicKey = keys.getPublic();
			map.put("public", publicKey);
			map.put("private", privateKey);
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.map = map;
		return map;
	}
	
	public PrivateKey getPrivateKey(){
		PrivateKey privateKey = (PrivateKey)map.get("private");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
		return privateKey;
	}
	public PublicKey getPublicKey(){
		PublicKey publicKey = (PublicKey)map.get("public");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(publicKey.getEncoded());
		return publicKey;
	}
	
	
	public String toBase64(PrivateKey key){
		return Base64.encodeBase64String(key.getEncoded());
	}
	public String toBase64(PublicKey key){
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	/**
	 * �ù�Կ����
	 */
	public byte [] Encrypt(String text ,RSAPublicKey publicKey){
		try {
			//��������
			Cipher cipher = Cipher.getInstance("RSA");
			//����ģʽΪ����
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			byte[] stext = cipher.doFinal(text.getBytes());
			return Base64.encodeBase64(stext);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��˽Կ����
	 * @return String
	 */
	public byte[]  Decrypt(String stext ,RSAPrivateKey privateKey){
		byte [] data = Base64.decodeBase64(stext);
		Cipher cipher;
		try {		
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte [] newText = cipher.doFinal(data);
			return newText;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	//----------------------------------------------
	
	public static void main(String[] args) {
		RSA_0 rsa = new RSA_0();
		RSAPrivateKey privateKey = (RSAPrivateKey)rsa.getPrivateKey();
		RSAPublicKey publicKey = (RSAPublicKey)rsa.getPublicKey();

		//����
		byte [] stext = rsa.Encrypt("hello world", publicKey);
		String stextbase64 = Base64.encodeBase64String(stext);
		System.out.println("���ܺ���ļ�:"+stextbase64);
		
		//����
		byte [] newText = rsa.Decrypt(new String(stext), privateKey);
		System.out.println("���ܺ���ļ�:"+new String(newText));
		
		String sss = Base64.encodeBase64String(privateKey.getEncoded());
		
		
		System.out.println("��Կ��"+Base64.encodeBase64String(publicKey.getEncoded()));
		System.out.println("˽Կ��"+Base64.encodeBase64String(privateKey.getEncoded()));
	}
}


