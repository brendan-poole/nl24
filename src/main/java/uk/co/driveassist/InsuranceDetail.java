//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.13 at 09:47:59 AM BST 
//


package uk.co.driveassist;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InsuranceDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InsuranceDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Insurer" type="{}Insurer" minOccurs="0"/>
 *         &lt;element name="Broker" type="{}Broker" minOccurs="0"/>
 *         &lt;element name="Cover" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PolicyInception" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="PolicyExpiry" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="PolicyExcess" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="InsuranceExtra" type="{}InsuranceExtraDetail" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="VerifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerifiedWith" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerifiedBranch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerifiedDealing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClaimReported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LiabilityAccepted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ProtocolAgreementApplies" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="MIDRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="MIDSentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="MIDPositive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="MIDCompletedBy" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MIDReferrer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MIDOOH" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InsuranceDetail", propOrder = {
    "insurer",
    "broker",
    "cover",
    "policyInception",
    "policyExpiry",
    "policyExcess",
    "insuranceExtra",
    "verifiedBy",
    "verifiedWith",
    "verifiedBranch",
    "verifiedDealing",
    "claimReported",
    "liabilityAccepted",
    "protocolAgreementApplies",
    "midRequired",
    "midSentDate",
    "midPositive",
    "midCompletedBy",
    "midReferrer",
    "midooh"
})
public class InsuranceDetail {

    @XmlElement(name = "Insurer")
    protected Insurer insurer;
    @XmlElement(name = "Broker")
    protected Broker broker;
    @XmlElement(name = "Cover")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cover;
    @XmlElement(name = "PolicyInception")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar policyInception;
    @XmlElement(name = "PolicyExpiry")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar policyExpiry;
    @XmlElement(name = "PolicyExcess")
    protected BigDecimal policyExcess;
    @XmlElement(name = "InsuranceExtra")
    protected List<InsuranceExtraDetail> insuranceExtra;
    @XmlElement(name = "VerifiedBy")
    protected String verifiedBy;
    @XmlElement(name = "VerifiedWith")
    protected String verifiedWith;
    @XmlElement(name = "VerifiedBranch")
    protected String verifiedBranch;
    @XmlElement(name = "VerifiedDealing")
    protected String verifiedDealing;
    @XmlElement(name = "ClaimReported")
    protected Boolean claimReported;
    @XmlElement(name = "LiabilityAccepted")
    protected Boolean liabilityAccepted;
    @XmlElement(name = "ProtocolAgreementApplies")
    protected BigInteger protocolAgreementApplies;
    @XmlElement(name = "MIDRequired")
    protected Boolean midRequired;
    @XmlElement(name = "MIDSentDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar midSentDate;
    @XmlElement(name = "MIDPositive")
    protected Boolean midPositive;
    @XmlElement(name = "MIDCompletedBy")
    protected Integer midCompletedBy;
    @XmlElement(name = "MIDReferrer")
    protected Integer midReferrer;
    @XmlElement(name = "MIDOOH")
    protected Boolean midooh;

    /**
     * Gets the value of the insurer property.
     * 
     * @return
     *     possible object is
     *     {@link Insurer }
     *     
     */
    public Insurer getInsurer() {
        return insurer;
    }

    /**
     * Sets the value of the insurer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Insurer }
     *     
     */
    public void setInsurer(Insurer value) {
        this.insurer = value;
    }

    /**
     * Gets the value of the broker property.
     * 
     * @return
     *     possible object is
     *     {@link Broker }
     *     
     */
    public Broker getBroker() {
        return broker;
    }

    /**
     * Sets the value of the broker property.
     * 
     * @param value
     *     allowed object is
     *     {@link Broker }
     *     
     */
    public void setBroker(Broker value) {
        this.broker = value;
    }

    /**
     * Gets the value of the cover property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCover() {
        return cover;
    }

    /**
     * Sets the value of the cover property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCover(String value) {
        this.cover = value;
    }

    /**
     * Gets the value of the policyInception property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPolicyInception() {
        return policyInception;
    }

    /**
     * Sets the value of the policyInception property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPolicyInception(XMLGregorianCalendar value) {
        this.policyInception = value;
    }

    /**
     * Gets the value of the policyExpiry property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPolicyExpiry() {
        return policyExpiry;
    }

    /**
     * Sets the value of the policyExpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPolicyExpiry(XMLGregorianCalendar value) {
        this.policyExpiry = value;
    }

    /**
     * Gets the value of the policyExcess property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPolicyExcess() {
        return policyExcess;
    }

    /**
     * Sets the value of the policyExcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPolicyExcess(BigDecimal value) {
        this.policyExcess = value;
    }

    /**
     * Gets the value of the insuranceExtra property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insuranceExtra property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInsuranceExtra().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsuranceExtraDetail }
     * 
     * 
     */
    public List<InsuranceExtraDetail> getInsuranceExtra() {
        if (insuranceExtra == null) {
            insuranceExtra = new ArrayList<InsuranceExtraDetail>();
        }
        return this.insuranceExtra;
    }

    /**
     * Gets the value of the verifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifiedBy() {
        return verifiedBy;
    }

    /**
     * Sets the value of the verifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifiedBy(String value) {
        this.verifiedBy = value;
    }

    /**
     * Gets the value of the verifiedWith property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifiedWith() {
        return verifiedWith;
    }

    /**
     * Sets the value of the verifiedWith property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifiedWith(String value) {
        this.verifiedWith = value;
    }

    /**
     * Gets the value of the verifiedBranch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifiedBranch() {
        return verifiedBranch;
    }

    /**
     * Sets the value of the verifiedBranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifiedBranch(String value) {
        this.verifiedBranch = value;
    }

    /**
     * Gets the value of the verifiedDealing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifiedDealing() {
        return verifiedDealing;
    }

    /**
     * Sets the value of the verifiedDealing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifiedDealing(String value) {
        this.verifiedDealing = value;
    }

    /**
     * Gets the value of the claimReported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isClaimReported() {
        return claimReported;
    }

    /**
     * Sets the value of the claimReported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setClaimReported(Boolean value) {
        this.claimReported = value;
    }

    /**
     * Gets the value of the liabilityAccepted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLiabilityAccepted() {
        return liabilityAccepted;
    }

    /**
     * Sets the value of the liabilityAccepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLiabilityAccepted(Boolean value) {
        this.liabilityAccepted = value;
    }

    /**
     * Gets the value of the protocolAgreementApplies property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProtocolAgreementApplies() {
        return protocolAgreementApplies;
    }

    /**
     * Sets the value of the protocolAgreementApplies property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProtocolAgreementApplies(BigInteger value) {
        this.protocolAgreementApplies = value;
    }

    /**
     * Gets the value of the midRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMIDRequired() {
        return midRequired;
    }

    /**
     * Sets the value of the midRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMIDRequired(Boolean value) {
        this.midRequired = value;
    }

    /**
     * Gets the value of the midSentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMIDSentDate() {
        return midSentDate;
    }

    /**
     * Sets the value of the midSentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMIDSentDate(XMLGregorianCalendar value) {
        this.midSentDate = value;
    }

    /**
     * Gets the value of the midPositive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMIDPositive() {
        return midPositive;
    }

    /**
     * Sets the value of the midPositive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMIDPositive(Boolean value) {
        this.midPositive = value;
    }

    /**
     * Gets the value of the midCompletedBy property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMIDCompletedBy() {
        return midCompletedBy;
    }

    /**
     * Sets the value of the midCompletedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMIDCompletedBy(Integer value) {
        this.midCompletedBy = value;
    }

    /**
     * Gets the value of the midReferrer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMIDReferrer() {
        return midReferrer;
    }

    /**
     * Sets the value of the midReferrer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMIDReferrer(Integer value) {
        this.midReferrer = value;
    }

    /**
     * Gets the value of the midooh property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMIDOOH() {
        return midooh;
    }

    /**
     * Sets the value of the midooh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMIDOOH(Boolean value) {
        this.midooh = value;
    }

}
