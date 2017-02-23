package TEST;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
public class DSA {
	public static void main(String[] args) {
		try {
			DSA my = new DSA();
			my.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		// ����ǩ��������Կ
		// ��һ��������Կ��,����Ѿ����ɹ� , �����̾Ϳ�������
		// ���û����� myprikey.dat Ҫ�����ڱ��أ��� mypubkey.dat �������������û�
		if ((new java.io.File("myprikey.dat")).exists() == false) {
			if (generatekey() == false) {
				System.out.println("������Կ�԰�");
				return;
			}
		}
		// �ڶ��� , ���û�
		// ���ļ��ж���˽Կ , ��һ���ַ�������ǩ���󱣴���һ���ļ� (myinfo.dat) ��
		// �����ٰ� myinfo.dat ���ͳ�ȥ��Ϊ�˷�������ǩ��Ҳ�Ž��� myifno.dat �ļ��� , ��ȻҲ�ɷֱ���
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("myprikey.dat"));
			PrivateKey myprikey = (PrivateKey) in.readObject();
			in.close();
			String myinfo = "�����ҵ���Ϣ"; // Ҫǩ������Ϣ
			// ��˽Կ����Ϣ��������ǩ��
			Signature signet = Signature.getInstance("DSA");	//��������
			signet.initSign(myprikey);							//����Կ��ʼ��
			signet.update(myinfo.getBytes());					//���£���ȡ����
			byte[] signed = signet.sign(); 						////����Ϣ������ǩ��
			System.out.println("signed( ǩ������ )=" + byte2hex(signed));
			// ����Ϣ������ǩ��������һ���ļ���
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("myinfo.dat"));
			out.writeObject(myinfo);
			out.writeObject(signed);
			out.close();
			System.out.println("ǩ���������ļ��ɹ�");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("ǩ���������ļ�ʧ��");
		}
		// ������ �����Ϣ���
		// ������ͨ��������ʽ�õ��˻��Ĺ�Կ���ļ�
		// �������ô˻��Ĺ�Կ , ���ļ����м�� , ����ɹ�˵���Ǵ��û���������Ϣ .
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("mypubkey.dat"));
			PublicKey pubkey = (PublicKey) in.readObject();
			in.close();
			System.out.println(pubkey.getFormat());
			in = new ObjectInputStream(new FileInputStream("myinfo.dat"));
			String info = (String) in.readObject();
			byte[] signed = (byte[]) in.readObject();
			in.close();
			Signature signetcheck = Signature.getInstance("DSA");
			signetcheck.initVerify(pubkey);
			signetcheck.update(info.getBytes());
			if (signetcheck.verify(signed)) {
				System.out.println("info=" + info);
				System.out.println("ǩ������");
			} else
				System.out.println("��ǩ������");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		;
	}
	// ����һ���ļ� myprikey.dat �� mypubkey.dat ˽Կ�͹�Կ
	// ��ԿҪ�û����� ( �ļ� , ����ȷ��� ) �������û� , ˽Կ�����ڱ���
	public boolean generatekey() {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
			keygen.initialize(512);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("myprikey.dat"));
			out.writeObject(prikey);
			out.close();
			System.out.println("д����� prikeys ok");
			out = new ObjectOutputStream(new FileOutputStream("mypubkey.dat"));
			out.writeObject(pubkey);
			out.close();
			System.out.println("д����� pubkeys ok");
			System.out.println("������Կ�Գɹ�");
			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("������Կ��ʧ��");
			return false;
		}
	}
	public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}
}