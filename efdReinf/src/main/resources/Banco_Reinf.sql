\connect nfe;
ALTER DATABASE nfe SET search_path=nfe;


 alter table reinf.tb_informacoes_contribuinte drop constraint FK_INFORMACOESCONTRIBUINTE_CLASSIFICACAOTRIBUTARIA;
 alter table reinf.tb_informacoes_contribuinte drop constraint FK_SERVICOSTOMADOS_EMPRESA;
 alter table reinf.tb_informacoes_contribuinte drop constraint FK_INFORMACOESCONTRIBUINTE_EMPRESARAIZCNPJ;
 alter table reinf.tb_informacoes_contribuinte drop constraint FK_INFORMACOESCONTRIBUINTE_PERIODOAPURACAOREINF;
 alter table reinf.tb_informacoes_contribuinte drop constraint FK_INFORMACOESCONTRIBUINTE_TIPOAMBIENTE;
 alter table reinf.tb_reinf_lotes drop constraint FK_REINFLOTES_CNPJEMPRESARAIZREINF;
 alter table reinf.tb_reinf_lotes drop constraint FK_REINFLOTES_PERIODOAPURACAOREINF;
 alter table reinf.tb_reinf_lotes drop constraint FK_REINFLOTES_TIPOAMBIENTE;
 alter table reinf.tb_reinf_lotes_eventos drop constraint FK_REINFLOTESEVENTOS_REINFLOTES;
 alter table reinf.tb_reinf_lotes_eventos_retorno drop constraint FK_REINFLOTESEVENTOSRETORNO_REINFLOTESEVENTOS;
 alter table reinf.tb_reinf_lotes_eventos_retorno drop constraint FK_REINFLOTESEVENTOSRETORNO_REINFLOTESMENSAGENS;
 alter table reinf.tb_servicos_tomados drop constraint FK_SERVICOSTOMADOS_EMPRESA;
 alter table reinf.tb_servicos_tomados drop constraint FK_SERVICOSTOMADOS_EMPRESARAIZCNPJ;
 alter table reinf.tb_servicos_tomados drop constraint FK_SERVICOSTOMADOS_PERIODOAPURACAOREINF;
 alter table reinf.tb_servicos_tomados drop constraint FK_SERVICOSTOMADOS_TIPOAMBIENTE;
 alter table reinf.tb_servicos_tomados_nfs drop constraint FK_SERVICOSTOMADOSNFS_SERVICOSTOMADOS;
 alter table reinf.tb_servicos_tomados_proc_ret_ad drop constraint FK_SERVICOSTOMADOSPROCRETAD_SERVICOSTOMADOS;
 alter table reinf.tb_servicos_tomados_proc_ret_pr drop constraint FK_SERVICOSTOMADOSPROCRETPR_SERVICOSTOMADOS;
 drop table if exists reinf.tb_classificacao_tributaria cascade;
 drop table if exists reinf.tb_informacoes_contribuinte cascade;
 drop table if exists reinf.tb_periodo_apuracao_reinf cascade;
 drop table if exists reinf.tb_reinf_lotes cascade;
 drop table if exists reinf.tb_reinf_lotes_eventos cascade;
 drop table if exists reinf.tb_reinf_lotes_eventos_retorno cascade;
 drop table if exists reinf.tb_reinf_mensagens cascade;
 drop table if exists reinf.tb_servicos_tomados cascade;
 drop table if exists reinf.tb_servicos_tomados_nfs cascade;
 drop table if exists reinf.tb_servicos_tomados_proc_ret_ad cascade;
 drop table if exists reinf.tb_servicos_tomados_proc_ret_pr cascade;
 drop table if exists reinf.tb_reinf_arquivo cascade; 
 drop table if exists reinf.tb_reinf_arquivo_conteudo cascade; 
 drop table if exists tb_reinf_lotes_reinf_arquivo cascade; 

 drop sequence if exists REINF.SE_CLASSIFICACAO_TRIBUTARIA;
 drop sequence if exists REINF.SE_INFORMACOES_CONTRIBUINTE;
 drop sequence if exists REINF.SE_PERIODO_APURACAO_REINF;
 drop sequence if exists REINF.SE_REINF_LOTES;
 drop sequence if exists REINF.SE_REINF_LOTES_EVENTOS;
 drop sequence if exists REINF.SE_REINF_LOTES_EVENTOS_RETORNO;
 drop sequence if exists REINF.SE_REINF_MENSAGENS;
 drop sequence if exists REINF.SE_SERVICOS_TOMADOS;
 drop sequence if exists REINF.SE_SERVICOS_TOMADOS_NFS;
 drop sequence if exists REINF.SE_SERVICOS_TOMADOS_PROC_RET_AD;
 drop sequence if exists REINF.SE_SERVICOS_TOMADOS_PROC_RET_PR;
 drop sequence if exists REINF.SE_REINF_ARQUIVO_CONTEUDO_PLC;
 drop sequence if exists REINF.SE_REINF_ARQUIVO_PLC;
 drop sequence if exists hibernate_sequence;
 
 create table reinf.tb_classificacao_tributaria (
    id_classificacao_tributaria bigint not null,
    desc_classificacao          varchar(150) not null,
    data_ult_alteracao          timestamp without time zone default now() not null,
    usuario_ult_alteracao       varchar(30) default 'anonimo' not null,
    versao                      integer not null default 0,
    sit_historico_plc           varchar(1) default 'A' not null,    
    primary key (id_classificacao_tributaria)
);
 create table reinf.tb_informacoes_contribuinte (
    id_informacoes_contribuinte  bigint not null,
    id_empresa_raiz_cnpj         bigint not null,
    tipo_cadastro_contribuinte   varchar(1) not null,
    tipo_ambiente                bigint not null,
    periodo_apuracao_reinf       bigint not null,
    per_apur                     varchar(7) not null,
    empresa_raiz_cnpj            bigint not null,
    situacao_pessoa_juridica     varchar(5) not null,
    tipo_inscricao               varchar(5) not null,
    empresa_contato_reinf        bigint not null,
    classificacao_tributaria     bigint not null,
    ind_acordo_isen_multa        varchar(1) not null,
    ind_desoneracao               varchar(1) not null,
    ind_escrituracao             varchar(1) not null,    
    ind_situacao_pessoa_juridica varchar(6)
    data_ult_alteracao           timestamp without time zone default now() not null,
    usuario_ult_alteracao        varchar(30) default 'anonimo' not null,
    versao                       integer not null default 0,
    sit_historico_plc            varchar(1) default 'A' not null,    
    primary key (id_informacoes_contribuinte)
);
 create table reinf.tb_periodo_apuracao_reinf (
    id_periodo_apuracao_reinf   bigint not null,
    competencia                 timestamp not null,
    per_apur                    varchar(7) not null,
    status_apuracao_reinf       varchar(20) not null,
    data_ult_alteracao          timestamp without time zone default now() not null,
    usuario_ult_alteracao       varchar(30) default 'anonimo' not null,
    versao                      integer not null default 0,
    sit_historico_plc           varchar(1) default 'A' not null,   
    primary key (id_periodo_apuracao_reinf)
);
create table reinf.tb_reinf_lotes ( 
    id_reinf_lotes              bigint not null,
    id_empresa_raiz_cnpj        bigint not null,
    periodo_apuracao_reinf      bigint not null,
    status_lote_reinf           varchar(5) not null,
    tipo_ambiente               bigint not null,
    num_lote                    varchar(36) not null,
    cnpj_prestador              varchar(14) not null,
    data_geracao                timestamp not null,    
    valor_base_inss             numeric(13,2) not null,
    valor_imposto_inss          numeric(13,2) not null,    
    data_ult_alteracao          timestamp without time zone default now() not null,
    usuario_ult_alteracao       varchar(30) default 'anonimo' not null,
    versao                      integer not null default 0,
    sit_historico_plc           varchar(1) default 'A' not null,      
    primary key (id_reinf_lotes)
);
create table reinf.tb_reinf_lotes_eventos (
    id_reinf_lotes_eventos bigint not null,
    reinf_lotes            bigint not null,
    status_reinf_evento    varchar(5) not null, 
    lote_evento            varchar(36) not null,    
    codigo_evento          varchar(6) not null,
    nome_evento            varchar(20) not null,
    seq_evento             Integer not null,    
    dh_process             varchar(20) not null,    
    nome_arquivo           varchar(100) not null,    
    num_protocolo          varchar(30) not null,    
    status_retorno         varchar(20) not null,
    id_servicos_tomados    bigint not null,
    data_ult_alteracao     timestamp without time zone default now() not null,
    usuario_ult_alteracao  varchar(30) default 'anonimo' not null,
    versao                 integer not null default 0,
    sit_historico_plc      varchar(1) default 'A' not null,     
    primary key (id_reinf_lotes_eventos)
);
create table reinf.tb_reinf_lotes_eventos_retorno (
    id_reinf_lotes_eventos_retorno bigint not null,    
    reinf_lotes_eventos            bigint not null,
    reinf_lotes_mensagens          bigint not null,
    localizacao_erro_aviso         varchar(100),
    data_ult_alteracao             timestamp without time zone default now() not null,
    usuario_ult_alteracao          varchar(30) default 'anonimo' not null,
    versao                         integer not null default 0,
    sit_historico_plc              varchar(1) default 'A' not null,     
    primary key (id_reinf_lotes_eventos_retorno)
);
create table reinf.tb_reinf_mensagens (
    id_reinf_mensagens     bigint not null,
    codigo_mensagem        varchar(20) not null,
    descricao_mensagem     varchar(800) not null,
    data_ult_alteracao     timestamp without time zone default now() not null,
    usuario_ult_alteracao  varchar(30) default 'anonimo' not null,
    versao                 integer not null default 0,
    sit_historico_plc      varchar(1) default 'A' not null,      
    primary key (id_reinf_mensagens)    
);
 create table reinf.tb_servicos_tomados (
    id_servicos_tomados           bigint not null,
    periodo_apuracao_reinf        bigint not null,
    per_apur                      varchar(7) not null,
    ind_retif                     varchar(1) not null,
    nr_recibo                     varchar(54),
    tipo_ambiente                 bigint not null,
    tp_insc                       varchar(5) not null,
    empresa_raiz_cnpj             bigint not null,
    tp_insc_estab                 varchar(5) not null,
    empresa                       bigint not null,
    ind_obra                      bigint not null,
    cnpj_prestador                varchar(14) not null,
    nome_prestador                varchar(60) not null,
    vlr_total_bruto               numeric(14,2) not null,
    vlr_total_base_ret            numeric(14,2) not null,
    vlr_total_ret_princ           numeric(14,2) not null,
    vlr_totalnret_princ           numeric(14, 2) default 0,
    vlr_totalnret_adic            numeric(14,2) default 0,    
    vlr_total_ret_adic            numeric(14,2) default 0,    
    contribuintecprb              varchar(1) not null,
    status_lote_reinf             varchar(5),
    valor_base_retorno_receita    numeric(14,2),
    valor_imposto_retorno_receita numeric(14,2),
    data_recibo_receita           timestamp,  
    data_ult_alteracao            timestamp without time zone default now() not null,
    usuario_ult_alteracao         varchar(30) default 'anonimo' not null,
    versao                        integer not null default 0,
    sit_historico_plc             varchar(1) default 'A' not null,     
    primary key (id_servicos_tomados)
);
 create table reinf.tb_servicos_tomados_nfs (
    id_servicos_tomados_nfs     bigint not null,
    servicos_tomados            bigint not null,
    serie                       varchar(5) not null,    
    num_docto                   varchar(9) not null,
    dt_emissaonf                timestamp not null,    
    vlr_bruto                   numeric(14,2) not null,
    obs                         varchar(250),    
    data_ult_alteracao          timestamp without time zone default now() not null,
    usuario_ult_alteracao       varchar(30) default 'anonimo' not null,
    versao                      integer not null default 0,
    sit_historico_plc           varchar(1) default 'A' not null,     
    primary key (id_servicos_tomados_nfs)
);
 create table reinf.tb_servicos_tomados_proc_ret_ad (
    id_servicos_tomados_proc_ret_ad bigint not null,
    servicos_tomados                bigint,
    tp_proc_ret_adic                bigint,
    tipo_processo                   varchar(5),
    nr_proc_ret_adic                varchar(21),    
    cod_susp_adic                   bigint,   
    valor_adic                      numeric(14,2),    
    data_ult_alteracao              timestamp without time zone default now() not null,
    usuario_ult_alteracao           varchar(30) default 'anonimo' not null,
    versao                          integer not null default 0,
    sit_historico_plc               varchar(1) default 'A' not null,     
    primary key (id_servicos_tomados_proc_ret_ad)
);
 create table reinf.tb_servicos_tomados_proc_ret_pr (
    id_servicos_tomados_proc_ret_pr bigint not null,
    servicos_tomados                bigint,
    tp_proc_ret_princ               bigint,
    tipo_processo                   varchar(5),
    nr_proc_ret_princ               varchar(21),    
    cod_susp_princ                  bigint,    
    valor_princ                     numeric(14,2),    
    data_ult_alteracao              timestamp without time zone default now() not null,
    usuario_ult_alteracao           varchar(30) default 'anonimo' not null,
    versao                          integer not null default 0,
    sit_historico_plc               varchar(1) default 'A' not null,     
    primary key (id_servicos_tomados_proc_ret_pr)
);
 create table reinf.tb_reinf_lotes_efd_receita (
    id_reinf_lotes_efd_receita  bigint not null,    
    status_lote_reinf           varchar(5) not null,
    evento                      varchar(6) not null,
    num_lote                    varchar(36) not null,
    periodo_apuracao_reinf      bigint not null,
    cnpj_empresa_raiz_reinf     bigint not null,
    empresa_reinf               bigint,
    attribute1                  bigint not null,
    usuario_criacao             varchar(30) not null,
    data_criacao                timestamp not null,
    usuario_exclusao_lote       varchar(30),
    data_exclusao_lote          timestamp,
    usuario_retorno_receita     varchar(30),
    data_retorno_receita        timestamp,    
    numero_recibo               varchar(50),   
    data_ult_alteracao          timestamp without time zone default now() not null,
    usuario_ult_alteracao       varchar(30) default 'anonimo' not null,
    versao                      integer not null default 0,
    sit_historico_plc           varchar(1) default 'A' not null,  
    primary key (id_reinf_lotes_efd_receita)
);
create table reinf.tb_reinf_arquivo (
    id bigint not null,
    dat_seguranca timestamp,
    tamanho integer,
    nome varchar(255),
    tipo varchar(255),
    usu_seguranca varchar(255),
    versao integer,
    binary_content bigint,
    primary key (id)
);
 create table reinf.tb_reinf_arquivo_conteudo (
    id bigint not null,
    conteudo_binario bytea,
    primary key (id)
);
 create table tb_reinf_lotes_reinf_arquivo (
    tb_reinf_lotes bigint not null,
    reinf_arquivo bigint not null
);


alter table reinf.tb_informacoes_contribuinte 
    add constraint FK_INFORMACOESCONTRIBUINTE_CLASSIFICACAOTRIBUTARIA 
    foreign key (classificacao_tributaria) 
    references reinf.tb_classificacao_tributaria;
alter table reinf.tb_informacoes_contribuinte 
    add constraint FK_SERVICOSTOMADOS_EMPRESA 
    foreign key (empresa_contato_reinf) 
    references bridge.tb_empresa;    
alter table reinf.tb_informacoes_contribuinte 
    add constraint FK_INFORMACOESCONTRIBUINTE_EMPRESARAIZCNPJ 
    foreign key (empresa_raiz_cnpj) 
    references bridge.tb_empresa_raiz_cnpj;
alter table reinf.tb_informacoes_contribuinte 
    add constraint FK_INFORMACOESCONTRIBUINTE_PERIODOAPURACAOREINF 
    foreign key (periodo_apuracao_reinf) 
    references reinf.tb_periodo_apuracao_reinf;
alter table reinf.tb_informacoes_contribuinte 
    add constraint FK_INFORMACOESCONTRIBUINTE_TIPOAMBIENTE 
    foreign key (tipo_ambiente) 
    references bridge.tb_tipo_ambiente;  
alter table reinf.tb_reinf_lotes 
    add constraint FK_REINFLOTES_CNPJEMPRESARAIZREINF 
    foreign key (cnpj_empresa_raiz_reinf) 
    references bridge.tb_empresa_raiz_cnpj;
 alter table reinf.tb_reinf_lotes 
    add constraint FK_REINFLOTES_PERIODOAPURACAOREINF 
    foreign key (periodo_apuracao_reinf) 
    references reinf.tb_periodo_apuracao_reinf;    
 alter table reinf.tb_reinf_lotes 
    add constraint FK_REINFLOTES_TIPOAMBIENTE 
    foreign key (tipo_ambiente) 
    references bridge.tb_tipo_ambiente;
 alter table reinf.tb_reinf_lotes_eventos 
    add constraint FK_REINFLOTESEVENTOS_REINFLOTES 
    foreign key (reinf_lotes) 
    references reinf.tb_reinf_lotes;    
 alter table reinf.tb_reinf_lotes_eventos_retorno 
    add constraint FK_REINFLOTESEVENTOSRETORNO_REINFLOTESEVENTOS 
    foreign key (reinf_lotes_eventos) 
    references reinf.tb_reinf_lotes_eventos;    
 alter table reinf.tb_reinf_lotes_eventos_retorno 
    add constraint FK_REINFLOTESEVENTOSRETORNO_REINFLOTESMENSAGENS 
    foreign key (reinf_lotes_mensagens) 
    references reinf.tb_reinf_mensagens;    
 alter table reinf.tb_servicos_tomados 
    add constraint FK_SERVICOSTOMADOS_EMPRESA 
    foreign key (empresa) 
    references bridge.tb_empresa; 
alter table reinf.tb_servicos_tomados 
    add constraint FK_SERVICOSTOMADOS_EMPRESARAIZCNPJ 
    foreign key (empresa_raiz_cnpj) 
    references bridge.tb_empresa_raiz_cnpj;
 alter table reinf.tb_servicos_tomados 
    add constraint FK_SERVICOSTOMADOS_PERIODOAPURACAOREINF 
    foreign key (periodo_apuracao_reinf) 
    references reinf.tb_periodo_apuracao_reinf;  
 alter table reinf.tb_servicos_tomados 
    add constraint FK_SERVICOSTOMADOS_TIPOAMBIENTE 
    foreign key (tipo_ambiente) 
    references bridge.tb_tipo_ambiente;    
 alter table reinf.tb_servicos_tomados_nfs 
    add constraint FK_SERVICOSTOMADOSNFS_SERVICOSTOMADOS 
    foreign key (servicos_tomados) 
    references reinf.tb_servicos_tomados;    
 alter table reinf.tb_servicos_tomados_proc_ret_ad 
    add constraint FK_SERVICOSTOMADOSPROCRETAD_SERVICOSTOMADOS 
    foreign key (servicos_tomados) 
    references reinf.tb_servicos_tomados;
 alter table reinf.tb_servicos_tomados_proc_ret_pr 
    add constraint FK_SERVICOSTOMADOSPROCRETPR_SERVICOSTOMADOS 
    foreign key (servicos_tomados) 
    references reinf.tb_servicos_tomados;     


 create sequence REINF.SE_CLASSIFICACAO_TRIBUTARIA;
 create sequence REINF.SE_INFORMACOES_CONTRIBUINTE;
 create sequence REINF.SE_PERIODO_APURACAO_REINF;
 create sequence REINF.SE_REINF_LOTES;
 create sequence REINF.SE_REINF_LOTES_EVENTOS;
 create sequence REINF.SE_REINF_LOTES_EVENTOS_RETORNO;
 create sequence REINF.SE_REINF_MENSAGENS;
 create sequence REINF.SE_SERVICOS_TOMADOS;
 create sequence REINF.SE_SERVICOS_TOMADOS_NFS;
 create sequence REINF.SE_SERVICOS_TOMADOS_PROC_RET_AD;
 create sequence REINF.SE_SERVICOS_TOMADOS_PROC_RET_PR;
 create sequence REINF.SE_REINF_LOTES_EFD_RECEITA;
 create sequence REINF.SE_REINF_ARQUIVO_CONTEUDO_PLC;
 create sequence REINF.SE_REINF_ARQUIVO_PLC; 
 create sequence hibernate_sequence;
 
 
 
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (1, 'Empresa enquadrada no regime de tributa��o Simples Nacional com tributa��o previdenci�ria substitu�da');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (2 ,'Empresa enquadrada no regime de tributa��o Simples Nacional com tributa��o previdenci�ria n�o substitu�da');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (3, 'Empresa enquadrada no regime de tributa��o Simples Nacional com tributa��o previdenci�ria substitu�da e n�o substitu�da');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (4, 'MEI - Micro Empreendedor Individual');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (6, 'Agroind�stria');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (7, 'Produtor Rural Pessoa Jur�dica');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (8, 'Cons�rcio Simplificado de Produtores Rurais');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (9, '�rg�o Gestor de M�o de Obra');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (10, 'Entidade Sindical a que se refere a Lei 12.023/2009');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (11, 'Associa��o Desportiva que mant�m Clube de Futebol Profissional');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (13, 'Banco, caixa econ�mica, sociedade de cr�dito, financiamento e investimento e demais empresas relacionadas no par�grafo 1� do art. 22 da Lei 8.212./91');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (14, 'Sindicatos em geral, exceto aquele classificado no c�digo [10]');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (21, 'Pessoa F�sica, exceto Segurado Especial');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (22, 'Segurado Especial');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (60, 'Miss�o Diplom�tica ou Reparti��o Consular de carreira estrangeira');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (70, 'Empresa de que trata o Decreto 5.436/2005');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (80, 'Entidade Imune ou Isenta');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (85, 'Ente Federativo, �rg�os da Uni�o, Autarquias e Funda��es P�blicas');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (99, 'Pessoas Jur�dicas em Geral');
commit;

insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0000', 'Sem Erro');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0001', 'Ocorreu uma falha ao realizar a verifica��o da vers�o das tabelas do sistema no Ambiente Nacional');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0002', 'Ocorreu uma falha ao atualizar as tabelas do sistema');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0003', 'Erro na cadeia do certificado digital do signat�rio ou do solicitante da informa��o');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0004', 'A raiz do certificado digital do signat�rio ou do solicitante da informa��o dever� pertencer a Autoridade Certificadora Raiz Brasileira (ICP-Brasil).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0005', 'O certificado digital do signat�rio ou do solicitante da informa��o encontra-se revogado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0006', 'O certificado digital do signat�rio ou do solicitante da informa��o encontra-se expirado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0007', 'O certificado digital do signat�rio ou do solicitante da informa��o n�o � v�lido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0008', 'Par�metro {0} obrigat�rio n�o informado');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0009', 'Par�metro {0} inv�lido');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0015', 'Deve ser utilizado certificado digital do tipo e-CNPJ ou e-PJ cujo CNPJ base seja o mesmo do contribuinte respons�vel pela informa��o, ou do tipo e-CPF ou e-PF cujo CPF perten�a ao representante legal do contribuinte ou qualquer certificado que perten�a a um procurador devidamente habilitado no sistema de Procura��o Eletr�nica da RFB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0016', 'O evento deve ser assinado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0017', 'Assinatura do evento inv�lida.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0018', 'Falha na disponibiliza��o do resultado do processamento na fila de sa�da.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0019', 'Ocorreu uma falha no acesso ao Sistema CNPJ. Aguarde alguns minutos e tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0020', 'Ocorreu uma falha no acesso ao Sistema de Procura��o Eletr�nica. Aguarde alguns minutos e tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0021', 'Falha ao assinar o recibo de entrega do Evento. Envie o evento novamente, para obter o recibo de entrega.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0022', 'O evento j� se encontra na base de dados do sistema.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0023', 'J� existe no sistema um evento com mesma chave: <<exibir os campos que comp�em a chave>>.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0025', 'Falha no processamento da solicita��o. Tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0026', 'Falha ao inserir evento no sistema. Tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0029', '"Erro ao gerar DCTF: <n�mero do erro retornado pela DCTF> - <descri��o do erro retornado pela DCTF>."');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0030', '"A estrutura do arquivo XML esta em desconformidade com o esquema XSD." + <erro retornado pelo XSD>');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0031', 'Ocorreu uma falha durante o envio dos d�bitos apurados para a DCTF. Tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0032', 'A altera��o do conte�do de tabela ocorrida ap�s a presta��o de informa��es da EFD causaram inconsist�ncia nas informa��es prestadas.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0033', 'O n�mero de recibo informado deve ser relativo a um evento de mesmo tipo indicado em na tag tpEvento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0034', 'O n�mero de recibo informado deve ser relativo a um evento ativo e do mesmo contribuinte.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0035', 'Este evento n�o pode ser exclu�do.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0036', 'A vers�o do leiaute do evento, definida no namespace do evento, dever� ser aceita pelo sistema (por padr�o a �ltima vigente para o tipo de evento)');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0037', 'J� existe na base de dados do Ambiente Nacional um evento com mesma identifica��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0038', 'Para envio de eventos para a produ��o, o ambiente deve ser igual a 1. Para envio de eventos para a pr�-produ��o, deve ser igual a 2 ou 3.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0052', 'Foi informado um valor com tamanho inv�lido para o campo {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0053', 'Quantidade de casas decimais inv�lida no campo: {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0054', 'O grupo ''{0}'' � de preenchimento obrigat�rio.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0092', 'Vers�o do lote inv�lida. Deve ser utilizada a vers�o {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0039', 'N�mero de recibo inv�lido');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0051', 'Foi informado um valor com tipo inv�lido para o campo {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0055', 'O campo {0} � obrigat�rio e n�o foi informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0056', 'O Grupo ''{0}'' n�o deve ser preenchido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0040', 'Erro atualiza��o controle escritura��o');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0041', 'Erro atualiza��o situa��o evento de fechamento');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0042', 'Somente podem utilizar a EFD-REINF os contribuintes com obrigatoriedade de entrega ou aqueles que fizeram op��o por antecipar sua utiliza��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1000', 'CNPJ n�o encontrado no cadastro do Sistema CNPJ.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1001', 'Data inv�lida. A data informada deve ser uma data v�lida posterior ou igual � data inicial de implanta��o do Reinf.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1002', 'Data inv�lida. A data informada deve ser posterior ou igual � data inicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1003', 'Telefone inv�lido. Devem ser informados apenas n�meros, com m�nimo de 10 d�gitos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1004', 'E-mail inv�lido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1005', 'J� existe outro registro com per�odo conflitante com o per�odo informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1006', 'O m�s/ano de in�cio da validade das informa��es deve ser igual ou posterior � data de in�cio das atividades da empresa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1007', 'O CNPJ dever� ser informado com 8 d�gitos, ou seja, apenas a Raiz/Base, com exce��o no caso de �rg�os p�blicos da administra��o direta federal, com natureza jur�dica igual a [101-5], [104-0], [107-4], [116-3], quando o CNPJ dever� ser informado com 14 d�gitos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1008', 'Registro n�o pode ser exclu�do (registro n�o existe ou j� foi referenciado em outros eventos).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1009', 'N�o existem Informa��es do Contribuinte vigente na data do evento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1010', 'ID inv�lido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1011', 'CNPJ inv�lido: pertence a pessoa jur�dica Inapta pelo motivo de Inexist�ncia de Fato.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1012', 'CNPJ inv�lido: est� baixado no per�odo de apura��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1013', 'CNPJ inv�lido: est� anulado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1014', 'CPF inv�lido: CNPJ cancelado no per�odo de apura��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1015', 'Classifica��o tribut�ria inv�lida. Os c�digos 21 e 22 s� podem ser utilizados por pessoa f�sica. Os demais c�digos devem ser utilizados por pessoa jur�dica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1016', 'IndObra incompat�vel com tipo de inscri��o do tomador de servi�o (tpInscTomador). Se tpInscTomador = 1, indObra deve ser 0. Se tpInscTomador = 4, indObra deve ser 1 ou 2.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1017', 'CPF n�o consta na base da RFB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1018', 'O preenchimento do campo telefone fixo � obrigat�rio se o campo telefone celular n�o for preenchido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1019', 'A situa��o � obrigat�ria para pessoa jur�dica e n�o deve ser informada para pessoa f�sica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1020', 'CNPJ inv�lido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1021', 'CPF inv�lido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1022', 'O n�mero do recibo deve ser referente a um evento ativo, com mesmo tipo e per�odo de apura��o. S� � poss�vel enviar retifica��es se o per�odo n�o estiver fechado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1023', 'J� foi enviado outro evento com a mesma nota fiscal: CNPJ {cnpjPrestador}, S�rie {serie} e N�mero {numDocto}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1024', 'O n�mero do recibo deve ser informado para eventos retificadores.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1025', 'O CNPJ informado deve pertencer ao contribuinte declarante.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1026', 'A inscri��o (CNPJ) informada deve ser compat�vel com o tipo de inscri��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1027', 'Para pessoa f�sica deve ser informado o valor 1-Obra de Constru��o Civil - Empreitada Total ou 2-Obra de Constru��o Civil - Empreitada Parcial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1028', 'N�o � permitido o envio de mais de um evento para o contribuinte, num mesmo per�odo de apura��o, para o mesmo estabelecimento tomador e mesmo prestador, exceto se for para retifica��o de um evento enviado anteriormente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1029', 'O CNPJ informado n�o deve pertencer ao contribuinte declarante.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1030', 'O valor informado deve corresponder a soma do valor bruto da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1031', 'O valor informado deve corresponder a soma da base de c�lculo da reten��o da contribui��o previdenci�ria da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1032', 'O valor informado deve corresponder a soma do valor da reten��o das notas fiscais de servi�o emitidas para o contratante menos a soma do valor da reten��o destacada na(s) nota fiscal(ias), relativo aos servi�os subcontratados, se houver. MS1033 - O valor informado deve corresponder a soma do valor do adicional de reten��o da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1034', 'O valor informado deve corresponder a soma do valor da reten��o principal que deixou de ser efetuada pelo contratante ou que foi depositada em ju�zo em decorr�ncia de decis�o judicial/administrativa da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1035', 'O valor informado n�o pode ser maior que a soma do valor da reten��o principal das notas fiscais de servi�o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1036', 'IndObra incompat�vel com tipo de inscri��o do estabelecimento (tpInscEstab). Se tpInscEstab = 1, indObra deve ser 0. Se tpInscEstab = 4, indObra deve ser 1 ou 2. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1037', 'N�o foi enviado evento R-1070 para o n�mero do processo judicial, com validade vigente no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1038', 'O valor informado deve corresponder a soma do valor da reten��o adicional que deixou de ser efetuada pelo contratante ou que foi depositada em ju�zo em decorr�ncia de decis�o judicial/administrativa da(s) nota(s) fiscal(is) correspondente(s). Deve corresponder a soma do campo {vlrNRetAdic } dos registros vinculados. N�o pode ser maior que {vlrTotalRetAdic}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1039', 'O valor informado n�o pode ser maior que a soma do valor do adicional de reten��o das notas fiscais.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1041', 'O m�s/ano da data informada deve ser igual ao m�s/ano do per�odo de apura��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1042', 'O valor informado deve ser maior que zero.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1043', 'Em caso de altera��o de per�odo de validade das informa��es, n�o deve existir outro registro na tabela com o mesmo c�digo de identifica��o (chave) em per�odo de vig�ncia conflitante com o novo per�odo de validade informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1044', 'O preenchimento do CNAE (campo codAtivEcon) � obrigat�rio para Contribuinte da Contribui��o Previdenci�ria sobre a Receita Bruta (CPRB) - Reten��o 3,5%');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1045', 'O valor informado deve existir na Tabela de C�digo de Atividades, Produtos e Servi�os Sujeitos � Contribui��o Sobre a Receita Bruta, no grupo : I - Pessoas Jur�dicas Prestadoras de Servi�os.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1046', 'O valor informado deve corresponder ao valor bruto das notas fiscais menos os valores de materiais e equipamentos, valores do custo da alimenta��o e valores do custo do fornecimento de transporte.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1047', 'O valor informado deve ser igual a 11% da base de c�lculo da reten��o da contribui��o previdenci�ria.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1048', 'O valor informado deve ser igual a 3,5% da base de c�lculo da reten��o da contribui��o previdenci�ria.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1049', 'O valor informado deve corresponder ao valor da reten��o relativa aos servi�os contidos na nota fiscal/fatura menos o valor da reten��o destacada na(s) nota fiscal(ais), relativo aos servi�os subcontratados menos o valor da reten��o principal que deixou de ser efetuada pelo contratante ou que foi depositada em ju�zo em decorr�ncia de decis�o judicial/administrativa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1050', 'O valor informado n�o pode ser superior ao valor da reten��o relativa aos servi�os contidos na nota fiscal/fatura.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1051', 'O valor informado n�o pode ser superior ao valor da reten��o (11% da base de c�lculo ou 3,5% da base de c�lculo (para Contribuinte da Contribui��o Previdenci�ria sobre a Receita Bruta), deduzido o valor da reten��o destacada na nota fiscal relativo aos servi�os subcontratados).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1052', 'O somat�rio dos valores dos servi�os prestados por segurados em condi��es especiais, cuja atividade permita concess�o de aposentadoria especial ap�s 15, 20 e 25 anos de contribui��o n�o pode ser superior � base de c�lculo da reten��o da contribui��o previdenci�ria. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1053', 'O valor informado neste campo deve corresponder ao somat�rio de 4% sobre os valores dos servi�os prestados por segurados em condi��es especiais, cuja atividade permita concess�o de aposentadoria especial ap�s 15 anos de contribui��o mais 3% sobre os valores dos servi�os prestados por segurados em condi��es especiais, cuja atividade permita concess�o de aposentadoria especial ap�s 20 anos de contribui��o mais 2% sobre os valores dos servi�os prestados por segurados em condi��es especiais, cuja atividade permita concess�o de aposentadoria especial ap�s 25 anos de contribui��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1054', 'O valor informado n�o pode ser superior ao valor adicional apurado de reten��o da nota fiscal, caso os servi�os tenham sido prestados sob condi��es especiais que ensejem aposentadoria especial aos trabalhadores ap�s 15, 20, ou 25 anos de contribui��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1055', 'J� foi enviado outro evento com a mesma nota fiscal: CNPJ do estabelecimento prestador {nrInscricao}, S�rie {serie} e N�mero {numDocto}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1056', 'O valor informado neste campo deve corresponder ao valor adicional apurado de reten��o da nota fiscal, caso os servi�os tenham sido prestados sob condi��es especiais que ensejem aposentadoria especial aos trabalhadores ap�s 15, 20, ou 25 anos de contribui��o menos o valor da reten��o adicional que deixou de ser efetuada pelo contratante ou que foi depositada em ju�zo em decorr�ncia de decis�o judicial/administrativa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1057', 'O preenchimento do c�digo da conta anal�tica cont�bil � obrigat�rio para empresa obrigada a entregar a ECD.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1058', 'O valor informado deve ser menor ou igual a 11% da base de c�lculo da reten��o da contribui��o previdenci�ria. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1059', 'O valor informado deve ser menor ou igual a 3,5% da base de c�lculo da reten��o da contribui��o previdenci�ria.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1060', 'N�o existe evento de fechamento para o per�odo informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1061', 'N�o foram enviados eventos R-2010 - Reten��o Contribui��o Previdenci�ria � Servi�os Tomados no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1062', 'Foram enviados eventos R-2010 - Reten��o Contribui��o Previdenci�ria � Servi�os Tomados no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1063', 'N�o foram enviados eventos R-2020 - Reten��o Contribui��o Previdenci�ria � Servi�os Prestados no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1064', 'Foram enviados eventos R-2020 - Reten��o Contribui��o Previdenci�ria � Servi�os Prestados no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1065', 'N�o foram enviados eventos R-2030 - Recurso Recebido p/ Assoc. Desp. que Mantenha Equipe de Futebol Profissional no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1066', 'Foram enviados eventos R-2030 - Recurso Recebido p/ Assoc. Desp. que Mantenha Equipe de Futebol Profissional no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1067', 'N�o foram enviados eventos R-2040 - Recurso Repassado p/ Assoc. Desp. que Mantenha Equipe de Futebol no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1068', 'Foram enviados eventos R-2040 - Recurso Repassado p/ Assoc. Desp. que Mantenha Equipe de Futebol no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1069', 'N�o foram enviados eventos R-2050 - Comercializa��o da Produ��o Por Produtor Rural PJ/Agroind�stria no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1070', 'Foram enviados eventos R-2050 - Comercializa��o da Produ��o Por Produtor Rural PJ/Agroind�stria no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1071', 'N�o foram enviados eventos R-2060 - Informa��es � CPRBno per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1072', 'Foram enviados eventos R-2060 - Informa��es � CPRB no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1073', 'N�o foram enviados eventos R-2070 � Reten��es na Fonte (IR,CSLL,Cofins,PIS/PASEB) � Pagamento diversos no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1074', 'Foram enviados eventos R-2070 � Reten��es na Fonte (IR,CSLL,Cofins,PIS/PASEB) � Pagamento diversos no per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1075', 'O preenchimento � obrigat�rio se no per�odo de apura��o o contribuinte n�o contratou servi�os sujeitos � reten��o de contribui��o previdenci�ria, n�o prestou servi�os sujeitos � reten��o de contribui��o previdenci�ria, n�o possui informa��es sobre recursos recebidos (associa��o desportiva que mant�m equipe de futebol profissional), n�o possui informa��es sobre repasses efetuados � associa��o desportiva que mant�m equipe de futebol profissional, n�o possui informa��es de comercializa��o de produ��o (produtor rural PJ/agroind�stria), n�o possui informa��es sobre a apura��o da Contribui��o Previdenci�ria sobre a Receita Bruta e n�o possui informa��es de pagamento diversos no per�odo de apura��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1076', 'J� existe outra tag <<nome da tag>> com mesma chave no evento. N�o pode ser informada mais de uma tag com a mesma chave.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1077', 'O preenchimento � obrigat�rio para pessoa jur�dica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1078', 'A EFD j� foi fechada para o per�odo informado, ou existe um evento de fechamento em processamento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1079', 'Em caso de altera��o, deve existir registro, na tabela de contribuinte, com o mesmo per�odo de validade informado no evento nas tags iniValid e fimValid do grupo altera��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1080', 'O Valor da contribui��o para o Senar com exigibilidade suspensa � obrigat�rio se n�o tiverem sido informados o Valor da Contribui��o Previdenci�ria com exigibilidade suspensa e o Valor da contribui��o para Gilrat com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1081', 'O Valor da Contribui��o Previdenci�ria com exigibilidade suspensa � obrigat�rio se n�o tiverem sido informados o Valor da contribui��o para Gilrat com exigibilidade suspensa e o Valor da contribui��o para o Senar com exigibilidade suspensa. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1082', 'O Valor da contribui��o para Gilrat com exigibilidade suspensa � obrigat�rio se n�o tiverem sido informados o Valor da Contribui��o Previdenci�ria com exigibilidade suspensa e o Valor da contribui��o para o Senar com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1083', 'O valor informado deve ser igual a soma dos valores informados no campo Valor total da comercializa��o');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1084', 'O valor informado deve ser igual a soma dos valores informados no campo Valor Bruto da nota fiscal/fatura.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1085', 'O valor informado n�o pode ser maior que o valor informado no campo Valor da Receita Bruta Total do Estabelecimento no Per�odo.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1086', 'O evento R-2050-Comercializa��o da Produ��o Por Produtor Rural PJ/Agroind�strias� pode ser enviado por contribuinte cuja classifica��o tribut�ria seja igual a 6 - Agroind�stria. ou 7 - Produtor Rural Pessoa Jur�dica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1087', 'O indicativo de suspens�o da exigibilidade deve ser igual a 3, 90 ou 92 quando o tipo do processo informado for igual a 1.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1088', 'O indicativo de suspens�o da exigibilidade deve ser igual a 01, 02, 04, 05, 08, 09, 10, 11, 12, 13, 90 ou 92 quando o tipo do processo informado for igual a 2.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1089', 'N�o � permitido o envio de mais de um evento para o contribuinte, num mesmo per�odo de apura��o para o mesmo estabelecimento e tomador, exceto se for para retifica��o de um evento enviado anteriormente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1090', 'Conte�do inv�lido. Preenchimento obrigat�rio se tipo do processo igual a 2 (Judicial). Caso contr�rio, n�o deve ser informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1091', 'N�o � permitido o envio de mais de um evento no mesmo per�odo de apura��o pelo mesmo estabelecimento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1092', 'O evento que est� sendo exclu�do pertence a um per�odo de apura��o para o qual j� exista encerramento das informa��es, o evento de exclus�o somente ser� aceito ap�s o evento de "reabertura" das informa��es (R-2098).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1093', 'O campo {classTrib} do Contribuinte (R-1000) dever� ser igual a 11 - Associa��o Desportiva que mant�m Clube de Futebol Profissional.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1094', 'O valor informado deve ser igual a soma dos valores brutos dos repasses efetuados, informados no Detalhamento dos recursos recebidos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1095', 'O valor deve corresponder a soma dos valores da apura��o da reten��o que deveria sofrer a associa��o desportiva, informados no Detalhamento dos recursos recebidos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1096', 'O valor informado deve corresponder a soma dos valores da reten��o que deixou de ser feita pela empresa que efetuou o repasse ou que foi depositada em ju�zo em decorr�ncia de decis�o judicial/administrativa, informados no Detalhamento dos recursos recebidos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1097', 'O valor informado deve ser igual a 5% do Valor Bruto do repasse efetuado a t�tulo de patroc�nio, publicidade, licenciamento, etc.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1098', 'O valor informado n�o pode ser maior que o Valor da apura��o da reten��o que deveria sofrer a associa��o desportiva.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1099', 'O CNPJ informado deve ser um CNPJ diferente do CNPJ da empresa declarante.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1100', 'O campo � obrigat�rio se o valor da reten��o que deixou de ser feita pela entidade que efetuou o repasse ou que foi depositada em ju�zo em decorr�ncia da decis�o judicial/administrativa for maior que zero.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1101', 'O valor informado n�o pode ser maior que 5% do valor bruto dos recursos repassados.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1102', 'A data informada n�o pode ser maior que o ano/m�s corrente. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1103', 'O per�odo de refer�ncia das informa��es dever� estar no no formato AAAA-MM, exceto se {tpEvento} = [R-3010], situa��o em que deve ser informado no formato AAAA-MM-DD.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1105', 'Indicativo de Suspens�o n�o cadastrado para esse processo.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1106', 'O c�digo da atividade econ�mica informado deve ser um c�digo v�lido da tabela 9.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1107', 'O campo n�o deve ser preenchido se o prestador n�o for enquadrado na desonera��o da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1108', 'As informa��es de processo principal s�o obrigat�rias quando a (Soma do valor da reten��o das notas fiscais de servi�o emitidas para o contratante) for maior que 0 (zero), e n�o devem ser informadas em caso contr�rio.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1109', 'As informa��es de processo adicional s�o obrigat�rias quando a soma do valor do adicional de reten��o das notas fiscais for maior que 0 (zero), e n�o devem ser informadas em caso contr�rio.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1110', 'O preenchimento do c�digo de suspens�o � obrigat�rio quando houver a informa��o de mais de um Indicativo de Suspens�o para o processo.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1111', 'O preenchimento do campo {codAtivEcon} � obrigat�rio se o prestador for enquadrado na desonera��o da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1112', 'O campo n�o deve ser preenchido para o contribuinte n�o enquadrado na desonera��o da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1113', 'O preenchimento do campo {codAtivEcon} � obrigat�rio para contribuinte enquadrado na desonera��o da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1114', 'O campo n�o deve ser validado na tabela de processo para processo judicial do prestador - tipo processo = 3.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1115', 'O n�mero de inscri��o informado deve ser um CNO de propriedade do estabelecimento prestador no Sistema CNO.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1116', 'O n�mero de inscri��o informado deve ser um CNO de propriedade do contribuinte declarante no Sistema CNO.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1117', 'O n�mero de inscri��o informado deve ser um CNO n�o pertencente ao contribuinte declarante no Sistema CNO.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1118', 'Se {indSusp} = [90], deve ser preenchido obrigatoriamente com [N].');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1119', 'Para recep��o do evento de Contribui��o Previdenci�ria sobre a Receita Bruta, a {classTrib} do R-1000 deve ser igual a 99 (Pessoas Jur�dicas em Geral) e {indDesoneracao} deve ser igual a 1.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1120', 'O valor informado deve corresponder a 2,5% do valor total da comercializa��o cujo indicativo de comercializa��o � igual a 1 - Comercializa��o da Produ��o por Prod. Rural PJ/Agroind�stria, exceto para entidades executoras do PAA. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1121', 'O valor informado deve corresponder a 0,1% do valor total da comercializa��o cujo indicativo de comercializa��o � igual a 1 - Comercializa��o da Produ��o por Prod. Rural PJ/Agroind�stria, exceto para entidades executoras do PAA.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1122', 'O valor informado deve corresponder a 0,25% do valor total da comercializa��o cujo indicativo de comercializa��o � igual a 1 - Comercializa��o da Produ��o por Prod. Rural PJ/Agroind�stria, exceto para entidades executoras do PAA ou 8 - Comercializa��o da Produ��o para Entidade do Programa de Aquisi��o de Alimentos - PAA ou 9 - Comercializa��o direta da Produ��o no Mercado Externo.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1123', 'O valor informado deve corresponder ao valor total da receita da atividade {vlrRecBrutaAtiv} + o Valor total das Adi��es da Receita Bruta {vlrAdicRecBruta} - o Valor total das Exclus�es da Receita Bruta {vlrExcRecBruta}');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1124', 'O valor informado deve ser igual a soma dos valores informados no campo Valor Contribui��o Previdenci�ria sobre a Receita Bruta do grupo atividade econ�mica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1125', 'O campo tem preenchimento obrigat�rio porque o Valor da Contribui��o Previdenci�ria com exigibilidade suspensa foi informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1126', 'O valor informado deve ser igual ao somat�rio do campo valor bruto {vlrBruto} do grupo nota fiscal de servi�os {nfs}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1127', 'O valor informado deve ser igual a soma dos valores informados no campo {vlrAjuste} cujo {tpAjuste} seja igual a "0" - Ajuste de redu��o. Caso n�o haja ajuste de redu��o, preencher com 0 (zero). ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1128', 'O valor informado n�o pode ser maior que o valor total da receita bruta da atividade econ�mica {vlrRecBrutaAtiv}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1129', 'O valor informado deve ser igual a soma dos valores informados no campo {vlrAjuste} cujo {tpAjuste} seja igual a "1" - Ajuste de adi��o. Caso n�o haja ajuste de adi��o, preencher com 0 (zero). ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1130', 'O valor informado deve ser igual ao Valor da Base de C�lculo da Contribui��o Previdenci�ria sobre a Receita Bruta {vlrBcCPRB} multiplicado pela al�quota do c�digo da atividade econ�mica {codAtivEcon} da tabela 9.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1131', 'M�s / ano inv�lido. O M�s / ano informado deve ser v�lido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1132', 'N�o pode haver altera��o ou exclus�o de n�mero de processo {nrProc} + c�digo suspens�o {codSusp} que esteja sendo utilizado em outro evento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1133', 'O campo {indSitPJ} dever� ser obrigatoriamente informado apenas se {tpInscr} = 1 (CNPJ), caso contr�rio n�o dever� ser informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1135', 'A soma dos valores da reten��o das Informa��es de processos relacionados a n�o reten��o de contribui��o previdenci�ria , com exce��o dos valores informados para processos sem suspens�o de exigibilidade, deve ser igual ao valor da reten��o de contribui��o previdenci�ria principal que deixou de ser efetuada em fun��o de processo administrativo ou judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1136', 'A soma dos valores das reten��es das Informa��es de processos relacionados a n�o reten��o de contribui��o previdenci�ria adicional , com exce��o dos valores informados para processos sem suspens�o de exigibilidade, deve ser igual ao Valor da reten��o de contribui��o previdenci�ria adicional que deixou de ser efetuada em fun��o de processo administrativo ou judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1137', 'O declarante do evento correspondente ao {nrRecibo) informado dever� ser o mesmo informado no campo {nrInsc}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1138', 'O evento n�o pode ser recebido porque existe fechamento para o per�odo de apura��o informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1181', 'A quantidade de ingressos vendidos {qtdeIngrVendidos} n�o pode ser superior a quantidade de ingressos colocados � venda {qtdeIngrVenda}');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1182', 'N�o � permitido o envio de mais de um evento para o contribuinte num mesmo per�odo de apura��o');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1183', 'O valor da reten��o {vlrRetencao} informado n�o pode ser maior que 11% da Base de c�lculo da reten��o da contribui��o previdenci�ria {vlrBaseRet}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1184', 'O CNPJ do Ente Federativo Respons�vel � obrigat�rio se o �rg�o P�blico {ideContri} n�o � o Ente Federativo Respons�vel e n�o pode ser informado caso contr�rio. O CNPJ informado dever� ser validado com a informa��o referente ao Ente Federativo Respons�vel (EFR) existente no cadastro do CNPJ da RFB. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1185', 'Obrigat�rio se a Natureza Jur�dica {natJurid} = [102-3, 103-1, 105-8, 106-6, 108-2, 117-1, 118-0, 123-6, 124-4]). N�o dever� ser informado nos demais casos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1186', 'Foram encontrados caracteres especiais restritos no conte�do das tags do evento');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1187', 'A soma dos valores da reten��o das Informa��es de processos relacionados a n�o reten��o de contribui��o previdenci�ria, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, deve ser igual ao Valor da reten��o que deixou de ser feita pela entidade que efetuou o repasse ou que foi depositada em ju�zo em decorr�ncia da decis�o judicial/administrativa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1188', 'O grupo infoProc � de preenchimento obrigat�rio se o valor total n�o retido {vlrTotalNRet} for maior que zero.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1189', 'O Valor Total da Contribui��o Previdenci�ria com exigibilidade suspensa deve ser igual ao somat�rio dos valores da Contribui��o Previdenci�ria com exigibilidade suspensa das Informa��es de processos relacionados a Suspens�o da Contribui��o previdenci�ria, com exce��o dos valores informados para processos sem suspens�o de exigibilidade.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1190', 'Preenchimento obrigat�rio se {vlrCPSuspTotal} > 0.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1191', 'A soma dos valores da Contribui��o Previdenci�ria com exigibilidade suspensa das Informa��es de processos administrativos/judiciais com decis�o/senten�a favor�vel ao contribuinte, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, deve ser igual ao Valor da Contribui��o Previdenci�ria com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1192', 'A soma dos valores da Contribui��o Gilrat com exigibilidade suspensa das Informa��es de processos administrativos/judiciais com decis�o/senten�a favor�vel ao contribuinte, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, deve ser igual ao Valor da Contribui��o Gilrat com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1193', 'A soma dos valores da Contribui��o para o Senar com exigibilidade suspensa das Informa��es de processos administrativos/judiciais com decis�o/senten�a favor�vel ao contribuinte, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, deve ser igual ao Valor da Contribui��o para o Senar com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1194', 'Em caso de altera��o, deve existir registro, na tabela de processos, com o mesmo tipo, n�mero de processo e per�odo de validade informados no evento nas tags iniValid e fimValid do grupo altera��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1195', 'A soma dos valores da Contribui��o Previdenci�ria com exigibilidade suspnsa das Informa��es de processos relacionados a Suspens�o da CPRB, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, deve ser igual ao Valor da Contribui��o Previdenci�ria com exigibilidade suspensa informada para o estabelecimento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1196', 'N�mero de processo inv�lido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1197', 'As Informa��es Complementares do Processo Judicial s�o obrigat�rias para Processo Judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1198', 'As Informa��es Complementares do Processo Judicial s� devem ser informadas para Processo Judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1199', 'Preenchimento obrigat�rio se {vlrCPSuspTotal} ou {vlrRATSuspTotal} ou {vlrSenarSuspTotal} forem > 0');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1200', 'O grupo infoProc (Informa��es de Processos) n�o deve ser informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1201', 'Os processos origin�rios do Conselho Nacional de Justi�a, Justi�a do Trabalho, Justi�a Eleitoral, Justi�a Militar da Uni�o e Justi�a Militar Estadual n�o podem ser utilizados para a suspens�o de d�bitos na RFB');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1202', 'O somat�rio dos valores da Contribui��o Previdenci�ria com exigibilidade suspensa, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, n�o deve ser superior a 2,5% do valor total da comercializa��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1203', 'O somat�rio dos valores da Contribui��o Gilrat com exigibilidade suspensa, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, n�o deve ser superior a 0,1% do valor total da comercializa��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1204', 'O somat�rio dos valores da Contribui��o para o Senar com exigibilidade suspensa, com exce��o dos valores informados para processos sem suspens�o de exigibilidade, n�o deve ser superior a 0,25% do valor total da comercializa��o.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1205', 'O somat�rio dos valores da Contribui��o Previdenci�ria com exigibilidade suspensa n�o pode ser superior ao valor da base de C�lculo da Contribui��o Previdenci�ria sobre a Receita Bruta multiplicado pela al�quota definida na tabela 09 para o c�digo da atividade informada no c�digo da atividade econ�mica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1206', 'O valor informado n�o pode ser superior ao Valor da contribui��o previdenci�ria.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1207', 'S� pode existir acordo se a classifica��o tribut�ria do contribuinte for igual a 60 - Miss�o Diplom�tica ou Reparti��o Consular de carreira estrangeira.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1208', 'O valor total n�o retido {vlrTotalNRet} n�o pode ser maior que valor total retido {vlrTotalRet}. 2.2.1. Com tabelas MS2001 - O valor informado deve existir na Tabela de Classifica��o Tribut�ria.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2002', 'O valor informado deve existir na Tabela de Classifica��o de Servi�os Prestados Mediante Cess�o de M�o de Obra/Empreitada Sujeitos a Reten��o de Contribui��o Previdenci�ria');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2003', 'O valor informado deve existir na Tabela de UF.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2004', 'O valor informado deve existir na Tabela de Munic�pios do IBGE.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2005', 'O valor informado deve existir na Tabela de C�digo de Atividades, Produtos e Servi�os');
commit;

