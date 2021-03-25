package it.eng.auriga.ui.module.layout.client.gestioneatti;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class SmistamentoAttiItem extends ReplicableItem {

	private String idProcedimento;

	public SmistamentoAttiItem(String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("idProcedimento", idProcedimento);
		SmistamentoAttiCanvas lSmistamentoAttiCanvas = new SmistamentoAttiCanvas(this, params);
		return lSmistamentoAttiCanvas;
	}

	@Override
	protected ImgButton[] createAddButtons() {
		
		ImgButton[] addButtons = new ImgButton[2];

		addButtons[0] = new ImgButton();
		addButtons[0].setSrc("[SKIN]actions/add.png");
		addButtons[0].setShowDown(false);
		addButtons[0].setShowRollOver(false);
		addButtons[0].setSize(16);
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();
			}
		});

		addButtons[1] = new ImgButton();
		addButtons[1].setSrc("lookup/organigrammamulti.png");
		addButtons[1].setShowDown(false);
		addButtons[1].setShowRollOver(false);
		addButtons[1].setSize(16);
		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupOrganigrammaButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SmistamentoAttiMultiLookupOrganigramma lookupOrganigrammaPopup = new SmistamentoAttiMultiLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});

		return addButtons;
	}

	public class SmistamentoAttiMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<SmistamentoAttiCanvas> multiLookupList = new ArrayList<SmistamentoAttiCanvas>();

		public SmistamentoAttiMultiLookupOrganigramma(Record record) {
			super(record, false, new Integer(1)); // includo sia UO che SV
		}

		@Override
		public void manageLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			SmistamentoAttiCanvas lastCanvas = (SmistamentoAttiCanvas) getLastCanvas();
			if (lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecord(record);
				multiLookupList.add(lastCanvas);
			} else {
				SmistamentoAttiCanvas canvas = (SmistamentoAttiCanvas) onClickNewButton();
				canvas.setFormValuesFromRecord(record);
				multiLookupList.add(canvas);
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			for (SmistamentoAttiCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if (values.getAttribute("organigramma").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}

		@Override
		public String getFinalita() {
			return "SMIST_PROC";
		}

	}

	@Override
	public Record getCanvasDefaultRecord() {
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			Record lRecord = new Record();
			lRecord.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			return lRecord;
		}
		return null;
	}

}
