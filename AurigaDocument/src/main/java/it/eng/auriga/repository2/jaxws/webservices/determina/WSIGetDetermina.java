package it.eng.auriga.repository2.jaxws.webservices.determina;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * WSGetDetermina
 */

@WebService(targetNamespace="http://determina.webservices.jaxws.repository2.auriga.eng.it", name="WSGetDetermina")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public abstract interface WSIGetDetermina
{
  @WebResult(name="ResponseGetDetermina", targetNamespace="http://determina.webservices.jaxws.repository2.auriga.eng.it", partName="parameters")
  @WebMethod(operationName="GetDetermina", action="http://getdetermina.webservices.repository2.auriga.eng.it/GetDetermina")
  public abstract ResponseGetDetermina getDetermina(@WebParam(partName="parameters", name="RequestGetDetermina", targetNamespace="http://determina.webservices.jaxws.repository2.auriga.eng.it") it.eng.auriga.repository2.jaxws.webservices.determina.RequestGetDetermina paramRequestGetDetermina);
}