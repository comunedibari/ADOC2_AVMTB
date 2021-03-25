package it.eng.utility.email.operation.impl.interoperation;


/**
 * Enumeration contenente i tipi di messaggi interoperabili
 * @author michele
 *
 */
public enum InteroperabilitaType {
	
	SEGNATURA("Segnatura","Segnatura.xml"),
	CONFERMA_RICEZIONE("ConfermaRicezione","Conferma.xml"),
	AGGIORNAMENTO_CONFERMA("AggiornamentoConferma","Aggiornamento.xml"),
	NOTIFICA_ECCEZIONE("NotificaEccezione","Eccezione.xml"),
	ANNULLAMENTO_PROTOCOLLAZIONE("AnnullamentoProtocollazione","Annullamento.xml");

	
	private String name;
	private String filename;
	
	private InteroperabilitaType(String name,String filename){
		this.name = name;
		this.filename = filename;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public static InteroperabilitaType getValueForFileName(String filename,boolean casesensitive){
		for(InteroperabilitaType intertype:InteroperabilitaType.values()){
			if(casesensitive){
				if(intertype.filename.equals(filename)){
					return intertype;
				}	
			}else{
				if(intertype.filename.equalsIgnoreCase(filename)){
					return intertype;
				}
			}			
		}
		return null;
	}
	
}