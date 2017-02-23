package secp256k1;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class GenerateKeys {
	private BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC2F",16);
	private BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",16);
	private Node2 G;
	private BigInteger a =new BigInteger("0",10);
	private BigInteger b = new BigInteger("7",10);
	
	private BigInteger privateKey ;
	private Node2 publicKey;
	public GenerateKeys(){
		BigInteger x = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",16);
		BigInteger y = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8",16);
		G = new Node2(x,y);
		
		generate();
	}
	
	public void generate(){
//		BigInteger privateKey = new BigInteger("6d2718f3705f42c9b94a420bbf6f4c9db7a6f2f470c4a9acfaae95e4252c3f39",16);
		BigInteger privateKey = new BigInteger(GetRandom.getPrivateKey(),16);
		Test1_Multiply2  cal = new Test1_Multiply2();
		Node2 publicKey = cal.Multiply(privateKey, this.G);
		System.out.println("#pub-x :"+publicKey.getX());
		System.out.println("#pub-y :"+publicKey.getY());
		
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}
	
	public BigInteger getPrivateKey(){
		return this.privateKey;
	}
	public Node2 getPublicKey(){
		return this.publicKey;
	}
	
	public String getHexPrivateKey(){
		BigInteger privateKey = this.privateKey;
		String privateKey_Hex = new HexBinaryAdapter().marshal(privateKey.toByteArray());
		return privateKey_Hex;
	}   
	public String getHexPublicKey(){
		Node2 publicKey = this.publicKey;
		String publicKey_x = new HexBinaryAdapter().marshal(publicKey.getX().toByteArray());
		String publicKey_y = new HexBinaryAdapter().marshal(publicKey.getY().toByteArray());
		if(publicKey_x.length()==66) publicKey_x = publicKey_x.substring(2);
		if(publicKey_y.length()==66) publicKey_y = publicKey_y.substring(2);
		System.out.println("public_x:"+publicKey_x+"    length:"+publicKey_x.length());
		System.out.println("public_y:"+publicKey_y+"    length:"+publicKey_y.length());
		return publicKey_x+publicKey_y;
	}
	
	
	
	
	//---------------------------------------
	public static void main(String[] args) {
		GenerateKeys  generate = new GenerateKeys();
//		String s = generate.getHexPublicKey();
//		String x = s.substring(0, 64);
//		String y = s.substring(64,s.length());
//		System.out.println("$"+new BigInteger(x,16));
//		System.out.println("$"+new BigInteger(y,16));
		System.out.println("$"+generate.getPublicKey().getY());
//		System.out.println(new BigInteger(generate.getHexPrivateKey(),16));
		System.out.println("privateKey:"+generate.getHexPrivateKey()+"length:"+generate.getHexPrivateKey().length());
		System.out.println("publicKey:"+generate.getHexPublicKey());
	}
}
