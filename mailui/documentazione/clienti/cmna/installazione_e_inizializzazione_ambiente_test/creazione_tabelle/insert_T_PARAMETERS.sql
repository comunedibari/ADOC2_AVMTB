set feedback off
set define off
prompt Disabling triggers for T_PARAMETERS...
alter table T_PARAMETERS disable all triggers;
prompt Loading T_PARAMETERS...
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('INS_AUTO_MITTENTI_IN_RUBRICA', 'PEC', null, null, to_timestamp('27-03-2016 20:31:43.743000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:31:43.743000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'indica se la rubrica indirizzi e-mail deve essere alimentata in automatico:' || chr(10) || 'con gli indirizzi di tutti i mittenti delle e-mail (=true)' || chr(10) || 'solo con i mittenti delle e-mail certificate (=PEC)' || chr(10) || 'con nessun mittente di e-mail (se false o NULL)');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('INS_AUTO_DESTINATARI_IN_RUBRICA', 'OUT', null, null, to_timestamp('27-03-2016 20:31:59.795000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:31:59.795000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'indica se la rubrica indirizzi e-mail deve essere alimentata in automatico' || chr(10) || 'con gli indirizzi di tutti i destinatari delle e-mail ricevute e inviate ( = true)' || chr(10) || 'solo con i destinatari delle e-mail inviate ( = OUT)' || chr(10) || 'solo con i destinatari delle e-mail inviate da caselle PEC ( = OUT_PEC)' || chr(10) || 'solo con i destinatari delle e-mail ricevute ( = IN)' || chr(10) || 'con nessun destinatario di e-mail (se = false o NULL)');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('SEARCH_MAX_NUM_EMAIL', null, null, 250, to_timestamp('27-03-2016 20:32:07.300000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:07.300000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'massimo numero di e-mail che possono essere restituite insieme; è anche il limite di occorrenze-email trovate su Lucene superato il quale viene dato errore');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('WILDCARD', '*,%', null, null, to_timestamp('27-03-2016 20:32:07.519000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:07.519000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'caratteri usati come wildcard nelle ricerche su stringhe (separati da , se più di uno)');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('ARCHIVIAZIONE_AUTO_EMAIL_PROTOCOLLATE', 'true', null, null, to_timestamp('27-03-2016 20:32:07.799000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:07.799000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'spostamento automatico delle e-mail in arrivo nelle "archiviate" na volta che sono state protocollate (tutti gli allegati). Valori ammessi: true e false');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('ARCHIVIAZIONE_AUTO_EMAIL_POST_ECCEZIONE', 'true', null, null, to_timestamp('27-03-2016 20:32:08.078000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:08.078000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'spostamento automatico delle e-mail in arrivo nelle "archiviate" una volta che sono state respinte con notifica di eccezione. Valori ammessi: true e false');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('INVIO_CONFERMA_EMAIL_PROTOCOLLATA', 'auto', null, null, to_timestamp('27-03-2016 20:32:08.286000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:08.286000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Modalità invio notifica interoperabile conferma ricezione per le mail interoperabili protocollate (auto=automatica)');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('DURATA_LOCK', null, null, 10, to_timestamp('27-03-2016 20:32:08.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:08.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Durata dei lock sulle e-mail espressa in minuti');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('TIPO_RICEVUTA_PEC', 'S', null, null, to_timestamp('27-03-2016 20:32:08.700000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:08.700000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Tipo di ricevuta PEC per le mail inviate: C = Completa, B = Breve, S = Sintetica');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('ATTACHMENT_MAX_SIZE', null, null, 20, to_timestamp('27-03-2016 20:32:08.921000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:08.921000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Massima dimensione - in MB - degli allgati di una mail inviata. Se il parametro deve valere per una sola casella deve avere suffisso .ID_ACCOUNT nella PAR_KEY');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('ATTACHMENT_CHECK_TYPE', 'assoluto', null, null, to_timestamp('27-03-2016 20:32:09.184000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:09.184000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Indica se ATTACHMENT_MAX_SIZE vale indipendentemente dal n.ro di destinatari (=assoluto) o va diviso per i destinatari (=relativo). Se il parametro deve valere per una sola casella deve avere suffisso .ID_ACCOUNT nella PAR_KEY');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('SPECIFIC_SENDER_CONFIG#1', '*@pec.fatturapa.it', null, null, to_timestamp('27-03-2016 20:32:09.403000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:09.403000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Indirizzo di ricezione #1 per cui si seguono logiche specifiche di folderizzazione, può essere anche una classe di inidirizzi con wildcard iniziale o finale');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('SPECIFIC_FOLDER_CONFIG#1', 'standard.arrivo.fatturePA', null, null, to_timestamp('27-03-2016 20:32:09.615000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:09.615000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Classifica del folder in cui andranno le email provenienti dallo specifico sender #1');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('ATTIVA_CIFRATURA_PWD', 'false', null, null, to_timestamp('27-03-2016 20:32:13.114000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:13.114000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Se true si attiva la cifratura delle password');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('STRING#1', 'tvnw63ufg9gh5392', null, null, to_timestamp('27-03-2016 20:32:16.688000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:16.688000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Parola da ricercare nel subject per smistare la mail');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('MAX_NUM_DESTINATARI_INVIO_EMAIL', null, null, 100, to_timestamp('27-03-2016 20:32:16.900000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('27-03-2016 20:32:16.900000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Massimo n.ro di indirizzi destianatari ammessi in un invio e-mail');
insert into T_PARAMETERS (par_key, str_value, date_value, num_value, setting_time, ts_ins, meaning)
values ('MAX_MINUTI_PROCESSO_MAIL', null, null, 5, to_timestamp('21-07-2016 11:30:07.848171', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('21-07-2016 11:30:07.848171', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Durata massima in minuti per l''elaborazione di un messaggio. Passati i minuti l''operazione viene interrotta e sarà ripresa alla successiva schedulazione per la mailbox');
commit;
prompt 17 records loaded
prompt Enabling triggers for T_PARAMETERS...
alter table T_PARAMETERS enable all triggers;
set feedback on
set define on
prompt Done.
/

SHOW ERRORS;