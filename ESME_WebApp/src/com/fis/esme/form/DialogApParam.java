package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.component.CustomTable;
import com.fis.esme.persistence.ApParam;
import com.fis.esme.util.CacheDB;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.i18n.TM;

public class DialogApParam extends WDialog {

	private VerticalLayout mainDisplay;
	private BeanItemContainer<ApParam> dataApParam = new BeanItemContainer<ApParam>(ApParam.class);
	private CustomTable tbl;
	private FormMessageFieldFactory parent;
	private Button btnOk, btnCancel;
	private HorizontalLayout buttonLayout = new HorizontalLayout();

	public DialogApParam(String caption, FormMessageFieldFactory parent) {

		super(caption);
		this.parent = parent;
		init();
	}

	private void init() {

		initComponent();
		initData();
		initButton();
		initTable();

	}

	private void initComponent() {

		this.setWidth("300px");
		this.setHeight("400px");
		this.setPositionX(900);
		this.setPositionY(150);
		this.setStyleName("pop_window");
		this.setResizable(true);

	}

	private void initData() {

		try {
			ApParam apParam = new ApParam();
			CacheDB.cacheApParam = CacheServiceClient.serviceApParam.findAllWithOrderPaging(apParam, null, false, -1, -1, true);
			Collections.sort(CacheDB.cacheApParam, FormUtil.stringComparator(true));

			for (ApParam param : CacheDB.cacheApParam) {

				if ("SMS_ITEM_PATTERN".equalsIgnoreCase(param.getParType())) {

					dataApParam.addBean(param);
				}
			}
		} catch (com.fis.esme.apparam.Exception_Exception e) {
			e.printStackTrace();
		}
	}

	private void initButton() {

		btnOk = new Button(TM.get("message.dialog.button.ok.caption"));
		btnCancel = new Button(TM.get("message.dialog.button.cancel.caption"));
		btnOk.setWidth("70px");
		btnCancel.setWidth("70px");

		btnOk.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				accept();
				close();
			}
		});

		btnCancel.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				close();
			}
		});
	}

	private void initTable() {

		mainDisplay = new VerticalLayout();
		mainDisplay.setSizeFull();
		mainDisplay.setSpacing(true);

		tbl = new CustomTable("", dataApParam) {

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("message.dialogApparam.sortColumnsProperties").split(",");
				for (Object obj : sortCol) {

					arr.add(obj);

				}
				return arr;
			}

		};
		tbl.setImmediate(true);

		tbl.addGeneratedColumn("select", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				final ApParam bean = (ApParam) itemId;

				CheckBox checkBox = new CheckBox();
				checkBox.setImmediate(true);
				checkBox.addListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(Property.ValueChangeEvent event) {

						bean.setSelect((Boolean) event.getProperty().getValue());
					}
				});
				if (bean.isSelect()) {
					checkBox.setValue(true);
				} else {
					checkBox.setValue(false);
				}
				return checkBox;
			}
		});

		tbl.addListener(new Table.HeaderClickListener() {

			public void headerClick(HeaderClickEvent event) {

				String property = event.getPropertyId().toString();
				if (property.equals("select")) {
					tbl.setSelectAll(!tbl.isSelectAll());
					for (int i = 0; i < dataApParam.size(); i++) {
						ApParam bean = dataApParam.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		tbl.setSizeFull();
		tbl.setSelectable(true);
		tbl.setNullSelectionAllowed(false);
		tbl.setVisibleColumns(TM.get("message.dialogApparam.visibleColumns").split(","));
		tbl.setColumnHeaders(TM.get("message.dialogApparam.visibleColumnsCaption").split(","));

		String[] VisibleColumns = TM.get("message.dialogApparam.columnwidth").split(",");
		String[] VisibleColumnsSize = TM.get("message.dialogApparam.columnwidth_value").split(",");
		for (int i = 0; i < VisibleColumns.length; i++) {
			int size = -1;
			try {
				size = Integer.parseInt(VisibleColumnsSize[i]);
			} catch (Exception e) {

			}
			tbl.setColumnWidth(VisibleColumns[i], size);
		}

		tbl.setColumnCollapsingAllowed(false);
		tbl.setColumnReorderingAllowed(false);

		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(btnOk);
		buttonLayout.addComponent(btnCancel);
		buttonLayout.setComponentAlignment(btnOk, Alignment.MIDDLE_CENTER);
		buttonLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_CENTER);

		mainDisplay.addComponent(tbl);
		mainDisplay.addComponent(buttonLayout);
		mainDisplay.setExpandRatio(tbl, 1.0f);
		mainDisplay.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		this.setContent(mainDisplay);
	}

	public void accept() {

		getAllItemCheckedOnTable();
		String strParam = "";
		for (ApParam value : getAllItemCheckedOnTable()) {

			strParam += strParam.equals("") ? value.getParName() : " " + value.getParName();
		}
		parent.getTxtMessage().setValue(
		        ((String) parent.getTxtMessage().getValue()).substring(0, parent.getTxtMessage().getCursorPosition()) + strParam
		                + ((String) parent.getTxtMessage().getValue()).substring(parent.getTxtMessage().getCursorPosition()));

	}

	public List<ApParam> getAllItemCheckedOnTable() {

		List<ApParam> list = new ArrayList<ApParam>();
		Collection<ApParam> collection = (Collection<ApParam>) tbl.getItemIds();
		for (ApParam obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

}
