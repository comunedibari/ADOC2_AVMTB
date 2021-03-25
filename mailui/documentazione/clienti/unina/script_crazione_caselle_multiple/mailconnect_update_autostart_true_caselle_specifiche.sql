-- Script per impostare il parametro autostart = true                              
-- per tutti i flussi di scarico relativi agli indirizzi pec specificati nella clausola IN
-- commentata dalla stringa "lista account"

begin

    update MAILBOX_CONFIG mc
    set mc.config_value = 'true'
    where mc.config_key = 'mail.mailbox.autostart'
    and mc.id_mailbox
    in
    (
        select id_mailbox 
        from MAILBOX m, MAILBOX_ACCOUNT ma
        where m.id_account = ma.id_account
        and lower( ma.account )
        
        -- lista account
        -- specificare qui la lista di indirizzi PEC da attivare
        IN 
        (
            --  lower( 'aoo1@pec.it' )
            -- ,lower( 'aoo2@pec.it' )
            -- ,lower( 'aoo3@pec.it' )
            -- ....
        )
    );
    
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
