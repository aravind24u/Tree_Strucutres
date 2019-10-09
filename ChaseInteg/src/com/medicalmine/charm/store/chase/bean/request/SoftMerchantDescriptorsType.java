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
 * <p>Java class for softMerchantDescriptorsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="softMerchantDescriptorsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SMDDBA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDMerchantID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDContactInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDStreet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDRegion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDPostalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDCountryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDMCC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMDPhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "softMerchantDescriptorsType", propOrder = {
    "smddba",
    "smdMerchantID",
    "smdContactInfo",
    "smdStreet",
    "smdCity",
    "smdRegion",
    "smdPostalCode",
    "smdCountryCode",
    "smdmcc",
    "smdEmail",
    "smdPhoneNumber"
})
public class SoftMerchantDescriptorsType {

    @XmlElement(name = "SMDDBA")
    protected String smddba;
    @XmlElement(name = "SMDMerchantID")
    protected String smdMerchantID;
    @XmlElement(name = "SMDContactInfo")
    protected String smdContactInfo;
    @XmlElement(name = "SMDStreet")
    protected String smdStreet;
    @XmlElement(name = "SMDCity")
    protected String smdCity;
    @XmlElement(name = "SMDRegion")
    protected String smdRegion;
    @XmlElement(name = "SMDPostalCode")
    protected String smdPostalCode;
    @XmlElement(name = "SMDCountryCode")
    protected String smdCountryCode;
    @XmlElement(name = "SMDMCC")
    protected String smdmcc;
    @XmlElement(name = "SMDEmail")
    protected String smdEmail;
    @XmlElement(name = "SMDPhoneNumber")
    protected String smdPhoneNumber;

    /**
     * Gets the value of the smddba property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDDBA() {
        return smddba;
    }

    /**
     * Sets the value of the smddba property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDDBA(String value) {
        this.smddba = value;
    }

    /**
     * Gets the value of the smdMerchantID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDMerchantID() {
        return smdMerchantID;
    }

    /**
     * Sets the value of the smdMerchantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDMerchantID(String value) {
        this.smdMerchantID = value;
    }

    /**
     * Gets the value of the smdContactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDContactInfo() {
        return smdContactInfo;
    }

    /**
     * Sets the value of the smdContactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDContactInfo(String value) {
        this.smdContactInfo = value;
    }

    /**
     * Gets the value of the smdStreet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDStreet() {
        return smdStreet;
    }

    /**
     * Sets the value of the smdStreet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDStreet(String value) {
        this.smdStreet = value;
    }

    /**
     * Gets the value of the smdCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDCity() {
        return smdCity;
    }

    /**
     * Sets the value of the smdCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDCity(String value) {
        this.smdCity = value;
    }

    /**
     * Gets the value of the smdRegion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDRegion() {
        return smdRegion;
    }

    /**
     * Sets the value of the smdRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDRegion(String value) {
        this.smdRegion = value;
    }

    /**
     * Gets the value of the smdPostalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDPostalCode() {
        return smdPostalCode;
    }

    /**
     * Sets the value of the smdPostalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDPostalCode(String value) {
        this.smdPostalCode = value;
    }

    /**
     * Gets the value of the smdCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDCountryCode() {
        return smdCountryCode;
    }

    /**
     * Sets the value of the smdCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDCountryCode(String value) {
        this.smdCountryCode = value;
    }

    /**
     * Gets the value of the smdmcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDMCC() {
        return smdmcc;
    }

    /**
     * Sets the value of the smdmcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDMCC(String value) {
        this.smdmcc = value;
    }

    /**
     * Gets the value of the smdEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDEmail() {
        return smdEmail;
    }

    /**
     * Sets the value of the smdEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDEmail(String value) {
        this.smdEmail = value;
    }

    /**
     * Gets the value of the smdPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMDPhoneNumber() {
        return smdPhoneNumber;
    }

    /**
     * Sets the value of the smdPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMDPhoneNumber(String value) {
        this.smdPhoneNumber = value;
    }

}