package com.fis.esme.component;

import java.util.ArrayList;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public abstract class AbsCustomTableField<BEANTYPE> extends CustomField
{
	protected Table tbl;
	protected BeanItemContainer<BEANTYPE> data;
	protected boolean modified = false;
	
	public AbsCustomTableField()
	{
		initTable();
		initTableAction();
		setCompositionRoot(tbl);
	}
	
	public AbsCustomTableField(String caption)
	{
		this.setCaption(caption);
		initTable();
		initTableAction();
		setCompositionRoot(tbl);
	}
	
	@Override
	public Class<?> getType()
	{
		return ArrayList.class;
	}
	
	@Override
	public boolean isModified()
	{
		return modified;
	}
	
	/*
	 * @Override public void setPropertyDataSource(Property newDataSource) {
	 * Object value = newDataSource.getValue(); if (value instanceof List<?>) {
	 * 
	 * @SuppressWarnings("unchecked") List<BEANTYPE> beans = (List<BEANTYPE>)
	 * value; data.removeAllItems(); data.addAll(beans); //
	 * tbl.setPageLength(beans.size()); } else throw new
	 * ConversionException("Invalid type");
	 * 
	 * super.setPropertyDataSource(newDataSource); }
	 */

	@Override
	public Object getValue()
	{
		ArrayList<BEANTYPE> beans = new ArrayList<BEANTYPE>();
		for (Object itemId : data.getItemIds())
			beans.add(data.getItem(itemId).getBean());
		return beans;
	}
	
	/*
	 * @Override public void setReadOnly(boolean readOnly) {
	 * table.setEditable(!readOnly); super.setReadOnly(readOnly); }
	 */

	public abstract void initTable();
	
	public abstract void initTableAction();
	
	public void setModified(boolean modified)
	{
		this.modified = modified;
	}
	
}
