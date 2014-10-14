package com.fis.esme.util;


public class ComponentEntity {
	private Object value;
	private String description;

	public ComponentEntity() {
	}

	public ComponentEntity(Object value, String description) {
		super();
		this.value = value;
		this.description = description;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		ComponentEntity ce = (ComponentEntity) obj;
		return value.equals(ce.getValue());
	}
}
