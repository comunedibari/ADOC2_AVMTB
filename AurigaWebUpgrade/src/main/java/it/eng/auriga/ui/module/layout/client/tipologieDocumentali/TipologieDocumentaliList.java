package it.eng.auriga.ui.module.layout.client.tipologieDocumentali;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author cristiano
 *
 */
public class TipologieDocumentaliList extends CustomList {

	private ListGridField idTipoDoc;
	private ListGridField nome;
	private ListGridField descrizione;
	private ListGridField nomeDocTypeGen;
	private ListGridField periodoConserv;
	private ListGridField descrSuppConserv;
	private ListGridField specAccess;
	private ListGridField specRiprod;
	private ListGridField annotazioni;
	private ListGridField valido;
	private ListGridField creataIl;
	private ListGridField creataDa;
	private ListGridField ultAggiorn;
	private ListGridField ultAggiornEffeDa;
	private ListGridField flgRichAbilVis;
	private ListGridField flgAbilLavor;
	private ListGridField flgAbilUtilizzo;
	private ListGridField flgAbilFirma;
	private boolean tipologieDocAbilitate;
	private String opzioniAbil;
	

	public TipologieDocumentaliList(String nomeEntita,String tipologieDocAbilitate, String opzioniAbil) {
		super(nomeEntita);
		
		this.tipologieDocAbilitate = tipologieDocAbilitate != null && "1".equals(tipologieDocAbilitate) ? true : false;
		
		this.opzioniAbil = opzioniAbil;
		

		idTipoDoc = new ListGridField("idTipoDoc", I18NUtil.getMessages().tipologieDocumentali_idTipoDoc_list());

		nome = new ListGridField("nome", I18NUtil.getMessages().tipologieDocumentali_nome_list());

		descrizione = new ListGridField("descrizione", I18NUtil.getMessages().tipologieDocumentali_descrizione_list());

		nomeDocTypeGen = new ListGridField("nomeDocTypeGen", I18NUtil.getMessages().tipologieDocumentali_sottoTipoDi_list());

		periodoConserv = new ListGridField("periodoConserv", I18NUtil.getMessages().tipologieDocumentali_periodoConserv_list());

		descrSuppConserv = new ListGridField("descrSuppConserv", I18NUtil.getMessages().tipologieDocumentali_descrSuppConserv_list());

		specAccess = new ListGridField("specAccess", I18NUtil.getMessages().tipologieDocumentali_specAccess_list());

		specRiprod = new ListGridField("specRiprod", I18NUtil.getMessages().tipologieDocumentali_specRiprod_list());

		annotazioni = new ListGridField("annotazioni", I18NUtil.getMessages().tipologieDocumentali_annotazioni_list());

		valido = buildFlagIconField("valido", I18NUtil.getMessages().tipologieDocumentali_valido_list(), "Valido", "true", "ok.png");

		creataIl = new ListGridField("creataIl", I18NUtil.getMessages().tipologieDocumentali_creataIl_list());
		creataIl.setType(ListGridFieldType.DATE);
		creataIl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		creataDa = new ListGridField("creataDa", I18NUtil.getMessages().tipologieDocumentali_creataDa_list());

		ultAggiorn = new ListGridField("ultAggiorn", I18NUtil.getMessages().tipologieDocumentali_ultAggiorn_list());
		ultAggiorn.setType(ListGridFieldType.DATE);
		ultAggiorn.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		ultAggiornEffeDa = new ListGridField("ultAggiornEffeDa", I18NUtil.getMessages().tipologieDocumentali_ultAggiornEffeDa_list());

		flgRichAbilVis = buildFlagIconField("flgRichAbilVis", I18NUtil.getMessages().tipologieDocumentali_detail_flgRichAbilVis(), I18NUtil.getMessages()
				.tipologieDocumentali_detail_flgRichAbilVis(), "true", "ok.png");

		flgAbilLavor = buildFlagIconField("flgAbilLavor", I18NUtil.getMessages().tipologieDocumentali_detail_flgRichAbilXGestIn(), I18NUtil.getMessages()
				.tipologieDocumentali_detail_flgRichAbilXGestIn(), "true", "ok.png");

		flgAbilUtilizzo = buildFlagIconField("flgAbilUtilizzo", I18NUtil.getMessages().tipologieDocumentali_detail_flgRichAbilXAssegnIn(), I18NUtil
				.getMessages().tipologieDocumentali_detail_flgRichAbilXAssegnIn(), "true", "ok.png");

		flgAbilFirma = buildFlagIconField("flgAbilFirma", I18NUtil.getMessages().tipologieDocumentali_detail_flgAbilFirma(), I18NUtil.getMessages()
				.tipologieDocumentali_detail_flgAbilFirma(), "true", "ok.png");

		setFields(idTipoDoc, nome, descrizione, nomeDocTypeGen, periodoConserv, descrSuppConserv, specAccess, specRiprod, annotazioni, valido, creataIl,
				creataDa, ultAggiorn, ultAggiornEffeDa, flgRichAbilVis, flgAbilLavor, flgAbilUtilizzo, flgAbilFirma);

	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {

		return TipologieDocumentaliLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {

		return TipologieDocumentaliLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		return TipologieDocumentaliLayout.isRecordAbilToMod(flgDiSistema);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		final boolean flgValido = record.getAttribute("valido") != null && record.getAttribute("valido").equals("true") ? true : false;
		return TipologieDocumentaliLayout.isRecordAbilToDel(flgValido, flgDiSistema);
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	public ListGridField buildFlagIconField(final String name, final String title, final String hover, final String trueValue, final String icon) {

		ListGridField flagIconField = new ListGridField(name, title);
		flagIconField.setAlign(Alignment.CENTER);
		flagIconField.setAttribute("custom", true);
		flagIconField.setShowHover(true);
		flagIconField.setType(ListGridFieldType.ICON);
		flagIconField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value != null && value.toString().equals(trueValue)) {
					return buildIconHtml(icon);
				}
				return null;
			}
		});
		flagIconField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				String realValue = record.getAttribute(name);
				if (realValue != null && realValue.equals(trueValue)) {
					return hover;
				}
				return null;
			}
		});
		return flagIconField;
	}
	
	public boolean isListaTipologieDocAbilitate() {
		return tipologieDocAbilitate;
	}
	
	public String getOpzioniAbil(){
		return opzioniAbil;
	}
}