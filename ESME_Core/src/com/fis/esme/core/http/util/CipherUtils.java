package com.fis.esme.core.http.util;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class CipherUtils
{
	
	private static byte[] key = { 0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41,
			0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79 }; // "thisIsASecretKey";
														 
	public static String encrypt(String strToEncrypt)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final String encryptedString = Base64.encode(cipher
					.doFinal(strToEncrypt.getBytes()));
			return encryptedString;
		}
		catch (Exception e)
		{
		}
		return null;
		
	}
	
	public static String decrypt(String strToDecrypt)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			final String decryptedString = new String(cipher.doFinal(Base64
					.decode(strToDecrypt)));
			return decryptedString;
		}
		catch (Exception e)
		{
		}
		return null;
	}
	
	public static void main(String args[])
	{
		String strKey = CipherUtils
				.encrypt("192.168.1.1|CP_01|admin|2013/12/06 15:00:00");
		System.out.println(strKey.length() + ": " + strKey);
		System.out.println(CipherUtils.decrypt(strKey));
	}
}
