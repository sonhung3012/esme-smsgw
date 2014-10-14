package com.fis.esme.classes;
//package com.fis.prc.classes;
//
//
//import com.fis.prc.util.MessageAlerter;
//import com.vaadin.data.validator.AbstractValidator;
//
//public class PasswordValidator extends AbstractValidator
//{
//	
//	private String str;
//
//	public PasswordValidator(String errorMessage)
//	{
//		super(errorMessage);
//	}
//	
//	@Override
//	public boolean isValid(Object value)
//	{
//		if (value == null || "".equals(value.toString().trim())
//				|| "***************".equals(value))
//		{
//			return true;
//		}
//		try
//		{
//			str = SecGroupData.getPasswordLength();
//			if (str != null && !"".equals(str.trim()))
//			{
//				int x = Integer.parseInt(str);
//				if (value.toString().trim().length() < x)
//				{
//					return false;
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			MessageAlerter.showErrorMessage(null, e.getMessage());
//			return false;
//		}
//		
//		return true;
//	}
//	
//	@Override
//	public void validate(Object value) throws InvalidValueException
//	{
//		if (!isValid(value))
//		{
//			String message = getErrorMessage().replace("{0}", str);
//			throw new InvalidValueException(message);
//		}
//	}
//	
//}
