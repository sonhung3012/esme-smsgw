package com.fis.esme.scheduler;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSchedulerBo;
import com.fis.esme.persistence.EsmeScheduler;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://scheduler.esme.fis.com/", portName = "SchedulerTransfererPort", serviceName = "SchedulerTransfererService")
public class SchedulerTransferer {
	private EsmeSchedulerBo bo;

	public SchedulerTransferer() {
		bo = ServiceLocator.createService(EsmeSchedulerBo.class);
	};

	public Long add(EsmeScheduler esmeScheduler) throws Exception {
		return bo.persist(esmeScheduler);
	}
	
	public void update(EsmeScheduler esmeScheduler) throws Exception {
		bo.update(esmeScheduler);
	}

	public void delete(EsmeScheduler esmeScheduler) throws Exception {
		bo.delete(esmeScheduler);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeScheduler esmeScheduler) {
		try {
			return bo.checkExited(esmeScheduler);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeScheduler> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeScheduler> findAllWithOrderPaging(EsmeScheduler esmeScheduler,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeScheduler, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeScheduler esmeScheduler, boolean exactMatch) {
		try {
			return bo.count(esmeScheduler, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
