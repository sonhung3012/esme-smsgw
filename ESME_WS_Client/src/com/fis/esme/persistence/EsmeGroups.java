package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for esmeGroups complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeGroups">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="rootId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeGroups", propOrder = { "createDate", "description", "groupId", "name", "parentId", "rootId", "status" })
public class EsmeGroups {

	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar createDate;
	protected String description;
	protected long groupId;
	protected String name;
	protected long parentId;
	protected long rootId;
	protected String status;
	transient boolean select;

	public boolean isSelect() {

		return select;
	}

	public void setSelect(boolean select) {

		this.select = select;
	}

	/**
	 * Gets the value of the createDate property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getCreateDate() {

		return createDate;
	}

	/**
	 * Sets the value of the createDate property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setCreateDate(XMLGregorianCalendar value) {

		this.createDate = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {

		this.description = value;
	}

	/**
	 * Gets the value of the groupId property.
	 * 
	 */
	public long getGroupId() {

		return groupId;
	}

	/**
	 * Sets the value of the groupId property.
	 * 
	 */
	public void setGroupId(long value) {

		this.groupId = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {

		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {

		this.name = value;
	}

	/**
	 * Gets the value of the parentId property.
	 * 
	 */
	public long getParentId() {

		return parentId;
	}

	/**
	 * Sets the value of the parentId property.
	 * 
	 */
	public void setParentId(long value) {

		this.parentId = value;
	}

	/**
	 * Gets the value of the rootId property.
	 * 
	 */
	public long getRootId() {

		return rootId;
	}

	/**
	 * Sets the value of the rootId property.
	 * 
	 */
	public void setRootId(long value) {

		this.rootId = value;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStatus() {

		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStatus(String value) {

		this.status = value;
	}

	@Override
	public String toString() {

		return this.description;
	}

	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		// object must be Test at this point
		EsmeGroups groups = (EsmeGroups) obj;
		return groupId == groups.getGroupId();
	}

}
