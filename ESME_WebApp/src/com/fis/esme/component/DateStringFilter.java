package com.fis.esme.component;

import java.util.Date;

import com.fis.esme.util.FormUtil;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * Simple string filter for matching items that start with or contain a
 * specified string. The matching can be case-sensitive or case-insensitive.
 * 
 * This filter also directly supports in-memory filtering. When performing
 * in-memory filtering, values of other types are converted using toString(),
 * but other (lazy container) implementations do not need to perform such
 * conversions and might not support values of different types.
 * 
 * Note that this filter is modeled after the pre-6.6 filtering mechanisms, and
 * might not be very efficient e.g. for database filtering.
 * 
 * TODO this might still change
 * 
 * @since 6.6
 */
public final class DateStringFilter implements Filter
{
	
	final Object propertyId;
	final String filterString;
	final boolean ignoreCase;
	final boolean onlyMatchPrefix;
	
	public DateStringFilter(Object propertyId, String filterString,
			boolean ignoreCase, boolean onlyMatchPrefix)
	{
		this.propertyId = propertyId;
		this.filterString = ignoreCase ? filterString.toLowerCase()
				: filterString;
		this.ignoreCase = ignoreCase;
		this.onlyMatchPrefix = onlyMatchPrefix;
	}
	
	public boolean passesFilter(Object itemId, Item item)
	{
		final Property p = item.getItemProperty(propertyId);
		if (p == null || p.toString() == null)
		{
			return false;
		}
		
		Object obj = p.getValue();
		String value = "";
		
		if (obj instanceof Date)
		{
			value = FormUtil.simpleShortDateFormat.format((Date)obj);
		}
		else
		{
			value = ignoreCase ? p.toString().toLowerCase() : p
				.toString();
		}
		
		if (onlyMatchPrefix)
		{
			if (!value.startsWith(filterString))
			{
				return false;
			}
		}
		else
		{
			if (!value.contains(filterString))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean appliesToProperty(Object propertyId)
	{
		return this.propertyId.equals(propertyId);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		
		// Only ones of the objects of the same class can be equal
		if (!(obj instanceof DateStringFilter))
		{
			return false;
		}
		final DateStringFilter o = (DateStringFilter) obj;
		
		// Checks the properties one by one
		if (propertyId != o.propertyId && o.propertyId != null
				&& !o.propertyId.equals(propertyId))
		{
			return false;
		}
		if (filterString != o.filterString && o.filterString != null
				&& !o.filterString.equals(filterString))
		{
			return false;
		}
		if (ignoreCase != o.ignoreCase)
		{
			return false;
		}
		if (onlyMatchPrefix != o.onlyMatchPrefix)
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return (propertyId != null ? propertyId.hashCode() : 0)
				^ (filterString != null ? filterString.hashCode() : 0);
	}
	
	/**
	 * Returns the property identifier to which this filter applies.
	 * 
	 * @return property id
	 */
	public Object getPropertyId()
	{
		return propertyId;
	}
	
	/**
	 * Returns the filter string.
	 * 
	 * Note: this method is intended only for implementations of lazy string
	 * filters and may change in the future.
	 * 
	 * @return filter string given to the constructor
	 */
	public String getFilterString()
	{
		return filterString;
	}
	
	/**
	 * Returns whether the filter is case-insensitive or case-sensitive.
	 * 
	 * Note: this method is intended only for implementations of lazy string
	 * filters and may change in the future.
	 * 
	 * @return true if performing case-insensitive filtering, false for
	 *         case-sensitive
	 */
	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}
	
	/**
	 * Returns true if the filter only applies to the beginning of the value
	 * string, false for any location in the value.
	 * 
	 * Note: this method is intended only for implementations of lazy string
	 * filters and may change in the future.
	 * 
	 * @return true if checking for matches at the beginning of the value only,
	 *         false if matching any part of value
	 */
	public boolean isOnlyMatchPrefix()
	{
		return onlyMatchPrefix;
	}
}
