package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.SubscriberDao;
import com.fis.esme.persistence.Groups;
import com.fis.esme.persistence.Subscriber;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class SubscriberDaoImpl extends GenericDaoSpringHibernateTemplate<Subscriber, Long> implements SubscriberDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Subscriber> findAll(Subscriber esmeServices) throws Exception {

		return findAll(esmeServices, false);
	}

	public List<Subscriber> findAll(Subscriber esmeServices, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	/*
	 * 
	 * */
	private Criteria createCriteria(Subscriber esmeServices, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(Subscriber.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {
			String msisdn = esmeServices.getMsisdn();

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
		}

		finder.add(or);

		if (orderedColumn != null && FieldChecker.classContainsField(Subscriber.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<Subscriber> findAll(Subscriber esmeServices, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<Subscriber> findAll(Subscriber esmeServices, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeServices, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(Subscriber esmeServices, boolean exactMatch) throws Exception {

		// Criteria counter = createCriteria(esmeServices, null, false,
		// exactMatch);
		// counter.setProjection(Projections.rowCount());
		// List re = counter.list();
		String strSQL = "select count(*) total from SUBSCRIBER ";

		if (esmeServices != null) {
			if (esmeServices.getMsisdn() != null && !esmeServices.getMsisdn().equals("")) {
				strSQL += " where msisdn like '%" + esmeServices.getMsisdn() + "%'";
			} else if (esmeServices.getEmail() != null && !esmeServices.getEmail().equals("")) {
				strSQL += " where lower(email) like '%" + esmeServices.getEmail().toLowerCase() + "%'";
			} else if (esmeServices.getAddress() != null && !esmeServices.getAddress().equals("") && !esmeServices.getAddress().endsWith("_Search")) {

				strSQL += " where SUB_ID in (" + esmeServices.getAddress() + ")";
			} else if (esmeServices.getAddress() != null && !esmeServices.getAddress().equals("") && esmeServices.getAddress().endsWith("_Search")) {

				String strAddress = esmeServices.getAddress().substring(0, esmeServices.getAddress().lastIndexOf("_Search"));
				strSQL += " where lower(ADDRESS) like '%" + strAddress.toLowerCase() + "%'";
			}
		}
		SQLQuery query = getSession().createSQLQuery(strSQL);
		// if (esmeServices != null) {
		// if (esmeServices.getSubId()>0)
		// query.setLong("subId", esmeServices.getSubId());
		// }
		query.addScalar("total", Hibernate.INTEGER);
		Integer size = (Integer) query.uniqueResult();
		return size;
	}

	@Override
	public List<Subscriber> findAll(Subscriber esmeServices, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		// // Criteria finder = createCriteria(esmeServices, sortedColumn,
		// // ascSorted,
		// // exactMatch);
		// // finder.setFirstResult(firstItemIndex);
		// // finder.setMaxResults(maxItems);
		//
		// /*
		// * select * from (select a.*, rownum rnum from esme_smsc_param a) where
		// * rnum < 80 and rnum > 20;
		// */
		// String strSQL = "(select a.*, rownum rnum from SUBSCRIBER a ";
		// if (esmeServices != null
		// && (esmeServices.getMsisdn() != null
		// )) {
		// strSQL += "where ";
		// if (esmeServices.getMsisdn() != null) {
		// String name = esmeServices.getMsisdn();
		// if (esmeServices.getMsisdn().startsWith("@SWK-"))
		// esmeServices.setMsisdn(esmeServices.getMsisdn()
		// .replace("@SWK-", "").trim());
		// if (name.startsWith("@SWK-")) {
		// strSQL += "upper(NAME) LIKE '" + esmeServices.getMsisdn()
		// + "%' ";
		// } else if (!exactMatch) {
		// strSQL += "upper(NAME) LIKE '%"
		// + esmeServices.getMsisdn().toUpperCase() + "%'";
		// } else if (exactMatch) {
		// strSQL += " upper(NAME) ='"
		// + esmeServices.getMsisdn().toUpperCase() + "'";
		// }
		// }
		// }
		// strSQL += ") ";
		// strSQL = "select * from " + strSQL;
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// // if (strSQL.contains("where")) {
		// // strSQL += "and rnum>=:firstItemIndex ";
		// // } else {
		// strSQL += "where rnum>:firstItemIndex ";
		// // }
		// strSQL += "and rnum<=:maxItems";
		// }
		// System.out.println("from "+firstItemIndex+" to "+maxItems);
		// SQLQuery query = getSession().createSQLQuery(strSQL);
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// query.setInteger("firstItemIndex", firstItemIndex);
		// query.setInteger("maxItems", maxItems + firstItemIndex);
		// }
		// // if (esmeServices != null ) {
		// // query.setLong("subId", esmeServices.getSubId());
		// // }
		//
		// List<Subscriber> l = (List<Subscriber>) query.list();
		//
		// return l;

		// Criteria finder = createCriteria(esmeServices, sortedColumn, ascSorted, exactMatch);
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// finder.setFirstResult(firstItemIndex);
		// finder.setMaxResults(maxItems);
		// }
		//
		// return finder.list();

		String strSQL = "SELECT * from SUBSCRIBER";
		if (esmeServices != null) {

			if (esmeServices.getMsisdn() != null && !esmeServices.getMsisdn().equals("")) {
				strSQL += " where msisdn like '%" + esmeServices.getMsisdn() + "%'";
			} else if (esmeServices.getEmail() != null && !esmeServices.getEmail().equals("")) {
				strSQL += " where lower(email) like '%" + esmeServices.getEmail().toLowerCase() + "%'";
			} else if (esmeServices.getAddress() != null && !esmeServices.getAddress().equals("") && !esmeServices.getAddress().endsWith("_Search")) {

				strSQL += " where SUB_ID in (" + esmeServices.getAddress() + ")";
			} else if (esmeServices.getAddress() != null && !esmeServices.getAddress().equals("") && esmeServices.getAddress().endsWith("_Search")) {

				String strAddress = esmeServices.getAddress().substring(0, esmeServices.getAddress().lastIndexOf("_Search"));
				strSQL += " where lower(ADDRESS) like '%" + strAddress.toLowerCase() + "%'";
			}

		}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(Subscriber.class);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			query = (SQLQuery) query.setFirstResult(firstItemIndex);
			query.setMaxResults(maxItems);
		}

		return query.list();

	}

	@Override
	public int checkExited(Subscriber esmeServices) throws Exception {

		// Criteria criteria = getSession().createCriteria(Subscriber.class);
		// criteria.add(Expression.eq("name", esmeServices.getName()));
		// criteria.setProjection(Projections.count("smsc_id"));
		// return (Integer) criteria.uniqueResult();
		String strSQL = "select count(*) total from SUBSCRIBER where " + " msisdn=:msisdn ";
		// if (esmeServices != null) {
		// strSQL += " and sub_id=:subId";
		// }
		SQLQuery query = getSession().createSQLQuery(strSQL);
		// if (esmeServices != null ) {
		// query.setLong("subId", esmeServices.getSubId());
		// }
		query.setString("msisdn", esmeServices.getMsisdn());
		query.addScalar("total", Hibernate.INTEGER);
		Integer size = (Integer) query.uniqueResult();
		return size;
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		// Subscriber obj = new Subscriber();
		// EsmeSmscDao smscDao = DaoFactory.createDao(EsmeSmscDao.class);
		// EsmeSmsc smsc = smscDao.findById(id);
		// obj.setSmscId(smsc);
		//
		// Criteria criteria = null;
		// Session session = getSession();
		// ;
		// int i = 0;
		//
		// Class[] cls = new Class[] {};
		//
		// for (Class c : cls) {
		// criteria = session.createCriteria(c);
		// criteria.add(Expression.eq("esmeSmscParam", obj));
		// criteria.setProjection(Projections.count("esmeSmscParam"));
		// i += (Integer) criteria.uniqueResult();
		// if (i > 0)
		// return true;
		// }
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(Subscriber.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public List<Groups> findGroupsBySub(long subId) throws Exception {

		String strSQL = "select g.* from GROUPS g, SUB_GROUP sg where g.group_id = sg.group_id and sub_id = :subId";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setLong("subId", subId);
		query.addEntity(Groups.class);

		return (List<Groups>) query.list();
	}

	@Override
	public List<Subscriber> findSubcribersByGroup(long groupId) throws Exception {

		String strSQL = "select s.* from SUBSCRIBER s, SUB_GROUP sg where s.sub_id = sg.sub_id and group_id = :groupId";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setLong("groupId", groupId);
		query.addEntity(Subscriber.class);

		return (List<Subscriber>) query.list();
	}

}