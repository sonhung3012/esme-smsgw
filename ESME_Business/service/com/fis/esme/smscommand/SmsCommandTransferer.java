package com.fis.esme.smscommand;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSmsCommandBo;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://smscommand.esme.fis.com/", portName = "SmsCommandTransfererPort", serviceName = "SmsCommandTransfererService")
public class SmsCommandTransferer {
	private EsmeSmsCommandBo bo;

	public SmsCommandTransferer() {
		bo = ServiceLocator.createService(EsmeSmsCommandBo.class);
	};

	public Long add(EsmeSmsCommand smsCommand) throws Exception {
		return bo.persist(smsCommand);
	}
	
	public void update(EsmeSmsCommand smsCommand) throws Exception {
		bo.update(smsCommand);
	}

	public void delete(EsmeSmsCommand smsCommand) throws Exception {
		bo.delete(smsCommand);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeSmsCommand smsCommand) {
		try {
			return bo.checkExited(smsCommand);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmsCommand> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSmsCommand> findAllWithOrderPaging(EsmeSmsCommand smsCommand,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(smsCommand, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeSmsCommand smsCommand, boolean exactMatch) {
		try {
			return bo.count(smsCommand, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
