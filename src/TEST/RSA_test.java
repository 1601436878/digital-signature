package TEST;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

public class RSA_test {
	public static void main(String[] args) throws Exception {
		HashMap<String, Object> map = RSAUtils.getKeys();
		//���ɹ�Կ��˽Կ
		RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
		RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
		
		//ģ
		String modulus = publicKey.getModulus().toString();
		//��Կָ��
		String public_exponent = publicKey.getPublicExponent().toString();
		//˽Կָ��
		String private_exponent = privateKey.getPrivateExponent().toString();
		//����
		String ming = "123456789";
		//ʹ��ģ��ָ�����ɹ�Կ��˽Կ
		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
		RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);
		//���ܺ������
		String mi = RSAUtils.encryptByPublicKey(ming, pubKey);
		System.out.println("���ܺ�����ģ�"+mi);
		//���ܺ������
		ming = RSAUtils.decryptByPrivateKey(mi, priKey);
		System.out.println("���ܺ�����ģ�"+ming);
	}
}

