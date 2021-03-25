package it.eng.auriga.repository2.jaxws.webservices.updunitadoc;

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsUpdudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Updud;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.auriga.repository2.jaxws.webservices.util.WSAttachBean;
import it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.*;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.activation.DataHandler;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import org.apache.log4j.Logger;
import org.exolab.castor.xml.Unmarshaller;
import org.w3c.dom.Document;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.document.function.GestioneDocumenti;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DelUdDocVerIn;
import it.eng.document.function.bean.DelUdDocVerOut;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ottavio passalacqua
 */


@WebService(targetNamespace = "http://updunitadoc.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.updunitadoc.WSIUpdUd", name = "WSUpdUd")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSUpdUd extends JAXWSAbstractAurigaService implements WSIUpdUd{	

    private final String K_SAVEPOINTNAME = "INIZIOWSUPDUD";
 
    private final String K_AGGIORN_VERS = "AV";
    private final String K_MODIF_METADATA = "MM";
    private final String K_DELETE_VERS = "DV";
    private final String K_DELETE_DOC = "DD";
    private final String K_SOST_VERS = "SV"; //Sostituzione di versione
    private final String K_MODIF_MD_VERS = "MV"; //Modifica metadati di versione
    
    static Logger aLogger = Logger.getLogger(WSUpdUd.class.getName());    
    
    public WSUpdUd() {
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

    String risposta        = null;    
    String outRispostaWS   = null;
    String warnRegIn       = "";
    String docStoreFallita = "";
    String errMsg = null;
    String xmlIn = null;
    
    try {

    	 aLogger.info("Inizio WSUpdUd");
    	
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
         
         WSUpdUdBean outWS = new WSUpdUdBean();
         
         WSUpdUdOutBean outServizio = new WSUpdUdOutBean();
         
         WSAttachBean wsAttach = new WSAttachBean();
         
         String errori = "";
         
         try {
        	 
        	 	// Leggo gli attach e le info 
     	 		wsAttach = getAttachment(loginBean);
     	    
     	 		// Cpntrollo se il mimetype degli attach e' valido
     	 		errori = checkMimeTypeAttach(wsAttach);
     	 	
     	 		// se ho trovato errori ritorno errore
     	 		if (!errori.equals("")) {
     	 			aLogger.error("Rilevati i seguenti errori: " + errori);
     	 			throw new Exception(errori);	        		
     	 		}
     	 		
        	 	// Chiamo il WS
        	 	outWS =  callWS(loginBean,xml);
        	 	
        	 	// Chiamo il servizio di AurigaDocument
        	 	outServizio =  eseguiServizio(loginBean, conn, outWS, wsAttach);        	 	
	 		}
	 	catch (Exception e){	
	 		if(e.getMessage()!=null)
	 			 errMsg = "Errore = " + e.getMessage();
	 		 else
	 			errMsg = "Errore imprevisto.";
	 		}
	 	
	 	if (errMsg==null){
	 		xmlIn     = outServizio.getXmlRegOut();	
	 		warnRegIn = outServizio.getWarnRegOut();
	 	}
	 	else{
 	 		xmlIn = errMsg;
	 	}
	 	                 
	 	/**************************************************************************
		 * Creo XML di risposta del servzio e lo metto in attach alla response
		 **************************************************************************/
	 	try {
	 		 	  // Creo XML di risposta
	 			  outRispostaWS = generaXMLRispostaWS(xmlIn, warnRegIn);
	 			
	 			  // Creo la lista di attach
		  		  List<InputStream> lListInputStreams = new ArrayList<InputStream>();
		  		  
	 			  // Converto l'XML
		 		  ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());
		 		 
		 		  // Aggiungo l'XML
		  		  lListInputStreams.add(inputStreamXml); 
		  		  
		  		  // Salvo gli ATTACH alla response
		  		  attachListInputStream(lListInputStreams); 
	 	     }
	 	 catch (Exception e){
	 			return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  e.getMessage(), "", "");
	 		 }   
        	
	     
	 	 /*************************************************************
		  * Restituisco XML di risposta del WS
		  ************************************************************/	
	 	 
	 	 if (errMsg==null){
		 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", warnRegIn);
		 }
		 else{
		 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  errMsg, "", "");
		 }
            	
	     aLogger.info("Fine WSUpdUd");
	    
	     return risposta;
    }
  
   
    catch (Exception excptn) {
        aLogger.error("WSUpdUd: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSUpdUd serviceImplementation");
	}
    }

    
    /****************************************************************************************************************
	* Prende le info dall'xml e versiona i file elettronici ( primario + allegati ).
	* L'xml restituisce  : 
	*    -- 1: Valore fisso AV: indica il tipo di operazione da fare sul documento corrispondente all'attach
	*    -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
	* 	 -- 3: Identificativo del documento creato in DB in corrispondenza dell'attachment
	* 	 -- 4: Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
	* 	 -- 5: (valori 1/0) Se 1 indica che la versione e' pubblicata, se 0 no
	* 	 -- 6: (valori 1/0) Se 1 indica che la versione deriva da scansione, se 0 no
	* 	 -- 7: Nr. che identifica la versione (alfanumerico, opzionale)
	* 	 -- 8: Note della versione
	* 	 -- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR 
	**************************************************************************************************************/
    
    private WSUpdUdOutBean eseguiServizio(AurigaLoginBean loginBean,  Connection conn,  WSUpdUdBean bean, WSAttachBean wsAttachEinfo) throws Exception {
    	
   	
    	String xmlOut                  = null;
    	String docAttXmlOut            = null;
    	Output_UD listaFileElettroniciNonSalvatiOut             = null;
    	    	
    	// popolo il bean di out
    	WSUpdUdOutBean  ret = new WSUpdUdOutBean();
		
    	
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	try 
    	  {
    		// Leggo input
        	xmlOut                     = bean.getXml();   	
        	docAttXmlOut               = bean.getOperVsDocXML();  
        	        	
        	// Estraggol'IDUD dall'XML
      		StringReader  srXmlOut = new StringReader(xmlOut);  	
      		listaFileElettroniciNonSalvatiOut = (Output_UD) Unmarshaller.unmarshal(Output_UD.class, srXmlOut);
      
      		// Estraggo le info dei file elettronici dall'XML
      		Lista lsDocAttXmlOut =  null;
      		if ( docAttXmlOut != null) {
            	StringReader srDocAttXmlOut = new StringReader(docAttXmlOut);        	
            	lsDocAttXmlOut = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srDocAttXmlOut);
            }
      		
      	    // Processo i file elettronici
            if ( lsDocAttXmlOut != null && lsDocAttXmlOut.getRiga().size() > 0) {
                        	
            	String errori = "";
            	
            	// Prendo gli attach
        		DataHandler[] attachments =  wsAttachEinfo.getAttachments();
        		
        		// Prendo la lista delle info degli attach
        		List<RebuildedFile> listRebuildedFile   = wsAttachEinfo.getListRebuildedFile();
        		
            	/*************************************************************
            	 * Controlli sulla lista_std:
            	 *    - tutti gli allegati esistono e il nome e' corrispondente
            	 *    - tutti gli attach fisici hanno una corrispondenza nella lista
            	 *    - non vi sono attach ripetuti nella lista 
            	 *************************************************************/
            	errori = verificaAllegati_local(attachments, lsDocAttXmlOut);
            	
            	// Se ho trovato errori ritorno errore
            	if (!errori.equals("")) {
            		aLogger.error("Rilevati i seguenti errori: " + errori);
            		throw new Exception(errori);	        		
            	}

            	/*************************************************************
            	 * Salvo i file allegati
            	 *************************************************************/
            	errori = salvaAllegati(loginBean, conn, listRebuildedFile, attachments, lsDocAttXmlOut, listaFileElettroniciNonSalvatiOut);
                                
            	// Se il salvataggio e' fallito restituisco l'elenco dei file non salvati
                if (errori!= null && !errori.trim().equalsIgnoreCase("")){
                	if (listaFileElettroniciNonSalvatiOut != null) {
                		StringBuffer lStringBuffer = new StringBuffer();
                		if (listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata()!=null && listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata().length >0){
                			for (int i = 0; i < listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata().length; i++) {
                				String lStrFileInError  = listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata(i).getNomeFile();
                				lStringBuffer.append(lStrFileInError + "; ");	
                			}
                			String warnRegOut  = lStringBuffer.toString();
                			ret.setWarnRegOut(warnRegOut);
                			aLogger.debug(warnRegOut);
                		}
                		else{
                    		ret.setWarnRegOut(errori);
                			aLogger.debug(errori);
                    	}
             	   }
                	
                		
                }
           }
     	                    
     	   // Restituisco l'xml
     	   ret.setXmlRegOut(xmlOut);
     	  
    	}    	
    	catch (Exception e){
    		throw new Exception(e.getMessage());	
    	}               
	 	return ret;    	
    }
    
  
    /****************************************************************************************************************
	* Funzione per aggiornare un'unità documentaria.
	* INPUT : 
	*     -  XMLIn : xml con i metadati da ggiornare
	* OUTPUT :     
	*   a) OperVsDocXMLOut : xml con le informazioni da aggiornare sui file elettronoci  : 
	*       -- 1: Tipo di operazione da fare sul documento; valori possibili:
	*			    --		AV = Step-version, ovvero aggiunta di nuova versione elettronica (anche la prima)
	*				--		MM = Modifica dei metadati del documento
	*				--		DV = Cancellazione dell'ultima versione elettronica valida e visibile all'utente
	*				--		DD = Cancellazione del documento
	*				--		SV = Sostituzione di versione
	*				--		MV = Modifica metadati di versione
	*		-- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository (valorizzato solo se il tipo di operazione è AV o SV)
	*		-- 3: Identificativo del documento
	*		-- 4: Nome del file della versione (valorizzato solo se il tipo di operazione è AV o SV o MV); è il nome con cui mostrarlo e dalla cui estensione si ricava il formato)
	*		-- 5: (valori 1/0) Se 1 indica che è la versione è pubblicata, se 0 no (valorizzato solo se il tipo di operazione è AV o SV o MV)
	*		-- 6: (valori 1/0) Se 1 indica che è la versione deriva da scansione, se 0 no (valorizzato solo se il tipo di operazione è AV o SV o MV)
	*		-- 7: Nr. che identifica la versione (opzionale) (valorizzato solo se il tipo di operazione è AV o SV o MV)
	*		-- 8: Note della versione (valorizzato solo se il tipo di operazione è AV o SV o MV)
	*		-- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR
	*
	*   b) RegNumDaRichASistEstOut : Lista con gli estremi delle registrazioni/numerazioni richieste da farsi dare da sistemi esterni (XML conforme a schema LISTA_STD.xsd) o da AURIGA stesso tramite API di RegistraUnitaDoc
	*       -- Per ogni registrazioni/numerazioni vi è un tag Riga che contiene le seguenti colonne:
	*		-- 1: Codice che indica la categoria di registrazione/numerazione (PG = Protocollo Generale, ecc)
	*		-- 2: Sigla che indica il registro di registrazione/numerazione
	*		-- 3: Anno di registrazione/numerazione se diverso da quello corrente
	*		
	*   c) XmlOut : XML con dati di output specifici del WS, restituito solo in caso di successo, conforme a schema Output_UD.xsd
	*   
	**************************************************************************************************************/
        
    private WSUpdUdBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DmpkWSUpdUd.");
    	
    	String xml = null;
    	String operVsDocXML         = null;
    	String regNumDaRichASistEst =  null;
    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsUpdudBean input = new DmpkWsUpdudBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  Updud service = new Updud();
    		  StoreResultBean<DmpkWsUpdudBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // leggo  XmlOut
    		  xml = output.getResultBean().getXmlout();
    		  
    		  if (xml== null || xml.equalsIgnoreCase(""))
    			  throw new Exception("La store procedure ha ritornato XmlOut nullo");
    		      		  
    		  // leggo operVsDocXML
    		  operVsDocXML = output.getResultBean().getOpervsdocxmlout();
    		      		  
    		  // leggo regNumDaRichASistEst
    		  regNumDaRichASistEst = output.getResultBean().getRegnumdarichasistestout();
    		  
    	      // popolo il bean di out
    		  WSUpdUdBean  result = new WSUpdUdBean();
    		  
    		  result.setOperVsDocXML(operVsDocXML);
    		  result.setRegNumDaRichASistEst(regNumDaRichASistEst);
    		  result.setXml(xml);
    			  
    		  return result;
 			}
 		catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
    }
    
	
	/**
     * Genera il file XML di risposta del servizio
     * Questo file viene passato come allegato in caso di successo.
     * @param xmlIn       Contiene xml restutuito dal servizio
     * @param warnMessage Puo' essere valorizzato con eventuali warning
     * @return String stringa XML secondo il formato per il ritorno dell'idud
     */
    private String generaXMLRispostaWS(String xmlIn, String warnMessage)  throws Exception {
    
        StringBuffer xml = new StringBuffer();
        String xmlInEsc = null;
        
        try {
        	// ...se il token non e' null
            if (xmlIn != null) {
            	// effettuo l'escape di tutti i caratteri
            	xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
            }
            xml.append(xmlInEsc);
            if (warnMessage != null && !warnMessage.equals("")) {
            	xml.append("<WarningMessage>" + warnMessage + "</WarningMessage>\n");
            }
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }

    public static String getContentColonnaNro(Riga r, int nro) {
		
		if (r == null)
			return null;
		
		for (int i = 0; i < r.getColonna().size(); i++) {
			Colonna c = r.getColonna().get(i);
			if (c!=null && c.getNro().intValue() == nro) {
				return c.getContent();
			}
		}
		
		return null;
	}
    

    // Controllo se le informazioni degli allagti sono corrette
    private String verificaAllegati_local(DataHandler[] attachments, Lista lsDocAttXmlOut) throws Exception 
    {
    	String errori ="";    	
    	String fileName = "";
    	
    	boolean allegatoPresenteInOutput[] = new boolean[attachments.length];
    	    	
    	try {
			           for (int i = 0; i < lsDocAttXmlOut.getRiga().size(); i++) 
					   {
		    				// prendo la riga i-esima
		    				Riga r = lsDocAttXmlOut.getRiga().get(i);
		    				String attachNum = "";
		    				String operazione= "";        		
		    				for(int j = 0; j < r.getColonna().size(); j++) {
		    					int num = 0;            			
		    					Colonna c = r.getColonna().get(j);
		    					String val = c.getContent();
		    					if ( c!=null )
		    						num = c.getNro().intValue();
						
		    					// -- 1: operazione da fare sul documento
		    					if (num == 1) {	            	
		    						operazione= val;
		    					}	
		                        // -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
		    					else if (num == 2) {	            	
		    						attachNum = val;
		    					}	
		    				}      

		                    if (operazione.equals(K_AGGIORN_VERS) || operazione.equals(K_SOST_VERS)) 
		                       {		  		        	
		    				        // prendo l'indice dell'attachment
		    				        Integer indAtt = null;

		         				    // indice in formato int
		         				    int index; 
		     
		         				    //provo a convertire in int l'indice estratto
		         	     			try {
		         					     indAtt = new java.lang.Integer(attachNum);
		    	     				     index = indAtt.intValue();
		    				        } 
									catch (Throwable ee) { 
		    					                aLogger.error("Errore nella conversione dell'indice dell'allegato");            				
		    					                errori += "Errore nella conversione dell'indice dell'allegato\n";
		    					                continue;
		    				        }

		    				        // prendo l'attach indicato dalla store procedure
		    				        try {            			
		    					           fileName = attachments[index-1].getName();			// -- 4: Nome del file da caricare
		    				        }    		
		    				            // se l'attach e' sbagliato considero il documento come non caricato e passo oltre
		    				        catch (Exception e) 
									{
		    					         aLogger.error("Non esiste un allegato all'indice " + index);
		         					     errori += "Non esiste un allegato all'indice " + index + "\n";
		         					     continue;
		    	     			    }

		    				        if (allegatoPresenteInOutput[index-1]) {
		    					         aLogger.error("Allegato all'indice " + index + " duplicato nell'output della store" );
		    					         errori += "Allegato all'indice " + index + " duplicato nell'output della store\n";
		    					         continue;
		    				        }
		    				        // tengo traccia del fatto che l'allegato e' considerato nell'ouput della store
		    				        allegatoPresenteInOutput[index-1] = true;
		    			          }
		    	        }
		    	
		    			// controllo che tutti gli attach con file fisico siano stati considerati
		    			for (int i = 0; i < allegatoPresenteInOutput.length; i++) {
		    				if (!allegatoPresenteInOutput[i]) {
		    					// allegato i-esimo non considerato: ha un file fisico?
		    					fileName = attachments[i].getName();
		    					// controllo il nome del file estratto
		    					if (fileName != null && !fileName.equals("")) {
		    						// file fisico presente --> errore
		    						aLogger.error("Allegato all'indice " + i + " non considerato" );
		    						errori += "Allegato all'indice " + i + " non considerato\n";
		    					}
		    				}
		    			}		    						
    	}
    	 catch (Throwable ee) { 
 			errori += "Errore nella funzione nella verificaAllegati() - " + ee.getMessage() + "\n";
 			aLogger.error(errori);
 			throw new Exception(errori);	 
 		}	
    	
    	return errori;    	
    }
    
    // Salvo gli allegati
    private String salvaAllegati(AurigaLoginBean loginBean , Connection conn, List<RebuildedFile> listRebuildedFile , DataHandler[] attachments, Lista lsDocAttXmlOut, Output_UD listaFileElettroniciNonSalvatiOut) throws Exception
    {
    	String returnMess ="";
    	try {
    			AllegatiBean lAllegatiBean = new AllegatiBean();
    		
    			//booleano per sapere se e' gia' fallita una step
    			boolean unaStepGiaFallita = false;

    			//Array per le Versioni elettroniche non caricate
    			List versioniNonCaricate = new ArrayList();
        	
    			String docId = "";
    			BigDecimal idDocPrimario = null;
    			String idDocFile         = "";
    			String flgTipoFile       = "";    			
    			String attachNum         = "";				
				String nome              = ""; 
				String flgPubblicata     = "";
				String flgDaScansione    = "";
				String nroVersione       = "";
				String note              = "";
				String flgRichOCR        = ""; 
    			String operazione        = "";
    			String nroVersioneFile   = "";
    			Long idUdInt          	 = new Long(0);
    			String  idUd             = "";
    	    	
    			// ciclo sulla lista per caricare i bean  lFilePrimarioBean ,lAllegatiBean
    			for (int i = 0; i < lsDocAttXmlOut.getRiga().size(); i++) {
    				// prendo la riga i-esima        		
    				Riga r = lsDocAttXmlOut.getRiga().get(i);
        		
    				attachNum      = "";				
    				nome           = ""; 
    				flgPubblicata  = "";
    				flgDaScansione = "";
    				nroVersione    = "";
    				note           = "";
    				flgRichOCR     = ""; 
    				operazione     = "";
    				
    				for(int j = 0; j < r.getColonna().size(); j++) {
    					int num = 0;            			
    					Colonna c = r.getColonna().get(j);
    					String val = c.getContent();
    					if ( c!=null )
    						num = c.getNro().intValue();
										
    					// -- 1: operazione da eseguire								
    					if (num == 1) {
        					operazione = val;
    					}					
    					// -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
    					else if (num == 2) {	            	
    						attachNum = val;        
    					}		
    					// -- 3: Identificativo del documento creato in DB in corrispondenza dell'attachment
    					else if (num == 3) {
    						docId = val;
    					}				
    					// -- 4: Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
    					else if (num == 4) {					
    						nome = val;
    					}
    					// -- 5: (valori 1/0) Se 1 indica che la versione e' pubblicata, se 0 no
    					else if (num == 5) {					
    						flgPubblicata = val;
    					}
    					// -- 6: (valori 1/0) Se 1 indica che la versione deriva da scansione, se 0 no
    					else if (num == 6) {					
    						flgDaScansione = val;
    					}
    					// -- 7: nr. che identifica la versione (alfanumerico, opzionale)
    					else if (num == 7) {					
    						nroVersione = val;
    					}
    					// -- 8: Note della versione
    					else if (num == 8) {					
    						note = val;
    					}
    					// -- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR 
    					else if (num == 9) {					
    						flgRichOCR = val; 
    					}

    					// resetto se operazione <> K_AGGIORN_VERS,K_SOST_VERS
    					if( !operazione.equals(K_AGGIORN_VERS) && !operazione.equals(K_SOST_VERS) )
    					{
    						attachNum = "";					  
    					}
					
    					// resetto se operazione <> K_AGGIORN_VERS,K_SOST_VERS, K_MODIF_MD_VERS
    					if (!operazione.equals(K_AGGIORN_VERS) && !operazione.equals(K_SOST_VERS) && !operazione.equals(K_MODIF_MD_VERS)) {
    						nome           = "";
    						flgPubblicata  = "";
    						flgDaScansione = "";
    						nroVersione    = "";
    						note           = "";
    						flgRichOCR     = "";
    					}
    					
    					// Se l'operazione e' 'MM' oppure 'MV'
    					// non faccio nulla perche' le modifiche sono state fatte dal servizio Dmpk_Ws->Updud
    					if( operazione.equals(K_MODIF_METADATA)   ||  operazione.equals(K_MODIF_MD_VERS) ){    						
    						 break;
    					}
    				}       
    				
    				BigDecimal idDocFileDecimal = new BigDecimal(0);
    				flgTipoFile = "";
    				
    				// Per ogni operazione indicata nell'xml eseguo il servizio di AurigaDocument
    				idUdInt         = listaFileElettroniciNonSalvatiOut.getIdUD();
        			idUd            = idUdInt.toString();
        			idDocFile       = docId;
        			nroVersioneFile = nroVersione;
        			
        			// leggo IdDocPrimario associato alla UD
        			if (operazione.equals(K_AGGIORN_VERS) || operazione.equals(K_SOST_VERS)) {
        				try {        		
    						GestioneDocumenti servizio = new GestioneDocumenti();
    						idDocPrimario = servizio.leggiIdDocPrimarioWS(loginBean, idUd);
        				}
        				catch (Exception ve) {    			
    						String mess = "------> Fallita la ricerca del iDDocPrimario  per idUd = " + idUd +  ", ERRORE = " + ve.getMessage(); 
    						aLogger.debug(mess + " - " + ve.getMessage(), ve);
    						throw new Exception(mess);	    						
    					} 
        				
        				if (idDocFile!=null && !idDocFile.equalsIgnoreCase("")){
        					idDocFileDecimal = new BigDecimal(idDocFile);
        				}
        				
        				if (idDocPrimario!=null && idDocPrimario.compareTo(idDocFileDecimal) == 0 ){        					 
        					flgTipoFile = "P";
        				}
        				else{
        					flgTipoFile = "A";
        				}
        				
        				// Simulo il file elettronico ( primario o allegato)
        				if (operazione.equals(K_AGGIORN_VERS) || operazione.equals(K_SOST_VERS)) {       					
        					if (flgTipoFile.equalsIgnoreCase("P")){
        						Integer indAttP = new java.lang.Integer(attachNum);
    						    RebuildedFile lRebuildedFile1 = new RebuildedFile();
    						    lRebuildedFile1 = listRebuildedFile.get(indAttP-1);
    						    
    						    if(operazione.equals(K_SOST_VERS))
    						    	lRebuildedFile1.setAnnullaLastVer(true);
    						    else
    						    	lRebuildedFile1.setAnnullaLastVer(false);
    						    
    						    lAllegatiBean = popoloAllegatiBean(lRebuildedFile1, nome, docId, note); 
        					}
        					else if (flgTipoFile.equalsIgnoreCase("A")){
        						Integer indAttA = new java.lang.Integer(attachNum);
        						RebuildedFile lRebuildedFile2 = new RebuildedFile();
        						lRebuildedFile2 = listRebuildedFile.get(indAttA-1);
        						
        						if(operazione.equals(K_SOST_VERS))
    						    	lRebuildedFile2.setAnnullaLastVer(true);
    						    else
    						    	lRebuildedFile2.setAnnullaLastVer(false);
        						
        						lAllegatiBean = popoloAllegatiBean(lRebuildedFile2, nome, docId, note); 
        					}       						
        				}        			
        			}
    				
        			/**************************************************
    				 * AV = step version 
    				 **************************************************/    			
    				if (operazione.equals(K_AGGIORN_VERS)) {
    				
    					try {        		
    						GestioneDocumenti servizio = new GestioneDocumenti();
    						CreaModDocumentoOutBean servizioOut = new CreaModDocumentoOutBean();
    						servizioOut = servizio.addUpdDocsWS(loginBean, flgTipoFile, idUd, idDocFileDecimal, lAllegatiBean);
            	    
    						// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
    						if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
    							returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
    						}
            	    
    						//popolo un oggetto VersioneElettronicaNonCaricata per tener conto del fallimento
    						VersioneElettronicaNonCaricata venc = new VersioneElettronicaNonCaricata();
        			
    						// Se la gestione dei file e' andata in errore restituisco il messaggio di errore 
    						StringBuffer lStringBuffer = new StringBuffer();
            	    
    						int key = 0;
    						if (servizioOut.getFileInErrors()!=null && servizioOut.getFileInErrors().size()>0){
        							for (String lStrFileInError : servizioOut.getFileInErrors().values()){
                				 
        								lStringBuffer.append("; " + lStrFileInError);			
                				
        								//setto l'indice
        								String sIndex = servizioOut.getFileInErrors().get(key);
        								if (sIndex != null && !sIndex.equalsIgnoreCase(""))
        									venc.setNroAttachmentAssociato(new Integer(sIndex));

        								//setto il nome file
        								venc.setNomeFile(lStrFileInError);

        								//aggiungo alla lista dei fallimenti
        								versioniNonCaricate.add(venc);
                    			
        								key++;
        							}
    						}
            	    
    						if (lStringBuffer != null && lStringBuffer.length()>0 && !lStringBuffer.toString().equalsIgnoreCase("")) 
    							returnMess += (unaStepGiaFallita)?", "+ lStringBuffer : " " + lStringBuffer;        	    
    					}
    			
    					catch (Exception ve) {    			
    						String mess = "------> Fallita la stepVersion per il doc " + idDocFile;    			
    						returnMess += (unaStepGiaFallita)?", "+ mess : " " + mess;
    						unaStepGiaFallita = true;    			
    						aLogger.debug(mess + " - " + ve.getMessage(), ve);
    					}        			
    				}
    			
    				/**************************************************
    				 * SV = sostituzione di versione
    				 **************************************************/
    				else if( operazione.equals(K_SOST_VERS)){    
    					
    					try {        		
    						GestioneDocumenti servizio = new GestioneDocumenti();
    						CreaModDocumentoOutBean servizioOut = new CreaModDocumentoOutBean();
    						servizioOut = servizio.addUpdDocsWS(loginBean, flgTipoFile, idUd, idDocFileDecimal, lAllegatiBean);
            	    
    						// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
    						if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
    							returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
    						}
            	    
    						//popolo un oggetto VersioneElettronicaNonCaricata per tener conto del fallimento
    						VersioneElettronicaNonCaricata venc = new VersioneElettronicaNonCaricata();
        			
    						// Se la gestione dei file e' andata in errore restituisco il messaggio di errore 
    						StringBuffer lStringBuffer = new StringBuffer();
            	    
    						int key = 0;
    						if (servizioOut.getFileInErrors()!=null && servizioOut.getFileInErrors().size()>0){
    							for (String lStrFileInError : servizioOut.getFileInErrors().values()){                				 
        								lStringBuffer.append("; " + lStrFileInError);			
                				
        								//setto l'indice
        								String sIndex = servizioOut.getFileInErrors().get(key);
        								if (sIndex != null && !sIndex.equalsIgnoreCase(""))
        									venc.setNroAttachmentAssociato(new Integer(sIndex));

        								//setto il nome file
        								venc.setNomeFile(lStrFileInError);

        								//aggiungo alla lista dei fallimenti
        								versioniNonCaricate.add(venc);
                    			
        								key++;
        							}
    						}
            	    
    						if (lStringBuffer != null && lStringBuffer.length()>0 && !lStringBuffer.toString().equalsIgnoreCase("")) 
    							returnMess += (unaStepGiaFallita)?", "+ lStringBuffer : " " + lStringBuffer;        	    
    					}
    			
    					catch (Exception ve) {    			
    						String mess = "------> Fallita la modifyVersionDoc per il doc " + idDocFile;    			
    						returnMess += (unaStepGiaFallita)?", "+ mess : " " + mess;
    						unaStepGiaFallita = true;    			
    						aLogger.debug(mess + " - " + ve.getMessage(), ve);
    					}
    				}
    			    			
    				/********************************************************************************
    				 * DV = delete dell'ultima versione del file elettronico
    				 *******************************************************************************/
    				else if( operazione.equals(K_DELETE_VERS)){
    					// creo l'input
    					DelUdDocVerIn  input = new DelUdDocVerIn();
    					input.setIdUdDocIn(idDocFile);     				
    					input.setFlgTipoTargetIn("V");
    					input.setNroProgrVerIn(null);                // cancella sempre l'ultima versione
    					input.setFlgCancFisicaIn(new Integer(1));    // effettua sempre la cancellazione fisica
    				    		    	    	
    					// eseguo il servizio
    					try {
    						GestioneDocumenti servizio     = new GestioneDocumenti();
    						DelUdDocVerOut  servizioOut  = new DelUdDocVerOut();    
    						servizioOut = servizio.delUdDocVer(loginBean, input);
    		    		 
    						// Se il servizio e' andato in errore restituisco il messaggio di errore 	
    						if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
    							throw new Exception(servizioOut.getDefaultMessage());
    						}
    		    		 
    						// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
    						if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
    							returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
    						}
    					}
    					catch (Exception e){
    						throw new Exception(e.getMessage());	
    					}
    				}
    			
    				/********************************************************************************
    				 * DD = delete del documento
    				 *******************************************************************************/    			
    				else if( operazione.equals(K_DELETE_DOC)){
    				
    					// creo l'input
    					DelUdDocVerIn  input = new DelUdDocVerIn();
    					input.setIdUdDocIn(idDocFile);     				
    					input.setFlgTipoTargetIn("D");               // D = delete doc
    					input.setFlgCancFisicaIn(new Integer(1));    // effettua sempre la cancellazione fisica
    				
    					// eseguo il servizio
    					try {
    							GestioneDocumenti servizio     = new GestioneDocumenti();
   		    		 			DelUdDocVerOut  servizioOut  = new DelUdDocVerOut();    
   		    		 			servizioOut = servizio.delUdDocVer(loginBean, input);
   		    		 
   		    		 			// Se il servizio e' andato in errore restituisco il messaggio di errore 	
   		    		 			if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
   		    		 				throw new Exception(servizioOut.getDefaultMessage());
   		    		 			}
   		    		 
   		    		 			// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
   		    		 			if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
   		    		 			returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
   		    		 			}
    					}
    					catch (Exception e){
    						String mess = "------> Fallita la DelUdDocVer per il doc " + idDocFile;    	    						
    						aLogger.debug(mess + " - " + e.getMessage(), e);
   			 				throw new Exception(e.getMessage());	
   			 			}
    				}
    			
    				/********************************************************************************
    				 * MM = modifyMetadata
    				 *******************************************************************************/ 
    				else if( operazione.equals(K_MODIF_METADATA)){
    				   // non faccio nulla 
    				}
    			
    				/********************************************************************************
    				 * MV = Modifica metadati di versione
    				 *******************************************************************************/ 
    				else if( operazione.equals(K_MODIF_MD_VERS)){    				
     				   // non faccio nulla     				
    				}
    			}
    			    				   	        	
    			/*************************************************************
    			 * Gestisco gli eventuali messaggi di warning
    			 ************************************************************/        

    			// preparo la stringa per xml di risposta
    			if (!returnMess.equals("")) {
    				returnMess="Fallito il caricamento per i file:" + returnMess;
    			}

    			// se vi sono versioni non caricate
    			if (versioniNonCaricate.size() > 0) {
    				//trasferisco la lista in un array per effettuare il set nell'XMLOUT 
    				VersioneElettronicaNonCaricata vencArray[] =  new VersioneElettronicaNonCaricata[versioniNonCaricate.size()];
    				// scorro la lista e trasferisco
    				Iterator iter = versioniNonCaricate.iterator();
    				//contatore per popolare l'array
    				int i = 0;
    				//popolo l'array
    				while (iter.hasNext()) {
    					vencArray[i] = (VersioneElettronicaNonCaricata)iter.next();
    					aLogger.debug("NON inserita versione #" + i);
        				i++;
    				}
    				// effettuo il set sull'output_UD
    				listaFileElettroniciNonSalvatiOut.setVersioneElettronicaNonCaricata(vencArray);        	
    			}    		
    		}
    		catch (Throwable ee) { 
    			returnMess += "Errore nella funzione nella salvaAllegati() - " + ee.getMessage() + "\n";
    			aLogger.error(returnMess);  			
    			throw new Exception(returnMess);	 
    		}
    		return returnMess;    	
    }
	
    

}