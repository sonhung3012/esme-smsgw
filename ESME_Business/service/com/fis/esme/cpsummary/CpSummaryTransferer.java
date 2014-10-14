package com.fis.esme.cpsummary;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeDailyCpSummaryBo;
import com.fis.esme.persistence.EsmeDailyCpSummary;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://cpsummary.esme.fis.com/", portName = "CpSummaryTransfererPort", serviceName = "CpSummaryTransfererService")
public class CpSummaryTransferer {

	private EsmeDailyCpSummaryBo bo;

	public CpSummaryTransferer() {
		bo = ServiceLocator.createService(EsmeDailyCpSummaryBo.class);
	}

	public List<EsmeDailyCpSummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception {

		return bo.findAllByDate(fromDate, toDate);
	}

	public List<EsmeDailyCpSummary> findAllByDateByListField(
			List<String> fields, Date fromDate, Date toDate) throws Exception {

		return bo.findAllByDateByListField(fields, fromDate, toDate);
	}

	public List<EsmeDailyCpSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllInDateByField(field, fromDate, toDate);
	}

	public List<EsmeDailyCpSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllByDateAndId(serviceId, fromDate, toDate);
	}
}
