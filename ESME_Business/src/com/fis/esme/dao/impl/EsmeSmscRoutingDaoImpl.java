package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSmscRoutingDao;
import com.fis.esme.persistence.EsmeSmscRouting;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSmscRoutingDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeSmscRouting, Long> implements
		EsmeSmscRoutingDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting esmeServices)
			throws Exception {
		return findAll(esmeServices, false);
	}

	public List<EsmeSmscRouting> findAll(EsmeSmscRouting esmeServices,
			int firstItemIndex, int maxItems) throws Exception {
		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeSmscRouting esmeServices,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSmscRouting.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {
			Long id = esmeServices.getSmscRoutingId();
			// String name = esmeServices.getShortcode();
			// String status = esmeServices.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("smscRoutingId", id));
				} else {
					or.add(Restrictions.like("smscRoutingId", "%" + id + "%"));
				}
			}

			if (esmeServices.getEsmeSmsc() != null) {
				finder.add(Restrictions.eq("esmeSmsc",
						esmeServices.getEsmeSmsc()));
				// if (exactMatch) {
				// or.add(Restrictions.eq("SMSC_ID", id));
				// } else {
				// or.add(Restrictions.like("smscRoutingId", "%" + id + "%"));
				// }
			}
			if (esmeServices.getEsmeIsdnPrefix() != null) {
				finder.add(Restrictions.eq("esmeIsdnPrefix",
						esmeServices.getEsmeIsdnPrefix()));
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

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeSmscRouting.class,
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
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting esmeServices,
			boolean exactMatch) throws Exception {
		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting esmeServices,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception {
		return findAll(esmeServices, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public int count(EsmeSmscRouting esmeServices, boolean exactMatch)
			throws Exception {
		// Criteria counter = createCriteria(esmeServices, null, false,
		// exactMatch);
		// counter.setProjection(Projections.rowCount());
		// List re = counter.list();
		//
		// if (re.size() < 1) {
		// return 0;
		// } else {
		// return (Integer) re.get(0);
		// }
		String strSQL = "select count(*) total from ESME_SMSC_ROUTING ";

		if (esmeServices != null
				&& (esmeServices.getEsmeSmsc() != null || esmeServices
						.getEsmeIsdnPrefix() != null)) {
			strSQL += "where ";
			if (esmeServices.getEsmeSmsc() != null) {
				strSQL += "smsc_id in (select smsc_id from ESME_SMSC "
						+ "where status='1' and SMSC_ID ";
				if (!exactMatch) {
					strSQL += " = "
							+ esmeServices.getEsmeSmsc().getSmscId() + " ) ";
				} else if (exactMatch) {
					strSQL += "= " + esmeServices.getEsmeSmsc().getSmscId()
							+ ") ";
				}
//				if (!exactMatch) {
//					strSQL += " like '%"
//							+ esmeServices.getEsmeSmsc().getName()
//									.toLowerCase()
//							+ "%' or name like '%"
//							+ esmeServices.getEsmeSmsc().getName()
//									.toUpperCase() + "') ";
//				} else if (exactMatch) {
//					strSQL += "= '" + esmeServices.getEsmeSmsc().getName()
//							+ "') ";
//				}
				if (esmeServices.getEsmeIsdnPrefix() != null) {
					strSQL += "and PREFIX_ID in (select PREFIX_ID from ESME_ISDN_PREFIX "
							+ "where status='1' and PREFIX_VALUE ";
					if (!exactMatch) {
						strSQL += " like '%"
								+ esmeServices.getEsmeIsdnPrefix()
										.getPrefixValue().toLowerCase()
								+ "%' or PREFIX_VALUE like '%"
								+ esmeServices.getEsmeIsdnPrefix()
										.getPrefixValue().toUpperCase() + "%') ";
					} else if (exactMatch) {
						strSQL += "= '"
								+ esmeServices.getEsmeIsdnPrefix()
										.getPrefixValue() + "') ";
					}
				}
			} else if (esmeServices.getEsmeIsdnPrefix() != null) {
				strSQL += "PREFIX_ID in (select PREFIX_ID from ESME_ISDN_PREFIX "
						+ "where status='1' and PREFIX_VALUE ";
				if (!exactMatch) {
					strSQL += " like '%"
							+ esmeServices.getEsmeIsdnPrefix().getPrefixValue()
									.toLowerCase()
							+ "%' or PREFIX_VALUE like '%"
							+ esmeServices.getEsmeIsdnPrefix().getPrefixValue()
									.toUpperCase() + "%') ";
				} else if (exactMatch) {
					strSQL += "= '"
							+ esmeServices.getEsmeIsdnPrefix().getPrefixValue()
							+ "') ";
				}
			}
		}
		SQLQuery query = getSession().createSQLQuery(strSQL);
		// if (esmeServices != null) {
		// if (esmeServices.getEsmeSmsc() != null) {
		// query.setString("smscId", esmeServices.getEsmeSmsc().getSmscId());
		// }
		// if (esmeServices.getEsmeIsdnPrefix() != null) {
		// query.setLong("prefixId", esmeServices.getEsmeIsdnPrefix()
		// .getPrefixId());
		// }
		// }
		query.addScalar("total", Hibernate.INTEGER);
		Integer size = (Integer) query.uniqueResult();
		return size;
	}

	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting esmeServices,
			String sortedColumn, boolean ascSorted, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// Criteria finder = createCriteria(esmeServices, sortedColumn,
		// ascSorted,
		// exactMatch);
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// finder.setFirstResult(firstItemIndex);
		// finder.setMaxResults(maxItems);
		// }
		//
		// return finder.list();
		String strSQL = "(select a.*, rownum rnum from ESME_SMSC_ROUTING a ";
		// String strSQL = "select * from ESME_SMSC_ROUTING ";
		if (esmeServices != null
				&& (esmeServices.getEsmeSmsc() != null || esmeServices
						.getEsmeIsdnPrefix() != null)) {
			strSQL += "where ";
			if (esmeServices.getEsmeSmsc() != null) {
				strSQL += "smsc_id in (select smsc_id from ESME_SMSC "
						+ "where status='1' and SMSC_ID ";
				if (!exactMatch) {
					strSQL += " = "
							+ esmeServices.getEsmeSmsc().getSmscId() + " ) ";
				} else if (exactMatch) {
					strSQL += "= " + esmeServices.getEsmeSmsc().getSmscId()
							+ ") ";
				}
//				if (!exactMatch) {
//					strSQL += " like '%"
//							+ esmeServices.getEsmeSmsc().getName()
//									.toLowerCase()
//							+ "%' or name like '%"
//							+ esmeServices.getEsmeSmsc().getName()
//									.toUpperCase() + "') ";
//				} else if (exactMatch) {
//					strSQL += "= '" + esmeServices.getEsmeSmsc().getName()
//							+ "') ";
//				}
				if (esmeServices.getEsmeIsdnPrefix() != null) {
					strSQL += "and PREFIX_ID in (select PREFIX_ID from ESME_ISDN_PREFIX "
							+ "where status='1' and " + "PREFIX_VALUE ";
					if (!exactMatch) {
						strSQL += " like '%"
								+ esmeServices.getEsmeIsdnPrefix()
										.getPrefixValue().toLowerCase()
								+ "%' or PREFIX_VALUE like '%"
								+ esmeServices.getEsmeIsdnPrefix()
										.getPrefixValue().toUpperCase() + "%') ";
					} else if (exactMatch) {
						strSQL += "= '"
								+ esmeServices.getEsmeIsdnPrefix()
										.getPrefixValue() + "') ";
					}
				}
			} else if (esmeServices.getEsmeIsdnPrefix() != null) {
				strSQL += "PREFIX_ID in (select PREFIX_ID from ESME_ISDN_PREFIX "
						+ "where status='1' and " + "PREFIX_VALUE ";
				if (!exactMatch) {
					strSQL += " like '%"
							+ esmeServices.getEsmeIsdnPrefix().getPrefixValue()
									.toLowerCase()
							+ "%' or PREFIX_VALUE like '%"
							+ esmeServices.getEsmeIsdnPrefix().getPrefixValue()
									.toUpperCase() + "%') ";
				} else if (exactMatch) {
					strSQL += "= '"
							+ esmeServices.getEsmeIsdnPrefix().getPrefixValue()
							+ "') ";
				}
			}
		}
		strSQL += ") ";
		strSQL = "select * from " + strSQL;

		if (firstItemIndex >= 0 && maxItems >= 0) {
			// if (strSQL.contains("where")) {
			// strSQL += "and rnum>=:firstItemIndex ";
			// } else {
			strSQL += "where rnum>:firstItemIndex ";
			// }
			strSQL += "and rnum<=:maxItems";
		}
		SQLQuery query = getSession().createSQLQuery(strSQL);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			query.setInteger("firstItemIndex", firstItemIndex);
			query.setInteger("maxItems", maxItems + firstItemIndex);
		}
		// if (esmeServices != null) {
		// if (esmeServices.getEsmeSmsc() != null) {
		// query.setLong("smscId", esmeServices.getEsmeSmsc().getSmscId());
		// }
		// if (esmeServices.getEsmeIsdnPrefix() != null) {
		// query.setLong("prefixId", esmeServices.getEsmeIsdnPrefix()
		// .getPrefixId());
		// }
		// }
		// query.addScalar("total", Hibernate.INTEGER);
		query.addEntity(EsmeSmscRouting.class);

		return query.list();
	}

	@Override
	public int checkExited(EsmeSmscRouting esmeServices) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeSmscRouting.class);
		criteria.add(Expression.eq("esmeSmsc", esmeServices.getEsmeSmsc()));
		criteria.add(Expression.eq("esmeIsdnPrefix",
				esmeServices.getEsmeIsdnPrefix()));
		criteria.setProjection(Projections.count("smscRoutingId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeSmscRouting obj = new EsmeSmscRouting();
		obj.setSmscRoutingId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeSmscRouting", obj));
			criteria.setProjection(Projections.count("esmeSmscRouting"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(EsmeSmscRouting.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

}