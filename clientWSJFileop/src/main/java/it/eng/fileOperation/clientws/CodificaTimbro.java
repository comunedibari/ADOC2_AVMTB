
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per codificaTimbro.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="codificaTimbro">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BarcodePDF417"/>
 *     &lt;enumeration value="BarcodeDatamatrix"/>
 *     &lt;enumeration value="BarcodeQRCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "codificaTimbro", namespace = "it.eng.fileoperation.ws.timbro")
@XmlEnum
public enum CodificaTimbro {

    @XmlEnumValue("BarcodePDF417")
    BARCODE_PDF_417("BarcodePDF417"),
    @XmlEnumValue("BarcodeDatamatrix")
    BARCODE_DATAMATRIX("BarcodeDatamatrix"),
    @XmlEnumValue("BarcodeQRCode")
    BARCODE_QR_CODE("BarcodeQRCode");
    private final String value;

    CodificaTimbro(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CodificaTimbro fromValue(String v) {
        for (CodificaTimbro c: CodificaTimbro.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
