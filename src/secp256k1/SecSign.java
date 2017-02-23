package secp256k1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import HASH256.Hash256;
import RSA.RSA;

public class SecSign {
	private String p = "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc2f";
	private String n = "fffffffffffffffffffffffffffffffeebaaedce6af48a03bbfd25e8cd0364141";
	private int h = 1;
	private int a = 0;
	private int b = 7;
	private String G = "0279be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798";
	
	private long getRandom(){
		Random random = new Random();
		long result = random.nextLong();
		return result>0?result:-result;
	}
	
	public int cal(){
		BigInteger bigInt = new BigInteger(n,16);
		System.out.println(bigInt.toString(10));

//		System.out.println(result.toString());
//		BigDecimal calculator = new BigDecimal(p);
//		int data  = Integer.parseInt(p, 16);
//		int data = calculator.intValue();
		return 1 ;
	}
	
	public void deal(){
		long random = getRandom();			//获得随机数d
		System.out.println("d:"+random);
		BigInteger g = new BigInteger(G,16);
		System.out.println("G:"+g.toString());
		BigDecimal decimal = new BigDecimal(g);
		BigInteger dG = g.multiply(new BigInteger(String.valueOf(random)));	//获得dG
		System.out.println("dG:"+dG.toString());
		
		BigInteger r = dG.remainder(new BigInteger(n,16));
		System.out.println("r:"+r.toString());			//   r = x1/n
		
		// 获得 n
		BigInteger big_n = new BigInteger(n,16);		System.out.println("n:"+big_n);
		BigInteger randoms = new BigInteger(String.valueOf(random));
		// 获得 M
		BigInteger M = big_n.divide(randoms);
		
		// m * d % n 
		BigInteger temp = M.multiply(randoms).remainder(big_n);
		int s = 0;
		BigInteger s1 = new BigInteger("1");
		
		// 当余数为1时，跳出
		while(!temp.equals(s1)){
			// M 递增
			if(-1 == M.multiply(randoms).compareTo(big_n)){
				M = M.add(randoms);
			}else{
				M = M.add(s1);
			}
			// 更新 M*d%n 的值
			temp = M.multiply(randoms).remainder(big_n);
			
			System.out.print((M.toString())+":");
			
			if(s%50==0) System.out.println("");
			s++;
		}
		
		System.out.println("M:"+M.toString());
		
	}
//	private BigInteger getM(long random){
//		BigInteger M = new BigInteger("0",10);
//		BigInteger randoms = new BigInteger(String.valueOf(random));
//		while(M.multiply(randoms).remainder(val)){
//			
//		}
//		
//	}
	
	
	public static void main(String[] args) {
		SecSign sign = new SecSign();
		sign.deal();
		
		
////---------------------------------------------------
//		
//		String str = new String("hello world");
//		Hash256 hash = new Hash256();
//		byte []  data = hash.Encrypt(str);
//		int sum = 0;
//		for(int i = 0 ; i < data.length;i++){
//			sum += data[i];
//		}
//		int e = sum ;
//		System.out.println("e:"+e);		//e
//		
//		RSA rsa = new RSA();
//		byte [] priData = rsa.getPrivateKeyByte();
//		sum = 0;
//		for(int i = 0 ; i< priData.length;i++){
//			sum += priData[i];
//		}
//		int k = sum ;
//		System.out.println("k:"+k);
		
	}
	
}
