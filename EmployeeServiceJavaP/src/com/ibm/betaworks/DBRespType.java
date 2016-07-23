//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 10:01:06 AM IST 
//


package com.ibm.betaworks;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DBRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DBRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserReturnCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RowsRetrieved" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RowsAdded" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RowsUpdated" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RowsDeleted" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SQLCode_ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SQLState_SQLState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SQL_Error_Message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DBRespType", namespace = "", propOrder = {
    "userReturnCode",
    "rowsRetrieved",
    "rowsAdded",
    "rowsUpdated",
    "rowsDeleted",
    "sqlCodeErrorCode",
    "sqlStateSQLState",
    "sqlErrorMessage"
})
public class DBRespType {

    @XmlElement(name = "UserReturnCode")
    protected int userReturnCode;
    @XmlElement(name = "RowsRetrieved")
    protected int rowsRetrieved;
    @XmlElement(name = "RowsAdded")
    protected int rowsAdded;
    @XmlElement(name = "RowsUpdated")
    protected int rowsUpdated;
    @XmlElement(name = "RowsDeleted")
    protected int rowsDeleted;
    @XmlElement(name = "SQLCode_ErrorCode")
    protected int sqlCodeErrorCode;
    @XmlElement(name = "SQLState_SQLState", required = true)
    protected String sqlStateSQLState;
    @XmlElement(name = "SQL_Error_Message", required = true)
    protected String sqlErrorMessage;

    /**
     * Gets the value of the userReturnCode property.
     * 
     */
    public int getUserReturnCode() {
        return userReturnCode;
    }

    /**
     * Sets the value of the userReturnCode property.
     * 
     */
    public void setUserReturnCode(int value) {
        this.userReturnCode = value;
    }

    /**
     * Gets the value of the rowsRetrieved property.
     * 
     */
    public int getRowsRetrieved() {
        return rowsRetrieved;
    }

    /**
     * Sets the value of the rowsRetrieved property.
     * 
     */
    public void setRowsRetrieved(int value) {
        this.rowsRetrieved = value;
    }

    /**
     * Gets the value of the rowsAdded property.
     * 
     */
    public int getRowsAdded() {
        return rowsAdded;
    }

    /**
     * Sets the value of the rowsAdded property.
     * 
     */
    public void setRowsAdded(int value) {
        this.rowsAdded = value;
    }

    /**
     * Gets the value of the rowsUpdated property.
     * 
     */
    public int getRowsUpdated() {
        return rowsUpdated;
    }

    /**
     * Sets the value of the rowsUpdated property.
     * 
     */
    public void setRowsUpdated(int value) {
        this.rowsUpdated = value;
    }

    /**
     * Gets the value of the rowsDeleted property.
     * 
     */
    public int getRowsDeleted() {
        return rowsDeleted;
    }

    /**
     * Sets the value of the rowsDeleted property.
     * 
     */
    public void setRowsDeleted(int value) {
        this.rowsDeleted = value;
    }

    /**
     * Gets the value of the sqlCodeErrorCode property.
     * 
     */
    public int getSQLCodeErrorCode() {
        return sqlCodeErrorCode;
    }

    /**
     * Sets the value of the sqlCodeErrorCode property.
     * 
     */
    public void setSQLCodeErrorCode(int value) {
        this.sqlCodeErrorCode = value;
    }

    /**
     * Gets the value of the sqlStateSQLState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSQLStateSQLState() {
        return sqlStateSQLState;
    }

    /**
     * Sets the value of the sqlStateSQLState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSQLStateSQLState(String value) {
        this.sqlStateSQLState = value;
    }

    /**
     * Gets the value of the sqlErrorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSQLErrorMessage() {
        return sqlErrorMessage;
    }

    /**
     * Sets the value of the sqlErrorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSQLErrorMessage(String value) {
        this.sqlErrorMessage = value;
    }

}
