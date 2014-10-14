package com.fis.esme.component;

import java.util.ArrayList;
import java.util.Collection;

import com.fis.esme.classes.CustomRegexpValidator;
import com.vaadin.data.Validator;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class PromValueTable extends Table {
	private ArrayList<Field> fields;
	private CustomRegexpValidator validator = new CustomRegexpValidator("", "");
	private Form form;
	private boolean modified = false;

	public PromValueTable(Form form) {
		fields = new ArrayList<Field>();
		this.form = form;
//		this.addValidator(validator);
	}

	public void registerFieldToValidate(Field field) {
		if (fields.contains(field)) {
			return;
		}

		fields.add(field);
	}


	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public void clearAllFields() {
		fields.clear();
	}

	public boolean validateFields() {
//		this.setValidationVisible(false);
//		form.setValidationVisible(false);
		
		for (Field field : fields) {
			try {
				field.validate();
				field.removeStyleName("err");
				this.removeValidator(validator);
				this.setValidationVisible(false);
				form.setValidationVisible(false);
				field.commit();
			} catch (Validator.InvalidValueException ex) {
				ex.printStackTrace();
//				System.out.println("error: " + field.getValue());
				field.addStyleName("err");
				field.focus();
				((TextField) field).selectAll();
				
				if (this.getValidators() == null || !this.getValidators().contains(validator))
				{
					this.addValidator(validator);
				}
				
				validator.setErrorMessage(ex.getMessage());
				this.setValidationVisible(true);
				form.setValidationVisible(true);
				return false;
			}
		}

//		this.removeValidator(validator);
		return true;
	}

	public boolean isTableModified() {
		for (Field field : fields) {
			if (field.isModified()) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public boolean isModified()
	{
		return modified || super.isModified();
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}
}