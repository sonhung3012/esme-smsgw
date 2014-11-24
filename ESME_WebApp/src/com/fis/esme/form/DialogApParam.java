package com.fis.esme.form;

import java.util.Collections;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.component.CustomTable;
import com.fis.esme.persistence.ApParam;
import com.fis.esme.util.CacheDB;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.i18n.TM;

public class DialogApParam extends WDialog {

	private VerticalLayout mainDisplay;
	private BeanItemContainer<ApParam> dataApParam = new BeanItemContainer<ApParam>(ApParam.class);
	private CustomTable tbl;
	private FormMessageFieldFactory parent;
	public String value;

	public DialogApParam(String caption, FormMessageFieldFactory parent) {

		super(caption);
		this.parent = parent;
		init();
	}

	private void init() {

		initComponent();
		initData();
		initTable();

	}

	private void initComponent() {

		this.setWidth("300px");
		this.setHeight("400px");
		this.setPositionX(900);
		this.setPositionY(150);
		this.setStyleName("pop_window");
		this.setResizable(false);

	}

	private void initData() {

		try {
			ApParam apParam = new ApParam();
			CacheDB.cacheApParam = CacheServiceClient.serviceApParam.findAllWithOrderPaging(apParam, null, false, -1, -1, true);
			Collections.sort(CacheDB.cacheApParam, FormUtil.stringComparator(true));
			dataApParam.addAll(CacheDB.cacheApParam);
		} catch (com.fis.esme.apparam.Exception_Exception e) {
			e.printStackTrace();
		}
	}

	private void initTable() {

		mainDisplay = new VerticalLayout();
		mainDisplay.setSizeFull();
		mainDisplay.setSpacing(true);

		tbl = new CustomTable("", dataApParam);
		tbl.setImmediate(true);

		tbl.setSizeFull();
		tbl.setSelectable(true);
		tbl.setNullSelectionAllowed(false);
		tbl.setVisibleColumns(TM.get("message.dialogApparam.visibleColumns").split(","));
		tbl.setColumnHeaders(TM.get("message.dialogApparam.visibleColumnsCaption").split(","));

		tbl.addListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {

				if (event.isDoubleClick()) {
					BeanItem<ApParam> item = (BeanItem<ApParam>) event.getItem();
					ApParam bean = item.getBean();

					parent.getTxtMessage().setValue(parent.getTxtMessage().getValue() + bean.getParName());
					close();
				}
			}
		});

		tbl.setSortDisabled(true);
		tbl.setColumnCollapsingAllowed(false);
		tbl.setColumnReorderingAllowed(false);

		mainDisplay.addComponent(tbl);
		mainDisplay.setExpandRatio(tbl, 1.0f);
		this.setContent(mainDisplay);
	}
}
