package it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean;

import it.eng.document.NumeroColonna;

public class DettColonnaAttributoListaBean {

	// 1: N.ro (valori da 1 a n) del campo
	@NumeroColonna(numero = "1")
	private String numero;

	// 2: Nome identificativo del campo (ATTR_NAME)
	@NumeroColonna(numero = "2")
	private String nome;

	// 3: Label del campo
	@NumeroColonna(numero = "3")
	private String label;

	// 4: Tipo di campo. Valori: DATE, DATETIME, TEXT, TEXT-AREA, CHECK, INTEGER, EURO, DECIMAL, COMBO-BOX, LISTA
	@NumeroColonna(numero = "4")
	private String tipo;

	// 5: N.ro massimo di caratteri/cifre
	@NumeroColonna(numero = "5")
	private String numMaxCaratteri;

	// 6: Larghezza del campo (size)
	@NumeroColonna(numero = "6")
	private String larghezza;

	// 7: Valore di default
	@NumeroColonna(numero = "7")
	private String valoreDefault;

	// 9: (valori 1/0) 1 indica che il campo è obbligatorio, 0 che è facoltativo
	@NumeroColonna(numero = "9")
	private String obbligatorio;

	// 10: Altezza (in nro righe) del campo (se TEXT-AREA)
	@NumeroColonna(numero = "10")
	private String altezza;

	// 11:(valori 1/0) 1 indica se il campo è modificabile, 0 che è in sola visualizzazione
	@NumeroColonna(numero = "11")
	private String modificabile;

	// 12: N.ro di cifre decimali (se tipo EURO o DECIMAL)
	@NumeroColonna(numero = "12")
	private String numCifreDecimali;

	// 13: Codice della lista di lookup associata al campo (viene mostrato solo se attributo modificabile in base ACL, anche se non editabile)
	@NumeroColonna(numero = "13")
	private String codListaLookup;

	// 14: Nomi identificativi dei campi (quello corrente ed eventuali altri dello stesso attributo strutturato) da popolare con la lookup e corrispondenti
	// indici delle colonne del clob di popolamento della lista di lookup (nel formato <ATTR_NAME_1>|-|<Indice_1>|-|.....|-|<ATTR_NAME_n>|-|<Indice_n>)
	@NumeroColonna(numero = "14")
	private String attributiIndiciDaLookup;

	// 15: Nomi identificativi dei campi (quello corrente ed eventuali altri dello stesso attributo strutturato) che servono a prefiltrare la lookup e
	// corrispondenti campi di filtro della lookup (così come sono indicati nella jsp) (nel formato
	// <ATTR_NAME_1>|-|<Filtro_1>|-|.....|-|<ATTR_NAME_n>|-|<Filtro_n>)
	@NumeroColonna(numero = "15")
	private String attributiFiltriLookup;

	// 16: Nro di riga (da 1 in su) in cui posizionare l'attributo nel fieldest di appartenenza (i.e. attributo complesso)
	@NumeroColonna(numero = "16")
	private String riga;

	// 17: End-point del web-service attraverso cui popolare la lista di possibili valori dell'attributo
	@NumeroColonna(numero = "17")
	private String endpointWS;

	// 18: Xml/input da passare al web-service per popolare la lista di possibili valori dell'attributo
	@NumeroColonna(numero = "18")
	private String xmlRequestWS;

	// 20: Mappa valori per popolare il radio con i possibili valori dell'attributo
	@NumeroColonna(numero = "20")
	private String valueMap;

	// 21: Mapping con le property dei catasti
	@NumeroColonna(numero = "21")
	private String propertyCatasti;

	// 25: Espressione regolare da aggiungere in validazione
	@NumeroColonna(numero = "25")
	private String regularExpr;

	// 26: Case in cui convertire il testo durante la digitazione (LOWER | UPPER)
	@NumeroColonna(numero = "26")
	private String textCase;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumMaxCaratteri() {
		return numMaxCaratteri;
	}

	public void setNumMaxCaratteri(String numMaxCaratteri) {
		this.numMaxCaratteri = numMaxCaratteri;
	}

	public String getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(String larghezza) {
		this.larghezza = larghezza;
	}

	public String getValoreDefault() {
		return valoreDefault;
	}

	public void setValoreDefault(String valoreDefault) {
		this.valoreDefault = valoreDefault;
	}

	public String getObbligatorio() {
		return obbligatorio;
	}

	public void setObbligatorio(String obbligatorio) {
		this.obbligatorio = obbligatorio;
	}

	public String getAltezza() {
		return altezza;
	}

	public void setAltezza(String altezza) {
		this.altezza = altezza;
	}

	public String getModificabile() {
		return modificabile;
	}

	public void setModificabile(String modificabile) {
		this.modificabile = modificabile;
	}

	public String getNumCifreDecimali() {
		return numCifreDecimali;
	}

	public void setNumCifreDecimali(String numCifreDecimali) {
		this.numCifreDecimali = numCifreDecimali;
	}

	public String getCodListaLookup() {
		return codListaLookup;
	}

	public void setCodListaLookup(String codListaLookup) {
		this.codListaLookup = codListaLookup;
	}

	public String getAttributiIndiciDaLookup() {
		return attributiIndiciDaLookup;
	}

	public void setAttributiIndiciDaLookup(String attributiIndiciDaLookup) {
		this.attributiIndiciDaLookup = attributiIndiciDaLookup;
	}

	public String getAttributiFiltriLookup() {
		return attributiFiltriLookup;
	}

	public void setAttributiFiltriLookup(String attributiFiltriLookup) {
		this.attributiFiltriLookup = attributiFiltriLookup;
	}

	public String getRiga() {
		return riga;
	}

	public void setRiga(String riga) {
		this.riga = riga;
	}

	public String getEndpointWS() {
		return endpointWS;
	}

	public void setEndpointWS(String endpointWS) {
		this.endpointWS = endpointWS;
	}

	public String getXmlRequestWS() {
		return xmlRequestWS;
	}

	public void setXmlRequestWS(String xmlRequestWS) {
		this.xmlRequestWS = xmlRequestWS;
	}

	public String getValueMap() {
		return valueMap;
	}

	public void setValueMap(String valueMap) {
		this.valueMap = valueMap;
	}

	public String getPropertyCatasti() {
		return propertyCatasti;
	}

	public void setPropertyCatasti(String propertyCatasti) {
		this.propertyCatasti = propertyCatasti;
	}

	public String getRegularExpr() {
		return regularExpr;
	}

	public void setRegularExpr(String regularExpr) {
		this.regularExpr = regularExpr;
	}

	public String getTextCase() {
		return textCase;
	}

	public void setTextCase(String textCase) {
		this.textCase = textCase;
	}

}
