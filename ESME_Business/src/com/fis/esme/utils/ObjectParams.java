package com.fis.esme.utils;


public class ObjectParams {

	private String switchCase;
	private String multiValues;

	private Integer firstItemIndex;
	private Integer maxItems;
	private Boolean exactMatch;
	private Boolean ascSorted;
	private String groupColumn;
	private String sortedColumn;

	public ObjectParams() {

	}

	public ObjectParams(String switchCase, String multiValues,
			Integer firstItemIndex, Integer maxItems, Boolean exactMatch,
			Boolean ascSorted, String groupColumn, String sortedColumn) {
		super();
		this.switchCase = switchCase;
		this.multiValues = multiValues;
		this.firstItemIndex = firstItemIndex;
		this.maxItems = maxItems;
		this.exactMatch = exactMatch;
		this.ascSorted = ascSorted;
		this.groupColumn = groupColumn;
		this.sortedColumn = sortedColumn;
	}

	public ObjectParams(String switchCase, String multiValues) {
		this(switchCase, multiValues, null, null, null, null, null, null);
	}

	public ObjectParams(String switchCase) {
		this(switchCase, null, null, null, null, null, null, null);
	}

	public String getSwitchCase() {
		return switchCase;
	}

	public void setSwitchCase(String switchCase) {
		this.switchCase = switchCase;
	}

	public String getMultiValues() {
		return multiValues;
	}

	public void setMultiValues(String multiValues) {
		this.multiValues = multiValues;
	}

	public Integer getFirstItemIndex() {
		return firstItemIndex;
	}

	public void setFirstItemIndex(Integer firstItemIndex) {
		this.firstItemIndex = firstItemIndex;
	}

	public Integer getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(Integer maxItems) {
		this.maxItems = maxItems;
	}

	public Boolean getExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(Boolean exactMatch) {
		this.exactMatch = exactMatch;
	}

	public Boolean getAscSorted() {
		return ascSorted;
	}

	public void setAscSorted(Boolean ascSorted) {
		this.ascSorted = ascSorted;
	}

	public String getGroupColumn() {
		return groupColumn;
	}

	public void setGroupColumn(String groupColumn) {
		this.groupColumn = groupColumn;
	}

	public String getSortedColumn() {
		return sortedColumn;
	}

	public void setSortedColumn(String sortedColumn) {
		this.sortedColumn = sortedColumn;
	}
}
