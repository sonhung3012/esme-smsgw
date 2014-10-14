package com.fis.esme.subgroups;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.SubGroupBo;
import com.fis.esme.persistence.SubGroup;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://subgroups.esme.fis.com/", portName = "SubGroupsTransfererPort", serviceName = "SubGroupsTransfererService")
public class SubGroupsTransferer {
	private SubGroupBo bo;

	public SubGroupsTransferer() {
		bo = ServiceLocator.createService(SubGroupBo.class);
	};

	public Long add(SubGroup SubGroup) throws Exception {
		return bo.persist(SubGroup);
	}

	public void update(SubGroup SubGroup) throws Exception {
		bo.update(SubGroup);
	}

	public void delete(SubGroup SubGroup) throws Exception {
		bo.delete(SubGroup);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(SubGroup SubGroup) {
		try {
			return bo.checkExited(SubGroup);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<SubGroup> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<SubGroup> findAllWithOrderPaging(SubGroup SubGroup,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(SubGroup, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(SubGroup SubGroup, boolean exactMatch) {
		try {
			return bo.count(SubGroup, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
