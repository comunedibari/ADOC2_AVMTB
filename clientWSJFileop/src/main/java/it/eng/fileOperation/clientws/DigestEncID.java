
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DigestEncID.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="DigestEncID">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="hex"/>
 *     &lt;enumeration value="base64"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DigestEncID")
@XmlEnum
public enum DigestEncID {

    @XmlEnumValue("hex")
    HEX("hex"),
    @XmlEnumValue("base64")
    BASE_64("base64");
    private final String value;

    DigestEncID(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DigestEncID fromValue(String v) {
        for (DigestEncID c: DigestEncID.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
