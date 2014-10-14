package com.fis.framework.bo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import com.fis.framework.dao.DaoFactory;
import com.fis.framework.dao.GenericDao;

public class GenericBoImp<T, PK extends Serializable, DaoInternal extends GenericDao<T, PK>>
		implements GenericBo<T, PK> {
	protected DaoInternal mDaoInternal;
	private Class<?> typeDao;

	protected Logger logger;

	protected Class<?> getPersistenceType() {
		return this.typeDao;
	}

	

	protected Logger getLogger() {
		return this.logger;
	}

	
	public void printMemory() {
		Double totalMemory = Double.valueOf(Runtime.getRuntime().totalMemory());
		Double freeMemory = Double.valueOf(Runtime.getRuntime().freeMemory());

		System.out
				.println("*****---booooooooooo-----------------------------------------------------------------**********");
		System.out.println("totalMemory:" + totalMemory.doubleValue()
				/ 1048576.0D);
		System.out.println("freeMemory:" + freeMemory.doubleValue()
				/ 1048576.0D);
		System.out.println("user Memory:"
				+ (totalMemory.doubleValue() - freeMemory.doubleValue())
				/ 1048576.0D);
		System.out.println("%freeMemory:" + 100.0D * freeMemory.doubleValue()
				/ totalMemory.doubleValue() + "%");
		System.out.println("maxMemory:"
				+ Double.valueOf(Runtime.getRuntime().maxMemory() / 1048576L));
		System.out
				.println("*****----booooooooooo----------------------------------------------------------------**********");
	}

	public DaoInternal getDaoInternal() {
		return  this.mDaoInternal;
	}

	public void setDaoInternal(DaoInternal genericDao) {
		this.mDaoInternal = genericDao;
	}

	public void delete(T persistentObject) throws Exception {
		getDaoInternal().delete(persistentObject);
	}

	public void deleteAll() throws Exception {
		getDaoInternal().deleteAll();
	}

	public List<T> findAll() throws Exception {
		return getDaoInternal().findAll();
	}

	public List<T> findByExample(T exampleInstance) throws Exception {
		return getDaoInternal().findByExample(exampleInstance);
	}

	public T findById(PK id) throws Exception {
		return getDaoInternal().findById(id);
	}

	public T findByIdUnique(PK id) throws Exception {
		return getDaoInternal().findByIdUnique(id);
	}

	public PK persist(T newInstance) throws Exception {
		return getDaoInternal().persist(newInstance);
	}

	public void update(T transientObject) throws Exception {
		getDaoInternal().update(transientObject);
	}

	public Date getSystemDate() throws Exception {
		return getDaoInternal().getSystemDate();
	}

	public T findByIdLock(PK id, boolean lock) throws Exception {
		return getDaoInternal().findByIdLock(id, lock);
	}
}