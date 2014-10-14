package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.util.StringUtil;

/**
 * <p>
 * Java class for esmeSmscParam complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSmscParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smscId" type="{http://smscparam.esme.fis.com/}esmeSmsc" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeSmscParam", propOrder = { "name", "smscId", "value" })
public class EsmeSmscParam {

	protected String name;
	protected EsmeSmsc smscId;
	protected String value;
	transient boolean select;

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
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
	 * Gets the value of the smscId property.
	 * 
	 * @return possible object is {@link EsmeSmsc }
	 * 
	 */
	public EsmeSmsc getSmscId() {
		return smscId;
	}

	/**
	 * Sets the value of the smscId property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeSmsc }
	 * 
	 */
	public void setSmscId(EsmeSmsc value) {
		this.smscId = value;
	}

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the value property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	 @Override
	    public String toString() {
	    	
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
			EsmeSmscParam  service = (EsmeSmscParam) obj;
			return smscId == service.getSmscId();
		}

		
		public int compareTo(EsmeSmsc service)
		{
			String o1 = this.getName();
			String o2 = service.getName();
			return StringUtil.compareVietnameseString(o1, o2);
		}

}
