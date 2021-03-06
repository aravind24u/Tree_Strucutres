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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="FlexCacheResp" type="{}flexCacheRespType"/>
 *         &lt;element name="NewOrderResp" type="{}newOrderRespType"/>
 *         &lt;element name="ProfileResp" type="{}profileRespType"/>
 *         &lt;element name="EndOfDayResp" type="{}endOfDayRespType"/>
 *         &lt;element name="ReversalResp" type="{}reversalRespType"/>
 *         &lt;element name="MarkForCaptureResp" type="{}markForCaptureRespType"/>
 *         &lt;element name="QuickResp" type="{}quickRespType"/>
 *         &lt;element name="QuickResponse" type="{}quickRespType_old"/>
 *         &lt;element name="InquiryResp" type="{}inquiryRespType"/>
 *         &lt;element name="AccountUpdaterResp" type="{}accountUpdaterRespType"/>
 *         &lt;element name="SafetechFraudAnalysisResp" type="{}safetechFraudAnalysisRespType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "flexCacheResp",
    "newOrderResp",
    "profileResp",
    "endOfDayResp",
    "reversalResp",
    "markForCaptureResp",
    "quickResp",
    "quickResponse",
    "inquiryResp",
    "accountUpdaterResp",
    "safetechFraudAnalysisResp"
})
@XmlRootElement(name = "Response")
public class Response {

    @XmlElement(name = "FlexCacheResp")
    protected FlexCacheRespType flexCacheResp;
    @XmlElement(name = "NewOrderResp")
    protected NewOrderRespType newOrderResp;
    @XmlElement(name = "ProfileResp")
    protected ProfileRespType profileResp;
    @XmlElement(name = "EndOfDayResp")
    protected EndOfDayRespType endOfDayResp;
    @XmlElement(name = "ReversalResp")
    protected ReversalRespType reversalResp;
    @XmlElement(name = "MarkForCaptureResp")
    protected MarkForCaptureRespType markForCaptureResp;
    @XmlElement(name = "QuickResp")
    protected QuickRespType quickResp;
    @XmlElement(name = "QuickResponse")
    protected QuickRespTypeOld quickResponse;
    @XmlElement(name = "InquiryResp")
    protected InquiryRespType inquiryResp;
    @XmlElement(name = "AccountUpdaterResp")
    protected AccountUpdaterRespType accountUpdaterResp;
    @XmlElement(name = "SafetechFraudAnalysisResp")
    protected SafetechFraudAnalysisRespType safetechFraudAnalysisResp;

    /**
     * Gets the value of the flexCacheResp property.
     * 
     * @return
     *     possible object is
     *     {@link FlexCacheRespType }
     *     
     */
    public FlexCacheRespType getFlexCacheResp() {
        return flexCacheResp;
    }

    /**
     * Sets the value of the flexCacheResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlexCacheRespType }
     *     
     */
    public void setFlexCacheResp(FlexCacheRespType value) {
        this.flexCacheResp = value;
    }

    /**
     * Gets the value of the newOrderResp property.
     * 
     * @return
     *     possible object is
     *     {@link NewOrderRespType }
     *     
     */
    public NewOrderRespType getNewOrderResp() {
        return newOrderResp;
    }

    /**
     * Sets the value of the newOrderResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link NewOrderRespType }
     *     
     */
    public void setNewOrderResp(NewOrderRespType value) {
        this.newOrderResp = value;
    }

    /**
     * Gets the value of the profileResp property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileRespType }
     *     
     */
    public ProfileRespType getProfileResp() {
        return profileResp;
    }

    /**
     * Sets the value of the profileResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileRespType }
     *     
     */
    public void setProfileResp(ProfileRespType value) {
        this.profileResp = value;
    }

    /**
     * Gets the value of the endOfDayResp property.
     * 
     * @return
     *     possible object is
     *     {@link EndOfDayRespType }
     *     
     */
    public EndOfDayRespType getEndOfDayResp() {
        return endOfDayResp;
    }

    /**
     * Sets the value of the endOfDayResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndOfDayRespType }
     *     
     */
    public void setEndOfDayResp(EndOfDayRespType value) {
        this.endOfDayResp = value;
    }

    /**
     * Gets the value of the reversalResp property.
     * 
     * @return
     *     possible object is
     *     {@link ReversalRespType }
     *     
     */
    public ReversalRespType getReversalResp() {
        return reversalResp;
    }

    /**
     * Sets the value of the reversalResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReversalRespType }
     *     
     */
    public void setReversalResp(ReversalRespType value) {
        this.reversalResp = value;
    }

    /**
     * Gets the value of the markForCaptureResp property.
     * 
     * @return
     *     possible object is
     *     {@link MarkForCaptureRespType }
     *     
     */
    public MarkForCaptureRespType getMarkForCaptureResp() {
        return markForCaptureResp;
    }

    /**
     * Sets the value of the markForCaptureResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkForCaptureRespType }
     *     
     */
    public void setMarkForCaptureResp(MarkForCaptureRespType value) {
        this.markForCaptureResp = value;
    }

    /**
     * Gets the value of the quickResp property.
     * 
     * @return
     *     possible object is
     *     {@link QuickRespType }
     *     
     */
    public QuickRespType getQuickResp() {
        return quickResp;
    }

    /**
     * Sets the value of the quickResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuickRespType }
     *     
     */
    public void setQuickResp(QuickRespType value) {
        this.quickResp = value;
    }

    /**
     * Gets the value of the quickResponse property.
     * 
     * @return
     *     possible object is
     *     {@link QuickRespTypeOld }
     *     
     */
    public QuickRespTypeOld getQuickResponse() {
        return quickResponse;
    }

    /**
     * Sets the value of the quickResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuickRespTypeOld }
     *     
     */
    public void setQuickResponse(QuickRespTypeOld value) {
        this.quickResponse = value;
    }

    /**
     * Gets the value of the inquiryResp property.
     * 
     * @return
     *     possible object is
     *     {@link InquiryRespType }
     *     
     */
    public InquiryRespType getInquiryResp() {
        return inquiryResp;
    }

    /**
     * Sets the value of the inquiryResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link InquiryRespType }
     *     
     */
    public void setInquiryResp(InquiryRespType value) {
        this.inquiryResp = value;
    }

    /**
     * Gets the value of the accountUpdaterResp property.
     * 
     * @return
     *     possible object is
     *     {@link AccountUpdaterRespType }
     *     
     */
    public AccountUpdaterRespType getAccountUpdaterResp() {
        return accountUpdaterResp;
    }

    /**
     * Sets the value of the accountUpdaterResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountUpdaterRespType }
     *     
     */
    public void setAccountUpdaterResp(AccountUpdaterRespType value) {
        this.accountUpdaterResp = value;
    }

    /**
     * Gets the value of the safetechFraudAnalysisResp property.
     * 
     * @return
     *     possible object is
     *     {@link SafetechFraudAnalysisRespType }
     *     
     */
    public SafetechFraudAnalysisRespType getSafetechFraudAnalysisResp() {
        return safetechFraudAnalysisResp;
    }

    /**
     * Sets the value of the safetechFraudAnalysisResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link SafetechFraudAnalysisRespType }
     *     
     */
    public void setSafetechFraudAnalysisResp(SafetechFraudAnalysisRespType value) {
        this.safetechFraudAnalysisResp = value;
    }

}
