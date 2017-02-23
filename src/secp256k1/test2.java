package secp256k1;

import java.math.BigInteger;
import java.util.Random;

 class TestSecp256k1 {
	
	public static int createRandom(){
		
		Random rand = new Random();
		/** 产生5000-10000之间的随机数*/
		int randNum = rand.nextInt(10000)+10000;
		
		return randNum;
	}
	
	public static void createSignature(){
		
		Integer d = null;
		BigInteger tempD = null;
		BigInteger p = null;
		BigInteger G = null;
		BigInteger x1 = null;
		BigInteger n = null;
		BigInteger m = null;
		BigInteger t = null;
		String strP = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC2F";
		String strG = "0279BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798";
		String strN = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141";
		BigInteger r = new BigInteger("1", 10);
		BigInteger i = new BigInteger("2", 10);
		BigInteger k = new BigInteger("0", 10);
		
		
		
		d = createRandom();
		tempD = new BigInteger(d.toString(), 10); 
		p = new BigInteger(strP, 16);
		G = new BigInteger(strG, 16);
		x1 = G.multiply(new BigInteger(d.toString(), 10));
		n = new BigInteger(strN, 16);
		r = x1.remainder(n);   //将x1对n取模后的结果返回给r
		
		while(r.toString().equals("0")){   //若r=1，则转向第一步
			d = createRandom();
			p = new BigInteger(strP, 16);
			G = new BigInteger(strG, 16);
			x1 = G.multiply(new BigInteger(d.toString(), 10));
			n = new BigInteger(strN, 16);
			r = x1.remainder(n);
		}
		
		
		/** 第4步
		 * 先n/d,取整，并临时赋值给m，同时记下t=m*d,t值很接近n
		 * 然后初始m的值为m*d+d
		 * 接下来在while循环的条件里判断m%n=1是否成立，若成立，则求得m，否则进入循环
		 *        m = t*i+d (m扩大一个t的倍数）
		 *        while(m<n*i)
		 *             m += d
		 * */
		m = n.divide(tempD);                  //m = n/d
		t = m.multiply(tempD);                //t = m*d
		m = m.multiply(tempD).add(tempD);      //m = m*d+d
		
		while(!m.remainder(n).toString().equals("1")){ //判断 m%n = 1 是否成立，若成立，则求得m，否则进入循环
//			System.out.print(i+": ");    //
//			System.out.println(m.remainder(n));   //
			m = t.multiply(i).add(tempD.multiply(k.add(new BigInteger("1", 10))));          //m = t*i+d
			
//			int j=0;
			while(m.subtract(n.multiply(i)).compareTo(new BigInteger("0", 10))==-1){  //判断 m < n*i 是否成立
//				j++;
//				System.out.println("j:"+j);
				m = m.add(tempD);                   //m += d
				k.add(new BigInteger("1", 10));
				
			}
				
			i = i.add(new BigInteger("1", 10));      //i++
		}
		m = m.divide(tempD);
		
		System.out.println();    //
		System.out.println("d = "+d);       //
		System.out.println("p = "+p);
		System.out.println("G = "+G);     //	
		System.out.println("d*G = "+x1);     //
		System.out.println("r = "+r);       //
		System.out.println("n = "+n);
		System.out.println("m = "+m);	
	}
	
	
	public static BigInteger mDivision(BigInteger d ,BigInteger n){
		BigInteger m = null;
		BigInteger t = null;
		BigInteger i = new BigInteger("2", 10);
		BigInteger k = new BigInteger("0", 10);
		
		m = n.divide(d);                  //m = n/d
		t = m.multiply(d);                //t = m*d
		m = m.multiply(d).add(d);      //m = m*d+d
		int j=0;
		while(!m.remainder(n).toString().equals("1")){ //判断 m%n = 1 是否成立，若成立，则求得m，否则进入循环
			System.out.print(i+": ");    //
			System.out.println(m.remainder(n));   //
			m = t.multiply(i).add(d.multiply(k.add(new BigInteger("1", 10))));          //m = t*i+d
			
			
			while(m.subtract(n.multiply(i)).compareTo(new BigInteger("0", 10))==-1){  //判断 m < n*i 是否成立
				j++;
				System.out.println("内部j:"+j);
				m = m.add(d);                   //m += d
				k = k.add(new BigInteger("1", 10));
			}
				
			i = i.add(new BigInteger("1", 10));      //i++
		}
		m = m.divide(d);
		System.out.println("循环处k ="+k);
		return m;
	}
	
	
	
	
	
	
	
	/** 测试*/
	public static void main(String[] args) {
		
//		TestSecp256k1 t = new TestSecp256k1();
//		System.out.println(TestSecp256k1.createRandom());
		long startTime = System.currentTimeMillis();    //获取开始时间
		TestSecp256k1.createSignature();
		long endTime = System.currentTimeMillis();    //获取结束时间
		
		System.out.println();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
		
		
	}
}






