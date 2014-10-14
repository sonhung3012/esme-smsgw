package com.fis.esme.util;

import com.vaadin.ui.Window;

import eu.livotov.tpt.i18n.TM;

public class MessageAlerter extends Window.Notification 
{
	private final int DELAY = 0;
	private static final MessageAlerter messenger = new MessageAlerter("");
	private static final MessageAlerter errorMessage = new MessageAlerter("",MessageAlerter.TYPE_ERROR_MESSAGE);
	private static final MessageAlerter trayMessage = new MessageAlerter("","",MessageAlerter.TYPE_TRAY_NOTIFICATION);
	
	public MessageAlerter(String message)
	{
		super(message);
		this.setDelayMsec(DELAY);
	}
	
	public MessageAlerter(String message, int type)
	{
		super(message,type);
		this.setDelayMsec(DELAY);
	}
	public MessageAlerter(String caption, String message, int type)
	{
		super(caption, message, type);
		this.setDelayMsec(DELAY);
	}
	
	public static MessageAlerter getMessenger(String message)
	{
		messenger.setCaption(message);
		return messenger;
	}
	
	public static MessageAlerter errorMessage(String message)
	{
		errorMessage.setCaption(message);
		return errorMessage;
	}
	public static MessageAlerter trayMessage(String caption, String message)
	{
		trayMessage.setCaption(caption);
		trayMessage.setDescription(message);
		return trayMessage;
	}
	
	public static void showMessage(Window win, String message)
	{
		win.showNotification(message);
	}
	
	public static void showMessageI18n(Window win, String key, Object... params)
	{
		win.showNotification(TM.get(key, params));
	}
	
	public static void showErrorMessage(Window win, String message)
	{
		win.showNotification(errorMessage(message));
	}
	
	public static void showErrorMessageI18n(Window win, String key, Object... params)
	{
		win.showNotification(errorMessage(TM.get(key,params)));
	}
}
