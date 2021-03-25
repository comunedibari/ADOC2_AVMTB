/**
 * GestioneAttoServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public interface GestioneAttoServer extends java.rmi.Remote {
    public it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn salvaAllegato(it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn salvaAllegatoIn) throws java.rmi.RemoteException;
    public it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn pubblicaAtto(it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn pubblicaAttoIn) throws java.rmi.RemoteException;
    public it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn elencoTipiAtto() throws java.rmi.RemoteException;
    public it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn pubblicaAttoByEnte(it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEntePubblicaAttoByEnteIn pubblicaAttoByEnteIn) throws java.rmi.RemoteException;
    public it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn dettaglioAtto(it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn dettaglioAttoIn) throws java.rmi.RemoteException;
    public it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn elencoAtti(it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn elencoAttiIn) throws java.rmi.RemoteException;
}
