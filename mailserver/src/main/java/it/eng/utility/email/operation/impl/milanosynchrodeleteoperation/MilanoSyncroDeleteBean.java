package it.eng.utility.email.operation.impl.milanosynchrodeleteoperation;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MilanoSyncroDeleteBean {

	private Boolean deleteok;

	public Boolean getDeleteok() {
		return deleteok;
	}

	public void setDeleteok(Boolean deleteok) {
		this.deleteok = deleteok;
	}
}
