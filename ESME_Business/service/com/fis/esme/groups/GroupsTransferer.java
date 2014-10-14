package com.fis.esme.groups;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeGroupsBo;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://groups.esme.fis.com/", portName = "GroupsTransfererPort", serviceName = "GroupsTransfererService")
public class GroupsTransferer {
	private EsmeGroupsBo bo;

	public GroupsTransferer() {
		bo = ServiceLocator.createService(EsmeGroupsBo.class);
	};

	public Long add(EsmeGroups esmeGroups) throws Exception {
		return bo.persist(esmeGroups);
	}
	
	public void update(EsmeGroups esmeGroups) throws Exception {
		bo.update(esmeGroups);
	}

	public void delete(EsmeGroups esmeGroups) throws Exception {
		bo.delete(esmeGroups);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeGroups esmeGroups) {
		try {
			return bo.checkExited(esmeGroups);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeGroups> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeGroups> findAllWithOrderPaging(EsmeGroups esmeGroups,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeGroups, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeGroups esmeGroups, boolean exactMatch) {
		try {
			return bo.count(esmeGroups, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
