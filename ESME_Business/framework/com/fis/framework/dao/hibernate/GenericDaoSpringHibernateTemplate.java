package com.fis.framework.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.fis.framework.dao.GenericDao;
import com.fis.framework.service.ThreadLocalContext;

public class GenericDaoSpringHibernateTemplate<T, PK extends Serializable>
		implements GenericDao<T, PK> {
	private Class<?> type;
	private SessionFactory sessionFactory;
	protected Logger logger;
	private Session session;

	protected Class<?> getPersistenceType() {
		return this.type;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	protected Logger getLogger() {
		return this.logger;
	}

	public GenericDaoSpringHibernateTemplate() {
		ParameterizedType superclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.type = ((Class) superclass.getActualTypeArguments()[0]);
		this.logger = Logger.getLogger(this.type);
	}

	public void delete(T persistentObject) throws Exception {
		getSession().delete(persistentObject);
	}

	public List<T> findAll() throws Exception {
		return getSession().createCriteria(this.type.getName()).list();
	}

	public List<T> findByExample(T exampleInstance) throws Exception {
		List results = getSession().createCriteria(this.type.getName())
				.add(Example.create(exampleInstance)).list();
		this.logger.debug("find by example successful, result size: "
				+ results.size());
		return results;
	}

	public T findById(PK id) throws Exception {
		return (T) getSession().get(this.type, id);
	}

	public T findByIdUnique(PK id) throws Exception {
		return (T) getSession().get(this.type, id);
	}

	public T findByIdLock(PK id, boolean lock) throws Exception {
		if (lock) {
			return (T) getSession().get(this.type, id, LockMode.UPGRADE);
		}
		return (T) getSession().get(this.type, id);
	}

	public void flush() throws Exception {
		getSession().flush();
	}

	public PK persist(T newInstance) throws Exception {
		Session session = getSession();

		return (PK) session.save(newInstance);
	}

	public Session getSession() {
		 return (Session) ThreadLocalContext.get();
	}
	public void setSession(Session session) {
		 this.session=session;
	}
	public void update(T transientObject) throws Exception {
		getSession().update(transientObject);
		
	}

	public Date getSystemDate() throws Exception {
		String strDateFormat = "dd-MM-yyyy-HH:mm:ss";
		String strDateFormatOracle = "dd-MM-yyyy-HH24:mi:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(strDateFormat);
		String strColumn = "systemdate";

		String strSqlSysDate = " Select to_char(sysdate, '"
				+ strDateFormatOracle + "') " + strColumn + "  FROM dual";

		SQLQuery sqlQuery = getSession().createSQLQuery(strSqlSysDate)
				.addScalar(strColumn, Hibernate.STRING);

		String strDate = sqlQuery.list().get(0).toString();
		Date date = formatter.parse(strDate);

		System.out.println(date);

		return date;
	}

	public void clear() throws Exception {
		getSession().clear();
	}

	public void deleteAll() throws Exception {
	}
}