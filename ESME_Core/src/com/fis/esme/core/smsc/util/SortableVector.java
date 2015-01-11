package com.fis.esme.core.smsc.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Vector;

public class SortableVector {
	private Vector vtKeys = new Vector();
	private Hashtable htValues = new Hashtable();

	class Comparer implements Comparator {
		public int compare(Object obj1, Object obj2) {
			return obj2.toString().compareToIgnoreCase(obj1.toString());
		}
	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public boolean checkContainkey(String keys) {
		return vtKeys.contains(keys);
	}

	/***
	 * 
	 * @param strKey
	 * @throws Exception
	 */
	public void addElement(String strKey, String strValue) {
		if (!checkContainkey(strKey)) {
			vtKeys.add(strKey);
			synchronized (vtKeys) {
				Collections.sort(vtKeys, new Comparer());
				htValues.put(strKey, strValue);
			}
		} else {
			synchronized (vtKeys) {
				Collections.sort(vtKeys, new Comparer());
				htValues.put(strKey, strValue);
			}
		}

	}

	/**
	 * 
	 * @param strkey
	 */
	public void removeElement(String strkey) {
		vtKeys.remove(strkey);
		htValues.remove(strkey);
	}

	/**
	 * 
	 * @param strKey
	 * @return
	 */
	public String getValue(String strKey) {
		if (!checkContainkey(strKey)) {
			return null;
		}
		return (String) htValues.get(strKey);
	}

	/**
	 * 
	 * @return
	 */
	public Vector getKeys() {
		return vtKeys;
	}

}
