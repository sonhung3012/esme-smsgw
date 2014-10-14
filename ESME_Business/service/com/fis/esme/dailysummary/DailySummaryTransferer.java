package com.fis.esme.dailysummary;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeDailySummaryBo;
import com.fis.esme.persistence.EsmeDailySummary;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://dailysummary.esme.fis.com/", portName = "DailySummaryTransfererPort", serviceName = "DailySummaryTransfererService")
public class DailySummaryTransferer {

	private EsmeDailySummaryBo bo;

	public DailySummaryTransferer() {
		bo = ServiceLocator.createService(EsmeDailySummaryBo.class);
	}

	public List<EsmeDailySummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception {

		return bo.findAllByDate(fromDate, toDate);
	}

	public List<EsmeDailySummary> findAllByDateByListField(List<String> fields,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllByDateByListField(fields, fromDate, toDate);
	}

	public List<EsmeDailySummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllInDateByField(field, fromDate, toDate);
	}

}
