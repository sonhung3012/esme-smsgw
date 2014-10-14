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

import com.fis.esme.dao.EsmeMessageContentDao;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeMessageContentDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeMessageContent, Long> implements
		EsmeMessageContentDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage) throws Exception {
		return findAll(esmeMessage, false);
	}

	public List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, int firstItemIndex,
			int maxItems) throws Exception {
		return findAll(esmeMessage, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeMessageContent esmeMessage,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeMessageContent.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeMessage != null) {
			Long id = esmeMessage.getId();
			EsmeMessage message = esmeMessage.getEsmeMessage();
			EsmeLanguage language = esmeMessage.getEsmeLanguage();
			String mess = esmeMessage.getMessage();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("id", id));
				} else {
					or.add(Restrictions.like("id", "%" + id + "%"));
				}
			}
			
			if (!FieldChecker.isEmptyString(mess)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(mess);
				if (checkStartsWith != null) {
					or.add(Expression.like("message", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("message", mess).ignoreCase());
					} else {
						or.add(Restrictions.like("message", "%" + mess + "%")
								.ignoreCase());
					}
				}
			}
			
			if (message != null) {
				or.add(Restrictions.eq("esmeMessage", message));
			}
			
			if (language != null) {
				or.add(Restrictions.eq("esmeLanguage", language));
			}
			
			
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeMessageContent.class,
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
	public List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeMessage, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeMessage, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public int count(EsmeMessageContent esmeMessage, boolean exactMatch)
			throws Exception {
		Criteria counter = createCriteria(esmeMessage, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		Criteria finder = createCriteria(esmeMessage, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeMessageContent esmeMessage) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeMessageContent.class);
		criteria.add(Expression.eq("code", esmeMessage.getMessage()));
		criteria.setProjection(Projections.count("id"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeMessageContent obj = new EsmeMessageContent();
		obj.setId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {  };

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeMessage", obj));
			criteria.setProjection(Projections.count("esmeMessage"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(EsmeMessageContent.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public int deleteByMessageId(long messageId) throws Exception {
		
		String strQuery = " delete from esme_message_content msgct "
				+ "where msgct.message_id=:message_id ";
		SQLQuery query = getSession().createSQLQuery(strQuery);
		query.addEntity(getPersistenceType());
		query.setLong("message_id", messageId);
		return query.executeUpdate();
	}

	@Override
	public List<EsmeMessageContent> findByMessageId(long messageId)
			throws Exception {
		
		String strQuery = " select * from esme_message_content msgct "
				+ "where msgct.message_id=:message_id ";
		SQLQuery query = getSession().createSQLQuery(strQuery);
		query.addEntity(getPersistenceType());
		query.setLong("message_id", messageId);
		List lst = query.list();
		return ((lst != null) && (lst.size() > 0)) ? lst : null;
	}

	@Override
	public EsmeMessageContent findByMessageIdAndLanguageId(long messageId,
			long languageId) throws Exception {
		
		String strQuery = " select * from esme_message_content msgct "
				+ "where msgct.MESSAGE_ID=:message_id and msgct.LANGUAGE_ID=:language_id ";

		SQLQuery query = getSession().createSQLQuery(strQuery);
		query.addEntity(getPersistenceType());
		query.setLong("message_id", messageId);
		query.setLong("language_id", languageId);
		List lst = query.list();
		if ((lst != null) && (lst.size() == 1)) {
			return (EsmeMessageContent) lst.get(0);
		}
		return null;
	}

}