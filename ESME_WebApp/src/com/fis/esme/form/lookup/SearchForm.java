package com.fis.esme.form.lookup;

import java.util.Iterator;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.i18n.TM;

public class SearchForm extends Form {

	public ContextHelp contextHelp;
	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout;
	// private FormLayout msisdnLayout = new FormLayout();
	// private FormLayout fromDateLayout = new FormLayout();
	// private FormLayout toDateLayout = new FormLayout();
	// private FormLayout shortCodeLayout = new FormLayout();
	// private FormLayout serviceLayout = new FormLayout();
	// private FormLayout commandLayout = new FormLayout();
	private VerticalLayout btnLayout = new VerticalLayout();
	private GridLayout gridLayout = new GridLayout(7, 2);
	private Label lblPhoneBook = new Label(TM.get("smslog.formSearch.field.msisdn.caption"));
	private Label lblFromDay = new Label(TM.get("smslog.formSearch.field.fromDate.caption"));
	private Label lblToDay = new Label(TM.get("smslog.formSearch.field.toDate.caption"));
	private Label lblShortCode = new Label(TM.get("smslog.formSearch.field.shortCode.caption"));
	private Label lblService = new Label(TM.get("smslog.formSearch.field.service.caption"));
	private Label lblCommand = new Label(TM.get("smslog.formSearch.field.command.caption"));

	public SearchForm() throws Exception {

		this.setFormFieldFactory(new SearchFormFieldFactory());
		// mainLayout = new VerticalLayout();
		// mainLayout.setMargin(false);
		// mainLayout.setSpacing(true);

		// HorizontalLayout h1layout = new HorizontalLayout();
		// // HorizontalLayout h2layout = new HorizontalLayout();
		// HorizontalLayout h3layout = new HorizontalLayout();
		// setLayout(mainLayout);
		// setInvalidCommitted(false);

		// msisdnLayout.setSpacing(true);
		// fromDateLayout.setSpacing(true);
		// toDateLayout.setSpacing(true);
		// h1layout.setSpacing(true);
		// h1layout.addComponent(msisdnLayout);
		// h1layout.addComponent(fromDateLayout);
		// h1layout.addComponent(toDateLayout);
		// h2layout.addComponent(shortCodeLayout);
		// h2layout.addComponent(serviceLayout);
		// h2layout.addComponent(commandLayout);
		// h3layout.addComponent(btnLayout);
		// h3layout.setSpacing(true);
		// btnLayout.setSizeFull();

		// mainLayout.addComponent(h1layout);
		// mainLayout.addComponent(h2layout);
		lblPhoneBook.setWidth("60px");
		lblFromDay.setWidth("60px");
		lblToDay.setWidth("60px");
		lblShortCode.setWidth("60px");
		lblService.setWidth("60px");
		lblCommand.setWidth("60px");

		gridLayout.setSizeFull();
		gridLayout.setSpacing(true);

		gridLayout.addComponent(lblPhoneBook, 0, 0);
		gridLayout.addComponent(lblFromDay, 2, 0);
		gridLayout.addComponent(lblToDay, 4, 0);
		gridLayout.addComponent(lblShortCode, 0, 1);
		gridLayout.addComponent(lblService, 2, 1);
		gridLayout.addComponent(lblCommand, 4, 1);
		gridLayout.setComponentAlignment(lblPhoneBook, Alignment.MIDDLE_CENTER);
		gridLayout.setComponentAlignment(lblFromDay, Alignment.MIDDLE_CENTER);
		gridLayout.setComponentAlignment(lblToDay, Alignment.MIDDLE_CENTER);
		gridLayout.setComponentAlignment(lblShortCode, Alignment.MIDDLE_CENTER);
		gridLayout.setComponentAlignment(lblService, Alignment.MIDDLE_CENTER);
		gridLayout.setComponentAlignment(lblCommand, Alignment.MIDDLE_CENTER);

		gridLayout.setColumnExpandRatio(1, 0.3f);
		gridLayout.setColumnExpandRatio(3, 0.3f);
		gridLayout.setColumnExpandRatio(5, 0.3f);

		mainLayout = new VerticalLayout();
		mainLayout.setSpacing(false);
		mainLayout.addComponent(gridLayout);
		mainLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		gridLayout.setMargin(false, false, false, true);
		this.setLayout(mainLayout);
		this.setSizeFull();

		// h1layout.setComponentAlignment(msisdnLayout, Alignment.TOP_LEFT);
		// h1layout.setComponentAlignment(fromDateLayout, Alignment.TOP_CENTER);
		// h1layout.setComponentAlignment(toDateLayout, Alignment.TOP_RIGHT);
		// h2layout.setComponentAlignment(shortCodeLayout,
		// Alignment.MIDDLE_LEFT);
		// h2layout.setComponentAlignment(serviceLayout,
		// Alignment.MIDDLE_CENTER);
		// h2layout.setComponentAlignment(commandLayout,
		// Alignment.MIDDLE_RIGHT);
		// h3layout.setComponentAlignment(btnLayout, Alignment.MIDDLE_CENTER);
	}

	public void setSearchButton(Button btn) {

		// btnLayout.addComponent(btn);
		gridLayout.addComponent(btn, 6, 0);
		// btnLayout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
	}

	public void setSearchButtonReport(Button btn) {

		// btnLayout.addComponent(btn);
		gridLayout.addComponent(btn, 6, 1);
		// btnLayout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
	}

	public boolean isValid() {

		boolean valid = true;
		for (final Iterator<?> i = getItemPropertyIds().iterator(); i.hasNext();) {
			Field field = getField(i.next());

			if (field instanceof Table) {
				return true;
			}

			if (!field.isValid()) {
				field.focus();
				if (field instanceof AbstractTextField) {
					((AbstractTextField) field).selectAll();
				}
				setValidationVisible(true);
				return false;
			}
		}
		return valid;
	}

	@Override
	protected void attachField(Object propertyId, Field field) {

		if (propertyId.equals("tfMsisdn")) {
			gridLayout.addComponent(field, 1, 0);
		} else if (propertyId.equals("fromDate")) {
			gridLayout.addComponent(field, 3, 0);
		} else if (propertyId.equals("toDate")) {
			gridLayout.addComponent(field, 5, 0);
		} else if (propertyId.equals("cbbShortCode")) {
			gridLayout.addComponent(field, 1, 1);
		} else if (propertyId.equals("cbbService")) {
			gridLayout.addComponent(field, 3, 1);
		} else if (propertyId.equals("cbbCommand")) {
			gridLayout.addComponent(field, 5, 1);
		}
	}
}
