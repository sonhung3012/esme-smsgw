package com.fis.esme;

import java.io.Serializable;

import com.fis.esme.form.FormIsdnSpecial;
import com.fis.esme.form.FormSmsRouting;
import com.vaadin.terminal.ThemeResource;

public class Dashboard implements Serializable {
	private int id = 0;
	private ThemeResource icon;
	private String caption;
	private int parent;
	private String functionName;
	private Class cls = null;
	private boolean showOnDashboard;
	private boolean multiLevel = true;

	public Dashboard(int id, ThemeResource icon, String caption, Class cls,
			boolean showOnDashboard) {
		this.id = id;
		this.icon = icon;
		this.caption = caption;
		this.cls = cls;
		this.showOnDashboard = showOnDashboard;
	}

	public Dashboard(int id, ThemeResource icon, String caption,
			String functionName, int parent, boolean showOnDashboard) {
		this.id = id;
		this.icon = icon;
		this.caption = caption;
		this.functionName = functionName;
		this.cls = null;
		this.parent = parent;
		this.showOnDashboard = showOnDashboard;
	}

	public Dashboard(ThemeResource icon, String caption, String functionName,
			int parent, boolean showOnDashboard) {
		this.id = 0;
		this.icon = icon;
		this.caption = caption;
		this.functionName = functionName;
		this.cls = null;
		this.parent = parent;
		this.showOnDashboard = showOnDashboard;
	}

	public Dashboard(ThemeResource icon, String caption, Class cls, int parent,
			boolean showOnDashboard) {
		this.id = 0;
		this.icon = icon;
		this.caption = caption;
		this.functionName = null;
		this.cls = cls;
		this.parent = parent;
		this.showOnDashboard = showOnDashboard;
	}

	public Dashboard(int id, ThemeResource icon, String caption, Class cls,
			int parent, boolean showOnDashboard, boolean multiLevel) {
		this.id = 0;
		this.icon = icon;
		this.caption = caption;
		this.functionName = null;
		this.cls = cls;
		this.parent = parent;
		this.showOnDashboard = showOnDashboard;
		this.multiLevel = multiLevel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ThemeResource getIcon() {
		return icon;
	}

	public void setIcon(ThemeResource icon) {
		this.icon = icon;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Class getCls() {
		return cls;
	}

	public void setCls(Class cls) {
		this.cls = cls;
	}

	public boolean isMultiLevel() {
		return multiLevel;
	}

	public void setMultiLevel(boolean multiLevel) {
		this.multiLevel = multiLevel;
	}

	public boolean isShowOnDashboard() {
		return showOnDashboard;
	}

	public void setShowOnDashboard(boolean showOnDashboard) {
		this.showOnDashboard = showOnDashboard;
	}

}
