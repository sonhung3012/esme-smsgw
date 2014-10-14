package com.fis.esme.commandsummary;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeDailyCommandSummaryBo;
import com.fis.esme.persistence.EsmeDailyCommandSummary;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://commandsummary.esme.fis.com/", portName = "CommandSummaryTransfererPort", serviceName = "CommandSummaryTransfererService")
public class CommandSummaryTransferer {

	private EsmeDailyCommandSummaryBo bo;

	public CommandSummaryTransferer() {
		bo = ServiceLocator.createService(EsmeDailyCommandSummaryBo.class);
	}

	public List<EsmeDailyCommandSummary> findAllByDate(Date fromDate,
			Date toDate) throws Exception {

		return bo.findAllByDate(fromDate, toDate);
	}

	public List<EsmeDailyCommandSummary> findAllByDateByListField(
			List<String> fields, Date fromDate, Date toDate) throws Exception {

		return bo.findAllByDateByListField(fields, fromDate, toDate);
	}

	public List<EsmeDailyCommandSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllInDateByField(field, fromDate, toDate);
	}

	public List<EsmeDailyCommandSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception {

		return bo.findAllByDateAndId(serviceId, fromDate, toDate);
	}

}
