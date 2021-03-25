
package it.eng.auriga.repository2.jaxws.webservices.operazioniaurigaFeedbackElabDocs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import it.eng.auriga.repository2.jaxws.jaxbBean.feedbackOperazioniaurigalottidoc.RequestFeedbackElabDocsList;
import it.eng.auriga.repository2.jaxws.jaxbBean.feedbackOperazioniaurigalottidoc.ResponseReqFeedbackElabDocsList;
import it.eng.auriga.repository2.jaxws.webservices.operazioniaurigalottidoc.OperazioniAurigaLottiDocFault;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.9-b130926.1035 Generated source version: 2.2
 * 
 */
@WebService(name = "WSFeedbackOperazioniAurigaLottiDoc", targetNamespace = "http://operazioniaurigaFeedbackElabDocs.webservices.repository2.auriga.eng.it")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
// @XmlSeeAlso({ ObjectFactory.class })
public interface WSIFeedbackOperazioniAurigaLottiDoc {

	/**
	 * 
	 * @param parameter
	 * @return returns it.eng.auriga.repository2.jaxws.webservices.operazioniaurigaFeedbackElabDocs.ResponseActionsOnDocList
	 */
	@WebMethod(operationName = "FeedbackOperazioniAurigaLottiDoc", action = "http://operazioniaurigaFeedbackElabDocs.webservices.repository2.auriga.eng.it/FeedbackOperazioniAurigaLottiDoc")
	@WebResult(name = "ResponseReqFeedbackElabDocsList", targetNamespace = "http://operazioniaurigaFeedbackElabDocs.webservices.repository2.auriga.eng.it", partName = "parameter")
	public ResponseReqFeedbackElabDocsList feedbackOperazioniAurigaLottiDoc(
			@WebParam(name = "RequestFeedbackElabDocsList", targetNamespace = "http://operazioniaurigaFeedbackElabDocs.webservices.repository2.auriga.eng.it", partName = "parameter") RequestFeedbackElabDocsList parameter)
			throws OperazioniAurigaLottiDocFault;

}
