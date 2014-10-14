package com.fis.esme.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSmsRoutingDao;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.utils.FieldChecker;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSmsRoutingDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeSmsRouting, Long> implements
		EsmeSmsRoutingDao {

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting) throws Exception {
		return findAll(searchEntity, esmeSmsRouting, null, false, -1, -1, true);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, int firstItemIndex, int maxItems)
			throws Exception {
		return findAll(searchEntity, esmeSmsRouting, null, false,
				firstItemIndex, maxItems, true);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, boolean exactMatch) throws Exception {
		return findAll(searchEntity, esmeSmsRouting, null, false, -1, -1,
				exactMatch);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return findAll(searchEntity, esmeSmsRouting, null, false,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(searchEntity, esmeSmsRouting,
				sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}
		return finder.list();

	}

	@Override
	public int count(SearchEntity searchEntity, EsmeSmsRouting esmeSmsRouting,
			boolean exactMatch) throws Exception {
		Criteria counter = createCriteria(searchEntity, esmeSmsRouting, null,
				false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting) throws Exception {
		Criteria criteria = getSession().createCriteria(EsmeSmsRouting.class);
		if (esmeSmsRouting.getEsmeCp() != null)
			criteria.add(Expression.eq("esmeCp", esmeSmsRouting.getEsmeCp()));
		if (esmeSmsRouting.getEsmeShortCode() != null)
			criteria.add(Expression.eq("esmeShortCode",
					esmeSmsRouting.getEsmeShortCode()));
		if (esmeSmsRouting.getEsmeSmsCommand() != null)
			criteria.add(Expression.eq("esmeSmsCommand",
					esmeSmsRouting.getEsmeSmsCommand()));
		if (esmeSmsRouting.getEsmeServices() != null)
			criteria.add(Expression.eq("esmeServices",
					esmeSmsRouting.getEsmeServices()));
		criteria.setProjection(Projections.count("routingId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(EsmeSmsRouting.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		EsmeSmsRouting obj = new EsmeSmsRouting();
		obj.setRoutingId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeSmsRoutings", obj));
			criteria.setProjection(Projections.count("esmeSmsRoutings"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	private Criteria createCriteria(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, String orderedColumn, boolean asc,
			boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSmsRouting.class);
		Disjunction or = Restrictions.disjunction();
		if (searchEntity != null && esmeSmsRouting != null) {
			if (searchEntity.getSwitchCase() != null
					&& searchEntity.getSwitchCase().equals("FIND_BY_GROUP")) {

				if (searchEntity.getValues() != null) {
					String[] arrStr = searchEntity.getValues().split(",");
					EsmeServices[] listSV = new EsmeServices[arrStr.length];
					EsmeServices sv = null;
					for (int i = 0; i < arrStr.length; i++) {
						sv = new EsmeServices();
						sv.setServiceId(Long.valueOf(arrStr[i]));
						listSV[i] = sv;
					}

					if (arrStr.length > 0) {
						finder.add(Restrictions.in("esmeServices", listSV));
					}
				}

				if (esmeSmsRouting.getEsmeCp() != null) {
					finder.add(Restrictions.eq("esmeCp",
							esmeSmsRouting.getEsmeCp()));
				}

				if (esmeSmsRouting.getEsmeShortCode() != null) {
					finder.add(Restrictions.eq("esmeShortCode",
							esmeSmsRouting.getEsmeShortCode()));
				}

				if (esmeSmsRouting.getEsmeSmsCommand() != null) {
					finder.add(Restrictions.eq("esmeSmsCommand",
							esmeSmsRouting.getEsmeSmsCommand()));
				}

			} else if (searchEntity.getSwitchCase() != null
					&& searchEntity.getSwitchCase().equals("FIND_BY_SERVICES")) {
				String[] arrStr = searchEntity.getValues().split(",");
				EsmeServices[] listSV = new EsmeServices[arrStr.length];
				EsmeServices sv = null;
				for (int i = 0; i < arrStr.length; i++) {
					sv = new EsmeServices();
					sv.setServiceId(Long.valueOf(arrStr[i]));
					listSV[i] = sv;
				}

				if (arrStr.length > 0) {
					or.add(Restrictions.in("esmeServices", listSV));
				}
				finder.add(or);
			} else {

				if (esmeSmsRouting.getRoutingId() > 0) {
					or.add(Restrictions.eq("routingId",
							esmeSmsRouting.getRoutingId()));
				}

				if (esmeSmsRouting.getEsmeCp() != null) {
					or.add(Restrictions.eq("esmeCp", esmeSmsRouting.getEsmeCp()));
				}

				if (esmeSmsRouting.getEsmeServices() != null) {
					or.add(Restrictions.eq("esmeServices",
							esmeSmsRouting.getEsmeServices()));
				}

				if (esmeSmsRouting.getEsmeShortCode() != null) {
					or.add(Restrictions.eq("esmeShortCode",
							esmeSmsRouting.getEsmeShortCode()));
				}

				if (esmeSmsRouting.getEsmeSmsCommand() != null) {
					or.add(Restrictions.eq("esmeSmsCommand",
							esmeSmsRouting.getEsmeSmsCommand()));
				}

			}
			finder.add(or);
		}

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeSmsRouting.class,
						orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;
	}

}