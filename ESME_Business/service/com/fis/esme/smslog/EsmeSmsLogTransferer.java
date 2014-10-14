package com.fis.esme.smslog;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSmsLogBo;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.SubSearchDetail;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://smslog.esme.fis.com/", portName = "EsmeSmsLogTransfererPort", serviceName = "EsmeSmsLogTransfererService")
public class EsmeSmsLogTransferer {
	private EsmeSmsLogBo bo;

	public EsmeSmsLogTransferer() {
		bo = ServiceLocator.createService(EsmeSmsLogBo.class);
	};

	public Long add(EsmeSmsLog esmeServices) throws Exception {
		return bo.persist(esmeServices);
	}

	public void update(EsmeSmsLog esmeServices) throws Exception {
		bo.update(esmeServices);
	}

	public void delete(EsmeSmsLog esmeServices) throws Exception {
		bo.delete(esmeServices);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeSmsLog esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmsLog> lookUpInfo(String msisdn, Date strFromDate,
			Date strToDate, String serviceId, String commandId,
			String shortCodeId) throws Exception {
		try {
			return bo.lookUpInfo(msisdn, strFromDate, strToDate, serviceId,
					commandId, shortCodeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<EsmeServices> getServiceActive() throws Exception {
		return bo.getServiceActive();
	}

	public List<EsmeSmsCommand> getCommandActive() throws Exception {
		return bo.getCommandActive();
	}

	public List<EsmeShortCode> getShortCodeActive() throws Exception {
		return bo.getShortCodeActive();
	}

	public List<EsmeSmsLog> reportInfo(Date strFromDate, Date strToDate,
			String serviceId, String commandId, String shortCodeId)
			throws Exception {
		return bo.reportInfo(strFromDate, strToDate, serviceId, commandId,
				shortCodeId);
	}

	public List<EsmeSmsLog> findAllNoPaper(SubSearchDetail searchEntity,
			EsmeSmsLog esmeSmsLog) throws Exception {

		return bo.findAll(searchEntity, esmeSmsLog);
	}

	public List<EsmeSmsLog> findAll(SubSearchDetail searchEntity,
			EsmeSmsLog esmeSmsLog, String sortedColumn, boolean ascSorted,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception {

		return bo.findAll(searchEntity, esmeSmsLog, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	public int count(SubSearchDetail searchEntity, EsmeSmsLog esmeSmsLog,
			boolean exactMatch) throws Exception {

		return bo.count(searchEntity, esmeSmsLog, exactMatch);
	}
}
