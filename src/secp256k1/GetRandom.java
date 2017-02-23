package secp256k1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetRandom{
	public static String getPrivateKey(){
//		privateKey = new BigInteger(createRandom().toString(), 10);
		FileReader in = null;
		char[] pri = new char[100];
		int read;
		String priKey;
		
		try {
			in = new FileReader("D:\\temp.txt");
			read =  in.read();
			int i=0;
			while(read!=-1){
//				System.out.println(read);
				pri[i] = (char)read;
				read =  in.read();
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2){
			e2.printStackTrace();
		}
		priKey = new String(pri, 0, 64);
		return priKey;
	}
}