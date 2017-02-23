package TEST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import HASH256.Hash256;
import RSA.RSA_0;

public class Signtest {
	private RSA_0 rsa;
	private Hash256 hash ;
	private Map map;
	private String str;
	private byte [] result;
	private byte [] summary;
	
	public Signtest(){
		rsa = new RSA_0();
		hash = new Hash256();
		map = rsa.get();
	}
	
	/**
	 * 用私钥和数据签名
	 * 私钥来自rsa
	 * @return byte [] 
	 */
	public byte [] sign(String data){
		this.str = data;							//保存待加密的明文
		byte [] summary = hash.Encrypt(data);		//用hash256生成的摘要
		this.summary = summary;
//		ObjectInputStream input;
//		ObjectOutputStream output;
		try {
//			input = new ObjectInputStream(new FileInputStream(new File("D:/KEY/privateKey.dat")));
//			RSAPrivateKey privateKey = (RSAPrivateKey)input.readObject();
//			input.close();
			Signature sign = Signature.getInstance("SHA256withRSA");	//创建签名对象  --> RSA/ECB/PKCS1Padding
			sign.initSign((RSAPrivateKey)map.get("private"));			//用私钥初始化对象
			sign.update(summary);							//用生成的摘要更新对象
			byte [] result = sign.sign();					//返回签名后的字节数组
			
			this.result = result;	// 赋值给全局变量
			
//			output = new ObjectOutputStream(new FileOutputStream(new File("D:KEY/signed.dat")));
//			output.writeObject(data);
//			output.writeObject(result);
			
			return result;
			
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
	public boolean verify(){
		ObjectInputStream input;
		try {
//			input = new ObjectInputStream(new FileInputStream(new File("D:/KEY/publicKey.dat")));
//			RSAPublicKey publicKey = (RSAPublicKey)input.readObject();
//			input = new ObjectInputStream(new FileInputStream(new File("D:/KEY/signed.dat")));
//			String data = (String)input.readObject();
//			byte [] signed = (byte [])input.readObject();
//			input.close();
			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initVerify((RSAPublicKey)map.get("public"));
			sign.update(summary);
			return sign.verify(result);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 以16进制的形式显示签名后的密文
	 * @param data
	 * @return String
	 */
	public String toHexString(byte [] data){
		StringBuilder str = new StringBuilder();
		for(int i=0;i<data.length;i++){
			String s = Integer.toHexString(0xff & data[i]);
			if(s.length()==1) str.append("0");
			str.append(s);
		}
		return str.toString();
	}
	
	
	public static void main(String[] args) {
		Signtest test = new Signtest();
		byte [] result = test.sign("hello world");
		System.out.println(test.toHexString(result));
		if(test.verify())System.out.println("签名成功");
		else System.out.println("签名失败");
	}
}
