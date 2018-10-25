package br.com.pinhoinformatica.reinf.model.repository;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import br.com.pinhoinformatica.bridge.model.utils.UtilsBridge;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.envioLoteEventos.Reinf;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.IdeContri;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.IdeEvento;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.NovaValidade;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Exclusao;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Inclusao;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Inclusao.IdePeriodo;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Inclusao.InfoCadastro;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Inclusao.InfoCadastro.Contato;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Inclusao.InfoCadastro.SoftHouse;
import br.com.pinhoinformatica.pinhoNfeCert.model.envia.RecepcaoLoteReinf;
import br.com.pinhoinformatica.pinhoNfeCert.model.run.Assinar;
import br.com.pinhoinformatica.pinhoNfeCert.model.run.Validar;
import br.com.pinhoinformatica.pinhoNfeCert.model.utils.ConstantesUtil;
import br.com.pinhoinformatica.reinf.facade.IAppFacade;
import br.com.pinhoinformatica.reinf.model.run.StartConfiguracoes;
import br.com.pinhoinformatica.reinf.persistence.jpa.reinflotes.ReinfLotesDAO;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.FecharConexao;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.SqlConnectionJdbc;

public class GerarLoteReinfRepository extends FecharConexao {

	private static final Long idServicosTomados = null;
	@Inject private ReinfLotesDAO reinfLotesDAO;
	@Inject private UtilsBridge utilsBridge;
	private Logger log = Logger.getLogger(this.getClass().getCanonicalName());

	private static String numLote = null;
	private String IdEvento = null;
	private Integer seqChave = 0;
	private Reinf reinf;
	private Reinf.LoteEventos loteEventos;
	private String strPerApur;
	private short tipoInscricaoShort;

	public String gerarDadosEnvio(IAppFacade facade, String nomeServidor, String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String ret = "Gerado com Sucesso...";
		try {
			conn = SqlConnectionJdbc.getConnection();
			conn.setAutoCommit(false);
			String sql1 = " Select distinct ";
			sql1       += "        rler.id_reinf_lotes_efd_receita, ";
			sql1       += "        rler.cnpj_empresa_raiz_reinf, ";
			sql1       += "        rler.periodo_apuracao_reinf, ";
			sql1       += "        ic.per_apur, ";
			sql1       += "        ic.tipo_ambiente, ";
			sql1       += "        e.emp_diretorio, ";
			sql1       += "        e.tipo_certificado, ";
			sql1       += "        e.modelo_certificado, ";
			sql1       += "        e.nome_certificado, ";
			sql1       += "        e.senha_certificado, ";                  
			sql1       += "        e.ativa_log, ";  
			sql1       += "        ls.nome_schema, ";
			sql1       += "        ls.nome_xsd, ";
			sql1       += "        ls.versao_lote, ";
			sql1       += "        md.desc_modelo, ";
			sql1       += "        cs.uf_cacert, ";
			sql1       += "        sws.sigla_estado, ";
			sql1       += "        sws.versao_nfe, ";
			sql1       += "        sws.servico, ";
			sql1       += "        sws.tempo_timeout, ";
			sql1       += "        sws.url ";
			sql1       += " From reinf.tb_reinf_lotes_efd_receita rler, ";
			sql1       += "      reinf.tb_informacoes_contribuinte ic, ";
			sql1       += "      bridge.tb_empresa_raiz_cnpj erc, ";
			sql1       += "      bridge.tb_empresa e, ";
			sql1       += "      bridge.tb_sefaz_web_services sws, ";
			sql1       += "      bridge.tb_lote_sefaz ls, ";
			sql1       += "      bridge.tb_modelo_doc md, ";
			sql1       += "      bridge.tb_cacerts_sefaz cs ";
			sql1       += " Where rler.evento = '1000' ";
			sql1       += " and   rler.status_lote_reinf in('EWS') ";
			sql1       += " and   ic.id_informacoes_contribuinte = attribute1 ";
			sql1       += " and   ic.id_empresa_raiz_cnpj = rler.cnpj_empresa_raiz_reinf ";
			sql1       += " and   ic.periodo_apuracao_reinf = rler.periodo_apuracao_reinf ";
			sql1       += " and   erc.id_empresa_raiz_cnpj = ic.id_empresa_raiz_cnpj ";
			sql1       += " and   e.id_empresa_raiz_cnpj = ic.id_empresa_raiz_cnpj ";
			sql1       += " and   substr(e.emp_cnpj_cpf,9,4) = '0001' ";
			sql1       += " and   substr(e.emp_cnpj_cpf,1,8) = erc.cnpj_raiz ";
			sql1       += " and   sws.servico = 'RecepcaoLoteReinf' ";
			sql1       += " and   sws.estados = e.estados ";
			sql1       += " and   sws.tipo_ambiente = ic.tipo_ambiente "; 
			sql1       += " and   ls.id_lote_sefaz = lote_sefaz ";
			sql1       += " and   md.id_modelo = sws.modelo_doc ";
			sql1       += " and   cs.tipo_ambiente = sws.tipo_ambiente ";
			sql1       += " and   cs.uf_cacert = sws.uf_cacert ";
			sql1       += " and   cs.modelo_doc = sws.modelo_doc ";

			pstmt = conn.prepareStatement(sql1);
			System.out.println(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {			
				Long    idCnpjRaiz        = rs.getLong("cnpj_empresa_raiz_reinf");
				Long    idPeriodoApuracao = rs.getLong("periodo_apuracao_reinf");				
				Integer tipoAmbiente      = rs.getInt("tipo_ambiente");
				String  dirEmp            = rs.getString("emp_diretorio");
				String  nomeSchema        = rs.getString("nome_schema");
				String  modeloCertificado = rs.getString("modelo_certificado");
				String  tipoCertificado   = rs.getString("tipo_certificado");
				String  nomeCertificado   = rs.getString("nome_certificado");
				String  senhaCertificado  = rs.getString("senha_certificado");
				String  modDocumento      = rs.getString("desc_modelo");
				String  ufCacerts         = rs.getString("uf_cacert");
				String  siglaUf           = rs.getString("sigla_estado");
				String  verProc           = rs.getString("versao_lote");
				String  versaoXml         = rs.getString("versao_lote");
				String  versaoNfe         = rs.getString("versao_nfe");
				Integer tempoTimeOut      = rs.getInt("tempo_timeout");
				String  url               = rs.getString("url");
				URL     webService        = new URL(url);	
				String  servicoSefaz      = rs.getString("servico");
				strPerApur                = rs.getString("per_apur");
				boolean ativaLog          = rs.getBoolean("ativa_log");


				String  reg1000           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "1000");
				String  reg1070           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "1070");
				String  reg2010           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2010");
				String  reg2020           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2020");
				String  reg2030           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2030");
				String  reg2040           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2040");
				String  reg2050           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2050");
				String  reg2060           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2060");
				String  reg2070           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2070");
				String  reg2098           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2098");
				String  reg2099           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "2099");
				String  reg3010           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "3010");
				String  reg9000           = reinfLotesDAO.confGerarRegistro(conn, idCnpjRaiz, idPeriodoApuracao, "9000");

				if (reg1000.equals("S") || reg2010.equals("S")) {
					String pastaSchemas = utilsBridge.retornaPastaSchema(tipoAmbiente, dirEmp);
					String caminhoCertificado = utilsBridge.retornaCaminhoCertificadoA1(tipoCertificado, tipoAmbiente, nomeCertificado, dirEmp);
					String caminhoCacerts = utilsBridge.retornaCaminhoCacerts(tipoAmbiente, ufCacerts, modDocumento, dirEmp);
					StartConfiguracoes.iniciaConfigurações(tipoAmbiente, modDocumento, siglaUf, tipoCertificado, nomeCertificado, senhaCertificado, modeloCertificado, caminhoCertificado, dirEmp, pastaSchemas, caminhoCacerts, versaoXml, versaoNfe, tempoTimeOut, webService, ativaLog);
					geraLotesReinf(conn, facade, tipoAmbiente, idCnpjRaiz, idPeriodoApuracao, strPerApur, dirEmp, verProc, reg1000, reg2010, reg9000, servicoSefaz);
				} // reg1000.equals("S") || reg2010.equals("S")

			} // while (rs.next())	

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fechaConnection(conn, pstmt, rs);
		}
		return ret;
	}

	private void geraLotesReinf(Connection conn, IAppFacade facade, Integer tipoAmbiente, Long idCnpjRaiz, Long idPeriodoApuracao, String strPerApur, String dirEmp, String verProc, String reg1000, String reg2010, String reg9000, String servicoSefaz) {
		Long idServicoTomado = (long) 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String ret = "";
		String sql1 = "";
		int qtde = 0;
		try {
			if (reg2010.equals("S") && reg1000.equals("S")) {
				sql1       += " Select distinct ";
				sql1       += "        st.tp_insc, ";
				sql1       += "        cnpj_raiz, ";
				sql1       += "        erc.id_empresa_raiz_cnpj, ";
				sql1       += "        rpad(erc.cnpj_raiz, 14,'0') cnpj_raiz_lote, ";
				sql1       += "        to_char(current_timestamp, 'yyyyMMdd') dataGeracao, ";
				sql1       += "        st.cnpj_prestador, ";
				sql1       += "        rlef.status_lote_reinf ";
				sql1       += " From reinf.tb_reinf_lotes_efd_receita rlef, ";
				sql1       += "      reinf.tb_servicos_tomados st, ";
				sql1       += "      bridge.tb_empresa_raiz_cnpj erc ";
				sql1       += " Where rlef.evento = '2010' ";
				sql1       += " and   rlef.attribute1 = st.id_servicos_tomados ";   
				sql1       += " and   rlef.periodo_apuracao_reinf = st.periodo_apuracao_reinf ";
				sql1       += " and   erc.id_empresa_raiz_cnpj = cnpj_empresa_raiz_reinf ";
			} else {
				if (reg2010.equals("S") && reg1000.equals("N")) {
					sql1       += " Select distinct ";
					sql1       += "        st.tp_insc, ";
					sql1       += "        cnpj_raiz, ";
					sql1       += "        erc.id_empresa_raiz_cnpj, ";
					sql1       += "        rpad(erc.cnpj_raiz, 14,'0') cnpj_raiz_lote, ";
					sql1       += "        to_char(current_timestamp, 'yyyyMMdd') dataGeracao, ";
					sql1       += "        st.cnpj_prestador, ";
					sql1       += "        rlef.status_lote_reinf ";
					sql1       += " From reinf.tb_reinf_lotes_efd_receita rlef, ";
					sql1       += "      reinf.tb_servicos_tomados st, ";
					sql1       += "      bridge.tb_empresa_raiz_cnpj erc ";
					sql1       += " Where rlef.evento = '2010' ";
					sql1       += " and   rlef.attribute1 = st.id_servicos_tomados ";   
					sql1       += " and   rlef.periodo_apuracao_reinf = st.periodo_apuracao_reinf ";
					sql1       += " and   erc.id_empresa_raiz_cnpj = cnpj_empresa_raiz_reinf ";
				} else {
					if (reg2010.equals("N") && reg1000.equals("S")) {
						sql1       += " Select distinct ";
						sql1       += "        ic.tipo_inscricao as tp_insc, ";
						sql1       += "        cnpj_raiz, ";
						sql1       += "        erc.id_empresa_raiz_cnpj, ";
						sql1       += "        rpad(erc.cnpj_raiz, 14,'0') cnpj_raiz_lote, ";
						sql1       += "        to_char(current_timestamp, 'yyyyMMdd') dataGeracao, ";
						sql1       += "        '0' as cnpj_prestador, ";
						sql1       += "        rlef.status_lote_reinf ";
						sql1       += " From reinf.tb_reinf_lotes_efd_receita rlef, ";
						sql1       += "      reinf.tb_informacoes_contribuinte ic, ";
						sql1       += "      bridge.tb_empresa_raiz_cnpj erc ";
						sql1       += " Where rlef.evento = '1000' ";
						sql1       += " and   rlef.attribute1 = ic.id_informacoes_contribuinte ";   
						sql1       += " and   rlef.periodo_apuracao_reinf = ic.periodo_apuracao_reinf ";
						sql1       += " and   erc.id_empresa_raiz_cnpj = cnpj_empresa_raiz_reinf ";
					} // reg2010.equals("N") && reg1000.equals("S")
				} // reg2010.equals("S") && reg1000.equals("N")
			} // reg2010.equals("S") && reg1000.equals("S")

			if (sql1 != null) {
				pstmt = conn.prepareStatement(sql1);
				rs = pstmt.executeQuery();
				while (rs.next()) {		
					String tp_insc            = rs.getString("tp_insc");
					String  cnpjRaiz          = rs.getString("cnpj_raiz");
					String cnpjRaizId         = rs.getString("cnpj_raiz_lote");
					String dataGeracao        = rs.getString("dataGeracao");	
					String cnpjPrestador      = rs.getString("cnpj_prestador");
					String statusLoteEfd      = rs.getString("status_lote_reinf");
                 

					String tipoInscricao = null;
					if (tp_insc.equals("Um")) {
						tipoInscricao = "1";
					} else {
						tipoInscricao = "2";
					}

					qtde++;
					reinf                     = new Reinf();
					loteEventos               = new Reinf.LoteEventos();
					numLote                   = null;				
					String horaGeracao        = reinfLotesDAO.selecionaHoraGeracao(conn);
					horaGeracao = horaGeracao.replaceAll(":", "");
					numLote = "ID" + tipoInscricao + cnpjRaizId + dataGeracao + horaGeracao;
					Long idReinf = reinfLotesDAO.retornaIdLoteReinf(conn, numLote, cnpjPrestador, idPeriodoApuracao);
					int qtdeLotes = reinfLotesDAO.retornaQtdeLotes(conn, idCnpjRaiz, idPeriodoApuracao);

					// Registro R-1000 - Informações do Contribuinte
					String arqLote = null;
					if (reg1000.equals("S") && statusLoteEfd.equals("EWS")) {
						arqLote = dadosContribuinte(conn, facade, cnpjRaizId, dataGeracao, horaGeracao, tipoInscricao, verProc, dirEmp, strPerApur, idPeriodoApuracao, idCnpjRaiz, cnpjPrestador, idReinf, servicoSefaz);
					}

					
					String arqXml = preencherCabecalhoLote(arqLote);
					System.out.println(arqXml);

					/*String arqXml = strValueLoteReinf(reinf); */
					String tagRaiz = utilsBridge.retornaTagRaizXml(arqXml);
					String statusLote = "Receita";
					String vArq = utilsBridge.retornoCaminhoArqReinf(dirEmp, servicoSefaz, cnpjRaiz, strPerApur, cnpjPrestador, numLote, tagRaiz, statusLote, numLote);
					utilsBridge.saveXmlReinf(arqXml, vArq);
					
				
					Boolean valida = true;
					String retValidacao = "";
					if (valida == true) {
						String tipo = Validar.REINF_LOTE;
						retValidacao = Validar.validaXml(arqXml, tipo);
						System.out.println(retValidacao);
					} else {
						System.out.println(retValidacao);
					} // valida == true
					
					if (retValidacao == null || retValidacao.isEmpty()) {
						String retXml = RecepcaoLoteReinf.enviarLoteReinfSefaz(vArq);
						System.out.println(" Retorno Receita" + retXml);
						tagRaiz = utilsBridge.retornaTagRaizXml(arqXml);
						statusLote = "Retorno_Receita";
						vArq = utilsBridge.retornoCaminhoArqReinf(dirEmp, servicoSefaz, cnpjRaiz, strPerApur, cnpjPrestador, numLote, tagRaiz, statusLote, numLote);
						utilsBridge.saveXmlReinf(retXml, vArq);
					}
					
				} // while (rs.next()
			} // sql1 != null
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		} finally {
			fechaSql(conn, pstmt, rs);
		}		
	}

	private String dadosContribuinte(Connection conn, IAppFacade facade, String cnpjRaizId, String dataGeracao, String horaGeracao, String tipoInscricao, String verProc, String dirEmp, String strPerApur2, Long idPeriodoApuracao, Long idCnpjRaiz, String cnpjPrestador, Long idReinf, String servicoSefaz) {
		String ret = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String Id = "ID";	
		try {
			conn = SqlConnectionJdbc.getConnection();
			conn.setAutoCommit(false);
			String sql1 = " Select distinct ";
			sql1 += "              ic.tipo_ambiente, ";
			sql1 += "              ic.tipo_cadastro_contribuinte, ";
			sql1 += "              1 proc_emi, ";
			sql1 += "              tipo_inscricao, ";
			sql1 += "              erc.cnpj_raiz, ";
			sql1 += "              to_char(par.competencia, 'yyyy-MM') ini_validade, ";
			sql1 += "              to_char(par.competencia, 'yyyy-MM') fim_validade, ";
			sql1 += "              classificacao_tributaria, ";
			sql1 += "              ind_escrituracao, ";
			sql1 += "              ind_desoneracao, ";
			sql1 += "              ind_acordo_isen_multa, ";
			sql1 += "              ind_situacao_pessoa_juridica, ";
			sql1 += "              nome_contato, ";
			sql1 += "              cpf_contato, ";
			sql1 += "              fone_contato, ";
			sql1 += "              celular_contato, ";
			sql1 += "              email_contato, ";
			sql1 += "              rler.evento, ";
			sql1 += "              rler.attribute1, ";
			sql1 += "              id_reinf_lotes_efd_receita ";
			sql1 += " From reinf.tb_informacoes_contribuinte ic, ";
			sql1 += "      bridge.tb_empresa_raiz_cnpj erc, ";
			sql1 += "      reinf.tb_periodo_apuracao_reinf par, ";
			sql1 += "      bridge.tb_empresa e, ";
			sql1 += "      bridge.tb_empresa_contato ec, ";
			sql1 += "      reinf.tb_reinf_lotes_efd_receita rler ";
			sql1 += " Where erc.id_empresa_raiz_cnpj = ic.id_empresa_raiz_cnpj ";  
			sql1 += " and   par.id_periodo_apuracao_reinf = ic.periodo_apuracao_reinf ";
			sql1 += " and   e.id_empresa_raiz_cnpj = ic.id_empresa_raiz_cnpj ";
			sql1 += " and   ec.id_empresa = e.id_empresa ";
			sql1 += " and   rler.evento = '1000' ";
			sql1 += " and   rler.periodo_apuracao_reinf = ic.periodo_apuracao_reinf ";
			sql1 += " and   rler.cnpj_empresa_raiz_reinf = ic.id_empresa_raiz_cnpj ";

			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {	
				Integer tipoAmbiente                 = rs.getInt("tipo_ambiente");
				short   procEmi                      = rs.getShort("proc_emi");				
				String  cnpjRaiz                     = rs.getString("cnpj_raiz");
				String  tipoCadastro                 = rs.getString("tipo_cadastro_contribuinte");
				String  iniValidade                  = rs.getString("ini_validade");
				String  fimValidade                  = rs.getString("fim_validade");
				String  classTrib                    = rs.getString("classificacao_tributaria");
				String  ind_escrituracao             = rs.getString("ind_escrituracao");
				String  ind_desoneracao              = rs.getString("ind_desoneracao");
				String  ind_acordo_isen_multa        = rs.getString("ind_acordo_isen_multa");
				String  ind_situacao_pessoa_juridica = rs.getString("ind_situacao_pessoa_juridica");
				String  contatoNome                  = rs.getString("nome_contato");
				String  contatoCpf                   = rs.getString("cpf_contato");
				String  contatoFone                  = rs.getString("fone_contato");
				String  contatoCelular               = rs.getString("celular_contato");
				String  contatoEmail                 = rs.getString("email_contato");
				String  codigoEvento                 = rs.getString("evento");
				Long    Attribute1                   = rs.getLong("attribute1");
				Long    idLoteEfd                    = rs.getLong("id_reinf_lotes_efd_receita");



				
				if (tipoInscricao.equals("Um") || tipoInscricao.equals("1")) {
					tipoInscricaoShort = 1;
				} else {
					tipoInscricaoShort = 2;
				}

				long indEscrituracao;
				if (ind_escrituracao.equals("N")) {
					indEscrituracao = 0;
				} else {
					indEscrituracao = 1;
				}

				long indDesoneracao;
				if (ind_desoneracao.equals("N")) {
					indDesoneracao = 0;
				} else {
					indDesoneracao = 1;
				}

				long indAcordoIsenMulta;
				if (ind_acordo_isen_multa.equals("N")) {
					indAcordoIsenMulta = 0; 
				} else {
					indAcordoIsenMulta = 1; 
				}

				long indSitPj = 0;
				if (ind_situacao_pessoa_juridica.equals("Zero")) {
					indSitPj = 0;
				} else {
					if (ind_situacao_pessoa_juridica.equals("Um")) {
						indSitPj = 1;
					} else {
						if (ind_situacao_pessoa_juridica.equals("Dois")) {
							indSitPj = 2;
						} else {
							if (ind_situacao_pessoa_juridica.equals("Tres")) {
								indSitPj = 3;
							} else {
								if (ind_situacao_pessoa_juridica.equals("Quatro")) {
									indSitPj = 4;
								}
							}
						}
					}
				}

				seqChave++;
				IdEvento = "ID" + String.format("%05d",seqChave);

				br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf reinf = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf();
				br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf reinfContrib = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf();
				EvtInfoContri evtInfoContri = new EvtInfoContri();
				evtInfoContri.setId(Id);

				IdeEvento ideEvento = new IdeEvento();
				ideEvento.setTpAmb(tipoAmbiente);
				ideEvento.setProcEmi(Long.valueOf(procEmi));
				ideEvento.setVerProc(verProc);

				IdeContri ideContri = new IdeContri();
				ideContri.setTpInsc(tipoInscricaoShort); // Deve ser igual a [1] (CNPJ) ou [2] (CPF)
				ideContri.setNrInsc(cnpjRaiz);

				InfoContri infoContri = new InfoContri();
				if (tipoCadastro.equals("I")) {
					
					if (tipoCadastro.equals("E") && tipoAmbiente == 2) {
						ideEvento.setVerProc("RemoverContribuinte");
					} else {
						ideEvento.setVerProc(verProc);
					}
					
					Inclusao inclusao = new Inclusao();
					IdePeriodo idePeriodo = new IdePeriodo();
					idePeriodo.setIniValid(iniValidade);
					idePeriodo.setFimValid(fimValidade);
					inclusao.setIdePeriodo(idePeriodo);

					InfoCadastro infoCadastro = new InfoCadastro();
					infoCadastro.setClassTrib(classTrib);
					infoCadastro.setIndEscrituracao(indEscrituracao);
					infoCadastro.setIndDesoneracao(indDesoneracao);
					infoCadastro.setIndAcordoIsenMulta(indAcordoIsenMulta);
					infoCadastro.setIndSitPJ(indSitPj);

					Contato contato = new Contato();
					contato.setNmCtt(contatoNome);
					contato.setCpfCtt(contatoCpf);
					contato.setFoneFixo(contatoFone);
					contato.setFoneCel(contatoCelular);
					contato.setEmail(contatoEmail);
					infoCadastro.setContato(contato);

					SoftHouse softHouse = new SoftHouse();
					softHouse.setCnpjSoftHouse("07671092000180");
					softHouse.setNmRazao("TBM - Textil Bezerra de Menezes S A");
					softHouse.setNmCont("Ocelio Jose Sousa de Pinho");
					softHouse.setTelefone("8540129100");
					softHouse.setEmail("ocelio@tbmtextil.com.br");
					infoCadastro.getSoftHouse().add(softHouse);
					inclusao.setInfoCadastro(infoCadastro);
					infoContri.setInclusao(inclusao);
				} else {
					if (tipoCadastro.equals("A")) {
						Alteracao alteracao = new Alteracao();
						br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.IdePeriodo idePeriodo = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.IdePeriodo();
						idePeriodo.setIniValid(iniValidade);						
						idePeriodo.setFimValid(fimValidade);
						alteracao.setIdePeriodo(idePeriodo);

						br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro infoCadastro = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro();
						infoCadastro.setClassTrib(classTrib);
						infoCadastro.setIndEscrituracao(indEscrituracao);
						infoCadastro.setIndDesoneracao(indDesoneracao);
						infoCadastro.setIndAcordoIsenMulta(indAcordoIsenMulta);
						infoCadastro.setIndSitPJ(indSitPj);

						br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro.Contato contato = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro.Contato();
						contato.setNmCtt(contatoNome);
						contato.setCpfCtt(contatoCpf);
						contato.setFoneFixo(contatoFone);
						contato.setFoneCel(contatoCelular);
						contato.setEmail(contatoEmail);
						infoCadastro.setContato(contato);
						alteracao.setInfoCadastro(infoCadastro);

						NovaValidade novaValidade = new NovaValidade();
						novaValidade.setIniValid(iniValidade);
						novaValidade.setFimValid(fimValidade);
						alteracao.setNovaValidade(novaValidade);
						infoContri.setAlteracao(alteracao);
					} else {
						if (tipoCadastro.equals("E")) {
							if (tipoAmbiente == 1) {
								Exclusao exclusao = new Exclusao();
								br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Exclusao.IdePeriodo idePeriodo = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Exclusao.IdePeriodo();
								idePeriodo.setIniValid(iniValidade);
								idePeriodo.setFimValid(fimValidade);
								exclusao.setIdePeriodo(idePeriodo);
								infoContri.setExclusao(exclusao);
							} else {
								Alteracao alteracao = new Alteracao();
								br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.IdePeriodo idePeriodo = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.IdePeriodo();
								idePeriodo.setIniValid(iniValidade);
								;
								idePeriodo.setFimValid(fimValidade);
								alteracao.setIdePeriodo(idePeriodo);

								br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro infoCadastro = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro();
								classTrib = "99";
								infoCadastro.setClassTrib(classTrib);
								infoCadastro.setIndEscrituracao(indEscrituracao);
								infoCadastro.setIndDesoneracao(indDesoneracao);
								infoCadastro.setIndAcordoIsenMulta(indAcordoIsenMulta);
								infoCadastro.setIndSitPJ(indSitPj);

								br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro.Contato contato = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Alteracao.InfoCadastro.Contato();
								contato.setNmCtt(contatoNome);
								contato.setCpfCtt(contatoCpf);
								contato.setFoneFixo(contatoFone);
								contato.setFoneCel(contatoCelular);
								contato.setEmail(contatoEmail);
								infoCadastro.setContato(contato);
								alteracao.setInfoCadastro(infoCadastro);

								NovaValidade novaValidade = new NovaValidade();
								novaValidade.setIniValid(iniValidade);
								novaValidade.setFimValid(fimValidade);
								alteracao.setNovaValidade(novaValidade);
								infoContri.setAlteracao(alteracao);
							}
						}
					}
				}

				evtInfoContri.setIdeEvento(ideEvento);
				evtInfoContri.setIdeContri(ideContri);
				evtInfoContri.setInfoContri(infoContri);				
				reinf.setEvtInfoContri(evtInfoContri);
				
				StringWriter sw = new StringWriter();
				JAXBContext context = JAXBContext.newInstance(br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.class);
				Marshaller marshaller = context.createMarshaller();
				JAXBElement<br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri> element = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.ObjectFactory().createEvtInfoContribuinte(evtInfoContri);
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
				marshaller.marshal(element, sw);
				String xml = sw.toString();
				System.out.println(xml);

				String tagRaiz = utilsBridge.retornaTagRaizXml(xml);
				Id = "ID" + tipoInscricao + cnpjRaizId + dataGeracao + horaGeracao + String.format("%05d", seqChave);				
				xml = xml.replaceAll(" xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + ConstantesUtil.VERSAO.EFD_REINF + "\">", ">");
				System.out.println(xml);
				String pesquisa ="id=\"ID\"";
				String alteracao = "id=\"" + Id + "\"";
				xml = xml.replaceAll(pesquisa, alteracao);
				String nameSpaceEvento ="xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + ConstantesUtil.VERSAO.EFD_REINF;
				String xmlReinf = preencherCabecalhoEvento(xml, IdEvento, nameSpaceEvento);
				System.out.println(xmlReinf);
		
				String statusLote = "Gerado";
				String statusReinf = "G";
				String vArq = utilsBridge.retornoCaminhoArqReinf(dirEmp, servicoSefaz, cnpjRaiz, strPerApur, cnpjPrestador, numLote, tagRaiz, statusLote,  Id);				             
				utilsBridge.saveXmlReinf(xmlReinf, vArq);
				

				String codRetorno      = ".";
				String descRetorno     = ".";
				Long confReinfLote     = reinfLotesDAO.confReinfLote(conn, numLote, cnpjPrestador, strPerApur);
				Long confEvento        = reinfLotesDAO.confReinfEventoLote(conn, Id);
				Long confArquivo       = reinfLotesDAO.confReinfArquivo(conn, vArq);
				Long idServicosTomados = (long) 0;
				salvaReinf(conn, facade, statusReinf, confReinfLote, confEvento, codigoEvento, vArq, numLote, idPeriodoApuracao, cnpjPrestador);


				
				xmlReinf = Assinar.assinarXml(xmlReinf,  Assinar.REINF_INFO_CONTRIB);
				statusLote = "Assinado";
				statusReinf = "A";
				vArq = utilsBridge.retornoCaminhoArqReinf(dirEmp, servicoSefaz, cnpjRaiz, strPerApur, cnpjPrestador, numLote, tagRaiz, statusLote,  Id);				      
				utilsBridge.saveXmlReinf(xmlReinf, vArq);
				salvaReinf(conn, facade, statusReinf, confReinfLote, confEvento, codigoEvento, vArq, numLote, idPeriodoApuracao, cnpjPrestador);
		
				
				
				Boolean valida = true;
				String retValidacao = null;				
				if (valida == false) {
					retValidacao = "";
				} else {
					if (valida == true) {
						String tipo = Validar.REINF_INFO_CONTRIB;
						retValidacao = Validar.validaXml(xmlReinf, tipo);
					}
				}
				
				if (retValidacao.isEmpty()) {
//					statusReinf = "VALIDADO";
//					confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
//					reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
//					reinfLotesDAO.updateReinfEventos(conn, facade, statusReinf, confEvento);
//					conn.commit();
//					reinf = getContribuinte(xmlReinf);
					ret = preencherEvento(xmlReinf, IdEvento);
				} else {
//					statusReinf = "ERRO_VALIDACAO";
//					confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
//					reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
//					reinfLotesDAO.updateReinfEventosValidacao(conn, facade, statusReinf, confEvento, retValidacao);
//					conn.commit();
					System.out.println("E R R O  V  A L I D A C A O " + tagRaiz + " - " + retValidacao);
				}
				
				/*
				JAXBContext context2 = JAXBContext.newInstance(br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.class);
				Marshaller marshaller2 = context2.createMarshaller();
				marshaller2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = docBuilder.newDocument();
				marshaller2.marshal(reinf, document);
				Element evtContribuinte = document.getDocumentElement();
				TArquivoeReinf arquivoeReinf = new TArquivoeReinf();
				arquivoeReinf.setId(IdEvento);
				arquivoeReinf.setAny(evtContribuinte);
				loteEventos.getEvento().add(arquivoeReinf); */
				
			} // while (rs.next()
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		//	reinf.setLoteEventos(loteEventos);
			fechaSql(conn, pstmt, rs);
		}
		return ret;
		
	}

	private String preencherCabecalhoEvento(String xml, String idEvento, String nameSpaceEvento)  {  
		StringBuilder sb = new StringBuilder();	
		sb.append("<Reinf ").append(nameSpaceEvento).append("\">");  
		sb.append(xml);  
		sb.append("</Reinf>"); 
		return sb.toString();  
	}
	
	private String preencherEvento(String xml, String IdEvento)  {  
		StringBuilder sb = new StringBuilder();	 
		sb.append("<evento id=\"" + IdEvento + "\">"); 
		sb.append(xml);  
		sb.append("</evento>"); 
		return sb.toString();  
	}
	
	private String preencherCabecalhoLote(String xml)  {  
		StringBuilder sb = new StringBuilder();	
		sb.append("<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/envioLoteEventos/v1_04_00\"><loteEventos>");  
		sb.append(xml);  
		sb.append("</loteEventos></Reinf>"); 
		return sb.toString(); 
	}

/*	
	private String salvaReinf(Connection conn, IAppFacade facade, Long idReinf, Long confReinfLote, Long confEvento, Long confArquivo, String statusReinf, String tagRaiz, String vArq, String codRetorno, String descRetorno, String cnpjRaiz, String Id, String codigoEvento, Long Attribute1, String cnpjPrestador, String strPerApur, Long idLoteEfd, Long idServicosTomados) throws SQLException {
		String ret = "";
		String tipo = "text/xml";
		String usu_seguranca = "admin";
		try {
			if (tagRaiz.equals("Reinf") || tagRaiz.equals("ReinfFechamento") || tagRaiz.equals("ReinfExclusao")) {
				idLoteEfd = (long) 0;
				seqChave = 0;
			}
			if (confReinfLote > 0) {
				if (confEvento > 0) {
					reinfLotesDAO.updateReinfEventos(conn, facade, statusReinf, confEvento);
				} else {
					Long idEventoReinf = reinfLotesDAO.retornaIdEvento(conn);
					reinfLotesDAO.inserirReinfEventos(conn, facade, idEventoReinf, idReinf, Id, statusReinf, vArq, tagRaiz, seqChave, codigoEvento, idLoteEfd, idServicosTomados);
					confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
					reinfLotesDAO.inserirReinfEventosRetorno(conn, facade, confEvento);
				}
			} else {
				ret = reinfLotesDAO.inserirReinfLotes(conn, facade, idReinf, cnpjRaiz, numLote, statusReinf, cnpjPrestador, strPerApur);
				if (confEvento > 0) {
					reinfLotesDAO.updateReinfEventos(conn, facade, statusReinf, confEvento);
				} else {
					Long idEventoReinf = reinfLotesDAO.retornaIdEvento(conn);
					reinfLotesDAO.inserirReinfEventos(conn, facade, idEventoReinf, idReinf, Id, statusReinf, vArq, tagRaiz, seqChave, codigoEvento, idLoteEfd, idServicosTomados);
					confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
					reinfLotesDAO.inserirReinfEventosRetorno(conn, facade, confEvento);
				} // confEvento
			} // confReinfLote

			if (confArquivo == 0 && statusReinf.equals("ASSINADO")) {
				Long idArquivo = reinfLotesDAO.selIdArquivo(conn);
				Long IdArquivoConteudo = reinfLotesDAO.selIdArquivoConteudo(conn);
				reinfLotesDAO.insertArquivoConteudo(IdArquivoConteudo, vArq, conn);
				reinfLotesDAO.insertArquivo(idArquivo, vArq, tipo, usu_seguranca, IdArquivoConteudo, conn);
				reinfLotesDAO.insertReinfLoteArquivo(idReinf, IdArquivoConteudo, conn);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.commit();
		}
		return ret;
	}
*/
	
	protected static br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf getContribuinte(String xml) throws Exception {
		br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf reinf;
		try {
			JAXBContext context = JAXBContext.newInstance(br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			reinf = unmarshaller.unmarshal(new StreamSource(new StringReader(xml)),	br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.class).getValue();
			return reinf;
		} catch (Exception ex) {
			throw new Exception(ex.toString());
		}
	}
	
	private String salvaReinf(Connection conn, IAppFacade facade, String statusReinf, Long confReinfLote, Long confEvento, String codigoEvento, String nomeArquivo, String nomeEvento, Long idPeriodoApuracao, String cnpjPrestador ) throws SQLException {
		String ret = "";
		try {
			
			if (confReinfLote > 0) {
				if (confEvento > 0) {
					reinfLotesDAO.updateReinfEventos(conn, facade, statusReinf, confEvento);
					conn.commit();
				} else {
					Long idEventoReinf = reinfLotesDAO.retornaIdEvento(conn);
					reinfLotesDAO.inserirReinfEvento(conn, facade, confEvento, confReinfLote, statusReinf, nomeEvento, codigoEvento, nomeEvento, seqChave, nomeArquivo, idServicosTomados);
					conn.commit();
				} // confEvento
			} else {
				Long idReinf = reinfLotesDAO.retornaIdLoteReinf(conn, nomeEvento, cnpjPrestador, idPeriodoApuracao);
				Long idEventoReinf = reinfLotesDAO.retornaIdEvento(conn);
				reinfLotesDAO.inserirReinfEvento(conn, facade, idEventoReinf, idReinf, statusReinf, nomeEvento, codigoEvento, nomeEvento, seqChave, nomeArquivo, idServicosTomados);
				
			} // confReinfLote
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.commit();
		}
		return ret;
	}
	
	public String inserirReinfLotes(Connection conn, IAppFacade facade, Long idReinf, String cnpjRaiz, String numLote, String statusReinf, String cnpjPrestador, String strPerApur) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " INSERT INTO reinf.tb_reinf_lotes(id_reinf_lotes  , cnpj_empresa_raiz_reinf, periodo_apuracao_reinf, status_lote_reinf     , tipo_ambiente, num_lote, cnpj_prestador, data_geracao) "
					+ "                                   VALUES ( " + idReinf + ", '" + cnpjRaiz + "'     , '" + strPerApur + "'  , '" + statusReinf + "'), ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 			
		} catch (Exception e) {
			String subject = "Erro inserirReinfLotes ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
	//		facade.enviarEmailException(subject, bodyMail, emailAttachment);
		} finally {
			if (!sqlErro.contains("Erro Sql : ")) {
				sqlErro = null;
			}
			try {				
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sqlErro;
	}
}
