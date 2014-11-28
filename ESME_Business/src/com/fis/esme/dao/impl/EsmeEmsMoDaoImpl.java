package com.fis.esme.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeEmsMoDao;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.persistence.EsmeGroups;
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
		Disjunction or = Restrictions.disjunction();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.eq("type", "3"));
		if (esmeEmsMo != null) {
			Long id = esmeEmsMo.getMoId();
			String status = esmeEmsMo.getStatus();
			String type = esmeEmsMo.getType();
			String message = esmeEmsMo.getMessage();
			String msisdn = esmeEmsMo.getMsisdn();
			String shortCode = esmeEmsMo.getShortCode();
			Date fromDate = esmeEmsMo.getRequestTime();
			Date toDate = esmeEmsMo.getLastUpdate();
			String strListMoId = esmeEmsMo.getReason();
			EsmeGroups group = esmeEmsMo.getEsmeGroups();

			if (id > 0) {
				if (exactMatch) {
					and.add(Restrictions.eq("moId", id));
				} else {
					and.add(Restrictions.like("moId", "%" + id + "%"));
				}
			}

			if (strListMoId != null) {

				String[] listMoId = strListMoId.split(",");
				for (String strMoId : listMoId) {

					Long moId = Long.parseLong(strMoId);
					or.add(Restrictions.eq("moId", moId));

				}

				and.add(or);
			}

			if (fromDate != null && toDate != null) {
				and.add(Restrictions.between("requestTime", fromDate, toDate));
			}

			if (!FieldChecker.isEmptyString(status)) {
				and.add(Restrictions.eq("status", status).ignoreCase());
			}
			if (!FieldChecker.isEmptyString(msisdn)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(msisdn);
				if (checkStartsWith != null) {
					and.add(Expression.like("msisdn", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						and.add(Restrictions.eq("msisdn", msisdn).ignoreCase());
					} else {
						and.add(Restrictions.like("msisdn", "%" + msisdn + "%").ignoreCase());
					}
				}
			}
			if (!FieldChecker.isEmptyString(message)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(message);
				if (checkStartsWith != null) {
					and.add(Expression.like("message", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						and.add(Restrictions.eq("message", message).ignoreCase());
					} else {
						and.add(Restrictions.like("message", "%" + message + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(shortCode)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(shortCode);
				if (checkStartsWith != null) {
					and.add(Expression.like("shortCode", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						and.add(Restrictions.eq("shortCode", shortCode).ignoreCase());
					} else {
						and.add(Restrictions.like("shortCode", "%" + shortCode + "%").ignoreCase());
					}
				}
			}

			if (group != null) {

				and.add(Restrictions.eq("esmeGroups", group));
			}
		}

		finder.add(and);

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

		Criteria finder = createCriteria(esmeEmsMo, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();

		// String strSQL = "select mo.* from esme_sms_mo mo LEFT OUTER JOIN esme_sms_mt mt ON mo.mo_id = mt.mo_sequence_number ";
		//
		// if (esmeEmsMo != null) {
		//
		// strSQL += "WHERE ";
		//
		// if (esmeEmsMo.getRequestTime() != null && esmeEmsMo.getLastUpdate() != null) {
		//
		// strSQL += "mo.request_time >= '" + String.valueOf(esmeEmsMo.getRequestTime()) + "' ";
		// strSQL += "AND mo.request_time < '" + String.valueOf(esmeEmsMo.getLastUpdate()) + "' ";
		//
		// if (esmeEmsMo.getMsisdn() != null && !"".equals(esmeEmsMo.getMsisdn())) {
		// strSQL += "AND mo.msisdn ";
		// if (!exactMatch) {
		// strSQL += "LIKE '%" + esmeEmsMo.getMsisdn() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getMsisdn() + "' ";
		// }
		//
		// }
		//
		// if (esmeEmsMo.getEsmeShortCode() != null && esmeEmsMo.getEsmeShortCode().getCode() != null) {
		// strSQL += "AND mo.short_code ";
		// if (!exactMatch) {
		// strSQL += " LIKE '%" + esmeEmsMo.getEsmeShortCode().getCode() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getEsmeShortCode().getCode() + "' ";
		// }
		// }
		//
		// if (esmeEmsMo.getMessage() != null) {
		// strSQL += "AND lower(mo.message) ";
		// if (!exactMatch) {
		// strSQL += " LIKE '%" + esmeEmsMo.getMessage().toLowerCase() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getMessage().toLowerCase() + "' ";
		// }
		// }
		//
		// if (esmeEmsMo.getReason() != null) {
		// strSQL += "AND lower(mt.message) ";
		// if (!exactMatch) {
		// strSQL += " LIKE '%" + esmeEmsMo.getReason().toLowerCase() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getReason().toLowerCase() + "' ";
		// }
		// }
		//
		// if (esmeEmsMo.getStatus() != null) {
		// strSQL += "AND mt.status = '" + esmeEmsMo.getStatus() + "' ";
		//
		// }
		//
		// if (esmeEmsMo.getEsmeGroups() != null) {
		//
		// strSQL += "AND mo.group_id = " + esmeEmsMo.getEsmeGroups().getGroupId() + " ";
		// }
		// }
		// }
		//
		// SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addEntity(EsmeEmsMo.class);
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// query = (SQLQuery) query.setFirstResult(firstItemIndex);
		// query.setMaxResults(maxItems);
		// }
		//
		// return query.list();
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

		// String strSQL = "select count(*) total from esme_sms_mo mo LEFT OUTER JOIN esme_sms_mt mt ON mo.mo_id = mt.mo_sequence_number ";
		//
		// if (esmeEmsMo != null) {
		//
		// if (esmeEmsMo.getMsisdn() != null && !"".equals(esmeEmsMo.getMsisdn())) {
		// strSQL += "AND mo.msisdn ";
		// if (!exactMatch) {
		// strSQL += "LIKE '%" + esmeEmsMo.getMsisdn() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getMsisdn() + "' ";
		// }
		//
		// }
		//
		// if (esmeEmsMo.getEsmeShortCode() != null && esmeEmsMo.getEsmeShortCode().getCode() != null) {
		// strSQL += "AND mo.short_code ";
		// if (!exactMatch) {
		// strSQL += " LIKE '%" + esmeEmsMo.getEsmeShortCode().getCode() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getEsmeShortCode().getCode() + "' ";
		// }
		// }
		//
		// if (esmeEmsMo.getMessage() != null) {
		// strSQL += "AND lower(mo.message) ";
		// if (!exactMatch) {
		// strSQL += " LIKE '%" + esmeEmsMo.getMessage().toLowerCase() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getMessage().toLowerCase() + "' ";
		// }
		// }
		//
		// if (esmeEmsMo.getReason() != null) {
		// strSQL += "AND lower(mt.message) ";
		// if (!exactMatch) {
		// strSQL += " LIKE '%" + esmeEmsMo.getReason().toLowerCase() + "%' ";
		// } else if (exactMatch) {
		// strSQL += "= '" + esmeEmsMo.getReason().toLowerCase() + "' ";
		// }
		// }
		//
		// if (esmeEmsMo.getStatus() != null) {
		// strSQL += "AND mt.status = '" + esmeEmsMo.getStatus() + "' ";
		//
		// }
		//
		// if (esmeEmsMo.getEsmeGroups() != null) {
		//
		// strSQL += "AND mo.group_id = " + esmeEmsMo.getEsmeGroups().getGroupId() + " ";
		// }
		// }
		// SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addScalar("total", Hibernate.INTEGER);
		// Integer size = (Integer) query.uniqueResult();
		// return size;

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
