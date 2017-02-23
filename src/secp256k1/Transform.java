package secp256k1;

import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class Transform {

	public static String toHex(BigInteger m){
		String m_Hex = new HexBinaryAdapter().marshal(m.toByteArray());
		
		return m_Hex;
	}
	
	public static BigInteger toBigInt(String m){
		BigInteger m_Big = new BigInteger(m, 16);
		
		return m_Big;
	}
}
