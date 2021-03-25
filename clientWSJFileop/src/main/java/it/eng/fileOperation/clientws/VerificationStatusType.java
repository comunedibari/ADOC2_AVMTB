
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VerificationStatusType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="VerificationStatusType">
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
@XmlType(name = "VerificationStatusType", namespace = "it.eng.fileoperation.ws.base")
@XmlEnum
public enum VerificationStatusType {

    OK,
    KO,
    ERROR,
    SKIPPED;

    public String value() {
        return name();
    }

    public static VerificationStatusType fromValue(String v) {
        return valueOf(v);
    }

}
