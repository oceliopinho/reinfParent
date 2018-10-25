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
 
 
 
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (1, 'Empresa enquadrada no regime de tributação Simples Nacional com tributação previdenciária substituída');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (2 ,'Empresa enquadrada no regime de tributação Simples Nacional com tributação previdenciária não substituída');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (3, 'Empresa enquadrada no regime de tributação Simples Nacional com tributação previdenciária substituída e não substituída');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (4, 'MEI - Micro Empreendedor Individual');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (6, 'Agroindústria');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (7, 'Produtor Rural Pessoa Jurídica');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (8, 'Consórcio Simplificado de Produtores Rurais');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (9, 'Órgão Gestor de Mão de Obra');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (10, 'Entidade Sindical a que se refere a Lei 12.023/2009');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (11, 'Associação Desportiva que mantém Clube de Futebol Profissional');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (13, 'Banco, caixa econômica, sociedade de crédito, financiamento e investimento e demais empresas relacionadas no parágrafo 1º do art. 22 da Lei 8.212./91');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (14, 'Sindicatos em geral, exceto aquele classificado no código [10]');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (21, 'Pessoa Física, exceto Segurado Especial');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (22, 'Segurado Especial');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (60, 'Missão Diplomática ou Repartição Consular de carreira estrangeira');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (70, 'Empresa de que trata o Decreto 5.436/2005');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (80, 'Entidade Imune ou Isenta');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (85, 'Ente Federativo, Órgãos da União, Autarquias e Fundações Públicas');
INSERT INTO reinf.tb_classificacao_tributaria(id_classificacao_tributaria, desc_classificacao) VALUES (99, 'Pessoas Jurídicas em Geral');
commit;

insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0000', 'Sem Erro');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0001', 'Ocorreu uma falha ao realizar a verificação da versão das tabelas do sistema no Ambiente Nacional');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0002', 'Ocorreu uma falha ao atualizar as tabelas do sistema');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0003', 'Erro na cadeia do certificado digital do signatário ou do solicitante da informação');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0004', 'A raiz do certificado digital do signatário ou do solicitante da informação deverá pertencer a Autoridade Certificadora Raiz Brasileira (ICP-Brasil).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0005', 'O certificado digital do signatário ou do solicitante da informação encontra-se revogado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0006', 'O certificado digital do signatário ou do solicitante da informação encontra-se expirado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0007', 'O certificado digital do signatário ou do solicitante da informação não é válido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0008', 'Parâmetro {0} obrigatório não informado');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0009', 'Parâmetro {0} inválido');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0015', 'Deve ser utilizado certificado digital do tipo e-CNPJ ou e-PJ cujo CNPJ base seja o mesmo do contribuinte responsável pela informação, ou do tipo e-CPF ou e-PF cujo CPF pertença ao representante legal do contribuinte ou qualquer certificado que pertença a um procurador devidamente habilitado no sistema de Procuração Eletrônica da RFB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0016', 'O evento deve ser assinado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0017', 'Assinatura do evento inválida.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0018', 'Falha na disponibilização do resultado do processamento na fila de saída.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0019', 'Ocorreu uma falha no acesso ao Sistema CNPJ. Aguarde alguns minutos e tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0020', 'Ocorreu uma falha no acesso ao Sistema de Procuração Eletrônica. Aguarde alguns minutos e tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0021', 'Falha ao assinar o recibo de entrega do Evento. Envie o evento novamente, para obter o recibo de entrega.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0022', 'O evento já se encontra na base de dados do sistema.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0023', 'Já existe no sistema um evento com mesma chave: <<exibir os campos que compõem a chave>>.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0025', 'Falha no processamento da solicitação. Tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0026', 'Falha ao inserir evento no sistema. Tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0029', '"Erro ao gerar DCTF: <número do erro retornado pela DCTF> - <descrição do erro retornado pela DCTF>."');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0030', '"A estrutura do arquivo XML esta em desconformidade com o esquema XSD." + <erro retornado pelo XSD>');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0031', 'Ocorreu uma falha durante o envio dos débitos apurados para a DCTF. Tente novamente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0032', 'A alteração do conteúdo de tabela ocorrida após a prestação de informações da EFD causaram inconsistência nas informações prestadas.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0033', 'O número de recibo informado deve ser relativo a um evento de mesmo tipo indicado em na tag tpEvento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0034', 'O número de recibo informado deve ser relativo a um evento ativo e do mesmo contribuinte.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0035', 'Este evento não pode ser excluído.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0036', 'A versão do leiaute do evento, definida no namespace do evento, deverá ser aceita pelo sistema (por padrão a última vigente para o tipo de evento)');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0037', 'Já existe na base de dados do Ambiente Nacional um evento com mesma identificação.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0038', 'Para envio de eventos para a produção, o ambiente deve ser igual a 1. Para envio de eventos para a pré-produção, deve ser igual a 2 ou 3.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0052', 'Foi informado um valor com tamanho inválido para o campo {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0053', 'Quantidade de casas decimais inválida no campo: {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0054', 'O grupo ''{0}'' é de preenchimento obrigatório.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0092', 'Versão do lote inválida. Deve ser utilizada a versão {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0039', 'Número de recibo inválido');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0051', 'Foi informado um valor com tipo inválido para o campo {0}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0055', 'O campo {0} é obrigatório e não foi informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0056', 'O Grupo ''{0}'' não deve ser preenchido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0040', 'Erro atualização controle escrituração');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0041', 'Erro atualização situação evento de fechamento');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS0042', 'Somente podem utilizar a EFD-REINF os contribuintes com obrigatoriedade de entrega ou aqueles que fizeram opção por antecipar sua utilização.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1000', 'CNPJ não encontrado no cadastro do Sistema CNPJ.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1001', 'Data inválida. A data informada deve ser uma data válida posterior ou igual à data inicial de implantação do Reinf.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1002', 'Data inválida. A data informada deve ser posterior ou igual à data inicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1003', 'Telefone inválido. Devem ser informados apenas números, com mínimo de 10 dígitos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1004', 'E-mail inválido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1005', 'Já existe outro registro com período conflitante com o período informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1006', 'O mês/ano de início da validade das informações deve ser igual ou posterior à data de início das atividades da empresa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1007', 'O CNPJ deverá ser informado com 8 dígitos, ou seja, apenas a Raiz/Base, com exceção no caso de órgãos públicos da administração direta federal, com natureza jurídica igual a [101-5], [104-0], [107-4], [116-3], quando o CNPJ deverá ser informado com 14 dígitos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1008', 'Registro não pode ser excluído (registro não existe ou já foi referenciado em outros eventos).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1009', 'Não existem Informações do Contribuinte vigente na data do evento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1010', 'ID inválido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1011', 'CNPJ inválido: pertence a pessoa jurídica Inapta pelo motivo de Inexistência de Fato.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1012', 'CNPJ inválido: está baixado no período de apuração.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1013', 'CNPJ inválido: está anulado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1014', 'CPF inválido: CNPJ cancelado no período de apuração.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1015', 'Classificação tributária inválida. Os códigos 21 e 22 só podem ser utilizados por pessoa física. Os demais códigos devem ser utilizados por pessoa jurídica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1016', 'IndObra incompatível com tipo de inscrição do tomador de serviço (tpInscTomador). Se tpInscTomador = 1, indObra deve ser 0. Se tpInscTomador = 4, indObra deve ser 1 ou 2.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1017', 'CPF não consta na base da RFB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1018', 'O preenchimento do campo telefone fixo é obrigatório se o campo telefone celular não for preenchido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1019', 'A situação é obrigatória para pessoa jurídica e não deve ser informada para pessoa física.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1020', 'CNPJ inválido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1021', 'CPF inválido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1022', 'O número do recibo deve ser referente a um evento ativo, com mesmo tipo e período de apuração. Só é possível enviar retificações se o período não estiver fechado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1023', 'Já foi enviado outro evento com a mesma nota fiscal: CNPJ {cnpjPrestador}, Série {serie} e Número {numDocto}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1024', 'O número do recibo deve ser informado para eventos retificadores.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1025', 'O CNPJ informado deve pertencer ao contribuinte declarante.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1026', 'A inscrição (CNPJ) informada deve ser compatível com o tipo de inscrição informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1027', 'Para pessoa física deve ser informado o valor 1-Obra de Construção Civil - Empreitada Total ou 2-Obra de Construção Civil - Empreitada Parcial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1028', 'Não é permitido o envio de mais de um evento para o contribuinte, num mesmo período de apuração, para o mesmo estabelecimento tomador e mesmo prestador, exceto se for para retificação de um evento enviado anteriormente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1029', 'O CNPJ informado não deve pertencer ao contribuinte declarante.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1030', 'O valor informado deve corresponder a soma do valor bruto da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1031', 'O valor informado deve corresponder a soma da base de cálculo da retenção da contribuição previdenciária da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1032', 'O valor informado deve corresponder a soma do valor da retenção das notas fiscais de serviço emitidas para o contratante menos a soma do valor da retenção destacada na(s) nota fiscal(ias), relativo aos serviços subcontratados, se houver. MS1033 - O valor informado deve corresponder a soma do valor do adicional de retenção da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1034', 'O valor informado deve corresponder a soma do valor da retenção principal que deixou de ser efetuada pelo contratante ou que foi depositada em juízo em decorrência de decisão judicial/administrativa da(s) nota(s) fiscal(is) correspondente(s).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1035', 'O valor informado não pode ser maior que a soma do valor da retenção principal das notas fiscais de serviço.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1036', 'IndObra incompatível com tipo de inscrição do estabelecimento (tpInscEstab). Se tpInscEstab = 1, indObra deve ser 0. Se tpInscEstab = 4, indObra deve ser 1 ou 2. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1037', 'Não foi enviado evento R-1070 para o número do processo judicial, com validade vigente no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1038', 'O valor informado deve corresponder a soma do valor da retenção adicional que deixou de ser efetuada pelo contratante ou que foi depositada em juízo em decorrência de decisão judicial/administrativa da(s) nota(s) fiscal(is) correspondente(s). Deve corresponder a soma do campo {vlrNRetAdic } dos registros vinculados. Não pode ser maior que {vlrTotalRetAdic}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1039', 'O valor informado não pode ser maior que a soma do valor do adicional de retenção das notas fiscais.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1041', 'O mês/ano da data informada deve ser igual ao mês/ano do período de apuração.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1042', 'O valor informado deve ser maior que zero.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1043', 'Em caso de alteração de período de validade das informações, não deve existir outro registro na tabela com o mesmo código de identificação (chave) em período de vigência conflitante com o novo período de validade informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1044', 'O preenchimento do CNAE (campo codAtivEcon) é obrigatório para Contribuinte da Contribuição Previdenciária sobre a Receita Bruta (CPRB) - Retenção 3,5%');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1045', 'O valor informado deve existir na Tabela de Código de Atividades, Produtos e Serviços Sujeitos à Contribuição Sobre a Receita Bruta, no grupo : I - Pessoas Jurídicas Prestadoras de Serviços.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1046', 'O valor informado deve corresponder ao valor bruto das notas fiscais menos os valores de materiais e equipamentos, valores do custo da alimentação e valores do custo do fornecimento de transporte.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1047', 'O valor informado deve ser igual a 11% da base de cálculo da retenção da contribuição previdenciária.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1048', 'O valor informado deve ser igual a 3,5% da base de cálculo da retenção da contribuição previdenciária.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1049', 'O valor informado deve corresponder ao valor da retenção relativa aos serviços contidos na nota fiscal/fatura menos o valor da retenção destacada na(s) nota fiscal(ais), relativo aos serviços subcontratados menos o valor da retenção principal que deixou de ser efetuada pelo contratante ou que foi depositada em juízo em decorrência de decisão judicial/administrativa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1050', 'O valor informado não pode ser superior ao valor da retenção relativa aos serviços contidos na nota fiscal/fatura.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1051', 'O valor informado não pode ser superior ao valor da retenção (11% da base de cálculo ou 3,5% da base de cálculo (para Contribuinte da Contribuição Previdenciária sobre a Receita Bruta), deduzido o valor da retenção destacada na nota fiscal relativo aos serviços subcontratados).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1052', 'O somatório dos valores dos serviços prestados por segurados em condições especiais, cuja atividade permita concessão de aposentadoria especial após 15, 20 e 25 anos de contribuição não pode ser superior à base de cálculo da retenção da contribuição previdenciária. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1053', 'O valor informado neste campo deve corresponder ao somatório de 4% sobre os valores dos serviços prestados por segurados em condições especiais, cuja atividade permita concessão de aposentadoria especial após 15 anos de contribuição mais 3% sobre os valores dos serviços prestados por segurados em condições especiais, cuja atividade permita concessão de aposentadoria especial após 20 anos de contribuição mais 2% sobre os valores dos serviços prestados por segurados em condições especiais, cuja atividade permita concessão de aposentadoria especial após 25 anos de contribuição.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1054', 'O valor informado não pode ser superior ao valor adicional apurado de retenção da nota fiscal, caso os serviços tenham sido prestados sob condições especiais que ensejem aposentadoria especial aos trabalhadores após 15, 20, ou 25 anos de contribuição.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1055', 'Já foi enviado outro evento com a mesma nota fiscal: CNPJ do estabelecimento prestador {nrInscricao}, Série {serie} e Número {numDocto}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1056', 'O valor informado neste campo deve corresponder ao valor adicional apurado de retenção da nota fiscal, caso os serviços tenham sido prestados sob condições especiais que ensejem aposentadoria especial aos trabalhadores após 15, 20, ou 25 anos de contribuição menos o valor da retenção adicional que deixou de ser efetuada pelo contratante ou que foi depositada em juízo em decorrência de decisão judicial/administrativa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1057', 'O preenchimento do código da conta analítica contábil é obrigatório para empresa obrigada a entregar a ECD.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1058', 'O valor informado deve ser menor ou igual a 11% da base de cálculo da retenção da contribuição previdenciária. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1059', 'O valor informado deve ser menor ou igual a 3,5% da base de cálculo da retenção da contribuição previdenciária.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1060', 'Não existe evento de fechamento para o período informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1061', 'Não foram enviados eventos R-2010 - Retenção Contribuição Previdenciária ¿ Serviços Tomados no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1062', 'Foram enviados eventos R-2010 - Retenção Contribuição Previdenciária ¿ Serviços Tomados no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1063', 'Não foram enviados eventos R-2020 - Retenção Contribuição Previdenciária ¿ Serviços Prestados no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1064', 'Foram enviados eventos R-2020 - Retenção Contribuição Previdenciária ¿ Serviços Prestados no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1065', 'Não foram enviados eventos R-2030 - Recurso Recebido p/ Assoc. Desp. que Mantenha Equipe de Futebol Profissional no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1066', 'Foram enviados eventos R-2030 - Recurso Recebido p/ Assoc. Desp. que Mantenha Equipe de Futebol Profissional no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1067', 'Não foram enviados eventos R-2040 - Recurso Repassado p/ Assoc. Desp. que Mantenha Equipe de Futebol no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1068', 'Foram enviados eventos R-2040 - Recurso Repassado p/ Assoc. Desp. que Mantenha Equipe de Futebol no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1069', 'Não foram enviados eventos R-2050 - Comercialização da Produção Por Produtor Rural PJ/Agroindústria no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1070', 'Foram enviados eventos R-2050 - Comercialização da Produção Por Produtor Rural PJ/Agroindústria no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1071', 'Não foram enviados eventos R-2060 - Informações ¿ CPRBno período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1072', 'Foram enviados eventos R-2060 - Informações ¿ CPRB no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1073', 'Não foram enviados eventos R-2070 ¿ Retenções na Fonte (IR,CSLL,Cofins,PIS/PASEB) ¿ Pagamento diversos no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1074', 'Foram enviados eventos R-2070 ¿ Retenções na Fonte (IR,CSLL,Cofins,PIS/PASEB) ¿ Pagamento diversos no período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1075', 'O preenchimento é obrigatório se no período de apuração o contribuinte não contratou serviços sujeitos à retenção de contribuição previdenciária, não prestou serviços sujeitos à retenção de contribuição previdenciária, não possui informações sobre recursos recebidos (associação desportiva que mantém equipe de futebol profissional), não possui informações sobre repasses efetuados à associação desportiva que mantém equipe de futebol profissional, não possui informações de comercialização de produção (produtor rural PJ/agroindústria), não possui informações sobre a apuração da Contribuição Previdenciária sobre a Receita Bruta e não possui informações de pagamento diversos no período de apuração.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1076', 'Já existe outra tag <<nome da tag>> com mesma chave no evento. Não pode ser informada mais de uma tag com a mesma chave.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1077', 'O preenchimento é obrigatório para pessoa jurídica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1078', 'A EFD já foi fechada para o período informado, ou existe um evento de fechamento em processamento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1079', 'Em caso de alteração, deve existir registro, na tabela de contribuinte, com o mesmo período de validade informado no evento nas tags iniValid e fimValid do grupo alteração.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1080', 'O Valor da contribuição para o Senar com exigibilidade suspensa é obrigatório se não tiverem sido informados o Valor da Contribuição Previdenciária com exigibilidade suspensa e o Valor da contribuição para Gilrat com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1081', 'O Valor da Contribuição Previdenciária com exigibilidade suspensa é obrigatório se não tiverem sido informados o Valor da contribuição para Gilrat com exigibilidade suspensa e o Valor da contribuição para o Senar com exigibilidade suspensa. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1082', 'O Valor da contribuição para Gilrat com exigibilidade suspensa é obrigatório se não tiverem sido informados o Valor da Contribuição Previdenciária com exigibilidade suspensa e o Valor da contribuição para o Senar com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1083', 'O valor informado deve ser igual a soma dos valores informados no campo Valor total da comercialização');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1084', 'O valor informado deve ser igual a soma dos valores informados no campo Valor Bruto da nota fiscal/fatura.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1085', 'O valor informado não pode ser maior que o valor informado no campo Valor da Receita Bruta Total do Estabelecimento no Período.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1086', 'O evento R-2050-Comercialização da Produção Por Produtor Rural PJ/Agroindústriasó pode ser enviado por contribuinte cuja classificação tributária seja igual a 6 - Agroindústria. ou 7 - Produtor Rural Pessoa Jurídica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1087', 'O indicativo de suspensão da exigibilidade deve ser igual a 3, 90 ou 92 quando o tipo do processo informado for igual a 1.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1088', 'O indicativo de suspensão da exigibilidade deve ser igual a 01, 02, 04, 05, 08, 09, 10, 11, 12, 13, 90 ou 92 quando o tipo do processo informado for igual a 2.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1089', 'Não é permitido o envio de mais de um evento para o contribuinte, num mesmo período de apuração para o mesmo estabelecimento e tomador, exceto se for para retificação de um evento enviado anteriormente.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1090', 'Conteúdo inválido. Preenchimento obrigatório se tipo do processo igual a 2 (Judicial). Caso contrário, não deve ser informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1091', 'Não é permitido o envio de mais de um evento no mesmo período de apuração pelo mesmo estabelecimento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1092', 'O evento que está sendo excluído pertence a um período de apuração para o qual já exista encerramento das informações, o evento de exclusão somente será aceito após o evento de "reabertura" das informações (R-2098).');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1093', 'O campo {classTrib} do Contribuinte (R-1000) deverá ser igual a 11 - Associação Desportiva que mantém Clube de Futebol Profissional.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1094', 'O valor informado deve ser igual a soma dos valores brutos dos repasses efetuados, informados no Detalhamento dos recursos recebidos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1095', 'O valor deve corresponder a soma dos valores da apuração da retenção que deveria sofrer a associação desportiva, informados no Detalhamento dos recursos recebidos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1096', 'O valor informado deve corresponder a soma dos valores da retenção que deixou de ser feita pela empresa que efetuou o repasse ou que foi depositada em juízo em decorrência de decisão judicial/administrativa, informados no Detalhamento dos recursos recebidos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1097', 'O valor informado deve ser igual a 5% do Valor Bruto do repasse efetuado a título de patrocínio, publicidade, licenciamento, etc.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1098', 'O valor informado não pode ser maior que o Valor da apuração da retenção que deveria sofrer a associação desportiva.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1099', 'O CNPJ informado deve ser um CNPJ diferente do CNPJ da empresa declarante.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1100', 'O campo é obrigatório se o valor da retenção que deixou de ser feita pela entidade que efetuou o repasse ou que foi depositada em juízo em decorrência da decisão judicial/administrativa for maior que zero.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1101', 'O valor informado não pode ser maior que 5% do valor bruto dos recursos repassados.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1102', 'A data informada não pode ser maior que o ano/mês corrente. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1103', 'O período de referência das informações deverá estar no no formato AAAA-MM, exceto se {tpEvento} = [R-3010], situação em que deve ser informado no formato AAAA-MM-DD.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1105', 'Indicativo de Suspensão não cadastrado para esse processo.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1106', 'O código da atividade econômica informado deve ser um código válido da tabela 9.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1107', 'O campo não deve ser preenchido se o prestador não for enquadrado na desoneração da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1108', 'As informações de processo principal são obrigatórias quando a (Soma do valor da retenção das notas fiscais de serviço emitidas para o contratante) for maior que 0 (zero), e não devem ser informadas em caso contrário.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1109', 'As informações de processo adicional são obrigatórias quando a soma do valor do adicional de retenção das notas fiscais for maior que 0 (zero), e não devem ser informadas em caso contrário.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1110', 'O preenchimento do código de suspensão é obrigatório quando houver a informação de mais de um Indicativo de Suspensão para o processo.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1111', 'O preenchimento do campo {codAtivEcon} é obrigatório se o prestador for enquadrado na desoneração da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1112', 'O campo não deve ser preenchido para o contribuinte não enquadrado na desoneração da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1113', 'O preenchimento do campo {codAtivEcon} é obrigatório para contribuinte enquadrado na desoneração da folha pela CPRB.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1114', 'O campo não deve ser validado na tabela de processo para processo judicial do prestador - tipo processo = 3.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1115', 'O número de inscrição informado deve ser um CNO de propriedade do estabelecimento prestador no Sistema CNO.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1116', 'O número de inscrição informado deve ser um CNO de propriedade do contribuinte declarante no Sistema CNO.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1117', 'O número de inscrição informado deve ser um CNO não pertencente ao contribuinte declarante no Sistema CNO.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1118', 'Se {indSusp} = [90], deve ser preenchido obrigatoriamente com [N].');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1119', 'Para recepção do evento de Contribuição Previdenciária sobre a Receita Bruta, a {classTrib} do R-1000 deve ser igual a 99 (Pessoas Jurídicas em Geral) e {indDesoneracao} deve ser igual a 1.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1120', 'O valor informado deve corresponder a 2,5% do valor total da comercialização cujo indicativo de comercialização é igual a 1 - Comercialização da Produção por Prod. Rural PJ/Agroindústria, exceto para entidades executoras do PAA. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1121', 'O valor informado deve corresponder a 0,1% do valor total da comercialização cujo indicativo de comercialização é igual a 1 - Comercialização da Produção por Prod. Rural PJ/Agroindústria, exceto para entidades executoras do PAA.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1122', 'O valor informado deve corresponder a 0,25% do valor total da comercialização cujo indicativo de comercialização é igual a 1 - Comercialização da Produção por Prod. Rural PJ/Agroindústria, exceto para entidades executoras do PAA ou 8 - Comercialização da Produção para Entidade do Programa de Aquisição de Alimentos - PAA ou 9 - Comercialização direta da Produção no Mercado Externo.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1123', 'O valor informado deve corresponder ao valor total da receita da atividade {vlrRecBrutaAtiv} + o Valor total das Adições da Receita Bruta {vlrAdicRecBruta} - o Valor total das Exclusões da Receita Bruta {vlrExcRecBruta}');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1124', 'O valor informado deve ser igual a soma dos valores informados no campo Valor Contribuição Previdenciária sobre a Receita Bruta do grupo atividade econômica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1125', 'O campo tem preenchimento obrigatório porque o Valor da Contribuição Previdenciária com exigibilidade suspensa foi informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1126', 'O valor informado deve ser igual ao somatório do campo valor bruto {vlrBruto} do grupo nota fiscal de serviços {nfs}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1127', 'O valor informado deve ser igual a soma dos valores informados no campo {vlrAjuste} cujo {tpAjuste} seja igual a "0" - Ajuste de redução. Caso não haja ajuste de redução, preencher com 0 (zero). ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1128', 'O valor informado não pode ser maior que o valor total da receita bruta da atividade econômica {vlrRecBrutaAtiv}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1129', 'O valor informado deve ser igual a soma dos valores informados no campo {vlrAjuste} cujo {tpAjuste} seja igual a "1" - Ajuste de adição. Caso não haja ajuste de adição, preencher com 0 (zero). ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1130', 'O valor informado deve ser igual ao Valor da Base de Cálculo da Contribuição Previdenciária sobre a Receita Bruta {vlrBcCPRB} multiplicado pela alíquota do código da atividade econômica {codAtivEcon} da tabela 9.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1131', 'Mês / ano inválido. O Mês / ano informado deve ser válido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1132', 'Não pode haver alteração ou exclusão de número de processo {nrProc} + código suspensão {codSusp} que esteja sendo utilizado em outro evento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1133', 'O campo {indSitPJ} deverá ser obrigatoriamente informado apenas se {tpInscr} = 1 (CNPJ), caso contrário não deverá ser informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1135', 'A soma dos valores da retenção das Informações de processos relacionados a não retenção de contribuição previdenciária , com exceção dos valores informados para processos sem suspensão de exigibilidade, deve ser igual ao valor da retenção de contribuição previdenciária principal que deixou de ser efetuada em função de processo administrativo ou judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1136', 'A soma dos valores das retenções das Informações de processos relacionados a não retenção de contribuição previdenciária adicional , com exceção dos valores informados para processos sem suspensão de exigibilidade, deve ser igual ao Valor da retenção de contribuição previdenciária adicional que deixou de ser efetuada em função de processo administrativo ou judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1137', 'O declarante do evento correspondente ao {nrRecibo) informado deverá ser o mesmo informado no campo {nrInsc}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1138', 'O evento não pode ser recebido porque existe fechamento para o período de apuração informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1181', 'A quantidade de ingressos vendidos {qtdeIngrVendidos} não pode ser superior a quantidade de ingressos colocados à venda {qtdeIngrVenda}');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1182', 'Não é permitido o envio de mais de um evento para o contribuinte num mesmo período de apuração');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1183', 'O valor da retenção {vlrRetencao} informado não pode ser maior que 11% da Base de cálculo da retenção da contribuição previdenciária {vlrBaseRet}.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1184', 'O CNPJ do Ente Federativo Responsável é obrigatório se o Órgão Público {ideContri} não é o Ente Federativo Responsável e não pode ser informado caso contrário. O CNPJ informado deverá ser validado com a informação referente ao Ente Federativo Responsável (EFR) existente no cadastro do CNPJ da RFB. ');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1185', 'Obrigatório se a Natureza Jurídica {natJurid} = [102-3, 103-1, 105-8, 106-6, 108-2, 117-1, 118-0, 123-6, 124-4]). Não deverá ser informado nos demais casos.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1186', 'Foram encontrados caracteres especiais restritos no conteúdo das tags do evento');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1187', 'A soma dos valores da retenção das Informações de processos relacionados a não retenção de contribuição previdenciária, com exceção dos valores informados para processos sem suspensão de exigibilidade, deve ser igual ao Valor da retenção que deixou de ser feita pela entidade que efetuou o repasse ou que foi depositada em juízo em decorrência da decisão judicial/administrativa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1188', 'O grupo infoProc é de preenchimento obrigatório se o valor total não retido {vlrTotalNRet} for maior que zero.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1189', 'O Valor Total da Contribuição Previdenciária com exigibilidade suspensa deve ser igual ao somatório dos valores da Contribuição Previdenciária com exigibilidade suspensa das Informações de processos relacionados a Suspensão da Contribuição previdenciária, com exceção dos valores informados para processos sem suspensão de exigibilidade.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1190', 'Preenchimento obrigatório se {vlrCPSuspTotal} > 0.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1191', 'A soma dos valores da Contribuição Previdenciária com exigibilidade suspensa das Informações de processos administrativos/judiciais com decisão/sentença favorável ao contribuinte, com exceção dos valores informados para processos sem suspensão de exigibilidade, deve ser igual ao Valor da Contribuição Previdenciária com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1192', 'A soma dos valores da Contribuição Gilrat com exigibilidade suspensa das Informações de processos administrativos/judiciais com decisão/sentença favorável ao contribuinte, com exceção dos valores informados para processos sem suspensão de exigibilidade, deve ser igual ao Valor da Contribuição Gilrat com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1193', 'A soma dos valores da Contribuição para o Senar com exigibilidade suspensa das Informações de processos administrativos/judiciais com decisão/sentença favorável ao contribuinte, com exceção dos valores informados para processos sem suspensão de exigibilidade, deve ser igual ao Valor da Contribuição para o Senar com exigibilidade suspensa.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1194', 'Em caso de alteração, deve existir registro, na tabela de processos, com o mesmo tipo, número de processo e período de validade informados no evento nas tags iniValid e fimValid do grupo alteração.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1195', 'A soma dos valores da Contribuição Previdenciária com exigibilidade suspnsa das Informações de processos relacionados a Suspensão da CPRB, com exceção dos valores informados para processos sem suspensão de exigibilidade, deve ser igual ao Valor da Contribuição Previdenciária com exigibilidade suspensa informada para o estabelecimento.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1196', 'Número de processo inválido.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1197', 'As Informações Complementares do Processo Judicial são obrigatórias para Processo Judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1198', 'As Informações Complementares do Processo Judicial só devem ser informadas para Processo Judicial.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1199', 'Preenchimento obrigatório se {vlrCPSuspTotal} ou {vlrRATSuspTotal} ou {vlrSenarSuspTotal} forem > 0');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1200', 'O grupo infoProc (Informações de Processos) não deve ser informado.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1201', 'Os processos originários do Conselho Nacional de Justiça, Justiça do Trabalho, Justiça Eleitoral, Justiça Militar da União e Justiça Militar Estadual não podem ser utilizados para a suspensão de débitos na RFB');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1202', 'O somatório dos valores da Contribuição Previdenciária com exigibilidade suspensa, com exceção dos valores informados para processos sem suspensão de exigibilidade, não deve ser superior a 2,5% do valor total da comercialização.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1203', 'O somatório dos valores da Contribuição Gilrat com exigibilidade suspensa, com exceção dos valores informados para processos sem suspensão de exigibilidade, não deve ser superior a 0,1% do valor total da comercialização.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1204', 'O somatório dos valores da Contribuição para o Senar com exigibilidade suspensa, com exceção dos valores informados para processos sem suspensão de exigibilidade, não deve ser superior a 0,25% do valor total da comercialização.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1205', 'O somatório dos valores da Contribuição Previdenciária com exigibilidade suspensa não pode ser superior ao valor da base de Cálculo da Contribuição Previdenciária sobre a Receita Bruta multiplicado pela alíquota definida na tabela 09 para o código da atividade informada no código da atividade econômica.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1206', 'O valor informado não pode ser superior ao Valor da contribuição previdenciária.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1207', 'Só pode existir acordo se a classificação tributária do contribuinte for igual a 60 - Missão Diplomática ou Repartição Consular de carreira estrangeira.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS1208', 'O valor total não retido {vlrTotalNRet} não pode ser maior que valor total retido {vlrTotalRet}. 2.2.1. Com tabelas MS2001 - O valor informado deve existir na Tabela de Classificação Tributária.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2002', 'O valor informado deve existir na Tabela de Classificação de Serviços Prestados Mediante Cessão de Mão de Obra/Empreitada Sujeitos a Retenção de Contribuição Previdenciária');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2003', 'O valor informado deve existir na Tabela de UF.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2004', 'O valor informado deve existir na Tabela de Municípios do IBGE.');
insert into reinf.tb_reinf_mensagens (id_reinf_mensagens, codigo_mensagem, descricao_mensagem) values (nextval('reinf.se_reinf_mensagens'), 'MS2005', 'O valor informado deve existir na Tabela de Código de Atividades, Produtos e Serviços');
commit;

