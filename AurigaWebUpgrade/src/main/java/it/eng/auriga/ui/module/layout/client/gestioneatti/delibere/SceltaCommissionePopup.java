package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

/**
 * 
 * @author DANCRIST
 *
 */

public class SceltaCommissionePopup extends Window {

	private SceltaCommissionePopup instance;
	private SceltaCommissioneForm form;

	public SceltaCommissionePopup(Record[] listaCommissioni, final ServiceCallback<Record> callback) {

		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona commissione/i da convocare");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);

		form = new SceltaCommissioneForm(listaCommissioni, instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);

		addItem(form);

		setShowTitle(true);
		setHeaderIcon("menu/commissione.png");
		
		show();
	}
}