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
	 * ��˽Կ������ǩ��
	 * ˽Կ����rsa
	 * @return byte [] 
	 */
	public byte [] sign(String data,String privateKeyStr){		// ˽Կ�Ǿ���base64���ܵ�����
		/* ���ַ���ת����˽Կ���� */
		PrivateKey privateKey = RSA.privateKeystr2Obj(privateKeyStr.getBytes());
		
		/*��hash256���ɵ�ժҪ*/
		Hash256 hash = new Hash256();
		byte [] summary = hash.Encrypt(data);		
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");	//����ǩ������  --> RSA/ECB/PKCS1Padding
			sign.initSign(privateKey);			//��˽Կ��ʼ������
			sign.update(summary);				//�����ɵ�ժҪ��˽Կһ����������
			byte [] result = sign.sign();		//����ǩ������ֽ�����
			
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
	 * �ù�Կ��֤ǩ������ļ���
	 * ��Ҫ�����ļ�һ�������ݵ�ժҪ��һ������˽Կǩ���������-->��<font color="red"><b>signed.dat</b></font>�л��<br/>
	 * ��Կ����rsa
	 * @return boolean
	 */
	public boolean verify(String text,String secText,String publicKeyStr ){
		/*��ȡ��Կ����*/
		PublicKey publicKey = RSA.publicKeystr2Obj(publicKeyStr.getBytes());
		
		byte [] secTextByte = Base64.decodeBase64(secText);
		
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initVerify(publicKey);
			
			Hash256 hash = new Hash256();
			byte [] summary = hash.Encrypt(text);	// ���շ�����ͬ�Ĺ�ϣ�����õ�����ժҪsummary
			sign.update(summary);
			return sign.verify(secTextByte);				//����ù�Կ����result���ժҪ��summary��ͬ���ʾǩ���ɹ�

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
		String privateKeyStr = new String(rsa.getPrivateKeyByte());		// �������ַ���֮���ת����base64���Զ�����
		String publicKeyStr = new String(rsa.getPublicKeyByte());
		
		String text = "hello world";
		
		Signtest sign = new Signtest();
		byte [] secTextByte = sign.sign(text, privateKeyStr);			//ǩ��  ����base64���ܵ�ǩ���������
		boolean flag = sign.verify(text, new String(secTextByte), publicKeyStr);	//��֤
		
		if(flag){
			System.out.println("�ɹ�");
		}else{
			System.out.println("ʧ��");
		}
	}
}

