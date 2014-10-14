package com.fis.esme.language;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeLanguageBo;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://language.esme.fis.com/", portName = "languageTransfererPort", serviceName = "languageTransfererService")
public class languageTransferer {
	
	private EsmeLanguageBo languageBo;
	
	public languageTransferer(){
		
		languageBo = ServiceLocator.createService(EsmeLanguageBo.class);
	}
	public long add(EsmeLanguage language) throws Exception
	{
		return languageBo.persist(language);
	}
	
	public void edit(EsmeLanguage language) throws Exception
	{
		languageBo.update(language);
	}
	
	public void delete(EsmeLanguage language) throws Exception
	{
		languageBo.delete(language);
	}
	public void deleteAll() throws Exception
	{
		languageBo.deleteAll();
	}
	
	public void updateAllLanguage(EsmeLanguage language,String field, String value) throws Exception {
		languageBo.updateAllLanguage(language,field, value);
	}
	
	public EsmeLanguage getDefaultLanguage() throws Exception {
		return languageBo.getDefaultLanguage();
		
	}
	
	public List<EsmeLanguage> getLanguageStatus() {
		return languageBo.getLanguageStatus();
		
	}
		
	public int countAll()
	{
		try
		{
			return languageBo.countAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public int checkExisted(EsmeLanguage PrcService) {
		try {
			return languageBo.checkExited(PrcService);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	
	public boolean checkConstraints(Long id)
	{
		try
		{
			return languageBo.checkConstraints(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	public List<EsmeLanguage> findAll() throws Exception {
		return languageBo.findAll();
	}
	
	public List<EsmeLanguage> findAllWithoutParameter() throws Exception {
		return languageBo.findAll();
	}

	public List<EsmeLanguage> findAllWithOrderPaging(
			EsmeLanguage language, String sortedColumn, boolean asc,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception {
		return languageBo.findAll(language, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeLanguage language, boolean exactMatch) {
		try {
			return languageBo.count(language, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
