package com.fis.esme.isdnpermission;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeIsdnPermissionBo;
import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://isdnpermission.esme.fis.com/", portName = "IsdnPermissionTransfererPort", serviceName = "IsdnPermissionTransfererService")
public class IsdnPermissionTransferer {
	private EsmeIsdnPermissionBo bo;

	public IsdnPermissionTransferer() {
		bo = ServiceLocator.createService(EsmeIsdnPermissionBo.class);
	};

	public Long add(EsmeIsdnPermission esmeIsdnPermission) throws Exception {
		return bo.persist(esmeIsdnPermission);
	}

	public void update(EsmeIsdnPermission esmeIsdnPermission) throws Exception {
		bo.update(esmeIsdnPermission);
	}

	public void delete(EsmeIsdnPermission esmeIsdnPermission) throws Exception {
		bo.delete(esmeIsdnPermission);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission) {
		try {
			return bo.checkExited(searchEntity, esmeIsdnPermission);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeIsdnPermission> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeIsdnPermission> findAllWithParameter(
			SearchEntity searchEntity, EsmeIsdnPermission esmeIsdnPermission)
			throws Exception {
		return bo.findAll(searchEntity, esmeIsdnPermission);
	}

	public List<EsmeIsdnPermission> findAllWithOrderPaging(
			SearchEntity searchEntity, EsmeIsdnPermission esmeIsdnPermission,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(searchEntity, esmeIsdnPermission, sortedColumn, asc,
				firstItemIndex, maxItems, exactMatch);
	}

	public int count(EsmeIsdnPermission esmeIsdnPermission, boolean exactMatch) {
		try {
			return bo.count(null, esmeIsdnPermission, exactMatch);
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
}
