package secp256k1;

import java.math.BigInteger;
import java.util.Random;

 class TestSecp256k1 {
	
	public static int createRandom(){
		
		Random rand = new Random();
		/** ����5000-10000֮��������*/
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
		r = x1.remainder(n);   //��x1��nȡģ��Ľ�����ظ�r
		
		while(r.toString().equals("0")){   //��r=1����ת���һ��
			d = createRandom();
			p = new BigInteger(strP, 16);
			G = new BigInteger(strG, 16);
			x1 = G.multiply(new BigInteger(d.toString(), 10));
			n = new BigInteger(strN, 16);
			r = x1.remainder(n);
		}
		
		
		/** ��4��
		 * ��n/d,ȡ��������ʱ��ֵ��m��ͬʱ����t=m*d,tֵ�ܽӽ�n
		 * Ȼ���ʼm��ֵΪm*d+d
		 * ��������whileѭ�����������ж�m%n=1�Ƿ�������������������m���������ѭ��
		 *        m = t*i+d (m����һ��t�ı�����
		 *        while(m<n*i)
		 *             m += d
		 * */
		m = n.divide(tempD);                  //m = n/d
		t = m.multiply(tempD);                //t = m*d
		m = m.multiply(tempD).add(tempD);      //m = m*d+d
		
		while(!m.remainder(n).toString().equals("1")){ //�ж� m%n = 1 �Ƿ�������������������m���������ѭ��
//			System.out.print(i+": ");    //
//			System.out.println(m.remainder(n));   //
			m = t.multiply(i).add(tempD.multiply(k.add(new BigInteger("1", 10))));          //m = t*i+d
			
//			int j=0;
			while(m.subtract(n.multiply(i)).compareTo(new BigInteger("0", 10))==-1){  //�ж� m < n*i �Ƿ����
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
		while(!m.remainder(n).toString().equals("1")){ //�ж� m%n = 1 �Ƿ�������������������m���������ѭ��
			System.out.print(i+": ");    //
			System.out.println(m.remainder(n));   //
			m = t.multiply(i).add(d.multiply(k.add(new BigInteger("1", 10))));          //m = t*i+d
			
			
			while(m.subtract(n.multiply(i)).compareTo(new BigInteger("0", 10))==-1){  //�ж� m < n*i �Ƿ����
				j++;
				System.out.println("�ڲ�j:"+j);
				m = m.add(d);                   //m += d
				k = k.add(new BigInteger("1", 10));
			}
				
			i = i.add(new BigInteger("1", 10));      //i++
		}
		m = m.divide(d);
		System.out.println("ѭ����k ="+k);
		return m;
	}
	
	
	
	
	
	
	
	/** ����*/
	public static void main(String[] args) {
		
//		TestSecp256k1 t = new TestSecp256k1();
//		System.out.println(TestSecp256k1.createRandom());
		long startTime = System.currentTimeMillis();    //��ȡ��ʼʱ��
		TestSecp256k1.createSignature();
		long endTime = System.currentTimeMillis();    //��ȡ����ʱ��
		
		System.out.println();
		System.out.println("��������ʱ�䣺" + (endTime - startTime) + "ms");    //�����������ʱ��
		
		
	}
}






