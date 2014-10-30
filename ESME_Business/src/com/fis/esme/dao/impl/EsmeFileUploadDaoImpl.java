package com.fis.esme.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeFileUploadDao;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeFileUploadDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeFileUpload, Long> implements EsmeFileUploadDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload) throws Exception {

		return findAll(esmeFileUpload, false);
	}

	public List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeFileUpload, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeFileUpload esmeFileUpload, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeFileUpload.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeFileUpload != null) {
			Long id = esmeFileUpload.getFileUploadId();
			String name = esmeFileUpload.getType();
			String status = esmeFileUpload.getStatus();
			EsmeServices service = esmeFileUpload.getEsmeServices();

			if (service != null) {
				finder.add(Restrictions.eq("esmeServices", service));
			}
			if (id != null) {
				if (exactMatch) {
					or.add(Restrictions.eq("fileUploadId", id));
				} else {
					or.add(Restrictions.like("fileUploadId", "%" + id + "%"));
				}
			}

			if (!FieldChecker.isEmptyString(name)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(name);
				if (checkStartsWith != null) {
					or.add(Expression.like("type", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("type", name).ignoreCase());
					} else {
						or.add(Restrictions.like("type", "%" + name + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(status)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(status);
				if (checkStartsWith != null) {
					or.add(Expression.like("status", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					or.add(Restrictions.eq("status", status));
				}
			}
		}

		finder.add(or);

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeFileUpload.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeFileUpload, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeFileUpload, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeFileUpload esmeFileUpload, boolean exactMatch) throws Exception {

		Criteria counter = createCriteria(esmeFileUpload, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeFileUpload, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeFileUpload esmeFileUpload) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeFileUpload.class);
		criteria.add(Expression.eq("type", esmeFileUpload.getFileName()));
		criteria.setProjection(Projections.count("fileUploadId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeFileUpload.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public List<EsmeFileUpload> findAllByDate(Date fromDate, Date toDate) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeFileUpload.class);
		criteria.add(Restrictions.between("createDate", fromDate, toDate));
		criteria.addOrder(Order.asc("createDate"));
		List<EsmeFileUpload> list = criteria.list();
		return list;
	}

	@Override
	public List<EsmeFileUpload> findAllInDateByField(String field, Date fromDate, Date toDate) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeFileUpload.class);
		criteria.add(Expression.between(field, fromDate, toDate));
		return criteria.list();
	}

}