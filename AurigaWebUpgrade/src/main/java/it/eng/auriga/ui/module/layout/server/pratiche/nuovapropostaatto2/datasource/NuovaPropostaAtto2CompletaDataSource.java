package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.albopretorio.bean.AlboPretorioAttachBean;
import it.eng.albopretorio.bean.FTPUploadFileBean;
import it.eng.albopretorio.bean.ProxyBean;
import it.eng.albopretorio.protocollo.CaricaDocumento;
import it.eng.albopretorio.protocollo.ElaboraResponseWS;
import it.eng.albopretorio.protocollo.FTPUploadFile;
import it.eng.albopretorio.protocollo.SetSystemProxy;
import it.eng.albopretorio.ws.DocumentoType;
import it.eng.auriga.compiler.FreeMarkerModelliUtil;
import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFindudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetlistaemendamentiBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindsoggettoinrubricaBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGetdatinuovoiterattocomecopiaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.AttributiDinamiciDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciOutputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.ConversionePdfDataSource;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.ConversionePdfBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.FileDaConvertireBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaUOBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloAttivitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ImpostazioniUnioneFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AllegatoParteIntSeparatoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AltriDirRespRegTecnicaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AssessoreBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AttoRiferimentoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CIGBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CompilaListaModelliNuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CompilaModelloNuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ConsigliereBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CoordinatoreCompCircBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DestAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DestVantaggioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteAdottanteBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteDiConcertoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteProponenteBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteRespRegTecnicaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EmendamentoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EmendamentoXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EstensoreBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespFirmatarioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespFirmatarioConMotiviBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespVistoConformitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IstruttoreBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.MovimentiContabiliSICRABean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovoAttoComeCopiaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.PeriodoPubblicazioneBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RUPCompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RdPCompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabilePEGBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabileVistiConformitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.TaskNuovaPropostaAtto2CompletaFileFirmatiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.ProtocolloDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.VisualizzaVersioniFileDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiSoggXMLIOBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.VisualizzaVersioniFileBean;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.bean.Result;
import it.eng.client.AlboPretorioAVBImpl;
import it.eng.client.AlboPretorioReggioImpl;
import it.eng.client.AurigaMailService;
import it.eng.client.DaoTRelAlboAvbVsAuriga;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkCoreFindud;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkProcessesGetdatixmodellipratica;
import it.eng.client.DmpkRepositoryGuiGetlistaemendamenti;
import it.eng.client.DmpkUtilityFindsoggettoinrubrica;
import it.eng.client.DmpkWfGetdatinuovoiterattocomecopia;
import it.eng.client.GestioneDocumenti;
import it.eng.client.ProtocollazioneProsaImpl;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.client.SalvataggioFile;
import it.eng.document.function.AllegatoVersConOmissisStoreBean;
import it.eng.document.function.bean.AssessoreProponenteBean;
import it.eng.document.function.bean.ConsigliereProponenteBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DestNotificaAttoXmlBean;
import it.eng.document.function.bean.DestinatarioAttoBean;
import it.eng.document.function.bean.DestinatarioVantaggioBean;
import it.eng.document.function.bean.DirRespRegTecnicaBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.KeyValueBean;
import it.eng.document.function.bean.MovimentiContabiliaXmlBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegNumPrincipale;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.RespDiConcertoBean;
import it.eng.document.function.bean.RespSpesaBean;
import it.eng.document.function.bean.RespVistiConformitaBean;
import it.eng.document.function.bean.ScrivaniaDirProponenteBean;
import it.eng.document.function.bean.ScrivaniaEstensoreBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.ValueBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoIn;
import it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoResponseReturn;
import it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoIn;
import it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoResponseReturn;
import it.eng.document.function.bean.alboreggio.AlboReggioAllegato;
import it.eng.document.function.bean.alboreggio.AlboReggioAtto;
import it.eng.document.function.bean.alboreggio.AlboReggioResult;
import it.eng.document.function.bean.prosa.ProsaAllegato;
import it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato;
import it.eng.document.function.bean.prosa.ProsaMittenteDestinatario;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FirmaUtility;
import it.eng.utility.PdfUtility;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "NuovaPropostaAtto2CompletaDataSource")
public class NuovaPropostaAtto2CompletaDataSource extends AbstractDataSource<NuovaPropostaAtto2CompletaBean, NuovaPropostaAtto2CompletaBean> {
	
	private static final Logger logger = Logger.getLogger(NuovaPropostaAtto2CompletaDataSource.class);
	
	public static final String _FLG_SI = "SI";
//	public static final String _FLG_SI_SENZA_VLD_RIL_IMP = "SI, ma senza movimenti contabili"; // su Milano il valore è "SI, senza validazione/rilascio impegni"
	public static final String _FLG_NO = "NO";
	
	public static final String _MANDATORY = "mandatory"; 
	public static final String _OPTIONAL = "optional";
	
	public static final String _DECORR_PUBBL_STD = "standard";
	public static final String _DECORR_PUBBL_POST = "posticipata";	
	
	public static final String _FLG_EMENDAMENTO_SU_FILE_D = "D";
	public static final String _FLG_EMENDAMENTO_SU_FILE_A = "A";	
	
	public static final String _PERMANENTE = "permanente";
	public static final String _TEMPORANEA = "temporanea";
	
	public static final String _TIPO_LUOGO_DA_TOPONOMASTICA = "da toponomastica";
	public static final String _TIPO_LUOGO_TESTO_LIBERO = "testo libero";	
	
	public static final String _OPZIONE_INDICE_CLASSIFICAZIONE_ACTA = "indice classif. esteso";
	public static final String _OPZIONE_AGGREGATO_ACTA = "aggregato";	
	
	private String idPubblicazione;
		
	public ProtocolloDataSource getProtocolloDataSource(final NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			
			@Override
			public boolean isAppendRelazioniVsUD(ProtocollazioneBean beanDettaglio) {
				return false;
			}		
			
			@Override
			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
				if(pNuovaPropostaAtto2CompletaBean != null) {
					salvaAttributiCustomProposta(pNuovaPropostaAtto2CompletaBean, pSezioneCacheAttributiDinamici);
				}
			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		lProtocolloDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di NuovaPropostaAtto2CompletaDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}
	
	public ConversionePdfDataSource getConversionePdfDataSource() {
		ConversionePdfDataSource lConversionePdfDataSource = new ConversionePdfDataSource();
		lConversionePdfDataSource.setSession(getSession());
		lConversionePdfDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lConversionePdfDataSource.setMessages(getMessages()); 		
		return lConversionePdfDataSource;
	}	
	
	public SIBDataSource getSIBDataSource() {	
		SIBDataSource lSIBDataSource = new SIBDataSource();
		lSIBDataSource.setSession(getSession());
		lSIBDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lSIBDataSource.setMessages(getMessages()); 		
		return lSIBDataSource;
	}
	
	public ContabiliaDataSource getContabiliaDataSource() {
		ContabiliaDataSource lContabiliaDataSource = new ContabiliaDataSource();
		lContabiliaDataSource.setSession(getSession());
		lContabiliaDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lContabiliaDataSource.setMessages(getMessages()); 		
		return lContabiliaDataSource;
	}	
	
	public SICRADataSource getSICRADataSource() {
		SICRADataSource lSICRADataSource = new SICRADataSource();
		lSICRADataSource.setSession(getSession());
		lSICRADataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lSICRADataSource.setMessages(getMessages()); 		
		return lSICRADataSource;
	}	
	
	public AttributiDinamiciDatasource getAttributiDinamiciDatasource() {
		AttributiDinamiciDatasource lAttributiDinamiciDatasource = new AttributiDinamiciDatasource();
		lAttributiDinamiciDatasource.setSession(getSession());
		lAttributiDinamiciDatasource.setLoginBean(AurigaUserUtil.getLoginInfo(getSession()));				
		lAttributiDinamiciDatasource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lAttributiDinamiciDatasource.setMessages(getMessages()); 		
		return lAttributiDinamiciDatasource;
	}	

	@Override
	public Map<String, String> getExtraparams() {		
		
		Map<String, String> extraparams = super.getExtraparams();
		extraparams.put("isPropostaAtto", "true");
		extraparams.put("flgSalvaOrdAllegati", "true");
		return extraparams;		
	}
	
	public boolean skipDatiContabiliAMC(NuovaPropostaAtto2CompletaBean bean) {
		
		String idUdSkipDatiContabiliAMC = ParametriDBUtil.getParametroDB(getSession(), "ID_UD_SKIP_DATI_CONTAB_AMC");
		return idUdSkipDatiContabiliAMC != null && bean.getIdUd() != null && bean.getIdUd().equals(idUdSkipDatiContabiliAMC);
	}
		
	public boolean isAttivaRequestMovimentiDaAMC(NuovaPropostaAtto2CompletaBean bean) {
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "INT_AMC_OTTIMIZZATA")) {			
			boolean isAttivaRequestMovimentiDaAMC = getExtraparams().get("flgAttivaRequestMovimentiDaAMC") != null && "true".equalsIgnoreCase(getExtraparams().get("flgAttivaRequestMovimentiDaAMC"));
			return isAttivaRequestMovimentiDaAMC;
		} else {
			/*  
			 * dopo la contabilità non devono più essere recuperati gli impegni da AMC, e quelli salvati in DB non devono essere più sovrascritti. 
			 * bisogna dare un errore bloccante quando il recupero dei dati contabili di AMC non va a buon fine
			 */
			// se l'atto risulta già firmato la situazione deve rimanere congelata a quella salvata in DB, perciò devo inibire il recupero dei dati da AMC
			boolean isAttoFirmato = bean.getInfoFilePrimario() != null && bean.getInfoFilePrimario().isFirmato();
			return !isAttoFirmato && !skipDatiContabiliAMC(bean);			
		}
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC(NuovaPropostaAtto2CompletaBean bean) {
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "INT_AMC_OTTIMIZZATA")) {		
			boolean isAttivaSalvataggioMovimentiDaAMC = getExtraparams().get("flgAttivaSalvataggioMovimentiDaAMC") != null && "true".equalsIgnoreCase(getExtraparams().get("flgAttivaSalvataggioMovimentiDaAMC"));
			return isAttivaSalvataggioMovimentiDaAMC;
		} else {
			return !skipDatiContabiliAMC(bean);			
		}
	}
	
	public String getPrefixRegNum(NuovaPropostaAtto2CompletaBean bean) {
		if (StringUtils.isNotBlank(bean.getNumeroRegistrazione())) {
			String annoRegistrazione = bean.getDataRegistrazione() != null ? new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione()) : bean.getAnnoRegistrazione();
			return bean.getSiglaRegistrazione() + "_" + bean.getNumeroRegistrazione() + "_" + annoRegistrazione + "_";					
		} else if (StringUtils.isNotBlank(bean.getNumeroRegProvvisoria())) {
			String annoRegProvvisoria = bean.getDataRegProvvisoria() != null ? new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria()) : bean.getAnnoRegProvvisoria();
			return bean.getSiglaRegProvvisoria() + "_" + bean.getNumeroRegProvvisoria() + "_" + annoRegProvvisoria + "_";	
		}
		return "";
	}
	
	@Override
	public NuovaPropostaAtto2CompletaBean get(NuovaPropostaAtto2CompletaBean bean) throws Exception {	
		
 		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		String idProcess = getExtraparams().get("idProcess");
		String taskName = getExtraparams().get("taskName");
		BigDecimal idUd = StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null;
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(getExtraparams().get("flgSoloAbilAzioni"));
		lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
		lRecuperaDocumentoInBean.setIdProcess(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
		lRecuperaDocumentoInBean.setTaskName(StringUtils.isNotBlank(taskName) ? taskName : "#NONE");
		
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		ProtocollazioneBean lProtocollazioneBean = new ProtocolloUtility(getSession()).getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());			
		
		bean.setIdUd(idUd != null ? String.valueOf(idUd.intValue()) : null);
		bean.setTipoDocumento(lProtocollazioneBean.getTipoDocumento());
		bean.setNomeTipoDocumento(lProtocollazioneBean.getNomeTipoDocumento());
		bean.setRowidDoc(lProtocollazioneBean.getRowidDoc());
		bean.setIdDocPrimario(lProtocollazioneBean.getIdDocPrimario() != null ?  String.valueOf(lProtocollazioneBean.getIdDocPrimario().longValue()) : null);
		bean.setNomeFilePrimario(lProtocollazioneBean.getNomeFilePrimario());
		bean.setUriFilePrimario(lProtocollazioneBean.getUriFilePrimario());
		bean.setRemoteUriFilePrimario(lProtocollazioneBean.getRemoteUriFilePrimario());
		bean.setInfoFilePrimario(lProtocollazioneBean.getInfoFile());
		bean.setIsChangedFilePrimario(lProtocollazioneBean.getIsDocPrimarioChanged());
		bean.setFlgDatiSensibili(lProtocollazioneBean.getFlgDatiSensibili());
		
		if(lProtocollazioneBean.getFilePrimarioOmissis() != null) {
			bean.setIdDocPrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getIdDoc());
			bean.setNomeFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getNomeFile());
			bean.setUriFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getUriFile());
			bean.setRemoteUriFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getRemoteUri());
			bean.setInfoFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getInfoFile());	
			bean.setIsChangedFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getIsChanged());
		}
		
		// Avvio revoca atto
		bean.setIdTipoProcRevocaAtto(lDocumentoXmlOutBean.getIdTipoProcRevocaAtto());
		bean.setNomeTipoProcRevocaAtto(lDocumentoXmlOutBean.getNomeTipoProcRevocaAtto());
		bean.setIdDefFlussoWFRevocaAtto(lDocumentoXmlOutBean.getIdDefFlussoWFRevocaAtto());
		bean.setIdTipoDocPropostaRevocaAtto(lDocumentoXmlOutBean.getIdTipoDocPropostaRevocaAtto());
		bean.setNomeTipoDocPropostaRevocaAtto(lDocumentoXmlOutBean.getNomeTipoDocPropostaRevocaAtto());
		bean.setSiglaPropostaRevocaAtto(lDocumentoXmlOutBean.getSiglaPropostaRevocaAtto());
				
		// Avvio emendamento		
		bean.setIdTipoProcEmendamento(lDocumentoXmlOutBean.getIdTipoProcEmendamento());
		bean.setNomeTipoProcEmendamento(lDocumentoXmlOutBean.getNomeTipoProcEmendamento());
		bean.setIdDefFlussoWFEmendamento(lDocumentoXmlOutBean.getIdDefFlussoWFEmendamento());
		bean.setIdTipoDocPropostaEmendamento(lDocumentoXmlOutBean.getIdTipoDocPropostaEmendamento());
		bean.setNomeTipoDocPropostaEmendamento(lDocumentoXmlOutBean.getNomeTipoDocPropostaEmendamento());
		bean.setSiglaPropostaEmendamento(lDocumentoXmlOutBean.getSiglaPropostaEmendamento());			
		bean.setTipoAttoRifEmendamento(lDocumentoXmlOutBean.getTipoAttoRifEmendamento());			
		bean.setSiglaAttoRifEmendamento(lDocumentoXmlOutBean.getSiglaAttoRifEmendamento());			
		bean.setNumeroAttoRifEmendamento(lDocumentoXmlOutBean.getNumeroAttoRifEmendamento());			
		bean.setAnnoAttoRifEmendamento(lDocumentoXmlOutBean.getAnnoAttoRifEmendamento());
		bean.setIdEmendamentoRif(lDocumentoXmlOutBean.getIdEmendamentoRif());			
		bean.setNumeroEmendamentoRif(lDocumentoXmlOutBean.getNumeroEmendamentoRif());			
		
		/* Dati scheda - Registrazione */
		
		if(lProtocollazioneBean.getNumeroNumerazioneSecondaria() != null) {
			// Numerazione finale
			bean.setSiglaRegistrazione(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegistrazione(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegistrazione(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegistrazione(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegistrazione(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegistrazione(lProtocollazioneBean.getDesUOProtocollo());		
			// Numerazione provvisoria
			bean.setSiglaRegProvvisoria(lProtocollazioneBean.getSiglaNumerazioneSecondaria());
			bean.setNumeroRegProvvisoria(lProtocollazioneBean.getNumeroNumerazioneSecondaria() != null ? String.valueOf(lProtocollazioneBean.getNumeroNumerazioneSecondaria().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBean.getDataRegistrazioneNumerazioneSecondaria());
			bean.setAnnoRegProvvisoria(lProtocollazioneBean.getAnnoNumerazioneSecondaria());
		} else if(lProtocollazioneBean.getCodCategoriaProtocollo() != null && "R".equals(lProtocollazioneBean.getCodCategoriaProtocollo())) {
			// Numerazione finale
			bean.setSiglaRegistrazione(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegistrazione(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegistrazione(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegistrazione(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegistrazione(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegistrazione(lProtocollazioneBean.getDesUOProtocollo());	
		} else {
			// Numerazione provvisoria
			bean.setSiglaRegProvvisoria(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegProvvisoria(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegProvvisoria(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegProvvisoria(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegProvvisoria(lProtocollazioneBean.getDesUOProtocollo());	
		}
		bean.setEstremiRepertorioPerStruttura(lProtocollazioneBean.getEstremiRepertorioPerStruttura());	
		bean.setIdUdLiquidazione(lDocumentoXmlOutBean.getIdUdLiquidazione());
		bean.setIdDocPrimarioLiquidazione(lDocumentoXmlOutBean.getIdDocPrimarioLiquidazione());
		bean.setCodTipoLiquidazioneXContabilia(lDocumentoXmlOutBean.getCodTipoLiquidazioneXContabilia());
		bean.setSiglaRegLiquidazione(lDocumentoXmlOutBean.getSiglaRegLiquidazione());
		bean.setAnnoRegLiquidazione(lDocumentoXmlOutBean.getAnnoRegLiquidazione());
		bean.setNroRegLiquidazione(lDocumentoXmlOutBean.getNroRegLiquidazione());
		bean.setDataAdozioneLiquidazione(lDocumentoXmlOutBean.getDataAdozioneLiquidazione());
		bean.setEstremiAttoLiquidazione(lDocumentoXmlOutBean.getEstremiAttoLiquidazione());
		// Archiviazione in ACTA
		bean.setEsitoInvioACTASerieAttiIntegrali(lProtocollazioneBean.getEsitoInvioACTASerieAttiIntegrali());
		bean.setEsitoInvioACTASeriePubbl(lProtocollazioneBean.getEsitoInvioACTASeriePubbl());								
		
		bean.setFlgDettRevocaAtto(lDocumentoXmlOutBean.getFlgDettRevocaAtto() == Flag.SETTED);
		bean.setIdPropostaAMC(lDocumentoXmlOutBean.getCodPropostaSistContabile());
		
		/* Dati scheda - Dati di pubblicazione */
		
		// se i dati di pubblicazione non sono salvati in DB mi tengo quelli che già mi arrivano nella chiamata
		if(lDocumentoXmlOutBean.getDataInizioPubbl() != null) {
			bean.setDataInizioPubblicazione(lDocumentoXmlOutBean.getDataInizioPubbl());
		}
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getGiorniDurataPubbl())) {
			bean.setGiorniPubblicazione(lDocumentoXmlOutBean.getGiorniDurataPubbl());
		}
		
		/* Dati scheda - Emenda */
		
		bean.setTipoAttoEmendamento(lDocumentoXmlOutBean.getTipoAttoEmendamento());	
		bean.setSiglaAttoEmendamento(lDocumentoXmlOutBean.getSiglaAttoEmendamento());	
		bean.setNumeroAttoEmendamento(lDocumentoXmlOutBean.getNumeroAttoEmendamento());			
		bean.setAnnoAttoEmendamento(lDocumentoXmlOutBean.getAnnoAttoEmendamento());	
		bean.setIdEmendamento(lDocumentoXmlOutBean.getIdEmendamento());	 
		bean.setNumeroEmendamento(lDocumentoXmlOutBean.getNumeroEmendamento());	 
		bean.setFlgEmendamentoSuFile(lDocumentoXmlOutBean.getFlgEmendamentoSuFile());	
		bean.setNumeroAllegatoEmendamento(lDocumentoXmlOutBean.getNumeroAllegatoEmendamento());	 
		bean.setFlgEmendamentoIntegraleAllegato(lDocumentoXmlOutBean.getFlgEmendamentoIntegraleAllegato() == Flag.SETTED);		
		bean.setNumeroPaginaEmendamento(lDocumentoXmlOutBean.getNumeroPaginaEmendamento());	
		bean.setNumeroRigaEmendamento(lDocumentoXmlOutBean.getNumeroRigaEmendamento());			
		bean.setEffettoEmendamento(lDocumentoXmlOutBean.getEffettoEmendamento());	
				
		/* Dati scheda - Destinatari */		
		
		bean.setFlgAttivaDestinatari(lDocumentoXmlOutBean.getFlgAttivaDestinatari() == Flag.SETTED);		
		
		List<DestAttoBean> listaDestinatariAtto = new ArrayList<DestAttoBean>();
		if (lDocumentoXmlOutBean.getListaDestinatariAtto() != null && lDocumentoXmlOutBean.getListaDestinatariAtto().size() > 0) {
			for (DestinatarioAttoBean lDestinatarioAttoBean : lDocumentoXmlOutBean.getListaDestinatariAtto()) {
				DestAttoBean lDestAttoBean = new DestAttoBean();
				lDestAttoBean.setPrefisso(lDestinatarioAttoBean.getPrefisso());
				lDestAttoBean.setDenominazione(lDestinatarioAttoBean.getDenominazione());
				lDestAttoBean.setIndirizzo(lDestinatarioAttoBean.getIndirizzo());
				lDestAttoBean.setCorteseAttenzione(lDestinatarioAttoBean.getCorteseAttenzione());
				lDestAttoBean.setIdSoggRubrica(lDestinatarioAttoBean.getIdSoggRubrica());				
				listaDestinatariAtto.add(lDestAttoBean);
			}
		} else {
			listaDestinatariAtto.add(new DestAttoBean());
		}
		bean.setListaDestinatariAtto(listaDestinatariAtto);
		
		List<DestAttoBean> listaDestinatariPCAtto = new ArrayList<DestAttoBean>();
		if (lDocumentoXmlOutBean.getListaDestinatariPCAtto() != null && lDocumentoXmlOutBean.getListaDestinatariPCAtto().size() > 0) {
			for (DestinatarioAttoBean lDestinatarioPCAttoBean : lDocumentoXmlOutBean.getListaDestinatariPCAtto()) {
				DestAttoBean lDestPCAttoBean = new DestAttoBean();
				lDestPCAttoBean.setPrefisso(lDestinatarioPCAttoBean.getPrefisso());
				lDestPCAttoBean.setDenominazione(lDestinatarioPCAttoBean.getDenominazione());
				lDestPCAttoBean.setIndirizzo(lDestinatarioPCAttoBean.getIndirizzo());
				lDestPCAttoBean.setCorteseAttenzione(lDestinatarioPCAttoBean.getCorteseAttenzione());
				lDestPCAttoBean.setIdSoggRubrica(lDestinatarioPCAttoBean.getIdSoggRubrica());					
				listaDestinatariPCAtto.add(lDestPCAttoBean);
			}
		} else {
			listaDestinatariPCAtto.add(new DestAttoBean());
		}
		bean.setListaDestinatariPCAtto(listaDestinatariPCAtto);
		
		/* Dati scheda - Tipo proposta */
		
		bean.setIniziativaProposta(lDocumentoXmlOutBean.getIniziativaPropAtto());		
		bean.setFlgAttoMeroIndirizzo(lDocumentoXmlOutBean.getFlgAttoMeroIndirizzo() == Flag.SETTED);		
		bean.setFlgModificaRegolamento(lDocumentoXmlOutBean.getFlgModificaRegolamento() == Flag.SETTED);			
		bean.setFlgModificaStatuto(lDocumentoXmlOutBean.getFlgModificaStatuto() == Flag.SETTED);	
		bean.setFlgNomina(lDocumentoXmlOutBean.getFlgNomina() == Flag.SETTED);	
		bean.setFlgRatificaDeliberaUrgenza(lDocumentoXmlOutBean.getFlgRatificaDeliberaUrgenza() == Flag.SETTED);		
		bean.setFlgAttoUrgente(lDocumentoXmlOutBean.getFlgAttoUrgente() == Flag.SETTED);		
		
		/* Dati scheda - Circoscrizioni proponenti delibera */
		
		List<SimpleKeyValueBean> listaCircoscrizioni = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getCircoscrizioniProponenti() != null && lDocumentoXmlOutBean.getCircoscrizioniProponenti().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getCircoscrizioniProponenti()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaCircoscrizioni.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaCircoscrizioni(listaCircoscrizioni);
		
		/* Dati scheda - Interpellanza */
		
		bean.setTipoInterpellanza(lDocumentoXmlOutBean.getTipoInterpellanza());
		
		/* Dati scheda - Ordinanza di mobilità */
		
		bean.setTipoOrdMobilita(lDocumentoXmlOutBean.getTipoOrdMobilita());
		bean.setDataInizioVldOrdinanza(lDocumentoXmlOutBean.getDataInizioVldOrdinanza());
		bean.setDataFineVldOrdinanza(lDocumentoXmlOutBean.getDataFineVldOrdinanza());
		
		bean.setTipoLuogoOrdMobilita(lDocumentoXmlOutBean.getTipoLuogoOrdMobilita());
		bean.setListaIndirizziOrdMobilita(lProtocollazioneBean.getListaAltreVie() != null ? lProtocollazioneBean.getListaAltreVie() : new ArrayList<AltraViaProtBean>());
		bean.setLuogoOrdMobilita(lDocumentoXmlOutBean.getLuogoOrdMobilita());
		
		List<SimpleKeyValueBean> listaCircoscrizioniOrdMobilita = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getCircoscrizioniOrdMobilita() != null && lDocumentoXmlOutBean.getCircoscrizioniOrdMobilita().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getCircoscrizioniOrdMobilita()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaCircoscrizioniOrdMobilita.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaCircoscrizioniOrdMobilita(listaCircoscrizioniOrdMobilita);
		
		bean.setDescrizioneOrdMobilita(lDocumentoXmlOutBean.getDescrizioneOrdMobilita());
		
		/* Dati scheda - Ruoli */
		
		// Struttura proponente
		bean.setUfficioProponente(lDocumentoXmlOutBean.getIdUOProponente());
		bean.setCodUfficioProponente(lDocumentoXmlOutBean.getCodUOProponente());
		bean.setDesUfficioProponente(lDocumentoXmlOutBean.getDesUOProponente());
		bean.setDesDirezioneProponente(lDocumentoXmlOutBean.getDesDirProponente());
		List<SelezionaUOBean> listaUfficioProponente = new ArrayList<SelezionaUOBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUOProponente())) {
			SelezionaUOBean lSelezionaUOBean = new SelezionaUOBean();
			lSelezionaUOBean.setIdUo(lDocumentoXmlOutBean.getIdUOProponente());
			lSelezionaUOBean.setCodRapido(lDocumentoXmlOutBean.getCodUOProponente());
			lSelezionaUOBean.setDescrizione(lDocumentoXmlOutBean.getDesUOProponente());
			lSelezionaUOBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesUOProponente());				
			lSelezionaUOBean.setOrganigramma("UO" + lDocumentoXmlOutBean.getIdUOProponente());
			lSelezionaUOBean.setOrganigrammaFromLoadDett("UO" + lDocumentoXmlOutBean.getIdUOProponente());
			listaUfficioProponente.add(lSelezionaUOBean);
		}
		bean.setListaUfficioProponente(listaUfficioProponente);
		
		// Dir. adottante
		List<DirigenteAdottanteBean> listaAdottante = new ArrayList<DirigenteAdottanteBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaAdottante())) {
			DirigenteAdottanteBean lDirigenteAdottanteBean = new DirigenteAdottanteBean();
			lDirigenteAdottanteBean.setDirigenteAdottante(lDocumentoXmlOutBean.getIdScrivaniaAdottante());
			lDirigenteAdottanteBean.setDirigenteAdottanteFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaAdottante());						
			lDirigenteAdottanteBean.setDesDirigenteAdottante(lDocumentoXmlOutBean.getDesScrivaniaAdottante());
			lDirigenteAdottanteBean.setCodUoDirigenteAdottante(lDocumentoXmlOutBean.getCodUOScrivaniaAdottante());
			lDirigenteAdottanteBean.setFlgAdottanteAncheRdP(lDocumentoXmlOutBean.getFlgAdottanteAncheRespProc() == Flag.SETTED);	
			lDirigenteAdottanteBean.setFlgAdottanteAncheRUP(lDocumentoXmlOutBean.getFlgAdottanteAncheRUP() == Flag.SETTED);	
			listaAdottante.add(lDirigenteAdottanteBean);
		} else {
			listaAdottante.add(new DirigenteAdottanteBean());
		}
		bean.setListaAdottante(listaAdottante);	
		
		// Centro di Costo
		bean.setCentroDiCosto(lDocumentoXmlOutBean.getCentroDiCosto());		
		
		// Adottanti di concerto
		List<DirigenteDiConcertoBean> listaDirigentiConcerto = new ArrayList<DirigenteDiConcertoBean>();
		if(lDocumentoXmlOutBean.getFlgDetDiConcerto() == Flag.SETTED) {
			if (lDocumentoXmlOutBean.getResponsabiliDiConcerto() != null && lDocumentoXmlOutBean.getResponsabiliDiConcerto().size() > 0) {
				for (RespDiConcertoBean lRespDiConcertoBean : lDocumentoXmlOutBean.getResponsabiliDiConcerto()) {
					DirigenteDiConcertoBean lDirigenteDiConcertoBean = new DirigenteDiConcertoBean();
					lDirigenteDiConcertoBean.setDirigenteConcerto(lRespDiConcertoBean.getIdSV());
					lDirigenteDiConcertoBean.setDirigenteConcertoFromLoadDett(lRespDiConcertoBean.getIdSV());		
					lDirigenteDiConcertoBean.setCodUoDirigenteConcerto(lRespDiConcertoBean.getCodUO());
					lDirigenteDiConcertoBean.setDesDirigenteConcerto(lRespDiConcertoBean.getDescrizione());
					lDirigenteDiConcertoBean.setFlgDirigenteConcertoFirmatario(lRespDiConcertoBean.getFlgFirmatario());
					listaDirigentiConcerto.add(lDirigenteDiConcertoBean);
				}
			}
		}
		bean.setListaDirigentiConcerto(listaDirigentiConcerto);

		// Dir. resp reg. tecnica
		List<DirigenteRespRegTecnicaBean> listaDirRespRegTecnica = new ArrayList<DirigenteRespRegTecnicaBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaDirRespRegTecnica())) {
			DirigenteRespRegTecnicaBean lDirigenteRespRegTecnicaBean = new DirigenteRespRegTecnicaBean();
			lDirigenteRespRegTecnicaBean.setDirigenteRespRegTecnica(lDocumentoXmlOutBean.getIdScrivaniaDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setDirigenteRespRegTecnicaFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaDirRespRegTecnica());									
			lDirigenteRespRegTecnicaBean.setCodUoDirigenteRespRegTecnica(lDocumentoXmlOutBean.getLivelliUOScrivaniaDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setDesDirigenteRespRegTecnica(lDocumentoXmlOutBean.getDesScrivaniaDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setFlgDirRespRegTecnicaAncheRdP(lDocumentoXmlOutBean.getFlgDirAncheRespProc() == Flag.SETTED);
			lDirigenteRespRegTecnicaBean.setFlgDirRespRegTecnicaAncheRUP(lDocumentoXmlOutBean.getFlgDirAncheRUP() == Flag.SETTED);
			listaDirRespRegTecnica.add(lDirigenteRespRegTecnicaBean);
		} else {
			listaDirRespRegTecnica.add(new DirigenteRespRegTecnicaBean());
		}
		bean.setListaDirRespRegTecnica(listaDirRespRegTecnica);
		
		// Altri pareri reg. tecnica
		List<AltriDirRespRegTecnicaBean> listaAltriDirRespTecnica = new ArrayList<AltriDirRespRegTecnicaBean>();
		if (lDocumentoXmlOutBean.getAltriDirRespTecnica() != null && lDocumentoXmlOutBean.getAltriDirRespTecnica().size() > 0) {
			for (DirRespRegTecnicaBean lDirRespRegTecnicaBean : lDocumentoXmlOutBean.getAltriDirRespTecnica()) {
				AltriDirRespRegTecnicaBean lAltriDirRespRegTecnicaBean = new AltriDirRespRegTecnicaBean();
				lAltriDirRespRegTecnicaBean.setDirigenteRespRegTecnica(lDirRespRegTecnicaBean.getIdSV());
				lAltriDirRespRegTecnicaBean.setDirigenteRespRegTecnicaFromLoadDett(lDirRespRegTecnicaBean.getIdSV());
				lAltriDirRespRegTecnicaBean.setCodUoDirigenteRespRegTecnica(lDirRespRegTecnicaBean.getCodUO());
				lAltriDirRespRegTecnicaBean.setDesDirigenteRespRegTecnica(lDirRespRegTecnicaBean.getDescrizione());
				lAltriDirRespRegTecnicaBean.setFlgDirigenteRespRegTecnicaFirmatario(lDirRespRegTecnicaBean.getFlgFirmatario());
				listaAltriDirRespTecnica.add(lAltriDirRespRegTecnicaBean);
			}
		}
		bean.setListaAltriDirRespRegTecnica(listaAltriDirRespTecnica);
		
		// Responsabile di Procedimento
		List<RdPCompletaBean> listaRdP = new ArrayList<RdPCompletaBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRespProc())) {
			RdPCompletaBean lRdPCompletaBean = new RdPCompletaBean();
			lRdPCompletaBean.setResponsabileDiProcedimento(lDocumentoXmlOutBean.getIdScrivaniaRespProc());
			lRdPCompletaBean.setResponsabileDiProcedimentoFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRespProc());								
			lRdPCompletaBean.setCodUoResponsabileDiProcedimento(lDocumentoXmlOutBean.getCodUOScrivaniaRespProc());
			lRdPCompletaBean.setDesResponsabileDiProcedimento(lDocumentoXmlOutBean.getDesScrivaniaRespProc());		
			lRdPCompletaBean.setFlgRdPAncheRUP(lDocumentoXmlOutBean.getFlgRespProcAncheRUP() == Flag.SETTED);
			listaRdP.add(lRdPCompletaBean);
		} else {
			listaRdP.add(new RdPCompletaBean());
		}
		bean.setListaRdP(listaRdP);		
				
		// Responsabile Unico Provvedimento
		List<RUPCompletaBean> listaRUP = new ArrayList<RUPCompletaBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRUP())) {
			RUPCompletaBean lRUPCompletaBean = new RUPCompletaBean();
			lRUPCompletaBean.setResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lRUPCompletaBean.setResponsabileUnicoProvvedimentoFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lRUPCompletaBean.setCodUoResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getCodUOScrivaniaRUP());
			lRUPCompletaBean.setDesResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getDesScrivaniaRUP());
			listaRUP.add(lRUPCompletaBean);
		} else {
			listaRUP.add(new RUPCompletaBean());
		}
		bean.setListaRUP(listaRUP);
		
		// Assessore proponente
		List<AssessoreBean> listaAssessori = new ArrayList<AssessoreBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdAssessoreProponente())) {
			AssessoreBean lAssessoreBean = new AssessoreBean();
			lAssessoreBean.setAssessore(lDocumentoXmlOutBean.getIdAssessoreProponente());
			lAssessoreBean.setAssessoreFromLoadDett(lDocumentoXmlOutBean.getIdAssessoreProponente());
			lAssessoreBean.setDesAssessore(lDocumentoXmlOutBean.getDesAssessoreProponente());
			listaAssessori.add(lAssessoreBean);
		} else {
			listaAssessori.add(new AssessoreBean());
		}
		bean.setListaAssessori(listaAssessori);
		
		// Altri assessori
		List<AssessoreBean> listaAltriAssessori = new ArrayList<AssessoreBean>();
		if (lDocumentoXmlOutBean.getAltriAssessori() != null && lDocumentoXmlOutBean.getAltriAssessori().size() > 0) {
			for (AssessoreProponenteBean lAssessoreProponenteBean : lDocumentoXmlOutBean.getAltriAssessori()) {
				AssessoreBean lAssessoreBean = new AssessoreBean();
				lAssessoreBean.setAssessore(lAssessoreProponenteBean.getIdSvUt());
				lAssessoreBean.setAssessoreFromLoadDett(lAssessoreProponenteBean.getIdSvUt());
				lAssessoreBean.setDesAssessore(lAssessoreProponenteBean.getDescrizione());
				lAssessoreBean.setFlgAssessoreFirmatario(lAssessoreProponenteBean.getFlgFirmatario());
				listaAltriAssessori.add(lAssessoreBean);
			}
		}
		bean.setListaAltriAssessori(listaAltriAssessori);
		
		// Consigliere proponente
		List<ConsigliereBean> listaConsiglieri = new ArrayList<ConsigliereBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdConsigliereProponente())) {
			ConsigliereBean lConsigliereBean = new ConsigliereBean();
			lConsigliereBean.setConsigliere(lDocumentoXmlOutBean.getIdConsigliereProponente());
			lConsigliereBean.setConsigliereFromLoadDett(lDocumentoXmlOutBean.getIdConsigliereProponente());
			lConsigliereBean.setDesConsigliere(lDocumentoXmlOutBean.getDesConsigliereProponente());
			listaConsiglieri.add(lConsigliereBean);
		} else {
			listaConsiglieri.add(new ConsigliereBean());
		}
		bean.setListaConsiglieri(listaConsiglieri);
		
		// Altri consiglieri
		List<ConsigliereBean> listaAltriConsiglieri = new ArrayList<ConsigliereBean>();
		if (lDocumentoXmlOutBean.getAltriConsiglieri() != null && lDocumentoXmlOutBean.getAltriConsiglieri().size() > 0) {
			for (ConsigliereProponenteBean lConsigliereProponenteBean : lDocumentoXmlOutBean.getAltriConsiglieri()) {
				ConsigliereBean lConsigliereBean = new ConsigliereBean();
				lConsigliereBean.setConsigliere(lConsigliereProponenteBean.getIdSvUt());
				lConsigliereBean.setConsigliereFromLoadDett(lConsigliereProponenteBean.getIdSvUt());
				lConsigliereBean.setDesConsigliere(lConsigliereProponenteBean.getDescrizione());
				lConsigliereBean.setFlgConsigliereFirmatario(lConsigliereProponenteBean.getFlgFirmatario());
				listaAltriConsiglieri.add(lConsigliereBean);
			}
		}
		bean.setListaAltriConsiglieri(listaAltriConsiglieri);
		
		// Dirigente proponente
		List<DirigenteProponenteBean> listaDirigentiProponenti = new ArrayList<DirigenteProponenteBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaDirProponente())) {
			DirigenteProponenteBean lDirigenteProponenteBean = new DirigenteProponenteBean();
			lDirigenteProponenteBean.setDirigenteProponente(lDocumentoXmlOutBean.getIdScrivaniaDirProponente());
			lDirigenteProponenteBean.setDirigenteProponenteFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaDirProponente());						
			lDirigenteProponenteBean.setDesDirigenteProponente(lDocumentoXmlOutBean.getDesScrivaniaDirProponente());
			lDirigenteProponenteBean.setCodUoDirigenteProponente(lDocumentoXmlOutBean.getLivelliUOScrivaniaDirProponente());
			listaDirigentiProponenti.add(lDirigenteProponenteBean);
		} else {
			listaDirigentiProponenti.add(new DirigenteProponenteBean());
		}
		bean.setListaDirigentiProponenti(listaDirigentiProponenti);	
				
		// Altri dirigenti proponenti
		List<DirigenteProponenteBean> listaAltriDirigentiProponenti = new ArrayList<DirigenteProponenteBean>();
		if (lDocumentoXmlOutBean.getDirigentiProponenti() != null && lDocumentoXmlOutBean.getDirigentiProponenti().size() > 0) {
			for (ScrivaniaDirProponenteBean lScrivaniaDirProponenteBean : lDocumentoXmlOutBean.getAltriDirProponenti()) {
				DirigenteProponenteBean lDirigenteProponenteBean = new DirigenteProponenteBean();
				lDirigenteProponenteBean.setDirigenteProponente(lScrivaniaDirProponenteBean.getIdSV());
				lDirigenteProponenteBean.setDirigenteProponenteFromLoadDett(lScrivaniaDirProponenteBean.getIdSV());
				lDirigenteProponenteBean.setCodUoDirigenteProponente(lScrivaniaDirProponenteBean.getCodUO());
				lDirigenteProponenteBean.setDesDirigenteProponente(lScrivaniaDirProponenteBean.getDescrizione());
				lDirigenteProponenteBean.setFlgDirigenteProponenteFirmatario(lScrivaniaDirProponenteBean.getFlgFirmatario());
				lDirigenteProponenteBean.setMotiviDirigenteProponente(lScrivaniaDirProponenteBean.getMotivi());
				listaAltriDirigentiProponenti.add(lDirigenteProponenteBean);
			}
		}
		bean.setListaAltriDirigentiProponenti(listaAltriDirigentiProponenti);
		
		// Coordinatore competente per materia
		List<CoordinatoreCompCircBean> listaCoordinatoriCompCirc = new ArrayList<CoordinatoreCompCircBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdCoordinatoreCompCirc())) {
			CoordinatoreCompCircBean lCoordinatoreCompCircBean = new CoordinatoreCompCircBean();
			lCoordinatoreCompCircBean.setCoordinatoreCompCirc(lDocumentoXmlOutBean.getIdCoordinatoreCompCirc());
			lCoordinatoreCompCircBean.setCoordinatoreCompCircFromLoadDett(lDocumentoXmlOutBean.getIdCoordinatoreCompCirc());
			lCoordinatoreCompCircBean.setDesCoordinatoreCompCirc(lDocumentoXmlOutBean.getDesCoordinatoreCompCirc());
			listaCoordinatoriCompCirc.add(lCoordinatoreCompCircBean);
		} else {
			listaCoordinatoriCompCirc.add(new CoordinatoreCompCircBean());
		}
		bean.setListaCoordinatoriCompCirc(listaCoordinatoriCompCirc);
		
		// Richiesto visto Dir. sovraordinato
		bean.setFlgRichiediVistoDirettore(lDocumentoXmlOutBean.getFlgRichiediVistoDirettore() == Flag.SETTED);
		
		// Visti di conformità
		List<ResponsabileVistiConformitaBean> listaRespVistiConformita = new ArrayList<ResponsabileVistiConformitaBean>();
		if (lDocumentoXmlOutBean.getRespVistiConformita() != null && lDocumentoXmlOutBean.getRespVistiConformita().size() > 0) {
			for (RespVistiConformitaBean lRespVistiConformitaBean : lDocumentoXmlOutBean.getRespVistiConformita()) {
				ResponsabileVistiConformitaBean lResponsabileVistiConformitaBean = new ResponsabileVistiConformitaBean();
				lResponsabileVistiConformitaBean.setRespVistiConformita(lRespVistiConformitaBean.getIdSV());
				lResponsabileVistiConformitaBean.setRespVistiConformitaFromLoadDett(lRespVistiConformitaBean.getIdSV());
				lResponsabileVistiConformitaBean.setCodUoRespVistiConformita(lRespVistiConformitaBean.getCodUO());
				lResponsabileVistiConformitaBean.setDesRespVistiConformita(lRespVistiConformitaBean.getDescrizione());
				lResponsabileVistiConformitaBean.setFlgRespVistiConformitaFirmatario(lRespVistiConformitaBean.getFlgFirmatario());
				lResponsabileVistiConformitaBean.setMotiviRespVistiConformita(lRespVistiConformitaBean.getMotivi());
				lResponsabileVistiConformitaBean.setFlgRiacqVistoInRitornoIterRespVistiConformita(lRespVistiConformitaBean.getFlgRiacqVistoInRitornoIter());				
				listaRespVistiConformita.add(lResponsabileVistiConformitaBean);
			}
		}
		bean.setListaRespVistiConformita(listaRespVistiConformita);
		
		// Estensore principale
		List<EstensoreBean> listaEstensori = new ArrayList<EstensoreBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaEstensoreMain())) {
			EstensoreBean lEstensoreBean = new EstensoreBean();
			lEstensoreBean.setEstensore(lDocumentoXmlOutBean.getIdScrivaniaEstensoreMain());
			lEstensoreBean.setEstensoreFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaEstensoreMain());
			lEstensoreBean.setCodUoEstensore(lDocumentoXmlOutBean.getLivelliUOScrivaniaEstensoreMain());
			lEstensoreBean.setDesEstensore(lDocumentoXmlOutBean.getDesScrivaniaEstensoreMain());
			listaEstensori.add(lEstensoreBean);
		} else {
			listaEstensori.add(new EstensoreBean());
		}
		bean.setListaEstensori(listaEstensori);
		
		// Altri estensori
		List<EstensoreBean> listaAltriEstensori = new ArrayList<EstensoreBean>();
		if (lDocumentoXmlOutBean.getAltriEstensori() != null && lDocumentoXmlOutBean.getAltriEstensori().size() > 0) {
			for (ScrivaniaEstensoreBean lScrivaniaEstensoreBean : lDocumentoXmlOutBean.getAltriEstensori()) {
				EstensoreBean lEstensoreBean = new EstensoreBean();
				lEstensoreBean.setEstensore(lScrivaniaEstensoreBean.getIdSV());
				lEstensoreBean.setEstensoreFromLoadDett(lScrivaniaEstensoreBean.getIdSV());
				lEstensoreBean.setCodUoEstensore(lScrivaniaEstensoreBean.getCodUO());
				lEstensoreBean.setDesEstensore(lScrivaniaEstensoreBean.getDescrizione());
				listaAltriEstensori.add(lEstensoreBean);
			}
		}
		bean.setListaAltriEstensori(listaAltriEstensori);
		
		// Resp. istruttoria
		List<IstruttoreBean> listaIstruttori = new ArrayList<IstruttoreBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaIstruttoreMain())) {
			IstruttoreBean lIstruttoreBean = new IstruttoreBean();
			lIstruttoreBean.setIstruttore(lDocumentoXmlOutBean.getIdScrivaniaIstruttoreMain());
			lIstruttoreBean.setIstruttoreFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaIstruttoreMain());
			lIstruttoreBean.setCodUoIstruttore(lDocumentoXmlOutBean.getLivelliUOScrivaniaIstruttoreMain());
			lIstruttoreBean.setDesIstruttore(lDocumentoXmlOutBean.getDesScrivaniaIstruttoreMain());
			listaIstruttori.add(lIstruttoreBean);
		} else {
			listaIstruttori.add(new IstruttoreBean());
		}
		bean.setListaIstruttori(listaIstruttori);
		
		// Altri istruttori
		List<IstruttoreBean> listaAltriIstruttori = new ArrayList<IstruttoreBean>();
		if (lDocumentoXmlOutBean.getAltriIstruttori() != null && lDocumentoXmlOutBean.getAltriIstruttori().size() > 0) {
			for (ScrivaniaEstensoreBean lScrivaniaIstruttoreBean : lDocumentoXmlOutBean.getAltriIstruttori()) {
				IstruttoreBean lIstruttoreBean = new IstruttoreBean();
				lIstruttoreBean.setIstruttore(lScrivaniaIstruttoreBean.getIdSV());
				lIstruttoreBean.setIstruttoreFromLoadDett(lScrivaniaIstruttoreBean.getIdSV());
				lIstruttoreBean.setCodUoIstruttore(lScrivaniaIstruttoreBean.getCodUO());
				lIstruttoreBean.setDesIstruttore(lScrivaniaIstruttoreBean.getDescrizione());
				listaAltriIstruttori.add(lIstruttoreBean);
			}
		}
		bean.setListaAltriIstruttori(listaAltriIstruttori);
		
		/* Dati scheda - Visti dir. superiori */
		
		bean.setFlgVistoDirSup1(lDocumentoXmlOutBean.getFlgVistoDirSup1() == Flag.SETTED);
		bean.setFlgVistoDirSup2(lDocumentoXmlOutBean.getFlgVistoDirSup2() == Flag.SETTED);
		
		/* Dati scheda - Parere della/e circoscrizioni */
		
		List<SimpleKeyValueBean> listaParereCircoscrizioni = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getParereCircoscrizioni() != null && lDocumentoXmlOutBean.getParereCircoscrizioni().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getParereCircoscrizioni()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaParereCircoscrizioni.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaParereCircoscrizioni(listaParereCircoscrizioni);
		
		/* Dati scheda - Parere della/e commissioni */
		
		List<SimpleKeyValueBean> listaParereCommissioni = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getParereCommissioni() != null && lDocumentoXmlOutBean.getParereCommissioni().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getParereCommissioni()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaParereCommissioni.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaParereCommissioni(listaParereCommissioni);
		
		/* Dati scheda - Oggetto */
		
		bean.setOggetto(lProtocollazioneBean.getOggetto());
		// Se non ho l'oggetto html metto l'oggetto normale
		bean.setOggettoHtml(StringUtils.isNotBlank(lDocumentoXmlOutBean.getOggettoHtml()) ? lDocumentoXmlOutBean.getOggettoHtml() : lProtocollazioneBean.getOggetto());
		
		/* Dati scheda - Atto di riferimento */
		
		/*
//		if(lDocumentoXmlOutBean.getDocCollegato() != null) {
//			bean.setFlgAttoRifASistema(null);
//			bean.setIdUdAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getIdUd());
//			bean.setCategoriaRegAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getTipo() != null ? lDocumentoXmlOutBean.getDocCollegato().getTipo().toString() : null);
//			bean.setSiglaAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getRegistro());
//			bean.setNumeroAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getNro());
//			bean.setAnnoAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getAnno());
//		}		
		if(lDocumentoXmlOutBean.getDocumentiCollegati() != null  && lDocumentoXmlOutBean.getDocumentiCollegati().size() > 0) {
			DocumentoCollegato lDocumentoCollegato = lDocumentoXmlOutBean.getDocumentiCollegati().get(0);
			bean.setFlgAttoRifASistema(lDocumentoCollegato.getFlgPresenteASistema());
			bean.setIdUdAttoDeterminaAContrarre(lDocumentoCollegato.getIdUd());
			bean.setCategoriaRegAttoDeterminaAContrarre(lDocumentoCollegato.getTipo() != null ? lDocumentoCollegato.getTipo().toString() : null);
			bean.setSiglaAttoDeterminaAContrarre(lDocumentoCollegato.getSiglaRegistro());
			bean.setNumeroAttoDeterminaAContrarre(lDocumentoCollegato.getNumero() != null ? String.valueOf(lDocumentoCollegato.getNumero().intValue()) : null);
			bean.setAnnoAttoDeterminaAContrarre(lDocumentoCollegato.getAnno() != null ? String.valueOf(lDocumentoCollegato.getAnno().intValue()) : null);
		} 
		*/
		
		List<AttoRiferimentoBean> listaAttiRiferimento = new ArrayList<AttoRiferimentoBean>();
		if(lDocumentoXmlOutBean.getDocumentiCollegati() != null  && lDocumentoXmlOutBean.getDocumentiCollegati().size() > 0) {
			for(int i = 0; i < lDocumentoXmlOutBean.getDocumentiCollegati().size(); i++) {
				DocumentoCollegato lDocumentoCollegato = lDocumentoXmlOutBean.getDocumentiCollegati().get(i);
				AttoRiferimentoBean lAttoRiferimentoBean = new AttoRiferimentoBean();
				lAttoRiferimentoBean.setFlgPresenteASistema(lDocumentoCollegato.getFlgPresenteASistema());
				lAttoRiferimentoBean.setIdUd(lDocumentoCollegato.getIdUd());
				lAttoRiferimentoBean.setCategoriaReg(lDocumentoCollegato.getTipo() != null ? lDocumentoCollegato.getTipo().toString() : null);
				lAttoRiferimentoBean.setSigla(lDocumentoCollegato.getSiglaRegistro());
				lAttoRiferimentoBean.setNumero(lDocumentoCollegato.getNumero() != null ? String.valueOf(lDocumentoCollegato.getNumero().intValue()) : null);
				lAttoRiferimentoBean.setAnno(lDocumentoCollegato.getAnno() != null ? String.valueOf(lDocumentoCollegato.getAnno().intValue()) : null);
				listaAttiRiferimento.add(lAttoRiferimentoBean);
			}
		} 
		bean.setListaAttiRiferimento(listaAttiRiferimento);
		
		/* Dati scheda - Specificità del provvedimento */
		
		bean.setOggLiquidazione(lDocumentoXmlOutBean.getOggLiquidazione());
		bean.setDataScadenzaLiquidazione(lDocumentoXmlOutBean.getDataScadenzaLiquidazione());
		bean.setUrgenzaLiquidazione(lDocumentoXmlOutBean.getUrgenzaLiquidazione());
		bean.setFlgLiqXUffCassa(lDocumentoXmlOutBean.getFlgLiqXUffCassa() == Flag.SETTED);
		bean.setImportoAnticipoCassa(lDocumentoXmlOutBean.getImportoAnticipoCassa());
		bean.setDataDecorrenzaContratto(lDocumentoXmlOutBean.getDataDecorrenzaContratto());
		bean.setAnniDurataContratto(lDocumentoXmlOutBean.getAnniDurataContratto());
		
		bean.setFlgAffidamento(lDocumentoXmlOutBean.getFlgAffidamento() == Flag.SETTED);
		bean.setFlgDeterminaAContrarreTramiteProceduraGara(lDocumentoXmlOutBean.getFlgDetContrConGara() == Flag.SETTED);
		bean.setFlgDeterminaAggiudicaProceduraGara(lDocumentoXmlOutBean.getFlgDetAggiudicaGara() == Flag.SETTED);
		bean.setFlgDeterminaRimodulazioneSpesaGaraAggiudicata(lDocumentoXmlOutBean.getFlgDetRimodSpesaGaraAggiud() == Flag.SETTED);
		bean.setFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro(lDocumentoXmlOutBean.getFlgDetPersonale() == Flag.SETTED);
		bean.setFlgDeterminaRiaccertamento(lDocumentoXmlOutBean.getFlgDetRiaccert() == Flag.SETTED);
		bean.setFlgDeterminaAccertRadiaz(lDocumentoXmlOutBean.getFlgDetAccertRadiaz() == Flag.SETTED);
		bean.setFlgDeterminaVariazBil(lDocumentoXmlOutBean.getFlgDetVariazBil() == Flag.SETTED);
		bean.setFlgVantaggiEconomici(lDocumentoXmlOutBean.getFlgVantaggiEconomici() == Flag.SETTED);
		bean.setFlgDecretoReggio(lDocumentoXmlOutBean.getFlgDecretoReggio() == Flag.SETTED);
		bean.setFlgAvvocatura(lDocumentoXmlOutBean.getFlgAvvocatura() == Flag.SETTED);
		
		bean.setFlgSpesa(lDocumentoXmlOutBean.getFlgDetConSpesa());
		bean.setFlgCorteConti(lDocumentoXmlOutBean.getFlgCorteConti() == Flag.SETTED);
		bean.setFlgLiqContestualeImpegno(lDocumentoXmlOutBean.getFlgLiqContestualeImpegno() == Flag.SETTED);
		bean.setFlgLiqContestualeAltriAspettiRilCont(lDocumentoXmlOutBean.getFlgLiqContestualeAltriAspettiRilCont() == Flag.SETTED);
		bean.setFlgCompQuadroFinRagDec(lDocumentoXmlOutBean.getFlgCompQuadroFinRagDec() == Flag.SETTED);		
		bean.setFlgNuoviImpAcc(lDocumentoXmlOutBean.getFlgNuoviImpAcc() == Flag.SETTED);
		bean.setFlgImpSuAnnoCorrente(lDocumentoXmlOutBean.getFlgImpSuAnnoCorrente() == Flag.SETTED);
		bean.setFlgInsMovARagioneria(lDocumentoXmlOutBean.getFlgInsMovARagioneria() == Flag.SETTED);
		bean.setFlgPresaVisioneContabilita(lDocumentoXmlOutBean.getFlgRichPresaVisContabilita() == Flag.SETTED);
		bean.setFlgSpesaCorrente(lDocumentoXmlOutBean.getFlgDetConSpesaCorrente() == Flag.SETTED);
		bean.setFlgImpegniCorrenteGiaValidati(lDocumentoXmlOutBean.getFlgDetConImpCorrValid() == Flag.SETTED);
		bean.setFlgSpesaContoCapitale(lDocumentoXmlOutBean.getFlgDetConSpesaContoCap() == Flag.SETTED);
		bean.setFlgImpegniContoCapitaleGiaRilasciati(lDocumentoXmlOutBean.getFlgDetConImpCCapRil() == Flag.SETTED);
		bean.setFlgSoloSubImpSubCrono(lDocumentoXmlOutBean.getFlgSoloSubImpSubCrono() == Flag.SETTED);
		bean.setTipoAttoInDeliberaPEG(lDocumentoXmlOutBean.getTipoAttoInDelPeg());
		bean.setTipoAffidamento(lDocumentoXmlOutBean.getTipoAffidamento());
		bean.setNormRifAffidamento(lDocumentoXmlOutBean.getNormRifAffidamento());
		bean.setRespAffidamento(lDocumentoXmlOutBean.getRespAffidamento());
		bean.setMateriaTipoAtto(lDocumentoXmlOutBean.getMateriaNaturaAtto());
		bean.setDesMateriaTipoAtto(lDocumentoXmlOutBean.getDesMateriaNaturaAtto());
		bean.setFlgFondiEuropeiPON(lDocumentoXmlOutBean.getFlgFondiEuropeiPON() == Flag.SETTED);
		bean.setFlgFondiPRU(lDocumentoXmlOutBean.getFlgFondiPRU() == Flag.SETTED);		
		bean.setFlgVistoPar117_2013(lDocumentoXmlOutBean.getFlgVistoPar117_2013() == Flag.SETTED);		
		bean.setFlgNotificaDaMessi(lDocumentoXmlOutBean.getFlgNotificaDaMessi() == Flag.SETTED);
		bean.setFlgLLPP(lDocumentoXmlOutBean.getFlgLLPP());	
		bean.setAnnoProgettoLLPP(lDocumentoXmlOutBean.getAnnoProgettoLLPP());	
		bean.setNumProgettoLLPP(lDocumentoXmlOutBean.getNumProgettoLLPP());	
		bean.setFlgBeniServizi(lDocumentoXmlOutBean.getFlgBeniServizi());
		bean.setAnnoProgettoBeniServizi(lDocumentoXmlOutBean.getAnnoProgettoBeniServizi());	
		bean.setNumProgettoBeniServizi(lDocumentoXmlOutBean.getNumProgettoBeniServizi());
		bean.setFlgPrivacy(lDocumentoXmlOutBean.getFlgPrivacy());	
		bean.setFlgDatiProtettiTipo1(lDocumentoXmlOutBean.getFlgDatiProtettiTipo1() == Flag.SETTED);
		bean.setFlgDatiProtettiTipo2(lDocumentoXmlOutBean.getFlgDatiProtettiTipo2() == Flag.SETTED);
		bean.setFlgDatiProtettiTipo3(lDocumentoXmlOutBean.getFlgDatiProtettiTipo3() == Flag.SETTED);
		bean.setFlgDatiProtettiTipo4(lDocumentoXmlOutBean.getFlgDatiProtettiTipo4() == Flag.SETTED);
		
		/* Dati scheda - Dest. vantaggio */		
		
		List<DestVantaggioBean> listaDestVantaggio = new ArrayList<DestVantaggioBean>();
		if (lDocumentoXmlOutBean.getDestinatariVantaggio() != null && lDocumentoXmlOutBean.getDestinatariVantaggio().size() > 0) {
			for (DestinatarioVantaggioBean lDestinatarioVantaggioBean : lDocumentoXmlOutBean.getDestinatariVantaggio()) {
				DestVantaggioBean lDestVantaggioBean = new DestVantaggioBean();
				lDestVantaggioBean.setTipoPersona(lDestinatarioVantaggioBean.getTipoPersona());
				lDestVantaggioBean.setCognome(lDestinatarioVantaggioBean.getCognome());
				lDestVantaggioBean.setNome(lDestinatarioVantaggioBean.getNome());
				lDestVantaggioBean.setRagioneSociale(lDestinatarioVantaggioBean.getRagioneSociale());
				lDestVantaggioBean.setCodFiscalePIVA(lDestinatarioVantaggioBean.getCodFiscalePIVA());
				lDestVantaggioBean.setImporto(lDestinatarioVantaggioBean.getImporto());
				listaDestVantaggio.add(lDestVantaggioBean);
			}
		} else {
			listaDestVantaggio.add(new DestVantaggioBean());
		}
		bean.setListaDestVantaggio(listaDestVantaggio);
				
		/* Dati scheda - Ruoli e visti per dati contabili */		
		
		// Responsabili PEG
		bean.setFlgAdottanteUnicoRespPEG(lDocumentoXmlOutBean.getFlgAdottanteUnicoRespSpesa() == Flag.SETTED);	
		List<ResponsabilePEGBean> listaResponsabiliPEG = new ArrayList<ResponsabilePEGBean>();
		if (lDocumentoXmlOutBean.getResponsabiliSpesa() != null && lDocumentoXmlOutBean.getResponsabiliSpesa().size() > 0) {
			for (RespSpesaBean lRespSpesaBean : lDocumentoXmlOutBean.getResponsabiliSpesa()) {
				ResponsabilePEGBean lResponsabilePEGBean = new ResponsabilePEGBean();
				lResponsabilePEGBean.setResponsabilePEG(lRespSpesaBean.getIdSV());
				lResponsabilePEGBean.setResponsabilePEGFromLoadDett(lRespSpesaBean.getIdSV());
				lResponsabilePEGBean.setCodUoResponsabilePEG(lRespSpesaBean.getCodUO());
				lResponsabilePEGBean.setDesResponsabilePEG(lRespSpesaBean.getDescrizione());			
				listaResponsabiliPEG.add(lResponsabilePEGBean);
			}
		}
		bean.setListaResponsabiliPEG(listaResponsabiliPEG);
				
		// Ufficio definizione spesa
		bean.setUfficioDefinizioneSpesa(lDocumentoXmlOutBean.getIdUOCompSpesa());
		bean.setCodUfficioDefinizioneSpesa(lDocumentoXmlOutBean.getCodUOCompSpesa());
		bean.setDesUfficioDefinizioneSpesa(lDocumentoXmlOutBean.getDesUOCompSpesa());
		List<SelezionaUOBean> listaUfficioDefinizioneSpesa = new ArrayList<SelezionaUOBean>();		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUOCompSpesa())) {
			SelezionaUOBean lSelezionaUOBean = new SelezionaUOBean();
			lSelezionaUOBean.setIdUo(lDocumentoXmlOutBean.getIdUOCompSpesa());
			lSelezionaUOBean.setCodRapido(lDocumentoXmlOutBean.getCodUOCompSpesa());
			lSelezionaUOBean.setDescrizione(lDocumentoXmlOutBean.getDesUOCompSpesa());
			lSelezionaUOBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesUOCompSpesa());				
			lSelezionaUOBean.setOrganigramma("UO" + lDocumentoXmlOutBean.getIdUOCompSpesa());
			lSelezionaUOBean.setOrganigrammaFromLoadDett("UO" + lDocumentoXmlOutBean.getIdUOCompSpesa());
			listaUfficioDefinizioneSpesa.add(lSelezionaUOBean);
		}
		bean.setListaUfficioDefinizioneSpesa(listaUfficioDefinizioneSpesa);	
		
		bean.setOpzAssCompSpesa(lDocumentoXmlOutBean.getOpzAssCompSpesa());
			
		bean.setFlgRichVerificaDiBilancioCorrente(lDocumentoXmlOutBean.getFlgRichVerificaDiBilancioCorrente() == Flag.SETTED);		
		bean.setFlgRichVerificaDiBilancioContoCapitale(lDocumentoXmlOutBean.getFlgRichVerificaDiBilancioContoCapitale() == Flag.SETTED);				
		bean.setFlgRichVerificaDiContabilita(lDocumentoXmlOutBean.getFlgRichVerificaDiContabilita() == Flag.SETTED);		
		bean.setFlgConVerificaContabilita(lDocumentoXmlOutBean.getFlgRichVerificaContabilita() == Flag.SETTED);
		bean.setFlgRichiediParereRevisoriContabili(lDocumentoXmlOutBean.getFlgRichParereRevContabili() == Flag.SETTED);		
			
		/* Dati scheda - CIG */
		
		bean.setFlgOpCommerciale(lDocumentoXmlOutBean.getFlgOpCommerciale());
		bean.setFlgEscludiCIG(lDocumentoXmlOutBean.getFlgEscludiCIG() == Flag.SETTED);				
		bean.setMotivoEsclusioneCIG(lDocumentoXmlOutBean.getMotivoEsclusioneCIG());
		
		List<CIGBean> listaCIG = new ArrayList<CIGBean>();
		if (lDocumentoXmlOutBean.getListaCIG() != null && !lDocumentoXmlOutBean.getListaCIG().isEmpty()) {
			for (ValueBean lValueBean : lDocumentoXmlOutBean.getListaCIG()) {
				CIGBean lCIGBean = new CIGBean();
				lCIGBean.setCodiceCIG(lValueBean.getValue());
				listaCIG.add(lCIGBean);
			}
		}
		bean.setListaCIG(listaCIG);
						
		/* Dati dispositivo - Riferimenti normativi */
		
		List<SimpleValueBean> listaRiferimentiNormativi = new ArrayList<SimpleValueBean>();
		if (lDocumentoXmlOutBean.getRiferimentiNormativi() != null && lDocumentoXmlOutBean.getRiferimentiNormativi().size() > 0) {
			for (ValueBean lValueBean : lDocumentoXmlOutBean.getRiferimentiNormativi()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lValueBean.getValue());
				listaRiferimentiNormativi.add(lSimpleValueBean);
			}
		}
		bean.setListaRiferimentiNormativi(listaRiferimentiNormativi);
		
		/* Dati dispositivo - Atti presupposti */
		
//		List<SimpleValueBean> listaAttiPresupposti = new ArrayList<SimpleValueBean>();
//		if (lDocumentoXmlOutBean.getAttiPresupposti() != null && lDocumentoXmlOutBean.getAttiPresupposti().size() > 0) {
//			for (ValueBean lValueBean : lDocumentoXmlOutBean.getAttiPresupposti()) {
//				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
//				lSimpleValueBean.setValue(lValueBean.getValue());
//				listaAttiPresupposti.add(lSimpleValueBean);
//			}
//		}
//		bean.setListaAttiPresupposti(listaAttiPresupposti);
		
		bean.setAttiPresupposti(lDocumentoXmlOutBean.getAttiPresupposti());	
		
		/* Dati dispositivo - Motivazioni */
		
		bean.setMotivazioni(lDocumentoXmlOutBean.getMotivazioniAtto());
	
		/* Dati dispositivo - Premessa */
		
		bean.setPremessa(lDocumentoXmlOutBean.getPremessaAtto());
		
		/* Dati dispositivo - Dispositivo */
		
		bean.setDispositivo(lDocumentoXmlOutBean.getDispositivoAtto());
		bean.setLoghiAggiuntiviDispositivo(lDocumentoXmlOutBean.getLoghiDispositivoAtto());		
		
		/* Dati dispositivo 2 - Premessa 2 */
		
		bean.setPremessa2(lDocumentoXmlOutBean.getPremessaAtto2());
		
		/* Dati dispositivo 2 - Dispositivo 2 */
		
		bean.setDispositivo2(lDocumentoXmlOutBean.getDispositivoAtto2());
		
		/* Allegati */
		
		bean.setFlgPubblicaAllegatiSeparati(lDocumentoXmlOutBean.getFlgPubblicaAllegatiSeparati() == Flag.SETTED);
		bean.setListaAllegati(lProtocollazioneBean.getListaAllegati() != null ? lProtocollazioneBean.getListaAllegati() : new ArrayList<AllegatoProtocolloBean>());					
		
		/* Pubblicazione/notifiche - Pubblicazione all'Albo */
		
		bean.setFlgPubblAlbo(lDocumentoXmlOutBean.getFlgPubblAlbo());
		bean.setNumGiorniPubblAlbo(lDocumentoXmlOutBean.getNumGiorniPubblAlbo());
		bean.setTipoDecorrenzaPubblAlbo(lDocumentoXmlOutBean.getTipoDecorrenzaPubblAlbo());
		bean.setDataPubblAlboDal(lDocumentoXmlOutBean.getDataPubblAlboDal());
		bean.setFlgUrgentePubblAlbo(lDocumentoXmlOutBean.getFlgUrgentePubblAlbo() == Flag.SETTED);
		bean.setDataPubblAlboEntro(lDocumentoXmlOutBean.getDataPubblAlboEntro());
		
		/* Pubblicazione/notifiche - Pubblicazione in Amm. Trasparente */
		
		bean.setFlgPubblAmmTrasp(lDocumentoXmlOutBean.getFlgPubblAmmTrasp());
		bean.setSezionePubblAmmTrasp(lDocumentoXmlOutBean.getSezionePubblAmmTrasp());
		bean.setSottoSezionePubblAmmTrasp(lDocumentoXmlOutBean.getSottoSezionePubblAmmTrasp());
		
		/* Pubblicazione/notifiche - Pubblicazione al B.U. */
		
		bean.setFlgPubblBUR(lDocumentoXmlOutBean.getFlgPubblBUR());
		bean.setAnnoTerminePubblBUR(lDocumentoXmlOutBean.getAnnoTerminePubblBUR());
		bean.setTipoDecorrenzaPubblBUR(lDocumentoXmlOutBean.getTipoDecorrenzaPubblBUR());
		bean.setDataPubblBURDal(lDocumentoXmlOutBean.getDataPubblBURDal());
		bean.setFlgUrgentePubblBUR(lDocumentoXmlOutBean.getFlgUrgentePubblBUR() == Flag.SETTED);
		bean.setDataPubblBUREntro(lDocumentoXmlOutBean.getDataPubblBUREntro());
		
		/* Pubblicazione/notifiche - Pubblicazione sul notiziario */
		
		bean.setFlgPubblNotiziario(lDocumentoXmlOutBean.getFlgPubblNotiziario());
			
		/*IdUO albo pretorio reggio calabria*/
		bean.setIdUoAlboReggio(lDocumentoXmlOutBean.getIdUoAlboReggio());
		
		/* Pubblicazione/notifiche - Esecutività */
		
		bean.setDataEsecutivitaDal(lDocumentoXmlOutBean.getDtEsecutivita());
		bean.setFlgImmediatamenteEseguibile(lDocumentoXmlOutBean.getFlgImmediatamenteEseg() == Flag.SETTED);
		bean.setMotiviImmediatamenteEseguibile(lDocumentoXmlOutBean.getMotiviImmediatamenteEseguibile());
		
		/* Pubblicazione/notifiche - Notifiche */
		
		if (lDocumentoXmlOutBean.getListaDestNotificaAtto() != null && lDocumentoXmlOutBean.getListaDestNotificaAtto().size() > 0) {
			String listaDestNotificaAtto = "";
			for (DestNotificaAttoXmlBean lDestNotificaAttoXmlBean : lDocumentoXmlOutBean.getListaDestNotificaAtto()) {
				if(StringUtils.isBlank(listaDestNotificaAtto)) {
					listaDestNotificaAtto += lDestNotificaAttoXmlBean.getIndirizzoEmail();
				} else {
					listaDestNotificaAtto += "; " + lDestNotificaAttoXmlBean.getIndirizzoEmail();
				}	
			}
			bean.setListaDestNotificaAtto(listaDestNotificaAtto);
		}
		
		/* Dati spesa corrente - Opzioni */
		
		bean.setFlgDisattivaAutoRequestDatiContabiliSIBCorrente(lDocumentoXmlOutBean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() == Flag.SETTED);
		bean.setPrenotazioneSpesaSIBDiCorrente(lDocumentoXmlOutBean.getPrenotazioneSpesaSIBDiCorrente());
		bean.setModalitaInvioDatiSpesaARagioneriaCorrente(lDocumentoXmlOutBean.getModalitaInvioDatiSpesaARagioneriaCorrente());		
		bean.setListaDatiContabiliSIBCorrente(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaDatiContabiliSIBCorrente() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaDatiContabiliSIBCorrente().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaDatiContabiliSIBCorrente().get(i)); 
				bean.getListaDatiContabiliSIBCorrente().add(lDatiContabiliBean);
			}
		}
		bean.setListaInvioDatiSpesaCorrente(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaInvioDatiSpesaCorrente() != null) {
			for(int i = 0; i < lDocumentoXmlOutBean.getListaInvioDatiSpesaCorrente().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaInvioDatiSpesaCorrente().get(i)); 
				bean.getListaInvioDatiSpesaCorrente().add(lDatiContabiliBean);
			}
		}
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getFileXlsCorrente())) {
			bean.setFileXlsCorrente(AttributiDinamiciDatasource.buildDocumentBean(lDocumentoXmlOutBean.getFileXlsCorrente()));
		}
		bean.setNomeFileTracciatoXlsCorrente("Tracciato_SIB.xls");
		bean.setUriFileTracciatoXlsCorrente(ParametriDBUtil.getParametroDB(getSession(), "URI_TRACCIATO_XLS_SIB"));
		bean.setNoteCorrente(lDocumentoXmlOutBean.getNoteCorrente());		
		
		/* Dati spesa in conto capitale - Opzioni */
		
		bean.setFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale(lDocumentoXmlOutBean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() == Flag.SETTED);
		bean.setModalitaInvioDatiSpesaARagioneriaContoCapitale(lDocumentoXmlOutBean.getModalitaInvioDatiSpesaARagioneriaContoCapitale());		
		bean.setListaDatiContabiliSIBContoCapitale(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaDatiContabiliSIBContoCapitale() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaDatiContabiliSIBContoCapitale().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaDatiContabiliSIBContoCapitale().get(i)); 
				bean.getListaDatiContabiliSIBContoCapitale().add(lDatiContabiliBean);
			}
		}
		bean.setListaInvioDatiSpesaContoCapitale(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaInvioDatiSpesaContoCapitale() != null) {
			for(int i = 0; i < lDocumentoXmlOutBean.getListaInvioDatiSpesaContoCapitale().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaInvioDatiSpesaContoCapitale().get(i)); 
				bean.getListaInvioDatiSpesaContoCapitale().add(lDatiContabiliBean);
			}
		}
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getFileXlsContoCapitale())) {
			bean.setFileXlsContoCapitale(AttributiDinamiciDatasource.buildDocumentBean(lDocumentoXmlOutBean.getFileXlsContoCapitale()));
		}
		bean.setNomeFileTracciatoXlsContoCapitale("Tracciato_SIB.xls");
		bean.setUriFileTracciatoXlsContoCapitale(ParametriDBUtil.getParametroDB(getSession(), "URI_TRACCIATO_XLS_SIB"));
		bean.setNoteContoCapitale(lDocumentoXmlOutBean.getNoteContoCapitale());	
			
		/* Dati spesa personale */
		/*
		bean.setListaDatiSpesaAnnualiXDetPersonale(lDocumentoXmlOutBean.getListaDatiSpesaAnnualiXDetPersonale() != null ? lDocumentoXmlOutBean.getListaDatiSpesaAnnualiXDetPersonale() : new ArrayList<DatiSpesaAnnualiXDetPersonaleXmlBean>());
		bean.setCapitoloDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getCapitoloDatiSpesaAnnuaXDetPers());
		bean.setArticoloDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getArticoloDatiSpesaAnnuaXDetPers());
		bean.setNumeroDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getNumeroDatiSpesaAnnuaXDetPers());	
		bean.setImportoDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getImportoDatiSpesaAnnuaXDetPers());
		*/
		
		if(isAttivoSIB(bean)) {
			
			populateListaVociPEGNoVerifDisp(bean);
			
			if(isAttivaRequestMovimentiDaAMC(bean)) {
				if(!bean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente()) {
					populateListaDatiContabiliSIBCorrente(bean);
				}
				if(!bean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale()) {
					populateListaDatiContabiliSIBContoCapitale(bean);
				}
			}
		}
		
		/****** [EMEND] ELIMINA RIGA PER EMENDAMENTI
		//TODEL togliere i dati di test e leggere da lDocumentoXmlOutBean
		List<EmendamentoBean> listaEmendamenti = new ArrayList<EmendamentoBean>();
		for (int i = 0; i < 5; i++) {
			EmendamentoBean emendamento = new EmendamentoBean();
			emendamento.setEstremiEmendamento("Emendamento " + i);
			emendamento.setTestoEmendamento("Questo è il testo dell'emendamento " + i);
			listaEmendamenti.add(emendamento);
		}
		
		for (int i = 5; i < 8; i++) {
			EmendamentoBean emendamento = new EmendamentoBean();
			emendamento.setEstremiEmendamento("Emendamento " + i);
			emendamento.setTestoEmendamento("Questo è il testo dell'emendamento " + i);
			emendamento.setNomeFileEmendamento(bean.getNomeFilePrimario());
			emendamento.setUriFileEmendamento(bean.getUriFilePrimario());
			emendamento.setInfoFileEmendamento(bean.getInfoFilePrimario());
			listaEmendamenti.add(emendamento);
		}
		
		bean.setListaEmendamenti(listaEmendamenti);
		****** [EMEND] ELIMINA RIGA PER EMENDAMENTI */
		
		
		/* Movimenti contabili - Contabilia */
		
		bean.setListaMovimentiContabilia(new ArrayList<MovimentiContabiliaXmlBean>());
		if(lDocumentoXmlOutBean.getListaMovimentiContabilia() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaMovimentiContabilia().size(); i++) {
				MovimentiContabiliaXmlBean lMovimentiContabiliaXmlBean = new MovimentiContabiliaXmlBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lMovimentiContabiliaXmlBean, lDocumentoXmlOutBean.getListaMovimentiContabilia().get(i)); 
				bean.getListaMovimentiContabilia().add(lMovimentiContabiliaXmlBean);
			}
		}
		
		bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
		bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
		bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());	
		
		if(isAttivoContabilia(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			populateListaMovimentiContabilia(bean);
		}
		
		// DA UTILIZZARE NEL CASO SI VOGLIANO RECUPERARE I MOVIMENTI DA CONTABILIA PER RISALVARLI IN BOZZA CON LA DETERMINA, QUANDO L'ITER E' GIA' CONCLUSO
		boolean isRecuperoMovimentiContabilia = getExtraparams().get("isRecuperoMovimentiContabilia") != null && "true".equalsIgnoreCase(getExtraparams().get("isRecuperoMovimentiContabilia"));		
		if(isRecuperoMovimentiContabilia && isAttivoContabilia(bean) /*&& (bean.getListaMovimentiContabilia() == null || bean.getListaMovimentiContabilia().size() == 0)*/) {
			populateListaMovimentiContabilia(bean);
			aggiornaMovimentiContabilia(bean);
		}
		
		/* Movimenti contabili - SICRA */
		
		bean.setListaInvioMovimentiContabiliSICRA(new ArrayList<MovimentiContabiliSICRABean>());
		if(lDocumentoXmlOutBean.getListaMovimentiContabiliSICRA() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaMovimentiContabiliSICRA().size(); i++) {
				MovimentiContabiliSICRABean lMovimentiContabiliSICRABean = new MovimentiContabiliSICRABean();
				lMovimentiContabiliSICRABean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lMovimentiContabiliSICRABean, lDocumentoXmlOutBean.getListaMovimentiContabiliSICRA().get(i)); 
				bean.getListaInvioMovimentiContabiliSICRA().add(lMovimentiContabiliSICRABean);
			}
		}

		// DECOMMENTARE (IN DEBUG) NEL CASO SI VOGLIA CANCELLARE UN MOVIMENTO SU STILO (CHE E' GIA' STATO CANCELLATO SU SICRA)
//		if(isAttivoSICRA(bean)) {
//			cancellaMovimentoSICRA(bean, numImpAcc);
//		}
		
		/* Aggregato/smistamento ACTA */
		
		bean.setCodAOOXSelNodoACTA(lDocumentoXmlOutBean.getCodAOOXSelNodoACTA()); // bean.setCodAOOXSelNodoACTA("A19000");	
		bean.setCodStrutturaXSelNodoACTA(lDocumentoXmlOutBean.getCodStrutturaXSelNodoACTA()); // bean.setCodStrutturaXSelNodoACTA("A1906A");
				
		bean.setFlgAggregatoACTA(lDocumentoXmlOutBean.getFlgAggregatoACTA() == Flag.SETTED);
		bean.setFlgSmistamentoACTA(lDocumentoXmlOutBean.getFlgSmistamentoACTA() == Flag.SETTED);
		bean.setFlgIndiceClassificazioneACTA(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA() != null && _OPZIONE_INDICE_CLASSIFICAZIONE_ACTA.equalsIgnoreCase(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA()));
		bean.setFlgFascicoloACTA(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA() != null && _OPZIONE_AGGREGATO_ACTA.equalsIgnoreCase(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA()));		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getCodIndiceClassificazioneACTA())) {
			bean.setCodIndiceClassificazioneACTA(lDocumentoXmlOutBean.getCodIndiceClassificazioneACTA());
			bean.setFlgPresenzaClassificazioneACTA(true);
		} else {
			bean.setCodIndiceClassificazioneACTA(null);
			bean.setFlgPresenzaClassificazioneACTA(false);
		}
		bean.setCodVoceTitolarioACTA(lDocumentoXmlOutBean.getCodVoceTitolarioACTA());
		bean.setCodFascicoloACTA(lDocumentoXmlOutBean.getCodFascicoloACTA());
		bean.setSuffissoCodFascicoloACTA(lDocumentoXmlOutBean.getSuffissoCodFascicoloACTA());
		bean.setIdFascicoloACTA(lDocumentoXmlOutBean.getIdFascicoloACTA());
		bean.setIdNodoSmistamentoACTA(lDocumentoXmlOutBean.getIdNodoSmistamentoACTA());
		bean.setDesNodoSmistamentoACTA(lDocumentoXmlOutBean.getDesNodoSmistamentoACTA());	
		
		return bean;
	}

	public NuovaPropostaAtto2CompletaBean nuovoComeCopia(NuovoAttoComeCopiaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				

		DmpkWfGetdatinuovoiterattocomecopiaBean input = new DmpkWfGetdatinuovoiterattocomecopiaBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
		input.setFlgtiponumerazionein(bean.getTipoNumerazioneCopia());
		input.setSiglanumerazionein(bean.getSiglaCopia());
		input.setNumeroin(StringUtils.isNotBlank(bean.getNumeroCopia()) ? new BigDecimal(bean.getNumeroCopia()) : null);
		input.setAnnonumerazionein(StringUtils.isNotBlank(bean.getAnnoCopia()) ? new BigDecimal(bean.getAnnoCopia()) : null);
		input.setIdproctypedaavviarein(StringUtils.isNotBlank(bean.getIdTipoProc()) ? new BigDecimal(bean.getIdTipoProc()) : null);

		DmpkWfGetdatinuovoiterattocomecopia lDmpkWfGetdatinuovoiterattocomecopia = new DmpkWfGetdatinuovoiterattocomecopia();
		StoreResultBean<DmpkWfGetdatinuovoiterattocomecopiaBean> storeOutput = lDmpkWfGetdatinuovoiterattocomecopia.execute(getLocale(), loginBean, input);
		
		if (storeOutput.isInError()) {
			throw new StoreException(storeOutput);
		} 
		
		NuovaPropostaAtto2CompletaBean copiaBean = new NuovaPropostaAtto2CompletaBean();
		
		if (storeOutput.getResultBean() != null) {
			copiaBean.setIdUd(storeOutput.getResultBean().getIduddacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIduddacopiareout()) : null);
			copiaBean.setIdProcess(storeOutput.getResultBean().getIdprocessdacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIdprocessdacopiareout()) : null);
		}
		
		copiaBean = get(copiaBean);
		
		if(StringUtils.isNotBlank(copiaBean.getRowidDoc())) {
			
			AttributiDinamiciInputBean attributiDinamiciDocInput = new AttributiDinamiciInputBean();
			attributiDinamiciDocInput.setNomeTabella("DMT_DOCUMENTS");
			attributiDinamiciDocInput.setTipoEntita(copiaBean.getTipoDocumento());
			attributiDinamiciDocInput.setRowId(copiaBean.getRowidDoc());
			
			AttributiDinamiciOutputBean attributiDinamiciDocOuput = getAttributiDinamiciDatasource().call(attributiDinamiciDocInput);	
			
			if (attributiDinamiciDocOuput.getAttributiAdd() != null) {
				Map<String, Object> attributiDinamiciDocValues = new HashMap<String, Object>();
				for (AttributoBean attr : attributiDinamiciDocOuput.getAttributiAdd()) {
					if ("LISTA".equals(attr.getTipo())) {
						if (attributiDinamiciDocOuput.getMappaValoriAttrLista().get(attr.getNome()) != null) {
							List<Map<String, Object>> valoriLista = new ArrayList<Map<String, Object>>();
							for (Map<String, String> mapValori : attributiDinamiciDocOuput.getMappaValoriAttrLista().get(attr.getNome())) {
								Map<String, Object> valori = new HashMap<String, Object>(mapValori);
								for (DettColonnaAttributoListaBean dett : attributiDinamiciDocOuput.getMappaDettAttrLista().get(attr.getNome())) {
									if ("CHECK".equals(dett.getTipo())) {
										if (valori.get((String) dett.getNome()) != null
												&& !"".equals(valori.get((String) dett.getNome()))) {
											String valueStr = valori.get((String) dett.getNome()) != null ? ((String) valori.get((String) dett.getNome())).trim() : "";
											Boolean value = Boolean.FALSE;
											if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
												value = Boolean.TRUE;
											}
											valori.put((String) dett.getNome(), value);
										}
									} else if ("DOCUMENT".equals(dett.getTipo())) {
										String value = mapValori.get((String) dett.getNome());
										valori.put((String) dett.getNome(), value != null && !"".equals(value) ? attributiDinamiciDocOuput.getMappaDocumenti().get(value)
												: null);
									}
								}
								valoriLista.add(valori);
							}
							attributiDinamiciDocValues.put(attr.getNome(), valoriLista);
						}
					} else if ("DOCUMENT".equals(attr.getTipo())) {
						String value = attr.getValore();
						attributiDinamiciDocValues.put(attr.getNome(), value != null && !"".equals(value) ? attributiDinamiciDocOuput.getMappaDocumenti().get(value) : null);
					} else {
						if (attr.getValore() != null && !"".equals(attr.getValore())) {
							if ("CHECK".equals(attr.getTipo())) {
								String valueStr = attr.getValore() != null ? attr.getValore().trim() : "";
								Boolean value = Boolean.FALSE;
								if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
									value = Boolean.TRUE;
								}
								attributiDinamiciDocValues.put(attr.getNome(), value);
							} else
								attributiDinamiciDocValues.put(attr.getNome(), attr.getValore());
						}
	
					}
				}
				copiaBean.setValori(attributiDinamiciDocValues);
			}
		}
		
		return copiaBean;
	}

	private void populateListaVociPEGNoVerifDisp(NuovaPropostaAtto2CompletaBean bean) throws Exception {
	
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|DICTIONARY_ENTRY|*|CAP_PEG_NO_VERIF_DISPONIBILITA";
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
		
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
				
		List<SimpleKeyValueBean> listaVociPEGNoVerifDisp = new ArrayList<SimpleKeyValueBean>();
		if(!lStoreResultBean.isInError()) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			for (XmlListaSimpleBean lXmlListaSimpleBean : XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class)) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lXmlListaSimpleBean.getKey());
				lSimpleKeyValueBean.setValue(lXmlListaSimpleBean.getValue());
				listaVociPEGNoVerifDisp.add(lSimpleKeyValueBean);
			}		
		} 
		
		bean.setListaVociPEGNoVerifDisp(listaVociPEGNoVerifDisp);
	}
	
	public NuovaPropostaAtto2CompletaBean getListaDatiContabiliSIBCorrente(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(isAttivoSIB(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			populateListaDatiContabiliSIBCorrente(bean);
		}
		return bean;
	}
	
	private void populateListaDatiContabiliSIBCorrente(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		populateListaDatiContabiliSIBCorrente(bean, true);
	}

	private void populateListaDatiContabiliSIBCorrente(NuovaPropostaAtto2CompletaBean bean, boolean skipError) throws Exception {
		try {			
			List<DatiContabiliBean> listaDatiContabiliSIBCorrente = new ArrayList<DatiContabiliBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) {
				List<DatiContabiliBean> listaDatiContabiliSIB = getSIBDataSource().getListaDatiContabiliCorrente(bean);
				if(listaDatiContabiliSIB != null) {
					for(DatiContabiliBean lDatiContabiliBean : listaDatiContabiliSIB) {
						listaDatiContabiliSIBCorrente.add(lDatiContabiliBean);					
					}
				}
			}
			bean.setListaDatiContabiliSIBCorrente(listaDatiContabiliSIBCorrente);
			bean.setErrorMessageDatiContabiliSIBCorrente(null);			
		} catch(Exception e) {
			bean.setListaDatiContabiliSIBCorrente(new ArrayList<DatiContabiliBean>());
			bean.setErrorMessageDatiContabiliSIBCorrente("<font color=\"red\">Si è verificato un'errore durante la chiamata ai servizi di integrazione con SIB</font>");
			if(skipError) {
				addMessage("Si è verificato un'errore durante il recupero dei dati contabili da SIB", "", MessageType.WARNING);
			} else {
				throw new StoreException("Si è verificato un'errore durante il recupero dei dati contabili da SIB");
			}		
		}		
	}
	
	public NuovaPropostaAtto2CompletaBean getListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(isAttivoSIB(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			populateListaDatiContabiliSIBContoCapitale(bean);
		}
		return bean;
	}
	
	private void populateListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		populateListaDatiContabiliSIBContoCapitale(bean, true);
	}

	private void populateListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2CompletaBean bean, boolean skipError) throws Exception {
		try {	
			List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale = new ArrayList<DatiContabiliBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) {
				List<DatiContabiliBean> listaDatiContabiliSIB = getSIBDataSource().getListaDatiContabiliContoCapitale(bean);
				if(listaDatiContabiliSIB != null) {
					for(DatiContabiliBean lDatiContabiliBean : listaDatiContabiliSIB) {
						listaDatiContabiliSIBContoCapitale.add(lDatiContabiliBean);					
					}
				}
			}
			bean.setListaDatiContabiliSIBContoCapitale(listaDatiContabiliSIBContoCapitale);
			bean.setErrorMessageDatiContabiliSIBContoCapitale(null);			
		} catch(Exception e) {
			bean.setListaDatiContabiliSIBContoCapitale(new ArrayList<DatiContabiliBean>());
			bean.setErrorMessageDatiContabiliSIBContoCapitale("<font color=\"red\">Si è verificato un'errore durante la chiamata ai servizi di integrazione con SIB</font>");
			if(skipError) {
				addMessage("Si è verificato un'errore durante il recupero dei dati contabili da SIB", "", MessageType.WARNING);
			} else {
				throw new StoreException("Si è verificato un'errore durante il recupero dei dati contabili da SIB");
			}		
		}		
	}
	
	public NuovaPropostaAtto2CompletaBean getListaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(isAttivoContabilia(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			// Recupero i dati della struttura da passare ai servizi di Contabilia
			try {
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
				String idProcess = getExtraparams().get("idProcess");
				String taskName = getExtraparams().get("taskName");
				RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
				lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
				lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(getExtraparams().get("flgSoloAbilAzioni"));
				lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
				lRecuperaDocumentoInBean.setIdProcess(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
				lRecuperaDocumentoInBean.setTaskName(StringUtils.isNotBlank(taskName) ? taskName : "#NONE");
				RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
				RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
				if(lRecuperaDocumentoOutBean.isInError()) {
					throw new StoreException(lRecuperaDocumentoOutBean);
				}
				DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();				
				bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
				bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
				bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());				
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}		
			populateListaMovimentiContabilia(bean);
		}
		return bean;
	}
	
	private void populateListaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {	
		populateListaMovimentiContabilia(bean, true);
	}

	private void populateListaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean, boolean skipError) throws Exception {	
		try {	
			List<MovimentiContabiliaXmlBean> listaMovimentiContabilia = new ArrayList<MovimentiContabiliaXmlBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC()) && bean.getFlgSpesa() != null && _FLG_SI.equalsIgnoreCase(bean.getFlgSpesa())) {					
				String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
				if(lSistAMC != null) {
					if("CONTABILIA".equalsIgnoreCase(lSistAMC)) {
						listaMovimentiContabilia.addAll(getContabiliaDataSource().ricercaImpegno(bean));
						listaMovimentiContabilia.addAll(getContabiliaDataSource().ricercaAccertamento(bean));
					} else if("CONTABILIA2".equalsIgnoreCase(lSistAMC)) {
						List<MovimentiContabiliaXmlBean> listaMovimentiContabilia2 = getContabiliaDataSource().ricercaMovimentoGestione(bean);
						if(listaMovimentiContabilia2 != null && listaMovimentiContabilia2.size() == 0 && StringUtils.isNotBlank(bean.getNumeroRegistrazione())) {
							// se non trovo niente con la numerazione di proposta provo a passare la numerazione definitiva
							listaMovimentiContabilia2 = getContabiliaDataSource().ricercaMovimentoGestioneWithNumDefinitiva(bean);
						}
						listaMovimentiContabilia.addAll(listaMovimentiContabilia2);	
					}
				}
			}
			bean.setListaMovimentiContabilia(listaMovimentiContabilia);
			bean.setErrorMessageMovimentiContabilia(null);
		} catch(Exception e) {
			bean.setListaMovimentiContabilia(new ArrayList<MovimentiContabiliaXmlBean>());
			bean.setErrorMessageMovimentiContabilia("<font color=\"red\">Si è verificato un'errore durante la chiamata ai servizi di integrazione con Contabilia</font>");
			if(skipError) {
				addMessage("Si è verificato un'errore durante il recupero dei movimenti da Contabilia", "", MessageType.WARNING);
			} else {
				throw new StoreException("Si è verificato un'errore durante il recupero dei movimenti da Contabilia");
			}
		}	
	}
	
	public void cancellaMovimentoSICRA(NuovaPropostaAtto2CompletaBean bean, String numImpAcc) {
		if (bean.getListaInvioMovimentiContabiliSICRA() != null) {
			int posMovimentoToRemove = -1;
			for(int i = 0; i < bean.getListaInvioMovimentiContabiliSICRA().size(); i++) {
				if(StringUtils.isNotBlank(bean.getListaInvioMovimentiContabiliSICRA().get(i).getNumeroImpAcc()) && StringUtils.isNotBlank(numImpAcc) && bean.getListaInvioMovimentiContabiliSICRA().get(i).getNumeroImpAcc().equals(numImpAcc)) {
					posMovimentoToRemove = i;
				}
			}
			if(posMovimentoToRemove != -1) {
				bean.setEsitoSetMovimentiAttoSICRA("OK");
				bean.getListaInvioMovimentiContabiliSICRA().remove(posMovimentoToRemove);
			}
		}
		try {
			aggiornaMovimentiSICRA(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public NuovaPropostaAtto2CompletaBean add(NuovaPropostaAtto2CompletaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();	
		
		// Copio i dati a maschera nel bean di salvataggio
		populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBeanInput, bean);
		
		boolean isNumPropostaDiffXStruttura = getExtraparams().get("isNumPropostaDiffXStruttura") != null && "true".equalsIgnoreCase(getExtraparams().get("isNumPropostaDiffXStruttura"));
		
		String siglaRegSuffix = null;
		
		if(isNumPropostaDiffXStruttura) {		
			try {
				DmpkUtilityFindsoggettoinrubricaBean input = new DmpkUtilityFindsoggettoinrubricaBean();
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : loginBean.getIdUser());
				input.setIddominioin(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio());
				input.setFlgcompletadatidarubrin(1);
				input.setFlginorganigrammain("UO");
				
				DatiSoggXMLIOBean lDatiSoggXMLIOBean = new DatiSoggXMLIOBean();
				if (StringUtils.isNotBlank(bean.getCodUfficioProponente())) {
					lDatiSoggXMLIOBean.setCodRapidoSoggetto(bean.getCodUfficioProponente());
				}
	
				Riga lRigaIn = new Riga();
				Colonna lColonnaIn = new Colonna();
				lColonnaIn.setContent(bean.getCodUfficioProponente());
				lColonnaIn.setNro(new BigInteger("27"));
				lRigaIn.getColonna().add(lColonnaIn);
			
				StringWriter lStringWriter = new StringWriter();
				SingletonJAXBContext.getInstance().createMarshaller().marshal(lRigaIn, lStringWriter);
				input.setDatisoggxmlio(lStringWriter.toString());
	
				SchemaBean lSchemaBean = new SchemaBean();
				lSchemaBean.setSchema(loginBean.getSchema());
				DmpkUtilityFindsoggettoinrubrica lDmpkUtilityFindsoggettoinrubrica = new DmpkUtilityFindsoggettoinrubrica();
				StoreResultBean<DmpkUtilityFindsoggettoinrubricaBean> output = lDmpkUtilityFindsoggettoinrubrica.execute(getLocale(), lSchemaBean, input);
			
				if (output.isInError()) {
					throw new StoreException(output);
				}
				
				if (StringUtils.isNotBlank(output.getResultBean().getDatisoggxmlio())) {
					StringReader lStringReader = new StringReader(output.getResultBean().getDatisoggxmlio());
					Riga lRigaOut = (Riga) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(lStringReader);
					if (lRigaOut != null) {
						for (int i = 0; i < lRigaOut.getColonna().size(); i++) {
							if (lRigaOut.getColonna().get(i).getNro().toString().equals("40")) {
								siglaRegSuffix = lRigaOut.getColonna().get(i).getContent();
							}
						}
					}
				}
			
			} catch (Exception e) {
				logger.error(e);
				throw new StoreException("Si è verificato un errore durante il recupero del codice struttura da appendere alla sigla di numerazione della proposta");
			}
		}
		
		// Aggiungo i valori dei tab dinamici da salvare in avvio, tutti con il suffisso _Doc
		lProtocollazioneBeanInput.setValori(new HashMap<String, Object>());		
		if (bean.getValori() != null) {
			for (String attrName : bean.getValori().keySet()) {
				lProtocollazioneBeanInput.getValori().put(attrName + "_Doc", bean.getValori().get(attrName));
			}
		}		
		lProtocollazioneBeanInput.setTipiValori(new HashMap<String, String>());
		if (bean.getTipiValori() != null) {
			for (String attrName : bean.getTipiValori().keySet()) {
				lProtocollazioneBeanInput.getTipiValori().put(attrName + "_Doc", bean.getTipiValori().get(attrName));
			}
		}
		
		// per la numerazione da dare all'avvio dell'atto
		lProtocollazioneBeanInput.setCodCategoriaProtocollo(bean.getCategoriaRegAvvio());			
		if(StringUtils.isNotBlank(siglaRegSuffix)) {
			lProtocollazioneBeanInput.setSiglaProtocollo(bean.getSiglaRegAvvio() + siglaRegSuffix);		
		} else {
			lProtocollazioneBeanInput.setSiglaProtocollo(bean.getSiglaRegAvvio());			
		}
		
		try {
			ProtocollazioneBean lProtocollazioneBeanOutput = getProtocolloDataSource(bean).add(lProtocollazioneBeanInput);
			bean.setIdUd(lProtocollazioneBeanOutput.getIdUd() != null ? String.valueOf(lProtocollazioneBeanOutput.getIdUd().longValue()) : null);
			bean.setRowidDoc(lProtocollazioneBeanOutput.getRowidDoc());
			bean.setIdDocPrimario(lProtocollazioneBeanOutput.getIdDocPrimario() != null ? String.valueOf(lProtocollazioneBeanOutput.getIdDocPrimario().longValue()) : null);
			bean.setSiglaRegProvvisoria(lProtocollazioneBeanOutput.getSiglaProtocollo());
			bean.setNumeroRegProvvisoria(lProtocollazioneBeanOutput.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBeanOutput.getNroProtocollo().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBeanOutput.getDataProtocollo());
			bean.setAnnoRegProvvisoria(lProtocollazioneBeanOutput.getAnnoProtocollo());
			bean.setDesUserRegProvvisoria(lProtocollazioneBeanOutput.getDesUserProtocollo());
			bean.setDesUORegProvvisoria(lProtocollazioneBeanOutput.getDesUOProtocollo());	
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		bean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di SIB, con la versione con omissis di oggettoHtml
		
		// Chiamo il servizio CreaProposta di SIB
		if(isAttivoSIB(bean)) {
			bean.setEventoSIB("creaProposta");
			getSIBDataSource().creaProposta(bean);	 		
			aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB
		}													
		
		if(isAttivoContabilia(bean)) {
			boolean isAvvioLiquidazioneContabilia = getExtraparams().get("isAvvioLiquidazioneContabilia") != null && "true".equalsIgnoreCase(getExtraparams().get("isAvvioLiquidazioneContabilia"));
			if(isAvvioLiquidazioneContabilia) {
				try {
					String idProcess = getExtraparams().get("idProcess");
					String taskName = getExtraparams().get("taskName");
					RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
					lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
					lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(getExtraparams().get("flgSoloAbilAzioni"));
					lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
					lRecuperaDocumentoInBean.setIdProcess(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
					lRecuperaDocumentoInBean.setTaskName(StringUtils.isNotBlank(taskName) ? taskName : "#NONE");
					RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
					RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
					if(lRecuperaDocumentoOutBean.isInError()) {
						throw new StoreException(lRecuperaDocumentoOutBean);
					}
					DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();	
					bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
					bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
					bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());
					RegNumPrincipale lRegNumPrincipale = lDocumentoXmlOutBean.getEstremiRegistrazione();
					if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria())) {
						// Numerazione finale
						bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
						bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
						bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
						bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
						bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
						bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
						// Numerazione provvisoria
						bean.setSiglaRegProvvisoria(lDocumentoXmlOutBean.getSiglaRegNumerazioneSecondaria());
						bean.setNumeroRegProvvisoria(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria());
						bean.setDataRegProvvisoria(lDocumentoXmlOutBean.getDataRegistrazioneNumerazioneSecondaria());	
						bean.setAnnoRegProvvisoria(lDocumentoXmlOutBean.getAnnoRegNumerazioneSecondaria());						
					}  else if(lRegNumPrincipale != null && lRegNumPrincipale.getCodCategoria() != null && "R".equals(lRegNumPrincipale.getCodCategoria())) {
						// Numerazione finale
						bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
						bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
						bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
						bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
						bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
						bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
					} else {
						// Numerazione provvisoria
						bean.setSiglaRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
						bean.setNumeroRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
						bean.setDataRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
						bean.setAnnoRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);		
						bean.setDesUserRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
						bean.setDesUORegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
					}		
					bean.setEstremiRepertorioPerStruttura(lDocumentoXmlOutBean.getEstremiRepertorioPerStruttura());	
					bean.setIdUdLiquidazione(lDocumentoXmlOutBean.getIdUdLiquidazione());
					bean.setIdDocPrimarioLiquidazione(lDocumentoXmlOutBean.getIdDocPrimarioLiquidazione());
					bean.setCodTipoLiquidazioneXContabilia(lDocumentoXmlOutBean.getCodTipoLiquidazioneXContabilia());
					bean.setSiglaRegLiquidazione(lDocumentoXmlOutBean.getSiglaRegLiquidazione());
					bean.setAnnoRegLiquidazione(lDocumentoXmlOutBean.getAnnoRegLiquidazione());
					bean.setNroRegLiquidazione(lDocumentoXmlOutBean.getNroRegLiquidazione());
					bean.setDataAdozioneLiquidazione(lDocumentoXmlOutBean.getDataAdozioneLiquidazione());
					bean.setEstremiAttoLiquidazione(lDocumentoXmlOutBean.getEstremiAttoLiquidazione());
				} catch (StoreException se) {
					throw se;
				} catch (Exception e) {
					throw new StoreException(e.getMessage());
				}
				bean.setEventoContabilia("creaLiquidazione");
				getContabiliaDataSource().creaLiquidazione(bean);
				aggiornaDatiLiquidazioneContabilia(bean); // salvo i dati relativi a Contabilia in DB			
				if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
					throw new StoreException(bean.getErrMsgEventoContabilia());					
				}
			} else if(bean.getFlgSpesa() != null && bean.getFlgSpesa().toUpperCase().startsWith(_FLG_SI)) {
				try {
					String idProcess = getExtraparams().get("idProcess");
					String taskName = getExtraparams().get("taskName");
					RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
					lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
					lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(getExtraparams().get("flgSoloAbilAzioni"));
					lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
					lRecuperaDocumentoInBean.setIdProcess(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
					lRecuperaDocumentoInBean.setTaskName(StringUtils.isNotBlank(taskName) ? taskName : "#NONE");
					RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
					RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
					if(lRecuperaDocumentoOutBean.isInError()) {
						throw new StoreException(lRecuperaDocumentoOutBean);
					}
					DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();				
					bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
					bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
					bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());					
				} catch (StoreException se) {
					throw se;
				} catch (Exception e) {
					throw new StoreException(e.getMessage());
				}
				bean.setEventoContabilia("invioProposta");
				getContabiliaDataSource().invioProposta(bean);
				aggiornaDatiPropostaContabilia(bean); // salvo i dati relativi a Contabilia in DB
				if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
					throw new StoreException(bean.getErrMsgEventoContabilia());
				}
			}
		}
		
		return bean;
	}

	@Override
	public NuovaPropostaAtto2CompletaBean update(NuovaPropostaAtto2CompletaBean bean, NuovaPropostaAtto2CompletaBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if (isAttivoSICRA(bean) && bean.getListaInvioMovimentiContabiliSICRA() != null && bean.getListaInvioMovimentiContabiliSICRA().size() > 0 && bean.getFlgSpesa() != null && !_FLG_SI.equals(bean.getFlgSpesa())) {
			if(_FLG_NO.equals(bean.getFlgSpesa())) {
				throw new StoreException("E' stato indicato che l'atto è senza rilevanza contabile: non è consentito indicare dei movimenti contabili");
			} else if(getFLG_SI_SENZA_VLD_RIL_IMP().equals(bean.getFlgSpesa())) {
				throw new StoreException("E' stato indicato che l'atto è con rilevanza contabile ma senza movimenti: non è consentito indicare dei movimenti contabili");
			} 
		}
		
		if(isAttivaRequestMovimentiDaAMC(bean) && isAttivaSalvataggioMovimentiDaAMC(bean)) {			
			// in salvataggio se non ho dei movimenti contabili rifaccio la chiamata ad AMC e se va' in errore blocco tutto
			if(isAttivoSIB(bean)) {
				if (bean.getListaDatiContabiliSIBCorrente() == null || bean.getListaDatiContabiliSIBCorrente().isEmpty()) {
					populateListaDatiContabiliSIBCorrente(bean, false);	
				}
				if (bean.getListaDatiContabiliSIBContoCapitale() == null || bean.getListaDatiContabiliSIBContoCapitale().isEmpty()) {
					populateListaDatiContabiliSIBContoCapitale(bean, false);	
				}				
			}
			if(isAttivoContabilia(bean) && (bean.getListaMovimentiContabilia() == null || bean.getListaMovimentiContabilia().isEmpty())) {
				populateListaMovimentiContabilia(bean, false);				
			}
		}
		
		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();
		lProtocollazioneBeanInput.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		try {
			lProtocollazioneBeanInput = getProtocolloDataSource(bean).get(lProtocollazioneBeanInput);
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		
		
		// se l'atto risulta già firmato devo inibire il versionamento del dispositivo (vers. integrale e omissis) per non sovrascrivere il file unione
		boolean isAttoFirmato = bean.getInfoFilePrimario() != null && bean.getInfoFilePrimario().isFirmato();
		
		// Genero il file dispositivo atto (vers. integrale e omissis) da passare in salvataggio
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "VERS_DISPOSITIVO_NUOVA_PROPOSTA_ATTO_2")) {
			try {
				boolean versionaFileDispositivo = getExtraparams().get("versionaFileDispositivo") != null && "true".equalsIgnoreCase(getExtraparams().get("versionaFileDispositivo"));
				if(versionaFileDispositivo && !isAttoFirmato) {
					bean.setFlgMostraDatiSensibili(true); // per generare la vers. integrale			
	//				FileDaFirmareBean lFileDaFirmareBean = generaDispositivoDaModello(bean, false); // in questo modo lo salverebbe in .doc, come faceva prima	
					FileDaFirmareBean lFileDaFirmareBean = generaDispositivoDaModello(bean, true); // ma noi vogliamo salvarlo in pdf
					if(lFileDaFirmareBean != null) {
						aggiornaPrimario(bean, lFileDaFirmareBean);
					}
					boolean hasPrimarioDatiSensibili = getExtraparams().get("hasPrimarioDatiSensibili") != null && "true".equalsIgnoreCase(getExtraparams().get("hasPrimarioDatiSensibili"));		
					if(hasPrimarioDatiSensibili) {
						bean.setFlgMostraDatiSensibili(false); // per generare la vers. con omissis			
	//					FileDaFirmareBean lFileDaFirmareBeanOmissis = generaDispositivoDaModello(bean, false); // in questo modo lo salverebbe in .doc, come faceva prima	
						FileDaFirmareBean lFileDaFirmareBeanOmissis = generaDispositivoDaModello(bean, true); // ma noi vogliamo salvarlo in pdf
						if(lFileDaFirmareBeanOmissis != null) {
							aggiornaPrimarioOmissis(bean, lFileDaFirmareBeanOmissis);
						}				
					} else {
						// se il file primario non contiene dati sensibili devo annullare la versione
						bean.setUriFilePrimarioOmissis(null);
						bean.setNomeFilePrimarioOmissis(null);
						bean.setInfoFilePrimarioOmissis(null);
					}
				}
			} catch(Exception e) {
				throw new StoreException("Si è verificato un errore durante la generazione della nuova versione pdf dell'atto con i dati attuali: " + e.getMessage());
			}
		}
		
		// Copio i dati a maschera nel bean di salvataggio
		populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBeanInput, bean);
		
		// Aggiungo i valori dei tab dinamici da salvare in avvio, tutti con il suffisso _Doc
		lProtocollazioneBeanInput.setValori(new HashMap<String, Object>());		
		if (bean.getValori() != null) {
			for (String attrName : bean.getValori().keySet()) {
				lProtocollazioneBeanInput.getValori().put(attrName + "_Doc", bean.getValori().get(attrName));
			}
		}		
		lProtocollazioneBeanInput.setTipiValori(new HashMap<String, String>());
		if (bean.getTipiValori() != null) {
			for (String attrName : bean.getTipiValori().keySet()) {
				lProtocollazioneBeanInput.getTipiValori().put(attrName + "_Doc", bean.getTipiValori().get(attrName));
			}
		}
		
		// Passo le numerazioni da dare in salvataggio
		if (lProtocollazioneBeanInput.getNumeroNumerazioneSecondaria() == null) {
			List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
			if(StringUtils.isNotBlank(getExtraparams().get("siglaRegistroAtto"))) {
				TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();			
				lTipoNumerazioneBean.setSigla(getExtraparams().get("siglaRegistroAtto"));
				lTipoNumerazioneBean.setCategoria("R");		
				listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);
			}
			if(StringUtils.isNotBlank(getExtraparams().get("siglaRegistroAtto2"))) {
				TipoNumerazioneBean lTipoNumerazione2Bean = new TipoNumerazioneBean();			
				lTipoNumerazione2Bean.setSigla(getExtraparams().get("siglaRegistroAtto2"));
				lTipoNumerazione2Bean.setCategoria("R");		
				listaTipiNumerazioneDaDare.add(lTipoNumerazione2Bean);
			}
			lProtocollazioneBeanInput.setListaTipiNumerazioneDaDare(listaTipiNumerazioneDaDare);
		}
		
		try {
			ProtocollazioneBean lProtocollazioneBeanOutput = getProtocolloDataSource(bean).update(lProtocollazioneBeanInput, null);
			if (lProtocollazioneBeanOutput.getFileInErrors() != null && lProtocollazioneBeanOutput.getFileInErrors().size() > 0) {
				for (String error : lProtocollazioneBeanOutput.getFileInErrors().values()) {
					logger.error(error);					
				}
				throw new StoreException("Si è verificato un errore durante il salvataggio: alcuni dei file sono andati in errore");
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 
		
		// Recupero i dati di registrazione 
		// se in salvataggio ho passato una nuova numerazione da dare devo recuperarmela
		// devo anche recuperarmi i dati della struttura da passare ai servizi di Contabilia
		try {
			String idProcess = getExtraparams().get("idProcess");
			String taskName = getExtraparams().get("taskName");
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
			lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(getExtraparams().get("flgSoloAbilAzioni"));
			lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
			lRecuperaDocumentoInBean.setIdProcess(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
			lRecuperaDocumentoInBean.setTaskName(StringUtils.isNotBlank(taskName) ? taskName : "#NONE");
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
			if(lRecuperaDocumentoOutBean.isInError()) {
				throw new StoreException(lRecuperaDocumentoOutBean);
			}
			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();				
			bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
			bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
			bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());
			RegNumPrincipale lRegNumPrincipale = lDocumentoXmlOutBean.getEstremiRegistrazione();
			if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria())) {
				// Numerazione finale
				bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
				bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
				// Numerazione provvisoria
				bean.setSiglaRegProvvisoria(lDocumentoXmlOutBean.getSiglaRegNumerazioneSecondaria());
				bean.setNumeroRegProvvisoria(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria());
				bean.setDataRegProvvisoria(lDocumentoXmlOutBean.getDataRegistrazioneNumerazioneSecondaria());	
				bean.setAnnoRegProvvisoria(lDocumentoXmlOutBean.getAnnoRegNumerazioneSecondaria());						
			}  else if(lRegNumPrincipale != null && lRegNumPrincipale.getCodCategoria() != null && "R".equals(lRegNumPrincipale.getCodCategoria())) {
				// Numerazione finale
				bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
				bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
			} else {
				// Numerazione provvisoria
				bean.setSiglaRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setAnnoRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);		
				bean.setDesUserRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
			}
			bean.setEstremiRepertorioPerStruttura(lDocumentoXmlOutBean.getEstremiRepertorioPerStruttura());
			bean.setIdUdLiquidazione(lDocumentoXmlOutBean.getIdUdLiquidazione());
			bean.setIdDocPrimarioLiquidazione(lDocumentoXmlOutBean.getIdDocPrimarioLiquidazione());
			bean.setCodTipoLiquidazioneXContabilia(lDocumentoXmlOutBean.getCodTipoLiquidazioneXContabilia());
			bean.setSiglaRegLiquidazione(lDocumentoXmlOutBean.getSiglaRegLiquidazione());			
			bean.setAnnoRegLiquidazione(lDocumentoXmlOutBean.getAnnoRegLiquidazione());
			bean.setNroRegLiquidazione(lDocumentoXmlOutBean.getNroRegLiquidazione());
			bean.setDataAdozioneLiquidazione(lDocumentoXmlOutBean.getDataAdozioneLiquidazione());
			bean.setEstremiAttoLiquidazione(lDocumentoXmlOutBean.getEstremiAttoLiquidazione());
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		
		
		bean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml()));// devo settare l'oggetto, da passare ai servizi di SIB, con la versione con omissis di oggettoHtml
		
		// Chiamo il servizio CreaProposta o AggiornaAtto di SIB
		if(isAttivoSIB(bean)) {		
			if (StringUtils.isBlank(bean.getIdPropostaAMC())) {			
				bean.setEventoSIB("creaProposta");
				getSIBDataSource().creaProposta(bean);											 		
				aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB		
			} else {			
				getSIBDataSource().aggiornaAtto(bean);			
				if(StringUtils.isNotBlank(bean.getEventoSIB())) {
					if(bean.getEsitoEventoSIB() != null && bean.getEsitoEventoSIB().equals("KO")) {
						addMessage(bean.getErrMsgEventoSIB(), "", MessageType.WARNING);
					}
				}			
				aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB			
			}
		}
		
		if(isAttivoContabilia(bean)) {
			if(bean.getEventoContabilia() != null && "creaLiquidazione".equalsIgnoreCase(bean.getEventoContabilia())) {
				getContabiliaDataSource().creaLiquidazione(bean);
				aggiornaDatiLiquidazioneContabilia(bean); // salvo i dati relativi a Contabilia in DB			
				if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
					throw new StoreException(bean.getErrMsgEventoContabilia());					
				}
			} else if(bean.getFlgSpesa() != null && bean.getFlgSpesa().toUpperCase().startsWith(_FLG_SI)) {	
				if(StringUtils.isBlank(bean.getIdPropostaAMC())) {
					bean.setEventoContabilia("invioProposta");
					getContabiliaDataSource().invioProposta(bean);
					aggiornaDatiPropostaContabilia(bean); // salvo i dati relativi a Contabilia in DB	
					if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
						throw new StoreException(bean.getErrMsgEventoContabilia());
					}
				}
				if(StringUtils.isNotBlank(bean.getEventoContabilia())) {
					if("aggiornaProposta".equalsIgnoreCase(bean.getEventoContabilia())) {
						getContabiliaDataSource().aggiornaProposta(bean);
					} else if("bloccoDatiProposta".equalsIgnoreCase(bean.getEventoContabilia())) {
						getContabiliaDataSource().bloccoDatiProposta(bean);				
					} else if("invioAttoDef".equalsIgnoreCase(bean.getEventoContabilia())) {
						getContabiliaDataSource().invioAttoDef(bean);				
					} else if("invioAttoDefEsec".equalsIgnoreCase(bean.getEventoContabilia())) {
						getContabiliaDataSource().invioAttoDefEsec(bean);				
					} else if("invioAttoEsec".equalsIgnoreCase(bean.getEventoContabilia())) {
						getContabiliaDataSource().invioAttoEsec(bean);				
					} else if("sbloccoDatiProposta".equalsIgnoreCase(bean.getEventoContabilia())) {
						getContabiliaDataSource().sbloccoDatiProposta(bean);				
					} else if("annullamentoProposta".equalsIgnoreCase(bean.getEventoContabilia())) {
						getContabiliaDataSource().annullamentoProposta(bean);				
					}
					aggiornaDatiPropostaContabilia(bean); // salvo i dati relativi a Contabilia in DB			
					if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
						if("aggiornaProposta".equalsIgnoreCase(bean.getEventoContabilia())) {
							addMessage(bean.getErrMsgEventoContabilia(), "", MessageType.WARNING);
						} else {
							throw new StoreException(bean.getErrMsgEventoContabilia());
						}
					} 
				}				
			}
		}
		
		if(isAttivoSICRA(bean)) {
			if(bean.getFlgSpesa() != null && bean.getFlgSpesa().toUpperCase().startsWith(_FLG_SI)) {	
				if(_FLG_SI.equals(bean.getFlgSpesa())) {
					getSICRADataSource().setMovimentiAtto(bean);
					aggiornaMovimentiSICRA(bean); // per aggiornare i movimenti contabili di SICRA in DB
				}
				if(StringUtils.isNotBlank(bean.getEventoSICRA())) {
					if("archiviaAtto".equalsIgnoreCase(bean.getEventoSICRA())) {
						getSICRADataSource().archiviaAtto(bean);
					} else if("setEsecutivitaAtto".equalsIgnoreCase(bean.getEventoSICRA())) {
						getSICRADataSource().setEsecutivitaAtto(bean);				
					} 
				}
				aggiornaDatiEventoSICRA(bean); // per aggiornare esito, data ed eventuale messaggio di errore dell'evento SICRA in DB
				if(bean.getEsitoEventoSICRA() != null && bean.getEsitoEventoSICRA().equals("KO")) {
					throw new StoreException(bean.getErrMsgEventoSICRA());
				}
			}
		}
		
		boolean flgProtocollazioneProsa = getExtraparams().get("flgProtocollazioneProsa") != null && "true".equalsIgnoreCase(getExtraparams().get("flgProtocollazioneProsa"));
		if(flgProtocollazioneProsa) {
			eseguiProtocollazioneProsa(bean);
		}
		
		return bean;
	}
	
	private void aggiornaDatiSIB(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		if(isAttivoSIB(bean)/* && bean.getFlgSpesa() != null && "SI".equals(bean.getFlgSpesa())*/) {				
			try {
				SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
				
				if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) { 
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc", bean.getIdPropostaAMC() != null ? bean.getIdPropostaAMC() : "");
				}
				
				if(StringUtils.isNotBlank(bean.getEventoSIB()) && !"aggiornamento".equals(bean.getEventoSIB())) { 
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_SIB_Doc", bean.getEventoSIB() != null ? bean.getEventoSIB() : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_SIB_Doc", bean.getEventoSIB() != null ? bean.getEventoSIB() : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_SIB_Doc", bean.getEsitoEventoSIB() != null ? bean.getEsitoEventoSIB() : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_SIB_Doc", bean.getDataEventoSIB() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoSIB()) : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_SIB_Doc", bean.getErrMsgEventoSIB() != null ? bean.getErrMsgEventoSIB() : "");
				}
				
				if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
				
					AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
					DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
					input.setCodidconnectiontokenin(loginBean.getToken());
					input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
		
					input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
					input.setFlgtipotargetin("D");
		
					CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
					lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
					
					XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
					input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
			
					DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
					StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
			
					if (lUpddocudOutput.isInError()) {
						throw new StoreException(lUpddocudOutput);
					}		
				}
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
	}
	
	private void aggiornaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {				
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if (bean.getListaMovimentiContabilia() != null && bean.getFlgSpesa() != null && _FLG_SI.equals(bean.getFlgSpesa())) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_CONTABILIA_Doc", new XmlUtilitySerializer().createVariabileLista(bean.getListaMovimentiContabilia()));					
			}				
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
	
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(loginBean.getToken());
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
	
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
				input.setFlgtipotargetin("D");
	
				CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
		
				DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
		
				if (lUpddocudOutput.isInError()) {
					throw new StoreException(lUpddocudOutput);
				}		
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
	}
	
	private void aggiornaDatiLiquidazioneContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc", bean.getIdPropostaAMC() != null ? bean.getIdPropostaAMC() : "");
			}				
			
			if(StringUtils.isNotBlank(bean.getEventoContabilia())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_AMC_Doc", bean.getEventoContabilia() != null ? bean.getEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_AMC_Doc", bean.getEsitoEventoContabilia() != null ? bean.getEsitoEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_AMC_Doc", bean.getDataEventoContabilia() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoContabilia()) : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_AMC_Doc", bean.getErrMsgEventoContabilia() != null ? bean.getErrMsgEventoContabilia() : "");
			}
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
	
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(loginBean.getToken());
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
	
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimarioLiquidazione()) ? new BigDecimal(bean.getIdDocPrimarioLiquidazione()) : null);
				input.setFlgtipotargetin("D");
	
				CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
		
				DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
		
				if (lUpddocudOutput.isInError()) {
					throw new StoreException(lUpddocudOutput);
				}		
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
	}
	
	private void aggiornaDatiPropostaContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc", bean.getIdPropostaAMC() != null ? bean.getIdPropostaAMC() : "");
			}				
			
			if(StringUtils.isNotBlank(bean.getEventoContabilia()) /*&& "aggiornaProposta".equalsIgnoreCase(bean.getEventoContabilia())*/) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_AMC_Doc", bean.getEventoContabilia() != null ? bean.getEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_AMC_Doc", bean.getEsitoEventoContabilia() != null ? bean.getEsitoEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_AMC_Doc", bean.getDataEventoContabilia() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoContabilia()) : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_AMC_Doc", bean.getErrMsgEventoContabilia() != null ? bean.getErrMsgEventoContabilia() : "");
			}
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
	
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(loginBean.getToken());
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
	
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
				input.setFlgtipotargetin("D");
	
				CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
		
				DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
		
				if (lUpddocudOutput.isInError()) {
					throw new StoreException(lUpddocudOutput);
				}		
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
	}
	
	private void aggiornaMovimentiSICRA(NuovaPropostaAtto2CompletaBean bean) throws Exception {				
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			boolean isSetMovimentiAttoSicraOK = bean.getEsitoSetMovimentiAttoSICRA() != null && bean.getEsitoSetMovimentiAttoSICRA().equalsIgnoreCase("OK");
			boolean isSetMovimentiAttoSicraWARNWithoutMessageXml = bean.getEsitoSetMovimentiAttoSICRA() != null && bean.getEsitoSetMovimentiAttoSICRA().equalsIgnoreCase("WARN") && StringUtils.isBlank(bean.getCodXSalvataggioConWarning());

			if (bean.getListaInvioMovimentiContabiliSICRA() != null && bean.getFlgSpesa() != null && _FLG_SI.equals(bean.getFlgSpesa()) && (isSetMovimentiAttoSicraOK || isSetMovimentiAttoSicraWARNWithoutMessageXml)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_SICRA_Doc", new XmlUtilitySerializer().createVariabileLista(bean.getListaInvioMovimentiContabiliSICRA()));					
			}				
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
	
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(loginBean.getToken());
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
	
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
				input.setFlgtipotargetin("D");
	
				CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
		
				DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
		
				if (lUpddocudOutput.isInError()) {
					throw new StoreException(lUpddocudOutput);
				}		
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
	}
		
	private void aggiornaDatiEventoSICRA(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if(StringUtils.isNotBlank(bean.getEventoSICRA())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_AMC_Doc", bean.getEventoSICRA() != null ? bean.getEventoSICRA() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_AMC_Doc", bean.getEsitoEventoSICRA() != null ? bean.getEsitoEventoSICRA() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_AMC_Doc", bean.getDataEventoSICRA() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoSICRA()) : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_AMC_Doc", bean.getErrMsgEventoSICRA() != null ? bean.getErrMsgEventoSICRA() : "");
			}
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
	
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(loginBean.getToken());
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
	
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
				input.setFlgtipotargetin("D");
	
				CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
		
				DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
		
				if (lUpddocudOutput.isInError()) {
					throw new StoreException(lUpddocudOutput);
				}		
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		
	}
	
	private void populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(ProtocollazioneBean pProtocollazioneBean, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {
		
		if(pProtocollazioneBean != null && pNuovaPropostaAtto2CompletaBean != null) {
			
			HashSet<String> setAttributiCustomCablati = buildSetAttributiCustomCablati(pNuovaPropostaAtto2CompletaBean);

			/* Hidden */
			
			pProtocollazioneBean.setIdUd(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdUd()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null);
			pProtocollazioneBean.setFlgTipoProv("I");
			pProtocollazioneBean.setTipoDocumento(pNuovaPropostaAtto2CompletaBean.getTipoDocumento());
			pProtocollazioneBean.setNomeTipoDocumento(pNuovaPropostaAtto2CompletaBean.getNomeTipoDocumento());
			pProtocollazioneBean.setRowidDoc(pNuovaPropostaAtto2CompletaBean.getRowidDoc());
			pProtocollazioneBean.setIdDocPrimario(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdDocPrimario()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getIdDocPrimario()) : null);
			pProtocollazioneBean.setNomeFilePrimario(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimario());
			pProtocollazioneBean.setUriFilePrimario(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario());
			pProtocollazioneBean.setRemoteUriFilePrimario(pNuovaPropostaAtto2CompletaBean.getRemoteUriFilePrimario());
			pProtocollazioneBean.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimario());
			pProtocollazioneBean.setIsDocPrimarioChanged(pNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimario());
			pProtocollazioneBean.setFlgDatiSensibili(pNuovaPropostaAtto2CompletaBean.getFlgDatiSensibili());
			
			DocumentBean lFilePrimarioOmissis = new DocumentBean();			
			lFilePrimarioOmissis.setIdDoc(pNuovaPropostaAtto2CompletaBean.getIdDocPrimarioOmissis());
			lFilePrimarioOmissis.setNomeFile(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimarioOmissis());
			lFilePrimarioOmissis.setUriFile(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis());
			lFilePrimarioOmissis.setRemoteUri(pNuovaPropostaAtto2CompletaBean.getRemoteUriFilePrimarioOmissis());		
			lFilePrimarioOmissis.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis());
			lFilePrimarioOmissis.setIsChanged(pNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimarioOmissis());
			pProtocollazioneBean.setFilePrimarioOmissis(lFilePrimarioOmissis);
			
			/* Dati scheda - Registrazione */
			
			if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione())) {
				pProtocollazioneBean.setSiglaProtocollo(pNuovaPropostaAtto2CompletaBean.getSiglaRegistrazione());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione()) : null);
				pProtocollazioneBean.setDataProtocollo(pNuovaPropostaAtto2CompletaBean.getDataRegistrazione());
				pProtocollazioneBean.setDesUserProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUserRegistrazione());
				pProtocollazioneBean.setDesUOProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUORegistrazione());		
				pProtocollazioneBean.setSiglaNumerazioneSecondaria(pNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNumeroNumerazioneSecondaria(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataRegistrazioneNumerazioneSecondaria(pNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria());			
			} else {
				pProtocollazioneBean.setSiglaProtocollo(pNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataProtocollo(pNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria());
				pProtocollazioneBean.setDesUserProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUserRegProvvisoria());
				pProtocollazioneBean.setDesUOProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUORegProvvisoria());	
			}
			
			/* Dati scheda - Ordinanza di mobilità */
			
			if(showAttributoCustomCablato(setAttributiCustomCablati, "ALTRE_UBICAZIONI")) {
				String tipoLuogoOrdMobilita = "";
				if(showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_LUOGO_ORD_MOBILITA")) {
					tipoLuogoOrdMobilita = pNuovaPropostaAtto2CompletaBean.getTipoLuogoOrdMobilita() != null ? pNuovaPropostaAtto2CompletaBean.getTipoLuogoOrdMobilita() : "";
				}
				if (_TIPO_LUOGO_DA_TOPONOMASTICA.equalsIgnoreCase(tipoLuogoOrdMobilita)) {
					pProtocollazioneBean.setListaAltreVie(pNuovaPropostaAtto2CompletaBean.getListaIndirizziOrdMobilita());
				}
			}

			/* Dati scheda - Ruoli */
			
			// Ufficio proponente
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUfficioProponente())) {							
				pProtocollazioneBean.setUoProtocollante("UO" + pNuovaPropostaAtto2CompletaBean.getUfficioProponente());
				pProtocollazioneBean.setIdUoProponente(pNuovaPropostaAtto2CompletaBean.getUfficioProponente());
			}
			
			/* Dati scheda - Oggetto */
			
			if(!isPresenteAttributoCustomCablato(setAttributiCustomCablati, "NASCONDI_OGGETTO")) {
				pProtocollazioneBean.setOggetto(estraiTestoOmissisDaHtml(pNuovaPropostaAtto2CompletaBean.getOggettoHtml()));
			}
			
			/* Dati scheda - Atto di riferimento */
			
			/*
			List<DocCollegatoBean> listaDocumentiDaCollegare = new ArrayList<DocCollegatoBean>();
			if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdUdAttoDeterminaAContrarre())) {
				DocCollegatoBean lDocCollegatoBean = new DocCollegatoBean();
				lDocCollegatoBean.setFlgPresenteASistema(pNuovaPropostaAtto2CompletaBean.getFlgAttoRifASistema());
				lDocCollegatoBean.setIdUdCollegata(pNuovaPropostaAtto2CompletaBean.getIdUdAttoDeterminaAContrarre());
				lDocCollegatoBean.setTipo(pNuovaPropostaAtto2CompletaBean.getCategoriaRegAttoDeterminaAContrarre());
				lDocCollegatoBean.setSiglaRegistro(pNuovaPropostaAtto2CompletaBean.getSiglaAttoDeterminaAContrarre());
				lDocCollegatoBean.setNumero(pNuovaPropostaAtto2CompletaBean.getNumeroAttoDeterminaAContrarre());
				lDocCollegatoBean.setAnno(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getAnnoAttoDeterminaAContrarre()) ? new Integer(pNuovaPropostaAtto2CompletaBean.getAnnoAttoDeterminaAContrarre()) : null);			
				listaDocumentiDaCollegare.add(lDocCollegatoBean);				
			}
			pProtocollazioneBean.setListaDocumentiDaCollegare(listaDocumentiDaCollegare);
			*/
			
			List<DocCollegatoBean> listaDocumentiDaCollegare = new ArrayList<DocCollegatoBean>();
			if(showAttributoCustomCablato(setAttributiCustomCablati, "ATTO_RIFERIMENTO")) {								
				if (pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento() != null && pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().size() > 0) {
					for(int i = 0; i < pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().size(); i++) {
						AttoRiferimentoBean lAttoRiferimentoBean = pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().get(i);
						DocCollegatoBean lDocCollegatoBean = new DocCollegatoBean();
						lDocCollegatoBean.setFlgPresenteASistema(lAttoRiferimentoBean.getFlgPresenteASistema());
						if(lAttoRiferimentoBean.getFlgPresenteASistema() != null && _FLG_NO.equals(lAttoRiferimentoBean.getFlgPresenteASistema())) {
							// non è un atto presente a sistema quindi non devo passare ne la categoria ne l'idUd
						} else {
							lDocCollegatoBean.setTipo(lAttoRiferimentoBean.getCategoriaReg());
							lDocCollegatoBean.setIdUdCollegata(lAttoRiferimentoBean.getIdUd());
						}
						lDocCollegatoBean.setSiglaRegistro(lAttoRiferimentoBean.getSigla());
						lDocCollegatoBean.setNumero(lAttoRiferimentoBean.getNumero());
						lDocCollegatoBean.setAnno(StringUtils.isNotBlank(lAttoRiferimentoBean.getAnno()) ? new Integer(lAttoRiferimentoBean.getAnno()) : null);			
						listaDocumentiDaCollegare.add(lDocCollegatoBean);	
					}						
				}
			}
			pProtocollazioneBean.setListaDocumentiDaCollegare(listaDocumentiDaCollegare);
			
			// metto a null gli estremi dell'atto di riferimento o in salvataggio me lo mette doppio nei documenti collegati
			pProtocollazioneBean.setSiglaDocRiferimento(null);
			pProtocollazioneBean.setNroDocRiferimento(null);
			pProtocollazioneBean.setAnnoDocRiferimento(null);
						
			/* Dati scheda - Allegati */
			
			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2CompletaBean.getListaAllegati());
			
			/* Dati spesa corrente - Opzioni */
			
//			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2CompletaBean.getListaAllegatiCorrente());			

			/* Dati spesa in conto capitale - Opzioni */
			
//			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2CompletaBean.getListaAllegatiContoCapitale());	
			
		}		
	}

	protected void salvaAttributiCustomProposta(NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici, false);
	}
	
	protected void salvaAttributiCustomProposta(NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici, boolean flgGenModello) throws Exception {

		HashSet<String> setAttributiCustomCablati = null;
		if(flgGenModello) {
			// se sono in generazione da modello passo tutti gli attributi, perchè quando faccio l'anteprima da lista non chiamo la CallExecAtt
		} else {
			// se sono in salvataggio devo passare solo gli attributi cablati che si vedono a maschera
			setAttributiCustomCablati = buildSetAttributiCustomCablati(bean);
		}
		
		String flgDettRevocaAtto = bean.getFlgDettRevocaAtto() != null && bean.getFlgDettRevocaAtto() ? "1" : "";
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DET_ANN_REVOCA_Doc", flgDettRevocaAtto);
		
		if (!flgGenModello && StringUtils.isNotBlank(bean.getUriDocGeneratoFormatoOdt())) {
			try {
				File fileOdtGenerato = File.createTempFile("temp", ".odt");
				InputStream lInputStream = StorageImplementation.getStorage().extract(bean.getUriDocGeneratoFormatoOdt());
				FileUtils.copyInputStreamToFile(lInputStream, fileOdtGenerato);
				File fileDocGenerato = ModelliUtil.convertToDoc(fileOdtGenerato);
				String md5FileDocGenerato = getMd5StringFormFile(fileDocGenerato);
				FileSavedIn lFileSavedIn = new FileSavedIn();
				lFileSavedIn.setSaved(fileDocGenerato);
				SalvataggioFile lSalvataggioFile = new SalvataggioFile();
				FileSavedOut out = lSalvataggioFile.savefile(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileSavedIn);
				if(out.getErrorInSaved() != null) {
					throw new Exception(out.getErrorInSaved());
				}
				DocumentBean fileDocDocumentBean = new DocumentBean();
				if (StringUtils.isNotBlank(bean.getNomeFilePrimario())){
					fileDocDocumentBean.setNomeFile(FilenameUtils.getBaseName(bean.getNomeFilePrimario()) + ".doc");
				} else {
					fileDocDocumentBean.setNomeFile("ATTO_COMPLETO.doc");
				}
				fileDocDocumentBean.setUriFile(out.getUri());
				String idUdFileDoc = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, fileDocDocumentBean, getSession());
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VERS_ATTO_FMT_DOC_Ud", idUdFileDoc);
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VERS_ATTO_FMT_MD5_Ud", md5FileDocGenerato);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new StoreException("Si è verificato un errore durante il salvataggio della versione .doc del dispositivo atto");
			}
		}
		
		/* Dati scheda - Specificità del provvedimento */
		
		// Spesa 
		
		String flgSpesa = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SPESA")) {
			flgSpesa = bean.getFlgSpesa() != null ? bean.getFlgSpesa() : "";
			if("".equals(flgSpesa)) {
				throw new StoreException("Non è possibile non valorizzare il flag con o senza rilevanza contabile.");
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_Doc", flgSpesa);
		} else {
			throw new StoreException("Errore di configurazione: obbligatorio configurare il flag di atto con spesa.");
		}
		
		/* Dati scheda - Emenda */
		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DATI_EMANDAMENTO")) {

			String tipoAttoEmendamento = "";		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_TIPO_ATTO")) {
				tipoAttoEmendamento = bean.getTipoAttoEmendamento() != null ? bean.getTipoAttoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_TIPO_ATTO_Doc", tipoAttoEmendamento);		
			}
		
			String siglaAttoEmendamento = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ATTO_SIGLA_REG")) {
				siglaAttoEmendamento = bean.getSiglaAttoEmendamento() != null ? bean.getSiglaAttoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ATTO_SIGLA_REG_Doc", siglaAttoEmendamento);		
			}
		
			String numeroAttoEmendamento = "";		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ATTO_NRO")) {
				numeroAttoEmendamento = bean.getNumeroAttoEmendamento() != null ? bean.getNumeroAttoEmendamento() : "";	
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ATTO_NRO_Doc", numeroAttoEmendamento);		
			}
		
			String annoAttoEmendamento = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ATTO_ANNO")) {
				annoAttoEmendamento = bean.getAnnoAttoEmendamento() != null ? bean.getAnnoAttoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ATTO_ANNO_Doc", annoAttoEmendamento);				
			}
			
			String idEmendamento = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_ID")) {
				idEmendamento = bean.getIdEmendamento() != null ? bean.getIdEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_ID_Doc", idEmendamento);		
			}
		
			String numeroEmendamento = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SUB_SU_EM_NRO")) {
				numeroEmendamento = bean.getNumeroEmendamento() != null ? bean.getNumeroEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SUB_SU_EM_NRO_Doc", numeroEmendamento);		
			}
		
			String flgEmendamentoSuFile = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_FILE")) {
				flgEmendamentoSuFile = bean.getFlgEmendamentoSuFile() != null ? bean.getFlgEmendamentoSuFile() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_FILE_Doc", flgEmendamentoSuFile);		
			}
		
			String numeroAllegatoEmendamento = ""; 		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ALLEGATO_NRO")) {
				if (_FLG_EMENDAMENTO_SU_FILE_A.equals(flgEmendamentoSuFile)) {
					numeroAllegatoEmendamento = bean.getNumeroAllegatoEmendamento() != null ? bean.getNumeroAllegatoEmendamento() : ""; 
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ALLEGATO_NRO_Doc", numeroAllegatoEmendamento);
			}
		
			String flgEmendamentoIntegraleAllegato = "";	
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_INTEGRALE_ALLEGATO")) {
				if (_FLG_EMENDAMENTO_SU_FILE_A.equals(flgEmendamentoSuFile)) {
					flgEmendamentoIntegraleAllegato = bean.getFlgEmendamentoIntegraleAllegato() != null && bean.getFlgEmendamentoIntegraleAllegato() ? "1" : "";	
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_INTEGRALE_ALLEGATO_Doc", flgEmendamentoIntegraleAllegato);	
			}
		
			String numeroPaginaEmendamento = "";		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_PAGINA")) {
				if (!"1".equals(flgEmendamentoIntegraleAllegato)) {
					numeroPaginaEmendamento = bean.getNumeroPaginaEmendamento() != null ? bean.getNumeroPaginaEmendamento() : "";
				}	
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_PAGINA_Doc", numeroPaginaEmendamento);
			}
		
			String numeroRigaEmendamento = "";		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_RIGA")) {
				if (!"1".equals(flgEmendamentoIntegraleAllegato)) {
					numeroRigaEmendamento = bean.getNumeroRigaEmendamento() != null ? bean.getNumeroRigaEmendamento() : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_RIGA_Doc", numeroRigaEmendamento);		
			}
	    
			String effettoEmendamento = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_EFFETTO")) {
				effettoEmendamento = bean.getEffettoEmendamento() != null ? bean.getEffettoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_EFFETTO_Doc", effettoEmendamento);
			}
		
		}

		/* Dati scheda - Destinatari */		
		
		String flgAttivaDestinatari = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ATTIVA_SEZIONE_DESTINATARI")) {
			flgAttivaDestinatari = bean.getFlgAttivaDestinatari() != null && bean.getFlgAttivaDestinatari() ? "1" : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ATTIVA_SEZIONE_DESTINATARI_Doc", flgAttivaDestinatari);		
		}
		
		List<DestinatarioAttoBean> listaDestinatariAtto = new ArrayList<DestinatarioAttoBean>();					
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DESTINATARI_ATTO")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "ATTIVA_SEZIONE_DESTINATARI") || "1".equals(flgAttivaDestinatari)) {
				if(bean.getListaDestinatariAtto() != null) {
					for(DestAttoBean lDestAttoBean : bean.getListaDestinatariAtto()) {
						if(StringUtils.isNotBlank(lDestAttoBean.getPrefisso()) || StringUtils.isNotBlank(lDestAttoBean.getDenominazione()) || StringUtils.isNotBlank(lDestAttoBean.getIndirizzo()) || StringUtils.isNotBlank(lDestAttoBean.getCorteseAttenzione()) || StringUtils.isNotBlank(lDestAttoBean.getIdSoggRubrica())) {
							DestinatarioAttoBean lDestinatarioAttoBean = new DestinatarioAttoBean();
							lDestinatarioAttoBean.setPrefisso(lDestAttoBean.getPrefisso());
							lDestinatarioAttoBean.setDenominazione(lDestAttoBean.getDenominazione());
							lDestinatarioAttoBean.setIndirizzo(lDestAttoBean.getIndirizzo());
							lDestinatarioAttoBean.setCorteseAttenzione(lDestAttoBean.getCorteseAttenzione());
							lDestinatarioAttoBean.setIdSoggRubrica(lDestAttoBean.getIdSoggRubrica());
							listaDestinatariAtto.add(lDestinatarioAttoBean);
						}
					}
				}	
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DESTINATARI_ATTO_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestinatariAtto));		
		}
		
		List<DestinatarioAttoBean> listaDestinatariPCAtto = new ArrayList<DestinatarioAttoBean>();					
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DESTINATARI_PC_ATTO")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "ATTIVA_SEZIONE_DESTINATARI") || "1".equals(flgAttivaDestinatari)) {
				if(bean.getListaDestinatariPCAtto() != null) {
					for(DestAttoBean lDestPCAttoBean : bean.getListaDestinatariPCAtto()) {
						if(StringUtils.isNotBlank(lDestPCAttoBean.getPrefisso()) || StringUtils.isNotBlank(lDestPCAttoBean.getDenominazione()) || StringUtils.isNotBlank(lDestPCAttoBean.getIndirizzo()) || StringUtils.isNotBlank(lDestPCAttoBean.getCorteseAttenzione()) || StringUtils.isNotBlank(lDestPCAttoBean.getIdSoggRubrica())) {
							DestinatarioAttoBean lDestinatarioPCAttoBean = new DestinatarioAttoBean();
							lDestinatarioPCAttoBean.setPrefisso(lDestPCAttoBean.getPrefisso());
							lDestinatarioPCAttoBean.setDenominazione(lDestPCAttoBean.getDenominazione());
							lDestinatarioPCAttoBean.setIndirizzo(lDestPCAttoBean.getIndirizzo());
							lDestinatarioPCAttoBean.setCorteseAttenzione(lDestPCAttoBean.getCorteseAttenzione());
							lDestinatarioPCAttoBean.setIdSoggRubrica(lDestPCAttoBean.getIdSoggRubrica());
							listaDestinatariPCAtto.add(lDestinatarioPCAttoBean);
						}
					}
				}				
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DESTINATARI_PC_ATTO_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestinatariPCAtto));		
		}
						
		/* Dati scheda - Tipo proposta */
		
		String iniziativaProposta = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO")) {
			iniziativaProposta = bean.getIniziativaProposta() != null ? bean.getIniziativaProposta() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO_Doc", iniziativaProposta);
		}
		
		String flgAttoMeroIndirizzo = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_ATTO_MERO_INDIRIZZO")) {
			flgAttoMeroIndirizzo = bean.getFlgAttoMeroIndirizzo() != null && bean.getFlgAttoMeroIndirizzo() ? "1" : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_ATTO_MERO_INDIRIZZO_Doc", flgAttoMeroIndirizzo);		
		}
		
		String flgModificaRegolamento = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_MODIFICA_REGOLAMENTO")) {
			flgModificaRegolamento = bean.getFlgModificaRegolamento() != null && bean.getFlgModificaRegolamento() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_MODIFICA_REGOLAMENTO_Doc", flgModificaRegolamento);
		}
		
		String flgModificaStatuto = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_MODIFICA_STATUTO")) {
			flgModificaStatuto = bean.getFlgModificaStatuto() != null && bean.getFlgModificaStatuto() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_MODIFICA_STATUTO_Doc", flgModificaStatuto); 
		}
		
		String flgNomina = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DEL_NOMINA")) {
			flgNomina = bean.getFlgNomina() != null && bean.getFlgNomina() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DEL_NOMINA_Doc", flgNomina); 
		}
		
		String flgRatificaDeliberaUrgenza = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RATIFICA_DEL_URGENZA")) {
			flgRatificaDeliberaUrgenza = bean.getFlgRatificaDeliberaUrgenza() != null && bean.getFlgRatificaDeliberaUrgenza() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RATIFICA_DEL_URGENZA_Doc", flgRatificaDeliberaUrgenza); 
		}
		
		String flgAttoUrgente = "";			
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_ATTO_URGENTE")) {
			flgAttoUrgente = bean.getFlgAttoUrgente() != null && bean.getFlgAttoUrgente() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_ATTO_URGENTE_Doc", flgAttoUrgente);		
		}
		
		/* Dati scheda - Circoscrizioni proponenti delibera */
		
		List<SimpleValueBean> listaCircoscrizioni = new ArrayList<SimpleValueBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "CIRCOSCRIZIONE_PROPONENTE")) {
			if("CIRCOSCRIZIONE".equalsIgnoreCase(iniziativaProposta)) {
				if(bean.getListaCircoscrizioni() != null) {
					for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaCircoscrizioni()) {
						if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
							SimpleValueBean lSimpleValueBean = new SimpleValueBean();
							lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
							listaCircoscrizioni.add(lSimpleValueBean);						
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "CIRCOSCRIZIONE_PROPONENTE_Doc", new XmlUtilitySerializer().createVariabileLista(listaCircoscrizioni)); 
		}
		
		/* Dati scheda - Interpellanza */
		
		String tipoInterpellanza = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_INTERPELLANZA")) {
			tipoInterpellanza = bean.getTipoInterpellanza() != null ? bean.getTipoInterpellanza() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_INTERPELLANZA_Doc", tipoInterpellanza);		
		}
		
		/* Dati scheda - Ordinanza di mobilità */
		
		String tipoOrdMobilita = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_ORD_MOBILITA")) {
			tipoOrdMobilita = bean.getTipoOrdMobilita() != null ? bean.getTipoOrdMobilita() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_ORD_MOBILITA_Doc", tipoOrdMobilita); 
		}
		
		String dataInizioVldOrdinanza = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "INIZIO_VLD_ORDINANZA")) {
			dataInizioVldOrdinanza = bean.getDataInizioVldOrdinanza() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataInizioVldOrdinanza()) : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "INIZIO_VLD_ORDINANZA_Doc", dataInizioVldOrdinanza); 
		}
		
		String dataFineVldOrdinanza = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FINE_VLD_ORDINANZA")) {
			dataFineVldOrdinanza = bean.getDataFineVldOrdinanza() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataFineVldOrdinanza()) : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FINE_VLD_ORDINANZA_Doc", dataFineVldOrdinanza); 
		}
		
		String tipoLuogoOrdMobilita = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_LUOGO_ORD_MOBILITA")) {
			tipoLuogoOrdMobilita = bean.getTipoLuogoOrdMobilita() != null ? bean.getTipoLuogoOrdMobilita() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_LUOGO_ORD_MOBILITA_Doc", tipoLuogoOrdMobilita); 	
		}
		
		String luogoOrdMobilita = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "LUOGO_ORD_MOBILITA")) {
			if (_TIPO_LUOGO_TESTO_LIBERO.equalsIgnoreCase(tipoLuogoOrdMobilita)) {
				luogoOrdMobilita = bean.getLuogoOrdMobilita() != null ? bean.getLuogoOrdMobilita() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LUOGO_ORD_MOBILITA_Doc", luogoOrdMobilita); 		
		}
		
		List<SimpleValueBean> listaCircoscrizioniOrdMobilita = new ArrayList<SimpleValueBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "CIRCOSCRIZIONE_ORD_MOBILITA")) {
			if(bean.getListaCircoscrizioniOrdMobilita() != null) {
				for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaCircoscrizioniOrdMobilita()) {
					if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
						listaCircoscrizioniOrdMobilita.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "CIRCOSCRIZIONE_ORD_MOBILITA_Doc", new XmlUtilitySerializer().createVariabileLista(listaCircoscrizioniOrdMobilita)); 
		}
		
		String descrizioneOrdMobilita = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DESCRIZIONE_ORD_MOBILITA")) {
			descrizioneOrdMobilita = bean.getDescrizioneOrdMobilita() != null ? bean.getDescrizioneOrdMobilita() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DESCRIZIONE_ORD_MOBILITA_Doc", descrizioneOrdMobilita); 
		}
		
		/* Dati scheda - Dati di pubblicazione */
		
		List<PeriodoPubblicazioneBean> listaPeriodoPubblicazione = new ArrayList<PeriodoPubblicazioneBean>();		
		if(bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
			PeriodoPubblicazioneBean lPeriodoPubblicazioneBean = new PeriodoPubblicazioneBean();
			lPeriodoPubblicazioneBean.setDataInizioPubblicazione(bean.getDataInizioPubblicazione());
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(bean.getDataInizioPubblicazione());
			cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(bean.getGiorniPubblicazione()));
			lPeriodoPubblicazioneBean.setDataFinePubblicazione(cal.getTime());		
			listaPeriodoPubblicazione.add(lPeriodoPubblicazioneBean);			
		}		
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "PERIODO_PUBBLICAZIONE_Doc", new XmlUtilitySerializer().createVariabileLista(listaPeriodoPubblicazione));
	
		/* Dati scheda - Ruoli */		
		
		// Dir. adottante
		
		String dirigenteAdottante = "";
		String flgAdottanteAncheRdP = "";
		String flgAdottanteAncheRUP = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ADOTTANTE")) {
			if(bean.getListaAdottante() != null && bean.getListaAdottante().size() > 0) {
				dirigenteAdottante = bean.getListaAdottante().get(0).getDirigenteAdottante();			
				flgAdottanteAncheRdP = bean.getListaAdottante().get(0).getFlgAdottanteAncheRdP() != null && bean.getListaAdottante().get(0).getFlgAdottanteAncheRdP() ? "1" : "";
				flgAdottanteAncheRUP = bean.getListaAdottante().get(0).getFlgAdottanteAncheRUP() != null && bean.getListaAdottante().get(0).getFlgAdottanteAncheRUP() ? "1" : "";
			}			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ADOTTANTE_Ud", dirigenteAdottante);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_ANCHE_RDP_Ud", flgAdottanteAncheRdP);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_ANCHE_RUP_Ud", flgAdottanteAncheRUP);
		}
		
		// Centro di Costo
		
		String centroDiCosto = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "CDC_ATTO")) {
			if (!_FLG_NO.equals(flgSpesa)) {	
				centroDiCosto = bean.getCentroDiCosto() != null ? bean.getCentroDiCosto() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "CDC_ATTO_Doc", centroDiCosto);
		}
				
		// Adottanti di concerto
		
		List<IdSVRespFirmatarioBean> listaDirigentiConcerto = new ArrayList<IdSVRespFirmatarioBean>();		
		String flgDeterminaConcerto = "";				
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_DI_CONCERTO")) {
			if(bean.getListaDirigentiConcerto() != null) {
				for(DirigenteDiConcertoBean lDirigenteDiConcertoBean : bean.getListaDirigentiConcerto()) {
					if(StringUtils.isNotBlank(lDirigenteDiConcertoBean.getDirigenteConcerto())) {
						IdSVRespFirmatarioBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioBean();
						lIdSVRespFirmatarioBean.setIdSV(lDirigenteDiConcertoBean.getDirigenteConcerto());
						lIdSVRespFirmatarioBean.setFlgFirmatario(lDirigenteDiConcertoBean.getFlgDirigenteConcertoFirmatario());
						listaDirigentiConcerto.add(lIdSVRespFirmatarioBean);
					}
				}
			}	
			flgDeterminaConcerto = listaDirigentiConcerto.size() > 0 ? "1" : "";				
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_DI_CONCERTO_Ud", new XmlUtilitySerializer().createVariabileLista(listaDirigentiConcerto));		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_DI_CONCERTO_Doc", flgDeterminaConcerto);		
		}
		
		// Dir. resp reg. tecnica
		
		String dirRespRegTecnica = "";
		String flgDirRespRegTecnicaAncheRdP = "";
		String flgDirRespRegTecnicaAncheRUP = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_DIR_RESP_REG_TECNICA")) {
			if(!"1".equals(flgAttoMeroIndirizzo)) {
				if(bean.getListaDirRespRegTecnica() != null && bean.getListaDirRespRegTecnica().size() > 0) {
					dirRespRegTecnica = bean.getListaDirRespRegTecnica().get(0).getDirigenteRespRegTecnica();			
					flgDirRespRegTecnicaAncheRdP = bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRdP() != null && bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRdP() ? "1" : "";
					flgDirRespRegTecnicaAncheRUP = bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRUP() != null && bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRUP() ? "1" : "";
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_DIR_RESP_REG_TECNICA_Ud", dirRespRegTecnica);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_DIR_ANCHE_RDP_Ud", flgDirRespRegTecnicaAncheRdP);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_DIR_ANCHE_RUP_Ud", flgDirRespRegTecnicaAncheRUP);
		}
				
		// Altri pareri reg. tecnica
		
		List<IdSVRespFirmatarioBean> listaAltriDirRespRegTecnica = new ArrayList<IdSVRespFirmatarioBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_DIR_REG_TECNICA")) {
			if(!"1".equals(flgAttoMeroIndirizzo)) {
				if(bean.getListaAltriDirRespRegTecnica() != null) {
					for(AltriDirRespRegTecnicaBean lAltriDirRespRegTecnicaBean : bean.getListaAltriDirRespRegTecnica()) {
						if(StringUtils.isNotBlank(lAltriDirRespRegTecnicaBean.getDirigenteRespRegTecnica())) {
							IdSVRespFirmatarioBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioBean();
							lIdSVRespFirmatarioBean.setIdSV(lAltriDirRespRegTecnicaBean.getDirigenteRespRegTecnica());
							lIdSVRespFirmatarioBean.setFlgFirmatario(lAltriDirRespRegTecnicaBean.getFlgDirigenteRespRegTecnicaFirmatario());
							listaAltriDirRespRegTecnica.add(lIdSVRespFirmatarioBean);					
						}
					}
				}		
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_DIR_REG_TECNICA_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriDirRespRegTecnica));				
		}
		
		// Responsabile di Procedimento
		
		String responsabileProcedimento = "";
		String flgRdPAncheRUP = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_PROC")) {
			if ("1".equals(flgAdottanteAncheRdP)) { 
				responsabileProcedimento = dirigenteAdottante;						
			} else if("1".equals(flgDirRespRegTecnicaAncheRdP)) {
				responsabileProcedimento = dirRespRegTecnica;
			} else if(bean.getListaRdP() != null && bean.getListaRdP().size() > 0) {		
				responsabileProcedimento = bean.getListaRdP().get(0).getResponsabileDiProcedimento();			
				flgRdPAncheRUP = bean.getListaRdP().get(0).getFlgRdPAncheRUP() != null && bean.getListaRdP().get(0).getFlgRdPAncheRUP() ? "1" : "";			
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_PROC_Ud", responsabileProcedimento);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_RDP_ANCHE_RUP_Ud", flgRdPAncheRUP);
		}
		
		// Responsabile Unico Provvedimento
		
		String responsabileUnicoProvvedimento = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RUP")) {
			if ("1".equals(flgAdottanteAncheRUP)) { 
				responsabileUnicoProvvedimento = dirigenteAdottante;						
			} else if ("1".equals(flgDirRespRegTecnicaAncheRUP)) { 
				responsabileUnicoProvvedimento = dirRespRegTecnica;						
			} else if ("1".equals(flgRdPAncheRUP)) { 
				responsabileUnicoProvvedimento = responsabileProcedimento;						
			} else if(bean.getListaRUP() != null && bean.getListaRUP().size() > 0) {
				responsabileUnicoProvvedimento = bean.getListaRUP().get(0).getResponsabileUnicoProvvedimento();	
			}	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RUP_Ud", responsabileUnicoProvvedimento);		
		}
		
		// Assessore proponente
		
		String assessoreProponente = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_ASSESSORE_PROPONENTE")) {
			assessoreProponente = (bean.getListaAssessori() != null && bean.getListaAssessori().size() > 0) ? bean.getListaAssessori().get(0).getAssessore() : "";			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_ASSESSORE_PROPONENTE_Ud", assessoreProponente);		
		}
			
		// Altri assessori
		
		List<IdSVRespFirmatarioBean> listaAltriAssessori = new ArrayList<IdSVRespFirmatarioBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_ALTRI_ASSESSORI")) {
			if(bean.getListaAltriAssessori() != null) {
				for(AssessoreBean lAssessoreBean : bean.getListaAltriAssessori()) {
					if(StringUtils.isNotBlank(lAssessoreBean.getAssessore())) {
						IdSVRespFirmatarioBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioBean();
						lIdSVRespFirmatarioBean.setIdSV(lAssessoreBean.getAssessore());
						lIdSVRespFirmatarioBean.setFlgFirmatario(lAssessoreBean.getFlgAssessoreFirmatario());
						listaAltriAssessori.add(lIdSVRespFirmatarioBean);
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_ALTRI_ASSESSORI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriAssessori));		
		}
				
		// Consigliere proponente
		
		String consigliereProponente = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_CONSIGLIERE_PROPONENTE")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO") || (!"POPOLARE".equalsIgnoreCase(iniziativaProposta) && !"CIRCOSCRIZIONE".equalsIgnoreCase(iniziativaProposta))) {
				consigliereProponente = (bean.getListaConsiglieri() != null && bean.getListaConsiglieri().size() > 0) ?  bean.getListaConsiglieri().get(0).getConsigliere() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_CONSIGLIERE_PROPONENTE_Ud", consigliereProponente);		
		}			
				
		// Altri consiglieri
				
		List<IdSVRespFirmatarioBean> listaAltriConsiglieri = new ArrayList<IdSVRespFirmatarioBean>();	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_ALTRI_CONSIGLIERI")) {			
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO") || (!"POPOLARE".equalsIgnoreCase(iniziativaProposta) && !"CIRCOSCRIZIONE".equalsIgnoreCase(iniziativaProposta))) {
				if(bean.getListaAltriConsiglieri() != null) {
					for(ConsigliereBean lConsigliereBean : bean.getListaAltriConsiglieri()) {
						if(StringUtils.isNotBlank(lConsigliereBean.getConsigliere())) {
							IdSVRespFirmatarioBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioBean();
							lIdSVRespFirmatarioBean.setIdSV(lConsigliereBean.getConsigliere());
							lIdSVRespFirmatarioBean.setFlgFirmatario(lConsigliereBean.getFlgConsigliereFirmatario());
							listaAltriConsiglieri.add(lIdSVRespFirmatarioBean);
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_ALTRI_CONSIGLIERI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriConsiglieri));		
		}
				
		// Dirigente proponente
		
		String dirigenteProponente = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_DIR_PROPONENTE")) {
			dirigenteProponente = (bean.getListaDirigentiProponenti() != null && bean.getListaDirigentiProponenti().size() > 0) ?  bean.getListaDirigentiProponenti().get(0).getDirigenteProponente() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_DIR_PROPONENTE_Ud", dirigenteProponente);
		}
						
		// Altri dirigenti proponenti
		
		List<IdSVRespFirmatarioConMotiviBean> listaAltriDirProponenti = new ArrayList<IdSVRespFirmatarioConMotiviBean>();	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_DIR_PROPONENTI")) {
			if(!"1".equals(flgAttoMeroIndirizzo)) {
				if(bean.getListaAltriDirigentiProponenti() != null) {
					for(DirigenteProponenteBean lDirigenteProponenteBean : bean.getListaAltriDirigentiProponenti()) {
						if(StringUtils.isNotBlank(lDirigenteProponenteBean.getDirigenteProponente())) {
							IdSVRespFirmatarioConMotiviBean lIdSVRespFirmatarioConMotiviBean = new IdSVRespFirmatarioConMotiviBean();
							lIdSVRespFirmatarioConMotiviBean.setIdSV(lDirigenteProponenteBean.getDirigenteProponente());
							lIdSVRespFirmatarioConMotiviBean.setFlgFirmatario(lDirigenteProponenteBean.getFlgDirigenteProponenteFirmatario());
							lIdSVRespFirmatarioConMotiviBean.setMotivi(lDirigenteProponenteBean.getMotiviDirigenteProponente());
							listaAltriDirProponenti.add(lIdSVRespFirmatarioConMotiviBean);
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_DIR_PROPONENTI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriDirProponenti));		
		}
				
		// Coordinatore competente per materia
		
		String coordinatoreCompCirc = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_COORDINATORE_COMP_CIRC")) {
			coordinatoreCompCirc = (bean.getListaCoordinatoriCompCirc() != null && bean.getListaCoordinatoriCompCirc().size() > 0) ?  bean.getListaCoordinatoriCompCirc().get(0).getCoordinatoreCompCirc() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_COORDINATORE_COMP_CIRC_Ud", coordinatoreCompCirc);
		}
				
		// Richiedi visto Dir. sovraordinato
		
		String flgRichiediVistoDirettore = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_DIR_SUP")) {
			flgRichiediVistoDirettore = bean.getFlgRichiediVistoDirettore() != null && bean.getFlgRichiediVistoDirettore() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_DIR_SUP_Doc", flgRichiediVistoDirettore);			
		}
				
		// Visti di conformità
		
		List<IdSVRespVistoConformitaBean> listaRespVistiConformita = new ArrayList<IdSVRespVistoConformitaBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_VISTI_CONFORMITA")) {
			if(bean.getListaRespVistiConformita() != null) {
				for(ResponsabileVistiConformitaBean lResponsabileVistiConformitaBean : bean.getListaRespVistiConformita()) {
					if(StringUtils.isNotBlank(lResponsabileVistiConformitaBean.getRespVistiConformita())) {
						IdSVRespVistoConformitaBean lIdSVRespVistoConformitaBean = new IdSVRespVistoConformitaBean();
						lIdSVRespVistoConformitaBean.setIdSV(lResponsabileVistiConformitaBean.getRespVistiConformita());
						lIdSVRespVistoConformitaBean.setFlgFirmatario(lResponsabileVistiConformitaBean.getFlgRespVistiConformitaFirmatario());
						lIdSVRespVistoConformitaBean.setMotivi(lResponsabileVistiConformitaBean.getMotiviRespVistiConformita());
						lIdSVRespVistoConformitaBean.setFlgRiacqVistoInRitornoIter(lResponsabileVistiConformitaBean.getFlgRiacqVistoInRitornoIterRespVistiConformita());
						listaRespVistiConformita.add(lIdSVRespVistoConformitaBean);	
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_VISTI_CONFORMITA_Ud", new XmlUtilitySerializer().createVariabileLista(listaRespVistiConformita));		
		}
				
		// Estensore principale
		
		String estensorePrincipale = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ESTENSORE_MAIN")) {
			estensorePrincipale = (bean.getListaEstensori() != null && bean.getListaEstensori().size() > 0) ?  bean.getListaEstensori().get(0).getEstensore() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ESTENSORE_MAIN_Ud", estensorePrincipale);		
		}
						
		// Altri estensori
		
		List<SimpleValueBean> listaAltriEstensori = new ArrayList<SimpleValueBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_ESTENSORI")) {
			if(bean.getListaAltriEstensori() != null) {
				for(EstensoreBean lEstensoreBean : bean.getListaAltriEstensori()) {
					if(StringUtils.isNotBlank(lEstensoreBean.getEstensore())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lEstensoreBean.getEstensore());
						listaAltriEstensori.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_ESTENSORI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriEstensori));		
		}
				
		// Resp. istruttoria
		
		String istruttorePrincipale = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ISTRUTTORE_MAIN")) {
			istruttorePrincipale = (bean.getListaIstruttori() != null && bean.getListaIstruttori().size() > 0) ?  bean.getListaIstruttori().get(0).getIstruttore() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ISTRUTTORE_MAIN_Ud", istruttorePrincipale);		
		}
						
		// Altri istruttori
		
		List<SimpleValueBean> listaAltriIstruttori = new ArrayList<SimpleValueBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_ISTRUTTORI")) {
			if(bean.getListaAltriIstruttori() != null) {
				for(IstruttoreBean lIstruttoreBean : bean.getListaAltriIstruttori()) {
					if(StringUtils.isNotBlank(lIstruttoreBean.getIstruttore())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lIstruttoreBean.getIstruttore());
						listaAltriIstruttori.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_ISTRUTTORI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriIstruttori));
		}
		
		/* Dati scheda - Visti dir. superiori */
		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "VISTI_DIR_SUPERIORI")) {			

			String flgVistoDirSup1 = "";			
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_DIR_SUP_1")) {
				flgVistoDirSup1 = bean.getFlgVistoDirSup1() != null && bean.getFlgVistoDirSup1() ? "1" : "";	
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_DIR_SUP_1_Doc", flgVistoDirSup1);			
			}
		
			String flgVistoDirSup2 = "";	
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_DIR_SUP_2")) {
				flgVistoDirSup2 = bean.getFlgVistoDirSup2() != null && bean.getFlgVistoDirSup2() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_DIR_SUP_2_Doc", flgVistoDirSup2);			
			}

		}
		
		/* Dati scheda - Parere della/e circoscrizioni */
			
		List<SimpleValueBean> listaParereCircoscrizioni = new ArrayList<SimpleValueBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "COD_CIRCOSCRIZIONE_X_PARERE")) {
			if(bean.getListaParereCircoscrizioni() != null) {
				for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaParereCircoscrizioni()) {
					if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
						listaParereCircoscrizioni.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "COD_CIRCOSCRIZIONE_X_PARERE_Doc", new XmlUtilitySerializer().createVariabileLista(listaParereCircoscrizioni));
		}
		
		/* Dati scheda - Parere della/e commissioni */
		
		List<SimpleValueBean> listaParereCommissioni = new ArrayList<SimpleValueBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "COD_COMMISSIONE_X_PARERE")) {
			if(bean.getListaParereCommissioni() != null) {
				for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaParereCommissioni()) {
					if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
						listaParereCommissioni.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "COD_COMMISSIONE_X_PARERE_Doc", new XmlUtilitySerializer().createVariabileLista(listaParereCommissioni));
		}
		
		/* Oggetto HTML */


		String oggettoHtml = "";
		if(!isPresenteAttributoCustomCablato(setAttributiCustomCablati, "NASCONDI_OGGETTO")) {
			oggettoHtml = bean.getOggettoHtml() != null ? bean.getOggettoHtml() : "";
		}
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "OGGETTO_HTML_Doc", oggettoHtml);
		
		/* Dati scheda - Specificità del provvedimento */
		
		String oggLiquidazione = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OGG_LIQUIDAZIONE")) {
			oggLiquidazione = bean.getOggLiquidazione() != null ? bean.getOggLiquidazione() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_OGG_LIQUIDAZIONE_Doc", oggLiquidazione);
		}
		
		String dataScadenzaLiquidazione = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "SCADENZA_LIQUIDAZIONE")) {
			dataScadenzaLiquidazione = bean.getDataScadenzaLiquidazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataScadenzaLiquidazione()) : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SCADENZA_LIQUIDAZIONE_Doc", dataScadenzaLiquidazione);
		}
		
		String urgenzaLiquidazione = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "URGENZA_LIQUIDAZIONE")) {
			urgenzaLiquidazione = bean.getUrgenzaLiquidazione() != null ? bean.getUrgenzaLiquidazione() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "URGENZA_LIQUIDAZIONE_Doc", urgenzaLiquidazione);
		}
		
		String flgLiqXUffCassa = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA")) {
			flgLiqXUffCassa = bean.getFlgLiqXUffCassa() != null && bean.getFlgLiqXUffCassa() ? "1" : "";		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA_Doc", flgLiqXUffCassa);
		}
		
		String importoAnticipoCassa = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "IMPORTO_ANTICIPO_CASSA")) {
			if("1".equals(flgLiqXUffCassa)) {
				importoAnticipoCassa = "1".equals(flgLiqXUffCassa) && bean.getImportoAnticipoCassa() != null ? bean.getImportoAnticipoCassa() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "IMPORTO_ANTICIPO_CASSA_Doc", importoAnticipoCassa);
		}
		
		String dataDecorrenzaContratto = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DECORRENZA_CONTRATTO")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OGG_LIQUIDAZIONE") || oggLiquidazione.toUpperCase().contains("CONTRATTO")) {
				dataDecorrenzaContratto = bean.getDataDecorrenzaContratto() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDecorrenzaContratto()) : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DECORRENZA_CONTRATTO_Doc", dataDecorrenzaContratto);
		}
		
		String anniDurataContratto = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ANNI_DURATA_CONTRATTO")) {			
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OGG_LIQUIDAZIONE") || oggLiquidazione.toUpperCase().contains("CONTRATTO")) {
				anniDurataContratto = bean.getAnniDurataContratto() != null ? bean.getAnniDurataContratto() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNI_DURATA_CONTRATTO_Doc", anniDurataContratto);		
		}
				
		String flgAffidamento = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO")) {
			flgAffidamento = bean.getFlgAffidamento() != null && bean.getFlgAffidamento() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_AFFIDAMENTO_Doc", flgAffidamento);
		}
		
		String flgDeterminaAContrarreTramiteProceduraGara = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CONTR_CON_GARA")) {
			flgDeterminaAContrarreTramiteProceduraGara = bean.getFlgDeterminaAContrarreTramiteProceduraGara() != null && bean.getFlgDeterminaAContrarreTramiteProceduraGara() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CONTR_CON_GARA_Doc", flgDeterminaAContrarreTramiteProceduraGara);
		}
		
		String flgDeterminaAggiudicaProceduraGara = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_AGGIUDICA_GARA")) {
			flgDeterminaAggiudicaProceduraGara = bean.getFlgDeterminaAggiudicaProceduraGara() != null && bean.getFlgDeterminaAggiudicaProceduraGara() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_AGGIUDICA_GARA_Doc", flgDeterminaAggiudicaProceduraGara);
		}
		
		String flgDeterminaRimodulazioneSpesaGaraAggiudicata = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD")) {
			flgDeterminaRimodulazioneSpesaGaraAggiudicata = bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() != null && bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD_Doc", flgDeterminaRimodulazioneSpesaGaraAggiudicata);
		}
		
		String flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_PERSONALE")) {
			flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro = bean.getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() != null && bean.getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_PERSONALE_Doc", flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro);
		}
		
		String flgDeterminaRiaccertamento = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_RIACCERT")) {
			flgDeterminaRiaccertamento = bean.getFlgDeterminaRiaccertamento() != null && bean.getFlgDeterminaRiaccertamento() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_RIACCERT_Doc", flgDeterminaRiaccertamento);		
		}
		
		String flgDeterminaAccertRadiaz = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_ACCERT_RADIAZ")) {
			flgDeterminaAccertRadiaz = bean.getFlgDeterminaAccertRadiaz() != null && bean.getFlgDeterminaAccertRadiaz() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_ACCERT_RADIAZ_Doc", flgDeterminaAccertRadiaz);		
		}
		
		String flgDeterminaVariazBil = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_VARIAZ_BIL")) {
			flgDeterminaVariazBil = bean.getFlgDeterminaVariazBil() != null && bean.getFlgDeterminaVariazBil() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_VARIAZ_BIL_Doc", flgDeterminaVariazBil);		
		}
		
		String flgVantaggiEconomici = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI")) {
			flgVantaggiEconomici = bean.getFlgVantaggiEconomici() != null && bean.getFlgVantaggiEconomici() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI_Doc", flgVantaggiEconomici);
		}
		
		String flgDecretoReggio = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DECRETO_REGGIO")) {
			flgDecretoReggio =  bean.getFlgDecretoReggio() != null && bean.getFlgDecretoReggio() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DECRETO_REGGIO_Doc", flgDecretoReggio); 
		}
		
		String flgAvvocatura = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_AVVOCATURA")) {
			flgAvvocatura =  bean.getFlgAvvocatura() != null && bean.getFlgAvvocatura() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_AVVOCATURA_Doc", flgAvvocatura);
		}
		
		String flgCorteConti = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_CORTE_CONTI")) {
			if(_FLG_SI.equals(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equals(flgSpesa)) {
				flgCorteConti =  bean.getFlgCorteConti() != null && bean.getFlgCorteConti() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_CORTE_CONTI_Doc", flgCorteConti); 
		}
		
		String flgLiqContestualeImpegno = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_LIQ_CONTESTUALE_IMPEGNO")) {
			if (_FLG_SI.equals(flgSpesa)) {
				flgLiqContestualeImpegno = bean.getFlgLiqContestualeImpegno() != null && bean.getFlgLiqContestualeImpegno() ? "1" : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_LIQ_CONTESTUALE_IMPEGNO_Doc", flgLiqContestualeImpegno); 
		}
		
		String flgLiqContestualeAltriAspettiRilCont = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT")) {
			if (_FLG_SI.equals(flgSpesa)) {
				flgLiqContestualeAltriAspettiRilCont = bean.getFlgLiqContestualeAltriAspettiRilCont() != null && bean.getFlgLiqContestualeAltriAspettiRilCont() ? "1" : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT_Doc", flgLiqContestualeAltriAspettiRilCont);
		}
		
		String flgCompQuadroFinRagDec = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC")) {
			if (_FLG_SI.equals(flgSpesa)) {
				flgCompQuadroFinRagDec = bean.getFlgCompQuadroFinRagDec() != null && bean.getFlgCompQuadroFinRagDec() ? "1" : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC_Doc", flgCompQuadroFinRagDec);
		}

		String flgNuoviImpAcc = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_NUOVI_IMP_ACC")) {
			if (_FLG_SI.equals(flgSpesa)) {
				flgNuoviImpAcc = bean.getFlgNuoviImpAcc() != null && bean.getFlgNuoviImpAcc() ? "1" : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_NUOVI_IMP_ACC_Doc", flgNuoviImpAcc);
		}
				 
		String flgImpSuAnnoCorrente = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE")) {
			if (_FLG_SI.equals(flgSpesa)) {
				flgImpSuAnnoCorrente = bean.getFlgImpSuAnnoCorrente() != null && bean.getFlgImpSuAnnoCorrente() ? "1" : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE_Doc", flgImpSuAnnoCorrente);
		}
		
		String flgInsMovARagioneria = "";			
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INS_MOV_A_RAGIONERIA")) {
			if (_FLG_SI.equals(flgSpesa)) {
				flgInsMovARagioneria = bean.getFlgInsMovARagioneria() != null && bean.getFlgInsMovARagioneria() ? "1" : "";											
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_INS_MOV_A_RAGIONERIA_Doc", flgInsMovARagioneria); 
		}
		
		String flgPresaVisioneContabilita = "";			
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA")) {
			if (_FLG_NO.equals(flgSpesa)) {
				flgPresaVisioneContabilita = bean.getFlgPresaVisioneContabilita() != null && bean.getFlgPresaVisioneContabilita() ? "1" : "";
			}			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA_Doc", flgPresaVisioneContabilita);						
		}
		
		String flgSpesaCorrente = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SPESA_CORRENTE")) {
			if (_FLG_SI.equals(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgSpesaCorrente = bean.getFlgSpesaCorrente() != null && bean.getFlgSpesaCorrente() ? "1" : "";					
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_CORRENTE_Doc", flgSpesaCorrente);
		}
		
		String flgImpegniCorrenteGiaValidati = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_IMP_CORR_VALID")) {
			if (_FLG_SI.equals(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					if("1".equals(flgSpesaCorrente)) {
						flgImpegniCorrenteGiaValidati = bean.getFlgImpegniCorrenteGiaValidati() != null && bean.getFlgImpegniCorrenteGiaValidati() ? "1" : "";
					}					
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_IMP_CORR_VALID_Doc", flgImpegniCorrenteGiaValidati);
		}
		
		String flgSpesaContoCapitale = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP")) {
			if (_FLG_SI.equals(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgSpesaContoCapitale = bean.getFlgSpesaContoCapitale() != null && bean.getFlgSpesaContoCapitale() ? "1" : "";					
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP_Doc", flgSpesaContoCapitale);
		}
		
		String flgImpegniContoCapitaleGiaRilasciati = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_IMP_CCAP_RIL")) {
			if (_FLG_SI.equals(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					if("1".equals(flgSpesaContoCapitale)) {
						flgImpegniContoCapitaleGiaRilasciati = bean.getFlgImpegniContoCapitaleGiaRilasciati() != null && bean.getFlgImpegniContoCapitaleGiaRilasciati() ? "1" : "";
					}
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_IMP_CCAP_RIL_Doc", flgImpegniContoCapitaleGiaRilasciati);
		}
		
		String flgSoloSubImpSubCrono = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SUB")) {
			if (_FLG_SI.equals(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgSoloSubImpSubCrono = bean.getFlgSoloSubImpSubCrono() != null && bean.getFlgSoloSubImpSubCrono() ? "1" : "";
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SUB_Doc", flgSoloSubImpSubCrono);
		}
		
		String tipoAttoInDeliberaPEG = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG")) {
			if(_FLG_SI.equals(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equals(flgSpesa)) {
				tipoAttoInDeliberaPEG = bean.getTipoAttoInDeliberaPEG() != null ? bean.getTipoAttoInDeliberaPEG() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG_Doc", tipoAttoInDeliberaPEG);				
		}
		
		String tipoAffidamento = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_AFFIDAMENTO")) {						
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO") || "1".equals(flgAffidamento)) {
				tipoAffidamento = bean.getTipoAffidamento() != null ? bean.getTipoAffidamento() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_AFFIDAMENTO_Doc", tipoAffidamento);
		}
	
		String normRifAffidamento = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "NORM_RIF_AFFIDAMENTO")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO") || "1".equals(flgAffidamento)) {
				normRifAffidamento = bean.getNormRifAffidamento() != null ? bean.getNormRifAffidamento() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NORM_RIF_AFFIDAMENTO_Doc", normRifAffidamento);
		}
		
		String respAffidamento = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "RESP_AFFIDAMENTO")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO") || "1".equals(flgAffidamento)) {
				respAffidamento = bean.getRespAffidamento() != null ? bean.getRespAffidamento() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "RESP_AFFIDAMENTO_Doc", respAffidamento);
		}
		
		String materiaTipoAtto = "";			
		if(showAttributoCustomCablato(setAttributiCustomCablati, "MATERIA_NATURA_ATTO")) {
			materiaTipoAtto = bean.getMateriaTipoAtto() != null ? bean.getMateriaTipoAtto() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MATERIA_NATURA_ATTO_Doc", materiaTipoAtto);
		}
				
		String flgFondiEuropeiPON = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FONDI_EUROPEI_PON")) {
			if(_FLG_SI.equals(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equals(flgSpesa)) {
				flgFondiEuropeiPON =  bean.getFlgFondiEuropeiPON() != null && bean.getFlgFondiEuropeiPON() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FONDI_EUROPEI_PON_Doc", flgFondiEuropeiPON);
		}
				 
		String flgFondiPRU = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FONDI_PRU")) {
			flgFondiPRU = bean.getFlgFondiPRU() != null && bean.getFlgFondiPRU() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FONDI_PRU_Doc", flgFondiPRU);
		}
		
		String flgVistoPar117_2013 = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_PAR_117_2013")) {
			flgVistoPar117_2013 = bean.getFlgVistoPar117_2013() != null && bean.getFlgVistoPar117_2013() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_PAR_117_2013_Doc", flgVistoPar117_2013);
		}
		
		String flgNotificaDaMessi = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_NOTIFICA_DA_MESSI")) {
			flgNotificaDaMessi = bean.getFlgNotificaDaMessi() != null && bean.getFlgNotificaDaMessi() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_NOTIFICA_DA_MESSI_Doc", flgNotificaDaMessi);
		}
		
		String flgLLPP = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_LLPP")) {
			flgLLPP = bean.getFlgLLPP() != null ? bean.getFlgLLPP() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_LLPP_Doc", flgLLPP);
		}
		
		String annoProgettoLLPP = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_PROGETTO_LLPP")) {
			if (_FLG_SI.equals(flgLLPP)) {
				annoProgettoLLPP = bean.getAnnoProgettoLLPP() != null ? bean.getAnnoProgettoLLPP() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_PROGETTO_LLPP_Doc", annoProgettoLLPP);
		}
		
		String numProgettoLLPP = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "NRO_PROGETTO_LLPP")) {
			if (_FLG_SI.equals(flgLLPP)) {	
				numProgettoLLPP = bean.getNumProgettoLLPP() != null ? bean.getNumProgettoLLPP() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_PROGETTO_LLPP_Doc", numProgettoLLPP);
		}
		
		String flgBeniServizi = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_BENI_SERVIZI")) {
			flgBeniServizi = bean.getFlgBeniServizi() != null ? bean.getFlgBeniServizi() : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_BENI_SERVIZI_Doc", flgBeniServizi);
		}
		
		String annoProgettoBeniServizi = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_PROGETTO_BENI_SERVIZI")) {
			if (_FLG_SI.equals(flgBeniServizi)) {
				annoProgettoBeniServizi = bean.getAnnoProgettoBeniServizi() != null ? bean.getAnnoProgettoBeniServizi() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_PROGETTO_BENI_SERVIZI_Doc", annoProgettoBeniServizi);
		}
		
		String numProgettoBeniServizi = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "NRO_PROGETTO_BENI_SERVIZI")) {
			if (_FLG_SI.equals(flgBeniServizi)) {
				numProgettoBeniServizi = bean.getNumProgettoBeniServizi() != null ? bean.getNumProgettoBeniServizi() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_PROGETTO_BENI_SERVIZI_Doc", numProgettoBeniServizi);
		}
		
		String flgPrivacy = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI")) {
			flgPrivacy = bean.getFlgPrivacy() != null  ? bean.getFlgPrivacy() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_ATTO_CON_DATI_RISERVATI_Doc", flgPrivacy);
		}
		
		String flgDatiProtettiTipo1 = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_1")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI") || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo1 = bean.getFlgDatiProtettiTipo1() != null && bean.getFlgDatiProtettiTipo1() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_1_Doc", flgDatiProtettiTipo1);
		}

		String flgDatiProtettiTipo2 = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_2")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI") || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo2 = bean.getFlgDatiProtettiTipo2() != null && bean.getFlgDatiProtettiTipo2() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_2_Doc", flgDatiProtettiTipo2);
		}
		
		String flgDatiProtettiTipo3 = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_3")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI") || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo3 = bean.getFlgDatiProtettiTipo3() != null && bean.getFlgDatiProtettiTipo3() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_3_Doc", flgDatiProtettiTipo3);
		}
		
		String flgDatiProtettiTipo4 = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_4")) {			
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI") || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo4 = bean.getFlgDatiProtettiTipo4() != null && bean.getFlgDatiProtettiTipo4() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_4_Doc", flgDatiProtettiTipo4);
		}
		
		/* Dati scheda - Dest. vantaggio */				
		
		List<DestinatarioVantaggioBean> listaDestVantaggio = new ArrayList<DestinatarioVantaggioBean>();	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DEST_VANTAGGIO")) {
			if("1".equals(flgVantaggiEconomici)) {			
				if(bean.getListaDestVantaggio() != null) {
					for(DestVantaggioBean lDestVantaggioBean : bean.getListaDestVantaggio()) {
						if(StringUtils.isNotBlank(lDestVantaggioBean.getTipoPersona()) && (StringUtils.isNotBlank(lDestVantaggioBean.getRagioneSociale()) || (StringUtils.isNotBlank(lDestVantaggioBean.getCognome()) && StringUtils.isNotBlank(lDestVantaggioBean.getNome())))) {
							DestinatarioVantaggioBean lDestinatarioVantaggioBean = new DestinatarioVantaggioBean();
							lDestinatarioVantaggioBean.setTipoPersona(lDestVantaggioBean.getTipoPersona());
							if("fisica".equalsIgnoreCase(lDestVantaggioBean.getTipoPersona())) {
								lDestinatarioVantaggioBean.setCognome(lDestVantaggioBean.getCognome());
								lDestinatarioVantaggioBean.setNome(lDestVantaggioBean.getNome());
							} else if("giuridica".equalsIgnoreCase(lDestVantaggioBean.getTipoPersona())) {							
								lDestinatarioVantaggioBean.setRagioneSociale(lDestVantaggioBean.getRagioneSociale());
							}
							lDestinatarioVantaggioBean.setCodFiscalePIVA(lDestVantaggioBean.getCodFiscalePIVA());
							lDestinatarioVantaggioBean.setImporto(lDestVantaggioBean.getImporto());
							listaDestVantaggio.add(lDestinatarioVantaggioBean);
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DEST_VANTAGGIO_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestVantaggio));		
		}
				
		/* Dati scheda - Ruoli e visti per dati contabili */	
		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI")) {	

			// Responsabili PEG
			
			String flgAdottanteUnicoRespPEG = "";							
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA")) {
				if(_FLG_SI.equals(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equals(flgSpesa)) {
					if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ADOTTANTE")) {
						flgAdottanteUnicoRespPEG = bean.getFlgAdottanteUnicoRespPEG() != null && bean.getFlgAdottanteUnicoRespPEG() ? "1" : "";
					}
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA_Ud", flgAdottanteUnicoRespPEG);	
			}
		
			List<SimpleValueBean> listaResponsabiliPEG = new ArrayList<SimpleValueBean>();		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_SPESA")) {
				if (_FLG_SI.equals(flgSpesa)) {
					if ("1".equals(flgAdottanteUnicoRespPEG)) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(dirigenteAdottante);				
						listaResponsabiliPEG.add(lSimpleValueBean);				
					} else if(bean.getListaResponsabiliPEG() != null) {
						for(ResponsabilePEGBean lResponsabilePEGBean : bean.getListaResponsabiliPEG()) {
							SimpleValueBean lSimpleValueBean = new SimpleValueBean();
							lSimpleValueBean.setValue(lResponsabilePEGBean.getResponsabilePEG());
							listaResponsabiliPEG.add(lSimpleValueBean);
						}
					}			
				}
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_SPESA_Ud", new XmlUtilitySerializer().createVariabileLista(listaResponsabiliPEG));
			}
		
			// Ufficio definizione spesa
						
			String ufficioDefinizioneSpesa = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "ID_UO_COMP_SPESA")) {
				if (_FLG_SI.equals(flgSpesa)) {			
					if (StringUtils.isNotBlank(bean.getUfficioDefinizioneSpesa())) {
						ufficioDefinizioneSpesa = bean.getUfficioDefinizioneSpesa();				
					}		
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_UO_COMP_SPESA_Ud", ufficioDefinizioneSpesa);		
			}
		
			// Definizione dati contabili in carico a
		
			String opzAssCompSpesa = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OPZ_ASS_COMP_SPESA")) {
				if (_FLG_SI.equals(flgSpesa)) {
					opzAssCompSpesa = bean.getOpzAssCompSpesa() != null ? bean.getOpzAssCompSpesa() : "";		
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_OPZ_ASS_COMP_SPESA_Doc", opzAssCompSpesa);
			}
		
			String flgRichVerificaDiBilancioCorrente = "";	
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_VRF_BIL_CORR")) {
				if (getFLG_SI_SENZA_VLD_RIL_IMP().equals(flgSpesa)) {
					flgRichVerificaDiBilancioCorrente = bean.getFlgRichVerificaDiBilancioCorrente() != null && bean.getFlgRichVerificaDiBilancioCorrente() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_BIL_CORR_Doc", flgRichVerificaDiBilancioCorrente);										
			}
		
			String flgRichVerificaDiBilancioContoCapitale = "";	
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_VRF_BIL_CCAP")) {
				if (getFLG_SI_SENZA_VLD_RIL_IMP().equals(flgSpesa)) {
					flgRichVerificaDiBilancioContoCapitale = bean.getFlgRichVerificaDiBilancioContoCapitale() != null && bean.getFlgRichVerificaDiBilancioContoCapitale() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_BIL_CCAP_Doc", flgRichVerificaDiBilancioContoCapitale);							
			}
		
			String flgRichVerificaDiContabilita = "";	
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_VRF_CONTABIL")) {
				if (getFLG_SI_SENZA_VLD_RIL_IMP().equals(flgSpesa)) {
					flgRichVerificaDiContabilita = bean.getFlgRichVerificaDiContabilita() != null && bean.getFlgRichVerificaDiContabilita() ? "1" : "";	
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_CONTABIL_Doc", flgRichVerificaDiContabilita);					
			}
		
			String flgConVerificaContabilita = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_VERIFICA_CONTABILITA")) {
				if (_FLG_SI.equals(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgConVerificaContabilita = bean.getFlgConVerificaContabilita() != null && bean.getFlgConVerificaContabilita() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_VERIFICA_CONTABILITA_Doc", flgConVerificaContabilita);
			}
		
			String flgRichiediParereRevisoriContabili = "";		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_PARERE_REV_CONTABILI")) {
				if (_FLG_SI.equals(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgRichiediParereRevisoriContabili = bean.getFlgRichiediParereRevisoriContabili() != null && bean.getFlgRichiediParereRevisoriContabili() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_PARERE_REV_CONTABILI_Doc", flgRichiediParereRevisoriContabili);
			}

		}

		/* Dati scheda - CIG */
		
		String flgOpCommerciale = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE")) {
			flgOpCommerciale = bean.getFlgOpCommerciale() != null ? bean.getFlgOpCommerciale() : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_OP_COMMERCIALE_Doc", flgOpCommerciale);			
		}
		
		String flgEscludiCIG = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ESCL_CIG")) {
			if(!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE") || _FLG_SI.equals(flgOpCommerciale)) {
				flgEscludiCIG = bean.getFlgEscludiCIG() != null && bean.getFlgEscludiCIG() ? "1" : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ESCL_CIG_Doc", flgEscludiCIG);			
		}
		
		String motivoEsclusioneCIG = "";			
		if(showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVO_ESCLUSIONE_CIG")) {
			boolean showMotivoEsclusioneCIG = false;
			if ((showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ESCL_CIG"))) {
				if("1".equals(flgEscludiCIG)) {
					showMotivoEsclusioneCIG = true;
				}
			} else if ((showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE"))) {
				if(_FLG_SI.equals(flgOpCommerciale)) {
					showMotivoEsclusioneCIG = true;
				}
			} else {
				showMotivoEsclusioneCIG = true;	
			}
			if(showMotivoEsclusioneCIG) {
				motivoEsclusioneCIG = bean.getMotivoEsclusioneCIG() != null ? bean.getMotivoEsclusioneCIG() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOTIVO_ESCLUSIONE_CIG_Doc", motivoEsclusioneCIG);			 
		}
		
		List<CIGBean> listaCIG = new ArrayList<CIGBean>();		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "CIG")) {
			boolean showListaCIG = false;			
			if ((showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ESCL_CIG"))) {
				if(!"1".equals(flgEscludiCIG)) {
					showListaCIG = true;
				}
			} else if ((showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE"))) {
				if(_FLG_SI.equals(flgOpCommerciale)) {
					showListaCIG = true;
				}
			} else {
				showListaCIG = true;
			}
			if(showListaCIG) {
				listaCIG = bean.getListaCIG() != null ? bean.getListaCIG() : new ArrayList<CIGBean>();	
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "CIG_Doc", new XmlUtilitySerializer().createVariabileLista(listaCIG));
		}
		
		/* Dati dispositivo - Riferimenti normativi */
		
		List<SimpleValueBean> listaRiferimentiNormativi = new ArrayList<SimpleValueBean>();		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "RIFERIMENTI_NORMATIVI")) {
			listaRiferimentiNormativi = bean.getListaRiferimentiNormativi() != null ? bean.getListaRiferimentiNormativi() : new ArrayList<SimpleValueBean>();
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "RIFERIMENTI_NORMATIVI_Doc", new XmlUtilitySerializer().createVariabileLista(listaRiferimentiNormativi));			
		}
		
		/* Dati dispositivo - Atti presupposti */
		
//		List<SimpleValueBean> listaAttiPresupposti = new ArrayList<SimpleValueBean>();
//		if(showAttributoCustomCablato(setAttributiCustomCablati, "ATTI_PRESUPPOSTI")) {
//			listaAttiPresupposti = bean.getListaAttiPresupposti() != null ? bean.getListaAttiPresupposti() : new ArrayList<SimpleValueBean>();
//			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ATTI_PRESUPPOSTI_Doc", new XmlUtilitySerializer().createVariabileLista(listaAttiPresupposti));
//		}
		
		String attiPresupposti = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "ATTI_PRESUPPOSTI")) {
			attiPresupposti = bean.getAttiPresupposti() != null  ? bean.getAttiPresupposti() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ATTI_PRESUPPOSTI_Doc", attiPresupposti);
		}
		
		/* Dati dispositivo - Motivazioni */
		
		String motivazioni = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVAZIONI_ATTO")) {
			motivazioni = bean.getMotivazioni() != null  ? bean.getMotivazioni() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOTIVAZIONI_ATTO_Doc", motivazioni);
		}
		
		/* Dati dispositivo - Premessa */
		
		String premessa = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "PREMESSA_ATTO")) {
			premessa = bean.getPremessa() != null  ? bean.getPremessa() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PREMESSA_ATTO_Doc", premessa);
		}
		
		/* Dati dispositivo - Dispositivo */
		
		String dispositivo = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DISPOSITIVO_ATTO")) {
			dispositivo = bean.getDispositivo() != null  ? bean.getDispositivo() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DISPOSITIVO_ATTO_Doc", dispositivo);
		}
		
		String loghiAggiuntiviDispositivo = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "LOGHI_DISPOSITIVO_ATTO")) {
			loghiAggiuntiviDispositivo = bean.getLoghiAggiuntiviDispositivo() != null  ? bean.getLoghiAggiuntiviDispositivo() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LOGHI_DISPOSITIVO_ATTO_Doc", loghiAggiuntiviDispositivo);
		}
		
		/* Dati dispositivo 2 - Premessa 2 */
		
		String premessa2 = "";	
		if(showAttributoCustomCablato(setAttributiCustomCablati, "PREMESSA_ATTO_2")) {
			premessa2 = bean.getPremessa2() != null  ? bean.getPremessa2() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PREMESSA_ATTO_2_Doc", premessa2);
		}
		
		/* Dati dispositivo 2 - Dispositivo 2 */
		
		String dispositivo2 = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DISPOSITIVO_ATTO_2")) {
			dispositivo2 = bean.getDispositivo2() != null  ? bean.getDispositivo2() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DISPOSITIVO_ATTO_2_Doc", dispositivo2);
		}
		
		/* Allegati */
		
		String flgPubblicaAllegatiSeparati = bean.getFlgPubblicaAllegatiSeparati() != null && bean.getFlgPubblicaAllegatiSeparati() ? "1" : "";		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_ALLEGATI_SEPARATA_Ud", flgPubblicaAllegatiSeparati);
		
		/* Pubblicazione/notifiche - Pubblicazione all'Albo */
		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DATI_PUBBL_ALBO")) {

			String flgPubblAlbo = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) {
				flgPubblAlbo = bean.getFlgPubblAlbo() != null  ? bean.getFlgPubblAlbo() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_ALBO_Doc", flgPubblAlbo);
			}
		
			String numGiorniPubblAlbo = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "NRO_GIORNI_PUBBL_ALBO")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO") || _FLG_SI.equals(flgPubblAlbo)) {
					numGiorniPubblAlbo = bean.getNumGiorniPubblAlbo() != null  ? bean.getNumGiorniPubblAlbo() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_GIORNI_PUBBL_ALBO_Doc", numGiorniPubblAlbo);
			}
		
			String tipoDecorrenzaPubblAlbo = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_DECORRENZA_PUBBL_ALBO")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO") || _FLG_SI.equals(flgPubblAlbo)) {
					tipoDecorrenzaPubblAlbo = bean.getTipoDecorrenzaPubblAlbo() != null  ? bean.getTipoDecorrenzaPubblAlbo() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_DECORRENZA_PUBBL_ALBO_Doc", tipoDecorrenzaPubblAlbo);
			}
		
			String dataPubblAlboDal = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_ALBO_DAL")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO") || _FLG_SI.equals(flgPubblAlbo)) {
					if (_DECORR_PUBBL_POST.equals(tipoDecorrenzaPubblAlbo)) {
						dataPubblAlboDal = bean.getDataPubblAlboDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblAlboDal()) : "";			
					}				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_ALBO_DAL_Doc", dataPubblAlboDal);
			}
		
			String flgUrgentePubblAlbo = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_URGENTE_PUBBL_ALBO")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO") || _FLG_SI.equals(flgPubblAlbo)) {
					flgUrgentePubblAlbo = bean.getFlgUrgentePubblAlbo() != null && bean.getFlgUrgentePubblAlbo() ? "1" : "";							
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_URGENTE_PUBBL_ALBO_Doc", flgUrgentePubblAlbo);
			}
		
			String dataPubblAlboEntro = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_ALBO_ENTRO")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO") || _FLG_SI.equals(flgPubblAlbo)) {
					if ("1".equals(flgUrgentePubblAlbo)) {
						dataPubblAlboEntro = bean.getDataPubblAlboEntro() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblAlboEntro()) : "";			
					}				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_ALBO_ENTRO_Doc", dataPubblAlboEntro);
			}

		}

		/* Pubblicazione/notifiche - Pubblicazione in Amm. Trasparente */
		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DATI_PUBBL_AMM_TRASP")) {

			String flgPubblAmmTrasp = "";		
			if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_IN_TRASPARENZA")) {
				flgPubblAmmTrasp = bean.getFlgPubblAmmTrasp() != null  ? bean.getFlgPubblAmmTrasp() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_IN_TRASPARENZA_Doc", flgPubblAmmTrasp);
			}
		
			String sezionePubblAmmTrasp = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "SEZ1_PUBBL_IN_TRASPARENZA")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_IN_TRASPARENZA") || _FLG_SI.equals(flgPubblAmmTrasp)) {
					sezionePubblAmmTrasp = bean.getSezionePubblAmmTrasp() != null  ? bean.getSezionePubblAmmTrasp() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SEZ1_PUBBL_IN_TRASPARENZA_Doc", sezionePubblAmmTrasp);
			}
		
			String sottoSezionePubblAmmTrasp = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "SEZ2_PUBBL_IN_TRASPARENZA")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_IN_TRASPARENZA") || _FLG_SI.equals(flgPubblAmmTrasp)) {
					sottoSezionePubblAmmTrasp = bean.getSottoSezionePubblAmmTrasp() != null  ? bean.getSottoSezionePubblAmmTrasp() : "";			
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SEZ2_PUBBL_IN_TRASPARENZA_Doc", sottoSezionePubblAmmTrasp);
			}

		}

		/* Pubblicazione/notifiche - Pubblicazione al B.U. */
		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DATI_PUBBL_BUR")) {

			String flgPubblBUR = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR")) {
				flgPubblBUR = bean.getFlgPubblBUR() != null  ? bean.getFlgPubblBUR() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_BUR_Doc", flgPubblBUR);
			}
		
			String annoTerminePubblBUR = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_TERMINE_PUBBL_BUR")) {				
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR") || _FLG_SI.equals(flgPubblBUR)) {
					annoTerminePubblBUR = bean.getAnnoTerminePubblBUR() != null  ? bean.getAnnoTerminePubblBUR() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_TERMINE_PUBBL_BUR_Doc", annoTerminePubblBUR);
			}
		
			String tipoDecorrenzaPubblBUR = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_DECORRENZA_PUBBL_BUR")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR") || _FLG_SI.equals(flgPubblBUR)) {
					tipoDecorrenzaPubblBUR = bean.getTipoDecorrenzaPubblBUR() != null  ? bean.getTipoDecorrenzaPubblBUR() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_DECORRENZA_PUBBL_BUR_Doc", tipoDecorrenzaPubblBUR);
			}
		
			String dataPubblBURDal = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_BUR_DAL")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR") || _FLG_SI.equals(flgPubblBUR)) {
					if (_DECORR_PUBBL_POST.equals(tipoDecorrenzaPubblBUR)) {
						dataPubblBURDal = bean.getDataPubblBURDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblBURDal()) : "";			
					}
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_BUR_DAL_Doc", dataPubblBURDal);
			}
		
			String flgUrgentePubblBUR = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_URGENTE_PUBBL_BUR")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR") || _FLG_SI.equals(flgPubblBUR)) {
					flgUrgentePubblBUR = bean.getFlgUrgentePubblBUR() != null && bean.getFlgUrgentePubblBUR() ? "1" : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_URGENTE_PUBBL_BUR_Doc", flgUrgentePubblBUR);
			}
		
			String dataPubblBUREntro = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_BUR_ENTRO")) {
				if(!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR") || _FLG_SI.equals(flgPubblBUR)) {
					if ("1".equals(flgUrgentePubblBUR)) {
						dataPubblBUREntro = bean.getDataPubblBUREntro() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblBUREntro()) : "";			
					}				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_BUR_ENTRO_Doc", dataPubblBUREntro);		
			}

		}

		/* Pubblicazione/notifiche - Pubblicazione sul notiziario */
		
		String flgPubblNotiziario = "";		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO")) {
			flgPubblNotiziario = bean.getFlgPubblNotiziario() != null  ? bean.getFlgPubblNotiziario() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO_Doc", flgPubblNotiziario);
		}
		
		/* Pubblicazione/notifiche - Esecutività */
		
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DATI_ESECUTIVITA")) {

			String flgImmediatamenteEseguibile = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_IMMEDIATAMENTE_ESEGUIBILE")) {
				flgImmediatamenteEseguibile = bean.getFlgImmediatamenteEseguibile() != null && bean.getFlgImmediatamenteEseguibile() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_IMMEDIATAMENTE_ESEGUIBILE_Ud", flgImmediatamenteEseguibile);
			}
		
			// la data di esecutività non si salva perchè è un dato sempre calcolato
		
			String motiviImmediatamenteEseguibile = "";
			if(showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVI_IE")) {
				if ("1".equals(flgImmediatamenteEseguibile)) {
					motiviImmediatamenteEseguibile = bean.getMotiviImmediatamenteEseguibile();
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOTIVI_IE_Ud", motiviImmediatamenteEseguibile); 
			}

		}

		/* Pubblicazione/notifiche - Da notificare a */
		
		List<SimpleValueBean> listaDestNotificaAtto = new ArrayList<SimpleValueBean>();
		if(showAttributoCustomCablato(setAttributiCustomCablati, "IND_EMAIL_DEST_NOTIFICA_ATTO")) {
			if(StringUtils.isNotBlank(bean.getListaDestNotificaAtto())) {
				String[] destinatari = IndirizziEmailSplitter.split(bean.getListaDestNotificaAtto());
				for(int i = 0; i < destinatari.length; i++) {
					SimpleValueBean lSimpleValueBean = new SimpleValueBean();
					lSimpleValueBean.setValue(destinatari[i]);
					listaDestNotificaAtto.add(lSimpleValueBean);
				}			
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "IND_EMAIL_DEST_NOTIFICA_ATTO_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestNotificaAtto)); 					
		}
		
		/* Dati spesa corrente */
		
		if(isAttivoSIB(bean)) {	
		
			String flgDisattivaAutoRequestDatiContabiliSIBCorrente = "";
			String prenotazioneSpesaSIBDiCorrente = "";
			String modalitaInvioDatiSpesaARagioneriaCorrente = "";
			List<DatiContabiliBean> listaDatiContabiliSIBCorrente = new ArrayList<DatiContabiliBean>();
			List<DatiContabiliBean> listaInvioDatiSpesaCorrente = new ArrayList<DatiContabiliBean>();
			String idUdXlsCorrente = "";
			String noteCorrente = "";
			
			if (_FLG_SI.equals(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro) && "1".equals(flgSpesaCorrente)) {					
				flgDisattivaAutoRequestDatiContabiliSIBCorrente = bean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() != null && bean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() ? "1" : "";
				prenotazioneSpesaSIBDiCorrente = bean.getPrenotazioneSpesaSIBDiCorrente() != null ? bean.getPrenotazioneSpesaSIBDiCorrente() : "";
				modalitaInvioDatiSpesaARagioneriaCorrente = bean.getModalitaInvioDatiSpesaARagioneriaCorrente() != null ? bean.getModalitaInvioDatiSpesaARagioneriaCorrente() : "";
				if(bean.getListaDatiContabiliSIBCorrente() != null) {
					listaDatiContabiliSIBCorrente = bean.getListaDatiContabiliSIBCorrente();
				}	
				if(bean.getListaInvioDatiSpesaCorrente() != null) {
					listaInvioDatiSpesaCorrente = bean.getListaInvioDatiSpesaCorrente();
				}	
				if(bean.getFileXlsCorrente() != null && !flgGenModello) {
					if(StringUtils.isBlank(bean.getFileXlsCorrente().getIdUd()) || (bean.getFileXlsCorrente().getIsChanged() != null && bean.getFileXlsCorrente().getIsChanged())) {
						idUdXlsCorrente = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, bean.getFileXlsCorrente(), getSession());
					} else if(StringUtils.isNotBlank(bean.getFileXlsCorrente().getIdUd())) {
						idUdXlsCorrente = bean.getFileXlsCorrente().getIdUd();
					}
				}					
				noteCorrente = bean.getNoteCorrente() != null ? bean.getNoteCorrente() : "";							
			}		
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CORR_AMC_Doc", flgDisattivaAutoRequestDatiContabiliSIBCorrente);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PRENOT_SPESA_DI_CORR_Doc", prenotazioneSpesaSIBDiCorrente);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOD_INVIO_CONT_CORR_Doc", modalitaInvioDatiSpesaARagioneriaCorrente);
			
			//ATTENZIONE: i punti separatori delle migliaia non vengono messi nelle colonne con gli importi, di conseguenza non vengono iniettati correttamente nel modello dei dati di spesa
			
			if(isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CORR_AMC_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliSIBCorrente));
			}
			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CORR_AUR_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioDatiSpesaCorrente));
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "XLS_DATI_CONT_CORR_Doc", idUdXlsCorrente);
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NOTE_CONT_CORR_Doc", noteCorrente);
		}
		
		/* Dati spesa conto capitale */
		
		if(isAttivoSIB(bean)) {
		
			String flgDisattivaAutoRequestDatiContabiliSIBContoCapitale = "";
			String modalitaInvioDatiSpesaARagioneriaContoCapitale = "";
			List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale = new ArrayList<DatiContabiliBean>();
			List<DatiContabiliBean> listaInvioDatiSpesaContoCapitale = new ArrayList<DatiContabiliBean>();
			String idUdXlsContoCapitale = "";
			String noteContoCapitale = "";
			
			if (_FLG_SI.equals(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro) && "1".equals(flgSpesaContoCapitale)) {					
				flgDisattivaAutoRequestDatiContabiliSIBContoCapitale = bean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() != null && bean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() ? "1" : "";
				modalitaInvioDatiSpesaARagioneriaContoCapitale = bean.getModalitaInvioDatiSpesaARagioneriaContoCapitale() != null ? bean.getModalitaInvioDatiSpesaARagioneriaContoCapitale() : "";
				if(bean.getListaDatiContabiliSIBContoCapitale() != null) {
					listaDatiContabiliSIBContoCapitale = bean.getListaDatiContabiliSIBContoCapitale();
				}	
				if(bean.getListaInvioDatiSpesaContoCapitale() != null) {
					listaInvioDatiSpesaContoCapitale = bean.getListaInvioDatiSpesaContoCapitale();
				}	
				if(bean.getFileXlsContoCapitale() != null && !flgGenModello) {
					if(StringUtils.isBlank(bean.getFileXlsContoCapitale().getIdUd()) || (bean.getFileXlsContoCapitale().getIsChanged() != null && bean.getFileXlsContoCapitale().getIsChanged())) {
						idUdXlsContoCapitale = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, bean.getFileXlsContoCapitale(), getSession());
					} else if(StringUtils.isNotBlank(bean.getFileXlsContoCapitale().getIdUd())) {
						idUdXlsContoCapitale = bean.getFileXlsContoCapitale().getIdUd();
					}
				}
				noteContoCapitale = bean.getNoteContoCapitale() != null ? bean.getNoteContoCapitale() : "";					
			}				
		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CCAP_AMC_Doc", flgDisattivaAutoRequestDatiContabiliSIBContoCapitale);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOD_INVIO_CONT_CCAP_Doc", modalitaInvioDatiSpesaARagioneriaContoCapitale);
			
			//ATTENZIONE: i punti separatori delle migliaia non vengono messi nelle colonne con gli importi, di conseguenza non vengono iniettati correttamente nel modello dei dati di spesa
			
			if(isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CCAP_AMC_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliSIBContoCapitale));
			}
			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CCAP_AUR_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioDatiSpesaContoCapitale));
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "XLS_DATI_CONT_CCAP_Doc", idUdXlsContoCapitale);
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NOTE_CONT_CCAP_Doc", noteContoCapitale);
		}
		
		/* Dati spesa personale */
		/*
		List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale = new ArrayList<DatiSpesaAnnualiXDetPersonaleXmlBean>();
		String capitoloDatiSpesaAnnuaXDetPers = "";		
		String articoloDatiSpesaAnnuaXDetPers = "";		
		String numeroDatiSpesaAnnuaXDetPers = "";		
		String importoDatiSpesaAnnuaXDetPers = "";		
		
		if("1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
			if(bean.getListaDatiSpesaAnnualiXDetPersonale() != null) {
				listaDatiSpesaAnnualiXDetPersonale = bean.getListaDatiSpesaAnnualiXDetPersonale();
			}
			capitoloDatiSpesaAnnuaXDetPers = bean.getCapitoloDatiSpesaAnnuaXDetPers() != null ? bean.getCapitoloDatiSpesaAnnuaXDetPers() : "";
			articoloDatiSpesaAnnuaXDetPers = bean.getArticoloDatiSpesaAnnuaXDetPers() != null ? bean.getArticoloDatiSpesaAnnuaXDetPers() : "";
			numeroDatiSpesaAnnuaXDetPers = bean.getNumeroDatiSpesaAnnuaXDetPers() != null ? bean.getNumeroDatiSpesaAnnuaXDetPers() : "";
			importoDatiSpesaAnnuaXDetPers = bean.getImportoDatiSpesaAnnuaXDetPers() != null ? bean.getImportoDatiSpesaAnnuaXDetPers() : "";
		}
		
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_GEST_DET_PERS_EXTRA_AMC")) {			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUALI_X_DET_PERSONALE", new XmlUtilitySerializer().createVariabileLista(listaDatiSpesaAnnualiXDetPersonale));
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUA_X_DET_PERS_COD_CAPITOLO_Doc", capitoloDatiSpesaAnnuaXDetPers);	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUA_X_DET_PERS_ARTICOLO_Doc", articoloDatiSpesaAnnuaXDetPers);	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUAX_DET_PERS_NRO_VOCE_PEG_Doc", numeroDatiSpesaAnnuaXDetPers);	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUA_X_DET_PERS_IMPORTO_Doc", importoDatiSpesaAnnuaXDetPers);			
		}
		*/
		
		/* Movimenti contabili */
		
		if(isAttivoContabilia(bean)) {	
			
			List<MovimentiContabiliaXmlBean> listaMovimentiContabilia = new ArrayList<MovimentiContabiliaXmlBean>();			
			if (_FLG_SI.equals(flgSpesa)) {					
				if(bean.getListaMovimentiContabilia() != null) {
					listaMovimentiContabilia = bean.getListaMovimentiContabilia();
				}					
			}
			
			// solo se sto generando da modello oppure se è attivo il salvataggio dei movimenti AMC
			if(flgGenModello || isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_CONTABILIA_Doc", new XmlUtilitySerializer().createVariabileLista(listaMovimentiContabilia));		
			}
		}
		
		// l'aggiornamento dei movimenti contabili di SICRA in DB va' fatto solo dopo la chiamata al servizio setMovimentiAtto di SICRA
		// MA nel caso di anteprima dei dati di spesa (generazione da modello) la variabile va' comunque passata, quindi la devo gestire anche qui
		if(isAttivoSICRA(bean)) {
			
			List<MovimentiContabiliSICRABean> listaInvioMovimentiContabiliSICRA = new ArrayList<MovimentiContabiliSICRABean>();					
			if (_FLG_SI.equals(flgSpesa)) {					
				if(bean.getListaInvioMovimentiContabiliSICRA() != null) {
					listaInvioMovimentiContabiliSICRA = bean.getListaInvioMovimentiContabiliSICRA();
				}	
			}
			
			// solo se sto generando da modello
			if(flgGenModello) { 
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_SICRA_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioMovimentiContabiliSICRA));
			}
		}
		
		/* Aggregato/smistamento ACTA */
		
		String fascSmistACTA = ParametriDBUtil.getParametroDB(getSession(), "FASC_SMIST_ACTA");
		if(fascSmistACTA != null && (_MANDATORY.equalsIgnoreCase(fascSmistACTA) || _OPTIONAL.equalsIgnoreCase(fascSmistACTA))) {
			
			String flgAggregatoACTA = bean.getFlgAggregatoACTA() != null && bean.getFlgAggregatoACTA() ? "1" : "";
			String flgSmistamentoACTA = bean.getFlgSmistamentoACTA() != null && bean.getFlgSmistamentoACTA() ? "1" : "";
			String opzioneIndiceClassificazioneFascicoloACTA = "";
			if(bean.getFlgIndiceClassificazioneACTA() != null && bean.getFlgIndiceClassificazioneACTA()) {
				opzioneIndiceClassificazioneFascicoloACTA = _OPZIONE_INDICE_CLASSIFICAZIONE_ACTA;
			} else if(bean.getFlgFascicoloACTA() != null && bean.getFlgFascicoloACTA()) {
				opzioneIndiceClassificazioneFascicoloACTA = _OPZIONE_AGGREGATO_ACTA;
			}
			String codIndiceClassificazioneACTA = "";
			if(bean.getFlgPresenzaClassificazioneACTA() != null && bean.getFlgPresenzaClassificazioneACTA()) {
				codIndiceClassificazioneACTA = bean.getCodIndiceClassificazioneACTA() != null  ? bean.getCodIndiceClassificazioneACTA() : "";
			}
			String codVoceTitolarioACTA = bean.getCodVoceTitolarioACTA() != null  ? bean.getCodVoceTitolarioACTA() : "";		
			String codFascicoloACTA = bean.getCodFascicoloACTA() != null  ? bean.getCodFascicoloACTA() : "";		
			String suffissoCodFascicoloACTA = bean.getSuffissoCodFascicoloACTA() != null  ? bean.getSuffissoCodFascicoloACTA() : "";		
			String idFascicoloACTA = bean.getIdFascicoloACTA() != null  ? bean.getIdFascicoloACTA() : "";		
			String idNodoSmistamentoACTA = bean.getIdNodoSmistamentoACTA() != null  ? bean.getIdNodoSmistamentoACTA() : "";	
			String desNodoSmistamentoACTA = bean.getDesNodoSmistamentoACTA() != null  ? bean.getDesNodoSmistamentoACTA() : "";	
		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_SCELTA_AGGR_ACTA_Ud", flgAggregatoACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_SMIST_ACTA_Ud", flgSmistamentoACTA); 
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "OPZ_CLASSIF_AGGR_ACTA_Ud", opzioneIndiceClassificazioneFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_INDICE_CLASSIF_ACTA_Ud", codIndiceClassificazioneACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_VOCE_TITOLARIO_ACTA_Ud", codVoceTitolarioACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_AGGREGATO_ACTA_Ud", codFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SUFF_NRO_AGGREGATO_ACTA_Ud", suffissoCodFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_AGGREGATO_ACTA_Ud", idFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_NODO_DEST_SMIST_ACTA_Ud", idNodoSmistamentoACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DES_NODO_DEST_SMIST_ACTA_Ud", desNodoSmistamentoACTA);			
		}
				
		// solo se sto generando da modello passo le liste degli allegati parte integrante separati
		if(flgGenModello) {			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FlgAllegatiParteIntUniti", bean.getFlgAllegatiParteIntUniti() != null && bean.getFlgAllegatiParteIntUniti() ? "1" : "");
			
			List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparati = new ArrayList<AllegatoParteIntSeparatoBean>();		
			if(bean.getListaAllegatiParteIntSeparati() != null) {
				listaAllegatiParteIntSeparati = bean.getListaAllegatiParteIntSeparati();
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ListaAllegatiParteIntSeparati", new XmlUtilitySerializer().createVariabileLista(listaAllegatiParteIntSeparati));
						
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FlgAllegatiParteIntUnitiXPubbl", bean.getFlgAllegatiParteIntUnitiXPubbl() != null && bean.getFlgAllegatiParteIntUnitiXPubbl() ? "1" : "");
			
			List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiXPubbl = new ArrayList<AllegatoParteIntSeparatoBean>();		
			if(bean.getListaAllegatiParteIntSeparatiXPubbl() != null) {
				listaAllegatiParteIntSeparatiXPubbl = bean.getListaAllegatiParteIntSeparatiXPubbl();
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ListaAllegatiParteIntSeparatiXPubbl", new XmlUtilitySerializer().createVariabileLista(listaAllegatiParteIntSeparatiXPubbl));			
		}

	}

	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {	
		if(sezioneCache != null && sezioneCache.getVariabile() != null) {
			for(int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if(var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileLista(nomeVariabile, lista));
		}
	}
	
	// questa è vecchia e non andrebbe più usata
	private String getDatiXModelliPratica(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkProcessesGetdatixmodellipratica lDmpkProcessesGetdatixmodellipratica = new DmpkProcessesGetdatixmodellipratica();
		DmpkProcessesGetdatixmodellipraticaBean lDmpkProcessesGetdatixmodellipraticaInput = new DmpkProcessesGetdatixmodellipraticaBean();
		lDmpkProcessesGetdatixmodellipraticaInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkProcessesGetdatixmodellipraticaInput.setIdprocessin(BigDecimal.valueOf(Long.valueOf(bean.getIdProcess())));
		// Creo gli attributi addizionali
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici, true);
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		lDmpkProcessesGetdatixmodellipraticaInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> lDmpkProcessesGetdatixmodellipraticaOutput = lDmpkProcessesGetdatixmodellipratica.execute(getLocale(), loginBean,
				lDmpkProcessesGetdatixmodellipraticaInput);
		if (lDmpkProcessesGetdatixmodellipraticaOutput.isInError()) {
			throw new StoreException(lDmpkProcessesGetdatixmodellipraticaOutput);
		}
		
		return lDmpkProcessesGetdatixmodellipraticaOutput.getResultBean().getDatimodellixmlout();
	}
	
	private String getDatiXGenDaModello(NuovaPropostaAtto2CompletaBean bean, String nomeModello) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUd());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		
		// Creo gli attributi addizionali
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici, true);
		
		if(StringUtils.isNotBlank(getExtraparams().get("esitoTask"))) {
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "#EsitoTask", getExtraparams().get("esitoTask"));		
		}
		
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	public FileDaFirmareBean generaDispositivoDaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		/*
		if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
			String idProcessToForcePubbl = ParametriDBUtil.getParametroDB(getSession(), "ID_PROC_TO_FORCE_PUBBL");
			if(idProcessToForcePubbl != null && bean.getIdProcess() != null && idProcessToForcePubbl.equals(bean.getIdProcess()) 
			   && bean.getDataInizioPubblicazione() != null && bean.getGiorniPubblicazione() != null) {
				pubblica(bean);
			}
		}
		*/
		
		boolean flgAllegatiParteIntUnitiVersIntegrale = false;
		boolean flgAllegatiParteIntUnitiVersXPubbl = false;
		List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersIntegrale = new ArrayList<AllegatoParteIntSeparatoBean>();
		List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersXPubbl = new ArrayList<AllegatoParteIntSeparatoBean>();
		if (bean.getListaAllegati() != null) {
			boolean flgPubblicaAllegatiSeparati = bean.getFlgPubblicaAllegatiSeparati() != null && bean.getFlgPubblicaAllegatiSeparati(); // se è true tutti gli allegati sono da pubblicare separatamente		
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaAllegati()){
				boolean flgParteDispositivo = lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo();
				boolean flgPubblicaSeparato = lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null && lAllegatoProtocolloBean.getFlgPubblicaSeparato();
				boolean flgNoPubblAllegato = lAllegatoProtocolloBean.getFlgNoPubblAllegato() != null && lAllegatoProtocolloBean.getFlgNoPubblAllegato();				
				if (flgParteDispositivo) { // se è parte integrante						
					if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
						// se è la vers. integrale
						if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
							AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
							lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
							lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
							lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
							lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
							lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
							lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
							listaAllegatiParteIntSeparatiVersIntegrale.add(lAllegatoParteIntSeparatoBean);						
						} else {
							flgAllegatiParteIntUnitiVersIntegrale = true;
						}
					} else if(!flgNoPubblAllegato) {
						// se è la vers. per la pubbl. e l'allegato non è escluso dalla pubblicazione
						if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
							if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) {
								AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBeanOmissis = new AllegatoParteIntSeparatoBean();
								lAllegatoParteIntSeparatoBeanOmissis.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
								lAllegatoParteIntSeparatoBeanOmissis.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
								lAllegatoParteIntSeparatoBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
								lAllegatoParteIntSeparatoBeanOmissis.setImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getImpronta() : null);
								lAllegatoParteIntSeparatoBeanOmissis.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getAlgoritmo() : null);
								lAllegatoParteIntSeparatoBeanOmissis.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getEncoding() : null);
								listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBeanOmissis);	
							} else {
								AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
								lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
								lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
								lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
								lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
								lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
								lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
								listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBean);	
							}
						} else {
							flgAllegatiParteIntUnitiVersXPubbl = true;
						}
					}
				}
			}
		}
		
		if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
			// se è la vers. integrale
			bean.setFlgAllegatiParteIntUniti(flgAllegatiParteIntUnitiVersIntegrale);
			bean.setListaAllegatiParteIntSeparati(listaAllegatiParteIntSeparatiVersIntegrale);
			bean.setFlgAllegatiParteIntUnitiXPubbl(null);
			bean.setListaAllegatiParteIntSeparatiXPubbl(null);
		} else {
			// se è la vers. per la pubbl.
			bean.setFlgAllegatiParteIntUniti(null);
			bean.setListaAllegatiParteIntSeparati(null);
			bean.setFlgAllegatiParteIntUnitiXPubbl(flgAllegatiParteIntUnitiVersXPubbl);
			bean.setListaAllegatiParteIntSeparatiXPubbl(listaAllegatiParteIntSeparatiVersXPubbl);
		}
		
		return generaDispositivoDaModello(bean, true);
	}
	
	private FileDaFirmareBean generaDispositivoDaModello(NuovaPropostaAtto2CompletaBean bean, boolean flgConvertiInPdf) throws Exception {
		
		String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // DISPOSITIVO_DETERMINA
		
		// Ho tolto la parte cablata (vedi il vecchio generaDispositivoDaModelloCablato) e passo direttamente la SezioneCache per l'iniezione nel modello		
		Map<String, Object> model = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		
		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), model, flgConvertiInPdf, bean.getFlgMostraDatiSensibili(), bean.getFlgMostraOmissisBarrati(), getSession());
		
//		fillModelBean.getInfoFile().setCorrectFileName(bean.getDisplayFilenameModello());
		
		String ext = flgConvertiInPdf ? "pdf" : FilenameUtils.getExtension(fillModelBean.getNomeFile());
		String nomeFilePdf = null;
		if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
			if(bean.getFlgMostraOmissisBarrati() != null && bean.getFlgMostraOmissisBarrati()) {
				nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_VERS_PER_VERIFICA_OMISSIS.%s", ext);
			} else {
				nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_VERS_INTEGRALE.%s", ext);
			}
		} else {
			nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_VERS_X_PUBBLICAZIONE.%s", ext);
		}
		fillModelBean.setNomeFile(nomeFilePdf);
		
		return fillModelBean;
	}
	
	public FileDaFirmareBean generaDatiSpesaDaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {
	
		String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // APPENDICE DATI DI SPESA
		Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, false, getSession()); 
		fillModelBean.setNomeFile(getPrefixRegNum(bean) + "MOVIMENTI_CONTABILI.pdf");	
		
		return fillModelBean;
	}	
	
	public FileDaFirmareBean generaRiepilogoFirmeVistiDaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		if(StringUtils.isNotBlank(bean.getIdModello()) && StringUtils.isNotBlank(bean.getNomeModello())) {
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // RIEPILOGO FIRME E VISTI
			Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, false, getSession());
			fillModelBean.setNomeFile(getPrefixRegNum(bean) + "FOGLIO_RIEPILOGO.pdf");
			
			return fillModelBean;
			
		} else {				
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			if(StringUtils.isBlank(bean.getNomeModello())) {
				bean.setNomeModello("FOGLIO_FIRME_VISTI");
			}
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(bean.getNomeModello());
			
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			bean.setUriModello(lExtractvermodelloOutput.getResultBean().getUriverout());
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // RIEPILOGO FIRME E VISTI
			
			File templateWithValues = ModelliUtil.fillTemplateAndConvertToPdf(bean.getIdProcess(), bean.getUriModello(), null, templateValues, getSession());
			
			String nomeFile = getPrefixRegNum(bean) + "FOGLIO_RIEPILOGO.pdf";
			String uriFile = StorageImplementation.getStorage().store(templateWithValues);
			MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString(), nomeFile, false, null);
			
			FileDaFirmareBean fillModelBean = new FileDaFirmareBean();
			fillModelBean.setNomeFile(nomeFile);	
			fillModelBean.setUri(uriFile);
			fillModelBean.setInfoFile(lMimeTypeFirmaBean);
			
			return fillModelBean;
		} 
	}	
	
	public void versionaDocumento(IdFileBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdFile()));
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(bean.getUri()))));
		MimeTypeFirmaBean lMimeTypeFirmaBean;
		if(bean.getInfoFile() == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		} else {
			lMimeTypeFirmaBean = bean.getInfoFile();
		}
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaFirma(lGenericFile, lMimeTypeFirmaBean);
//		if (lMimeTypeFirmaBean.getFirmatari() != null) {
//			List<Firmatario> lList = new ArrayList<Firmatario>();
//			for (String lString : lMimeTypeFirmaBean.getFirmatari()) {
//				Firmatario lFirmatario = new Firmatario();
//				lFirmatario.setCommonName(lString);
//				lList.add(lFirmatario);
//			}
//			lGenericFile.setFirmatari(lList);
//			lGenericFile.setFirmato(Flag.SETTED);
//		} else if (lMimeTypeFirmaBean.isFirmato()) {
//			lGenericFile.setFirmato(Flag.SETTED);
//		}
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(bean.getNomeFile());
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		if (lMimeTypeFirmaBean.isDaScansione()) {
			lGenericFile.setDaScansione(Flag.SETTED);
			lGenericFile.setDataScansione(new Date());
			lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
		}
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
		if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
			logger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}	
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnire(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception{
		ArrayList<FileDaFirmareBean> listaFileDaUnire = new ArrayList<FileDaFirmareBean>();
		pNuovaPropostaAtto2CompletaBean.setFlgMostraDatiSensibili(true); // per generare la vers. integrale
		FileDaFirmareBean lFileDispositivoBean  = generaDispositivoDaModello(pNuovaPropostaAtto2CompletaBean, true);
		lFileDispositivoBean.setIsDispositivoNuovaPropostaAtto2Completa(true);
		listaFileDaUnire.add(lFileDispositivoBean);		
		String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd();
		if (pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati() == null || !pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati()) { // se tutti gli allegati non sono pubblicati separatamente		
			if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
				for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
					if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
						if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() == null || !lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se non è da pubblicare separatamente						
							lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
							aggiungiAllegato(listaFileDaUnire, lAllegatoProtocolloBean);
						}
					}
				}
			}
		}
		return listaFileDaUnire;
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnireOmissis(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception{
		ArrayList<FileDaFirmareBean> listaFileDaUnireOmissis = new ArrayList<FileDaFirmareBean>();
		pNuovaPropostaAtto2CompletaBean.setFlgMostraDatiSensibili(false); // per generare la vers. con omissis
		FileDaFirmareBean lFileDispositivoOmissisBean  = generaDispositivoDaModello(pNuovaPropostaAtto2CompletaBean, true);
		lFileDispositivoOmissisBean.setIsDispositivoNuovaPropostaAtto2Completa(true);
		listaFileDaUnireOmissis.add(lFileDispositivoOmissisBean);		
		String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd();
		if (pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati() == null || !pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati()) { // se tutti gli allegati non sono pubblicati separatamente				
			if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
				for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
					if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
						if (lAllegatoProtocolloBean.getFlgNoPubblAllegato() == null || !lAllegatoProtocolloBean.getFlgNoPubblAllegato()) { // se non è escluso dalla pubblicazione
							if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() == null || !lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se non è da pubblicare separatamente
								lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
								if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) {
									aggiungiAllegatoOmissis(listaFileDaUnireOmissis, lAllegatoProtocolloBean);
								} else {
									aggiungiAllegato(listaFileDaUnireOmissis, lAllegatoProtocolloBean);
								}
							}
						}
					}
				}			
			}
		}
		return listaFileDaUnireOmissis;
	}
	
	public AttoRiferimentoBean recuperaIdUdAttoRiferimento(AttoRiferimentoBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		boolean hideMessageError = getExtraparams().get("hideMessageError") != null && "true".equals(getExtraparams().get("hideMessageError"));
		
		DmpkCoreFindudBean input = new DmpkCoreFindudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCategoriaReg());
		input.setSiglaregin(bean.getSigla());
		input.setNumregin(bean.getNumero() != null ? Integer.parseInt(bean.getNumero()) : null);
		input.setAnnoregin(bean.getAnno() != null ? Integer.parseInt(bean.getAnno()) : null);
		
		DmpkCoreFindud lDmpkCoreFindud = new DmpkCoreFindud();
		StoreResultBean<DmpkCoreFindudBean> output = lDmpkCoreFindud.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (!hideMessageError) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
			}
		}		
		bean.setIdUd((output.getResultBean() != null && output.getResultBean().getIdudio() != null) ? String.valueOf(output.getResultBean().getIdudio().longValue()) : null);
			
		return bean;
	}
	
	/*
	public NuovaPropostaAtto2CompletaBean recuperaIdUdAttoDeterminaAContrarre(NuovaPropostaAtto2CompletaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		boolean hideMessageError = getExtraparams().get("hideMessageError") != null && "true".equals(getExtraparams().get("hideMessageError"));
		
		DmpkCoreFindudBean input = new DmpkCoreFindudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCategoriaRegAttoDeterminaAContrarre());
		input.setSiglaregin(bean.getSiglaAttoDeterminaAContrarre());
		input.setNumregin(bean.getNumeroAttoDeterminaAContrarre() != null ? Integer.parseInt(bean.getNumeroAttoDeterminaAContrarre()) : null);
		input.setAnnoregin(bean.getAnnoAttoDeterminaAContrarre() != null ? Integer.parseInt(bean.getAnnoAttoDeterminaAContrarre()) : null);
		
		DmpkCoreFindud lDmpkCoreFindud = new DmpkCoreFindud();
		StoreResultBean<DmpkCoreFindudBean> output = lDmpkCoreFindud.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (!hideMessageError) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
			}
		}		
		bean.setIdUdAttoDeterminaAContrarre((output.getResultBean() != null && output.getResultBean().getIdudio() != null) ? String.valueOf(output.getResultBean().getIdudio().longValue()) : null);
			
		return bean; 
	}
	*/
	
	public AllegatoProtocolloBean getAllegatoVistoContabile(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			String idTipoDocAllegatoVistoContabile = ParametriDBUtil.getParametroDB(getSession(), "ID_DOC_TYPE_VISTO_CONTAB_ITER_ATTI");
			if (StringUtils.isNotBlank(idTipoDocAllegatoVistoContabile)) {
				for (int i = 0; i < pNuovaPropostaAtto2CompletaBean.getListaAllegati().size(); i++) {
					AllegatoProtocolloBean lAllegatoProtocolloBean = pNuovaPropostaAtto2CompletaBean.getListaAllegati().get(i);
					if (lAllegatoProtocolloBean.getListaTipiFileAllegato() != null && lAllegatoProtocolloBean.getListaTipiFileAllegato().equalsIgnoreCase(idTipoDocAllegatoVistoContabile)) {
						return lAllegatoProtocolloBean;
					}
				}
			}
		}
		return null;
	}
	
	public NuovaPropostaAtto2CompletaBean pubblica(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		NuovaPropostaAtto2CompletaBean bean = null;
		
		String sistemaPubblicazione = ParametriDBUtil.getParametroDB(getSession(), "SIST_ALBO");
		
		//Se la pubblicazione deve avvenire con chiamata ai WS dell'albo di ReggioCalabria
		if(StringUtils.isNotBlank(sistemaPubblicazione) && "ALBO_CORC".equalsIgnoreCase(sistemaPubblicazione)) {
			bean = pubblicaAlboReggio(pNuovaPropostaAtto2CompletaBean);
		//Se la pubblicazione deve avvenire con chiamata ai WS dell'albo di Area Vasta Bari
		} else if(StringUtils.isNotBlank(sistemaPubblicazione) && "ALBO_AVB".equalsIgnoreCase(sistemaPubblicazione)) {
			bean = pubblicaAlboAVB(pNuovaPropostaAtto2CompletaBean);
		} else {
			bean = pubblicaAlbo(pNuovaPropostaAtto2CompletaBean);
		}
		
		return bean;
	}

	
	public NuovaPropostaAtto2CompletaBean pubblicaAlboReggio(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		boolean primarioOmissis=false;
		
		// Mi recupero i dati di registrazione dopo che ho salvato
		NuovaPropostaAtto2CompletaBean bean = get(pNuovaPropostaAtto2CompletaBean);
		
		try {
			boolean skipOmissisInFirmaAdozioneAtto = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
			boolean convPdfPreFirma = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "CONV_PDF_PRE_FIRMA");
			File fileDaPubblicare = null;
			MimeTypeFirmaBean infoFileDaPubblicare = null;
			boolean flgDatiSensibili = bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili();
			if (!flgDatiSensibili && StringUtils.isNotBlank(bean.getUriFilePrimario())) {
				logger.debug("Pubblico il file primario (vers. integrale): " + bean.getUriFilePrimario());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
				infoFileDaPubblicare = bean.getInfoFilePrimario();
			} else if (StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
				logger.debug("Pubblico il file primario (vers. con omissis): " + bean.getUriFilePrimarioOmissis());
				if(!bean.getInfoFilePrimarioOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {					
					ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(bean.getUriFilePrimarioOmissis(),
							bean.getNomeFilePrimarioOmissis(), bean.getInfoFilePrimarioOmissis()));					
					bean.setUriFilePrimarioOmissis(outputBean.getFiles().get(0).getUri());
					bean.setNomeFilePrimarioOmissis(outputBean.getFiles().get(0).getNomeFile());
					bean.setInfoFilePrimarioOmissis(outputBean.getFiles().get(0).getInfoFile());
				}
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimarioOmissis());
				infoFileDaPubblicare = bean.getInfoFilePrimarioOmissis();
				primarioOmissis = true;
			}
			String ext = "pdf";
			if (infoFileDaPubblicare != null && infoFileDaPubblicare.isFirmato()
					&& infoFileDaPubblicare.getTipoFirma() != null
					&& infoFileDaPubblicare.getTipoFirma().startsWith("CAdES")) {
				ext = "pdf.p7m";
			}
			if (fileDaPubblicare != null) {
				// Setto i parametri della richiesta
				AlboReggioAtto attoDaPubblicare = new AlboReggioAtto();

				if (StringUtils.isNotBlank(bean.getSiglaRegistrazione()) && StringUtils.isNotBlank(bean.getNumeroRegistrazione()) && bean.getDataRegistrazione() != null) {
					attoDaPubblicare.setNumero(Integer.valueOf(bean.getNumeroRegistrazione()));
					attoDaPubblicare.setAnno(bean.getDataRegistrazione() != null? new Integer(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataRegistrazione()).substring(6)): null);
					attoDaPubblicare.setData(setDateForAlbo(bean.getDataRegistrazione()));
					attoDaPubblicare.setDataInserimento(setDateForAlbo(bean.getDataInizioPubblicazione()));
				} else {
					throw new StoreException("Mancano gli estremi di registrazione del documento");
				}

				if (bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
					GregorianCalendar calPubblicazione = new GregorianCalendar();
					calPubblicazione.setTime(bean.getDataInizioPubblicazione());
					attoDaPubblicare.setDataInizio(setDateForAlbo(calPubblicazione.getTime()));
					calPubblicazione.add(Calendar.DAY_OF_YEAR, Integer.parseInt(bean.getGiorniPubblicazione()));
					attoDaPubblicare.setDataFine(setDateForAlbo(calPubblicazione.getTime()));
				} else {
					throw new StoreException("Mancano i dati relativi al periodo di pubblicazione");
				}

				String enteProvenienza = ParametriDBUtil.getParametroDB(getSession(), "ENTE_PROVENIENZA_ALBO_PRETORIO");
				attoDaPubblicare.setProvenienza(enteProvenienza != null && !"".equals(enteProvenienza) ? enteProvenienza : "Comune di Reggio Calabria");
				
				String tipoDocumento = ParametriDBUtil.getParametroDB(getSession(), "TIPO_DOCUMENTO_ALBO_PRETORIO");
				attoDaPubblicare.setIdType(tipoDocumento != null && !"".equals(tipoDocumento) ? tipoDocumento : "00000000-0000-0000-0000-000000000001");

				String idUtente = ParametriDBUtil.getParametroDB(getSession(), "ID_UTENTE_PUBBL_ALBO");
				attoDaPubblicare.setIdUtente(idUtente != null && !"".equals(idUtente) ? idUtente : "MuB6DV3JYUmWzBt]@[jZ");

				attoDaPubblicare.setIdUo(bean.getIdUoAlboReggio());
				
				attoDaPubblicare.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di pubblicazione all'Albo Pretorio, con la versione con omissis di oggettoHtml

				attoDaPubblicare.setManualeImport("I");
				
				logger.debug("---- INVOCO I WS DI PUBBLICAZIONE ALBO DI REGGIO CALABRIA ----");
				
				logger.debug("--REQUEST-- servizio nuovoAtto");
				//Trasformo l'oggetto in xml per poterlo scrivere nei log e averne traccia				
				logger.debug(convertBeanToXml(attoDaPubblicare));				

				// Invoco il WS Albo Pretorio di Reggio Calabria per la pubblicazione dell'atto senza file
				AlboPretorioReggioImpl service = new AlboPretorioReggioImpl();
				Result<AlboReggioResult> output = service.nuovoatto(getLocale(), attoDaPubblicare);

				//da Ok solo se viene pubblicato l'atto, e cioè se mi ritorna l'idRecord di pubblicazione (vedere nel progetto AlboReggioClient come inizializzo l ok)
				if (!output.isOk()) {
					String errore;
					if (output.isTimeout()) {
						errore = "La chiamata al WS di pubblicazione dell'atto è andata in timeout";
						logger.error(errore);
					} else if (output.isRispostaNonRicevuta()) {
						errore = "La chiamata al WS di pubblicazione dell'atto non ha resituito nessuna risposta: " +  output.getMessage();
						logger.error(errore);
					} else {
						if(output.getPayload()!=null && StringUtils.isNotBlank(output.getPayload().getMessaggio())) {
							 errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getMessaggio() + ", codice: " + output.getPayload().getCodice();
							 logger.error("--RESPONSE-- servizio nuovoAtto: " + errore);
						}else {
							errore = "La chiamata al WS di pubblicazione dell'atto non ha restiuito nessun esito"+  output.getMessage(); // se il Payload() == null o non c'è messaggio
							logger.error(errore);
						}
					}
					
					//Invio mail con relativo errore di fallita pubblicazione
					sendMailErrorePubblicazione("nuovoAtto", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
				
				} else {
					// Se la pubblicazione degli estremi dell'atto è andata a buon fine aggiungo il  file primario chiamando il WS aggiungi allegato
					if (output.getPayload() != null && StringUtils.isNotBlank(output.getPayload().getIdRecord())) {
						
							logger.debug("--RESPONSE-- servizio nuovoAtto: " + convertBeanToXml(output.getPayload()));
						
							//id di pubblicazione restituito dal servizio dell'albo
							idPubblicazione = output.getPayload().getIdRecord();
							
							aggiornaIdAlbo(pNuovaPropostaAtto2CompletaBean.getIdUd(), idPubblicazione);

							AlboReggioAllegato filePrimarioDaPubblicare = new AlboReggioAllegato();
							filePrimarioDaPubblicare.setIdAtto(idPubblicazione);
							filePrimarioDaPubblicare.setPrincipale("S"); // S=Si N=No
							filePrimarioDaPubblicare.setEstensione(ext);

							// trasformo il file in base64
							String base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(fileDaPubblicare));
							filePrimarioDaPubblicare.setBase64File(base64File);

							if (primarioOmissis) {
								filePrimarioDaPubblicare.setNomeFile(bean.getNomeFilePrimarioOmissis());
								filePrimarioDaPubblicare.setDescrizione(bean.getNomeFilePrimarioOmissis());
								filePrimarioDaPubblicare.setTipo("E"); // I= Integrale E = Estratto

							} else {
								filePrimarioDaPubblicare.setNomeFile(bean.getNomeFilePrimario());
								filePrimarioDaPubblicare.setDescrizione(bean.getNomeFilePrimario());
								filePrimarioDaPubblicare.setTipo("I"); // I= Integrale E = Estratto
							}
							
							logger.debug("--REQUEST-- servizio nuovoAllegato");
							logger.debug(convertBeanToXml(filePrimarioDaPubblicare));

							Result<AlboReggioResult> output2 = service.nuovoallegato(getLocale(), filePrimarioDaPubblicare);

							//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboReggioClient come inizializzo l' OK)
							if (!output2.isOk()) {
								String errore;
								if (output2.isTimeout()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
									logger.error(errore);
								} else if (output2.isRispostaNonRicevuta()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " +  output2.getMessage();
									logger.error(errore);
								} else {
									if(output2.getPayload()!=null && StringUtils.isNotBlank(output2.getPayload().getMessaggio())) {
										 errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output2.getPayload().getMessaggio() + ", codice: " + output2.getPayload().getCodice();
										 logger.error("--RESPONSE-- servizio nuovoAllegato: " + errore);
									}else {
										errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" +  output2.getMessage(); // se il Payload() == null o non c'è messaggio
										logger.error(errore);
									}
								}
								
								//Invio mail con relativo errore di fallita pubblicazione
								sendMailErrorePubblicazione("nuovoAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
							} else {
								
								logger.debug("--RESPONSE-- servizio nuovoAllegato: Aggiunto file primario: " + filePrimarioDaPubblicare.getDescrizione() + " all'atto pubblicato con id: " + idPubblicazione);
								
								List<AllegatoProtocolloBean> listaAllegatiDaPubblicare = new ArrayList<>();

								// Nel caso di determina con spesa, devo recuperare le informazioni dell'allegato del visto contabile  generato in quel task
								// se non è stato generato in quel task, ma in uno precedente, me lo recupero dalla lista allegati 
								if(bean.getAllegatoVistoContabile() == null) {
									bean.setAllegatoVistoContabile(getAllegatoVistoContabile(pNuovaPropostaAtto2CompletaBean));
								}
								if (bean.getAllegatoVistoContabile() != null) {
									listaAllegatiDaPubblicare.add(bean.getAllegatoVistoContabile());
								}

								// Controllo se ci sono anche allegati dell'atto da pubblicare separatamente
								if (bean.getListaAllegati() != null) {
									for (AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaAllegati()) {
										if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null
												&& lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
											if(lAllegatoProtocolloBean.getFlgNoPubblAllegato() == null
													|| !lAllegatoProtocolloBean.getFlgNoPubblAllegato()) { // // se non è escluso dalla pubblicazione
												if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null
														&& lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se è da pubblicare separatamente
													listaAllegatiDaPubblicare.add(lAllegatoProtocolloBean);
												}
											}
										}
									}
								}

								if(listaAllegatiDaPubblicare!=null && listaAllegatiDaPubblicare.size()>=1) {
									logger.debug("Pubblico gli allegati");
								}
								
								for (AllegatoProtocolloBean allegato : listaAllegatiDaPubblicare) {
									File fileAllegatoDaPubblicare = null;
									boolean allegatoOmissis = false;

									if (allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili() && StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
										if(!allegato.getInfoFileOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {		
											ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(allegato.getUriFileOmissis(),
													allegato.getNomeFileOmissis(), allegato.getInfoFileOmissis()));					
											allegato.setUriFileOmissis(outputBean.getFiles().get(0).getUri());
											allegato.setNomeFileOmissis(outputBean.getFiles().get(0).getNomeFile());
											allegato.setInfoFileOmissis(outputBean.getFiles().get(0).getInfoFile());
										}
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
										allegatoOmissis = true;
									}
									else if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
									}else {
										String errore = "La chiamata al WS di aggiunta allegato con nome: " + allegato.getNomeFileAllegato() != null ? allegato.getNomeFileAllegato() : allegato.getNomeFileOmissis() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: uri allegato non presente";
										logger.error(errore);
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("nuovoAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
									}

									AlboReggioAllegato allegatoDaPubblicare = new AlboReggioAllegato();
									allegatoDaPubblicare.setIdAtto(idPubblicazione);
									allegatoDaPubblicare.setPrincipale("N"); // S=Si N=No

									// trasformo il file in base64
									String base64FileAllegato = Base64.encodeBase64String(FileUtils.readFileToByteArray(fileAllegatoDaPubblicare));
									allegatoDaPubblicare.setBase64File(base64FileAllegato);
									
									if (allegatoOmissis) {
										allegatoDaPubblicare.setNomeFile(allegato.getNomeFileOmissis());
										allegatoDaPubblicare.setDescrizione(allegato.getNomeFileOmissis());  // è il nome del file che compare sull albo
										allegatoDaPubblicare.setTipo("E"); // I= Integrale E = Estretto
										allegatoDaPubblicare.setEstensione(FilenameUtils.getExtension(allegato.getNomeFileOmissis()));

									} else {
										allegatoDaPubblicare.setNomeFile(allegato.getNomeFileAllegato());
										allegatoDaPubblicare.setDescrizione(allegato.getNomeFileAllegato()); // è il nome del file che compare sull albo
										allegatoDaPubblicare.setTipo("I"); // I= Integrale E = Estretto
										allegatoDaPubblicare.setEstensione(FilenameUtils.getExtension(allegato.getNomeFileAllegato()));
									}

									logger.debug("--REQUEST-- servizio nuovoAllegato");
									logger.debug(convertBeanToXml(allegatoDaPubblicare));
									
									Result<AlboReggioResult> output3 = service.nuovoallegato(getLocale(), allegatoDaPubblicare);

									//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboReggioClient come inizializzo l' OK)
									if (!output3.isOk()) {
										String errore;
										if (output3.isTimeout()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
											logger.error(errore);
										} else if (output3.isRispostaNonRicevuta()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " + output3.getMessage();
											logger.error(errore);
										} else {
											if(output3.getPayload()!=null && StringUtils.isNotBlank(output3.getPayload().getMessaggio())) {
												 errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output3.getPayload().getMessaggio() + ", codice: " + output3.getPayload().getCodice();
												 logger.error("--RESPONSE-- servizio nuovoAllegato: " + errore);
											}else {
												errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" + output3.getMessage(); // se il Payload() == null o non c'è messaggio
												logger.error(errore);
											}
										}
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("nuovoAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
									
									}
									logger.debug("--RESPONSE-- servizio nuovoAllegato: Aggiunto file primario: " + allegatoDaPubblicare.getDescrizione() + " all'atto pubblicato con id: " + idPubblicazione);
								}
								
								logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
								
								addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);
								
							}
					}else {
						String errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getMessaggio() + ", codice: " + output.getPayload().getCodice();
						logger.error("--RESPONSE-- servizio nuovoAtto: " + errore);
						
						//Invio mail con relativo errore di fallita pubblicazione
						sendMailErrorePubblicazione("nuovoAtto", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
					}
				}
			} else {
				throw new StoreException("Nessun file da pubblicare");
			}
		} catch (Throwable e) {
			logger.error("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
			throw new StoreException("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage());
		}

		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean pubblicaAlboAVB(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		boolean primarioOmissis=false;
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		// Mi recupero i dati di registrazione dopo che ho salvato
		NuovaPropostaAtto2CompletaBean bean = get(pNuovaPropostaAtto2CompletaBean);
		
		try {
			boolean skipOmissisInFirmaAdozioneAtto = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
			boolean convPdfPreFirma = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "CONV_PDF_PRE_FIRMA");
			File fileDaPubblicare = null;
			MimeTypeFirmaBean infoFileDaPubblicare = null;
			boolean flgDatiSensibili = bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili();
			if (!flgDatiSensibili && StringUtils.isNotBlank(bean.getUriFilePrimario())) {
				logger.debug("Pubblico il file primario (vers. integrale): " + bean.getUriFilePrimario());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
				infoFileDaPubblicare = bean.getInfoFilePrimario();
			} else if (StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
				logger.debug("Pubblico il file primario (vers. con omissis): " + bean.getUriFilePrimarioOmissis());
				if(!bean.getInfoFilePrimarioOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {					
					ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(bean.getUriFilePrimarioOmissis(),
							bean.getNomeFilePrimarioOmissis(), bean.getInfoFilePrimarioOmissis()));					
					bean.setUriFilePrimarioOmissis(outputBean.getFiles().get(0).getUri());
					bean.setNomeFilePrimarioOmissis(outputBean.getFiles().get(0).getNomeFile());
					bean.setInfoFilePrimarioOmissis(outputBean.getFiles().get(0).getInfoFile());
				}
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimarioOmissis());
				infoFileDaPubblicare = bean.getInfoFilePrimarioOmissis();
				primarioOmissis = true;
			}
			String ext = "pdf";
			if (infoFileDaPubblicare != null && infoFileDaPubblicare.isFirmato()
					&& infoFileDaPubblicare.getTipoFirma() != null
					&& infoFileDaPubblicare.getTipoFirma().startsWith("CAdES")) {
				ext = "pdf.p7m";
			}
			if (fileDaPubblicare != null) {
				// Setto i parametri della richiesta
				AlboAVBPubblicaAttoIn attoDaPubblicare = new AlboAVBPubblicaAttoIn();

				if (StringUtils.isNotBlank(bean.getSiglaRegistrazione()) && StringUtils.isNotBlank(bean.getNumeroRegistrazione()) && bean.getDataRegistrazione() != null) {
					attoDaPubblicare.setNumeroRegistroGenerale(String.valueOf(bean.getNumeroRegistrazione()));
					attoDaPubblicare.setDataAdozione(setDateForAlbo(bean.getDataRegistrazione()));
					attoDaPubblicare.setDataPubblicazione(setDateForAlbo(bean.getDataRegistrazione()));
				} else {
					throw new StoreException("Mancano gli estremi di registrazione del documento");
				}

				if (bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
					attoDaPubblicare.setDurataPubblicazioneAtto(Integer.parseInt(bean.getGiorniPubblicazione()));
					
				} else {
					throw new StoreException("Mancano i dati relativi al periodo di pubblicazione");
				}

				String enteProvenienza = ParametriDBUtil.getParametroDB(getSession(), "ENTE_PROVENIENZA_ALBO_PRETORIO");
				attoDaPubblicare.setEnteRichiestaPubblicazione(enteProvenienza != null && !"".equals(enteProvenienza) ? enteProvenienza : "Comune aderente al progetto Area Vasta Metropoli Terra di Bari");
				
				DaoTRelAlboAvbVsAuriga dao = new DaoTRelAlboAvbVsAuriga();
				
				BigDecimal getidattoavb = dao.getidattoavb(getLocale(), loginBean, new BigDecimal(bean.getTipoDocumento()));
				
				Long idTipoAtto = getidattoavb.longValue();
				attoDaPubblicare.setIdTipoAtto(idTipoAtto != null ? idTipoAtto : new Long(1));

				String idUtente = ParametriDBUtil.getParametroDB(getSession(), "ID_UTENTE_PUBBL_ALBO");
				attoDaPubblicare.setUsernameResponsabilePubblicazione(idUtente != null && !"".equals(idUtente) ? idUtente : "");

				attoDaPubblicare.setNomeArea(bean.getUfficioProponente());
				
				attoDaPubblicare.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di pubblicazione all'Albo Pretorio, con la versione con omissis di oggettoHtml

//				attoDaPubblicare.setManualeImport("I");
				
				logger.debug("---- INVOCO I WS DI PUBBLICAZIONE ALBO DI AREA VASTA BARI ----");
				
				logger.debug("--REQUEST-- servizio pubblicaatto");
				//Trasformo l'oggetto in xml per poterlo scrivere nei log e averne traccia				
				logger.debug(convertBeanToXml(attoDaPubblicare));				

				// Invoco il WS Albo Pretorio di Area Vasta per la pubblicazione dell'atto senza file
				AlboPretorioAVBImpl service = new AlboPretorioAVBImpl();
				Result<AlboAVBPubblicaAttoResponseReturn> output = service.pubblicaatto(getLocale(), attoDaPubblicare);

				//da Ok solo se viene pubblicato l'atto, e cioè se mi ritorna l'idRecord di pubblicazione (vedere nel progetto AlbAVBClient come inizializzo l ok)
				if (!output.isOk()) {
					String errore;
					if (output.isTimeout()) {
						errore = "La chiamata al WS di pubblicazione dell'atto è andata in timeout";
						logger.error(errore);
					} else if (output.isRispostaNonRicevuta()) {
						errore = "La chiamata al WS di pubblicazione dell'atto non ha resituito nessuna risposta: " +  output.getMessage();
						logger.error(errore);
					} else {
						if(output.getPayload()!=null && StringUtils.isNotBlank(output.getPayload().getAlboAvbAttoError().getDescription())) {
							 errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output.getPayload().getAlboAvbAttoError().getCode();
							 logger.error("--RESPONSE-- servizio pubblicaAtto: " + errore);
						}else {
							errore = "La chiamata al WS di pubblicazione dell'atto non ha restiuito nessun esito"+  output.getMessage(); // se il Payload() == null o non c'è messaggio
							logger.error(errore);
						}
					}
					
					//Invio mail con relativo errore di fallita pubblicazione
					sendMailErrorePubblicazione("pubblicaAtto", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
				
				} else {
					// Se la pubblicazione degli estremi dell'atto è andata a buon fine aggiungo il  file primario chiamando il WS salva allegato
					if (output.getPayload() != null && StringUtils.isNotBlank(String.valueOf(output.getPayload().getAlboAvbAtto().getIdAtto()))) {
						
							logger.debug("--RESPONSE-- servizio pubblicaAtto: " + convertBeanToXml(output.getPayload()));
						
							//id di pubblicazione restituito dal servizio dell'albo
							idPubblicazione = String.valueOf(output.getPayload().getAlboAvbAtto().getIdAtto());
							
							aggiornaIdAlbo(pNuovaPropostaAtto2CompletaBean.getIdUd(), idPubblicazione);

							AlboAVBSalvaAllegatoIn filePrimarioDaPubblicare = new AlboAVBSalvaAllegatoIn();
							filePrimarioDaPubblicare.setIdAtto(Long.valueOf(idPubblicazione));
							filePrimarioDaPubblicare.setMainDocument(true);
							filePrimarioDaPubblicare.setMimeType(Files.probeContentType(fileDaPubblicare.toPath()));
							filePrimarioDaPubblicare.setFileContent(FileUtils.readFileToByteArray(fileDaPubblicare));
							
							if (primarioOmissis) {
								filePrimarioDaPubblicare.setFileName(bean.getNomeFilePrimarioOmissis());
								// Come discriminare omissis?
//								filePrimarioDaPubblicare.setTipo("E"); // I= Integrale E = Estratto

							} else {
								filePrimarioDaPubblicare.setFileName(bean.getNomeFilePrimarioOmissis());
								// Come discriminare omissis?
//								filePrimarioDaPubblicare.setTipo("I"); // I= Integrale E = Estratto
							}
							
							logger.debug("--REQUEST-- servizio salvaAllegato");
							logger.debug(convertBeanToXml(filePrimarioDaPubblicare));

							Result<AlboAVBSalvaAllegatoResponseReturn> output2 = service.salvaallegato(getLocale(), filePrimarioDaPubblicare);

							//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboAVBClient come inizializzo l' OK)
							if (!output2.isOk()) {
								String errore;
								if (output2.isTimeout()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
									logger.error(errore);
								} else if (output2.isRispostaNonRicevuta()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " +  output2.getMessage();
									logger.error(errore);
								} else {
									if(output2.getPayload()!=null && StringUtils.isNotBlank(output2.getPayload().getAlboAvbAttoError().getDescription())) {
										 errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output2.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output2.getPayload().getAlboAvbAttoError().getDescription();
										 logger.error("--RESPONSE-- servizio salvaAllegato: " + errore);
									}else {
										errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" +  output2.getMessage(); // se il Payload() == null o non c'è messaggio
										logger.error(errore);
									}
								}
								
								//Invio mail con relativo errore di fallita pubblicazione
								sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
							} else {
								
								logger.debug("--RESPONSE-- servizio salvaAllegato: Aggiunto file primario: " + filePrimarioDaPubblicare.getFileName() + " all'atto pubblicato con id: " + idPubblicazione);
								
								List<AllegatoProtocolloBean> listaAllegatiDaPubblicare = new ArrayList<>();

								// Nel caso di determina con spesa, devo recuperare le informazioni dell'allegato del visto contabile  generato in quel task
								// se non è stato generato in quel task, ma in uno precedente, me lo recupero dalla lista allegati 
								if(bean.getAllegatoVistoContabile() == null) {
									bean.setAllegatoVistoContabile(getAllegatoVistoContabile(pNuovaPropostaAtto2CompletaBean));
								}
								if (bean.getAllegatoVistoContabile() != null) {
									listaAllegatiDaPubblicare.add(bean.getAllegatoVistoContabile());
								}

								// Controllo se ci sono anche allegati dell'atto da pubblicare separatamente
								if (bean.getListaAllegati() != null) {
									for (AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaAllegati()) {
										if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null
												&& lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
											if(lAllegatoProtocolloBean.getFlgNoPubblAllegato() == null
													|| !lAllegatoProtocolloBean.getFlgNoPubblAllegato()) { // // se non è escluso dalla pubblicazione
												if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null
														&& lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se è da pubblicare separatamente
													listaAllegatiDaPubblicare.add(lAllegatoProtocolloBean);
												}
											}
										}
									}
								}

								if(listaAllegatiDaPubblicare!=null && listaAllegatiDaPubblicare.size()>=1) {
									logger.debug("Pubblico gli allegati");
								}
								
								for (AllegatoProtocolloBean allegato : listaAllegatiDaPubblicare) {
									File fileAllegatoDaPubblicare = null;
									boolean allegatoOmissis = false;

									if (allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili() && StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
										if(!allegato.getInfoFileOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {		
											ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(allegato.getUriFileOmissis(),
													allegato.getNomeFileOmissis(), allegato.getInfoFileOmissis()));					
											allegato.setUriFileOmissis(outputBean.getFiles().get(0).getUri());
											allegato.setNomeFileOmissis(outputBean.getFiles().get(0).getNomeFile());
											allegato.setInfoFileOmissis(outputBean.getFiles().get(0).getInfoFile());
										}
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
										allegatoOmissis = true;
									}
									else if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
									}else {
										String errore = "La chiamata al WS di aggiunta allegato con nome: " + allegato.getNomeFileAllegato() != null ? allegato.getNomeFileAllegato() : allegato.getNomeFileOmissis() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: uri allegato non presente";
										logger.error(errore);
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
									}

									AlboAVBSalvaAllegatoIn allegatoDaPubblicare = new AlboAVBSalvaAllegatoIn();
									allegatoDaPubblicare.setIdAtto(Long.valueOf(idPubblicazione));
									allegatoDaPubblicare.setMainDocument(false);
									allegatoDaPubblicare.setFileContent(FileUtils.readFileToByteArray(fileAllegatoDaPubblicare));
									
									if (allegatoOmissis) {
										allegatoDaPubblicare.setFileName(allegato.getNomeFileOmissis());
										allegatoDaPubblicare.setMimeType(Files.probeContentType(fileAllegatoDaPubblicare.toPath()));
										// Come discriminare gli omissis?
//										allegatoDaPubblicare.setTipo("E"); // I= Integrale E = Estretto

									} else {
										allegatoDaPubblicare.setFileName(allegato.getNomeFileAllegato());
										allegatoDaPubblicare.setMimeType(Files.probeContentType(fileAllegatoDaPubblicare.toPath()));
										// Come discriminare gli omissis?
//										allegatoDaPubblicare.setTipo("I"); // I= Integrale E = Estretto
									}

									logger.debug("--REQUEST-- servizio salvaAllegato");
									logger.debug(convertBeanToXml(allegatoDaPubblicare));
									
									Result<AlboAVBSalvaAllegatoResponseReturn> output3 = service.salvaallegato(getLocale(), allegatoDaPubblicare);

									//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboAVBClient come inizializzo l' OK)
									if (!output3.isOk()) {
										String errore;
										if (output3.isTimeout()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + allegatoDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
											logger.error(errore);
										} else if (output3.isRispostaNonRicevuta()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + allegatoDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " + output3.getMessage();
											logger.error(errore);
										} else {
											if(output3.getPayload()!=null && StringUtils.isNotBlank(output3.getPayload().getAlboAvbAttoError().getDescription())) {
												 errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output3.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output3.getPayload().getAlboAvbAttoError().getCode();
												 logger.error("--RESPONSE-- servizio salvaAllegato: " + errore);
											}else {
												errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" + output3.getMessage(); // se il Payload() == null o non c'è messaggio
												logger.error(errore);
											}
										}
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
									
									}
									logger.debug("--RESPONSE-- servizio salvaAllegato: Aggiunto file primario: " + allegatoDaPubblicare.getFileName() + " all'atto pubblicato con id: " + idPubblicazione);
								}
								
								logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
								
								addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);
								
							}
					}else {
						String errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output.getPayload().getAlboAvbAttoError().getCode();
						logger.error("--RESPONSE-- servizio salvaAtto: " + errore);
						
						//Invio mail con relativo errore di fallita pubblicazione
						sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
					}
				}
			} else {
				throw new StoreException("Nessun file da pubblicare");
			}
		} catch (Throwable e) {
			logger.error("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
			throw new StoreException("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage());
		}

		return bean;
	}
	
	private ConversionePdfBean getConversionePdfBean(String uriFile, String nomeFile, MimeTypeFirmaBean infoFile) {
		List<FileDaConvertireBean> files = new ArrayList<FileDaConvertireBean>();
		FileDaConvertireBean lFileDaConvertireBean = new FileDaConvertireBean();
		lFileDaConvertireBean.setUri(uriFile);
		lFileDaConvertireBean.setNomeFile(nomeFile);
		lFileDaConvertireBean.setInfoFile(infoFile);
		files.add(lFileDaConvertireBean);
		ConversionePdfBean inputBean = new ConversionePdfBean();
		inputBean.setFiles(files);
		
		return inputBean;
	}
	
	private String convertBeanToXml(Object object) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(object, sw);
			// Verify XML Content
			String xmlContent = sw.toString();

			return xmlContent;
		} catch (Exception e) {
			logger.error("Errore nella conversione dell' Bean di input in Xml: " + e.getMessage(),e);
			throw new Exception("Errore nella conversione dell' Bean di input");
		}
	}


	private void sendMailErrorePubblicazione(String ws, String errore, String siglaAtto, String numeroAtto, String annoAtto) throws Exception {
		String mittenteMailNotifica = ParametriDBUtil.getParametroDB(getSession(), "EMAIL_MITT_NOTIF_ASS_INVIO_CC");
		String destinatariMailNotifica = ParametriDBUtil.getParametroDB(getSession(), "DEST_NOTIFICHE_ERRORE_PUBBL");
		
		String dettagliAtto = "<" + siglaAtto + "> <" + numeroAtto + ">/<" + annoAtto + ">";
		
		String oggetto = "AURIGA - Notifica errore pubblicazione atto " + dettagliAtto;
		
		String bodyMail = "";
		
		Date today = new Date();
		String data = setDateForAlbo(today);
		String ore = String.valueOf(today.getHours()) + ":" + String.valueOf(today.getMinutes());
		
		if("nuovoAtto".equalsIgnoreCase(ws)) {
			bodyMail="In data " + data + " ore " + ore + " l'invio in pubblicazione dell'atto " + dettagliAtto + " è andato in errore con il seguente messaggio di errore:" + errore;
		}else if("nuovoAllegato".equalsIgnoreCase(ws)) {
			bodyMail="In data " + data + " ore " + ore + " l'invio in pubblicazione dell'atto " + dettagliAtto + " è andato parzialmente in errore con il seguente messaggio di errore:" + errore;
		}		
		
		SenderBean senderBean = new SenderBean();
		senderBean.setFlgInvioSeparato(false);
		senderBean.setIsPec(false);
		senderBean.setAccount(mittenteMailNotifica);
		senderBean.setAddressFrom(mittenteMailNotifica);

		// Destinatari principali
		if(destinatariMailNotifica != null) {
			String[] destinatariTo = IndirizziEmailSplitter.split(destinatariMailNotifica);
			senderBean.setAddressTo(Arrays.asList(destinatariTo));
		} else {
			logger.error("Errore durante invio mail di notifica di mancata pubblicazione: destinatari non presenti");
			throw new Exception("Errore in pubblicazione: " + errore +" non è stato possbibile inviare la mail di notifica perchè destinatari non presenti");
		}		

		// Oggetto
		senderBean.setSubject(oggetto);

		// CORPO
		senderBean.setBody(bodyMail);
		senderBean.setIsHtml(false);

		// Conferma di lettura
		senderBean.setReturnReceipt(false);

		try {
			AurigaMailService.getMailSenderService().send(new Locale("it"), senderBean);
		} catch (Exception e) {
			logger.error("Errore durante invio mail di notifica di mancata pubblicazione: " + e.getMessage(), e);
			throw new Exception(errore + ", non è stato possibile inviare una mail di notifica: " + e.getMessage());
		}
		
		throw new Exception(errore);
	}

	private String setDateForAlbo(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = sdf.format(date);
		
		return dateString;
	}

	// SENZA SPESA: la pubblicazione nelle determine senza spesa avviene dopo la firma adozione se non è una proposta di concerto, altrimenti dopo la firma dei dirigenti di concerto, e devo pubblicare solo il file unione (dispositivo e allegati parte integrante) 
	// CON SPESA: la pubblicazione nelle determine con spesa avviene dopo la firma del visto contabile e devo pubblicare il file unione (dispositivo e allegati parte integrante) e come allegato il file del visto generato in quel task 
	public NuovaPropostaAtto2CompletaBean pubblicaAlbo(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		// Mi recupero i dati di registrazione dopo che ho salvato
		NuovaPropostaAtto2CompletaBean bean = get(pNuovaPropostaAtto2CompletaBean);
		
		try {
			File fileDaPubblicare = null;
			MimeTypeFirmaBean infoFileDaPubblicare = null;
			boolean flgDatiSensibili = bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili();
			if (!flgDatiSensibili && StringUtils.isNotBlank(bean.getUriFilePrimario())) {
				logger.debug("Pubblico il file primario (vers. integrale): " + bean.getUriFilePrimario());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());				
				infoFileDaPubblicare = bean.getInfoFilePrimario();
			} else if (StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
				logger.debug("Pubblico il file primario (vers. con omissis): " + bean.getUriFilePrimarioOmissis());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimarioOmissis());
				infoFileDaPubblicare = bean.getInfoFilePrimarioOmissis();
			}
			String ext = "pdf";
			if (infoFileDaPubblicare != null && infoFileDaPubblicare.isFirmato() && infoFileDaPubblicare.getTipoFirma() != null && infoFileDaPubblicare.getTipoFirma().startsWith("CAdES")) {
				ext = "pdf.p7m";
			}	
			if (fileDaPubblicare != null) {
				// Setto i parametri della richiesta
				DocumentoType documentoType = new DocumentoType();

				if (StringUtils.isNotBlank(bean.getSiglaRegistrazione()) && StringUtils.isNotBlank(bean.getNumeroRegistrazione()) && bean.getDataRegistrazione() != null) {
					documentoType.setProtocollo(bean.getSiglaRegistrazione());
					documentoType.setNumeroDocumento(bean.getNumeroRegistrazione());
					documentoType.setAnnoDocumento(bean.getDataRegistrazione() != null ? 
						new Integer(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataRegistrazione()).substring(6)) : null);
					documentoType.setDataDocumento(bean.getDataRegistrazione());
				} else {
					throw new StoreException("Mancano gli estremi di registrazione del documento");
				}

				documentoType.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di pubblicazione all'Albo Pretorio, con la versione con omissis di oggettoHtml
				documentoType.setSettore(bean.getDesDirezioneProponente());

				if (bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
					GregorianCalendar calPubblicazione = new GregorianCalendar();
					calPubblicazione.setTime(bean.getDataInizioPubblicazione());
					documentoType.setDataInizioEsposizione(calPubblicazione.getTime());
					calPubblicazione.add(Calendar.DAY_OF_YEAR, Integer.parseInt(bean.getGiorniPubblicazione()));
					documentoType.setDataFineEsposizione(calPubblicazione.getTime());
				} else {
					throw new StoreException("Mancano i dati relativi al periodo di pubblicazione");
				}

				String nomeFilePrimario= documentoType.getProtocollo() + "_" + documentoType.getAnnoDocumento() + "_" + documentoType.getNumeroDocumento() + "." + ext;
				
				documentoType.setNomeFile(nomeFilePrimario);
				documentoType.setUsername(loginBean.getUserid());
				String tipoDocumento = ParametriDBUtil.getParametroDB(getSession(), "TIPO_DOCUMENTO_ALBO_PRETORIO");
				documentoType.setTipoDocumento(tipoDocumento != null && !"".equals(tipoDocumento) ? Integer.parseInt(tipoDocumento) : 9050);
				String enteProvenienza = ParametriDBUtil.getParametroDB(getSession(), "ENTE_PROVENIENZA_ALBO_PRETORIO");
				documentoType.setEnteProvenienza(enteProvenienza != null && !"".equals(enteProvenienza) ? enteProvenienza : "Comune Milano");
				documentoType.setNote(null);
				
				logger.debug("DocumentType.Protocollo: " + documentoType.getProtocollo());
				logger.debug("DocumentType.NumeroDocumento: " + documentoType.getNumeroDocumento());
				logger.debug("DocumentType.AnnoDocumento: " + documentoType.getAnnoDocumento());
				logger.debug("DocumentType.DataDocumento: " + documentoType.getDataDocumento());
				logger.debug("DocumentType.Oggetto: " + documentoType.getOggetto());
				logger.debug("DocumentType.Settore: " + documentoType.getSettore());
				logger.debug("DocumentType.DataInizioEsposizione: " + documentoType.getDataInizioEsposizione());
				logger.debug("DocumentType.DataFineEsposizione: " + documentoType.getDataFineEsposizione());
				logger.debug("DocumentType.NomeFile: " + documentoType.getNomeFile());
				logger.debug("DocumentType.Username: " + documentoType.getUsername());
				logger.debug("DocumentType.TipoDocumento: " + documentoType.getTipoDocumento());
				logger.debug("DocumentType.EnteProvenienza: " + documentoType.getEnteProvenienza());
				logger.debug("DocumentType.Note: " + documentoType.getNote());

				List<AlboPretorioAttachBean> listaFile = new ArrayList<AlboPretorioAttachBean>();
				List<AlboPretorioAttachBean> listaFileAllegati = new ArrayList<AlboPretorioAttachBean>();

				// Recupero le informazioni del file primario
				AlboPretorioAttachBean filePrimarioAttachBean = new AlboPretorioAttachBean();
				filePrimarioAttachBean.setTipoFile("P");				
				filePrimarioAttachBean.setFileName(nomeFilePrimario);
				filePrimarioAttachBean.setUri(fileDaPubblicare.getPath());
				listaFile.add(filePrimarioAttachBean);
									
				// Nel caso di determina con spesa, devo recuperare le informazioni dell'allegato del visto contabile  generato in quel task
				// se non è stato generato in quel task, ma in uno precedente, me lo recupero dalla lista allegati 
				if(bean.getAllegatoVistoContabile() == null) {
					bean.setAllegatoVistoContabile(getAllegatoVistoContabile(pNuovaPropostaAtto2CompletaBean));
				}
				if (bean.getAllegatoVistoContabile() != null) {
					logger.debug("Pubblico il file allegato del visto contabile: " + bean.getAllegatoVistoContabile().getUriFileAllegato());
					File fileAllegatoVistoContabile = StorageImplementation.getStorage().extractFile(bean.getAllegatoVistoContabile().getUriFileAllegato());																																
					AlboPretorioAttachBean fileAllegatoVistoContabileAttachBean = new AlboPretorioAttachBean();
					fileAllegatoVistoContabileAttachBean.setTipoFile("A");
					fileAllegatoVistoContabileAttachBean.setFileName(bean.getAllegatoVistoContabile().getNomeFileAllegato());
					fileAllegatoVistoContabileAttachBean.setUri(fileAllegatoVistoContabile.getPath());							
					listaFileAllegati.add(fileAllegatoVistoContabileAttachBean);
					listaFile.add(fileAllegatoVistoContabileAttachBean);					
				}
				
				documentoType.setAllegati(listaFileAllegati);

				pubblicaSuAlboPretorio(listaFile, documentoType, pNuovaPropostaAtto2CompletaBean);

			} else {
				throw new StoreException("Nessun file da pubblicare");
			}

		} catch (Throwable e) {
			logger.error("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage());
		}

		return bean;
	}
	
	public void pubblicaSuAlboPretorio(List<AlboPretorioAttachBean> listaFiles, DocumentoType documentoType,
						NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {

		logger.debug("Inizio della procedura di collegamento all'Albo Pretorio");

		// Setto il proxy se necessario
		ProxyBean impostazioniProxy = new ProxyBean();
		boolean settoIlProxy = new SetSystemProxy().impostaProxy(impostazioniProxy);

		if (settoIlProxy) {
			logger.debug("Proxy settato");
		} else {
			logger.debug("Proxy non settato");
		}

		// Carico il file in FTP
		if (listaFiles != null && !listaFiles.isEmpty()) {
			for (int i = 0; i < listaFiles.size(); i++) {
				
				AlboPretorioAttachBean lAlboPretorioAttachBean = listaFiles.get(i);
				
				FTPUploadFileBean impostazioniUploadBean = new FTPUploadFileBean();
				impostazioniUploadBean.setFileRequest(lAlboPretorioAttachBean.getUri());
				impostazioniUploadBean.setNomeFileRemoto(lAlboPretorioAttachBean.getFileName());

				logger.debug("File request: " + lAlboPretorioAttachBean.getUri());
				logger.debug("Nome file: " + lAlboPretorioAttachBean.getFileName());
				
				boolean uploadFile = false;
				try {
					uploadFile = new FTPUploadFile().uploadFTP(impostazioniUploadBean);
				} catch (Exception e) {
					logger.error("Errore durante l'upload del file su FTP: " + e.getMessage(), e);
				}

				if (!uploadFile) {
					throw new Exception(
							"Errore durante l'upload del file su FTP. Impossibile procedere all'invocazione del servizio per la pubblicazione all'Albo Pretorio");
				}
			}
		}

		// Invoco il WS Albo Pretorio con il file primario e gli allegati
		try {
			SOAPEnvelope responseDocument = new CaricaDocumento().caricaDocumento(null, documentoType);
			if (responseDocument != null) {
				String respString = responseDocument.getAsString();
				logger.debug("CaricaDocumento response: " + respString);
				// Elaboro la response del WS
				String idAlbo = new ElaboraResponseWS().elaboraResponse(responseDocument);
				if(idAlbo != null && !"".equals(idAlbo)) {
					aggiornaIdAlbo(pNuovaPropostaAtto2CompletaBean.getIdUd(), idAlbo);
				} else {
					logger.error("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio: nessun idAlbo restituito");
					throw new Exception("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio");
				}
			}
		} catch (Throwable e) {
			logger.error("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			throw new Exception("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio");
		}

		addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);

		logger.debug("Fine della procedura di collegamento all'Albo Pretorio");
	}
	
	private void aggiornaIdAlbo(String idUd, String idAlbo) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setIduddocin(new BigDecimal(idUd));
		input.setFlgtipotargetin("U");

		SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
		sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("ID_PUBBL_ALBO_Ud", idAlbo));

		CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
		lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));

		DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);

		if (lUpddocudOutput.isInError()) {
			throw new StoreException(lUpddocudOutput);
		}		
	}
	
	public UnioneFileAttoBean unioneFile(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {		
		// Prima di tutto devo numerare
		UnioneFileAttoBean lUnioneFileAttoBean = new UnioneFileAttoBean();
		try {			
			if(getExtraparams().get("isVersIntegrale") != null && "true".equalsIgnoreCase(getExtraparams().get("isVersIntegrale"))) {
				FileDaFirmareBean lFileUnioneBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, true);
				lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneBean.getNomeFile());
				lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneBean.getUri());
				lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneBean.getInfoFile());				
				lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneBean.getUriFileOdtGenerato());
			} else if(getExtraparams().get("isVersXPubbl") != null && "true".equalsIgnoreCase(getExtraparams().get("isVersXPubbl"))) {
				FileDaFirmareBean lFileUnioneVersConOmissisBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, false);
				lUnioneFileAttoBean.setNomeFile(lFileUnioneVersConOmissisBean.getNomeFile());
				lUnioneFileAttoBean.setUri(lFileUnioneVersConOmissisBean.getUri());
				lUnioneFileAttoBean.setInfoFile(lFileUnioneVersConOmissisBean.getInfoFile());
				lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneVersConOmissisBean.getUriFileOdtGenerato());
			} else {
				if(pNuovaPropostaAtto2CompletaBean.getFlgDatiSensibili() != null && pNuovaPropostaAtto2CompletaBean.getFlgDatiSensibili()) {
					FileDaFirmareBean lFileUnioneVersIntegraleBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, true);
					lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneVersIntegraleBean.getNomeFile());
					lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneVersIntegraleBean.getUri());
					lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneVersIntegraleBean.getInfoFile());
					FileDaFirmareBean lFileUnioneVersConOmissisBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, false);
					lUnioneFileAttoBean.setNomeFile(lFileUnioneVersConOmissisBean.getNomeFile());
					lUnioneFileAttoBean.setUri(lFileUnioneVersConOmissisBean.getUri());
					lUnioneFileAttoBean.setInfoFile(lFileUnioneVersConOmissisBean.getInfoFile());
					lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneVersConOmissisBean.getUriFileOdtGenerato());
				} else {
					FileDaFirmareBean lFileUnioneBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, true);
					lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneBean.getNomeFile());
					lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneBean.getUri());
					lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneBean.getInfoFile());
					lUnioneFileAttoBean.setNomeFile(null);
					lUnioneFileAttoBean.setUri(null);
					lUnioneFileAttoBean.setInfoFile(new MimeTypeFirmaBean());
					lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneBean.getUriFileOdtGenerato());
				}
			}					
		}  catch(StoreException e) {
			logger.error("Si è verificato un errore durante l'unione dei file. " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante l'unione dei file. " + e.getMessage());
		} catch (Exception e1) {
			logger.error("Si è verificato un errore durante l'unione dei file. " + e1.getMessage(), e1);
			throw new StoreException("Si è verificato un errore durante l'unione dei file.");
		}		
		return lUnioneFileAttoBean;
	}
	
	public TaskFileDaFirmareBean getFileDaFirmare(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Aggiungo il primario
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario())){
			aggiungiPrimario(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
		}
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis())){
			aggiungiPrimarioOmissis(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
		}
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())){
							aggiungiAllegato(listaFileDaFirmare, lAllegatoProtocolloBean);
						}
					} 
					if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())) {
							aggiungiAllegatoOmissis(listaFileDaFirmare, lAllegatoProtocolloBean);
						}
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public TaskFileDaFirmareBean getFileVersPubblAggiornataDaFirmare(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis())) {
//			if(!pNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis().isFirmato()) {
				aggiungiPrimarioOmissis(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
//			}
		} else if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario())) {
//			if(!pNuovaPropostaAtto2CompletaBean.getInfoFilePrimario().isFirmato()) {
				aggiungiPrimario(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
//			}
		}
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				boolean flgParteDispositivo = lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo();
				boolean flgNoPubblAllegato = lAllegatoProtocolloBean.getFlgNoPubblAllegato() != null && lAllegatoProtocolloBean.getFlgNoPubblAllegato();
				boolean flgPubblicaSeparato = lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null && lAllegatoProtocolloBean.getFlgPubblicaSeparato();
				boolean flgPubblicaAllegatiSeparati = pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati();
				boolean flgDatiSensibili = lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili();
				if (flgParteDispositivo && !flgNoPubblAllegato && (flgPubblicaAllegatiSeparati || flgPubblicaSeparato)) {
					String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis()) && flgDatiSensibili) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())) {
//							if (!lAllegatoProtocolloBean.getInfoFileOmissis().isFirmato()) {
								aggiungiAllegatoOmissis(listaFileDaFirmare, lAllegatoProtocolloBean);
//							}
						}
					} else if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())) {
	//						if (!lAllegatoProtocolloBean.getInfoFileAllegato().isFirmato()) {
								aggiungiAllegato(listaFileDaFirmare, lAllegatoProtocolloBean);
	//						}
						}
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public TaskFileDaFirmareBean getFileAllegatiDaFirmare(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileAllegatiDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())){
							aggiungiAllegato(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					} 
					if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())){
							aggiungiAllegatoOmissis(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileAllegatiDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public TaskFileDaFirmareBean getFileAllegatiDaFirmareWithFileUnione(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		boolean skipOmissisInFirmaAdozioneAtto = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileAllegatiDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())){
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())){
							aggiungiAllegato(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					} 
					if (!skipOmissisInFirmaAdozioneAtto && lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())){
							aggiungiAllegatoOmissis(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileAllegatiDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public FileDaFirmareBean generaFileUnione(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean, boolean flgMostraDatiSensibili) throws Exception{
		
		logger.debug("UNIONE DEI FILE");

		boolean flgAllegatiParteIntUnitiVersIntegrale = false;
		boolean flgAllegatiParteIntUnitiVersXPubbl = false;
		List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersIntegrale = new ArrayList<AllegatoParteIntSeparatoBean>();
		List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersXPubbl = new ArrayList<AllegatoParteIntSeparatoBean>();
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			boolean flgPubblicaAllegatiSeparati = pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati() != null && pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati(); // se è true tutti gli allegati sono da pubblicare separatamente		
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				boolean flgParteDispositivo = lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo();
				boolean flgPubblicaSeparato = lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null && lAllegatoProtocolloBean.getFlgPubblicaSeparato();
				boolean flgNoPubblAllegato = lAllegatoProtocolloBean.getFlgNoPubblAllegato() != null && lAllegatoProtocolloBean.getFlgNoPubblAllegato();				
				if (flgParteDispositivo) { // se è parte integrante						
					if(flgMostraDatiSensibili) {
						// se è la vers. integrale
						if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
							AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
							lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
							lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
							lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
							lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
							lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
							lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
							listaAllegatiParteIntSeparatiVersIntegrale.add(lAllegatoParteIntSeparatoBean);						
						} else {
							flgAllegatiParteIntUnitiVersIntegrale = true;
						}
					} else if(!flgNoPubblAllegato) {
						// se è la vers. per la pubbl. e l'allegato non è escluso dalla pubblicazione
						if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
							if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) {
								AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBeanOmissis = new AllegatoParteIntSeparatoBean();
								lAllegatoParteIntSeparatoBeanOmissis.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
								lAllegatoParteIntSeparatoBeanOmissis.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
								lAllegatoParteIntSeparatoBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
								lAllegatoParteIntSeparatoBeanOmissis.setImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getImpronta() : null);
								lAllegatoParteIntSeparatoBeanOmissis.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getAlgoritmo() : null);
								lAllegatoParteIntSeparatoBeanOmissis.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getEncoding() : null);
								listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBeanOmissis);	
							} else {
								AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
								lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
								lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
								lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
								lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
								lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
								lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
								listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBean);	
							}
						} else {
							flgAllegatiParteIntUnitiVersXPubbl = true;
						}
					}
				}
			}
		}
		
		if(flgMostraDatiSensibili) {
			// se è la vers. integrale
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUniti(flgAllegatiParteIntUnitiVersIntegrale);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparati(listaAllegatiParteIntSeparatiVersIntegrale);
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUnitiXPubbl(null);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparatiXPubbl(null);
		} else {
			// se è la vers. per la pubbl.
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUniti(null);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparati(null);
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUnitiXPubbl(flgAllegatiParteIntUnitiVersXPubbl);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparatiXPubbl(listaAllegatiParteIntSeparatiVersXPubbl);
		}

		String templateValuesPratica = null;
		List<FileDaFirmareBean> lListFileDaUnireBean = flgMostraDatiSensibili ? getListaFileDaUnire(pNuovaPropostaAtto2CompletaBean) : getListaFileDaUnireOmissis(pNuovaPropostaAtto2CompletaBean);
		String uriFileOdtDaSalvare = null;
		if (lListFileDaUnireBean != null && !lListFileDaUnireBean.isEmpty()) {		
//			List<InputStream> lListInputStream = new ArrayList<InputStream>();
			List<FileDaFirmareBean> lListFile = new ArrayList<FileDaFirmareBean>();
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModCopertinaFinale()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModCopertinaFinale())) {	
				String templateValuesCopertinaFinale = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModCopertinaFinale());				
//				FileDaFirmareBean lFileDaFirmareBeanCopertinaFinale = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertinaFinale(), templateValuesCopertinaFinale, true, getSession());
				Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesCopertinaFinale, true);
				FileDaFirmareBean lFileDaFirmareBeanCopertinaFinale = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertinaFinale(), mappaValoriCopertinaFinale, true, flgMostraDatiSensibili, getSession()); 
//				lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanCopertinaFinale.getUri()));	
				lFileDaFirmareBeanCopertinaFinale.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanCopertinaFinale.getUri()));
				lListFile.add(lFileDaFirmareBeanCopertinaFinale);
			} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModCopertinaFinale())) {				
				if(templateValuesPratica == null) {
					templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
				}
				File fileCopertinaFinale = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModCopertinaFinale(), pNuovaPropostaAtto2CompletaBean.getTipoModCopertinaFinale(), templateValuesPratica, getSession());
//				lListInputStream.add(new FileInputStream(fileCopertinaFinale));		
				FileDaFirmareBean lfileCopertinaFinale = new FileDaFirmareBean();
				lfileCopertinaFinale.setFile(fileCopertinaFinale);
				lListFile.add(lfileCopertinaFinale);
			} 
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModCopertina()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModCopertina())) {	
				String templateValuesCopertina = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModCopertina());				
//				FileDaFirmareBean lFileDaFirmareBeanCopertina = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertina(), templateValuesCopertina, true, getSession());
				Map<String, Object> mappaValoriCopertina = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesCopertina, true);
				FileDaFirmareBean lFileDaFirmareBeanCopertina = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertina(), mappaValoriCopertina, true, flgMostraDatiSensibili, getSession()); 
//				lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanCopertina.getUri()));		
				lFileDaFirmareBeanCopertina.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanCopertina.getUri()));
				lListFile.add(lFileDaFirmareBeanCopertina);

			} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModCopertina())) {
				if(templateValuesPratica == null) {
					templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
				}
				File fileCopertina = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModCopertina(), pNuovaPropostaAtto2CompletaBean.getTipoModCopertina(), templateValuesPratica, getSession());
//				lListInputStream.add(new FileInputStream(fileCopertina));		
				FileDaFirmareBean lfileCopertina = new FileDaFirmareBean();
				lfileCopertina.setFile(fileCopertina);
				lListFile.add(lfileCopertina);
			}
			for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean) {
				if (lFileDaUnireBean.getIsDispositivoNuovaPropostaAtto2Completa() != null && lFileDaUnireBean.getIsDispositivoNuovaPropostaAtto2Completa()) {
					uriFileOdtDaSalvare = lFileDaUnireBean.getUriFileOdtGenerato();
				}
				logger.debug("File " + lFileDaUnireBean.getNomeFile() + ": " + lFileDaUnireBean.getUri());
				if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
					logger.debug("Il file è firmato in CAdES");
					FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaUnireBean);
					if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
						logger.debug("Il file sbustato è un pdf, quindi lo aggiungo");
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoBean.getUri()));
						lFileSbustatoBean.setFile(StorageImplementation.getStorage().extractFile(lFileSbustatoBean.getUri()));
						lListFile.add(lFileSbustatoBean);
					} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
						logger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
						logger.debug("mimetype: " + lFileDaUnireBean.getInfoFile().getMimetype());							
						try {
							FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
//							lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoConvertitoBean.getUri()));	
							lFileSbustatoConvertitoBean.setFile(StorageImplementation.getStorage().extractFile(lFileSbustatoConvertitoBean.getUri()));
							lListFile.add(lFileSbustatoConvertitoBean);
						} catch (Exception e) {
							String errorMessage = "Errore durante la conversione del file sbustato";
							if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
								errorMessage = "Errore durante la conversione del file sbustato " + lFileSbustatoBean.getNomeFile();
							}
							logger.error(errorMessage + " : " + e.getMessage(), e);
							throw new StoreException(errorMessage);
						}
					} else {
						String errorMessage = "Il file sbustato non è un pdf e non è convertibile.";
						if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
							errorMessage = "Il file sbustato " + lFileSbustatoBean.getNomeFile() + " non è un pdf e non è convertibile.";
						}
						logger.error(errorMessage);
						throw new StoreException(errorMessage);
					}
				} else if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
					if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().equalsIgnoreCase("PADES")) {
						logger.debug("Il file è firmato in PAdES quindi devo prendere la versione precedente la firma");
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriVerPreFirma()));
						lFileDaUnireBean.setFile(StorageImplementation.getStorage().extractFile(lFileDaUnireBean.getUriVerPreFirma()));
						lListFile.add(lFileDaUnireBean);
					} else {
						logger.debug("Il file è un pdf, quindi lo aggiungo");
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUri()));
						lFileDaUnireBean.setFile(StorageImplementation.getStorage().extractFile(lFileDaUnireBean.getUri()));
						lListFile.add(lFileDaUnireBean);
					}
				} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
					logger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
					try {
						FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaUnireBean);
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileConvertitoBean.getUri()));
						lFileConvertitoBean.setFile(StorageImplementation.getStorage().extractFile(lFileConvertitoBean.getUri()));
						lListFile.add(lFileConvertitoBean);
					} catch (Exception e) {
						String errorMessage = "Errore durante la conversione del file";
						if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
							errorMessage = "Errore durante la conversione del file " + lFileDaUnireBean.getNomeFile();
						}
						logger.error(errorMessage + " : " + e.getMessage(), e);
						throw new StoreException(errorMessage);
					}
				} else {
					String errorMessage = "Il file non è un pdf e non è convertibile.";
					if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
						errorMessage = "Il file " + lFileDaUnireBean.getNomeFile() + " non è un pdf e non è convertibile.";
					}
					logger.error(errorMessage);
					throw new StoreException(errorMessage);
				}					
			}

			if(flgMostraDatiSensibili) {
				// se è la vers. integrale e ho file allegati parte integrante sia uniti che separati
				if(listaAllegatiParteIntSeparatiVersIntegrale.size() > 0 && flgAllegatiParteIntUnitiVersIntegrale) {
					if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparati()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparati())) {	
						String templateValuesAllegatiParteIntSeparati = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparati());				
//						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparati = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparati(), templateValuesAllegatiParteIntSeparati, true, getSession());
						Map<String, Object> mappaValoriAllegatiParteIntSeparati = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesAllegatiParteIntSeparati, true);
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparati = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparati(), mappaValoriAllegatiParteIntSeparati, true, flgMostraDatiSensibili, getSession()); 
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanAllegatiParteIntSeparati.getUri()));		
						lFileDaFirmareBeanAllegatiParteIntSeparati.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanAllegatiParteIntSeparati.getUri()));
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparati);
		
					} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparati())) {
						if(templateValuesPratica == null) {
							templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
						}
						File fileAllegatiParteIntSeparati = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparati(), pNuovaPropostaAtto2CompletaBean.getTipoModAllegatiParteIntSeparati(), templateValuesPratica, getSession());
//						lListInputStream.add(new FileInputStream(fileAllegatiParteIntSeparati));		
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparati = new FileDaFirmareBean();
						lFileDaFirmareBeanAllegatiParteIntSeparati.setFile(fileAllegatiParteIntSeparati);
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparati);
					}
				}
			} else {
				// se è la vers. per la pubbl. e ho file allegati parte integrante sia uniti che separati (e non esclusi)				
				if(listaAllegatiParteIntSeparatiVersXPubbl.size() > 0 && flgAllegatiParteIntUnitiVersXPubbl) {
					if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparatiXPubbl()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparatiXPubbl())) {	
						String templateValuesAllegatiParteIntSeparatiXPubbl = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparatiXPubbl());				
//						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparatiXPubbl(), templateValuesAllegatiParteIntSeparatiXPubbl, true, getSession());
						Map<String, Object> mappaValoriAllegatiParteIntSeparatiXPubbl = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesAllegatiParteIntSeparatiXPubbl, true);
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparatiXPubbl(), mappaValoriAllegatiParteIntSeparatiXPubbl, true, flgMostraDatiSensibili, getSession()); 
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.getUri()));		
						lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.getUri()));
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl);
		
					} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparatiXPubbl())) {
						if(templateValuesPratica == null) {
							templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
						}
						File fileAllegatiParteIntSeparatiXPubbl = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparatiXPubbl(), pNuovaPropostaAtto2CompletaBean.getTipoModAllegatiParteIntSeparatiXPubbl(), templateValuesPratica, getSession());
//						lListInputStream.add(new FileInputStream(fileAllegatiParteIntSeparatiXPubbl));		
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl = new FileDaFirmareBean();
						lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.setFile(fileAllegatiParteIntSeparatiXPubbl);
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl);
					}
				}
			}	
			
			if(pNuovaPropostaAtto2CompletaBean.getFlgAppendiceDaUnire() != null && pNuovaPropostaAtto2CompletaBean.getFlgAppendiceDaUnire()) {
				if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModAppendice()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModAppendice())) {	
					String templateValuesAppendice = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModAppendice());				
//					FileDaFirmareBean lFileDaFirmareBeanAppendice = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAppendice(), templateValuesAppendice, true, getSession());
					Map<String, Object> mappaValoriAppendice = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesAppendice, true);
					FileDaFirmareBean lFileDaFirmareBeanAppendice = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAppendice(), mappaValoriAppendice, true, flgMostraDatiSensibili, getSession()); 
//					lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanAppendice.getUri()));		
					lFileDaFirmareBeanAppendice.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanAppendice.getUri()));
					lListFile.add(lFileDaFirmareBeanAppendice);
				} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModAppendice())) {
					if(templateValuesPratica == null) {
						templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
					}
					File fileAppendice = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModAppendice(), pNuovaPropostaAtto2CompletaBean.getTipoModAppendice(), templateValuesPratica, getSession());
//					lListInputStream.add(new FileInputStream(fileAppendice));	
					FileDaFirmareBean lfileAppendice = new FileDaFirmareBean();
					lfileAppendice.setFile(fileAppendice);
					lListFile.add(lfileAppendice);
				}
			}
//			File lFileUnione = unioneFilePdfOld(lListInputStream);
			File lFileUnione = unioneFilePdf(lListFile, pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile());
//			File lFileUnione = mergeFilePdf(lListFile);
			String nomeFileUnione = null;
			if(flgMostraDatiSensibili) {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnione")) ? getExtraparams().get("nomeFileUnione") : getPrefixRegNum(pNuovaPropostaAtto2CompletaBean) + "ATTO_COMPLETO_VERS_INTEGRALE.pdf";
			} else {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnioneOmissis")) ? getExtraparams().get("nomeFileUnioneOmissis") : getPrefixRegNum(pNuovaPropostaAtto2CompletaBean) + "ATTO_COMPLETO_VERS_X_PUBBLICAZIONE.pdf";
			}			 
			String uriFileUnione = StorageImplementation.getStorage().store(lFileUnione, new String[] {});
			FileDaFirmareBean lFileUnioneBean = new FileDaFirmareBean();
			lFileUnioneBean.setUri(uriFileUnione);
			lFileUnioneBean.setNomeFile(nomeFileUnione);	
//			lFileUnioneBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), nomeFileUnione, false, null));
			MimeTypeFirmaBean infoFileUnione = new MimeTypeFirmaBean();
			infoFileUnione.setMimetype("application/pdf");
			infoFileUnione.setDaScansione(false);
			infoFileUnione.setFirmato(false);
			infoFileUnione.setFirmaValida(false);
			infoFileUnione.setBytes(lFileUnione.length());
			infoFileUnione.setCorrectFileName(nomeFileUnione);
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFileUnione.setImpronta(lInfoFileUtility.calcolaImpronta(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), nomeFileUnione));
			lFileUnioneBean.setInfoFile(infoFileUnione);
			lFileUnioneBean.setUriFileOdtGenerato(uriFileOdtDaSalvare);
			return lFileUnioneBean;									
		} else {
			String errorMessage = "E' obbligatorio inserire almeno un file per procedere";
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
	}

	public File unioneFilePdf(List<FileDaFirmareBean> lListFile, ImpostazioniUnioneFileBean impostazioneUnioneFile) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = File.createTempFile("pdf", ".pdf");
		FileOutputStream out = new FileOutputStream(lFileUnione);
		Document document = new Document();
		// Istanzio una copia nell'output
		PdfCopy copy = new PdfCopy(document, out);
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		
		// recupero le impostazioni di unione relative all'intestazione degli allegati
		boolean infoAllegatoAttiva = false;
		int infoAllegatoMaxLenNomeFileAllegato = 100;
		String infoAllegatoMargine = "";
		String infoAllegatoOrientamento = "";
		String infoAllegatoPagine = "";
		Font infoAllegatoFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
		if(impostazioneUnioneFile != null) {
			infoAllegatoAttiva = impostazioneUnioneFile.getInfoAllegatoAttiva() != null && impostazioneUnioneFile.getInfoAllegatoAttiva();
			if (infoAllegatoAttiva) {
				infoAllegatoMaxLenNomeFileAllegato = impostazioneUnioneFile.getInfoAllegatoMaxLenNomeFileAllegato();
				infoAllegatoMargine = impostazioneUnioneFile.getInfoAllegatoMargine();
				infoAllegatoOrientamento = impostazioneUnioneFile.getInfoAllegatoOrientamento();
				infoAllegatoPagine = impostazioneUnioneFile.getInfoAllegatoPagine();		
				if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoFontSize())) {
					infoAllegatoFont.setSize(Float.parseFloat(impostazioneUnioneFile.getInfoAllegatoFontSize()));
				}
				if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoFont())) {
					infoAllegatoFont.setFamily(impostazioneUnioneFile.getInfoAllegatoFont());
				}
				if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoStyle())) {
					infoAllegatoFont.setStyle(impostazioneUnioneFile.getInfoAllegatoStyle().toLowerCase());
				}
				if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoTextColor())) {
					setFontColor(infoAllegatoFont, impostazioneUnioneFile.getInfoAllegatoTextColor());
				}
			}
		}

		// recupero le impostazioni di unione relative al numero pagina degli allegati
		boolean nroPaginaAttiva = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaAttiva();
		boolean nroPaginaEscludiAllegatiMaggioreA4 = false;
		boolean nroPaginaNumerazioneDistintaAttiva = false;
		int nTotDoc = 0;
		String nroPaginaPosizione = "";
		Font nroPaginaFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
		if (nroPaginaAttiva) {
			nroPaginaEscludiAllegatiMaggioreA4 = impostazioneUnioneFile.getNroPaginaEscludiAllegatiMaggioreA4();
			nroPaginaNumerazioneDistintaAttiva = impostazioneUnioneFile.getNroPaginaNumerazioneDistintaXFileUniti();
			
			if (!nroPaginaNumerazioneDistintaAttiva) {
				nTotDoc = getTotalNumberPages(lListFile, nTotDoc);
			}
			
			nroPaginaPosizione = impostazioneUnioneFile.getNroPaginaPosizione();
			String nroPaginaFontFamily = impostazioneUnioneFile.getNroPaginaFont();
			String nroPaginaFontSize = impostazioneUnioneFile.getNroPaginaFontSize();
			String nroPaginaFontStyle = impostazioneUnioneFile.getNroPaginaStyle();
			String nroPaginaFontColor = impostazioneUnioneFile.getNroPaginaTextColor();
			if (StringUtils.isNotBlank(nroPaginaFontSize)) {
				nroPaginaFont.setSize(Float.parseFloat(nroPaginaFontSize));
			} 
			if (StringUtils.isNotBlank(nroPaginaFontFamily)) {
				nroPaginaFont.setFamily(nroPaginaFontFamily);
			}
			if (StringUtils.isNotBlank(nroPaginaFontStyle)) {
				nroPaginaFont.setStyle(nroPaginaFontStyle.toLowerCase());
			}
			if (StringUtils.isNotBlank(nroPaginaFontColor)) {
				setFontColor(nroPaginaFont, nroPaginaFontColor);
			}
		}
		
		
		// inizializzo un counter per gli allegati
		int counterAllegati = 0;
		//inizializzo il counter delle pagine per la numerazione unica
		int lastPage = 0;
		
		// Per ogni file passato
		for (FileDaFirmareBean fileDaFirmareBean : lListFile) {
			// recupero il file
			File lFile = fileDaFirmareBean.getFile();
			
			//verifico se è o meno un allegato
			boolean isAllegato = StringUtils.isNotBlank(fileDaFirmareBean.getIdFile()) ? fileDaFirmareBean.getIdFile().toLowerCase().startsWith("allegato") : false;
			String infoAllegatoTesto = "";
			if (isAllegato) {
				//se è un allegato aggiorno il counter degli allegati
				counterAllegati++;
				if (infoAllegatoAttiva) {
					// se devo aggiungere l'intestazione calcolo l'intestazione in base al formato desiderato
					if(StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoFormato())) {		
						infoAllegatoTesto = impostazioneUnioneFile.getInfoAllegatoFormato().replace("$nroAllegato$", counterAllegati + "");
						String nomeFileAllegato = fileDaFirmareBean.getNomeFile();
						if(nomeFileAllegato != null) {
							if (nomeFileAllegato.length()>infoAllegatoMaxLenNomeFileAllegato){
								nomeFileAllegato = nomeFileAllegato.substring(0,infoAllegatoMaxLenNomeFileAllegato);
							}
							nomeFileAllegato = nomeFileAllegato.replaceAll("_", " ");
						} else {
							nomeFileAllegato = "";
						}
						infoAllegatoTesto = infoAllegatoTesto.replace("$nomeFileAllegato$", nomeFileAllegato);
					}
				}
			}
			
			// Leggo il file
			reader = new PdfReader(lFile.getAbsolutePath());
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				PdfImportedPage importedPage = copy.getImportedPage(reader, ++page);
				Rectangle pageSizeWithRotation = reader.getPageSizeWithRotation(page);
				// se sto processando un allegato ed è attiva l'aggiunta dell'intestazione
				if (isAllegato && infoAllegatoAttiva) {
					if (infoAllegatoPagine.equalsIgnoreCase("prima")) {
						// aggiungo l'intestazione solo alla prima pagina
						if (page == 1) {
							PdfUtility.addTesto(copy, infoAllegatoTesto, infoAllegatoFont, importedPage, infoAllegatoMargine, infoAllegatoOrientamento, pageSizeWithRotation);
						}
					} else {
						PdfUtility.addTesto(copy, infoAllegatoTesto, infoAllegatoFont, importedPage, infoAllegatoMargine, infoAllegatoOrientamento, pageSizeWithRotation);
					}
				}
				
				// se è attiva l'aggiunta del numero pagina
				if (nroPaginaAttiva) {
					
					boolean addNroPagina = !(nroPaginaEscludiAllegatiMaggioreA4 && PdfUtility.isFileMaggioreA4(reader.getPageSizeWithRotation(page))) || !nroPaginaEscludiAllegatiMaggioreA4;
					// se non è attiva l'esclusione dei formati maggiori di a4
					// o è attiva ma non è un allegato maggiore di a4 applico il nroPagina
					if (addNroPagina) {
						// definisco il formato del numero pagina per ogni pagina
						String nroPaginaTesto = "";
						if (nroPaginaNumerazioneDistintaAttiva) {
							// se è attiva la numerazione distinta, ad ogni file riparto da pagina 1
							if(StringUtils.isNotBlank( impostazioneUnioneFile.getNroPaginaFormato())) {		
								nroPaginaTesto =  impostazioneUnioneFile.getNroPaginaFormato().replace("$nroPagina$", page + "");
								nroPaginaTesto = nroPaginaTesto.replace("$totPagine$", n + "");
							}
						} else {
							// la numerazione è unica per tutto il file unione
							if(StringUtils.isNotBlank( impostazioneUnioneFile.getNroPaginaFormato())) {		
								nroPaginaTesto =  impostazioneUnioneFile.getNroPaginaFormato().replace("$nroPagina$", ++lastPage + "");
								nroPaginaTesto = nroPaginaTesto.replace("$totPagine$", nTotDoc + "");
							}
						}
						PdfUtility.addTesto(copy, nroPaginaTesto, nroPaginaFont, importedPage, nroPaginaPosizione, "orizzontale", pageSizeWithRotation);
					}
				}
				
				copy.addPage(importedPage);
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
			// Chiudo il reader
			reader.close();
		}
		// Chiudo la copia
		copy.close();
		// Chiudo il documento
		document.close();
		// Chiudo lo stream del file in output
		out.close();
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("unioneFilePdf executed in " + delay + " ms");
		return lFileUnione;
	}

	protected int getTotalNumberPages(List<FileDaFirmareBean> lListFile, int nTotDoc) throws IOException {
		PdfReader reader;
		for (FileDaFirmareBean fileDaFirmareBean : lListFile) {
			// Leggo il file
			reader = new PdfReader(fileDaFirmareBean.getFile().getAbsolutePath());
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// le aggiungo al numero di pagine totali
			nTotDoc += n;
			//chiudo il reader di questo file
			reader.close();
		}
		return nTotDoc;
	}

	protected void setFontColor(Font font, String fontColor) {
		switch (fontColor.toUpperCase()) {
		case "WHITE":
			font.setColor(BaseColor.WHITE);				
			break;
		case "LIGHT_GRAY":
			font.setColor(BaseColor.LIGHT_GRAY);				
			break;
		case "GRAY":
			font.setColor(BaseColor.GRAY);				
			break;
		case "DARK_GRAY":
			font.setColor(BaseColor.DARK_GRAY);
			break;
		case "GREEN":
			font.setColor(BaseColor.GREEN);
			break;
		case "YELLOW":
			font.setColor(BaseColor.YELLOW);
			break;
		case "ORANGE":
			font.setColor(BaseColor.ORANGE);
			break;
		case "PINK":
			font.setColor(BaseColor.PINK);
			break;
		case "RED":
			font.setColor(BaseColor.RED);
			break;
		case "MAGENTA":
			font.setColor(BaseColor.MAGENTA);
			break;
		case "CYAN":
			font.setColor(BaseColor.CYAN);
			break;
		case "BLUE":
			font.setColor(BaseColor.BLUE);
			break;
		case "BLACK ":
		default:
			font.setColor(BaseColor.BLACK);
			break;
		}
	}

	@Deprecated
	public File unioneFilePdfOld(List<InputStream> lListInputStream) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = File.createTempFile("pdf", ".pdf");
		FileOutputStream out = new FileOutputStream(lFileUnione);
		Document document = new Document();
		// Istanzio una copia nell'output
		PdfCopy copy = new PdfCopy(document, out);
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		// Per ogni file passato
		for (InputStream lInputStream : lListInputStream) {
			// Leggo il file
			reader = new PdfReader(lInputStream);
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
			// Chiudo il reader
			reader.close();
			// Chiudo gli stream dei file in input
			try { lInputStream.close(); } catch(Exception e) {}
		}
		// Chiudo la copia
		copy.close();
		// Chiudo il documento
		document.close();
		// Chiudo lo stream del file in output
		out.close();		
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("unioneFilePdfOld executed in " + delay + " ms");
		return lFileUnione;
	}
	
	public File unioneFilePdfSenzaIntestazioniNroPagina(List<File> lListFile) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = File.createTempFile("pdf", ".pdf");
		FileOutputStream out = new FileOutputStream(lFileUnione);
		Document document = new Document();
		// Istanzio una copia nell'output
		PdfCopy copy = new PdfCopy(document, out);
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		// Per ogni file passato
		for (File lFile : lListFile) {
			// Leggo il file
			reader = new PdfReader(lFile.getAbsolutePath());
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
			// Chiudo il reader
			reader.close();
		}
		// Chiudo la copia
		copy.close();
		// Chiudo il documento
		document.close();
		// Chiudo lo stream del file in output
		out.close();
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("unioneFilePdf executed in " + delay + " ms");
		return lFileUnione;
	}
	
	public File mergeFilePdf(List<File> lListFile) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = new MergeDocument().mergeDocuments(lListFile.toArray(new File[lListFile.size()]));
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("mergeFilePdf executed in " + delay + " ms");
		return lFileUnione;
	}
	
	public CompilaModelloNuovaPropostaAtto2CompletaBean compilazioneAutomaticaModelloPdf(CompilaModelloNuovaPropostaAtto2CompletaBean modelloBean) throws Exception {
		String templateValues = getDatiXGenDaModello(modelloBean.getDettaglioBean(), modelloBean.getNomeModello());
		if(StringUtils.isNotBlank(modelloBean.getIdModello())) {
			FileDaFirmareBean lFileDaFirmareBean = ModelliUtil.fillTemplate(modelloBean.getProcessId(), modelloBean.getIdModello(), templateValues, true, getSession());			
			modelloBean.setUri(lFileDaFirmareBean.getUri());
			modelloBean.setInfoFile(lFileDaFirmareBean.getInfoFile());			
		} else {
			File fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModello(), modelloBean.getTipoModello(), templateValues, getSession());
			String storageUri = StorageImplementation.getStorage().store(fileModelloPdf);
			modelloBean.setUri(storageUri);
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileModelloPdf.length());
			infoFile.setCorrectFileName(modelloBean.getNomeFile());
			File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
			modelloBean.setInfoFile(infoFile);	
		}		
		return modelloBean;
	}
	
	public CompilaListaModelliNuovaPropostaAtto2CompletaBean compilazioneAutomaticaListaModelliPdf(CompilaListaModelliNuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(bean.getListaRecordModelli() != null) {
			for(int i = 0; i < bean.getListaRecordModelli().size(); i++) {
				CompilaModelloAttivitaBean modelloBean = bean.getListaRecordModelli().get(i);
				String templateValues = getDatiXGenDaModello(bean.getDettaglioBean(), modelloBean.getNomeModello());				
				if(StringUtils.isNotBlank(modelloBean.getIdModello())) {
					FileDaFirmareBean lFileDaFirmareBean = ModelliUtil.fillTemplate(bean.getProcessId(), modelloBean.getIdModello(), templateValues, true, getSession());			
					modelloBean.setUriFileGenerato(lFileDaFirmareBean.getUri());
					modelloBean.setInfoFileGenerato(lFileDaFirmareBean.getInfoFile());			
				} else {
					File fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(bean.getProcessId(), modelloBean.getUri(), modelloBean.getTipoModello(), templateValues, getSession());
					String storageUri = StorageImplementation.getStorage().store(fileModelloPdf);
					modelloBean.setUriFileGenerato(storageUri);
					MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
					infoFile.setMimetype("application/pdf");
					infoFile.setDaScansione(false);
					infoFile.setFirmato(false);
					infoFile.setFirmaValida(false);
					infoFile.setBytes(fileModelloPdf.length());
					infoFile.setCorrectFileName(modelloBean.getNomeFile());
					File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
					InfoFileUtility lInfoFileUtility = new InfoFileUtility();
					infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
					modelloBean.setInfoFileGenerato(infoFile);	
				}	
			}			
		}
		return bean;
	}
	
	public FileDaFirmareBean getInfoOggLiquidazione(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(ParametriDBUtil.getParametroDB(getSession(), "URI_INFO_OGG_LIQUIDAZIONE"));
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uri = StorageImplementation.getStorage().store(out.getExtracted());
		File lFile = StorageImplementation.getStorage().getRealFile(uri);				
		String nomeFile = "INFO_OGG_LIQUIDAZIONE.pdf";
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setNomeFile(nomeFile);
		lFileDaFirmareBean.setUri(uri);
		lFileDaFirmareBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaFirmareBean;			
	}
	
	private void aggiungiPrimario(ArrayList<FileDaFirmareBean> listaFile, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setIdFile("primario" + pNuovaPropostaAtto2CompletaBean.getUriFilePrimario());
		lFileDaFirmareBean.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimario());
		lFileDaFirmareBean.setNomeFile(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimario());
		lFileDaFirmareBean.setUri(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario());		
		if(listaFile == null) {
			listaFile = new ArrayList<FileDaFirmareBean>();
		}
		listaFile.add(lFileDaFirmareBean);		
	}

	private void aggiungiPrimarioOmissis(ArrayList<FileDaFirmareBean> listaFile, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
		lFileDaFirmareBeanOmissis.setIdFile("primarioOmissis" + pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setNomeFile(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setUri(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis());		
		if(listaFile == null) {
			listaFile = new ArrayList<FileDaFirmareBean>();
		}
		listaFile.add(lFileDaFirmareBeanOmissis);			
	}
	
	private void aggiungiAllegato(ArrayList<FileDaFirmareBean> listaFile, AllegatoProtocolloBean lAllegatoProtocolloBean) throws Exception {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
			FileDaFirmareBean lFileAllegatoBean = new FileDaFirmareBean();
			lFileAllegatoBean.setIdFile("allegato" + lAllegatoProtocolloBean.getUriFileAllegato());
			lFileAllegatoBean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
			lFileAllegatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
			lFileAllegatoBean.setUri(lAllegatoProtocolloBean.getUriFileAllegato());
			lFileAllegatoBean.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirma());
			if(listaFile == null) {
				listaFile = new ArrayList<FileDaFirmareBean>();
			}
			listaFile.add(lFileAllegatoBean);		
		}
	}
	
	private void aggiungiAllegatoOmissis(ArrayList<FileDaFirmareBean> listaFile, AllegatoProtocolloBean lAllegatoProtocolloBean) throws Exception {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
			FileDaFirmareBean lFileAllegatoOmissisBean = new FileDaFirmareBean();
			lFileAllegatoOmissisBean.setIdFile("allegatoOmissis" + lAllegatoProtocolloBean.getUriFileOmissis());
			lFileAllegatoOmissisBean.setInfoFile(lAllegatoProtocolloBean.getInfoFileOmissis());
			lFileAllegatoOmissisBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
			lFileAllegatoOmissisBean.setUri(lAllegatoProtocolloBean.getUriFileOmissis());
			lFileAllegatoOmissisBean.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirmaOmissis());
			if(listaFile == null) {
				listaFile = new ArrayList<FileDaFirmareBean>();
			}
			listaFile.add(lFileAllegatoOmissisBean);		
		}
	}
	
	public NuovaPropostaAtto2CompletaBean aggiornaFileFirmati(TaskNuovaPropostaAtto2CompletaFileFirmatiBean pTaskNuovaPropostaAtto2CompletaFileFirmatiBean) throws Exception {
		NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getProtocolloOriginale();
		if (pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getFileFirmati() != null && pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getFileFirmati().getFiles() != null) {
			boolean firmaNonValida = false;
			for (FileDaFirmareBean lFileDaFirmareBean : pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getFileFirmati().getFiles()) {
				String idFile = lFileDaFirmareBean.getIdFile();
				if (lFileDaFirmareBean.getInfoFile().isFirmato() && !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
					if (idFile.startsWith("primarioOmissis")) {
						logger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("primario")) {
						logger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("allegatoOmissis")) {
						logger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("allegato")) {
						logger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					}
					firmaNonValida = true;
				}
				if (idFile.startsWith("primarioOmissis")) {
					aggiornaPrimarioOmissisFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("primario")) {
					aggiornaPrimarioFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegatoOmissis")) {
					aggiornaAllegatoOmissisFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegato")) {
					aggiornaAllegatoFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} 
			}
			if (firmaNonValida) {
				throw new StoreException("La firma di uno o più file risulta essere non valida");
			}
		}
		return lNuovaPropostaAtto2CompletaBean;
	}
	
	private void aggiornaPrimarioFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		if(lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimario() != null && lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimario()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, lNuovaPropostaAtto2CompletaBean);			
			getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean).aggiornaFilePrimario(lProtocollazioneBean);
		}
		aggiornaPrimario(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
	}
	
	private void aggiornaPrimarioOmissisFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		if(lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimarioOmissis() != null && lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimarioOmissis()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, lNuovaPropostaAtto2CompletaBean);			
			getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean).aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		aggiornaPrimarioOmissis(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaAllegatoFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean);
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {
				if(lAllegatoProtocolloBean.getIsChanged() != null && lAllegatoProtocolloBean.getIsChanged()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegato(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
	}
	
	private void aggiornaAllegatoOmissisFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean);
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				if(lAllegatoProtocolloBean.getIsChangedOmissis() != null && lAllegatoProtocolloBean.getIsChangedOmissis()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegatoOmissis(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegatoOmissis(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaPrimario(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) {
		lNuovaPropostaAtto2CompletaBean.setUriFilePrimario(lFileDaFirmareBean.getUri());
		lNuovaPropostaAtto2CompletaBean.setNomeFilePrimario(lFileDaFirmareBean.getNomeFile());
		lNuovaPropostaAtto2CompletaBean.setRemoteUriFilePrimario(false);
		String precImpronta = lNuovaPropostaAtto2CompletaBean.getInfoFilePrimario() != null ? lNuovaPropostaAtto2CompletaBean.getInfoFilePrimario().getImpronta() : null;
		lNuovaPropostaAtto2CompletaBean.setInfoFilePrimario(lFileDaFirmareBean.getInfoFile());
		if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lNuovaPropostaAtto2CompletaBean.setIsChangedFilePrimario(true);
		}
	}
	
	private void aggiornaPrimarioOmissis(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) {
		lNuovaPropostaAtto2CompletaBean.setUriFilePrimarioOmissis(lFileDaFirmareBean.getUri());
		lNuovaPropostaAtto2CompletaBean.setNomeFilePrimarioOmissis(lFileDaFirmareBean.getNomeFile());
		lNuovaPropostaAtto2CompletaBean.setRemoteUriFilePrimarioOmissis(false);
		String precImprontaOmissis = lNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis() != null ? lNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis().getImpronta() : null;
		lNuovaPropostaAtto2CompletaBean.setInfoFilePrimarioOmissis(lFileDaFirmareBean.getInfoFile());
		if (precImprontaOmissis == null || !precImprontaOmissis.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lNuovaPropostaAtto2CompletaBean.setIsChangedFilePrimarioOmissis(true);
		}
	}

	private void aggiornaAllegato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		if(lNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {				
					lAllegatoProtocolloBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
					lAllegatoProtocolloBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
					lAllegatoProtocolloBean.setRemoteUri(false);
					String precImpronta = lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null;
					lAllegatoProtocolloBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
					if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
						lAllegatoProtocolloBean.setIsChanged(true);
					}
					break;
				}
			}
		}
	}
	
	private void aggiornaAllegatoOmissis(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegatoOmissis".length());
		if(lNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginale)){
					lAllegatoProtocolloBean.setUriFileOmissis(lFileDaFirmareBean.getUri());
					lAllegatoProtocolloBean.setNomeFileOmissis(lFileDaFirmareBean.getNomeFile());
					lAllegatoProtocolloBean.setRemoteUriOmissis(false);
					String precImprontaOmissis = lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getImpronta() : null;
					lAllegatoProtocolloBean.setInfoFileOmissis(lFileDaFirmareBean.getInfoFile());
					if (precImprontaOmissis == null || !precImprontaOmissis.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
						lAllegatoProtocolloBean.setIsChangedOmissis(true);
					}
					break;
				}
			}
		}
	}
	
	private FileDaFirmareBean convertiFile(FileDaFirmareBean lFileDaConvertireBean) throws Exception{
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(lFileDaConvertireBean.getUri());
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));				
		String nomeFile = lFileDaConvertireBean.getInfoFile().getCorrectFileName() != null ? lFileDaConvertireBean.getInfoFile().getCorrectFileName() : "";
		String nomeFilePdf = FilenameUtils.getBaseName(nomeFile) + ".pdf";
		String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(lFile.toURI().toString(), nomeFile));
		lFileDaConvertireBean.setNomeFile(nomeFilePdf);
		lFileDaConvertireBean.setUri(uriPdf);
		lFileDaConvertireBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaConvertireBean;			
	}
	
	private FileDaFirmareBean sbustaFile(FileDaFirmareBean lFileDaSbustareBean) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(lFileDaSbustareBean.getUri());				
		String nomeFile = lFileDaSbustareBean.getInfoFile().getCorrectFileName() != null ? lFileDaSbustareBean.getInfoFile().getCorrectFileName() : "";		
		String nomeFileSbustato = (nomeFile != null && nomeFile.toLowerCase().endsWith(".p7m")) ? nomeFile.substring(0, nomeFile.length() - 4) : nomeFile;		
		String uriSbustato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(lFile.toURI().toString(), nomeFile));		
		lFileDaSbustareBean.setNomeFile(nomeFileSbustato);
		lFileDaSbustareBean.setUri(uriSbustato);
		lFileDaSbustareBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaSbustareBean;				
	}
	
	@Override
	public PaginatorBean<NuovaPropostaAtto2CompletaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		return null;
	}
	
	@Override
	public NuovaPropostaAtto2CompletaBean remove(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		return null;
	};
	
	public boolean isAttivoSIB(NuovaPropostaAtto2CompletaBean bean) {
		if(!isSiglaPropostaAttoXAMC(bean)) {
			return false;
		}
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
	
	public boolean isAttivoContabilia(NuovaPropostaAtto2CompletaBean bean) {
		if(!isSiglaPropostaAttoXAMC(bean)) {
			return false;
		}
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC));
	}
	
	public boolean isAttivoSICRA(NuovaPropostaAtto2CompletaBean bean) {
		if(!isSiglaPropostaAttoXAMC(bean)) {
			return false;
		}
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SICRA".equalsIgnoreCase(lSistAMC);
	}
	
	private boolean isSiglaPropostaAttoXAMC(NuovaPropostaAtto2CompletaBean bean) {
		String lSiglePropAttiXAMC = ParametriDBUtil.getParametroDB(getSession(), "SIGLE_PROP_ATTI_PER_AMC");
		if(StringUtils.isNotBlank(lSiglePropAttiXAMC)) {
			StringSplitterServer st = new StringSplitterServer(lSiglePropAttiXAMC, ";");
			boolean trovato = false;
    		while(st.hasMoreTokens()) {
    			String sigla = st.nextToken();
    			if(StringUtils.isNotBlank(sigla) && StringUtils.isNotBlank(bean.getSiglaRegProvvisoria()) && 
    					(bean.getSiglaRegProvvisoria().equalsIgnoreCase(sigla) || bean.getSiglaRegProvvisoria().toUpperCase().startsWith(sigla.toUpperCase() + "-"))) {
    				trovato = true;
    			}
    		}
    		if(!trovato) {
    			return false;
    		}
		}
		return true;
	}
	
	private BufferedImage joinBufferedImage(float aspectRatio, BufferedImage... imgs) throws IOException {

		// do some calculate first
		int totalWidth =  Math.round(100 * aspectRatio);
		int wid = 0;
		int height =  0;
		for (BufferedImage bufferedImage : imgs) {
			wid += bufferedImage.getWidth();
			height = Math.max(height, bufferedImage.getHeight());
		}
		
		BufferedImage blankImg = ImageIO.read(new File(getRequest().getServletContext().getRealPath("/images/pratiche/icone/blank.png")));
		Image tmp = blankImg.getScaledInstance((totalWidth - wid) / 2, height, Image.SCALE_SMOOTH);
	    BufferedImage scaledBalnkImage = new BufferedImage((totalWidth - wid) / 2, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = scaledBalnkImage.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	     
		BufferedImage newImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		// fill background
		g2.fillRect(0, 0, totalWidth, height);
		// draw image
		g2.setColor(oldColor);
		int x = 0;
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		for (BufferedImage bufferedImage : imgs) {
			g2.drawImage(bufferedImage, null, x, 0);
			x += bufferedImage.getWidth();
		}
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		g2.dispose();
		return newImage;
	}
	
	private AllegatoProtocolloBean getFileModello(ModelliDocBean bean) {
		
		if(bean.getListaModelli() != null && bean.getListaModelli().size() > 0) {			
			return bean.getListaModelli().get(0);
		}		
		
		return null;
	}	
	
	private String estraiTestoOmissisDaHtml(String html) {
		if (StringUtils.isNotBlank(html)) {
			html = FreeMarkerModelliUtil.replaceOmissisInHtml(html);
			return Jsoup.parse(html).text().replaceAll("\\<.*?>","");
		} else {
			return html;
		}
	}
	
	public void eseguiProtocollazioneProsa(NuovaPropostaAtto2CompletaBean nuovaPropostaAtto2CompletaBean) throws Exception {
		boolean allegatiErrorMessages = false;
		
		ProsaDocumentoProtocollato inputProsaProtocollaBean = new ProsaDocumentoProtocollato();
	
		File filePrimario = recuperaFile(nuovaPropostaAtto2CompletaBean.getRemoteUriFilePrimario(), nuovaPropostaAtto2CompletaBean.getUriFilePrimario());
		
		byte[] bytes = convertFileToBytes(filePrimario);
		
		inputProsaProtocollaBean.setNomeFileContenuto(filePrimario.getName());
		inputProsaProtocollaBean.setContenuto(bytes);
		
		inputProsaProtocollaBean.setOggetto(nuovaPropostaAtto2CompletaBean.getOggetto());
		inputProsaProtocollaBean.setTipoProtocollo("U");
		

		ProsaMittenteDestinatario mittdest = new ProsaMittenteDestinatario();
		mittdest.setDenominazione(nuovaPropostaAtto2CompletaBean.getDesUfficioProponente());
		mittdest.setCodiceMittenteDestinatario(nuovaPropostaAtto2CompletaBean.getCodUfficioProponente());
		ProsaMittenteDestinatario[] mitt = new ProsaMittenteDestinatario[1];
		mitt[0] = mittdest;

		inputProsaProtocollaBean.setMittentiDestinatari(mitt);		

		ProtocollazioneProsaImpl service = new ProtocollazioneProsaImpl();
		Result<ProsaDocumentoProtocollato> output = service.protocolla(getLocale(), inputProsaProtocollaBean);
		
		if (!output.isOk()) {
			if(output.isTimeout()) {
				logger.error("Errore WSprotocolla Prosa: timeout");
				throw new StoreException("Errore WSprotocolla Prosa: timeout");
			}else if(output.isRispostaNonRicevuta()){
				logger.error("Errore WSprotocolla Prosa: nessuna risposta dal servizio");
				throw new StoreException("Errore WSprotocolla Prosa: nessuna risposta dal servizio");
			}else {
				String errorMessage = "Fallita protocollazione: " + (StringUtils.isNotBlank(output.getMessage()) ? output.getMessage() : "");
				logger.error("Errore WSprotocolla Prosa: " + output.getMessage());
				throw new StoreException(errorMessage);
			}
		}else {
			 
			ProsaDocumentoProtocollato resultBeanProtocollazioneProsa = output.getPayload();
			
			//Dopo aver fatto la protocollazione sul file primario aggiungo gli allegati chiamando il WS di Prosa: inserisciAllegato 
			allegatiErrorMessages = aggiungiAllegatiProtocollazioneProsa(nuovaPropostaAtto2CompletaBean.getListaAllegati(), resultBeanProtocollazioneProsa.getId());
			
			if(StringUtils.isNotBlank(resultBeanProtocollazioneProsa.getNumeroProtocollo())) {
				//Se la protocollazione in Prosa è adnata a buon fine chiamo UpdDocUD
				
				//Converto le date dal formato di prosa al formato di Auriga
				Calendar calendar = GregorianCalendar.getInstance();				
				String date;
				if(resultBeanProtocollazioneProsa.getDataProtocollo()!=null) {
					date = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(resultBeanProtocollazioneProsa.getDataProtocollo().getTime());
				}else {
					date = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(calendar.getTime());
				}
				
				String annoProtocolazioneProsa = String.valueOf(calendar.get(Calendar.YEAR));
				Date dataProtocollazioneProsa = new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(date);
				String numeroProtocolloProsa = resultBeanProtocollazioneProsa.getNumeroProtocollo();
				
				updateDatiProsa(nuovaPropostaAtto2CompletaBean.getIdUd(), numeroProtocolloProsa, dataProtocollazioneProsa, annoProtocolazioneProsa);				
						
			}else {
				throw new StoreException("Fallita Protocollazione");
			}
		}
		
		//Se sono avvenuti errori durante l'inserimento di uno o piu allegati
		if(allegatiErrorMessages) {
			addMessage("Non e' stato possibile aggiungere uno o più allegati al protocollo", "", MessageType.WARNING);
		}
		
	}


	private boolean aggiungiAllegatiProtocollazioneProsa(List<AllegatoProtocolloBean> listaAllegatiPROSA, Long idProtocolloProsa) throws Exception {
		boolean flgErroreInsAllegati = false;
		
		for(AllegatoProtocolloBean allegatoBean : listaAllegatiPROSA) {
			
			ProsaAllegato inputProsaAllegatoBean = new ProsaAllegato();
			
			File fileAllegato = recuperaFile(allegatoBean.getRemoteUri(), allegatoBean.getUriFileAllegato());
			
			byte[] bytes = convertFileToBytes(fileAllegato);
			
			inputProsaAllegatoBean.setContenuto(bytes);
			inputProsaAllegatoBean.setNomeFile(fileAllegato.getName());
			inputProsaAllegatoBean.setIdProfilo(idProtocolloProsa);
			
			ProtocollazioneProsaImpl service = new ProtocollazioneProsaImpl();
			Result<ProsaAllegato> output = service.inserisciallegato(getLocale(), inputProsaAllegatoBean);
			
			if (!output.isOk()) {
				String messageError = "";

				if (output.isTimeout()) {
					messageError = "timeout";
				} else if (output.isRispostaNonRicevuta()) {
					messageError = "nessuna risposta dal servizio";
				} else {
					messageError = (StringUtils.isNotBlank(output.getMessage()) ? output.getMessage() : "");
				}
				logger.error("Errore WSaggiungiAllegato Prosa sull'allegato chiamato: " + fileAllegato.getName() + ": " + messageError);
				flgErroreInsAllegati = true;
			}		
				
		}
		
		return flgErroreInsAllegati;
	}
		
	void updateDatiProsa(String idUd, String numeroProtocolloProsa, Date dataProtocollazioneProsa, String annoProtocolazioneProsa) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();	
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtipotargetin("D");
		input.setIduddocin(new BigDecimal(idUd));

		CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
		creaModDocumentoInBean.setAppendRegistroEmergenza("1");
		
		List<RegistroEmergenza> lListRegistrazioniDaDare = new ArrayList<RegistroEmergenza>();

		RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
		lRegistroEmergenza.setFisso("PG");
		lRegistroEmergenza.setRegistro(null);
		lRegistroEmergenza.setAnno(annoProtocolazioneProsa);
		lRegistroEmergenza.setNro(numeroProtocolloProsa);
		lRegistroEmergenza.setDataRegistrazione(dataProtocollazioneProsa);
		lListRegistrazioniDaDare.add(lRegistroEmergenza);
		
		creaModDocumentoInBean.setRegistroEmergenza(lListRegistrazioniDaDare);
		
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));

		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> outputStoreBean = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if (outputStoreBean.isInError()) {
			throw new StoreException("Fallita memorizzazione degli estremi di protocollo ricevuti da Prosa: " + outputStoreBean.getDefaultMessage());
		}
	}
		
	private File recuperaFile(Boolean remoteUriFile, String uriFile) throws StorageException {
		// Estraggo il file dal repository
		File file = null;
		if (remoteUriFile != null && remoteUriFile) {
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uriFile);
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
			file = out.getExtracted();
		} else {
			// File locale
			file = StorageImplementation.getStorage().extractFile(uriFile);
		}
		
		return file;
	}
	
	byte[] convertFileToBytes(File file) throws Exception {		
		FileInputStream is = new FileInputStream(file);
		long length = file.length();
		byte[] bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		is.close();
		
		return bytes;
	}
		
//	private float getLogoAspectRatio() throws Exception{             
//        TextDocument td = TextDocument.loadDocument(new File("multilingual.odt"));
//        OdfContentDom conDom=td.getContentDom();
//        Node n1=conDom.getFirstChild();
//        return parseXMl(n1);
//	}
	
	// private void parseXMl(Node n,OdfPackage pack) throws Exception{
//	private float parseXMl(Node n) throws Exception{
//       NodeList nl = n.getChildNodes();
//       if(nl==null || nl.getLength()==0){//leaf element
//    	   NamedNodeMap  map=n.getAttributes();
//    	   if("draw:image".equals(n.getNodeName())){
//    		   Node frameNode = n.getParentNode();
//    		   if (frameNode instanceof OdfDrawFrame) {
//    			   NamedNodeMap attributi = frameNode.getAttributes();
//    			   for (int i = 0; i < attributi.getLength(); i++){
//    				   if ("jooscript.image(imgLogo)".equals(attributi.item(i).getNodeValue())){
//    					   String altezza = ;
//    					   String larghezza = "";
//    					   if (StringUtils.isNotBlank(altezza) && altezza.lengh)
//    					   
//    					   String s = "ciao";    				   }
//    			   }
//    			   String s = "";
//    			   //if ("jooscript.image(imgLogo)".equalsIgnoreCase(frameNode.ge))
//    		   }
//    			   
//    		   String s = "";
//    		   return 0;
//    	   }
//    	   return 0;
//       }
//       for (int i=0; i < nl.getLength(); i++) {
//    	   Node   an = nl.item(i);
//    	   parseXMl(an);
//       }
//       return 0;
//
//	}
	
	public NuovaPropostaAtto2CompletaBean aggiornaStato(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		if(StringUtils.isNotBlank(bean.getUriFilePrimario())) {
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, bean);			
			getProtocolloDataSource(bean).aggiornaFilePrimario(lProtocollazioneBean);
		}
		if(StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
			if(StringUtils.isBlank(bean.getIdDocPrimarioOmissis())) {
				DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
				lAdddocInput.setCodidconnectiontokenin(token);
				lAdddocInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
				// La versione omissis del primario viene salvata come fosse un allegato
				AllegatoVersConOmissisStoreBean attributiOmissis = new AllegatoVersConOmissisStoreBean();
				attributiOmissis.setIdUd(new BigDecimal(bean.getIdUd()));
				attributiOmissis.setFlgVersConOmissis("1");
				attributiOmissis.setIdDocVersIntegrale(bean.getIdDocPrimario());							
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiOmissis));
				DmpkCoreAdddoc lDmpkCoreAdddoc = new DmpkCoreAdddoc();
				StoreResultBean<DmpkCoreAdddocBean> lAdddocOutput = lDmpkCoreAdddoc.execute(getLocale(), loginBean, lAdddocInput);
				if (StringUtils.isNotBlank(lAdddocOutput.getDefaultMessage())) {
					if (lAdddocOutput.isInError()) {
						throw new StoreException(lAdddocOutput);
					} else {
						addMessage(lAdddocOutput.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
				bean.setIdDocPrimarioOmissis(lAdddocOutput.getResultBean().getIddocout() != null ? String.valueOf(lAdddocOutput.getResultBean().getIddocout().longValue()) : null);							
			}
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, bean);			
			getProtocolloDataSource(bean).aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtipotargetin("U");
		input.setIduddocin(new BigDecimal(bean.getIdUd()));
		
		CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
		creaModDocumentoInBean.setCodStatoDett(getExtraparams().get("codStato"));				
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
		
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if(output.isInError()) {
			throw new StoreException(output);
		}
		
		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean recuperaListaEmendamenti(NuovaPropostaAtto2CompletaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		DmpkRepositoryGuiGetlistaemendamentiBean input = new DmpkRepositoryGuiGetlistaemendamentiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlginclsubin(1);
		input.setIdudin(new BigDecimal(bean.getIdUd()));
		
		DmpkRepositoryGuiGetlistaemendamenti lDmpkRepositoryGuiGetlistaemendamenti = new DmpkRepositoryGuiGetlistaemendamenti();
		StoreResultBean<DmpkRepositoryGuiGetlistaemendamentiBean> output = lDmpkRepositoryGuiGetlistaemendamenti.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			throw new StoreException(output);
		}

		String strListaEmendamentiXml = output.getResultBean().getEmendamentiout();
		List<EmendamentoXmlBean> listaEmendamentiXml = new ArrayList<EmendamentoXmlBean>();
		List<EmendamentoBean> listaEmendamenti = new ArrayList<EmendamentoBean>();
		Map<String, List<EmendamentoBean>> subEmendamenti = new HashMap<>();

		if (StringUtils.isNotBlank(strListaEmendamentiXml)) {
			try {
				// Bisogna utilizzare questo o vanno in errore i Flag
				listaEmendamentiXml = XmlListaUtility.recuperaListaWithEnum(strListaEmendamentiXml, EmendamentoXmlBean.class);
//				listaEmendamentiXml = XmlListaUtility.recuperaLista(strListaEmendamentiXml, EmendamentoXmlBean.class);
				if (listaEmendamentiXml != null) {
					for (EmendamentoXmlBean emendamentoXmlBean : listaEmendamentiXml) {
						EmendamentoBean emendamentoBean = populateEmendamentoBeanFromEmendamentoXmlBean(emendamentoXmlBean);
						if (StringUtils.isNotBlank(emendamentoBean.getNroSubEmendamento())) {
							String nroEmendamento = emendamentoBean.getNroEmendamento();
							if (subEmendamenti.containsKey(nroEmendamento)) {
								subEmendamenti.get(nroEmendamento).add(emendamentoBean);
							} else {
								List<EmendamentoBean> listaSubEmendamenti = new ArrayList<>();
								listaSubEmendamenti.add(emendamentoBean);
								subEmendamenti.put(nroEmendamento, listaSubEmendamenti);
							}
						} else {
							listaEmendamenti.add(emendamentoBean);
						}
					}
				}

				for (EmendamentoBean emendamentoBean : listaEmendamenti) {
					emendamentoBean.setListaSubEmendamenti(subEmendamenti.get(emendamentoBean.getNroEmendamento()));
				}

				if (listaEmendamenti != null && !listaEmendamenti.isEmpty()) {
					Collections.sort(listaEmendamenti, EmendamentoBean.EmendamentoNo);
					bean.setListaEmendamenti(listaEmendamenti);
				} else if (subEmendamenti.values().size() == 1){
					for (List<EmendamentoBean> subEmendamentiList : subEmendamenti.values()) {
						Collections.sort(subEmendamentiList, EmendamentoBean.EmendamentoNo);
						bean.setListaEmendamenti(subEmendamentiList);
					}
				}
				bean.setListaEmendamentiBloccoRiordinoAut(output.getResultBean().getFlgbloccoriordautoout() != null && output.getResultBean().getFlgbloccoriordautoout() == 1 ? true : false);
				
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
				
		return bean;
	} 
	
	private EmendamentoBean populateEmendamentoBeanFromEmendamentoXmlBean(EmendamentoXmlBean emendamentoXmlBean) {
		
		EmendamentoBean result = new EmendamentoBean();
		result.setIdUd(emendamentoXmlBean.getIdUd());
		if (StringUtils.isNotBlank(emendamentoXmlBean.getNroEmendamento()) && emendamentoXmlBean.getNroEmendamento().contains("/")) {
			StringSplitterServer ss = new StringSplitterServer(emendamentoXmlBean.getNroEmendamento(), "/");
			String nroEmendamento = ss.getTokens()[0];
			String nroSub = ss.getTokens()[1];
			emendamentoXmlBean.setNroEmendamento(nroEmendamento);
			emendamentoXmlBean.setNroSubEmendamento(nroSub);
		} else if (StringUtils.isNotBlank(emendamentoXmlBean.getNroSubEmendamento()) && emendamentoXmlBean.getNroSubEmendamento().contains("/")) {
			StringSplitterServer ss = new StringSplitterServer(emendamentoXmlBean.getNroSubEmendamento(), "/");
			String nroEmendamento = ss.getTokens()[0];
			String nroSub = ss.getTokens()[1];
			emendamentoXmlBean.setNroEmendamento(nroEmendamento);
			emendamentoXmlBean.setNroSubEmendamento(nroSub);
		}
		result.setIdEmendamento(emendamentoXmlBean.getIdEmendamento());
		result.setNroEmendamento(emendamentoXmlBean.getNroEmendamento());
		result.setNroSubEmendamento(emendamentoXmlBean.getNroSubEmendamento());
		result.setTsCaricamento(emendamentoXmlBean.getTsCaricamento());
		result.setStrutturaProponente(emendamentoXmlBean.getStrutturaProponente());
		result.setCdcStrutturaProponente(emendamentoXmlBean.getCdcStrutturaProponente());
		result.setFirmatari(emendamentoXmlBean.getFirmatari());
		result.setTsPerfezionamento(emendamentoXmlBean.getTsPerfezionamento());
		result.setTipoFileRiferimento(emendamentoXmlBean.getTipoFileRiferimento());
		result.setNroAllegatoRiferimento(emendamentoXmlBean.getNroAllegatoRiferimento() != null ? emendamentoXmlBean.getNroAllegatoRiferimento() + "" : null);
		result.setNroPaginaRiferimento(emendamentoXmlBean.getNroPaginaRiferimento() != null ? emendamentoXmlBean.getNroPaginaRiferimento() + "" : null);
		result.setNroRigaRiferimento(emendamentoXmlBean.getNroRigaRiferimento() != null ? emendamentoXmlBean.getNroRigaRiferimento() + "" : null);
		result.setEffettoEmendamento(emendamentoXmlBean.getEffettoEmendamento());
		result.setEmendamentoIntegrale(emendamentoXmlBean.getEmendamentoIntegrale() != null && emendamentoXmlBean.getEmendamentoIntegrale() == Flag.SETTED);
		result.setTestoHtml(emendamentoXmlBean.getTestoHtml());
		result.setUriFile(emendamentoXmlBean.getUriFile());
		result.setNomeFile(emendamentoXmlBean.getNomeFile());
		result.setFirmato(emendamentoXmlBean.getFirmato() != null && emendamentoXmlBean.getFirmato() == Flag.SETTED);
		result.setMimetype(emendamentoXmlBean.getMimetype());
		result.setConvertibilePdf(emendamentoXmlBean.getConvertibilePdf() != null && emendamentoXmlBean.getConvertibilePdf() == Flag.SETTED);
		result.setPareriEspressi(emendamentoXmlBean.getPareriEspressi());
		result.setFilePareri(emendamentoXmlBean.getFilePareri());
		result.setIdProcess(emendamentoXmlBean.getIdProcess());
		result.setBytes(emendamentoXmlBean.getDimensioneFile() + "");
		result.setOrganoCollegiale("CONSIGLIO");
		return result;
		
	}
	
	public NuovaPropostaAtto2CompletaBean salvaListaEmendamenti(NuovaPropostaAtto2CompletaBean bean) throws StoreException {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setFlgtipotargetin("U");
		input.setIduddocin(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);

		try {
			List<EmendamentoXmlBean> idUDEmendamentiOrdinati = new ArrayList<>();

			for (EmendamentoBean emendamento : bean.getListaEmendamenti()) {
				EmendamentoXmlBean lEmendamento = new EmendamentoXmlBean();
				lEmendamento.setIdUd(emendamento.getIdUd());
				idUDEmendamentiOrdinati.add(lEmendamento);
			}

			CreaModDocumentoInBean pModificaDocumentoInXOrdBean = new CreaModDocumentoInBean();
			pModificaDocumentoInXOrdBean.setIdUDEmendamentiOrdinati(idUDEmendamentiOrdinati);		

			XmlUtilitySerializer lXmlUtilitySerializerXOrd = new XmlUtilitySerializer();
			input.setAttributiuddocxmlin(lXmlUtilitySerializerXOrd.bindXml(pModificaDocumentoInXOrdBean));

			DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean, input);

			if (lUpddocudOutput.isInError()) {
				throw new StoreException(lUpddocudOutput);
			}
			
			return bean;
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		

	}
	
	public NuovaPropostaAtto2CompletaBean approvaEmendamenti(NuovaPropostaAtto2CompletaBean bean) throws StoreException {	
		return bean;
	}
	
	public FileDaFirmareBean getVersIntegraleSenzaFirma(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setNomeFile(bean.getNomeFilePrimario());
		lFileDaFirmareBean.setUri(bean.getUriFilePrimario());
		lFileDaFirmareBean.setInfoFile(bean.getInfoFilePrimario());
		return lFileDaFirmareBean;
	}
		
	public FileDaFirmareBean getLastVersPubblicazioneFirmata(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String idDocIn = null;		
		if(bean.getIdDocPrimarioOmissis() != null && !"".equals(bean.getIdDocPrimarioOmissis())) {
			idDocIn = bean.getIdDocPrimarioOmissis();
		} else if(bean.getIdDocPrimario() != null && !"".equals(bean.getIdDocPrimario())) {			
			idDocIn = bean.getIdDocPrimario();
		}
		
		if(idDocIn != null && !"".equals(idDocIn)) {
			
			Map<String, String> extraparams = super.getExtraparams();
			extraparams.put("idDocIn", idDocIn);			
			
			VisualizzaVersioniFileDataSource lVersioniDatasource = new VisualizzaVersioniFileDataSource();
			lVersioniDatasource.setSession(getSession());
			lVersioniDatasource.setExtraparams(extraparams);	
			if(getMessages() == null) {
				setMessages(new ArrayList<MessageBean>());
			}
			lVersioniDatasource.setMessages(getMessages()); 
			
			PaginatorBean<VisualizzaVersioniFileBean> versioni = lVersioniDatasource.fetch(null, null, null, null);
			
			List<VisualizzaVersioniFileBean> versioniList = versioni.getData();
			if(versioniList != null) {
				List<ComparableVisualizzaVersioniFileBean> sortedVersioniList = new ArrayList<ComparableVisualizzaVersioniFileBean>();
				for (int i = 0; i < versioniList.size(); i++) {
					ComparableVisualizzaVersioniFileBean lComparableVisualizzaVersioniFileBean = new ComparableVisualizzaVersioniFileBean();
					lComparableVisualizzaVersioniFileBean.setNr(versioniList.get(i).getNr());
					lComparableVisualizzaVersioniFileBean.setBean(versioniList.get(i));
					sortedVersioniList.add(lComparableVisualizzaVersioniFileBean);					
				}					
				Collections.sort(sortedVersioniList);
				for (int i = sortedVersioniList.size() - 1; i >= 0; i--) {
					VisualizzaVersioniFileBean currentVers = sortedVersioniList.get(i).getBean();
					if (currentVers.getIconaFirmata() != null && "1".equals(currentVers.getIconaFirmata())) {
						FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
						lFileDaFirmareBean.setNomeFile(currentVers.getNome());
						lFileDaFirmareBean.setUri(currentVers.getUriFile());
						lFileDaFirmareBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(currentVers.getUriFile()).toURI().toString(), currentVers.getNome(), false, null));
						return lFileDaFirmareBean;
					}
				}
			}
		}

		return null;
	}
	
	protected static String getMd5StringFormFile(File lFile) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(new FileInputStream(lFile));
	}
	
	private boolean skipFirmaAllegatiFirmati(String uriFile, MimeTypeFirmaBean lInfoFile) throws Exception {
		
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_ALLEGATI_FIRMATI_PI_ATTO")) {
			if (lInfoFile != null && lInfoFile.isFirmato()) {
				boolean presentiFirmeExtraAuriga = lInfoFile.getInfoFirmaMarca().isFirmeExtraAuriga();
				if (presentiFirmeExtraAuriga) {
					boolean firmaValida = lInfoFile.isFirmaValida();
					if (firmaValida) {
						// Rifaccio un controllo della firma
						File lFile = StorageImplementation.getStorage().extractFile(uriFile);
						firmaValida = FirmaUtility.isSigned(lFile.toURI().toString(), lInfoFile.getCorrectFileName());
					}
					if (firmaValida) {
						// La firma è valida e non sono presenti firme extra Auriga
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private class ComparableVisualizzaVersioniFileBean implements Comparable<ComparableVisualizzaVersioniFileBean> {
		
		private Integer nr;
		private VisualizzaVersioniFileBean bean;
		
		public Integer getNr() {
			return nr;
		}

		public void setNr(Integer nr) {
			this.nr = nr;
		}

		public VisualizzaVersioniFileBean getBean() {
			return bean;
		}

		public void setBean(VisualizzaVersioniFileBean bean) {
			this.bean = bean;
		}

		@Override
		public int compareTo(ComparableVisualizzaVersioniFileBean o) {
			Integer nr1 = getNr() != null ? getNr() : 0;
			Integer nr2 = o != null && o.getNr() != null ? o.getNr() : 0;
			return nr1.compareTo(nr2);			
		}
		
	}
	
	public boolean isClienteComuneMilano() {
		return ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(getSession(), "CLIENTE").equalsIgnoreCase("CMMI");
	}
	
	public String getFLG_SI_SENZA_VLD_RIL_IMP() {
		if(isClienteComuneMilano()) {
			return "SI, senza validazione/rilascio impegni";
		}
		return "SI, ma senza movimenti contabili";	
	}
	
	public HashSet<String> buildSetAttributiCustomCablati(NuovaPropostaAtto2CompletaBean bean) {
		HashSet<String> setAttributiCustomCablati = new HashSet<String>();		
		if(bean.getParametriTipoAtto() != null && bean.getParametriTipoAtto().getAttributiCustomCablati() != null && !bean.getParametriTipoAtto().getAttributiCustomCablati().isEmpty()) {
			for(int i = 0; i < bean.getParametriTipoAtto().getAttributiCustomCablati().size(); i++) {
				setAttributiCustomCablati.add(bean.getParametriTipoAtto().getAttributiCustomCablati().get(i).getAttrName());
			}
		}
		return setAttributiCustomCablati;
	}

	public boolean isPresenteAttributoCustomCablato(HashSet<String> setAttributiCustomCablati, String nomeAttributo) {
		return setAttributiCustomCablati != null && setAttributiCustomCablati.contains(nomeAttributo);
	}
	
	public boolean showAttributoCustomCablato(HashSet<String> setAttributiCustomCablati, String nomeAttributo) {
		return setAttributiCustomCablati == null || isPresenteAttributoCustomCablato(setAttributiCustomCablati, nomeAttributo);
	}
	
}