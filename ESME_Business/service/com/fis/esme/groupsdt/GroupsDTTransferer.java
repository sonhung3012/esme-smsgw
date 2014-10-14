package com.fis.esme.groupsdt;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.GroupsBo;
import com.fis.esme.persistence.Groups;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://groupsdt.esme.fis.com/", portName = "GroupsDTTransfererPort", serviceName = "GroupsDTTransfererService")
public class GroupsDTTransferer {
	private GroupsBo bo;

	public GroupsDTTransferer() {
		bo = ServiceLocator.createService(GroupsBo.class);
	};

	public Long add(Groups Groups) throws Exception {
		return bo.persist(Groups);
	}

	public void update(Groups Groups) throws Exception {
		bo.update(Groups);
	}

	public void delete(Groups Groups) throws Exception {
		bo.delete(Groups);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(Groups Groups) {
		try {
			return bo.checkExited(Groups);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<Groups> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<Groups> findAllWithOrderPaging(Groups Groups,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(Groups, sortedColumn, asc, firstItemIndex, maxItems,
				exactMatch);
	}

	public int count(Groups Groups, boolean exactMatch) {
		try {
			return bo.count(Groups, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Groups findBySubGgroup(long subId) {
		try {
			return bo.findBySubGgroup(subId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
