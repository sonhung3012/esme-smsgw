package com.fis.esme.dao.impl;

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

import com.fis.esme.dao.EsmeServiceDao;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeServiceDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeServices, Long> implements EsmeServiceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeServices> findAll(EsmeServices esmeServices) throws Exception {

		return findAll(esmeServices, false);
	}

	public List<EsmeServices> findAll(EsmeServices esmeServices, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeServices esmeServices, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeServices.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {
			Long id = esmeServices.getServiceId();
			String name = esmeServices.getName();
			String status = esmeServices.getStatus();
			Long rootId = esmeServices.getRootId();
			Long parentId = esmeServices.getParentId();
			String desc = esmeServices.getDesciption();

			if (rootId != null) {
				or.add(Restrictions.eq("rootId", rootId));
			}

			if (parentId != null) {
				or.add(Restrictions.eq("parentId", parentId));
			}

			if (id > 0) {
				or.add(Restrictions.eq("serviceId", id));
			}
			if (desc != null) {
				String[] arrStr = desc.split(",");
				EsmeServices[] listSV = new EsmeServices[arrStr.length];
				EsmeServices sv = null;
				for (int i = 0; i < arrStr.length; i++) {
					sv = new EsmeServices();
					sv.setServiceId(Long.valueOf(arrStr[i]));
					listSV[i] = sv;
				}

				if (arrStr.length > 0) {
					or.add(Restrictions.in("serviceId", listSV));
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

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeServices.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeServices> findAll(EsmeServices esmeServices, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeServices> findAll(EsmeServices esmeServices, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeServices, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeServices esmeServices, boolean exactMatch) throws Exception {

		// Criteria counter = createCriteria(esmeServices, null, false,
		// exactMatch);
		// counter.setProjection(Projections.rowCount());
		// List re = counter.list();
		String strSQL = "select count(*) from ESME_SERVICES";
		if (esmeServices != null) {
			if (esmeServices.getName() != null) {
				String checkStartsWith = BusinessUtil.checkStartsWith(esmeServices.getName());
				if (checkStartsWith != null) {
					strSQL += " where upper(NAME) like '" + checkStartsWith + "%' ";
				} else {
					strSQL += " where lower(NAME) like '%" + esmeServices.getName() + "%' ";
				}
			} else {

				if (esmeServices.getDesciption() != null && esmeServices.getDesciption() != "" && !esmeServices.getDesciption().endsWith("_Search")) {
					strSQL += " where SERVICE_ID in (" + esmeServices.getDesciption() + ")";
				}
				if (esmeServices.getDesciption() != null && esmeServices.getDesciption() != "" && esmeServices.getDesciption().endsWith("_Search")) {
					String strDesc = esmeServices.getDesciption().substring(0, esmeServices.getDesciption().lastIndexOf("_Search")).toLowerCase();
					strSQL += " where lower(DESCIPTION) like '%" + strDesc + "%'";
				}
				if (esmeServices.getStatus() != null && esmeServices.getDesciption() == null) {
					strSQL += " where STATUS = " + esmeServices.getStatus() + " ";
				}
				if (esmeServices.getStatus() != null && esmeServices.getDesciption() != null) {
					strSQL += " where SERVICE_ID in (" + esmeServices.getDesciption() + ") and STATUS = " + esmeServices.getStatus() + " ";
				}
			}
		}
		SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addEntity(ApParam.class);
		List re = query.list();

		if (re.size() < 1) {
			return 0;
		} else {
			// BigDecimal de = (BigDecimal) re.get(0);
			// String s = String.valueOf(de);
			Integer i = (Integer) re.get(0);
			return i;
		}
	}

	@Override
	public List<EsmeServices> findAll(EsmeServices esmeServices, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		// Criteria finder = createCriteria(esmeServices, sortedColumn,
		// ascSorted,
		// exactMatch);
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// finder.setFirstResult(firstItemIndex);
		// finder.setMaxResults(maxItems);
		// }
		String strSQL = "SELECT * from ESME_SERVICES";
		if (esmeServices != null) {
			if (esmeServices.getName() != null) {
				String checkStartsWith = BusinessUtil.checkStartsWith(esmeServices.getName());
				if (checkStartsWith != null) {
					strSQL += " where upper(NAME) like '" + checkStartsWith + "%' ";
				} else {
					strSQL += " where lower(NAME) like '%" + esmeServices.getName() + "%' ";
				}
			} else {

				if (esmeServices.getDesciption() != null && esmeServices.getDesciption() != "" && !esmeServices.getDesciption().endsWith("_Search")) {
					strSQL += " where SERVICE_ID in (" + esmeServices.getDesciption() + ")";
				}
				if (esmeServices.getDesciption() != null && esmeServices.getDesciption() != "" && esmeServices.getDesciption().endsWith("_Search")) {
					String strDesc = esmeServices.getDesciption().substring(0, esmeServices.getDesciption().lastIndexOf("_Search")).toLowerCase();
					strSQL += " where lower(DESCIPTION) like '%" + strDesc + "%'";
				}
				if (esmeServices.getStatus() != null && esmeServices.getDesciption() == null) {
					strSQL += " where STATUS = " + esmeServices.getStatus() + " ";
				}
				if (esmeServices.getStatus() != null && esmeServices.getDesciption() != null) {
					strSQL += " where SERVICE_ID in (" + esmeServices.getDesciption() + ") and STATUS = " + esmeServices.getStatus() + " ";
				}
			}
		}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(EsmeServices.class);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			query = (SQLQuery) query.setFirstResult(firstItemIndex);
			query.setMaxResults(maxItems);
		}

		return query.list();
	}

	@Override
	public int checkExited(EsmeServices esmeServices) throws Exception {

		String strSQL = "SELECT * from ESME_SERVICES";
		if (esmeServices != null) {
			if (esmeServices.getName() != null) {
				strSQL += " where lower(NAME) = '" + esmeServices.getName().trim().toLowerCase() + "' ";
			}
		}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(EsmeServices.class);

		// Criteria criteria = getSession().createCriteria(EsmeServices.class);
		// criteria.add(Expression.eq("name",
		// esmeServices.getName().toLowerCase()));
		// criteria.setProjection(Projections.count("serviceId"));
		return query.list().size();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeServices obj = new EsmeServices();
		obj.setServiceId(id);

		Criteria criteria = null;
		Session session = getSession();

		int i = 0;

		Class[] cls = new Class[] { EsmeSmsRouting.class, EsmeSmsLog.class, EsmeEmsMo.class, EsmeFileUpload.class, EsmeIsdnPermission.class };

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeServices", obj));
			criteria.setProjection(Projections.count("esmeServices"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeServices.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

}