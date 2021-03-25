package it.eng.auriga.ui.module.layout.client.etichetta;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

public class EtichettaWindow extends ModalWindow{
	
	private EtichettaWindow window;
	private EtichettaDetail layout;

	public EtichettaWindow(String nomeEntita, boolean isJustWindow) {
		super(nomeEntita, isJustWindow);
		setTitle("Stampa Etichetta");  	
		setIcon("file/timbra.gif");
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		layout = new EtichettaDetail(nomeEntita, this);
		
		layout.setHeight100();
		layout.setWidth100();

		setBody(layout);		

		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				window.markForDestroy();
			}
		});
		setWidth(500);
		setHeight(300);
	}

}
