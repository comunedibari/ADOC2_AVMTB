package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class GroupReplicableItem extends ReplicableItem {
	
	private String initGroupTitle = null;
	
	public GroupReplicableItem(String title) {
		super();
		setShowTitle(false);
		setGroupTitle(title);
	}
	
	@Override
	protected VLayout creaVLayout() {
		VLayout lVLayout = super.creaVLayout();
		lVLayout.setWidth100();
		lVLayout.setPadding(11);
		lVLayout.setMargin(4);
		lVLayout.setIsGroup(true);
		lVLayout.setStyleName(it.eng.utility.Styles.detailSection);		
		lVLayout.setGroupTitle(initGroupTitle);
		return lVLayout;
	}
	
	public void setGroupTitle(String groupTitle) {
		VLayout lVLayout = getVLayout();
		if(lVLayout != null) {
			lVLayout.setGroupTitle(groupTitle);
		} else {
			initGroupTitle = groupTitle;
		}
	}

}
