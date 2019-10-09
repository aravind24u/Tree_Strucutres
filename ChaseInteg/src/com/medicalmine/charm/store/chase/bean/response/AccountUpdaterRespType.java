//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.12 at 06:20:23 PM IST 
//


package com.medicalmine.charm.store.chase.bean.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountUpdaterRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountUpdaterRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerBin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerMerchantID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CustomerRefNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CustomerProfileAction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ScheduledDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProfileProcStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CustomerProfileMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RespTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountUpdaterRespType", propOrder = {
    "customerBin",
    "customerMerchantID",
    "customerRefNum",
    "customerProfileAction",
    "status",
    "scheduledDate",
    "profileProcStatus",
    "customerProfileMessage",
    "respTime"
})
public class AccountUpdaterRespType {

    @XmlElement(required = true)
    protected String customerBin;
    @XmlElement(required = true)
    protected String customerMerchantID;
    @XmlElement(name = "CustomerRefNum", required = true)
    protected String customerRefNum;
    @XmlElement(name = "CustomerProfileAction", required = true)
    protected String customerProfileAction;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "ScheduledDate", required = true)
    protected String scheduledDate;
    @XmlElement(name = "ProfileProcStatus", required = true)
    protected String profileProcStatus;
    @XmlElement(name = "CustomerProfileMessage", required = true)
    protected String customerProfileMessage;
    @XmlElement(name = "RespTime", required = true)
    protected String respTime;

    /**
     * Gets the value of the customerBin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerBin() {
        return customerBin;
    }

    /**
     * Sets the value of the customerBin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerBin(String value) {
        this.customerBin = value;
    }

    /**
     * Gets the value of the customerMerchantID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerMerchantID() {
        return customerMerchantID;
    }

    /**
     * Sets the value of the customerMerchantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerMerchantID(String value) {
        this.customerMerchantID = value;
    }

    /**
     * Gets the value of the customerRefNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerRefNum() {
        return customerRefNum;
    }

    /**
     * Sets the value of the customerRefNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerRefNum(String value) {
        this.customerRefNum = value;
    }

    /**
     * Gets the value of the customerProfileAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerProfileAction() {
        return customerProfileAction;
    }

    /**
     * Sets the value of the customerProfileAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerProfileAction(String value) {
        this.customerProfileAction = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the scheduledDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduledDate() {
        return scheduledDate;
    }

    /**
     * Sets the value of the scheduledDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduledDate(String value) {
        this.scheduledDate = value;
    }

    /**
     * Gets the value of the profileProcStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileProcStatus() {
        return profileProcStatus;
    }

    /**
     * Sets the value of the profileProcStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileProcStatus(String value) {
        this.profileProcStatus = value;
    }

    /**
     * Gets the value of the customerProfileMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerProfileMessage() {
        return customerProfileMessage;
    }

    /**
     * Sets the value of the customerProfileMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerProfileMessage(String value) {
        this.customerProfileMessage = value;
    }

    /**
     * Gets the value of the respTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespTime() {
        return respTime;
    }

    /**
     * Sets the value of the respTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespTime(String value) {
        this.respTime = value;
    }

}