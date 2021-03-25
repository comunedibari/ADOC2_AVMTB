/**
 * GestioneAttoServerImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server.impl;

public class GestioneAttoServerImplServiceLocator extends org.apache.axis.client.Service implements it.linksmt.avt.albopretorio.ws.server.impl.GestioneAttoServerImplService {

    public GestioneAttoServerImplServiceLocator() {
    }


    public GestioneAttoServerImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GestioneAttoServerImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GestioneAttoServerImplPort
    private java.lang.String GestioneAttoServerImplPort_address = "http://10.0.5.14:80/albo-pretorio-ws/gestione-atto";

    public java.lang.String getGestioneAttoServerImplPortAddress() {
        return GestioneAttoServerImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GestioneAttoServerImplPortWSDDServiceName = "GestioneAttoServerImplPort";

    public java.lang.String getGestioneAttoServerImplPortWSDDServiceName() {
        return GestioneAttoServerImplPortWSDDServiceName;
    }

    public void setGestioneAttoServerImplPortWSDDServiceName(java.lang.String name) {
        GestioneAttoServerImplPortWSDDServiceName = name;
    }

    public it.linksmt.avt.albopretorio.ws.server.GestioneAttoServer getGestioneAttoServerImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GestioneAttoServerImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGestioneAttoServerImplPort(endpoint);
    }

    public it.linksmt.avt.albopretorio.ws.server.GestioneAttoServer getGestioneAttoServerImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.linksmt.avt.albopretorio.ws.server.impl.GestioneAttoServerImplPortBindingStub _stub = new it.linksmt.avt.albopretorio.ws.server.impl.GestioneAttoServerImplPortBindingStub(portAddress, this);
            _stub.setPortName(getGestioneAttoServerImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGestioneAttoServerImplPortEndpointAddress(java.lang.String address) {
        GestioneAttoServerImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.linksmt.avt.albopretorio.ws.server.GestioneAttoServer.class.isAssignableFrom(serviceEndpointInterface)) {
                it.linksmt.avt.albopretorio.ws.server.impl.GestioneAttoServerImplPortBindingStub _stub = new it.linksmt.avt.albopretorio.ws.server.impl.GestioneAttoServerImplPortBindingStub(new java.net.URL(GestioneAttoServerImplPort_address), this);
                _stub.setPortName(getGestioneAttoServerImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GestioneAttoServerImplPort".equals(inputPortName)) {
            return getGestioneAttoServerImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.server.ws.albopretorio.avt.linksmt.it/", "GestioneAttoServerImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.server.ws.albopretorio.avt.linksmt.it/", "GestioneAttoServerImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GestioneAttoServerImplPort".equals(portName)) {
            setGestioneAttoServerImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
