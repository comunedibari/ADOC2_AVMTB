package it.eng.aurigamailbusiness.bean.restrepresentation;

public class EmailMetadataOrdering {
	
	private EmailMetadata intestazioneColonna;
	private boolean discendente;
	
	public EmailMetadata getIntestazioneColonna() {
		return intestazioneColonna;
	}
	public void setIntestazioneColonna(EmailMetadata intestazioneColonna) {
		this.intestazioneColonna = intestazioneColonna;
	}
	
	public boolean isDiscendente() {
		return discendente;
	}
	public void setDiscendente(boolean discendente) {
		this.discendente = discendente;
	}
	
}
