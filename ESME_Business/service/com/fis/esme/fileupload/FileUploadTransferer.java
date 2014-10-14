package com.fis.esme.fileupload;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeFileUploadBo;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://fileupload.esme.fis.com/", portName = "FileUploadTransfererPort", serviceName = "FileUploadTransfererService")
public class FileUploadTransferer {
	private EsmeFileUploadBo bo;

	public FileUploadTransferer() {
		bo = ServiceLocator.createService(EsmeFileUploadBo.class);
	};

	public Long add(EsmeFileUpload esmeFileUpload) throws Exception {
		return bo.persist(esmeFileUpload);
	}
	
	public void update(EsmeFileUpload esmeFileUpload) throws Exception {
		bo.update(esmeFileUpload);
	}

	public void delete(EsmeFileUpload esmeFileUpload) throws Exception {
		bo.delete(esmeFileUpload);
	}

	public int checkExisted(EsmeFileUpload esmeFileUpload) {
		try {
			return bo.checkExited(esmeFileUpload);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeFileUpload> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeFileUpload> findAllWithOrderPaging(EsmeFileUpload esmeFileUpload,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeFileUpload, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeFileUpload esmeFileUpload, boolean exactMatch) {
		try {
			return bo.count(esmeFileUpload, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<EsmeFileUpload> findAllByDate(Date fromDate, Date toDate)
			throws Exception {
		
		return bo.findAllByDate(fromDate, toDate);
	}
	
	public List<EsmeFileUpload> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {
		
		return bo.findAllInDateByField(field, fromDate, toDate);
	}

}
