package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class RuoliScrivaniaAttiCompletaItem extends ReplicableItem {

	private String idSvFieldName;
	private String idSvFromLoadDettFieldName;
	private String codUoFieldName;
	private String descrizioneFieldName;
	private String flgFirmatarioFieldName;
	private String motiviFieldName;
	private String flgRiacqVistoInRitornoIterFieldName;
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName) {
		this(idSvFieldName, idSvFromLoadDettFieldName, codUoFieldName, descrizioneFieldName, null, null);
	}
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName, String flgFirmatarioFieldName) {
		this(idSvFieldName, idSvFromLoadDettFieldName, codUoFieldName, descrizioneFieldName, flgFirmatarioFieldName, null);
	}
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName, String flgFirmatarioFieldName, String motiviFieldName) {
		this(idSvFieldName, idSvFromLoadDettFieldName, codUoFieldName, descrizioneFieldName, flgFirmatarioFieldName, motiviFieldName, null);
	}
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName, String flgFirmatarioFieldName, String motiviFieldName, String flgRiacqVistoInRitornoIterFieldName) {
		this.setIdSvFieldName(idSvFieldName);
		this.setIdSvFromLoadDettFieldName(idSvFromLoadDettFieldName);
		this.setCodUoFieldName(codUoFieldName);
		this.setDescrizioneFieldName(descrizioneFieldName);
		this.setFlgFirmatarioFieldName(flgFirmatarioFieldName);
		this.setMotiviFieldName(motiviFieldName);
		this.setFlgRiacqVistoInRitornoIterFieldName(flgRiacqVistoInRitornoIterFieldName);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		RuoliScrivaniaAttiCompletaCanvas lRuoliScrivaniaAttiCompletaCanvas = new RuoliScrivaniaAttiCompletaCanvas(this);
		return lRuoliScrivaniaAttiCompletaCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public String getIdUdAttoDaAnn() {
		return null;
	}
	
	public boolean selectUniqueValueAfterChangedParams() {
		return false;
	}

	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((RuoliScrivaniaAttiCompletaCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
	public Boolean getFlgAttoMeroIndirizzo() {
		return null;
	}

	public boolean showFlgFirmatario() {
		return false;
	}
	
	public boolean showMotivi() {
		return false;
	}
	
	public boolean isRequiredMotivi() {
		return false;
	}
	
	public boolean showFlgRiacqVistoInRitornoIter() {
		return false;
	}
	
	public void manageChangedScrivaniaSelezionata() {
		
	}
	
	public List<FormItem> getCustomItems() {
		return new ArrayList<FormItem>();
	}

	public String getIdSvFieldName() {
		return idSvFieldName;
	}

	public void setIdSvFieldName(String idSvFieldName) {
		this.idSvFieldName = idSvFieldName;
	}
	
	public String getIdSvFromLoadDettFieldName() {
		return idSvFromLoadDettFieldName;
	}
	
	public void setIdSvFromLoadDettFieldName(String idSvFromLoadDettFieldName) {
		this.idSvFromLoadDettFieldName = idSvFromLoadDettFieldName;
	}
	
	public String getCodUoFieldName() {
		return codUoFieldName;
	}

	public void setCodUoFieldName(String codUoFieldName) {
		this.codUoFieldName = codUoFieldName;
	}

	public String getDescrizioneFieldName() {
		return descrizioneFieldName;
	}

	public void setDescrizioneFieldName(String descrizioneFieldName) {
		this.descrizioneFieldName = descrizioneFieldName;
	}
	
	public String getFlgFirmatarioFieldName() {
		return flgFirmatarioFieldName;
	}

	public void setFlgFirmatarioFieldName(String flgFirmatarioFieldName) {
		this.flgFirmatarioFieldName = flgFirmatarioFieldName;
	}

	public String getMotiviFieldName() {
		return motiviFieldName;
	}

	public void setMotiviFieldName(String motiviFieldName) {
		this.motiviFieldName = motiviFieldName;
	}

	public String getFlgRiacqVistoInRitornoIterFieldName() {
		return flgRiacqVistoInRitornoIterFieldName;
	}

	public void setFlgRiacqVistoInRitornoIterFieldName(String flgRiacqVistoInRitornoIterFieldName) {
		this.flgRiacqVistoInRitornoIterFieldName = flgRiacqVistoInRitornoIterFieldName;
	}
	
}
