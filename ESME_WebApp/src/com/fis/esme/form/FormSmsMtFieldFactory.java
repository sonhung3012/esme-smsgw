package com.fis.esme.form;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.emsmt.EmsMtTransferer;
import com.fis.esme.emsmo.EmsMoTransferer;
import com.fis.esme.util.MessageAlerter;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormSmsMtFieldFactory extends DefaultFieldFactory implements
PropertyExisted, FieldsValidatorInterface{

	private final TextArea mtMessage = new TextArea(
			TM.get("emsmt.field_mtMessage.caption"));
	private EmsMtTransferer serviceEmsMt;
	private boolean search = false;
	private EmsMtTransferer emsmtService;
	private EmsMoTransferer emsmoService;	
	public FormSmsMtFieldFactory() {
		initService();
		initTextArea();
	}
	private void initTextArea() {

		mtMessage.setMaxLength(500);
		mtMessage.setWidth("350px");
		mtMessage.setHeight("150px");
		String nullmessgeMsg = TM.get("common.field.msg.validator_nulloremty",
				mtMessage.getCaption());
		mtMessage.setRequired(true);
		mtMessage.setRequiredError(nullmessgeMsg);
		SpaceValidator empty = new SpaceValidator(nullmessgeMsg);
		mtMessage.addValidator(empty);
		mtMessage.setNullRepresentation("");
	}	
	private void initService() {
		try {
			emsmoService = CacheServiceClient.emsMoService;
			emsmtService = CacheServiceClient.emsMtService;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public FormSmsMtFieldFactory(boolean search) {
		this.search = search;
	}

	@Override
	public Object isValid(String property, Object currentFieldValue,
			Object otherObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		
		if (propertyId.equals("mtMessage")) {
			return mtMessage;
		}else {
			return super.createField(item, propertyId, uiContext);
		}	
		
	}
}
