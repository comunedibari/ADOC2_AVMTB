-- Con "undefine" la valorizzazione viene chiesta all'utente
undefine LOGS_PATH;
undefine DB_ALIAS;

-- Prevalorizzazione delle variabili: controllare i valori da imopstare
SET DEFINE ON;

--define LOGS_PATH   = "D:\TEMP\SQL\LOGS";
--define DB_ALIAS    = "EGR_CMNA_TEST";

-- File di log generale
SPOOL &&LOGS_PATH/log.txt;

prompt INSTALL.SQL - INIZIO

conn mmaster/mmaster@&&DB_ALIAS

@@CreaDB_x_FileOperation.sql;
prompt eseguito DB_x_FileOperation.sql

@@create_tabelle_mailbox.sql;
prompt eseguito create_tabelle_mailbox.sql

@@create_T_APPOGGIO.SQL;
prompt eseguito @@create_T_APPOGGIO.SQL

commit;

prompt INSTALL.SQL - FINE

SPOOL OFF;
/	 

SHOW ERRORS;