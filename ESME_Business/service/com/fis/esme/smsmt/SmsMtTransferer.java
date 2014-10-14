package com.fis.esme.smsmt;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSmsMtBo;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsMt;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://smsmt.esme.fis.com/", portName = "SmsMtTransfererPort", serviceName = "SmsMtTransfererService")
public class SmsMtTransferer {
	private EsmeSmsMtBo bo;

	public SmsMtTransferer() {
		bo = ServiceLocator.createService(EsmeSmsMtBo.class);
	};

	public Long add(EsmeSmsMt esmeServices) throws Exception {
		return bo.persist(esmeServices);
	}

	public void update(EsmeSmsMt esmeServices) throws Exception {
		bo.update(esmeServices);
	}

	public void delete(EsmeSmsMt esmeServices) throws Exception {
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

	public int checkExisted(EsmeSmsMt esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmsMt> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSmsMt> findAllWithOrderPaging(EsmeSmsMt esmeServices,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeServices, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeSmsMt esmeServices, boolean exactMatch) {
		try {
			return bo.count(esmeServices, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmsRouting> FindBySmsRouting(EsmeSmsRouting esmeSmsRouting)
			throws Exception {

		return bo.FindBySmsRouting(esmeSmsRouting);
	}

	public List<EsmeCp> findByCP(String id) throws Exception {

		return bo.findByCP(id);
	}

	public List<EsmeSmsCommand> findByCommand(String id) throws Exception {

		return bo.findByCommand(id);
	}

	public List<EsmeShortCode> findByShortCode(String id) throws Exception {

		return bo.findByShortCode(id);
	}

	public void deleteByFileUploadId(long fileUploadId) throws Exception {
		bo.deleteByFileUploadId(fileUploadId);
	}

	public long addMultiProcess(List<EsmeSmsMt> esmeSmsMt) throws Exception {
		return bo.addMultiProcess(esmeSmsMt);
	}

	public void stopUpload() throws Exception {
		bo.stopUpload();
	}

}
