
CREATE TABLE auri_owner_1.act_hi_procinst (
	id_ varchar(64) NULL,
	proc_inst_id_ varchar(64) NULL,
	business_key_ varchar(255) NULL,
	proc_def_id_ varchar(64) NULL,
	start_time_ timestamp NULL,
	end_time_ timestamp NULL,
	duration_ numeric(19) NULL,
	start_user_id_ varchar(255) NULL,
	start_act_id_ varchar(255) NULL,
	end_act_id_ varchar(255) NULL,
	super_process_instance_id_ varchar(64) NULL,
	delete_reason_ varchar(2000) NULL,
	tenant_id_ varchar(255) NULL,
	name_ varchar(255) NULL
);



CREATE TABLE auri_owner_1.act_re_procdef (
	id_ varchar(64) NULL,
	rev_ int4 NULL,
	category_ varchar(255) NULL,
	name_ varchar(255) NULL,
	key_ varchar(255) NULL,
	version_ int4 NULL,
	deployment_id_ varchar(64) NULL,
	resource_name_ varchar(2000) NULL,
	dgrm_resource_name_ varchar(4000) NULL,
	description_ varchar(2000) NULL,
	has_start_form_key_ numeric(1) NULL,
	suspension_state_ int4 NULL,
	tenant_id_ varchar(255) NULL
);



CREATE TABLE auri_owner_1.bmt_id_sequence (
	id_type varchar(20) NOT NULL,
	id_value numeric(38) NOT NULL,
	CONSTRAINT bmt_id_sequence_pkey PRIMARY KEY (id_type)
);



CREATE TABLE auri_owner_1.bmt_job_parameters (
	id_job numeric(38) NOT NULL,
	parameter_id numeric(38) NOT NULL,
	parameter_value text NULL,
	parameter_dir varchar(3) NOT NULL,
	parameter_type varchar(150) NOT NULL,
	parameter_subtype varchar(30) NULL,
	CONSTRAINT bmt_job_par_c_dir CHECK (((parameter_dir)::text = ANY (ARRAY[('IN'::character varying)::text, ('OUT'::character varying)::text, ('IO'::character varying)::text]))),
	CONSTRAINT bmt_job_parameters_pkey PRIMARY KEY (id_job, parameter_id),
	CONSTRAINT bmt_job_par_f_job FOREIGN KEY (id_job) REFERENCES bmt_jobs(id_job) ON DELETE CASCADE
);




CREATE TABLE auri_owner_1.bmt_jobs (
	id_job numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_user varchar(60) NOT NULL,
	parametri text NULL,
	tipo varchar(150) NOT NULL,
	formato varchar(20) NULL,
	submit_time timestamp NULL,
	schedule_time timestamp NULL,
	start_time timestamp NULL,
	end_time timestamp NULL,
	status varchar(2) NULL,
	conn_string varchar(100) NULL,
	conn_driver varchar(50) NULL,
	id_doc numeric(38) NULL,
	message text NULL,
	export_filename varchar(250) NULL,
	priorita int2 NULL,
	flg_x_bassa_oper int2 NULL,
	ts_invio_in_cons timestamp NULL,
	CONSTRAINT bmt_jobs_c_priorita CHECK ((priorita >= 0)),
	CONSTRAINT bmt_jobs_c_x_bassa_oper CHECK ((flg_x_bassa_oper = 1)),
	CONSTRAINT bmt_jobs_pkey PRIMARY KEY (id_job)
);



CREATE TABLE auri_owner_1.dmo_documents_incaricati_trattamento (
	id_doc numeric(22) NULL,
	incaricato_trattamento numeric(10) NULL
);



CREATE TABLE auri_owner_1.dmt_acl (
	id_acl numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	nome_acl varchar(250) NULL,
	cod_applicazione varchar(30) NULL,
	cod_ist_applicazione varchar(30) NULL,
	flg_for_append int2 NULL,
	note varchar(500) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_acl_c_append CHECK ((flg_for_append = 1)),
	CONSTRAINT dmt_acl_pkey PRIMARY KEY (id_acl)
);
CREATE INDEX dmt_acl_i_nome ON auri_owner_1.dmt_acl USING btree (auri_master.dmfn_prepare_denom_x_match(nome_acl), id_sp_aoo);


CREATE TABLE auri_owner_1.dmt_acl_acl (
	id_acl numeric(22) NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL,
	nro_pos_in_acl int4 NULL,
	flg_visib_dati numeric NULL,
	flg_visib_file numeric NULL,
	flg_modifica_dati numeric NULL,
	flg_modifica_file numeric NULL,
	flg_modifica_acl numeric NULL,
	flg_eliminazione numeric NULL,
	flg_copia numeric NULL,
	flg_recupero numeric NULL,
	flg_mod_cont_ud numeric NULL,
	flg_mod_cont_folder numeric NULL,
	flg_acc_lim_ver_pubbl numeric NULL
);




CREATE TABLE auri_owner_1.dmt_activities (
	ts_act timestamp NOT NULL,
	cod_tp_act varchar(100) NOT NULL,
	id_user numeric(38) NULL,
	a_nome_di numeric(38) NULL,
	id_ud numeric(38) NULL,
	id_doc numeric(38) NULL,
	nro_ver_doc int2 NULL,
	id_folder numeric(38) NULL,
	id_workspace numeric(38) NULL,
	id_process numeric(38) NULL,
	target_table_name varchar(30) NULL,
	table_rec_pk_val varchar(1000) NULL,
	flg_esito int2 NOT NULL,
	flg_to_show int2 NULL,
	err_msg varchar(1000) NULL,
	note varchar(4000) NULL,
	nro_progr numeric(38) NULL,
	id_email varchar(64) NULL
);



CREATE TABLE auri_owner_1.dmt_activities_table_rec_old_attr_values (
	nro_progr numeric(38) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_anag_entrypoint_vs_conserv (
	id_entrypoint_vers varchar(64) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	ci_applicazione varchar(30) NULL,
	ci_istanza_applicazione varchar(30) NULL,
	id_sist_conserv varchar(64) NOT NULL,
	liv_priorita numeric(38) NOT NULL DEFAULT 0,
	nro_min_item_in_pdv numeric(38) NOT NULL DEFAULT 1,
	nro_max_item_in_pdv numeric(38) NULL,
	dim_max_pdv numeric(38) NULL,
	gg_sla_chiusura_pdc numeric(38) NOT NULL DEFAULT 7,
	flg_ud_coll_con_ud_rif int2 NULL,
	flg_serve_impronta int2 NULL,
	algoritmo_impronta varchar(500) NULL,
	encoding_impronta varchar(10) NULL,
	flg_accettaz_parz_doc_pdv int2 NULL,
	note varchar(4000) NULL,
	stato varchar(10) NOT NULL DEFAULT 'attivo'::character varying,
	nome_classe_doc varchar(250) NULL,
	ts_agg_stato timestamp NOT NULL DEFAULT LOCALTIMESTAMP,
	id_user_ins numeric(38) NULL,
	ts_ins timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	ts_last_upd timestamp NULL,
	CONSTRAINT dmt_anag_entrypoint_vs_conserv_pkey PRIMARY KEY (id_entrypoint_vers),
	CONSTRAINT dmt_anag_ep_vs_cons_c_accparz CHECK ((flg_accettaz_parz_doc_pdv = 1)),
	CONSTRAINT dmt_anag_ep_vs_cons_c_encimp CHECK (((encoding_impronta)::text = ANY ((ARRAY['hex'::character varying, 'base64'::character varying])::text[]))),
	CONSTRAINT dmt_anag_ep_vs_cons_c_flgimp CHECK ((flg_serve_impronta = 1)),
	CONSTRAINT dmt_anag_ep_vs_cons_c_udcoll CHECK ((flg_ud_coll_con_ud_rif = 1)),
	CONSTRAINT dmt_anag_ep_vs_conserv_c_livp CHECK ((liv_priorita = ANY (ARRAY[(0)::numeric, (1)::numeric, (2)::numeric, (3)::numeric, (4)::numeric, (5)::numeric]))),
	CONSTRAINT dmt_anag_ep_vs_conserv_c_stato CHECK (((stato)::text = ANY ((ARRAY['attivo'::character varying, 'cessato'::character varying, 'sospeso'::character varying])::text[])))
);




CREATE TABLE auri_owner_1.dmt_applicazioni_esterne (
	ci_applicazione varchar(30) NOT NULL,
	ci_istanza_applicazione varchar(30) NOT NULL,
	descrizione varchar(1000) NULL,
	id_sp_aoo numeric(38) NOT NULL,
	flg_usa_credenziali_proprie int2 NULL,
	flg_ann int2 NULL,
	ts_first_logon timestamp NULL,
	ts_last_logon timestamp NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_appl_est_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_appl_est_c_cred_pr CHECK ((flg_usa_credenziali_proprie = 1)),
	CONSTRAINT dmt_applicazioni_esterne_ci_applicazione_ci_istanza_applica_key UNIQUE (ci_applicazione, ci_istanza_applicazione),
	CONSTRAINT dmt_applicazioni_esterne_pkey PRIMARY KEY (ci_applicazione, ci_istanza_applicazione, id_sp_aoo)
);
CREATE UNIQUE INDEX dmt_applicazioni_esterne_u ON auri_owner_1.dmt_applicazioni_esterne USING btree (ci_applicazione, ci_istanza_applicazione);




CREATE TABLE auri_owner_1.dmt_applicazioni_esterne_altri_attributi (
	id_sp_aoo numeric(22) NULL,
	ci_istanza_applicazione varchar(30) NULL,
	ci_applicazione varchar(30) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_applicazioni_esterne_info_storico (
	id_sp_aoo numeric(22) NULL,
	ci_istanza_applicazione varchar(30) NULL,
	ci_applicazione varchar(30) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_attivita_wf (
	activity_name varchar(200) NOT NULL,
	flg_gui_specifica int4 NULL,
	flg_ass_abil int4 NULL,
	flg_nolog int4 NULL,
	CONSTRAINT dmt_att_wf_c_gui_spec CHECK ((flg_gui_specifica = 1)),
	CONSTRAINT dmt_att_wf_c_nolog CHECK ((flg_nolog = 1)),
	CONSTRAINT dmt_attiv_ass_abil CHECK ((flg_ass_abil = 1)),
	CONSTRAINT dmt_attivita_wf_pkey PRIMARY KEY (activity_name)
);




CREATE TABLE auri_owner_1.dmt_attivita_wf_dett_x_esito (
	activity_name varchar(200) NULL,
	activity_result_code varchar(250) NULL,
	flg_tp_mod_ass varchar(1) NULL,
	CONSTRAINT dmt_attivita_wf_dett_x_esito_dmt_attivita_wf_fk FOREIGN KEY (activity_name) REFERENCES dmt_attivita_wf(activity_name)
);



CREATE TABLE auri_owner_1.dmt_attivita_wf_stored_fnc (
	activity_name varchar(200) NULL,
	id_sp_aoo int4 NULL,
	str_value varchar(1000) NULL,
	CONSTRAINT dmt_attivita_wf_stored_fnc_dmt_attivita_wf_fk FOREIGN KEY (activity_name) REFERENCES dmt_attivita_wf(activity_name)
);



CREATE TABLE auri_owner_1.dmt_attivita_wf_url (
	activity_name varchar(200) NULL,
	id_sp_aoo int4 NULL,
	str_value varchar(1000) NULL,
	CONSTRAINT dmt_attivita_wf_url_dmt_attivita_wf_fk FOREIGN KEY (activity_name) REFERENCES dmt_attivita_wf(activity_name)
);



CREATE TABLE auri_owner_1.dmt_attr_add_clob (
	id_clob varchar(64) NOT NULL,
	clob_value text NULL,
	CONSTRAINT dmt_attr_add_clob_pkey PRIMARY KEY (id_clob)
);
CREATE INDEX dmi_attr_add_clob_val ON auri_owner_1.dmt_attr_add_clob USING gin (to_tsvector('english'::regconfig, clob_value));




CREATE TABLE auri_owner_1.dmt_attributes_def (
	attr_name varchar(250) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	attr_label varchar(100) NOT NULL,
	attr_label_x_filtro varchar(150) NULL,
	flg_ann int2 NULL,
	attr_type varchar(30) NOT NULL,
	attr_name_sup varchar(250) NULL,
	nro_ord_in_sup int2 NULL,
	nro_riga_in_sup int2 NULL,
	flg_obblig_in_sup int2 NULL,
	attr_size int2 NULL,
	attr_scale int2 NULL,
	attr_format varchar(30) NULL,
	num_min_value int8 NULL,
	num_max_value int8 NULL,
	date_min_value timestamp NULL,
	date_max_value timestamp NULL,
	case_restriction varchar(10) NULL,
	attr_width int2 NULL,
	attr_heigth int2 NULL,
	attr_def_value varchar(4000) NULL,
	attr_list_query varchar(4000) NULL,
	wsdl_url varchar(1000) NULL,
	xml_request_ws text NULL,
	attr_value_list varchar(4000) NULL,
	attr_description varchar(1000) NULL,
	lista_lookup varchar(30) NULL,
	nro_col_lookup numeric(38) NULL,
	filtro_in_lookup varchar(80) NULL,
	flg_solo_val_da_lookup int2 NULL,
	regular_expr varchar(500) NULL,
	ts_first_used timestamp NULL,
	ts_last_used timestamp NULL,
	flg_da_indicizzare int2 NULL,
	flg_valori_univoci int2 NULL,
	flg_protected int2 NULL,
	prov_property varchar(1000) NULL,
	cod_gruppo varchar(100) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_attr_def_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_attr_def_c_case CHECK (((case_restriction)::text = ANY ((ARRAY['UPPER'::character varying, 'LOWER'::character varying])::text[]))),
	CONSTRAINT dmt_attr_def_c_indexer CHECK ((flg_da_indicizzare = 1)),
	CONSTRAINT dmt_attr_def_c_obblig CHECK ((flg_obblig_in_sup = 1)),
	CONSTRAINT dmt_attr_def_c_protected CHECK ((flg_protected = 1)),
	CONSTRAINT dmt_attr_def_c_solo_val_da_lkp CHECK ((flg_solo_val_da_lookup = 1)),
	CONSTRAINT dmt_attr_def_c_type CHECK (((attr_type)::text = ANY ((ARRAY['CHECK'::character varying, 'DATE'::character varying, 'DATETIME'::character varying, 'NUMBER'::character varying, 'EURO'::character varying, 'TEXT-BOX'::character varying, 'TEXT-AREA'::character varying, 'COMBO-BOX'::character varying, 'RADIO'::character varying, 'COMPLEX'::character varying, 'DOCUMENT'::character varying, 'CKEDITOR'::character varying])::text[]))),
	CONSTRAINT dmt_attr_def_c_vu CHECK ((flg_valori_univoci = 1)),
	CONSTRAINT dmt_attributes_def_pk PRIMARY KEY (attr_name)
);
CREATE UNIQUE INDEX dmt_attributes_def_u ON auri_owner_1.dmt_attributes_def USING btree (attr_name, id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_attributes_def_lim_accesso (
	attr_name varchar(250) NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL,
	id_sp_aoo int4 NULL,
	nro_pos_in_acl int4 NULL,
	flg_visibilita numeric NULL,
	flg_modifica numeric NULL,
	CONSTRAINT dmt_attributes_def_lim_accesso_fk FOREIGN KEY (attr_name, id_sp_aoo) REFERENCES dmt_attributes_def(attr_name, id_sp_aoo)
);




CREATE TABLE auri_owner_1.dmt_auth (
	username_loc varchar(50) NOT NULL,
	cod_sistema_est varchar(30) NULL,
	cod_ist_sistema_est varchar(30) NULL,
	username_est varchar(50) NULL,
	sesamo varchar(30) NULL
);
CREATE UNIQUE INDEX dmt_auth_u ON auri_owner_1.dmt_auth USING btree (username_loc, cod_sistema_est, cod_ist_sistema_est);



CREATE TABLE auri_owner_1.dmt_bookmark (
	nome_bookmark varchar(50) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	explanation varchar(4000) NOT NULL,
	argomento varchar(150) NULL,
	flg_multivalore int2 NULL,
	query_for_values varchar(4000) NOT NULL,
	CONSTRAINT dmt_bookmark_c_mv CHECK ((flg_multivalore = 1))
);
CREATE UNIQUE INDEX dmt_bookmark_u ON auri_owner_1.dmt_bookmark USING btree (nome_bookmark, id_sp_aoo);
CREATE UNIQUE INDEX dmt_bookmark_u_expl ON auri_owner_1.dmt_bookmark USING btree (explanation, id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_cf_digit (
	id_num int2 NOT NULL,
	lettera varchar(1) NOT NULL,
	flg_operaz varchar(1) NOT NULL
);




CREATE TABLE auri_owner_1.dmt_classi_sp_aoo (
	id_classe_sp_aoo numeric(38) NOT NULL,
	nome varchar(250) NOT NULL,
	descrizione varchar(1000) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_cl_sp_aoo_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_classi_sp_aoo_pkey PRIMARY KEY (id_classe_sp_aoo)
);




CREATE TABLE auri_owner_1.dmt_classi_sp_aoo_altri_attributi (
	id_classe_sp_aoo numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_classi_sp_aoo_info_storico (
	id_classe_sp_aoo numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);


CREATE TABLE auri_owner_1.dmt_classi_sp_aoo_livelli_so (
	id_classe_sp_aoo numeric(22) NULL,
	nro int4 NULL,
	cod_tipi varchar(500) NULL,
	flg_codice_numerico numeric NULL,
	flg_romano numeric NULL
);



CREATE TABLE auri_owner_1.dmt_clienti (
	id_cliente numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_in_rubrica numeric(38) NOT NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_cliente varchar(200) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_clienti_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_clienti_id_sp_aoo_id_in_rubrica_cod_appl_owner_cod_ist__key UNIQUE (id_sp_aoo, id_in_rubrica, cod_appl_owner, cod_ist_appl_owner),
	CONSTRAINT dmt_clienti_pkey PRIMARY KEY (id_cliente)
);
CREATE INDEX dmi_clienti_rubr ON auri_owner_1.dmt_clienti USING btree (id_in_rubrica);
CREATE UNIQUE INDEX dmt_clienti_u ON auri_owner_1.dmt_clienti USING btree (id_sp_aoo, id_in_rubrica, cod_appl_owner, cod_ist_appl_owner);




CREATE TABLE auri_owner_1.dmt_clienti_altri_attributi (
	id_cliente numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_cols_tipi_cont_x_import (
	tipo_contenuto varchar(250) NOT NULL,
	nome_colonna varchar(150) NOT NULL,
	des_colonna varchar(1000) NULL,
	tipo_colonna varchar(1) NOT NULL,
	CONSTRAINT dmt_cols_tipi_cont_x_import_pkey PRIMARY KEY (tipo_contenuto, nome_colonna),
	CONSTRAINT dmt_cols_tp_cnt_x_imp_c_tipo CHECK (((tipo_colonna)::text = ANY ((ARRAY['S'::character varying, 'N'::character varying, 'D'::character varying])::text[])))
);



CREATE TABLE auri_owner_1.dmt_comuni_italiani (
	cod_istat_comune varchar(6) NOT NULL,
	nome_comune varchar(150) NOT NULL,
	targa_prov varchar(2) NULL,
	flg_capoluogo_prov int2 NULL,
	cf_comune varchar(4) NULL,
	cap varchar(5) NULL,
	prefisso_tel varchar(6) NULL,
	cod_istat_regione varchar(2) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_comuni_c_cp CHECK ((flg_capoluogo_prov = 1)),
	CONSTRAINT dmt_comuni_italiani_cf_comune_key UNIQUE (cf_comune),
	CONSTRAINT dmt_comuni_italiani_pkey PRIMARY KEY (cod_istat_comune)
);
CREATE INDEX dmi_comuni_nome ON auri_owner_1.dmt_comuni_italiani USING btree (auri_master.dmfn_prepare_denom_x_match(nome_comune));



CREATE TABLE auri_owner_1.dmt_config_param (
	par_name varchar(100) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	par_value varchar(4000) NULL,
	CONSTRAINT dmt_config_param_par_name_id_sp_aoo_key UNIQUE (par_name, id_sp_aoo)
);
CREATE UNIQUE INDEX dmt_config_param_u ON auri_owner_1.dmt_config_param USING btree (par_name, id_sp_aoo);
CREATE UNIQUE INDEX dmt_config_param_u_pn ON auri_owner_1.dmt_config_param USING btree (btrim((auri_master.dmfn_upper(par_name))::text), id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_connection_token (
	ci_connection_token varchar(1000) NOT NULL,
	id_user numeric(38) NOT NULL,
	cod_applicazione varchar(30) NULL,
	cod_id_istanza_appl varchar(30) NULL,
	tipo_dominio_aut int2 NULL,
	id_dominio_aut numeric(38) NULL,
	ts_creazione timestamp NOT NULL,
	ts_ultimo_utilizzo timestamp NULL,
	nro_utilizzi numeric(38) NOT NULL DEFAULT 1,
	durata_da_ult_utilizzo numeric(38) NULL,
	durata_complessiva numeric(38) NULL,
	id_user_delega numeric(38) NULL,
	ts_inizio_sessione_in_delega timestamp NULL,
	nro_max_utilizzi numeric(38) NULL,
	flg_in_use int2 NULL,
	CONSTRAINT dmt_conn_tkn_c_tp_dom CHECK ((tipo_dominio_aut = ANY (ARRAY[1, 2, 3]))),
	CONSTRAINT dmt_conn_tkn_c_use CHECK ((flg_in_use = ANY (ARRAY[1, 0]))),
	CONSTRAINT dmt_connection_token_pkey PRIMARY KEY (ci_connection_token)
);
CREATE INDEX dmt_connection_token_user ON auri_owner_1.dmt_connection_token USING btree (id_user);
CREATE INDEX dmt_connection_token_user_del ON auri_owner_1.dmt_connection_token USING btree (id_user_delega);




CREATE TABLE auri_owner_1.dmt_cont_fogli_x_import (
	id_foglio varchar(64) NOT NULL,
	nro_riga numeric(38) NOT NULL,
	tipo_contenuto varchar(250) NOT NULL,
	flg_tipo_dato varchar(1) NOT NULL,
	valore_stringa varchar(4000) NULL,
	valore_numero int8 NULL,
	valore_data timestamp NULL,
	ts_ins timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	try_num numeric(38) NOT NULL DEFAULT 0,
	ts_last_elab timestamp NULL,
	esito_elab varchar(2) NULL,
	err_code varchar(10) NULL,
	err_msg text NULL,
	CONSTRAINT dmt_cont_fogli_x_imp_c_es CHECK (((esito_elab)::text = ANY ((ARRAY['OK'::character varying, 'KO'::character varying])::text[]))),
	CONSTRAINT dmt_cont_fogli_x_imp_c_nr CHECK ((nro_riga > (0)::numeric)),
	CONSTRAINT dmt_cont_fogli_x_imp_c_tp CHECK (((flg_tipo_dato)::text = ANY ((ARRAY['S'::character varying, 'N'::character varying, 'D'::character varying])::text[]))),
	CONSTRAINT dmt_cont_fogli_x_import_pkey PRIMARY KEY (id_foglio, nro_riga, tipo_contenuto)
);



CREATE TABLE auri_owner_1.dmt_cookie_info (
	cookie varchar(250) NOT NULL,
	obj_ty varchar(2) NOT NULL,
	obj_id varchar(250) NOT NULL,
	ts_last_access timestamp NOT NULL,
	ts_last_upd timestamp NULL,
	CONSTRAINT dmt_cookie_info_pkey PRIMARY KEY (cookie, obj_ty, obj_id)
);



CREATE TABLE auri_owner_1.dmt_copie_ud (
	id_copia numeric(38) NOT NULL,
	id_ud numeric(38) NOT NULL,
	id_user_ass numeric(38) NULL,
	id_uo_ass numeric(38) NULL,
	id_scrivania_ass numeric(38) NULL,
	id_ass varchar(40) NULL,
	flg_in_carico int2 NULL,
	id_user_in_carico numeric(38) NULL,
	id_uo_in_carico numeric(38) NULL,
	id_scrivania_in_carico numeric(38) NULL,
	id_invio_ultimo_vld numeric(38) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_copie_ud_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_copie_ud_c_in_carico CHECK ((flg_in_carico = 1)),
	CONSTRAINT dmt_copie_ud_pkey PRIMARY KEY (id_copia)
);
CREATE INDEX dmi_copie_ud_ass ON auri_owner_1.dmt_copie_ud USING btree ((
CASE
    WHEN ((id_uo_ass)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_ass)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_ass)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_ass)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_ass)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_ass)::character varying)::text)
END));
CREATE INDEX dmi_copie_ud_ass_2 ON auri_owner_1.dmt_copie_ud USING btree (id_ass);
CREATE INDEX dmi_copie_ud_carico ON auri_owner_1.dmt_copie_ud USING btree ((
CASE
    WHEN ((id_uo_in_carico)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_in_carico)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_in_carico)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_in_carico)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_in_carico)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_in_carico)::character varying)::text)
END));
CREATE INDEX dmi_copie_ud_invio ON auri_owner_1.dmt_copie_ud USING btree (id_invio_ultimo_vld);
CREATE INDEX dmi_copie_ud_ud ON auri_owner_1.dmt_copie_ud USING btree (id_ud);



CREATE TABLE auri_owner_1.dmt_copie_ud_altri_attributi (
	id_copia numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_def_attr_add_entita (
	table_name varchar(30) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_def_attr_add_entita_pkey PRIMARY KEY (table_name)
);



CREATE TABLE auri_owner_1.dmt_def_attr_add_entita_attr_addizionali (
	table_name varchar(30) NULL,
	attr_name varchar(250) NULL,
	nro_posizione int4 NULL,
	flg_obblig numeric NULL,
	max_num_values int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	flg_x_timbro numeric NULL,
	flg_x_intitolazione numeric NULL,
	cod_tab_sez_gui varchar(30) NULL,
	des_tab_sez_gui varchar(250) NULL
);




CREATE TABLE auri_owner_1.dmt_def_config_param (
	par_name varchar(100) NULL,
	meaning varchar(1000) NULL,
	par_type varchar(10) NULL,
	max_liv_def int2 NULL,
	default_value varchar(6000) NULL,
	flg_x_gui int2 NULL
);



CREATE TABLE auri_owner_1.dmt_def_config_param_valori_ammessi (
	par_name varchar(100) NULL,
	valore_ammesso varchar(100) NULL
);



CREATE TABLE auri_owner_1.dmt_def_impostazioni_personali (
	id_sp_aoo numeric(38) NULL,
	cod_target varchar(30) NOT NULL,
	meaning varchar(1000) NOT NULL,
	value_type varchar(10) NOT NULL DEFAULT 'S'::character varying,
	max_num_values numeric(38) NULL DEFAULT 1,
	valori_ammessi auri_master.dmo_valore_imp_personale[] NULL,
	default_values auri_master.dmo_def_val_imp_personale[] NULL,
	CONSTRAINT dmt_def_imp_pers_c_ty CHECK (((value_type)::text = ANY (ARRAY[('S'::character varying)::text, ('N'::character varying)::text, ('D'::character varying)::text]))),
	CONSTRAINT dmt_def_impostazioni_personali_id_sp_aoo_cod_target_key UNIQUE (id_sp_aoo, cod_target),
	CONSTRAINT dmt_def_impostazioni_personali_id_sp_aoo_meaning_key UNIQUE (id_sp_aoo, meaning)
);
CREATE UNIQUE INDEX dmt_def_imp_pers_u_cod ON auri_owner_1.dmt_def_impostazioni_personali USING btree (id_sp_aoo, cod_target);
CREATE UNIQUE INDEX dmt_def_imp_pers_u_meaning ON auri_owner_1.dmt_def_impostazioni_personali USING btree (id_sp_aoo, meaning);



CREATE TABLE auri_owner_1.dmt_def_object_to_audit (
	target_object_name varchar(250) NOT NULL,
	auditing_condition varchar(30) NOT NULL DEFAULT 'ALL'::character varying,
	audit_limited_to_attr varchar[] NULL,
	CONSTRAINT dmt_def_obj_audit_c_cond CHECK (((auditing_condition)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text, ('ERROR'::character varying)::text, ('SEVERE_ERROR'::character varying)::text, ('SUCCESS'::character varying)::text, ('ALL'::character varying)::text]))),
	CONSTRAINT dmt_def_object_to_audit_pkey PRIMARY KEY (target_object_name, auditing_condition)
);



CREATE TABLE auri_owner_1.dmt_def_rel_process_objects (
	id_proc_obj_type_master numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NOT NULL,
	id_proc_obj_type_detail numeric(38) NOT NULL,
	id_process_type numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	descrizione_rel varchar(1000) NULL,
	nro_posizione numeric(38) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_def_rel_proc_obj_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_def_rel_proc_obj_c_lck CHECK ((flg_locked = 1))
);
CREATE INDEX dmi_def_rel_proc_obj_nat_rel ON auri_owner_1.dmt_def_rel_process_objects USING btree (cod_natura_rel);
CREATE INDEX dmi_def_rel_proc_obj_obj_d ON auri_owner_1.dmt_def_rel_process_objects USING btree (id_proc_obj_type_detail);
CREATE INDEX dmi_def_rel_proc_obj_proc_ty ON auri_owner_1.dmt_def_rel_process_objects USING btree (id_process_type);
CREATE INDEX dmi_def_rel_proc_obj_spaoo ON auri_owner_1.dmt_def_rel_process_objects USING btree (id_sp_aoo);
CREATE UNIQUE INDEX dmt_def_rel_proc_obj_u ON auri_owner_1.dmt_def_rel_process_objects USING btree (id_proc_obj_type_master, cod_natura_rel, id_proc_obj_type_detail, id_process_type, id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_dest_reg_emergenza (
	id_reg_emerg varchar(64) NOT NULL,
	nro_posiz numeric(38) NOT NULL,
	destinatario varchar(500) NOT NULL,
	ts_ins timestamp NULL,
	CONSTRAINT dmt_dest_reg_emergenza_pkey PRIMARY KEY (id_reg_emerg, nro_posiz)
);
CREATE UNIQUE INDEX dmt_dest_reg_emergenza_u_dest ON auri_owner_1.dmt_dest_reg_emergenza USING btree (auri_master.dmfn_prepare_denom_x_match(destinatario), id_reg_emerg);




CREATE TABLE auri_owner_1.dmt_destinatari_doc (
	id_destinatario numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_in_rubrica numeric(38) NOT NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_dest_doc varchar(200) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_dest_doc_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_destinatari_doc_id_sp_aoo_id_in_rubrica_cod_appl_owner__key UNIQUE (id_sp_aoo, id_in_rubrica, cod_appl_owner, cod_ist_appl_owner),
	CONSTRAINT dmt_destinatari_doc_pkey PRIMARY KEY (id_destinatario)
);
CREATE INDEX dmt_destinatari_doc_rubr ON auri_owner_1.dmt_destinatari_doc USING btree (id_in_rubrica);
CREATE UNIQUE INDEX dmt_destinatari_doc_u ON auri_owner_1.dmt_destinatari_doc USING btree (id_sp_aoo, id_in_rubrica, cod_appl_owner, cod_ist_appl_owner);




CREATE TABLE auri_owner_1.dmt_destinatari_doc_altri_attributi (
	id_destinatario numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_dett_attivita_wf (
	id_sp_aoo int4 NULL,
	prov_ci_ty_flusso_wf varchar(250) NULL,
	process_name varchar(200) NULL,
	activity_name varchar(200) NULL,
	ctrl_obbl varchar(1000) NULL,
	flg_ass_abil numeric(1) NULL,
	nro_ordine_vis int4 NULL,
	flg_process_end numeric(1) NULL,
	flg_ripetibile numeric(1) NULL,
	id_event_type_app int4 NULL,
	attributi_editabili varchar[] NULL,
	mono_esec_user_post_activity varchar(200) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL
);



CREATE TABLE auri_owner_1.dmt_dict_entry_x_sw_gest (
	id_sp_aoo numeric(38) NULL,
	dictionary_entry varchar(30) NOT NULL,
	CONSTRAINT dmt_dict_entry_x_sw_gest_id_sp_aoo_dictionary_entry_key UNIQUE (id_sp_aoo, dictionary_entry)
);
CREATE UNIQUE INDEX dmt_dict_entry_x_sw_gest_u ON auri_owner_1.dmt_dict_entry_x_sw_gest USING btree (id_sp_aoo, dictionary_entry);



CREATE TABLE auri_owner_1.dmt_dictionary_entries (
	dictionary_entry varchar(30) NOT NULL,
	meaning varchar(250) NOT NULL,
	dett_di_dict_entry varchar(30) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	flg_valori_spec_sp_aoo int2 NULL,
	valori_spec_sp_aoo numeric[] NULL,
	CONSTRAINT dmt_dictionary_entries_pkey PRIMARY KEY (dictionary_entry),
	CONSTRAINT dmt_diz_ent_c_val_pec CHECK ((flg_valori_spec_sp_aoo = 1))
);



CREATE TABLE auri_owner_1.dmt_dizionario (
	id_sp_aoo numeric(38) NULL,
	dictionary_entry varchar(30) NOT NULL,
	value varchar(1000) NOT NULL,
	cod_value varchar(10) NULL,
	explanation varchar(2000) NULL,
	vincolato_a_val_gen varchar(1000) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_obblig_dettagli int2 NULL,
	id_uo numeric(38) NULL,
	flg_visib_sottouo int2 NULL,
	flg_gest_sottouo int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_diz_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_diz_c_obblig_dett CHECK ((flg_obblig_dettagli = 1)),
	CONSTRAINT dmt_diz_c_vis_suo CHECK ((flg_visib_sottouo = 1)),
	CONSTRAINT dmt_diz_sogg_c_gest_suo CHECK ((flg_gest_sottouo = 1)),
	CONSTRAINT dmt_dizionario_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_dizionario_id_sp_aoo_dictionary_entry_value_key UNIQUE (id_sp_aoo, dictionary_entry, value)
);
CREATE INDEX dmi_diz_uo ON auri_owner_1.dmt_dizionario USING btree (id_uo);
CREATE INDEX dmi_dizionario_cod ON auri_owner_1.dmt_dizionario USING btree (dictionary_entry, cod_value);
CREATE INDEX dmi_dizionario_val ON auri_owner_1.dmt_dizionario USING btree (dictionary_entry, value);
CREATE UNIQUE INDEX dmt_dizionario_u ON auri_owner_1.dmt_dizionario USING btree (id_sp_aoo, dictionary_entry, value);



CREATE TABLE auri_owner_1.dmt_doc_eliminati_def (
	id_user numeric(38) NOT NULL,
	ci_sezione_elim varchar(5) NOT NULL,
	id_ud numeric(38) NOT NULL,
	ts_last_del timestamp NOT NULL,
	CONSTRAINT dmt_doc_elim_def_c_sez CHECK (((ci_sezione_elim)::text = ANY ((ARRAY['N'::character varying, 'W'::character varying, 'NA'::character varying, 'NCC'::character varying, 'NNA'::character varying, 'DA'::character varying, 'DFA'::character varying, 'DPR'::character varying, 'DFI'::character varying, 'DAV'::character varying, 'ADA'::character varying, 'S'::character varying, 'DIA'::character varying, 'IEML'::character varying])::text[]))),
	CONSTRAINT dmt_doc_eliminati_def_pkey PRIMARY KEY (id_user, id_ud, ci_sezione_elim)
);
CREATE INDEX dmi_doc_elim_def_sez ON auri_owner_1.dmt_doc_eliminati_def USING btree (ci_sezione_elim);
CREATE INDEX dmi_doc_elim_def_ud ON auri_owner_1.dmt_doc_eliminati_def USING btree (id_ud);
CREATE INDEX dmi_doc_elim_def_user ON auri_owner_1.dmt_doc_eliminati_def USING btree (id_user);



CREATE TABLE auri_owner_1.dmt_doc_lotti_elab_massive (
	id_job numeric(38) NOT NULL,
	nro_posiz_doc numeric(38) NOT NULL,
	id_rich_doc varchar(50) NULL,
	stato varchar(50) NOT NULL,
	xml_fragment text NOT NULL,
	ts_validazione timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ts_inizio_elaborazione timestamp NULL,
	ts_fine_elaborazione timestamp NULL,
	ts_esito_completo timestamp NULL,
	id_ud numeric(38) NULL,
	id_email varchar(64) NULL,
	oggetto_email varchar(4000) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ts_last_upd timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT dmt_doc_lotti_c_nro_pos CHECK ((nro_posiz_doc >= (0)::numeric)),
	CONSTRAINT dmt_doc_lotti_c_stato CHECK (((stato)::text = ANY ((ARRAY['validazione_superata'::character varying, 'validazione_fallita'::character varying, 'operazioni_in_corso'::character varying, 'operazioni_completate'::character varying, 'operazioni_in_errore'::character varying])::text[]))),
	CONSTRAINT dmt_doc_lotti_elab_massive_pkey PRIMARY KEY (id_job, nro_posiz_doc)
);
CREATE INDEX dmi_doc_lotti_elab_mass_ts_fe ON auri_owner_1.dmt_doc_lotti_elab_massive USING btree (ts_fine_elaborazione);
CREATE INDEX dmi_doc_lotti_elab_mass_ts_ie ON auri_owner_1.dmt_doc_lotti_elab_massive USING btree (ts_inizio_elaborazione);
CREATE INDEX dmi_doc_lotti_elab_mass_ts_vld ON auri_owner_1.dmt_doc_lotti_elab_massive USING btree (ts_validazione);
CREATE INDEX dmt_doc_lotti_elab_mass_u_idr ON auri_owner_1.dmt_doc_lotti_elab_massive USING btree (id_rich_doc);



CREATE TABLE auri_owner_1.dmt_doc_preview (
	id_doc numeric(38) NOT NULL,
	nro_progr_ver numeric(38) NOT NULL,
	preview bytea NULL,
	ts_ins timestamp NOT NULL,
	CONSTRAINT dmt_doc_preview_pkey PRIMARY KEY (id_doc, nro_progr_ver)
);




CREATE TABLE auri_owner_1.dmt_doc_types (
	id_doc_type int4 NOT NULL,
	id_sp_aoo int4 NULL,
	nome_doc_type varchar(100) NULL,
	des_doc_type varchar(1000) NULL,
	id_doc_type_gen int4 NULL,
	flg_allegato numeric(1) NULL,
	flg_ric_abil_x_vis numeric(1) NULL,
	flg_ric_abil_x_tratt numeric(1) NULL,
	flg_ric_abil_x_assegn numeric(1) NULL,
	flg_ric_abil_x_firma numeric(1) NULL,
	cod_natura varchar(10) NULL,
	flg_tipo_prov varchar(1) NULL,
	cod_supporto_orig varchar(10) NULL,
	flg_tipo_cartaceo varchar(3) NULL,
	flg_rich_scansione numeric(1) NULL,
	flg_conserv_perm numeric(1) NULL,
	periodo_conserv int4 NULL,
	cod_supporto_conserv varchar(10) NULL,
	id_classificazione int4 NULL,
	id_formato_el int4 NULL,
	spec_accessibilita varchar(1000) NULL,
	spec_riproducibilita varchar(1000) NULL,
	note varchar(1000) NULL,
	cod_doc_type_x_repl varchar(150) NULL,
	flg_ann numeric(1) NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_doc_type varchar(30) NULL,
	flg_hidden numeric(1) NULL,
	flg_locked numeric(1) NULL,
	template_nome_ud varchar(1000) NULL,
	template_timbro_ud varchar(1000) NULL,
	id_process_type int4 NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL
);



CREATE TABLE auri_owner_1.dmt_doc_types_altri_attributi (
	id_doc_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_doc_types_attr_add_x_doc_del_tipo (
	id_doc_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_posizione int4 NULL,
	flg_obblig numeric NULL,
	max_num_values int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	flg_x_timbro numeric NULL,
	flg_x_intitolazione numeric NULL,
	cod_tab_sez_gui varchar(30) NULL,
	des_tab_sez_gui varchar(250) NULL
);



CREATE TABLE auri_owner_1.dmt_doc_types_default_acl (
	id_doc_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);



CREATE TABLE auri_owner_1.dmt_doc_types_folderizzazione (
	id_doc_type numeric(22) NULL,
	tipo_valore varchar(1) NULL,
	valore varchar(1000) NULL
);



CREATE TABLE auri_owner_1.dmt_doc_types_info_storico (
	id_doc_type numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);



CREATE TABLE auri_owner_1.dmt_doc_types_modelli (
	id_doc_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);



CREATE TABLE auri_owner_1.dmt_doc_vs_sez_area_lav (
	id_user numeric(38) NOT NULL,
	ci_sezione varchar(5) NOT NULL,
	id_ud numeric(38) NOT NULL,
	ts_last_ins timestamp NOT NULL,
	ts_ins_in_sez_prov timestamp NULL,
	motivi varchar(500) NULL,
	lista_id_invii numeric[] NULL,
	CONSTRAINT dmt_doc_vs_sez_al_sez CHECK (((ci_sezione)::text = ANY (ARRAY[('P'::character varying)::text, ('I'::character varying)::text, ('C'::character varying)::text, ('EN'::character varying)::text, ('EB'::character varying)::text, ('EI'::character varying)::text, ('EC'::character varying)::text, ('EP'::character varying)::text, ('RL'::character varying)::text, ('RV'::character varying)::text, ('RD'::character varying)::text, ('EW'::character varying)::text, ('V'::character varying)::text, ('ENA'::character varying)::text, ('ENCC'::character varying)::text, ('ENNA'::character varying)::text, ('S'::character varying)::text, ('ES'::character varying)::text, ('EDA'::character varying)::text, ('EDFA'::character varying)::text, ('EDPR'::character varying)::text, ('EDFI'::character varying)::text, ('EDAV'::character varying)::text, ('EADA'::character varying)::text, ('ADC'::character varying)::text, ('EIEML'::character varying)::text, ('EDIA'::character varying)::text]))),
	CONSTRAINT dmt_doc_vs_sez_area_lav_pkey PRIMARY KEY (id_user, ci_sezione, id_ud)
);
CREATE INDEX dmi_doc_vs_sez_al_ci_sez ON auri_owner_1.dmt_doc_vs_sez_area_lav USING btree (ci_sezione);
CREATE INDEX dmi_doc_vs_sez_al_ud ON auri_owner_1.dmt_doc_vs_sez_area_lav USING btree (id_ud);
CREATE INDEX dmi_doc_vs_sez_al_user ON auri_owner_1.dmt_doc_vs_sez_area_lav USING btree (id_user);



CREATE TABLE auri_owner_1.dmt_documents (
	id_doc numeric(22) NOT NULL,
	id_sp_aoo int4 NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	des_ogg varchar(4000) NULL,
	parole_chiave varchar(4000) NULL,
	container_type varchar(30) NULL,
	container_alias varchar(30) NULL,
	id_doc_type int4 NULL,
	cod_supporto_orig varchar(10) NULL,
	flg_tipo_cartaceo varchar(3) NULL,
	flg_digitalizzazione numeric(1) NULL,
	ci_su_cartaceo varchar(250) NULL,
	nro_pagine_cartaceo int4 NULL,
	dt_stesura date NULL,
	luogo_stesura varchar(150) NULL,
	liv_riservatezza int4 NULL,
	dt_termine_riservatezza date NULL,
	flg_visib_in_chiaro varchar(2) NULL,
	flg_dati_personali numeric(1) NULL,
	flg_dati_sensibili numeric(1) NULL,
	flg_dati_sensibilissimi numeric(1) NULL,
	flg_dati_giudiziari numeric(1) NULL,
	flg_eserc_dir_privacy numeric(1) NULL,
	flg_blocco_trattamento numeric(1) NULL,
	dt_termine_blocco_tratt date NULL,
	flg_limit_accesso numeric(1) NULL,
	dt_termine_diff_accesso date NULL,
	id_user_titolare_tratt int4 NULL,
	id_user_resp_tratt int4 NULL,
	restrizioni_accesso varchar(1000) NULL,
	restrizioni_riprod varchar(1000) NULL,
	flg_cartaceo_eliminato numeric(1) NULL,
	dt_elim_cartaceo date NULL,
	id_user_elim_cart int4 NULL,
	id_user_aut_elim_cart int4 NULL,
	ts_lock date NULL,
	id_user_lock int4 NULL,
	flg_tipo_lock varchar(1) NULL,
	flg_ann numeric(1) NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_doc varchar(30) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_ud int4 NULL,
	nro_allegato numeric(4) NULL,
	flg_invio_in_conserv numeric(1) NULL,
	flg_urgenza_invio_in_conserv numeric(1) NULL,
	ts_invio_in_conserv date NULL,
	id_pdv varchar(64) NULL,
	ts_acc_in_conserv date NULL,
	ts_inizio_conserv date NULL,
	dt_termine_conserv date NULL,
	flg_porogato_termine_cons numeric(1) NULL,
	flg_esib_da_cons numeric(1) NULL,
	id_lotto_invio_in_conserv int4 NULL,
	id_in_conservazione varchar(250) NULL,
	err_msg_invio_in_conserv varchar(4000) NULL,
	stato_conservazione varchar(30) NULL,
	file_content text NULL,
	flg_ver_con_omissis numeric(1) NULL,
	id_doc_ver_con_omissis int4 NULL,
	CONSTRAINT dmt_doc_c_blocco_tratt CHECK ((flg_blocco_trattamento = (1)::numeric)),
	CONSTRAINT dmt_doc_c_cart_elim CHECK ((flg_cartaceo_eliminato = (1)::numeric)),
	CONSTRAINT dmt_doc_c_dati_giud CHECK ((flg_dati_giudiziari = (1)::numeric)),
	CONSTRAINT dmt_doc_c_dati_pers CHECK ((flg_dati_personali = (1)::numeric)),
	CONSTRAINT dmt_doc_c_dati_sens1 CHECK ((flg_dati_sensibili = (1)::numeric)),
	CONSTRAINT dmt_doc_c_dati_sens2 CHECK ((flg_dati_sensibilissimi = (1)::numeric)),
	CONSTRAINT dmt_doc_c_digit CHECK ((flg_digitalizzazione = ANY (ARRAY[(1)::numeric, (2)::numeric]))),
	CONSTRAINT dmt_doc_c_esib_da_cons CHECK ((flg_esib_da_cons = (1)::numeric)),
	CONSTRAINT dmt_doc_c_inv_cons CHECK ((flg_invio_in_conserv = ANY (ARRAY[(0)::numeric, (1)::numeric]))),
	CONSTRAINT dmt_doc_c_lim_acc CHECK ((flg_limit_accesso = ANY (ARRAY[(1)::numeric, (2)::numeric]))),
	CONSTRAINT dmt_doc_c_privacy CHECK ((flg_eserc_dir_privacy = (1)::numeric)),
	CONSTRAINT dmt_doc_c_pror_term_cons CHECK ((flg_porogato_termine_cons = (1)::numeric)),
	CONSTRAINT dmt_doc_c_stato_cons_ck CHECK (((stato_conservazione)::text = ANY (ARRAY[('inviato'::character varying)::text, ('accettato'::character varying)::text, ('conservato'::character varying)::text, ('rifiutato'::character varying)::text, ('da_inviare'::character varying)::text, ('non_prevista'::character varying)::text]))),
	CONSTRAINT dmt_doc_c_tipo_cart CHECK (((flg_tipo_cartaceo)::text = ANY (ARRAY[('OU'::character varying)::text, ('ONU'::character varying)::text, ('C'::character varying)::text]))),
	CONSTRAINT dmt_doc_c_tipo_lock CHECK (((flg_tipo_lock)::text = ANY (ARRAY[('I'::character varying)::text, ('E'::character varying)::text]))),
	CONSTRAINT dmt_doc_c_urg_inv_cons CHECK ((flg_urgenza_invio_in_conserv = ANY (ARRAY[(0)::numeric, (1)::numeric, (2)::numeric, (3)::numeric]))),
	CONSTRAINT dmt_doc_c_vis_chiaro CHECK (((flg_visib_in_chiaro)::text = ANY (ARRAY[('C'::character varying)::text, ('CE'::character varying)::text, ('CC'::character varying)::text]))),
	CONSTRAINT dmt_documents_c_ann CHECK ((flg_ann = (1)::numeric)),
	CONSTRAINT dmt_documents_c_omissis CHECK ((flg_ver_con_omissis = (1)::numeric)),
	CONSTRAINT dmt_documents_pkey PRIMARY KEY (id_doc)
)
WITH (
	OIDS=TRUE
);
CREATE INDEX dmi_doc_dt_stesura ON auri_owner_1.dmt_documents USING btree (dt_stesura);
CREATE INDEX dmi_doc_lock ON auri_owner_1.dmt_documents USING btree (ts_lock);
CREATE INDEX dmi_doc_pdv ON auri_owner_1.dmt_documents USING btree (id_pdv);
CREATE INDEX dmi_doc_prov_ci ON auri_owner_1.dmt_documents USING btree (prov_ci_doc);
CREATE INDEX dmi_doc_prov_ci2 ON auri_owner_1.dmt_documents USING btree (auri_master.dmfn_prepare_denom_x_match(prov_ci_doc));
CREATE INDEX dmi_doc_prov_id_cons ON auri_owner_1.dmt_documents USING btree (auri_master.dmfn_prepare_denom_x_match(id_in_conservazione));
CREATE INDEX dmi_doc_spaoo ON auri_owner_1.dmt_documents USING btree (id_sp_aoo);
CREATE INDEX dmi_doc_ts_acc_cons ON auri_owner_1.dmt_documents USING btree (ts_acc_in_conserv);
CREATE INDEX dmi_doc_ts_ini_cons ON auri_owner_1.dmt_documents USING btree (ts_inizio_conserv);
CREATE INDEX dmi_doc_ts_term_cons ON auri_owner_1.dmt_documents USING btree (dt_termine_conserv);
CREATE INDEX dmi_doc_ud_char ON auri_owner_1.dmt_documents USING btree (id_ud);
CREATE UNIQUE INDEX dmi_doc_vs_ud ON auri_owner_1.dmt_documents USING btree (id_ud, nro_allegato);
CREATE INDEX dmi_docs_keys ON auri_owner_1.dmt_documents USING gin (to_tsvector('english'::regconfig, (parole_chiave)::text));
CREATE INDEX dmi_docs_ogg ON auri_owner_1.dmt_documents USING gin (to_tsvector('english'::regconfig, (des_ogg)::text));
CREATE INDEX dmi_docs_ogg_2 ON auri_owner_1.dmt_documents USING btree (auri_master.dmfn_prepare_denom_x_match(des_ogg));
CREATE INDEX dmi_documents_ty ON auri_owner_1.dmt_documents USING btree (id_doc_type);



CREATE TABLE auri_owner_1.dmt_documents_altri_attributi (
	id_doc numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL,
	nro_progr_ver numeric NULL,
	CONSTRAINT dmt_documents_altri_attributi_fk FOREIGN KEY (id_doc) REFERENCES dmt_documents(id_doc)
);



CREATE TABLE auri_owner_1.dmt_documents_estensori (
	id_doc numeric(22) NULL,
	estensore numeric(10) NULL,
	CONSTRAINT dmt_documents_altri_attributi_fk FOREIGN KEY (id_doc) REFERENCES dmt_documents(id_doc)
);



CREATE TABLE auri_owner_1.dmt_documents_firme_interne (
	id_doc numeric(22) NULL,
	id_user int4 NULL,
	a_nome_di int4 NULL,
	ts date NULL,
	flg_elettronica numeric NULL,
	dt_scadenza date NULL,
	intestazione_certif varchar(4000) NULL,
	flg_marca_temp numeric NULL,
	ts_marca_temp date NULL,
	CONSTRAINT dmt_documents_firme_interne_fk FOREIGN KEY (id_doc) REFERENCES dmt_documents(id_doc)
);



CREATE TABLE auri_owner_1.dmt_documents_incaricati_trattamento (
	id_doc numeric(22) NULL,
	incaricato_trattamento numeric(10) NULL,
	CONSTRAINT dmt_documents_altri_attributi_fk FOREIGN KEY (id_doc) REFERENCES dmt_documents(id_doc)
);



CREATE TABLE auri_owner_1.dmt_documents_info_storico (
	id_doc numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL,
	CONSTRAINT dmt_documents_info_storico_fk FOREIGN KEY (id_doc) REFERENCES dmt_documents(id_doc)
);



CREATE TABLE auri_owner_1.dmt_documents_info_storico_old_values (
	id_doc numeric NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);


CREATE TABLE auri_owner_1.dmt_documents_uff_produttori (
	id_doc numeric(22) NULL,
	uff_produttore numeric(10) NULL,
	CONSTRAINT dmt_documents_altri_attributi_fk FOREIGN KEY (id_doc) REFERENCES dmt_documents(id_doc)
);



CREATE TABLE auri_owner_1.dmt_documents_versioni (
	id_doc numeric(22) NULL,
	nro_progr numeric NULL,
	cod_ver varchar(30) NULL,
	display_filename varchar(1000) NULL,
	id_formato_el int4 NULL,
	mimetype varchar(250) NULL,
	dimensione numeric NULL,
	flg_firmato numeric NULL,
	rif_in_repository varchar(1000) NULL,
	flg_pubblicata numeric NULL,
	flg_ann numeric NULL,
	note varchar(1000) NULL,
	impronta varchar(255) NULL,
	algoritmo_impronta varchar(30) NULL,
	encoding_impronta varchar(10) NULL,
	nro_progr_bkver numeric NULL,
	flg_da_scansione numeric NULL,
	dt_scansione date NULL,
	id_user_scansione int4 NULL,
	spec_scansione varchar(1000) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	CONSTRAINT dmt_documents_versioni_fk FOREIGN KEY (id_doc) REFERENCES dmt_documents(id_doc)
);



CREATE TABLE auri_owner_1.dmt_email (
	casella_email varchar(100) NOT NULL,
	indirizzo_mittente varchar(100) NOT NULL,
	ts_invio timestamp NOT NULL,
	id_ud numeric(38) NULL,
	ts_last_try timestamp NULL,
	"try#" numeric(38) NULL,
	err_msg varchar(4000) NULL,
	note text NULL,
	email bytea NULL,
	flg_status varchar(1) NULL,
	flg_in_elaborazione int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_email_c_elab CHECK ((flg_in_elaborazione = 1)),
	CONSTRAINT dmt_email_c_status CHECK (((flg_status)::text = ANY ((ARRAY['S'::character varying, 'X'::character varying, 'R'::character varying])::text[]))),
	CONSTRAINT dmt_email_pkey PRIMARY KEY (casella_email, ts_invio, indirizzo_mittente)
);



CREATE TABLE auri_owner_1.dmt_email_invio_doc_elab_mass (
	userid_applicazione varchar(100) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	tipologia_doc varchar(250) NOT NULL,
	email_account varchar(500) NOT NULL,
	alias_account varchar(500) NULL,
	id_account varchar(64) NULL,
	CONSTRAINT dmt_email_invio_doc_elab_mass_pkey PRIMARY KEY (userid_applicazione, id_sp_aoo, tipologia_doc, email_account)
);



CREATE TABLE auri_owner_1.dmt_errori_doc_lotti_elab_mass (
	id_job numeric(38) NOT NULL,
	nro_posiz_doc numeric(38) NOT NULL,
	tipo_operazione varchar(100) NOT NULL,
	progr_errore numeric(38) NOT NULL,
	err_context varchar(250) NULL,
	err_code varchar(30) NULL,
	err_msg text NOT NULL,
	CONSTRAINT dmt_err_doc_lotti_c_nro_pos CHECK ((nro_posiz_doc >= (0)::numeric)),
	CONSTRAINT dmt_err_doc_lotti_c_progr CHECK ((progr_errore >= (0)::numeric)),
	CONSTRAINT dmt_errori_doc_lotti_elab_mass_pkey PRIMARY KEY (id_job, nro_posiz_doc, tipo_operazione, progr_errore)
);



CREATE TABLE auri_owner_1.dmt_event_types (
	id_event_type numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_applicazione varchar(30) NULL,
	cod_ist_applicazione varchar(30) NULL,
	des_event_type varchar(250) NOT NULL,
	flg_durativo int2 NULL,
	categoria varchar(150) NULL,
	durata_max numeric(38) NULL,
	id_doc_type numeric(38) NULL,
	cod_tipo_data_rel_doc varchar(10) NULL,
	flg_for_all_proc_amm int2 NULL DEFAULT 1,
	note varchar(1000) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_evt_type varchar(30) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_event_types_pkey PRIMARY KEY (id_event_type),
	CONSTRAINT dmt_evt_ty_c_all_proc CHECK ((flg_for_all_proc_amm = 1)),
	CONSTRAINT dmt_evt_ty_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_evt_ty_c_durativo CHECK ((flg_durativo = 1)),
	CONSTRAINT dmt_evt_ty_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_evt_ty_c_lck CHECK ((flg_locked = 1))
);
CREATE UNIQUE INDEX dmt_evt_type_u_des ON auri_owner_1.dmt_event_types USING btree (auri_master.dmfn_prepare_denom_x_match(des_event_type), id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_event_types_altri_attributi (
	id_event_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_event_types_attr_add_x_evt_del_tipo (
	id_event_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_posizione int4 NULL,
	flg_obblig numeric NULL,
	max_num_values int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	flg_x_timbro numeric NULL,
	flg_x_intitolazione numeric NULL,
	cod_tab_sez_gui varchar(30) NULL,
	des_tab_sez_gui varchar(250) NULL
);



CREATE TABLE auri_owner_1.dmt_event_types_eventi_derivati (
	id_event_type numeric(22) NULL,
	data_prov varchar(30) NULL,
	flg_tipo varchar(5) NULL,
	durata_max_sosp_interr int4 NULL,
	dett_esiti varchar(1000) NULL
);



CREATE TABLE auri_owner_1.dmt_event_types_info_storico (
	id_event_type numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);



CREATE TABLE auri_owner_1.dmt_event_types_stored_fnc (
	id_event_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	str_value varchar(1000) NULL
);



CREATE TABLE auri_owner_1.dmt_fax (
	id_rich_trasmissione numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_applicazione varchar(30) NULL,
	cod_ist_applicazione varchar(30) NULL,
	id_user numeric(38) NOT NULL,
	fax_server_mitt varchar(50) NOT NULL,
	nro_fax_dest varchar(50) NOT NULL,
	ts_rich_trasmissione timestamp NOT NULL,
	prov_ci_fax varchar(30) NULL,
	flg_status varchar(1) NULL,
	err_msg varchar(4000) NULL,
	note varchar(4000) NULL,
	nro_pagine_trasmesse numeric(38) NULL,
	ts_last_upd timestamp NULL,
	CONSTRAINT dmt_fax_c_status CHECK (((flg_status)::text = ANY ((ARRAY['S'::character varying, 'X'::character varying, 'R'::character varying, 'K'::character varying])::text[]))),
	CONSTRAINT dmt_fax_pkey PRIMARY KEY (id_rich_trasmissione)
);
CREATE INDEX dmi_fax_ts ON auri_owner_1.dmt_fax USING btree (ts_rich_trasmissione);
CREATE INDEX dmi_fax_user ON auri_owner_1.dmt_fax USING btree (id_user);



CREATE TABLE auri_owner_1.dmt_flussi_nav_web (
	ci_flusso varchar(1000) NOT NULL,
	ts_ins timestamp NOT NULL,
	ts_last_upd timestamp NULL,
	variabili auri_master.dmo_var_flusso_lav[] NULL,
	CONSTRAINT dmt_flussi_nav_web_pkey PRIMARY KEY (ci_flusso)
);


CREATE TABLE auri_owner_1.dmt_flussi_nav_web_variabili (
	ci_flusso varchar(1000) NULL,
	sezione varchar(100) NULL,
	nome varchar(250) NULL,
	valore_semplice varchar(4000) NULL,
	valore_complesso text NULL
);



CREATE TABLE auri_owner_1.dmt_fogli_x_import (
	id_foglio varchar(64) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	tipo_contenuto varchar(250) NOT NULL,
	ts_upload timestamp NOT NULL DEFAULT LOCALTIMESTAMP,
	id_user_upload numeric(38) NULL,
	uri varchar(1000) NOT NULL,
	stato varchar(100) NOT NULL,
	ts_inizio_elab timestamp NULL,
	ts_fine_elab timestamp NULL,
	nro_righe numeric(38) NULL,
	nro_righe_ok numeric(38) NULL DEFAULT 0,
	nro_righe_ko numeric(38) NULL DEFAULT 0,
	impronta varchar(255) NULL,
	algoritmo_impronta varchar(30) NULL,
	encoding_impronta varchar(10) NULL,
	info_elaborazione text NULL,
	CONSTRAINT dmt_fogli_x_imp_c_stato CHECK (((stato)::text = ANY ((ARRAY['da_elaborare'::character varying, 'in_elaborazione'::character varying, 'elaborato_senza_errori'::character varying, 'elaborato_con_errori'::character varying])::text[]))),
	CONSTRAINT dmt_fogli_x_import_pkey PRIMARY KEY (id_foglio)
);
CREATE INDEX dmi_fogli_x_import_appl ON auri_owner_1.dmt_fogli_x_import USING btree (cod_appl_owner, cod_ist_appl_owner);
CREATE INDEX dmi_fogli_x_import_impronta ON auri_owner_1.dmt_fogli_x_import USING btree (impronta, algoritmo_impronta, encoding_impronta);
CREATE INDEX dmi_fogli_x_import_sp ON auri_owner_1.dmt_fogli_x_import USING btree (id_sp_aoo);
CREATE INDEX dmi_fogli_x_import_stato ON auri_owner_1.dmt_fogli_x_import USING btree (stato);
CREATE INDEX dmi_fogli_x_import_tsup ON auri_owner_1.dmt_fogli_x_import USING btree (ts_upload);
CREATE INDEX dmi_fogli_x_import_ty ON auri_owner_1.dmt_fogli_x_import USING btree (tipo_contenuto);
CREATE INDEX dmi_fogli_x_import_usup ON auri_owner_1.dmt_fogli_x_import USING btree (id_user_upload);



CREATE TABLE auri_owner_1.dmt_folder (
	id_folder numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	nome_folder varchar(250) NOT NULL,
	id_library numeric(38) NOT NULL,
	id_folder_type_gen numeric(38) NOT NULL,
	id_folder_type_dett numeric(38) NULL,
	anno_apertura numeric(38) NULL,
	id_classificazione numeric(38) NULL,
	nro_progr_fasc int4 NULL,
	nro_progr_sottofasc int4 NULL,
	nro_inserto int4 NULL,
	nro_secondario varchar(30) NULL,
	des_ogg varchar(4000) NULL,
	parole_chiave varchar(4000) NULL,
	order_by_value varchar(250) NULL,
	ts_apertura timestamp NOT NULL,
	id_user_apertura numeric(38) NULL,
	id_uo_apertura numeric(38) NULL,
	id_uo_resp numeric(38) NULL,
	id_scrivania_resp numeric(38) NULL,
	ts_chiusura timestamp NULL,
	id_user_chiusura numeric(38) NULL,
	flg_archivio varchar(1) NULL,
	dt_vers_deposito timestamp NULL,
	id_user_vers_deposito numeric(38) NULL,
	id_user_aut_vers_dep numeric(38) NULL,
	dt_vers_storico timestamp NULL,
	id_user_vers_storico numeric(38) NULL,
	id_user_aut_vers_stor numeric(38) NULL,
	liv_riservatezza numeric(38) NULL,
	dt_termine_riservatezza timestamp NULL,
	flg_visib_in_chiaro varchar(3) NULL,
	id_user_ass numeric(38) NULL,
	id_uo_ass numeric(38) NULL,
	id_scrivania_ass numeric(38) NULL,
	id_ass varchar(40) NULL,
	flg_in_carico int2 NULL,
	id_user_in_carico numeric(38) NULL,
	id_uo_in_carico numeric(38) NULL,
	id_scrivania_in_carico numeric(38) NULL,
	id_movimento_ultimo_vld numeric(38) NULL,
	flg_senza_proced int2 NULL,
	liv_evidenza numeric(38) NULL,
	flg_conserv_perm int2 NULL,
	periodo_conserv numeric(38) NULL,
	cod_supporto_conserv varchar(10) NULL,
	flg_cartaceo_eliminato int2 NULL,
	dt_elim_cartaceo timestamp NULL,
	id_user_elim_cart numeric(38) NULL,
	id_user_aut_elim_cart numeric(38) NULL,
	dt_scarto timestamp NULL,
	id_user_scarto numeric(38) NULL,
	id_user_aut_scarto numeric(38) NULL,
	id_toponimo numeric(38) NULL,
	collocazione_fisica varchar(1000) NULL,
	note varchar(4000) NULL,
	ts_lock timestamp NULL,
	id_user_lock numeric(38) NULL,
	flg_tipo_lock varchar(1) NULL,
	cod_stato varchar(10) NULL,
	cod_stato_dett varchar(10) NULL,
	ts_last_upd_stato timestamp NULL,
	flg_interesse_attivo int2 NULL DEFAULT 1,
	ts_interesse_cessato timestamp NULL,
	flg_segnatura_auto int2 NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	flg_cons_ered_permessi int2 NULL DEFAULT 1,
	id_folder_ered numeric(38) NULL,
	flg_cons_prop_permessi int2 NULL DEFAULT 1,
	riaperture auri_master.dmo_riapertura NULL,
	prov_ci_folder varchar(30) NULL,
	container_type varchar(30) NULL,
	container_alias varchar(30) NULL,
	rif_in_repository varchar(1000) NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	id_pratica_sinapoli varchar(50) NULL,
	ts_invio_in_conserv timestamp NULL,
	id_in_conservazione varchar(250) NULL,
	id_pdv varchar(64) NULL,
	ts_acc_in_conserv timestamp NULL,
	ts_inizio_conserv timestamp NULL,
	dt_termine_conserv timestamp NULL,
	flg_porogato_termine_cons int2 NULL,
	flg_esib_da_cons int2 NULL,
	err_msg_invio_in_conserv varchar(4000) NULL,
	stato_conservazione varchar(30) NULL,
	CONSTRAINT dmt_fld_c_cart_elim CHECK ((flg_cartaceo_eliminato = 1)),
	CONSTRAINT dmt_fld_c_conserv CHECK ((flg_conserv_perm = 1)),
	CONSTRAINT dmt_fld_c_ered_perm CHECK ((flg_cons_ered_permessi = 1)),
	CONSTRAINT dmt_fld_c_esib_da_cons CHECK ((flg_esib_da_cons = 1)),
	CONSTRAINT dmt_fld_c_int_attivo CHECK ((flg_interesse_attivo = 1)),
	CONSTRAINT dmt_fld_c_prop_perm CHECK ((flg_cons_prop_permessi = 1)),
	CONSTRAINT dmt_fld_c_pror_term_cons CHECK ((flg_porogato_termine_cons = 1)),
	CONSTRAINT dmt_fld_c_stato_cons_ck CHECK (((stato_conservazione)::text = ANY (ARRAY[('inviato'::character varying)::text, ('accettato'::character varying)::text, ('conservato'::character varying)::text, ('rifiutato'::character varying)::text, ('da_inviare'::character varying)::text, ('non_prevista'::character varying)::text]))),
	CONSTRAINT dmt_fld_c_tipo_lock CHECK (((flg_tipo_lock)::text = ANY (ARRAY[('I'::character varying)::text, ('E'::character varying)::text]))),
	CONSTRAINT dmt_folder_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_folder_c_archivio CHECK (((flg_archivio)::text = ANY (ARRAY[('C'::character varying)::text, ('D'::character varying)::text, ('S'::character varying)::text]))),
	CONSTRAINT dmt_folder_c_in_carico CHECK ((flg_in_carico = 1)),
	CONSTRAINT dmt_folder_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_folder_c_no_proced CHECK ((flg_senza_proced = 1)),
	CONSTRAINT dmt_folder_c_segn_auto CHECK ((flg_segnatura_auto = 1)),
	CONSTRAINT dmt_folder_c_vis_chiaro CHECK (((flg_visib_in_chiaro)::text = ANY (ARRAY[('RC'::character varying)::text, ('RCC'::character varying)::text, ('RCE'::character varying)::text]))),
	CONSTRAINT dmt_folder_pkey PRIMARY KEY (id_folder)
)
WITH (
	OIDS=TRUE
);
CREATE INDEX dmi_fld_ts_acc_cons ON auri_owner_1.dmt_folder USING btree (ts_acc_in_conserv);
CREATE INDEX dmi_fld_ts_ini_cons ON auri_owner_1.dmt_folder USING btree (ts_inizio_conserv);
CREATE INDEX dmi_fld_ts_term_cons ON auri_owner_1.dmt_folder USING btree (dt_termine_conserv);
CREATE INDEX dmi_folder_anno_ap ON auri_owner_1.dmt_folder USING btree (anno_apertura);
CREATE INDEX dmi_folder_ass ON auri_owner_1.dmt_folder USING btree ((
CASE
    WHEN ((id_uo_ass)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_ass)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_ass)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_ass)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_ass)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_ass)::character varying)::text)
END));
CREATE INDEX dmi_folder_ass_2 ON auri_owner_1.dmt_folder USING btree (id_ass);
CREATE INDEX dmi_folder_classif ON auri_owner_1.dmt_folder USING btree (id_classificazione);
CREATE INDEX dmi_folder_folder_ered ON auri_owner_1.dmt_folder USING btree (id_folder_ered);
CREATE INDEX dmi_folder_id_cons ON auri_owner_1.dmt_folder USING btree (auri_master.dmfn_prepare_denom_x_match(id_in_conservazione));
CREATE INDEX dmi_folder_in_carico ON auri_owner_1.dmt_folder USING btree ((
CASE
    WHEN ((id_uo_in_carico)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_in_carico)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_in_carico)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_in_carico)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_in_carico)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_in_carico)::character varying)::text)
END));
CREATE INDEX dmi_folder_keys ON auri_owner_1.dmt_folder USING gin (to_tsvector('english'::regconfig, (parole_chiave)::text));
CREATE INDEX dmi_folder_lock ON auri_owner_1.dmt_folder USING btree (ts_lock);
CREATE INDEX dmi_folder_nome ON auri_owner_1.dmt_folder USING btree (auri_master.dmfn_prepare_denom_x_match(nome_folder), id_sp_aoo);
CREATE INDEX dmi_folder_nome_2 ON auri_owner_1.dmt_folder USING gin (to_tsvector('english'::regconfig, (nome_folder)::text));
CREATE INDEX dmi_folder_note ON auri_owner_1.dmt_folder USING gin (to_tsvector('english'::regconfig, (note)::text));
CREATE INDEX dmi_folder_nro_fasc ON auri_owner_1.dmt_folder USING btree (nro_progr_fasc, nro_progr_sottofasc);
CREATE INDEX dmi_folder_nro_sec ON auri_owner_1.dmt_folder USING btree (nro_secondario);
CREATE INDEX dmi_folder_ogg ON auri_owner_1.dmt_folder USING gin (to_tsvector('english'::regconfig, (des_ogg)::text));
CREATE INDEX dmi_folder_order_by ON auri_owner_1.dmt_folder USING btree (order_by_value);
CREATE INDEX dmi_folder_pdv ON auri_owner_1.dmt_folder USING btree (id_pdv);
CREATE INDEX dmi_folder_prov_ci ON auri_owner_1.dmt_folder USING btree (prov_ci_folder);
CREATE INDEX dmi_folder_prov_ci2 ON auri_owner_1.dmt_folder USING btree (auri_master.dmfn_prepare_denom_x_match(prov_ci_folder));
CREATE INDEX dmi_folder_spaoo ON auri_owner_1.dmt_folder USING btree (id_sp_aoo);
CREATE INDEX dmi_folder_stato ON auri_owner_1.dmt_folder USING btree (cod_stato);
CREATE INDEX dmi_folder_stato_dett ON auri_owner_1.dmt_folder USING btree (cod_stato_dett);
CREATE INDEX dmi_folder_sv_resp ON auri_owner_1.dmt_folder USING btree (id_scrivania_resp);
CREATE INDEX dmi_folder_topon ON auri_owner_1.dmt_folder USING btree (id_toponimo);
CREATE INDEX dmi_folder_ts_ap ON auri_owner_1.dmt_folder USING btree (ts_apertura);
CREATE INDEX dmi_folder_ts_ch ON auri_owner_1.dmt_folder USING btree (ts_chiusura);
CREATE INDEX dmi_folder_ts_int_cess ON auri_owner_1.dmt_folder USING btree (ts_interesse_cessato);
CREATE INDEX dmi_folder_ty_dett ON auri_owner_1.dmt_folder USING btree (id_folder_type_dett);
CREATE INDEX dmi_folder_ty_gen ON auri_owner_1.dmt_folder USING btree (id_folder_type_gen);
CREATE INDEX dmi_folder_ult_mov ON auri_owner_1.dmt_folder USING btree (id_movimento_ultimo_vld);
CREATE INDEX dmi_folder_uo_resp ON auri_owner_1.dmt_folder USING btree (id_uo_resp);



CREATE TABLE auri_owner_1.dmt_folder_acl (
	id_folder numeric(22) NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL,
	nro_pos_in_acl int4 NULL,
	flg_visibilita numeric NULL,
	flg_modifica_dati numeric NULL,
	flg_modifica_acl numeric NULL,
	flg_eliminazione numeric NULL,
	flg_copia numeric NULL,
	flg_recupero numeric NULL,
	flg_mod_cont_ud numeric NULL,
	flg_mod_cont_folder numeric NULL,
	flg_ereditato numeric NULL
);



CREATE TABLE auri_owner_1.dmt_folder_altri_attributi (
	id_folder numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);


CREATE TABLE auri_owner_1.dmt_folder_eliminati_def (
	id_user numeric(38) NOT NULL,
	ci_sezione_elim varchar(5) NOT NULL,
	id_folder numeric(38) NOT NULL,
	ts_last_del timestamp NOT NULL,
	CONSTRAINT dmt_fld_elim_def_c_sez CHECK (((ci_sezione_elim)::text = ANY ((ARRAY['N'::character varying, 'W'::character varying, 'NA'::character varying, 'NCC'::character varying, 'NNA'::character varying, 'DIA'::character varying])::text[]))),
	CONSTRAINT dmt_folder_eliminati_def_pkey PRIMARY KEY (id_user, id_folder, ci_sezione_elim)
);
CREATE INDEX dmi_fld_elim_def_fld ON auri_owner_1.dmt_folder_eliminati_def USING btree (id_folder);
CREATE INDEX dmi_fld_elim_def_sez ON auri_owner_1.dmt_folder_eliminati_def USING btree (ci_sezione_elim);
CREATE INDEX dmi_fld_elim_def_user ON auri_owner_1.dmt_folder_eliminati_def USING btree (id_user);



CREATE TABLE auri_owner_1.dmt_folder_folder_appartenenza (
	id_folder numeric(22) NULL,
	id_folder_app int4 NULL,
	nro_pos_vs_folder int4 NULL,
	motivo_rel varchar(250) NULL,
	nro_pos_in_folder_app int4 NULL,
	flg_locked numeric NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL
);



CREATE TABLE auri_owner_1.dmt_folder_info_storico (
	id_folder numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_folder_prese_visioni (
	id_folder numeric(22) NULL,
	id_user int4 NULL,
	a_nome_di int4 NULL,
	ts date NULL,
	osservazioni varchar(1000) NULL
);



CREATE TABLE auri_owner_1.dmt_folder_riaperture (
	id_folder numeric(22) NULL,
	ts_riapertura date NULL,
	id_user_riapertura int4 NULL,
	id_uo_riapertura int4 NULL,
	id_user_aut_riap int4 NULL,
	ts_chiusura_prec date NULL,
	id_user_chiusura_prec int4 NULL,
	motivazioni_riapertura varchar(1000) NULL
);



CREATE TABLE auri_owner_1.dmt_folder_types (
	id_folder_type numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_applicazione varchar(30) NULL,
	cod_ist_applicazione varchar(30) NULL,
	nome_folder_type varchar(100) NOT NULL,
	id_folder_type_gen numeric(38) NULL,
	flg_ric_abil_x_vis int2 NULL,
	flg_ric_abil_x_tratt int2 NULL,
	flg_ric_abil_x_assegn int2 NULL,
	flg_rich_scansione int2 NULL,
	flg_conserv_perm int2 NULL,
	periodo_conserv numeric(38) NULL,
	cod_supporto_conserv varchar(10) NULL,
	id_classificazione numeric(38) NULL,
	flg_x_fasc int2 NULL,
	flg_x_sottofasc int2 NULL,
	flg_x_inserto int2 NULL,
	template_nome_folder varchar(1000) NULL,
	template_code_folder varchar(1000) NULL,
	template_timbro_folder varchar(1000) NULL,
	note varchar(1000) NULL,
	cod_folder_type_x_repl varchar(150) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_folder_type varchar(30) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	id_process_type numeric(38) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	default_acl auri_master.dmo_ci_x_sp_aoo[] NULL,
	CONSTRAINT dmt_folder_ty_c_ric_abil_a CHECK ((flg_ric_abil_x_assegn = 1)),
	CONSTRAINT dmt_folder_ty_c_ric_abil_t CHECK ((flg_ric_abil_x_tratt = 1)),
	CONSTRAINT dmt_folder_ty_c_ric_abil_v CHECK ((flg_ric_abil_x_vis = 1)),
	CONSTRAINT dmt_folder_ty_c_x_fasc CHECK ((flg_x_fasc = 1)),
	CONSTRAINT dmt_folder_ty_c_x_inserto CHECK ((flg_x_inserto = 1)),
	CONSTRAINT dmt_folder_ty_c_x_sf CHECK ((flg_x_sottofasc = 1)),
	CONSTRAINT dmt_folder_types_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_folder_types_c_conserv CHECK ((flg_conserv_perm = 1)),
	CONSTRAINT dmt_folder_types_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_folder_types_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_folder_types_c_scans CHECK ((flg_rich_scansione = 1)),
	CONSTRAINT dmt_folder_types_pkey PRIMARY KEY (id_folder_type)
);
CREATE INDEX dmi_fld_ty_proc_ty ON auri_owner_1.dmt_folder_types USING btree (id_process_type);
CREATE UNIQUE INDEX dmt_folder_types_u_nome ON auri_owner_1.dmt_folder_types USING btree (auri_master.dmfn_prepare_denom_x_match(nome_folder_type), id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_folder_types_altri_attributi (
	id_folder_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL,
	CONSTRAINT dmt_folder_types_altri_attributi_fk FOREIGN KEY (id_folder_type) REFERENCES dmt_folder_types(id_folder_type)
);



CREATE TABLE auri_owner_1.dmt_folder_types_attr_add_x_folder_del_tipo (
	id_folder_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_posizione int4 NULL,
	flg_obblig numeric NULL,
	max_num_values int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	flg_x_timbro numeric NULL,
	flg_x_intitolazione numeric NULL,
	cod_tab_sez_gui varchar(30) NULL,
	des_tab_sez_gui varchar(250) NULL,
	CONSTRAINT dmt_folder_types_altri_attributi_fk FOREIGN KEY (id_folder_type) REFERENCES dmt_folder_types(id_folder_type)
);



CREATE TABLE auri_owner_1.dmt_folder_types_default_acl (
	id_folder_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	CONSTRAINT dmt_folder_types_altri_attributi_fk FOREIGN KEY (id_folder_type) REFERENCES dmt_folder_types(id_folder_type)
);



CREATE TABLE auri_owner_1.dmt_folder_types_folderizzazione_auto (
	id_folder_type numeric(22) NULL,
	tipo_valore varchar(1) NULL,
	valore varchar(1000) NULL,
	progressivo numeric(10) NULL
);



CREATE TABLE auri_owner_1.dmt_folder_types_info_storico (
	id_folder_type numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL,
	CONSTRAINT dmt_folder_types_altri_attributi_fk FOREIGN KEY (id_folder_type) REFERENCES dmt_folder_types(id_folder_type)
);



CREATE TABLE auri_owner_1.dmt_folder_types_modelli (
	id_folder_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	CONSTRAINT dmt_folder_types_altri_attributi_fk FOREIGN KEY (id_folder_type) REFERENCES dmt_folder_types(id_folder_type)
);



CREATE TABLE auri_owner_1.dmt_folder_vs_sez_area_lav (
	id_user numeric(38) NOT NULL,
	ci_sezione varchar(5) NOT NULL,
	id_folder numeric(38) NOT NULL,
	ts_last_ins timestamp NOT NULL,
	ts_ins_in_sez_prov timestamp NULL,
	motivi varchar(500) NULL,
	lista_id_movimenti numeric[] NULL,
	CONSTRAINT dmt_folder_vs_sez_al_sez CHECK (((ci_sezione)::text = ANY (ARRAY[('P'::character varying)::text, ('I'::character varying)::text, ('C'::character varying)::text, ('EN'::character varying)::text, ('EI'::character varying)::text, ('EC'::character varying)::text, ('EP'::character varying)::text, ('RL'::character varying)::text, ('RV'::character varying)::text, ('EW'::character varying)::text, ('V'::character varying)::text, ('ENA'::character varying)::text, ('ENCC'::character varying)::text, ('ENNA'::character varying)::text, ('EDIA'::character varying)::text]))),
	CONSTRAINT dmt_folder_vs_sez_area_lav_pkey PRIMARY KEY (id_user, ci_sezione, id_folder)
);
CREATE INDEX dmi_fld_vs_sez_al_ci_sez ON auri_owner_1.dmt_folder_vs_sez_area_lav USING btree (ci_sezione);
CREATE INDEX dmi_fld_vs_sez_al_user ON auri_owner_1.dmt_folder_vs_sez_area_lav USING btree (id_user);
CREATE INDEX dmi_folder_vs_sez_al_ud ON auri_owner_1.dmt_folder_vs_sez_area_lav USING btree (id_folder);



CREATE TABLE auri_owner_1.dmt_fonti (
	id_fonte numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	nro_fonte varchar(100) NOT NULL,
	anno_fonte int2 NOT NULL,
	cod_tipo_fonte varchar(250) NOT NULL,
	descrizione varchar(250) NULL,
	id_ente_emitt numeric(38) NULL,
	id_ud numeric(38) NOT NULL,
	id_process_orig numeric(38) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_fonte varchar(200) NULL,
	note varchar(4000) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_fonti_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_fonti_pkey PRIMARY KEY (id_fonte)
);
CREATE UNIQUE INDEX dmi_fonti_ente_emitt ON auri_owner_1.dmt_fonti USING btree (id_ente_emitt);
CREATE UNIQUE INDEX dmi_fonti_ud ON auri_owner_1.dmt_fonti USING btree (id_ud);



CREATE TABLE auri_owner_1.dmt_fonti_altri_attributi (
	id_fonte numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_formati_el_ammessi (
	id_formato_el numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_formato varchar(150) NULL,
	estensione varchar(100) NULL,
	mimetype varchar(500) NOT NULL,
	flg_binario int2 NULL,
	flg_da_indicizzare int2 NULL,
	flg_convertibile_pdf int2 NULL,
	dt_inizio_amm timestamp NULL,
	dt_fine_amm timestamp NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	info_storico auri_master.dmo_info_storico[] NULL,
	prov_ci_formato_el varchar(30) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_formati_el_ammessi_pkey PRIMARY KEY (id_formato_el),
	CONSTRAINT dmt_formati_el_c_bin CHECK ((flg_binario = 1)),
	CONSTRAINT dmt_formati_el_c_convpdf CHECK ((flg_convertibile_pdf = 1)),
	CONSTRAINT dmt_formati_el_c_ind CHECK ((flg_da_indicizzare = 1))
);
CREATE UNIQUE INDEX dmt_formati_el_u_nome ON auri_owner_1.dmt_formati_el_ammessi USING btree (auri_master.dmfn_prepare_denom_x_match(nome_formato), id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_formati_el_ammessi_altri_attributi (
	id_formato_el numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_formati_el_ammessi_info_storico (
	id_formato_el numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);



CREATE TABLE auri_owner_1.dmt_funzioni (
	cod_funz_p1 varchar(3) NOT NULL,
	cod_funz_p2 varchar(3) NULL,
	cod_funz_p3 varchar(3) NULL,
	des_funz varchar(150) NOT NULL,
	disattiva_in_sp_aoo numeric[] NULL,
	flg_ann int2 NULL,
	flg_no_opz_std int2 NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_funzioni_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_funzioni_c_no_opz_std CHECK ((flg_no_opz_std = 1))
);
CREATE UNIQUE INDEX dmt_funzioni_u ON auri_owner_1.dmt_funzioni USING btree (cod_funz_p1, cod_funz_p2, cod_funz_p3);
CREATE UNIQUE INDEX dmt_funzioni_u2 ON auri_owner_1.dmt_funzioni USING btree (((((cod_funz_p1)::text ||
CASE
    WHEN (cod_funz_p2 IS NULL) THEN NULL::text
    ELSE ('/'::text || (cod_funz_p2)::text)
END) ||
CASE
    WHEN (cod_funz_p3 IS NULL) THEN NULL::text
    ELSE ('/'::text || (cod_funz_p3)::text)
END)));
CREATE UNIQUE INDEX dmt_funzioni_u_des ON auri_owner_1.dmt_funzioni USING btree (auri_master.dmfn_prepare_denom_x_match(des_funz));




CREATE TABLE auri_owner_1.dmt_gen_progr (
	id_sp_aoo numeric(38) NOT NULL,
	cod_scope varchar(30) NOT NULL,
	cond_value varchar(30) NULL,
	anno int2 NULL,
	progr numeric(38) NOT NULL DEFAULT 0,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_gen_progr_id_sp_aoo_cod_scope_cond_value_anno_key UNIQUE (id_sp_aoo, cod_scope, cond_value, anno)
);
CREATE UNIQUE INDEX dmt_gen_progr_u ON auri_owner_1.dmt_gen_progr USING btree (id_sp_aoo, cod_scope, cond_value, anno);




CREATE TABLE auri_owner_1.dmt_gruppi (
	id_gruppo numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_gruppo varchar(1000) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	flg_di_def_in_acl varchar(3) NULL,
	membri auri_master.dmo_componente_gruppo NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	prov_ci_gruppo varchar(30) NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_gruppi_c_def_acl CHECK ((replace(replace(replace((flg_di_def_in_acl)::text, 'D'::text, NULL::text), 'F'::text, NULL::text), 'W'::text, NULL::text) IS NULL)),
	CONSTRAINT dmt_gruppi_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_gruppi_pkey PRIMARY KEY (id_gruppo)
);
CREATE INDEX dmi_gruppi_spaoo ON auri_owner_1.dmt_gruppi USING btree (id_sp_aoo);
CREATE UNIQUE INDEX dmt_gruppi_u ON auri_owner_1.dmt_gruppi USING btree (auri_master.dmfn_prepare_denom_x_match(nome_gruppo), id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_gruppi_altri_attributi (
	id_gruppo numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_gruppi_info_storico (
	id_gruppo numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);



CREATE TABLE auri_owner_1.dmt_gruppi_membri (
	id_gruppo numeric(22) NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric NULL,
	flg_incl_scrivanie numeric NULL
);



CREATE TABLE auri_owner_1.dmt_gruppi_privilegi (
	id_gruppo_priv numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_gruppo_priv varchar(150) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	flg_ann int2 NULL,
	note varchar(500) NULL,
	prov_ci_gruppo varchar(30) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_gruppi_priv_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_gruppi_priv_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_gruppi_priv_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_gruppi_privilegi_pkey PRIMARY KEY (id_gruppo_priv)
);
CREATE INDEX dmi_gruppi_priv_spaoo ON auri_owner_1.dmt_gruppi_privilegi USING btree (id_sp_aoo);
CREATE UNIQUE INDEX dmt_gruppi_priv_u ON auri_owner_1.dmt_gruppi_privilegi USING btree (auri_master.dmfn_prepare_denom_x_match(nome_gruppo_priv), id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_gruppi_sogg_est (
	id_gruppo numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_gruppo varchar(1000) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	prov_ci_gruppo varchar(30) NULL,
	id_gruppo_int numeric(38) NULL,
	cod_rapido varchar(30) NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_gruppi_est_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_gruppi_sogg_est_pkey PRIMARY KEY (id_gruppo)
);
CREATE INDEX dmi_gruppi_est_spaoo ON auri_owner_1.dmt_gruppi_sogg_est USING btree (id_sp_aoo);
CREATE INDEX dmi_gruppi_sogg_est_cod_r ON auri_owner_1.dmt_gruppi_sogg_est USING btree (cod_rapido, id_sp_aoo);
CREATE INDEX dmi_gruppi_sogg_est_u_gr_int ON auri_owner_1.dmt_gruppi_sogg_est USING btree (id_gruppo_int);
CREATE UNIQUE INDEX dmt_gruppi_sogg_est_u ON auri_owner_1.dmt_gruppi_sogg_est USING btree (auri_master.dmfn_prepare_denom_x_match(nome_gruppo), id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_gruppi_sogg_est_altri_attributi (
	id_gruppo numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL,
	CONSTRAINT dmt_gruppi_sogg_est_altri_attributi_fk FOREIGN KEY (id_gruppo) REFERENCES dmt_gruppi_sogg_est(id_gruppo)
);



CREATE TABLE auri_owner_1.dmt_gruppi_sogg_est_info_storico (
	id_gruppo numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL,
	CONSTRAINT dmt_gruppi_sogg_est_altri_attributi_fk FOREIGN KEY (id_gruppo) REFERENCES dmt_gruppi_sogg_est(id_gruppo)
);



CREATE TABLE auri_owner_1.dmt_gruppi_sogg_est_membri (
	id_gruppo numeric(22) NULL,
	flg_tipo varchar(1) NULL,
	id_in_anag_prov int4 NULL,
	CONSTRAINT dmt_gruppi_sogg_est_altri_attributi_fk FOREIGN KEY (id_gruppo) REFERENCES dmt_gruppi_sogg_est(id_gruppo)
);



CREATE TABLE auri_owner_1.dmt_impostazioni_personali (
	id_user numeric(38) NOT NULL,
	cod_target varchar(30) NOT NULL,
	valore varchar(1000) NOT NULL,
	val_dettaglio varchar(1000) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_impostazioni_personali_pkey PRIMARY KEY (id_user, cod_target, valore)
);




CREATE TABLE auri_owner_1.dmt_info_x_indexer (
	categoria varchar(30) NOT NULL DEFAULT 'REP_DOC'::character varying,
	id_sp_aoo numeric(38) NULL,
	ci_obj varchar(80) NOT NULL,
	attr_name varchar(250) NOT NULL,
	attr_value varchar(4000) NULL,
	note varchar(250) NULL,
	ts_ins timestamp NULL,
	stato varchar(1) NULL,
	ts_last_try timestamp NULL,
	"try#" numeric(38) NULL,
	err_msg varchar(4000) NULL
);
CREATE INDEX dmi_info_x_indexer_ts ON auri_owner_1.dmt_info_x_indexer USING btree (ts_ins);
CREATE UNIQUE INDEX dmt_info_x_indexer_u ON auri_owner_1.dmt_info_x_indexer USING btree (ci_obj, attr_name, id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_input_moduli_comp (
	id_modello numeric(38) NOT NULL,
	nome_input varchar(100) NOT NULL,
	attr_name varchar(250) NULL,
	id_sp_aoo_attr numeric(38) NULL,
	flg_obbligatorio int2 NULL,
	flg_compilabile int2 NULL,
	flg_multivalore int2 NOT NULL DEFAULT 0,
	flg_barcode int2 NOT NULL DEFAULT 0,
	tipo_barcode varchar(100) NULL,
	max_num_values numeric(38) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	attr_type varchar(30) NULL,
	nome_tab_sr varchar(250) NULL,
	tipo_car varchar(100) NULL,
	dimensione_car numeric(38) NULL,
	altezza_campo float8 NULL,
	lunghezza_campo float8 NULL,
	CONSTRAINT dmt_input_moduli_c_bar CHECK ((flg_barcode = ANY (ARRAY[1, 0]))),
	CONSTRAINT dmt_input_moduli_c_comp CHECK ((flg_compilabile = ANY (ARRAY[1, 0]))),
	CONSTRAINT dmt_input_moduli_c_mv CHECK ((flg_multivalore = ANY (ARRAY[1, 0]))),
	CONSTRAINT dmt_input_moduli_c_obbl CHECK ((flg_obbligatorio = 1)),
	CONSTRAINT dmt_input_moduli_comp_pkey PRIMARY KEY (id_modello, nome_input, flg_multivalore)
);



CREATE TABLE auri_owner_1.dmt_invii_ud (
	id_invio numeric NOT NULL,
	id_ud numeric NOT NULL,
	cod_tipo_invio varchar(10) NOT NULL,
	cod_motivo varchar(10) NULL,
	ts_invio timestamp NOT NULL,
	id_user_invio numeric NOT NULL,
	invio_a_nome_di numeric NULL,
	id_user_prov numeric NULL,
	id_uo_prov numeric NULL,
	id_scrivania_prov numeric NULL,
	ts_decorr_assegnazione timestamp NOT NULL,
	flg_mod_agg_acl varchar(2) NULL DEFAULT 'M'::character varying,
	destinatario auri_master.dmo_soggetto_interno NULL,
	flg_primo_dest int2 NULL,
	cod_ruolo_dest varchar(10) NULL,
	id_uo_dest_sel_mitt numeric NULL,
	id_scriv_dest_sel_mitt numeric NULL,
	msg_invio varchar(4000) NULL,
	livello_priorita numeric NULL,
	id_folder_inviato_con_ud numeric NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric NULL,
	CONSTRAINT dmt_invii_ud_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_invii_ud_c_pr_dest CHECK ((flg_primo_dest = 1)),
	CONSTRAINT dmt_invii_ud_pkey PRIMARY KEY (id_invio)
);
CREATE INDEX dmi_invii_ud_dest ON auri_owner_1.dmt_invii_ud USING btree (((destinatario).flg_tipo), ((destinatario).id_in_anag_prov), ((destinatario).vs_id_uo), ((destinatario).vs_livello_so));
CREATE INDEX dmi_invii_ud_fld_inv ON auri_owner_1.dmt_invii_ud USING btree (id_folder_inviato_con_ud);
CREATE INDEX dmi_invii_ud_prov ON auri_owner_1.dmt_invii_ud USING btree ((
CASE
    WHEN ((id_uo_prov)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_prov)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_prov)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_prov)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_prov)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_prov)::character varying)::text)
END));
CREATE INDEX dmi_invii_ud_ts ON auri_owner_1.dmt_invii_ud USING btree (ts_invio);
CREATE INDEX dmi_invii_ud_ts_ass ON auri_owner_1.dmt_invii_ud USING btree (ts_decorr_assegnazione);
CREATE INDEX dmi_invii_ud_ud ON auri_owner_1.dmt_invii_ud USING btree (id_ud);
CREATE INDEX dmi_invii_ud_user_invio ON auri_owner_1.dmt_invii_ud USING btree (id_user_invio);



CREATE TABLE auri_owner_1.dmt_invii_ud_altri_attributi (
	id_invio numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);


CREATE TABLE auri_owner_1.dmt_invii_ud_destinatario (
	id_invio int4 NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric(1) NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL
);



CREATE TABLE auri_owner_1.dmt_item_da_inviare_conserv (
	tipo_item varchar(1) NOT NULL DEFAULT 'U'::character varying,
	id_item numeric(38) NOT NULL,
	tipo_oper varchar(1) NOT NULL DEFAULT 'I'::character varying,
	id_entrypoint_vers varchar(64) NOT NULL,
	dt_invio_conserv_entro timestamp NOT NULL,
	liv_priorita numeric(38) NOT NULL DEFAULT 0,
	tipo_item_prec varchar(1) NULL,
	id_item_prec numeric(38) NULL,
	vers_prec_da_versare varchar(100) NULL,
	dimensione_file numeric(38) NOT NULL DEFAULT 0,
	id_gruppo_item varchar(64) NULL,
	ts_lock timestamp NULL,
	ts_last_try timestamp NULL,
	try_num numeric(38) NOT NULL DEFAULT 0,
	id_pdv varchar(64) NULL,
	id_item_inviato varchar(250) NULL,
	err_warn_code varchar(4000) NULL,
	err_warn_msg text NULL,
	ts_rec_esito_cons timestamp NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT dmt_item_da_inviare_conserv_pkey PRIMARY KEY (tipo_item, id_item),
	CONSTRAINT dmt_item_x_conserv_c_livp CHECK ((liv_priorita = ANY (ARRAY[(0)::numeric, (1)::numeric, (2)::numeric, (3)::numeric, (4)::numeric, (5)::numeric]))),
	CONSTRAINT dmt_item_x_conserv_c_tit CHECK (((tipo_item)::text = ANY ((ARRAY['U'::character varying, 'D'::character varying, 'F'::character varying])::text[]))),
	CONSTRAINT dmt_item_x_conserv_c_titpr CHECK (((tipo_item_prec)::text = ANY ((ARRAY['U'::character varying, 'D'::character varying, 'F'::character varying])::text[]))),
	CONSTRAINT dmt_item_x_conserv_c_top CHECK (((tipo_oper)::text = ANY ((ARRAY['I'::character varying, 'U'::character varying, 'D'::character varying])::text[])))
);
CREATE INDEX dmt_item_da_inv_conserv_entro ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (dt_invio_conserv_entro);
CREATE INDEX dmt_item_da_inv_conserv_ep ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (id_entrypoint_vers);
CREATE INDEX dmt_item_da_inv_conserv_grpit ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (id_gruppo_item);
CREATE INDEX dmt_item_da_inv_conserv_id_inv ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (id_item_inviato);
CREATE INDEX dmt_item_da_inv_conserv_item ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (id_item, tipo_item);
CREATE INDEX dmt_item_da_inv_conserv_lock ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (ts_lock);
CREATE INDEX dmt_item_da_inv_conserv_pdv ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (id_pdv);
CREATE INDEX dmt_item_da_inv_conserv_prec ON auri_owner_1.dmt_item_da_inviare_conserv USING btree (id_item_prec, tipo_item_prec);



CREATE TABLE auri_owner_1.dmt_item_x_conserv_h (
	tipo_item varchar(1) NOT NULL DEFAULT 'U'::character varying,
	id_item numeric(38) NOT NULL,
	tipo_oper varchar(1) NOT NULL DEFAULT 'I'::character varying,
	id_entrypoint_vers varchar(64) NOT NULL,
	dt_invio_conserv_entro timestamp NOT NULL,
	liv_priorita numeric(38) NOT NULL DEFAULT 0,
	tipo_item_prec varchar(1) NULL,
	id_item_prec numeric(38) NULL,
	vers_prec_da_versare varchar(100) NULL,
	dimensione_file numeric(38) NOT NULL DEFAULT 0,
	try_num numeric(38) NOT NULL DEFAULT 0,
	id_pdv varchar(64) NOT NULL,
	id_item_inviato varchar(250) NULL,
	ts_rec_esito_cons timestamp NULL,
	esito_invio_cons varchar(2) NULL,
	err_warn_code varchar(4000) NULL,
	err_warn_msg text NULL,
	id_item_in_conserv varchar(250) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT dmt_item_x_cons_c_esito CHECK (((esito_invio_cons)::text = ANY ((ARRAY['OK'::character varying, 'KO'::character varying, 'W'::character varying])::text[]))),
	CONSTRAINT dmt_item_x_cons_h_c_livp CHECK ((liv_priorita = ANY (ARRAY[(0)::numeric, (1)::numeric, (2)::numeric, (3)::numeric, (4)::numeric, (5)::numeric]))),
	CONSTRAINT dmt_item_x_cons_h_c_tit CHECK (((tipo_item)::text = ANY ((ARRAY['U'::character varying, 'D'::character varying, 'F'::character varying])::text[]))),
	CONSTRAINT dmt_item_x_cons_h_c_titpr CHECK (((tipo_item_prec)::text = ANY ((ARRAY['U'::character varying, 'D'::character varying, 'F'::character varying])::text[]))),
	CONSTRAINT dmt_item_x_cons_h_c_top CHECK (((tipo_oper)::text = ANY ((ARRAY['I'::character varying, 'U'::character varying, 'D'::character varying])::text[]))),
	CONSTRAINT dmt_item_x_conserv_h_pkey PRIMARY KEY (tipo_item, id_item, id_pdv)
);
CREATE INDEX dmt_item_x_cons_h_entro ON auri_owner_1.dmt_item_x_conserv_h USING btree (dt_invio_conserv_entro);
CREATE INDEX dmt_item_x_cons_h_ep ON auri_owner_1.dmt_item_x_conserv_h USING btree (id_entrypoint_vers);
CREATE INDEX dmt_item_x_cons_h_idcons ON auri_owner_1.dmt_item_x_conserv_h USING btree (id_item_in_conserv);
CREATE INDEX dmt_item_x_cons_h_idinv ON auri_owner_1.dmt_item_x_conserv_h USING btree (id_item_inviato);
CREATE INDEX dmt_item_x_cons_h_item ON auri_owner_1.dmt_item_x_conserv_h USING btree (id_item, tipo_item);
CREATE INDEX dmt_item_x_cons_h_pdv ON auri_owner_1.dmt_item_x_conserv_h USING btree (id_pdv);
CREATE INDEX dmt_item_x_cons_h_prec ON auri_owner_1.dmt_item_x_conserv_h USING btree (id_item_prec, tipo_item_prec);



CREATE TABLE auri_owner_1.dmt_log (
	id int4 NULL,
	func_name varchar(100) NULL,
	input_txt text NULL,
	output_txt text NULL,
	data_elab date NULL
);



CREATE TABLE auri_owner_1.dmt_log_job_caricamenti_file (
	id_evento int8 NOT NULL,
	id_job varchar(200) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_lotto varchar(64) NULL,
	id_doc_lotto varchar(64) NULL,
	cod_evento varchar(250) NOT NULL,
	cod_esito_evento varchar(250) NOT NULL,
	desc_esito_evento varchar(4000) NULL,
	ts_evento timestamp NULL,
	note varchar(4000) NULL,
	flg_hidden numeric(38) NULL,
	flg_locked numeric(38) NULL,
	flg_ann numeric(38) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_log_job_caricamenti_file_pkey PRIMARY KEY (id_evento)
);
CREATE INDEX dmt_logjobcarfile_idx_iddoclot ON auri_owner_1.dmt_log_job_caricamenti_file USING btree (id_doc_lotto);
CREATE INDEX dmt_logjobcarfile_idx_idlotto ON auri_owner_1.dmt_log_job_caricamenti_file USING btree (id_lotto);



CREATE TABLE auri_owner_1.dmt_lotti_doc_elab_massive (
	id_job numeric(38) NOT NULL,
	id_lotto varchar(64) NOT NULL,
	userid_applicazione varchar(100) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	ts_messa_disposizione timestamp NOT NULL,
	formato varchar(10) NOT NULL,
	tipologia_doc varchar(250) NOT NULL,
	stato varchar(50) NOT NULL,
	xml_request text NOT NULL,
	ts_inizio_validazione timestamp NULL,
	ts_fine_validazione timestamp NULL,
	ts_inizio_elaborazione timestamp NULL,
	ts_fine_elaborazione timestamp NULL,
	nro_doc_dichiarati int4 NOT NULL,
	nro_file_dichiarati int4 NOT NULL,
	nro_doc_rilevati int4 NOT NULL DEFAULT 0,
	nro_file_rilevati int4 NOT NULL DEFAULT 0,
	nro_doc_corretti int4 NOT NULL DEFAULT 0,
	nro_doc_in_err_validazione int4 NOT NULL DEFAULT 0,
	nro_doc_in_err_operazioni int4 NOT NULL DEFAULT 0,
	cod_errore_globale varchar(30) NULL,
	msg_errore_globale text NULL,
	work_path varchar(1000) NULL,
	backup_path varchar(1000) NULL,
	dimensione numeric(38) NOT NULL,
	file_esito text NULL,
	ts_gen_file_esito timestamp NULL,
	ts_consegna_file_esito timestamp NULL,
	ts_esito_completo timestamp NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ts_last_upd timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT dmt_lotti_doc_c_fmt CHECK (((formato)::text = ANY ((ARRAY['ZIP'::character varying, '7Z'::character varying, 'CARTELLA'::character varying])::text[]))),
	CONSTRAINT dmt_lotti_doc_c_stato CHECK (((stato)::text = ANY ((ARRAY['inserito'::character varying, 'in_validazione'::character varying, 'validato'::character varying, 'operazioni_in_corso'::character varying, 'elaborato'::character varying, 'scartato'::character varying])::text[]))),
	CONSTRAINT dmt_lotti_doc_elab_massive_pkey PRIMARY KEY (id_job)
);
CREATE INDEX dmi_lotti_doc_elab_mass_ts_fe ON auri_owner_1.dmt_lotti_doc_elab_massive USING btree (ts_fine_elaborazione);
CREATE INDEX dmi_lotti_doc_elab_mass_ts_fv ON auri_owner_1.dmt_lotti_doc_elab_massive USING btree (ts_fine_validazione);
CREATE INDEX dmi_lotti_doc_elab_mass_ts_ie ON auri_owner_1.dmt_lotti_doc_elab_massive USING btree (ts_inizio_elaborazione);
CREATE INDEX dmi_lotti_doc_elab_mass_ts_iv ON auri_owner_1.dmt_lotti_doc_elab_massive USING btree (ts_inizio_validazione);
CREATE INDEX dmi_lotti_doc_elab_mass_ts_md ON auri_owner_1.dmt_lotti_doc_elab_massive USING btree (ts_messa_disposizione);
CREATE UNIQUE INDEX dmt_lotti_doc_elab_mass_u_id ON auri_owner_1.dmt_lotti_doc_elab_massive USING btree (id_lotto, userid_applicazione);



CREATE TABLE auri_owner_1.dmt_lotti_documenti (
	id_lotto varchar(64) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	nome_lotto varchar(250) NOT NULL,
	descrizione varchar(4000) NULL,
	cod_stato varchar(10) NULL,
	ts_last_upd_stato timestamp NULL,
	ts_start_job timestamp NULL,
	ts_end_job timestamp NULL,
	note varchar(1000) NULL,
	nro_rec_totale numeric(38) NULL,
	nro_rec_caricati numeric(38) NULL,
	nro_rec_errore numeric(38) NULL,
	nro_rec_warning numeric(38) NULL,
	nro_rec_doppi numeric(38) NULL,
	nro_file_in_indice numeric(38) NULL,
	nro_file_ricevuti numeric(38) NULL,
	nro_file_assenti numeric(38) NULL,
	nro_file_non_descritti numeric(38) NULL,
	prov_ci_lotto varchar(30) NULL,
	id_ud numeric(38) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_ann int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_lotti_documenti_pkey PRIMARY KEY (id_lotto)
);



CREATE TABLE auri_owner_1.dmt_lotti_invii_in_conserv (
	id_lotto numeric(38) NOT NULL,
	stato varchar(1) NULL,
	err_msg varchar(4000) NULL,
	ts_invio timestamp NULL,
	ts_ins timestamp NULL,
	ts_last_upd timestamp NULL,
	CONSTRAINT dmt_lotti_c_stato CHECK (((stato)::text = ANY ((ARRAY['E'::character varying, 'I'::character varying, 'X'::character varying])::text[])))
);



CREATE TABLE auri_owner_1.dmt_modelli_doc (
	id_modello numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	nome_modello varchar(100) NOT NULL,
	des_modello varchar(1000) NULL,
	tipo_modello varchar(250) NULL,
	id_doc numeric(38) NOT NULL,
	id_doc_xml numeric(38) NULL,
	id_doc_pdf numeric(38) NULL,
	id_doc_html numeric(38) NULL,
	nro_ordine numeric(38) NULL,
	note varchar(1000) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_modello varchar(30) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_prof_comp int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_mod_doc_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_mod_doc_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_mod_doc_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_mod_doc_c_prof_c CHECK ((flg_prof_comp = 1)),
	CONSTRAINT dmt_modelli_doc_pkey PRIMARY KEY (id_modello)
);
CREATE UNIQUE INDEX dmt_modelli_doc_u_nome ON auri_owner_1.dmt_modelli_doc USING btree (auri_master.dmfn_prepare_denom_x_match(nome_modello), id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_modelli_doc_altri_attributi (
	id_modello numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);


CREATE TABLE auri_owner_1.dmt_modelli_doc_bookmark (
	id_modello numeric(38) NULL,
	valore varchar(100) NULL,
	progressivo numeric(10) NULL
);



CREATE TABLE auri_owner_1.dmt_modelli_doc_info_storico (
	id_modello numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_modelli_oggetti (
	id_mod_oggetto numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	ci_nome varchar(150) NOT NULL,
	oggetto varchar(4000) NOT NULL,
	prov_ci_modello varchar(30) NULL,
	flg_x_doc_entrata int2 NULL DEFAULT 1,
	flg_x_doc_uscita int2 NULL DEFAULT 1,
	flg_x_doc_interno int2 NULL DEFAULT 1,
	lista_tipi_reg_ammesse numeric[] NULL,
	flg_pubblico int2 NULL,
	flg_locked int2 NULL,
	flg_ann int2 NULL,
	note varchar(1000) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_uo numeric(38) NULL,
	flg_visib_sottouo int2 NULL,
	flg_gest_sottouo int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_mod_ogg_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_mod_ogg_c_entr CHECK ((flg_x_doc_entrata = 1)),
	CONSTRAINT dmt_mod_ogg_c_gest_suo CHECK ((flg_gest_sottouo = 1)),
	CONSTRAINT dmt_mod_ogg_c_int CHECK ((flg_x_doc_interno = 1)),
	CONSTRAINT dmt_mod_ogg_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_mod_ogg_c_pubbl CHECK ((flg_pubblico = 1)),
	CONSTRAINT dmt_mod_ogg_c_usc CHECK ((flg_x_doc_uscita = 1)),
	CONSTRAINT dmt_mod_ogg_c_vis_suo CHECK ((flg_visib_sottouo = 1)),
	CONSTRAINT dmt_modelli_oggetti_pkey PRIMARY KEY (id_mod_oggetto)
);
CREATE INDEX dmi_mod_oggetti_appl_owner ON auri_owner_1.dmt_modelli_oggetti USING btree (cod_appl_owner, cod_ist_appl_owner);
CREATE INDEX dmi_mod_oggetti_ci_nome ON auri_owner_1.dmt_modelli_oggetti USING btree (auri_master.dmfn_prepare_denom_x_match(ci_nome), id_sp_aoo);
CREATE INDEX dmi_mod_oggetti_prov_ci ON auri_owner_1.dmt_modelli_oggetti USING btree (prov_ci_modello);
CREATE INDEX dmi_mod_oggetti_sp_aoo ON auri_owner_1.dmt_modelli_oggetti USING btree (id_sp_aoo);
CREATE INDEX dmi_mod_oggetti_user_ins ON auri_owner_1.dmt_modelli_oggetti USING btree (id_user_ins);
CREATE INDEX dmi_modelli_oggetti_ogg ON auri_owner_1.dmt_modelli_oggetti USING gin (to_tsvector('english'::regconfig, (oggetto)::text));



CREATE TABLE auri_owner_1.dmt_modelli_reg_entita (
	id_modello_reg numeric(38) NOT NULL,
	cod_tipo_entita_target varchar(10) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_modello_reg varchar(100) NOT NULL,
	des_modello_reg varchar(1000) NULL,
	dati_modello xml NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_mod_reg_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_mod_reg_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_modelli_reg_entita_pkey PRIMARY KEY (id_modello_reg)
);
CREATE UNIQUE INDEX dmt_modelli_reg_u ON auri_owner_1.dmt_modelli_reg_entita USING btree (auri_master.dmfn_prepare_denom_x_match(nome_modello_reg), cod_tipo_entita_target, id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_movimenti_folder (
	id_movimento numeric(38) NOT NULL,
	id_folder numeric(38) NOT NULL,
	cod_tipo_invio varchar(10) NOT NULL,
	cod_motivo varchar(10) NULL,
	ts_invio timestamp NOT NULL,
	id_user_invio numeric(38) NOT NULL,
	invio_a_nome_di numeric(38) NULL,
	id_user_prov numeric(38) NULL,
	id_uo_prov numeric(38) NULL,
	id_scrivania_prov numeric(38) NULL,
	ts_decorr_assegnazione timestamp NOT NULL,
	flg_mod_agg_acl varchar(2) NULL DEFAULT 'M'::character varying,
	id_user_dest numeric(38) NULL,
	id_uo_dest numeric(38) NULL,
	id_scrivania_dest numeric(38) NULL,
	cod_ruolo_dest varchar(10) NULL,
	msg_invio varchar(4000) NULL,
	livello_priorita numeric(38) NULL,
	folder_app_inviato_con numeric(38) NULL,
	ts_acc timestamp NULL,
	flg_acc_implicita int2 NULL,
	id_user_acc numeric(38) NULL,
	acc_a_nome_di numeric(38) NULL,
	folder_app_acc_con numeric(38) NULL,
	ts_prima_presa_vis timestamp NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_mov_fld_c_acc_impl CHECK ((flg_acc_implicita = 1)),
	CONSTRAINT dmt_mov_fld_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_movimenti_folder_pkey PRIMARY KEY (id_movimento)
);
CREATE INDEX dmi_mov_fld_dest ON auri_owner_1.dmt_movimenti_folder USING btree ((
CASE
    WHEN ((id_uo_dest)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_dest)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_dest)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_dest)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_dest)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_dest)::character varying)::text)
END));
CREATE INDEX dmi_mov_fld_fld ON auri_owner_1.dmt_movimenti_folder USING btree (id_folder);
CREATE INDEX dmi_mov_fld_fld_inv ON auri_owner_1.dmt_movimenti_folder USING btree (folder_app_inviato_con);
CREATE INDEX dmi_mov_fld_prov ON auri_owner_1.dmt_movimenti_folder USING btree ((
CASE
    WHEN ((id_uo_prov)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_prov)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_prov)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_prov)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_prov)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_prov)::character varying)::text)
END));
CREATE INDEX dmi_mov_fld_ts_acc ON auri_owner_1.dmt_movimenti_folder USING btree (ts_acc);
CREATE INDEX dmi_mov_fld_ts_ass ON auri_owner_1.dmt_movimenti_folder USING btree (ts_decorr_assegnazione);
CREATE INDEX dmi_mov_fld_ts_invio ON auri_owner_1.dmt_movimenti_folder USING btree (ts_invio);
CREATE INDEX dmi_mov_fld_ts_pv ON auri_owner_1.dmt_movimenti_folder USING btree (ts_prima_presa_vis);
CREATE INDEX dmi_mov_fld_user_invio ON auri_owner_1.dmt_movimenti_folder USING btree (id_user_invio);




CREATE TABLE auri_owner_1.dmt_movimenti_folder_altri_attributi (
	id_movimento numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_movimenti_ud (
	id_invio numeric(38) NOT NULL,
	id_copia numeric(38) NOT NULL,
	id_user_dest numeric(38) NULL,
	id_uo_dest numeric(38) NULL,
	id_scrivania_dest numeric(38) NULL,
	ts_acc timestamp NULL,
	flg_acc_implicita int2 NULL,
	id_user_acc numeric(38) NULL,
	acc_a_nome_di numeric(38) NULL,
	id_folder_acc_con_ud numeric(38) NULL,
	ts_prima_presa_vis timestamp NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_mov_ud_c_acc_impl CHECK ((flg_acc_implicita = 1)),
	CONSTRAINT dmt_movimenti_ud_pkey PRIMARY KEY (id_invio, id_copia)
);
CREATE INDEX dmi_mov_ud_copia ON auri_owner_1.dmt_movimenti_ud USING btree (id_copia);
CREATE INDEX dmi_mov_ud_dest ON auri_owner_1.dmt_movimenti_ud USING btree ((
CASE
    WHEN ((id_uo_dest)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_dest)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_dest)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_dest)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_dest)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_dest)::character varying)::text)
END));
CREATE INDEX dmi_mov_ud_ts_acc ON auri_owner_1.dmt_movimenti_ud USING btree (ts_acc);
CREATE INDEX dmi_mov_ud_ts_pv ON auri_owner_1.dmt_movimenti_ud USING btree (ts_prima_presa_vis);



CREATE TABLE auri_owner_1.dmt_navigatore_sv (
	ci_nodo varchar(30) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_nodo varchar(150) NOT NULL,
	dett_nodo varchar(1000) NULL,
	ci_nodo_sup varchar(30) NULL,
	azione varchar(500) NULL,
	param_azione varchar(1000) NULL,
	criteri_avanzati varchar(4000) NULL,
	nro_ordine numeric(38) NULL,
	cod_funz varchar(250) NULL,
	flg_sel_multipla int2 NULL DEFAULT 1,
	ci_sezione_elim varchar(5) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	flg_contatore int2 NULL,
	CONSTRAINT dmt_nav_sv_c_selm CHECK ((flg_sel_multipla = 1)),
	CONSTRAINT dmt_navigatore_sv_ci_nodo_id_sp_aoo_key UNIQUE (ci_nodo, id_sp_aoo)
);
CREATE UNIQUE INDEX dmt_navigatore_sv_u_cin ON auri_owner_1.dmt_navigatore_sv USING btree (ci_nodo, id_sp_aoo);
CREATE UNIQUE INDEX dmt_navigatore_sv_u_nn ON auri_owner_1.dmt_navigatore_sv USING btree (btrim((auri_master.dmfn_upper(nome_nodo))::text), id_sp_aoo, ci_nodo_sup);



CREATE TABLE auri_owner_1.dmt_notifiche (
	id_notifica numeric(38) NOT NULL,
	ts_generazione timestamp NOT NULL,
	cod_tipo_notifica varchar(10) NOT NULL,
	cod_motivo varchar(10) NULL,
	id_richiesta numeric(38) NULL,
	id_ud numeric(38) NULL,
	id_folder numeric(38) NULL,
	id_process numeric(38) NULL,
	messaggio varchar(4000) NULL,
	id_invio_ud numeric(38) NULL,
	id_movimento_folder numeric(38) NULL,
	id_evento numeric(38) NULL,
	id_user_notifica numeric(38) NULL,
	notifica_a_nome_di numeric(38) NULL,
	id_user_prov numeric(38) NULL,
	id_uo_prov numeric(38) NULL,
	id_scrivania_prov numeric(38) NULL,
	ts_decorr_notifica timestamp NOT NULL,
	flg_mod_agg_acl varchar(2) NULL DEFAULT 'V'::character varying,
	cod_ruolo_dest varchar(10) NULL,
	livello_priorita numeric(38) NULL,
	tel_cell varchar[] NULL,
	fax varchar[] NULL,
	email varchar[] NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_notif_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_notifiche_pkey PRIMARY KEY (id_notifica)
);
CREATE INDEX dmi_notifiche_fld ON auri_owner_1.dmt_notifiche USING btree (id_folder);
CREATE INDEX dmi_notifiche_proc ON auri_owner_1.dmt_notifiche USING btree (id_process);
CREATE INDEX dmi_notifiche_prov ON auri_owner_1.dmt_notifiche USING btree ((
CASE
    WHEN ((id_uo_prov)::character varying IS NULL) THEN
    CASE
        WHEN ((id_scrivania_prov)::character varying IS NULL) THEN
        CASE
            WHEN ((id_user_prov)::character varying IS NULL) THEN NULL::text
            ELSE ('UT'::text || ((id_user_prov)::character varying)::text)
        END
        ELSE ('SV'::text || ((id_scrivania_prov)::character varying)::text)
    END
    ELSE ('UO'::text || ((id_uo_prov)::character varying)::text)
END));
CREATE INDEX dmi_notifiche_rich ON auri_owner_1.dmt_notifiche USING btree (id_richiesta);
CREATE INDEX dmi_notifiche_ts ON auri_owner_1.dmt_notifiche USING btree (ts_generazione);
CREATE INDEX dmi_notifiche_ts_dec ON auri_owner_1.dmt_notifiche USING btree (ts_decorr_notifica);
CREATE INDEX dmi_notifiche_ud ON auri_owner_1.dmt_notifiche USING btree (id_ud);
CREATE INDEX dmi_notifiche_user_not ON auri_owner_1.dmt_notifiche USING btree (id_user_notifica);



CREATE TABLE auri_owner_1.dmt_notifiche_altri_attributi (
	id_notifica numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_notifiche_destinatario (
	id_notifica int4 NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric(1) NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL
);



CREATE TABLE auri_owner_1.dmt_obj_rich_oper_massive (
	id_richiesta varchar(64) NOT NULL,
	tipo_obj varchar(3) NOT NULL,
	id_obj varchar(64) NOT NULL,
	esito_elab varchar(2) NULL,
	ts_ult_elab timestamp NULL,
	num_tentativi numeric(38) NOT NULL DEFAULT 0,
	err_context varchar(1000) NULL,
	err_code varchar(250) NULL,
	err_msg varchar(4000) NULL,
	xml_info text NULL,
	CONSTRAINT chk_obj_rich_oper_massive_0 CHECK (((esito_elab)::text = ANY ((ARRAY['OK'::character varying, 'KO'::character varying])::text[]))),
	CONSTRAINT chk_obj_rich_oper_massive_1 CHECK (((tipo_obj)::text = ANY ((ARRAY['D'::character varying, 'C'::character varying, 'F'::character varying, 'E'::character varying, 'P'::character varying])::text[]))),
	CONSTRAINT dmt_obj_rich_oper_massive_pkey PRIMARY KEY (id_richiesta, tipo_obj, id_obj)
);
CREATE INDEX dmi_obj_rich_oper_massive_1 ON auri_owner_1.dmt_obj_rich_oper_massive USING btree (tipo_obj, id_obj);



CREATE TABLE auri_owner_1.dmt_oper_doc_lotti_elab_mass (
	id_job numeric(38) NOT NULL,
	nro_posiz_doc numeric(38) NOT NULL,
	tipo_operazione varchar(100) NOT NULL,
	stato varchar(50) NOT NULL,
	ts_operazione timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	operation_info text NULL,
	nro_tentativi numeric(38) NOT NULL DEFAULT 1,
	CONSTRAINT dmt_op_doc_lotti_c_nro_pos CHECK ((nro_posiz_doc >= (0)::numeric)),
	CONSTRAINT dmt_op_doc_lotti_c_stato CHECK (((stato)::text = ANY ((ARRAY['in_corso'::character varying, 'in_errore'::character varying, 'completata'::character varying])::text[]))),
	CONSTRAINT dmt_oper_doc_lotti_elab_mass_pkey PRIMARY KEY (id_job, nro_posiz_doc, tipo_operazione)
);



CREATE TABLE auri_owner_1.dmt_operations_vs_container (
	id_operation numeric(38) NOT NULL,
	container_type varchar(30) NOT NULL,
	container_alias varchar(30) NOT NULL,
	cod_operation_type varchar(4) NOT NULL,
	ts_ins timestamp NOT NULL,
	id_doc numeric(38) NULL,
	nro_ver_doc int2 NULL,
	id_folder numeric(38) NULL,
	id_workspace numeric(38) NULL,
	flg_completed int2 NULL,
	flg_not_to_retry int2 NULL,
	ts_last_try timestamp NULL,
	"try#" numeric(38) NULL,
	err_msg varchar(1000) NULL,
	note text NULL,
	CONSTRAINT dmt_oper_vs_cont_c_compl CHECK ((flg_completed = 1)),
	CONSTRAINT dmt_oper_vs_cont_c_notretry CHECK ((flg_not_to_retry = 1)),
	CONSTRAINT dmt_oper_vs_cont_c_type CHECK (((cod_operation_type)::text = ANY ((ARRAY['ADD'::character varying, 'MOD'::character varying, 'DEL'::character varying, 'LDEL'::character varying, 'REC'::character varying, 'MURI'::character varying])::text[]))),
	CONSTRAINT dmt_operations_vs_container_pkey PRIMARY KEY (id_operation)
);



CREATE TABLE auri_owner_1.dmt_organigrammi (
	id_organigramma numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	dt_adozione timestamp NOT NULL,
	dt_dismissione timestamp NULL,
	note varchar(1000) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_organigrammi_pkey PRIMARY KEY (id_organigramma)
);




CREATE TABLE auri_owner_1.dmt_organigrammi_livelli_so (
	id_organigramma numeric(22) NULL,
	nro int4 NULL,
	cod_tipi varchar(500) NULL,
	flg_codice_numerico numeric NULL,
	flg_romano numeric NULL
);




CREATE TABLE auri_owner_1.dmt_pdv_x_conserv (
	id_pdv varchar(64) NOT NULL,
	id_entrypoint_vers varchar(64) NOT NULL,
	label_pdv varchar(500) NOT NULL,
	xml_pdv text NOT NULL,
	file_inf_pdv text NULL,
	dimensione_pdv numeric(38) NOT NULL,
	impronta_pdv varchar(500) NULL,
	algoritmo_impronta varchar(30) NULL,
	encoding_impronta varchar(10) NULL,
	stato varchar(100) NOT NULL DEFAULT 'creato'::character varying,
	ts_agg_stato timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ts_lock timestamp NULL,
	ts_last_try_trasm_pdv timestamp NULL,
	try_num_trasm_pdv numeric(38) NOT NULL DEFAULT 0,
	ts_invio_conserv timestamp NULL,
	ts_rec_ricevuta_trasm timestamp NULL,
	ricevuta_trasm text NULL,
	ts_last_try_rec_rdv timestamp NULL,
	err_warn_code_trasm_pdv varchar(30) NULL,
	err_warn_msg_trasm_pdv text NULL,
	try_num_rec_rdv numeric(38) NOT NULL DEFAULT 0,
	id_pdv_in_conserv varchar(250) NULL,
	ts_rec_rdv timestamp NULL,
	ts_gen_rdv timestamp NULL,
	xml_rdv text NULL,
	err_warn_code_rec_rdv varchar(100) NULL,
	err_warn_msg_rec_rdv text NULL,
	nro_item_conservati numeric(38) NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT dmt_pdv_vs_conserv_c_impenc CHECK (((encoding_impronta)::text = ANY (ARRAY[('hex'::character varying)::text, ('base64'::character varying)::text]))),
	CONSTRAINT dmt_pdv_vs_conserv_c_stato CHECK (((stato)::text = ANY (ARRAY[('creato'::character varying)::text, ('in_trasmissione'::character varying)::text, ('trasmesso'::character varying)::text, ('trasmissione_fallita'::character varying)::text, ('trasmissione_accettata'::character varying)::text, ('trasmissione_respinta'::character varying)::text, ('elaborato'::character varying)::text, ('accettato'::character varying)::text, ('rifiutato'::character varying)::text]))),
	CONSTRAINT dmt_pdv_x_conserv_pkey PRIMARY KEY (id_pdv)
);
CREATE INDEX dmt_pdv_x_cons_errcoderrdv ON auri_owner_1.dmt_pdv_x_conserv USING btree (err_warn_code_rec_rdv);
CREATE INDEX dmt_pdv_x_cons_errcodetpdv ON auri_owner_1.dmt_pdv_x_conserv USING btree (err_warn_code_trasm_pdv);
CREATE INDEX dmt_pdv_x_cons_idcons ON auri_owner_1.dmt_pdv_x_conserv USING btree (id_pdv_in_conserv);
CREATE INDEX dmt_pdv_x_conserv_dim ON auri_owner_1.dmt_pdv_x_conserv USING btree (dimensione_pdv);
CREATE INDEX dmt_pdv_x_conserv_ep ON auri_owner_1.dmt_pdv_x_conserv USING btree (id_entrypoint_vers);
CREATE INDEX dmt_pdv_x_conserv_impronta ON auri_owner_1.dmt_pdv_x_conserv USING btree (impronta_pdv, algoritmo_impronta, encoding_impronta);
CREATE INDEX dmt_pdv_x_conserv_label ON auri_owner_1.dmt_pdv_x_conserv USING btree (auri_master.dmfn_prepare_denom_x_match(label_pdv));
CREATE INDEX dmt_pdv_x_conserv_lock ON auri_owner_1.dmt_pdv_x_conserv USING btree (ts_lock);
CREATE INDEX dmt_pdv_x_conserv_try_rrdv ON auri_owner_1.dmt_pdv_x_conserv USING btree (ts_last_try_rec_rdv);
CREATE INDEX dmt_pdv_x_conserv_try_tpdv ON auri_owner_1.dmt_pdv_x_conserv USING btree (ts_last_try_trasm_pdv);
CREATE INDEX dmt_pdv_x_conserv_ts_agg_stato ON auri_owner_1.dmt_pdv_x_conserv USING btree (ts_agg_stato);
CREATE INDEX dmt_pdv_x_conserv_ts_inv ON auri_owner_1.dmt_pdv_x_conserv USING btree (ts_invio_conserv);




CREATE TABLE auri_owner_1.dmt_pdv_x_conserv_cronologia_agg_stato (
	id_pdv varchar(64) NULL,
	stato varchar(100) NULL,
	setting_time timestamp NULL,
	id_user int4 NULL,
	err_warn_code varchar(4000) NULL,
	err_warn_msg text NULL
);




CREATE TABLE auri_owner_1.dmt_piani_classif (
	id_piano_classif numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	id_classe_sp_aoo numeric(38) NULL,
	dt_adozione timestamp NOT NULL DEFAULT date_trunc('day'::text, LOCALTIMESTAMP),
	dt_dismissione timestamp NULL,
	note varchar(1000) NULL,
	prov_ci_piano varchar(30) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_piani_classif_pkey PRIMARY KEY (id_piano_classif)
);
CREATE INDEX dmi_piano_classif_classe ON auri_owner_1.dmt_piani_classif USING btree (id_classe_sp_aoo);
CREATE INDEX dmi_piano_classif_sp_aoo ON auri_owner_1.dmt_piani_classif USING btree (id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_piani_classif_livelli_titolario (
	id_piano_classif numeric(22) NULL,
	nro int4 NULL,
	denominazione varchar(30) NULL,
	flg_codice_numerico numeric NULL,
	flg_romano numeric NULL
);




CREATE TABLE auri_owner_1.dmt_privilegi (
	flg_tp_owner_priv varchar(2) NOT NULL,
	id_owner_priv numeric(38) NOT NULL,
	flg_tp_privilegio varchar(2) NOT NULL,
	ci_ogg_privilegio varchar(30) NOT NULL,
	flg_opz_priv_funz_std varchar(5) NULL,
	altre_opz_priv varchar[] NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_privilegi_pkey PRIMARY KEY (flg_tp_owner_priv, id_owner_priv, flg_tp_privilegio, ci_ogg_privilegio),
	CONSTRAINT dmt_privs_c_opz_std_priv CHECK (((flg_opz_priv_funz_std)::text = ANY (ARRAY[('FC'::character varying)::text, ('I'::character varying)::text, ('M'::character varying)::text]))),
	CONSTRAINT dmt_privs_c_tp_own CHECK (((flg_tp_owner_priv)::text = ANY (ARRAY[('SA'::character varying)::text, ('UT'::character varying)::text, ('UO'::character varying)::text, ('SV'::character varying)::text, ('PR'::character varying)::text, ('GP'::character varying)::text, ('RA'::character varying)::text]))),
	CONSTRAINT dmt_privs_c_tp_priv CHECK (((flg_tp_privilegio)::text = ANY (ARRAY[('F'::character varying)::text, ('TP'::character varying)::text, ('TD'::character varying)::text, ('TF'::character varying)::text, ('C'::character varying)::text, ('TR'::character varying)::text, ('MR'::character varying)::text])))
);
CREATE INDEX dmi_privs_u_own ON auri_owner_1.dmt_privilegi USING btree (flg_tp_owner_priv, id_owner_priv);
CREATE INDEX dmi_privs_u_what ON auri_owner_1.dmt_privilegi USING btree (flg_tp_privilegio, ci_ogg_privilegio);




CREATE TABLE auri_owner_1.dmt_proc_obj_types (
	id_proc_obj_type numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_applicazione varchar(30) NULL,
	cod_ist_applicazione varchar(30) NULL,
	nome_proc_obj_type varchar(250) NOT NULL,
	des_proc_obj_type varchar(1000) NULL,
	id_proc_obj_type_gen numeric(38) NULL,
	note varchar(1000) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_proc_obj_macrotype varchar(30) NULL,
	prov_ci_proc_obj_type varchar(30) NULL,
	tipo_geometria varchar(15) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_obj_types_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_proc_obj_types_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_obj_types_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_obj_types_c_tp_geom CHECK (((tipo_geometria)::text = ANY (ARRAY[('PUNTO'::character varying)::text, ('LINEA'::character varying)::text, ('POLIGONO'::character varying)::text]))),
	CONSTRAINT dmt_proc_obj_types_pkey PRIMARY KEY (id_proc_obj_type)
);
CREATE INDEX dmi_proc_obj_prov_ci_mty ON auri_owner_1.dmt_proc_obj_types USING btree (prov_ci_proc_obj_macrotype);
CREATE INDEX dmi_proc_obj_ty_prov_ci_ty ON auri_owner_1.dmt_proc_obj_types USING btree (prov_ci_proc_obj_type);
CREATE UNIQUE INDEX dmt_proc_obj_types_u_nome ON auri_owner_1.dmt_proc_obj_types USING btree (auri_master.dmfn_prepare_denom_x_match(nome_proc_obj_type), id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_proc_obj_types_altri_attributi (
	id_proc_obj_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_proc_obj_types_attr_add_x_proc_obj_del_tipo (
	id_proc_obj_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_posizione int4 NULL,
	flg_obblig numeric NULL,
	max_num_values int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	flg_x_timbro numeric NULL,
	flg_x_intitolazione numeric NULL,
	cod_tab_sez_gui varchar(30) NULL,
	des_tab_sez_gui varchar(250) NULL
);




CREATE TABLE auri_owner_1.dmt_proc_obj_types_info_storico (
	id_proc_obj_type numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_process_folder (
	id_process numeric(38) NOT NULL,
	id_folder numeric(38) NOT NULL,
	nro_posizione numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NULL,
	cod_stato_fld_in_proc varchar(10) NULL,
	note varchar(1000) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_fld_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_fld_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_process_folder_pkey PRIMARY KEY (id_process, id_folder)
);
CREATE INDEX dmi_proc_fld_fld ON auri_owner_1.dmt_process_folder USING btree (id_folder);
CREATE UNIQUE INDEX dmt_proc_fld_u_pos ON auri_owner_1.dmt_process_folder USING btree (id_process, nro_posizione);




CREATE TABLE auri_owner_1.dmt_process_folder_altri_attributi (
	id_process numeric(22) NULL,
	id_folder numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_process_history (
	id_evento numeric(38) NOT NULL,
	id_process numeric(38) NOT NULL,
	des_evento varchar(500) NOT NULL,
	cod_tipo_evento varchar(10) NOT NULL,
	ts_inizio timestamp NULL,
	ts_fine timestamp NULL,
	iniziato_a_nome_di numeric(38) NULL,
	iniziato_da numeric(38) NULL,
	completato_a_nome_di numeric(38) NULL,
	completato_da numeric(38) NULL,
	des_esito varchar(250) NULL,
	messaggio varchar(4000) NULL,
	log varchar(4000) NULL,
	instance_id_wf numeric(38) NULL,
	activity_name_wf varchar(200) NULL,
	activity_result_code_wf varchar(30) NULL,
	flg_not_to_exec_in_rit int2 NULL,
	altri_attributi auri_master.dmto_attr_values NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_pubblicato int2 NULL DEFAULT 1,
	inizio_da_evento numeric(38) NULL,
	fine_da_evento numeric(38) NULL,
	durata_max numeric(38) NULL,
	id_ud numeric(38) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_his_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_his_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_his_c_no_exec CHECK ((flg_not_to_exec_in_rit = 1)),
	CONSTRAINT dmt_proc_his_c_pubbl CHECK ((flg_pubblicato = 1)),
	CONSTRAINT dmt_process_history_pkey PRIMARY KEY (id_evento)
);
CREATE INDEX dmi_proc_hist_des ON auri_owner_1.dmt_process_history USING btree (auri_master.dmfn_prepare_denom_x_match(des_evento));
CREATE INDEX dmi_proc_hist_fine ON auri_owner_1.dmt_process_history USING btree (ts_fine);
CREATE INDEX dmi_proc_hist_inizio ON auri_owner_1.dmt_process_history USING btree (ts_inizio);
CREATE INDEX dmi_proc_hist_log ON auri_owner_1.dmt_process_history USING gin (to_tsvector('english'::regconfig, (log)::text));
CREATE INDEX dmi_proc_hist_msg ON auri_owner_1.dmt_process_history USING gin (to_tsvector('english'::regconfig, (messaggio)::text));
CREATE INDEX dmi_proc_hist_proc ON auri_owner_1.dmt_process_history USING btree (id_process);
CREATE INDEX dmi_proc_hist_tipo ON auri_owner_1.dmt_process_history USING btree (cod_tipo_evento, activity_name_wf);



CREATE TABLE auri_owner_1.dmt_process_history_altri_attributi (
	id_evento numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_process_objects (
	id_proc_obj_car numeric(38) NOT NULL,
	id_proc_obj_car_prec numeric(38) NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_evento numeric(38) NOT NULL,
	id_proc_obj_type numeric(38) NOT NULL,
	"label" varchar(1000) NOT NULL,
	ci_geolocalizzazione varchar(30) NULL,
	ts_lock timestamp NULL,
	id_user_lock numeric(38) NULL,
	flg_tipo_lock varchar(1) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	altri_attributi auri_master.dmto_attr_values NULL,
	prov_ci_proc_obj varchar(30) NULL,
	prov_ci_proc_obj_car varchar(30) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_obj_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_proc_obj_c_tipo_lock CHECK (((flg_tipo_lock)::text = ANY ((ARRAY['I'::character varying, 'E'::character varying])::text[]))),
	CONSTRAINT dmt_process_objects_pkey PRIMARY KEY (id_proc_obj_car)
);
CREATE INDEX dmi_proc_obj_car_prec ON auri_owner_1.dmt_process_objects USING btree (id_proc_obj_car_prec);
CREATE INDEX dmi_proc_obj_ci_geoloc ON auri_owner_1.dmt_process_objects USING btree (ci_geolocalizzazione);
CREATE INDEX dmi_proc_obj_evt_proc ON auri_owner_1.dmt_process_objects USING btree (id_evento);
CREATE INDEX dmi_proc_obj_label ON auri_owner_1.dmt_process_objects USING btree (auri_master.dmfn_prepare_denom_x_match(label), id_sp_aoo);
CREATE INDEX dmi_proc_obj_prov_ci_obj ON auri_owner_1.dmt_process_objects USING btree (prov_ci_proc_obj);
CREATE INDEX dmi_proc_obj_prov_ci_obj_car ON auri_owner_1.dmt_process_objects USING btree (prov_ci_proc_obj_car);
CREATE INDEX dmi_proc_obj_ts_ins ON auri_owner_1.dmt_process_objects USING btree (ts_ins);
CREATE INDEX dmi_proc_obj_ts_lu ON auri_owner_1.dmt_process_objects USING btree (ts_last_upd);
CREATE INDEX dmi_proc_obj_ty ON auri_owner_1.dmt_process_objects USING btree (id_proc_obj_type);




CREATE TABLE auri_owner_1.dmt_process_objects_altri_attributi (
	id_proc_obj_car numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_process_sogg_est (
	id_relazione numeric(38) NOT NULL,
	id_process numeric(38) NOT NULL,
	ruolo_sogg_in_proc varchar(150) NOT NULL,
	nro_occ_value numeric(38) NOT NULL,
	des_dett_rel varchar(250) NULL,
	id_sogg_rubrica numeric(38) NULL,
	denominazione varchar(1000) NULL,
	cognome varchar(150) NULL,
	nome varchar(100) NULL,
	flg_persona_fisica int2 NULL,
	cf varchar(16) NULL,
	piva varchar(11) NULL,
	dt_nascita timestamp NULL,
	flg_sex varchar(1) NULL,
	cod_istat_comune_nasc varchar(6) NULL,
	cod_istat_stato_nasc varchar(3) NULL,
	cod_istat_stato_citt varchar(3) NULL,
	ts_inizio_vld timestamp NULL,
	ts_fine_vld timestamp NULL,
	indirizzo auri_master.dmo_estremi_indirizzo NULL,
	cod_tipo_ind_in_rubr varchar(10) NULL,
	sede_legale_residenza auri_master.dmo_estremi_indirizzo NULL,
	rif_email auri_master.dmo_rif_email NULL,
	rif_tel varchar(500) NULL,
	rif_fax varchar(500) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	note varchar(500) NULL,
	prov_ci_anag varchar(200) NULL,
	comune_nasc varchar(150) NULL,
	rec_num numeric(38) NULL,
	rec_prov varchar(2) NULL,
	rec_date timestamp NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_sogg_est_c_fis CHECK ((flg_persona_fisica = ANY (ARRAY[0, 1]))),
	CONSTRAINT dmt_proc_sogg_est_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_sogg_est_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_sogg_est_c_sex CHECK (((flg_sex)::text = ANY (ARRAY[('F'::character varying)::text, ('M'::character varying)::text, ('I'::character varying)::text]))),
	CONSTRAINT dmt_process_sogg_est_pkey PRIMARY KEY (id_relazione)
);
CREATE INDEX dmi_proc_sogg_est_cf ON auri_owner_1.dmt_process_sogg_est USING btree (cf);
CREATE INDEX dmi_proc_sogg_est_d_nasc ON auri_owner_1.dmt_process_sogg_est USING btree (dt_nascita);
CREATE INDEX dmi_proc_sogg_est_denom ON auri_owner_1.dmt_process_sogg_est USING btree (auri_master.dmfn_prepare_denom_x_match(denominazione));
CREATE INDEX dmi_proc_sogg_est_piva ON auri_owner_1.dmt_process_sogg_est USING btree (btrim((piva)::text, '0'::text));
CREATE INDEX dmi_proc_sogg_est_proc ON auri_owner_1.dmt_process_sogg_est USING btree (id_process);
CREATE INDEX dmi_proc_sogg_est_ruolo ON auri_owner_1.dmt_process_sogg_est USING btree (auri_master.dmfn_prepare_denom_x_match(ruolo_sogg_in_proc));
CREATE INDEX dmi_proc_sogg_est_sogg ON auri_owner_1.dmt_process_sogg_est USING btree (id_sogg_rubrica);
CREATE INDEX dmi_sogg_est_proc_prov_ci ON auri_owner_1.dmt_process_sogg_est USING btree (prov_ci_anag);




CREATE TABLE auri_owner_1.dmt_process_sogg_est_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_process_sogg_int (
	id_relazione numeric(38) NOT NULL,
	id_process numeric(38) NOT NULL,
	ruolo_sogg_in_proc varchar(150) NOT NULL,
	nro_occ_value numeric(38) NOT NULL,
	des_dett_rel varchar(250) NULL,
	sogg_value auri_master.dmo_soggetto_interno NULL,
	ts_inizio_vld timestamp NULL,
	ts_fine_vld timestamp NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_sogg_int_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_sogg_int_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_process_sogg_int_pkey PRIMARY KEY (id_relazione)
);
CREATE INDEX dmi_proc_sogg_int_proc ON auri_owner_1.dmt_process_sogg_int USING btree (id_process);
CREATE INDEX dmi_proc_sogg_int_sogg ON auri_owner_1.dmt_process_sogg_int USING btree (((sogg_value).flg_tipo), ((sogg_value).id_in_anag_prov), ruolo_sogg_in_proc);




CREATE TABLE auri_owner_1.dmt_process_sogg_int_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_process_type_attributes (
	id_process_type numeric(38) NOT NULL,
	attr_name varchar(250) NOT NULL,
	nro_posizione numeric(38) NOT NULL,
	flg_obbligatorio int2 NULL,
	max_num_values numeric(38) NULL DEFAULT 1,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_x_timbro int2 NULL,
	flg_x_intitolazione int2 NULL,
	cod_tab_sez_gui varchar(30) NULL,
	des_tab_sez_gui varchar(250) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ty_attr_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_ty_attr_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_ty_attr_c_obbl CHECK ((flg_obbligatorio = 1)),
	CONSTRAINT dmt_proc_ty_attr_c_timbro CHECK ((flg_x_timbro = 1)),
	CONSTRAINT dmt_proc_ty_attr_c_title CHECK ((flg_x_intitolazione = 1)),
	CONSTRAINT dmt_process_type_attributes_pkey PRIMARY KEY (id_process_type, attr_name)
);
CREATE UNIQUE INDEX dmt_proc_ty_attr_u ON auri_owner_1.dmt_process_type_attributes USING btree (id_process_type, nro_posizione);



CREATE TABLE auri_owner_1.dmt_process_type_attributes_ctrl_presenza_ba_wf_act (
	id_process_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	id_sp_aoo int4 NULL,
	prov_ci_ty_flusso_wf varchar(100) NULL,
	flg_ba varchar(1) NULL,
	activity_name varchar(250) NULL,
	esiti varchar(1000) NULL,
	flg_warning numeric NULL,
	nro_min_occ int4 NULL
);



CREATE TABLE auri_owner_1.dmt_process_type_docs (
	id_process_type numeric(38) NOT NULL,
	id_doc_type numeric(38) NOT NULL,
	nro_posizione numeric(38) NULL,
	flg_acq_prod varchar(1) NULL,
	cod_ruolo_doc varchar(10) NULL,
	quadro_pratica varchar(150) NULL,
	flg_obbligatorio int2 NULL,
	max_num_docs numeric(38) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	ctrl_presenza_firma auri_master.dmo_ctrl_x_doc[] NULL,
	ctrl_protocollazione auri_master.dmo_ctrl_ba_activity[] NULL,
	ctrl_repertoriazione auri_master.dmo_ctrl_ba_activity[] NULL,
	modelli auri_master.dmo_ci_x_sp_aoo[] NULL,
	wf_act_x_access auri_master.dmo_doc_vs_wf_act[] NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ty_docs_c_ap CHECK (((flg_acq_prod)::text = ANY (ARRAY[('A'::character varying)::text, ('P'::character varying)::text]))),
	CONSTRAINT dmt_proc_ty_docs_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_ty_docs_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_ty_docs_c_obbl CHECK ((flg_obbligatorio = 1)),
	CONSTRAINT dmt_process_type_docs_pkey PRIMARY KEY (id_process_type, id_doc_type)
);




CREATE TABLE auri_owner_1.dmt_process_type_docs_ctrl_presenza_firma (
	id_process_type numeric(22) NULL,
	id_doc_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	prov_ci_ty_flusso_wf varchar(100) NULL,
	flg_ba varchar(1) NULL,
	activity_name varchar(250) NULL,
	esiti varchar(1000) NULL,
	flg_warning numeric NULL,
	nro_min_occ int4 NULL,
	flg_file numeric NULL,
	flg_firmato numeric NULL
);



CREATE TABLE auri_owner_1.dmt_process_type_docs_ctrl_protocollazione (
	id_process_type numeric(22) NULL,
	id_doc_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	prov_ci_ty_flusso_wf varchar(100) NULL,
	flg_ba varchar(1) NULL,
	activity_name varchar(250) NULL,
	esiti varchar(1000) NULL,
	flg_warning numeric NULL,
	nro_min_occ int4 NULL
);




CREATE TABLE auri_owner_1.dmt_process_type_docs_ctrl_repertoriazione (
	id_process_type numeric(22) NULL,
	id_doc_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	prov_ci_ty_flusso_wf varchar(100) NULL,
	flg_ba varchar(1) NULL,
	activity_name varchar(250) NULL,
	esiti varchar(1000) NULL,
	flg_warning numeric NULL,
	nro_min_occ int4 NULL
);




CREATE TABLE auri_owner_1.dmt_process_type_docs_modelli (
	id_process_type numeric(22) NULL,
	id_doc_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);




CREATE TABLE auri_owner_1.dmt_process_type_docs_wf_act_x_access (
	id_process_type numeric(22) NULL,
	id_doc_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	prov_ci_ty_flusso_wf varchar(100) NULL,
	activity_name varchar(250) NULL,
	flg_modalita varchar(1) NULL
);



CREATE TABLE auri_owner_1.dmt_process_type_events (
	id_process_type numeric(38) NOT NULL,
	id_event_type numeric(38) NOT NULL,
	nro_posizione numeric(38) NOT NULL,
	eventi_derivati auri_master.dmo_evento_derivato[] NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ty_evt_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_process_type_events_pkey PRIMARY KEY (id_process_type, id_event_type)
);
CREATE UNIQUE INDEX dmt_proc_ty_evt_type_u ON auri_owner_1.dmt_process_type_events USING btree (id_process_type, nro_posizione);



CREATE TABLE auri_owner_1.dmt_process_type_events_eventi_derivati (
	id_process_type numeric(22) NULL,
	id_event_type numeric(22) NULL,
	data_prov varchar(30) NULL,
	flg_tipo varchar(5) NULL,
	durata_max_sosp_interr int4 NULL,
	dett_esiti varchar(1000) NULL
);



CREATE TABLE auri_owner_1.dmt_process_type_obj (
	id_process_type numeric(38) NOT NULL,
	id_proc_obj_type numeric(38) NOT NULL,
	nro_posizione numeric(38) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_principale int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ty_obj_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_ty_obj_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_ty_obj_c_princip CHECK ((flg_principale = 1)),
	CONSTRAINT dmt_process_type_obj_pkey PRIMARY KEY (id_process_type, id_proc_obj_type)
);
CREATE INDEX dmi_process_type_obj_obj ON auri_owner_1.dmt_process_type_obj USING btree (id_proc_obj_type);




CREATE TABLE auri_owner_1.dmt_process_type_scad (
	id_process_type numeric(38) NOT NULL,
	des_scadenza varchar(250) NOT NULL,
	durata numeric(38) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	start_scad auri_master.dmo_start_end_scad[] NULL,
	end_scad auri_master.dmo_start_end_scad[] NULL,
	note varchar(500) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ty_scad_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_ty_scad_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_process_type_scad_pkey PRIMARY KEY (id_process_type)
);
CREATE UNIQUE INDEX dmt_proc_ty_scad_u ON auri_owner_1.dmt_process_type_scad USING btree (id_process_type, auri_master.dmfn_prepare_denom_x_match(des_scadenza));




CREATE TABLE auri_owner_1.dmt_process_type_sogg (
	id_process_type numeric(38) NOT NULL,
	ruolo_sogg_in_proc varchar(150) NOT NULL,
	des_ruolo varchar(250) NULL,
	nro_posizione numeric(38) NULL,
	limitazioni_tipo_sogg varchar[] NULL,
	flg_obbligatorio int2 NULL,
	max_num_occ numeric(38) NULL DEFAULT 1,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	ctrl_presenza_ba_wf_act auri_master.dmo_ctrl_ba_activity[] NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_principale int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ty_sogg_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_ty_sogg_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_ty_sogg_c_obbl CHECK ((flg_obbligatorio = 1)),
	CONSTRAINT dmt_proc_ty_sogg_c_princip CHECK ((flg_principale = 1)),
	CONSTRAINT dmt_process_type_sogg_pkey PRIMARY KEY (id_process_type)
);
CREATE UNIQUE INDEX dmt_proc_ty_sogg_u ON auri_owner_1.dmt_process_type_sogg USING btree (id_process_type, auri_master.dmfn_prepare_denom_x_match(ruolo_sogg_in_proc));




CREATE TABLE auri_owner_1.dmt_process_type_sogg_ctrl_presenza_ba_wf_act (
	id_process_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	prov_ci_ty_flusso_wf varchar(100) NULL,
	flg_ba varchar(1) NULL,
	activity_name varchar(250) NULL,
	esiti varchar(1000) NULL,
	flg_warning numeric NULL,
	nro_min_occ int4 NULL
);



CREATE TABLE auri_owner_1.dmt_process_types (
	id_process_type numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_applicazione varchar(30) NULL,
	cod_ist_applicazione varchar(30) NULL,
	nome_process_type varchar(100) NOT NULL,
	des_process_type varchar(1000) NULL,
	id_process_type_gen numeric(38) NULL,
	flg_ric_abil_x_vis int2 NULL,
	flg_ric_abil_x_tratt int2 NULL,
	flg_ric_abil_x_assegn int2 NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	gg_durata_prevista numeric(38) NULL,
	flg_tipo_iniziativa varchar(1) NULL,
	base_normativa varchar(4000) NULL,
	flg_sospendibile int2 NULL,
	nro_max_sospensioni numeric(38) NULL,
	flg_interrompibile int2 NULL,
	nro_max_gg_x_interr numeric(38) NULL,
	flg_parti_esterne int2 NULL,
	flg_silenzio_assenso int2 NULL,
	gg_silenzio_assenso numeric(38) NULL,
	flg_fasc_sottofasc varchar(1) NULL,
	id_classificazione numeric(38) NULL,
	id_folder_type numeric(38) NULL,
	flg_conserv_perm int2 NULL,
	periodo_conserv numeric(38) NULL,
	cod_supporto_conserv varchar(10) NULL,
	note varchar(1000) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_process_type varchar(30) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	template_nome_proc varchar(1000) NULL,
	template_code_proc varchar(1000) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ty_c_conserv CHECK ((flg_conserv_perm = 1)),
	CONSTRAINT dmt_proc_ty_c_est CHECK ((flg_parti_esterne = 1)),
	CONSTRAINT dmt_proc_ty_c_fs CHECK (((flg_fasc_sottofasc)::text = ANY (ARRAY[('F'::character varying)::text, ('S'::character varying)::text]))),
	CONSTRAINT dmt_proc_ty_c_interr CHECK ((flg_interrompibile = 1)),
	CONSTRAINT dmt_proc_ty_c_ric_abil_a CHECK ((flg_ric_abil_x_assegn = 1)),
	CONSTRAINT dmt_proc_ty_c_ric_abil_t CHECK ((flg_ric_abil_x_tratt = 1)),
	CONSTRAINT dmt_proc_ty_c_ric_abil_v CHECK ((flg_ric_abil_x_vis = 1)),
	CONSTRAINT dmt_proc_ty_c_sil_ass CHECK ((flg_silenzio_assenso = 1)),
	CONSTRAINT dmt_proc_types_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_proc_types_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_types_c_iniz CHECK (((flg_tipo_iniziativa)::text = ANY (ARRAY[('P'::character varying)::text, ('U'::character varying)::text]))),
	CONSTRAINT dmt_proc_types_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_types_c_sosp CHECK ((flg_sospendibile = 1)),
	CONSTRAINT dmt_process_types_pkey PRIMARY KEY (id_process_type)
);
CREATE INDEX dmi_proc_types_des ON auri_owner_1.dmt_process_types USING btree (auri_master.dmfn_prepare_denom_x_match(des_process_type));
CREATE UNIQUE INDEX dmt_proc_types_u_nome ON auri_owner_1.dmt_process_types USING btree (auri_master.dmfn_prepare_denom_x_match(nome_process_type), id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_process_types_altri_attributi (
	id_process_type numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_process_types_delegati_sost_resp (
	id_process_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);




CREATE TABLE auri_owner_1.dmt_process_types_flussi_wf_associati (
	id_process_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);



CREATE TABLE auri_owner_1.dmt_process_types_info_storico (
	id_process_type numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_process_types_modelli (
	id_process_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);




CREATE TABLE auri_owner_1.dmt_process_types_uo_resp (
	id_process_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);




CREATE TABLE auri_owner_1.dmt_process_types_user_resp (
	id_process_type numeric(22) NULL,
	id_sp_aoo int4 NULL,
	cardinalita int4 NULL,
	cid varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
);




CREATE TABLE auri_owner_1.dmt_process_ud (
	id_process numeric(38) NOT NULL,
	id_ud numeric(38) NOT NULL,
	nro_posizione numeric(38) NOT NULL,
	flg_acq_prod varchar(1) NULL,
	cod_ruolo_doc varchar(10) NULL,
	cod_stato_ud_in_proc varchar(10) NULL,
	note varchar(1000) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_ud_c_ap CHECK (((flg_acq_prod)::text = ANY (ARRAY[('A'::character varying)::text, ('P'::character varying)::text]))),
	CONSTRAINT dmt_proc_ud_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_ud_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_process_ud_pkey PRIMARY KEY (id_process, id_ud)
);
CREATE INDEX dmi_proc_ud_ud ON auri_owner_1.dmt_process_ud USING btree (id_ud);
CREATE UNIQUE INDEX dmt_proc_ud_u_pos ON auri_owner_1.dmt_process_ud USING btree (id_process, nro_posizione);




CREATE TABLE auri_owner_1.dmt_process_ud_altri_attributi (
	id_process numeric(22) NULL,
	id_ud numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_processes (
	id_process numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_process_type numeric(38) NOT NULL,
	prov_ci_ty_flusso_wf varchar(250) NULL,
	prov_ci_flusso_wf varchar(250) NULL,
	etichetta_proponente varchar(250) NULL,
	etichetta_proponente_rft varchar(250) NULL,
	oggetto varchar(4000) NULL,
	id_process_parent numeric(38) NULL,
	ts_avvio timestamp NULL,
	dt_decorrenza timestamp NULL,
	ts_fine timestamp NULL,
	dt_chiusura_termini timestamp NULL,
	flg_tipo_esito varchar(1) NULL,
	esito varchar(150) NULL,
	flg_stato varchar(1) NOT NULL,
	dt_inizio_sosp_interr timestamp NULL,
	dt_fine_interr timestamp NULL,
	gg_durata_sosp_concluse int8 NULL DEFAULT 0,
	id_copia_ud numeric(38) NULL,
	id_ud numeric(38) NULL,
	id_folder numeric(38) NULL,
	nro_sospensioni numeric(38) NULL DEFAULT 0,
	motivazioni varchar(4000) NULL,
	note varchar(4000) NULL,
	flg_ann int2 NULL,
	prov_ci_process varchar(250) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	userid_ins varchar(50) NULL,
	profilo_ins varchar(20) NULL,
	next_task varchar(4000) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_proc_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_proc_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_proc_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_proc_c_prof_ins CHECK (((profilo_ins)::text = ANY (ARRAY[('CITTADINO'::character varying)::text, ('AZIENDA-ENTE'::character varying)::text, ('DELEGATO'::character varying)::text, ('ISTRUTTORE'::character varying)::text]))),
	CONSTRAINT dmt_proc_c_stato CHECK (((flg_stato)::text = ANY (ARRAY[('B'::character varying)::text, ('A'::character varying)::text, ('I'::character varying)::text, ('S'::character varying)::text, ('C'::character varying)::text, ('E'::character varying)::text]))),
	CONSTRAINT dmt_proc_c_tipo_esito CHECK (((flg_tipo_esito)::text = ANY (ARRAY[('P'::character varying)::text, ('N'::character varying)::text, ('A'::character varying)::text]))),
	CONSTRAINT dmt_processes_pkey PRIMARY KEY (id_process)
);
CREATE INDEX dmi_proc_copia ON auri_owner_1.dmt_processes USING btree (id_copia_ud);
CREATE INDEX dmi_proc_dt_decorr ON auri_owner_1.dmt_processes USING btree (dt_decorrenza);
CREATE INDEX dmi_proc_etich_prop ON auri_owner_1.dmt_processes USING gin (to_tsvector('english'::regconfig, (etichetta_proponente_rft)::text));
CREATE INDEX dmi_proc_flusso ON auri_owner_1.dmt_processes USING btree (substr((prov_ci_ty_flusso_wf)::text, 1, ("position"((prov_ci_ty_flusso_wf)::text, ':'::text) - 1)), prov_ci_flusso_wf);
CREATE INDEX dmi_proc_folder ON auri_owner_1.dmt_processes USING btree (id_folder);
CREATE INDEX dmi_proc_id_flusso_wf ON auri_owner_1.dmt_processes USING btree (prov_ci_flusso_wf);
CREATE INDEX dmi_proc_ogg ON auri_owner_1.dmt_processes USING gin (to_tsvector('english'::regconfig, (oggetto)::text));
CREATE INDEX dmi_proc_parent ON auri_owner_1.dmt_processes USING btree (id_process_parent);
CREATE INDEX dmi_proc_prov_ci ON auri_owner_1.dmt_processes USING btree (auri_master.dmfn_prepare_denom_x_match(prov_ci_process));
CREATE INDEX dmi_proc_ts_avvio ON auri_owner_1.dmt_processes USING btree (ts_avvio);
CREATE INDEX dmi_proc_ts_fine ON auri_owner_1.dmt_processes USING btree (ts_fine);
CREATE INDEX dmi_proc_ts_ins ON auri_owner_1.dmt_processes USING btree (ts_ins);
CREATE INDEX dmi_proc_type ON auri_owner_1.dmt_processes USING btree (id_process_type);
CREATE INDEX dmi_proc_ud ON auri_owner_1.dmt_processes USING btree (id_ud);
CREATE INDEX dmi_proc_userid_ins ON auri_owner_1.dmt_processes USING btree (userid_ins, profilo_ins, flg_stato);
CREATE INDEX dmi_process_note ON auri_owner_1.dmt_processes USING gin (to_tsvector('english'::regconfig, (note)::text));




CREATE TABLE auri_owner_1.dmt_processes_altri_attributi (
	id_process numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_processes_info_storico (
	id_process numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_profili (
	id_profilo int4 NULL,
	id_sp_aoo int4 NULL,
	nome_profilo varchar(250) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	flg_acc_implicita numeric(1) NULL,
	liv_max_riservatezza numeric(2) NULL,
	flg_ered_abil_classif numeric(1) NULL,
	flg_ered_abil_mod_reg numeric(1) NULL,
	flg_ered_abil_tp_doc numeric(1) NULL,
	flg_ered_abil_tp_fasc numeric(1) NULL,
	flg_ered_abil_tp_reg numeric(1) NULL,
	flg_ered_abil_tp_proc numeric(1) NULL,
	id_acl_def_ud_create int4 NULL,
	id_acl_def_fld_creati int4 NULL,
	id_acl_def_wrks_creati int4 NULL,
	id_acl_to_append_ud_ass int4 NULL,
	id_acl_to_append_fld_ass int4 NULL,
	flg_visib_indip_acl varchar(3) NULL,
	flg_control_indip_acl varchar(3) NULL,
	flg_di_def_in_acl varchar(3) NULL,
	flg_visib_indip_user_abil varchar(4) NULL,
	flg_control_indip_user_abil varchar(4) NULL,
	copia_presso varchar(5) NULL,
	flg_amm numeric(1) NULL,
	flg_ann numeric(1) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL
);




CREATE TABLE auri_owner_1.dmt_profili_add_to_acl_ogg_ass (
	id_profilo int4 NOT NULL,
	accesso_a_livello_so numeric(1) NULL,
	opz_accesso varchar(100) NULL,
	flg_estendi_uo_sup numeric(1) NULL,
	opz_accesso_uo_sup varchar(100) NULL,
	flg_estendi_uo_incluse numeric(1) NULL,
	opz_accesso_uo_incl varchar(100) NULL
);




CREATE TABLE auri_owner_1.dmt_profili_add_to_acl_ogg_creati (
	id_profilo int4 NOT NULL,
	accesso_a_livello_so numeric(1) NULL,
	opz_accesso varchar(100) NULL,
	flg_estendi_uo_sup numeric(1) NULL,
	opz_accesso_uo_sup varchar(100) NULL,
	flg_estendi_uo_incluse numeric(1) NULL,
	opz_accesso_uo_incl varchar(100) NULL
);



CREATE TABLE auri_owner_1.dmt_profili_add_to_acl_ogg_prod (
	id_profilo int4 NOT NULL,
	accesso_a_livello_so numeric(1) NULL,
	opz_accesso varchar(100) NULL,
	flg_estendi_uo_sup numeric(1) NULL,
	opz_accesso_uo_sup varchar(100) NULL,
	flg_estendi_uo_incluse numeric(1) NULL,
	opz_accesso_uo_incl varchar(100) NULL
);




CREATE TABLE auri_owner_1.dmt_profili_gruppi_privilegi (
	id_profilo int4 NOT NULL,
	id_gruppi_privilegi int4 NOT NULL
);




CREATE TABLE auri_owner_1.dmt_province_regioni (
	targa_provincia varchar(2) NOT NULL,
	cod_istat_provincia varchar(3) NOT NULL,
	nome_provincia varchar(250) NOT NULL,
	cod_istat_regione varchar(2) NOT NULL,
	nome_regione varchar(250) NOT NULL,
	CONSTRAINT dmt_province_regioni_pkey PRIMARY KEY (targa_provincia)
);




CREATE TABLE auri_owner_1.dmt_quartieri (
	ci_quartiere varchar(30) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_quartiere varchar(250) NOT NULL,
	dettagli varchar(1000) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	note varchar(500) NULL,
	prov_ci_quartiere varchar(30) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_quartieri_pkey PRIMARY KEY (ci_quartiere)
);
CREATE INDEX dmi_quartieri_nome ON auri_owner_1.dmt_quartieri USING btree (auri_master.dmfn_prepare_denom_x_match(nome_quartiere));
CREATE INDEX dmi_quartieri_prov_ci ON auri_owner_1.dmt_quartieri USING btree (prov_ci_quartiere);




CREATE TABLE auri_owner_1.dmt_reg_emergenza (
	id_reg_emerg varchar(64) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	sigla_registro varchar(10) NOT NULL,
	anno_reg int2 NOT NULL,
	nro_progr_reg int4 NOT NULL,
	ts_reg timestamp NOT NULL,
	cod_uo_reg varchar(50) NULL,
	username_reg varchar(50) NULL,
	ts_arrivo timestamp NULL,
	flg_tipo_prov varchar(1) NULL,
	dt_doc_ricevuto timestamp NULL,
	estremi_reg_doc_ricevuto varchar(50) NULL,
	rif_doc_ricevuto varchar(50) NULL,
	anno_doc_ricevuto int2 NULL,
	mittente varchar(500) NULL,
	oggetto varchar(4000) NULL,
	prov_ci_reg_emerg varchar(30) NULL,
	ts_lock timestamp NULL,
	id_user_lock numeric(38) NULL,
	id_ud numeric(38) NULL,
	ts_ins timestamp NOT NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_reg_emerg_c_tp_prov CHECK (((flg_tipo_prov)::text = ANY ((ARRAY['E'::character varying, 'I'::character varying, 'U'::character varying])::text[]))),
	CONSTRAINT dmt_reg_emergenza_pkey PRIMARY KEY (id_reg_emerg)
);
CREATE INDEX dmi_reg_em__rif_ric ON auri_owner_1.dmt_reg_emergenza USING btree (auri_master.dmfn_prepare_denom_x_match(rif_doc_ricevuto), id_sp_aoo);
CREATE INDEX dmi_reg_em_dt_ric ON auri_owner_1.dmt_reg_emergenza USING btree (dt_doc_ricevuto, id_sp_aoo);
CREATE INDEX dmi_reg_em_estremi_reg_ric ON auri_owner_1.dmt_reg_emergenza USING btree (auri_master.dmfn_prepare_denom_x_match(estremi_reg_doc_ricevuto), COALESCE((anno_doc_ricevuto)::numeric, (auri_master.custom_to_char(dt_doc_ricevuto, 'RRRR'::character varying))::numeric), id_sp_aoo);
CREATE INDEX dmi_reg_emereg_ogg ON auri_owner_1.dmt_reg_emergenza USING gin (to_tsvector('english'::regconfig, (oggetto)::text));
CREATE INDEX dmi_reg_emergenza_mitt ON auri_owner_1.dmt_reg_emergenza USING btree (auri_master.dmfn_prepare_denom_x_match(mittente));
CREATE INDEX dmi_reg_emergenza_nro_reg ON auri_owner_1.dmt_reg_emergenza USING btree (nro_progr_reg);
CREATE INDEX dmi_reg_emergenza_ts_reg ON auri_owner_1.dmt_reg_emergenza USING btree (ts_reg);
CREATE INDEX dmi_reg_emergenza_ud ON auri_owner_1.dmt_reg_emergenza USING btree (id_ud);
CREATE UNIQUE INDEX dmt_reg_emergenza_u_segn ON auri_owner_1.dmt_reg_emergenza USING btree (sigla_registro, anno_reg, nro_progr_reg, id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_rel_col_diz (
	object_name varchar(30) NOT NULL,
	column_name varchar(30) NOT NULL,
	dictionary_entry varchar(30) NOT NULL,
	CONSTRAINT dmt_rel_col_diz_pkey PRIMARY KEY (object_name, column_name)
);




CREATE TABLE auri_owner_1.dmt_rel_nodi_repository (
	id_folder_app numeric(38) NOT NULL,
	id_folder_tramite numeric(38) NOT NULL,
	flg_tipo_nodo_figlio varchar(1) NOT NULL,
	id_nodo_figlio numeric(38) NOT NULL,
	CONSTRAINT dmt_rel_nodi_rep_c_tp CHECK (((flg_tipo_nodo_figlio)::text = ANY ((ARRAY['F'::character varying, 'U'::character varying])::text[]))),
	CONSTRAINT dmt_rel_nodi_repository_pkey PRIMARY KEY (id_folder_app, id_folder_tramite, id_nodo_figlio, flg_tipo_nodo_figlio)
);
CREATE INDEX dmi_rel_nodi_rep_figlio ON auri_owner_1.dmt_rel_nodi_repository USING btree (flg_tipo_nodo_figlio, id_nodo_figlio);
CREATE INDEX dmi_rel_nodi_rep_fld_app ON auri_owner_1.dmt_rel_nodi_repository USING btree (id_folder_app);
CREATE INDEX dmi_rel_nodi_rep_fld_tr ON auri_owner_1.dmt_rel_nodi_repository USING btree (id_folder_tramite);



CREATE TABLE auri_owner_1.dmt_rel_sogg_est_doc (
	id_relazione numeric(38) NOT NULL,
	id_doc numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NOT NULL,
	nro_posiz numeric(38) NOT NULL,
	des_dett_rel varchar(250) NULL,
	id_sogg_rubrica numeric(38) NULL,
	denominazione varchar(1000) NULL,
	denominazione_rft varchar(1000) NULL,
	cognome varchar(150) NULL,
	nome varchar(100) NULL,
	flg_persona_fisica int2 NULL,
	cf varchar(16) NULL,
	piva varchar(11) NULL,
	dt_nascita timestamp NULL,
	flg_sex varchar(1) NULL,
	cod_istat_comune_nasc varchar(6) NULL,
	comune_nasc varchar(150) NULL,
	cod_istat_stato_nasc varchar(3) NULL,
	cod_istat_stato_citt varchar(3) NULL,
	flg_epc int2 NULL,
	ts_spedizione_a_sogg timestamp NULL,
	cod_mezzo_trasm_a_sogg varchar(10) NULL,
	indirizzo_dest auri_master.dmo_estremi_indirizzo NULL,
	email_dest auri_master.dmo_rif_email NULL,
	fax_dest varchar(50) NULL,
	dt_raccomandata_a_sogg timestamp NULL,
	nro_raccomandata_a_sogg varchar(50) NULL,
	dt_notifica_a_sogg timestamp NULL,
	nro_notifica_a_sogg varchar(50) NULL,
	ts_ann timestamp NULL,
	motivi_ann varchar(500) NULL,
	id_user_ann numeric(38) NULL,
	id_uo_ann numeric(38) NULL,
	prov_ci_anag varchar(200) NULL,
	id_gruppo numeric(38) NULL,
	nome_gruppo varchar(150) NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rel_sogg_est_doc_c_epc CHECK ((flg_epc = 1)),
	CONSTRAINT dmt_rel_sogg_est_doc_c_fis CHECK ((flg_persona_fisica = ANY (ARRAY[0, 1]))),
	CONSTRAINT dmt_rel_sogg_est_doc_c_sex CHECK (((flg_sex)::text = ANY (ARRAY[('F'::character varying)::text, ('M'::character varying)::text, ('I'::character varying)::text]))),
	CONSTRAINT dmt_rel_sogg_est_doc_pkey PRIMARY KEY (id_relazione)
);
CREATE INDEX dmi_rel_sogg_est_doc_denom ON auri_owner_1.dmt_rel_sogg_est_doc USING btree (auri_master.dmfn_prepare_denom_x_match(denominazione));
CREATE INDEX dmi_rel_sogg_est_doc_doc ON auri_owner_1.dmt_rel_sogg_est_doc USING btree (id_doc);
CREATE INDEX dmi_rel_sogg_est_doc_sogg ON auri_owner_1.dmt_rel_sogg_est_doc USING btree (id_sogg_rubrica);
CREATE INDEX dmi_sogg_est_doc_cf ON auri_owner_1.dmt_rel_sogg_est_doc USING btree (cf);
CREATE INDEX dmi_sogg_est_doc_denom_2 ON auri_owner_1.dmt_rel_sogg_est_doc USING gin (to_tsvector('english'::regconfig, (denominazione_rft)::text));
CREATE INDEX dmi_sogg_est_doc_piva ON auri_owner_1.dmt_rel_sogg_est_doc USING btree (btrim((piva)::text, '0'::text));
CREATE INDEX dmi_sogg_est_doc_prov_ci ON auri_owner_1.dmt_rel_sogg_est_doc USING btree (prov_ci_anag);




CREATE TABLE auri_owner_1.dmt_rel_sogg_est_doc_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_rel_sogg_est_doc_email_dest (
	id_relazione numeric(22) NULL,
	indirizzo varchar(200) NULL,
	flg_casella_istituz numeric(1) NULL,
	flg_dich_ipa numeric(1) NULL,
	flg_pec numeric(1) NULL,
	gestore_pec varchar(500) NULL
);



CREATE TABLE auri_owner_1.dmt_rel_sogg_est_folder (
	id_relazione numeric(38) NOT NULL,
	id_folder numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NOT NULL,
	nro_posiz numeric(38) NOT NULL,
	des_dett_rel varchar(250) NULL,
	id_sogg_rubrica numeric(38) NULL,
	denominazione varchar(1000) NULL,
	cognome varchar(150) NULL,
	nome varchar(100) NULL,
	flg_persona_fisica int2 NULL,
	cf varchar(16) NULL,
	piva varchar(11) NULL,
	dt_nascita timestamp NULL,
	flg_sex varchar(1) NULL,
	cod_istat_comune_nasc varchar(6) NULL,
	comune_nasc varchar(150) NULL,
	cod_istat_stato_nasc varchar(3) NULL,
	cod_istat_stato_citt varchar(3) NULL,
	ts_spedizione_a_sogg timestamp NULL,
	cod_mezzo_trasm_a_sogg varchar(10) NULL,
	indirizzo_dest auri_master.dmo_estremi_indirizzo NULL,
	email_dest auri_master.dmo_rif_email NULL,
	fax_dest varchar(50) NULL,
	dt_raccomandata_a_sogg timestamp NULL,
	nro_raccomandata_a_sogg varchar(50) NULL,
	ts_ann timestamp NULL,
	motivi_ann varchar(500) NULL,
	id_user_ann numeric(38) NULL,
	id_uo_ann numeric(38) NULL,
	prov_ci_anag varchar(200) NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rel_sogg_est_fol_c_fis CHECK ((flg_persona_fisica = ANY (ARRAY[0, 1]))),
	CONSTRAINT dmt_rel_sogg_est_fol_c_sex CHECK (((flg_sex)::text = ANY (ARRAY[('F'::character varying)::text, ('M'::character varying)::text, ('I'::character varying)::text]))),
	CONSTRAINT dmt_rel_sogg_est_folder_pkey PRIMARY KEY (id_relazione)
);
CREATE INDEX dmi_rel_sogg_est_fld_denom ON auri_owner_1.dmt_rel_sogg_est_folder USING btree (auri_master.dmfn_prepare_denom_x_match(denominazione));
CREATE INDEX dmi_rel_sogg_est_fld_fld ON auri_owner_1.dmt_rel_sogg_est_folder USING btree (id_folder);
CREATE INDEX dmi_rel_sogg_est_fld_sogg ON auri_owner_1.dmt_rel_sogg_est_folder USING btree (id_sogg_rubrica);
CREATE INDEX dmi_sogg_est_fld_cf ON auri_owner_1.dmt_rel_sogg_est_folder USING btree (cf);
CREATE INDEX dmi_sogg_est_fld_piva ON auri_owner_1.dmt_rel_sogg_est_folder USING btree (btrim((piva)::text, '0'::text));
CREATE INDEX dmi_sogg_est_fld_prov_ci ON auri_owner_1.dmt_rel_sogg_est_folder USING btree (prov_ci_anag);




CREATE TABLE auri_owner_1.dmt_rel_sogg_est_folder_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_rel_sogg_int_doc (
	id_relazione numeric(38) NOT NULL,
	id_doc numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NOT NULL,
	nro_posiz numeric(38) NOT NULL,
	des_dett_rel varchar(250) NULL,
	flg_firma_apposta int2 NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rel_sogg_int_doc_pkey PRIMARY KEY (id_relazione),
	CONSTRAINT dmt_sogg_int_doc_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_sogg_int_doc_c_fir CHECK ((flg_firma_apposta = 1))
);
CREATE INDEX dmi_rel_sogg_int_doc_doc ON auri_owner_1.dmt_rel_sogg_int_doc USING btree (id_doc);




CREATE TABLE auri_owner_1.dmt_rel_sogg_int_doc_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_rel_sogg_int_doc_soggetto (
	id_relazione int4 NULL,
	id_doc int4 NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric(1) NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL
);




CREATE TABLE auri_owner_1.dmt_rel_sogg_int_folder (
	id_relazione numeric(38) NOT NULL,
	id_folder numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NOT NULL,
	nro_posiz numeric(38) NOT NULL,
	des_dett_rel varchar(250) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rel_sogg_int_folder_pkey PRIMARY KEY (id_relazione),
	CONSTRAINT dmt_sogg_int_fol_c_ann CHECK ((flg_ann = 1))
);
CREATE INDEX dmi_rel_sogg_int_fld_fld ON auri_owner_1.dmt_rel_sogg_int_folder USING btree (id_folder);




CREATE TABLE auri_owner_1.dmt_rel_sogg_int_folder_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_rel_sogg_int_folder_soggetto (
	id_relazione int4 NULL,
	id_folder int4 NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric(1) NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL
);



CREATE TABLE auri_owner_1.dmt_rel_sogg_int_workspace (
	id_relazione numeric(38) NOT NULL,
	id_workspace numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NOT NULL,
	nro_posiz numeric(38) NOT NULL,
	des_dett_rel varchar(250) NULL,
	soggetto auri_master.dmo_soggetto_interno NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rel_sogg_int_workspace_pkey PRIMARY KEY (id_relazione),
	CONSTRAINT dmt_sogg_int_wks_c_ann CHECK ((flg_ann = 1))
);
CREATE INDEX dmi_rel_sogg_int_wksp_sogg ON auri_owner_1.dmt_rel_sogg_int_workspace USING btree (((soggetto).flg_tipo), ((soggetto).id_in_anag_prov));
CREATE INDEX dmi_rel_sogg_int_wksp_wksp ON auri_owner_1.dmt_rel_sogg_int_workspace USING btree (id_workspace);




CREATE TABLE auri_owner_1.dmt_rel_sogg_int_workspace_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_rel_vs_fonti (
	id_relazione numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	id_fonte numeric(38) NOT NULL,
	cod_natura_rel varchar(10) NOT NULL,
	id_proc_obj_car numeric(38) NULL,
	prov_ci_proc_obj varchar(30) NULL,
	prov_ci_proc_obj_car varchar(30) NULL,
	nro_posiz numeric(38) NULL,
	des_dett_rel varchar(4000) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_rel_fonte varchar(200) NULL,
	note varchar(4000) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rel_fonti_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_rel_vs_fonti_pkey PRIMARY KEY (id_relazione)
);
CREATE UNIQUE INDEX dmi_rel_vs_fonti_uk ON auri_owner_1.dmt_rel_vs_fonti USING btree (id_fonte, id_proc_obj_car, prov_ci_proc_obj, prov_ci_proc_obj_car);
CREATE INDEX dmi_rev_vs_fonti_fonte ON auri_owner_1.dmt_rel_vs_fonti USING btree (id_fonte);
CREATE INDEX dmi_rev_vs_fonti_obj ON auri_owner_1.dmt_rel_vs_fonti USING btree (prov_ci_proc_obj);
CREATE INDEX dmi_rev_vs_fonti_obj_car ON auri_owner_1.dmt_rel_vs_fonti USING btree (prov_ci_proc_obj_car);
CREATE INDEX dmi_rev_vs_fonti_pobj_car ON auri_owner_1.dmt_rel_vs_fonti USING btree (id_proc_obj_car);




CREATE TABLE auri_owner_1.dmt_rel_vs_fonti_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_relazioni_folder (
	id_relazione numeric(38) NOT NULL,
	id_folder_1 numeric(38) NOT NULL,
	id_folder_2 numeric(38) NOT NULL,
	cod_categ_gen_rel varchar(10) NOT NULL,
	cod_ruolo_folder_1 varchar(10) NULL,
	cod_ruolo_folder_2 varchar(10) NULL,
	descrizione_rel varchar(500) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_relazioni_folder_pkey PRIMARY KEY (id_relazione)
);
CREATE INDEX dmi_rel_folder_folder1 ON auri_owner_1.dmt_relazioni_folder USING btree (id_folder_1);
CREATE INDEX dmi_rel_folder_folder2 ON auri_owner_1.dmt_relazioni_folder USING btree (id_folder_2);




CREATE TABLE auri_owner_1.dmt_relazioni_folder_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_relazioni_soggetti (
	id_relazione numeric(38) NOT NULL,
	id_sogg_rubr_1 numeric(38) NOT NULL,
	id_sogg_rubr_2 numeric(38) NOT NULL,
	cod_categ_gen_rel varchar(10) NOT NULL,
	cod_ruolo_sogg_1 varchar(10) NULL,
	cod_ruolo_sogg_2 varchar(10) NULL,
	descrizione_rel varchar(500) NULL,
	dt_inizio_rel timestamp NOT NULL,
	dt_fine_rel timestamp NULL,
	dt_avvicendamento timestamp NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_relazioni_soggetti_pkey PRIMARY KEY (id_relazione)
);
CREATE INDEX dmi_rel_sogg_sogg1 ON auri_owner_1.dmt_relazioni_soggetti USING btree (id_sogg_rubr_1);
CREATE INDEX dmi_rel_sogg_sogg2 ON auri_owner_1.dmt_relazioni_soggetti USING btree (id_sogg_rubr_2);




CREATE TABLE auri_owner_1.dmt_relazioni_soggetti_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_relazioni_ud (
	id_relazione numeric(38) NOT NULL,
	id_ud_1 numeric(38) NOT NULL,
	id_ud_2 numeric(38) NOT NULL,
	cod_categ_gen_rel varchar(10) NOT NULL,
	cod_ruolo_ud_1 varchar(10) NULL,
	cod_ruolo_ud_2 varchar(10) NULL,
	descrizione_rel varchar(500) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_relazioni_ud_pkey PRIMARY KEY (id_relazione)
);
CREATE INDEX dmi_rel_ud_ud1 ON auri_owner_1.dmt_relazioni_ud USING btree (id_ud_1);
CREATE INDEX dmi_rel_ud_ud2 ON auri_owner_1.dmt_relazioni_ud USING btree (id_ud_2);




CREATE TABLE auri_owner_1.dmt_relazioni_ud_altri_attributi (
	id_relazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_rich_notifiche (
	id_richiesta numeric(38) NOT NULL,
	flg_tipo_motivo varchar(1) NOT NULL,
	flg_mancanza_motivo int2 NULL,
	dett_motivo varchar(250) NOT NULL,
	entro_gg numeric(38) NULL,
	ts_richiesta timestamp NOT NULL,
	id_user_richiesta numeric(38) NOT NULL,
	id_ud numeric(38) NULL,
	id_folder numeric(38) NULL,
	id_process_type numeric(38) NULL,
	id_process numeric(38) NULL,
	messaggio varchar(1000) NULL,
	id_invio_ud numeric(38) NULL,
	id_movimento_folder numeric(38) NULL,
	id_notifica_orig numeric(38) NULL,
	id_evento numeric(38) NULL,
	tel_cell varchar[] NULL,
	fax varchar[] NULL,
	email varchar[] NULL,
	ts_evasione timestamp NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rich_notif_manc CHECK ((flg_mancanza_motivo = 1)),
	CONSTRAINT dmt_rich_notif_motivo CHECK (((flg_tipo_motivo)::text = ANY (ARRAY[('E'::character varying)::text, ('S'::character varying)::text]))),
	CONSTRAINT dmt_rich_notifiche_pkey PRIMARY KEY (id_richiesta)
);
CREATE INDEX dmi_rich_notif_evento ON auri_owner_1.dmt_rich_notifiche USING btree (id_evento);
CREATE INDEX dmi_rich_notif_invio_ud ON auri_owner_1.dmt_rich_notifiche USING btree (id_invio_ud);
CREATE INDEX dmi_rich_notif_mov_fld ON auri_owner_1.dmt_rich_notifiche USING btree (id_movimento_folder);




CREATE TABLE auri_owner_1.dmt_rich_notifiche_altri_attributi (
	id_richiesta numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_rich_notifiche_destinatari (
	id_richiesta numeric(22) NULL,
	flg_tipo varchar(3) NULL,
	ci_in_anag_prov varchar(150) NULL,
	flg_incl_sottouo numeric NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_uo_con_ruolo_proc varchar(150) NULL,
	vs_cod_tipo_uo varchar(10) NULL,
	rif_sv_con_ruolo_proc varchar(150) NULL
);




CREATE TABLE auri_owner_1.dmt_rich_notifiche_destinatario (
	id_notifica int4 NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric(1) NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL
);




CREATE TABLE auri_owner_1.dmt_rich_oper_massive (
	id_richiesta varchar(64) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	tipo_richiesta varchar(100) NOT NULL,
	tipo_obj_on varchar(10) NOT NULL,
	ts_richiesta timestamp NOT NULL,
	ts_decorrenza timestamp NOT NULL,
	motivo_richiesta varchar(4000) NULL,
	userid_richiesta varchar(250) NOT NULL,
	id_user_richiesta numeric(38) NULL,
	derivato_da_id_rich varchar(64) NULL,
	tipo_nuovo_ass varchar(2) NULL,
	id_nuovo_ass numeric(38) NULL,
	id_classifica_nuova numeric(38) NULL,
	id_folder_nuovo numeric(38) NULL,
	stato varchar(100) NOT NULL DEFAULT 'inserita'::character varying,
	ts_inizio_prima_elab timestamp NULL,
	ts_rich_evasa timestamp NULL,
	ts_inizio_ult_elab timestamp NULL,
	ts_fine_ult_elab timestamp NULL,
	num_elaborazioni int8 NOT NULL DEFAULT 0,
	liv_priorita int2 NOT NULL,
	note varchar(4000) NULL,
	tipo_evento_orig varchar(100) NULL,
	tipo_obj_evento_orig_su varchar(2) NULL,
	id_obj_evento_orig_su varchar(64) NULL,
	xml_info text NULL,
	CONSTRAINT chk_rich_oper_massive_0 CHECK (((stato)::text = ANY ((ARRAY['inserita'::character varying, 'in_elaborazione'::character varying, 'da_riprocessare'::character varying, 'eliminata'::character varying, 'elaborata_senza_errori'::character varying, 'elaborata_con_errori'::character varying])::text[]))),
	CONSTRAINT chk_rich_oper_massive_1 CHECK (((tipo_obj_on)::text = ANY ((ARRAY['D'::character varying, 'F'::character varying, 'DF'::character varying, 'E'::character varying, 'P'::character varying])::text[]))),
	CONSTRAINT chk_rich_oper_massive_2 CHECK (((tipo_nuovo_ass)::text = ANY ((ARRAY['UO'::character varying, 'SV'::character varying, 'UT'::character varying])::text[]))),
	CONSTRAINT chk_rich_oper_massive_3 CHECK ((liv_priorita = ANY (ARRAY[0, 1, 2, 3, 4]))),
	CONSTRAINT chk_rich_oper_massive_4 CHECK (((tipo_richiesta)::text = ANY ((ARRAY['spostamento'::character varying, 'cambio_classificazione_fascicolazione'::character varying, 'presa_in_carico'::character varying, 'chiusura'::character varying, 'modifica_permessi'::character varying])::text[]))),
	CONSTRAINT chk_rich_oper_massive_5 CHECK (((tipo_obj_evento_orig_su)::text = ANY ((ARRAY['UO'::character varying, 'SV'::character varying, 'UT'::character varying])::text[]))),
	CONSTRAINT dmt_rich_oper_massive_pkey PRIMARY KEY (id_richiesta)
);
CREATE INDEX dmi_rich_oper_massive_1 ON auri_owner_1.dmt_rich_oper_massive USING btree (ts_richiesta);
CREATE INDEX dmi_rich_oper_massive_10 ON auri_owner_1.dmt_rich_oper_massive USING btree (ts_rich_evasa);
CREATE INDEX dmi_rich_oper_massive_11 ON auri_owner_1.dmt_rich_oper_massive USING btree (ts_decorrenza);
CREATE INDEX dmi_rich_oper_massive_12 ON auri_owner_1.dmt_rich_oper_massive USING btree (derivato_da_id_rich);
CREATE INDEX dmi_rich_oper_massive_2 ON auri_owner_1.dmt_rich_oper_massive USING btree (userid_richiesta);
CREATE INDEX dmi_rich_oper_massive_3 ON auri_owner_1.dmt_rich_oper_massive USING btree (id_user_richiesta);
CREATE INDEX dmi_rich_oper_massive_4 ON auri_owner_1.dmt_rich_oper_massive USING btree (tipo_nuovo_ass, id_nuovo_ass);
CREATE INDEX dmi_rich_oper_massive_5 ON auri_owner_1.dmt_rich_oper_massive USING btree (stato);
CREATE INDEX dmi_rich_oper_massive_6 ON auri_owner_1.dmt_rich_oper_massive USING btree (ts_fine_ult_elab);
CREATE INDEX dmi_rich_oper_massive_7 ON auri_owner_1.dmt_rich_oper_massive USING btree (tipo_richiesta, tipo_obj_on);
CREATE INDEX dmi_rich_oper_massive_8 ON auri_owner_1.dmt_rich_oper_massive USING btree (id_classifica_nuova);
CREATE INDEX dmi_rich_oper_massive_9 ON auri_owner_1.dmt_rich_oper_massive USING btree (id_folder_nuovo);




CREATE TABLE auri_owner_1.dmt_rubrica_soggetti (
	id_sogg_rubrica numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	denominazione varchar(1000) NOT NULL,
	denominazione_rft varchar(1000) NOT NULL,
	flg_persona_fisica int2 NULL,
	cod_tipo_pg varchar(10) NULL,
	cod_tipo_dett_pg varchar(10) NULL,
	cod_cond_giuridica varchar(10) NULL,
	cf varchar(16) NULL,
	piva varchar(11) NULL,
	altre_forme_nome auri_master.dmo_denom_tipizzata[] NULL,
	indirizzi_luoghi auri_master.dmo_indirizzo[] NULL,
	dt_nascita timestamp NULL,
	dt_cessazione timestamp NULL,
	cod_caus_cessazione varchar(10) NULL,
	cognome varchar(150) NULL,
	nome varchar(100) NULL,
	flg_sex varchar(1) NULL,
	titolo varchar(100) NULL,
	cod_istat_comune_nasc varchar(6) NULL,
	comune_nasc varchar(150) NULL,
	cod_istat_stato_nasc varchar(3) NULL,
	cod_istat_stato_citt varchar(3) NULL,
	fax varchar[] NULL,
	cod_amm_ipa varchar(150) NULL,
	cod_aoo_ipa varchar(150) NULL,
	cod_indpa varchar(150) NULL,
	ts_last_sync_ipa timestamp NULL,
	flg_no_agg_auto_sincro_ipa int2 NULL,
	flg_certificato int2 NULL,
	ts_last_certif timestamp NULL,
	id_user_last_certif numeric(38) NULL,
	cod_rapido varchar(30) NULL,
	id_uo numeric(38) NULL,
	flg_visib_sottouo int2 NULL,
	flg_gest_sottouo int2 NULL,
	username varchar(50) NULL,
	"password" varchar(30) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_sogg varchar(200) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_rubr_sogg_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_rubr_sogg_c_certif CHECK ((flg_certificato = 1)),
	CONSTRAINT dmt_rubr_sogg_c_fis CHECK ((flg_persona_fisica = 1)),
	CONSTRAINT dmt_rubr_sogg_c_gest_suo CHECK ((flg_gest_sottouo = 1)),
	CONSTRAINT dmt_rubr_sogg_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_rubr_sogg_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_rubr_sogg_c_no_agg_ipa CHECK ((flg_no_agg_auto_sincro_ipa = 1)),
	CONSTRAINT dmt_rubr_sogg_c_sex CHECK (((flg_sex)::text = ANY (ARRAY[('F'::character varying)::text, ('M'::character varying)::text]))),
	CONSTRAINT dmt_rubr_sogg_c_vis_suo CHECK ((flg_visib_sottouo = 1)),
	CONSTRAINT dmt_rubrica_soggetti_pkey PRIMARY KEY (id_sogg_rubrica),
	CONSTRAINT dmt_rubrica_soggetti_username_key UNIQUE (username)
)
WITH (
	OIDS=TRUE
);
CREATE INDEX dmi_rs_spaoo ON auri_owner_1.dmt_rubrica_soggetti USING btree (id_sp_aoo);
CREATE INDEX dmi_rubr_sogg_amm_ipa ON auri_owner_1.dmt_rubrica_soggetti USING btree (auri_master.dmfn_upper(cod_amm_ipa));
CREATE INDEX dmi_rubr_sogg_aoo_ipa ON auri_owner_1.dmt_rubrica_soggetti USING btree (auri_master.dmfn_upper(cod_aoo_ipa));
CREATE INDEX dmi_rubr_sogg_cf ON auri_owner_1.dmt_rubrica_soggetti USING btree (cf);
CREATE INDEX dmi_rubr_sogg_cod_rapido ON auri_owner_1.dmt_rubrica_soggetti USING btree (auri_master.dmfn_upper(cod_rapido));
CREATE INDEX dmi_rubr_sogg_cogn ON auri_owner_1.dmt_rubrica_soggetti USING btree (replace((auri_master.dmfn_upper(cognome))::text, ' '::text, NULL::text));
CREATE INDEX dmi_rubr_sogg_com_nasc ON auri_owner_1.dmt_rubrica_soggetti USING btree (cod_istat_comune_nasc);
CREATE INDEX dmi_rubr_sogg_denom ON auri_owner_1.dmt_rubrica_soggetti USING btree (auri_master.dmfn_prepare_denom_x_match(denominazione));
CREATE INDEX dmi_rubr_sogg_dt_nasc ON auri_owner_1.dmt_rubrica_soggetti USING btree (dt_nascita);
CREATE INDEX dmi_rubr_sogg_indpa ON auri_owner_1.dmt_rubrica_soggetti USING btree (auri_master.dmfn_upper(cod_indpa));
CREATE INDEX dmi_rubr_sogg_nome ON auri_owner_1.dmt_rubrica_soggetti USING btree (replace((auri_master.dmfn_upper(nome))::text, ' '::text, NULL::text));
CREATE INDEX dmi_rubr_sogg_piva ON auri_owner_1.dmt_rubrica_soggetti USING btree (btrim((piva)::text, '0'::text));
CREATE INDEX dmi_rubr_sogg_prov_ci ON auri_owner_1.dmt_rubrica_soggetti USING btree (prov_ci_sogg);
CREATE INDEX dmi_rubr_sogg_tipo ON auri_owner_1.dmt_rubrica_soggetti USING btree (cod_tipo_pg, cod_tipo_dett_pg);
CREATE INDEX dmi_rubr_sogg_tp_dett_pg ON auri_owner_1.dmt_rubrica_soggetti USING btree (cod_tipo_dett_pg);
CREATE INDEX dmi_rubr_sogg_tp_pg ON auri_owner_1.dmt_rubrica_soggetti USING btree (cod_tipo_pg);
CREATE INDEX dmi_rubr_sogg_uo ON auri_owner_1.dmt_rubrica_soggetti USING btree (id_uo);
CREATE INDEX dmi_rubrica_sogg_denom_2 ON auri_owner_1.dmt_rubrica_soggetti USING gin (to_tsvector('english'::regconfig, (denominazione_rft)::text));




CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_altre_forme_nome (
	id_sogg_rubrica numeric(22) NULL,
	denominazione varchar(500) NULL,
	cardinalita int4 NULL,
	tipo varchar(150) NULL
)
WITH (
	OIDS=TRUE
);




CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_altri_attributi (
	id_sogg_rubrica numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_email (
	id_sogg_rubrica numeric(22) NULL,
	indirizzo varchar(200) NULL,
	flg_casella_istituz numeric NULL,
	flg_dich_ipa numeric NULL,
	flg_pec numeric NULL,
	gestore_pec varchar(500) NULL,
	cardinalita int4 NULL
)
WITH (
	OIDS=TRUE
);




CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_fax (
	id_sogg_rubrica numeric(22) NULL,
	nro_fax varchar(200) NULL
)
WITH (
	OIDS=TRUE
);



CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_indirizzi_luoghi (
	id_sogg_rubrica numeric(22) NULL,
	cod_istat_stato_estero varchar(3) NULL,
	nome_stato_estero varchar(250) NULL,
	cod_istat_comune varchar(6) NULL,
	nome_citta varchar(150) NULL,
	localita varchar(250) NULL,
	zona varchar(50) NULL,
	complemento_ind varchar(50) NULL,
	ci_toponomastico varchar(30) NULL,
	tipo_toponimo varchar(100) NULL,
	indirizzo varchar(250) NULL,
	civico varchar(10) NULL,
	esponente_civico varchar(10) NULL,
	interno varchar(30) NULL,
	scala varchar(10) NULL,
	piano int4 NULL,
	cap varchar(5) NULL,
	cod_tipo varchar(10) NULL,
	dettaglio_tipo varchar(250) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL
)
WITH (
	OIDS=TRUE
);



CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_info_storico (
	id_sogg_rubrica numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_old_passwords (
	id_sogg_rubrica numeric(22) NULL,
	"password" varchar(30) NULL,
	dt_fine_vld date NULL
);




CREATE TABLE auri_owner_1.dmt_rubrica_soggetti_tel (
	id_sogg_rubrica numeric(22) NULL,
	cardinalita int4 NULL,
	tipo varchar(150) NULL,
	nro_tel varchar(100) NULL
)
WITH (
	OIDS=TRUE
);




CREATE TABLE auri_owner_1.dmt_ruoli_amm (
	id_ruolo_amm numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	des_ruolo_amm varchar(150) NOT NULL,
	flg_lim_uo int2 NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	prov_ci_ruolo_amm varchar(30) NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_ruoli_amm_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_ruoli_amm_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_ruoli_amm_c_lim CHECK ((flg_lim_uo = 1)),
	CONSTRAINT dmt_ruoli_amm_pkey PRIMARY KEY (id_ruolo_amm)
);
CREATE UNIQUE INDEX dmt_ruoli_amm_u_des ON auri_owner_1.dmt_ruoli_amm USING btree (auri_master.dmfn_prepare_denom_x_match(des_ruolo_amm), id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_ruoli_amm_altri_attributi (
	id_ruolo_amm numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_ruoli_amm_info_storico (
	id_ruolo_amm numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_ruoli_amm_ruoli_amm_inclusi (
	id_ruolo_amm numeric(38) NULL,
	id_ruolo_amm_incluso numeric NULL
);




CREATE TABLE auri_owner_1.dmt_scadenziario (
	cod_tipo_ogg_scad_su varchar(3) NOT NULL,
	ci_ogg_scad_su varchar(30) NOT NULL,
	flg_tipo_scad varchar(1) NOT NULL,
	flg_scad_predefinita int2 NULL,
	des_scadenza varchar(250) NOT NULL,
	gg_durata_prevista numeric(38) NULL,
	ts_start_periodo timestamp NULL,
	ts_end_periodo_scad timestamp NULL,
	ts_ins timestamp NOT NULL,
	ts_last_upd timestamp NULL,
	CONSTRAINT dmt_scad_c_scad_predef CHECK ((flg_scad_predefinita = 1)),
	CONSTRAINT dmt_scad_c_tipo_scad CHECK (((flg_tipo_scad)::text = ANY ((ARRAY['P'::character varying, 'D'::character varying])::text[])))
);
CREATE INDEX dmi_scadenziario_des_scad ON auri_owner_1.dmt_scadenziario USING btree (auri_master.dmfn_prepare_denom_x_match(des_scadenza));
CREATE UNIQUE INDEX dmt_scadenziario_u ON auri_owner_1.dmt_scadenziario USING btree (cod_tipo_ogg_scad_su, ci_ogg_scad_su, auri_master.dmfn_prepare_denom_x_match(des_scadenza), ts_start_periodo, ts_end_periodo_scad);




CREATE TABLE auri_owner_1.dmt_scrivanie_virtuali (
	id_scrivania numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	intestazione varchar(250) NOT NULL,
	id_profilo numeric(38) NULL,
	gruppi_privilegi numeric[] NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	info_storico auri_master.dmo_info_storico[] NULL,
	prov_ci_scrivania varchar(30) NULL,
	id_acl_to_append_ud_ass numeric(38) NULL,
	id_acl_to_append_fld_ass numeric(38) NULL,
	flg_locked int2 NULL,
	intestazione_rft varchar(250) NOT NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_scriv_virt_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_scriv_virt_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_scrivanie_virtuali_pkey PRIMARY KEY (id_scrivania)
)
WITH (
	OIDS=TRUE
);
CREATE INDEX dmi_sv_intest ON auri_owner_1.dmt_scrivanie_virtuali USING gin (to_tsvector('english'::regconfig, (intestazione_rft)::text));
CREATE INDEX dmt_scrivanie_virtuali_u ON auri_owner_1.dmt_scrivanie_virtuali USING btree (auri_master.dmfn_prepare_denom_x_match(intestazione), id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_scrivanie_virtuali_altri_attributi (
	id_scrivania numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_scrivanie_virtuali_info_storico (
	id_scrivania numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_sezionali (
	id_sezionale int8 NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	cod_sezionale varchar(5) NOT NULL,
	anno numeric(38) NOT NULL,
	descrizione varchar(4000) NULL,
	range_da int8 NULL,
	range_a int8 NULL,
	flg_hidden numeric(38) NULL,
	flg_locked numeric(38) NULL,
	flg_ann numeric(38) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_sezionali_pkey PRIMARY KEY (id_sp_aoo, cod_sezionale, anno)
);




CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo (
	id_sp_aoo int4 NOT NULL,
	flg_sp_aoo varchar(1) NOT NULL,
	id_in_rubrica int4 NOT NULL,
	id_sogg_prod_app int4 NULL,
	id_classe_sp_aoo int4 NULL,
	poteri_funzioni_attiv varchar(4000) NULL,
	storia varchar(4000) NULL,
	id_uo_serv_prot_inf int4 NULL,
	id_user_resp_prot_inf int4 NULL,
	id_uo_conserv_digit int4 NULL,
	id_sogg_est_cecodi int4 NULL,
	id_user_resp_conserv int4 NULL,
	id_doc_piano_conserv int4 NULL,
	id_doc_manuale_gest_doc int4 NULL,
	id_doc_amb_tecnologico int4 NULL,
	id_doc_dps int4 NULL,
	dt_approv_dps date NULL,
	id_doc_rich_accr_ipa int4 NULL,
	id_doc_titolario int4 NULL,
	id_doc_massimario int4 NULL,
	id_user_resp_si int4 NULL,
	id_user_resp_sicurezza int4 NULL,
	id_user_resp_privacy int4 NULL,
	flg_ann numeric(1) NULL,
	motivi_ann varchar(500) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	CONSTRAINT dmt_soggetti_prod_aoo_pkey PRIMARY KEY (id_sp_aoo),
	CONSTRAINT dmt_sp_aoo_c_ann CHECK ((flg_ann = (1)::numeric)),
	CONSTRAINT dmt_sp_aoo_c_sa CHECK (((flg_sp_aoo)::text = ANY (ARRAY[('S'::character varying)::text, ('A'::character varying)::text])))
);
CREATE INDEX dmi_sp_aoo_classe ON auri_owner_1.dmt_soggetti_prod_aoo USING btree (id_classe_sp_aoo);
CREATE UNIQUE INDEX dmt_soggetti_prod_aoo_p ON auri_owner_1.dmt_soggetti_prod_aoo USING btree (id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_altri_attributi (
	id_sp_aoo numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_delegati_resp_conserv (
	id_sp_aoo numeric(22) NULL,
	id_user int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	natura_motivi varchar(500) NULL
);




CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_delegati_resp_privacy (
	id_sp_aoo numeric(22) NULL,
	id_user int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	natura_motivi varchar(500) NULL
);




CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_delegati_resp_prot_inf (
	id_sp_aoo int4 NOT NULL,
	id_user int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	natura_motivi varchar(500) NULL
);
CREATE INDEX dmt_soggetti_prod_aoo_delegati_resp_prot_inf_idx ON auri_owner_1.dmt_soggetti_prod_aoo_delegati_resp_prot_inf USING btree (id_sp_aoo);



CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_delegati_resp_si (
	id_sp_aoo numeric(22) NULL,
	id_user int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	natura_motivi varchar(500) NULL
);




CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_delegati_resp_sicurezza (
	id_sp_aoo numeric(22) NULL,
	id_user int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	natura_motivi varchar(500) NULL
);




CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_info_storico (
	id_sp_aoo numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_soggetti_prod_aoo_livelli_so (
	id_sp_aoo numeric NOT NULL,
	nro int4 NULL,
	cod_tipi varchar(500) NULL,
	flg_codice_numerico numeric(1) NULL,
	flg_romano numeric(1) NULL
);
CREATE INDEX dmt_soggetti_prod_aoo_livelli_so_idx ON auri_owner_1.dmt_soggetti_prod_aoo_livelli_so USING btree (id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_stati_nazionali (
	cod_istat_stato varchar(3) NOT NULL,
	nome_stato varchar(150) NOT NULL,
	cf_stato varchar(4) NULL,
	des_cittadinanza varchar(150) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	prov_ci_stato varchar(10) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_stati_nazionali_pkey PRIMARY KEY (cod_istat_stato)
);
CREATE UNIQUE INDEX dmt_stati_nazionali_u_citt ON auri_owner_1.dmt_stati_nazionali USING btree (auri_master.dmfn_prepare_denom_x_match(des_cittadinanza));
CREATE UNIQUE INDEX dmt_stati_nazionali_u_nome ON auri_owner_1.dmt_stati_nazionali USING btree (auri_master.dmfn_prepare_denom_x_match(nome_stato));




CREATE TABLE auri_owner_1.dmt_struttura_org (
	id_uo numeric(38) NOT NULL,
	id_organigramma numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	denominazione varchar(250) NOT NULL,
	livello_gerarchico numeric(38) NOT NULL,
	cod_tipo varchar(10) NULL,
	nro_livello varchar(10) NOT NULL,
	nri_livelli varchar(250) NOT NULL,
	nro_posizione int8 NULL,
	dt_istituzione timestamp NOT NULL,
	dt_soppressione timestamp NULL,
	id_uo_sup numeric(38) NULL,
	competenze varchar(4000) NULL,
	storia varchar(4000) NULL,
	id_uo_in_rubrica numeric(38) NULL,
	flg_dich_ipa int2 NULL,
	dt_dich_ipa timestamp NULL,
	id_profilo numeric(38) NULL,
	prov_ci_uo varchar(30) NULL,
	flg_locked int2 NULL,
	denominazione_rft varchar(250) NOT NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	des_estesa varchar(4000) NULL,
	CONSTRAINT dmt_so_c_ipa CHECK ((flg_dich_ipa = 1)),
	CONSTRAINT dmt_so_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_struttura_org_pkey PRIMARY KEY (id_uo)
)
WITH (
	OIDS=TRUE
);
CREATE INDEX dmi_organigrammi_sp_aoo ON auri_owner_1.dmt_struttura_org USING btree (id_sp_aoo);
CREATE INDEX dmi_so_denom ON auri_owner_1.dmt_struttura_org USING gin (to_tsvector('english'::regconfig, (denominazione)::text));
CREATE INDEX dmi_so_denom_2 ON auri_owner_1.dmt_struttura_org USING btree (auri_master.dmfn_prepare_denom_x_match(denominazione));
CREATE INDEX dmi_so_denom_ctx ON auri_owner_1.dmt_struttura_org USING gin (to_tsvector('english'::regconfig, (denominazione_rft)::text));
CREATE INDEX dmi_so_denom_estesa ON auri_owner_1.dmt_struttura_org USING btree (auri_master.dmfn_prepare_denom_x_match(des_estesa));
CREATE INDEX dmi_so_liv ON auri_owner_1.dmt_struttura_org USING btree (livello_gerarchico);
CREATE INDEX dmi_so_nri_liv ON auri_owner_1.dmt_struttura_org USING btree (nri_livelli);
CREATE INDEX dmi_so_nro_liv ON auri_owner_1.dmt_struttura_org USING btree (nro_livello);
CREATE INDEX dmi_so_org ON auri_owner_1.dmt_struttura_org USING btree (id_organigramma);
CREATE INDEX dmi_so_prov_ci_uo ON auri_owner_1.dmt_struttura_org USING btree (prov_ci_uo);
CREATE INDEX dmi_so_uo_sup ON auri_owner_1.dmt_struttura_org USING btree (id_uo_sup);




CREATE TABLE auri_owner_1.dmt_struttura_org_altri_attributi (
	id_uo int4 NOT NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL,
	CONSTRAINT dmt_struttura_org_altri_attributi_fk FOREIGN KEY (id_uo) REFERENCES dmt_struttura_org(id_uo)
);




CREATE TABLE auri_owner_1.dmt_struttura_org_gruppi_privilegi (
	id_uo int4 NOT NULL,
	gruppi_privilegi int4 NOT NULL
);




CREATE TABLE auri_owner_1.dmt_struttura_org_info_storico (
	id_uo numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_strutture_org_offline (
	id_uo numeric(38) NOT NULL,
	id_organigramma numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	denominazione varchar(250) NOT NULL,
	livello_gerarchico numeric(38) NOT NULL,
	cod_tipo varchar(10) NULL,
	nro_livello varchar(10) NOT NULL,
	nri_livelli varchar(250) NOT NULL,
	nro_posizione int8 NULL,
	dt_istituzione timestamp NOT NULL,
	dt_soppressione timestamp NULL,
	id_uo_sup numeric(38) NULL,
	competenze varchar(4000) NULL,
	storia varchar(4000) NULL,
	id_uo_in_rubrica numeric(38) NULL,
	flg_dich_ipa int2 NULL,
	dt_dich_ipa timestamp NULL,
	id_profilo numeric(38) NULL,
	gruppi_privilegi numeric[] NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	info_storico auri_master.dmo_info_storico[] NULL,
	prov_ci_uo varchar(30) NULL,
	flg_locked int2 NULL,
	denominazione_rft varchar(250) NOT NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	des_estesa varchar(4000) NULL,
	CONSTRAINT dmt_so_off_c_ipa CHECK ((flg_dich_ipa = 1)),
	CONSTRAINT dmt_so_off_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_strutture_org_offline_pkey PRIMARY KEY (id_uo, id_organigramma)
);
CREATE INDEX dmi_so_off_denom ON auri_owner_1.dmt_strutture_org_offline USING gin (to_tsvector('english'::regconfig, (denominazione)::text));
CREATE INDEX dmi_so_off_denom_2 ON auri_owner_1.dmt_strutture_org_offline USING btree (auri_master.dmfn_prepare_denom_x_match(denominazione));
CREATE INDEX dmi_so_off_denom_ctx ON auri_owner_1.dmt_strutture_org_offline USING gin (to_tsvector('english'::regconfig, (denominazione_rft)::text));
CREATE INDEX dmi_so_off_denom_estesa ON auri_owner_1.dmt_strutture_org_offline USING btree (auri_master.dmfn_prepare_denom_x_match(des_estesa));
CREATE INDEX dmi_so_off_liv ON auri_owner_1.dmt_strutture_org_offline USING btree (livello_gerarchico);
CREATE INDEX dmi_so_off_nri_liv ON auri_owner_1.dmt_strutture_org_offline USING btree (nri_livelli);
CREATE INDEX dmi_so_off_nro_liv ON auri_owner_1.dmt_strutture_org_offline USING btree (nro_livello);
CREATE INDEX dmi_so_off_org ON auri_owner_1.dmt_strutture_org_offline USING btree (id_organigramma);
CREATE INDEX dmi_so_off_prov_ci_uo ON auri_owner_1.dmt_strutture_org_offline USING btree (prov_ci_uo);
CREATE INDEX dmi_so_off_uo_sup ON auri_owner_1.dmt_strutture_org_offline USING btree (id_uo_sup);




CREATE TABLE auri_owner_1.dmt_strutture_org_offline_altri_attributi (
	id_uo numeric(22) NULL,
	id_organigramma numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_strutture_org_offline_info_storico (
	id_uo numeric(22) NULL,
	id_organigramma numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_tipi_cont_x_import (
	tipo_contenuto varchar(250) NOT NULL,
	des_tipo_contenuto varchar(1000) NULL,
	cod_func varchar(100) NULL,
	CONSTRAINT dmt_tipi_cont_x_import_pkey PRIMARY KEY (tipo_contenuto)
);




CREATE TABLE auri_owner_1.dmt_tipi_registrazione (
	id_tp_registrazione numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	cod_categoria varchar(10) NOT NULL,
	sigla varchar(30) NULL,
	descrizione varchar(150) NOT NULL,
	des_gruppo_reg varchar(150) NOT NULL,
	restrizioni_verso_reg varchar(2) NULL,
	flg_gest_interna int2 NULL,
	dt_inizio_gest_interna timestamp NULL,
	flg_ric_abil_x_vis int2 NULL,
	flg_ric_abil_x_tratt int2 NULL,
	flg_ric_abil_x_assegn int2 NULL,
	flg_ric_abil_x_firma int2 NULL,
	flg_sel_da_uo_mitt int2 NULL,
	nro_anni_rinnovo_num numeric(38) NULL,
	anno_inizio_num int2 NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	flg_stato_registro varchar(1) NOT NULL DEFAULT 'A'::character varying,
	flg_ammessi_buchi int2 NULL,
	ts_ini_stato_registro timestamp NULL,
	lista_tipi_doc_ammessi numeric[] NULL,
	lista_tipi_doc_esclusi numeric[] NULL,
	prov_ci_tp_reg varchar(30) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_tipi_registrazione_id_sp_aoo_cod_categoria_sigla_key UNIQUE (id_sp_aoo, cod_categoria, sigla),
	CONSTRAINT dmt_tipi_registrazione_pkey PRIMARY KEY (id_tp_registrazione),
	CONSTRAINT dmt_tp_reg_c_ammbuchi_int CHECK ((flg_ammessi_buchi = 1)),
	CONSTRAINT dmt_tp_reg_c_gest_int CHECK ((flg_gest_interna = 1)),
	CONSTRAINT dmt_tp_reg_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_tp_reg_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_tp_reg_c_ric_abil_a CHECK ((flg_ric_abil_x_assegn = 1)),
	CONSTRAINT dmt_tp_reg_c_ric_abil_f CHECK ((flg_ric_abil_x_firma = 1)),
	CONSTRAINT dmt_tp_reg_c_ric_abil_t CHECK ((flg_ric_abil_x_tratt = 1)),
	CONSTRAINT dmt_tp_reg_c_ric_abil_v CHECK ((flg_ric_abil_x_vis = 1)),
	CONSTRAINT dmt_tp_reg_c_stato CHECK (((flg_stato_registro)::text = ANY (ARRAY[('A'::character varying)::text, ('C'::character varying)::text]))),
	CONSTRAINT dmt_tp_reg_c_uo_mitt_f CHECK ((flg_sel_da_uo_mitt = 1))
);
CREATE UNIQUE INDEX dmt_tipi_registrazione_u ON auri_owner_1.dmt_tipi_registrazione USING btree (id_sp_aoo, cod_categoria, sigla);




CREATE TABLE auri_owner_1.dmt_tipi_registrazione_altri_attributi (
	id_tp_registrazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_tipi_registrazione_attr_add_x_doc_reg_a_tp (
	id_tp_registrazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_posizione int4 NULL,
	flg_obblig numeric NULL,
	max_num_values int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	flg_x_timbro numeric NULL,
	flg_x_intitolazione numeric NULL,
	cod_tab_sez_gui varchar(30) NULL,
	des_tab_sez_gui varchar(250) NULL
);




CREATE TABLE auri_owner_1.dmt_tipi_registrazione_info_storico (
	id_tp_registrazione numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_titolario (
	id_classificazione numeric(38) NOT NULL,
	id_piano_classif numeric(38) NOT NULL,
	des_classificazione varchar(250) NOT NULL,
	livello_gerarchico numeric(38) NOT NULL,
	nro_livello varchar(10) NOT NULL,
	nri_livelli varchar(250) NOT NULL,
	nro_posizione numeric(38) NULL,
	dt_inizio_vld timestamp NOT NULL,
	dt_fine_vld timestamp NULL,
	id_classif_sup numeric(38) NULL,
	flg_ric_abil_x_vis int2 NULL,
	flg_ric_abil_x_tratt int2 NULL,
	flg_ric_abil_x_assegn int2 NULL,
	flg_ric_abil_x_firma int2 NULL,
	flg_ric_abil_x_apert_fasc int2 NULL,
	flg_conserv_perm int2 NULL,
	periodo_conserv numeric(38) NULL,
	cod_supporto_conserv varchar(10) NULL,
	flg_folder_anno int2 NULL,
	parole_chiave varchar(4000) NULL,
	flg_classif_inibita int2 NULL,
	prov_ci_classificazione varchar(30) NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	des_estesa varchar(4000) NULL,
	id_indice_egr numeric(38) NULL,
	CONSTRAINT dmt_classif_c_inib CHECK ((flg_classif_inibita = 1)),
	CONSTRAINT dmt_classif_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_tit_c_conserv CHECK ((flg_conserv_perm = 1)),
	CONSTRAINT dmt_tit_c_fld_anno CHECK ((flg_folder_anno = 1)),
	CONSTRAINT dmt_tit_c_ric_abil_a CHECK ((flg_ric_abil_x_assegn = 1)),
	CONSTRAINT dmt_tit_c_ric_abil_af CHECK ((flg_ric_abil_x_apert_fasc = 1)),
	CONSTRAINT dmt_tit_c_ric_abil_f CHECK ((flg_ric_abil_x_firma = 1)),
	CONSTRAINT dmt_tit_c_ric_abil_t CHECK ((flg_ric_abil_x_tratt = 1)),
	CONSTRAINT dmt_tit_c_ric_abil_v CHECK ((flg_ric_abil_x_vis = 1)),
	CONSTRAINT dmt_titolario_pkey PRIMARY KEY (id_classificazione)
);
CREATE INDEX dmi_tit_classif_sup ON auri_owner_1.dmt_titolario USING btree (id_classif_sup);
CREATE INDEX dmi_tit_des ON auri_owner_1.dmt_titolario USING gin (to_tsvector('english'::regconfig, (des_classificazione)::text));
CREATE INDEX dmi_tit_des_2 ON auri_owner_1.dmt_titolario USING btree (auri_master.dmfn_prepare_denom_x_match(des_classificazione));
CREATE INDEX dmi_tit_keys ON auri_owner_1.dmt_titolario USING gin (to_tsvector('english'::regconfig, (parole_chiave)::text));
CREATE INDEX dmi_tit_liv ON auri_owner_1.dmt_titolario USING btree (livello_gerarchico);
CREATE INDEX dmi_tit_nri_liv ON auri_owner_1.dmt_titolario USING btree (nri_livelli);
CREATE INDEX dmi_tit_nro_liv ON auri_owner_1.dmt_titolario USING btree (nro_livello);
CREATE INDEX dmi_tit_piano ON auri_owner_1.dmt_titolario USING btree (id_piano_classif);
CREATE INDEX dmi_tit_prov_ci_classif ON auri_owner_1.dmt_titolario USING btree (prov_ci_classificazione);
CREATE INDEX dmi_titolario_des_estesa ON auri_owner_1.dmt_titolario USING btree (auri_master.dmfn_prepare_denom_x_match(des_estesa));




CREATE TABLE auri_owner_1.dmt_titolario_altri_attributi (
	id_classificazione numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.dmt_titolario_info_storico (
	id_classificazione numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_titolario_template_oggetti_ua (
	id_classificazione numeric(22) NULL,
	cardinalita int4 NULL,
	valore varchar(1000) NULL
);




CREATE TABLE auri_owner_1.dmt_topografico (
	id_toponimo numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	nome_toponimo varchar(250) NOT NULL,
	descrizione varchar(4000) NULL,
	cod_rapido varchar(30) NULL,
	prov_ci_toponimo varchar(30) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	flg_ann int2 NULL,
	note varchar(1000) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_topog_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_topog_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_topog_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_topografico_pkey PRIMARY KEY (id_toponimo)
);
CREATE INDEX dmi_topografico_cod_rapido ON auri_owner_1.dmt_topografico USING btree (auri_master.dmfn_upper(cod_rapido));
CREATE INDEX dmi_topografico_des ON auri_owner_1.dmt_topografico USING gin (to_tsvector('english'::regconfig, (descrizione)::text));
CREATE INDEX dmi_topografico_nome ON auri_owner_1.dmt_topografico USING gin (to_tsvector('english'::regconfig, (nome_toponimo)::text));
CREATE INDEX dmi_topografico_prov_ci ON auri_owner_1.dmt_topografico USING btree (prov_ci_toponimo);
CREATE INDEX dmi_topografico_sp_aoo ON auri_owner_1.dmt_topografico USING btree (id_sp_aoo);
CREATE INDEX dmi_topografico_user_ins ON auri_owner_1.dmt_topografico USING btree (id_user_ins);
CREATE UNIQUE INDEX dmt_topografico_u_nome ON auri_owner_1.dmt_topografico USING btree (auri_master.dmfn_prepare_denom_x_match(nome_toponimo), id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_toponomastica (
	ci_toponomastico varchar(30) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	nome_toponomastico varchar(250) NOT NULL,
	cap varchar(5) NULL,
	ci_quartiere varchar(30) NULL,
	tipo_toponimo varchar(100) NOT NULL,
	nome_senza_tipo varchar(250) NOT NULL,
	ci_zona varchar(30) NULL,
	stato varchar(100) NOT NULL DEFAULT 'attivo'::character varying,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	prov_ci_toponomastico varchar(30) NULL,
	cod_istat_comune varchar(6) NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_toponomastica_c_stato CHECK (((stato)::text = ANY (ARRAY[('attivo'::character varying)::text, ('deliberato'::character varying)::text, ('soppresso'::character varying)::text]))),
	CONSTRAINT dmt_toponomastica_pkey PRIMARY KEY (ci_toponomastico)
);
CREATE INDEX dmi_topon_cap ON auri_owner_1.dmt_toponomastica USING btree (cap);
CREATE INDEX dmi_topon_ci_quart ON auri_owner_1.dmt_toponomastica USING btree (ci_quartiere);
CREATE INDEX dmi_topon_ci_zona ON auri_owner_1.dmt_toponomastica USING btree (ci_zona);
CREATE INDEX dmi_topon_istat_com ON auri_owner_1.dmt_toponomastica USING btree (cod_istat_comune);
CREATE INDEX dmi_topon_nome ON auri_owner_1.dmt_toponomastica USING btree (auri_master.dmfn_prepare_denom_x_match(nome_toponomastico), id_sp_aoo);
CREATE INDEX dmi_topon_nome_rft ON auri_owner_1.dmt_toponomastica USING gin (to_tsvector('english'::regconfig, (nome_toponomastico)::text));
CREATE INDEX dmi_topon_prov_ci ON auri_owner_1.dmt_toponomastica USING btree (prov_ci_toponomastico);




CREATE TABLE auri_owner_1.dmt_toponomastica_civici (
	ci_toponomastico varchar(30) NULL,
	ci_civico varchar(30) NULL,
	civico varchar(30) NULL,
	civico_numero int4 NULL,
	civico_appendice varchar(100) NULL,
	barrato varchar(30) NULL,
	stato varchar(100) NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	localita varchar(250) NULL,
	cap varchar(5) NULL,
	ci_quartiere varchar(30) NULL,
	ci_zona varchar(30) NULL,
	ts_ins date NULL,
	ts_last_upd date NULL
);




CREATE TABLE auri_owner_1.dmt_toponomastica_civici_coordinate (
	ci_toponomastico varchar(30) NULL,
	ci_civico varchar(30) NULL,
	x numeric NULL,
	y numeric NULL,
	z numeric NULL
);




CREATE TABLE auri_owner_1.dmt_unita_doc (
	id_ud numeric(10) NOT NULL,
	id_sp_aoo numeric(10) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	nome_ud varchar(100) NOT NULL,
	flg_nome_ud_auto numeric(1) NULL,
	id_library numeric(10) NULL,
	flg_archivio varchar(1) NULL,
	flg_tipo_prov varchar(1) NULL,
	cod_stato varchar(10) NULL,
	cod_stato_dett varchar(10) NULL,
	ts_last_upd_stato date NULL,
	flg_interesse_attivo numeric(1) NULL DEFAULT 1,
	ts_interesse_cessato date NULL,
	ts_arrivo date NULL,
	id_ud_aut_reg_diff int4 NULL,
	estremi_aut_reg_diff varchar(100) NULL,
	motivi_reg_diff varchar(500) NULL,
	ts_termine_x_reg_diff date NULL,
	dt_doc_ricevuto date NULL,
	anno_doc_ricevuto numeric(4) NULL,
	estremi_reg_doc_ricevuto varchar(50) NULL,
	rif_doc_ricevuto varchar(50) NULL,
	cod_mezzo_trasm varchar(10) NULL,
	dt_raccomandata date NULL,
	nro_raccomandata varchar(50) NULL,
	nro_lista_raccomandata varchar(150) NULL,
	dt_notifica date NULL,
	nro_notifica varchar(50) NULL,
	indirizzo_prov auri_master.dmo_estremi_indirizzo NULL,
	email_prov auri_master.dmo_rif_email NULL,
	id_user_ricezione int4 NULL,
	id_uo_ricezione int4 NULL,
	flg_interoperabilita numeric(1) NULL,
	id_user_ctrl_ammissib int4 NULL,
	ts_spedizione date NULL,
	id_user_spedizione int4 NULL,
	id_uo_spedizione int4 NULL,
	nro_allegati numeric(2) NULL,
	liv_evidenza int4 NULL,
	flg_conserv_perm numeric(1) NULL,
	periodo_conserv int4 NULL,
	cod_supporto_conserv varchar(10) NULL,
	dt_scarto date NULL,
	id_user_scarto int4 NULL,
	id_user_aut_scarto int4 NULL,
	id_toponimo int4 NULL,
	collocazione_fisica varchar(1000) NULL,
	note varchar(4000) NULL,
	ts_lock date NULL,
	id_user_lock int4 NULL,
	flg_tipo_lock varchar(1) NULL,
	flg_ann numeric(1) NULL,
	motivi_ann varchar(500) NULL,
	flg_cons_ered_permessi numeric(1) NULL DEFAULT 1,
	id_folder_ered int4 NULL,
	id_workspace_ered int4 NULL,
	prov_ci_ud varchar(30) NULL,
	cod_canale_invio_dest varchar(10) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	dt_inizio_pubbl date NULL,
	dt_esec_ado date NULL,
	dt_esecutivita date NULL,
	dt_adozione date NULL,
	id_attivita_sinapoli varchar(50) NULL,
	ts_invio_in_conserv date NULL,
	id_in_conservazione varchar(250) NULL,
	id_pdv varchar(64) NULL,
	ts_acc_in_conserv date NULL,
	ts_inizio_conserv date NULL,
	dt_termine_conserv date NULL,
	flg_porogato_termine_cons numeric(1) NULL,
	flg_esib_da_cons numeric(1) NULL,
	err_msg_invio_in_conserv varchar(4000) NULL,
	stato_conservazione varchar(30) NULL,
	CONSTRAINT dmt_ud_c_ann CHECK ((flg_ann = (1)::numeric)),
	CONSTRAINT dmt_ud_c_archivio CHECK (((flg_archivio)::text = ANY (ARRAY[('C'::character varying)::text, ('D'::character varying)::text, ('S'::character varying)::text]))),
	CONSTRAINT dmt_ud_c_conserv CHECK ((flg_conserv_perm = (1)::numeric)),
	CONSTRAINT dmt_ud_c_ered_perm CHECK ((flg_cons_ered_permessi = (1)::numeric)),
	CONSTRAINT dmt_ud_c_esib_da_cons CHECK ((flg_esib_da_cons = (1)::numeric)),
	CONSTRAINT dmt_ud_c_int_attivo CHECK ((flg_interesse_attivo = (1)::numeric)),
	CONSTRAINT dmt_ud_c_interop CHECK ((flg_interoperabilita = (1)::numeric)),
	CONSTRAINT dmt_ud_c_nud_auto CHECK ((flg_nome_ud_auto = (1)::numeric)),
	CONSTRAINT dmt_ud_c_pror_term_cons CHECK ((flg_porogato_termine_cons = (1)::numeric)),
	CONSTRAINT dmt_ud_c_stato_cons_ck CHECK (((stato_conservazione)::text = ANY (ARRAY[('inviato'::character varying)::text, ('accettato'::character varying)::text, ('conservato'::character varying)::text, ('rifiutato'::character varying)::text, ('da_inviare'::character varying)::text, ('non_prevista'::character varying)::text]))),
	CONSTRAINT dmt_ud_c_tipo_lock CHECK (((flg_tipo_lock)::text = ANY (ARRAY[('I'::character varying)::text, ('E'::character varying)::text]))),
	CONSTRAINT dmt_ud_c_tp_prov CHECK (((flg_tipo_prov)::text = ANY (ARRAY[('E'::character varying)::text, ('I'::character varying)::text, ('U'::character varying)::text]))),
	CONSTRAINT dmt_unita_doc_pkey PRIMARY KEY (id_ud)
)
WITH (
	OIDS=TRUE
);
CREATE INDEX dmi_ud_canale_inv ON auri_owner_1.dmt_unita_doc USING btree (cod_canale_invio_dest);
CREATE INDEX dmi_ud_dt_racc ON auri_owner_1.dmt_unita_doc USING btree (dt_raccomandata, id_sp_aoo);
CREATE INDEX dmi_ud_dt_ric ON auri_owner_1.dmt_unita_doc USING btree (dt_doc_ricevuto, id_sp_aoo);
CREATE INDEX dmi_ud_estremi_reg_ric ON auri_owner_1.dmt_unita_doc USING btree (auri_master.dmfn_prepare_denom_x_match(estremi_reg_doc_ricevuto), COALESCE(anno_doc_ricevuto, (auri_master.custom_to_char((dt_doc_ricevuto)::timestamp without time zone, 'RRRR'::character varying))::numeric), id_sp_aoo);
CREATE INDEX dmi_ud_folder_ered ON auri_owner_1.dmt_unita_doc USING btree (id_folder_ered);
CREATE INDEX dmi_ud_id_cons ON auri_owner_1.dmt_unita_doc USING btree (auri_master.dmfn_prepare_denom_x_match(id_in_conservazione));
CREATE INDEX dmi_ud_lock ON auri_owner_1.dmt_unita_doc USING btree (ts_lock);
CREATE INDEX dmi_ud_nome ON auri_owner_1.dmt_unita_doc USING btree (auri_master.dmfn_prepare_denom_x_match(nome_ud), id_sp_aoo);
CREATE INDEX dmi_ud_note ON auri_owner_1.dmt_unita_doc USING gin (to_tsvector('english'::regconfig, (note)::text));
CREATE INDEX dmi_ud_nro_racc ON auri_owner_1.dmt_unita_doc USING btree (auri_master.dmfn_prepare_denom_x_match(nro_raccomandata), id_sp_aoo);
CREATE INDEX dmi_ud_pdv ON auri_owner_1.dmt_unita_doc USING btree (id_pdv);
CREATE INDEX dmi_ud_prov_ci ON auri_owner_1.dmt_unita_doc USING btree (prov_ci_ud);
CREATE INDEX dmi_ud_prov_ci2 ON auri_owner_1.dmt_unita_doc USING btree (auri_master.dmfn_prepare_denom_x_match(prov_ci_ud));
CREATE INDEX dmi_ud_rif_ric ON auri_owner_1.dmt_unita_doc USING btree (auri_master.dmfn_prepare_denom_x_match(rif_doc_ricevuto), id_sp_aoo);
CREATE INDEX dmi_ud_spaoo ON auri_owner_1.dmt_unita_doc USING btree (id_sp_aoo);
CREATE INDEX dmi_ud_topon ON auri_owner_1.dmt_unita_doc USING btree (id_toponimo);
CREATE INDEX dmi_ud_ts_acc_cons ON auri_owner_1.dmt_unita_doc USING btree (ts_acc_in_conserv);
CREATE INDEX dmi_ud_ts_ini_cons ON auri_owner_1.dmt_unita_doc USING btree (ts_inizio_conserv);
CREATE INDEX dmi_ud_ts_int_cess ON auri_owner_1.dmt_unita_doc USING btree (ts_interesse_cessato);
CREATE INDEX dmi_ud_ts_term_cons ON auri_owner_1.dmt_unita_doc USING btree (dt_termine_conserv);
CREATE INDEX dmi_ud_workspace_ered ON auri_owner_1.dmt_unita_doc USING btree (id_workspace_ered);
CREATE INDEX dmi_unita_doc_i_dt_ado ON auri_owner_1.dmt_unita_doc USING btree (dt_adozione);
CREATE INDEX dmi_unita_doc_i_dt_ea ON auri_owner_1.dmt_unita_doc USING btree (dt_esec_ado);
CREATE INDEX dmi_unita_doc_i_dt_esec ON auri_owner_1.dmt_unita_doc USING btree (dt_esecutivita);
CREATE INDEX dmi_unita_doc_i_dt_ip ON auri_owner_1.dmt_unita_doc USING btree (dt_inizio_pubbl);
CREATE INDEX dmi_unita_doc_stato ON auri_owner_1.dmt_unita_doc USING btree (cod_stato);
CREATE INDEX dmi_unita_doc_stato_dett ON auri_owner_1.dmt_unita_doc USING btree (cod_stato_dett);



CREATE TABLE auri_owner_1.dmt_unita_doc_acl (
	id_ud numeric(22) NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL,
	nro_pos_in_acl int4 NULL,
	flg_visib_dati numeric NULL,
	flg_visib_file numeric NULL,
	visib_su_files varchar(250) NULL,
	flg_modifica_dati numeric NULL,
	flg_modifica_file numeric NULL,
	modifica_su_files varchar(250) NULL,
	flg_modifica_acl numeric NULL,
	flg_eliminazione numeric NULL,
	flg_copia numeric NULL,
	flg_recupero numeric NULL,
	flg_acc_lim_ver_pubbl numeric NULL,
	lim_ver_pubbl_su_file varchar(250) NULL,
	flg_ereditato numeric NULL
);




CREATE TABLE auri_owner_1.dmt_unita_doc_altri_attributi (
	id_ud numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);



CREATE TABLE auri_owner_1.dmt_unita_doc_classificazioni (
	id_ud numeric(22) NULL,
	id_classificazione int4 NULL,
	ts date NULL,
	id_user int4 NULL
);



CREATE TABLE auri_owner_1.dmt_unita_doc_documenti (
	id_ud numeric(22) NULL,
	id_doc int4 NULL,
	cod_natura_rel varchar(10) NULL,
	nro_allegato numeric NULL,
	des_dett_rel varchar(250) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL
);



CREATE TABLE auri_owner_1.dmt_unita_doc_folder (
	id_ud numeric(22) NULL,
	id_folder int4 NULL,
	nro_pos_folder_vs_ud int4 NULL,
	cod_ruolo_ud varchar(10) NULL,
	motivo_rel varchar(250) NULL,
	nro_pos_ud_in_folder int4 NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL
);



CREATE TABLE auri_owner_1.dmt_unita_doc_info_storico (
	id_ud numeric(22) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_unita_doc_prese_visioni (
	id_ud numeric(22) NULL,
	id_user int4 NULL,
	a_nome_di int4 NULL,
	ts date NULL,
	osservazioni varchar(1000) NULL
);




CREATE TABLE auri_owner_1.dmt_unita_doc_reg_num (
	id_ud numeric(22) NULL,
	cod_categoria varchar(10) NULL,
	sigla varchar(30) NULL,
	anno numeric NULL,
	numero int4 NULL,
	ts date NULL,
	id_user int4 NULL,
	id_uo int4 NULL,
	flg_ann numeric NULL,
	ts_ann date NULL,
	id_ud_atto_ann int4 NULL,
	estremi_atto_ann varchar(100) NULL,
	motivi_ann varchar(500) NULL,
	dt_termine_rsv date NULL,
	prov_ci_reg_num varchar(30) NULL,
	flg_rich_generazione numeric NULL,
	flg_generata numeric NULL,
	id_user_ann int4 NULL,
	sub_numero varchar(10) NULL
);




CREATE TABLE auri_owner_1.dmt_unita_doc_workspace (
	id_ud numeric(22) NULL,
	id_workspace int4 NULL,
	nro_pos_wks_vs_ud int4 NULL,
	motivo_rel varchar(250) NULL,
	nro_pos_ud_in_wks int4 NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL
);




CREATE TABLE auri_owner_1.dmt_users (
	id_user numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	des_user varchar(150) NOT NULL,
	username varchar(50) NOT NULL,
	"password" varchar(60) NULL,
	dt_inizio_vld timestamp NULL,
	dt_fine_vld timestamp NULL,
	domini_aut auri_master.dmo_user_domain[] NULL,
	id_user_in_rubrica numeric(38) NULL,
	rel_strutt_org auri_master.dmo_rel_uo_user[] NULL,
	deleghe auri_master.dmo_delega_ext[] NULL,
	old_passwords auri_master.dmo_password[] NULL,
	e_mail varchar(100) NULL,
	qualifica varchar(100) NULL,
	nro_matricola varchar(30) NULL,
	titolo varchar(30) NULL,
	flg_no_acc_sis int2 NULL,
	credenz_aut_ext auri_master.dmo_credenz_aut_ext[] NULL,
	acl_predefinite auri_master.dmo_user_acl[] NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	info_storico auri_master.dmo_info_storico[] NULL,
	cod_rapido varchar(30) NULL,
	prov_ci_user varchar(50) NULL,
	flg_locked int2 NULL,
	des_user_rft varchar(150) NOT NULL,
	id_destinatario numeric(38) NULL,
	ts_last_logon timestamp NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	"PASSWORD" varchar(30) NULL,
	CONSTRAINT dmt_users_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_users_no_acc CHECK ((flg_no_acc_sis = 1)),
	CONSTRAINT dmt_users_pkey PRIMARY KEY (id_user),
	CONSTRAINT dmt_users_username_key UNIQUE (username)
);
CREATE INDEX dmi_users_des ON auri_owner_1.dmt_users USING btree (auri_master.dmfn_prepare_denom_x_match(des_user));
CREATE INDEX dmi_users_des_2 ON auri_owner_1.dmt_users USING gin (to_tsvector('english'::regconfig, (des_user_rft)::text));
CREATE INDEX dmi_users_prov_ci ON auri_owner_1.dmt_users USING btree (prov_ci_user);
CREATE INDEX dmi_users_ts_last_logon ON auri_owner_1.dmt_users USING btree (ts_last_logon);
CREATE INDEX dmt_users_dest ON auri_owner_1.dmt_users USING btree (id_destinatario);




CREATE TABLE auri_owner_1.dmt_users_acl_predefinite (
	id_user numeric NOT NULL,
	tipo_dom numeric(1) NULL,
	id_dom int4 NULL,
	id_acl_def_ud_create int4 NULL,
	id_acl_def_fld_creati int4 NULL,
	id_acl_def_wrks_creati int4 NULL,
	id_acl_to_append_ud_ass int4 NULL,
	id_acl_to_append_fld_ass int4 NULL,
	CONSTRAINT dmt_users_acl_predefinite_fk FOREIGN KEY (id_user) REFERENCES dmt_users(id_user)
);
CREATE INDEX dmt_users_acl_predefinite_idx ON auri_owner_1.dmt_users_acl_predefinite USING btree (id_user);




CREATE TABLE auri_owner_1.dmt_users_altri_attributi (
	id_user numeric NOT NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);
CREATE INDEX dmt_users_altri_attributi_idx ON auri_owner_1.dmt_users_altri_attributi USING btree (id_user);



CREATE TABLE auri_owner_1.dmt_users_credenz_aut_ext (
	id_user numeric NOT NULL,
	cod_applicazione varchar(30) NULL,
	cod_id_istanza_appl varchar(30) NULL,
	username varchar(30) NULL,
	"password" varchar(30) NULL,
	ci_user varchar(30) NULL,
	id_uo_reg_default int4 NULL
);
CREATE INDEX dmt_users_credenz_aut_ext_idx ON auri_owner_1.dmt_users_credenz_aut_ext USING btree (id_user);




CREATE TABLE auri_owner_1.dmt_users_deleghe (
	id_user numeric NOT NULL,
	id_scrivania int4 NULL,
	id_user_d numeric NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	natura_motivi varchar(500) NULL
);
CREATE INDEX dmt_users_deleghe_idx ON auri_owner_1.dmt_users_deleghe USING btree (id_user);




CREATE TABLE auri_owner_1.dmt_users_domini_aut (
	id_user numeric NOT NULL,
	tipo_dom numeric(1) NULL,
	id_dom int4 NULL,
	dt_accr_dal date NULL,
	dt_accr_al date NULL,
	id_profilo int4 NULL,
	lista_id_gruppi_priv varchar(1000) NULL
);
CREATE INDEX dmt_users_domini_aut_idx ON auri_owner_1.dmt_users_domini_aut USING btree (id_user);




CREATE TABLE auri_owner_1.dmt_users_info_storico (
	id_main_user numeric(38) NULL,
	ts_fine_vld date NULL,
	id_user int4 NULL,
	motivi varchar(4000) NULL,
	estremi_atto auri_owner_1.dmo_reg_num_ud NULL,
	old_values auri_owner_1.dmva_attr_values NULL
);




CREATE TABLE auri_owner_1.dmt_users_old_passwords (
	id_user numeric NOT NULL,
	"password" varchar(30) NULL,
	dt_fine_vld date NULL
);
CREATE INDEX dmt_users_old_passwords_idx ON auri_owner_1.dmt_users_old_passwords USING btree (id_user);




CREATE TABLE auri_owner_1.dmt_users_rel_strutt_org (
	id_user numeric NOT NULL,
	id_uo int4 NULL,
	dt_inizio_vld date NULL,
	dt_fine_vld date NULL,
	flg_tipo_rel varchar(1) NULL,
	id_ruolo_amm int4 NULL,
	id_scrivania numeric(38) NULL,
	flg_incl_sottouo numeric(1) NULL,
	flg_incl_scrivanie numeric(1) NULL,
	flg_primario numeric(1) NULL,
	deriva_da_pp int4 NULL,
	esclusioni_uo_coll_pp varchar(4000) NULL
)
WITH (
	OIDS=TRUE
);
CREATE INDEX dmt_users_rel_strutt_org_idx ON auri_owner_1.dmt_users_rel_strutt_org USING btree (id_user);




CREATE TABLE auri_owner_1.dmt_web_form_template (
	template_name varchar(150) NOT NULL,
	description varchar(500) NULL,
	id_user_owner numeric(38) NULL,
	id_sp_aoo numeric(38) NULL,
	ci_page varchar(150) NOT NULL,
	element_values text NOT NULL,
	ts_ins timestamp NOT NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL
);
CREATE INDEX dmi_web_form_templ_des ON auri_owner_1.dmt_web_form_template USING gin (to_tsvector('english'::regconfig, (description)::text));
CREATE INDEX dmi_web_form_templ_name ON auri_owner_1.dmt_web_form_template USING gin (to_tsvector('english'::regconfig, (template_name)::text));
CREATE UNIQUE INDEX dmt_web_form_template_u ON auri_owner_1.dmt_web_form_template USING btree (auri_master.dmfn_prepare_denom_x_match(template_name), id_user_owner, id_sp_aoo, ci_page);




CREATE TABLE auri_owner_1.dmt_web_page_history (
	id_user numeric(38) NOT NULL,
	ci_page varchar(150) NOT NULL,
	ci_element varchar(150) NOT NULL,
	element_value varchar(1000) NOT NULL,
	ts_ins timestamp NOT NULL,
	CONSTRAINT dmt_web_page_history_pkey PRIMARY KEY (id_user, ci_page, ci_element, element_value)
);




CREATE TABLE auri_owner_1.dmt_workspaces (
	id_workspace numeric(38) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	nome_workspace varchar(100) NOT NULL,
	des_workspace varchar(1000) NULL,
	note varchar(4000) NULL,
	ts_lock timestamp NULL,
	id_user_lock numeric(38) NULL,
	flg_tipo_lock varchar(1) NULL,
	flg_ann int2 NULL,
	motivi_ann varchar(500) NULL,
	flg_cons_prop_permessi int2 NULL DEFAULT 1,
	prov_ci_workspace varchar(30) NULL,
	container_type varchar(30) NULL,
	container_alias varchar(30) NULL,
	rif_in_repository varchar(1000) NULL,
	flg_hidden int2 NULL,
	flg_locked int2 NULL,
	ts_ins timestamp NULL,
	id_user_ins numeric(38) NULL,
	ts_last_upd timestamp NULL,
	id_user_last_upd numeric(38) NULL,
	CONSTRAINT dmt_workspaces_c_ann CHECK ((flg_ann = 1)),
	CONSTRAINT dmt_workspaces_c_hidden CHECK ((flg_hidden = 1)),
	CONSTRAINT dmt_workspaces_c_lck CHECK ((flg_locked = 1)),
	CONSTRAINT dmt_workspaces_pkey PRIMARY KEY (id_workspace),
	CONSTRAINT dmt_wrk_c_prop_perm CHECK ((flg_cons_prop_permessi = 1)),
	CONSTRAINT dmt_wrks_c_tipo_lock CHECK (((flg_tipo_lock)::text = ANY (ARRAY[('I'::character varying)::text, ('E'::character varying)::text])))
);
CREATE INDEX dmi_workspace_des ON auri_owner_1.dmt_workspaces USING gin (to_tsvector('english'::regconfig, (des_workspace)::text));
CREATE INDEX dmi_workspace_name ON auri_owner_1.dmt_workspaces USING gin (to_tsvector('english'::regconfig, (nome_workspace)::text));
CREATE INDEX dmi_workspace_note ON auri_owner_1.dmt_workspaces USING gin (to_tsvector('english'::regconfig, (note)::text));
CREATE INDEX dmi_workspaces_lock ON auri_owner_1.dmt_workspaces USING btree (ts_lock);
CREATE INDEX dmi_wrk_spaoo ON auri_owner_1.dmt_workspaces USING btree (id_sp_aoo);
CREATE UNIQUE INDEX dmt_workspaces_u_nome ON auri_owner_1.dmt_workspaces USING btree (auri_master.dmfn_prepare_denom_x_match(nome_workspace), id_sp_aoo);




CREATE TABLE auri_owner_1.dmt_workspaces_acl (
	id_workspace numeric(22) NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL,
	nro_pos_in_acl int4 NULL,
	flg_visibilita numeric NULL,
	flg_modifica_dati numeric NULL,
	flg_modifica_acl numeric NULL,
	flg_eliminazione numeric NULL,
	flg_recupero numeric NULL,
	flg_mod_contenuto numeric NULL,
	CONSTRAINT dmt_workspaces_acl_fk FOREIGN KEY (id_workspace) REFERENCES dmt_workspaces(id_workspace)
);




CREATE TABLE auri_owner_1.dmt_workspaces_altri_attributi (
	id_workspace numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL,
	CONSTRAINT dmt_workspaces_altri_attributi_fk FOREIGN KEY (id_workspace) REFERENCES dmt_workspaces(id_workspace)
);




CREATE TABLE auri_owner_1.l_acltab (
	id_ud numeric(22) NULL,
	flg_tipo varchar(3) NULL,
	id_in_anag_prov int4 NULL,
	flg_incl_sottouo numeric NULL,
	vs_livello_so int4 NULL,
	vs_id_uo int4 NULL,
	vs_cod_tipo_uo varchar(10) NULL,
	nro_pos_in_acl int4 NULL,
	flg_visib_dati numeric NULL,
	flg_visib_file numeric NULL,
	visib_su_files varchar(250) NULL,
	flg_modifica_dati numeric NULL,
	flg_modifica_file numeric NULL,
	modifica_su_files varchar(250) NULL,
	flg_modifica_acl numeric NULL,
	flg_eliminazione numeric NULL,
	flg_copia numeric NULL,
	flg_recupero numeric NULL,
	flg_acc_lim_ver_pubbl numeric NULL,
	lim_ver_pubbl_su_file varchar(250) NULL,
	flg_ereditato numeric NULL
);



CREATE TABLE auri_owner_1.l_cnt (
	count int8 NULL
);




CREATE TABLE auri_owner_1.l_deflivellitittab (
	nro int4 NULL,
	denominazione varchar(30) NULL,
	flg_codice_numerico numeric NULL,
	flg_romano numeric NULL
);




CREATE TABLE auri_owner_1.l_idacl (
	id_acl_def_ud_create int4 NULL
);




CREATE TABLE auri_owner_1.l_idpianoclassif (
	id_piano_classif numeric(38) NULL
);



CREATE TABLE auri_owner_1.l_idprofilo (
	id_profilo int4 NULL
);




CREATE TABLE auri_owner_1.l_idspapp (
	id_sogg_prod_app int4 NULL
);




CREATE TABLE auri_owner_1.l_idud (
	id_ud numeric(10) NULL
);




CREATE TABLE auri_owner_1.l_iduser (
	id_user numeric(38) NULL
);




CREATE TABLE auri_owner_1.l_strapp (
	str_value varchar(4000) NULL
);




CREATE TABLE auri_owner_1.l_unitadocrec (
	id_ud numeric(10) NULL,
	id_sp_aoo numeric(10) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	nome_ud varchar(100) NULL,
	flg_nome_ud_auto numeric(1) NULL,
	id_library numeric(10) NULL,
	flg_archivio varchar(1) NULL,
	flg_tipo_prov varchar(1) NULL,
	cod_stato varchar(10) NULL,
	cod_stato_dett varchar(10) NULL,
	ts_last_upd_stato date NULL,
	flg_interesse_attivo numeric(1) NULL,
	ts_interesse_cessato date NULL,
	ts_arrivo date NULL,
	id_ud_aut_reg_diff int4 NULL,
	estremi_aut_reg_diff varchar(100) NULL,
	motivi_reg_diff varchar(500) NULL,
	ts_termine_x_reg_diff date NULL,
	dt_doc_ricevuto date NULL,
	anno_doc_ricevuto numeric(4) NULL,
	estremi_reg_doc_ricevuto varchar(50) NULL,
	rif_doc_ricevuto varchar(50) NULL,
	cod_mezzo_trasm varchar(10) NULL,
	dt_raccomandata date NULL,
	nro_raccomandata varchar(50) NULL,
	nro_lista_raccomandata varchar(150) NULL,
	dt_notifica date NULL,
	nro_notifica varchar(50) NULL,
	indirizzo_prov auri_master.dmo_estremi_indirizzo NULL,
	email_prov auri_master.dmo_rif_email NULL,
	id_user_ricezione int4 NULL,
	id_uo_ricezione int4 NULL,
	flg_interoperabilita numeric(1) NULL,
	id_user_ctrl_ammissib int4 NULL,
	ts_spedizione date NULL,
	id_user_spedizione int4 NULL,
	id_uo_spedizione int4 NULL,
	nro_allegati numeric(2) NULL,
	liv_evidenza int4 NULL,
	flg_conserv_perm numeric(1) NULL,
	periodo_conserv int4 NULL,
	cod_supporto_conserv varchar(10) NULL,
	dt_scarto date NULL,
	id_user_scarto int4 NULL,
	id_user_aut_scarto int4 NULL,
	id_toponimo int4 NULL,
	collocazione_fisica varchar(1000) NULL,
	note varchar(4000) NULL,
	ts_lock date NULL,
	id_user_lock int4 NULL,
	flg_tipo_lock varchar(1) NULL,
	flg_ann numeric(1) NULL,
	motivi_ann varchar(500) NULL,
	flg_cons_ered_permessi numeric(1) NULL,
	id_folder_ered int4 NULL,
	id_workspace_ered int4 NULL,
	prov_ci_ud varchar(30) NULL,
	cod_canale_invio_dest varchar(10) NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	dt_inizio_pubbl date NULL,
	dt_esec_ado date NULL,
	dt_esecutivita date NULL,
	dt_adozione date NULL,
	id_attivita_sinapoli varchar(50) NULL,
	ts_invio_in_conserv date NULL,
	id_in_conservazione varchar(250) NULL,
	id_pdv varchar(64) NULL,
	ts_acc_in_conserv date NULL,
	ts_inizio_conserv date NULL,
	dt_termine_conserv date NULL,
	flg_porogato_termine_cons numeric(1) NULL,
	flg_esib_da_cons numeric(1) NULL,
	err_msg_invio_in_conserv varchar(4000) NULL,
	stato_conservazione varchar(30) NULL
);




CREATE TABLE auri_owner_1.liste (
	nome_lista varchar(30) NOT NULL,
	meaning varchar(250) NOT NULL,
	nome_tabella varchar(30) NULL,
	cod_funz varchar(100) NULL,
	cod_appl_owner varchar(30) NULL,
	cod_ist_appl_owner varchar(30) NULL,
	altri_attributi auri_master.dmo_attr_value[] NULL,
	CONSTRAINT liste_p PRIMARY KEY (nome_lista)
);




CREATE TABLE auri_owner_1.liste_altri_attributi (
	nome_lista varchar(30) NOT NULL,
	meaning varchar(250) NOT NULL,
	id_destinatario numeric(22) NULL,
	attr_name varchar(250) NULL,
	nro_occ_value int4 NULL,
	str_value varchar(4000) NULL,
	num_value numeric NULL,
	date_value date NULL,
	ts_ins date NULL,
	id_user_ins int4 NULL,
	ts_last_upd date NULL,
	id_user_last_upd int4 NULL,
	id_valore_attr int4 NULL,
	id_valore_attr_sup int4 NULL
);




CREATE TABLE auri_owner_1.mailbox_account (
	id_account varchar(40) NULL,
	account varchar(100) NULL
);




CREATE TABLE auri_owner_1.old_dmt_def_config_param (
	par_name varchar(100) NULL,
	meaning varchar(1000) NULL,
	par_type varchar(10) NULL,
	max_liv_def int2 NULL,
	default_value varchar(4000) NULL,
	valori_ammessi varchar[] NULL,
	flg_x_gui int2 NULL
);




CREATE TABLE auri_owner_1.sedi (
	id numeric NULL,
	descrizione varchar(100) NULL,
	lista_indirizzi auri_owner_1.tt_indirizzi NULL
);




CREATE TABLE auri_owner_1.storages (
	id_storage varchar(50) NOT NULL,
	flg_disattivo int2 NULL,
	tipo_storage varchar(20) NOT NULL,
	xml_config text NULL,
	CONSTRAINT storages_c_flg_disattivo CHECK ((flg_disattivo = 1)),
	CONSTRAINT storages_pkey PRIMARY KEY (id_storage)
);




CREATE TABLE auri_owner_1.t1 (
	col1 int4 NULL
)
WITH (
	OIDS=TRUE
);




CREATE TABLE auri_owner_1.t_anag_formati_dig (
	id_dig_format varchar(64) NOT NULL,
	nome varchar(250) NOT NULL,
	versione varchar(30) NULL,
	altri_nomi varchar(1000) NULL,
	estensione_principale varchar(5) NOT NULL,
	mimetype_principale varchar(100) NOT NULL,
	des_estesa varchar(1000) NULL,
	id_rec_diz_registro_fmt varchar(64) NOT NULL,
	id_in_registro_fmt varchar(64) NOT NULL,
	id_rec_diz_tipo_fmt varchar(64) NULL,
	byte_orders varchar(3) NULL,
	dt_rilascio timestamp NULL,
	dt_desupp timestamp NULL,
	rischi varchar(4000) NULL,
	sviluppato_da varchar(250) NULL,
	mantenuto_da varchar(250) NULL,
	flg_copyright int2 NOT NULL,
	flg_indicizzabile numeric(38) NOT NULL,
	flg_stato varchar(2) NOT NULL,
	ts_agg_stato timestamp NOT NULL DEFAULT LOCALTIMESTAMP,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	CONSTRAINT chk_t_formati_dig_0 CHECK (((byte_orders)::text = ANY ((ARRAY['L'::character varying, 'B'::character varying, 'M'::character varying, 'LB'::character varying, 'LM'::character varying, 'BM'::character varying, 'LBM'::character varying])::text[]))),
	CONSTRAINT chk_t_formati_dig_1 CHECK ((flg_copyright = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_formati_dig_2 CHECK ((flg_indicizzabile = ANY (ARRAY[(0)::numeric, (1)::numeric, (2)::numeric]))),
	CONSTRAINT chk_t_formati_dig_3 CHECK (((flg_stato)::text = ANY ((ARRAY['A'::character varying, 'P'::character varying, 'NA'::character varying])::text[]))),
	CONSTRAINT chk_t_formati_dig_4 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_anag_formati_dig_pkey PRIMARY KEY (id_dig_format)
);
CREATE INDEX idx_anag_formati_dig_1 ON auri_owner_1.t_anag_formati_dig USING btree (id_rec_diz_tipo_fmt);
CREATE INDEX idx_anag_formati_dig_ts ON auri_owner_1.t_anag_formati_dig USING btree (ts_ultimo_agg);
CREATE UNIQUE INDEX uk_anag_formati_dig_1 ON auri_owner_1.t_anag_formati_dig USING btree (versione, nome);
CREATE UNIQUE INDEX uk_anag_formati_dig_2 ON auri_owner_1.t_anag_formati_dig USING btree (id_rec_diz_registro_fmt, id_in_registro_fmt);




CREATE TABLE auri_owner_1.t_anag_fruitori_caselle (
	id_fruitore_casella varchar(64) NOT NULL,
	tipo varchar(30) NOT NULL DEFAULT 'UO'::character varying,
	denominazione varchar(250) NOT NULL,
	codice varchar(30) NULL,
	id_fruitore_sup varchar(64) NULL,
	id_ente_aoo varchar(64) NULL,
	flg_attivo int2 NOT NULL DEFAULT 0,
	codice_completo varchar(250) NULL,
	denominazione_completa varchar(2000) NOT NULL,
	id_prov_fruitore varchar(100) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_anag_fruitori_caselle_0 CHECK ((flg_attivo = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_anag_fruitori_caselle_1 CHECK (((tipo)::text = ANY ((ARRAY['ENTE'::character varying, 'AOO'::character varying, 'UO'::character varying])::text[]))),
	CONSTRAINT t_anag_fruitori_caselle_pkey PRIMARY KEY (id_fruitore_casella)
);
CREATE INDEX idx_anag_fruitori_caselle_1 ON auri_owner_1.t_anag_fruitori_caselle USING btree (denominazione);
CREATE INDEX idx_anag_fruitori_caselle_2 ON auri_owner_1.t_anag_fruitori_caselle USING btree (codice);
CREATE INDEX idx_anag_fruitori_caselle_3 ON auri_owner_1.t_anag_fruitori_caselle USING btree (id_fruitore_sup);
CREATE INDEX idx_anag_fruitori_caselle_4 ON auri_owner_1.t_anag_fruitori_caselle USING btree (codice_completo);
CREATE INDEX idx_anag_fruitori_caselle_5 ON auri_owner_1.t_anag_fruitori_caselle USING btree (id_prov_fruitore);
CREATE INDEX idx_anag_fruitori_caselle_6 ON auri_owner_1.t_anag_fruitori_caselle USING btree (denominazione_completa, id_ente_aoo);




CREATE TABLE auri_owner_1.t_anag_notif_email (
	id_schedulazione varchar(64) NOT NULL,
	tipo_notifica varchar(30) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	ci_applicazione varchar(30) NULL,
	ci_istanza_applicazione varchar(30) NULL,
	cod_lingua varchar(5) NOT NULL DEFAULT 'it'::character varying,
	ipa_dest_fattura varchar(250) NULL,
	dest_notifica varchar(4000) NOT NULL,
	dest_cc_notifica varchar(4000) NULL,
	id_account_mitt varchar(64) NULL,
	account_mittente varchar(150) NULL,
	smtp_endpoint_account_mitt varchar(500) NULL,
	smtp_port_account_mitt varchar(5) NULL,
	subject varchar(1000) NOT NULL,
	body text NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_anag_notif_email_1 CHECK (((tipo_notifica)::text = ANY ((ARRAY['RIC_NOT_SDI'::character varying, 'FATT_DA_FIRMARE'::character varying, 'FATT_PRESA_IN_CARICO_FTP'::character varying, 'FATT_NON_PRESA_IN_CARICO_FTP'::character varying, 'NO_LOTTO_SU_FTP'::character varying])::text[]))),
	CONSTRAINT chk_t_anag_notif_email_3 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_anag_notif_email_pkey PRIMARY KEY (id_schedulazione)
);
CREATE INDEX idx_anag_notif_email_0 ON auri_owner_1.t_anag_notif_email USING btree (tipo_notifica);
CREATE INDEX idx_anag_notif_email_1 ON auri_owner_1.t_anag_notif_email USING btree (id_sp_aoo);
CREATE INDEX idx_anag_notif_email_2 ON auri_owner_1.t_anag_notif_email USING btree (dest_notifica);
CREATE INDEX idx_anag_notif_email_3 ON auri_owner_1.t_anag_notif_email USING btree (dest_cc_notifica);
CREATE INDEX idx_anag_notif_email_ts ON auri_owner_1.t_anag_notif_email USING btree (ts_ultimo_agg);
CREATE UNIQUE INDEX uk_anag_notif_email ON auri_owner_1.t_anag_notif_email USING btree (tipo_notifica, id_sp_aoo, ipa_dest_fattura, ci_applicazione, ci_istanza_applicazione);




CREATE TABLE auri_owner_1.t_anag_template_email (
	id_template_email varchar(64) NOT NULL,
	tipo_invio varchar(100) NOT NULL,
	id_sp_aoo numeric(38) NOT NULL,
	ci_applicazione varchar(30) NULL,
	ci_istanza_applicazione varchar(30) NULL,
	cod_lingua varchar(5) NOT NULL DEFAULT 'it'::character varying,
	id_account_mitt varchar(64) NOT NULL,
	account_mitt varchar(1000) NULL,
	cod_template_subject varchar(10) NOT NULL,
	cod_template_body varchar(250) NOT NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_anag_template_email_1 CHECK (((tipo_invio)::text = ANY ((ARRAY['notifica_pubblicazione_web'::character varying, 'invio_link_al_documento'::character varying, 'invio_automatico_da_pec'::character varying, 'invio_automatico_con_documento_allegato'::character varying, 'reinvio_manuale_documento'::character varying])::text[]))),
	CONSTRAINT chk_t_anag_template_email_2 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_anag_template_email_pkey PRIMARY KEY (id_template_email)
);
CREATE INDEX idx_anag_template_email_0 ON auri_owner_1.t_anag_template_email USING btree (tipo_invio);
CREATE INDEX idx_anag_template_email_1 ON auri_owner_1.t_anag_template_email USING btree (id_sp_aoo);
CREATE INDEX idx_anag_template_email_2 ON auri_owner_1.t_anag_template_email USING btree (ci_applicazione, ci_istanza_applicazione);
CREATE INDEX idx_anag_template_email_3 ON auri_owner_1.t_anag_template_email USING btree (cod_lingua);
CREATE INDEX idx_anag_template_email_4 ON auri_owner_1.t_anag_template_email USING btree (cod_template_subject);
CREATE INDEX idx_anag_template_email_5 ON auri_owner_1.t_anag_template_email USING btree (cod_template_body);
CREATE INDEX idx_anag_template_email_6 ON auri_owner_1.t_anag_template_email USING btree (id_account_mitt);
CREATE INDEX idx_anag_template_email_7 ON auri_owner_1.t_anag_template_email USING btree (account_mitt);
CREATE INDEX idx_anag_template_email_ts ON auri_owner_1.t_anag_template_email USING btree (ts_ultimo_agg);
CREATE UNIQUE INDEX uk_anag_template_email ON auri_owner_1.t_anag_template_email USING btree (tipo_invio, id_sp_aoo, ci_applicazione, ci_istanza_applicazione, cod_lingua);




CREATE TABLE auri_owner_1.t_applicazioni_mod_pec (
	id_applicazione varchar(64) NOT NULL,
	cod_applicazione varchar(30) NOT NULL,
	nome varchar(150) NOT NULL,
	"password" varchar(100) NOT NULL,
	descrizione varchar(500) NULL,
	flg_attiva int2 NOT NULL DEFAULT 1,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_applicazioni_mod_pec_0 CHECK ((flg_attiva = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_applicazioni_mod_pec_pkey PRIMARY KEY (id_applicazione)
);
CREATE UNIQUE INDEX uk_applicazioni_mod_pec_1 ON auri_owner_1.t_applicazioni_mod_pec USING btree (cod_applicazione);
CREATE UNIQUE INDEX uk_applicazioni_mod_pec_2 ON auri_owner_1.t_applicazioni_mod_pec USING btree (nome);




CREATE TABLE auri_owner_1.t_assegn_email (
	id_assegnazione_email varchar(64) NOT NULL,
	id_email varchar(64) NOT NULL,
	id_fruitore_dest varchar(64) NULL,
	id_utente_dest varchar(64) NULL,
	ts_assegnazione timestamp NOT NULL,
	id_utente_mitt varchar(64) NOT NULL,
	messaggio varchar(4000) NULL,
	flg_ann int2 NOT NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_assegn_email_0 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_assegn_email_pkey PRIMARY KEY (id_assegnazione_email)
);
CREATE INDEX idx_assegn_email_1 ON auri_owner_1.t_assegn_email USING btree (id_fruitore_dest);
CREATE INDEX idx_assegn_email_2 ON auri_owner_1.t_assegn_email USING btree (id_utente_dest);
CREATE INDEX idx_assegn_email_3 ON auri_owner_1.t_assegn_email USING btree (id_utente_mitt);
CREATE INDEX uk_assegna_email_1 ON auri_owner_1.t_assegn_email USING btree (id_email, ts_assegnazione);




CREATE TABLE auri_owner_1.t_assegn_email_h (
	id_email varchar(64) NOT NULL,
	id_fruitore_ass varchar(64) NOT NULL,
	CONSTRAINT t_assegn_email_h_pkey PRIMARY KEY (id_email, id_fruitore_ass)
);
CREATE INDEX idx_assegn_email_h_1 ON auri_owner_1.t_assegn_email_h USING btree (id_fruitore_ass);




CREATE TABLE auri_owner_1.t_attach_email_mgo (
	nro_alleg_reg_prot int2 NULL,
	nro_alleg_scheda_doc int2 NULL,
	id_scheda_doc_email varchar(64) NULL,
	id_attach_email varchar(64) NOT NULL,
	id_email varchar(64) NOT NULL,
	nome_originale varchar(500) NOT NULL,
	nome_originale_ctx varchar(500) NULL,
	display_filename varchar(500) NULL DEFAULT 'allegato'::character varying,
	dimensione int8 NOT NULL,
	flg_firmato int2 NOT NULL,
	flg_firma_valida int2 NOT NULL DEFAULT 0,
	mimetype varchar(100) NULL DEFAULT 'unknown'::character varying,
	impronta varchar(500) NULL,
	algoritmo_impronta varchar(30) NULL,
	encoding_impronta varchar(10) NULL,
	id_reg_prot_email varchar(64) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	nro_progr numeric(38) NULL,
	CONSTRAINT chk_t_attach_email_mgo_0 CHECK ((flg_firmato = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_attach_email_mgo_1 CHECK (((encoding_impronta)::text = ANY ((ARRAY['base64'::character varying, 'hex'::character varying])::text[]))),
	CONSTRAINT chk_t_attach_email_mgo_2 CHECK ((flg_firma_valida = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_attach_email_mgo_pkey PRIMARY KEY (id_attach_email)
);
CREATE INDEX idx_attach_email_mgo_1 ON auri_owner_1.t_attach_email_mgo USING btree (id_email);
CREATE INDEX idx_attach_email_mgo_2 ON auri_owner_1.t_attach_email_mgo USING btree (auri_master.dmfn_prepare_denom_x_match(nome_originale));
CREATE INDEX idx_attach_email_mgo_3 ON auri_owner_1.t_attach_email_mgo USING gin (to_tsvector('english'::regconfig, (nome_originale_ctx)::text));




CREATE TABLE auri_owner_1.t_cap_comuni (
	cap varchar(5) NOT NULL,
	nome_comune varchar(50) NOT NULL,
	targa_provincia varchar(2) NULL,
	CONSTRAINT t_cap_comuni_pkey PRIMARY KEY (cap, nome_comune)
);
CREATE INDEX idx_cap_comuni_1 ON auri_owner_1.t_cap_comuni USING btree (nome_comune);
CREATE INDEX idx_cap_comuni_2 ON auri_owner_1.t_cap_comuni USING btree (targa_provincia);




CREATE TABLE auri_owner_1.t_cap_frazioni (
	cap varchar(5) NOT NULL,
	frazione varchar(50) NOT NULL,
	nome_comune varchar(50) NOT NULL,
	targa_provincia varchar(2) NULL,
	CONSTRAINT t_cap_frazioni_pkey PRIMARY KEY (cap, frazione, nome_comune)
);
CREATE INDEX idx_cap_frazioni_1 ON auri_owner_1.t_cap_frazioni USING btree (nome_comune);
CREATE INDEX idx_cap_frazioni_2 ON auri_owner_1.t_cap_frazioni USING btree (cap);
CREATE INDEX idx_cap_frazioni_3 ON auri_owner_1.t_cap_frazioni USING btree (targa_provincia);




CREATE TABLE auri_owner_1.t_coda_email_da_agg_x_timeout (
	id_email_out varchar(64) NOT NULL,
	tipo_timeout varchar(30) NOT NULL,
	ts_decorr timestamp NOT NULL
);
CREATE INDEX idx_coda_email_x_timeout_2 ON auri_owner_1.t_coda_email_da_agg_x_timeout USING btree (tipo_timeout, ts_decorr);
CREATE UNIQUE INDEX uk_coda_email_x_timeout_0 ON auri_owner_1.t_coda_email_da_agg_x_timeout USING btree (id_email_out, tipo_timeout);




CREATE TABLE auri_owner_1.t_coda_email_da_numerare (
	id_email varchar(64) NULL,
	ts_ins timestamp NULL
);




CREATE TABLE auri_owner_1.t_commenti_email (
	id_commento_email varchar(64) NOT NULL,
	id_email varchar(64) NULL,
	commento varchar(4000) NULL,
	commento_ctx varchar(4000) NULL,
	tag varchar(250) NULL,
	uri_file varchar(250) NULL,
	nro_progr numeric(38) NOT NULL,
	display_filename varchar(500) NULL,
	display_filename_ctx varchar(500) NULL,
	dimensione_file int8 NULL,
	flg_file_firmato int2 NOT NULL,
	mimetype_file varchar(100) NULL,
	impronta_file varchar(500) NULL,
	algoritmo_impronta varchar(30) NULL,
	encoding_impronta varchar(10) NULL,
	id_fruitore_dest varchar(64) NULL,
	id_utente_dest varchar(64) NULL,
	flg_pubblico int2 NOT NULL DEFAULT 1,
	ts_commento timestamp NOT NULL,
	id_utente_autore varchar(64) NOT NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	ts_lock timestamp NULL,
	id_ute_lock varchar(64) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_commenti_email_0 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_commenti_email_1 CHECK ((flg_pubblico = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_commenti_email_pkey PRIMARY KEY (id_commento_email)
);
CREATE INDEX idx_attach_email_7 ON auri_owner_1.t_commenti_email USING btree (auri_master.dmfn_prepare_denom_x_match(display_filename));
CREATE INDEX idx_commenti_email_0 ON auri_owner_1.t_commenti_email USING btree (id_email);
CREATE INDEX idx_commenti_email_1 ON auri_owner_1.t_commenti_email USING btree (id_fruitore_dest);
CREATE INDEX idx_commenti_email_2 ON auri_owner_1.t_commenti_email USING btree (id_utente_dest);
CREATE INDEX idx_commenti_email_3 ON auri_owner_1.t_commenti_email USING btree (id_utente_autore);
CREATE INDEX idx_commenti_email_4 ON auri_owner_1.t_commenti_email USING btree (tag);
CREATE INDEX idx_commenti_email_5 ON auri_owner_1.t_commenti_email USING gin (to_tsvector('english'::regconfig, (commento_ctx)::text));
CREATE INDEX idx_commenti_email_6 ON auri_owner_1.t_commenti_email USING gin (to_tsvector('english'::regconfig, (display_filename_ctx)::text));
CREATE INDEX idx_commenti_email_7 ON auri_owner_1.t_commenti_email USING btree (ts_lock);
CREATE INDEX idx_commenti_email_8 ON auri_owner_1.t_commenti_email USING btree (id_ute_lock);




CREATE TABLE auri_owner_1.t_dest_email_to_send (
	id_dest_email_to_send varchar(64) NOT NULL,
	id_richiesta varchar(64) NOT NULL,
	flg_tipo_destinatario varchar(3) NOT NULL,
	account_destinatario varchar(150) NULL,
	id_voce_rubrica_dest varchar(64) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_dest_email_to_send_0 CHECK (((flg_tipo_destinatario)::text = ANY ((ARRAY['P'::character varying, 'CC'::character varying, 'CCN'::character varying])::text[]))),
	CONSTRAINT t_dest_email_to_send_pkey PRIMARY KEY (id_dest_email_to_send)
);
CREATE INDEX idx_dest_email_to_send_1 ON auri_owner_1.t_dest_email_to_send USING btree (account_destinatario, flg_tipo_destinatario);
CREATE INDEX idx_dest_email_to_send_2 ON auri_owner_1.t_dest_email_to_send USING btree (id_voce_rubrica_dest, flg_tipo_destinatario);
CREATE UNIQUE INDEX uk_dest_email_to_send ON auri_owner_1.t_dest_email_to_send USING btree (id_richiesta, flg_tipo_destinatario, account_destinatario, id_voce_rubrica_dest);




CREATE TABLE auri_owner_1.t_destinatari_email_mgo (
	id_destinatario_email varchar(64) NOT NULL,
	id_email varchar(64) NOT NULL,
	flg_dest_orig_invio int2 NOT NULL,
	flg_tipo_destinatario varchar(3) NOT NULL,
	account_destinatario varchar(2000) NULL,
	flg_dest_effettivo int2 NOT NULL,
	id_voce_rubrica_dest varchar(64) NULL,
	stato_consolidamento varchar(30) NULL,
	stato_consegna varchar(2) NULL,
	flg_notif_interop_ann int2 NOT NULL DEFAULT 0,
	flg_notif_interop_ecc int2 NOT NULL DEFAULT 0,
	flg_notif_interop_conf int2 NOT NULL DEFAULT 0,
	flg_notif_interop_agg int2 NOT NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	account_destinatario_ctx varchar(2000) NULL,
	CONSTRAINT chk_t_destinatari_email_mgo_0 CHECK (((flg_tipo_destinatario)::text = ANY ((ARRAY['P'::character varying, 'CC'::character varying, 'CCN'::character varying])::text[]))),
	CONSTRAINT chk_t_destinatari_email_mgo_1 CHECK ((flg_dest_orig_invio = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_destinatari_email_mgo_2 CHECK ((flg_dest_effettivo = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_destinatari_email_mgo_3 CHECK (((stato_consolidamento)::text = ANY ((ARRAY['accettata'::character varying, 'non_accettata'::character varying, 'avvertimenti_in_consegna'::character varying, 'errori_in_consegna'::character varying, 'consegnata'::character varying, 'consegnata_parzialmente'::character varying])::text[]))),
	CONSTRAINT chk_t_destinatari_email_mgo_4 CHECK ((flg_notif_interop_ecc = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_destinatari_email_mgo_5 CHECK ((flg_notif_interop_conf = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_destinatari_email_mgo_6 CHECK ((flg_notif_interop_agg = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_destinatari_email_mgo_7 CHECK ((flg_notif_interop_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_destinatari_email_mgo_8 CHECK (((stato_consegna)::text = ANY ((ARRAY['OK'::character varying, 'KO'::character varying, 'W'::character varying, 'IP'::character varying])::text[]))),
	CONSTRAINT t_destinatari_email_mgo_pkey PRIMARY KEY (id_destinatario_email)
);
CREATE INDEX dmi_dest_email_mgo ON auri_owner_1.t_destinatari_email_mgo USING btree (auri_master.dmfn_prepare_denom_x_match(account_destinatario));
CREATE INDEX dmi_dest_email_mgo_ctx ON auri_owner_1.t_destinatari_email_mgo USING gin (to_tsvector('english'::regconfig, (account_destinatario_ctx)::text));
CREATE INDEX idx_destinatari_email_mgo_1 ON auri_owner_1.t_destinatari_email_mgo USING btree (account_destinatario, flg_tipo_destinatario);
CREATE INDEX idx_destinatari_email_mgo_2 ON auri_owner_1.t_destinatari_email_mgo USING btree (id_voce_rubrica_dest, flg_tipo_destinatario);
CREATE INDEX idx_destinatari_email_mgo_4 ON auri_owner_1.t_destinatari_email_mgo USING btree (id_email);
CREATE UNIQUE INDEX uk_destinatari_email_mgo ON auri_owner_1.t_destinatari_email_mgo USING btree (id_email, flg_tipo_destinatario, account_destinatario, id_voce_rubrica_dest);




CREATE TABLE auri_owner_1.t_download_ticket (
	ticket_id varchar(64) NOT NULL,
	uri varchar(1000) NOT NULL,
	display_filename varchar(1000) NOT NULL,
	id_ud numeric(38) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT t_download_ticket_pkey PRIMARY KEY (ticket_id)
);
CREATE INDEX idx_download_ticket_ts ON auri_owner_1.t_download_ticket USING btree (ts_ins);




CREATE TABLE auri_owner_1.t_email_mgo (
	id_email varchar(64) NOT NULL,
	message_id varchar(500) NOT NULL,
	message_id_trasporto varchar(500) NULL,
	progr_msg numeric(38) NULL,
	anno_progr_msg int2 NULL,
	registro_progr_msg varchar(10) NULL,
	progr_bozza numeric(38) NULL,
	anno_progr_bozza int2 NULL,
	id_casella varchar(64) NOT NULL,
	flg_io varchar(1) NULL,
	categoria varchar(30) NOT NULL,
	dimensione int8 NOT NULL,
	uri_email varchar(2000) NOT NULL,
	flg_uri_ricevuta int2 NOT NULL DEFAULT 0,
	flg_spam int2 NOT NULL DEFAULT 0,
	flg_stato_spam varchar(2) NULL,
	flg_ricevuta_cbs varchar(1) NULL,
	stato_consolidamento varchar(30) NULL,
	stato_invio varchar(2) NULL,
	stato_accettazione varchar(2) NULL,
	stato_consegna varchar(2) NULL,
	stato_lavorazione varchar(30) NULL,
	ts_agg_stato_lavorazione timestamp NULL,
	id_utente_agg_stato_lav varchar(64) NULL,
	nro_allegati int2 NOT NULL DEFAULT 0,
	nro_allegati_firmati int2 NOT NULL DEFAULT 0,
	flg_email_firmata int2 NOT NULL,
	flg_no_assoc_auto int2 NOT NULL DEFAULT 0,
	account_mittente varchar(1000) NULL,
	oggetto varchar(4000) NULL,
	corpo text NULL,
	avvertimenti varchar(4000) NULL,
	ts_lock timestamp NULL,
	id_utente_lock varchar(64) NULL,
	id_rec_diz_oper_lock varchar(64) NULL,
	flg_rich_conferma int2 NOT NULL DEFAULT 0,
	flg_rich_conf_lettura int2 NOT NULL DEFAULT 0,
	ts_ricezione timestamp NULL,
	ts_invio timestamp NULL,
	ts_invio_client timestamp NULL,
	ts_assegn timestamp NULL,
	id_uo_assegn varchar(64) NULL,
	id_utente_assegn varchar(64) NULL,
	flg_inviata_risposta int2 NOT NULL,
	flg_inoltrata int2 NOT NULL,
	flg_stato_prot varchar(1) NULL,
	id_rec_diz_contrassegno varchar(64) NULL,
	flg_notif_interop_ecc int2 NOT NULL DEFAULT 0,
	flg_notif_interop_conf int2 NOT NULL DEFAULT 0,
	flg_notif_interop_agg int2 NOT NULL,
	flg_notif_interop_ann int2 NOT NULL DEFAULT 0,
	liv_priorita int8 NULL,
	ts_lettura timestamp NULL,
	sigla_registro_mitt varchar(100) NULL,
	num_reg_mitt int4 NULL,
	anno_reg_mitt int2 NULL,
	ts_reg_mitt timestamp NULL,
	oggetto_reg_mitt text NULL,
	cod_azione_da_fare varchar(10) NULL,
	dett_azione_da_fare varchar(4000) NULL,
	motivo_eccezione text NULL,
	message_id_rif varchar(500) NULL,
	err_message_ric_pec text NULL,
	nro_richiesta_massiva_invio varchar(100) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	account_mittente_ctx varchar(1000) NULL,
	oggetto_ctx varchar(4000) NULL,
	corpo_ctx text NULL,
	oggetto_reg_mitt_ctx text NULL,
	CONSTRAINT chk_t_email_mgo_0 CHECK (((flg_io)::text = ANY ((ARRAY['I'::character varying, 'O'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_1 CHECK ((flg_spam = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_10 CHECK ((flg_inviata_risposta = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_11 CHECK ((flg_inoltrata = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_12 CHECK (((flg_stato_prot)::text = ANY ((ARRAY['P'::character varying, 'C'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_13 CHECK ((flg_notif_interop_ecc = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_14 CHECK ((flg_notif_interop_conf = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_15 CHECK ((flg_notif_interop_agg = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_16 CHECK ((flg_notif_interop_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_17 CHECK (((stato_lavorazione)::text = ANY ((ARRAY['lavorazione_aperta'::character varying, 'da_lavorare'::character varying, 'in_lavorazione'::character varying, 'lavorazione_conclusa'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_18 CHECK (((flg_ricevuta_cbs)::text = ANY ((ARRAY['C'::character varying, 'B'::character varying, 'S'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_19 CHECK ((flg_rich_conf_lettura = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_2 CHECK (((flg_stato_spam)::text = ANY ((ARRAY['B'::character varying, 'RS'::character varying, 'S'::character varying, 'R'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_20 CHECK (((stato_invio)::text = ANY ((ARRAY['OK'::character varying, 'KO'::character varying, 'W'::character varying, 'IP'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_21 CHECK (((stato_accettazione)::text = ANY ((ARRAY['OK'::character varying, 'KO'::character varying, 'W'::character varying, 'IP'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_22 CHECK (((stato_consegna)::text = ANY ((ARRAY['OK'::character varying, 'KO'::character varying, 'W'::character varying, 'IP'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_23 CHECK ((flg_uri_ricevuta = ANY (ARRAY[1, 0]))),
	CONSTRAINT chk_t_email_mgo_3 CHECK (((categoria)::text = ANY ((ARRAY['INTEROP_SEGN'::character varying, 'INTEROP_ECC'::character varying, 'INTEROP_CONF'::character varying, 'INTEROP_AGG'::character varying, 'INTEROP_ANN'::character varying, 'PEC'::character varying, 'ANOMALIA'::character varying, 'PEC_RIC_ACC'::character varying, 'PEC_RIC_NO_ACC'::character varying, 'PEC_RIC_PREAS_C'::character varying, 'PEC_RIC_CONS'::character varying, 'PEC_RIC_PREAVV_NO_CONS'::character varying, 'PEC_RIC_NO_CONS'::character varying, 'DELIVERY_STATUS_NOT'::character varying, 'PEO_RIC_CONF'::character varying, 'PEC_RIC_CONF'::character varying, 'ALTRO'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_4 CHECK ((nro_allegati >= 0)),
	CONSTRAINT chk_t_email_mgo_5 CHECK ((nro_allegati_firmati >= 0)),
	CONSTRAINT chk_t_email_mgo_6 CHECK ((flg_email_firmata = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_7 CHECK ((flg_no_assoc_auto = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_email_mgo_8 CHECK (((stato_consolidamento)::text = ANY ((ARRAY['in_bozza'::character varying, 'in_invio'::character varying, 'accettata'::character varying, 'non_accettata'::character varying, 'avvertimenti_in_consegna'::character varying, 'errori_in_consegna'::character varying, 'consegnata'::character varying, 'consegnata_parzialmente'::character varying])::text[]))),
	CONSTRAINT chk_t_email_mgo_9 CHECK ((flg_rich_conferma = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_email_mgo_pkey PRIMARY KEY (id_email)
);
CREATE INDEX dmi_email_mgo_corpo_ctx ON auri_owner_1.t_email_mgo USING gin (to_tsvector('english'::regconfig, corpo_ctx));
CREATE INDEX dmi_email_mgo_mitt ON auri_owner_1.t_email_mgo USING btree (auri_master.dmfn_prepare_denom_x_match(account_mittente));
CREATE INDEX dmi_email_mgo_mitt_ctx ON auri_owner_1.t_email_mgo USING gin (to_tsvector('english'::regconfig, (account_mittente_ctx)::text));
CREATE INDEX dmi_email_mgo_msgid ON auri_owner_1.t_email_mgo USING btree (auri_master.dmfn_prepare_denom_x_match(message_id));
CREATE INDEX dmi_email_mgo_ogg ON auri_owner_1.t_email_mgo USING btree (auri_master.dmfn_prepare_denom_x_match(oggetto));
CREATE INDEX dmi_email_mgo_ogg_ctx ON auri_owner_1.t_email_mgo USING gin (to_tsvector('english'::regconfig, (oggetto_ctx)::text));
CREATE INDEX dmi_email_mgo_ogg_reg_ctx ON auri_owner_1.t_email_mgo USING gin (to_tsvector('english'::regconfig, oggetto_reg_mitt));
CREATE INDEX idx_email_mgo_1 ON auri_owner_1.t_email_mgo USING btree (id_casella);
CREATE INDEX idx_email_mgo_10 ON auri_owner_1.t_email_mgo USING btree (liv_priorita);
CREATE INDEX idx_email_mgo_11 ON auri_owner_1.t_email_mgo USING btree (account_mittente);
CREATE INDEX idx_email_mgo_12 ON auri_owner_1.t_email_mgo USING btree (id_ute_ins);
CREATE INDEX idx_email_mgo_13 ON auri_owner_1.t_email_mgo USING btree (COALESCE(ts_invio, ts_invio_client));
CREATE INDEX idx_email_mgo_14 ON auri_owner_1.t_email_mgo USING btree (ts_ricezione);
CREATE INDEX idx_email_mgo_15 ON auri_owner_1.t_email_mgo USING btree (ts_invio);
CREATE INDEX idx_email_mgo_2 ON auri_owner_1.t_email_mgo USING btree (categoria);
CREATE INDEX idx_email_mgo_3 ON auri_owner_1.t_email_mgo USING btree (dimensione);
CREATE INDEX idx_email_mgo_4 ON auri_owner_1.t_email_mgo USING btree (stato_consolidamento);
CREATE INDEX idx_email_mgo_5 ON auri_owner_1.t_email_mgo USING btree (ts_lock);
CREATE INDEX idx_email_mgo_6 ON auri_owner_1.t_email_mgo USING btree (id_utente_lock);
CREATE INDEX idx_email_mgo_7 ON auri_owner_1.t_email_mgo USING btree (ts_assegn);
CREATE INDEX idx_email_mgo_8 ON auri_owner_1.t_email_mgo USING btree (id_uo_assegn);
CREATE INDEX idx_email_mgo_9 ON auri_owner_1.t_email_mgo USING btree (id_utente_assegn);
CREATE INDEX idx_email_mgo_anno_bozza ON auri_owner_1.t_email_mgo USING btree (anno_progr_bozza);
CREATE INDEX idx_email_mgo_contr ON auri_owner_1.t_email_mgo USING btree (id_rec_diz_contrassegno);
CREATE INDEX idx_email_mgo_id_trasp ON auri_owner_1.t_email_mgo USING btree (message_id_trasporto);
CREATE INDEX idx_email_mgo_next_act ON auri_owner_1.t_email_mgo USING btree (cod_azione_da_fare);
CREATE INDEX idx_email_mgo_progr ON auri_owner_1.t_email_mgo USING btree (progr_msg);
CREATE INDEX idx_email_mgo_progr_anno ON auri_owner_1.t_email_mgo USING btree (anno_progr_msg);
CREATE INDEX idx_email_mgo_progrb ON auri_owner_1.t_email_mgo USING btree (progr_bozza);
CREATE INDEX idx_email_mgo_ts_agg_stl ON auri_owner_1.t_email_mgo USING btree (ts_agg_stato_lavorazione);
CREATE INDEX idx_email_mgo_ts_ins ON auri_owner_1.t_email_mgo USING btree (ts_ins);
CREATE INDEX idx_email_mgo_uri ON auri_owner_1.t_email_mgo USING btree (uri_email);
CREATE INDEX idx_email_mgo_ute_agg_stl ON auri_owner_1.t_email_mgo USING btree (id_utente_agg_stato_lav);
CREATE UNIQUE INDEX uk_email_mgo_1 ON auri_owner_1.t_email_mgo USING btree (message_id, id_casella);




CREATE TABLE auri_owner_1.t_email_vs_utenti (
	id_email varchar(64) NOT NULL,
	id_utente varchar(64) NOT NULL,
	id_rec_diz_stato_email varchar(64) NOT NULL,
	ts_agg_stato timestamp NOT NULL DEFAULT LOCALTIMESTAMP,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT t_email_vs_utenti_pkey PRIMARY KEY (id_email, id_utente)
);
CREATE INDEX idx_email_vs_utenti_1 ON auri_owner_1.t_email_vs_utenti USING btree (id_utente, id_rec_diz_stato_email);
CREATE INDEX idx_email_vs_utenti_2 ON auri_owner_1.t_email_vs_utenti USING btree (ts_agg_stato, id_utente);
CREATE UNIQUE INDEX uk_email_vs_utenti_1 ON auri_owner_1.t_email_vs_utenti USING btree (id_utente, id_email);




CREATE TABLE auri_owner_1.t_estensioni_fmt_dig (
	id_dig_format varchar(64) NOT NULL,
	estensione varchar(5) NOT NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	CONSTRAINT chk_t_estensioni_fmt_dig_0 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_estensioni_fmt_dig_pkey PRIMARY KEY (id_dig_format, estensione)
);
CREATE INDEX idx_estensione_fmt_dig_2 ON auri_owner_1.t_estensioni_fmt_dig USING btree (estensione);
CREATE INDEX idx_estensioni_fmt_dig_ts ON auri_owner_1.t_estensioni_fmt_dig USING btree (ts_ultimo_agg);




CREATE TABLE auri_owner_1.t_folder_caselle (
	id_folder_casella varchar(64) NOT NULL,
	id_casella varchar(64) NOT NULL,
	id_folder_sup varchar(64) NULL,
	nome_folder varchar(150) NOT NULL,
	classificazione varchar(2000) NOT NULL,
	flg_chiuso int2 NOT NULL DEFAULT 0,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	id_ute_ultimo_agg varchar(64) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT chk_t_folder_caselle_0 CHECK ((flg_chiuso = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_folder_caselle_pkey PRIMARY KEY (id_folder_casella)
);
CREATE INDEX idx_folder_caselle_1 ON auri_owner_1.t_folder_caselle USING btree (id_casella);
CREATE INDEX idx_folder_caselle_2 ON auri_owner_1.t_folder_caselle USING btree (id_folder_sup);
CREATE INDEX idx_folder_caselle_3 ON auri_owner_1.t_folder_caselle USING btree (classificazione);
CREATE INDEX idx_folder_caselle_4 ON auri_owner_1.t_folder_caselle USING btree (nome_folder);




CREATE TABLE auri_owner_1.t_mail_config_key_x_gui (
	config_key varchar(100) NOT NULL,
	id_sp_aoo numeric(38) NULL,
	meaning varchar(1000) NOT NULL,
	"key_type" varchar(10) NOT NULL DEFAULT 'string'::character varying,
	valori_ammessi auri_owner_1.dmva_shortstring NULL,
	flg_obblig int2 NOT NULL DEFAULT 1,
	CONSTRAINT t_mail_config_key_x_gui_c_ob CHECK ((flg_obblig = ANY (ARRAY[1, 0]))),
	CONSTRAINT t_mail_config_key_x_gui_c_tp CHECK (((key_type)::text = ANY ((ARRAY['string'::character varying, 'int'::character varying, 'enum'::character varying, 'password'::character varying, 'path'::character varying])::text[])))
);
CREATE UNIQUE INDEX t_mail_config_key_x_gui_uk ON auri_owner_1.t_mail_config_key_x_gui USING btree (config_key, id_sp_aoo);




CREATE TABLE auri_owner_1.t_membri_mailing_list (
	id_voce_rubrica_mailing_list varchar(64) NOT NULL,
	id_voce_rubrica_membro varchar(64) NOT NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT t_membri_mailing_list_pkey PRIMARY KEY (id_voce_rubrica_mailing_list, id_voce_rubrica_membro)
);
CREATE INDEX idx_membri_mailing_list_1 ON auri_owner_1.t_membri_mailing_list USING btree (id_voce_rubrica_membro);




CREATE TABLE auri_owner_1.t_mimetype_fmt_dig (
	id_dig_format varchar(64) NOT NULL,
	mimetype varchar(100) NOT NULL,
	rif_reader varchar(100) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	CONSTRAINT chk_t_mimetype_fmt_dig_0 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_mimetype_fmt_dig_pkey PRIMARY KEY (id_dig_format, mimetype)
);
CREATE INDEX idx_mimetype_fmt_dig ON auri_owner_1.t_mimetype_fmt_dig USING btree (ts_ultimo_agg);
CREATE INDEX idx_mimetype_fmt_dig_1 ON auri_owner_1.t_mimetype_fmt_dig USING btree (mimetype);




CREATE TABLE auri_owner_1.t_not_email_generate (
	id_schedulazione varchar(64) NOT NULL,
	ci_applicazione varchar(30) NOT NULL,
	ci_istanza_applicazione varchar(30) NOT NULL,
	ts_previsto_x_evt timestamp NULL,
	notifica_nro numeric(38) NOT NULL DEFAULT 1,
	id_email_notif varchar(64) NOT NULL,
	dett_motivo_notifica text NULL
);
CREATE INDEX idx_not_email_generate_0 ON auri_owner_1.t_not_email_generate USING btree (id_schedulazione);
CREATE INDEX idx_not_email_generate_1 ON auri_owner_1.t_not_email_generate USING btree (ci_applicazione, ci_istanza_applicazione);
CREATE INDEX idx_not_email_generate_2 ON auri_owner_1.t_not_email_generate USING btree (ts_previsto_x_evt);
CREATE UNIQUE INDEX uk_not_email_generate ON auri_owner_1.t_not_email_generate USING btree (id_email_notif);




CREATE TABLE auri_owner_1.t_parameters (
	par_key varchar(50) NOT NULL,
	str_value varchar(1000) NULL,
	date_value timestamp NULL,
	num_value int8 NULL,
	setting_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	meaning varchar(4000) NOT NULL,
	CONSTRAINT t_parameters_pkey PRIMARY KEY (par_key)
);




CREATE TABLE auri_owner_1.t_profili_fruitori_mgo (
	id_rel_fruitore_casella varchar(64) NOT NULL,
	profilo varchar(100) NOT NULL,
	flg_incl_fruitori_sub int2 NOT NULL DEFAULT 0,
	flg_ann int2 NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_profili_fruitori_mgo_0 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_profili_fruitori_mgo_1 CHECK (((profilo)::text = ANY ((ARRAY['mittente'::character varying, 'gestore'::character varying, 'amministratore'::character varying, 'smistatore'::character varying, 'destinatario_per_competenza'::character varying])::text[]))),
	CONSTRAINT chk_t_profili_fruitori_mgo_2 CHECK ((flg_incl_fruitori_sub = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_profili_fruitori_mgo_pkey PRIMARY KEY (id_rel_fruitore_casella, profilo)
);




CREATE TABLE auri_owner_1.t_profili_fruitori_mgo_bck (
	id_rel_fruitore_casella varchar(64) NOT NULL,
	profilo varchar(100) NOT NULL,
	flg_incl_fruitori_sub int2 NOT NULL,
	flg_ann int2 NOT NULL,
	ts_ins timestamp NOT NULL,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL,
	id_ute_ultimo_agg varchar(64) NULL
);




CREATE TABLE auri_owner_1.t_profili_utenti_mgo (
	id_rel_utente_fruitore varchar(64) NOT NULL,
	profilo varchar(100) NOT NULL,
	id_rel_fruitore_casella varchar(64) NOT NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_profili_utenti_mgo_0 CHECK (((profilo)::text = ANY ((ARRAY['mittente'::character varying, 'gestore'::character varying, 'amministratore'::character varying, 'smistatore'::character varying, 'destinatario_per_competenza'::character varying])::text[]))),
	CONSTRAINT chk_t_profili_utenti_mgo_1 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_profili_utenti_mgo_pkey PRIMARY KEY (id_rel_utente_fruitore, profilo, id_rel_fruitore_casella)
);
CREATE INDEX idx_profili_utenti_mgo_1 ON auri_owner_1.t_profili_utenti_mgo USING btree (id_rel_fruitore_casella);




CREATE TABLE auri_owner_1.t_reg_est_vs_email (
	id_reg_est_email varchar(64) NOT NULL,
	id_email_ricevuta varchar(64) NOT NULL,
	id_destinatario_email varchar(64) NULL,
	id_email_inviata varchar(64) NULL,
	cod_amministrazione varchar(150) NULL,
	cod_aoo varchar(150) NULL,
	sigla_registro varchar(30) NULL,
	anno_reg int2 NOT NULL,
	num_reg int4 NOT NULL,
	ts_reg timestamp NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT t_reg_est_vs_email_pkey PRIMARY KEY (id_reg_est_email)
);
CREATE INDEX idx_reg_est_vs_email ON auri_owner_1.t_reg_est_vs_email USING btree (num_reg, anno_reg, cod_aoo, cod_amministrazione, sigla_registro);
CREATE INDEX idx_reg_est_vs_email_1 ON auri_owner_1.t_reg_est_vs_email USING btree (id_email_inviata);
CREATE INDEX idx_reg_est_vs_email_2 ON auri_owner_1.t_reg_est_vs_email USING btree (id_destinatario_email);
CREATE INDEX uk_reg_est_vs_email_3 ON auri_owner_1.t_reg_est_vs_email USING btree (id_email_ricevuta);




CREATE TABLE auri_owner_1.t_reg_prot_vs_email (
	id_reg_prot_email varchar(64) NOT NULL,
	id_email varchar(64) NOT NULL,
	categoria_reg varchar(2) NOT NULL,
	sigla_registro varchar(30) NULL,
	anno_reg int2 NOT NULL,
	num_reg int4 NOT NULL,
	ts_reg timestamp NULL,
	id_prov_reg varchar(150) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_reg_prot_vs_email_0 CHECK (((categoria_reg)::text = ANY ((ARRAY['PG'::character varying, 'PP'::character varying, 'R'::character varying, 'E'::character varying, 'A'::character varying, 'I'::character varying])::text[]))),
	CONSTRAINT t_reg_prot_vs_email_pkey PRIMARY KEY (id_reg_prot_email)
);
CREATE INDEX idx_reg_prot_vs_email_1 ON auri_owner_1.t_reg_prot_vs_email USING btree (num_reg, anno_reg);
CREATE INDEX idx_reg_prot_vs_email_2 ON auri_owner_1.t_reg_prot_vs_email USING btree (ts_reg);
CREATE INDEX idx_reg_prot_vs_email_3 ON auri_owner_1.t_reg_prot_vs_email USING btree (id_prov_reg);
CREATE INDEX idx_reg_prot_vs_email_em ON auri_owner_1.t_reg_prot_vs_email USING btree (id_email);
CREATE UNIQUE INDEX uk_reg_prot_vs_email_1 ON auri_owner_1.t_reg_prot_vs_email USING btree (id_email, categoria_reg, sigla_registro, anno_reg, num_reg);




CREATE TABLE auri_owner_1.t_reinvio_email_mgo (
	id_email varchar(64) NOT NULL,
	nro_tentativi int2 NOT NULL DEFAULT 0,
	ts_ultimo_tentativo timestamp NULL,
	ultimo_errore_invio varchar(4000) NULL,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_reinvio_email_mgo_1 CHECK ((nro_tentativi > 0)),
	CONSTRAINT t_reinvio_email_mgo_pkey PRIMARY KEY (id_email)
);




CREATE TABLE auri_owner_1.t_rel_caselle_fruitori (
	id_rel_fruitore_casella varchar(64) NOT NULL,
	id_casella varchar(64) NOT NULL,
	id_fruitore_casella varchar(64) NOT NULL,
	flg_utilizzo_x_ricezione int2 NOT NULL,
	flg_utilizzo_x_invio int2 NOT NULL,
	flg_ann int2 NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_rel_caselle_fruitori_0 CHECK ((flg_utilizzo_x_ricezione = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_rel_caselle_fruitori_1 CHECK ((flg_utilizzo_x_invio = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_rel_caselle_fruitori_2 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_rel_caselle_fruitori_pkey PRIMARY KEY (id_rel_fruitore_casella)
);
CREATE INDEX idx_rel_caselle_fruitori_1 ON auri_owner_1.t_rel_caselle_fruitori USING btree (id_casella);
CREATE UNIQUE INDEX uk_rel_caselle_fruitori_1 ON auri_owner_1.t_rel_caselle_fruitori USING btree (id_fruitore_casella, id_casella);




CREATE TABLE auri_owner_1.t_rel_caselle_fruitori_bck (
	id_rel_fruitore_casella varchar(64) NOT NULL,
	id_casella varchar(64) NOT NULL,
	id_fruitore_casella varchar(64) NOT NULL,
	flg_utilizzo_x_ricezione int2 NOT NULL,
	flg_utilizzo_x_invio int2 NOT NULL,
	flg_ann int2 NOT NULL,
	ts_ins timestamp NOT NULL,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL,
	id_ute_ultimo_agg varchar(64) NULL
);




CREATE TABLE auri_owner_1.t_rel_utenti_vs_fruitori (
	id_rel_utente_fruitore varchar(64) NOT NULL,
	id_utente varchar(64) NOT NULL,
	id_fruitore_casella varchar(64) NOT NULL,
	flg_app_gerarchica numeric(1) NULL DEFAULT 0,
	flg_ann numeric(1) NULL DEFAULT 0,
	ts_ins timestamp NOT NULL,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL,
	id_ute_ultimo_agg varchar(64) NULL
);




CREATE TABLE auri_owner_1.t_user_preferences (
	userid varchar(64) NOT NULL,
	pref_key varchar(150) NOT NULL,
	pref_name varchar(150) NOT NULL DEFAULT 'DEFAULT'::character varying,
	value text NULL,
	setting_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_uo numeric(38) NULL,
	flg_visib_sottouo int2 NULL,
	flg_gest_sottouo int2 NULL
);




CREATE TABLE auri_owner_1.t_utenti_mod_pec (
	id_utente varchar(64) NOT NULL,
	cognome varchar(100) NOT NULL,
	nome varchar(30) NOT NULL,
	cod_fiscale varchar(16) NULL,
	username varchar(30) NOT NULL,
	"password" varchar(100) NULL,
	flg_attivo int2 NOT NULL DEFAULT 1,
	flg_amministratore int2 NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_utenti_mod_pec_0 CHECK ((flg_attivo = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_utenti_mod_pec_pkey PRIMARY KEY (id_utente)
);
CREATE INDEX idx_utenti_mod_pec_1 ON auri_owner_1.t_utenti_mod_pec USING btree (cognome);
CREATE INDEX idx_utenti_mod_pec_2 ON auri_owner_1.t_utenti_mod_pec USING btree (nome);
CREATE INDEX idx_utenti_mod_pec_4 ON auri_owner_1.t_utenti_mod_pec USING btree (cod_fiscale);
CREATE UNIQUE INDEX uk_utenti_mod_pec_1 ON auri_owner_1.t_utenti_mod_pec USING btree (username);




CREATE TABLE auri_owner_1.t_val_dizionario (
	id_rec_diz varchar(64) NOT NULL,
	id_sez_diz varchar(64) NOT NULL,
	valore varchar(250) NOT NULL,
	codice varchar(50) NULL,
	flg_dismesso int2 NOT NULL DEFAULT 0,
	specializza_val_diz varchar(64) NULL,
	flg_di_sistema int2 NOT NULL DEFAULT 0,
	flg_ann int2 NOT NULL DEFAULT 0,
	ts_ins timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ins varchar(64) NULL,
	ts_ultimo_agg timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_ute_ultimo_agg varchar(64) NULL,
	CONSTRAINT chk_t_val_dizionario_0 CHECK ((flg_di_sistema = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_val_dizionario_1 CHECK ((flg_dismesso = ANY (ARRAY[0, 1]))),
	CONSTRAINT chk_t_val_dizionario_2 CHECK ((flg_ann = ANY (ARRAY[0, 1]))),
	CONSTRAINT t_val_dizionario_pkey PRIMARY KEY (id_rec_diz)
);
CREATE INDEX idx_val_dizionario_1 ON auri_owner_1.t_val_dizionario USING btree (id_rec_diz, codice);
CREATE INDEX idx_val_dizionario_ts ON auri_owner_1.t_val_dizionario USING btree (ts_ultimo_agg);
CREATE UNIQUE INDEX uk_val_dizionario_1 ON auri_owner_1.t_val_dizionario USING btree (id_sez_diz, valore);




CREATE TABLE auri_owner_1.test (
	f0 serial NOT NULL,
	f1 int4 NULL,
	f2 text NULL
);




CREATE TABLE auri_owner_1.test_import (
	test auri_master.dmo_user_domain[] NULL
);




CREATE TABLE auri_owner_1.utilizzatori_storage (
	id_utilizzatore varchar(150) NOT NULL,
	id_storage varchar(50) NOT NULL,
	CONSTRAINT utilizzatori_storage_pkey PRIMARY KEY (id_utilizzatore)
);




CREATE TABLE auri_owner_1.utilizzatori_storage_h (
	id_utilizzatore varchar(150) NOT NULL,
	ts_fino_al timestamp NOT NULL,
	id_storage varchar(50) NOT NULL,
	CONSTRAINT utilizzatori_storage_h_pkey PRIMARY KEY (id_utilizzatore, ts_fino_al)
);
