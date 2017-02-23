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
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSA {
	Map map ;

	public RSA(){
		map = get();
	}
	/**
	 * 生成公钥和私钥
	 * @return Map&lt;String,Object&gt;
	 */
	public Map<String,Object> get(){
		Map<String ,Object> map = new HashMap<>();
		try {
			// 返回生成指定算法的 public/private 密钥对的 KeyPairGenerator 对象
			KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
			// 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
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
	
	public byte [] getPrivateKeyByte(){
		PrivateKey privateKey = (PrivateKey)map.get("private");
		return Base64.encodeBase64(privateKey.getEncoded());
	}
	public byte [] getPublicKeyByte(){
		PublicKey publicKey = (PublicKey)map.get("public");
		return Base64.encodeBase64(publicKey.getEncoded());
	}
	
	/**
	 * 将私钥对象转换成字符串
	 * @param key
	 * @return
	 */
	public static String toBase64(PrivateKey key){
		return Base64.encodeBase64String(key.getEncoded());
	}
	/**
	 * 将公钥对象转换成字符串
	 * @param key
	 * @return
	 */
	public static  String toBase64(PublicKey key){
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	/**
	 * 用公钥加密
	 */
	public byte [] Encrypt(String text ,byte [] publicKeyStr){
		PublicKey publicKey = publicKeystr2Obj(publicKeyStr);
		try {
			//创建对象
			Cipher cipher = Cipher.getInstance("RSA");
			//设置模式为加密
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
	 * 用私钥解密
	 * @return String
	 */
	public byte[]  Decrypt(String stext ,byte [] privateKeystr){
		byte [] data = Base64.decodeBase64(stext);
		PrivateKey privateKey = privateKeystr2Obj(privateKeystr);
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
	/**
	 * 由字符转转换为私钥
	 * @param privateKey
	 * @return PrivateKey
	 */
	public static PrivateKey privateKeystr2Obj(byte [] privateKeyStr){
		byte [] privateKey = Base64.decodeBase64(privateKeyStr);
		try {
			KeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey pri = factory.generatePrivate(keySpec);
			return pri;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符串转换为公钥
	 * @param publicKey
	 * @return PublicKey
	 */
	public static PublicKey publicKeystr2Obj(byte [] publicKeyStr){
		byte [] publicKey = Base64.decodeBase64(publicKeyStr);
		try {
			KeySpec keySpec = new X509EncodedKeySpec(publicKey);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PublicKey pri = factory.generatePublic(keySpec);
			
			return pri;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 直接返回密钥对象
	 * @return
	 */
	public PrivateKey getPrivateKeyObj(){
		return (PrivateKey)map.get("private");
	}
	public PublicKey getPublicKeyObj(){
		return (PublicKey)map.get("public");
	}
	

	
	//----------------------------------------------
	
	public static void main(String[] args) {
		RSA rsa = new RSA();
		byte [] privateKey = rsa.getPrivateKeyByte();
		byte [] publicKey = rsa.getPublicKeyByte();
		
		System.out.println(new String(privateKey));
		System.out.println(new String(publicKey));
		
		//加密
		byte [] stext = rsa.Encrypt("hello world", publicKey);
		String stextbase64 = Base64.encodeBase64String(stext);
		System.out.println("加密后的文件:"+stextbase64);
		
		//解密
		byte [] newText = rsa.Decrypt(new String(stext), privateKey);
		System.out.println("解密后的文件:"+new String(newText));
		 
		System.out.println("公钥："+new String(publicKey));
		System.out.println("私钥："+new String(privateKey));
	}
}


