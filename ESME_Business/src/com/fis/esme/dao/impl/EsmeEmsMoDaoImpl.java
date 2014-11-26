package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeEmsMoDao;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeEmsMoDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeEmsMo, Long> implements EsmeEmsMoDao {

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo) throws Exception {

		// TODO Auto-generated method stub
		return findAll(esmeEmsMo, false);
	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, int firstItemIndex, int maxItems) throws Exception {

		// TODO Auto-generated method stub
		return findAll(esmeEmsMo, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeEmsMo esmeEmsMo, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeEmsMo.class);
		// Disjunction or = Restrictions.disjunction();
		Conjunction or = Restrictions.conjunction();
		or.add(Restrictions.eq("type", "3"));
		if (esmeEmsMo != null) {
			Long id = esmeEmsMo.getMoId();
			String status = esmeEmsMo.getStatus();
			String type = esmeEmsMo.getType();
			String message = esmeEmsMo.getMessage();
			String msisdn = esmeEmsMo.getMsisdn();
			String shortCode = esmeEmsMo.getShortCode();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("mo_id", id));
				} else {
					or.add(Restrictions.like("mo_id", "%" + id + "%"));
				}
			}

			if (!FieldChecker.isEmptyString(status)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(status);
				if (checkStartsWith != null) {
					or.add(Expression.like("status", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("status", status).ignoreCase());
					} else {
						or.add(Restrictions.like("status", "%" + status + "%").ignoreCase());
					}
				}
			}
			if (!FieldChecker.isEmptyString(msisdn)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(msisdn);
				if (checkStartsWith != null) {
					or.add(Expression.like("msisdn", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("msisdn", msisdn).ignoreCase());
					} else {
						or.add(Restrictions.like("msisdn", "%" + msisdn + "%").ignoreCase());
					}
				}
			}
			if (!FieldChecker.isEmptyString(message)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(message);
				if (checkStartsWith != null) {
					or.add(Expression.like("message", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("message", message).ignoreCase());
					} else {
						or.add(Restrictions.like("message", "%" + message + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(shortCode)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(shortCode);
				if (checkStartsWith != null) {
					or.add(Expression.like("shortCode", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("shortCode", shortCode).ignoreCase());
					} else {
						or.add(Restrictions.like("shortCode", "%" + shortCode + "%").ignoreCase());
					}
				}
			}
		}

		finder.add(or);

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeEmsMo.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeEmsMo, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeEmsMo, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		// TODO Auto-generated method stub
		Criteria finder = createCriteria(esmeEmsMo, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int count(EsmeEmsMo esmeEmsMo, boolean exactMatch) throws Exception {

		// TODO Auto-generated method stub
		Criteria counter = createCriteria(esmeEmsMo, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(EsmeEmsMo esmeEmsMo) throws Exception {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countAll() throws Exception {

		// TODO Auto-generated method stub
		Criteria counter = getSession().createCriteria(EsmeEmsMo.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		// TODO Auto-generated method stub
		return false;
	}

}
