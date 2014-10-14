package com.fis.esme.persistence;
// default package
// Generated Dec 4, 2013 2:28:41 PM by Hibernate Tools 3.4.0.CR1

/**
 * EsmeSmscParamId generated by hbm2java
 */
public class EsmeSmscParamId implements java.io.Serializable {

	private long smscId;
	private String name;

	public EsmeSmscParamId() {
	}

	public EsmeSmscParamId(long smscId, String name) {
		this.smscId = smscId;
		this.name = name;
	}

	public long getSmscId() {
		return this.smscId;
	}

	public void setSmscId(long smscId) {
		this.smscId = smscId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EsmeSmscParamId))
			return false;
		EsmeSmscParamId castOther = (EsmeSmscParamId) other;

		return (this.getSmscId() == castOther.getSmscId())
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getSmscId();
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		return result;
	}

}
