package com.fis.esme.component;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import eu.livotov.tpt.i18n.TM;

public class CustomTableMap extends Table
{
	public static final String INDEXED_COLUMN_CAPTION = "STT";
//	private int maxCellLength = 20;
	
	public CustomTableMap(String caption, Container data)
	{
		super(caption, data);
		this.setColumnCollapsingAllowed(true);
		this.setStyleName("table-header-center");
		setIndexedColumnVisible(true);
		addRowTooltip();
//		Collection<?> cols = data.getContainerPropertyIds();
//		for (Object col : cols)
//		{
//			this.setColumnExpandRatio(col, 1);
//		}
	}
	
	private void addRowTooltip()
	{
		this.setItemDescriptionGenerator(new ItemDescriptionGenerator()
		{
			@Override
			public String generateDescription(Component source, Object itemId,
					Object propertyId)
			{
				Property pro = getContainerProperty(itemId, propertyId);
				
				if (pro != null)
				{
					String str = formatPropertyValue(itemId, propertyId, pro);
					if (str != null)
					{
						str = str.replace("\\n", "</br>");
					}
					return str;
				}
				return null;
			}
		});
	}
	
	public void setIndexedColumnVisible(boolean visible)
	{
		if (visible)
		{
			this.addGeneratedColumn(INDEXED_COLUMN_CAPTION, new IndexedColumnGenerator());
			this.setColumnWidth(INDEXED_COLUMN_CAPTION, 30);
			this.setColumnAlignment(INDEXED_COLUMN_CAPTION, Table.ALIGN_RIGHT);
//			this.setVisibleColumns(getRealVisibleColumns(getVisibleColumns()));
		}
		else
		{
			this.removeGeneratedColumn(INDEXED_COLUMN_CAPTION);
		}
	}
	
	private Object[] getRealVisibleColumns(Object[] visibleCols)
	{
		ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(visibleCols));
		int index = list.indexOf(INDEXED_COLUMN_CAPTION); 
		if (index > 0)
		{
			Object obj = list.set(0, INDEXED_COLUMN_CAPTION);
			list.set(index, obj);
		}
		else if (index < 0)
		{
			list.add(0, INDEXED_COLUMN_CAPTION);
		}
		
		return list.toArray();
	}
	
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property)
	{
		String pid = (String) colId;
		Object val = property.getValue();
		
		if (val == null)
		{
			return super.formatPropertyValue(rowId, colId, property);
		}
		
		if (val instanceof Number)
		{
			return NumberFormat.getInstance().format(val);
		}
		else if ("status".equals(pid))
		{
			if ((property.getValue().equals("1")))
			{
				return TM.get("main.common.table.status.active");
			}
			else
			{
				return TM.get("main.common.table.status.inactive");
			}
		}
		
		return super.formatPropertyValue(rowId, colId, property);
	}
	
	public String getColumnAlignment(Object propertyId) 
	{
		try
		{
			Class<?> t = getContainerDataSource().getType(propertyId);
			if (t == int.class || t == double.class || t == short.class || t == float.class || t == byte.class || t == long.class || t.getSuperclass() == Number.class)
			{
				return Table.ALIGN_RIGHT;
			}
			
			return super.getColumnAlignment(propertyId);
		}
		catch (Exception e)
		{
			return super.getColumnAlignment(propertyId);
		}
    }
	
	class IndexedColumnGenerator implements ColumnGenerator
	{
		private int number = 1;
		
		public IndexedColumnGenerator()
		{
			
		}
		
		@Override
		public Component generateCell(Table source, Object itemId,
				Object columnId)
		{
			Container container = source.getContainerDataSource();
			
			if (container instanceof BeanItemContainer<?>)
			{
				BeanItemContainer<?> con = (BeanItemContainer<?>) source.getContainerDataSource();
				int id = con.indexOfId(itemId);
				return new Label("" + (id+1));
			}
			else
			{
				return new Label("");
			}
		}
	}

//	public int getMaxCellLength()
//	{
//		return maxCellLength;
//	}
//
//	public void setMaxCellLength(int maxCellLength)
//	{
//		this.maxCellLength = maxCellLength;
//	}
}
