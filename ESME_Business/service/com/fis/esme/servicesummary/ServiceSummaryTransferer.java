package com.fis.esme.servicesummary;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeDailyServiceSummaryBo;
import com.fis.esme.persistence.EsmeDailyServiceSummary;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://servicesummary.esme.fis.com/", portName = "ServiceSummaryTransfererPort", serviceName = "ServiceSummaryTransfererService")
public class ServiceSummaryTransferer {

	private EsmeDailyServiceSummaryBo bo;

	public ServiceSummaryTransferer() {
		bo = ServiceLocator.createService(EsmeDailyServiceSummaryBo.class);
	}

	public List<EsmeDailyServiceSummary> findAllByDate(Date fromDate,
			Date toDate) throws Exception {

		return bo.findAllByDate(fromDate, toDate);
	}

	public List<EsmeDailyServiceSummary> findAllByDateByListField(
			List<String> fields, Date fromDate, Date toDate) throws Exception {

		return bo.findAllByDateByListField(fields, fromDate, toDate);
	}

	public List<EsmeDailyServiceSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllInDateByField(field, fromDate, toDate);
	}

	public List<EsmeDailyServiceSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllByDateAndId(serviceId, fromDate, toDate);
	}
}
