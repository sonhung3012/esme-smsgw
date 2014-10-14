package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fis.esme.util.StringUtil;

/**
 * <p>
 * Java class for groups complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="groups">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="desciption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rootId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
@XmlType(name = "groups", propOrder = { "createDatetime", "desciption",
		"groupId", "name", "parentId", "rootId", "status" })
public class Groups {

	@XmlSchemaType(name = "dateTime")
	protected Date createDatetime;
	protected String desciption;
	protected long groupId;
	protected String name;
	protected Long parentId;
	protected Long rootId;
	protected String status;
	transient boolean select;

   	public boolean isSelect() {
   		return select;
   	}

   	public void setSelect(boolean select) {
   		this.select = select;
   	}

	/**
	 * Gets the value of the createDatetime property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public Date getCreateDatetime() {
		return createDatetime;
	}

	/**
	 * Sets the value of the createDatetime property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setCreateDatetime(Date value) {
		this.createDatetime = value;
	}

	/**
	 * Gets the value of the desciption property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDesciption() {
		return desciption;
	}

	/**
	 * Sets the value of the desciption property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDesciption(String value) {
		this.desciption = value;
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
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the value of the parentId property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setParentId(Long value) {
		this.parentId = value;
	}

	/**
	 * Gets the value of the rootId property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getRootId() {
		return rootId;
	}

	/**
	 * Sets the value of the rootId property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setRootId(Long value) {
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
    public String toString()
    {
    	return this.name;
    }
    
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// object must be Test at this point
		Groups service = (Groups) obj;
		return groupId == service.getGroupId();
	}

	
	public int compareTo(Groups service)
	{
		String o1 = this.getName();
		String o2 = service.getName();
		return StringUtil.compareVietnameseString(o1, o2);
	}

}
