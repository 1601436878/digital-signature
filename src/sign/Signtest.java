package sign;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import HASH256.Hash256;
import RSA.RSA;

public class Signtest {
	
	public Signtest(){

	}
	
	/**
	 * 用私钥和数据签名
	 * 私钥来自rsa
	 * @return byte [] 
	 */
	public byte [] sign(String data,String privateKeyStr){		// 私钥是经过base64加密的密文
		/* 将字符串转换成私钥对象 */
		PrivateKey privateKey = RSA.privateKeystr2Obj(privateKeyStr.getBytes());
		
		/*用hash256生成的摘要*/
		Hash256 hash = new Hash256();
		byte [] summary = hash.Encrypt(data);		
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");	//创建签名对象  --> RSA/ECB/PKCS1Padding
			sign.initSign(privateKey);			//用私钥初始化对象
			sign.update(summary);				//用生成的摘要和私钥一起生成密文
			byte [] result = sign.sign();		//返回签名后的字节数组
			
			return Base64.encodeBase64(result);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 用公钥验证签名后的文件，
	 * 需要两个文件一个是数据的摘要，一个是用私钥签名后的密文-->从<font color="red"><b>signed.dat</b></font>中获得<br/>
	 * 公钥来自rsa
	 * @return boolean
	 */
	public boolean verify(String text,String secText,String publicKeyStr ){
		/*获取公钥对象*/
		PublicKey publicKey = RSA.publicKeystr2Obj(publicKeyStr.getBytes());
		
		byte [] secTextByte = Base64.decodeBase64(secText);
		
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initVerify(publicKey);
			
			Hash256 hash = new Hash256();
			byte [] summary = hash.Encrypt(text);	// 接收方用相同的哈希函数得到报文摘要summary
			sign.update(summary);
			return sign.verify(secTextByte);				//如果用公钥解密result后的摘要与summary相同则表示签名成功

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	

//-----------------------------------------------------

	public static void main(String[] args) {
		RSA rsa = new RSA();
		String privateKeyStr = new String(rsa.getPrivateKeyByte());		// 对象与字符串之间的转换用base64，自动进行
		String publicKeyStr = new String(rsa.getPublicKeyByte());
		
		String text = "hello world";
		
		Signtest sign = new Signtest();
		byte [] secTextByte = sign.sign(text, privateKeyStr);			//签名  返回base64加密的签名结果密文
		boolean flag = sign.verify(text, new String(secTextByte), publicKeyStr);	//验证
		
		if(flag){
			System.out.println("成功");
		}else{
			System.out.println("失败");
		}
	}
}

