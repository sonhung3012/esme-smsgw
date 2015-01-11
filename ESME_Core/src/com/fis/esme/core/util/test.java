package com.fis.esme.core.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.Key;
import java.util.Formatter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.smpp.Data;
import org.smpp.pdu.SubmitSM;

import com.fss.util.StringUtil;
import com.fss.util.WildcardFilter;

public class test {
	private static CharsetEncoder encoder;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		 System.out.println(StringUtil.encrypt("123456", "SHA"));
//		 
//		 Key key = new SecretKeySpec(secret.getBytes(), "SHA");
//
//		// Encrypt
//		Cipher cipher = Cipher.getInstance( "SHA");
//		cipher.init(Cipher.ENCRYPT_MODE, key);
//		byte[] encryptedData = cipher.doFinal("123456");
//
//		// Decrypt
//		cipher.init(Cipher.DECRYPT_MODE, key)
//		byte[] decryptedData = cipher.doFinal(encryptedData);
//		 System.out.println(StringUtil. ("123456", "SHA"));
		
		String msg = "မဂၤလာပါ";
		String msg1 = "taiG ";
		String msg5 = "1";
		System.out.println(bytesToHexString(msg.getBytes(Data.ENC_UTF16)));
		System.out.println(bytesToHexString(msg.getBytes(Data.ENC_UTF16_LE)));
		System.out.println(bytesToHexString(msg.getBytes(Data.ENC_UTF16_BEM)));
		System.out.println(bytesToHexString(msg.getBytes(Data.ENC_UTF16_BE)));
		System.out.println(bytesToHexString(msg.getBytes(Data.ENC_UTF16_LEM)));
//
//		encoder = Charset.forName("SCGSM").newEncoder();
//		if (!onlyGSM7BitCharacter(msg)) {
//			System.out.println("oki");
//		}
//
//		if (!onlyGSM7BitCharacter(" hdhdhhdhh ")) {
//			System.out.println("oki1");
//		}
//		String msg3 = java.net.URLEncoder.encode("101910021064101C102C1015102B","UTF-8");	
//		System.out.println(msg3);
		//String msg3 = java.net.URLEncoder.encode(msg,Data.ENC_UTF8);	
		
		//System.out.println(AsnUtil.arrayByte2Hex(msg.getBytes(Data.ENC_UTF16),0,msg.getBytes(Data.ENC_UTF16).length,""));
//
//		System.out.println(bytesToHexString(msg.getBytes(Data.ENC_UTF8)));
//		
//		System.out.println(bytesToHexString(msg.getBytes(Data.ENC_UTF16)));
//		
//		System.out.println(java.net.URLEncoder.encode(msg,Data.ENC_UTF16));		
//		
//		System.out.println(java.net.URLEncoder.encode(msg,Data.ENC_UTF16_LE));
//		
//		System.out.println(java.net.URLEncoder.encode(msg,Data.ENC_UTF8));
//		
//		System.out.println(java.net.URLEncoder.encode(msg,Data.ENC_UTF16_BE));
//		
//		System.out.println(java.net.URLEncoder.encode(msg,Data.ENC_UTF16_BEM));
		
//
//		String msg6 = java.net.URLEncoder.encode(msg1,Data.ENC_UTF8);
//		System.out.println(bytesToHexString(msg1.getBytes(Data.ENC_UTF8)));
//		System.out.println(msg6);
//		System.out.println(AsnUtil.arrayByte2Hex(msg1.getBytes(Data.ENC_UTF8),0,msg1.getBytes(Data.ENC_UTF8).length,""));
//		
//		System.out.println(AsnUtil.arrayByte2Hex(msg.getBytes(Data.ENC_UTF16_BE),0,msg.getBytes(Data.ENC_UTF16_LE).length,""));
		//System.out.println(AsnUtil.arrayByte2Hex2(msg.getBytes(Data.ENC_UTF16_LE),0,msg.getBytes(Data.ENC_UTF16_LE).length,""));
		//System.out.println(AsnUtil.bytesToHex(msg.getBytes(Data.ENC_UTF16_LE)));
		
		//hexEncode();
		// System.out.println(AsnUtil.arrayByte2Hex(msg.getBytes(),0,msg.getBytes().length,""));
		// System.out.println(AsnUtil.arrayByte2Hex(msg.getBytes("UTF-8"),0,msg.getBytes().length,""));
		// System.out.println(AsnUtil.bytesToHex(msg.getBytes(Data.ENC_UTF16_LE)));
		// System.out.println(AsnUtil.arrayByte2Hex2(msg.getBytes(Data.ENC_UTF16_LE),0,msg.getBytes(Data.ENC_UTF16_LE).length,""));
//		System.out.println(AsnUtil.arrayByte2Hex3(msg.getBytes(Data.ENC_UTF16_BE)));
//		
//		System.out.println("99999999999999999");
//		for (int i = 0; i < msg.getBytes(Data.ENC_UTF16_BE).length; i++) {
//			System.out.println(msg.getBytes(Data.ENC_UTF16_BE)[i]);
//		}

		// SubmitSM submitRequest = new SubmitSM();
		// submitRequest.setShortMessage("ðʝɟɲŋ", Data.ENC_UTF16_LE);
		// System.out.println(submitRequest.getShortMessageData());
		// encoder = Charset.forName("SCGSM").newEncoder();
		// System.out.println(java.net.URLEncoder.encode("noi dung gì",
		// "UTF-8"));
		//
		// int iMaxLengthSingleMessage = 160;
		// int iMaxLengthMultipleMessage = 153;
		// String strEncoding = Data.ENC_ASCII;
		// byte bDataCoding = 0x00;
		// // Neu tin nhan co chua ky tu ngoai bang GSM 7 bit
		// if (!onlyGSM7BitCharacter(msg)) {
		// iMaxLengthSingleMessage = 70;
		// iMaxLengthMultipleMessage = 66;
		// strEncoding = Data.ENC_UTF16_BE;
		// bDataCoding = 0x08;
		// }
		//
		// String str=new String(msg.getBytes(),"UTF-8");
		//
		// System.out.println(AsnUtil.arrayByte2Hex(msg.getBytes(),0,msg.getBytes().length,""));
		//
		// //String msg1 = java.net.URLEncoder.encode(msg, strEncoding);
		// //System.out.println(str);

	}

	private static boolean onlyGSM7BitCharacter(String strContent) {
		return encoder.canEncode(strContent);
	}
	
	
	public static String bytesToHexString(byte[] bytes) {  
	    StringBuilder sb = new StringBuilder(bytes.length * 2);  
	  
	    Formatter formatter = new Formatter(sb);  
	    for (byte b : bytes) {  
	        formatter.format("%02x", b);  
	    }  
	  
	    return sb.toString();  
	}  
	
	public static Appendable hexEncode(byte buf[], Appendable sb) throws IOException     
	{     
	    final Formatter formatter = new Formatter(sb);     
	    for (int i = 0; i < buf.length; i++)     
	    {     
	        int low = buf[i] & 0xF;  
	        int high = (buf[i] >> 8) & 0xF;  
	        sb.append(Character.forDigit(high, 16));  
	        sb.append(Character.forDigit(low, 16));  
	    }     
	    return sb;     
	}  
}
