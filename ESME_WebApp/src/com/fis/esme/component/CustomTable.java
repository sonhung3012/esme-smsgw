package com.fis.esme.component;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.i18n.TM;

public class CustomTable extends Table {

	public static final String INDEXED_COLUMN_CAPTION = "STT";
	public static final String DELETE_EDIT_COLUMN_CAPTION = "DELETE_EDIT";
	public static final String SELECT_COLUMN_CAPTION = "SELECT";

	public static final int ACTION_MODE_DELETE = 0;
	public static final int ACTION_MODE_EDIT = 1;
	// private int maxCellLength = 20;

	private boolean selectAll = false;

	private CommonButtonPanel pnlAction;

	public CustomTable(String caption, Container data) {

		this(caption, data, null);
	}

	public CustomTable(String caption, Container data, CommonButtonPanel pnlAction) {

		super(caption, data);
		this.setColumnCollapsingAllowed(true);
		this.setStyleName("table-header-center");
		this.setSelectable(false);
		setIndexedColumnVisible(true);
		setDeleteEditColumnVisible(true);
		// setSelectColumnVisible(true);
		addRowTooltip();
		this.pnlAction = pnlAction;
		// Collection<?> cols = data.getContainerPropertyIds();
		// for (Object col : cols)
		// {
		// this.setColumnExpandRatio(col, 1);
		// }
	}

	// this.addGeneratedColumn("select", new Table.ColumnGenerator() {
	// @Override
	// public Object generateCell(Table source, Object itemId,
	// Object columnId) {
	// final CallHistory bean = (CallHistory) itemId;
	//
	// CheckBox checkBox = new CheckBox();
	// checkBox.setImmediate(true);
	// checkBox.addListener(new Property.ValueChangeListener() {
	// private static final long serialVersionUID = 1L;
	//
	// @Override
	// public void valueChange(Property.ValueChangeEvent event) {
	// bean.setSelect((Boolean) event.getProperty().getValue());
	// }
	// });
	// if (bean.isSelect()) {
	// checkBox.setValue(true);
	// } else {
	// checkBox.setValue(false);
	// }
	// if (!bean.isFileExisted())
	// checkBox.setEnabled(false);
	// return checkBox;
	// }
	// });

	private void addRowTooltip() {

		this.setItemDescriptionGenerator(new ItemDescriptionGenerator() {

			@Override
			public String generateDescription(Component source, Object itemId, Object propertyId) {

				Property pro = getContainerProperty(itemId, propertyId);

				if (pro != null) {
					String str = formatPropertyValue(itemId, propertyId, pro);
					if (str != null) {
						str = str.replace("\\n", "</br>");
					}
					return str;
				}
				return null;
			}
		});
	}

	public void setIndexedColumnVisible(boolean visible) {

		if (visible) {
			this.addGeneratedColumn(INDEXED_COLUMN_CAPTION, new IndexedColumnGenerator());
			this.setColumnWidth(INDEXED_COLUMN_CAPTION, 30);
			this.setColumnAlignment(INDEXED_COLUMN_CAPTION, Table.ALIGN_RIGHT);
			// this.setVisibleColumns(getRealVisibleColumns(getVisibleColumns()));
		} else {
			this.removeGeneratedColumn(INDEXED_COLUMN_CAPTION);
		}
	}

	public void setDeleteEditColumnVisible(boolean visible) {

		if (visible) {
			this.addGeneratedColumn(DELETE_EDIT_COLUMN_CAPTION, new DeleteEditColumnGenerator());
			// this.setColumnWidth(DELETE_COLUMN_CAPTION, 30);
			this.setColumnAlignment(DELETE_EDIT_COLUMN_CAPTION, Table.ALIGN_CENTER);
			// this.setVisibleColumns(getRealVisibleColumns(getVisibleColumns()));
		} else {
			this.removeGeneratedColumn(DELETE_EDIT_COLUMN_CAPTION);
		}
	}

	public void setSelectColumnVisible(boolean visible) {

		if (visible) {
			this.addGeneratedColumn(SELECT_COLUMN_CAPTION, new SelectColumnGenerator());
			// this.setColumnWidth(DELETE_COLUMN_CAPTION, 30);
			this.setColumnAlignment(SELECT_COLUMN_CAPTION, Table.ALIGN_CENTER);
			// this.setVisibleColumns(getRealVisibleColumns(getVisibleColumns()));
		} else {
			this.removeGeneratedColumn(SELECT_COLUMN_CAPTION);
		}
	}

	private Object[] getRealVisibleColumns(Object[] visibleCols) {

		ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(visibleCols));
		int index = list.indexOf(INDEXED_COLUMN_CAPTION);
		if (index > 0) {
			Object obj = list.set(0, INDEXED_COLUMN_CAPTION);
			list.set(index, obj);
		} else if (index < 0) {
			list.add(0, INDEXED_COLUMN_CAPTION);
		}

		index = list.indexOf(DELETE_EDIT_COLUMN_CAPTION);
		if (index > 0) {
			Object obj = list.set(0, DELETE_EDIT_COLUMN_CAPTION);
			list.set(index, obj);
		} else if (index < 0) {
			list.add(0, DELETE_EDIT_COLUMN_CAPTION);
		}

		index = list.indexOf(SELECT_COLUMN_CAPTION);
		if (index > 0) {
			Object obj = list.set(0, SELECT_COLUMN_CAPTION);
			list.set(index, obj);
		} else if (index < 0) {
			list.add(0, SELECT_COLUMN_CAPTION);
		}

		return list.toArray();
	}

	protected String formatPropertyValue(Object rowId, Object colId, Property property) {

		String pid = (String) colId;
		Object val = property.getValue();

		if (val == null) {
			return super.formatPropertyValue(rowId, colId, property);
		}

		if (val instanceof Number) {
			return NumberFormat.getInstance().format(val);
		} else if ("status".equals(pid)) {
			if ((property.getValue().equals("1"))) {
				return TM.get("main.common.table.status.active");
			} else {
				return TM.get("main.common.table.status.inactive");
			}
		} else if ("sex".equals(pid)) {
			if ((property.getValue().equals("1"))) {
				return TM.get("main.common.table.sex.male");
			} else {
				return TM.get("main.common.table.sex.female");
			}
		}

		return super.formatPropertyValue(rowId, colId, property);
	}

	public String getColumnAlignment(Object propertyId) {

		try {
			Class<?> t = getContainerDataSource().getType(propertyId);
			if (t == int.class || t == double.class || t == short.class || t == float.class || t == byte.class || t == long.class || t.getSuperclass() == Number.class) {
				return Table.ALIGN_RIGHT;
			} else if (propertyId.equals("select") || propertyId.equals("ACTION")) {
				return Table.ALIGN_CENTER;
			}
			return super.getColumnAlignment(propertyId);
		} catch (Exception e) {
			return super.getColumnAlignment(propertyId);
		}
	}

	class IndexedColumnGenerator implements ColumnGenerator {

		private int number = 1;

		public IndexedColumnGenerator() {

		}

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {

			Container container = source.getContainerDataSource();

			if (container instanceof BeanItemContainer<?>) {
				BeanItemContainer<?> con = (BeanItemContainer<?>) source.getContainerDataSource();
				int id = con.indexOfId(itemId);
				return new Label("" + (id + 1));
			} else {
				return new Label("");
			}
		}
	}

	class DeleteEditColumnGenerator implements ColumnGenerator {

		public DeleteEditColumnGenerator() {

		}

		@Override
		public Component generateCell(Table source, final Object itemId, Object columnId) {

			Container container = source.getContainerDataSource();

			if (container instanceof BeanItemContainer<?>) {
				// int id = con.indexOfId(itemId);
				HorizontalLayout buttonLayout = new HorizontalLayout();
				buttonLayout.setSpacing(true);
				// buttonLayout.setSizeFull();
				Button btn = new Button(TM.get("table.common.btn.delete.caption"));
				btn.setDescription(TM.get("table.common.btn.delete.description"));
				btn.setStyleName(BaseTheme.BUTTON_LINK);
				btn.setIcon(new ThemeResource("icons/16/delete.png"));
				btn.setCaption(null);
				btn.addListener(new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {

						btnActionClick(itemId, ACTION_MODE_DELETE);
					}
				});

				if (pnlAction != null)
					btn.setEnabled(pnlAction.getPermision().contains("D"));

				buttonLayout.addComponent(btn);
				buttonLayout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);

				btn = new Button(TM.get("table.common.btn.edit.caption"));
				btn.setDescription(TM.get("table.common.btn.edit.description"));
				btn.setStyleName(BaseTheme.BUTTON_LINK);
				btn.setIcon(new ThemeResource("icons/16/edit.png"));
				btn.setCaption(null);
				btn.addListener(new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {

						btnActionClick(itemId, ACTION_MODE_EDIT);
					}
				});

				if (pnlAction != null)
					btn.setEnabled(pnlAction.getPermision().contains("U"));

				buttonLayout.addComponent(btn);
				buttonLayout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
				return buttonLayout;
			} else {
				return new Label("");
			}
		}
	}

	class SelectColumnGenerator implements ColumnGenerator {

		public SelectColumnGenerator() {

		}

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {

			CheckBox checkBox = new CheckBox();
			checkBox.setImmediate(true);
			checkBox.setValue(false);
			return checkBox;
		}
	}

	public void btnActionClick(Object object, int actionMode) {

		// System.out.println("---->>>>"+pnlAction);
		if (actionMode == CustomTable.ACTION_MODE_EDIT) {
			pnlAction.edit(object);

		} else if (actionMode == CustomTable.ACTION_MODE_DELETE) {
			pnlAction.delete(object);
		}
	}

	public boolean isSelectAll() {

		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {

		this.selectAll = selectAll;
	}

	public CommonButtonPanel getPnlAction() {

		return pnlAction;
	}

	public void setPnlAction(CommonButtonPanel pnlAction) {

		this.pnlAction = pnlAction;
	}

	// public int getMaxCellLength()
	// {
	// return maxCellLength;
	// }
	//
	// public void setMaxCellLength(int maxCellLength)
	// {
	// this.maxCellLength = maxCellLength;
	// }

}
