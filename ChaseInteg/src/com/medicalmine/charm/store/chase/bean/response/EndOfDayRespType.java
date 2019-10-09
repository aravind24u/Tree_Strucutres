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
 * <p>Java class for endOfDayRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="endOfDayRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MerchantID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TerminalID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BatchSeqNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProcStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StatusMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}SettleRejectHoldingBin" minOccurs="0"/>
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
@XmlType(name = "endOfDayRespType", propOrder = {
    "merchantID",
    "terminalID",
    "batchSeqNum",
    "procStatus",
    "statusMsg",
    "settleRejectHoldingBin",
    "respTime"
})
public class EndOfDayRespType {

    @XmlElement(name = "MerchantID", required = true)
    protected String merchantID;
    @XmlElement(name = "TerminalID", required = true)
    protected String terminalID;
    @XmlElement(name = "BatchSeqNum", required = true)
    protected String batchSeqNum;
    @XmlElement(name = "ProcStatus", required = true)
    protected String procStatus;
    @XmlElement(name = "StatusMsg", required = true)
    protected String statusMsg;
    @XmlElement(name = "SettleRejectHoldingBin")
    protected SettleRejectHoldingBin settleRejectHoldingBin;
    @XmlElement(name = "RespTime", required = true)
    protected String respTime;

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
     * Gets the value of the batchSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchSeqNum() {
        return batchSeqNum;
    }

    /**
     * Sets the value of the batchSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchSeqNum(String value) {
        this.batchSeqNum = value;
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
     * Gets the value of the settleRejectHoldingBin property.
     * 
     * @return
     *     possible object is
     *     {@link SettleRejectHoldingBin }
     *     
     */
    public SettleRejectHoldingBin getSettleRejectHoldingBin() {
        return settleRejectHoldingBin;
    }

    /**
     * Sets the value of the settleRejectHoldingBin property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettleRejectHoldingBin }
     *     
     */
    public void setSettleRejectHoldingBin(SettleRejectHoldingBin value) {
        this.settleRejectHoldingBin = value;
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
