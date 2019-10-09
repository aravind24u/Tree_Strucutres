//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.12 at 06:20:05 PM IST 
//


package com.medicalmine.charm.store.chase.bean.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for inquiryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inquiryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrbitalConnectionUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrbitalConnectionPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BIN" type="{}valid-routing-bins"/>
 *         &lt;element name="MerchantID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TerminalID" type="{}terminal-type"/>
 *         &lt;element name="OrderID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InquiryRetryNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inquiryType", propOrder = {
    "orbitalConnectionUsername",
    "orbitalConnectionPassword",
    "bin",
    "merchantID",
    "terminalID",
    "orderID",
    "inquiryRetryNumber"
})
public class InquiryType {

    @XmlElement(name = "OrbitalConnectionUsername")
    protected String orbitalConnectionUsername;
    @XmlElement(name = "OrbitalConnectionPassword")
    protected String orbitalConnectionPassword;
    @XmlElement(name = "BIN", required = true)
    protected String bin;
    @XmlElement(name = "MerchantID", required = true)
    protected String merchantID;
    @XmlElement(name = "TerminalID", required = true)
    protected String terminalID;
    @XmlElement(name = "OrderID")
    protected String orderID;
    @XmlElement(name = "InquiryRetryNumber", required = true)
    protected String inquiryRetryNumber;

    /**
     * Gets the value of the orbitalConnectionUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrbitalConnectionUsername() {
        return orbitalConnectionUsername;
    }

    /**
     * Sets the value of the orbitalConnectionUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrbitalConnectionUsername(String value) {
        this.orbitalConnectionUsername = value;
    }

    /**
     * Gets the value of the orbitalConnectionPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrbitalConnectionPassword() {
        return orbitalConnectionPassword;
    }

    /**
     * Sets the value of the orbitalConnectionPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrbitalConnectionPassword(String value) {
        this.orbitalConnectionPassword = value;
    }

    /**
     * Gets the value of the bin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBIN() {
        return bin;
    }

    /**
     * Sets the value of the bin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBIN(String value) {
        this.bin = value;
    }

    /**
     * Gets the value of the merchantID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantID() {
        return merchantID;
    }

    /**
     * Sets the value of the merchantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantID(String value) {
        this.merchantID = value;
    }

    /**
     * Gets the value of the terminalID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminalID() {
        return terminalID;
    }

    /**
     * Sets the value of the terminalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminalID(String value) {
        this.terminalID = value;
    }

    /**
     * Gets the value of the orderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Sets the value of the orderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderID(String value) {
        this.orderID = value;
    }

    /**
     * Gets the value of the inquiryRetryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInquiryRetryNumber() {
        return inquiryRetryNumber;
    }

    /**
     * Sets the value of the inquiryRetryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInquiryRetryNumber(String value) {
        this.inquiryRetryNumber = value;
    }

}