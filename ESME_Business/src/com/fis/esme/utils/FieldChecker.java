package com.fis.esme.utils;


public class FieldChecker
{
	public static boolean classContainsField(Class aClass, String field)
	{
		try
		{
			return aClass.getDeclaredField(field) != null;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static boolean isEmptyString(String value)
	{
		return (value == null || "".equals(value));
	}
}
