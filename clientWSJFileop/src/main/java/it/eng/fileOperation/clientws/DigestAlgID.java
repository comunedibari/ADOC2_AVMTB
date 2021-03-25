
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DigestAlgID.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="DigestAlgID">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="crc-32"/>
 *     &lt;enumeration value="sha-1"/>
 *     &lt;enumeration value="sha-256"/>
 *     &lt;enumeration value="md5"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DigestAlgID")
@XmlEnum
public enum DigestAlgID {

    @XmlEnumValue("crc-32")
    CRC_32("crc-32"),
    @XmlEnumValue("sha-1")
    SHA_1("sha-1"),
    @XmlEnumValue("sha-256")
    SHA_256("sha-256"),
    @XmlEnumValue("md5")
    MD_5("md5");
    private final String value;

    DigestAlgID(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DigestAlgID fromValue(String v) {
        for (DigestAlgID c: DigestAlgID.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
