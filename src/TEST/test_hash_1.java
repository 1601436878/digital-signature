package TEST;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public  class test_hash_1
{

  /**
   * 传入文本内容，返回 SHA-256 串
   * 
   * @param strText
   * @return
   */
  public String SHA256(final String strText)
  {
    return SHA(strText, "SHA-256");
  }

  /**
   * 传入文本内容，返回 SHA-512 串
   * 
   * @param strText
   * @return
   */
  public String SHA512(final String strText)
  {
    return SHA(strText, "SHA-512");
  }

  /**
   * 字符串 SHA 加密
   * 
   * @param strSourceText
   * @return
   */
  private String SHA(final String strText, final String strType)
  {
    // 返回值
    String strResult = null;

    // 是否是有效字符串
    if (strText != null && strText.length() > 0)
    {
      try
      {
        // SHA 加密开始
        // 创建加密对象 并傳入加密類型
        MessageDigest messageDigest = MessageDigest.getInstance(strType);
        // 传入要加密的字符串
        messageDigest.update(strText.getBytes());
        // 得到 byte 類型结果
        byte byteBuffer[] = messageDigest.digest();

        // 將 byte 轉換爲 string
        StringBuffer strHexString = new StringBuffer();
        // 遍歷 byte buffer
        System.out.println("length:"+byteBuffer.length);
        for (int i = 0; i < byteBuffer.length; i++)
        {
//          String hex = Integer.toHexString(0xff & byteBuffer[i]);
        	int v = 0xff & byteBuffer[i];
//        	System.out.println(v);
        	String hex = Integer.toHexString( v);
          if (hex.length() == 1)
          {
            strHexString.append('0');
          }
          strHexString.append(hex+"");
        }
        // 得到返回結果
        strResult = strHexString.toString();
      }
      catch (NoSuchAlgorithmException e)
      {
        e.printStackTrace();
      }
    }

    return strResult;
  }
  
  public static void main(String[] args) {
	  test_hash_1 encrypt = new test_hash_1();
	  String str = "hello world";
	  String result = encrypt.SHA256(str);
	  System.out.println(str+"-->"+result);
	  System.out.println("hello world"+"-->"+encrypt.SHA256("hellow world"));
}
}