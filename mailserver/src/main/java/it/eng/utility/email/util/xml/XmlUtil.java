package it.eng.utility.email.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ListIterator;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Visitor;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import it.eng.utility.email.operation.impl.interoperation.InterOperation;


/**
 * Classe che contiene i metodi di utilità per effettuare il <br>
 * Marshaller e Unmarshaller
 * @author michele
 *
 */
public class XmlUtil {

	private static final String EXTERNAL_DTD_LOADING_FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static Logger logger = LogManager.getLogger(XmlUtil.class);

	public static JAXBContext context;
	public static JAXBContext oldContext;
	public static JAXBContext newContext;

	private static SAXParserFactory mParserFactory = null;

	public static void setContext(JAXBContext context) throws JAXBException {
		XmlUtil.context = context;
		oldContext = JAXBContext.newInstance(it.eng.utility.email.old.segnatura.Segnatura.class);
		newContext = JAXBContext.newInstance(it.eng.utility.email.util.segnatura.Segnatura.class);
		mParserFactory = SAXParserFactory.newInstance();
		mParserFactory.setNamespaceAware(true);
		mParserFactory.setValidating(false);
	}

	public static Object xmlToObject(String xml) throws JAXBException{

		SAXSource lSource = new SAXSource(getXmlReader(), new InputSource(new StringReader(xml)));

		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(null);


		return unmarshaller.unmarshal(lSource);
	}

	public static Object xmlToObject(String xml, XmlValidationHandler pXmlValidationHandler) throws JAXBException{
		logger.debug("Xml To Object");
		SAXSource lSource = new SAXSource(getXmlReader(), new InputSource(new StringReader(xml)));
		// 04/02/2015
		// Diego: setto il corretto namespace per corretto unmarshaller
		// Solo se abbiamo a che fare con il nuovo..
		if (pXmlValidationHandler instanceof it.eng.utility.email.util.xml.SegnaturaNewXmlValidationHandler) {
			Document doc;
			try {
				SAXReader lSaxReader = new SAXReader();
				lSaxReader.setEntityResolver(new MyResolver());
				lSaxReader.setIncludeExternalDTDDeclarations(false);
				lSaxReader.setIncludeInternalDTDDeclarations(false);
				lSaxReader.setFeature(EXTERNAL_DTD_LOADING_FEATURE, false);
				doc = lSaxReader.read(lSource.getInputSource());
				Namespace oldNs = doc.getRootElement().getNamespace();
				Namespace newNs = Namespace.get("", "http://www.digitPa.gov.it/protocollo/");
				Visitor visitor = new NamesapceChangingVisitor(oldNs, newNs);
				doc.accept(visitor);
				logger.debug(doc.asXML());
				lSource = new SAXSource(getXmlReader(), new InputSource(new StringReader(doc.asXML())));
			} catch (DocumentException e) {
				logger.error("DocumentException xmlToObject: "+e.getMessage(),e);
			} catch (SAXException e) {
				logger.error("SAXException xmlToObject: "+e.getMessage(),e);
			}	
		}
		Unmarshaller unmarshaller = context.createUnmarshaller();
		logger.debug("Creato unmarshaller generico");
		if (pXmlValidationHandler instanceof it.eng.utility.email.util.xml.SegnaturaOldXmlValidationHandler) {
			unmarshaller = oldContext.createUnmarshaller();
			logger.debug("Creato old");
		} else if (pXmlValidationHandler instanceof it.eng.utility.email.util.xml.SegnaturaNewXmlValidationHandler) {
			unmarshaller = newContext.createUnmarshaller();
			logger.debug("Creato new");
		} else {
			unmarshaller = context.createUnmarshaller();
			logger.debug("Creato altro");
		}			
		unmarshaller.setEventHandler(pXmlValidationHandler);
		logger.debug("Settato event handler");
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
		try {
			Source[] lSchemas = new Source[]{
					new StreamSource(XmlUtil.class.getClassLoader().getResourceAsStream("xml.xsd")),
					new StreamSource(new StringReader(pXmlValidationHandler.getXsd()))

			};
			Schema schema = sf.newSchema(lSchemas);
			unmarshaller.setSchema(schema);
			logger.debug("Settato lo schema");
		} catch (SAXException e) {
			logger.error("Errore: " + e.getMessage(), e);
		} 

		Object lObject = unmarshaller.unmarshal(lSource);
		logger.debug("Eseguito unmarshall");
		return lObject;
	}

	public static Object xmlToObject(InputStream xml) throws JAXBException{
		try {
			String ret = IOUtils.toString(xml);
			return xmlToObject(ret);
		} catch (Exception e) {
			logger.error("Exception xmlToObject: "+e.getMessage(),e);
			//throw new JAXBException(e);
		}
		return null;
	}

	public static String objectToXml(Object bean) throws JAXBException{
		if(bean!=null){
			StringWriter writer = new StringWriter();
			Marshaller marshall = context.createMarshaller();
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(bean, writer);
			return writer.toString();
		}else{
			return null;
		}
	}



	public static String objectToXml(Object bean,String encoding) throws JAXBException, SAXException, IOException{
		if(bean!=null){
			Marshaller marshall = null;
			StringWriter writer = new StringWriter();
			if (bean.getClass().getPackage().getName().equals("it.eng.utility.email.old.segnatura")){
				marshall = oldContext.createMarshaller();
			} else if (bean.getClass().getPackage().getName().equals("it.eng.utility.email.util.segnatura")){
				marshall = newContext.createMarshaller();
			} else {
				marshall = context.createMarshaller();
			}
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.setProperty("jaxb.encoding", encoding);
			if (bean.getClass().isAnnotationPresent(XmlRootElement.class)){
				marshall.marshal(bean, writer);
			} else {


				marshall.marshal(bean, writer);
			}
			//			JAXBContext contextLocal = null;
			//			if (bean instanceof Segnatura) {
			//				contextLocal = JAXBContext.newInstance(Segnatura.class);
			//			} else if (bean instanceof AggiornamentoConferma) {
			//				contextLocal = JAXBContext.newInstance(AggiornamentoConferma.class);
			//			} else if (bean instanceof AnnullamentoProtocollazione) {
			//				contextLocal = JAXBContext.newInstance(AnnullamentoProtocollazione.class);
			//			} else if (bean instanceof ConfermaRicezione) {
			//				contextLocal =  JAXBContext.newInstance(ConfermaRicezione.class);
			//			} else if (bean instanceof NotificaEccezione) {
			//				contextLocal = JAXBContext.newInstance(NotificaEccezione.class);
			//			} else if (bean instanceof it.eng.utility.email.util.segnatura.Segnatura) {
			//				contextLocal = JAXBContext.newInstance(it.eng.utility.email.util.segnatura.Segnatura.class);
			//			} else if (bean instanceof it.eng.utility.email.util.segnatura.AggiornamentoConferma) {
			//				contextLocal = JAXBContext.newInstance(it.eng.utility.email.util.segnatura.AnnullamentoProtocollazione.class);
			//			} else if (bean instanceof it.eng.utility.email.util.segnatura.AnnullamentoProtocollazione) {
			//				contextLocal = JAXBContext.newInstance(it.eng.utility.email.util.segnatura.AnnullamentoProtocollazione.class);
			//			} else if (bean instanceof it.eng.utility.email.util.segnatura.ConfermaRicezione) {
			//				contextLocal = JAXBContext.newInstance(it.eng.utility.email.util.segnatura.ConfermaRicezione.class);
			//			} else if (bean instanceof it.eng.utility.email.util.segnatura.NotificaEccezione) {
			//				contextLocal = JAXBContext.newInstance(it.eng.utility.email.util.segnatura.NotificaEccezione.class);
			//			}
			//			StringWriter writer = new StringWriter();
			//			Marshaller marshall = null;
			//			if (contextLocal != null ) {
			//				marshall = contextLocal.createMarshaller();
			//			} else {
			//				marshall = context.createMarshaller();
			//			}
			//			contextLocal = null;
			//			marshall.setProperty("jaxb.encoding", encoding);
			//			marshall.marshal(bean, writer);
			String xml = writer.toString();
			// 04/02/2015
			// Diego: setto il corretto namespace per corretto unmarshaller
			if (bean.getClass().getPackage().getName().equals("it.eng.utility.email.util.segnatura")){
				Document doc;
				try {
					doc = new SAXReader().read( new InputSource(new StringReader(xml)));
					Namespace oldNs = doc.getRootElement().getNamespace();
					Namespace newNs = Namespace.get("", "");
					Visitor visitor = new NamesapceChangingVisitor(oldNs, newNs);
					doc.accept(visitor);
					//				logger.debug(doc.asXML());
					xml = doc.asXML();
				} catch (DocumentException e) {
					logger.error("DocumentException objectToXml: "+e.getMessage(),e);
				}
			}
			return xml;
		}else{
			return null;
		}
	}


	private static synchronized XMLReader getXmlReader() throws JAXBException {
		try{
			if (mParserFactory == null){
				initParseFactory();
			}
			SAXParser lSaxParser = mParserFactory.newSAXParser();
			XMLReader lReader = lSaxParser.getXMLReader();
			lReader.setFeature(EXTERNAL_DTD_LOADING_FEATURE, false);
			return lReader;
		}catch(Exception e){
			throw new JAXBException(e);
		}
	}

	private static void initParseFactory() {
		mParserFactory = SAXParserFactory.newInstance();
		mParserFactory.setNamespaceAware(true);
		mParserFactory.setValidating(false);
	}

	static class PreferredMapper extends NamespacePrefixMapper {
		@Override
		public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
			return " ";
		}
	}
}

/**
 * classe di utility per cambiare il namespace ai documenti
 * 
 * @author LEONE
 * 
 *
 */
class NamesapceChangingVisitor extends VisitorSupport {
	private Namespace from;
	private Namespace to;

	public NamesapceChangingVisitor(Namespace from, Namespace to) {
		this.from = from;
		this.to = to;
	}

	public void visit(Element node) {
		Namespace ns = node.getNamespace();

		if (ns.getURI().equals(from.getURI())) {
			org.dom4j.QName newQName = new org.dom4j.QName(node.getName(), to);
			node.setQName(newQName);
		}

		ListIterator namespaces = node.additionalNamespaces().listIterator();
		while (namespaces.hasNext()) {
			Namespace additionalNamespace = (Namespace) namespaces.next();
			if (additionalNamespace.getURI().equals(from.getURI())) {
				namespaces.remove();
			}
		}
	}

}

class MyResolver implements EntityResolver {
	public InputSource resolveEntity (String publicId, String systemId)
	{
		if (systemId.endsWith("Segnatura.dtd")) {
			return new InputSource(InterOperation.class.getClassLoader().getResourceAsStream("segnatura_con_agg_2013.xsd"));
		} else {
			// use the default behaviour
			return null;
		}
	}
}