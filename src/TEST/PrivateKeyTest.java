package TEST;

public class PrivateKeyTest {
    public static void main(String[] args){
        Test test = new Test();
        
//        System.out.println(Test.privateKey);
//        String msg = new String(Test.resultBytes);
        
        byte[] decBytes = Test.decrypt(Test.privateKey, Test.resultBytes);
        String dec = new String(decBytes);
        
        System.out.println("用私钥加密后的结果是:" + dec);
    }
}