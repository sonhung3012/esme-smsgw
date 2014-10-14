package com.fis.esme.schedulerdetail;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSchedulerDetailBo;
import com.fis.esme.persistence.EsmeSchedulerDetail;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://schedulerdetail.esme.fis.com/", portName = "SchedulerDetailTransfererPort", serviceName = "SchedulerDetailTransfererService")
public class SchedulerDetailTransferer {
	private EsmeSchedulerDetailBo bo;

	public SchedulerDetailTransferer() {
		bo = ServiceLocator.createService(EsmeSchedulerDetailBo.class);
	};

	public Long add(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception {
		return bo.persist(esmeSchedulerDetail);
	}
	
	public void update(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception {
		bo.update(esmeSchedulerDetail);
	}

	public void delete(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception {
		bo.delete(esmeSchedulerDetail);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeSchedulerDetail esmeSchedulerDetail) {
		try {
			return bo.checkExited(esmeSchedulerDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSchedulerDetail> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSchedulerDetail> findAllWithOrderPaging(EsmeSchedulerDetail esmeSchedulerDetail,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeSchedulerDetail, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeSchedulerDetail esmeSchedulerDetail, boolean exactMatch) {
		try {
			return bo.count(esmeSchedulerDetail, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
