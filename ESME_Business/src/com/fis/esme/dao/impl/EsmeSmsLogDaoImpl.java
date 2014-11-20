package com.fis.esme.dao.impl;

import java.util.Date;
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

import com.fis.esme.dao.EsmeSmsLogDao;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.SubSearchDetail;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSmsLogDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeSmsLog, Long> implements EsmeSmsLogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeSmsLog> findAll(EsmeSmsLog esmeServices) throws Exception {

		return findAll(esmeServices, false);
	}

	public List<EsmeSmsLog> findAll(EsmeSmsLog esmeServices, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeSmsLog esmeServices, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSmsLog.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {
			Long id = esmeServices.getEsmeServices().getServiceId();
			String name = esmeServices.getMsisdn();
			String status = esmeServices.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("esmeServices", id));
				} else {
					or.add(Restrictions.like("esmeServices", "%" + id + "%"));
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

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeSmsLog.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeSmsLog> findAll(EsmeSmsLog esmeServices, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSmsLog> findAll(EsmeSmsLog esmeServices, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeServices, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeSmsLog esmeServices, boolean exactMatch) throws Exception {

		Criteria counter = createCriteria(esmeServices, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeSmsLog> findAll(EsmeSmsLog esmeServices, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeServices, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}
		return finder.list();
	}

	@Override
	public int checkExited(EsmeSmsLog esmeServices) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeSmsLog.class);
		criteria.add(Expression.eq("msisdn", esmeServices.getMsisdn()));
		criteria.setProjection(Projections.count("logId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeSmsLog obj = new EsmeSmsLog();
		obj.setLogId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeSmsLog", obj));
			criteria.setProjection(Projections.count("esmeSmsLog"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeSmsLog.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	public List<EsmeSmsLog> lookUpInfo(String msisdn, Date strFromDate, Date strToDate, String serviceId, String commandId, String shortCodeId) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeSmsLog.class);

		// if ((msisdn == null || msisdn.equalsIgnoreCase(""))
		// && (strFromDate == null)
		// && strToDate == null) {
		//
		// criteria.add(Restrictions.between("requestTime", strFromDate,
		// strToDate));
		//
		// if (serviceId != null) {
		// EsmeServices ser = new EsmeServices();
		// ser.setServiceId(Long.valueOf(serviceId));
		// criteria.add(Expression.eq("esmeServices", ser));
		// }
		// if (commandId != null) {
		// EsmeSmsCommand command = new EsmeSmsCommand();
		// command.setCommandId(Long.valueOf(commandId));
		// criteria.add(Expression.eq("esmeSmsCommand", command));
		// }
		// if (shortCodeId != null) {
		// EsmeShortCode shortCode = new EsmeShortCode();
		// shortCode.setShortCodeId(Long.valueOf(shortCodeId));
		// criteria.add(Expression.eq("esmeShortCode", shortCode));
		// }
		//
		// } else {

		if (msisdn != null && !msisdn.equalsIgnoreCase("")) {
			criteria.add(Expression.eq("msisdn", msisdn));
		}

		if (strFromDate == null && strToDate != null) {
			criteria.add(Restrictions.le("requestTime", strToDate));
		} else if (strFromDate != null && strToDate != null) {
			criteria.add(Restrictions.and(Restrictions.ge("requestTime", strFromDate), Restrictions.le("requestTime", strToDate)));
		} else if (strFromDate != null && strToDate == null) {
			criteria.add(Restrictions.ge("requestTime", strFromDate));
		}
		if (serviceId != null && !serviceId.equalsIgnoreCase("")) {
			EsmeServices ser = new EsmeServices();
			ser.setServiceId(Long.valueOf(serviceId));
			criteria.add(Expression.eq("esmeServices", ser));
		}
		if (commandId != null && !commandId.equalsIgnoreCase("")) {
			EsmeSmsCommand command = new EsmeSmsCommand();
			command.setCommandId(Long.valueOf(commandId));
			criteria.add(Expression.eq("esmeSmsCommand", command));
		}
		if (shortCodeId != null && !shortCodeId.equalsIgnoreCase("")) {
			EsmeShortCode shortCode = new EsmeShortCode();
			shortCode.setShortCodeId(Long.valueOf(shortCodeId));
			criteria.add(Expression.eq("esmeShortCode", shortCode));
		}

		// }

		criteria.addOrder(Order.desc("requestTime"));

		List<EsmeSmsLog> list = criteria.list();

		return list;
	}

	public List<EsmeSmsLog> reportInfo(Date strFromDate, Date strToDate, String serviceId, String commandId, String shortCodeId) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeSmsLog.class);

		if (strFromDate == null && strToDate != null) {
			criteria.add(Restrictions.le("requestTime", strToDate));
		} else if (strFromDate != null && strToDate != null) {
			criteria.add(Restrictions.and(Restrictions.ge("requestTime", strFromDate), Restrictions.le("requestTime", strToDate)));
		} else if (strFromDate != null && strToDate == null) {
			criteria.add(Restrictions.ge("requestTime", strFromDate));
		}
		if (serviceId != null) {
			EsmeServices ser = new EsmeServices();
			ser.setServiceId(Long.valueOf(serviceId));
			criteria.add(Expression.eq("esmeServices", ser));
		}
		if (commandId != null) {
			EsmeSmsCommand command = new EsmeSmsCommand();
			command.setCommandId(Long.valueOf(commandId));
			criteria.add(Expression.eq("esmeSmsCommand", command));
		}
		if (shortCodeId != null) {
			EsmeShortCode shortCode = new EsmeShortCode();
			shortCode.setShortCodeId(Long.valueOf(shortCodeId));
			criteria.add(Expression.eq("esmeShortCode", shortCode));
		}
		criteria.addOrder(Order.desc("requestTime"));

		return criteria.list();
	}

	public List<EsmeServices> getServiceActive() throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeServices.class);
		// criteria.add(Expression.eq("status", "1"));
		criteria.addOrder(Order.asc("name"));
		System.out.println("lst sv " + criteria.list().size());
		return criteria.list();
	}

	public List<EsmeSmsCommand> getCommandActive() throws Exception {

		// Criteria criteria =
		// getSession().createCriteria(EsmeSmsCommand.class);
		// criteria.add(Expression.eq("status", "1"));
		// criteria.addOrder(Order.asc("name"));
		// System.out.println("lst cm "+criteria.list().size());

		// String strSQl = "select * from ESME_SMS_COMMAND  where status='1' order by name ASC";
		String strSQl = "select * from ESME_SMS_COMMAND order by name ASC";
		SQLQuery query = getSession().createSQLQuery(strSQl);
		query.addEntity(EsmeSmsCommand.class);
		List<EsmeSmsCommand> lst = (List<EsmeSmsCommand>) query.list();
		if (lst.size() > 0) {
			return (List<EsmeSmsCommand>) lst;
		}
		return null;
	}

	public List<EsmeShortCode> getShortCodeActive() throws Exception {

		// Criteria criteria = getSession().createCriteria(EsmeShortCode.class);
		// criteria.add(Expression.eq("status", "1"));
		// criteria.addOrder(Order.asc("code"));
		// System.out.println("lst sh " + criteria.list().size());
		// return criteria.list();

		// String strSQl = "select * from ESME_SHORT_CODE  where status='1' order by code ASC";
		String strSQl = "select * from ESME_SHORT_CODE order by code ASC";
		SQLQuery query = getSession().createSQLQuery(strSQl);
		query.addEntity(EsmeShortCode.class);
		List<EsmeShortCode> lst = (List<EsmeShortCode>) query.list();
		if (lst.size() > 0) {
			return (List<EsmeShortCode>) lst;
		}
		return null;
	}

	@Override
	public List<EsmeSmsLog> findAll(SubSearchDetail searchEntity, EsmeSmsLog esmeSmsLog) throws Exception {

		if (searchEntity != null) {
			String strSQL = "select * from ESME_SMS_LOG sms where sms.REQUEST_TIME between :strFromDate and :strToDate +1 ";
			if ((searchEntity.getMsisdn() != null) && (!searchEntity.getMsisdn().equals(""))) {
				strSQL += " and sms.MSISDN =:msisdn";
			}
			if (searchEntity.getServiceId() != null) {
				strSQL += " and sms.SERVICE_ID =:serviceId";
			}
			if (searchEntity.getCommandId() != null) {
				strSQL += " and sms.COMMAND_ID =:commandId";
			}
			if (searchEntity.getShortcodeId() != null) {
				strSQL += " and sms.SHORT_CODE_ID =:shortcodeId";
			}
			strSQL += " ORDER BY sms.REQUEST_TIME ASC";
			SQLQuery query = getSession().createSQLQuery(strSQL);
			query.setDate("strFromDate", searchEntity.getFromDate());
			query.setDate("strToDate", searchEntity.getToDate());

			if ((searchEntity.getMsisdn() != null) && (!searchEntity.getMsisdn().equals(""))) {
				query.setString("msisdn", searchEntity.getMsisdn());
			}
			if (searchEntity.getServiceId() != null) {
				query.setLong("serviceId", searchEntity.getServiceId());
			}
			if (searchEntity.getCommandId() != null) {
				query.setLong("commandId", searchEntity.getCommandId());
			}
			if (searchEntity.getShortcodeId() != null) {
				query.setLong("shortcodeId", searchEntity.getShortcodeId());
			}
			query.addEntity(EsmeSmsLog.class);

			List<EsmeSmsLog> lst = query.list();
			return lst;
		}
		return null;
	}

	@Override
	public List<EsmeSmsLog> findAll(SubSearchDetail searchEntity, EsmeSmsLog esmeSmsLog, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		if (searchEntity != null) {

			String strSQL = "select * from ESME_SMS_LOG sms where sms.REQUEST_TIME between :strFromDate and :strToDate +1 ";
			if ((searchEntity.getMsisdn() != null) && (!searchEntity.getMsisdn().equals(""))) {
				strSQL += " and sms.MSISDN =:msisdn";
			}
			if (searchEntity.getServiceId() != null) {
				strSQL += " and sms.SERVICE_ID =:serviceId";
			}
			if (searchEntity.getCommandId() != null) {
				strSQL += " and sms.COMMAND_ID =:commandId";
			}
			if (searchEntity.getShortcodeId() != null) {
				strSQL += " and sms.SHORT_CODE_ID =:shortcodeId";
			}
			if ((sortedColumn != null)) {
				strSQL += " ORDER BY " + sortedColumn;
				if (!ascSorted) {
					strSQL += " DESC";
				}
			}
			SQLQuery query = getSession().createSQLQuery(strSQL);
			query.setDate("strFromDate", searchEntity.getFromDate());
			query.setDate("strToDate", searchEntity.getToDate());

			if ((searchEntity.getMsisdn() != null) && (!searchEntity.getMsisdn().equals(""))) {
				query.setString("msisdn", searchEntity.getMsisdn());
			}
			if (searchEntity.getServiceId() != null) {
				query.setLong("serviceId", searchEntity.getServiceId());
			}
			if (searchEntity.getCommandId() != null) {
				query.setLong("commandId", searchEntity.getCommandId());
			}
			if (searchEntity.getShortcodeId() != null) {
				query.setLong("shortcodeId", searchEntity.getShortcodeId());
			}

			query.addEntity(EsmeSmsLog.class);
			query = (SQLQuery) query.setFirstResult(firstItemIndex);
			query.setMaxResults(maxItems);
			List<EsmeSmsLog> lst = query.list();
			System.out.println("so ban ghi>>>>>>>>>" + lst.size());
			return lst;
		}
		return null;
	}

	@Override
	public int count(SubSearchDetail searchEntity, EsmeSmsLog esmeSmsLog, boolean exactMatch) throws Exception {

		if (searchEntity != null) {

			String strSQL = "select count(*) total from ESME_SMS_LOG sms where sms.REQUEST_TIME between :strFromDate and :strToDate +1 ";
			if ((searchEntity.getMsisdn() != null) && (!searchEntity.getMsisdn().equals(""))) {
				strSQL += " and sms.MSISDN =:msisdn";
			}
			if (searchEntity.getServiceId() != null) {
				strSQL += " and sms.SERVICE_ID =:serviceId";
			}
			if (searchEntity.getCommandId() != null) {
				strSQL += " and sms.COMMAND_ID =:commandId";
			}
			if (searchEntity.getShortcodeId() != null) {
				strSQL += " and sms.SHORT_CODE_ID =:shortcodeId";
			}

			SQLQuery query = getSession().createSQLQuery(strSQL);
			query.setDate("strFromDate", searchEntity.getFromDate());
			query.setDate("strToDate", searchEntity.getToDate());

			if ((searchEntity.getMsisdn() != null) && (!searchEntity.getMsisdn().equals(""))) {
				query.setString("msisdn", searchEntity.getMsisdn());
			}
			if (searchEntity.getServiceId() != null) {
				query.setLong("serviceId", searchEntity.getServiceId());
			}
			if (searchEntity.getCommandId() != null) {
				query.setLong("commandId", searchEntity.getCommandId());
			}
			if (searchEntity.getShortcodeId() != null) {
				query.setLong("shortcodeId", searchEntity.getShortcodeId());
			}

			query.addScalar("total", Hibernate.INTEGER);
			int i = (Integer) query.uniqueResult();
			System.out.println("dem so ban ghi>>>>>>>>>" + i);
			return i;
		}
		return 0;
	}
}