package com.fis.esme.component;

import com.fis.esme.util.SearchObj;

public interface PanelActionProvider {

	public static final int ACTION_ADD = 1;
	public static final int ACTION_EDIT = 2;
	public static final int ACTION_ADD_COPY = 3;
	public static final int ACTION_SEARCH = 4;
	public static final int ACTION_FIELDCSEARCH = 5;
	public static final int ACTION_SEARCH_ADDNEW = 6;
//	public static final int ACTION_REREGISTER = 4;
//	public static final int ACTION_RESUBC = 5;
//	public static final int ACTION_REPORT = 6;
//	public static final int ACTION_SUBCSEARCH = 8;

	public static final int ACTION_NONE = 0;

	void accept();
	
	void delete(Object object);

	void showDialog(Object object);

//	void reregister();

//	void report();

	void searchOrAddNew(String key);
	
	void search();

//	void subcSearch(String key);

	void fieldSearch(SearchObj searchObj);
	
	void export();

	String getPermission();
}
