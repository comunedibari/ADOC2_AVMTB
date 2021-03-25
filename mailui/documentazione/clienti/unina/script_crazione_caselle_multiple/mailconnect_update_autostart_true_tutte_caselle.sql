-- Script per impostare il parametro autostart = true                              
-- per tutti i flussi di scarico dalle caselle PEC configurate sul MailConnect.    
-- In tal modo al successivo riavvio del MailConnect i flussi saranno tutti attivi.

begin

    update MAILBOX_CONFIG mc
    set mc.config_value = 'true'
    where mc.config_key like 'mail.mailbox.autostart';
    
    dbms_output.put_line( 'Eseguita update MAILBOX_CONFIG, record modificati: ' || sql%rowcount );

    COMMIT;

    dbms_output.put_line( '' );
    dbms_output.put_line( 'ESEGUITO COMMIT!' );
    dbms_output.put_line( '' );
    dbms_output.put_line( '---------------------------------------------' );
    dbms_output.put_line( '-- ESITO: PROCEDURA TERMINATA CON SUCCESSO --' );
    dbms_output.put_line( '---------------------------------------------' );
    dbms_output.put_line( '' );

exception when others then

  ROLLBACK;
  
  dbms_output.put_line( '' );
  dbms_output.put_line( 'ESEGUITO ROLLBACK!' );
  dbms_output.put_line( '' );
  dbms_output.put_line( '-------------------------------------------' );
  dbms_output.put_line( '-- ESITO: PROCEDURA TERMINATA CON ERRORI --' );
  dbms_output.put_line( '-------------------------------------------' );
  dbms_output.put_line( '' );

end;

