package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeLanguageDao;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeLanguageDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeLanguage, Long> implements EsmeLanguageDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage) throws Exception {

		return findAll(esmeLanguage, false);
	}

	public List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeLanguage, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeLanguage esmeLanguage, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeLanguage.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeLanguage != null) {
			long id = esmeLanguage.getLanguageId();
			String code = esmeLanguage.getCode();
			String name = esmeLanguage.getName();
			String desc = esmeLanguage.getIsDefault();
			String status = esmeLanguage.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("languageid", id));
				} else {
					or.add(Restrictions.like("languageid", "%" + id + "%"));
				}
			}

			if (!FieldChecker.isEmptyString(code)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(code);
				if (checkStartsWith != null) {
					or.add(Expression.like("code", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("code", code).ignoreCase());
					} else {
						or.add(Restrictions.like("code", "%" + code + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(name)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(name);
				if (checkStartsWith != null) {
					or.add(Expression.like("name", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("name", name).ignoreCase());
					} else {
						or.add(Restrictions.like("name", "%" + name + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(desc)) {
				if (exactMatch) {
					or.add(Restrictions.eq("isDefault", desc).ignoreCase());
				} else {
					or.add(Restrictions.like("isDefault", "%" + desc + "%").ignoreCase());
				}
			}

			if (!FieldChecker.isEmptyString(status)) {
				or.add(Restrictions.eq("status", status));
			}
		}

		finder.add(or);

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeLanguage.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeLanguage, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeLanguage, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeLanguage esmeLanguage, boolean exactMatch) throws Exception {

		Criteria counter = createCriteria(esmeLanguage, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeLanguage, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeLanguage esmeLanguage) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeLanguage.class);
		criteria.add(Expression.eq("code", esmeLanguage.getCode()));
		criteria.setProjection(Projections.count("languageId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		// Session session = getSession();
		// SQLQuery query;
		//
		// query = session
		// .createSQLQuery(" SELECT COUNT(ROWID) total FROM PRC_LANGUAGE WHERE LANGUAGE_ID = :FKFieldValue ");
		// query.setInteger("FKFieldValue", id);
		// query.addScalar("total", Hibernate.INTEGER);
		// if ((Integer) query.uniqueResult() > 0)
		// return true;
		//
		// return false;
		EsmeLanguage obj = new EsmeLanguage();
		obj.setLanguageId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] { EsmeMessageContent.class };

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeLanguage", obj));
			criteria.setProjection(Projections.count("esmeLanguage"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeLanguage.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	public void updateAllLanguage(String field, String value) throws Exception {

		Session session = getSession();
		String sql = "update esme_language set " + field + " = :value ";
		Query query = session.createSQLQuery(sql);
		query.setParameter("value", value);
		query.executeUpdate();
	}

	public EsmeLanguage getDefaultLanguage() throws Exception {

		Session session = getSession();
		String strSQl = "select * from esme_language where is_default='1' and  status='1'";
		SQLQuery query = session.createSQLQuery(strSQl);
		query.addEntity(EsmeLanguage.class);
		List<EsmeLanguage> lst = query.list();
		if ((lst != null) && (lst.size() == 1)) {
			return lst.get(0);
		}

		throw new Exception("Erorr config defaul language");
	}

	public List<EsmeLanguage> getLanguageStatus() {

		Session session = getSession();
		String strSQl = "select * from esme_language where status='1'";
		SQLQuery query = session.createSQLQuery(strSQl);
		query.addEntity(EsmeLanguage.class);
		List<EsmeLanguage> lst = query.list();
		if (lst != null) {
			return (List<EsmeLanguage>) lst;
		}

		return null;
	}

}