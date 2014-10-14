package com.fis.esme.util;

import java.text.Collator;
import java.util.Locale;

public class StringUtil
{
	public static int compareVietnameseString(String o1, String o2)
	{
		return compareString(o1, o2, new Locale("vi"));
	}
	
	public static int compareString(String o1, String o2, Locale locale)
	{
		final String DELIMITERS = "\\p{Cntrl}\\s\\p{Punct}\u0080-\u00BF\u2000-\uFFFF";
		Collator primary = null;
		Collator secondary = null;
		if (primary == null)
		{
			primary = Collator.getInstance(locale);
			secondary = (Collator) primary.clone();
			secondary.setStrength(Collator.SECONDARY);
		}
		
		int result;
//		String o1 = str1;
//		String o2 = str;
		String[] s1 = (" " + o1).split("[" + DELIMITERS + "]+");
		String[] s2 = (" " + o2).split("[" + DELIMITERS + "]+");
		for (int i = 1; i < s1.length && i < s2.length; i++)
		{
			result = secondary.compare(s1[i], s2[i]);
			if (result != 0)
			{
				return result;
			}
		}
		
		if (s1.length > s2.length)
		{
			return 1;
		}
		else if (s1.length < s2.length)
		{
			return -1;
		}
		
		for (int i = 1; i < s1.length; i++)
		{
			result = primary.compare(s1[i], s2[i]);
			if (result != 0)
			{
				return result;
			}
			
		}
		
		s1 = (o1 + " ").split("[^" + DELIMITERS + "]+");
		s2 = (o2 + " ").split("[^" + DELIMITERS + "]+");
		
		for (int i = 1; i < s1.length - 1 && i < s2.length - 1; i++)
		{
			result = primary.compare(s1[i], s2[i]);
			if (result != 0)
			{
				return result;
			}
		}
		
		result = primary.compare(s1[0], s2[0]);
		
		if (result != 0)
		{
			return result;
		}
		
		return primary.compare(o1, o2);
	}
}
