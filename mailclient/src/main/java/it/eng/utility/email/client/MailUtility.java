package it.eng.utility.email.client;

import it.eng.utility.email.scheduler.MailScheduler;
import it.eng.utility.email.service.MailBoxService;
import it.eng.utility.email.service.delete.DeleteResultBean;
import it.eng.utility.email.service.force.ForceResultBean;
import it.eng.utility.email.service.sender.EmailSenderBean;
import it.eng.utility.email.service.sender.EmailSenderResultBean;

import org.apache.commons.lang3.StringUtils;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Classe di utilità per l'utilizzo dei servizi della mailbox
 * @author michele
 *
 */
public class MailUtility {

// "serviceurl" utilizzato solo nel caso "rest" e non in caso di config hibernate	
//	private static String serviceurl = null;
	
//	/**
//	 * Setta l'url in cui sono esposti i servizi del mailconnet
//	 * @param url
//	 */
//	public static void setServiceUrl(String url){
//		serviceurl = url+"/rest/RestMailService";		
//	}

	/**
	 * Setto la posizione dell'hibernate.xml
	 */
	public static void setServiceUrl(String url){
		//Inizializzo lo scheduler per l'accesso ai dati
		System.setProperty("hibernate.cfg.xml",url);
		
		try {
			MailScheduler.initialize(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Metodo di invio della mail
	 * @return
	 * @throws Exception 
	 */
	public static EmailSenderResultBean sendMail(EmailSenderBean mail,String account, String folder) throws Exception{

// "serviceurl" utilizzato solo nel caso "rest" e non in caso di config hibernate	
//		if(serviceurl==null){
//			throw new Exception("Service Url non configurato! Richimare il metodo setServiceUrl(String url)");
//		}
		
//		//Istanzio le configurazioni
//		ClientConfig config = new DefaultClientConfig();
//		
//		//Istanzio il client
//		Client client = Client.create(config);
//		WebResource service = client.resource(getBaseURI());
		
		//Se il nome del folder è vuoto lo setto a INBOX
		if(StringUtils.isBlank(folder)){
			folder = "INBOX";
		}
		
		//Creo i pacchetti da spedire
//		FormDataMultiPart multi= new FormDataMultiPart();
//		multi = multi.field("emailsender", mail, MediaType.APPLICATION_XML_TYPE);
//		multi = multi.field("account", account, MediaType.APPLICATION_XML_TYPE);
//		multi = multi.field("folder",folder, MediaType.APPLICATION_XML_TYPE);
		
		//Chiamo il servizio
		//EmailSenderResultBean wsresult = service.path("send").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(EmailSenderResultBean.class,multi);
		
		//Chiamo direttamente il processo di invio mail
		MailBoxService servicemailbox = new MailBoxService();
		return servicemailbox.sendMail(mail, account);
	}
	 
	/**
	 * Metodo che forza l'elaborazione di una mail nel caso sia in uno stato di non elaborazione
	 * Se il folder è null o vuoto di default va su INBOX 
	 * @throws Exception 
	 */
	public static ForceResultBean forceToElaborate(String messageid,String account, String folder) throws Exception {

// "serviceurl" utilizzato solo nel caso "rest" e non in caso di config hibernate	
//		if(serviceurl==null){
//			throw new Exception("Service Url non configurato! Richimare il metodo setServiceUrl(String url)");
//		}

		//Istanzio le configurazioni
		//ClientConfig config = new DefaultClientConfig();
		
		//Istanzio il client
		//Client client = Client.create(config);
		//WebResource service = client.resource(getBaseURI());
		
		//Se il nome del folder è vuoto lo setto a INBOX
		if(StringUtils.isBlank(folder)){
			folder = "INBOX";
		}
		
		//Creo i pacchetti da spedire
//		FormDataMultiPart multi= new FormDataMultiPart();
//		multi = multi.field("messageid", messageid, MediaType.APPLICATION_XML_TYPE);
//		multi = multi.field("account", account, MediaType.APPLICATION_XML_TYPE);
//		multi = multi.field("folder",folder, MediaType.APPLICATION_XML_TYPE);
//		
//		//Chiamo il servizio
//		ForceResultBean wsresult = service.path("forceelaborate").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ForceResultBean.class,multi);
		
		MailBoxService servicemailbox = new MailBoxService();
		return servicemailbox.forcetoelaborate(messageid, account, folder);
	}
	
	/**
	 * Effettua la cancellazione dalla mialbox sia sul processo e sia sulla webmail
	 * @param messageid
	 * @param account
	 * @param folder
	 * @return
	 * @throws Exception
	 */
	public static DeleteResultBean deleteMessage(String messageid,String account, String folder) throws Exception {

// "serviceurl" utilizzato solo nel caso "rest" e non in caso di config hibernate	
//		if(serviceurl==null){
//			throw new Exception("Service Url non configurato! Richimare il metodo setServiceUrl(String url)");
//		}

		//Istanzio le configurazioni
		ClientConfig config = new DefaultClientConfig();
		
//		//Istanzio il client
//		Client client = Client.create(config);
//		WebResource service = client.resource(getBaseURI());
		
		//Se il nome del folder è vuoto lo setto a INBOX
		if(StringUtils.isBlank(folder)){
			folder = "INBOX";
		}
		
//		//Creo i pacchetti da spedire
//		FormDataMultiPart multi= new FormDataMultiPart();
//		multi = multi.field("messageid", messageid, MediaType.APPLICATION_XML_TYPE);
//		multi = multi.field("account", account, MediaType.APPLICATION_XML_TYPE);
//		multi = multi.field("folder",folder, MediaType.APPLICATION_XML_TYPE);
//		
//		//Chiamo il servizio
//		DeleteResultBean wsresult = service.path("deleteelaborate").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(DeleteResultBean.class,multi);
//		return wsresult;
		
		MailBoxService servicemailbox = new MailBoxService();
		return servicemailbox.deleteMessage(messageid, account, folder);
		
	}
	
//	/**
//	 * Recupera il base uri
//	 * @return
//	 */
//	private static URI getBaseURI() {
//		return UriBuilder.fromUri(serviceurl).build();
//	}	
	
	
	
	
//	public static void main(String[] args) throws Exception {
//		
//		//Chiamo la force to operation
//		
//		serviceurl = "http://127.0.0.1:8888/rest/RestMailService";
//		idmailbox = "1";
//		
//		
//		ForceResultBean result = forceToElaborate("92929292929292","account@account.it",null);
//		
//		System.out.println(result.getResult());
//		System.out.println(result.getError());
//		
//		
//		EmailSenderBean mail = new EmailSenderBean();
//		mail.setAccount("account@account.it");
//		List<String> to = new ArrayList<String>();
//		to.add("michele.rigo@eng.it");
//		to.add("valentina.martinucci@eng.it");
//		mail.setAddressTo(to);
//		mail.setAddressFrom("sisartest@pec.provincia.pv.it");
//		mail.setBody("BODY DI TEST");
//		mail.setSubject("OGGETTO DELLA MAIL");
//		
//		EmailSenderAttachmentsBean attachment = new EmailSenderAttachmentsBean();
//		attachment.setContent(org.apache.commons.io.FileUtils.readFileToByteArray(new File("/home/michele/RapportoDiProvaArpa_RE.pdf")));
//		attachment.setFilename("file@çnome£$è test.pdf");
//		
//		List<EmailSenderAttachmentsBean> listattAttachmentsBeans = new ArrayList<EmailSenderAttachmentsBean>();
//		listattAttachmentsBeans.add(attachment);
// 		mail.setAttachments(listattAttachmentsBeans);
//		
//		EmailSenderResultBean resultsender =  sendMail(mail,"",null);
//		
//		System.out.println(resultsender.getResult());
//		System.out.println(resultsender.getError());
//		
//		
//		
//	}
	
	
}