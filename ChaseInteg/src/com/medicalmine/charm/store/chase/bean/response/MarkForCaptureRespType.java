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
 * <p>Java class for markForCaptureRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="markForCaptureRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MerchantID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TerminalID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TxRefNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TxRefIdx" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProcStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StatusMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RespTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ApprovalStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RespCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AVSRespCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AuthCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RespMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HostRespCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HostAVSRespCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TxnSurchargeAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TokenAssuranceLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DPANAccountStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MITReceivedTransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "markForCaptureRespType", propOrder = {
    "merchantID",
    "terminalID",
    "orderID",
    "txRefNum",
    "txRefIdx",
    "amount",
    "procStatus",
    "statusMsg",
    "respTime",
    "approvalStatus",
    "respCode",
    "avsRespCode",
    "authCode",
    "respMsg",
    "hostRespCode",
    "hostAVSRespCode",
    "txnSurchargeAmt",
    "tokenAssuranceLevel",
    "dpanAccountStatus",
    "mitReceivedTransactionID"
})
public class MarkForCaptureRespType {

    @XmlElement(name = "MerchantID", required = true)
    protected String merchantID;
    @XmlElement(name = "TerminalID", required = true)
    protected String terminalID;
    @XmlElement(name = "OrderID", required = true)
    protected String orderID;
    @XmlElement(name = "TxRefNum", required = true)
    protected String txRefNum;
    @XmlElement(name = "TxRefIdx", required = true)
    protected String txRefIdx;
    @XmlElement(name = "Amount", required = true)
    protected String amount;
    @XmlElement(name = "ProcStatus", required = true)
    protected String procStatus;
    @XmlElement(name = "StatusMsg", required = true)
    protected String statusMsg;
    @XmlElement(name = "RespTime", required = true)
    protected String respTime;
    @XmlElement(name = "ApprovalStatus", required = true)
    protected String approvalStatus;
    @XmlElement(name = "RespCode", required = true)
    protected String respCode;
    @XmlElement(name = "AVSRespCode", required = true)
    protected String avsRespCode;
    @XmlElement(name = "AuthCode", required = true)
    protected String authCode;
    @XmlElement(name = "RespMsg", required = true)
    protected String respMsg;
    @XmlElement(name = "HostRespCode", required = true)
    protected String hostRespCode;
    @XmlElement(name = "HostAVSRespCode", required = true)
    protected String hostAVSRespCode;
    @XmlElement(name = "TxnSurchargeAmt")
    protected String txnSurchargeAmt;
    @XmlElement(name = "TokenAssuranceLevel")
    protected String tokenAssuranceLevel;
    @XmlElement(name = "DPANAccountStatus")
    protected String dpanAccountStatus;
    @XmlElement(name = "MITReceivedTransactionID")
    protected String mitReceivedTransactionID;

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
     * Gets the value of the txRefNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxRefNum() {
        return txRefNum;
    }

    /**
     * Sets the value of the txRefNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxRefNum(String value) {
        this.txRefNum = value;
    }

    /**
     * Gets the value of the txRefIdx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxRefIdx() {
        return txRefIdx;
    }

    /**
     * Sets the value of the txRefIdx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxRefIdx(String value) {
        this.txRefIdx = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Gets the value of the procStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcStatus() {
        return procStatus;
    }

    /**
     * Sets the value of the procStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcStatus(String value) {
        this.procStatus = value;
    }

    /**
     * Gets the value of the statusMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusMsg() {
        return statusMsg;
    }

    /**
     * Sets the value of the statusMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusMsg(String value) {
        this.statusMsg = value;
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

    /**
     * Gets the value of the approvalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * Sets the value of the approvalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApprovalStatus(String value) {
        this.approvalStatus = value;
    }

    /**
     * Gets the value of the respCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * Sets the value of the respCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespCode(String value) {
        this.respCode = value;
    }

    /**
     * Gets the value of the avsRespCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAVSRespCode() {
        return avsRespCode;
    }

    /**
     * Sets the value of the avsRespCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAVSRespCode(String value) {
        this.avsRespCode = value;
    }

    /**
     * Gets the value of the authCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthCode() {
        return authCode;
    }

    /**
     * Sets the value of the authCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthCode(String value) {
        this.authCode = value;
    }

    /**
     * Gets the value of the respMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespMsg() {
        return respMsg;
    }

    /**
     * Sets the value of the respMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespMsg(String value) {
        this.respMsg = value;
    }

    /**
     * Gets the value of the hostRespCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostRespCode() {
        return hostRespCode;
    }

    /**
     * Sets the value of the hostRespCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostRespCode(String value) {
        this.hostRespCode = value;
    }

    /**
     * Gets the value of the hostAVSRespCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostAVSRespCode() {
        return hostAVSRespCode;
    }

    /**
     * Sets the value of the hostAVSRespCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostAVSRespCode(String value) {
        this.hostAVSRespCode = value;
    }

    /**
     * Gets the value of the txnSurchargeAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnSurchargeAmt() {
        return txnSurchargeAmt;
    }

    /**
     * Sets the value of the txnSurchargeAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnSurchargeAmt(String value) {
        this.txnSurchargeAmt = value;
    }

    /**
     * Gets the value of the tokenAssuranceLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenAssuranceLevel() {
        return tokenAssuranceLevel;
    }

    /**
     * Sets the value of the tokenAssuranceLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenAssuranceLevel(String value) {
        this.tokenAssuranceLevel = value;
    }

    /**
     * Gets the value of the dpanAccountStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPANAccountStatus() {
        return dpanAccountStatus;
    }

    /**
     * Sets the value of the dpanAccountStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPANAccountStatus(String value) {
        this.dpanAccountStatus = value;
    }

    /**
     * Gets the value of the mitReceivedTransactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMITReceivedTransactionID() {
        return mitReceivedTransactionID;
    }

    /**
     * Sets the value of the mitReceivedTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMITReceivedTransactionID(String value) {
        this.mitReceivedTransactionID = value;
    }

}