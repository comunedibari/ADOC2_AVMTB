package it.eng.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import it.eng.simplesendermail.service.SimpleSenderMail;
import it.eng.simplesendermail.service.bean.AttachmentMailToSendBean;
import it.eng.simplesendermail.service.bean.DummyMailToSendBean;
import it.eng.simplesendermail.service.bean.ResultSendMail;
import it.eng.spring.utility.SpringAppContext;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSendMail {
	@Before
	public void loadSpring(){
		AbstractXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		SpringAppContext.setContext(context);
	}
	@Test
	public void sendMail() throws FileNotFoundException, IOException{
		DummyMailToSendBean lDummyMailToSendBean = new DummyMailToSendBean();
		lDummyMailToSendBean.setAddressTo(Arrays.asList(new String[]{"fabrizio.rametta@eng.it"}));
		lDummyMailToSendBean.setBody("<h1>Test</h1>");
		lDummyMailToSendBean.setFrom("helpdesk2.documentale@eng.it");
		lDummyMailToSendBean.setSubject("Prova oggetto");
		lDummyMailToSendBean.setHtml(true);
		AttachmentMailToSendBean lAttachmentMailToSendBean = new AttachmentMailToSendBean();
		
		lAttachmentMailToSendBean.setContent(IOUtils.toByteArray(new FileInputStream("D:\\AURIGA - Analisi x soddisfare requisito di visualizzazione file con stampa e scarico inbiti.doc")));
		lAttachmentMailToSendBean.setFilename("Documento.doc");
		lDummyMailToSendBean.setAttachmentMailToSendBeans(Arrays.asList(new AttachmentMailToSendBean[]{lAttachmentMailToSendBean}));
//		lDummyMailToSendBean.setConfermaLettura(true);
		try {
			ResultSendMail lResultSendMail = new SimpleSenderMail().asyncSendFromConfigured(lDummyMailToSendBean, "helpdesk");
			if (!lResultSendMail.isInviata())
				System.out.println(lResultSendMail.getErrori().get(0));
			else System.out.println("ok");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		TestSendMail lTestSendMail = new TestSendMail();
		lTestSendMail.loadSpring();
		lTestSendMail.sendMail();
	}
}
