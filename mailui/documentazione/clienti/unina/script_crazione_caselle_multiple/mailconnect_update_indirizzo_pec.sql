-- Script per modificare un indirizzo PEC
-- Impostare la variabile "pIndirizzoPecOLD" con il valore dell'indirizzo da modificare
-- Impostare la variabile "pIndirizzoPecNEW" con il nuovo valore dell'indirizzo PEC che deve sostituire il precedente
-- Esempio:
--    pIndirizzoPecOLD VARCHAR2(150) := 'prova@pec.unina.it';
--    pIndirizzoPecNEW VARCHAR2(150) := 'provatest@pec.unina.it';

declare
 
    pIndirizzoPecOLD VARCHAR2(150) := '<imposta qui indirizzo PEC da modificare>';
    pIndirizzoPecNEW VARCHAR2(150) := '<imposta qui nuovo indirizzo PEC che deve sostituire il precedente>';
    
begin
    
    update MAILBOX_ACCOUNT a 
    set a.account = pIndirizzoPecNEW
    where a.account = pIndirizzoPecOLD;
    dbms_output.put_line( 'Eseguita update MAILBOX_ACCOUNT, record modificati: ' || sql%rowcount );

    update MAILBOX_ACCOUNT_CONFIG ac 
    set ac.config_value = pIndirizzoPecNEW
    where ac.config_value = pIndirizzoPecOLD
    and ac.config_key = 'mail.username';
    dbms_output.put_line( 'Eseguita update MAILBOX_ACCOUNT_CONFIG, record modificati: ' || sql%rowcount );
    
    update CVT_UO_PEC uopec
    set uopec.email = pIndirizzoPecNEW,
        uopec.imap_username = pIndirizzoPecNEW
    where uopec.email = pIndirizzoPecOLD;
    dbms_output.put_line( 'Eseguita update CVT_UO_PEC, record modificati: ' || sql%rowcount );

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
