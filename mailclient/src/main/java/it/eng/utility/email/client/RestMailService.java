package it.eng.utility.email.client;

import it.eng.utility.email.operation.impl.wserviceoperation.MailWSOperationBean;
import it.eng.utility.email.operation.impl.wserviceoperation.WSServiceBean;

import java.io.InputStream;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.multipart.FormDataParam;

/**
 * Rest service esposto per il recupero dei dati
 * @author michele
 */
@Path(value="/RestMailService")
public class RestMailService {
	
	@POST
	@Path("/service")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public WSServiceBean addAttachments(@FormDataParam("mimemessage") InputStream message, @FormDataParam("operation") MailWSOperationBean mailfile) throws Exception {
			
		//Recupero l'input stream e lo converto nel mimemessage
		MimeMessage mime = new MimeMessage(null,message);
		
		//Effettua la chiamata al servizio	
		return ServiceGroupObserver.service(mime,mailfile);
		
	}	
}