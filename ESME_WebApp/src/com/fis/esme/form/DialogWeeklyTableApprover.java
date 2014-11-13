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
	private BeanItemContainer<WeeklyTable> dataSelect = new BeanItemContainer<WeeklyTable>(WeeklyTable.class);
	private FormMessageSchedulerApprover parent;
	public String value;
	private Button btnOk, btnCancel;
	private HorizontalLayout buttonLayout = new HorizontalLayout();

	private final String strMonday = TM.get("messagescheduler.dialog.weekly.monday.caption");
	private final String strTuesday = TM.get("messagescheduler.dialog.weekly.tuesday.caption");
	private final String strWednesday = TM.get("messagescheduler.dialog.weekly.wednesday.caption");
	private final String strThursday = TM.get("messagescheduler.dialog.weekly.thursday.caption");
	private final String strFriday = TM.get("messagescheduler.dialog.weekly.friday.caption");
	private final String strSaturday = TM.get("messagescheduler.dialog.weekly.saturday.caption");
	private final String strSunday = TM.get("messagescheduler.dialog.weekly.sunday.caption");

	private final String str1st = TM.get("messagescheduler.dialog.monthly.1st.caption");
	private final String str2nd = TM.get("messagescheduler.dialog.monthly.2nd.caption");
	private final String str3rd = TM.get("messagescheduler.dialog.monthly.3rd.caption");
	private final String str4th = TM.get("messagescheduler.dialog.monthly.4th.caption");
	private final String str5th = TM.get("messagescheduler.dialog.monthly.5th.caption");
	private final String str6th = TM.get("messagescheduler.dialog.monthly.6th.caption");
	private final String str7th = TM.get("messagescheduler.dialog.monthly.7th.caption");
	private final String str8th = TM.get("messagescheduler.dialog.monthly.8th.caption");
	private final String str9th = TM.get("messagescheduler.dialog.monthly.9th.caption");
	private final String str10th = TM.get("messagescheduler.dialog.monthly.10th.caption");
	private final String str11th = TM.get("messagescheduler.dialog.monthly.11th.caption");
	private final String str12th = TM.get("messagescheduler.dialog.monthly.12th.caption");
	private final String str13th = TM.get("messagescheduler.dialog.monthly.13th.caption");
	private final String str14th = TM.get("messagescheduler.dialog.monthly.14th.caption");
	private final String str15th = TM.get("messagescheduler.dialog.monthly.15th.caption");
	private final String str16th = TM.get("messagescheduler.dialog.monthly.16th.caption");
	private final String str17th = TM.get("messagescheduler.dialog.monthly.17th.caption");
	private final String str18th = TM.get("messagescheduler.dialog.monthly.18th.caption");
	private final String str19th = TM.get("messagescheduler.dialog.monthly.19th.caption");
	private final String str20th = TM.get("messagescheduler.dialog.monthly.20th.caption");
	private final String str21st = TM.get("messagescheduler.dialog.monthly.21st.caption");
	private final String str22nd = TM.get("messagescheduler.dialog.monthly.22nd.caption");
	private final String str23rd = TM.get("messagescheduler.dialog.monthly.23rd.caption");
	private final String str24th = TM.get("messagescheduler.dialog.monthly.24th.caption");
	private final String str25th = TM.get("messagescheduler.dialog.monthly.25th.caption");
	private final String str26th = TM.get("messagescheduler.dialog.monthly.26th.caption");
	private final String str27th = TM.get("messagescheduler.dialog.monthly.27th.caption");
	private final String str28th = TM.get("messagescheduler.dialog.monthly.28th.caption");
	private final String str29th = TM.get("messagescheduler.dialog.monthly.29th.caption");
	private final String str30th = TM.get("messagescheduler.dialog.monthly.30th.caption");
	private final String str31st = TM.get("messagescheduler.dialog.monthly.31st.caption");

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

		btnOk = new Button(TM.get("messagescheduler.dialog.button.ok.caption"));
		btnCancel = new Button(TM.get("messagescheduler.dialog.button.cancel.caption"));
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
			lstDataWeekly.add(0, new WeeklyTable(strMonday, false));
			lstDataWeekly.add(1, new WeeklyTable(strTuesday, false));
			lstDataWeekly.add(2, new WeeklyTable(strWednesday, false));
			lstDataWeekly.add(3, new WeeklyTable(strThursday, false));
			lstDataWeekly.add(4, new WeeklyTable(strFriday, false));
			lstDataWeekly.add(5, new WeeklyTable(strSaturday, false));
			lstDataWeekly.add(6, new WeeklyTable(strSunday, false));
		}

		if (lstDataMonth.size() <= 0) {
			// for (int i = 1; i < 31; i++) {
			// lstDataMonth.add(new WeeklyTable(i + "th", false));
			// }
			lstDataMonth.add(0, new WeeklyTable(str1st, false));
			lstDataMonth.add(1, new WeeklyTable(str2nd, false));
			lstDataMonth.add(2, new WeeklyTable(str3rd, false));
			lstDataMonth.add(3, new WeeklyTable(str4th, false));
			lstDataMonth.add(4, new WeeklyTable(str5th, false));
			lstDataMonth.add(5, new WeeklyTable(str6th, false));
			lstDataMonth.add(6, new WeeklyTable(str7th, false));
			lstDataMonth.add(7, new WeeklyTable(str8th, false));
			lstDataMonth.add(8, new WeeklyTable(str9th, false));
			lstDataMonth.add(9, new WeeklyTable(str10th, false));
			lstDataMonth.add(10, new WeeklyTable(str11th, false));
			lstDataMonth.add(11, new WeeklyTable(str12th, false));
			lstDataMonth.add(12, new WeeklyTable(str13th, false));
			lstDataMonth.add(13, new WeeklyTable(str14th, false));
			lstDataMonth.add(14, new WeeklyTable(str15th, false));
			lstDataMonth.add(15, new WeeklyTable(str16th, false));
			lstDataMonth.add(16, new WeeklyTable(str17th, false));
			lstDataMonth.add(17, new WeeklyTable(str18th, false));
			lstDataMonth.add(18, new WeeklyTable(str19th, false));
			lstDataMonth.add(19, new WeeklyTable(str20th, false));
			lstDataMonth.add(20, new WeeklyTable(str21st, false));
			lstDataMonth.add(21, new WeeklyTable(str22nd, false));
			lstDataMonth.add(22, new WeeklyTable(str23rd, false));
			lstDataMonth.add(23, new WeeklyTable(str24th, false));
			lstDataMonth.add(24, new WeeklyTable(str25th, false));
			lstDataMonth.add(25, new WeeklyTable(str26th, false));
			lstDataMonth.add(26, new WeeklyTable(str27th, false));
			lstDataMonth.add(27, new WeeklyTable(str28th, false));
			lstDataMonth.add(28, new WeeklyTable(str29th, false));
			lstDataMonth.add(29, new WeeklyTable(str30th, false));
			lstDataMonth.add(30, new WeeklyTable(str31st, false));

		}

		String valueDisplay = null;

		if (parent.getValueDate() != null && type.equalsIgnoreCase("2")) {

			valueDisplay = parent.getValueDate();
			if (valueDisplay.contains(strMonday)) {
				lstDataWeekly.set(0, new WeeklyTable(strMonday, true));
			} else {
				lstDataWeekly.set(0, new WeeklyTable(strMonday, false));
			}
			if (valueDisplay.contains(strTuesday)) {
				lstDataWeekly.set(1, new WeeklyTable(strTuesday, true));
			} else {
				lstDataWeekly.set(1, new WeeklyTable(strTuesday, false));
			}
			if (valueDisplay.contains(strWednesday)) {
				lstDataWeekly.set(2, new WeeklyTable(strWednesday, true));
			} else {
				lstDataWeekly.set(2, new WeeklyTable(strWednesday, false));
			}
			if (valueDisplay.contains(strThursday)) {
				lstDataWeekly.set(3, new WeeklyTable(strThursday, true));
			} else {
				lstDataWeekly.set(3, new WeeklyTable(strThursday, false));
			}
			if (valueDisplay.contains(strFriday)) {
				lstDataWeekly.set(4, new WeeklyTable(strFriday, true));
			} else {
				lstDataWeekly.set(4, new WeeklyTable(strFriday, false));
			}
			if (valueDisplay.contains(strSaturday)) {
				lstDataWeekly.set(5, new WeeklyTable(strSaturday, true));
			} else {
				lstDataWeekly.set(5, new WeeklyTable(strSaturday, false));
			}
			if (valueDisplay.contains(strSunday)) {
				lstDataWeekly.set(6, new WeeklyTable(strSunday, true));
			} else {
				lstDataWeekly.set(6, new WeeklyTable(strSunday, false));
			}
		} else if (parent.getValueDate() != null && type.equalsIgnoreCase("3")) {

			valueDisplay = parent.getValueDate();

			if (valueDisplay.contains(str1st)) {
				lstDataMonth.set(0, new WeeklyTable(str1st, true));
			} else {
				lstDataMonth.set(0, new WeeklyTable(str1st, false));
			}
			if (valueDisplay.contains(str2nd)) {
				lstDataMonth.set(1, new WeeklyTable(str2nd, true));
			} else {
				lstDataMonth.set(1, new WeeklyTable(str2nd, false));
			}
			if (valueDisplay.contains(str3rd)) {
				lstDataMonth.set(2, new WeeklyTable(str3rd, true));
			} else {
				lstDataMonth.set(2, new WeeklyTable(str3rd, false));
			}
			if (valueDisplay.contains(str4th)) {
				lstDataMonth.set(3, new WeeklyTable(str4th, true));
			} else {
				lstDataMonth.set(3, new WeeklyTable(str4th, false));
			}
			if (valueDisplay.contains(str5th)) {
				lstDataMonth.set(4, new WeeklyTable(str5th, true));
			} else {
				lstDataMonth.set(4, new WeeklyTable(str5th, false));
			}
			if (valueDisplay.contains(str6th)) {
				lstDataMonth.set(5, new WeeklyTable(str6th, true));
			} else {
				lstDataMonth.set(5, new WeeklyTable(str6th, false));
			}
			if (valueDisplay.contains(str7th)) {
				lstDataMonth.set(6, new WeeklyTable(str7th, true));
			} else {
				lstDataMonth.set(6, new WeeklyTable(str7th, false));
			}
			if (valueDisplay.contains(str8th)) {
				lstDataMonth.set(7, new WeeklyTable(str8th, true));
			} else {
				lstDataMonth.set(7, new WeeklyTable(str8th, false));
			}
			if (valueDisplay.contains(str9th)) {
				lstDataMonth.set(8, new WeeklyTable(str9th, true));
			} else {
				lstDataMonth.set(8, new WeeklyTable(str9th, false));
			}
			if (valueDisplay.contains(str10th)) {
				lstDataMonth.set(9, new WeeklyTable(str10th, true));
			} else {
				lstDataMonth.set(9, new WeeklyTable(str10th, false));
			}
			if (valueDisplay.contains(str11th)) {
				lstDataMonth.set(10, new WeeklyTable(str11th, true));
			} else {
				lstDataMonth.set(10, new WeeklyTable(str11th, false));
			}
			if (valueDisplay.contains(str12th)) {
				lstDataMonth.set(11, new WeeklyTable(str12th, true));
			} else {
				lstDataMonth.set(11, new WeeklyTable(str12th, false));
			}
			if (valueDisplay.contains(str13th)) {
				lstDataMonth.set(12, new WeeklyTable(str13th, true));
			} else {
				lstDataMonth.set(12, new WeeklyTable(str13th, false));
			}
			if (valueDisplay.contains(str14th)) {
				lstDataMonth.set(13, new WeeklyTable(str14th, true));
			} else {
				lstDataMonth.set(13, new WeeklyTable(str14th, false));
			}
			if (valueDisplay.contains(str15th)) {
				lstDataMonth.set(14, new WeeklyTable(str15th, true));
			} else {
				lstDataMonth.set(14, new WeeklyTable(str15th, false));
			}
			if (valueDisplay.contains(str16th)) {
				lstDataMonth.set(15, new WeeklyTable(str16th, true));
			} else {
				lstDataMonth.set(15, new WeeklyTable(str16th, false));
			}
			if (valueDisplay.contains(str17th)) {
				lstDataMonth.set(16, new WeeklyTable(str17th, true));
			} else {
				lstDataMonth.set(16, new WeeklyTable(str17th, false));
			}
			if (valueDisplay.contains(str18th)) {
				lstDataMonth.set(17, new WeeklyTable(str18th, true));
			} else {
				lstDataMonth.set(17, new WeeklyTable(str18th, false));
			}
			if (valueDisplay.contains(str19th)) {
				lstDataMonth.set(18, new WeeklyTable(str19th, true));
			} else {
				lstDataMonth.set(18, new WeeklyTable(str19th, false));
			}
			if (valueDisplay.contains(str20th)) {
				lstDataMonth.set(19, new WeeklyTable(str20th, true));
			} else {
				lstDataMonth.set(19, new WeeklyTable(str20th, false));
			}
			if (valueDisplay.contains(str21st)) {
				lstDataMonth.set(20, new WeeklyTable(str21st, true));
			} else {
				lstDataMonth.set(20, new WeeklyTable(str21st, false));
			}
			if (valueDisplay.contains(str22nd)) {
				lstDataMonth.set(21, new WeeklyTable(str22nd, true));
			} else {
				lstDataMonth.set(21, new WeeklyTable(str22nd, false));
			}
			if (valueDisplay.contains(str23rd)) {
				lstDataMonth.set(22, new WeeklyTable(str23rd, true));
			} else {
				lstDataMonth.set(22, new WeeklyTable(str23rd, false));
			}
			if (valueDisplay.contains(str24th)) {
				lstDataMonth.set(23, new WeeklyTable(str24th, true));
			} else {
				lstDataMonth.set(23, new WeeklyTable(str24th, false));
			}
			if (valueDisplay.contains(str25th)) {
				lstDataMonth.set(24, new WeeklyTable(str25th, true));
			} else {
				lstDataMonth.set(24, new WeeklyTable(str25th, false));
			}
			if (valueDisplay.contains(str26th)) {
				lstDataMonth.set(25, new WeeklyTable(str26th, true));
			} else {
				lstDataMonth.set(25, new WeeklyTable(str26th, false));
			}
			if (valueDisplay.contains(str27th)) {
				lstDataMonth.set(26, new WeeklyTable(str27th, true));
			} else {
				lstDataMonth.set(26, new WeeklyTable(str27th, false));
			}
			if (valueDisplay.contains(str28th)) {
				lstDataMonth.set(27, new WeeklyTable(str28th, true));
			} else {
				lstDataMonth.set(27, new WeeklyTable(str28th, false));
			}
			if (valueDisplay.contains(str29th)) {
				lstDataMonth.set(28, new WeeklyTable(str29th, true));
			} else {
				lstDataMonth.set(28, new WeeklyTable(str29th, false));
			}
			if (valueDisplay.contains(str30th)) {
				lstDataMonth.set(29, new WeeklyTable(str30th, true));
			} else {
				lstDataMonth.set(29, new WeeklyTable(str30th, false));
			}
			if (valueDisplay.contains(str31st)) {
				lstDataMonth.set(30, new WeeklyTable(str31st, true));
			} else {
				lstDataMonth.set(30, new WeeklyTable(str31st, false));
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

		tblWeekly = new CustomTable("", conWeekly) {

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				// String pid = (String) colId;
				// Object val = property.getValue();
				// WeeklyTable content = (WeeklyTable) rowId;

				// if ("name".equals(pid)) {
				// if (property.getValue().equals("1st.")) {
				// return "1st";
				// }
				// if (property.getValue().equals("2nd.")) {
				// return "2nd";
				// }
				// if (property.getValue().equals("3rd.")) {
				// return "3rd";
				// }
				// if (property.getValue().equals("4th.")) {
				// return "4th";
				// }
				// if (property.getValue().equals("5th.")) {
				// return "5th";
				// }
				// if (property.getValue().equals("6th.")) {
				// return "6th";
				// }
				// if (property.getValue().equals("7th.")) {
				// return "7th";
				// }
				// if (property.getValue().equals("8th.")) {
				// return "8th";
				// }
				// if (property.getValue().equals("9th.")) {
				// return "9th";
				// }
				// }

				return super.formatPropertyValue(rowId, colId, property);
			}

		};
		tblWeekly.setColumnAlignment("name", Table.ALIGN_LEFT);
		tblWeekly.setColumnAlignment("select", Table.ALIGN_LEFT);
		tblWeekly.setSizeFull();
		tblWeekly.setSelectable(true);
		tblWeekly.setNullSelectionAllowed(false);
		tblWeekly.setImmediate(true);
		tblWeekly.setVisibleColumns(TM.get("messagescheduler.dialog.column.visible").split(","));
		tblWeekly.setColumnHeaders(TM.get("messagescheduler.dialog.column.header.caption").split(","));
		String[] columnWidth = new String[] { "select" };
		String[] columnWidthValue = new String[] { "40" };
		for (int i = 0; i < columnWidth.length; i++) {
			tblWeekly.setColumnWidth(columnWidth[i], Integer.parseInt(columnWidthValue[i]));
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
			public Object generateCell(Table source, Object itemId, Object columnId) {

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
						tblWeekly.setColumnHeader("select", (tblWeekly.isSelectAll() == true) ? "-" : "+");
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
		mainDisplay.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
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
		Collection<WeeklyTable> collection = (Collection<WeeklyTable>) tblWeekly.getItemIds();
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
