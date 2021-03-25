CREATE TABLE T_APPOGGIO 
(
  CAMPO_CLOB CLOB,
  CAMPO_CLOB_INPUT_01 CLOB,
  CAMPO_CLOB_OUTPUT_01 CLOB,
  CAMPO_CLOB_OUTPUT_02 CLOB
)
/   

comment on table T_APPOGGIO is 'Tabella di appoggio per scopi vari, es. per script di caricamento'
/

comment on column T_APPOGGIO.CAMPO_CLOB is 'Campo di appoggio di tipo CLOB per scopi generici';
/

comment on column T_APPOGGIO.CAMPO_CLOB_INPUT_01 is 'Campo di appoggio di tipo CLOB ad esempio per memorizzare l''input di uno script';
/

comment on column T_APPOGGIO.CAMPO_CLOB_OUTPUT_01 is 'Campo di appoggio di tipo CLOB ad esempio per memorizzare l''output di uno script';
/

comment on column T_APPOGGIO.CAMPO_CLOB_OUTPUT_02 is 'Campo di appoggio di tipo CLOB ad esempio per memorizzare l''output di uno script';
/

create or replace synonym ENTE02.T_APPOGGIO for MMASTER.T_APPOGGIO;
grant select, insert, update, delete, references, alter, index on T_APPOGGIO to ENTE02; 

@@insert_T_APPOGGIO.sql;

SHOW ERRORS;