package com.fis.esme.dao;

import java.util.Date;
import java.util.List;

import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.SubSearchDetail;
import com.fis.framework.dao.GenericDao;

public interface EsmeSmsLogDao extends GenericDao<EsmeSmsLog, Long> {
	List<EsmeSmsLog> findAll(EsmeSmsLog EsmeSmsLog) throws Exception;

	List<EsmeSmsLog> findAll(EsmeSmsLog EsmeSmsLog, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeSmsLog> findAll(EsmeSmsLog EsmeSmsLog, boolean exactMatch)
			throws Exception;

	List<EsmeSmsLog> findAll(EsmeSmsLog EsmeSmsLog, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeSmsLog> findAll(EsmeSmsLog EsmeSmsLog, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSmsLog EsmeSmsLog, boolean exactMatch) throws Exception;

	int checkExited(EsmeSmsLog EsmeSmsLog) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;

	public List<EsmeSmsLog> lookUpInfo(String msisdn, Date strFromDate,
			Date strToDate, String serviceId, String commandId,
			String shortCodeId) throws Exception;

	public List<EsmeServices> getServiceActive() throws Exception;

	public List<EsmeSmsCommand> getCommandActive() throws Exception;

	public List<EsmeShortCode> getShortCodeActive() throws Exception;

	public List<EsmeSmsLog> reportInfo(Date strFromDate, Date strToDate,
			String serviceId, String commandId, String shortCodeId)
			throws Exception;

	public List<EsmeSmsLog> findAll(SubSearchDetail searchEntity,
			EsmeSmsLog esmeSmsLog) throws Exception;

	public List<EsmeSmsLog> findAll(SubSearchDetail searchEntity,
			EsmeSmsLog esmeSmsLog, String sortedColumn, boolean ascSorted,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception;

	public int count(SubSearchDetail searchEntity, EsmeSmsLog esmeSmsLog,
			boolean exactMatch) throws Exception;
}
