-----------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------
-- PROGETTO EGR-CMNA (EGR2)
-- DESCRIZIONE:
-- Script di creazione massiva di N caselle MAILBOX, nelle tabelle usate dal MAILCONNECT senza usare la console mailui. 
-- PREREQUISITI:
-- Si prenda visione e si segua passo passo quanto descritto nel doc "EGR_CMNA_Readme_Crea_PEC.doc", con particolare 
-- riferimento al paragrafo "Istruzioni per creare massivamente PEC di struttura".
-- Creare un file di testo CSV, che chiameremo per esempio "input.txt" in cui ciascuna riga rappresenta i dati minimi 
-- di una nuova casella pec di struttura, da configurare:
-- 1 - nome mailbox (obbligatorio)
-- 2 - account, ovvero indirizzo PEC (obbligatorio)
-- 3 - ID_UO cui associare l'account (opzionale)
-- 4 - data inizio scarico (obbligatorio)
-- 5 - password della casella PEC (obbligatorio)
-- Questo è un esempio di contenuto di un tale file di testo CSV:
-- XOO1,xoo1@pec.comune.napoli.it,10154,01/01/2016,password1 
-- XOO2,xoo2@pec.comune.napoli.it,,02/01/2016,password2  (qui non è specificata la struttura UO)
-- XOO3,xoo3@pec.comune.napoli.it,10153,03/01/2016,password3
-- XOO4,xoo4@pec.comune.napoli.it,,04/01/2016,password4  (qui non è specificata la struttura UO)
-- Copiare e incollare il contenuto del file "input.txt" nel campo T_APPOGGIO.CAMPO_CLOB_INPUT_01.
-- PROCESSO:
-- Lo script farà uso della tabella di appoggio T_APPOGGIO, utilizzando di essa i soli campi: 
-- 1 - T_APPOGGIO.CAMPO_CLOB_INPUT_01 per l'input;
-- 2 - T_APPOGGIO.CAMPO_CLOB_OUTPUT_01 per l'output.
-- Lo script leggerà il campo T_APPOGGIO.CAMPO_CLOB_INPUT_01, riga per riga intercettando il carattere LR e/o CR
-- come delimitatore di ciascuna riga e per ciascuna riga estrae i dati separati da virgola:
-- 1 - nome mailbox (obbligatorio)
-- 2 - account, ovvero indirizzo PEC (obbligatorio)
-- 3 - ID_UO cui associare l'account (opzionale)
-- 4 - data inizio scarico (obbligatorio)
-- 5 - password della casella PEC (obbligatorio)
-- A fronte di questi dati creerà le configurazioni per: 
-- 1 - l'account se non esiste già;
-- 2 - Il flusso di scarico dalla folder INBOX;
-- Un qualunque errore durante l'eseuczione dello script, causarà il ROLLBACK dell'intera transazione.
-- Pertanto, al termine del processo, saranno create o TUTTE le caselle o NESSUNA casella.
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
  my_string_db_connection varchar2(200) := 'jdbc:oracle:thin:ente02/ente02@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=172.30.36.176)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SID=PROTOWIN)))';

  -- Valore parametro 'mail.imap.host', ovvero numero IP o nome del server IMAP, ad esempio in Test vale 'imaps.pec.aruba.it'
  my_account_config_imaphost VARCHAR2(400) := 'imaps.pec.aruba.it';
  
  -- Valore parametro 'mail.imap.port', ovvero numero porta del server IMAP, ad esempio in Test vale '993'
  my_account_config_imapport VARCHAR2(400) := '993';
  
  -- Valore parametro 'mail.smtp.host', ovvero numero IP o nome del server SMTP, ad esempio in Test vale 'smtps.pec.aruba.it'
  my_account_config_smtphost VARCHAR2(400) := 'smtps.pec.aruba.it';
  
  -- Valore parametro 'mail.smtp.port', ovvero numero porta del server IMAP, ad esempio in Test vale '465'
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
  -- Esempi: 'aoo1@pec.comune.napoli.it', 'aoo2@pec.comune.napoli.it', ecc... 
  my_mailbox_account varchar2(150) := NULL;

  -- Nome breve univoco, identificativo della mailbox e che magari richiama mentalmente l'account definito con la variabile my_mailbox_account.
  -- La mailbox di riferimento è quella attiva per lo scarico dalla cartella INBOX. 
  -- Questa variabile sarà utilizzata per valorizzare: i campi MAILBOX.ID_MAILBOX e MAILBOX.NAME,
  -- altre config del MailConnect (cartella in C:/docprot/protinfo/email e storage.h2.db).
  -- Esempi: 'AOO1' per richiamare 'aoo1@pec.comune.napoli.it', 'AOO2' per richiamare 'aoo2@pec.comune.napoli.it', e di 
  -- conseguenza le cartelle associate da creare saranno: C:/docprot/protinfo/email/AOO1, C:/docprot/protinfo/email/AOO2 per lo scarico da INBOX.
  my_mailbox_name varchar2(20) := NULL; 
  
  -- Username dell'account, per default è in pratica lo stesso valore della variabile my_mailbox_account.
  -- Pertanto valorizzare la variabile solo se il suo valore è diverso dal valore della variabile my_mailbox_account 
  -- Esempi: come per my_mailbox_account ad esempio aoo1@pec.comune.napoli.it
  my_username_account varchar2(150) := NULL;
  
  -- Password dell'account definito con la variabile my_username_account
  -- Valore parametro 'mail.password'
  my_account_config_password VARCHAR2(400) := NULL;   
  
  -- Valori per valorizzare i parametri di configurazione in tabella MAILBOX_CONFIG
  -- Valore parametro ente che è fisso a 1 avendo questa versione di e-grammata un solo ente
  my_mailbox_config_ente VARCHAR2(400)                     := '2';
  -- Valore parametro utente e-grammata corrispondente a CVT_UTENTI.ID_UTE where u.username = 'ADMIN'
  my_mailbox_config_utente VARCHAR2(400)                   := '1';
  my_mailbox_config_antispam VARCHAR2(400)                 := 'false';
  -- Valore parametro flag per indicare attivazione/disattivazione mailbox ovvero processo di scarico, all'avvio
  my_mailbox_config_autostart VARCHAR2(400)                := 'true';
  my_mailbox_config_debug VARCHAR2(400)                    := 'false';
  -- Valore parametro sul numero di ms che devono trascorrere per la prossima lettura cartella di scarico
  my_mailbox_config_delay VARCHAR2(400)                    := '5000';
  my_mailbox_config_delfs VARCHAR2(400)                    := 'false';
  -- Valore parametro cartella in cui vengono scaricate le email dalla INBOX
  my_mailbox_config_directory VARCHAR2(400)                := 'C:/docprot/protinfo/email/' || my_mailbox_name;
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
  -- Valore parametro 'mail.password.encryption'
  my_account_config_passwordenc VARCHAR2(400)            := 'false';   
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

  -- Valori per valorizzare i parametri di configurazione in tabella MAILBOX_OPERATION_CONFIG
  -- Valore parametro 'egrammatamessageoperation.tempfsobject', path del repository FS egrammata cartella temp
  my_egr_op_config_tempfsobject VARCHAR2(400)    := 'C:/docprot/protinfo/temp';
  -- Valore parametro 'egrammatamessageoperation.rootfsobject', path del repository FS egrammata cartella downloads
  my_egr_op_config_rootfsobject VARCHAR2(400)    := 'C:/docprot/protinfo/downloads';
    
  --------------------------------------------------------------------------------------------------------------------
  -- FINE Variabili che devono essere obbligatoriamente inizializzate seguendo scrupolosamente le regole indicate --
  --------------------------------------------------------------------------------------------------------------------


  ---------------------------------------------------------------------------------------------------------------------
  -- INIZIO Variabili che NON devono essere inizializzate perchè usate dallo script automaticamente per i suoi scopi --
  ---------------------------------------------------------------------------------------------------------------------
  
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
  
  -- Nome della cartella usata per lo scarico: 'INBOX'
  my_mailbox_folder_name VARCHAR2(20) := 'INBOX';

  -- Flusso di scarico dalla INBOX
  -- Esempio di flusso semplice di default che viene creato dalla console quando si crea una nuova casella
  /*
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
                                            <operationid>MODEL-EGRAMMATAOP</operationid>
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
  */
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
                                                    <expression>!messageinfo.isDeliveryStatusNotification() ' || chr(38) || 'amp;' || chr(38) || 'amp; !messageinfo.isRicevuta()</expression>
                                                    <operation>
                                                        <node>
                                                            <nodeElaborate>
                                                                <expression>1==1</expression>
                                                                <operation>
                                                                    <operationid>MODEL-DELETEOP</operationid>
                                                                </operation>
                                                            </nodeElaborate>
                                                        </node>
                                                        <operationid>MODEL-COPYOP-ELABORATE</operationid>
                                                    </operation>
                                                </nodeElaborate>
                                                <nodeElaborate>
                                                    <expression>messageinfo.isDeliveryStatusNotification() || messageinfo.isRicevuta()</expression>
                                                    <operation>
                                                        <node>
                                                            <nodeElaborate>
                                                                <expression>1==1</expression>
                                                                <operation>
                                                                    <operationid>MODEL-DELETEOP</operationid>
                                                                </operation>
                                                            </nodeElaborate>
                                                        </node>
                                                        <operationid>MODEL-COPYOP-CONSEGNA</operationid>
                                                    </operation>
                                                </nodeElaborate>
                                            </node>
                                            <operationid>MODEL-EGRAMMATAOP</operationid>
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

  -- Variabili utili alla gestione delle eccezioni
  sql_error_code integer           := null;
  sql_error_msg varchar2(2000)     := null;
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
  -- Valore parametro 'egrammatamessageoperation.datainizioscarico', data da cui iniziare lo scarico
  my_datinisca VARCHAR2(400)         := NULL; 
  
  -- Variabili per produrre nel campo T_APPOGGIO.CAMPO_CLOB_INPUT_01, la serie di istruzioni SQL da eseguire su storage.h2.db
  my_clob_output_temp CLOB           := NULL;
  my_riga_output_temp VARCHAR2(1000) := NULL;
  insert_storage_1 VARCHAR2(1000)    := 'INSERT INTO "PUBLIC"."STORAGES" (ID_STORAGE,FLG_DISATTIVO,TIPO_STORAGE,XML_CONFIG) VALUES (''[MODEL]'',0,''FS'',''<?xml version="1.0" encoding="UTF-8" standalone="yes"?><fileSystemStorageConfig><baseFolderPath>C:/docprot/protinfo/email/[MODEL]</baseFolderPath><repositoryLocations>C:/docprot/protinfo/email/[MODEL]</repositoryLocations><tempRepositoryBasePath>C:/docprot/protinfo/email/temp</tempRepositoryBasePath></fileSystemStorageConfig>'');';
  insert_storage_2 VARCHAR2(1000)    := 'INSERT INTO "PUBLIC"."UTILIZZATORI_STORAGE" (ID_UTILIZZATORE,ID_STORAGE) VALUES (''MailConnect.[MODEL]'',''[MODEL]'');';
  
  -------------------------------------------------------------------------------------------------------------------
  -- FINE Variabili che NON devono essere inizializzate perchè usate dallo script automaticamente per i suoi scopi --
  -------------------------------------------------------------------------------------------------------------------
  
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
  dbms_output.put_line( 'my_mailbox_folder_name         = ' || my_mailbox_folder_name );

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
  
  -- Controllo variabile flg_commit 
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
      my_datinisca                  := NULL;  
      my_id_account                 := NULL;
      my_username_account           := NULL;
      my_id_mailbox                 := NULL;
      my_mailbox_config_directory   := NULL;
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
      my_mailbox_name               := upper( trim( my_mailbox_data( 1 ) ) );
      my_mailbox_account            := trim( my_mailbox_data( 2 ) );
      my_id_uo_pec                  := trim( my_mailbox_data( 3 ) );
      my_datinisca                  := trim( my_mailbox_data( 4 ) );    
      my_account_config_password    := trim( my_mailbox_data( 5 ) );    
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Estratti dati casella dalla riga: ' || char_LF 
        || '1 - my_mailbox_name            = ' || my_mailbox_name || char_LF
        || '2 - my_mailbox_account         = ' || my_mailbox_account || char_LF
        || '3 - my_id_uo_pec               = ' || my_id_uo_pec || char_LF
        || '4 - my_datinisca               = ' || my_datinisca || char_LF
        || '5 - my_account_config_password = ' || my_account_config_password );
      
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

      -- Controllo variabile my_datinisca 
      step_ciclo := 7;
      if ( trim( my_datinisca ) is null ) then 
        dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La variabile my_datinisca deve essere valorizzata' );
        raise my_exception;
      elsif ( to_char( to_date( my_datinisca, 'DD/MM/YYYY' ), 'DD/MM/YYYY' ) != my_datinisca ) then
          dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': la variabile my_datinisca non è una data valida nel formato GG/MM/AAAA, my_datinisca = ' || my_datinisca );
          raise my_exception;
      end if;
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabile my_datinisca, eseguito' );

      -- Controllo variabile my_account_config_password 
      step_ciclo := 8;
      if ( my_account_config_password is null ) then
        dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': La variabile my_account_config_password deve essere valorizzata' );
        raise my_exception;
      end if;
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabile my_account_config_password, eseguito' );

      -- Imposto variabili identificative dell'account e della casella di scarico da creare
      step_ciclo := 9;
      my_id_account                 := my_mailbox_name || '-ACCOUNT';
      my_username_account           := my_mailbox_account;
      my_id_mailbox                 := my_mailbox_name;
      my_mailbox_config_directory   := 'C:/docprot/protinfo/email/' || my_mailbox_name;
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_account            = ' || my_mailbox_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_name               = ' || my_mailbox_name );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_id_uo_pec                  = ' || my_id_uo_pec );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_id_account                 = ' || my_id_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_username_account           = ' || my_username_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_id_mailbox                 = ' || my_id_mailbox );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': my_mailbox_config_directory   = ' || my_mailbox_config_directory );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Variabili identificative dell''account e della casella di scarico, impostate correttamente' );
      
    end if;      

    -- Controllo che tutte le varibili identificative dell'account e della casella di scarico, siano valorizzate
    step_ciclo := 10;
    if ( my_mailbox_account is NULL
          or my_mailbox_name is NULL
          or my_id_account is NULL
          or my_username_account is NULL
          or my_id_mailbox is NULL
          or my_mailbox_config_directory is NULL
          ) then
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Tutte le variabili identificative dell''account e della casella di scarico, devono essere valorizzate' );
      raise my_exception;
    end if;
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Controllo variabili identificative dell''account e della casella di scarico, eseguito' );
    
    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 1: CREAZIONE DELLA MAILBOX ATTIVA SU INBOX CON LE RELATIVE OPERATION - INIZIO ' );
    dbms_output.put_line( '' );
  
    -- Creazione record in MAILBOX_ACCOUNT oppure tengo in memoria se l'accounto è già esistente
    step_ciclo := 11;
    begin
      select ID_ACCOUNT into my_id_account_esistente from MAILBOX_ACCOUNT where upper( ACCOUNT ) = upper( my_mailbox_account ); 
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Record in MAILBOX_ACCOUNT con ACCOUNT = ' || my_mailbox_account || ' già esistente, viene riutilizzato, my_id_account_esistente = ' || my_id_account_esistente );
      my_id_account := my_id_account_esistente;
    exception when no_data_found then
      insert into MAILBOX_ACCOUNT ( ID_ACCOUNT, ACCOUNT ) values ( my_id_account, my_mailbox_account );
      dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione record in MAILBOX_ACCOUNT, eseguita' );
    end;

    -- Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX
    step_ciclo := 12;
    insert into MAILBOX ( ID_MAILBOX, DESCRIPTION, NAME, PROCESS_FLOW, STATUS, FOLDER, ID_ACCOUNT )
    values ( my_id_mailbox, my_mailbox_name || ' - ' || my_mailbox_folder_name, my_mailbox_name, replace( my_mailbox_process_flow, 'MODEL', my_id_mailbox ), 'active', my_mailbox_folder_name, my_id_account );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX eseguita' );
 
    -- Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata 
    -- Questa operation anche se utilizzata in tutti i flussi avendo id unico, viene referenziata da tutte le MAILBOX
    -- anche se inizialmente viene associata ad una sola MAILBOX.
    step_ciclo := 13;
    begin
      insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( '1', 'StartOperation', my_id_mailbox );
    exception when others then
      null;
    end;
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata, eseguita' );

    -- Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
    step_ciclo := 14;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-SIGNEROP', 'SignerOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-INTEROP', 'InterOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-EGRAMMATAOP', 'EGrammataMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.4';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-CONSEGNA', 'CopyMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.5';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-ELABORATE', 'CopyMessageOperation', my_id_mailbox );
    step_ciclo_sotto := step_ciclo || '.6';
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP', 'DeleteMessageOperation', my_id_mailbox );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX, eseguita' );

    -- Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
    step_ciclo := 15;
    step_ciclo_sotto := step_ciclo || '.1';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.tempfsobject', my_id_mailbox || '-EGRAMMATAOP', my_egr_op_config_tempfsobject );
    step_ciclo_sotto := step_ciclo || '.2';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.rootfsobject', my_id_mailbox || '-EGRAMMATAOP', my_egr_op_config_rootfsobject );
    step_ciclo_sotto := step_ciclo || '.3';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.folderprimario', my_id_mailbox || '-EGRAMMATAOP', 'email' );
    step_ciclo_sotto := step_ciclo || '.4';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.databaseurl', my_id_mailbox || '-EGRAMMATAOP', my_string_db_connection );
    step_ciclo_sotto := step_ciclo || '.5';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.datainizioscarico', my_id_mailbox || '-EGRAMMATAOP', my_datinisca );
    step_ciclo_sotto := step_ciclo || '.6';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-CONSEGNA', 'egracc_cons' );
    step_ciclo_sotto := step_ciclo || '.7';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-ELABORATE', 'egrelaborate' );
    step_ciclo_sotto := step_ciclo || '.8';
    insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'deletemessageoperation.folder', my_id_mailbox || '-DELETEOP', 'INBOX' );
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX, eseguita' );
  
    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 1: CREAZIONE DELLA MAILBOX ATTIVA SU INBOX CON LE RELATIVE OPERATION - FINE ' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 2: CONFIGURAZIONE MAILBOX TABELLA MAILBOX_CONFIG - INIZIO' );
    dbms_output.put_line( '' );
  
    -- Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella INBOX
    step_ciclo := 16;
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

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 2: CONFIGURAZIONE MAILBOX TABELLA MAILBOX_CONFIG - FINE' );
    dbms_output.put_line( '' );
  
    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 3: CONFIGURAZIONE ACCOUNT TABELLA MAILBOX_ACCOUNT_CONFIG - INIZIO' );
    dbms_output.put_line( '' );
  
    step_ciclo := 17;
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
    dbms_output.put_line( prefisso_log_ciclo || step_ciclo || ': Completata configurazione account e della casella di scarico' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 4: ASSOCIAZIONE CASELLA PEC ALLA UO DI STRUTTURA - INIZIO' );
    dbms_output.put_line( '' );

    -- Associazione account PEC con indirizzo my_mailbox_account e struttura UO con ID_UO = my_id_uo_pec
    step_ciclo := 18;
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
          IMAP_PASSWORD,
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
          my_account_config_password,
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
          IMAP_PASSWORD = my_account_config_password,
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
    step_ciclo := 19;
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

    dbms_output.put_line( '' );
    dbms_output.put_line( 'FASE 5: PRODUZIONE ISTRUZIONI SQL PER storage.h2.db - FINE' );
    dbms_output.put_line( '' );

    dbms_output.put_line( '' );
    dbms_output.put_line( '---------------------------------------------' );
    dbms_output.put_line( '-- Elaborazione indice_riga = ' || LPAD( indice_riga, 5 ) || ' - FINE --' );
    dbms_output.put_line( '---------------------------------------------' );
    dbms_output.put_line( '' );
    
    -- Impostazioni variabili per leggere la riga succesiva del CSV memorizzato nel campo T_APPOGGIO.CAMPO_CLOB_INPUT_01
    step_ciclo := 20;
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