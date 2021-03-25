package it.eng.auriga.repository2.jaxws.webservices.determina;


/**
 * WSGetDetermina
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.istack.ByteArrayDataSource;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_login.store.Login;
import it.eng.auriga.database.store.dmpk_login.store.impl.LoginImpl;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsAddudBean;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsGetdeterminaBean;
import it.eng.auriga.database.store.dmpk_ws.store.Addud;
import it.eng.auriga.database.store.dmpk_ws.store.impl.AddudImpl;
import it.eng.auriga.database.store.dmpk_ws.store.impl.GetdeterminaImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.WSFileUtils;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.listener.AurigaPreInsertEventListener;
import it.eng.auriga.module.business.listener.AurigaPreUpdateEventListener;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.auriga.module.config.SessionFile;
import it.eng.auriga.module.config.SessionFileConfigurator;
import it.eng.auriga.repository2.jaxws.webservices.extractmulti.ExtractBean;
import it.eng.client.DmpkLoginLogin;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.ReflectionUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;

@WebService(targetNamespace="http://determina.webservices.jaxws.repository2.auriga.eng.it", endpointInterface="it.eng.auriga.repository2.jaxws.webservices.determina.WSIGetDetermina", name="WSGetDetermina")
@MTOM(enabled=true, threshold=0)
public class WSGetDetermina
  implements WSIGetDetermina
{
  
  @Resource
  private WebServiceContext context;
  
  /**
   * WSGetDetermina
   * AURIGA-106
   * Nuovo web service per recuperare dati e file determine (per Milano - SAP)
   */
   
  static Logger aLogger = Logger.getLogger(WSGetDetermina.class.getName());
  private Locale locale;
  private static final String CLASS_NAME = WSGetDetermina.class.getName();
  public ResponseGetDetermina getDetermina(RequestGetDetermina parameters)
  {
    ResponseGetDetermina resp = new ResponseGetDetermina();
    MessageContext msgContext = context.getMessageContext();
    //it.eng.auriga.repository2.jaxws.webservices.determina.entity.ResponseGetDetermina response = new it.eng.auriga.repository2.jaxws.webservices.determina.entity.ResponseGetDetermina();
    it.eng.auriga.repository2.jaxws.webservices.determina.ResponseGetDetermina response = new it.eng.auriga.repository2.jaxws.webservices.determina.ResponseGetDetermina();
    ResponseGetDetermina.ErrorMessage errorMessage = new ResponseGetDetermina.ErrorMessage();
    aLogger.info("getDetermina");
    try
    {
      WSGetDeterminaOutBean out = new WSGetDeterminaOutBean();
      out  = requestDb(parameters,out);
    	
      XPath xPath = XPathFactory.newInstance().newXPath();

      String expression = "Determina/IDProposta/Numero";

      String numero = (String)xPath.compile(expression).evaluate(out.getDocument(), XPathConstants.STRING);
      
      aLogger.info("numero: "+numero);
      
      //JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { it.eng.auriga.repository2.jaxws.webservices.determina.entity.ResponseGetDetermina.class });
      JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { it.eng.auriga.repository2.jaxws.webservices.determina.ResponseGetDetermina.class });
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
     // response = (it.eng.auriga.repository2.jaxws.webservices.determina.entity.ResponseGetDetermina)jaxbUnmarshaller.unmarshal(out.getDocument());
      response = (it.eng.auriga.repository2.jaxws.webservices.determina.ResponseGetDetermina)jaxbUnmarshaller.unmarshal(out.getDocument());
      aLogger.info("determina " +response.toString());
      
      ResponseGetDetermina.Determina det = dettaglioDetermina(response);
      Map<String, DataHandler> mapDataHandlers = new HashMap<String, DataHandler>();
      if(out.getExtracted()!= null)
      {	  
			// Ciclo sulla lista dei FILE ATTACH
			for (File lFile : out.getExtracted()) {
				DataHandler handler = new DataHandler(
						new ByteArrayDataSource(FileUtils.readFileToByteArray(lFile), "application/octet-stream"));
				
				// Create the attachment as a DIME attachment
				if (mapDataHandlers == null || mapDataHandlers.size() == 0) {
					mapDataHandlers = new LinkedHashMap<String, DataHandler>();
				}

				mapDataHandlers.put(lFile.getName(), handler);

			}
      }
	 // aggiunge l'attachment all response
	if (mapDataHandlers.size()>0)
	{	
	   msgContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);      
	} 
	 resp.setDetermina(det);
      if (out.getWarning()!=null && !out.getWarning().equals(""))
      {
    	  errorMessage.setCodice("WARNING - 001");
          errorMessage.setValue(out.getWarning());
          resp.setErrorMessage(errorMessage);  
      }
      resp.setEsitoRequest("OK");
      
      
    } catch (Exception e) {
      aLogger.error("Exception " + e.getMessage());
      errorMessage.setCodice("001");
      errorMessage.setValue(e.getMessage());
      resp.setErrorMessage(errorMessage);
      resp.setEsitoRequest("KO");
    }

    return resp;
  }
  private ResponseGetDetermina.Determina dettaglioDetermina(it.eng.auriga.repository2.jaxws.webservices.determina.ResponseGetDetermina determina) throws Exception
  {
	  aLogger.info("dettaglioDetermina - INIZIO");
	  ResponseGetDetermina.Determina det = new  ResponseGetDetermina.Determina();
	  try {
			ResponseGetDetermina.Determina.IDProposta id = new ResponseGetDetermina.Determina.IDProposta();
			it.eng.auriga.repository2.jaxws.webservices.determina.AutoreType au = new it.eng.auriga.repository2.jaxws.webservices.determina.AutoreType();

			BeanUtils.copyProperties(au, determina.getDetermina().getIDProposta().getAutore());
			id.setAutore(au);
			id.setNumero(determina.getDetermina().getIDProposta().getNumero());
			id.setTsRegistrazione(determina.getDetermina().getIDProposta().getTsRegistrazione());
			det.setIDProposta(id);
			
			ResponseGetDetermina.Determina.IDAttoDefinitivo idA = new ResponseGetDetermina.Determina.IDAttoDefinitivo();
			BeanUtils.copyProperties(idA, determina.getDetermina().getIDAttoDefinitivo());
			det.setIDAttoDefinitivo(idA);
			
			det.setRifAttiPrecedenti(determina.getDetermina().getRifAttiPrecedenti());
			det.setOggettoPubbl(determina.getDetermina().getOggettoPubbl());
			det.cig = new ArrayList<String>();
			BeanUtils.copyProperties(det.cig , determina.getDetermina().getCIG());
			
			det.setConRilevanzaContabile(determina.getDetermina().getConRilevanzaContabile());
		    det.setRegContInContoCapitale(determina.getDetermina().isRegContInContoCapitale());
		    det.setRegContInContoCapitale(determina.getDetermina().isRegContInContoCapitale());
		    det.setSpecificita(determina.getDetermina().getSpecificita());
		    
		    det.strutturaProponente = new StrutturaType();
		    BeanUtils.copyProperties(det.strutturaProponente, determina.getDetermina().getStrutturaProponente());
		    
		    
		    det.responsabiliDiConcerto = new ArrayList<FirmatarioType>();
		    
		    for (it.eng.auriga.repository2.jaxws.webservices.determina.FirmatarioType temp : determina.getDetermina().getResponsabiliDiConcerto()) {
		    	FirmatarioType firm = new FirmatarioType();
		    	StrutturaType struttura = new StrutturaType();
		    	BeanUtils.copyProperties(struttura, temp.getStruttura());
		    	firm.setStruttura(struttura);
		    	firm.tsFirmaVisto = new ArrayList<FirmatarioType.TsFirmaVisto>();
		    	BeanUtils.copyProperties( firm.tsFirmaVisto, temp.getTsFirmaVisto());
		    	det.responsabiliDiConcerto.add(firm);
			}
		   // BeanUtils.copyProperties(det.responsabiliDiConcerto, determina.getDetermina().getResponsabiliDiConcerto());
		    det.responsabiliPEG = new ArrayList<FirmatarioType>();
		    for (it.eng.auriga.repository2.jaxws.webservices.determina.FirmatarioType temp : determina.getDetermina().getResponsabiliPEG()) {
		    	FirmatarioType firm = new FirmatarioType();
		    	StrutturaType struttura = new StrutturaType();
		    	BeanUtils.copyProperties(struttura, temp.getStruttura());
		    	firm.setStruttura(struttura);
		    	firm.tsFirmaVisto = new ArrayList<FirmatarioType.TsFirmaVisto>();
		    	BeanUtils.copyProperties( firm.tsFirmaVisto, temp.getTsFirmaVisto());
		    	det.responsabiliPEG.add(firm);
			}
		    //BeanUtils.copyProperties(det.responsabiliPEG, determina.getDetermina().getResponsabiliPEG());
			
		    det.rdP = new FirmatarioType();
		    det.rdP.struttura = new StrutturaType();
		    BeanUtils.copyProperties(det.rdP.struttura, determina.getDetermina().getRdP().getStruttura());
		    det.rdP.tsFirmaVisto = new ArrayList<FirmatarioType.TsFirmaVisto>();
		    BeanUtils.copyProperties( det.rdP.tsFirmaVisto, determina.getDetermina().getRdP().getTsFirmaVisto());
		    
		    det.rup = new FirmatarioType();
		    det.rup.struttura = new StrutturaType();
		    BeanUtils.copyProperties( det.rup.struttura, determina.getDetermina().getRUP().getStruttura());
		    det.rup.tsFirmaVisto = new ArrayList<FirmatarioType.TsFirmaVisto>();
		    BeanUtils.copyProperties( det.rup.tsFirmaVisto, determina.getDetermina().getRUP().getTsFirmaVisto());
		    
		    det.preverificaRagioneria = new ResponseGetDetermina.Determina.PreverificaRagioneria();
		    BeanUtils.copyProperties(det.preverificaRagioneria, determina.getDetermina().getPreverificaRagioneria());
		    
		    det.vistoRegolaritaContabile = new ResponseGetDetermina.Determina.VistoRegolaritaContabile();
		    det.vistoRegolaritaContabile.firmatario = new FirmatarioRestrictedType();
		    BeanUtils.copyProperties(det.vistoRegolaritaContabile.firmatario, determina.getDetermina().getVistoRegolaritaContabile().getFirmatario());
		    det.vistoRegolaritaContabile.setTsRilascio(determina.getDetermina().getVistoRegolaritaContabile().getTsRilascio());
		    
		    det.pubblicazione = new ResponseGetDetermina.Determina.Pubblicazione();
		    BeanUtils.copyProperties(det.pubblicazione, determina.getDetermina().getPubblicazione());
		    
		    det.attachmentFiles = new ResponseGetDetermina.Determina.AttachmentFiles();
		    det.attachmentFiles.file = new ArrayList<ResponseGetDetermina.Determina.AttachmentFiles.File>();
		    determina.getDetermina().getAttachmentFiles().getFile();
		    for (it.eng.auriga.repository2.jaxws.webservices.determina.ResponseGetDetermina.Determina.AttachmentFiles.File temp : determina.getDetermina().getAttachmentFiles().getFile()) {
		    	
		    	ResponseGetDetermina.Determina.AttachmentFiles.File firm = new ResponseGetDetermina.Determina.AttachmentFiles.File();
		    	ResponseGetDetermina.Determina.AttachmentFiles.File.Digest digest = new ResponseGetDetermina.Determina.AttachmentFiles.File.Digest();
		    	ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato firmato = new ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato();
		    	BeanUtils.copyProperties(digest, temp.getDigest());
		    	firm.setDigest(digest);
		    	BeanUtils.copyProperties(firmato, temp.getFirmato());
		    	firm.setFirmato(firmato);
		    	firm.setDescrizione(temp.getDescrizione());
		    	firm.setDimensione(temp.getDimensione());
		    	firm.setDisplayFilename(temp.getDisplayFilename());
		    	firm.setMimetype(temp.getMimetype());
		    	firm.setNroAttachment(temp.getNroAttachment());
		    	firm.setRappresenta(temp.getRappresenta());
		    	firm.setTipoologiaDoc(temp.getTipoologiaDoc());
		    	det.attachmentFiles.file.add(firm);
			}
		    
		    //BeanUtils.copyProperties(det.attachmentFiles.file, determina.getDetermina().getAttachmentFiles().getFile());
		    
		    
		    
	} catch (IllegalAccessException e) {
		aLogger.error("Exception " + e.getMessage());
		throw new Exception("dettaglioDetermina : "+e.getMessage());
	} catch (InvocationTargetException e) {
		aLogger.error("Exception " + e.getMessage());
		throw new Exception("dettaglioDetermina : "+e.getMessage());
	}
     
	  aLogger.info("dettaglioDetermina - FINE");
      return det;
  }

	private WSGetDeterminaOutBean requestDb(RequestGetDetermina parameters,WSGetDeterminaOutBean wsOut) throws Exception {
		// creo bean connessione
		aLogger.info("requestDb - INIZIO");
		
		Session session = null;
		
		try {
		
		configureSchemas(CLASS_NAME);
		
		AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();		
		session = it.eng.auriga.repository2.jaxws.webservices.determina.HibernateUtil.openSession(CLASS_NAME);
		if (parameters.arg0.user != null && !"".equals(parameters.arg0.user))
		{	
			DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
		//	lLoginInput.setFlgnoctrlpasswordin(1);
			lLoginInput.setUsernamein(parameters.arg0.user);
			lLoginInput.setPasswordin(parameters.arg0.password);
			//lLoginInput.setFlgtpdominioautio(3);
		//	lLoginInput.setIddominioautio(new BigDecimal("8"));
			lLoginInput.setCodapplicazioneestin(parameters.arg0.codiceApplicazione);
			lLoginInput.setCodistanzaapplestin(parameters.arg0.istanzaApplicativa);
			lLoginInput.setFlgrollbckfullin(null);
			lLoginInput.setFlgautocommitin(1);
		

			
			locale = new Locale("it");
			lAurigaLoginBean.setLinguaApplicazione("it");
			lAurigaLoginBean.setIdUtente(parameters.arg0.user);
			lAurigaLoginBean.setPassword(parameters.arg0.password);
			//lAurigaLoginBean.setSchema("OWNER_2");
			LoginImpl lLogin = new LoginImpl();
			lLogin.setBean(lLoginInput);
			SubjectBean subject1 =  new SubjectBean();
			subject1.setIdDominio(lAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject1);  
			try {
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lLogin.execute(paramConnection);
					}
				});
				StoreResultBean<DmpkLoginLoginBean> result = new StoreResultBean<DmpkLoginLoginBean>();
				AnalyzeResult.analyze(lLoginInput, result);
				result.setResultBean(lLoginInput);
				lAurigaLoginBean.setToken(result.getResultBean().getCodidconnectiontokenout());
				SpecializzazioneBean specializzazioneBean  =new SpecializzazioneBean();
				specializzazioneBean.setIdDominio(result.getResultBean().getIddominioautio());
				lAurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);
				aLogger.info("Token: "+result.getResultBean().getCodidconnectiontokenout());
			} catch (Exception e) {
				aLogger.error("Exception " + e.getMessage());
				throw new Exception("dettaglioDetermina : "+e.getMessage());
			}
			
			
		}
		else
		{	
		  lAurigaLoginBean.setToken(parameters.arg0.password);
		}
		
		JAXBContext lJAXBContextMarshaller = JAXBContext.newInstance(RequestGetDetermina.class);
		Marshaller marshaller = lJAXBContextMarshaller.createMarshaller();

		StringWriter stringWriter = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(parameters, stringWriter);
		
		
		aLogger.info("Request: "+stringWriter.toString());
		
		// Chiamo la store DmpkWsGetdetermina
		wsOut =  callGetdeterminaImpl(lAurigaLoginBean,stringWriter.toString(), session,wsOut);
		
		InputStream fileXslIn = new ByteArrayInputStream(wsOut.getXml().getBytes());
		//InputStream fileXslIn = getClass().getResourceAsStream("/Esempio.xml");

		DateFormat df = new SimpleDateFormat("dd_MM_YYYY");
		Date today = Calendar.getInstance().getTime();
		String sDate = df.format(today);
		String filename = "file_ws_temp_" + sDate + "_id_"
				+ Integer.toString(Math.abs(ThreadLocalRandom.current().nextInt()));

		File tempFile;
		
			tempFile = File.createTempFile(filename, ".tmp");
			copyInputStreamToFile(fileXslIn, tempFile);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			wsOut.setDocument(builder.parse(new InputSource(new FileReader(tempFile))));

			wsOut.getDocument().getDocumentElement().normalize();

			tempFile.delete();
		} catch (IOException e) {
			aLogger.error("Exception " + e.getMessage());
			throw new Exception("dettaglioDetermina : "+e.getMessage());
		} catch (ParserConfigurationException e) {
			aLogger.error("Exception " + e.getMessage());
			throw new Exception("dettaglioDetermina : "+e.getMessage());
		} catch (SAXException e) {
			aLogger.error("Exception " + e.getMessage());
			throw new Exception("dettaglioDetermina : "+e.getMessage());
		}finally {
			if( session!=null )
				it.eng.auriga.repository2.jaxws.webservices.determina.HibernateUtil.release(session);
		}
		
		 aLogger.info("requestDb - INIZIO");
		return wsOut;
	}
  private static void copyInputStreamToFile(InputStream inputStream, File file)
    throws IOException
  {
    FileOutputStream outputStream = new FileOutputStream(file); Throwable localThrowable3 = null;
    try
    {
      byte[] bytes = new byte[1024];
      int read;
      while ((read = inputStream.read(bytes)) != -1)
        outputStream.write(bytes, 0, read);
    }
    catch (Throwable localThrowable1)
    {
      localThrowable3 = localThrowable1; throw localThrowable1;
    }
    finally
    {
      if (outputStream != null) if (localThrowable3 != null) try { outputStream.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else outputStream.close();
    }
  }
  
  private WSGetDeterminaOutBean callGetdeterminaImpl(AurigaLoginBean loginBean, String xmlIn, Session session,WSGetDeterminaOutBean wsOut) throws Exception {
  	
	aLogger.info("callGetdeterminaImpl - INIZIO");
  	
  	String xml = null;
  	String ListaFile = null;
  	try {    		
  		  // Inizializzo l'INPUT
  		  aLogger.debug("Questo e' l'xml che passo alla store DmpkWsGetdetermina : \n\n\n " + xmlIn);
  		  DmpkWsGetdeterminaBean input = new DmpkWsGetdeterminaBean();
  		  input.setCodidconnectiontokenin(loginBean.getToken());
  		  input.setXmlrequestin(xmlIn);
  		  
  		  // Eseguo il servizio
  		  StoreResultBean<DmpkWsGetdeterminaBean> output = null;
  		  if( session!=null ){
  			  final GetdeterminaImpl service = new GetdeterminaImpl();
  			  service.setBean(input);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						service.execute(paramConnection);
					}
				});
				output = new StoreResultBean<DmpkWsGetdeterminaBean>();
				AnalyzeResult.analyze(input, output);
				output.setResultBean(input);
				
  		  } 
  		  if (output.isInError()){
  			  throw new Exception(output.getDefaultMessage());	
  			}	

  		  // leggo  XmlOut
  		  xml = output.getResultBean().getXmlresponseout();
  		  if (xml== null || xml.equalsIgnoreCase(""))
  		  {	  
  			  throw new Exception("La store procedure ha ritornato XmlOut nullo");
  		  }
  		  wsOut.setXml(xml);
  		  // XML conforme a schema LISTA_STD.xsd con URI dei file (solo una colonna) 
  		  //da restituire come attachment in output al servizio (nell'ordine 
  		  //con cui sono restituiti qui)
	      if (output.getResultBean().getListafileout() != null){
	    	  ListaFile = output.getResultBean().getListafileout().toString();
	    	  java.io.StringReader sr = new java.io.StringReader(ListaFile);
				Lista lsSr = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lsSr != null) {
					List<File> listF = new ArrayList<File>();
					for (int j = 0; j < lsSr.getRiga().size(); j++) {
						// prendo la riga i-esima
						Riga r = lsSr.getRiga().get(j);
						String uriFile       = getContentColonnaNro(r, 1);
						if(uriFile!=null && !uriFile.equals(""))
						{	
							FileExtractedOut lFileExtractedOut = new FileExtractedOut();
							try {
								lFileExtractedOut.setExtracted(DocumentStorage.extract(uriFile, null));
								aLogger.info("lFileExtractedOut.getExtracted(): "+lFileExtractedOut.getExtracted().getAbsolutePath());
								listF.add(lFileExtractedOut.getExtracted());
								
							}
							catch (Exception e) {
								aLogger.error("lWSFileUtils.extract "+uriFile+" "+ e.getMessage());
								wsOut.setWarning("lWSFileUtils.extract "+uriFile+" "+ e.getMessage());
							}
							
							 
						 }
					   }//chiudo if
					wsOut.setExtracted(listF);
					}//chiudo for				
			
	      }	//chiudo lista out
  		  
  		  
  		   aLogger.info("callGetdeterminaImpl - FINE");  
  		  return wsOut;
			}
		catch (Exception e){
			aLogger.error("Exception " + e.getMessage());
			throw new Exception("callGetdeterminaImpl : "+e.getMessage());		
		}
  	
  }
  private void configureSchemas(String name) throws Exception {
		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean("SessionFileConfigurator");
		for (SessionFile lSessionFile : lSessionFileConfigurator.getSessions()){
			aLogger.error("Aggiungo la session per " + lSessionFile.getSessionName() + " mediante il file " + lSessionFile.getFileName());
			Configuration configuration = new Configuration();
			configuration.configure(lSessionFile.getFileName());
//			ConfigUtil.initialize();
			it.eng.auriga.repository2.jaxws.webservices.determina.HibernateUtil.setEntitypackage("it.eng.auriga.module.business.entity");
			ReflectionUtil.setEntityPackage("it.eng.auriga.module.business.entity");
			configuration.setListener("save" , new AurigaPreInsertEventListener());
			configuration.setListener("save-update" , new AurigaPreUpdateEventListener());
			configuration.setListener("update" , new AurigaPreUpdateEventListener());
			
			it.eng.auriga.repository2.jaxws.webservices.determina.HibernateUtil.buildSessionFactory(lSessionFile.getFileName(),name);
		}
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
}