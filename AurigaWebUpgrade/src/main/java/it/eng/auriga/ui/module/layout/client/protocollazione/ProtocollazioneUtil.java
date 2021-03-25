package it.eng.auriga.ui.module.layout.client.protocollazione;

import java.util.Map;

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

public class ProtocollazioneUtil {
			
	public static ProtocollazioneDetailEntrata buildProtocollazioneDetailEntrata(Record recordDettaglio) {
		return buildProtocollazioneDetailEntrata(recordDettaglio, null);
	}
	
	public static ProtocollazioneDetailEntrata buildProtocollazioneDetailEntrata(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailEntrata(recordDettaglio, afterRegistraCallback, null);
	}

	public static ProtocollazioneDetailEntrata buildProtocollazioneDetailEntrata(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		if (recordDettaglio != null && recordDettaglio.getAttributeAsBoolean("protocolloAccessoAttiSueDaEmail")) {
			return new ProtocollazioneDetailAccessoAttiSueDaEmail("protocollazione_accesso_atti_sue") {
				
				@Override
				public boolean detailSectionCanaleDataRicezioneToShowOpen() {
					return false;
				}
				
				@Override
				public boolean detailSectionSupportoOriginaleToShowOpen() {
					return false;
				}
				
				@Override
				public boolean isModalitaWizard() {
					return false;
				}
				
				@Override
				public boolean isModalitaAllegatiGrid() {
					return isAttivaModalitaAllegatiGrid(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionPubblicazione() {
					return isPresentePubblicazione(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionRipubblicazione() {
					return isPresenteRipubblicazione(recordDettaglio);
				}
				
				@Override
				protected void afterRegistra(Record recordCallback) {
					EditaProtocolloWindowFromMail lEditaProtocolloWindowFromMail = getEditaProtocolloWindowFromMail();
					if (lEditaProtocolloWindowFromMail != null) {
						lEditaProtocolloWindowFromMail.archiviaMailFromProtocollazioneAccessoAttiSue(recordDettaglio);
					}
					if (afterRegistraCallback != null) {
						afterRegistraCallback.execute(recordCallback);
					}
				}
				
				@Override
				protected void afterUpdate(Record recordCallback) {
					if (afterUpdateCallback != null) {
						afterUpdateCallback.execute(recordCallback);
					}
				}
	
//				@Override
//				protected void afterRegistra(Record recordCallback) {
//					EditaProtocolloWindowFromMail modalWindow = getEditaProtocolloWindowFromMailwindow();
//					if (getDettaglioPostaElettronica() != null) {
//						// Ho chiamato la protocollazione sue da dettaglio mail
//						if (modalWindow != null) {
//							modalWindow.markForDestroy();
//						}
//						final DettaglioPostaElettronica dettaglioPostaElettronica = getDettaglioPostaElettronica();
//						if (dettaglioPostaElettronica != null) {
//							SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
//								
//								@Override
//								public void execute(Boolean value) {
//									if (value) {
//										dettaglioPostaElettronica.actionArchiviaMail();
//									}
//								}
//							});
//						}
////						if (dettaglioPostaElettronica != null && dettaglioPostaElettronica.getLayout() != null) {
////							dettaglioPostaElettronica.getLayout().doSearch();
////							dettaglioPostaElettronica.markForDestroy();
////						}
//					} else if (modalWindow != null && modalWindow.externalLayout){
//					if (modalWindow != null) {
//						modalWindow.markForDestroy();
//						if (dettaglioPostaElettronica != null) {
//							SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
//								
//								@Override
//								public void execute(Boolean value) {
//									if (value) {
//										dettaglioPostaElettronica.actionArchiviaMail();
//									}
//									modalWindow.manageAfterCloseWindow();
//								}
//							});
//						}
//						modalWindow.manageAfterCloseWindow();
//					}
//				}
				
				// Metodo per testare il reload dopo la protocollazione ma senza protocollare
//				@Override
//				public void clickSalvaRegistra() {
//					EditaProtocolloWindowFromMail modalWindow = getEditaProtocolloWindowFromMailwindow();
//					if (modalWindow != null) {
//						modalWindow.archiviaMailFromProtocollazioneAccessoAttiSue(recordDettaglio);
//					}
					
					
//					EditaProtocolloWindowFromMail modalWindow = getEditaProtocolloWindowFromMailwindow();
//					if (getDettaglioPostaElettronica() != null) {
//						if (modalWindow != null) {
//							modalWindow.markForDestroy();
//						}
//						DettaglioPostaElettronica dettaglioPostaElettronica = getDettaglioPostaElettronica();
//						if (dettaglioPostaElettronica != null && dettaglioPostaElettronica.getLayout() != null) {
//							dettaglioPostaElettronica.getLayout().doSearch();
//							dettaglioPostaElettronica.markForDestroy();
//						}
//					}
//					if (modalWindow != null) {
//						modalWindow.markForDestroy();
//						modalWindow.manageAfterCloseWindow();
//					}
//				}
				
			};
		} else {
			return new ProtocollazioneDetailEntrata("protocollazione_entrata") {
				
				@Override
				public boolean isModalitaWizard() {
					return isAttivoProtocolloWizard(recordDettaglio);
				}
				
				@Override
				public boolean isModalitaAllegatiGrid() {
					return isAttivaModalitaAllegatiGrid(recordDettaglio);
				}

				@Override
				public boolean showDetailSectionPubblicazione() {
					return isPresentePubblicazione(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionRipubblicazione() {
					return isPresenteRipubblicazione(recordDettaglio);
				}
	
				@Override
				protected void afterRegistra(Record recordCallback) {
					if (afterRegistraCallback != null) {
						afterRegistraCallback.execute(recordCallback);
					}
				}
				
				@Override
				protected void afterUpdate(Record recordCallback) {
					if (afterUpdateCallback != null) {
						afterUpdateCallback.execute(recordCallback);
					}
				}
			};
		}
	}
	
	public static ProtocollazioneDetailUscita buildProtocollazioneDetailUscita(Record recordDettaglio) {
		return buildProtocollazioneDetailUscita(recordDettaglio, null);
	}

	public static ProtocollazioneDetailUscita buildProtocollazioneDetailUscita(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailUscita(recordDettaglio, afterRegistraCallback, null);
	}
	
	public static ProtocollazioneDetailUscita buildProtocollazioneDetailUscita(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		
		return new ProtocollazioneDetailUscita("protocollazione_uscita") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}

			@Override
			protected void afterRegistra(Record recordCallback) {
				if (afterRegistraCallback != null) {
					afterRegistraCallback.execute(recordCallback);
				}
			}
			
			@Override
			protected void afterUpdate(Record recordCallback) {
				if (afterUpdateCallback != null) {
					afterUpdateCallback.execute(recordCallback);
				}
			}
		};
	}
	
	public static ProtocollazioneDetailInterna buildProtocollazioneDetailInterna(Record recordDettaglio) {
		return buildProtocollazioneDetailInterna(recordDettaglio, null);
	}

	public static ProtocollazioneDetailInterna buildProtocollazioneDetailInterna(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailInterna(recordDettaglio, afterRegistraCallback, null);
	}

	public static ProtocollazioneDetailInterna buildProtocollazioneDetailInterna(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		
		return new ProtocollazioneDetailInterna("protocollazione_interna") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}

			@Override
			protected void afterRegistra(Record recordCallback) {
				if (afterRegistraCallback != null) {
					afterRegistraCallback.execute(recordCallback);
				}
			}
			
			@Override
			protected void afterUpdate(Record recordCallback) {
				if (afterUpdateCallback != null) {
					afterUpdateCallback.execute(recordCallback);
				}
			}
		};
	}
	
	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(Record recordDettaglio) {
		return buildProtocollazioneDetailBozze(recordDettaglio, null, null, null);
	}
	
	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(Record recordDettaglio, Map<String, Object> params) {
		return buildProtocollazioneDetailBozze(recordDettaglio, params, null, null);
	}

	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(final Record recordDettaglio, final Map<String, Object> params, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailBozze(recordDettaglio, params, afterRegistraCallback, null);
	}
			
	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(final Record recordDettaglio, final Map<String, Object> params, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		
		return new ProtocollazioneDetailBozze("protocollazione_bozze") {
			
			@Override
			public String getFlgTipoProv() {
				if(params != null) {
					return (String) params.get("flgTipoProv");
				} else if(recordDettaglio != null) {
					return recordDettaglio.getAttributeAsString("flgTipoProv");
				}				
				return null;
			}
			
			public Boolean getFlgTipoDocConVie() {
				if(params != null && params.get("flgTipoDocConVie") != null) {
					return (Boolean) params.get("flgTipoDocConVie");
				} else if(recordDettaglio != null) {
					return recordDettaglio.getAttributeAsBoolean("flgTipoDocConVie");
				}				
				return null;
			}
			
			public Boolean getFlgOggettoNonObblig() {
				if(params != null && params.get("flgOggettoNonObblig") != null) {
					return (Boolean) params.get("flgOggettoNonObblig");
				} else if(recordDettaglio != null) {
					return recordDettaglio.getAttributeAsBoolean("flgOggettoNonObblig");
				}				
				return null;
			}
				
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}

			@Override
			protected void afterRegistra(Record recordCallback) {
				if (afterRegistraCallback != null) {
					afterRegistraCallback.execute(recordCallback);
				}
			}
			
			@Override
			protected void afterUpdate(Record recordCallback) {
				if (afterUpdateCallback != null) {
					afterUpdateCallback.execute(recordCallback);
				} 
			}
		};		
	}
	
	public static ProtocollazioneDetail buildRepertorioDetail(String flgTipoProv, Record recordDettaglio) {
		if("E".equals(flgTipoProv))
			return buildRepertorioDetailEntrata(recordDettaglio, null);
		else if("I".equals(flgTipoProv))
			return buildRepertorioDetailInterno(recordDettaglio, null);
		else if("U".equals(flgTipoProv))
			return buildRepertorioDetailUscita(recordDettaglio, null);
		return null;
	}
	
	public static RepertorioDetailEntrata buildRepertorioDetailEntrata(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return new RepertorioDetailEntrata("repertorio_entrata") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
		};	
	}

	public static RepertorioDetailInterno buildRepertorioDetailInterno(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return new RepertorioDetailInterno("repertorio_interno") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
		};	
	}
	
	public static RepertorioDetailUscita buildRepertorioDetailUscita(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return new RepertorioDetailUscita("repertorio_uscita") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
		};	
	}
	
	public static boolean isAttivoProtocolloWizard(Record recordDettaglio) {
		boolean isIdUdValorizzato = recordDettaglio != null && recordDettaglio.getAttribute("idUd") != null && !"".equals(recordDettaglio.getAttribute("idUd"));
		boolean isSupportoOriginaleValorizzato = recordDettaglio != null && recordDettaglio.getAttribute("supportoOriginale") != null && !"".equals(recordDettaglio.getAttribute("supportoOriginale"));
		// con la preference ottimizzata se è una nuova protocollazione appare la maschera normale, non wizard, e il supporto è digitale (anche se non si vede)
		if(!isIdUdValorizzato && !AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_PROT") && AurigaLayout.getAttivaProtOttimizzataAllegati()) {
			return false;
		}	
		// altrimenti se è una nuova protocollazione o il supporto originale e valorizzato apro la modalita wizard, se è attiva
		if(!isIdUdValorizzato || isSupportoOriginaleValorizzato) {
			return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PROTOCOLLO_WIZARD");
		}
		return false;
	}

	public static boolean isAttivaModalitaAllegatiGrid(Record recordDettaglio) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_PROT")) {
			return true;
		} else if(AurigaLayout.getAttivaProtOttimizzataAllegati()) {
			return true;
		}		
		if(AurigaLayout.getParametroDB("NRO_ALLEGATI_PROT_X_MODALITA_GRID") != null && !"".equals(AurigaLayout.getParametroDB("NRO_ALLEGATI_PROT_X_MODALITA_GRID"))) {	
			int nroAllegatiProtXModalitaGrid = Integer.parseInt(AurigaLayout.getParametroDB("NRO_ALLEGATI_PROT_X_MODALITA_GRID"));
			if(recordDettaglio != null && nroAllegatiProtXModalitaGrid > 0 && recordDettaglio.getAttributeAsRecordList("listaAllegati") != null && recordDettaglio.getAttributeAsRecordList("listaAllegati").getLength() > nroAllegatiProtXModalitaGrid) {
				return true;
			}
		}			
		return false;
	}
	
	public static boolean isPresentePubblicazione(Record recordDettaglio) {
		if(recordDettaglio != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni") != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni")) {
			if(recordDettaglio.getAttribute("nroPubblicazione") != null && !"".equals(recordDettaglio.getAttribute("nroPubblicazione"))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isPresenteRipubblicazione(Record recordDettaglio) {
		if(recordDettaglio != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni") != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni")) {
			if(recordDettaglio.getAttribute("nroRipubblicazione") != null && !"".equals(recordDettaglio.getAttribute("nroRipubblicazione"))) {
				return true;
			}			
		}
		return false;
	}
	
	public static boolean isAttivoEsibenteSenzaWizard() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESIBENTE_SENZA_WIZARD");
	}
	
	public static boolean isAttivoInteressatiSenzaWizard() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_INTERESSATI_SENZA_WIZARD");
	}
	
	public static boolean isAttivoAltreVieSenzaWizard() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIE_SENZA_WIZARD");
	}
	
}
