//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.04 at 10:03:09 AM CEST 
//


package it.eng.module.foutility.beans.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CertVerificationStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CertVerificationStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="KO"/>
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="SKIPPED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CertVerificationStatusType", namespace = "verify.cryptoutil.eng.it")
@XmlEnum
public enum CertVerificationStatusType {

    OK,
    KO,
    ERROR,
    SKIPPED;

    public String value() {
        return name();
    }

    public static CertVerificationStatusType fromValue(String v) {
        return valueOf(v);
    }

}
