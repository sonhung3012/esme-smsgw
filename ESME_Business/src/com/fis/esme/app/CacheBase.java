package com.fis.esme.app;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.fis.framework.bo.GenericBo;
import com.fis.framework.service.ServiceLocator;

public abstract class CacheBase<T extends Serializable, V extends GenericBo> {
	private Class<?> type;
	Hashtable<Object, T> hashCode = null;
	Hashtable<Object, T> hashId = null;

	public CacheBase() throws Exception {
		ParameterizedType superclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.type = ((Class<?>) superclass.getActualTypeArguments()[1]);
		hashCode = new Hashtable<Object, T>();
		hashId = new Hashtable<Object, T>();
		V mcaActionBo = (V) ServiceLocator.createService(type);
		List<T> lst = getData(mcaActionBo);
		if (lst != null)
			for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
				T prcClass = (T) iterator.next();
				hashCode.put(getCode(prcClass), prcClass);
				hashId.put(getId(prcClass), prcClass);
			}

	}

	protected abstract List<T> getData(V Bo) throws Exception;

	abstract Object getCode(T t) throws Exception;

	abstract Object getId(T t) throws Exception;

	// public T getObjectByCode(Object code) {
	// return hashCode.get(code);
	// }
	public T getObjectByCode(Object code) throws Exception {
		T rs = this.hashCode.get(code);
		if (rs != null) {
			return rs;
		}
		return null;
		// throw new BusinessException("CODE_ERROR", "Code not exist :"
		// + code.toString());
	}

	public T getObjectById(Object id) throws Exception {
		T rs = this.hashId.get(id);
		if (rs != null) {
			return rs;
		}
		return null;
		// throw new BusinessException("ID_ERROR", "Id not exist :"
		// + id.toString());
	}
}
