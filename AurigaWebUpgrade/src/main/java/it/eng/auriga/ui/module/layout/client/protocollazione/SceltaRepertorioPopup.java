package it.eng.auriga.ui.module.layout.client.protocollazione;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

public class SceltaRepertorioPopup extends Window {

	private SceltaRepertorioPopup instance;
	private SceltaRepertorioForm form;

	public SceltaRepertorioPopup(String flgTipoProv, String repertorioDefault, Record[] listaRepertori, final ServiceCallback<Record> callback) {

		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona repertorio");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);

		form = new SceltaRepertorioForm(flgTipoProv, repertorioDefault, listaRepertori, instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);

		addItem(form);

		setShowTitle(true);
		setHeaderIcon("blank.png");
	}
}