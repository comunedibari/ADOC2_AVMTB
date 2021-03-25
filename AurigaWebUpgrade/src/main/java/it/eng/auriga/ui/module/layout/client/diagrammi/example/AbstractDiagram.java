package it.eng.auriga.ui.module.layout.client.diagrammi.example;

import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;

public abstract class AbstractDiagram {

	protected DiagramController controller;

	public abstract String getName();

	public abstract String getDescription();

	public abstract void draw();

	public void setDiagramController(DiagramController controller) {
		this.controller = controller;
	}

	public Widget asWidget() {
		return controller.getView();
	}

}
