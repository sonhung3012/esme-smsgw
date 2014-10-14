package com.fis.esme.form.subdetail;

import com.fis.esme.component.SubDetailSearchFactory;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class SubRewardDetailSearchFactory extends SubDetailSearchFactory {
	private TextField subRewardSearch;

	public SubRewardDetailSearchFactory() {
		super(false, false);
		initSearch();
	}

	private void initSearch() {
		subRewardSearch = new TextField(TM.get("subreward.Caption"));
//		subRewardSearch.setCaption("MSDT");
		subRewardSearch.setWidth("100px");
		subRewardSearch.setMaxLength(12);
		// String sms =
		// "<span style='color:red'>Số chuyển tiếp cần nhập đúng định dạng 09xxxxxxxx,01xxxxxxxxx hoặc 9xxxxxxxx, 1xxxxxxxxx </span>";
		// subForwardSearch.addValidator(new RegexpValidator(
		// "^((01)|(09)|9|1)[0-9]{8,9}$", sms));
		subRewardSearch.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		if ("subRewardSearch".equals(propertyId)) {
			return subRewardSearch;
		}
		return super.createField(item, propertyId, uiContext);
	}

}
