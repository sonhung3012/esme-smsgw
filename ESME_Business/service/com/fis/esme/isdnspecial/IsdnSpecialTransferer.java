package com.fis.esme.isdnspecial;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeIsdnSpecialBo;
import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.persistence.EsmeIsdnSpecial;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://isdnspecial.esme.fis.com/", portName = "IsdnSpecialTransfererPort", serviceName = "IsdnSpecialTransfererService")
public class IsdnSpecialTransferer {
	private EsmeIsdnSpecialBo bo;

	public IsdnSpecialTransferer() {
		bo = ServiceLocator.createService(EsmeIsdnSpecialBo.class);
	};

	public String add(EsmeIsdnSpecial esmeIsdnSpecial) throws Exception {
		return bo.persist(esmeIsdnSpecial);
	}

	public void update(EsmeIsdnSpecial esmeIsdnSpecial) throws Exception {
		bo.update(esmeIsdnSpecial);
	}

	public void delete(EsmeIsdnSpecial esmeIsdnSpecial) throws Exception {
		bo.delete(esmeIsdnSpecial);
	}

	public boolean checkConstraints(String id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial) {
		try {
			return bo.checkExited(searchEntity, esmeIsdnSpecial);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeIsdnSpecial> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeIsdnSpecial> findAllWithParameter(
			SearchEntity searchEntity, EsmeIsdnSpecial esmeIsdnSpecial)
			throws Exception {
		return bo.findAll(searchEntity, esmeIsdnSpecial);
	}

	public List<EsmeIsdnSpecial> findAllWithOrderPaging(
			SearchEntity searchEntity, EsmeIsdnSpecial esmeIsdnSpecial,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(searchEntity, esmeIsdnSpecial, sortedColumn, asc,
				firstItemIndex, maxItems, exactMatch);
	}

	public int count(EsmeIsdnSpecial esmeIsdnSpecial, boolean exactMatch) {
		try {
			return bo.count(null, esmeIsdnSpecial, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int countAll() {
		try {
			return bo.countAll();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void updateSpecial(EsmeIsdnSpecial esmeIsdnSpecial, String oldMsisdn)
			throws Exception {
		bo.updateSpecial(esmeIsdnSpecial, oldMsisdn);
	}
	
	public void deletePermissionByMsisdn(String msisdn) throws Exception {
		bo.deletePermissionByMsisdn(msisdn);
	}
	
	public List<EsmeIsdnPermission> getPermissionByMisdn(String msisdn)
			throws Exception {
		return bo.getPermissionByMisdn(msisdn);
	}
}
