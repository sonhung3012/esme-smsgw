package com.fis.esme.core.smpp.util;

import java.util.Arrays;

public class Unicode {
	private static char[] SPECIAL_CHARACTERS = { 'À', 'Á', 'Â', 'Ã', 'È', 'É',
		'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
		'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
		'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
		'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
		'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
		'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
		'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
		'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
		'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
		'ữ', 'Ự', 'ự' };
	public static String spliNullCharacter(String strInput) {
		boolean blValidate = false;
		String strValidateTemp = null;
		if (strInput.length() > 0) {
			strValidateTemp = strInput.substring(0, 1);
			if (strValidateTemp == null || strValidateTemp.equalsIgnoreCase("")
					|| strValidateTemp.equalsIgnoreCase(" ")) {
				char[] arrCharactor = strInput.toCharArray();
				for (int index = 0; index < arrCharactor.length; index++) {
					if (index % 2 == 0) {
						if (arrCharactor[index] != ' ') {
							return strInput;
						}
					}
				}
				blValidate = true;
				if (blValidate) {
					char[] buffer = new char[(strInput.length() / 2) + 1];
					int i = 0;
					for (int index = 0; index < strInput.length(); index++) {
						if (index % 2 != 0) {
							buffer[i] = strInput.charAt(index);
							i++;
						}
					}
					return String.valueOf(buffer);
				}
			}
		}
		return strInput;
	}

	public static String rejectSpecialCharacter(String strInput) {
		int maxLength = Math.min(strInput.length(), 236);
		char[] buffer = new char[maxLength];
		int n = 0;
		for (int i = 0; i < maxLength; i++) {
			char ch = strInput.charAt(i);
			int iChar = (int) ch;
			if ((iChar >= 32 && iChar <= 126)
					|| Arrays.binarySearch(SPECIAL_CHARACTERS, ch) >= 0) {
				buffer[n] = ch;
				n++;
			} else {
				buffer[n] = '\0';
			}
		}
		return String.valueOf(buffer, 0, n).trim();
	}

	public static void main(String[] args) {
		String test = "ï¿½ï¿½ ï¿½DK vÃ  ( D K)  ";
		String test1 = "ï¿½   ï¿½ D  K ";
		System.out.println(test);
		System.out.println(rejectSpecialCharacter(test));
		System.out.println(spliNullCharacter(test1));
	}
}
