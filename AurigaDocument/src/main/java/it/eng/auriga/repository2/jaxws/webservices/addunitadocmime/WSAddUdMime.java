package it.eng.auriga.repository2.jaxws.webservices.addunitadocmime;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.WSAddUd;

/**
 * @author Ottavio passalacqua
 */

@WebService(targetNamespace = "http://addunitadocmime.webservices.repository2.auriga.eng.it", endpointInterface="it.eng.auriga.repository2.jaxws.webservices.addunitadocmime.WSIAddUdMime", name = "WSAddUdMime")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSAddUdMime extends WSAddUd implements WSIAddUdMime{	

   
}