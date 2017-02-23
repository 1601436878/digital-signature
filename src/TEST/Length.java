package TEST;

import java.security.interfaces.ECPrivateKey;

import org.apache.commons.codec.binary.Base64;

public class Length {
	public static void main(String[] args) {
		String s = "11111111111111111111111110111001";
		System.out.println("³¤¶ÈÎª£º"+s.length());
		String str = "aaaaaaa";
		byte [] data = Base64.encodeBase64(str.getBytes());
		System.out.println(new String (Base64.decodeBase64(data)));
	}
}
