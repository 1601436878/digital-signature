package secp256k1;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * �������
 * @author wangchao
 *
 */
public class Test1_Multiply2 {
	private BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC2F",16);
	private BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",16);
	private Node2 G;
	private BigInteger a =new BigInteger("0",10);
	private BigInteger b = new BigInteger("7",10);
	
	public Test1_Multiply2(){
		BigInteger x = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",16);
		BigInteger y = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8",16);
		G = new Node2(x,y);
	}
	
	/**
	 * ������
	 * @param p
	 * @param q
	 * @return
	 */
	public Node2 diffAdd(Node2 p,Node2 q){	//System.out.println("#��ʼ");
		//  ���c	
		BigInteger c = q.getY().subtract(p.getY());		// qy-py
//		c = c.multiply(mDivision(q.getX().subtract(p.getX()),this.p));	//	System.out.println("c0:"+c);
		c = c.multiply(q.getX().subtract(p.getX()));
		c = c.mod(this.p);												//	System.out.println("ȡģ��� C��"+c);
		
		Node2 result = new Node2();
		// ������
		BigInteger rx = c.multiply(c).subtract(p.getX()).subtract(q.getX());//System.out.println("rx:"+rx);
		rx = rx.mod(this.p);
		if(rx.compareTo(new BigInteger("0",10))<0){
			rx = rx.add(this.p);
		}
		BigInteger ry = c.multiply(p.getX().subtract(rx)).subtract(p.getY());//System.out.println("ry0:"+ry);
		ry = ry.mod(this.p);//System.out.println("ry:"+ry);
		if(ry.compareTo(new BigInteger("0",10))<0){
			ry = ry.add(this.p);
		}
		// ���ؽ��
		result.setX(rx);
		result.setY(ry);
								
		return result;
	}
	
	/**
	 * ͬ��ӱ�
	 * @param node
	 * @return
	 */
	public Node2 sameAdd(Node2 node ){
//		BigInteger c = (node.getX().multiply(node.getX()).multiply(new BigInteger("3",10))).multiply(mDivision(node.getY().multiply(new BigInteger("2",10)),this.p));
		BigInteger c = (node.getX().multiply(node.getX()).multiply(new BigInteger("3",10)));//.multiply(mDivision(node.getY().multiply(new BigInteger("2",10)),this.p));
		c = c.divide(node.getX().multiply(new BigInteger("2")))	;	
																	//	System.out.println("ȡģ֮ǰ��c:"+c);
																	//	System.out.println("#"+node.getX()+":"+node.getY()+"#");
		c = c.mod(this.p);										
																	//	System.out.println("ȡ��֮��c:"+c);
		BigInteger rx = c.multiply(c).subtract( node.getX().multiply(new BigInteger("2",10)));
		BigInteger ry = ((node.getX().subtract(rx)).multiply(c).subtract(node.getY()));
		rx = rx.mod(this.p);
		ry = ry.mod(this.p);

		Node2 result = new Node2(rx,ry);
		return result;
	}
	
//	 s * x % m = 1 
//	public static BigInteger mDivision(BigInteger s ,BigInteger m){
//		BigInteger i = new BigInteger("1");
////		BigInteger s2 = new BigInteger(s);
////		BigInteger m2 = new BigInteger(m);
//		BigInteger result = m.multiply(i).add(new BigInteger("1")).mod(s);
//		while(result.compareTo(new BigInteger("0")) != 0){
//			i = i.add(new BigInteger("1"));
//			result = m.multiply(i).add(new BigInteger("1")).mod(s);					System.out.println("i"+i);
//		}
//		return m.multiply(i).add(new BigInteger("1")).divide(s);
//		
//		//---------------------------------
//		return new BigInteger("1",10).mod(m.multiply(s)).divide(s);
//		
//	}
	
	
//	----------------------------test--------------
	public static BigInteger mDivision(BigInteger d ,BigInteger n){System.out.println("#");
		if(d.compareTo(new BigInteger("0",10))<0)		d = new BigInteger("0",10).subtract(d);
//		System.out.println("d:"+d);
		BigInteger m = null;
		BigInteger t = null;
		BigInteger i = new BigInteger("2", 10);
		BigInteger k = new BigInteger("0", 10);
		
		m = n.divide(d);                  //m = n/d
		t = m.multiply(d);                //t = m*d
		m = m.multiply(d).add(d);      //m = m*d+d
		int j=0;
		while(!m.mod(n).toString().equals("1")){ //�ж� m%n = 1 �Ƿ�������������������m���������ѭ��
//			System.out.print(i+": ");    //
//			System.out.println(m.remainder(n));   //
			m = t.multiply(i).add(d.multiply(k.add(new BigInteger("1", 10))));          //m = t*i+d

			
			while(m.subtract(n.multiply(i)).compareTo(new BigInteger("0", 10))==-1){  //�ж� m < n*i �Ƿ����
				j++;
//				System.out.println("�ڲ�j:"+j);
				m = m.add(d);                   //m += d
				k = k.add(new BigInteger("1", 10));			
			}//System.out.println("m:"+m);
				
			i = i.add(new BigInteger("1", 10));      //i++
		}
		m = m.divide(d);
//		System.out.println("ѭ����k ="+k);
//		System.out.println("m:"+m);
//		m = m.mod(p);
		return m;
	}
	
	
	//---------------------------------------------
	
	
	
/*	public Node2 Multiply(int s , Node2 g){
		if(s == 2 ){
			return sameAdd(g);
		}
		if(0 == s % 2){
			return sameAdd(Multiply(s/2,g));
		}
		if(1 == s % 2){
			return diffAdd(g,Multiply(s-1,g));
		}
		return null;
	}*/
	
	public Node2 Multiply(BigInteger s , Node2 g){
		if(s.equals(new BigInteger("2",10))){
			return sameAdd(g);
		}
		if(s.mod(new BigInteger("2",10)).equals(new BigInteger("0",10))){
			return sameAdd(Multiply(s.divide(new BigInteger("2",10)),g));
		}
		if(s.mod(new BigInteger("2",10)).equals(new BigInteger("1",10))){
			return diffAdd(g,Multiply(s.subtract(new BigInteger("1",10)),g));
		}
		return null;
	}
	

	public static void main(String[] args) {
		Node2 a = new Node2(new BigInteger("1"),new BigInteger("2"));
		Node2 b = new Node2(new BigInteger("2"),new BigInteger("22"));
		Test1_Multiply2 test = new Test1_Multiply2() ;


//		Node2 result = test.Multiply(new BigInteger("76",10), b);	//�˷�
//		Node2 result = test.sameAdd(b);		//ͬ��ӱ�
		
//		System.out.println(test.G.getX()+":"+test.G.getY());
//		Node2 result = test.diffAdd(a,b);	//������
//		System.out.println("#"+test.mDivision(new BigInteger("44",10),new BigInteger("67")));
//		System.out.println(result.getX()+":"+result.getY());
//		System.out.println(Test1_Multiply2.mDivision(new BigInteger("3"), new BigInteger("79")));
		

		
		
	}
}



/**
 * ����(������)
 * @author wangchao
 *
 */
class Node2{
	private BigInteger x ;
	private BigInteger y ;
	
	public Node2(){
		
	}
	
	public Node2(BigInteger x, BigInteger y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public BigInteger getX() {
		return x;
	}
	public void setX(BigInteger x) {
		this.x = x;
	}
	public BigInteger getY() {
		return y;
	}
	public void setY(BigInteger y) {
		this.y = y;
	}
	
	
}
