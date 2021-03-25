package it.eng.auriga.repository2.jaxws.webservices.extractone;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsExtractfileudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Extractfileud;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.RecuperoFile;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;

/**
 * @author Ottavio passalacqua
 */


@WebService(targetNamespace = "http://extractone.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.extractone.WSIExtractOne", name = "WSExtractOne")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSExtractOne extends JAXWSAbstractAurigaService implements WSIExtractOne{	

	private final String K_SAVEPOINTNAME = "INIZIOWSEXTRACTONE";

	static Logger aLogger = Logger.getLogger(WSExtractOne.class.getName());    

	public WSExtractOne() {
		super();

	}

	/**
	 * <code>serviceImplementation</code> biz logik del webservice.
	 *
	 * @param user a <code>String</code>
	 * @param token a <code>String</code>
	 * @param codAppl a <code>String</code>
	 * @param conn a <code>Connection</code>
	 * @param xmlDomDoc a <code>Document</code>
	 * @param xml a <code>String</code>
	 * @param istanzaAppl a <code>String</code>
	 * @return a <code>String</code>
	 * @exception Exception
	 */
	@WebMethod(exclude=true)
	public final String serviceImplementation(final String user,
			final String token,
			final String codiceApplicazione,
			final String istanzaAppl,
			final Connection conn,
			final Document xmlDomDoc,
			final String xml,
			final String schemaDb,
			final String idDominio,
			final String desDominio,
			final String tipoDominio) throws Exception {


		String risposta = null;
		String outRispostaWS = null;
		WSExtractOneBean outServizio = new WSExtractOneBean();

		String errMsg = null;
		String xmlIn = null;

		try {


			aLogger.info("Inizio WSExtractOne");

			//setto il savepoint
			DBHelperSavePoint.SetSavepoint(conn, K_SAVEPOINTNAME);

			// creo bean connessione
			AurigaLoginBean loginBean = new AurigaLoginBean();         
			loginBean.setToken(token);
			loginBean.setCodApplicazione(codiceApplicazione);
			loginBean.setIdApplicazione(istanzaAppl);
			loginBean.setSchema(schemaDb);  

			SpecializzazioneBean lspecializzazioneBean = new SpecializzazioneBean();
			lspecializzazioneBean.setCodIdConnectionToken(token);  		
			if (idDominio!=null && !idDominio.equalsIgnoreCase(""))
				lspecializzazioneBean.setIdDominio(new BigDecimal(idDominio));

			if (tipoDominio!=null && !tipoDominio.equalsIgnoreCase(""))
				lspecializzazioneBean.setTipoDominio(new Integer(tipoDominio));

			loginBean.setSpecializzazioneBean(lspecializzazioneBean);

			/*************************************************************
			 * Chiamo il WS e il servizio di AurigaDocument
			 ************************************************************/ 
			WSExtractOneBean outWS = new WSExtractOneBean();
			try {
				// Chiamo il WS        	 
				outWS =  callWS(loginBean,xml);

				// Chiamo il servizio di AurigaDocument
				outServizio =  eseguiServizio(loginBean,outWS); 	


			}
			catch (Exception e){	 
				if(e.getMessage()!=null)
					errMsg = "Errore = " + e.getMessage();
				else
					errMsg = "Errore imprevisto.";	 			
			}

			if (errMsg==null){
				xmlIn = outServizio.getXml();	
			}
			else{
				xmlIn = errMsg;
			}

			/**************************************************************************
			 * Creo XML di risposta del servzio e lo metto in attach alla response
			 **************************************************************************/
			try {
				// Creo XML di risposta
				outRispostaWS = generaXMLRispostaWS(xmlIn);

				// Creo la lista di attach
				List<InputStream> lListInputStreams = new ArrayList<InputStream>();
				List<String> lListInputStreamsTypes = new ArrayList<String>();

				// Converto l'XML
				ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());

				// Aggiungo l'XML
				lListInputStreams.add(inputStreamXml);
				lListInputStreamsTypes.add("application/xml");



				// Converto il FILE ATTACH
				if(outServizio!=null && outServizio.getExtracted() != null){
					//InputStream inputStreamFile = new FileInputStream(outServizio.getExtracted()); 


					ByteArrayInputStream bais = new ByteArrayInputStream(FileUtils.readFileToByteArray(outServizio.getExtracted()));

					// Aggiungo il FILE
					lListInputStreams.add(bais);										  		  


					String getSbustato = "0";
					String getPdf = "0";
					try {
						Document doc = convertStringToXMLDocument(xml);
						try {
							getSbustato = doc.getElementsByTagName("GetSbustato").item(0).getFirstChild().getNodeValue();
						} catch (Exception e1) {
							getSbustato = "0";
						}
						try {
							getPdf = doc.getElementsByTagName("GetPdf").item(0).getFirstChild().getNodeValue();
						} catch (Exception e2) {
							getPdf = "0";
						}
					} catch (Exception e) {}
					
					
					
					if (getSbustato.equals("1") || getPdf.equals("1")) {
						Tika tika = new Tika();
	
						InputStream lInputStream;
						InfoFileUtility lInfoFileUtility = new InfoFileUtility();		  			
						File p7m = outServizio.getExtracted();
						// FULVIO: se non è un xml, ossia è un p7m, lo sbusto
						boolean bustato = !tika.detect(outServizio.getExtracted()).equals("application/xml"); 
						if (bustato)
							lListInputStreamsTypes.add("application/octet-stream");
						else
							lListInputStreamsTypes.add("application/xml");

						if (bustato && getSbustato.equals("1")) {
							lInputStream = lInfoFileUtility.sbusta(p7m, p7m.getName());
	
							byte[] buff = new byte[1024];
							int bytesRead = 0;
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							while((bytesRead = lInputStream.read(buff)) != -1) {
								baos.write(buff, 0, bytesRead);
							}
							byte[] data = baos.toByteArray();
	
							ByteArrayInputStream bin = new ByteArrayInputStream(data);		            	
	
							lListInputStreams.add(bin);
							lListInputStreamsTypes.add("application/xml");
	
	
						}
						
						if (getPdf.equals("1")) {
							if (bustato)
								lInputStream = lInfoFileUtility.sbusta(p7m, p7m.getName());
							else
								lInputStream = new FileInputStream(p7m);
							File pdf = creaPDFOrdine(lInputStream);
							InputStream inputStreamPdf = new FileInputStream(pdf); 
							lListInputStreams.add(inputStreamPdf);
							lListInputStreamsTypes.add("application/pdf");
						}
					}



				}



				// Salvo gli ATTACH alla response
				attachListInputStreamTypes(lListInputStreams,lListInputStreamsTypes);
			}
			catch (Exception e){
				if(e.getMessage()!=null)
					errMsg = "Errore = " + e.getMessage();
				else
					errMsg = "Errore imprevisto.";	
			}   

			/*************************************************************
			 * Restituisco XML di risposta del WS
			 ************************************************************/	
			if (errMsg==null){
				risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", "");
			}
			else{
				risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  errMsg, "", "");
			}

			aLogger.info("Fine WSExtractOne");

			return risposta;
		}

		catch (Exception excptn) {
			aLogger.error("WSExtractOne: " + excptn.getMessage(), excptn);
			return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
			//throw excptn;
		}
		finally
		{
			try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
			aLogger.info("Fine WSExtractOne serviceImplementation");
		}

	}


	private WSExtractOneBean eseguiServizio(AurigaLoginBean loginBean, WSExtractOneBean bean) throws Exception {
		aLogger.debug("Eseguo il servizio di AurigaDocument.");

		WSExtractOneBean ret = new WSExtractOneBean();
		File fileOut = null;

		// creo l'input
		BigDecimal idDocIn       = (bean.getIdDoc() != null) ? new BigDecimal(bean.getIdDoc()) : null;	    		
		BigDecimal nroProgrVerIn = (bean.getNroProgrVer() != null) ? new BigDecimal(bean.getNroProgrVer()) : null;


		// eseguo il servizio
		try {
			//FileExtractedIn pFileExtractedIn = new FileExtractedIn();

			RecuperoFile servizio = new RecuperoFile();
			FileExtractedOut servizioOut = new FileExtractedOut();
			servizioOut = servizio.extractFileByIdDoc(loginBean, idDocIn, nroProgrVerIn);	    

			// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
			if(StringUtils.isNotBlank(servizioOut.getErrorInExtract())) {
				throw new Exception(servizioOut.getErrorInExtract());
			}
			// Altrimenti restituisco il FILE
			fileOut =  servizioOut.getExtracted();

			ret.setIdDoc(bean.getIdDoc());
			ret.setNroProgrVer(bean.getNroProgrVer());
			ret.setXml(bean.getXml());
			ret.setExtracted(fileOut);

		}
		catch (Exception e){
			throw new Exception(e.getMessage());	
		}

		return ret;

	}

	private WSExtractOneBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {

		aLogger.debug("Eseguo il WS DmpkWsExtractOne.");

		String idDoc       = null;
		String nroProgrVer = null;
		String xml         = null;    	
		try {    		
			// Inizializzo l'INPUT    		
			DmpkWsExtractfileudBean input = new DmpkWsExtractfileudBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setXmlin(xmlIn);

			// Eseguo il servizio
			Extractfileud service = new Extractfileud();
			StoreResultBean<DmpkWsExtractfileudBean> output = service.execute(loginBean, input);

			if (output.isInError()){
				throw new Exception(output.getDefaultMessage());	
			}	

			// restituisco l'XML
			if(output.getResultBean().getXmlout()!=null){
				xml = output.getResultBean().getXmlout();  
			}
			if (xml== null || xml.equalsIgnoreCase(""))
				throw new Exception("La store procedure ha ritornato XmlOut nullo");

			// restituisco l'ID DOC
			if (output.getResultBean().getIddoctoextractout() != null){
				idDoc = output.getResultBean().getIddoctoextractout().toString();  
			}
			if (idDoc== null || idDoc.equalsIgnoreCase("")){
				throw new Exception("La store procedure ExtractFileUD ha ritornato id doc nullo");
			}

			// restituisco il nro versione
			if (output.getResultBean().getNrovertoextractout()!= null){
				nroProgrVer = output.getResultBean().getNrovertoextractout().toString();
			}
			if (nroProgrVer == null) {
				throw new Exception("La store procedure ExtractFileUD ha ritornato un nro versione nullo");
			}

			// popolo il bean di out
			WSExtractOneBean result = new WSExtractOneBean();
			result.setXml(xml);
			result.setIdDoc(idDoc);
			result.setNroProgrVer(nroProgrVer);


			return result;
		}
		catch (Exception e){
			throw new Exception(e.getMessage()); 			
		}
	}




	/**
	 * Genera il file XML contenente l'id del folder aggiunto
	 * Questo file viene passato come allegato in caso di successo.
	 *
	 * @param String idFolder
	 * @return String stringa XML secondo il formato per il ritorno dell'idFolder
	 */
	private String generaXMLRispostaWS(String xmlIn)  throws Exception {

		StringBuffer xml = new StringBuffer();
		String xmlInEsc = null;

		try {
			// ...se il token non e' null
			if (xmlIn != null) {
				// effettuo l'escape di tutti i caratteri
				xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
			}
			//xmlInEsc = xmlIn;
			xml.append(xmlInEsc);
			aLogger.debug(xml.toString());
		}
		catch (Exception e){
			throw new Exception(e.getMessage());
		}        
		return xml.toString();
	}









	public File creaPDFOrdine(InputStream uriFileXmlIn) throws Exception{
		File pdfFileOut = null;
		try {			
			InputStream fileXslIn = getClass().getResourceAsStream("/fattura_tabellare_v1.2.xsl"); 	
			TransformerFactory transformer = new TransformerFactoryImpl();
			StreamSource source = new StreamSource(fileXslIn);
			Templates xslTemplate = transformer.newTemplates(source);
			Transformer transform = xslTemplate.newTransformer();
			transform.setOutputProperty(OutputKeys.METHOD, "xml"); 
			transform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transform.setOutputProperty(OutputKeys.INDENT, "yes"); 

			String fileName = "temp";
			File htmlFileOut = File.createTempFile(fileName, ".html");
			pdfFileOut = File.createTempFile(fileName, ".pdf");

			transform.transform(new StreamSource(uriFileXmlIn), new StreamResult(new FileOutputStream(htmlFileOut)));

			generatePDFOrdine(htmlFileOut.getCanonicalPath(), pdfFileOut, PD4Constants.A4, null, null);

		} catch (Exception e) {
		}
		return pdfFileOut;
	}




	public File creaPDFOrdine(String uriFileXmlIn, String nomeFileStyleSheet) throws Exception{
		String fileName = "";
		File pdfFileOut = null;
		try {			
			File fileXslIn = new File(nomeFileStyleSheet);	
			TransformerFactory transformer = new TransformerFactoryImpl();
			StreamSource source = new StreamSource(fileXslIn);
			Templates xslTemplate = transformer.newTemplates(source);
			Transformer transform = xslTemplate.newTransformer();
			transform.setOutputProperty(OutputKeys.METHOD, "xml"); 
			transform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transform.setOutputProperty(OutputKeys.INDENT, "yes"); 

			fileName = org.apache.commons.io.FilenameUtils.removeExtension(uriFileXmlIn);
			File htmlFileOut = new File(fileName+".html");
			pdfFileOut = new File(fileName+".pdf");

			transform.transform(new StreamSource(uriFileXmlIn), new StreamResult(new FileOutputStream(htmlFileOut)));

			generatePDFOrdine(htmlFileOut.getCanonicalPath(), pdfFileOut, PD4Constants.A4, null, null);

		} catch (Exception e) {
		}
		return pdfFileOut;
	}

	public File generatePDFOrdine(String inputHTMLFileName, File outputPDFFile, Dimension format, String fontsDir, String headerBody)
			throws Exception {

		FileOutputStream fos = new FileOutputStream(outputPDFFile);
		PD4ML pd4ml = new PD4ML();
		pd4ml.setHtmlWidth(1024); //default 640
		pd4ml.addStyle("TABLE,DIV {page-break-inside: auto !important}", true);
		pd4ml.enableTableBreaks(true);

		if ( fontsDir != null && fontsDir.length() > 0 ) {
			pd4ml.useTTF( fontsDir, true );
		}
		if ( headerBody != null && headerBody.length() > 0 ) {
			PD4PageMark header = new PD4PageMark();
			header.setAreaHeight( -1 );
			header.setHtmlTemplate( headerBody );
			pd4ml.setPageHeader( header );
		}
		pd4ml.enableDebugInfo();
		pd4ml.render("file:" + inputHTMLFileName, fos);
		return outputPDFFile;
	}

	private static Document convertStringToXMLDocument(String xmlString) {
		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try
		{
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			//Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}

