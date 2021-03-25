package it.eng.auriga.ui.module.layout.client.wizard;

import it.eng.auriga.ui.module.layout.client.protocollazione.AbstractPreviewCanvas;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class TreeViewer extends HLayout implements AbstractPreviewCanvas {

	@Override
	public void finishLoad(VLayout lVLayout) {
		setAlign(Alignment.CENTER);
		setMembers(lVLayout);
		markForRedraw();
	}

}
