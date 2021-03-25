package it.eng.auriga.ui.module.layout.client.archivio;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

import com.smartgwt.client.widgets.ImgButton;

public abstract class TaskItem extends ReplicableItem{
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		TaskCanvas lTaskCanvas = new TaskCanvas();
		return lTaskCanvas;
	}
	
	@Override
	protected ImgButton[] createAddButtons() {
		return new ImgButton[]{};	
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(false);
	}

	public abstract void reloadTask();
	
}