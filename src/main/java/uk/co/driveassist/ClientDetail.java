//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.13 at 09:47:59 AM BST 
//


package uk.co.driveassist;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ClientDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientDetail">
 *   &lt;complexContent>
 *     &lt;extension base="{}PersonDetail">
 *       &lt;sequence>
 *         &lt;element name="DateOfBirth" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EmploymentStatus" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Occupation" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="EmploymentDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VATRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LicenceType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="TestPass" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="MentalDefect" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Insurance" type="{}InsuranceDetail" minOccurs="0"/>
 *         &lt;element name="Solicitor" type="{}SolicitorDetail" minOccurs="0"/>
 *         &lt;element name="VehicleReg" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="VehicleType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleMake" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleModel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleDoors" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="VehicleSeats" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="VehicleDamage" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="Rolled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="EmergencyCutOpen" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="VehYYMake" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="VehicleCapacity" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleColour" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehiclePurchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="VehiclePurchaseDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="VehicleCurrentValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="VehicleCurrentMileage" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="VehicleModifications" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VehicleMainUser" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleOnHP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VehicleUsedForBusiness" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VehicleBusinessGoodsCarried" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VehicleDriveable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VehicleTemporaryRepairPossible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VehicleLocationAddress1" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleLocationAddress2" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleLocationAddress3" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleLocationAddress4" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleLocationPostcode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VehicleCourtesyCar" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VehicleCourtesyCarNotMeetNeedsReason" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AvailableFrom" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AvailableTo" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PerferredContact" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PreferredContactType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientDetail", propOrder = {
    "dateOfBirth",
    "employmentStatus",
    "occupation",
    "employmentDetails",
    "vatRegistered",
    "licenceType",
    "testPass",
    "mentalDefect",
    "insurance",
    "solicitor",
    "vehicleReg",
    "vehicleType",
    "vehicleMake",
    "vehicleModel",
    "vehicleDoors",
    "vehicleSeats",
    "vehicleDamage",
    "vehYYMake",
    "vehicleCapacity",
    "vehicleColour",
    "vehiclePurchasePrice",
    "vehiclePurchaseDate",
    "vehicleCurrentValue",
    "vehicleCurrentMileage",
    "vehicleModifications",
    "vehicleMainUser",
    "vehicleOnHP",
    "vehicleUsedForBusiness",
    "vehicleBusinessGoodsCarried",
    "vehicleDriveable",
    "vehicleTemporaryRepairPossible",
    "vehicleLocationAddress1",
    "vehicleLocationAddress2",
    "vehicleLocationAddress3",
    "vehicleLocationAddress4",
    "vehicleLocationPostcode",
    "vehicleCourtesyCar",
    "vehicleCourtesyCarNotMeetNeedsReason",
    "availableFrom",
    "availableTo",
    "perferredContact",
    "preferredContactType"
})
public class ClientDetail
    extends PersonDetail
{

    @XmlElement(name = "DateOfBirth")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfBirth;
    @XmlElement(name = "EmploymentStatus")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String employmentStatus;
    @XmlElement(name = "Occupation")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String occupation;
    @XmlElement(name = "EmploymentDetails")
    protected String employmentDetails;
    @XmlElement(name = "VATRegistered")
    protected Boolean vatRegistered;
    @XmlElement(name = "LicenceType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String licenceType;
    @XmlElement(name = "TestPass")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar testPass;
    @XmlElement(name = "MentalDefect")
    protected String mentalDefect;
    @XmlElement(name = "Insurance")
    protected InsuranceDetail insurance;
    @XmlElement(name = "Solicitor")
    protected SolicitorDetail solicitor;
    @XmlElement(name = "VehicleReg")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String vehicleReg;
    @XmlElement(name = "VehicleType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleType;
    @XmlElement(name = "VehicleMake")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleMake;
    @XmlElement(name = "VehicleModel")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleModel;
    @XmlElement(name = "VehicleDoors")
    protected BigInteger vehicleDoors;
    @XmlElement(name = "VehicleSeats")
    protected BigInteger vehicleSeats;
    @XmlElement(name = "VehicleDamage")
    protected ClientDetail.VehicleDamage vehicleDamage;
    @XmlElement(name = "VehYYMake")
    protected BigInteger vehYYMake;
    @XmlElement(name = "VehicleCapacity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleCapacity;
    @XmlElement(name = "VehicleColour")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleColour;
    @XmlElement(name = "VehiclePurchasePrice")
    protected BigDecimal vehiclePurchasePrice;
    @XmlElement(name = "VehiclePurchaseDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar vehiclePurchaseDate;
    @XmlElement(name = "VehicleCurrentValue")
    protected BigDecimal vehicleCurrentValue;
    @XmlElement(name = "VehicleCurrentMileage")
    protected BigInteger vehicleCurrentMileage;
    @XmlElement(name = "VehicleModifications")
    protected String vehicleModifications;
    @XmlElement(name = "VehicleMainUser")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleMainUser;
    @XmlElement(name = "VehicleOnHP")
    protected String vehicleOnHP;
    @XmlElement(name = "VehicleUsedForBusiness")
    protected Boolean vehicleUsedForBusiness;
    @XmlElement(name = "VehicleBusinessGoodsCarried")
    protected Boolean vehicleBusinessGoodsCarried;
    @XmlElement(name = "VehicleDriveable")
    protected Boolean vehicleDriveable;
    @XmlElement(name = "VehicleTemporaryRepairPossible")
    protected Boolean vehicleTemporaryRepairPossible;
    @XmlElement(name = "VehicleLocationAddress1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleLocationAddress1;
    @XmlElement(name = "VehicleLocationAddress2")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleLocationAddress2;
    @XmlElement(name = "VehicleLocationAddress3")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleLocationAddress3;
    @XmlElement(name = "VehicleLocationAddress4")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleLocationAddress4;
    @XmlElement(name = "VehicleLocationPostcode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleLocationPostcode;
    @XmlElement(name = "VehicleCourtesyCar")
    protected Boolean vehicleCourtesyCar;
    @XmlElement(name = "VehicleCourtesyCarNotMeetNeedsReason")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String vehicleCourtesyCarNotMeetNeedsReason;
    @XmlElement(name = "AvailableFrom")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String availableFrom;
    @XmlElement(name = "AvailableTo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String availableTo;
    @XmlElement(name = "PerferredContact")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String perferredContact;
    @XmlElement(name = "PreferredContactType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String preferredContactType;

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfBirth(XMLGregorianCalendar value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the employmentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmploymentStatus() {
        return employmentStatus;
    }

    /**
     * Sets the value of the employmentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmploymentStatus(String value) {
        this.employmentStatus = value;
    }

    /**
     * Gets the value of the occupation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Sets the value of the occupation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupation(String value) {
        this.occupation = value;
    }

    /**
     * Gets the value of the employmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmploymentDetails() {
        return employmentDetails;
    }

    /**
     * Sets the value of the employmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmploymentDetails(String value) {
        this.employmentDetails = value;
    }

    /**
     * Gets the value of the vatRegistered property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVATRegistered() {
        return vatRegistered;
    }

    /**
     * Sets the value of the vatRegistered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVATRegistered(Boolean value) {
        this.vatRegistered = value;
    }

    /**
     * Gets the value of the licenceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenceType() {
        return licenceType;
    }

    /**
     * Sets the value of the licenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenceType(String value) {
        this.licenceType = value;
    }

    /**
     * Gets the value of the testPass property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTestPass() {
        return testPass;
    }

    /**
     * Sets the value of the testPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTestPass(XMLGregorianCalendar value) {
        this.testPass = value;
    }

    /**
     * Gets the value of the mentalDefect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMentalDefect() {
        return mentalDefect;
    }

    /**
     * Sets the value of the mentalDefect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMentalDefect(String value) {
        this.mentalDefect = value;
    }

    /**
     * Gets the value of the insurance property.
     * 
     * @return
     *     possible object is
     *     {@link InsuranceDetail }
     *     
     */
    public InsuranceDetail getInsurance() {
        return insurance;
    }

    /**
     * Sets the value of the insurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link InsuranceDetail }
     *     
     */
    public void setInsurance(InsuranceDetail value) {
        this.insurance = value;
    }

    /**
     * Gets the value of the solicitor property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitorDetail }
     *     
     */
    public SolicitorDetail getSolicitor() {
        return solicitor;
    }

    /**
     * Sets the value of the solicitor property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitorDetail }
     *     
     */
    public void setSolicitor(SolicitorDetail value) {
        this.solicitor = value;
    }

    /**
     * Gets the value of the vehicleReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleReg() {
        return vehicleReg;
    }

    /**
     * Sets the value of the vehicleReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleReg(String value) {
        this.vehicleReg = value;
    }

    /**
     * Gets the value of the vehicleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Sets the value of the vehicleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleType(String value) {
        this.vehicleType = value;
    }

    /**
     * Gets the value of the vehicleMake property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleMake() {
        return vehicleMake;
    }

    /**
     * Sets the value of the vehicleMake property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleMake(String value) {
        this.vehicleMake = value;
    }

    /**
     * Gets the value of the vehicleModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleModel() {
        return vehicleModel;
    }

    /**
     * Sets the value of the vehicleModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleModel(String value) {
        this.vehicleModel = value;
    }

    /**
     * Gets the value of the vehicleDoors property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVehicleDoors() {
        return vehicleDoors;
    }

    /**
     * Sets the value of the vehicleDoors property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVehicleDoors(BigInteger value) {
        this.vehicleDoors = value;
    }

    /**
     * Gets the value of the vehicleSeats property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVehicleSeats() {
        return vehicleSeats;
    }

    /**
     * Sets the value of the vehicleSeats property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVehicleSeats(BigInteger value) {
        this.vehicleSeats = value;
    }

    /**
     * Gets the value of the vehicleDamage property.
     * 
     * @return
     *     possible object is
     *     {@link ClientDetail.VehicleDamage }
     *     
     */
    public ClientDetail.VehicleDamage getVehicleDamage() {
        return vehicleDamage;
    }

    /**
     * Sets the value of the vehicleDamage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientDetail.VehicleDamage }
     *     
     */
    public void setVehicleDamage(ClientDetail.VehicleDamage value) {
        this.vehicleDamage = value;
    }

    /**
     * Gets the value of the vehYYMake property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVehYYMake() {
        return vehYYMake;
    }

    /**
     * Sets the value of the vehYYMake property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVehYYMake(BigInteger value) {
        this.vehYYMake = value;
    }

    /**
     * Gets the value of the vehicleCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleCapacity() {
        return vehicleCapacity;
    }

    /**
     * Sets the value of the vehicleCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleCapacity(String value) {
        this.vehicleCapacity = value;
    }

    /**
     * Gets the value of the vehicleColour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleColour() {
        return vehicleColour;
    }

    /**
     * Sets the value of the vehicleColour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleColour(String value) {
        this.vehicleColour = value;
    }

    /**
     * Gets the value of the vehiclePurchasePrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVehiclePurchasePrice() {
        return vehiclePurchasePrice;
    }

    /**
     * Sets the value of the vehiclePurchasePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVehiclePurchasePrice(BigDecimal value) {
        this.vehiclePurchasePrice = value;
    }

    /**
     * Gets the value of the vehiclePurchaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVehiclePurchaseDate() {
        return vehiclePurchaseDate;
    }

    /**
     * Sets the value of the vehiclePurchaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVehiclePurchaseDate(XMLGregorianCalendar value) {
        this.vehiclePurchaseDate = value;
    }

    /**
     * Gets the value of the vehicleCurrentValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVehicleCurrentValue() {
        return vehicleCurrentValue;
    }

    /**
     * Sets the value of the vehicleCurrentValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVehicleCurrentValue(BigDecimal value) {
        this.vehicleCurrentValue = value;
    }

    /**
     * Gets the value of the vehicleCurrentMileage property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVehicleCurrentMileage() {
        return vehicleCurrentMileage;
    }

    /**
     * Sets the value of the vehicleCurrentMileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVehicleCurrentMileage(BigInteger value) {
        this.vehicleCurrentMileage = value;
    }

    /**
     * Gets the value of the vehicleModifications property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleModifications() {
        return vehicleModifications;
    }

    /**
     * Sets the value of the vehicleModifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleModifications(String value) {
        this.vehicleModifications = value;
    }

    /**
     * Gets the value of the vehicleMainUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleMainUser() {
        return vehicleMainUser;
    }

    /**
     * Sets the value of the vehicleMainUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleMainUser(String value) {
        this.vehicleMainUser = value;
    }

    /**
     * Gets the value of the vehicleOnHP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleOnHP() {
        return vehicleOnHP;
    }

    /**
     * Sets the value of the vehicleOnHP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleOnHP(String value) {
        this.vehicleOnHP = value;
    }

    /**
     * Gets the value of the vehicleUsedForBusiness property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVehicleUsedForBusiness() {
        return vehicleUsedForBusiness;
    }

    /**
     * Sets the value of the vehicleUsedForBusiness property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVehicleUsedForBusiness(Boolean value) {
        this.vehicleUsedForBusiness = value;
    }

    /**
     * Gets the value of the vehicleBusinessGoodsCarried property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVehicleBusinessGoodsCarried() {
        return vehicleBusinessGoodsCarried;
    }

    /**
     * Sets the value of the vehicleBusinessGoodsCarried property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVehicleBusinessGoodsCarried(Boolean value) {
        this.vehicleBusinessGoodsCarried = value;
    }

    /**
     * Gets the value of the vehicleDriveable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVehicleDriveable() {
        return vehicleDriveable;
    }

    /**
     * Sets the value of the vehicleDriveable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVehicleDriveable(Boolean value) {
        this.vehicleDriveable = value;
    }

    /**
     * Gets the value of the vehicleTemporaryRepairPossible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVehicleTemporaryRepairPossible() {
        return vehicleTemporaryRepairPossible;
    }

    /**
     * Sets the value of the vehicleTemporaryRepairPossible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVehicleTemporaryRepairPossible(Boolean value) {
        this.vehicleTemporaryRepairPossible = value;
    }

    /**
     * Gets the value of the vehicleLocationAddress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleLocationAddress1() {
        return vehicleLocationAddress1;
    }

    /**
     * Sets the value of the vehicleLocationAddress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleLocationAddress1(String value) {
        this.vehicleLocationAddress1 = value;
    }

    /**
     * Gets the value of the vehicleLocationAddress2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleLocationAddress2() {
        return vehicleLocationAddress2;
    }

    /**
     * Sets the value of the vehicleLocationAddress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleLocationAddress2(String value) {
        this.vehicleLocationAddress2 = value;
    }

    /**
     * Gets the value of the vehicleLocationAddress3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleLocationAddress3() {
        return vehicleLocationAddress3;
    }

    /**
     * Sets the value of the vehicleLocationAddress3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleLocationAddress3(String value) {
        this.vehicleLocationAddress3 = value;
    }

    /**
     * Gets the value of the vehicleLocationAddress4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleLocationAddress4() {
        return vehicleLocationAddress4;
    }

    /**
     * Sets the value of the vehicleLocationAddress4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleLocationAddress4(String value) {
        this.vehicleLocationAddress4 = value;
    }

    /**
     * Gets the value of the vehicleLocationPostcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleLocationPostcode() {
        return vehicleLocationPostcode;
    }

    /**
     * Sets the value of the vehicleLocationPostcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleLocationPostcode(String value) {
        this.vehicleLocationPostcode = value;
    }

    /**
     * Gets the value of the vehicleCourtesyCar property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVehicleCourtesyCar() {
        return vehicleCourtesyCar;
    }

    /**
     * Sets the value of the vehicleCourtesyCar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVehicleCourtesyCar(Boolean value) {
        this.vehicleCourtesyCar = value;
    }

    /**
     * Gets the value of the vehicleCourtesyCarNotMeetNeedsReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleCourtesyCarNotMeetNeedsReason() {
        return vehicleCourtesyCarNotMeetNeedsReason;
    }

    /**
     * Sets the value of the vehicleCourtesyCarNotMeetNeedsReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleCourtesyCarNotMeetNeedsReason(String value) {
        this.vehicleCourtesyCarNotMeetNeedsReason = value;
    }

    /**
     * Gets the value of the availableFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvailableFrom() {
        return availableFrom;
    }

    /**
     * Sets the value of the availableFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvailableFrom(String value) {
        this.availableFrom = value;
    }

    /**
     * Gets the value of the availableTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvailableTo() {
        return availableTo;
    }

    /**
     * Sets the value of the availableTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvailableTo(String value) {
        this.availableTo = value;
    }

    /**
     * Gets the value of the perferredContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerferredContact() {
        return perferredContact;
    }

    /**
     * Sets the value of the perferredContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerferredContact(String value) {
        this.perferredContact = value;
    }

    /**
     * Gets the value of the preferredContactType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredContactType() {
        return preferredContactType;
    }

    /**
     * Sets the value of the preferredContactType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredContactType(String value) {
        this.preferredContactType = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="Rolled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="EmergencyCutOpen" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class VehicleDamage {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "Rolled")
        protected Boolean rolled;
        @XmlAttribute(name = "EmergencyCutOpen")
        protected Boolean emergencyCutOpen;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the rolled property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isRolled() {
            return rolled;
        }

        /**
         * Sets the value of the rolled property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRolled(Boolean value) {
            this.rolled = value;
        }

        /**
         * Gets the value of the emergencyCutOpen property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isEmergencyCutOpen() {
            return emergencyCutOpen;
        }

        /**
         * Sets the value of the emergencyCutOpen property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setEmergencyCutOpen(Boolean value) {
            this.emergencyCutOpen = value;
        }

    }

}
