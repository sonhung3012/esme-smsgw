package com.fis.esme.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.KeyValue;

import com.fis.esme.admin.AppClient;
import com.fis.esme.admin.SessionData;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

import eu.livotov.tpt.i18n.TM;

public class FormUtil
{
	
	public static final String stringShortDateFormat = TM
			.get("main.common.short.date.format");
	public static final SimpleDateFormat simpleShortDateFormat = new SimpleDateFormat(
			stringShortDateFormat);
	
	public static final String stringDateFormat = TM
			.get("main.common.full.date.format");
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			stringDateFormat);
	
	public static final Integer[] notSetDateFields = new Integer[]{
			Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND,
			Calendar.MILLISECOND };
	
	public static void showException(Component com, Exception ex)
	{
		ex.printStackTrace();
		com.getWindow().showNotification(
				MessageAlerter.errorMessage(ex.getMessage()));
	}
	
	public static void showException(Window win, Exception ex)
	{
		ex.printStackTrace();
		win.showNotification(MessageAlerter.errorMessage(ex.getMessage()));
	}
	
	private static Calendar doNotSetField(Calendar calendar, Integer[] fields)
	{
		for (Integer integer : fields)
		{
			calendar.set(integer, 00);
		}
		return calendar;
	}
	
	public static boolean msisdnValidateUpload(String msisdn) {
		String regexp = TM.get("form.upload.number.msisdn.1");
//		if (msisdn.length() == 8) {
//			regexp = "[0-9]{8}";
//		} else if (msisdn.length() == 9) {
//			regexp = "[0-9]{9}";
//		} else if (msisdn.length() == 10) {
//			regexp = "[0-9]{10}";
//		} else if (msisdn.length() == 11) {
//			regexp = "[0-9]{11}";
//		}
		pattern = Pattern.compile(regexp);
		matcher = pattern.matcher(msisdn.trim());
		return matcher.matches();
	}
	
	private static Calendar doNotSetField(Calendar calendar,
			Dictionary[] dictionary)
	{
		for (Dictionary dic : dictionary)
		{
			calendar.set((Integer) dic.getKey(), (Integer) dic.getValue());
		}
		return calendar;
	}
	
	private static Calendar doNotSetField(Calendar calendar,
			KeyValue[] dictionary)
	{
		for (KeyValue dic : dictionary)
		{
			calendar.set((Integer) dic.getKey(), (Integer) dic.getValue());
		}
		return calendar;
	}
	
	public static Date getToday(Integer[] notSetFields)
	{
		Calendar cal = Calendar.getInstance();
		if (notSetFields != null)
		{
			cal = doNotSetField(cal, notSetFields);
		}
		return cal.getTime();
	}
	
	public static Calendar toCalendar(Date date, Integer[] notSetFields)
	{
		Calendar cal = Calendar.getInstance();
		if (notSetFields != null)
		{
			cal = doNotSetField(cal, notSetFields);
		}
		return cal;
	}
	
	public static Date toDate(Date date, Dictionary[] dictionary)
	{
		if (date != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (dictionary.length > 0)
			{
				cal = doNotSetField(cal, dictionary);
			}
			return cal.getTime();
		}
		else
			return date;
	}
	
	public static Date toDate(Date date, KeyValue[] dictionary)
	{
		if (date != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (dictionary.length > 0)
			{
				cal = doNotSetField(cal, dictionary);
			}
			return cal.getTime();
		}
		else
			return date;
	}
	
	public static Date toDate(Date date, Integer[] notSetFields)
	{
		if (date != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (notSetFields != null)
			{
				cal = doNotSetField(cal, notSetFields);
			}
			return cal.getTime();
		}
		else
			return date;
	}
	
	public static Date stringToDate(String dateString, String strFormat)
	{
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
			return simpleDateFormat.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
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
			'ữ', 'Ự', 'ự', };
	private static char[] REPLACEMENTS = { 'A', 'A', 'A', 'A', 'E', 'E', 'E',
			'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
			'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
			'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
			'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
			'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
			'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
			'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
			'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
			'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
			'U', 'u', };
	
	public static String stringEncodingToANSI(String s)
	{
		int maxLength = Math.min(s.length(), 236);
		char[] buffer = new char[maxLength];
		int n = 0;
		for (int i = 0; i < maxLength; i++)
		{
			char ch = s.charAt(i);
			int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			if (index >= 0)
			{
				buffer[n] = REPLACEMENTS[index];
			}
			else
			{
				buffer[n] = ch;
			}
			// skip not printable characters
			if (buffer[n] > 31)
			{
				n++;
			}
		}
		// skip trailing slashes
		while (n > 0 && buffer[n - 1] == '/')
		{
			n--;
		}
		return String.valueOf(buffer, 0, n);
	}
	
	public static boolean findUnicodeInString(String s)
	{
		int maxLength = Math.min(s.length(), 236);
		char[] buffer = new char[maxLength];
		int n = 0;
		for (int i = 0; i < maxLength; i++)
		{
			char ch = s.charAt(i);
			int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			if (index >= 0)
			{
				return true;
			}
			if (buffer[n] > 31)
			{
				n++;
			}
		}
		return false;
	}
	
	public static void setTabEnabled(TabSheet tabsheet)
	{
		Iterator<Component> tabs = tabsheet.getComponentIterator();
		
		while (tabs.hasNext())
		{
			Component tab = tabs.next();
			boolean enabled = AppClient.getPermission(tab.getClass().getName())
					.contains("S");
			tabsheet.getTab(tab).setEnabled(enabled);
		}
	}
	
	public static Comparator<Object> stringComparator(final boolean unicode)
	{
		
		Comparator<Object> comparator = new Comparator<Object>()
		{
			@Override
			public int compare(Object o1, Object o2)
			{
				final String DELIMITERS = "\\p{Cntrl}\\s\\p{Punct}\u0080-\u00BF\u2000-\uFFFF";
				Collator primary = null;
				Collator secondary = null;
				if (primary == null)
				{
					primary = Collator.getInstance(new Locale("vi"));
					secondary = (Collator) primary.clone();
					secondary.setStrength(Collator.SECONDARY);
				}
				
				int result;
				o1 = o1.toString();
				o2 = o2.toString();
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
		};
		return comparator;
	}
	
	public static Comparator<Object> integerComparator()
	{
		Comparator<Object> comparator = new Comparator<Object>()
		{
			@Override
			public int compare(Object arg0, Object arg1)
			{
				int a = Integer.parseInt(arg0.toString());
				int b = Integer.parseInt(arg1.toString());
				if (a > b)
					return 1;
				else if (a == b)
					return 0;
				else if (a < b)
					return -1;
				else
					return 0;
			}
		};
		return comparator;
	}
	
	public static String getErrorMessage(String caption, String message)
	{
		return "<span style='color:red'><b>" + caption + "</b> " + message
				+ "</span>";
	}
	
	public static String getErrorMessageI18n(String caption, String message)
	{
		return getErrorMessage(caption, TM.get(message));
	}
	
	public static String getErrorMessage2(String caption, String message,
			String caption2, String message2)
	{
		return "<span style='color:red'><b>" + caption + "</b> " + message
				+ " <b>" + caption2 + "</b> " + message2 + "</span>";
	}
	
	public static String getErrorMessage2I18n(String caption, String message,
			String caption2, String message2)
	{
		return getErrorMessage2(caption, TM.get(message), caption2,
				TM.get(message2));
	}
	
	public static void clearCache(Window window)
	{
		System.out.println("Clear cache...");
		if (window != null)
		{
			window.showNotification(TM.get("main.window.clearcache"));
		}
		System.out.println("Clear cache successful");
	}
	
	static Pattern pattern = null;
	static Matcher matcher = null;
	
	public static boolean msisdnValidate(String regexp, String msisdn)
	{
		pattern = Pattern.compile(regexp);
		matcher = pattern.matcher(msisdn.trim());
		return matcher.matches();
	}
	
	public static boolean msisdnValidate(String msisdn)
	{
		String regexp = "";
		if (msisdn.startsWith("9"))
		{
			regexp = "[0-9]{9}";
		}
		else if (msisdn.startsWith("1") || msisdn.startsWith("09"))
		{
			regexp = "[0-9]{10}";
		}
		else if (msisdn.startsWith("01"))
		{
			regexp = "[0-9]{11}";
		}
		pattern = Pattern.compile(regexp);
		matcher = pattern.matcher(msisdn.trim());
		return matcher.matches();
	}
	
	public static String cutMSISDN(String msisdn)
	{
		msisdn = msisdn.replace("\uFEFF", "");
		if (msisdn.startsWith("840"))
			msisdn = msisdn.replaceFirst("840", "");
		else if (msisdn.startsWith("84"))
			msisdn = msisdn.replaceFirst("84", "");
		else if (msisdn.startsWith("+84"))
			msisdn = msisdn.replaceFirst("+84", "");
		else if (msisdn.startsWith("0"))
			msisdn = msisdn.replaceFirst("0", "");
		else if (msisdn.startsWith("+0"))
			msisdn = msisdn.replaceFirst("+0", "");
		return msisdn;
	}
	
	public static boolean mobifoneValidateMSISDN(String msisdn)
	{
		if (msisdn.startsWith("90") || msisdn.startsWith("93")
				|| msisdn.startsWith("122") || msisdn.startsWith("126")
				|| msisdn.startsWith("128") || msisdn.startsWith("120")
				|| msisdn.startsWith("121"))
			return true;
		else
			return false;
	}
	
	public static void createZipFile(String zipFilePath, List<File> files,
			boolean renameForVnp)
	{
		try
		{
			byte[] buffer = new byte[4096];
			File zipFile = new File(zipFilePath);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFile));
			out.setLevel(Deflater.DEFAULT_COMPRESSION);
			FileInputStream in;
			int stt = 1;
			for (File file : files)
			{
				// try {
				in = new FileInputStream(file);
				// } catch (Exception e) {
				// continue;
				// }
				// String fp = file.getPath().replaceAll("20100922", "hh");
				// file.getName();
				
				String callId = file.getName().substring(0,
						file.getName().indexOf("_"));
				if (renameForVnp)
				{
					// int t = (10 - callId.toString().length());
					
					// if (callId.toString().length() < 10)
					// {
					// for (int i = 0; i < t; i++)
					// {
					// segmentID = "0" + segmentID;
					// }
					// }
					String segmentID = callId.toString();
					if (file.getName().contains(".xls"))
						out.putNextEntry(new ZipEntry(file.getName()));
					else
						out.putNextEntry(new ZipEntry(TM.get(
								"callhistory.filename.rename", stt, segmentID)));
					// out.putNextEntry(new ZipEntry("Call1_" + stt + "_"
					// + segmentID + "_1_29.mp3"));
				}
				else
				{
					out.putNextEntry(new ZipEntry(file.getName()));
				}
				// out.putNextEntry(new ZipEntry(file.getName()));
				int len;
				while ((len = in.read(buffer)) > 0)
				{
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				in.close();
				stt++;
			}
			out.close();
		}
		catch (IllegalArgumentException iae)
		{
			iae.printStackTrace();
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			
		}
	}
	
	public static void createZipFile2(String zipFilePath, List<File> files)
	{
		try
		{
			byte[] buffer = new byte[4096];
			File zipFile = new File(zipFilePath);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFile));
			out.setLevel(Deflater.DEFAULT_COMPRESSION);
			FileInputStream in;
			for (File file : files)
			{
				// try {
				in = new FileInputStream(file);
				// } catch (Exception e) {
				// continue;
				// }
				// String fp = file.getPath().replaceAll("20100922", "hh");
				// file.getName();
				
				out.putNextEntry(new ZipEntry(file.getName()));
				int len;
				while ((len = in.read(buffer)) > 0)
				{
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		}
		catch (IllegalArgumentException iae)
		{
			iae.printStackTrace();
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public static boolean stringIsNullOrEmty(Object obj)
	{
		if (obj == null)
			return true;
		else
		{
			String str = obj.toString();
			if (str.length() <= 0)
				return true;
		}
		return false;
	}
	
	public static boolean stringIsNullOrEmty(String str)
	{
		if (str == null)
			return true;
		else
		{
			if (str.length() <= 0)
				return true;
		}
		return false;
	}
	
	public static Long stringHMSToMillis(String strHMS)
	{
		if (stringIsNullOrEmty(strHMS))
			return null;
		
		String[] arrStr = strHMS.split(":");
		if (arrStr.length == 3)
		{
			int h = Integer.parseInt(arrStr[0]);
			int m = Integer.parseInt(arrStr[1]);
			int s = Integer.parseInt(arrStr[2]);
			long mH = h * (60 * (60 * 1000));
			long mM = m * (60 * 1000);
			long mS = s * 1000;
			return (mH + mM + mS);
		}
		else
			return null;
	}
	
	public static Object copyObject(Object copyObject)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(copyObject);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object deepCopy = ois.readObject();
			return deepCopy;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
