package it.eng.fileOperation.clientws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per mappaParametri complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="mappaParametri">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parametro" maxOccurs="unbounded" form="qualified">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="chiave" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *                   &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mappaParametri", namespace = "it.eng.fileoperation.ws.timbro", propOrder = { "parametro" })
public class MappaParametri {

	@XmlElement(required = true)
	protected List<MappaParametri.Parametro> parametro;

	/**
	 * Gets the value of the parametro property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the parametro property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getParametro().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link MappaParametri.Parametro }
	 * 
	 * 
	 */
	public List<MappaParametri.Parametro> getParametro() {
		if (parametro == null) {
			parametro = new ArrayList<MappaParametri.Parametro>();
		}
		return this.parametro;
	}

	public void setParametro(List<MappaParametri.Parametro> parametro) {
		this.parametro = parametro;
	}

	/**
	 * <p>
	 * Classe Java per anonymous complex type.
	 * 
	 * <p>
	 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="chiave" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
	 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "chiave", "valore" })
	public static class Parametro {

		@XmlElement(namespace = "it.eng.fileoperation.ws.timbro", required = true)
		protected String chiave;
		@XmlElement(namespace = "it.eng.fileoperation.ws.timbro", required = true)
		protected String valore;

		/**
		 * Recupera il valore della proprietà chiave.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getChiave() {
			return chiave;
		}

		/**
		 * Imposta il valore della proprietà chiave.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setChiave(String value) {
			this.chiave = value;
		}

		/**
		 * Recupera il valore della proprietà valore.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getValore() {
			return valore;
		}

		/**
		 * Imposta il valore della proprietà valore.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setValore(String value) {
			this.valore = value;
		}

	}

}
