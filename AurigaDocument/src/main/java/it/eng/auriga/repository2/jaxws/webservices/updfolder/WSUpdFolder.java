package it.eng.auriga.repository2.jaxws.webservices.updfolder;

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsUpdfolderBean;
import it.eng.auriga.database.store.dmpk_ws.store.Updfolder;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.GestioneFascicoli;
import it.eng.document.function.bean.ModificaFascicoloOut;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author Ottavio Passalacqua
 *
 */

@WebService(targetNamespace = "http://updfolder.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.updfolder.WSIUpdFolder", name = "WSUpdFolder")
@MTOM(enabled = true, threshold = 0)

public class WSUpdFolder extends JAXWSAbstractAurigaService implements WSIUpdFolder{	

    private final String K_SAVEPOINTNAME = "INIZIOWSUPDFOLDER";
    
    static Logger aLogger = Logger.getLogger(WSUpdFolder.class.getName());    
    
    public WSUpdFolder() {
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
    String outServizio = null;
    String outRispostaWS = null;
    String errMsg = null;
    String xmlIn = null;

    try {

        aLogger.info("Inizio WSUpdFolder");
        
    	// set a false dell'autocommit
        conn.setAutoCommit(false);
        
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
        WSUpdFolderBean outWS = new WSUpdFolderBean();
        
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
	 	  	xmlIn = outServizio;	
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
	  		  
	    	  // Converto l'XML
	 		  ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());
	 		  
	  		  // Aggiungo l'XML
	  		  lListInputStreams.add(inputStreamXml); 
	  		  
	  		  // Salvo gli ATTACH alla response
	  		  attachListInputStream(lListInputStreams); 	    	    		     
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

        aLogger.info("Fine WSUpdFolder");
   
        return risposta;
    }
    catch (Exception excptn) {
        aLogger.error("WSUpdFolder: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSDeleteFolder serviceImplementation");
	}
    }
        
    private WSUpdFolderBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	
    	String xml       = null;
    	String idFolder       = null;
    	
    	aLogger.debug("Eseguo il WS DmpkWSUpdFolder.");
    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsUpdfolderBean input = new DmpkWsUpdfolderBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  Updfolder service = new Updfolder();
    		  StoreResultBean<DmpkWsUpdfolderBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	
    		      		  
    		  // restituisco l'XML
    		  if (output.getResultBean().getXmlout() != null){
    			  xml = output.getResultBean().getXmlout().toString();  
    		  }
    		  
    		  if (xml== null || xml.equalsIgnoreCase(""))
    			  throw new Exception("La store procedure UpdFolder ha ritornato XmlOut nullo");
    		      		  
    		  // restituisco l'ID FOLDER
    		  if (output.getResultBean().getIdfolderout() != null){
    			  idFolder = output.getResultBean().getIdfolderout().toString();  
    		  }
    		  
    		  if (idFolder== null || idFolder.equalsIgnoreCase(""))
    			  throw new Exception("La store procedure UpdFolder ha ritornato id folder nullo");
    			      		 
    	      // popolo il bean di out
    	      WSUpdFolderBean  result = new WSUpdFolderBean();
    	        
    	      result.setIdFolder(idFolder);
    	      result.setXml(xml);
    	      
    	      
    		  return result;
 			}
 		catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
    }
    
    private String eseguiServizio(AurigaLoginBean loginBean, WSUpdFolderBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	String ret = null; 
    	
    	// creo l'input
		String idFolderIn = (bean.getIdFolder());
		String xmlIn = bean.getXml();
    	
    	// eseguo il servizio
		try {
	    	    GestioneFascicoli servizio = new GestioneFascicoli();
	    	    ModificaFascicoloOut servizioOut = new ModificaFascicoloOut();
	    	    servizioOut = servizio.modificaFascicoloWS(loginBean, idFolderIn, xmlIn);
	    	    	    	    
	    	    // Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
	    	    if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
	    	    	throw new Exception(servizioOut.getDefaultMessage());
	    		}
	    	    // Altrimenti restituisco l'ID FOLDER
	    	    //ret = (servizioOut.getUriPerAggiornamentoContainer()!= null ? (StringUtils.isNotBlank(servizioOut.getUriPerAggiornamentoContainer()) ? servizioOut.getUriPerAggiornamentoContainer() : null) : null);
	    	    
	    	    ret = idFolderIn;
	    	    
	    	    
	 		}
	 	catch (Exception e){
	 		throw new Exception(e.getMessage());	
	 	}
    	return ret;
    }
    
    
    /**
     * Genera il file XML contenente l'URI del folder camcellato
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
            aLogger.debug("generaXMLToken: token = " + xmlIn);
            aLogger.debug("generaXMLToken: tokenEsc = " + xmlInEsc);
            xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
           // xml.append("<UriFolder>" + xmlInEsc + "</UriFolder>\n");
            xml.append("<IdFolder>" + xmlInEsc + "</IdFolder>\n");
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 			
 		}
        return xml.toString();
    }
}