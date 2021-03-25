package it.eng.utility.email;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.config.MailServerSpringAppContext;
import it.eng.utility.email.util.mail.MailVerifier;
import it.eng.utility.email.util.xml.XmlUtil;


public class MailDBRepopulate {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 * @throws MessagingException 
	 */
	public static void main(String[] args) throws Exception {
	
		Reflections reflection = new Reflections("it.eng.utility");
		
		Set<Class<?>> classesXML = reflection.getTypesAnnotatedWith(XmlRootElement.class);		
		XmlUtil.setContext(JAXBContext.newInstance(classesXML.toArray(new Class[0])));
		
		//MailVerifier ver = new MailVerifier();
		MailVerifier ver = (MailVerifier) MailServerSpringAppContext.getContext().getBean("mailVerifier");	
		MessageInfos infos = ver.verifyAnalize(new MimeMessage(null, FileUtils.openInputStream(new File("/home/michele/progetti/inboxgenova/31dc6075-d5a8-4ad7-9f52-4c8d80bcd5b9"))), new File("/home/michele/progetti"),false);
		//MessageInfos infos = ver.verifyAnalize(new MimeMessage(null, FileUtils.openInputStream(new File("/home/michele/progetti/inboxgenova/106f2976-3964-425f-8f89-cbfe1d47d970"))), new File("/home/michele/progetti"));
		
//		InterOperation op = new InterOperation();
//		op.elaborate(infos);
		
//		System.out.println(infos.getHeaderinfo().getMittente());
//		if(infos.isPec() || infos.isRicevuta()){
//			if(infos.getHeaderinfo().getMittente()!=null){
//				System.out.println(infos.getHeaderinfo().getMittente());
//			}else if(infos.getDaticert()!=null){
//				System.out.println(infos.getDaticert().getIntestazione().getMittente());
//			}
//		}else if(infos.isAnomaliaPec()){
//			if(infos.getPostecerteml().getHeaderinfo().getMittente()!=null){
//				System.out.println(infos.getPostecerteml().getHeaderinfo().getMittente());
//			}else if(infos.getPostecerteml().getDaticert()!=null){
//				System.out.println(infos.getPostecerteml().getDaticert().getIntestazione().getMittente());
//			}
//			
//		}
		
//		System.out.println(infos);
//		if(infos.isRicevuta()){
//			if(infos.getDaticert().getDati().getRicevuta()!=null){
//				System.out.println(infos.getDaticert().getDati().getRicevuta().getTipo());
//			}
//			System.out.println(infos.getDaticert().getDati().getMsgid());
//			System.out.println(infos.getDaticert().getTipo());
//		}
	}

}
