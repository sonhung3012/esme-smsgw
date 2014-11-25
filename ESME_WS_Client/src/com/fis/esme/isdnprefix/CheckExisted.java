package com.fis.esme.isdnprefix;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeIsdnPrefix;

/**
 * <p>
 * Java class for checkExisted complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkExisted">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://isdnprefix.esme.fis.com/}esmeIsdnPrefix" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkExisted", propOrder = { "arg0" })
public class CheckExisted {

	protected EsmeIsdnPrefix arg0;

	/**
	 * Gets the value of the arg0 property.
	 * 
	 * @return possible object is {@link EsmeIsdnPrefix }
	 * 
	 */
	public EsmeIsdnPrefix getArg0() {

		return arg0;
	}

	/**
	 * Sets the value of the arg0 property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeIsdnPrefix }
	 * 
	 */
	public void setArg0(EsmeIsdnPrefix value) {

		this.arg0 = value;
	}

}
