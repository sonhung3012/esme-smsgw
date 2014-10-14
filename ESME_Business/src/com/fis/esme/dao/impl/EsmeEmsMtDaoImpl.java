package com.fis.esme.dao.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeEmsMtDao;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeEmsMtDaoImpl extends
GenericDaoSpringHibernateTemplate<EsmeEmsMt, Long> implements EsmeEmsMtDao{

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt EsmeEmsMt) throws Exception {
		// TODO Auto-generated method stub
		return findAll(EsmeEmsMt, false);
	}

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt EsmeEmsMt, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return findAll(EsmeEmsMt, firstItemIndex, maxItems, false);
	}
	private Criteria createCriteria(EsmeEmsMt EsmeEmsMt,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeEmsMt.class);
		Disjunction or = Restrictions.disjunction();
		if (EsmeEmsMt != null) {
			Long id = EsmeEmsMt.getMtId();
			EsmeEmsMo esmeEmsMo = EsmeEmsMt.getEsmeEmsMo();
			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("mt_id", id));
				} else {
					or.add(Restrictions.like("mt_id", "%" + id + "%"));
				}
			}
			if (esmeEmsMo != null) {
				or.add(Restrictions.eq("esmeEmsMo", esmeEmsMo));
			}

		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeEmsMt.class,
						orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}		
	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt EsmeEmsMt, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(EsmeEmsMt, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt EsmeEmsMt, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(EsmeEmsMt, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt EsmeEmsMt, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria finder = createCriteria(EsmeEmsMt, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int count(EsmeEmsMt EsmeEmsMt, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = createCriteria(EsmeEmsMt, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(EsmeEmsMt EsmeEmsMt) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countAll() throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = getSession().createCriteria(EsmeEmsMt.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EsmeEmsMt findByMtID(long id) throws Exception {
		// TODO Auto-generated method stub
		Session session = getSession();
		String strSQl = "select * from esme_sms_mt where mo_sequence_number = :mo_sequence_number";
		SQLQuery query = session.createSQLQuery(strSQl);
		query.addEntity(EsmeEmsMt.class);
		query.setLong("mo_sequence_number", id);
		List<EsmeEmsMt> lst = query.list();
		if ((lst != null) && (lst.size() == 1)) {
			return lst.get(0);
		}
		return null;
	}
	

}
