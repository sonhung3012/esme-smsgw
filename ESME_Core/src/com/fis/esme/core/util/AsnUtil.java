package com.fis.esme.core.util;

import com.fss.asn1.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Formatter;


/**
 * <p>
 * Title: Capture call center
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * <p>
 * Company: FIS-TES
 * </p>
 * 
 * @author HungVM
 * @version 1.0
 */
public class AsnUtil extends ASNUtil {
	public AsnUtil() {
	}

	public static String convertByteArray2Hex(byte[] b, int ofset, int index) {
		int indexs = ofset + index;
		StringBuilder s = new StringBuilder(2 * b.length);
		for (int i = indexs; i > ofset; i--) {
			int v = b[i] & 0xff;
			s.append((char) Hexhars[v >> 4]);
			s.append((char) Hexhars[v & 0xf]);
		}

		return "0x" + s.toString();
	}

	/**
	 * Author: LiemLT Convert bytes to IpAddress
	 * 
	 * @param b
	 *            byte[]
	 * @param ofset
	 *            int
	 * @param iLength
	 *            int
	 * @return String
	 * @throws UnknownHostException
	 */
	public static String convertByteToIpAddress(byte[] b, int ofset, int iLength) throws UnknownHostException {
		byte[] bt = new byte[iLength];

		System.arraycopy(b, ofset + 1, bt, 0, iLength);

		return InetAddress.getByAddress(bt).getHostAddress();
	}

	/**
	 * Author: LiemLT Convert bytes to port
	 * 
	 * @param b
	 *            byte[]
	 * @param ofset
	 *            int
	 * @param iLength
	 *            int
	 * @return int
	 */
	public static int convertByteToPort(byte[] b, int ofset, int iLength) {
		byte[] bt = new byte[iLength];
		int iLastIndex = iLength + ofset;
		int iIndex = 0;
		for (int i = iLastIndex; i > ofset; i--) {
			bt[iIndex] = b[i];
			iIndex++;
		}
		return byteArrayToInt(bt, 0);
	}

	private static final byte[] Hexhars = {

	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String arrayByte2Hex(byte[] b, int ofset, int index, String prefix) {
		int indexs = ofset + index;
		StringBuilder s = new StringBuilder(2 * b.length);
		for (int i = indexs - 1; i >= ofset; i--) {
			int v = b[i] & 0xff;
			s.append((char) Hexhars[v >> 4]);
			s.append((char) Hexhars[v & 0xf]);
		}
		return prefix + s.toString();
	}
	public static String arrayByte2Hex3(byte[] b) {
		StringBuilder s = new StringBuilder(b.length);
		for (int i = 0; i < b.length; i++) {
			s.append(b[i]);
		}
		return s.toString();
	}
	
	public static String arrayByte2Hex2(byte[] b, int ofset, int index, String prefix) {
		int indexs = ofset + index;
		StringBuilder s = new StringBuilder(2 * b.length);
		for (int i = indexs - 1; i >= ofset; i--) {
			int v = b[i] & 0xff;
			s.append((char) Hexhars[v >>> 4]);
			s.append((char) Hexhars[v & 0xf]);
		}
		return prefix + s.toString();
	}
	public static String bytesToHexString(byte[] bytes) {  
	    StringBuilder sb = new StringBuilder(bytes.length * 2);  
	  
	    Formatter formatter = new Formatter(sb);  
	    for (byte b : bytes) {  
	        formatter.format("%02x", b);  
	    }  
	  
	    return sb.toString();  
	}  
	
	public static String bytesToHex(byte[] bytes) {
	    final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xff;
	        hexChars[j * 2] = hexArray[v >> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

	public static String getH255ExtentionNumber(byte[] bytes) {
		return new String(bytes);
	}

	public static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	public static int byteArrayToInt2(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 2; i++) {
			int shift = (2 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	public static String getStringValue(byte[] arrByte, int position, int leng) throws Exception {
		int j = 0;
		if (arrByte.length == 0 || arrByte.length < position + leng) {
			throw new Exception("The position and length is not correct ");
		}
		for (int i = position + leng; i > position; i--) {
			if (arrByte[i] > 39 || arrByte[i] < 30) {
				j++;
			}
		}
		byte[] arrByteTemp = new byte[leng];
		System.arraycopy(arrByte, position, arrByteTemp, 0, leng - j);

		return new String(arrByteTemp);

	}

	public static int checkEmptyPosition(byte[] b, int position) {
		int iResult = 0;
		boolean bChecking = true;

		while (bChecking && iResult + position < b.length) {
			for (int i = 1; i <= 4; i++) {
				if (b[position + iResult + i] != 0) {
					return iResult;
				}
			}
			iResult += 4;
		}

		return iResult;
	}

	
}
