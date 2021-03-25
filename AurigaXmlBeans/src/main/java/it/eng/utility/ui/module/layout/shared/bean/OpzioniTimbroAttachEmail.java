package it.eng.utility.ui.module.layout.shared.bean;

import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.MappaParametri;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PosizioneRispettoAlTimbro;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TestoTimbro;
import it.eng.fileOperation.clientws.TipoRotazione;

public class OpzioniTimbroAttachEmail {

	private Integer timeout;
	private CodificaTimbro codifica;
	private TestoTimbro testoTimbro;
	private TipoRotazione rotazioneTimbro;
	private TestoTimbro intestazioneTimbro;
	private PosizioneTimbroNellaPagina posizioneTimbro;
	private PosizioneRispettoAlTimbro posizioneTestoInChiaro;
	private PosizioneRispettoAlTimbro posizioneIntestazione;
	private boolean timbroSingolo;
	private PaginaTimbro paginaTimbro;
	private MappaParametri mappaParametri;
	private boolean righeMultiple;
	
	public CodificaTimbro getCodifica() {
		return codifica;
	}
	public void setCodifica(CodificaTimbro codifica) {
		this.codifica = codifica;
	}
	public TestoTimbro getTestoTimbro() {
		return testoTimbro;
	}
	public void setTestoTimbro(TestoTimbro testoTimbro) {
		this.testoTimbro = testoTimbro;
	}
	public TipoRotazione getRotazioneTimbro() {
		return rotazioneTimbro;
	}
	public void setRotazioneTimbro(TipoRotazione rotazioneTimbro) {
		this.rotazioneTimbro = rotazioneTimbro;
	}
	public TestoTimbro getIntestazioneTimbro() {
		return intestazioneTimbro;
	}
	public void setIntestazioneTimbro(TestoTimbro intestazioneTimbro) {
		this.intestazioneTimbro = intestazioneTimbro;
	}
	public PosizioneTimbroNellaPagina getPosizioneTimbro() {
		return posizioneTimbro;
	}
	public void setPosizioneTimbro(PosizioneTimbroNellaPagina posizioneTimbro) {
		this.posizioneTimbro = posizioneTimbro;
	}
	public PosizioneRispettoAlTimbro getPosizioneTestoInChiaro() {
		return posizioneTestoInChiaro;
	}
	public void setPosizioneTestoInChiaro(
			PosizioneRispettoAlTimbro posizioneTestoInChiaro) {
		this.posizioneTestoInChiaro = posizioneTestoInChiaro;
	}
	public PosizioneRispettoAlTimbro getPosizioneIntestazione() {
		return posizioneIntestazione;
	}
	public void setPosizioneIntestazione(
			PosizioneRispettoAlTimbro posizioneIntestazione) {
		this.posizioneIntestazione = posizioneIntestazione;
	}
	public boolean isTimbroSingolo() {
		return timbroSingolo;
	}
	public void setTimbroSingolo(boolean timbroSingolo) {
		this.timbroSingolo = timbroSingolo;
	}
	public PaginaTimbro getPaginaTimbro() {
		return paginaTimbro;
	}
	public void setPaginaTimbro(PaginaTimbro paginaTimbro) {
		this.paginaTimbro = paginaTimbro;
	}
	public MappaParametri getMappaParametri() {
		return mappaParametri;
	}
	public void setMappaParametri(MappaParametri mappaParametri) {
		this.mappaParametri = mappaParametri;
	}
	public boolean isRigheMultiple() {
		return righeMultiple;
	}
	public void setRigheMultiple(boolean righeMultiple) {
		this.righeMultiple = righeMultiple;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
}
