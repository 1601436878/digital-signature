package secp256k1;

import java.math.BigInteger;
import java.util.Scanner;

import HASH256.Hash256;

public class SecpVerify {
	private BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC2F",16);
//	private BigInteger p = new BigInteger("FfFFFC2F",16);
	private BigInteger n =new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",16);
	private Node2 G;
	private Node2 Q;
	private BigInteger a =new BigInteger("0",10);
	private BigInteger b = new BigInteger("7",10);
	
	
	
	public SecpVerify(){
		BigInteger x = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",16);
		BigInteger y = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8",16);
		G = new Node2(x,y);
	}
	
	/**
	 * 签名的认证
	 * @param r
	 * @param s
	 * @return
	 */
	public boolean Verify(String text ,String sectext,String publicKeyStr){
		String rStr = sectext.substring(0, 64);
		String sStr = sectext.substring(64);
		String xStr = publicKeyStr.substring(0,64);
		String yStr = publicKeyStr.substring(64, publicKeyStr.length());
		
		System.out.println("rStr_length:"+rStr.length());
		System.out.println("sStr_length:"+rStr.length());
		System.out.println("xStr_length:"+rStr.length());
		System.out.println("rStr_length:"+rStr.length());
		
		BigInteger r =new BigInteger(rStr,16);		// 获得r
		BigInteger s = new BigInteger(sStr,16);		// 获得s
		BigInteger x = new BigInteger(xStr,16);
		BigInteger y = new BigInteger(yStr,16);
		
//		BigInteger r =new BigInteger("62",10);		// 获得r
//		BigInteger s = new BigInteger("47",10);		// 获得s
//		BigInteger x = new BigInteger("52",10);
//		BigInteger y = new BigInteger("7",10);
		
		this.Q = new Node2(x,y);	// 初始化公钥
		
		r = r.mod(this.n);
		s = s.mod(this.n);
		
		if(r.compareTo(new BigInteger("0"))<0 || r.compareTo(n.subtract(new BigInteger("1")))>0)  return false;
		if(s.compareTo(new BigInteger("0"))<0 || s.compareTo(n.subtract(new BigInteger("1")))>0) return false;
		
		BigInteger w = s.remainder(this.p);				// w = s mod n 
																							System.out.println("w:"+w);
		Hash256 hash = new Hash256();		
		byte [] data = hash.Encrypt(text);
		BigInteger z = new BigInteger(Hash256.Encrypt(text));	// 用户数据转换成的数字

		z = z.mod(this.p);								
																							System.out.println("z:"+z);
		BigInteger u = z.multiply(w);//.mod(this.n);  			// u = z*w mod n 				
		BigInteger v = r.multiply(w).mod(this.n);				// v = r*w mod n
																							System.out.println("u:"+u+"v:"+v);
																							System.out.println("47%79："+(47%79));
//		Node result = new Node();
		Test1_Multiply2   cal = new Test1_Multiply2();
		Node2 temp1 = cal.Multiply(u, this.G);
		Node2 temp2 = cal.Multiply(v, this.Q);
		
		Node2 result = cal.diffAdd(temp1, temp2);
		
		System.out.println("result-x"+result.getX()+"result-y"+result.getY()+"result-x%n:"+result.getX().mod(this.n));
		if(result.getX().mod(this.n).equals(r)){
			return true;
		}
		System.out.println("result-x%n:"+result.getX().mod(this.n));
		System.out.println("this.n"+this.n);
		System.out.println("r:"+r);
		
		return false;
	}
	
	public Node2 str2Obj(String data){
		String xStr = data.substring(0, 67);
		String yStr = data.substring(67);
		
		System.out.println(xStr);
		System.out.println(yStr);
		
		BigInteger x = new BigInteger(xStr);
		BigInteger y = new BigInteger(yStr);
		
		return new Node2(x,y);
	}
	
	
	
	//--------------------------------------------------
	public static void main(String[] args) {
		SecpVerify verify = new SecpVerify();
		GenerateKeys generate = new GenerateKeys();
		String pub = generate.getHexPublicKey();
		String sec = generate.getHexPrivateKey();

		verify.Verify("hello", sec, pub);
	}
}
	
