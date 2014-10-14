package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fis.esme.component.CustomTable;
import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.i18n.TM;

public class DialogWeeklyTableApprover extends WDialog {
	private VerticalLayout mainDisplay;
	private BeanItemContainer<WeeklyTable> conWeekly;
	private CustomTable tblWeekly;
	private List<WeeklyTable> lstDataWeekly = new ArrayList<WeeklyTable>();
	private List<WeeklyTable> lstDataMonth = new ArrayList<WeeklyTable>();
	private String type;
	private BeanItemContainer<WeeklyTable> dataSelect = new BeanItemContainer<WeeklyTable>(
			WeeklyTable.class);
	private FormMessageSchedulerApprover parent;
	public String value;
	private Button btnOk, btnCancel;
	private HorizontalLayout buttonLayout = new HorizontalLayout();

	public DialogWeeklyTableApprover(String caption, String type, FormMessageSchedulerApprover parent) {
		super(caption);
		this.type = type;
		this.parent = parent;
		this.setWidth("200px");
		this.setHeight("300px");
		this.setPositionX(750);
		this.setPositionY(250);
		this.setStyleName("pop_window");
		this.setResizable(false);
		createData();
		initButton();
		init();
	}

	private void initButton() {

		btnOk = new Button("Ok");
		btnCancel = new Button("Cancel");
		btnOk.setWidth("70px");
		btnCancel.setWidth("70px");
		btnOk.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getAllItemCheckedOnTable();
				String charter = "";
				for (WeeklyTable value : getAllItemCheckedOnTable()) {
					if (charter == null || charter.equalsIgnoreCase("")) {
						charter += value.getName();
					} else {
						charter += ";" + value.getName();
					}
				}
				setValue(charter);
				parent.setValueDate(charter);
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

	private void createData() {

		conWeekly = new BeanItemContainer<WeeklyTable>(WeeklyTable.class);
		if (lstDataWeekly.size() <= 0) {
			lstDataWeekly.add(0,new WeeklyTable("Monday", false));
			lstDataWeekly.add(1,new WeeklyTable("Tuesday", false));
			lstDataWeekly.add(2,new WeeklyTable("Wednesday", false));
			lstDataWeekly.add(3,new WeeklyTable("Thursday", false));
			lstDataWeekly.add(4,new WeeklyTable("Friday", false));
			lstDataWeekly.add(5,new WeeklyTable("Saturday", false));
			lstDataWeekly.add(6,new WeeklyTable("Sunday", false));
		}

		if (lstDataMonth.size() <= 0) {
			// for (int i = 1; i < 31; i++) {
			// lstDataMonth.add(new WeeklyTable(i + "th", false));
			// }
			lstDataMonth.add(0,new WeeklyTable(1 + "st.", false));
			lstDataMonth.add(1,new WeeklyTable(2 + "nd.", false));
			lstDataMonth.add(2,new WeeklyTable(3 + "rd.", false));
			lstDataMonth.add(3,new WeeklyTable(4 + "th.", false));
			lstDataMonth.add(4,new WeeklyTable(5 + "th.", false));
			lstDataMonth.add(5,new WeeklyTable(6 + "th.", false));
			lstDataMonth.add(6,new WeeklyTable(7 + "th.", false));
			lstDataMonth.add(7,new WeeklyTable(8 + "th.", false));
			lstDataMonth.add(8,new WeeklyTable(9 + "th.", false));
			lstDataMonth.add(9,new WeeklyTable(10 + "th", false));
			lstDataMonth.add(10,new WeeklyTable(11 + "th", false));
			lstDataMonth.add(11,new WeeklyTable(12 + "th", false));
			lstDataMonth.add(12,new WeeklyTable(13 + "th", false));
			lstDataMonth.add(13,new WeeklyTable(14 + "th", false));
			lstDataMonth.add(14,new WeeklyTable(15 + "th", false));
			lstDataMonth.add(15,new WeeklyTable(16 + "th", false));
			lstDataMonth.add(16,new WeeklyTable(17 + "th", false));
			lstDataMonth.add(17,new WeeklyTable(18 + "th", false));
			lstDataMonth.add(18,new WeeklyTable(19 + "th", false));
			lstDataMonth.add(19,new WeeklyTable(20 + "th", false));
			lstDataMonth.add(20,new WeeklyTable(21 + "st", false));
			lstDataMonth.add(21,new WeeklyTable(22 + "nd", false));
			lstDataMonth.add(22,new WeeklyTable(23 + "rd", false));
			lstDataMonth.add(23,new WeeklyTable(24 + "th", false));
			lstDataMonth.add(24,new WeeklyTable(25 + "th", false));
			lstDataMonth.add(25,new WeeklyTable(26 + "th", false));
			lstDataMonth.add(26,new WeeklyTable(27 + "th", false));
			lstDataMonth.add(27,new WeeklyTable(28 + "th", false));
			lstDataMonth.add(28,new WeeklyTable(29 + "th", false));
			lstDataMonth.add(29,new WeeklyTable(30 + "th", false));
			lstDataMonth.add(30,new WeeklyTable(31 + "st", false));

		}
		
		String valueDisplay = null;
		
		if (parent.getValueDate() != null && type.equalsIgnoreCase("2")) {
			
			valueDisplay = parent.getValueDate();
			if (valueDisplay.contains("Monday")) {
				lstDataWeekly.set(0, new WeeklyTable("Monday", true));
			}else{
				lstDataWeekly.set(0, new WeeklyTable("Monday", false));
			}
			if (valueDisplay.contains("Tuesday")) {
				lstDataWeekly.set(1, new WeeklyTable("Tuesday", true));
			}else{
				lstDataWeekly.set(1, new WeeklyTable("Tuesday", false));
			}
			if (valueDisplay.contains("Wednesday")) {
				lstDataWeekly.set(2, new WeeklyTable("Wednesday", true));
			}else{
				lstDataWeekly.set(2, new WeeklyTable("Wednesday", false));
			}
			if (valueDisplay.contains("Thursday")) {
				lstDataWeekly.set(3, new WeeklyTable("Thursday", true));
			}else{
				lstDataWeekly.set(3, new WeeklyTable("Thursday", false));
			}
			if (valueDisplay.contains("Friday")) {
				lstDataWeekly.set(4, new WeeklyTable("Friday", true));
			}else{
				lstDataWeekly.set(4, new WeeklyTable("Friday", false));
			}
			if (valueDisplay.contains("Saturday")) {
				lstDataWeekly.set(5, new WeeklyTable("Saturday", true));
			}else{
				lstDataWeekly.set(5, new WeeklyTable("Saturday", false));
			}
			if (valueDisplay.contains("Sunday")) {
				lstDataWeekly.set(6, new WeeklyTable("Sunday", true));
			}else{
				lstDataWeekly.set(6, new WeeklyTable("Sunday", false));
			}
		} else if (parent.getValueDate() != null && type.equalsIgnoreCase("3")) {
			
			valueDisplay = parent.getValueDate();
			
			if (valueDisplay.contains("1st.")) {
				lstDataMonth.set(0,new WeeklyTable(1 + "st.", true));
			}else{
				lstDataMonth.set(0,new WeeklyTable(1 + "st.", false));
			}
			if (valueDisplay.contains("2nd.")) {
				lstDataMonth.set(1,new WeeklyTable(2 + "nd.", true));
			}else{
				lstDataMonth.set(1,new WeeklyTable(2 + "nd.", false));
			}
			if (valueDisplay.contains("3rd.")) {
				lstDataMonth.set(2,new WeeklyTable(3 + "rd.", true));
			}else{
				lstDataMonth.set(2,new WeeklyTable(3 + "rd.", false));
			}
			if (valueDisplay.contains("4th.")) {
				lstDataMonth.set(3,new WeeklyTable(4 + "th.", true));
			}else{
				lstDataMonth.set(3,new WeeklyTable(4 + "th.", false));
			}
			if (valueDisplay.contains("5th.")) {
				lstDataMonth.set(4,new WeeklyTable(5 + "th.", true));
			}else{
				lstDataMonth.set(4,new WeeklyTable(5 + "th.", false));
			}
			if (valueDisplay.contains("6th.")) {
				lstDataMonth.set(5,new WeeklyTable(6 + "th.", true));
			}else{
				lstDataMonth.set(5,new WeeklyTable(6 + "th.", false));
			}
			if (valueDisplay.contains("7th.")) {
				lstDataMonth.set(6,new WeeklyTable(7 + "th.", true));
			}else{
				lstDataMonth.set(6,new WeeklyTable(7 + "th.", false));
			}
			if (valueDisplay.contains("8th.")) {
				lstDataMonth.set(7,new WeeklyTable(8 + "th.", true));
			}else{
				lstDataMonth.set(7,new WeeklyTable(8 + "th.", false));
			}
			if (valueDisplay.contains("9th.")) {
				lstDataMonth.set(8,new WeeklyTable(9 + "th.", true));
			}else{
				lstDataMonth.set(8,new WeeklyTable(9 + "th.", false));
			}
			if (valueDisplay.contains("10th")) {
				lstDataMonth.set(9,new WeeklyTable(10 + "th", true));
			}else{
				lstDataMonth.set(9,new WeeklyTable(10 + "th", false));
			}
			if (valueDisplay.contains("11th")) {
				lstDataMonth.set(10,new WeeklyTable(11 + "th", true));
			}else{
				lstDataMonth.set(10,new WeeklyTable(11 + "th", false));
			}
			if (valueDisplay.contains("12th")) {
				lstDataMonth.set(11,new WeeklyTable(12 + "th", true));
			}else{
				lstDataMonth.set(11,new WeeklyTable(12 + "th", false));
			}
			if (valueDisplay.contains("13th")) {
				lstDataMonth.set(12,new WeeklyTable(13 + "th", true));
			}else{
				lstDataMonth.set(12,new WeeklyTable(13 + "th", false));
			}
			if (valueDisplay.contains("14th")) {
				lstDataMonth.set(13,new WeeklyTable(14 + "th", true));
			}else{
				lstDataMonth.set(13,new WeeklyTable(14 + "th", false));
			}
			if (valueDisplay.contains("15th")) {
				lstDataMonth.set(14,new WeeklyTable(15 + "th", true));
			}else{
				lstDataMonth.set(14,new WeeklyTable(15 + "th", false));
			}
			if (valueDisplay.contains("16th")) {
				lstDataMonth.set(15,new WeeklyTable(16 + "th", true));
			}else{
				lstDataMonth.set(15,new WeeklyTable(16 + "th", false));
			}
			if (valueDisplay.contains("17th")) {
				lstDataMonth.set(16,new WeeklyTable(17 + "th", true));
			}else{
				lstDataMonth.set(16,new WeeklyTable(17 + "th", false));
			}
			if (valueDisplay.contains("18th")) {
				lstDataMonth.set(17,new WeeklyTable(18 + "th", true));
			}else{
				lstDataMonth.set(17,new WeeklyTable(18 + "th", false));
			}
			if (valueDisplay.contains("19th")) {
				lstDataMonth.set(18,new WeeklyTable(19 + "th", true));
			}else{
				lstDataMonth.set(18,new WeeklyTable(19 + "th", false));
			}
			if (valueDisplay.contains("20th")) {
				lstDataMonth.set(19,new WeeklyTable(20 + "th", true));
			}else{
				lstDataMonth.set(19,new WeeklyTable(20 + "th", false));
			}
			if (valueDisplay.contains("21st")) {
				lstDataMonth.set(20,new WeeklyTable(21 + "st", true));
			}else{
				lstDataMonth.set(20,new WeeklyTable(21 + "st", false));
			}
			if (valueDisplay.contains("22nd")) {
				lstDataMonth.set(21,new WeeklyTable(22 + "nd", true));
			}else{
				lstDataMonth.set(21,new WeeklyTable(22 + "nd", false));
			}
			if (valueDisplay.contains("23rd")) {
				lstDataMonth.set(22,new WeeklyTable(23 + "rd", true));
			}else{
				lstDataMonth.set(22,new WeeklyTable(23 + "rd", false));
			}
			if (valueDisplay.contains("24th")) {
				lstDataMonth.set(23,new WeeklyTable(24 + "th", true));
			}else{
				lstDataMonth.set(23,new WeeklyTable(24 + "th", false));
			}
			if (valueDisplay.contains("25th")) {
				lstDataMonth.set(24,new WeeklyTable(25 + "th", true));
			}else{
				lstDataMonth.set(24,new WeeklyTable(25 + "th", false));
			}
			if (valueDisplay.contains("26th")) {
				lstDataMonth.set(25,new WeeklyTable(26 + "th", true));
			}else{
				lstDataMonth.set(25,new WeeklyTable(26 + "th", false));
			}
			if (valueDisplay.contains("27th")) {
				lstDataMonth.set(26,new WeeklyTable(27 + "th", true));
			}else{
				lstDataMonth.set(26,new WeeklyTable(27 + "th", false));
			}
			if (valueDisplay.contains("28th")) {
				lstDataMonth.set(27,new WeeklyTable(28 + "th", true));
			}else{
				lstDataMonth.set(27,new WeeklyTable(28 + "th", false));
			}
			if (valueDisplay.contains("29th")) {
				lstDataMonth.set(28,new WeeklyTable(29 + "th", true));
			}else{
				lstDataMonth.set(28,new WeeklyTable(29 + "th", false));
			}
			if (valueDisplay.contains("30th")) {
				lstDataMonth.set(29,new WeeklyTable(30 + "th", true));
			}else{
				lstDataMonth.set(29,new WeeklyTable(30 + "th", false));
			}
			if (valueDisplay.contains("31th")) {
				lstDataMonth.set(30,new WeeklyTable(31 + "st", true));
			}else{
				lstDataMonth.set(30,new WeeklyTable(31 + "st", false));
			}
		}
		
		if (type.equalsIgnoreCase("2")) {
			conWeekly.removeAllItems();
			conWeekly.addAll(lstDataWeekly);
		} else if (type.equalsIgnoreCase("3")) {
			conWeekly.removeAllItems();
			conWeekly.addAll(lstDataMonth);
		} else {
			conWeekly.removeAllItems();
		}
	}

	private void init() {

		mainDisplay = new VerticalLayout();
		mainDisplay.setSizeFull();
		mainDisplay.setSpacing(true);

		tblWeekly = new CustomTable("", conWeekly){

			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property property) {
				String pid = (String) colId;
				Object val = property.getValue();
				WeeklyTable content = (WeeklyTable) rowId;
				
				if ("name".equals(pid)) {
					if (property.getValue().equals("1st.")) {
						return "1st";
					}
					if (property.getValue().equals("2nd.")) {
						return "2nd";
					}
					if (property.getValue().equals("3rd.")) {
						return "3rd";
					}
					if (property.getValue().equals("4th.")) {
						return "4th";
					}
					if (property.getValue().equals("5th.")) {
						return "5th";
					}
					if (property.getValue().equals("6th.")) {
						return "6th";
					}
					if (property.getValue().equals("7th.")) {
						return "7th";
					}
					if (property.getValue().equals("8th.")) {
						return "8th";
					}
					if (property.getValue().equals("9th.")) {
						return "9th";
					}
				}

				return super.formatPropertyValue(rowId, colId, property);
			}
			
		};
		tblWeekly.setColumnAlignment("name", Table.ALIGN_LEFT);
		tblWeekly.setColumnAlignment("select", Table.ALIGN_LEFT);
		tblWeekly.setSizeFull();
		tblWeekly.setSelectable(true);
		tblWeekly.setNullSelectionAllowed(false);
		tblWeekly.setImmediate(true);
		tblWeekly.setVisibleColumns(new String[] { "name", "select" });
		tblWeekly.setColumnHeaders(new String[] { "Day", "+" });
		String[] columnWidth = new String[] { "select" };
		String[] columnWidthValue = new String[] { "40" };
		for (int i = 0; i < columnWidth.length; i++) {
			tblWeekly.setColumnWidth(columnWidth[i],
					Integer.parseInt(columnWidthValue[i]));
		}
		tblWeekly.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object val = event.getProperty().getValue();
				WeeklyTable p = (WeeklyTable) val;
				if (p != null) {

				}
			}

		});
		tblWeekly.addListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {

				}
			}
		});

		tblWeekly.addGeneratedColumn("select", new Table.ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				final WeeklyTable bean = (WeeklyTable) itemId;

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

		tblWeekly.addListener(new Table.HeaderClickListener() {
			public void headerClick(HeaderClickEvent event) {
				String property = event.getPropertyId().toString();
				if (property.equals("select")) {
					tblWeekly.setSelectAll(!tblWeekly.isSelectAll());
					for (int i = 0; i < conWeekly.size(); i++) {
						WeeklyTable bean = conWeekly.getIdByIndex(i);
						bean.setSelect(tblWeekly.isSelectAll());
						tblWeekly.setColumnHeader("select",
								(tblWeekly.isSelectAll() == true) ? "-" : "+");
						tblWeekly.refreshRowCache();
					}
				}
			}
		});
		// tblWeekly.setSortAscending(false);
		tblWeekly.setSortDisabled(true);
		tblWeekly.setColumnCollapsingAllowed(false);
		tblWeekly.setColumnReorderingAllowed(false);

		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(btnOk);
		buttonLayout.addComponent(btnCancel);
		buttonLayout.setComponentAlignment(btnOk, Alignment.MIDDLE_CENTER);
		buttonLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_CENTER);

		mainDisplay.addComponent(tblWeekly);
		mainDisplay.addComponent(buttonLayout);
		mainDisplay.setExpandRatio(tblWeekly, 1.0f);
		mainDisplay
				.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		this.setContent(mainDisplay);
	}

	private void tableDisChecked() {

		for (int i = 0; i < conWeekly.size(); i++) {
			WeeklyTable bean = conWeekly.getIdByIndex(i);
			bean.setSelect(false);
			tblWeekly.refreshRowCache();
		}
	}

	public List<WeeklyTable> getAllItemCheckedOnTable() {
		List<WeeklyTable> list = new ArrayList<WeeklyTable>();
		Collection<WeeklyTable> collection = (Collection<WeeklyTable>) tblWeekly
				.getItemIds();
		for (WeeklyTable obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	private void tableChecked() {

		try {

			dataSelect.removeAllItems();
			dataSelect.addAll(getAllItemCheckedOnTable());
			for (int i = 0; i < conWeekly.size(); i++) {

				WeeklyTable bean = conWeekly.getIdByIndex(i);
				for (int j = 0; j < dataSelect.size(); j++) {

					WeeklyTable bean1 = dataSelect.getIdByIndex(j);
					if (bean.equals(bean1)) {
						bean.setSelect(true);
					}

					tblWeekly.refreshRowCache();
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void show(Application app) {

		super.show(app);
	}

	@Override
	protected void enterKeyPressed() {

	}

	@Override
	protected void escapeKeyPressed() {
		close();
	}

	@Override
	protected void close() {
		// getAllItemCheckedOnTable();
		// String charter = "";
		// for (WeeklyTable value : getAllItemCheckedOnTable()) {
		// if (charter == null || charter.equalsIgnoreCase("")) {
		// charter += value.getName();
		// } else {
		// charter += ";" + value.getName();
		// }
		// }
		// setValue(charter);
		// parent.setValueDate(charter);
		// System.out.println("hhhhhhhhh???" + getValue());
		super.close();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
