
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isModuleActiveReturn" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isModuleActiveReturn"
})
@XmlRootElement(name = "isModuleActiveResponse")
public class IsModuleActiveResponse {

    protected boolean isModuleActiveReturn;

    /**
     * Recupera il valore della propriet� isModuleActiveReturn.
     * 
     */
    public boolean isIsModuleActiveReturn() {
        return isModuleActiveReturn;
    }

    /**
     * Imposta il valore della propriet� isModuleActiveReturn.
     * 
     */
    public void setIsModuleActiveReturn(boolean value) {
        this.isModuleActiveReturn = value;
    }

}