package secp256k1;

import java.applet.Applet;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import HASH256.Hash256;

public class NewSecp extends Applet{
	
	private static String strP = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC2F";
	private static String strGx = "79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798";
	private static String strGy = "483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8";
	private static String strN = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141";
	private static String strZ = null;
	private static String source = null;

	private static BigInteger p = new BigInteger(strP, 16);
	private static BigInteger Gx = new BigInteger(strGx, 16);
	private static BigInteger n = new BigInteger(strN, 16);
	private static BigInteger privateKey = null;
	
	private static Node2 G;
	
	
	
	public NewSecp(){
		BigInteger x = new BigInteger(this.strGx,16);
		BigInteger y = new BigInteger(this.strGy,16);
		this.G = new Node2(x,y);
	}
	
	public  Integer createRandom(){
		
		Random rand = new Random();
		/** 产生15000000-16000000之间的随机数*/
		int randNum = rand.nextInt(1000000)+15000000;
		
		return randNum;
	}

	public  BigInteger getPrivateKey(){
		privateKey = new BigInteger(createRandom().toString(), 10);
		return privateKey;
	}
	
	public  BigInteger getPublicKey(){
		
		BigInteger Px = null;
		Px = privateKey.multiply(Gx);   //此处调用同步相加、异步相加
		
		return Px;
	}
	
/*	*//**
	 * 签名
	 * @return
	 *//*
	public static String createSignature(){
		int flag1=0;
		int flag2=0;
		BigInteger d = null;   //d为私钥
		BigInteger k = null;
		BigInteger Px = null;
		BigInteger r = null;
		BigInteger z = null;
		BigInteger s = null;
		String signature = null;
		
		d = getPrivateKey();
		z = new BigInteger(Hash256.Encrypt((source)));
	
		while(flag1==0){
			flag1=1;
			flag2=1;
			// step1-生成随机数
			k = new BigInteger(createRandom().toString(), 10); 
			// step2、step3
			//step2 标量乘法获取点
			Test1_Multiply2 cal = new Test1_Multiply2();
			Node2 result = cal.Multiply(k, G);
			
			//------------
//			r = getPublicKey().mod(n);
			
			// step3 获得r 如果r=0则返回step1
			r = result.getX().mod(n);
			if(r.toString().equals("0"))
				flag1=0;
			// step4 计算s的值 
			if(z.add(r.multiply(d)).mod(k).equals("0"))
				s = (z.add(r.multiply(d)).divide(k)).mod(n);
			else{
//				System.out.println("haa");   //
				s = z.add(r.multiply(d)).multiply(Test1_Multiply2.mDivision(k, n));
			}
			
			if(s.toString().equals("0"))
				flag2=0;
			
			// step5 对得到的(r,s) 生成16进制签名 
			String r_hex = Transform.toHex(r);
			String s_hex = Transform.toHex(s);
			signature = r_hex.toString()+s_hex.toString();

			
			
			
			
			System.out.println("k = "+k);   //
			System.out.println("r = "+r);    //
			strZ = new String(Hash256.Encrypt(source));   //
			System.out.println("strZ = "+strZ);   //
			System.out.println("z = "+z);     //
			System.out.println("s = "+s);    //
			System.out.println("s = "+s_hex);    //
		}
		
		return signature;
	}*/
	
	/**
	 * 签名
	 * @return String
	 */
	public String createSignature(String source,String  privateKeyStr){			System.out.println("开始");
		BigInteger privateKey= new BigInteger(privateKeyStr,16);				System.out.println(privateKey);
//		System.out.println("#privateKey："+privateKey);
		int flag1=0;
		int flag2=0;
		BigInteger d = null;   //d为私钥
		BigInteger k = null;
//		BigInteger Px = null;
		BigInteger r = null;
		BigInteger z = null;
		BigInteger s = null;
		String signature = null;
		
		d = privateKey;			// 获得私钥
		z = new BigInteger(Hash256.Encrypt((source)));	// 用户数据
		
		while(flag1==0){
			flag1=1;
			flag2=1;
			/** step1-生成随机数 */
			k = new BigInteger(createRandom().toString(), 10); 
//			k = new BigInteger(GetRandom.getPrivateKey(),16);
			
			/** step2 标量乘法获取点 */
			Test1_Multiply2 cal = new Test1_Multiply2();						System.out.println("开始计算乘积"+k);
			Node2 result = cal.Multiply(k, G);									System.out.println("result-x:"+result.getX()+"result-y:"+result.getY());
			
			/** step3 获得r 如果r=0则返回step1 **/
			r = result.getX().mod(n);
			if(r.toString().equals("0"))
				flag1=0;
			
			/** step4 计算s的值 */
			if(z.add(r.multiply(d)).mod(k).equals("0"))
				s = (z.add(r.multiply(d)).divide(k)).mod(n);
			else{
				s = z.add(r.multiply(d)).multiply(Test1_Multiply2.mDivision(k, n));
			}														
			s = s.mod(this.n);												
														System.out.println("r:"+r+"s:"+s);											
			if(s.toString().equals("0"))	flag2=0;
																			
			/** step5 对得到的(r,s) 生成16进制签名 */
			String r_hex = Transform.toHex(r);
			String s_hex = Transform.toHex(s);
			if(r_hex.length()==66)  r_hex = r_hex.substring(2);
																		System.out.println("r-length:"+r_hex.length()+"r:"+r_hex);
																		System.out.println("s_length:"+s_hex.length()+"s:"+s_hex);
			signature = r_hex.toString()+s_hex.toString();		// 最后得到的16进制签名

			System.out.println("k = "+k);   //
			System.out.println("r = "+r);    //
			strZ = new String(Hash256.Encrypt(source));   //
			System.out.println("strZ = "+strZ);   //
			System.out.println("z = "+z);     //
			System.out.println("s = "+s);    //
			System.out.println("s = "+s_hex);    //
		}
		
		return signature;
	}
	
	/** 测试*/
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();    //获取开始时间
		
		//---------------
		String signature = null;
//		GenerateKeys keys = new GenerateKeys();
//		BigInteger privateKey = new BigInteger(keys.getHexPrivateKey(),16);
		source = "haha";
		NewSecp sign = new NewSecp();
		signature = sign.createSignature(source,Integer.toHexString(2));		// 私钥是2
		long endTime = System.currentTimeMillis();    //获取结束时间
		
		// -------------
		System.out.println("数字签名："+signature);
		System.out.println();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
	}	
}
