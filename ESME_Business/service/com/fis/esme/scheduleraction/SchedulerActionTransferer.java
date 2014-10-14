package com.fis.esme.scheduleraction;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSchedulerActionBo;
import com.fis.esme.persistence.EsmeSchedulerAction;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://scheduleraction.esme.fis.com/", portName = "SchedulerActionTransfererPort", serviceName = "SchedulerActionTransfererService")
public class SchedulerActionTransferer {
	private EsmeSchedulerActionBo bo;

	public SchedulerActionTransferer() {
		bo = ServiceLocator.createService(EsmeSchedulerActionBo.class);
	};

	public Long add(EsmeSchedulerAction esmeSchedulerAction) throws Exception {
		return bo.persist(esmeSchedulerAction);
	}
	
	public void update(EsmeSchedulerAction esmeSchedulerAction) throws Exception {
		bo.update(esmeSchedulerAction);
	}

	public void delete(EsmeSchedulerAction esmeSchedulerAction) throws Exception {
		bo.delete(esmeSchedulerAction);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeSchedulerAction esmeSchedulerAction) {
		try {
			return bo.checkExited(esmeSchedulerAction);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSchedulerAction> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSchedulerAction> findAllWithOrderPaging(EsmeSchedulerAction esmeSchedulerAction,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeSchedulerAction, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeSchedulerAction esmeSchedulerAction, boolean exactMatch) {
		try {
			return bo.count(esmeSchedulerAction, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
