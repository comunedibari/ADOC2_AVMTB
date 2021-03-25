-- MOD_2016-10-06_RDM2196

---------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------
-- PROGETTO EGR-UNINA
-- SCRIPT DI CREAZIONE DI UNA CASELLA MAILBOX NELLE TABELLE USATE DAL MAILCONNECT SENZA USARE LA CONSOLE MAILUI
-- CREA LE CONFIGURAZIONI DI ENTRAMBI I FLUSSI DI SCARICO: 
-- 1 - DALLA INBOX PER LE RICEVUTE
-- 2 - DALLA DA-PROTOCOLLARE PER LE EMAIL CHE SI VORRANNO EVENTUALMENTE PROTOCOLLARE
---------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------
declare
     
  --------------------------------------------------------------------------------------------------------------------
  -- INIZIO Variabili che devono essere obbligatoriamente inizializzate seguendo scrupolosamente le regole indicate --
  --------------------------------------------------------------------------------------------------------------------
  
  -- Flag TRUE/FALSE per attivare o meno il commit di questa procedura
  flg_commit boolean := TRUE;

  -- Flag TRUE/FALSE per indicare se la casella è anche la PEI ovvero la PEC Istituzionale (TRUE), oppure no (FALSE)
  -- Deve essere obbligatoriamente impostato a TRUE o a FALSE
  flg_PEI boolean := TRUE;

  -- Indirizzo univoco di una PEC (univoca). Sarà utilizzato per valorizzare anche il campo: campo MAILBOX_ACCOUNT.ACCOUNT
  -- e se non è valorizzata la variabile my_username_account, sarà usata per valorizzare anche la username della casella
  -- all'interno della tabella MAILBOX_CONFIG_ACCOUNT.
  -- Esempi: 'aoo1@gestorepec.unina.it', 'aoo2@gestorepec.unina.it', ecc... 
  -- my_mailbox_account varchar2(150) := NULL;
  my_mailbox_account varchar2(150) := 'aoo3@gestorepec.unina.it';

  -- Nome breve univoco, identificativo della mailbox e che magari richiama mentalmente l'account definito con la variabile.
  -- La mailbox di riferimento è quella attiva per lo scarico dalla cartella INBOX. 
  -- my_mailbox_account. Questa variabile sarà utilizzata per valorizzare: i campi MAILBOX.ID_MAILBOX e MAILBOX.NAME,
  -- altre config del MailConnect (cartella in /allegati e storage.h2.db).
  -- Esempi: 'AOO1' per richiamare 'aoo1@gestorepec.unina.it', 'AOO2' per richiamare 'aoo2@gestorepec.unina.it', e di 
  -- conseguenza le cartelle associate da creare saranno: /allegati/email/AOO1 per lo scarico da INBOX, 
  -- /allegati/email/AOO1-P per lo scarico da DA-PROTOCOLLARE; /allegati/email/AOO2, /allegati/email/AOO2-P, ecc...
  -- my_mailbox_name varchar2(20) := NULL;
  my_mailbox_name varchar2(20) := 'AOO3'; 

  -- Stringa di connessione JDBC al database di di e-grammata.
  -- Esempi: 
  -- per il db di Test: 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.212.56)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))'
  -- per il db di Collaudo: 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.163.179)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))'
  -- per il db di Produzione: 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.212.26)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))'
  my_string_db_connection varchar2(200) := 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.163.179)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))';
  -- my_string_db_connection varchar2(200) := 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.212.56)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))';

  -- Valore parametro 'mail.imap.host', ovvero numero IP o nome del server IMAP, ad esempio in Test vale '192.132.34.238'
  -- my_account_config_imaphost VARCHAR2(400) := NULL;
  my_account_config_imaphost VARCHAR2(400) := '192.132.34.238';
  
  -- Valore parametro 'mail.imap.port', ovvero numero porta del server IMAP, ad esempio in Test vale '993'
  -- my_account_config_imapport VARCHAR2(400) := NULL;
  my_account_config_imapport VARCHAR2(400) := '993';
  
  -- Valore parametro 'mail.smtp.host', ovvero numero IP o nome del server SMTP, ad esempio in Test vale '192.132.34.238'
  -- my_account_config_smtphost VARCHAR2(400) := NULL;
  my_account_config_smtphost VARCHAR2(400) := '192.132.34.238';
  
  -- Valore parametro 'mail.smtp.port', ovvero numero porta del server IMAP, ad esempio in Test vale '465'
  -- my_account_config_smtpport VARCHAR2(400) := NULL;
  my_account_config_smtpport VARCHAR2(400) := '465';
  
  -- Username dell'account, per default è in pratica lo stesso valore della variabile my_mailbox_account.
  -- Pertanto valorizzare la variabile solo se il suo valore è diverso dal valore della variabile my_mailbox_account 
  -- Esempi: come per my_mailbox_account ad esempio aoo1@gestorepec.unina.it
  my_username_account varchar2(150) := my_mailbox_account;
  
  --------------------------------------------------------------------------------------------------------------------
  -- FINE Variabili che devono essere obbligatoriamente inizializzate seguendo scrupolosamente le regole indicate --
  --------------------------------------------------------------------------------------------------------------------


  --------------------------------------------------------------------------------------------------------------
  -- Variabili che NON devono essere inizializzate perchè usate dallo script automaticamente per i suoi scopi --
  --------------------------------------------------------------------------------------------------------------
  
  -- Contatore dei passi eseguiti dalla stored, utile per scopo debug
  step integer := 0;
  
  -- Campo MAILBOX_ACCOUNT.ID_ACCOUNT associato alla MAILBOX
  my_id_account VARCHAR2(40) := my_mailbox_name || '-ACCOUNT';
  
  -- Campo MAILBOX_ACCOUNT.ID_ACCOUNT associato alla MAILBOX se esiste già un record con MAILBOX.ACCOUNT = my_mailbox_account
  my_id_account_esistente VARCHAR2(40) := NULL;

  -- Campo MAILBOX.MAILBOX_ID coincidente con il nome, campo MAILBOX.NAME
  my_id_mailbox VARCHAR2(40) := my_mailbox_name;
  
  -- Nome e id della mailbox attiva per lo scarico dalla cartella DA-PROTOCOLLARE 
  -- che per convenzione ha il suffisso '-P' (richiama il fatto che è usata per la "Protocollazione" delle email)
  my_mailbox_name_P varchar2(20) := my_mailbox_name || '-P';
  my_id_mailbox_P VARCHAR2(40)   := my_mailbox_name_P;

  -- Nome delle cartelle usate per lo scarico: 'INBOX' e 'DA-PROTOCOLLARE'
  my_mailbox_folder_name VARCHAR2(20)      := 'INBOX';
  my_mailbox_folder_name_P VARCHAR2(20)    := 'DA-PROTOCOLLARE';

  -- Valori per valorizzare i parametri di configurazione in tabella MAILBOX_CONFIG
  -- Valore parametro ente che è fisso a 1 avendo questa versione di e-grammata un solo ente
  my_mailbox_config_ente VARCHAR2(400)                     := '1';
  -- Valore parametro utente e-grammata corrispondente a CVT_UTENTI.ID_UTE
  my_mailbox_config_utente VARCHAR2(400)                   := '124';
  my_mailbox_config_antispam VARCHAR2(400)                 := 'false';
  -- Valore parametro flag per indicare attivazione/disattivazione mailbox ovvero processo di scarico, all'avvio
  my_mailbox_config_autostart VARCHAR2(400)                := 'true';
  my_mailbox_config_debug VARCHAR2(400)                    := 'false';
  -- Valore parametro sul numero di ms che devono trascorrere per la prossima lettura cartella di scarico
  my_mailbox_config_delay VARCHAR2(400)                    := '5000';
  my_mailbox_config_delfs VARCHAR2(400)                    := 'false';
  -- Valore parametro cartella in cui vengono scaricate le email dalla INBOX
  my_mailbox_config_directory VARCHAR2(400)                := '/allegati/email/' || my_mailbox_name;
  -- Valore parametro cartella in cui vengono scaricate le email dalla DA-PROTOCOLLARE
  my_mailbox_config_directory_P VARCHAR2(400)              := '/allegati/email/' || my_mailbox_name_P;
  -- Valore parametro sul numero di email da scaricare ad ogni lettura della cartella di scarico
  my_mailbox_config_fetch VARCHAR2(400)                    := '5';
  my_mailbox_config_mailconnid VARCHAR2(400)               := '1';
  my_mailbox_config_maxtrynumop VARCHAR2(400)              := '10';
  my_mailbox_config_sentfolder VARCHAR2(400)               := 'Sent mail';
  -- Valore parametro 'mail.mailbox.cleardischarged': in caso di blocco e riavvio del mailconnect cancella
  -- i messaggi in stato DISCHARGED riscaricare i messaggi in tale stato e non bloccare il flusso di scarico
  my_mailbox_config_cldischarged VARCHAR2(400)             := 'true';

  -- Valori per valorizzare i parametri di configurazione in tabella MAILBOX_ACCOUNT_CONFIG
  -- Valore parametro 'mail.account.ispec', per indicare se l'account è di una PEC o casella email ordinaria
  my_account_config_ispec VARCHAR2(400)                  := 'true';
  -- Valore parametro 'mail.imap.auth'
  my_account_config_auth VARCHAR2(400)                   := 'true';
  -- Valore parametro 'mail.imap.auth.login.disable'
  my_account_config_authlogidisa VARCHAR2(400)           := 'true';
  -- Valore parametro 'mail.imap.socketFactory.class'
  my_account_config_sockfactclas VARCHAR2(400)           := 'java.net.ssl.SSLSocketFactory';   
  -- Valore parametro 'mail.imap.socketFactory.fallback'
  my_account_config_sockfactfall VARCHAR2(400)           := 'false';   
  -- Valore parametro 'mail.imap.ssl.enable'
  my_account_config_sslenable VARCHAR2(400)              := 'true';   
  -- Valore parametro 'mail.imap.store.protocol'
  my_account_config_storeprot VARCHAR2(400)              := 'imaps';   
  -- Valore parametro 'mail.mailbox.maxaddresstosend'
  my_account_config_maxaddrsend VARCHAR2(400)            := '10';   
  -- Valore parametro 'mail.password'
  my_account_config_password VARCHAR2(400)               := 'dlD-k1WelmtIGmt9OM6WlA';   
  -- Valore parametro 'mail.password.encryption'
  my_account_config_passwordenc VARCHAR2(400)            := 'true';   
  -- Valore parametro 'mail.smtp.auth'
  my_account_config_smtpauth VARCHAR2(400)               := 'true';   
  -- Valore parametro 'mail.smtp.auth.login.disable'
  my_account_config_smtpautlodi VARCHAR2(400)            := 'true';   
  -- Valore parametro 'mail.smtp.socketFactory.class'
  my_account_config_smtpsofacl VARCHAR2(400)             := 'javax.net.ssl.SSLSocketFactory';   
  -- Valore parametro 'mail.smtp.socketFactory.fallback'
  my_account_config_smtpsoffb VARCHAR2(400)              := 'false';   
  -- Valore parametro 'mail.smtp.ssl.enable'
  my_account_config_smtpsslenab VARCHAR2(400)            := 'true';   

  -- Flusso di scarico dalla INBOX
  my_mailbox_process_flow CLOB := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<processOperationFlow xmlns:ns2="http://www.digitPa.gov.it/protocollo/" xmlns:ns3="it.eng.fileoperation.ws.base">
    <start>
        <node>
            <nodeElaborate>
                <expression>1==1</expression>
                <operation>
                    <node>
                        <nodeElaborate>
                            <expression>1==1</expression>
                            <operation>
                                <node>
                                    <nodeElaborate>
                                        <expression>1==1</expression>
                                        <operation>
                                            <node>
                                                <nodeElaborate>
                                                    <expression>operation[''IsEgrMessageOperation''].getIsEgrMail() ' || chr(38) || 'amp;' || chr(38) || 'amp; messageinfo.isRicevuta()</expression>
                                                    <operation>
                                                        <node>
                                                            <nodeElaborate>
                                                                <expression>1==1</expression>
                                                                <operation>
                                                                    <operationid>MODEL-DELETEOP-5</operationid>
                                                                </operation>
                                                            </nodeElaborate>
                                                        </node>
                                                        <operationid>MODEL-COPYOP-5</operationid>
                                                    </operation>
                                                </nodeElaborate>
                                                <nodeElaborate>
                                                    <expression>(operation[''IsEgrMessageOperation''].getIsEgrMail() ' || chr(38) || 'amp;' || chr(38) || 'amp; operation[''InterOperation''].getType() != null) ? operation[''InterOperation''].getType().getName().equals(''ConfermaRicezione'') : false</expression>
                                                    <operation>
                                                        <node>
                                                            <nodeElaborate>
                                                                <expression>1==1</expression>
                                                                <operation>
                                                                    <operationid>MODEL-DELETEOP-4</operationid>
                                                                </operation>
                                                            </nodeElaborate>
                                                        </node>
                                                        <operationid>MODEL-COPYOP-4</operationid>
                                                    </operation>
                                                </nodeElaborate>
                                                <nodeElaborate>
                                                    <expression>(operation[''IsEgrMessageOperation''].getIsEgrMail() ' || chr(38) || 'amp;' || chr(38) || 'amp; operation[''InterOperation''].getType() != null) ? operation[''InterOperation''].getType().getName().equals(''AggiornamentoConferma'') : false</expression>
                                                    <operation>
                                                        <node>
                                                            <nodeElaborate>
                                                                <expression>1==1</expression>
                                                                <operation>
                                                                    <operationid>MODEL-DELETEOP-3</operationid>
                                                                </operation>
                                                            </nodeElaborate>
                                                        </node>
                                                        <operationid>MODEL-COPYOP-3</operationid>
                                                    </operation>
                                                </nodeElaborate>
                                                <nodeElaborate>
                                                    <expression>(operation[''IsEgrMessageOperation''].getIsEgrMail() ' || chr(38) || 'amp;' || chr(38) || 'amp; operation[''InterOperation''].getType() != null) ? operation[''InterOperation''].getType().getName().equals(''NotificaEccezione'') : false</expression>
                                                    <operation>
                                                        <node>
                                                            <nodeElaborate>
                                                                <expression>1==1</expression>
                                                                <operation>
                                                                    <operationid>MODEL-DELETEOP-2</operationid>
                                                                </operation>
                                                            </nodeElaborate>
                                                        </node>
                                                        <operationid>MODEL-COPYOP-2</operationid>
                                                    </operation>
                                                </nodeElaborate>
                                                <nodeElaborate>
                                                    <expression>(operation[''IsEgrMessageOperation''].getIsEgrMail() ' || chr(38) || 'amp;' || chr(38) || 'amp; operation[''InterOperation''].getType() != null) ? operation[''InterOperation''].getType().getName().equals(''AnnullamentoProtocollazione'') : false</expression>
                                                    <operation>
                                                        <node>
                                                            <nodeElaborate>
                                                                <expression>1==1</expression>
                                                                <operation>
                                                                    <operationid>MODEL-DELETEOP-1</operationid>
                                                                </operation>
                                                            </nodeElaborate>
                                                        </node>
                                                        <operationid>MODEL-COPYOP-1</operationid>
                                                    </operation>
                                                </nodeElaborate>
                                            </node>
                                            <operationid>MODEL-ISEGRMESSAGEOP</operationid>
                                        </operation>
                                    </nodeElaborate>
                                </node>
                                <operationid>MODEL-INTEROP</operationid>
                            </operation>
                        </nodeElaborate>
                    </node>
                    <operationid>MODEL-SIGNEROP</operationid>
                </operation>
            </nodeElaborate>
        </node>
        <operationid>1</operationid>
    </start>
</processOperationFlow>';

  -- Flusso di scarico dalla DA-PROTOCOLLARE
  my_mailbox_process_flow_P CLOB := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<processOperationFlow xmlns:ns2="http://www.digitPa.gov.it/protocollo/" xmlns:ns3="it.eng.fileoperation.ws.base">
    <start>
        <node>
            <nodeElaborate>
                <expression>1==1</expression>
                <operation>
                    <node>
                        <nodeElaborate>
                            <expression>1==1</expression>
                            <operation>
                                <node>
                                    <nodeElaborate>
                                        <expression>1==1</expression>
                                        <operation>
                                            <operationid>MODEL-P-EGRAMMATAOP</operationid>
                                        </operation>
                                    </nodeElaborate>
                                </node>
                                <operationid>MODEL-P-INTEROP</operationid>
                            </operation>
                        </nodeElaborate>
                    </node>
                    <operationid>MODEL-P-SIGNEROP</operationid>
                </operation>
            </nodeElaborate>
        </node>
        <operationid>1</operationid>
    </start>
</processOperationFlow>';

  -- Variabili per la PEI funzione e-grammata "Parametri Interoperabilità"
  my_des_aoo VARCHAR2(1000)                      := 'Università degli Studi di Napoli Federico II';
  my_template_subj_u VARCHAR2(1000)              := 'Messaggio di Protocollo (D.P.R. n. 445/2000) - n.ro{numProt} del {dataProt}';
  my_template_body_u VARCHAR2(1000)              := 'Amministrazione:[{codAmm}] {desAmm}, 
Area Organizzativa Omogenea: [{codAoo}] {desAoo}
 
In allegato al presente messaggio si trasmette la documentazione di cui siete destinatari, registrata in uscita nel sistema di Protocollo Informatico dell''Ateneo con n.ro  {numProt} del {dataProt}.
Cordiali saluti.
{desAoo} ';
  my_template_conferma VARCHAR2(1000)            := 'La e-mail e/o documentazione dal titolo:{emailMittenteTitolo}
è stata registrata nel Protocollo Informatico dell''{desAoo} con n.ro {numProt} del {dataProt}. 
Per maggiori informazioni rivolgersi all''URP.
Cordiali saluti.
{desAoo} ';
  my_template_eccezione VARCHAR2(1000)           := 'La e-mail e/o documentazione dal titolo:{emailMittenteTitolo}
è stata rifiutata dal protocollo dell''{desAoo} per i seguenti motivi: {motivoEccezione}.

Per maggiori informazioni consultare il Manuale di Gestione del Protocollo Informatico  dell''Università reperibile all''indirizzo: http://www.praxis.unina.it.
Cordiali saluti.
{desAoo} ';
  my_template_aggiorna VARCHAR2(1000)            := 'Amministrazione [{codAmm}] - {desAmm}
Area Organizzativa Omogenea [{codAoo}]  -{desAoo} 
Protocollo n.ro {numProt} del {dataProt}modificato con la seguente motivazione:
{motivoAggiornamento}. 
Cordiali saluti.
{desAmm}
';
  my_template_annulla VARCHAR2(1000)             := ' Amministrazione [{codAmm}] - {desAmm}
Area Organizzativa Omogenea [{codAoo}] - {desAoo} 
Il Protocollo n.ro {numProt} del {dataProt]} è stato annullato con la seguente motivazione:{motivoAnnullamento}.  
Cordiali saluti.
{desAOO} ';
    
  -- Variabili utili alla gestione delle eccezioni
  sql_error_code integer           := null;
  sql_error_msg varchar2(2000)     := null;
  my_exception EXCEPTION;
  
begin


  -- IMPOSTAZIONI PER I TEST
  /*
  flg_commit                       := TRUE;
  flg_PEI                          := FALSE;
  my_mailbox_account               := 'aoo3@pec.unina.it';
  my_mailbox_name                  := 'AOO3BIS'; 
  my_string_db_connection          := 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.212.56)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))';
  my_account_config_imaphost       := '192.132.34.238';
  my_account_config_imapport       := '993';
  my_account_config_smtphost       := '192.132.34.238';
  my_account_config_smtpport       := '465';
  my_id_account                    := my_mailbox_name || '-ACCOUNT';
  my_id_mailbox                    := my_mailbox_name;
  my_mailbox_name_P                := my_mailbox_name || '-P';
  my_id_mailbox_P                  := my_mailbox_name_P;
  my_mailbox_config_directory      := '/allegati/email/' || my_mailbox_name;
  my_mailbox_config_directory_P    := '/allegati/email/' || my_mailbox_name_P;
  */


  if ( trim( my_username_account ) is null ) then 
      my_username_account := my_mailbox_account;
  end if;

  dbms_output.put_line( '--------------------------------------------' );
  dbms_output.put_line( '-- Dati Configurazione mailbox e account -- ' );
  dbms_output.put_line( '--------------------------------------------' );
  if ( flg_commit ) then
    dbms_output.put_line( 'flg_commit                     = TRUE' );
  elsif ( not flg_commit ) then
    dbms_output.put_line( 'flg_commit                     = FALSE' );
  else
    dbms_output.put_line( 'flg_commit                     = NULL' );
  end if;
  if ( flg_PEI ) then
    dbms_output.put_line( 'flg_PEI                        = TRUE' );
  elsif ( not flg_PEI ) then
    dbms_output.put_line( 'flg_PEI                        = FALSE' );
  else
    dbms_output.put_line( 'flg_PEI                        = NULL' );
  end if;
  dbms_output.put_line( 'my_mailbox_account             = ' || my_mailbox_account );
  dbms_output.put_line( 'my_mailbox_name                = ' || my_mailbox_name );
  dbms_output.put_line( 'my_username_account            = ' || my_username_account );
  dbms_output.put_line( 'my_string_db_connection        = ' || my_string_db_connection );
  dbms_output.put_line( 'my_account_config_imaphost     = ' || my_account_config_imaphost );
  dbms_output.put_line( 'my_account_config_imapport     = ' || my_account_config_imapport );
  dbms_output.put_line( 'my_account_config_smtphost     = ' || my_account_config_smtphost );
  dbms_output.put_line( 'my_account_config_smtpoort     = ' || my_account_config_smtpport );
  dbms_output.put_line( 'my_id_account                  = ' || my_id_account );
  dbms_output.put_line( 'my_id_mailbox                  = ' || my_id_mailbox );
  dbms_output.put_line( 'my_mailbox_name_P              = ' || my_mailbox_name_P );
  dbms_output.put_line( 'my_id_mailbox_P                = ' || my_id_mailbox_P );
  dbms_output.put_line( 'my_mailbox_folder_name         = ' || my_mailbox_folder_name );
  dbms_output.put_line( 'my_mailbox_folder_name_P       = ' || my_mailbox_folder_name_P );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-----------------------' );
  dbms_output.put_line( '-- FASE 0: CONTROLLI --' );
  dbms_output.put_line( '-----------------------' );
  dbms_output.put_line( '' );

  step := step + 1;
  if ( flg_commit is null ) then 
      dbms_output.put_line( 'la variabile flg_commit deve essere impostata al valore TRUE o FALSE' );
      raise my_exception;
  end if;

  step := step + 1;
  if ( flg_PEI is null ) then 
      dbms_output.put_line( 'la variabile flg_PEI deve essere impostata al valore TRUE o FALSE' );
      raise my_exception;
  end if;

  step := step + 1;
  if ( trim( my_mailbox_account ) is null ) then 
      dbms_output.put_line( 'la variabile my_mailbox_account deve essere valorizzata' );
      raise my_exception;
  end if;

  step := step + 1;
  if ( trim( my_mailbox_name ) is null ) then 
      dbms_output.put_line( 'la variabile my_mailbox_name deve essere valorizzata' );
      raise my_exception;
  elsif instr( my_mailbox_name, '_' ) > 0 then
      dbms_output.put_line( 'la variabile my_mailbox_name non deve contenere il carattere underscore: ''_''' );
      raise my_exception;
  end if;

  step := step + 1;
  if ( trim( my_string_db_connection ) is null ) then 
      dbms_output.put_line( 'la variabile my_string_db_connection deve essere valorizzata' );
      raise my_exception;
  end if;

  step := step + 1;
  if ( trim( my_account_config_imaphost ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_imaphost deve essere valorizzata' );
      raise my_exception;
  end if;

  step := step + 1;
  if ( trim( my_account_config_imapport ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_imapport deve essere valorizzata' );
      raise my_exception;
  end if;
  
  step := step + 1;
  if ( trim( my_account_config_smtphost ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_smtphost deve essere valorizzata' );
      raise my_exception;
  end if;

  step := step + 1;
  if ( trim( my_account_config_smtpport ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_smtpport deve essere valorizzata' );
      raise my_exception;
  end if;

  dbms_output.put_line( 'Controlli eseguiti.' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------------------------------------------------------------------------' );
  dbms_output.put_line( '-- FASE 1: CREAZIONE DELLE MAILBOX ATTIVE SU INBOX E DA_PROTOCOLLARE CON LE RELATIVE OPERATION --' );
  dbms_output.put_line( '-------------------------------------------------------------------------------------------------' );
  dbms_output.put_line( '' );
  
  -- Creazione record in MAILBOX_ACCOUNT
  step := step + 1;
  begin
    select ID_ACCOUNT into my_id_account_esistente from MAILBOX_ACCOUNT where ACCOUNT = my_mailbox_account; 
    dbms_output.put_line( 'step = ' || step || ': Record in MAILBOX_ACCOUNT con ACCOUNT = ' || my_mailbox_account || ' già esistente, my_id_account_esistente = ' || my_id_account_esistente );
    my_id_account := my_id_account_esistente;
  exception when no_data_found then
    insert into MAILBOX_ACCOUNT ( ID_ACCOUNT, ACCOUNT ) values ( my_id_account, my_mailbox_account );
    dbms_output.put_line( 'step = ' || step || ': Creazione record in MAILBOX_ACCOUNT eseguita' );
  end;

  -- Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX
  step := step + 1;
  insert into MAILBOX ( ID_MAILBOX, DESCRIPTION, NAME, PROCESS_FLOW, STATUS, FOLDER, ID_ACCOUNT )
  values ( my_id_mailbox, my_mailbox_name || ' - ' || my_mailbox_folder_name, my_mailbox_name, replace( my_mailbox_process_flow, 'MODEL', my_id_mailbox ), 'active', my_mailbox_folder_name, my_id_account );
  dbms_output.put_line( 'step = ' || step || ': Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX eseguita' );
 
  -- Creazione record in MAILBOX relativo allo scarico dalla cartella DA-PROTOCOLLARE
  step := step + 1;
  insert into MAILBOX ( ID_MAILBOX, DESCRIPTION, NAME, PROCESS_FLOW, STATUS, FOLDER, ID_ACCOUNT )
  values ( my_id_mailbox_P, my_mailbox_name_P || ' - ' || my_mailbox_folder_name_P, my_mailbox_name_P, replace( my_mailbox_process_flow_P, 'MODEL-P', my_id_mailbox_P ), 'active', my_mailbox_folder_name_P, my_id_account );
  dbms_output.put_line( 'step = ' || step || ': Creazione record in MAILBOX relativo allo scarico dalla cartella DA-PROTOCOLLARE eseguita' );

  -- Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata 
  -- Questa operation anche se utilizzata in tutti i flussi avendo id unico, viene referenziata da tutte le MAILBOX
  -- anche se inizialmente viene associata ad una sola MAILBOX.
  step := step + 1;
  begin
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( '1', 'StartOperation', my_id_mailbox );
  exception when others then
    null;
  end;
  dbms_output.put_line( 'step = ' || step || ': Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata, eseguita' );
  
  -- Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
  step := step + 1;
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-SIGNEROP', 'SignerOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-INTEROP', 'InterOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-ISEGRMESSAGEOP', 'IsEgrMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-1', 'CopyMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-2', 'CopyMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-3', 'CopyMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-4', 'CopyMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-5', 'CopyMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-1', 'DeleteMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-2', 'DeleteMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-3', 'DeleteMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-4', 'DeleteMessageOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-5', 'DeleteMessageOperation', my_id_mailbox );
  dbms_output.put_line( 'step = ' || step || ': Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX eseguita' );

  -- Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
  step := step + 1;
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'isegrmessageoperation.databaseurl',  my_id_mailbox || '-ISEGRMESSAGEOP', my_string_db_connection );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-1', my_mailbox_folder_name_P );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-2', my_mailbox_folder_name_P );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-3', my_mailbox_folder_name_P );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-4', my_mailbox_folder_name_P );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-5', my_mailbox_folder_name_P );
  dbms_output.put_line( 'step = ' || step || ': Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX eseguita' );

  -- Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE
  step := step + 1;
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox_P || '-SIGNEROP', 'SignerOperation', my_id_mailbox_P );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox_P || '-INTEROP', 'InterOperation', my_id_mailbox_P );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox_P || '-EGRAMMATAOP', 'EGrammataMessageOperation', my_id_mailbox_P );
  dbms_output.put_line( 'step = ' || step || ': Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE eseguita' );

  -- Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE
  step := step + 1;
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.tempfsobject', my_id_mailbox_P || '-EGRAMMATAOP', '/allegati/temp' );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.rootfsobject', my_id_mailbox_P || '-EGRAMMATAOP', '/allegati/downloads' );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.folderprimario', my_id_mailbox_P || '-EGRAMMATAOP', 'email' );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.databaseurl', my_id_mailbox_P || '-EGRAMMATAOP', my_string_db_connection );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.attivasostituzioneunicodetestomail', my_id_mailbox_P || '-EGRAMMATAOP', 'true' );
  dbms_output.put_line( 'step = ' || step || ': Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE eseguita' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-----------------------------------------------------------' );
  dbms_output.put_line( '-- FASE 2: CONFIGURAZIONE MAILBOX TABELLA MAILBOX_CONFIG --' );
  dbms_output.put_line( '-----------------------------------------------------------' );
  dbms_output.put_line( '' );
  
  -- Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella INBOX
  step := step + 1;
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.ente', my_id_mailbox, my_mailbox_config_ente );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.utente', my_id_mailbox, my_mailbox_config_utente );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.antispam', my_id_mailbox, my_mailbox_config_antispam );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.autostart', my_id_mailbox, my_mailbox_config_autostart );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.debug', my_id_mailbox, my_mailbox_config_debug );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.delay', my_id_mailbox, my_mailbox_config_delay );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.deletetofilesystem', my_id_mailbox, my_mailbox_config_delfs );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.directory', my_id_mailbox, my_mailbox_config_directory );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.fetch', my_id_mailbox, my_mailbox_config_fetch );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.mailconnectid', my_id_mailbox, my_mailbox_config_mailconnid );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.maxtrynumoperation', my_id_mailbox, my_mailbox_config_maxtrynumop );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.sentfolder.name', my_id_mailbox, my_mailbox_config_sentfolder );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.cleardischarged', my_id_mailbox, my_mailbox_config_cldischarged );
  dbms_output.put_line( 'step = ' || step || ': Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella INBOX eseguita' );

  -- Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella DA-PROTOCOLLARE
  step := step + 1;
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.ente', my_id_mailbox_P, my_mailbox_config_ente );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.utente', my_id_mailbox_P, my_mailbox_config_utente );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.antispam', my_id_mailbox_P, my_mailbox_config_antispam );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.autostart', my_id_mailbox_P, my_mailbox_config_autostart );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.debug', my_id_mailbox_P, my_mailbox_config_debug );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.delay', my_id_mailbox_P, my_mailbox_config_delay );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.deletetofilesystem', my_id_mailbox_P, my_mailbox_config_delfs );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.directory', my_id_mailbox_P, my_mailbox_config_directory_P );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.fetch', my_id_mailbox_P, my_mailbox_config_fetch );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.mailconnectid', my_id_mailbox_P, my_mailbox_config_mailconnid );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.maxtrynumoperation', my_id_mailbox_P, my_mailbox_config_maxtrynumop );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.sentfolder.name', my_id_mailbox_P, my_mailbox_config_sentfolder );
  insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.cleardischarged', my_id_mailbox_P, my_mailbox_config_cldischarged );
  dbms_output.put_line( 'step = ' || step || ': Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella DA-PROTOCOLLARE eseguita' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------------------------------------------' );
  dbms_output.put_line( '-- FASE 3: CONFIGURAZIONE ACCOUNT TABELLA MAILBOX_ACCOUNT_CONFIG --' );
  dbms_output.put_line( '-------------------------------------------------------------------' );
  dbms_output.put_line( '' );
  
  step := step + 1;
  if ( my_id_account_esistente is null ) then
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.account.ispec', my_account_config_ispec, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.auth', my_account_config_auth, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.auth.login.disable', my_account_config_authlogidisa, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.host', my_account_config_imaphost, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.port', my_account_config_imapport, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.socketFactory.class', my_account_config_sockfactclas, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.socketFactory.fallback', my_account_config_sockfactfall, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.ssl.enable', my_account_config_sslenable, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.store.protocol', my_account_config_storeprot, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.mailbox.maxaddresstosend', my_account_config_maxaddrsend, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.password', my_account_config_password, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.password.encryption', my_account_config_passwordenc, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.auth', my_account_config_smtpauth, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.auth.login.disable', my_account_config_smtpautlodi, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.host', my_account_config_smtphost, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.port', my_account_config_smtpport, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.socketFactory.class', my_account_config_smtpsofacl, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.socketFactory.fallback', my_account_config_smtpsoffb, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.ssl.enable', my_account_config_smtpsslenab, my_id_account );
    insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.username', my_username_account, my_id_account );
    dbms_output.put_line( 'step = ' || step || ': Creazione records in MAILBOX_ACCOUNT_CONFIG relativi all'' ACCOUNT eseguita' );
  else
    dbms_output.put_line( 'step = ' || step || ': Records in MAILBOX_ACCOUNT_CONFIG relativi all'' ACCOUNT sono già esistenti' );
  end if;

  step := step + 1;
  if ( flg_PEI ) then
    
    dbms_output.put_line( '' );
    dbms_output.put_line( '-----------------------------------------------------------------------------------------------------' );
    dbms_output.put_line( '-- FASE 4: CONFIGURAZIONE PARAMETRI PEI PER LA FUNZIONE DI E-GRAMMATA ''PARAMETRI INTEROPERABILITA'' --' );
    dbms_output.put_line( '-----------------------------------------------------------------------------------------------------' );
    dbms_output.put_line( '' );

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_DES_AOO WHERE codice_par = 'DES_AOO';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'DES_AOO', my_des_aoo, my_mailbox_config_ente, 'Descrizione AOO' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_mailbox_account WHERE codice_par = 'SMTP_PEI_ACCOUNT';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'SMTP_PEI_ACCOUNT', my_mailbox_account, my_mailbox_config_ente, 'Indirizzo PEI' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_account_config_imaphost WHERE codice_par = 'IMAP_SERVER';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE,DESCRIZIONE ) 
        VALUES ( 'IMAP_SERVER', my_account_config_imaphost, my_mailbox_config_ente, 'PEC-CONNECT: IMAP Server.' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_account_config_imapport WHERE codice_par = 'IMAP_PORT';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'IMAP_PORT', my_account_config_imapport, my_mailbox_config_ente, 'PEC-CONNECT: IMAP porta.' );
      end if;
    END;
    
    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_account_config_sslenable WHERE codice_par = 'IMAP_SSL';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'IMAP_SSL', my_account_config_sslenable, my_mailbox_config_ente, 'PEC-CONNECT: IMAP SSL.' );
      end if;
    END;
    
    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_username_account WHERE codice_par = 'IMAP_USERNAME';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'IMAP_USERNAME', my_username_account, my_mailbox_config_ente, 'PEC-CONNECT: IMAP Username.' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_subj_u WHERE codice_par = 'TEMPLATE_SUBJ_USCITA';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'TEMPLATE_SUBJ_USCITA', my_template_subj_u, my_mailbox_config_ente, 'Template per l''oggetto (subject) delle email in uscita dalla PEI e relative a registrazioni con destinatari esterni' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_body_u WHERE codice_par = 'TEMPLATE_BODY_USCITA';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'TEMPLATE_BODY_USCITA', my_template_body_u, my_mailbox_config_ente, 'Template per il testo (body) delle email in uscita dalla PEI e relative a registrazioni con destinatari esterni' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_conferma WHERE codice_par = 'TEMPLATE_CONFERMA';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'TEMPLATE_CONFERMA', my_template_conferma, my_mailbox_config_ente, 'Template per il testo della conferma' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_eccezione WHERE codice_par = 'TEMPLATE_ECCEZIONE';
      if sql%rowcount=0 then 
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'TEMPLATE_ECCEZIONE', my_template_eccezione, my_mailbox_config_ente, 'Template per il testo dell''eccezione' );
      end if;
    END;
    
    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_aggiorna WHERE codice_par = 'TEMPLATE_AGGIORNA';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'TEMPLATE_AGGIORNA', my_template_aggiorna, my_mailbox_config_ente, 'Template per il testo dell''aggiornamento' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_annulla WHERE codice_par = 'TEMPLATE_ANNULLA';
      if sql%rowcount=0 then 
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE) 
        VALUES ( 'TEMPLATE_ANNULLA', my_template_annulla, my_mailbox_config_ente, 'Template per il testo dell''annullamento' );
      end if;
    END;

    dbms_output.put_line( 'step = ' || step || ': L''account è della PEI, pertanto ho configurato anche i parametri della funzione ''Parametri Interoperabilità''' );

  else
    dbms_output.put_line( 'step = ' || step || ': L''account non è la PEI pertanto non ho configurato i parametri della funzione ''Parametri Interoperabilità''' );
  end if;

  if ( flg_commit ) then
     dbms_output.put_line( '' );
     dbms_output.put_line( 'flg_commit = TRUE --> ESEGUITO COMMIT!' );
     dbms_output.put_line( '' );
     commit;
  else
     dbms_output.put_line( '' );
     dbms_output.put_line( 'flg_commit = FALSE --> ESEGUITO ROLLBACK!' );
     dbms_output.put_line( '' );
     rollback;
  end if;

  dbms_output.put_line( '' );
  dbms_output.put_line( '---------------------------------------------' );
  dbms_output.put_line( '-- ESITO: PROCEDURA TERMINATA CON SUCCESSO --' );
  dbms_output.put_line( '---------------------------------------------' );
  dbms_output.put_line( '' );

exception when others then

  sql_error_code := SQLCODE;
  sql_error_msg  := SQLERRM;

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------------------' );
  dbms_output.put_line( '-- ESITO: PROCEDURA TERMINATA CON ERRORI --' );
  dbms_output.put_line( '-------------------------------------------' );
  dbms_output.put_line( '' );

  dbms_output.put_line( 'Errore allo step: ' || step );
  dbms_output.put_line( 'sql_error_code = ' || sql_error_code );
  dbms_output.put_line( 'sql_error_msg  = ' || sql_error_msg );

  rollback;
  
  dbms_output.put_line( 'ESEGUITO ROLLBACK!' );
  
end;
