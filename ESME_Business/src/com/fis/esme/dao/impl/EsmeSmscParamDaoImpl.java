package com.fis.esme.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSmscDao;
import com.fis.esme.dao.EsmeSmscParamDao;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.DaoFactory;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSmscParamDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeSmscParam, Long> implements EsmeSmscParamDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeSmscParam> findAll(EsmeSmscParam esmeServices) throws Exception {

		return findAll(esmeServices, false);
	}

	public List<EsmeSmscParam> findAll(EsmeSmscParam esmeServices, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	/*
	 * 
	 * */
	private Criteria createCriteria(EsmeSmscParam esmeServices, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSmscParam.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {
			Long id = esmeServices.getSmscId().getSmscId();
			String name = esmeServices.getName();
			String value = esmeServices.getValue();
			// String status = esmeServices.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("smscId", id));
				} else {
					or.add(Restrictions.like("smscId", "%" + id + "%"));
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

			if (!FieldChecker.isEmptyString(value)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(value);
				if (checkStartsWith != null) {
					or.add(Expression.like("value", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("value", value).ignoreCase());
					} else {
						or.add(Restrictions.like("value", "%" + value + "%").ignoreCase());
					}
				}
			}

			// if (!FieldChecker.isEmptyString(status)) {
			// String checkStartsWith = BusinessUtil.checkStartsWith(status);
			// if (checkStartsWith != null) {
			// or.add(Expression.like("status", checkStartsWith,
			// MatchMode.START).ignoreCase());
			// } else {
			// or.add(Restrictions.eq("status", status));
			// }
			// }
		}

		finder.add(or);

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeSmscParam.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeSmscParam> findAll(EsmeSmscParam esmeServices, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSmscParam> findAll(EsmeSmscParam esmeServices, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeServices, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeSmscParam esmeServices, boolean exactMatch) throws Exception {

		// Criteria counter = createCriteria(esmeServices, null, false,
		// exactMatch);
		// counter.setProjection(Projections.rowCount());
		// List re = counter.list();
		String strSQL = "select count(*) total from ESME_SMSC_PARAM ";

		if (esmeServices != null && (esmeServices.getName() != null || esmeServices.getValue() != null || esmeServices.getSmscId() != null)) {
			strSQL += "where ";
			if (esmeServices.getName() != null) {
				String name = esmeServices.getName();
				if (esmeServices.getName().startsWith("@SWK-"))
					esmeServices.setName(esmeServices.getName().replace("@SWK-", "").trim());
				if (name.startsWith("@SWK-")) {
					strSQL += "upper(NAME) LIKE '" + esmeServices.getName() + "%' ";
				} else if (!exactMatch) {
					strSQL += "upper(NAME) LIKE '%" + esmeServices.getName().toUpperCase() + "%' ";
				} else if (exactMatch || name.startsWith("@SWK-")) {
					strSQL += "upper(NAME) ='" + esmeServices.getName() + "' ";
				}
				if (!exactMatch) {
					if (esmeServices.getValue() != null) {
						strSQL += "or upper(value) LIKE '%" + esmeServices.getValue().toUpperCase() + "%' ";
					}
				} else if (exactMatch) {
					strSQL += " or value='" + esmeServices.getValue() + "' ";
				}
				if (esmeServices.getSmscId() != null) {
					strSQL += "and smsc_id = :smscId ";
				}
			} else if (esmeServices.getValue() != null) {
				if (!exactMatch) {
					strSQL += "upper(value) LIKE '%" + esmeServices.getValue().toUpperCase() + "%' ";
				} else if (exactMatch) {
					strSQL += " upper(value) ='" + esmeServices.getValue() + "' ";
				}
				if (esmeServices.getSmscId() != null) {
					strSQL += "and smsc_id=:smscId ";
				}
			} else if (esmeServices.getSmscId() != null) {
				strSQL += "smsc_id = :smscId ";
			}
		}

		// if (esmeServices != null
		// && (esmeServices.getName() != null
		// || esmeServices.getValue() != null || esmeServices
		// .getSmscId() != null)) {
		// strSQL += "where ";
		// if (esmeServices.getName() != null) {
		// if (esmeServices.getName().startsWith("@SWK-"))
		// esmeServices.setName(esmeServices.getName()
		// .replace("@SWK-", "").trim());
		// if (!exactMatch) {
		// strSQL += "name LIKE '%"
		// + esmeServices.getName().toUpperCase() + "%' or "
		// + "name LIKE '%"
		// + esmeServices.getName().toLowerCase() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "name='" + esmeServices.getName() + "' ";
		// }
		// if (!exactMatch) {
		// if (esmeServices.getValue() != null) {
		// strSQL += "and value LIKE '%"
		// + esmeServices.getValue().toUpperCase()
		// + "%' or " + "value LIKE '%"
		// + esmeServices.getValue().toLowerCase() + "%' ";
		// }
		// } else if (exactMatch) {
		// strSQL += "value='" + esmeServices.getValue() + "' ";
		// }
		// if (esmeServices.getSmscId() != null) {
		// strSQL += "and smsc_id in (select smsc_id from ESME_SMSC "
		// + "where status='1' and smsc_id=:smscId ) ";
		// }
		// } else if (esmeServices.getValue() != null) {
		// if (!exactMatch) {
		// strSQL += "value LIKE '%"
		// + esmeServices.getValue().toUpperCase() + "%' or "
		// + "value LIKE '%"
		// + esmeServices.getValue().toLowerCase() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "value='" + esmeServices.getValue() + "' ";
		// }
		// if (esmeServices.getSmscId() != null) {
		// strSQL += "and smsc_id in (select smsc_id from ESME_SMSC "
		// + "where status='1' and smsc_id=:smscId ) ";
		// }
		// } else if (esmeServices.getSmscId() != null) {
		// strSQL += "smsc_id in (select smsc_id from ESME_SMSC "
		// + "where status='1' and smsc_id=:smscId ) ";
		// }
		// }

		SQLQuery query = getSession().createSQLQuery(strSQL);
		if (esmeServices != null) {
			if (esmeServices.getSmscId() != null)
				query.setLong("smscId", esmeServices.getSmscId().getSmscId());
			// if (esmeServices.getName() != null)
			// query.setString("name", esmeServices.getName());
			// if (esmeServices.getSmscId() != null)
			// query.setString("value", esmeServices.getValue());
		}
		query.addScalar("total", Hibernate.INTEGER);
		Integer size = (Integer) query.uniqueResult();
		return size;
	}

	@Override
	public List<EsmeSmscParam> findAll(EsmeSmscParam esmeServices, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		String strSQL = "select * from ESME_SMSC_PARAM ";
		if (esmeServices != null && (esmeServices.getName() != null || esmeServices.getValue() != null || esmeServices.getSmscId() != null)) {
			strSQL += "where ";
			if (esmeServices.getName() != null) {
				String name = esmeServices.getName();
				if (esmeServices.getName().startsWith("@SWK-"))
					esmeServices.setName(esmeServices.getName().replace("@SWK-", "").trim());
				if (name.startsWith("@SWK-")) {
					strSQL += "upper(NAME) LIKE '" + esmeServices.getName() + "%' ";
				} else if (!exactMatch) {
					strSQL += "upper(NAME) LIKE '%" + esmeServices.getName().toUpperCase() + "%'";
				} else if (exactMatch) {
					strSQL += " upper(NAME) ='" + esmeServices.getName().toUpperCase() + "'";
				}

				if (esmeServices.getValue() != null) {
					if (!exactMatch) {
						strSQL += "or upper(value) LIKE '%" + esmeServices.getValue().toUpperCase() + "%' ";
					} else if (exactMatch) {
						strSQL += " or upper(value) ='" + esmeServices.getValue() + "' ";
					}
				}
				if (esmeServices.getSmscId() != null) {
					strSQL += "and smsc_id = :smscId ";
				}
			} else if (esmeServices.getValue() != null) {
				if (!exactMatch) {
					strSQL += "upper(value) LIKE '%" + esmeServices.getValue().toUpperCase() + "%' ";
				} else if (exactMatch) {
					strSQL += " value='" + esmeServices.getValue() + "' ";

				}
				if (esmeServices.getSmscId() != null) {
					strSQL += "and smsc_id = :smscId ";
				}
			} else if (esmeServices.getSmscId() != null) {
				strSQL += "smsc_id = :smscId ";
			}
		}

		SQLQuery query = getSession().createSQLQuery(strSQL);

		// query.addEntity(EsmeSmscParam.class);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			query = (SQLQuery) query.setFirstResult(firstItemIndex);
			query.setMaxResults(maxItems);
		}

		if (esmeServices != null && esmeServices.getSmscId() != null) {
			query.setLong("smscId", esmeServices.getSmscId().getSmscId());
		}

		List<EsmeSmscParam> listParam = new ArrayList<EsmeSmscParam>();
		List<Object> listObject = query.list();

		for (Object object : listObject) {
			Object[] objs = (Object[]) object;
			EsmeSmscParam param = new EsmeSmscParam();
			EsmeSmsc smsc = new EsmeSmsc();
			long smscId = Long.parseLong(objs[0].toString());
			String name = objs[1].toString();
			String value = objs[2].toString();

			// if (esmeServices == null || (esmeServices != null && esmeServices.getSmscId() == null && esmeServices.getName() == null && esmeServices.getValue() == null)
			// || (esmeServices.getSmscId() != null && smscId == esmeServices.getSmscId().getSmscId())
			// || (esmeServices.getName() != null && name.toLowerCase().contains(esmeServices.getName().toLowerCase()))
			// || (esmeServices.getValue() != null && value.toLowerCase().contains(esmeServices.getValue().toLowerCase()))) {

			smsc.setSmscId(smscId);
			param.setSmscId(smsc);
			param.setName(name);
			param.setValue(value);
			listParam.add(param);
			// }
		}
		return listParam;
	}

	@Override
	public int checkExited(EsmeSmscParam esmeServices) throws Exception {

		// Criteria criteria = getSession().createCriteria(EsmeSmscParam.class);
		// criteria.add(Expression.eq("name", esmeServices.getName()));
		// criteria.setProjection(Projections.count("smsc_id"));
		// return (Integer) criteria.uniqueResult();
		String strSQL = "select count(*) total from ESME_SMSC_PARAM where " + " name=:name ";
		if (esmeServices != null && esmeServices.getSmscId() != null) {
			strSQL += " and smsc_id=:smscId";
		}
		SQLQuery query = getSession().createSQLQuery(strSQL);
		if (esmeServices != null && esmeServices.getSmscId() != null) {
			query.setLong("smscId", esmeServices.getSmscId().getSmscId());
		}
		query.setString("name", esmeServices.getName());
		query.addScalar("total", Hibernate.INTEGER);
		Integer size = (Integer) query.uniqueResult();
		return size;
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeSmscParam obj = new EsmeSmscParam();
		EsmeSmscDao smscDao = DaoFactory.createDao(EsmeSmscDao.class);
		EsmeSmsc smsc = smscDao.findById(id);
		obj.setSmscId(smsc);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeSmscParam", obj));
			criteria.setProjection(Projections.count("esmeSmscParam"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeSmscParam.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public Long persist(EsmeSmscParam bk) throws Exception {

		// insert into t_emp values (emp_no_seq.nexval, 'Joe Black');
		String strSQL = "insert into ESME_SMSC_PARAM(SMSC_ID,NAME,VALUE) " + "values (:smscId,:name,:value)";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setString("name", bk.getName());
		query.setString("value", bk.getValue());
		query.setLong("smscId", bk.getSmscId().getSmscId());
		query.executeUpdate();
		return bk.getSmscId().getSmscId();
	}

	public void update(EsmeSmscParam oldObj, EsmeSmscParam newObj) throws Exception {

		String strSQL = "update ESME_SMSC_PARAM " + "set name=:nameNew, value=:valueNew, smsc_id=:idNew " + "where smsc_id=:idOld and name=:nameOld";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setString("nameNew", newObj.getName());
		query.setString("valueNew", newObj.getValue());
		query.setLong("idNew", newObj.getSmscId().getSmscId());
		query.setString("nameOld", oldObj.getName());
		query.setLong("idOld", oldObj.getSmscId().getSmscId());
		System.out.println("new :" + newObj.getName() + " " + newObj.getValue() + " " + newObj.getSmscId().getSmscId());
		System.out.println("old :" + oldObj.getName() + " " + oldObj.getValue() + " " + oldObj.getSmscId().getSmscId());
		query.executeUpdate();
	}

	@Override
	public void delete(EsmeSmscParam esmeServices) throws Exception {

		String strSQL = "delete from ESME_SMSC_PARAM where smsc_id=:smscId and " + "name=:name and value=:value";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setString("name", esmeServices.getName());
		query.setString("value", esmeServices.getValue());
		query.setLong("smscId", esmeServices.getSmscId().getSmscId());
		query.executeUpdate();
	}

	@Override
	public List<EsmeSmscParam> findAll() throws Exception {

		String strSQL = "select * from ESME_SMSC_PARAM ";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addEntity(EsmeSmscParam.class);

		List<EsmeSmscParam> listParam = new ArrayList<EsmeSmscParam>();
		List<Object> listObject = query.list();

		for (Object object : listObject) {
			Object[] objs = (Object[]) object;
			EsmeSmscParam param = new EsmeSmscParam();
			EsmeSmsc smsc = new EsmeSmsc();
			smsc.setSmscId(Long.parseLong(objs[0].toString()));
			param.setSmscId(smsc);
			param.setName(objs[1].toString());
			param.setValue(objs[2].toString());

			listParam.add(param);
		}
		return listParam;
	}
}