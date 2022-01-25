Progetto:
AVB – Piattaforma documentale, Protocollo Informatico e Gestione Atti
Licenza
Il software è rilasciato con licenza di riuso ai sensi dell'art. 69 comma 1 del Codice dell’Amministrazione Digitale con una licenza EUPL-1.2
INTRODUZIONE
La piattaforma di ECM proposta è ADOC2_AVMTB, prodotto realizzato per offrire strumenti e servizi attraverso cui implementare qualsiasi processo che tratti documenti,
contenuti ed in generale informazioni di interesse per l’organizzazione, nel pieno rispetto delle normative vigenti e al tempo stesso usufruendo delle tecnologie più recenti ed efficienti.
ADOC2_AVMTB è una piattaforma che 

•	costituisce l’asset realizzato per le organizzazioni pubbliche e private. La piattaforma fornisce un’infrastruttura tecnologicamente avanzata basata su un’architettura di integrazione SOA e su componenti open-source collaudati e di comprovata affidabilità (Activiti BPM, Lucene, Open Office per citare alcuni esempi).


•	a partire dalle componenti applicative disponibili, consente di realizzare in modo semplice ed efficace nuovi servizi applicativi, improntati ai principi del Business Process Management;


•	grazie alla solida architettura SOA che la contraddistingue si integra agevolmente sia con i sistemi trasversali cooperanti – ad esempio di autenticazione e profilatura, di posta elettronica, di firma e timbro digitale, di archiviazione e conservazione della documentazione digitale/digitalizzata - che con gli applicativi e sistemi verticali, da un lato integrando i connettori verso i sistemi più diffusi (sistemi di firma remota, storage e DMS di mercato, sistemi di conservazione accreditati da AgID ecc), dall’altro offrendo per ogni funzionalità erogata servizi SOAP e/o REST da richiamare nonché strumenti per importazione ed esportazione massiva di dati e documenti.


Struttura del repository  
Il repository è così strutturato:

- ADOC2_AVMTBWeb
   
   Modulo per la componente web
- ADOC2_AVMTBBusiness
   
   Modulo per le funzionalità della logica di business
- ADOC2_AVMTBFileOperationWar
   
   Modulo per la gestione e verifica dei file
- ADOC2_AVMTBMail
   
   Modulo per l'invio delle mail
- ADOC2_AVMTBMailui
   
   Modulo per lo scarico delle mail
- ADOC2_AVMTBLib
   
   Modulo con le lib terze parti
- ADOC2_AVMTBDB
   
   Modulo DB

All’interno di ogni cartella è presente la cartella src con i relativi sorgenti
Elenco dettagliato prerequisiti e dipendenze
J2EE container:	Tomcat 8  o JBOSS EAP 7.2.0
RDBMS:	Postgress
Java Runtime:	JSE8, Java Platform Standard Edition 8
JDK e JRE Compatibility level:	JDK e JRE Compatibility level: JEE8, Java Platform Enterprise Edition 8.0
JAX-WS (Java API for XML Web Services)	Per realizzare web-service – SOAP e REST – e client che utilizzano XML per comunicare
Jersey	Per realizzare web-service REST e i relativi client (è l’implementazione di riferimento della specifica JAX-RS)
Smart GWT	Framework di base per la realizzazione delle web UI: si occupa del rendering del framework ajax GWT appoggiandosi alle librerie Smartclient
Spring	Per realizzare i componenti come applicazioni java enterprise facilmente configurabili e altamente riusabili
Hibernate	ORM utilizzato per l’accesso al database
Istruzioni per l’installazione
- Importare lo schema dati (modulo DB)
- Configurare file di properties
- Compilare e deployare i pacchetti software sull’application server di riferimento

Status del progetto:
Versione stabile

Detentori di copyright: 
Software rilasciato da Engineering spa per l’amministrazione comunale di Bari con possibilità di riuso per la pubblica amministrazione.

Nomi dei soggetti incaricati del mantenimento del progetto:
Engineering SPA

Per segnalazioni di sicurezza:
[Segnalazioni AVMTB](mailto:avb.protocollo@eslabs.eng.it?subject=[ADOC2]%20Sengalazione%20di%20Sicurezza)



