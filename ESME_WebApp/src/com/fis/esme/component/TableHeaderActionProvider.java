package com.fis.esme.component;

import com.fis.esme.util.SearchObj;

public interface TableHeaderActionProvider {
	public static final int ACTION_FIELDCSEARCH = 9;
	public static final int ACTION_NONE = 0;

	void fieldSearch(SearchObj searchObj);
}
