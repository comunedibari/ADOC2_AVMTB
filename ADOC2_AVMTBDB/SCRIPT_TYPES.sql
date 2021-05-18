
CREATE TYPE auri_owner_1.adddocout AS (
	idudout int4,
	iddocout int4,
	urixmlout text,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);



CREATE TYPE auri_owner_1.addfodler_out AS (
	idfolderout int4,
	errcontextout varchar(1000),
	errcodeout varchar(1000),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.addfolder_out AS (
	idfodlerout int4,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.addtorecent_out AS (
	errcontextout varchar(1000),
	errcodeout varchar,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.aggacl_out AS (
	matriceaclio dmto_matrice_valori,
	returncode int4);


CREATE TYPE auri_owner_1.aggautomaticoaclfld_postin_out AS (
	errcontextout varchar(1000),
	errcodeout varchar,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.aggiornacoluserdefined_out AS (
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.aggprocrecdahistory AS (
	errmsgout varchar,
	errcontextout varchar,
	errcodeout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.aggprocrecdahistory_out AS (
	errmsgout varchar,
	errcontextout varchar,
	errcodeout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.aggscaddopoopersudoc_out AS (
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.archiviaemail_out AS (
	esitiout text,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.attrcustomtabrec AS (
	var_key int4,
	attr_name varchar(100),
	nro_occ_value int4,
	str_value varchar(4000),
	date_value date,
	num_value numeric,
	attr_type varchar(100),
	attr_format varchar(100),
	attr_scale numeric(1,0),
	attr_label varchar(100),
	flg_protected numeric(1,0));


CREATE TYPE auri_owner_1.attributodefrectype AS (
	attr_name varchar(30),
	attr_type varchar(30));


CREATE TYPE auri_owner_1.bittabrec AS (
	var_key int4,
	var_value numeric(1,0));


CREATE TYPE auri_owner_1.breakpoint AS (
	func oid,
	linenumber int4,
	targetname text);


CREATE TYPE auri_owner_1.calcoladimpaginaout AS (
	dimpaginaout int4,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.checkpath_out AS (
	idnodoout int4,
	pathnonesistenteout varchar,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.convertdatisogginterno_out AS (
	datisoggintout _varchar,
	errcontextout varchar(1000),
	errcodeout varchar,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.convertxmltoaltriattrtab_out AS (
	altriattributitabout _dmo_attr_value,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.creadefaultaclnewud_out AS (
	aclxmlout text,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.ctrldatiindirizzo_out AS (
	datiindirizzoxmlio text,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.ctrldatisoggesterno_out AS (
	datisoggxmlio text,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.ctrldatisogginterno_out AS (
	datisoggxmlio text,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.datetabrec AS (
	var_key varchar(250),
	var_value date);


CREATE TYPE auri_owner_1.datetabtype AS (
	var_key varchar(250),
	var_value date);


CREATE TYPE auri_owner_1.defattrxentitarectype AS (
	attr_name varchar(250),
	suff_attr_name varchar(10),
	attr_label varchar(100),
	attr_label_x_filtro varchar(150),
	attr_type varchar(30),
	attr_name_sup varchar(250),
	attr_size numeric(4,0),
	attr_scale numeric(1,0),
	attr_format varchar(30),
	num_min_value numeric,
	num_max_value numeric,
	date_min_value date,
	date_max_value date,
	case_restriction varchar(10),
	regular_expr varchar(150),
	attr_width numeric(4,0),
	attr_heigth numeric(4,0),
	attr_def_value varchar(4000),
	attr_list_query varchar(4000),
	attr_value_list varchar(4000),
	flg_visibile numeric(1,0),
	flg_modificabile numeric(1,0),
	flg_obblig numeric(1,0),
	max_num_values int4,
	lista_lookup varchar(30),
	nro_col_lookup int4,
	filtro_in_lookup varchar(80),
	flg_solo_val_da_lookup numeric(1,0),
	flg_da_indicizzare numeric(1,0),
	flg_protected numeric(1,0),
	flg_valori_univoci numeric(1,0),
	nro_riga_in_sup numeric(2,0),
	wsdl_url varchar(1000),
	prov_property varchar(1000),
	cod_gruppo varchar(100),
	des_gruppo varchar(250),
	xml_request_ws text);


CREATE TYPE auri_owner_1.dmo_ace AS (
	flg_tipo varchar(3),
	id_in_anag_prov int4,
	flg_incl_sottouo numeric,
	vs_livello_so int4,
	vs_id_uo int4,
	vs_cod_tipo_uo int4,
	nro_pos_in_acl int4,
	flg_visib_dati numeric(1,0),
	flg_visib_file numeric(1,0),
	flg_modifica_dati numeric(1,0),
	flg_modifica_file numeric(1,0),
	flg_modifica_acl numeric(1,0),
	flg_eliminazione numeric(1,0),
	flg_copia numeric(1,0),
	flg_recupero numeric(1,0),
	flg_mod_cont_ud numeric(1,0),
	flg_mod_cont_folder numeric(1,0),
	flg_acc_lim_ver_pubbl numeric(1,0));


CREATE TYPE auri_owner_1.dmo_ace_attivita AS (
	nro_pos_in_acl int4,
	flg_lim_x_ass numeric(1,0),
	nro_ord_seq numeric(2,0),
	flg_visibilita numeric(1,0),
	flg_esecuzione numeric(1,0));


CREATE TYPE auri_owner_1.dmo_ace_attribute AS (
	id_sp_aoo int4,
	nro_pos_in_acl int4,
	flg_visibilita numeric(1,0),
	flg_modifica numeric(1,0));


CREATE TYPE auri_owner_1.dmo_ace_folder AS (
	flg_tipo varchar(3),
	id_in_anag_prov int4,
	flg_incl_sottouo numeric(1,0),
	vs_livello_so int4,
	vs_id_uo int4,
	vs_cod_tipo_uo varchar(10),
	nro_pos_in_acl int4,
	flg_visibilita numeric(1,0),
	flg_modifica_dati numeric(1,0),
	flg_modifica_acl numeric(1,0),
	flg_eliminazione numeric(1,0),
	flg_copia numeric(1,0),
	flg_recupero numeric(1,0),
	flg_mod_cont_ud numeric(1,0),
	flg_mod_cont_folder numeric(1,0),
	flg_ereditato numeric(1,0));


CREATE TYPE auri_owner_1.dmo_ace_ud AS (
	flg_tipo varchar(3),
	id_in_anag_prov int4,
	flg_incl_sottouo numeric(1,0),
	vs_livello_so int4,
	vs_id_uo int4,
	vs_cod_tipo_uo varchar(10),
	nro_pos_in_acl int4,
	flg_visib_dati numeric(1,0),
	flg_visib_file numeric(1,0),
	visib_su_files varchar(250),
	flg_modifica_dati numeric(1,0),
	flg_modifica_file numeric(1,0),
	modifica_su_files varchar(250),
	flg_modifica_acl numeric(1,0),
	flg_eliminazione numeric(1,0),
	flg_copia numeric(1,0),
	flg_recupero numeric(1,0),
	flg_acc_lim_ver_pubbl numeric(1,0),
	lim_ver_pubbl_su_file varchar(250),
	flg_ereditato numeric(1,0),
	id_ud numeric(22,0));


CREATE TYPE auri_owner_1.dmo_ace_workspace AS (
	flg_tipo varchar(3),
	id_in_anag_prov int4,
	flg_incl_sottouo numeric(1,0),
	vs_livello_so int4,
	vs_id_uo int4,
	vs_cod_tipo_uo varchar(20),
	nro_pos_in_acl int4,
	flg_visibilita numeric(1,0),
	flg_modifica_dati numeric(1,0),
	flg_modifica_acl numeric(1,0),
	flg_eliminazione numeric(1,0),
	flg_recupero numeric(1,0),
	flg_mod_contenuto numeric(1,0));


CREATE TYPE auri_owner_1.dmo_addverdoc_out AS (
	nroprogrverio int4,
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_attr_def AS (
	attr_name varchar(250),
	nro_posizione int4,
	flg_obblig numeric(1,0),
	max_num_values int4,
	dt_inizio_vld date,
	dt_fine_vld date,
	flg_x_timbro numeric(1,0),
	flg_x_intitolazione numeric(1,0),
	cod_tab_sez_gui varchar(30),
	des_tab_sez_gui varchar(250));


CREATE TYPE auri_owner_1.dmo_attr_value AS (
	attr_name varchar(250),
	nro_occ_value int4,
	str_value varchar(4000),
	num_value numeric,
	date_value date,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4,
	id_valore_attr int4,
	id_valore_attr_sup int4);


CREATE TYPE auri_owner_1.dmo_bookmark AS (
	posizione varchar(3),
	nome varchar(50),
	valore varchar(4000));


CREATE TYPE auri_owner_1.dmo_cambio_stato AS (
	stato varchar(100),
	setting_time timestamp,
	id_user int4,
	err_warn_code varchar(4000),
	err_warn_msg text);


CREATE TYPE auri_owner_1.dmo_ci_x_sp_aoo AS (
	id_sp_aoo int4,
	cardinalita int4,
	cid varchar(250),
	dt_inizio_vld date,
	dt_fine_vld date);


CREATE TYPE auri_owner_1.dmo_civico_toponimo AS (
	ci_civico varchar(30),
	civico varchar(30),
	civico_numero int4,
	civico_appendice varchar(100),
	barrato varchar(30),
	stato varchar(100),
	dt_inizio_vld date,
	dt_fine_vld date,
	localita varchar(250),
	cap varchar(5),
	ci_quartiere varchar(30),
	ci_zona varchar(30),
	coordinata_x numeric,
	coordinata_y numeric,
	coordinata_z numeric,
	ts_ins date,
	ts_last_upd date);


CREATE TYPE auri_owner_1.dmo_classif_ud AS (
	id_classificazione int4,
	ts date,
	id_user int4);


CREATE TYPE auri_owner_1.dmo_completadatinodoso_return AS (
	valcolrigaxmltabio varchar,
	datixrestrizionitabio valuevartabtype,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_componente_gruppo AS (
	flg_tipo varchar(3),
	id_in_anag_prov int4,
	flg_incl_sottouo numeric(1,0),
	flg_incl_scrivanie numeric(1,0));


CREATE TYPE auri_owner_1.dmo_componente_gruppo_est AS (
	flg_tipo varchar(1),
	id_in_anag_prov int4);


CREATE TYPE auri_owner_1.dmo_componicriterisuattrdin_out AS (
	whereconditiontoaddout varchar,
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_connection_info AS (
	iduserout int4,
	flgtpdominioautout int4,
	iddominioautout int4,
	codapplesternaout varchar(30),
	codistanzaapplestout varchar(30),
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_convorderbyvalueinsegn_indout_out AS (
	deflivellitittabio dmto_liv_gerarchia,
	indiceclassifxguiout varchar,
	returncode varchar);


CREATE TYPE auri_owner_1.dmo_convorderbyvalueinsegnatura_out AS (
	deflivellitittabio dmto_liv_gerarchia,
	returncode varchar(250));


CREATE TYPE auri_owner_1.dmo_credenz_aut_ext AS (
	cod_applicazione varchar(30),
	cod_id_istanza_appl varchar(30),
	username varchar(30),
	"password" varchar(30),
	ci_user varchar(30),
	id_uo_reg_default int4);


CREATE TYPE auri_owner_1.dmo_ctrl_ba_activity AS (
	id_sp_aoo int4,
	prov_ci_ty_flusso_wf varchar(100),
	flg_ba varchar(1),
	activity_name varchar(250),
	esiti varchar(1000),
	flg_warning numeric(1,0),
	nro_min_occ int4);


CREATE TYPE auri_owner_1.dmo_ctrl_x_doc AS (
	flg_file numeric(1,0),
	flg_firmato numeric(1,0));


CREATE TYPE auri_owner_1.dmo_decodifica AS (
	int_value varchar(100),
	display_value varchar(4000),
	categoria varchar(1000));


CREATE TYPE auri_owner_1.dmo_def_add_x_acl AS (
	id_profilo int4,
	accesso_a_livello_so numeric(1,0),
	opz_accesso varchar(100),
	flg_estendi_uo_sup numeric(1,0),
	opz_accesso_uo_sup varchar(100),
	flg_estendi_uo_incluse numeric(1,0),
	opz_accesso_uo_incl varchar(100));


CREATE TYPE auri_owner_1.dmo_def_val_imp_personale AS (
	valore_primario varchar(1000),
	valore_dettaglio varchar(1000));


CREATE TYPE auri_owner_1.dmo_delega AS (
	id_user int4,
	dt_inizio_vld date,
	dt_fine_vld date,
	natura_motivi varchar(500));


CREATE TYPE auri_owner_1.dmo_delega_ext AS (
	id_scrivania int4,
	id_user numeric,
	dt_inizio_vld date,
	dt_fine_vld date,
	natura_motivi varchar(500));


CREATE TYPE auri_owner_1.dmo_denom_tipizzata AS (
	denominazione varchar(500),
	cardinalita int4,
	tipo varchar(150));


CREATE TYPE auri_owner_1.dmo_dest_trasm AS (
	nro_pos_tra_dest int4,
	activity_result_code varchar(250),
	flg_all_sogg_in_dest numeric(1,0));


CREATE TYPE auri_owner_1.dmo_dett_att_x_esito AS (
	activity_result_code varchar(250),
	flg_tp_mod_ass varchar(1));


CREATE TYPE auri_owner_1.dmo_dmfn_load_combo AS (
	listaxmlout text,
	listavalori varchar(200),
	flganablexml int4,
	errcontextout varchar(1000),
	errcodeout varchar(200),
	errmsgout varchar(1000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_doc_attr_value AS (
	nro_progr_ver numeric(4,0),
	nro_occ_value int4,
	str_value varchar(4000),
	num_value numeric,
	date_value date,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4,
	id_valore_attr int4,
	id_valore_attr_sup int4,
	attr_name varchar(250));


CREATE TYPE auri_owner_1.dmo_documents_altri_attributi AS (
	id_doc numeric(22,0),
	attr_name varchar(250),
	nro_occ_value int4,
	str_value varchar(4000),
	num_value numeric,
	date_value date,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4,
	id_valore_attr int4,
	id_valore_attr_sup int4,
	nro_progr_ver numeric);


CREATE TYPE auri_owner_1.dmo_documents_estensori AS (
	id_doc int4,
	estensore int4);


CREATE TYPE auri_owner_1.dmo_documents_firme_interne AS (
	id_doc numeric(22,0),
	id_user int4,
	a_nome_di int4,
	ts date,
	flg_elettronica numeric,
	dt_scadenza date,
	intestazione_certif varchar(4000),
	flg_marca_temp numeric,
	ts_marca_temp date);


CREATE TYPE auri_owner_1.dmo_documents_info_storico AS (
	id_doc numeric(22,0),
	ts_fine_vld date,
	id_user int4,
	motivi varchar(4000),
	estremi_atto dmo_reg_num_ud,
	old_values dmva_attr_values);


CREATE TYPE auri_owner_1.dmo_documents_info_storico_old_values AS (
	id_doc numeric,
	attr_name varchar(250),
	nro_occ_value int4,
	str_value varchar(4000),
	num_value numeric,
	date_value date,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4,
	id_valore_attr int4,
	id_valore_attr_sup int4);


CREATE TYPE auri_owner_1.dmo_documents_uff_produttori AS (
	id_doc numeric(22,0),
	uff_produttore numeric(10,0));


CREATE TYPE auri_owner_1.dmo_documents_versioni AS (
	id_doc numeric(22,0),
	nro_progr numeric,
	cod_ver varchar(30),
	display_filename varchar(1000),
	id_formato_el int4,
	mimetype varchar(250),
	dimensione numeric,
	flg_firmato numeric,
	rif_in_repository varchar(1000),
	flg_pubblicata numeric,
	flg_ann numeric,
	note varchar(1000),
	impronta varchar(255),
	algoritmo_impronta varchar(30),
	encoding_impronta varchar(10),
	nro_progr_bkver numeric,
	flg_da_scansione numeric,
	dt_scansione date,
	id_user_scansione int4,
	spec_scansione varchar(1000),
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_estremi_indirizzo AS (
	cod_istat_stato_estero varchar(3),
	nome_stato_estero varchar(250),
	cod_istat_comune varchar(6),
	nome_citta varchar(150),
	localita varchar(250),
	zona varchar(50),
	complemento_ind varchar(50),
	ci_toponomastico varchar(30),
	tipo_toponimo varchar(100),
	indirizzo varchar(250),
	civico varchar(10),
	esponente_civico varchar(10),
	interno varchar(30),
	scala varchar(10),
	piano int4,
	cap varchar(5));


CREATE TYPE auri_owner_1.dmo_flgonofbystringtype AS (
	cardinalita int4,
	chiave varchar(1000),
	valore numeric);


CREATE TYPE auri_owner_1.dmo_folder_app_folder AS (
	id_folder_app int4,
	nro_pos_vs_folder int4,
	motivo_rel varchar(250),
	nro_pos_in_folder_app int4,
	flg_locked numeric(1,0),
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_folder_folder_appartenenza AS (
	id_folder numeric(22,0),
	id_folder_app int4,
	nro_pos_vs_folder int4,
	motivo_rel varchar(250),
	nro_pos_in_folder_app int4,
	flg_locked numeric,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_folder_ud AS (
	id_folder int4,
	nro_pos_folder_vs_ud int4,
	cod_ruolo_ud varchar(10),
	motivo_rel varchar(250),
	nro_pos_ud_in_folder int4,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_get_variables_from_xml_sezcache AS (
	l_result _dmo_var_sez_cache,
	errcontextout varchar(1000),
	errcodeout varchar(200),
	errmsgout varchar(1000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_get_varsemplice_out AS (
	valorevariabileout varchar,
	flgvarfoundtabio _flgfoundvartabrec,
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_getabilinvioricezione_out AS (
	flgabilricezionepecout int4,
	flgabilricezionepeoout int4,
	flgabilinviopecout int4,
	flgabilinviopeoout int4,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_getconnschema_return AS (
	schemaout varchar,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_getestremiregnumud_return AS (
	codcategoriaregio varchar,
	siglaregio varchar,
	annoregout int4,
	numregout int4,
	tsregout date,
	l_result int4);


CREATE TYPE auri_owner_1.dmo_getestremiregnumud_return4 AS (
	codcategoriaregio varchar,
	siglaregio varchar,
	annoregout int4,
	numregout int4,
	tsregout date,
	subnumregout varchar,
	l_result int4);


CREATE TYPE auri_owner_1.dmo_getestremiregnumud_return6 AS (
	codcategoriaregio varchar,
	siglaregio varchar,
	annoregout int4,
	numregout int4,
	tsregstrout varchar,
	subnumregout varchar,
	l_result int4);


CREATE TYPE auri_owner_1.dmo_getfromclausesuattrdin_out AS (
	fromclausetoaddout varchar,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_getidnodo_return AS (
	idnodoout varchar,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_getidudcatenaudcollegate_out AS (
	strudtoignoreio varchar,
	returncode dmto_id);


CREATE TYPE auri_owner_1.dmo_getrelemailvsud_out AS (
	idemailarrivoout varchar,
	nroemailpecinviateout int4,
	nroemailpeoinviateout int4,
	flgricevutenotconfout int4,
	flgricevutenoteccout int4,
	flgricevutenotaggout int4,
	flgricevutenotannout int4,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_getudcollegate_out AS (
	listarelazionivsudout text,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_getuserprivs_return AS (
	privslistout text,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_indici_doc_pdv AS (
	nome_orig varchar(150),
	nome_in_pdv varchar(50),
	tipo_in_pdv varchar(50),
	flg_opzionale numeric(1,0));


CREATE TYPE auri_owner_1.dmo_indirizzo AS (
	cod_istat_stato_estero varchar(3),
	nome_stato_estero varchar(250),
	cod_istat_comune varchar(6),
	nome_citta varchar(150),
	localita varchar(250),
	zona varchar(50),
	complemento_ind varchar(50),
	ci_toponomastico varchar(30),
	tipo_toponimo varchar(100),
	indirizzo varchar(250),
	civico varchar(10),
	esponente_civico varchar(10),
	interno varchar(30),
	scala varchar(10),
	piano int4,
	cap varchar(5),
	cod_tipo varchar(10),
	dettaglio_tipo varchar(250),
	dt_inizio_vld date,
	dt_fine_vld date);


CREATE TYPE auri_owner_1.dmo_info_storico AS (
	ts_fine_vld date,
	id_user int4,
	motivi varchar(4000),
	estremi_atto dmo_reg_num_ud,
	old_values _dmo_attr_value);


CREATE TYPE auri_owner_1.dmo_liv_gerarchia AS (
	nro int4,
	denominazione varchar(30),
	flg_codice_numerico numeric(1,0),
	flg_romano numeric(1,0));


CREATE TYPE auri_owner_1.dmo_liv_gerarchia_estesa AS (
	nro int4,
	cod_tipi varchar(500),
	flg_codice_numerico numeric(1,0),
	flg_romano numeric(1,0));


CREATE TYPE auri_owner_1.dmo_loaddettudxguimodprot_out AS (
	datiudxmlout text,
	attributiaddout text,
	flgmostraaltriattrout int4,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_login_2_return AS (
	flgtpdominioautio int4,
	iddominioautio int4,
	codidconnectiontokenout varchar,
	iduserout numeric,
	desuserout varchar,
	desdominioout varchar,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4,
	errcodeoutstring varchar);


CREATE TYPE auri_owner_1.dmo_login_3_return AS (
	flgtpdominioautio int4,
	iddominioautio int4,
	codidconnectiontokenout varchar,
	desuserout varchar,
	desdominioout varchar,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar);


CREATE TYPE auri_owner_1.dmo_login_return AS (
	flgtpdominioautio int4,
	iddominioautid int4,
	codidconnectiontokenout varchar,
	iduser numeric,
	desuser varchar,
	desdominio varchar,
	parametriconfig text,
	menu text,
	listexml text,
	errcontext varchar,
	errcode varchar,
	errmsg varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_navigarepositorytree_out AS (
	idrootnodeio varchar,
	treexmlout text,
	percorsorootnodexmlout text,
	dettaglirootnodeout text,
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_navigastrutturaorgtree_return AS (
	idrootnodeio varchar,
	idorganigrammaio numeric,
	treexmlout text,
	percorsorootnodexmlout text,
	dettaglirootnodeout text,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_navigatitolariotree_return AS (
	idrootnodeio varchar,
	treexmlout text,
	percorsorootnodexmlout text,
	dettaglirootnodeout text,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_notifica AS (
	indxnotifemailnotifio varchar,
	oggemailout varchar,
	bodyemailout varchar,
	testosmsout varchar,
	urixmlout varchar,
	nricellxnotifsmsnotifio varchar,
	warningmsgout text,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	return_code int4);


CREATE TYPE auri_owner_1.dmo_parte_folderizzazione AS (
	tipo_valore varchar(1),
	valore varchar(1000));


CREATE TYPE auri_owner_1.dmo_password AS (
	"password" varchar(30),
	dt_fine_vld date);


CREATE TYPE auri_owner_1.dmo_presa_visione AS (
	id_user int4,
	a_nome_di int4,
	ts date,
	osservazioni varchar(1000));


CREATE TYPE auri_owner_1.dmo_process_sogg AS (
	flg_tipo varchar(3),
	ci_in_anag_prov varchar(150),
	flg_incl_sottouo numeric(1,0),
	vs_livello_so int4,
	vs_id_uo int4,
	vs_uo_con_ruolo_proc varchar(150),
	vs_cod_tipo_uo varchar(10),
	rif_sv_con_ruolo_proc varchar(150));


CREATE TYPE auri_owner_1.dmo_reg_num_ud AS (
	cod_categoria varchar(10),
	sigla varchar(30),
	anno numeric(4,0),
	numero int4,
	ts date,
	id_user int4,
	id_uo int4,
	flg_ann numeric(1,0),
	ts_ann date,
	id_ud_atto_ann int4,
	estremi_atto_ann varchar(100),
	motivi_ann varchar(500),
	dt_termine_rsv date,
	prov_ci_reg_num varchar(30),
	flg_rich_generazione numeric(1,0),
	flg_generata numeric(1,0),
	id_user_ann int4,
	sub_numero varchar(10));


CREATE TYPE auri_owner_1.dmo_rel_doc_ud AS (
	id_doc int4,
	cod_natura_rel varchar(10),
	nro_allegato numeric(4,0),
	des_dett_rel varchar(250),
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_rel_folder_ud AS (
	id_folder int4,
	nro_pos_folder_vs_ud int4,
	cod_ruolo_ud varchar(10),
	motivo_rel varchar(250),
	nro_pos_ud_in_folder int4,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_rel_uo_user AS (
	id_uo int4,
	dt_inizio_vld date,
	dt_fine_vld date,
	flg_tipo_rel varchar(1),
	id_ruolo_amm int4,
	id_scrivania int4,
	flg_incl_sottouo numeric(1,0),
	flg_incl_scrivanie numeric(1,0),
	flg_primario numeric(1,0),
	deriva_da_pp int4,
	esclusioni_uo_coll_pp varchar(4000));


CREATE TYPE auri_owner_1.dmo_rel_wksp_ud AS (
	id_workspace int4,
	nro_pos_wks_vs_ud int4,
	motivo_rel varchar(250),
	nro_pos_ud_in_wks int4,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_riapertura AS (
	ts_riapertura date,
	id_user_riapertura int4,
	id_uo_riapertura int4,
	id_user_aut_riap int4,
	ts_chiusura_prec date,
	id_user_chiusura_prec int4,
	motivazioni_riapertura varchar(1000));


CREATE TYPE auri_owner_1.dmo_rif_email AS (
	indirizzo varchar(200),
	flg_casella_istituz numeric(1,0),
	flg_dich_ipa numeric(1,0),
	flg_pec numeric(1,0),
	gestore_pec varchar(500));


CREATE TYPE auri_owner_1.dmo_rif_email_sogg AS (
	indirizzo varchar(200),
	flg_casella_istituz numeric(1,0),
	flg_dich_ipa numeric(1,0),
	flg_pec numeric(1,0),
	gestore_pec varchar(500),
	cardinalita int4);

CREATE TYPE auri_owner_1.dmo_rif_tel AS (
	cardinalita int4,
	tipo varchar(150),
	nro_tel varchar(100));


CREATE TYPE auri_owner_1.dmo_rubrica_soggetti_indirizzi_luoghi AS (
	id_sogg_rubrica int4,
	cod_istat_stato_estero varchar(3),
	nome_stato_estero varchar(250),
	cod_istat_comune varchar(6),
	nome_citta varchar(150),
	localita varchar(250),
	zona varchar(50),
	complemento_ind varchar(50),
	ci_toponomastico varchar(30),
	tipo_toponimo varchar(100),
	indirizzo varchar(250),
	civico varchar(10),
	esponente_civico varchar(10),
	interno varchar(30),
	scala varchar(10),
	piano int4,
	cap varchar(5),
	cod_tipo varchar(10),
	dettaglio_tipo varchar(250),
	dt_inizio_vld date,
	dt_fine_vld date,
	tipo_ind varchar(250));


CREATE TYPE auri_owner_1.dmo_rubrica_soggetto_altre_forme AS (
	id_sogg_rubrica int4,
	denominazione varchar(500),
	cardinalita int4,
	tipo varchar(150));


CREATE TYPE auri_owner_1.dmo_rubrica_soggetto_indirizzo AS (
	id_sogg_rubrica int4,
	cod_istat_stato_estero varchar(3),
	nome_stato_estero varchar(250),
	cod_istat_comune varchar(6),
	nome_citta varchar(150),
	localita varchar(250),
	zona varchar(50),
	complemento_ind varchar(50),
	ci_toponomastico varchar(30),
	tipo_toponimo varchar(100),
	indirizzo varchar(250),
	civico varchar(10),
	esponente_civico varchar(10),
	interno varchar(30),
	scala varchar(10),
	piano int4,
	cap varchar(5),
	cod_tipo varchar(10),
	dettaglio_tipo varchar(250),
	dt_inizio_vld date,
	dt_fine_vld date);


CREATE TYPE auri_owner_1.dmo_rubrica_soggetto_info_storico AS (
	id_sogg_rubrica int4,
	ts_fine_vld date,
	id_user int4,
	motivi varchar(4000),
	estremi_atto dmo_reg_num_ud,
	old_values _dmo_attr_value);


CREATE TYPE auri_owner_1.dmo_rubrica_soggetto_rif_email AS (
	id_sogg_rubrica int4,
	indirizzo varchar(200),
	flg_casella_istituz numeric(1,0),
	flg_dich_ipa numeric(1,0),
	flg_pec numeric(1,0),
	gestore_pec varchar(500));


CREATE TYPE auri_owner_1.dmo_rubrica_soggetto_rif_email_sogg AS (
	id_sogg_rubrica int4,
	cardinalita int4);


CREATE TYPE auri_owner_1.dmo_rubrica_soggetto_rif_tel AS (
	id_sogg_rubrica int4,
	cardinalita int4,
	tipo varchar(150),
	nro_tel varchar(100));


CREATE TYPE auri_owner_1.dmo_setaclud_out AS (
	urixmlout text,
	warningmsgout varchar(4000),
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_soggetto_interno AS (
	flg_tipo varchar(3),
	id_in_anag_prov int4,
	flg_incl_sottouo numeric(1,0),
	vs_livello_so int4,
	vs_id_uo int4,
	vs_cod_tipo_uo varchar(10));


CREATE TYPE auri_owner_1.dmo_start_end_scad AS (
	tipo varchar(30),
	valore varchar(250),
	dett_data varchar(150),
	dett_esiti varchar(1000));


CREATE TYPE auri_owner_1.dmo_str_x_sp_aoo AS (
	id_sp_aoo int4,
	str_value varchar(1000));


CREATE TYPE auri_owner_1.dmo_string_with_pos AS (
	cardinalita int4,
	valore varchar(1000));


CREATE TYPE auri_owner_1.dmo_testshowfoldercontentallowed_return AS (
	flgshowcontentallowedout int4,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_trovaintitolario_return AS (
	cercainclassificazioneio numeric(38,0),
	flgcercainsubclassifio int4,
	criteriavanzatiio text,
	criteripersonalizzatiio text,
	colorderbyio varchar,
	flgdescorderbyio varchar,
	nropaginaio int4,
	bachsizeio int4,
	nrototrecout int4,
	nrorecinpaginaout int4,
	resultout text,
	percorsoricercaxmlout text,
	dettaglicercainclassifout text,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	ret_code int4);


CREATE TYPE auri_owner_1.dmo_trovasoggetti_out AS (
	denominazioneio varchar,
	flginclaltredenomio int4,
	flgincldenomstoricheio int4,
	cfio varchar,
	pivaio varchar,
	listacodtipisottotipiio varchar,
	emailio varchar,
	codrapidoio varchar,
	ciprovsoggio varchar,
	flgsolovldio int4,
	tsvldio varchar,
	codapplownerio varchar,
	codistapplownerio varchar,
	flgrestrapplownerio int4,
	flgcertificatiio int4,
	flginclannullatiio int4,
	idsoggrubricaio numeric,
	flginindicepaio int4,
	codammipaio varchar,
	codaooipaio varchar,
	issoggrubricaappio varchar,
	idgruppoappio numeric,
	nomegruppoappio varchar,
	criteripersonalizzatiio text,
	colorderbyio varchar,
	flgdescorderbyio varchar,
	nropaginaio int4,
	bachsizeio int4,
	nrototrecout int4,
	nrorecinpaginaout int4,
	listaxmlout text,
	flgabilinsout int4,
	flgmostraaltriattrout int4,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_unita_doc AS (
	id_ud numeric(10,0),
	id_sp_aoo numeric(10,0),
	cod_appl_owner varchar(30),
	cod_ist_appl_owner varchar(30),
	nome_ud varchar(100),
	flg_nome_ud_auto numeric(1,0),
	id_library numeric(10,0),
	flg_archivio varchar(1),
	flg_tipo_prov varchar(1),
	cod_stato varchar(10),
	cod_stato_dett varchar(10),
	ts_last_upd_stato date,
	flg_interesse_attivo numeric(1,0),
	ts_interesse_cessato date,
	ts_arrivo date,
	id_ud_aut_reg_diff int4,
	estremi_aut_reg_diff varchar(100),
	motivi_reg_diff varchar(500),
	ts_termine_x_reg_diff date,
	dt_doc_ricevuto date,
	anno_doc_ricevuto numeric(4,0),
	estremi_reg_doc_ricevuto varchar(50),
	rif_doc_ricevuto varchar(50),
	cod_mezzo_trasm varchar(10),
	dt_raccomandata date,
	nro_raccomandata varchar(50),
	nro_lista_raccomandata varchar(150),
	dt_notifica date,
	nro_notifica varchar(50),
	id_user_ricezione int4,
	id_uo_ricezione int4,
	flg_interoperabilita numeric(1,0),
	id_user_ctrl_ammissib int4,
	ts_spedizione date,
	id_user_spedizione int4,
	id_uo_spedizione int4,
	nro_allegati numeric(2,0),
	liv_evidenza int4,
	flg_conserv_perm numeric(1,0),
	periodo_conserv int4,
	cod_supporto_conserv varchar(10),
	dt_scarto date,
	id_user_scarto int4,
	id_user_aut_scarto int4,
	id_toponimo int4,
	collocazione_fisica varchar(1000),
	note varchar(4000),
	ts_lock date,
	id_user_lock int4,
	flg_tipo_lock varchar(1),
	flg_ann numeric(1,0),
	motivi_ann varchar(500),
	flg_cons_ered_permessi numeric(1,0),
	id_folder_ered int4,
	id_workspace_ered int4,
	prov_ci_ud varchar(30),
	cod_canale_invio_dest varchar(10),
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4,
	dt_inizio_pubbl date,
	dt_esec_ado date,
	dt_esecutivita date,
	dt_adozione date,
	id_attivita_sinapoli varchar(50),
	ts_invio_in_conserv date,
	id_in_conservazione varchar(250),
	id_pdv varchar(64),
	ts_acc_in_conserv date,
	ts_inizio_conserv date,
	dt_termine_conserv date,
	flg_porogato_termine_cons numeric(1,0),
	flg_esib_da_cons numeric(1,0),
	err_msg_invio_in_conserv varchar(4000),
	stato_conservazione varchar(30));


CREATE TYPE auri_owner_1.dmo_unita_doc_acl AS (
	id_ud numeric(22,0),
	flg_tipo varchar(3),
	id_in_anag_prov int4,
	flg_incl_sottouo numeric,
	vs_livello_so int4,
	vs_id_uo int4,
	vs_cod_tipo_uo varchar(10),
	nro_pos_in_acl int4,
	flg_visib_dati numeric,
	flg_visib_file numeric,
	visib_su_files varchar(250),
	flg_modifica_dati numeric,
	flg_modifica_file numeric,
	modifica_su_files varchar(250),
	flg_modifica_acl numeric,
	flg_eliminazione numeric,
	flg_copia numeric,
	flg_recupero numeric,
	flg_acc_lim_ver_pubbl numeric,
	lim_ver_pubbl_su_file varchar(250),
	flg_ereditato numeric);


CREATE TYPE auri_owner_1.dmo_unita_doc_classif_ud AS (
	id_ud int4,
	id_classificazione int4,
	ts date,
	id_user int4);


CREATE TYPE auri_owner_1.dmo_unita_doc_classificazioni AS (
	id_ud int4,
	id_classificazione int4,
	ts date,
	id_user int4);


CREATE TYPE auri_owner_1.dmo_unita_doc_folder AS (
	id_ud numeric(22,0),
	id_folder int4,
	nro_pos_folder_vs_ud int4,
	cod_ruolo_ud varchar(10),
	motivo_rel varchar(250),
	nro_pos_ud_in_folder int4,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_unita_doc_reg_num AS (
	id_ud int4,
	cod_categoria varchar(10),
	sigla varchar(30),
	anno numeric(4,0),
	numero int4,
	ts date,
	id_user int4,
	id_uo int4,
	flg_ann numeric(1,0),
	ts_ann date,
	id_ud_atto_ann int4,
	estremi_atto_ann varchar(100),
	motivi_ann varchar(500),
	dt_termine_rsv date,
	prov_ci_reg_num varchar(30),
	flg_rich_generazione numeric(1,0),
	flg_generata numeric(1,0),
	id_user_ann int4,
	sub_numero varchar(10));


CREATE TYPE auri_owner_1.dmo_unita_doc_workspace AS (
	id_ud numeric(22,0),
	id_workspace int4,
	nro_pos_wks_vs_ud int4,
	motivo_rel varchar(250),
	nro_pos_ud_in_wks int4,
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_updverdoc_out AS (
	nroprogrverio int4,
	urixmlout text,
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmo_user_acl AS (
	tipo_dom numeric(1,0),
	id_dom int4,
	id_acl_def_ud_create int4,
	id_acl_def_fld_creati int4,
	id_acl_def_wrks_creati int4,
	id_acl_to_append_ud_ass int4,
	id_acl_to_append_fld_ass int4);


CREATE TYPE auri_owner_1.dmo_user_domain AS (
	tipo_dom numeric(1,0),
	id_dom int4,
	dt_accr_dal date,
	dt_accr_al date,
	id_profilo int4,
	lista_id_gruppi_priv varchar(1000));


CREATE TYPE auri_owner_1.dmo_valore_imp_personale AS (
	codice varchar(30),
	descrizione varchar(250),
	flg_rich_val_dett numeric(1,0),
	type_val_dett varchar(10));


CREATE TYPE auri_owner_1.dmo_var_flusso_lav AS (
	sezione varchar(100),
	nome varchar(250),
	valore_semplice varchar(4000),
	valore_complesso text);


CREATE TYPE auri_owner_1.dmo_var_sez_cache AS (
	nome varchar(250),
	flg_tipo_semplice int4,
	valore_semplice varchar(4000),
	valore_complesso _varchar,
	flg_clob int4,
	valore_clob text);


CREATE TYPE auri_owner_1.dmo_ver_doc AS (
	nro_progr numeric(4,0),
	cod_ver varchar(30),
	display_filename varchar(1000),
	id_formato_el int4,
	mimetype varchar(250),
	dimensione numeric(38,0),
	flg_firmato numeric(1,0),
	rif_in_repository varchar(1000),
	flg_pubblicata numeric(1,0),
	flg_ann numeric(1,0),
	note varchar(1000),
	impronta varchar(255),
	algoritmo_impronta varchar(30),
	encoding_impronta varchar(10),
	nro_progr_bkver numeric(4,0),
	flg_da_scansione numeric(1,0),
	dt_scansione date,
	id_user_scansione int4,
	spec_scansione varchar(1000),
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4);


CREATE TYPE auri_owner_1.dmo_verify_for_lock AS (
	iduserlockout numeric,
	tslockout date,
	objtypelockonout varchar,
	idobjlockout int4,
	nomeobjlockout varchar,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmo_x_testgruppoincluso AS (
	strgruppitoignoreio varchar,
	return_code int4);


CREATE TYPE auri_owner_1.dmo_xesplodigruppo_out AS (
	strgruppitoignoreio varchar,
	returncode _varchar);


CREATE TYPE auri_owner_1.dmpkcore_adddocout AS (
	idudout int4,
	iddocout int4,
	urixmlout text,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.dmpkdmlsuoggetti_aggiornacoluserdefined_out AS (
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.dmt_dett_attivita_wf_acl AS (
	id_sp_aoo int4,
	nro_pos_in_acl int4,
	flg_lim_x_ass numeric(1,0),
	nro_ord_seq numeric(2,0),
	flg_visibilita numeric(1,0),
	flg_esecuzione numeric(1,0));


CREATE TYPE auri_owner_1.dmt_dett_attivita_wf_att_x_esito AS (
	id_sp_aoo int4,
	activity_result_code varchar(250),
	flg_tp_mod_ass varchar(1));


CREATE TYPE auri_owner_1.dmt_dett_attivita_wf_dest_trasmissione AS (
	id_sp_aoo int4,
	nro_pos_tra_dest int4,
	activity_result_code varchar(250),
	flg_all_sogg_in_dest numeric(1,0));


CREATE TYPE auri_owner_1.dmt_documents_type AS (
	id_doc int4,
	id_sp_aoo int4,
	cod_appl_owner varchar(30),
	cod_ist_appl_owner varchar(30),
	des_ogg varchar(4000),
	parole_chiave varchar(4000),
	container_type varchar(30),
	container_alias varchar(30),
	id_doc_type int4,
	cod_supporto_orig varchar(10),
	flg_tipo_cartaceo varchar(3),
	flg_digitalizzazione numeric(1,0),
	ci_su_cartaceo varchar(250),
	nro_pagine_cartaceo int4,
	dt_stesura date,
	luogo_stesura varchar(150),
	liv_riservatezza int4,
	dt_termine_riservatezza date,
	flg_visib_in_chiaro varchar(2),
	flg_dati_personali numeric(1,0),
	flg_dati_sensibili numeric(1,0),
	flg_dati_sensibilissimi numeric(1,0),
	flg_dati_giudiziari numeric(1,0),
	flg_eserc_dir_privacy numeric(1,0),
	flg_blocco_trattamento numeric(1,0),
	dt_termine_blocco_tratt date,
	flg_limit_accesso numeric(1,0),
	dt_termine_diff_accesso date,
	id_user_titolare_tratt int4,
	id_user_resp_tratt int4,
	restrizioni_accesso varchar(1000),
	restrizioni_riprod varchar(1000),
	flg_cartaceo_eliminato numeric(1,0),
	dt_elim_cartaceo date,
	id_user_elim_cart int4,
	id_user_aut_elim_cart int4,
	ts_lock date,
	id_user_lock int4,
	flg_tipo_lock varchar(1),
	flg_ann numeric(1,0),
	motivi_ann varchar(500),
	prov_ci_doc varchar(30),
	ts_ins date,
	id_user_ins int4,
	ts_last_upd date,
	id_user_last_upd int4,
	id_ud int4,
	nro_allegato numeric(4,0),
	flg_invio_in_conserv numeric(1,0),
	flg_urgenza_invio_in_conserv numeric(1,0),
	ts_invio_in_conserv date,
	id_pdv varchar(64),
	ts_acc_in_conserv date,
	ts_inizio_conserv date,
	dt_termine_conserv date,
	flg_porogato_termine_cons numeric(1,0),
	flg_esib_da_cons numeric(1,0),
	id_lotto_invio_in_conserv int4,
	id_in_conservazione varchar(250),
	err_msg_invio_in_conserv varchar(4000),
	stato_conservazione varchar(30),
	file_content text,
	flg_ver_con_omissis numeric(1,0),
	id_doc_ver_con_omissis int4);


CREATE TYPE auri_owner_1.dmt_processes_type AS (
	id_process numeric(38,0),
	id_sp_aoo numeric(38,0),
	cod_appl_owner varchar(30),
	cod_ist_appl_owner varchar(30),
	id_process_type varchar(38),
	prov_ci_ty_flusso_wf varchar(250),
	prov_ci_flusso_wf varchar(250),
	etichetta_proponente varchar(250),
	etichetta_proponente_rft varchar(250),
	oggetto varchar(4000),
	id_process_parent numeric(38,0),
	ts_avvio timestamp,
	dt_decorrenza timestamp,
	ts_fine timestamp,
	dt_chiusura_termini timestamp,
	flg_tipo_esito varchar(1),
	esito varchar(250),
	flg_stato varchar(1),
	dt_inizio_sosp_interr timestamp,
	dt_fine_interr timestamp,
	gg_durata_sosp_concluse int8,
	id_copia_ud numeric(38,0),
	id_ud numeric(38,0),
	id_folder numeric(38,0),
	nro_sospensioni numeric(38,0),
	motivazion varchar(4000),
	note varchar(4000),
	flg_ann int2,
	prov_ci_process varchar(250),
	flg_hidden int2,
	flg_locked int2,
	ts_ins timestamp,
	id_user_ins numeric(38,0),
	userid_ins varchar(50),
	profilo_ins varchar(20),
	next_task varchar(4000),
	ts_last_upd timestamp,
	id_user_last_upd numeric(38,0));


CREATE TYPE auri_owner_1.dmt_processes_type_1 AS (
	id_process numeric(38,0),
	id_sp_aoo numeric(38,0),
	cod_appl_owner varchar(30),
	cod_ist_appl_owner varchar(30),
	id_process_type varchar(38),
	prov_ci_ty_flusso_wf varchar(250),
	prov_ci_flusso_wf varchar(250),
	etichetta_proponente varchar(250),
	etichetta_proponente_rft varchar(250),
	oggetto varchar(4000),
	id_process_parent numeric(38,0),
	ts_avvio timestamp,
	dt_decorrenza timestamp,
	ts_fine timestamp,
	dt_chiusura_termini timestamp,
	flg_tipo_esito varchar(1),
	esito varchar(250),
	flg_stato varchar(1),
	dt_inizio_sosp_interr timestamp,
	dt_fine_interr timestamp,
	gg_durata_sosp_concluse int8,
	id_copia_ud numeric(38,0),
	id_ud numeric(38,0),
	id_folder numeric(38,0),
	nro_sospensioni numeric(38,0),
	motivazioni varchar(4000),
	note varchar(4000),
	flg_ann int2,
	prov_ci_process varchar(250),
	flg_hidden int2,
	flg_locked int2,
	ts_ins timestamp,
	id_user_ins numeric(38,0),
	userid_ins varchar(50),
	profilo_ins varchar(20),
	next_task varchar(4000),
	ts_last_upd timestamp,
	id_user_last_upd numeric(38,0));


CREATE TYPE auri_owner_1.dmto_acl AS (
	dmto_acl _dmo_ace);


CREATE TYPE auri_owner_1.dmto_acl_attivita AS (
	dmto_acl_attivita _dmo_ace_attivita);


CREATE TYPE auri_owner_1.dmto_acl_attribute AS (
	dmto_acl_attribute _dmo_ace_attribute);


CREATE TYPE auri_owner_1.dmto_acl_folder AS (
	dmto_acl_folder _dmo_ace_folder);


CREATE TYPE auri_owner_1.dmto_acl_ud AS (
	dmto_acl_ud _dmo_ace_ud);


CREATE TYPE auri_owner_1.dmto_acl_workspace AS (
	dmto_acl_workspace _dmo_ace_workspace);


CREATE TYPE auri_owner_1.dmto_attr_def AS (
	dmto_attr_def _dmo_attr_def);


CREATE TYPE auri_owner_1.dmto_attr_values AS (
	dmto_attr_values _dmo_attr_value);


CREATE TYPE auri_owner_1.dmto_bookmark AS (
	dmto_bookmark _dmo_bookmark);


CREATE TYPE auri_owner_1.dmto_cambi_stati AS (
	dmto_cambi_stati _dmo_cambio_stato);


CREATE TYPE auri_owner_1.dmto_ci_x_sp_aoo AS (
	dmto_ci_x_sp_aoo _dmo_ci_x_sp_aoo);


CREATE TYPE auri_owner_1.dmto_civici_toponimo AS (
	dmto_civici_toponimo _dmo_civico_toponimo);


CREATE TYPE auri_owner_1.dmto_classif_ud AS (
	dmto_classif_ud _dmo_classif_ud);


CREATE TYPE auri_owner_1.dmto_componenti_gruppo AS (
	dmto_componenti_gruppo _dmo_componente_gruppo);


CREATE TYPE auri_owner_1.dmto_componenti_gruppo_est AS (
	dmto_componenti_gruppo_est _dmo_componente_gruppo_est);


CREATE TYPE auri_owner_1.dmto_credenz_aut_ext AS (
	dmto_credenz_aut_ext _dmo_credenz_aut_ext);


CREATE TYPE auri_owner_1.dmto_ctrl_ba_activity AS (
	dmto_ctrl_ba_activity _dmo_ctrl_ba_activity);


CREATE TYPE auri_owner_1.dmto_ctrl_x_doc AS (
	dmto_ctrl_x_doc _dmo_ctrl_x_doc);


CREATE TYPE auri_owner_1.dmto_decodifiche AS (
	dmto_decodifiche _dmo_decodifica);


CREATE TYPE auri_owner_1.dmto_def_val_imp_personale AS (
	dmto_def_val_imp_personale _dmo_def_val_imp_personale);


CREATE TYPE auri_owner_1.dmto_deleghe AS (
	dmto_deleghe _dmo_delega);


CREATE TYPE auri_owner_1.dmto_deleghe_ext AS (
	dmto_deleghe_ext _dmo_delega_ext);


CREATE TYPE auri_owner_1.dmto_denom_tipizzate AS (
	dmto_denom_tipizzate _dmo_denom_tipizzata);


CREATE TYPE auri_owner_1.dmto_denominazioni AS (
	dmto_denominazioni _varchar);


CREATE TYPE auri_owner_1.dmto_dest_trasm AS (
	dmto_dest_trasm _dmo_dest_trasm);


CREATE TYPE auri_owner_1.dmto_dett_att_x_esito AS (
	dmto_dett_att_x_esito _dmo_dett_att_x_esito);


CREATE TYPE auri_owner_1.dmto_doc_attr_values AS (
	dmto_doc_attr_values _dmo_doc_attr_value);


CREATE TYPE auri_owner_1.dmto_folder_app_folder AS (
	dmto_folder_app_folder _dmo_folder_app_folder);


CREATE TYPE auri_owner_1.dmto_id AS (
	dmto_id _numeric);


CREATE TYPE auri_owner_1.dmto_indici_doc_pdv AS (
	dmto_indici_doc_pdv _dmo_indici_doc_pdv);


CREATE TYPE auri_owner_1.dmto_indirizzi AS (
	dmto_indirizzi _dmo_indirizzo);


CREATE TYPE auri_owner_1.dmto_info_storico AS (
	dmto_info_storico _dmo_info_storico);


CREATE TYPE auri_owner_1.dmto_liv_gerarchia AS (
	dmto_liv_gerarchia _dmo_liv_gerarchia);


CREATE TYPE auri_owner_1.dmto_liv_gerarchia_estesa AS (
	dmto_liv_gerarchia_estesa _dmo_liv_gerarchia_estesa);


CREATE TYPE auri_owner_1.dmto_matrice_valori AS (
	dmto_matrice_valori _dmva_string);


CREATE TYPE auri_owner_1.dmto_ordered_string AS (
	dmto_ordered_string _dmo_string_with_pos);


CREATE TYPE auri_owner_1.dmto_passwords AS (
	dmto_passwords _dmo_password);


CREATE TYPE auri_owner_1.dmto_prese_visioni AS (
	dmto_prese_visioni _dmo_presa_visione);


CREATE TYPE auri_owner_1.dmto_process_sogg AS (
	dmto_process_sogg _dmo_process_sogg);


CREATE TYPE auri_owner_1.dmto_reg_num_ud AS (
	dmto_reg_num_ud _dmo_reg_num_ud);


CREATE TYPE auri_owner_1.dmto_rel_doc_ud AS (
	dmto_rel_doc_ud _dmo_rel_doc_ud);


CREATE TYPE auri_owner_1.dmto_rel_folder_ud AS (
	dmto_rel_folder_ud _dmo_rel_folder_ud);


CREATE TYPE auri_owner_1.dmto_rel_uo_user AS (
	dmto_rel_uo_user _dmo_rel_uo_user);


CREATE TYPE auri_owner_1.dmto_rel_wksp_ud AS (
	dmto_rel_wksp_ud _dmo_rel_wksp_ud);


CREATE TYPE auri_owner_1.dmto_riaperture AS (
	dmto_riaperture _dmo_riapertura);


CREATE TYPE auri_owner_1.dmto_rif_email_sogg AS (
	dmto_rif_email_sogg _dmo_rif_email_sogg);


CREATE TYPE auri_owner_1.dmto_rif_fax AS (
	dmto_rif_fax _varchar);


CREATE TYPE auri_owner_1.dmto_rif_tel AS (
	dmto_rif_tel _dmo_rif_tel);


CREATE TYPE auri_owner_1.dmto_start_end_scad AS (
	dmto_start_end_scad _dmo_start_end_scad);


CREATE TYPE auri_owner_1.dmto_str_x_sp_aoo AS (
	dmto_str_x_sp_aoo _dmo_str_x_sp_aoo);


CREATE TYPE auri_owner_1.dmto_string AS (
	dmto_string _varchar);


CREATE TYPE auri_owner_1.dmto_user_acl AS (
	dmto_user_acl _dmo_user_acl);


CREATE TYPE auri_owner_1.dmto_user_domains AS (
	dmto_user_domains _dmo_user_domain);


CREATE TYPE auri_owner_1.dmto_valori_imp_personale AS (
	dmto_valori_imp_personale _dmo_valore_imp_personale);


CREATE TYPE auri_owner_1.dmto_var_flusso_lav AS (
	dmto_var_flusso_lav _dmo_var_flusso_lav);


CREATE TYPE auri_owner_1.dmto_vers_doc AS (
	dmto_vers_doc _dmo_ver_doc);


CREATE TYPE auri_owner_1.dmva_attr_values AS (
	dmva_attr_values _dmo_attr_value);


CREATE TYPE auri_owner_1.dmva_characters AS (
	dmva_characters _bpchar);


CREATE TYPE auri_owner_1.dmva_clob AS (
	dmva_clob _text);


CREATE TYPE auri_owner_1.dmva_folderizzazione AS (
	dmva_folderizzazione _dmo_parte_folderizzazione);


CREATE TYPE auri_owner_1.dmva_shortstring AS (
	dmva_shortstring _varchar);


CREATE TYPE auri_owner_1.dmva_string AS (
	dmva_string _varchar);


CREATE TYPE auri_owner_1.dsoggettoesterno_out AS (
	errcontextout varchar(1000),
	errcodeout varchar,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.explodetreenode_out AS (
	treexmlout text,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.finddictionaryvalue_out AS (
	codvalueio varchar(10),
	valueio varchar(1000),
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.findfascicoloinarchivio_out AS (
	datifascicoloxmlio text,
	idfolderout int4,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.findintopografico_out AS (
	datitoponimoxmlio text,
	idtoponimoout int4,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.findlibrary_out AS (
	idlibraryout int4,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.findsoggettoinrubrica_out AS (
	datisoggxmlio text,
	idsogginrubricaout numeric(38,0),
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.flgfoundvartabrec AS (
	var_key varchar(250),
	var_value int4);


CREATE TYPE auri_owner_1.flgfoundvartabtype AS (
	key_code varchar(250),
	key_value int4);


CREATE TYPE auri_owner_1.flgonofbystringtype AS (
	chiave varchar(50),
	valore int4);


CREATE TYPE auri_owner_1.frame AS (
	"level" int4,
	targetname text,
	func oid,
	linenumber int4,
	args text);


CREATE TYPE auri_owner_1.generaprogressivo_out AS (
	progrgeneratoout int4,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar);


CREATE TYPE auri_owner_1.generic_ret AS (
	errcontext varchar(1000),
	errcode varchar(100),
	errmsg varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getattrdinassociati_out AS (
	defattrxentitatabout _defattrxentitarectype,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getcontainerfromuri_out AS (
	containertypeout varchar(30),
	containeraliasout varchar(30),
	returncode int4);


CREATE TYPE auri_owner_1.getcontenutinodo AS (
	flgpresenticontenutiout numeric,
	nrocontenutiout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.getcontenutinodofunc AS (
	flgpresenticontenutiout numeric,
	nrocontenutiout varchar,
	errcontextout varchar,
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getdefaultlibrary_out AS (
	idlibraryout int4,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getdelegheuservsusersout AS (
	xmldelegheout text,
	errcontextout varchar(1000),
	errcodeout varchar(1000),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getestremiregnumud_j_out AS (
	codcategoriaregio varchar(100),
	siglaregio varchar(1000),
	annoregout int4,
	numregout int4,
	tsregout date,
	returncode int4);


CREATE TYPE auri_owner_1.getiddocinpraticastoricabypos_out AS (
	nroposizioneultimaudout int4,
	iddocout int4,
	idfolderout int4,
	returncode int4);


CREATE TYPE auri_owner_1.getinheritedaclxfolder_out AS (
	aclxmlout varchar,
	errcontextout varchar(1000),
	errcodeout varchar,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getinheritedaclxud_out AS (
	aclxmlout text,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(1000),
	returncode int4);


CREATE TYPE auri_owner_1.getlistacontattisoggint_out AS (
	listacontattiout varchar,
	errcontextout varchar(1000),
	errcodeout varchar,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getlistauridocud_out AS (
	listaxmlout text,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.getlistdefprefs_out AS (
	filterprefout text,
	filterlayoutprefout text,
	gridprefout text,
	autosearchprefout int4,
	flgfilterprefdefout int4,
	flgfilterlayoutprefdefout int4,
	flggridprefdefout int4,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.getnavigatorescrivaniaout AS (
	idutentemgoout varchar(64),
	treexmlout text,
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getoggettodamodello_out AS (
	oggettoout varchar(4000),
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getstatoprotemail_out AS (
	flgstatoprotout varchar(1000),
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000));


CREATE TYPE auri_owner_1.getstatopubblicazione_out AS (
	codstatoio varchar(100),
	codstatodettio varchar(100),
	returncode int4);


CREATE TYPE auri_owner_1.getuserprofiledata_out AS (
	idprofiloout int4,
	flgvisibindipaclout varchar(3),
	flgvisibindipuserabilout varchar(3),
	maxlivriservatezzaout int4,
	flggestindipaclout varchar(3),
	flggestindipuserabilout varchar(3),
	returncode int4);


CREATE TYPE auri_owner_1.getuserprofilemaindataout AS (
	flgvisibindipaclout varchar(100),
	flgvisibindipuserabilout varchar(100),
	maxlivriservatezzaout int4,
	flggestindipaclout varchar(100),
	flggestindipuserabilout varchar(100),
	returncode int4);


CREATE TYPE auri_owner_1.getvarcomplessa_out AS (
	valorevariabileout _varchar,
	flgvarfoundtabio _flgfoundvartabtype,
	errcontextout varchar(4000),
	errcodeout varchar(1000),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getvarsemplice_anag_out AS (
	valorevariabileout varchar,
	flgvarfoundtabio _flgfoundvartabrec,
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.getvarsemplice_out AS (
	valorevariabileout varchar(1000),
	flgvarfoundtabio _flgfoundvartabtype,
	errcontextout varchar(4000),
	errcodeout varchar(1000),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.idtabtyperec AS (
	var_value int4,
	var_key int4);


CREATE TYPE auri_owner_1.insupdprocess_out AS (
	errmsgout varchar,
	errcontextout varchar,
	errcodeout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.inttabtyperec AS (
	var_value numeric,
	var_key varchar(250));


CREATE TYPE auri_owner_1.invio_out AS (
	indxnotifemailinvioio varchar,
	oggemailout varchar,
	bodyemailout varchar,
	nricellxnotifsmsinvioio varchar,
	testosmsout varchar,
	copieudinviatexmlout text,
	urixmlout text,
	warningmsgout varchar,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.iuassociazioneudvsproc_out AS (
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.iufolder_out AS (
	flgvarfoundtabio _flgfoundvartabtype,
	errcontextout varchar(1000),
	errcodeout varchar(1000),
	errmsgout varchar(4000),
	logerrmsgout varchar(4000),
	debuginfoio varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.iuuddocver_out AS (
	flgvarfoundtabioout _flgfoundvartabtype,
	nroprogrverio int4,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	logerrmsgout varchar(1000),
	debuginfoio varchar(1000),
	returncode int4);


CREATE TYPE auri_owner_1.loadaclud_out AS (
	flgereditarietapossibileout numeric,
	flgereditarietaconsentitaout numeric,
	flgtipooggereditadaout varchar(1000),
	idoggereditadaout numeric(38,0),
	desoggereditadaout varchar(2000),
	flgtipooggpossibileereddaout varchar(1000),
	idoggpossibileereddaout numeric(38,0),
	desoggpossibileereddaout varchar(1000),
	aclxmlout text,
	listadocudxmlout text,
	flgabilupdout numeric(38,0),
	errcodeout varchar(1000),
	errcontextout varchar(1000),
	errmsgout varchar(2000),
	returncode numeric(38,0));


CREATE TYPE auri_owner_1.loaddettudxguimodprot_out AS (
	datiudxmlout text,
	attributiaddout text,
	flgmostraaltriattrout int4,
	errmsgout varchar,
	errcontextout varchar,
	errcodeout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.logout_out AS (
	errcontextout varchar(1000),
	errcodeout varchar(4000),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.marktokenusageout AS (
	errcontextout varchar(4000),
	errcodeout varchar(5),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.mytype AS (
	id numeric,
	lbl varchar(100));


CREATE TYPE auri_owner_1.nodorepositoryrec AS (
	var_key int4,
	id int4,
	nome varchar(150));


CREATE TYPE auri_owner_1.nodosotype AS (
	id varchar(250),
	nri_livelli varchar(250));


CREATE TYPE auri_owner_1.notifica_out AS (
	nricellxnotifsmsnotifio varchar(1000),
	indxnotifemailnotifio varchar(1000),
	oggemailout varchar(4000),
	bodyemailout varchar(4000),
	testosmsout varchar(4000),
	urixmlout text,
	warningmsgout varchar(1000),
	errcontextout varchar(1000),
	errcodeout varchar(1000),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.percorsorepositoryrec AS (
	var_key int4,
	id int4,
	nome varchar(150));


CREATE TYPE auri_owner_1.percorsosorec AS (
	var_key int4,
	var_id varchar(250),
	var_nri_livelli varchar(250));


CREATE TYPE auri_owner_1.percorsosotype AS (
	idx int4,
	id varchar(250),
	nri_livelli varchar(250));


CREATE TYPE auri_owner_1.percorsotitrec AS (
	var_key int4,
	id int4,
	nri_livelli varchar(250));


CREATE TYPE auri_owner_1.presaincarico_out AS (
	copieudpresexmlout text,
	warningmsgout varchar(1000),
	errcontextout varchar(1000),
	errcodeout varchar(1000),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.proxyinfo AS (
	serverversionstr text,
	serverversionnum int4,
	proxyapiver int4,
	serverprocessid int4);


CREATE TYPE auri_owner_1.riga_utente AS (
	id_user numeric(38,0),
	username varchar);


CREATE TYPE auri_owner_1.rimappacollistaxmlstd_out AS (
	listaxmlstdio text,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.setaclfolder_out AS (
	warningmsgout varchar,
	urixaggcontainerout varchar,
	errcontextout varchar(1000),
	errcodeout varchar,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.setattrdinamici_out AS (
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.setsogginterni_out AS (
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.splitrigaxmlstdinvarray_out AS (
	returncode int4,
	colonne _varchar,
	flgpresenzaout _varchar);


CREATE TYPE auri_owner_1.splitrigaxmlstdinvarrayout AS (
	returncode int4,
	colonne _varchar,
	flgpresenzaout _varchar);


CREATE TYPE auri_owner_1.startflussowfforprocess_out AS (
	idprocessio int4,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.strtabrec AS (
	var_key int4,
	var_value varchar(500));


CREATE TYPE auri_owner_1.t_indirizzo AS (
	via varchar(100),
	civico numeric,
	citta varchar(100),
	provincia varchar(2));


CREATE TYPE auri_owner_1.targetinfo AS (
	target oid,
	"schema" oid,
	nargs int4,
	argtypes oidvector,
	targetname name,
	argmodes _char,
	argnames _text,
	targetlang oid,
	fqname text,
	returnsset bool,
	returntype oid,
	isfunc bool,
	pkg oid,
	argdefvals _text);

CREATE TYPE auri_owner_1.testabilregistrazioneud_out AS (
	flgtpreggestinternaout int4,
	dtiniziogestinternaout date,
	errcontextout varchar(1000),
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.testpresattrdinxnuovaentita_out AS (
	flgpresenzaattrdinout int4,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.testshowfoldercontentallowed_out AS (
	flgshowcontentallowedout int4,
	errcontextout varchar,
	errcodeout varchar,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.testshowfoldercontentallowedout AS (
	flgshowcontentallowedout int4,
	errmsgout varchar,
	errcontextout varchar,
	errcodeout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.trovadictvaluesfortabcol_out AS (
	colorderbyio varchar,
	flgdescorderbyio varchar,
	nropaginaio int4,
	bachsizeio int4,
	nrototrecout int4,
	nrorecinpaginaout int4,
	listaxmlout text,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.trovaemail_out AS (
	filtriio text,
	colorderbyio varchar,
	flgdescorderbyio varchar,
	nrototrecout numeric,
	nrorecinpaginaout numeric,
	resultout text,
	errcontextout varchar,
	errcodeout numeric,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.trovafunzioni_out AS (
	codfunzp1io varchar(3),
	codfunzp2io varchar(3),
	codfunzp3io varchar(3),
	codfunzpnio varchar(3),
	desfunzio varchar(150),
	livellofunzio int4,
	flgconsottofunzioniio int4,
	flgsolodispio int4,
	flgsoloconopzstdio int4,
	codapplownerio varchar,
	codistapplownerio varchar,
	flgrestrapplownerio int4,
	colorderbyio varchar(1000),
	flgdescorderbyio varchar(1000),
	nropaginaio int4,
	bachsizeio int4,
	nrototrec int4,
	nrorecinpagina int4,
	listaxml text,
	errcontext varchar(1000),
	errcode varchar(100),
	errmsg varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.trovagruppisoggrubrica_out AS (
	colorderbyio varchar(4000),
	flgdescorderbyio varchar(1000),
	nropaginaio int4,
	bachsizeio int4,
	nrototrecout int4,
	nrorecinpaginaout int4,
	listaxmlout text,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.trovamodoggetti_out AS (
	colorderbyio varchar(100),
	flgdescorderbyio varchar(100),
	nropaginaio int4,
	bachsizeio int4,
	nrototrecout int4,
	nrorecinpaginaout int4,
	listaxmlout text,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.trovarepositoryobjout AS (
	flgfmtestremiregio varchar(4000),
	flgudfolderio varchar(2),
	cercainfolderio numeric,
	flgcercainsubfolderio int4,
	criteriavanzatiio text,
	criteripersonalizzatiio text,
	colorderbyio varchar(4000),
	flgdescorderbyio varchar(4000),
	nropaginaio int4,
	bachsizeio int4,
	nrototrecout int4,
	nrorecinpaginaout int4,
	resultout text,
	percorsoricercaxmlio text,
	dettaglicercainfolderout text,
	errcontextout numeric,
	errcodeout int4,
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.trovastruttorgobj_out AS (
	flgobjtypeio text,
	cercainuoio int4,
	flgcercainsubuoio int4,
	criteriavanzatiio text,
	criteripersonalizzatiio text,
	colorderbyio text,
	flgdescorderbyio text,
	nropaginaio int4,
	bachsizeio int4,
	nrototrecout int4,
	nrorecinpaginaout int4,
	resultout text,
	percorsoricercaxmlout text,
	dettaglicercainuoout text,
	flgcommit int4,
	errcontextout text,
	errcodeout varchar(100),
	errmsgout text,
	returncode int4);


CREATE TYPE auri_owner_1.trovausers_out AS (
	iduserio varchar(50),
	usernameio varchar(50),
	descrizioneio varchar(150),
	qualificaio varchar(100),
	matricolaio varchar(30),
	ciprovuserio varchar(100),
	flgaccreditatiindomio int4,
	codapplaccredio varchar(100),
	codistapplaccredio varchar(100),
	codapplnoaccredio varchar(100),
	codistapplnoaccredio varchar(100),
	idruoloammio int4,
	desruoloammio varchar(150),
	iduoconrelvsuserio int4,
	livelliuoconrelvsuserio varchar(100),
	desuoconrelvsuserio varchar(100),
	flgtiporelconuoio varchar(100),
	flginclsottouoio int4,
	idgruppoappio int4,
	nomegruppoappio varchar(1000),
	flgsolovldio int4,
	tsvldio varchar(100),
	flgconaccessoalsistemaio int4,
	altricriteriio text,
	colorderbyio varchar(100),
	flgdescorderbyio varchar(100),
	nropaginaio int4,
	bachsizeio int4,
	nrototrec int4,
	nrorecinpaginaout int4,
	listaxmlout text,
	flgabilinsout int4,
	flgabilupdout int4,
	flgabildelout int4,
	flgmostraaltriattrout int4,
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.tt_indirizzi AS (
	tt_indirizzi _t_indirizzo);


CREATE TYPE auri_owner_1.upddocud_out AS (
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	urixmlout text,
	returncode int4);


CREATE TYPE auri_owner_1.updfolder_out AS (
	urixaggcontainerout varchar(4000),
	errcontextout varchar(1000),
	errcodeout varchar(100),
	errmsgout varchar(4000),
	returncode int4);


CREATE TYPE auri_owner_1.valoreattrobjrectype AS (
	chiave varchar(100),
	str_value varchar(4000),
	num_value numeric,
	date_value date);


CREATE TYPE auri_owner_1.valuedatetabtype AS (
	var_key varchar(250),
	var_value date);


CREATE TYPE auri_owner_1.valuevartabrec AS (
	var_key varchar(250),
	var_value varchar(4000));


CREATE TYPE auri_owner_1.valuevartabtype AS (
	"key" varchar(250),
	value varchar(4000));


CREATE TYPE auri_owner_1.var AS (
	"name" text,
	varclass bpchar(1),
	linenumber int4,
	isunique bool,
	isconst bool,
	isnotnull bool,
	dtype oid,
	value text);


CREATE TYPE auri_owner_1.verifyforlock2 AS (
	iduserlockout numeric,
	desuserlockout varchar,
	tslockout varchar,
	objtypelockonout varchar,
	idobjlockout numeric,
	nomeobjlockout varchar,
	errcontextout varchar,
	errcodeout numeric,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.verifyforlock_out AS (
	iduserlockout int4,
	desuserlockout varchar,
	tslockout varchar,
	objtypelockonout varchar,
	idobjlockout int4,
	nomeobjlockout varchar,
	errcontextout varchar,
	errcodeout int4,
	errmsgout varchar,
	returncode int4);


CREATE TYPE auri_owner_1.xtrovaidruoliinclusi_out AS (
	strruolitoignore varchar(4000),
	returncode _int4);
