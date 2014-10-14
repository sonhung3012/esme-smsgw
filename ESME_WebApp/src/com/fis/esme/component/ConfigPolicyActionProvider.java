package com.fis.esme.component;

import com.fis.esme.util.SearchObj;

public interface ConfigPolicyActionProvider {

	public static final int ACTION_COPY = 1;
	public static final int ACTION_DELETE = 2;
	public static final int ACTION_CANCEL = 3;

	public static final int ACTION_NONE = 0;

	void cpCopy();

	void cpApply();

	void cpCancelApply();

	void cpDelete();

	void cpCancel();

	// String getPermission();
}
