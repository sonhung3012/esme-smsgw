package com.fis.esme.subscriberdt;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.SubGroupBo;
import com.fis.esme.bo.SubscriberBo;
import com.fis.esme.persistence.SubGroup;
import com.fis.esme.persistence.Subscriber;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://subscriberdt.esme.fis.com/", portName = "SubscriberDTTransfererPort", serviceName = "SubscriberDTTransfererService")
public class SubscriberDTTransferer {
	private static final SubscriberBo bo = ServiceLocator
			.createService(SubscriberBo.class);
	private static final SubGroupBo sgBo = ServiceLocator
			.createService(SubGroupBo.class);

	public void SubscriberDTTransferer() {
	};

	public Long add(Subscriber esmeServices, long groupId) throws Exception {
		long id = bo.persist(esmeServices);
		SubGroup sg = new SubGroup();
		esmeServices.setSubId(id);
		sg.setSubId(esmeServices.getSubId());
		sg.setGroupId(groupId);
		sgBo.persist(sg);
		return id;
	}

	public void update(Subscriber esmeServices, long groupId) throws Exception {
		SubGroup sg = new SubGroup();
		sg.setSubId(esmeServices.getSubId());
		sg.setGroupId(groupId);
		sgBo.update(sg);
		bo.update(esmeServices);
	}

	public void delete(Subscriber esmeServices, long groupId) throws Exception {
		SubGroup sg = new SubGroup();
		sg.setSubId(esmeServices.getSubId());
		sg.setGroupId(groupId);
		sgBo.delete(sg);
		bo.delete(esmeServices);
	}

	// public boolean checkConstraints(Long id) {
	// try {
	// return bo.checkConstraints(id);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }

	public int checkExisted(Subscriber esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<Subscriber> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<Subscriber> findAllWithOrderPaging(SearchEntity searchEntity,
			Subscriber esmeServices, String sortedColumn, boolean asc,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception {
		return bo.findAll(esmeServices, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(SearchEntity searchEntity, Subscriber esmeServices,
			boolean exactMatch) {
		try {
			return bo.count(esmeServices, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
