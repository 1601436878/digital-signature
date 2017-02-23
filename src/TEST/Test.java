package TEST;

import java.security.*;
import java.security.interfaces.*;
import javax.crypto.*;

public class Test {
    
    protected static RSAPrivateKey privateKey;
    protected static RSAPublicKey publicKey;
    protected static byte[] resultBytes; 
    
    public Test(){
        try{
            String message = "广东省广州市越秀区";
            
            System.out.println("明文是" + message);
            
            //生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(1024);
            
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            
            //得到私钥和公钥
            privateKey =(RSAPrivateKey) keyPair.getPrivate();
            publicKey = (RSAPublicKey)keyPair.getPublic();
            
            
            //用公钥加密
            byte[] srcBytes = message.getBytes();
            resultBytes = Test.encrypt(publicKey, srcBytes);
            String result = new String(resultBytes);
            System.out.println("用公钥加密后密文是:" + result);
            
//            return privateKey;
//            //用私钥解密
//            byte[] decBytes = Test.decrypt(privateKey,resultBytes);
//            String dec = new String(decBytes);
//            System.out.println("用私钥加密后的结果是:" + dec);
        }catch(Exception e){
            e.printStackTrace();
        }
//        return null;
    }
    
    protected static byte[] encrypt(RSAPublicKey publicKey,byte[] srcBytes){
        if(publicKey != null){            
            try{
                //Cipher负责完成加密或解密工作，基于RSA
                Cipher cipher = Cipher.getInstance("RSA");
                
                //根据公钥，对Cipher对象进行初始化
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                
                //加密，结果保存进resultBytes，并返回
                byte[] resultBytes = cipher.doFinal(srcBytes);
                return resultBytes;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    
    protected static byte[] decrypt(RSAPrivateKey privateKey,byte[] encBytes){
        if(privateKey != null){
            try{
                Cipher cipher = Cipher.getInstance("RSA");
                
                //根据私钥对Cipher对象进行初始化
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                
                //解密并将结果保存进resultBytes
                byte[] decBytes = cipher.doFinal(encBytes);
                return decBytes;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
