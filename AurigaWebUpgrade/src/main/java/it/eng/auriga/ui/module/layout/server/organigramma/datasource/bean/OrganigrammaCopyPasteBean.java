package it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean;

public class OrganigrammaCopyPasteBean {

	private OrganigrammaTreeNodeBean copyNode;
	private OrganigrammaTreeNodeBean pasteNode;
	public OrganigrammaTreeNodeBean getCopyNode() {
		return copyNode;
	}
	public void setCopyNode(OrganigrammaTreeNodeBean copyNode) {
		this.copyNode = copyNode;
	}
	public OrganigrammaTreeNodeBean getPasteNode() {
		return pasteNode;
	}
	public void setPasteNode(OrganigrammaTreeNodeBean pasteNode) {
		this.pasteNode = pasteNode;
	}
	
}
