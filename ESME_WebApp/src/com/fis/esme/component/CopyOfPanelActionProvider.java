package com.fis.esme.component;

public interface CopyOfPanelActionProvider
{
	public static final int ACTION_ADD = 1;
	public static final int ACTION_EDIT = 2;
	public static final int ACTION_SEARCH = 3;
	public static final int ACTION_ADD_COPY = 4;
	public static final int ACTION_NONE = 0;
//	public static final int ACTION_ADD;
	void showDialog();
	void accept();
	void delete();
//	void edit();
//	void search();
	String getPermission();
	void export();
}
