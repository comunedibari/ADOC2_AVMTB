-- Table T_ANAG_FORMATI_DIG

CREATE TABLE "T_ANAG_FORMATI_DIG"(
  "ID_DIG_FORMAT" Varchar2(64 ) NOT NULL,
  "NOME" Varchar2(250 ) NOT NULL,
  "VERSIONE" Varchar2(30 ),
  "ALTRI_NOMI" Varchar2(1000 ),
  "ESTENSIONE_PRINCIPALE" Varchar2(5 ) NOT NULL,
  "MIMETYPE_PRINCIPALE" Varchar2(100) NOT NULL,
  "DES_ESTESA" Varchar2(1000 ),
  "ID_REC_DIZ_REGISTRO_FMT" Varchar2(64 ) NOT NULL,
  "ID_IN_REGISTRO_FMT" Varchar2(64 ) NOT NULL,
  "ID_REC_DIZ_TIPO_FMT" Varchar2(64 ),
  "BYTE_ORDERS" Varchar2(3 ),
  "DT_RILASCIO" Date,
  "DT_DESUPP" Date,
  "RISCHI" Varchar2(4000 ),
  "SVILUPPATO_DA" Varchar2(250 ),
  "MANTENUTO_DA" Varchar2(250 ),
  "FLG_COPYRIGHT" Number(1,0) NOT NULL,
  "FLG_INDICIZZABILE" Integer NOT NULL,
  "FLG_STATO" Varchar2(2 ) NOT NULL,
  "TS_AGG_STATO" Date DEFAULT sysdate NOT NULL,
  "TS_INS" Timestamp(6) DEFAULT systimestamp NOT NULL,
  "ID_UTE_INS" Varchar2(64 ),
  "TS_ULTIMO_AGG" Timestamp(6) DEFAULT systimestamp NOT NULL,
  "ID_UTE_ULTIMO_AGG" Varchar2(64 ),
  "FLG_ANN" Number(1,0) DEFAULT 0 NOT NULL,
  CONSTRAINT "CHK_T_FORMATI_DIG_0" CHECK ("BYTE_ORDERS" in ('L', 'B', 'M', 'LB', 'LM', 'BM', 'LBM')),
  CONSTRAINT "CHK_T_FORMATI_DIG_1" CHECK ("FLG_COPYRIGHT" in (0, 1)),
  CONSTRAINT "CHK_T_FORMATI_DIG_2" CHECK ("FLG_INDICIZZABILE" in (0, 1, 2)),
  CONSTRAINT "CHK_T_FORMATI_DIG_3" CHECK ("FLG_STATO" in ('A', 'P', 'NA')),
  CONSTRAINT "CHK_T_FORMATI_DIG_4" CHECK ("FLG_ANN" in (0,1))
)
/

-- Create indexes for table T_ANAG_FORMATI_DIG

CREATE UNIQUE INDEX "UK_ANAG_FORMATI_DIG_1" ON "T_ANAG_FORMATI_DIG" ("VERSIONE","NOME")
/

CREATE UNIQUE INDEX "UK_ANAG_FORMATI_DIG_2" ON "T_ANAG_FORMATI_DIG" ("ID_REC_DIZ_REGISTRO_FMT","ID_IN_REGISTRO_FMT")
/

CREATE INDEX "IDX_ANAG_FORMATI_DIG_1" ON "T_ANAG_FORMATI_DIG" ("ID_REC_DIZ_TIPO_FMT")
/

CREATE INDEX "IDX_ANAG_FORMATI_DIG_TS" ON "T_ANAG_FORMATI_DIG" ("TS_ULTIMO_AGG")
/

-- Add keys for table T_ANAG_FORMATI_DIG

ALTER TABLE "T_ANAG_FORMATI_DIG" ADD CONSTRAINT "PK_T_FORMATI_DIG" PRIMARY KEY ("ID_DIG_FORMAT")
/

-- Table and Columns comments section
  
COMMENT ON TABLE "T_ANAG_FORMATI_DIG" IS 'Anagrafe dei formati digitali e loro specifiche versioni che il sistema è in grado di riconoscere'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ID_DIG_FORMAT" IS 'Identificativo univoco (UUID) del formato digitale o di unaspecifica versione di un formato'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."NOME" IS 'Nome che identifica il formato ed eventualmente la/e specifiche versioni rappresentate dal record'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."VERSIONE" IS 'Indica la specifica versione di formato che il record rappresenta'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ALTRI_NOMI" IS 'Altri nomi con cui è noto il formato (o la/le versioni del formato rappresentate dal record)'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ESTENSIONE_PRINCIPALE" IS 'Estensione principale dei file del dato formato (lowercase e senza .iniziale)'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."MIMETYPE_PRINCIPALE" IS 'Mimetype principale'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."DES_ESTESA" IS 'Descrizione dettagliata del formato o della/e specifiche versioni di formato che il record rappresenta'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ID_REC_DIZ_REGISTRO_FMT" IS 'Identificativo del record di dizionario che rappresenta il registro internazionale di formati (PRONOM, UDFR...) da cui è presa l''identificativo del formato contenuto nel campo ID_IN_REGISTRO_FMT'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ID_IN_REGISTRO_FMT" IS 'Identificativo univoco con cui il formato/versione di formato è identificato nel registro di formati indicato in ID_REC_DIZ_REGISTRO_FMT'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ID_REC_DIZ_TIPO_FMT" IS 'Identificativo del record di dizionario che esprime la lassificazione/tipo del formato: word processor, image (vector) ecc'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."BYTE_ORDERS" IS 'Byte order possibile/i per il formato/versione di formato: Little-Endian (L), Big-Endian (B), Mixed-Endian o Middle-Endian (M).'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."DT_RILASCIO" IS 'Data il cui il formato/versione di formato e'' stato rilasciato'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."DT_DESUPP" IS 'Data a partire dalla quale il formato è o sarà desupportato'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."RISCHI" IS 'Rischi connessi con l''utilizzo del formato/versione'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."SVILUPPATO_DA" IS 'Nome della societa''/organizzazione che ha sviluppato il formato'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."MANTENUTO_DA" IS 'Nome della societa''/organizzazione che attualmente supporta il formato'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."FLG_COPYRIGHT" IS 'Se 1 indica che e'' un formato le cui specifiche sono coperte da copyright, se 0 che è un formato aperto'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."FLG_INDICIZZABILE" IS 'Indica se e'' un formato: 2 = sempre indicizzabile; 1 = indicizzabile ma non sempre (puo'' essere sia testuale che binario); 0 = mai indicizzabile in quanto sempre binario'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."FLG_STATO" IS 'Indica lo stato del formato in IRIS: AMMESSO IN CONSERVAZIONE (=A), NON AMMESSO (=NA), AMMESSO SOLO IN PREARCHIVIO (=P). Il fatto che sia AMMESSO non implica che un soggetto versatore possa utilizzarlo a meno che non lo dichiari esplicitamente AMMESSO; invece il fatto che sia NON AMMESSO o AMMESSO SOLO IN PRE-ARCHIVIO fa si che per nessun soggetto versatore possa essere ammesso (se non eventualmente in PRE-ARCHIVIO)'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."TS_AGG_STATO" IS 'Data e ora in cui il formato ha assunto lo stato attuale (FLG_STATO)'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."TS_INS" IS 'Timestamp di creazione del record'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ID_UTE_INS" IS 'Id. dell''utente che ha inserito il record'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."TS_ULTIMO_AGG" IS 'Timestamp di ultimo aggiornamento del record'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."ID_UTE_ULTIMO_AGG" IS 'Id. dell''utente che ha effettuato l''ultimo aggiornamento sul record'
/
COMMENT ON COLUMN "T_ANAG_FORMATI_DIG"."FLG_ANN" IS 'Se 1 significa che il record è stato cancellato (logicamente)'
/

-- Table T_MIMETYPE_FMT_DIG

CREATE TABLE "T_MIMETYPE_FMT_DIG"(
  "ID_DIG_FORMAT" Varchar2(64 ) NOT NULL,
  "MIMETYPE" Varchar2(100 ) NOT NULL,
  "RIF_READER" Varchar2(100),
  "TS_INS" Timestamp(6) DEFAULT systimestamp NOT NULL,
  "ID_UTE_INS" Varchar2(64 ),
  "TS_ULTIMO_AGG" Timestamp(6) DEFAULT systimestamp NOT NULL,
  "ID_UTE_ULTIMO_AGG" Varchar2(64 ),
  "FLG_ANN" Number(1,0) DEFAULT 0 NOT NULL,
  CONSTRAINT "CHK_T_MIMETYPE_FMT_DIG_0" CHECK ("FLG_ANN" in (0,1))
)
/

-- Create indexes for table T_MIMETYPE_FMT_DIG

CREATE INDEX "IDX_MIMETYPE_FMT_DIG" ON "T_MIMETYPE_FMT_DIG" ("TS_ULTIMO_AGG")
/

CREATE INDEX "IDX_MIMETYPE_FMT_DIG_1" ON "T_MIMETYPE_FMT_DIG" ("MIMETYPE")
/

-- Add keys for table T_MIMETYPE_FMT_DIG

ALTER TABLE "T_MIMETYPE_FMT_DIG" ADD CONSTRAINT "PK_T_MIMETYPE_FMT_DIG" PRIMARY KEY ("ID_DIG_FORMAT","MIMETYPE")
/

-- Table and Columns comments section
  
COMMENT ON TABLE "T_MIMETYPE_FMT_DIG" IS 'Contiene i mimetype associati ai formati digitali censiti in T_FORMATI_DIG'
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."ID_DIG_FORMAT" IS 'Identificativo univoco (UUID) del formato digitale o sua/e versioni a cui è associato il mimetype riportato nel record '
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."MIMETYPE" IS 'Mimetype'
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."RIF_READER" IS 'Riferimento al reader da utilizzare per leggere i file che hanno il dato mimetype al fine della loro indicizzazione'
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."TS_INS" IS 'Timestamp di creazione del record'
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."ID_UTE_INS" IS 'Id. dell''utente che ha inserito il record'
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."TS_ULTIMO_AGG" IS 'Timestamp di ultimo aggiornamento del record'
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."ID_UTE_ULTIMO_AGG" IS 'Id. dell''utente che ha effettuato l''ultimo aggiornamento sul record'
/
COMMENT ON COLUMN "T_MIMETYPE_FMT_DIG"."FLG_ANN" IS 'Se 1 significa che il record è stato cancellato (logicamente)'
/

-- Table T_ESTENSIONI_FMT_DIG

CREATE TABLE "T_ESTENSIONI_FMT_DIG"(
  "ID_DIG_FORMAT" Varchar2(64 ) NOT NULL,
  "ESTENSIONE" Varchar2(5 ) NOT NULL,
  "TS_INS" Timestamp(6) DEFAULT systimestamp NOT NULL,
  "ID_UTE_INS" Varchar2(64 ),
  "TS_ULTIMO_AGG" Timestamp(6) DEFAULT systimestamp NOT NULL,
  "ID_UTE_ULTIMO_AGG" Varchar2(64 ),
  "FLG_ANN" Number(1,0) DEFAULT 0 NOT NULL,
  CONSTRAINT "CHK_T_ESTENSIONI_FMT_DIG_0" CHECK ("FLG_ANN" in (0,1))
)
/

-- Create indexes for table T_ESTENSIONI_FMT_DIG

CREATE INDEX "IDX_ESTENSIONI_FMT_DIG_TS" ON "T_ESTENSIONI_FMT_DIG" ("TS_ULTIMO_AGG")
/

CREATE INDEX "IDX_ESTENSIONE_FMT_DIG_2" ON "T_ESTENSIONI_FMT_DIG" ("ESTENSIONE")
/

-- Add keys for table T_ESTENSIONI_FMT_DIG

ALTER TABLE "T_ESTENSIONI_FMT_DIG" ADD CONSTRAINT "PK_T_ESTENSIONI_FMT_DIG" PRIMARY KEY ("ID_DIG_FORMAT","ESTENSIONE")
/

-- Table and Columns comments section
  
COMMENT ON COLUMN "T_ESTENSIONI_FMT_DIG"."ESTENSIONE" IS 'Una delle estensioni possibili per i file del dato formato (lower case e senza . iniziale)'
/
COMMENT ON COLUMN "T_ESTENSIONI_FMT_DIG"."TS_INS" IS 'Timestamp di creazione del record'
/
COMMENT ON COLUMN "T_ESTENSIONI_FMT_DIG"."ID_UTE_INS" IS 'Id. dell''utente che ha inserito il record'
/
COMMENT ON COLUMN "T_ESTENSIONI_FMT_DIG"."TS_ULTIMO_AGG" IS 'Timestamp di ultimo aggiornamento del record'
/
COMMENT ON COLUMN "T_ESTENSIONI_FMT_DIG"."ID_UTE_ULTIMO_AGG" IS 'Id. dell''utente che ha effettuato l''ultimo aggiornamento sul record'
/
COMMENT ON COLUMN "T_ESTENSIONI_FMT_DIG"."FLG_ANN" IS 'Se 1 significa che il record è stato cancellato (logicamente)'
/


-- Create relationships section ------------------------------------------------- 


ALTER TABLE "T_MIMETYPE_FMT_DIG" ADD CONSTRAINT "FK_MIMETYPE_FMT_DIG" FOREIGN KEY ("ID_DIG_FORMAT") REFERENCES "T_ANAG_FORMATI_DIG" ("ID_DIG_FORMAT")
/

ALTER TABLE "T_ESTENSIONI_FMT_DIG" ADD CONSTRAINT "FK_ESTENSIONI_FMT_DIG_1" FOREIGN KEY ("ID_DIG_FORMAT") REFERENCES "T_ANAG_FORMATI_DIG" ("ID_DIG_FORMAT")
/


-- Create table
create table T_PARAMETERS
(
  par_key      VARCHAR2(50) not null,
  str_value    VARCHAR2(1000),
  date_value   DATE,
  num_value    NUMBER,
  setting_time TIMESTAMP(6) default systimestamp,
  ts_ins       TIMESTAMP(6) default systimestamp not null,
  meaning      VARCHAR2(4000) not null
)
/

-- Add comments to the table 
comment on table T_PARAMETERS is 'Parametri di configurazione e più in generale dati necessari al funzionamento del modulo gestione ordinaria e-mail'
/
-- Add comments to the columns 
comment on column T_PARAMETERS.par_key is 'Chiave - i.e. nome/codice - che identifica univocamente il parametro'
/
comment on column T_PARAMETERS.str_value is 'Valore del parametro se alfanumerico'
/
comment on column T_PARAMETERS.date_value is 'Valore del parametro se è una data (o data e ora)'
/
comment on column T_PARAMETERS.num_value is 'Valore del parametro se numerico'
/
comment on column T_PARAMETERS.setting_time is 'Timestamp in cui il parametro è stato settato con il valore attuale'
/
comment on column T_PARAMETERS.ts_ins is 'Timestamp di creazione del record'
/
comment on column T_PARAMETERS.meaning is 'Significato del parametro';
/

-- Create/Recreate primary, unique and foreign key constraints 
ALTER TABLE T_PARAMETERS ADD 
(
	constraint PK_T_PARAMETERS primary key (PAR_KEY) using index 	
)
/

@@Insert_T_ANAG_FORMATI_DIG.sql;
@@Insert_T_MIMETYPE_FMT_DIG.sql;
@@Insert_T_ESTENSIONI_FMT_DIG.sql;
@@Insert_T_PARAMETERS.sql;