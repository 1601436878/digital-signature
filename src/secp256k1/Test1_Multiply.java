package secp256k1;
/**
 * �������
 * @author wangchao
 *
 */
public class Test1_Multiply {
	private int p = 67;
	private int n ;
	private Node G;
	private int a = 0;
	private int b = 7;
	
	/**
	 * ������
	 * @param p
	 * @param q
	 * @return
	 */
	public Node diffAdd(Node p,Node q){
		//  ���c	
		int c = (q.getY()-p.getY())*mDivision(q.getX()-p.getX(),this.p);   		//System.out.println("c:"+c);		// c mod p
		c = c % this.p;
//		System.out.println("ȡ�����c"+c);
		Node result = new Node();
		// ������
		int rx = c*c - p.getX()-q.getX();
		rx = rx%this.p;
		rx = rx>=0?rx:rx+this.p;
		int ry = c*(p.getX()-rx)-p.getY();
		ry = ry%this.p;
		ry = ry>=0?ry:ry+this.p;
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
	public Node sameAdd(Node node ){
		int c = (3*node.getX()*node.getX()+a)*mDivision(2*node.getY(),this.p);	//System.out.println("ȡ��֮ǰc��"+c);
		c = c % p; 											//	System.out.println("ȡ��֮��c"+c);System.out.println("#"+node.getX()+":"+node.getY()+"#");
		c = c>=0?c:c+this.p;
		int rx = (c*c - 2*node.getX())%this.p;				//	System.out.println("rx"+rx);
		int ry = (c*(node.getX()-rx) - node.getY());
		rx = rx>=0? rx:rx+this.p;							//  System.out.println("rx"+rx);
		ry = ry%this.p;
		ry = ry>=0?ry:ry+this.p;

		Node result = new Node(rx,ry);
		return result;
	}
	
	// s * x % m = 1 
	public int mDivision(int s ,int m){
//		int n = m/s;				
//		int result = s*n%m;		  // 44 * 1 % 67
//		while(result != 1){
//			++n;					System.out.println("s:"+s+"n:"+n+"m"+m);
//			result = s*n%m;
//		}
//		return n;

		int i = 1 ;
		int result = (m * i + 1)%s;
		while(result != 0){
			++i;		
			result = (m*i+1)%s;						//System.out.println(result+"#"+i);
		}
		return (m*i+1)/s;
		
	}
	
	public Node Multiply(int s , Node g){
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
	}
	

	public static void main(String[] args) {
		Node a = new Node(1,2);
		Node b = new Node(2,22);
		Test1_Multiply test = new Test1_Multiply() ;
		
//		Node result = test.Multiply(76, b);	//�˷�
		Node result = test.sameAdd(b);		//ͬ��ӱ�
//		Node result = test.diffAdd(b,a);	//������
//		System.out.println(test.mDivision(44, 633333));
		System.out.println(result.getX()+":"+result.getY());
		
	}
}



/**
 * ����
 * @author wangchao
 *
 */
class Node{
	private int x ;
	private int y ;
	
	public Node(){
		
	}
	
	public Node(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
