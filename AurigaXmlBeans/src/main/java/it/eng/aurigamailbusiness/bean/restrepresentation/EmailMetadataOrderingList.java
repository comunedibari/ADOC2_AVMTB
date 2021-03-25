package it.eng.aurigamailbusiness.bean.restrepresentation;

import java.util.ArrayList;
import java.util.List;

public class EmailMetadataOrderingList {
	
	private List<EmailMetadataOrdering> metadato = new ArrayList<>(0);

	public List<EmailMetadataOrdering> getMetadato() {
		return metadato;
	}
	public void setMetadato(List<EmailMetadataOrdering> metadato) {
		this.metadato = metadato;
	}

}
