-- MOD_2017-02-02_RDM2219

-----------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------
-- PROGETTO EGR-UNINA
-- DESCRIZIONE:
-- Script di creazione massiva di N caselle MAILBOX, nelle tabelle usate dal MAILCONNECT senza usare la console mailui. 
-- PREREQUISITI:
-- Creare un file di testo CSV, che chiameremo per esempio "input.txt" in cui ciascuna riga rappresenta i dati minimi 
-- di una nuova casella pec di struttura, da configurare:
-- 1 - nome mailbox (obbligatorio)
-- 2 - account, ovvero indirizzo PEC (obbligatorio)
-- 3 - ID_UO cui associare l'account (opzionale)
-- Questo è un esempio di contenuto di un tale file di testo CSV:
-- AOO1,aoo1@gestorepec.unina.it,259
-- AOO2,aoo2@gestorepec.unina.it
-- AOO3,aoo3@gestorepec.unina.it
-- AOO4,aoo4@gestorepec.unina.it,382
-- AOO5,aoo5@gestorepec.unina.it,382
-- Copiare e incollare il contenuto del file "input.txt" nel campo T_APPOGGIO.CAMPO_CLOB_INPUT_01.
-- PROCESSO:
-- Lo script leggerà il campo T_APPOGGIO.CAMPO_CLOB_INPUT_01, riga per riga intercettando il carattere LR e/o CR
-- come delimitatore di ciascuna riga e per ciascuna riga estrae i dati separati da virgola:
-- 1 - nome mailbox (obbligatorio)
-- 2 - account, ovvero indirizzo PEC (obbligatorio)
-- 3 - ID_UO cui associare l'account (opzionale)
-- A fronte di questi dati creerà le configurazioni per: 
-- 1 - l'account se non esiste già;
-- 2 - Il flusso di scarico dalla folder INBOX per le ricevute PEC;
-- 3 - il flusso di scarico dalla folder DA-PROTOCOLLARE per le PEC protocollabili.
-- Un qualunque errore durante l'eseuczione dello script, causarà il ROLLBACK dell'intera transazione.
-- Pertanto, al termine saranno create o TUTTE le caselle o NESSUNA casella.
-- AUTORE: 
-- Roberto Boldrin
-----------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------

declare
     
  --------------------------------------------------------------------------------------------------------------------
  -- INIZIO Variabili che devono essere obbligatoriamente inizializzate seguendo scrupolosamente le regole indicate --
  --------------------------------------------------------------------------------------------------------------------
  
  -- Flag TRUE/FALSE per attivare o meno il commit di questa procedura
  flg_commit boolean := TRUE;

  -- Stringa di connessione JDBC al database di di e-grammata.
  -- Esempi: 
  -- per il db di Test: 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.212.56)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))'
  -- per il db di Collaudo: 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.163.179)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))'
  -- per il db di Produzione: 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.212.26)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))'
  my_string_db_connection varchar2(200) := 'jdbc:oracle:thin:ente01/ente01@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=143.225.212.56)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PROT)))';

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
  
  --------------------------------------------------------------------------------------------------------------------
  -- FINE Variabili che devono essere obbligatoriamente inizializzate seguendo scrupolosamente le regole indicate --
  --------------------------------------------------------------------------------------------------------------------


  --------------------------------------------------------------------------------------------------------------
  -- Variabili che NON devono essere inizializzate perchè usate dallo script automaticamente per i suoi scopi --
  --------------------------------------------------------------------------------------------------------------

  -- Indirizzo univoco di una PEC (univoca). Sarà utilizzato per valorizzare anche il campo: campo MAILBOX_ACCOUNT.ACCOUNT
  -- e se non è valorizzata la variabile my_username_account, sarà usata per valorizzare anche la username della casella
  -- all'interno della tabella MAILBOX_CONFIG_ACCOUNT.
  -- Esempi: 'aoo1@gestorepec.unina.it', 'aoo2@gestorepec.unina.it', ecc... 
  -- my_mailbox_account varchar2(150) := NULL;
  my_mailbox_account varchar2(150) := NULL;

  -- Nome breve univoco, identificativo della mailbox e che magari richiama mentalmente l'account definito con la variabile.
  -- La mailbox di riferimento è quella attiva per lo scarico dalla cartella INBOX. 
  -- my_mailbox_account. Questa variabile sarà utilizzata per valorizzare: i campi MAILBOX.ID_MAILBOX e MAILBOX.NAME,
  -- altre config del MailConnect (cartella in /allegati e storage.h2.db).
  -- Esempi: 'AOO1' per richiamare 'aoo1@gestorepec.unina.it', 'AOO2' per richiamare 'aoo2@gestorepec.unina.it', e di 
  -- conseguenza le cartelle associate da creare saranno: /allegati/email/AOO1 per lo scarico da INBOX, 
  -- /allegati/email/AOO1-P per lo scarico da DA-PROTOCOLLARE; /allegati/email/AOO2, /allegati/email/AOO2-P, ecc...
  -- my_mailbox_name varchar2(20) := NULL;
  my_mailbox_name varchar2(20) := NULL; 
  
  -- Username dell'account, per default è in pratica lo stesso valore della variabile my_mailbox_account.
  -- Pertanto valorizzare la variabile solo se il suo valore è diverso dal valore della variabile my_mailbox_account 
  -- Esempi: come per my_mailbox_account ad esempio aoo1@gestorepec.unina.it
  my_username_account varchar2(150) := NULL;

  -- Contatore dei passi eseguiti dalla stored, utile per scopo debug
  step integer := 0;
  
  -- Contatore dei passi eseguiti all'interno del ciclo di lettura campo T_APPOGGIO.CAMPO_CLOB_INPUT_01, utile per scopo debug
  step_ciclo integer := 0;

  -- Contatore dei sotto-passi all'interno del passo i-esimo del ciclo di lettura campo T_APPOGGIO.CAMPO_CLOB_INPUT_01, utile per scopo debug
  step_ciclo_sotto VARCHAR2(10) := NULL;

  -- Campo MAILBOX_ACCOUNT.ID_ACCOUNT associato alla MAILBOX
  my_id_account VARCHAR2(40) := NULL;
  
  -- Campo MAILBOX_ACCOUNT.ID_ACCOUNT associato alla MAILBOX se esiste già un record con MAILBOX.ACCOUNT = my_mailbox_account
  my_id_account_esistente VARCHAR2(40) := NULL;

  -- Campo MAILBOX.MAILBOX_ID coincidente con il nome, campo MAILBOX.NAME
  my_id_mailbox VARCHAR2(40) := NULL;
  
  -- Nome e id della mailbox attiva per lo scarico dalla cartella DA-PROTOCOLLARE 
  -- che per convenzione ha il suffisso '-P' (richiama il fatto che è usata per la "Protocollazione" delle email)
  my_mailbox_name_P varchar2(20) := NULL;
  my_id_mailbox_P VARCHAR2(40)   := NULL;

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
  my_mailbox_config_directory VARCHAR2(400)                := NULL;
  -- Valore parametro cartella in cui vengono scaricate le email dalla DA-PROTOCOLLARE
  my_mailbox_config_directory_P VARCHAR2(400)              := NULL;
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

  -- Variabili utili alla gestione delle eccezioni
  sql_error_code INTEGER           := NULL;
  sql_error_msg VARCHAR2(2000)     := NULL;
  my_exception EXCEPTION;
  
  -- Variabili per la lettura del campo T_APPOGGIO.CAMPO_CLOB_INPUT_01 che rappresenta il file CSV 
  -- in cui ogni riga rappresenta una lista di due valori separati da virgola che sono: il nome della casella e l'indirizzo pec
  my_clob_temp CLOB                  := NULL;
  my_clob_temp_length NUMBER         := NULL;
  riga_clob VARCHAR2(32767)          := NULL;
  indice_riga NUMBER                 := 1;
  my_mailbox_data STRING_VARRAY      := NULL;
  prefisso_log_ciclo VARCHAR2(100)   := NULL;
  char_LF VARCHAR2(1)                := chr(10);
  char_CR VARCHAR2(1)                := chr(13);
  position_start NUMBER              := 1;
  position_end NUMBER                := 1; 
  line_length NUMBER                 := 0;
  count_temp NUMBER                  := 0;
  my_id_uo_pec NUMBER                := NULL;
  
  -- Variabili per produrre nel campo T_APPOGGIO.CAMPO_CLOB_INPUT_01, la serie di istruzioni SQL da eseguire su storage.h2.db
  my_clob_output_temp CLOB           := NULL;
  my_riga_output_temp VARCHAR2(1000) := NULL;
  insert_storage_1 VARCHAR2(1000)    := 'INSERT INTO "PUBLIC"."STORAGES" (ID_STORAGE,FLG_DISATTIVO,TIPO_STORAGE,XML_CONFIG) VALUES (''[MODEL]'',0,''FS'',''<?xml version="1.0" encoding="UTF-8" standalone="yes"?><fileSystemStorageConfig><baseFolderPath>/allegati/email/[MODEL]</baseFolderPath><repositoryLocations>/allegati/email/[MODEL]</repositoryLocations><tempRepositoryBasePath>/allegati/email/temp</tempRepositoryBasePath></fileSystemStorageConfig>'');';
  insert_storage_2 VARCHAR2(1000)    := 'INSERT INTO "PUBLIC"."UTILIZZATORI_STORAGE" (ID_UTILIZZATORE,ID_STORAGE) VALUES (''MailConnect.[MODEL]'',''[MODEL]'');';
  
begin

  -- Rende illimitato il buffer per l'output
  dbms_output.enable( NULL );

  dbms_output.put_line( '' );
  dbms_output.put_line( '---------------------------------' );      
  dbms_output.put_line( '-- INIZIO: ' || to_char( sysdate, 'dd/mm/yyyy hh24:mi:ss' ) || ' --' );
  dbms_output.put_line( '---------------------------------' );      
  dbms_output.put_line( '' );

  dbms_output.put_line( '-----------------------------------------------------' );
  dbms_output.put_line( '-- Dati Configurazione mailbox e account - INIZIO -- ' );
  dbms_output.put_line( '-----------------------------------------------------' );
  dbms_output.put_line( '' );

  if ( flg_commit ) then
    dbms_output.put_line( 'flg_commit                     = TRUE' );
  elsif ( not flg_commit ) then
    dbms_output.put_line( 'flg_commit                     = FALSE' );
  else
    dbms_output.put_line( 'flg_commit                     = NULL' );
  end if;
  dbms_output.put_line( 'my_string_db_connection        = ' || my_string_db_connection );
  dbms_output.put_line( 'my_account_config_imaphost     = ' || my_account_config_imaphost );
  dbms_output.put_line( 'my_account_config_imapport     = ' || my_account_config_imapport );
  dbms_output.put_line( 'my_account_config_smtphost     = ' || my_account_config_smtphost );
  dbms_output.put_line( 'my_account_config_smtpoort     = ' || my_account_config_smtpport );

  dbms_output.put_line( '' );
  dbms_output.put_line( '---------------------------------------------------' );
  dbms_output.put_line( '-- Dati Configurazione mailbox e account - FINE -- ' );
  dbms_output.put_line( '---------------------------------------------------' );
  dbms_output.put_line( '' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '--------------------------------' );
  dbms_output.put_line( '-- FASE 0: CONTROLLI - INIZIO --' );
  dbms_output.put_line( '--------------------------------' );
  dbms_output.put_line( '' );

  -- Controllo numero records in T_APPOGGIO 
  step := 1;
  count_temp := 0;
  select count(*) into count_temp from T_APPOGGIO;
  if ( count_temp = 0 ) then
    dbms_output.put_line( 'step = ' || step || ': La tabella T_APPOGGIO non ha nessun record: essa deve contenere uno ed un solo record' );
    raise my_exception;
  elsif ( count_temp > 1 ) then
    dbms_output.put_line( 'step = ' || step || ': La tabella T_APPOGGIO ha più di un record: essa deve contenere uno ed un solo record' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo numero records in T_APPOGGIO, eseguito' );
  
  step := 2;
  if ( flg_commit is null ) then
    dbms_output.put_line( 'step = ' || step || ': La variabile flg_commit deve essere impostata al valore TRUE o FALSE' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile flg_commit, eseguito' );

  -- Controllo variabile my_string_db_connection 
  step := 3;
  if ( trim( my_string_db_connection ) is null ) then 
    dbms_output.put_line( 'step = ' || step || ': La variabile my_string_db_connection deve essere valorizzata' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_string_db_connection, eseguito' );

  -- Controllo variabile my_account_config_imaphost 
  step := 4;
  if ( trim( my_account_config_imaphost ) is null ) then 
    dbms_output.put_line( 'step = ' || step || ': La variabile my_account_config_imaphost deve essere valorizzata' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_imaphost, eseguito' );

  -- Controllo variabile my_account_config_imapport 
  step := 5;
  if ( trim( my_account_config_imapport ) is null ) then 
    dbms_output.put_line( 'step = ' || step || ': La variabile my_account_config_imapport deve essere valorizzata' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_imapport, eseguito' );
  
  -- Controllo variabile my_account_config_smtphost 
  step := 6;
  if ( trim( my_account_config_smtphost ) is null ) then 
    dbms_output.put_line( 'step = ' || step || ': La variabile my_account_config_smtphost deve essere valorizzata' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_smtphost, eseguito' );

  -- Controllo variabile my_account_config_smtpport 
  step := 7;
  if ( trim( my_account_config_smtpport ) is null ) then 
    dbms_output.put_line( 'step = ' || step || ': La variabile my_account_config_smtpport deve essere valorizzata' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_smtpport, eseguito' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '------------------------------' );
  dbms_output.put_line( '-- FASE 0: CONTROLLI - FINE --' );
  dbms_output.put_line( '------------------------------' );
  dbms_output.put_line( '' );

  -- Inizializzo il CLOB di appoggio per produrre le istruzioni SQL per storage.h2.db ed il campo T_APPOGGIO.CAMPO_CLOB_OUTPUT_01
  step := 8;
  my_clob_output_temp := empty_clob();
  dbms_lob.createtemporary( my_clob_output_temp, true );
  update T_APPOGGIO set CAMPO_CLOB_OUTPUT_01 = NULL;
  if ( sql%rowcount = 0 ) then
    dbms_output.put_line( 'step = ' || step || ': update T_APPOGGIO set CAMPO_CLOB_OUTPUT_01 = NULL fallita' );
    raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Inizializzazione CLOB per le istruzioni SQL da eseguire su storage.h2.db e campo T_APPOGGIO.CAMPO_CLOB_OUTPUT_01, eseguita' );
  
  -- Leggo il campo CLOB contenente le righe CSV ciascuna delle quali rappresenta i dati di una casella da creare
  step := 9;  
  select trim( CAMPO_CLOB_INPUT_01 ) into my_clob_temp from T_APPOGGIO;
  my_clob_temp_length := dbms_lob.getlength( my_clob_temp );
  if ( my_clob_temp is null or my_clob_temp_length <= 0 ) then
    dbms_output.put_line( 'step = ' || step || ': Il campo T_APPOGGIO.CAMPO_CLOB_INPUT_01 deve essere valorizzato' );
    raise my_exception;
  end if;
  riga_clob := null;
  indice_riga := 1;
  if ( dbms_lob.isopen( my_clob_temp ) != 1 ) then
    dbms_lob.open( my_clob_temp, 0 );
  end if;
  dbms_output.put_line( 'step = ' || step || ': Lettura campo T_APPOGGIO.CAMPO_CLOB_INPUT_01, eseguita' );

  -- Ciclo lettura campo T_APPOGGIO.CAMPO_CLOB_INPUT_01
  step := 10;
  dbms_output.put_line( '' );
  dbms_output.put_line( '--------------------------------------------------------------------------------------------------' );
  dbms_output.put_line( '-- CICLO LETTURA CAMPO CONTENENTE I DATI DELLE CASELLE: T_APPOGGIO.CAMPO_CLOB_INPUT_01 - INIZIO --' );
  dbms_output.put_line( '--------------------------------------------------------------------------------------------------' );
  dbms_output.put_line( '' );

  while ( position_start < my_clob_temp_length )
  loop
    
    dbms_output.put_line( '' );
    dbms_output.put_line( '-----------------------------------------------' );
    dbms_output.put_line( '-- Elaborazione indice_riga = ' || LPAD( indice_riga, 5 ) || ' - INIZIO --' );
    dbms_output.put_line( '-----------------------------------------------' );
    dbms_output.put_line( '' );

    -- Prevalorizzo una variabile usata come prefisso di ogni stampa di output durante il ciclo    
    prefisso_log_ciclo := 'indice_riga = ' || indice_riga || ', step_ciclo = ';
    
    -- Leggo l'i-esima riga del CLOB e la ripulisco da spazi in testa, in coda e dai caratteri LF e CR
    step_ciclo := 1;
    begin
      step_ciclo_sotto              := NULL;
      my_mailbox_account            := NULL;
      my_mailbox_name               := NULL;
      my_id_uo_pec                  := NULL;
      my_id_account                 := NULL;
      my_username_account           := NULL;
      my_id_mailbox                 := NULL;
      my_mailbox_name_P             := NULL;
      my_id_mailbox_P               := NULL;
      my_mailbox_config_directory   := NULL;
      my_mailbox_config_directory_P := NULL;
      riga_clob                     := NULL;
      my_mailbox_data               := NULL;
      position_end                  := instr( my_clob_temp, char_LF, position_start );
      if ( position_end <= 0 ) then
        position_end := instr( my_clob_temp, char_CR, position_start );
      end if;          
      if ( position_end <= 0 ) then
        position_end := my_clob_temp_length + 1;
      end if;          
      line_length := position_end - position_start;
      dbms_lob.read( my_clob_temp, line_length, position_start, riga_clob );
      riga_clob := trim( riga_clob );
      riga_clob := replace( riga_clob, char_LF, '' );
      riga_clob := replace( riga_clob, char_CR, '' );
    exception when others then
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La riga dei dati in input non è valida: potrebbe essere nulla o vuota o non correttamente delimitata' );
      raise my_exception;
    end;
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Letta riga_clob = ' || riga_clob );
    
    -- Estraggo i singoli dati identificativi di una casella dalla riga ed effettuo vari controlli su di essi
    -- Modifico la riga letta in modo da usare il classico metodo di PTPK_GENERIC.Splittatore, usato in eGrammata    
    step_ciclo := 2;
    riga_clob := replace( riga_clob, ',', ptfn_get_separatore_varray ) || ptfn_get_separatore_varray;
    if ( PTPK_GENERIC.Splittatore( riga_clob, my_mailbox_data, sql_error_msg, sql_error_msg ) = 0 ) then
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La riga dei dati in input non è valida, sql_error_code = ' || sql_error_code || ', sql_error_msg = ' || sql_error_msg );
      raise my_exception;
    else

      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabile riga_clob, eseguito' );

      -- Lettura dati casella dalla riga i-esima
      step_ciclo := 3;
      my_mailbox_name    := upper( trim( my_mailbox_data( 1 ) ) );
      my_mailbox_account := trim( my_mailbox_data( 2 ) );
      my_id_uo_pec       := trim( my_mailbox_data( 3 ) );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Estratti dati casella dalla riga: ' || char_LF 
        || '1 - my_mailbox_name    = ' || my_mailbox_name || char_LF
        || '2 - my_mailbox_account = ' || my_mailbox_account || char_LF
        || '3 - my_id_uo_pec       = ' || my_id_uo_pec );
      
      -- Controllo variabile my_mailbox_account
      step_ciclo := 4;
      if ( my_mailbox_account is null ) then
        dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La variabile my_mailbox_account deve essere valorizzata' );
        raise my_exception;
      end if;
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabile my_mailbox_account, eseguito' );

      -- Controllo variabile my_mailbox_name
      step_ciclo := 5;
      if ( my_mailbox_name is null ) then 
        dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La variabile my_mailbox_name deve essere valorizzata' );
        raise my_exception;
      elsif ( instr( my_mailbox_name, ' ' ) > 0 ) or LENGTH( TRIM( TRANSLATE( my_mailbox_name, '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ', ' ' ) ) ) > 0 then
        dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La variabile my_mailbox_name deve essere composta da sole lettere maiuscole e/o numeri' );
        raise my_exception;
      else
        count_temp := 0;
        select count(*) into count_temp from MAILBOX where ID_MAILBOX = my_mailbox_name;
        if ( count_temp > 0 ) then
          dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La casella ' || my_mailbox_name || ' è già presente in tabella MAILBOX' );
          raise my_exception;
        end if;
      end if;
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabile my_mailbox_name, eseguito' );
      
      -- Controllo variabile my_id_uo_pec
      step_ciclo := 6;
      if ( my_id_uo_pec is null ) then 
        dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La variabile my_id_uo_pec è NULL, pertanto la casella PEC non sarà associata a nessuna struttura UO' );
      else
        count_temp := 0;
        select count(*) into count_temp 
        from CVT_UO 
        where ID_UO = my_id_uo_pec
        and sysdate between DT_INIZIO_VLD and NVL( DT_FINE_VLD, sysdate );
        if ( count_temp = 0 ) then
          dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La UO con ID_UO = ' || my_id_uo_pec || ' deve essere presente in tabella CVT_UO ed in corso di validità' );
          raise my_exception;
        end if;
      end if;
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabile my_id_uo_pec, eseguito' );

      -- Imposto varibili identificative dell'account e delle due caselle di scarico da creare
      step_ciclo := 7;
      my_id_account                 := my_mailbox_name || '-ACCOUNT';
      my_username_account           := my_mailbox_account;
      my_id_mailbox                 := my_mailbox_name;
      my_mailbox_name_P             := my_mailbox_name || '-P';
      my_id_mailbox_P               := my_mailbox_name_P;
      my_mailbox_config_directory   := '/allegati/email/' || my_mailbox_name;
      my_mailbox_config_directory_P := '/allegati/email/' || my_mailbox_name_P;
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_account            = ' || my_mailbox_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_name               = ' || my_mailbox_name );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_id_uo_pec                  = ' || my_id_uo_pec );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_id_account                 = ' || my_id_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_username_account           = ' || my_username_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_id_mailbox                 = ' || my_id_mailbox );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_name_P             = ' || my_mailbox_name_P );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_id_mailbox_P               = ' || my_id_mailbox_P );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_config_directory   = ' || my_mailbox_config_directory );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_config_directory_P = ' || my_mailbox_config_directory_P );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Variabili identificative dell''account e delle due caselle di scarico, impostate correttamente' );
      
    end if;      

    -- Controllo che tutte le varibili identificative dell'account e delle due caselle di scarico, siano valorizzate
    step_ciclo := 8;
    if ( my_mailbox_account is NULL
          or my_mailbox_name is NULL
          or my_id_account is NULL
          or my_username_account is NULL
          or my_id_mailbox is NULL
          or my_mailbox_name_P is NULL
          or my_id_mailbox_P is NULL 
          or my_mailbox_config_directory is NULL
          or my_mailbox_config_directory_P is NULL
          ) then
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Tutte le variabili identificative dell''account e delle due caselle di scarico, devono essere valorizzate' );
      raise my_exception;
    end if;
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabili identificative dell''account e delle due caselle di scarico, eseguito' );
    
    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 1: CREAZIONE DELLE MAILBOX ATTIVE SU INBOX E DA_PROTOCOLLARE CON LE RELATIVE OPERATION - INIZIO ' );
    dbms_output.put_line( '' );
  
    -- Creazione record in MAILBOX_ACCOUNT oppure tengo in memoria se l'accounto è già esistente
    step_ciclo := 9;
    begin
      select ID_ACCOUNT into my_id_account_esistente from MAILBOX_ACCOUNT where upper( ACCOUNT ) = upper( my_mailbox_account ); 
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Record in MAILBOX_ACCOUNT con ACCOUNT = ' || my_mailbox_account || ' già esistente, viene riutilizzato, my_id_account_esistente = ' || my_id_account_esistente );
      my_id_account := my_id_account_esistente;
    exception when no_data_found then
      insert into MAILBOX_ACCOUNT ( ID_ACCOUNT, ACCOUNT ) values ( my_id_account, my_mailbox_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione record in MAILBOX_ACCOUNT, eseguita' );
    end;

    -- Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX
    step_ciclo := 10;
    insert into MAILBOX ( ID_MAILBOX, DESCRIPTION, NAME, PROCESS_FLOW, STATUS, FOLDER, ID_ACCOUNT )
    values ( my_id_mailbox, my_mailbox_name || ' - ' || my_mailbox_folder_name, my_mailbox_name, replace( my_mailbox_process_flow, 'MODEL', my_id_mailbox ), 'active', my_mailbox_folder_name, my_id_account );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX, eseguita' );
 
    -- Creazione record in MAILBOX relativo allo scarico dalla cartella DA-PROTOCOLLARE
    step_ciclo := 11;
    insert into MAILBOX ( ID_MAILBOX, DESCRIPTION, NAME, PROCESS_FLOW, STATUS, FOLDER, ID_ACCOUNT )
    values ( my_id_mailbox_P, my_mailbox_name_P || ' - ' || my_mailbox_folder_name_P, my_mailbox_name_P, replace( my_mailbox_process_flow_P, 'MODEL-P', my_id_mailbox_P ), 'active', my_mailbox_folder_name_P, my_id_account );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione record in MAILBOX relativo allo scarico dalla cartella DA-PROTOCOLLARE, eseguita' );

    -- Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata 
    -- Questa operation anche se utilizzata in tutti i flussi avendo id unico, viene referenziata da tutte le MAILBOX
    -- anche se inizialmente viene associata ad una sola MAILBOX.
    step_ciclo := 12;
    begin
      insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( '1', 'StartOperation', my_id_mailbox );
    exception when others then
      null;
    end;
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata, eseguita' );
  
    -- Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
    step_ciclo := 13;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-SIGNEROP', 'SignerOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-INTEROP', 'InterOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-ISEGRMESSAGEOP', 'IsEgrMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.4';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-1', 'CopyMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.5';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-2', 'CopyMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.6';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-3', 'CopyMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.7';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-4', 'CopyMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.8';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-5', 'CopyMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.9';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-1', 'DeleteMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.10';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-2', 'DeleteMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.11';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-3', 'DeleteMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.12';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-4', 'DeleteMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.13';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP-5', 'DeleteMessageOperation', my_id_mailbox );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX, eseguita' );

    -- Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
    step_ciclo := 14;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'isegrmessageoperation.databaseurl',  my_id_mailbox || '-ISEGRMESSAGEOP', my_string_db_connection );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-1', my_mailbox_folder_name_P );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-2', my_mailbox_folder_name_P );
    step_ciclo_sotto := step_ciclo || '.4';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-3', my_mailbox_folder_name_P );
    step_ciclo_sotto := step_ciclo || '.5';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-4', my_mailbox_folder_name_P );
    step_ciclo_sotto := step_ciclo || '.6';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-5', my_mailbox_folder_name_P );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX, eseguita' );

    -- Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE
    step_ciclo := 15;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox_P || '-SIGNEROP', 'SignerOperation', my_id_mailbox_P );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox_P || '-INTEROP', 'InterOperation', my_id_mailbox_P );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox_P || '-EGRAMMATAOP', 'EGrammataMessageOperation', my_id_mailbox_P );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE, eseguita' );

    -- Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE
    step_ciclo := 16;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.tempfsobject', my_id_mailbox_P || '-EGRAMMATAOP', '/allegati/temp' );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.rootfsobject', my_id_mailbox_P || '-EGRAMMATAOP', '/allegati/downloads' );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.folderprimario', my_id_mailbox_P || '-EGRAMMATAOP', 'email' );
    step_ciclo_sotto := step_ciclo || '.4';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.databaseurl', my_id_mailbox_P || '-EGRAMMATAOP', my_string_db_connection );
    step_ciclo_sotto := step_ciclo || '.5';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.attivasostituzioneunicodetestomail', my_id_mailbox_P || '-EGRAMMATAOP', 'true' );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla DA-PROTOCOLLARE, eseguita' );
  
    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 1: CREAZIONE DELLE MAILBOX ATTIVE SU INBOX E DA_PROTOCOLLARE CON LE RELATIVE OPERATION - FINE ' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 2: CONFIGURAZIONE MAILBOX TABELLA MAILBOX_CONFIG - INIZIO' );
    dbms_output.put_line( '' );
  
    -- Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella INBOX
    step_ciclo := 17;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.ente', my_id_mailbox, my_mailbox_config_ente );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.utente', my_id_mailbox, my_mailbox_config_utente );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.antispam', my_id_mailbox, my_mailbox_config_antispam );
    step_ciclo_sotto := step_ciclo || '.4';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.autostart', my_id_mailbox, my_mailbox_config_autostart );
    step_ciclo_sotto := step_ciclo || '.5';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.debug', my_id_mailbox, my_mailbox_config_debug );
    step_ciclo_sotto := step_ciclo || '.6';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.delay', my_id_mailbox, my_mailbox_config_delay );
    step_ciclo_sotto := step_ciclo || '.7';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.deletetofilesystem', my_id_mailbox, my_mailbox_config_delfs );
    step_ciclo_sotto := step_ciclo || '.8';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.directory', my_id_mailbox, my_mailbox_config_directory );
    step_ciclo_sotto := step_ciclo || '.9';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.fetch', my_id_mailbox, my_mailbox_config_fetch );
    step_ciclo_sotto := step_ciclo || '.10';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.mailconnectid', my_id_mailbox, my_mailbox_config_mailconnid );
    step_ciclo_sotto := step_ciclo || '.11';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.maxtrynumoperation', my_id_mailbox, my_mailbox_config_maxtrynumop );
    step_ciclo_sotto := step_ciclo || '.12';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.sentfolder.name', my_id_mailbox, my_mailbox_config_sentfolder );
    step_ciclo_sotto := step_ciclo || '.13';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.cleardischarged', my_id_mailbox, my_mailbox_config_cldischarged );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella INBOX, eseguita' );

    -- Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella DA-PROTOCOLLARE
    step_ciclo := 18;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.ente', my_id_mailbox_P, my_mailbox_config_ente );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.egrammata.utente', my_id_mailbox_P, my_mailbox_config_utente );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.antispam', my_id_mailbox_P, my_mailbox_config_antispam );
    step_ciclo_sotto := step_ciclo || '.4';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.autostart', my_id_mailbox_P, my_mailbox_config_autostart );
    step_ciclo_sotto := step_ciclo || '.5';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.debug', my_id_mailbox_P, my_mailbox_config_debug );
    step_ciclo_sotto := step_ciclo || '.6';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.delay', my_id_mailbox_P, my_mailbox_config_delay );
    step_ciclo_sotto := step_ciclo || '.7';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.deletetofilesystem', my_id_mailbox_P, my_mailbox_config_delfs );
    step_ciclo_sotto := step_ciclo || '.8';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.directory', my_id_mailbox_P, my_mailbox_config_directory_P );
    step_ciclo_sotto := step_ciclo || '.9';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.fetch', my_id_mailbox_P, my_mailbox_config_fetch );
    step_ciclo_sotto := step_ciclo || '.10';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.mailconnectid', my_id_mailbox_P, my_mailbox_config_mailconnid );
    step_ciclo_sotto := step_ciclo || '.11';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.maxtrynumoperation', my_id_mailbox_P, my_mailbox_config_maxtrynumop );
    step_ciclo_sotto := step_ciclo || '.12';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.sentfolder.name', my_id_mailbox_P, my_mailbox_config_sentfolder );
    step_ciclo_sotto := step_ciclo || '.13';
    insert into MAILBOX_CONFIG ( CONFIG_KEY, ID_MAILBOX, CONFIG_VALUE ) values ( 'mail.mailbox.cleardischarged', my_id_mailbox_P, my_mailbox_config_cldischarged );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella DA-PROTOCOLLARE, eseguita' );

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 2: CONFIGURAZIONE MAILBOX TABELLA MAILBOX_CONFIG - FINE' );
    dbms_output.put_line( '' );
  
    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 3: CONFIGURAZIONE ACCOUNT TABELLA MAILBOX_ACCOUNT_CONFIG - INIZIO' );
    dbms_output.put_line( '' );
  
    step_ciclo := 19;
    if ( my_id_account_esistente is null ) then
      step_ciclo_sotto := step_ciclo || '.1';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.account.ispec', my_account_config_ispec, my_id_account );
      step_ciclo_sotto := step_ciclo || '.2';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.auth', my_account_config_auth, my_id_account );
      step_ciclo_sotto := step_ciclo || '.3';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.auth.login.disable', my_account_config_authlogidisa, my_id_account );
      step_ciclo_sotto := step_ciclo || '.4';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.host', my_account_config_imaphost, my_id_account );
      step_ciclo_sotto := step_ciclo || '.5';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.port', my_account_config_imapport, my_id_account );
      step_ciclo_sotto := step_ciclo || '.6';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.socketFactory.class', my_account_config_sockfactclas, my_id_account );
      step_ciclo_sotto := step_ciclo || '.7';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.socketFactory.fallback', my_account_config_sockfactfall, my_id_account );
      step_ciclo_sotto := step_ciclo || '.8';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.ssl.enable', my_account_config_sslenable, my_id_account );
      step_ciclo_sotto := step_ciclo || '.9';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.imap.store.protocol', my_account_config_storeprot, my_id_account );
      step_ciclo_sotto := step_ciclo || '.10';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.mailbox.maxaddresstosend', my_account_config_maxaddrsend, my_id_account );
      step_ciclo_sotto := step_ciclo || '.11';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.password', my_account_config_password, my_id_account );
      step_ciclo_sotto := step_ciclo || '.12';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.password.encryption', my_account_config_passwordenc, my_id_account );
      step_ciclo_sotto := step_ciclo || '.13';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.auth', my_account_config_smtpauth, my_id_account );
      step_ciclo_sotto := step_ciclo || '.14';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.auth.login.disable', my_account_config_smtpautlodi, my_id_account );
      step_ciclo_sotto := step_ciclo || '.15';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.host', my_account_config_smtphost, my_id_account );
      step_ciclo_sotto := step_ciclo || '.16';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.port', my_account_config_smtpport, my_id_account );
      step_ciclo_sotto := step_ciclo || '.17';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.socketFactory.class', my_account_config_smtpsofacl, my_id_account );
      step_ciclo_sotto := step_ciclo || '.18';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.socketFactory.fallback', my_account_config_smtpsoffb, my_id_account );
      step_ciclo_sotto := step_ciclo || '.19';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.smtp.ssl.enable', my_account_config_smtpsslenab, my_id_account );
      step_ciclo_sotto := step_ciclo || '.20';
      insert into MAILBOX_ACCOUNT_CONFIG ( CONFIG_KEY, CONFIG_VALUE, ID_ACCOUNT ) values ( 'mail.username', my_username_account, my_id_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione records in MAILBOX_ACCOUNT_CONFIG relativi all'' ACCOUNT, eseguita' );
    else
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Records in MAILBOX_ACCOUNT_CONFIG relativi all'' ACCOUNT, sono già esistenti' );
    end if;
  
    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 3: CONFIGURAZIONE ACCOUNT TABELLA MAILBOX_ACCOUNT_CONFIG - FINE' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Completata configurazione account e delle due caselle di scarico' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 4: ASSOCIAZIONE CASELLA PEC ALLA UO DI STRUTTURA - INIZIO' );
    dbms_output.put_line( '' );

    -- Associazione account PEC con indirizzo my_mailbox_account e struttura UO con ID_UO = my_id_uo_pec
    step_ciclo := 20;
    if ( my_id_uo_pec is null ) then
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La variabile my_id_uo_pec è NULL, pertanto la casella PEC non sarà associata a nessuna struttura UO' );
    else
      step_ciclo_sotto := step_ciclo || '.1';
      count_temp := 0;
      select count(*) into count_temp from CVT_UO_PEC where ID_UO = my_id_uo_pec;
      step_ciclo_sotto := step_ciclo || '.2';
      if ( count_temp = 0 ) then
        step_ciclo_sotto := step_ciclo || '.3';
        insert into CVT_UO_PEC 
        (
          ID_UO,
          DESCRIZIONE,
          EMAIL,
				  IMAP_SERVER,
          IMAP_USERNAME,
          IMAP_PORT,
          IMAP_SSL
        ) 
        VALUES 
        (
          my_id_uo_pec,
          'PEC associata alla mailbox: ' || my_mailbox_name,
          my_mailbox_account,
          my_account_config_imaphost,
          my_username_account,
          my_account_config_imapport,
          my_account_config_sslenable
        );
        dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Associazione casella ' || my_mailbox_account || ' e struttura UO ' || my_id_uo_pec || ', mediante INSERT, eseguita' );
      else
        step_ciclo_sotto := step_ciclo || '.4';
        update CVT_UO_PEC set
          DESCRIZIONE   = 'PEC associata alla mailbox: ' || my_mailbox_name,
          EMAIL 		    = my_mailbox_account,
		      IMAP_SERVER   = my_account_config_imaphost,
          IMAP_USERNAME = my_username_account,
          IMAP_PORT     = my_account_config_imapport,
          IMAP_SSL      = my_account_config_sslenable
        where ID_UO = my_id_uo_pec;
        step_ciclo_sotto := step_ciclo || '.5';
        if ( sql%rowcount = 1 ) then
          dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Associazione casella ' || my_mailbox_account || ' e struttura UO ' || my_id_uo_pec || ', mediante UPDATE, eseguita' );
        else
          dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Associazione casella ' || my_mailbox_account || ' e struttura UO ' || my_id_uo_pec || ', mediante UPDATE: NESSUN RECORD MODIFICATO' );
          raise my_exception;
        end if;  
      end if;
    end if;

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 4: ASSOCIAZIONE CASELLA PEC ALLA UO DI STRUTTURA - FINE' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 5: PRODUZIONE ISTRUZIONI SQL PER storage.h2.db - INIZIO' );
    dbms_output.put_line( '' );
    
    -- Produzione istruzioni SQL da eseguire sullo storage.h2.db
    step_ciclo := 21;
    step_ciclo_sotto := step_ciclo || '.1';
    my_riga_output_temp := replace( insert_storage_1, '[MODEL]', my_mailbox_name );
    dbms_lob.append( my_clob_output_temp, my_riga_output_temp );
    dbms_lob.append( my_clob_output_temp, char_LF );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': aggiunta insert_storage_1 per la casella ' || my_mailbox_name || ', my_riga_output_temp = ' || my_riga_output_temp );

    step_ciclo_sotto := step_ciclo || '.2';
    my_riga_output_temp := replace( insert_storage_2, '[MODEL]', my_mailbox_name );
    dbms_lob.append( my_clob_output_temp, my_riga_output_temp );
    dbms_lob.append( my_clob_output_temp, char_LF );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': aggiunta insert_storage_2 per la casella ' || my_mailbox_name || ', my_riga_output_temp = ' || my_riga_output_temp );

    step_ciclo_sotto := step_ciclo || '.3';
    my_riga_output_temp := replace( insert_storage_1, '[MODEL]', my_mailbox_name_P );
    dbms_lob.append( my_clob_output_temp, my_riga_output_temp );
    dbms_lob.append( my_clob_output_temp, char_LF );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': aggiunta insert_storage_1 per la casella ' || my_mailbox_name_P || ', my_riga_output_temp = ' || my_riga_output_temp );

    step_ciclo_sotto := step_ciclo || '.4';
    my_riga_output_temp := replace( insert_storage_2, '[MODEL]', my_mailbox_name_P );
    dbms_lob.append( my_clob_output_temp, my_riga_output_temp );
    dbms_lob.append( my_clob_output_temp, char_LF );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': aggiunta insert_storage_2 per la casella ' || my_mailbox_name_P || ', my_riga_output_temp = ' || my_riga_output_temp );

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 5: PRODUZIONE ISTRUZIONI SQL PER storage.h2.db - FINE' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( '---------------------------------------------' );
    dbms_output.put_line( '-- Elaborazione indice_riga = ' || LPAD( indice_riga, 5 ) || ' - FINE --' );
    dbms_output.put_line( '---------------------------------------------' );
    dbms_output.put_line( '' );
    
    -- Impostazioni variabili per leggere la riga succesiva del CSV memorizzato nel campo T_APPOGGIO.CAMPO_CLOB_INPUT_01
    step_ciclo := 22;
    position_start := position_end + 1;
    indice_riga    := indice_riga + 1;
    
  end loop; 

  step_ciclo       := NULL;
  step_ciclo_sotto := NULL; 
  indice_riga      := NULL;
     
  dbms_output.put_line( 'step = ' || step || ': Ciclo lettura campo T_APPOGGIO.CAMPO_CLOB_INPUT_01 terminato' );
  dbms_output.put_line( '' );
  dbms_output.put_line( '------------------------------------------------------------------------------------------------' );
  dbms_output.put_line( '-- CICLO LETTURA CAMPO CONTENENTE I DATI DELLE CASELLE: T_APPOGGIO.CAMPO_CLOB_INPUT_01 - FINE --' );
  dbms_output.put_line( '------------------------------------------------------------------------------------------------' );
  dbms_output.put_line( '' );

   -- Update campo T_APPOGGIO.CAMPO_CLOB_OUTPUT_01 con istruzioni SQL per storage.h2.db
  step := 11;
  if ( dbms_lob.getlength( my_clob_output_temp ) > 0 ) then
    update T_APPOGGIO set CAMPO_CLOB_OUTPUT_01 = my_clob_output_temp;
    if ( sql%rowcount = 1 ) then
      dbms_output.put_line( 'step = ' || step || ': Update campo T_APPOGGIO.CAMPO_CLOB_OUTPUT_01 con istruzioni SQL per storage.h2.db, eseguita' );
    else
      dbms_output.put_line( 'step = ' || step || ': Update campo T_APPOGGIO.CAMPO_CLOB_OUTPUT_01 non eseguita correttamente, numero record aggiornati: ' || sql%rowcount );
      raise my_exception;
    end if;  
  else
    dbms_output.put_line( 'step = ' || step || ': Update campo T_APPOGGIO.CAMPO_CLOB_OUTPUT_01 non eseguita perchè my_clob_output_temp è vuoto' );
    raise my_exception;
  end if; 

  -- Chiusura variabile my_clob_temp di tipo CLOB
  step := 12;
  if ( dbms_lob.isopen( my_clob_temp ) = 1 ) then
    dbms_lob.close( my_clob_temp );
  end if; 
  dbms_output.put_line( 'step = ' || step || ': Chiusura variabile my_clob_temp di tipo CLOB, eseguita' );

  -- Esecuzione di COMMIT o ROLLBACK
  step := 13;
  if ( flg_commit ) then

     COMMIT;

     dbms_output.put_line( '' );
     dbms_output.put_line( 'step = ' || step || ': flg_commit = TRUE --> COMMIT ESEGUITO!' );
     dbms_output.put_line( '' );
  
  else
     
     ROLLBACK;
     
     dbms_output.put_line( '' );
     dbms_output.put_line( 'step = ' || step || ': flg_commit = FALSE --> ROLLBACK ESEGUITO!' );
     dbms_output.put_line( '' );
  
  end if;

  dbms_output.put_line( '' );
  dbms_output.put_line( '---------------------------------------------' );
  dbms_output.put_line( '-- ESITO: PROCEDURA TERMINATA CON SUCCESSO --' );
  dbms_output.put_line( '---------------------------------------------' );
  dbms_output.put_line( '' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------' );      
  dbms_output.put_line( '-- FINE: ' || to_char( sysdate, 'dd/mm/yyyy hh24:mi:ss' ) || ' --' );
  dbms_output.put_line( '-------------------------------' );      
  dbms_output.put_line( '' );

exception when others then

  ROLLBACK;
  
  dbms_output.put_line( '' );
  dbms_output.put_line( 'ESEGUITO ROLLBACK!' );

  sql_error_code := SQLCODE;
  sql_error_msg  := SQLERRM;

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------------------' );
  dbms_output.put_line( '-- ESITO: PROCEDURA TERMINATA CON ERRORI --' );
  dbms_output.put_line( '-------------------------------------------' );
  dbms_output.put_line( '' );

  dbms_output.put_line( 'step             = ' || step );
  dbms_output.put_line( 'step_ciclo       = ' || step_ciclo );
  dbms_output.put_line( 'step_ciclo_sotto = ' || step_ciclo_sotto );
  dbms_output.put_line( 'indice_riga      = ' || indice_riga );
  dbms_output.put_line( 'sql_error_code   = ' || sql_error_code );
  dbms_output.put_line( 'sql_error_msg    = ' || sql_error_msg );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------' );      
  dbms_output.put_line( '-- FINE: ' || to_char( sysdate, 'dd/mm/yyyy hh24:mi:ss' ) || ' --' );
  dbms_output.put_line( '-------------------------------' );      
  dbms_output.put_line( '' );

end;

