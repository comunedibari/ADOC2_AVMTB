/**
 * GestioneAttoServerImplPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server.impl;

public class GestioneAttoServerImplPortBindingStub extends org.apache.axis.client.Stub implements it.linksmt.avt.albopretorio.ws.server.GestioneAttoServer {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[6];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("salvaAllegato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "salvaAllegatoIn"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">salvaAllegato>salvaAllegatoIn"), it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">salvaAllegatoResponse>return"));
        oper.setReturnClass(it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("pubblicaAtto");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pubblicaAttoIn"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAtto>pubblicaAttoIn"), it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAttoResponse>return"));
        oper.setReturnClass(it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elencoTipiAtto");
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoTipiAttoResponse>return"));
        oper.setReturnClass(it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("pubblicaAttoByEnte");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pubblicaAttoByEnteIn"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAttoByEnte>pubblicaAttoByEnteIn"), it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEntePubblicaAttoByEnteIn.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAttoByEnteResponse>return"));
        oper.setReturnClass(it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("dettaglioAtto");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dettaglioAttoIn"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DettaglioAttoIn"), it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">dettaglioAttoResponse>return"));
        oper.setReturnClass(it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elencoAtti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "elencoAttiIn"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoAtti>elencoAttiIn"), it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoAttiResponse>return"));
        oper.setReturnClass(it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

    }

    public GestioneAttoServerImplPortBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public GestioneAttoServerImplPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public GestioneAttoServerImplPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.1");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "Atto");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.RichiestaSOAP.Atto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DettaglioAttoDto");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DettaglioAttoIn");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DocumentoWSDTO");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "ElencoAttiDto");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.RichiestaSOAP.ElencoAttiDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "GestioneAttoError");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "TipoAtto");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.RichiestaSOAP.TipoAtto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">dettaglioAttoResponse>return");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoAtti>elencoAttiIn");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoAttiResponse>return");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoTipiAttoResponse>return");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAtto>pubblicaAttoIn");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAttoByEnte>pubblicaAttoByEnteIn");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEntePubblicaAttoByEnteIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAttoByEnteResponse>return");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAttoResponse>return");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">salvaAllegato>salvaAllegatoIn");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">salvaAllegatoResponse>return");
            cachedSerQNames.add(qName);
            cls = it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn salvaAllegato(it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn salvaAllegatoIn) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("return");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "salvaAllegato"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {salvaAllegatoIn});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn) org.apache.axis.utils.JavaUtils.convert(_resp, it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn pubblicaAtto(it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn pubblicaAttoIn) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("return");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "pubblicaAtto"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pubblicaAttoIn});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn) org.apache.axis.utils.JavaUtils.convert(_resp, it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn elencoTipiAtto() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("return");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "elencoTipiAtto"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn) org.apache.axis.utils.JavaUtils.convert(_resp, it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn pubblicaAttoByEnte(it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEntePubblicaAttoByEnteIn pubblicaAttoByEnteIn) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("return");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "pubblicaAttoByEnte"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pubblicaAttoByEnteIn});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn) org.apache.axis.utils.JavaUtils.convert(_resp, it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn dettaglioAtto(it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn dettaglioAttoIn) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("return");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "dettaglioAtto"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dettaglioAttoIn});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn) org.apache.axis.utils.JavaUtils.convert(_resp, it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn elencoAtti(it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn elencoAttiIn) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("return");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "elencoAtti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {elencoAttiIn});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn) org.apache.axis.utils.JavaUtils.convert(_resp, it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
