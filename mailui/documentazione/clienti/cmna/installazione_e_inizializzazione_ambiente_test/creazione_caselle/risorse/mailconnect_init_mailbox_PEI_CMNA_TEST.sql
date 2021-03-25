declare
     
  --------------------------------------------------------------------------------------------------------------------
  -- INIZIO Variabili che devono essere obbligatoriamente inizializzate seguendo scrupolosamente le regole indicate --
  --------------------------------------------------------------------------------------------------------------------
  
  -- PREREQUISITO:
  -- Si prenda visione e si segua passo passo quanto descritto nel doc "EGR_CMNA_Readme_Crea_PEC.doc", con particolare 
  -- riferimento al paragrafo "Istruzioni per creare la PEC PEI"
  
  -- Flag TRUE/FALSE per attivare o meno il commit di questa procedura
  flg_commit boolean := TRUE;

  -- Flag TRUE/FALSE per indicare se la casella è anche la PEI ovvero la PEC Istituzionale (TRUE), oppure no (FALSE)
  -- Deve essere obbligatoriamente impostato a TRUE o a FALSE
  flg_PEI boolean := TRUE;

  -- Indirizzo univoco di una PEC (univoca). Sarà utilizzato per valorizzare anche il campo: campo MAILBOX_ACCOUNT.ACCOUNT
  -- e se non è valorizzata la variabile my_username_account, sarà usata per valorizzare anche la username della casella
  -- all'interno della tabella MAILBOX_CONFIG_ACCOUNT.
  -- Esempi: 'aoo1@pec.comune.napoli.it', 'aoo2@pec.comune.napoli.it', ecc... 
  -- my_mailbox_account varchar2(150) := NULL;
  my_mailbox_account varchar2(150) := 'test@pec.comune.napoli.it';

  -- Nome breve univoco, identificativo della mailbox e che magari richiama mentalmente l'account definito con la variabile my_mailbox_account.
  -- La mailbox di riferimento è quella attiva per lo scarico dalla cartella INBOX. 
  -- Questa variabile sarà utilizzata per valorizzare: i campi MAILBOX.ID_MAILBOX e MAILBOX.NAME,
  -- altre config del MailConnect (cartella in C:/docprot/protinfo/email/ e storage.h2.db).
  -- Esempi: 'AOO1' per richiamare 'aoo1@pec.comune.napoli.it', 'AOO2' per richiamare 'aoo2@pec.comune.napoli.it', e di 
  -- conseguenza le cartelle associate da creare saranno: C:/docprot/protinfo/email/AOO1, C:/docprot/protinfo/email/AOO2 per lo scarico da INBOX. 
  -- my_mailbox_name varchar2(20) := NULL;
  my_mailbox_name varchar2(20) := 'TESTPEI'; 

  -- Password dell'account definito con la variabile my_username_account
  -- Valore parametro 'mail.password'
  my_account_config_password VARCHAR2(400) := 'testengineering2012';   
  
  -- Valore parametro 'egrammatamessageoperation.datainizioscarico', data da cui iniziare lo scarico
  my_egr_op_config_datinisca VARCHAR2(400) := '27/10/2017';

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
  
  -- Username dell'account, per default è in pratica lo stesso valore della variabile my_mailbox_account.
  -- Pertanto valorizzare la variabile solo se il suo valore è diverso dal valore della variabile my_mailbox_account 
  -- Esempi: come per my_mailbox_account ad esempio aoo1@pec.comune.napoli.it
  my_username_account varchar2(150) := my_mailbox_account;
  
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
  
  -- Variabili per la PEI funzione e-grammata "Parametri Interoperabilità" (tranne il COD AOO)
  my_cod_aoo VARCHAR2(1000)                      := 'c_f839';
  my_des_aoo VARCHAR2(1000)                      := 'Comune di Napoli';
  my_template_subj_u VARCHAR2(1000)              := 'Messaggio di Protocollo (D.P.R. n. 445/2000) - n.ro {numProt} del {dataProt}';
  my_template_body_u VARCHAR2(1000)              := 'Amministrazione:[{codAmm}] {desAmm},
Area Organizzativa Omogenea: [{codAoo}] {desAoo}

In allegato al presente messaggio si trasmette la documentazione di cui siete destinatari, registrata in uscita nel sistema di Protocollo Informatico del Comune con numero  {numProt} del {dataProt}.
Cordiali saluti.
{desAoo} ';
  my_template_conferma VARCHAR2(1000)            := 'La e-mail e/o documentazione dal titolo:{emailMittenteTitolo}
è stata registrata nel Protocollo Informatico del {desAoo} con n.ro {numProt} del {dataProt}.
Per maggiori informazioni rivolgersi all''URP.
Cordiali saluti.
{desAoo} ';
  my_template_eccezione VARCHAR2(1000)           := 'La e-mail e/o documentazione dal titolo:{emailMittenteTitolo}
è stata rifiutata dal protocollo del {desAoo} per i seguenti motivi: {motivoEccezione}.

Per maggiori informazioni consultare il Manuale di Gestione del Protocollo Informatico.
Cordiali saluti.
{desAoo} ';
  my_template_aggiorna VARCHAR2(1000)            := 'Amministrazione [{codAmm}] - {desAmm}
Area Organizzativa Omogenea [{codAoo}]  - {desAoo}
Protocollo n.ro {numProt} del {dataProt} modificato con la seguente motivazione:
{motivoAggiornamento}.
Cordiali saluti.
{desAmm}
';
  my_template_annulla VARCHAR2(1000)             := 'Amministrazione [{codAmm}] - {desAmm}
Area Organizzativa Omogenea [{codAoo}] - {desAoo}
Il Protocollo n.ro {numProt} del {dataProt} è stato annullato con la seguente motivazione:{motivoAnnullamento}.
Cordiali saluti.
{desAoo} 
';
    
  --------------------------------------------------------------------------------------------------------------------
  -- FINE Variabili che devono essere obbligatoriamente inizializzate seguendo scrupolosamente le regole indicate --
  --------------------------------------------------------------------------------------------------------------------


  ---------------------------------------------------------------------------------------------------------------------
  -- INIZIO Variabili che NON devono essere inizializzate perchè usate dallo script automaticamente per i suoi scopi --
  ---------------------------------------------------------------------------------------------------------------------
  
  -- Contatore dei passi eseguiti dalla stored, utile per scopo debug
  step integer := 0;
  
  -- Campo MAILBOX_ACCOUNT.ID_ACCOUNT associato alla MAILBOX
  my_id_account VARCHAR2(40) := my_mailbox_name || '-ACCOUNT';
  
  -- Campo MAILBOX_ACCOUNT.ID_ACCOUNT associato alla MAILBOX se esiste già un record con MAILBOX.ACCOUNT = my_mailbox_account
  my_id_account_esistente VARCHAR2(40) := NULL;

  -- Campo MAILBOX.MAILBOX_ID coincidente con il nome, campo MAILBOX.NAME
  my_id_mailbox VARCHAR2(40) := my_mailbox_name;
  
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

  count_temp NUMBER                := 0;
  
  -- Variabili utili alla gestione delle eccezioni
  sql_error_code integer           := null;
  sql_error_msg varchar2(2000)     := null;
  my_exception EXCEPTION;
  
  -------------------------------------------------------------------------------------------------------------------
  -- FINE Variabili che NON devono essere inizializzate perchè usate dallo script automaticamente per i suoi scopi --
  -------------------------------------------------------------------------------------------------------------------
  
begin

  if ( trim( my_username_account ) is null ) then 
      my_username_account := my_mailbox_account;
  end if;

  dbms_output.put_line( '-------------------------------------------' );
  dbms_output.put_line( '-- Dati Configurazione mailbox e account -- ' );
  dbms_output.put_line( '-------------------------------------------' );
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
  dbms_output.put_line( 'my_account_config_password     = ' || my_account_config_password );
  dbms_output.put_line( 'my_string_db_connection        = ' || my_string_db_connection );
  dbms_output.put_line( 'my_account_config_imaphost     = ' || my_account_config_imaphost );
  dbms_output.put_line( 'my_account_config_imapport     = ' || my_account_config_imapport );
  dbms_output.put_line( 'my_account_config_smtphost     = ' || my_account_config_smtphost );
  dbms_output.put_line( 'my_account_config_smtpoort     = ' || my_account_config_smtpport );
  dbms_output.put_line( 'my_id_account                  = ' || my_id_account );
  dbms_output.put_line( 'my_id_mailbox                  = ' || my_id_mailbox );
  dbms_output.put_line( 'my_mailbox_folder_name         = ' || my_mailbox_folder_name );
  dbms_output.put_line( 'my_egr_op_config_datinisca     = ' || my_egr_op_config_datinisca );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-----------------------' );
  dbms_output.put_line( '-- FASE 0: CONTROLLI --' );
  dbms_output.put_line( '-----------------------' );
  dbms_output.put_line( '' );

  -- Controllo variabile flg_commit 
  step := 1;
  if ( flg_commit is null ) then 
      dbms_output.put_line( 'la variabile flg_commit deve essere impostata al valore TRUE o FALSE' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile flg_commit, eseguito' );

  -- Controllo variabile flg_PEI 
  step := 2;
  if ( flg_PEI is null ) then 
      dbms_output.put_line( 'la variabile flg_PEI deve essere impostata al valore TRUE o FALSE' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile flg_PEI, eseguito' );

  -- Controllo variabile my_mailbox_account 
  step := 3;
  if ( trim( my_mailbox_account ) is null ) then 
      dbms_output.put_line( 'la variabile my_mailbox_account deve essere valorizzata' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_mailbox_account, eseguito' );

  -- Controllo variabile my_mailbox_name 
  step := 4;
  if ( my_mailbox_name is null ) then 
    dbms_output.put_line( 'La variabile my_mailbox_name deve essere valorizzata' );
    raise my_exception;
  elsif ( instr( my_mailbox_name, ' ' ) > 0 ) or LENGTH( TRIM( TRANSLATE( my_mailbox_name, '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ', ' ' ) ) ) > 0 then
    dbms_output.put_line( 'La variabile my_mailbox_name deve essere composta da sole lettere maiuscole e/o numeri' );
    raise my_exception;
  else
    count_temp := 0;
    select count(*) into count_temp from MAILBOX where ID_MAILBOX = my_mailbox_name;
    if ( count_temp > 0 ) then
      dbms_output.put_line( 'La casella ' || my_mailbox_name || ' è già presente in tabella MAILBOX' );
      raise my_exception;
    end if;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_mailbox_name, eseguito' );

  -- Controllo variabile my_string_db_connection 
  step := 5;
  if ( trim( my_string_db_connection ) is null ) then 
      dbms_output.put_line( 'la variabile my_string_db_connection deve essere valorizzata' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_string_db_connection, eseguito' );

  -- Controllo variabile my_account_config_imaphost 
  step := 6;
  if ( trim( my_account_config_imaphost ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_imaphost deve essere valorizzata' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_imaphost, eseguito' );

  -- Controllo variabile my_account_config_imapport 
  step := 7;
  if ( trim( my_account_config_imapport ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_imapport deve essere valorizzata' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_imapport, eseguito' );
  
  -- Controllo variabile my_account_config_smtphost 
  step := 8;
  if ( trim( my_account_config_smtphost ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_smtphost deve essere valorizzata' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_smtphost, eseguito' );

  -- Controllo variabile my_account_config_smtpport 
  step := 9;
  if ( trim( my_account_config_smtpport ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_smtpport deve essere valorizzata' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_smtpport, eseguito' );

  -- Controllo variabile my_account_config_password 
  step := 10;
  if ( trim( my_account_config_password ) is null ) then 
      dbms_output.put_line( 'la variabile my_account_config_password deve essere valorizzata' );
      raise my_exception;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_account_config_password, eseguito' );

  -- Controllo variabile my_egr_op_config_datinisca 
  step := 11;
  if ( trim( my_egr_op_config_datinisca ) is null ) then
    dbms_output.put_line( 'la variabile my_datinisca deve essere valorizzata' );
    raise my_exception;
  elsif ( trim( my_egr_op_config_datinisca ) is not null ) then 
      if ( to_char( to_date( my_egr_op_config_datinisca, 'DD/MM/YYYY' ), 'DD/MM/YYYY' ) != my_egr_op_config_datinisca ) then
        dbms_output.put_line( 'la variabile my_egr_op_config_datinisca non è una data valida nel formato GG/MM/AAAA, my_egr_op_config_datinisca = ' || my_egr_op_config_datinisca );
        raise my_exception;
      end if;
  end if;
  dbms_output.put_line( 'step = ' || step || ': Controllo variabile my_egr_op_config_datinisca, eseguito' );

  dbms_output.put_line( 'Controlli eseguiti.' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------------------------------------------------------' );
  dbms_output.put_line( '-- FASE 1: CREAZIONE DELLA MAILBOX ATTIVA SU INBOX CON LE RELATIVE OPERATION --' );
  dbms_output.put_line( '-------------------------------------------------------------------------------' );
  dbms_output.put_line( '' );
  
  -- Creazione record in MAILBOX_ACCOUNT
  step := 12;
  begin
    select ID_ACCOUNT into my_id_account_esistente from MAILBOX_ACCOUNT where ACCOUNT = my_mailbox_account; 
    dbms_output.put_line( 'step = ' || step || ': Record in MAILBOX_ACCOUNT con ACCOUNT = ' || my_mailbox_account || ' già esistente, my_id_account_esistente = ' || my_id_account_esistente );
    my_id_account := my_id_account_esistente;
  exception when no_data_found then
    insert into MAILBOX_ACCOUNT ( ID_ACCOUNT, ACCOUNT ) values ( my_id_account, my_mailbox_account );
    dbms_output.put_line( 'step = ' || step || ': Creazione record in MAILBOX_ACCOUNT eseguita' );
  end;

  -- Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX
  step := 13;
  insert into MAILBOX ( ID_MAILBOX, DESCRIPTION, NAME, PROCESS_FLOW, STATUS, FOLDER, ID_ACCOUNT )
  values ( my_id_mailbox, my_mailbox_name || ' - ' || my_mailbox_folder_name, my_mailbox_name, replace( my_mailbox_process_flow, 'MODEL', my_id_mailbox ), 'active', my_mailbox_folder_name, my_id_account );
  dbms_output.put_line( 'step = ' || step || ': Creazione record in MAILBOX relativo allo scarico dalla cartella INBOX eseguita' );
 
  -- Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata 
  -- Questa operation anche se utilizzata in tutti i flussi avendo id unico, viene referenziata da tutte le MAILBOX
  -- anche se inizialmente viene associata ad una sola MAILBOX.
  step := 14;
  begin
    insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( '1', 'StartOperation', my_id_mailbox );
  exception when others then
    null;
  end;
  dbms_output.put_line( 'step = ' || step || ': Creazione della StartOperation in MAILBOX_OPERATION, se non è già stata creata, eseguita' );
  
  -- Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
  step := 15;
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-SIGNEROP', 'SignerOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-INTEROP', 'InterOperation', my_id_mailbox );
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-EGRAMMATAOP', 'EGrammataMessageOperation', my_id_mailbox );
  -- Operation copia notifiche accettazione, consegna, delivery
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-CONSEGNA', 'CopyMessageOperation', my_id_mailbox );
  -- Operation copia elaborate 
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-COPYOP-ELABORATE', 'CopyMessageOperation', my_id_mailbox );
  -- Operation cancellazione messaggi
  insert into MAILBOX_OPERATION ( ID_MAILBOX_OPERATION, OPERATION_NAME, ID_MAILBOX ) values ( my_id_mailbox || '-DELETEOP', 'DeleteMessageOperation', my_id_mailbox );
  dbms_output.put_line( 'step = ' || step || ': Creazione di tutte le operation in MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX eseguita' );

  -- Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX
  step := 16;
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.tempfsobject', my_id_mailbox || '-EGRAMMATAOP', my_egr_op_config_tempfsobject );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.rootfsobject', my_id_mailbox || '-EGRAMMATAOP', my_egr_op_config_rootfsobject );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.folderprimario', my_id_mailbox || '-EGRAMMATAOP', 'email' );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.databaseurl', my_id_mailbox || '-EGRAMMATAOP', my_string_db_connection );
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'egrammatamessageoperation.datainizioscarico', my_id_mailbox || '-EGRAMMATAOP', my_egr_op_config_datinisca );
  -- Folder di destinazione notifiche
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-CONSEGNA', 'egracc_cons' );
  -- Folder di destinazione elaborate   
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'copymessageoperation.destinationfolder', my_id_mailbox || '-COPYOP-ELABORATE', 'egrelaborate' );
  -- Folder da cui cancellare i messaggi
  insert into MAILBOX_OPERATION_CONFIG ( CONFIG_KEY, ID_MAILBOX_OPERATION, CONFIG_VALUE ) values ( 'deletemessageoperation.folder', my_id_mailbox || '-DELETEOP', 'INBOX' );
  dbms_output.put_line( 'step = ' || step || ': Creazione di tutte le MAILBOX_OPERATION_CONFIG associate alle MAILBOX_OPERATION previste dal flusso di scarico attivo sulla INBOX eseguita' );

  dbms_output.put_line( '' );
  dbms_output.put_line( '-----------------------------------------------------------' );
  dbms_output.put_line( '-- FASE 2: CONFIGURAZIONE MAILBOX TABELLA MAILBOX_CONFIG --' );
  dbms_output.put_line( '-----------------------------------------------------------' );
  dbms_output.put_line( '' );
  
  -- Creazione records in MAILBOX_CONFIG relativi alla MAILBOX con scarico dalla cartella INBOX
  step := 17;
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

  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------------------------------------------' );
  dbms_output.put_line( '-- FASE 3: CONFIGURAZIONE ACCOUNT TABELLA MAILBOX_ACCOUNT_CONFIG --' );
  dbms_output.put_line( '-------------------------------------------------------------------' );
  dbms_output.put_line( '' );
  
  step := 18;
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

  step := 19;
  if ( flg_PEI ) then
    
    dbms_output.put_line( '' );
    dbms_output.put_line( '-----------------------------------------------------------------------------------------------------' );
    dbms_output.put_line( '-- FASE 4: CONFIGURAZIONE PARAMETRI PEI PER LA FUNZIONE DI E-GRAMMATA ''PARAMETRI INTEROPERABILITA'' --' );
    dbms_output.put_line( '-----------------------------------------------------------------------------------------------------' );
    dbms_output.put_line( '' );

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_COD_AOO WHERE codice_par = 'COD_AOO';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'COD_AOO', my_cod_aoo, my_mailbox_config_ente, 'Codica AOO IPA' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_DES_AOO WHERE codice_par = 'DES_AOO';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'DES_AOO', my_des_aoo, my_mailbox_config_ente, 'Descrizione AOO IPA' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_mailbox_account WHERE codice_par = 'SMTP_PEI_ACCOUNT';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'SMTP_PEI_ACCOUNT', my_mailbox_account, my_mailbox_config_ente, 'Account di posta elettronica che appare come indirizzo telematico del mittente' );
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
        VALUES ( 'TEMPLATE_CONFERMA', my_template_conferma, my_mailbox_config_ente, 'Email non interoperabili: template per il testo della conferma' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_eccezione WHERE codice_par = 'TEMPLATE_ECCEZIONE';
      if sql%rowcount=0 then 
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'TEMPLATE_ECCEZIONE', my_template_eccezione, my_mailbox_config_ente, 'Email non interoperabili: template per il testo dell''eccezione' );
      end if;
    END;
    
    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_aggiorna WHERE codice_par = 'TEMPLATE_AGGIORNA';
      if sql%rowcount=0 then
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE ) 
        VALUES ( 'TEMPLATE_AGGIORNA', my_template_aggiorna, my_mailbox_config_ente, 'Email non interoperabili: template per il testo dell''aggiornamento' );
      end if;
    END;

    BEGIN
      UPDATE CVT_PARAMETRI SET par_value = my_template_annulla WHERE codice_par = 'TEMPLATE_ANNULLA';
      if sql%rowcount=0 then 
        INSERT INTO CVT_PARAMETRI ( CODICE_PAR, PAR_VALUE, COD_ENTE, DESCRIZIONE) 
        VALUES ( 'TEMPLATE_ANNULLA', my_template_annulla, my_mailbox_config_ente, 'Email non interoperabili: template per il testo dell''annullamento' );
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

/*
INSERT INTO "PUBLIC"."STORAGES" (ID_STORAGE,FLG_DISATTIVO,TIPO_STORAGE,XML_CONFIG) VALUES ('TESTPEI',0,'FS','<?xml version="1.0" encoding="UTF-8" standalone="yes"?><fileSystemStorageConfig><baseFolderPath>C:/docprot/protinfo/email/TESTPEI</baseFolderPath><repositoryLocations>C:/docprot/protinfo/email/TESTPEI</repositoryLocations><tempRepositoryBasePath>C:/docprot/protinfo/email/temp</tempRepositoryBasePath></fileSystemStorageConfig>');
INSERT INTO "PUBLIC"."UTILIZZATORI_STORAGE" (ID_UTILIZZATORE,ID_STORAGE) VALUES ('MailConnect.TESTPEI','TESTPEI');
*/
