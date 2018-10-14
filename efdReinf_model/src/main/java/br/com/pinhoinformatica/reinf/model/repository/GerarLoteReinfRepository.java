package br.com.pinhoinformatica.reinf.model.repository;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.pinhoinformatica.bridge.model.utils.UtilsBridge;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.envioLoteEventos.Reinf;
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.envioLoteEventos.TArquivoeReinf;
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
import br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.retornoLoteEventos.TArquivoReinf;
import br.com.pinhoinformatica.pinhoNfeCert.model.ConfiguracoesIniciaisNfe;
import br.com.pinhoinformatica.pinhoNfeCert.model.run.Assinar;
import br.com.pinhoinformatica.pinhoNfeCert.model.run.Validar;
import br.com.pinhoinformatica.pinhoNfeCert.model.utils.CertificadoUtil;
import br.com.pinhoinformatica.pinhoNfeCert.model.utils.XmlUtil;
import br.com.pinhoinformatica.reinf.facade.IAppFacade;
import br.com.pinhoinformatica.reinf.model.run.StartConfiguracoes;
import br.com.pinhoinformatica.reinf.persistence.jpa.reinflotes.ReinfLotesDAO;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.FecharConexao;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.SqlConnectionJdbc;

public class GerarLoteReinfRepository extends FecharConexao {

	@Inject private ReinfLotesDAO reinfLotesDAO;
	@Inject private UtilsBridge utilsBridge;
	private Logger log = Logger.getLogger(this.getClass().getCanonicalName());

	private static String numLote = null;
	private String IdEvento = null;
	private Integer seqChave = 0;
	private Reinf reinf;
	Reinf.LoteEventos loteEventos;
	private String strPerApur;
//	private static String versaoReinf = "v1_03_02";

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
			sql1       += "        ic.tipo_ambiente, ";
			sql1       += "        e.emp_diretorio, ";
			sql1       += "        e.tipo_certificado, ";
			sql1       += "        e.modelo_certificado, ";
			sql1       += "        e.nome_certificado, ";
			sql1       += "        e.senha_certificado, ";                  
			sql1       += "        ls.nome_schema, ";
			sql1       += "        ls.nome_xsd, ";
			sql1       += "        ls.versao_lote, ";
			sql1       += "        md.desc_modelo, ";
			sql1       += "        cs.uf_cacert, ";
			sql1       += "        sws.sigla_estado, ";
			sql1       += "        sws.versao_nfe, ";
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
					StartConfiguracoes.iniciaConfigurações(tipoAmbiente, modDocumento, siglaUf, tipoCertificado, nomeCertificado, senhaCertificado, modeloCertificado, caminhoCertificado, dirEmp, pastaSchemas, caminhoCacerts, versaoXml, versaoNfe, tempoTimeOut, webService);
					ConfiguracoesIniciaisNfe config = CertificadoUtil.iniciaConfiguracoes();
					geraLotesReinf(conn, facade, tipoAmbiente, idCnpjRaiz, idPeriodoApuracao, dirEmp, verProc, reg1000, reg2010, reg9000);
				} else {
					if (reg2098.equals("S")) {	// Re-Abertura de Periodo						
						String pastaSchemas = utilsBridge.retornaPastaSchema(tipoAmbiente, dirEmp);
						String caminhoCertificado = utilsBridge.retornaCaminhoCertificadoA1(tipoCertificado, tipoAmbiente, nomeCertificado, dirEmp);
						String caminhoCacerts = utilsBridge.retornaCaminhoCacerts(tipoAmbiente, ufCacerts, modDocumento, dirEmp);
						StartConfiguracoes.iniciaConfigurações(tipoAmbiente, modDocumento, siglaUf, tipoCertificado, nomeCertificado, senhaCertificado, modeloCertificado, caminhoCertificado, dirEmp, pastaSchemas, caminhoCacerts, versaoXml, versaoNfe, tempoTimeOut, webService);
						ConfiguracoesIniciaisNfe config = CertificadoUtil.iniciaConfiguracoes();
						//	geraLotesReAberturaReinf(conn, facade, verProc, idCnpjRaiz, idPeriodoApuracao);
					} else {
						if (reg2099.equals("S")) {	// Fechamento de Periodo						
							String pastaSchemas = utilsBridge.retornaPastaSchema(tipoAmbiente, dirEmp);
							String caminhoCertificado = utilsBridge.retornaCaminhoCertificadoA1(tipoCertificado, tipoAmbiente, nomeCertificado, dirEmp);
							String caminhoCacerts = utilsBridge.retornaCaminhoCacerts(tipoAmbiente, ufCacerts, modDocumento, dirEmp);
							StartConfiguracoes.iniciaConfigurações(tipoAmbiente, modDocumento, siglaUf, tipoCertificado, nomeCertificado, senhaCertificado, modeloCertificado, caminhoCertificado, dirEmp, pastaSchemas, caminhoCacerts, versaoXml, versaoNfe, tempoTimeOut, webService);
							ConfiguracoesIniciaisNfe config = CertificadoUtil.iniciaConfiguracoes();
							//		geraLotesFechamentoReinf(conn, facade, tipoAmbiente, idCnpjRaiz, idPeriodoApuracao, dirEmp, verProc);
						} // reg2099.equals("S")
					} // reg2098.equals("S")
				} // reg1000.equals("S") || reg2010.equals("S")
			} // while (rs.next())	

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fechaConnection(conn, pstmt, rs);
		}
		return ret;
	}	

	private String geraLotesReinf(Connection conn, IAppFacade facade, Integer tipoAmbiente, Long idCnpjRaiz, Long idPeriodoApuracao, String dirEmp, String verProc, String reg1000, String reg2010, String reg9000) {
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
						sql1       += "        st.tp_insc, ";
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
					} // reg2010.equals("N") && reg1000.equals("S")
				} // reg2010.equals("S") && reg1000.equals("N")
			} // reg2010.equals("S") && reg1000.equals("S")

			if (sql1 != null) {
				pstmt = conn.prepareStatement(sql1);
				rs = pstmt.executeQuery();
				while (rs.next()) {		
					String tp_insc            = rs.getString("tp_insc");
					String cnpjRaiz           = rs.getString("id_empresa_raiz_cnpj");
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
					numLote = "ID" + tipoInscricao + cnpjRaizId + dataGeracao + horaGeracao;
					Long idReinf = reinfLotesDAO.retornaIdLoteReinf(conn, numLote, cnpjPrestador, strPerApur);
					int qtdeLotes = reinfLotesDAO.retornaQtdeLotes(conn, idCnpjRaiz, idPeriodoApuracao);

					// Registro R-1000 - Informações do Contribuinte
					if (reg1000.equals("S") && statusLoteEfd.equals("EWS")) {
						dadosContribuinte(conn, facade, cnpjRaizId, dataGeracao, horaGeracao, tipoInscricao, verProc, dirEmp, strPerApur, idPeriodoApuracao, idCnpjRaiz, cnpjPrestador, idReinf);
					}

					String arqXml = strValueLoteReinf(reinf, verProc);
					String tagRaiz = utilsBridge.retornaTagRaizXml(arqXml);
					String vArq = utilsBridge.retornoCaminhoArqReinf(tipoAmbiente, dirEmp, cnpjRaiz, strPerApur, numLote, numLote, "Assinado", tagRaiz, cnpjPrestador);
					String vDir = null;
					utilsBridge.saveXml(arqXml, vDir, vArq);

					String descRetorno = ".";
					String codRetorno = ".";
					String statusReinf = "ASSINADO";				
					Long confReinfLote = reinfLotesDAO.confReinfLote(conn, numLote, cnpjPrestador, strPerApur);
					Long confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, numLote);
					Long confArquivo = reinfLotesDAO.confReinfArquivo(conn, vArq);				
					Long Attribute1 = (long) 0;
					String codigoEvento = "0";
					Long idLoteEfd = (long) 0;
					Long idServicosTomados = (long) 0;
					salvaReinf(conn, facade, idReinf, confReinfLote, confEvento, confArquivo, statusReinf, tagRaiz, vArq, codRetorno, descRetorno, cnpjRaiz, numLote, codigoEvento, Attribute1, cnpjPrestador, strPerApur, idLoteEfd, idServicosTomados );
					conn.commit();


					Boolean valida = true;
					String retValidacao = "";
					confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, numLote);
					confArquivo = reinfLotesDAO.confReinfArquivo(conn, vArq);
					if (valida == true) {
						String tipo = Validar.REINF_LOTE;
						retValidacao = Validar.validaXml(arqXml, tipo);
						retValidacao = retValidacao.replaceAll("'", "");
						if (retValidacao.contains("cvc-elt.1: Cannot find the declaration do campo Reinf")) {
							retValidacao = null;
						}
						if (retValidacao == null || retValidacao.isEmpty()) {
							statusReinf = "VALIDADO";
							confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, numLote);
							reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
							reinfLotesDAO.updateReinfEventos(conn, facade, statusReinf, confEvento);
							conn.commit();
						} else {
							if (retValidacao != null) {
								statusReinf = "ERRO_VALIDACAO";
								confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, numLote);
								reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
								reinfLotesDAO.updateReinfEventosValidacao(conn, facade, statusReinf, confEvento, retValidacao);
								conn.commit();
								System.out.println("E R R O  V  A L I D A C A O " + tagRaiz + " - " + retValidacao);
							}
						}
					}

					String retSefaz = null;
					if (valida == true) {
						// String retXml = RecepcaoLoteReinf.enviarLoteReinfSefaz(vArq);
						String retXml = null;
						if (retXml != null) {
							statusReinf = "AUTORIZADO_RECEITA";
							retSefaz = utilsBridge.removeAcentos(retXml);
							retSefaz = retSefaz.replaceAll("ÃƒÂ£", "a").replaceAll("ÃƒÂ¡", "").replaceAll("ÃƒÂ­", "i").replaceAll("ÃƒÂº", "u").replaceAll("ÃƒÂ§ÃƒÂ£", "ca").replaceAll("ÃƒÂ¢", "a");
							tagRaiz = utilsBridge.retornaTagRaizXml(retSefaz);
							vArq = utilsBridge.retornoCaminhoArqReinf(tipoAmbiente, dirEmp, cnpjRaiz, strPerApur, numLote, numLote, "Receita", tagRaiz, cnpjPrestador);
							utilsBridge.saveXml(retSefaz, vDir, vArq);
							confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, numLote);
							reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
							reinfLotesDAO.updateReinfEventos(conn, facade, statusReinf, confEvento);					
							if (confArquivo == 0 && statusReinf.equals("AUTORIZADO_RECEITA")) {
								String tipo = "text/xml";
								String usu_seguranca = "admin";
								Long idArquivo = reinfLotesDAO.selIdArquivo(conn);
								Long IdArquivoConteudo = reinfLotesDAO.selIdArquivoConteudo(conn);
								reinfLotesDAO.insertArquivoConteudo(IdArquivoConteudo, vArq, conn);
								reinfLotesDAO.insertArquivo(idArquivo, vArq, tipo, usu_seguranca, IdArquivoConteudo, conn);
								reinfLotesDAO.insertReinfLoteArquivo(idReinf, IdArquivoConteudo, conn);
							}						
							conn.commit();						
							lerArquivoRetornoReceita(conn, facade, idReinf, vArq, retSefaz, idPeriodoApuracao);
						} else {
							statusReinf = "ERRO_RECEITA";
							reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
							String numProtocolo = "0";
							String statusEfd = "RECEPCIONADO_RECEITA_ERRO";
							reinfLotesDAO.updateReinfEventosErros(conn, facade, idReinf, numProtocolo, statusReinf, descRetorno);
							reinfLotesDAO.updateLotesControladoriaErroRetorno(conn, facade, statusEfd, numProtocolo, idCnpjRaiz, idPeriodoApuracao, idReinf);
							conn.commit();
						}
					}	
				} // while (rs.next()
			} // sql1 != null
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			fechaSql(conn, pstmt, rs);
		}
		return ret;
	}

	private void dadosContribuinte(Connection conn, IAppFacade facade, String cnpjRaizId, String dataGeracao, String horaGeracao, String tipoInscricao, String verProc, String dirEmp, String strPerApur, Long idPeriodoApuracao, Long idCnpjRaiz, String cnpjPrestador, Long idReinf) throws Exception {		
		String ret = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String Id = "ID";	
		try {
			conn = SqlConnectionJdbc.getConnection();
			conn.setAutoCommit(false);
			String sql1 = " Select distinct ";
			sql1 += "              ic.tipo_ambiente, ";
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
			sql1 += " and   ec.empresa_reinf = e.id ";
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
				String  classTrib                    = rs.getString("class_trib");
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


				short tipoInscricaoShort = 0;
				if (tipoInscricao.equals("Um")) {
					tipoInscricaoShort = 1;
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
							Exclusao exclusao = new Exclusao();
							br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Exclusao.IdePeriodo idePeriodo = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.InfoContri.Exclusao.IdePeriodo();
							idePeriodo.setIniValid(iniValidade);
							idePeriodo.setFimValid(fimValidade);
							exclusao.setIdePeriodo(idePeriodo);
							infoContri.setExclusao(exclusao);
						}
					}
				}

				evtInfoContri.setIdeEvento(ideEvento);
				evtInfoContri.setIdeContri(ideContri);
				evtInfoContri.setInfoContri(infoContri);

				br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf reinf = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf();
				reinf.setEvtInfoContri(evtInfoContri);

				JAXBContext context2 = JAXBContext.newInstance(br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri.class);
				Marshaller marshaller2 = context2.createMarshaller();
				JAXBElement<br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.EvtInfoContri> element = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.ObjectFactory().createEvtInfoContribuinte(evtInfoContri);
				marshaller2.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

				StringWriter sw = new StringWriter();
				marshaller2.marshal(element, sw);

				String xml = sw.toString();
				String tagRaiz = utilsBridge.retornaTagRaizXml(xml);
				Id = "ID" + tipoInscricao + cnpjRaizId + dataGeracao + horaGeracao + String.format("%05d", seqChave);


				String pesquisa = "<evtInfoContri id=\"ID\" xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + verProc + "\">";
				String alteracao = "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + verProc + "\"><evtInfoContri id=\"" + Id + "\" xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + verProc + "\">";
				xml = xml.replaceAll(pesquisa, alteracao);
				xml = xml.replaceAll("</evtInfoContri>", "</evtInfoContri></Reinf>");

				String vArq = utilsBridge.retornoCaminhoArqReinf(tipoAmbiente, dirEmp, cnpjRaiz, strPerApur, numLote, numLote, "Assinado", tagRaiz, cnpjPrestador);
				String vDir = null;
				utilsBridge.saveXml(xml, vDir, vArq);

				String codRetorno = ".";
				String descRetorno = ".";
				String statusReinf = "GERADO";
				Long confReinfLote = reinfLotesDAO.confReinfLote(conn, numLote, cnpjPrestador, strPerApur);
				Long confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
				Long confArquivo = reinfLotesDAO.confReinfArquivo(conn, vArq);
				Long idServicosTomados = (long) 0;

				salvaReinf(conn, facade, idReinf, confReinfLote, confEvento, confArquivo, statusReinf, tagRaiz, vArq, codRetorno, descRetorno, cnpjRaiz, Id, codigoEvento, Attribute1, cnpjPrestador, strPerApur, idLoteEfd, idServicosTomados);

				String arqXml = Assinar.assinarXml(xml, Assinar.REINF);
				vArq = utilsBridge.retornoCaminhoArqReinf(tipoAmbiente, dirEmp, cnpjRaiz, strPerApur, numLote, Id, "Assinado", tagRaiz, cnpjPrestador);
				utilsBridge.saveXml(arqXml, vDir, vArq);

				statusReinf = "ASSINADO";
				confReinfLote = reinfLotesDAO.confReinfLote(conn, numLote, cnpjPrestador, strPerApur);
				confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
				confArquivo = reinfLotesDAO.confReinfArquivo(conn, vArq);
				salvaReinf(conn, facade, idReinf, confReinfLote, confEvento, confArquivo, statusReinf, tagRaiz, vArq, codRetorno, descRetorno, cnpjRaiz, Id, codigoEvento, Attribute1, cnpjPrestador, strPerApur, idLoteEfd, idServicosTomados);

				Boolean valida = true;
				br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf reinf2 = null;
				String retValidacao = null;

				if (valida == true) {
					String tipo = Validar.REINF_INFO_CONTRIB;
					retValidacao = Validar.validaXml(arqXml, tipo);
					if (retValidacao.isEmpty()) {
						statusReinf = "VALIDADO";
						confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
						reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
						reinfLotesDAO.updateReinfEventos(conn, facade, statusReinf, confEvento);
						conn.commit();
						reinf2 = getContribuinte(arqXml);
					} else {
						statusReinf = "ERRO_VALIDACAO";
						confEvento = reinfLotesDAO.confReinfEvento(conn, idReinf, Id);
						reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
						reinfLotesDAO.updateReinfEventosValidacao(conn, facade, statusReinf, confEvento, retValidacao);
						conn.commit();
						System.out.println("E R R O  V  A L I D A C A O " + tagRaiz + " - " + retValidacao);
					}
				}

				JAXBContext context = JAXBContext.newInstance(br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.evtInfoContribuinte.Reinf.class);
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = docBuilder.newDocument();
				marshaller.marshal(reinf2, document);
				Element evtInfoContribuinte = document.getDocumentElement();

				TArquivoeReinf arquivoeReinf  = new TArquivoeReinf();
				arquivoeReinf.setId(IdEvento);			
				arquivoeReinf.setAny(evtInfoContribuinte);
				loteEventos.getEvento().add(arquivoeReinf);

			}	// while
			reinf.setLoteEventos(loteEventos);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

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

	private String salvaReinfFinal(Connection conn, IAppFacade facade, Long idReinf, String statusReinf, String statusEfd, String tagRaiz, String codigoEvento, String numLote, String numProtocolo, String dhProcess, Long idMensagem, String localErroAviso, String descRetorno, Long idPeriodoApuracao, Long idLoteEfd, String vlrTotalBaseRet, String vlrCRTom) throws SQLException {
		String ret = "";
		String tipo = "text/xml";
		String usu_seguranca = "admin";
		try {  
			if (idReinf > 0) {
				reinfLotesDAO.updateReinfLotes(conn, facade, idReinf, statusReinf);
				Long confEvento = reinfLotesDAO.confReinfEventoRetorno(conn, idReinf, numLote);
				if (confEvento > 0) {
					if (statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfReceita")) {
						reinfLotesDAO.updateReinfEventosReceita(conn, facade, statusReinf, codigoEvento, numLote, dhProcess, localErroAviso, numProtocolo);
						reinfLotesDAO.updateReinfEventosRetornoReceita(conn, facade, confEvento, idMensagem, localErroAviso);
						reinfLotesDAO.updateLotesControladoriaRetorno(conn, facade, confEvento, numProtocolo, statusEfd, dhProcess, numLote, idLoteEfd);
						if (codigoEvento.equals("2010")) {
							Long idServicosTomados = reinfLotesDAO.selecionaIdServicosTomados(conn, numLote);
							reinfLotesDAO.updateServicosTomadosReceita(conn, facade, idServicosTomados, statusEfd, numProtocolo, dhProcess, vlrTotalBaseRet, vlrCRTom);
						}
					} else {
						if (statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfReceitaAbertura")) {
							reinfLotesDAO.updateReinfEventosReceita(conn, facade, statusReinf, codigoEvento, numLote, dhProcess, localErroAviso, numProtocolo);
							reinfLotesDAO.updateReinfEventosRetornoReceita(conn, facade, confEvento, idMensagem, localErroAviso);
							reinfLotesDAO.updateLotesControladoriaRetorno(conn, facade, confEvento, numProtocolo, statusEfd, dhProcess, numLote, idLoteEfd);
						} else {
							if (statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfReceitaFechamento")) {
								reinfLotesDAO.updateReinfEventosReceita(conn, facade, statusReinf, codigoEvento, numLote, dhProcess, localErroAviso, numProtocolo);
								reinfLotesDAO.updateReinfEventosRetornoReceita(conn, facade, confEvento, idMensagem, localErroAviso);
								reinfLotesDAO.updateLotesControladoriaRetorno(conn, facade, confEvento, numProtocolo, statusEfd, dhProcess, numLote, idLoteEfd);
								reinfLotesDAO.updatePeriodoControladoria(conn, facade, idPeriodoApuracao);
							} else {
								if (statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfExclusao")) {
								} else {
									if (statusReinf.equals("ERRO_RECEITA") && tagRaiz.equals("ReinfReceita")) {
										reinfLotesDAO.updateReinfEventosReceita(conn, facade, statusReinf, codigoEvento, numLote, dhProcess, descRetorno, numProtocolo);
										reinfLotesDAO.updateReinfEventosRetornoReceita(conn, facade, confEvento, idMensagem, localErroAviso);
										reinfLotesDAO.updateLotesControladoriaRetorno(conn, facade, confEvento, numProtocolo, statusEfd, dhProcess, numLote, idLoteEfd);
									} else {
										if (statusReinf.equals("ERRO_RECEITA") && tagRaiz.equals("ReinfExclusao")) {

										} // statusReinf.equals("ERRO_RECEITA") && tagRaiz.equals("ReinfExclusao")
									} // statusReinf.equals("ERRO_RECEITA") && tagRaiz.equals("ReinfReceita")
								} // statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfExclusao")
							} // statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfReceitaFechamento")
						} // statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfReceitaAbertura")
					} // statusReinf.equals("AUTORIZADO_RECEITA") && tagRaiz.equals("ReinfReceita")
				} else {
				} // confEvento > 0
			} else {

			} // confReinf > 0
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.commit();
		}
		return ret;
	}


	public String lerArquivoRetornoReceita(Connection conn, IAppFacade facade, Long confReinf, String vArq, String retSefaz, Long idPeriodoApuracao) {
		String retLeitura = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		Long idReinf = confReinf;
		try {
			if (conn == null) {
				conn = SqlConnectionJdbc.getConnection();
				conn.setAutoCommit(false);
			}

			String codRetorno = null;
			String descRetorno = null;
			String localErroAviso = ".";
			String vlrTotalBaseRet = "0";
			String vlrCRTom = "0";

			File fileName = new File(vArq);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(fileName);
			NodeList nodeList = document.getElementsByTagName("retornoEventos");
			document.getDocumentElement().normalize();
			br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.retornoLoteEventos.Reinf retornoLoteEventos = XmlUtil.xmlToObject(retSefaz, br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.retornoLoteEventos.Reinf.class);
			String status = retornoLoteEventos.getRetornoLoteEventos().getStatus().getDescRetorno();
			if (status.equals("SUCESSO")) {
				List<TArquivoReinf> evento = retornoLoteEventos.getRetornoLoteEventos().getRetornoEventos().getEvento();
				if (evento != null) {
					for (TArquivoReinf detEvento : evento) {
						if (detEvento != null) {
							for (int index = 0; index < nodeList.getLength(); index++) {
								Node noRetornoEvento = nodeList.item(index);
								NodeList nosListEventos = noRetornoEvento.getChildNodes();
								for (int j = 0; j < nosListEventos.getLength(); j++) {
									Node noEvento = nosListEventos.item(j);						
									Node noReinf = getNodeFilho(noEvento, "Reinf");
									Node noEvtTotal = getNodeFilho(noReinf, "evtTotal");

									Node noIdeRecRetorno = getNodeFilho(noEvtTotal, "ideRecRetorno");
									Node noIdeStatus = getNodeFilho(noIdeRecRetorno, "ideStatus");
									codRetorno = getNodeValor(noIdeStatus, "cdRetorno");
									descRetorno = getNodeValor(noIdeStatus, "descRetorno");
									if (descRetorno.equals("SUCESSO") || descRetorno.equals("EM PROCESSAMENTO")) {
										String numProtocolo = "0";
										Node noInfoRecEv = getNodeFilho(noEvtTotal, "infoRecEv");
										String dataProcess  = getNodeValor(noInfoRecEv, "dhProcess");														
										String dhProcess    = utilsBridge.formatarDataBr(dataProcess);
										String codigoEvento = getNodeValor(noInfoRecEv, "tpEv");
										numLote             = getNodeValor(noInfoRecEv, "idEv");
										String hash         = getNodeValor(noInfoRecEv, "hash");	

										Node noInfoTotal = getNodeFilho(noEvtTotal, "infoTotal");
										if (codigoEvento.equals("2010")) {
											numProtocolo = getNodeValor(noInfoTotal, "nrRecArqBase");
											Node noRTom = getNodeFilho(noInfoTotal, "RTom");
											String cnpjPrestador = getNodeValor(noRTom, "cnpjPrestador");
											vlrTotalBaseRet      = getNodeValor(noRTom, "vlrTotalBaseRet");

											Node noInfoCRTom = getNodeFilho(noRTom, "infoCRTom");
											vlrCRTom = getNodeValor(noInfoCRTom, "VlrCRTom");
											//	reinfLotesDAO.selecionaIdLoteEfd(conn, numLote);
										} else {
											if (codigoEvento.equals("2099")) {
												numProtocolo = getNodeValor(noInfoRecEv, "nrProtEntr");
											} else {
												numProtocolo = getNodeValor(noInfoTotal, "nrRecArqBase");
											} // codigoEvento.equals("2010")
										} // codigoEvento.equals("2099")
										String statusReinf = "AUTORIZADO_RECEITA";
										String codResp = "MS0000";
										String statusEfd = null;
										String tagRaiz = null;

										if (codigoEvento.equals("2099")) {
											statusEfd = "RECEPCIONADO_RECEITA_SUCESSO";
											tagRaiz = "ReinfReceitaFechamento";
										} else {
											if (codigoEvento.equals("9000")) {
												statusEfd = "EXCLUIDO_LOTE_RECEITA_WEB_SERVICES";
												tagRaiz = "ReinfExclusao";
											} else {
												statusEfd = "RECEPCIONADO_RECEITA_SUCESSO";
												tagRaiz = "ReinfReceita";
											} // codigoEvento.equals("9000"
										} // codigoEvento.equals("2099"


										Long idLoteEfd = reinfLotesDAO.selecionaIdLoteEfd(conn, numLote);
										Long idMensagem = reinfLotesDAO.retornaIdMensagem(conn, codResp);
										salvaReinfFinal(conn, facade, idReinf, statusReinf, statusEfd, tagRaiz, codigoEvento, numLote, numProtocolo, dhProcess, idMensagem, localErroAviso, descRetorno, idPeriodoApuracao, idLoteEfd, vlrTotalBaseRet, vlrCRTom);
									} else {
										if (descRetorno.equals("ERRO")) {
											Node noRegOcorrss = getNodeFilho(noIdeStatus, "regOcorrs");
											String tpOcorr = getNodeValor(noRegOcorrss, "tpOcorr");
											localErroAviso = getNodeValor(noRegOcorrss, "localErroAviso");
											String codResp = getNodeValor(noRegOcorrss, "codResp");
											String dscResp = getNodeValor(noRegOcorrss, "dscResp");

											Node noInfoRecEv = getNodeFilho(noEvtTotal, "infoRecEv");
											String dataProcess = getNodeValor(noInfoRecEv, "dhProcess");														
											String dhProcess = utilsBridge.formatarDataBr(dataProcess);
											String codigoEvento = getNodeValor(noInfoRecEv, "tpEv");
											numLote = getNodeValor(noInfoRecEv, "idEv");
											String hash = getNodeValor(noInfoRecEv, "hash");	

											String statusEfd = null;
											String tagRaiz = null;
											String numProtocolo = "0";
											String statusReinf = "ERRO_RECEITA";
											if (codigoEvento.equals("9000")) {
												statusEfd = "EXCLUIDO_LOTE_RECEITA_WEB_SERVICES";
												tagRaiz = "ReinfExclusao";
											} else {
												statusEfd = "RECEPCIONADO_RECEITA_ERRO";
												tagRaiz = "ReinfReceita";
											} // codigoEvento.equals("9000"

											Long idLoteEfd = reinfLotesDAO.selecionaIdLoteEfd(conn, numLote);
											Long idMensagem = reinfLotesDAO.retornaIdMensagem(conn, codResp);											
											salvaReinfFinal(conn, facade, idReinf, statusReinf, statusEfd, tagRaiz, codigoEvento, numLote, numProtocolo , dhProcess, idMensagem, localErroAviso, descRetorno, idPeriodoApuracao, idLoteEfd, vlrTotalBaseRet, vlrCRTom);


										} // descRetorno.equals("ERRO")										
									} // descRetorno.equals("SUCESSO")
								} // for (int j = 0; j < nosListEventos.getLength(); j++)
							} // for (int index = 0; index < nodeList.getLength(); index++)
						} // if (detEvento != null) {
					} // for (TArquivoReinf detEvento : evento)
				} // evento != null
			} else {
				if (status.equals("REJEITADO")) {

				} else {
					if (status.equals("ERRO")) {

					} // status.equals("ERRO")
				} // status.equals("REJEITADO")
			} // status.equals("SUCESSO")
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fechaSql(conn, pstmt, rs);
		}
		return retLeitura;
	}



	private static String strValueLoteReinf(Reinf reinf, String verProc) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[] { br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.envioLoteEventos.Reinf.class });
		Marshaller marshaller = context.createMarshaller();
		JAXBElement element = new br.com.pinhoinformatica.pinhoNfeCert.jabx.refinf.envioLoteEventos.ObjectFactory().createLoteReinf(reinf);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		marshaller.marshal(element, sw);

		String xml = sw.toString();

		xml = xml.replaceAll("<Reinf xmlns:ns2=\"http://www.reinf.esocial.gov.br/schemas/envioLoteEventos/v1_03_02\">", "<Reinf xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.reinf.esocial.gov.br/schemas/envioLoteEventos/" + verProc + "\">");
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");
		xml = xml.replaceAll("<Reinf:Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/v1_03_02\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:Reinf=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/v1_03_02\">", "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + verProc + "\">");
		xml = xml.replaceAll("</Reinf:Reinf>", "</Reinf>");
		xml = xml.replaceAll("<Reinf:Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtTomadorServicos/v1_03_02\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:Reinf=\"http://www.reinf.esocial.gov.br/schemas/evtTomadorServicos/v1_03_02\">", "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtTomadorServicos/" + verProc + "\">");
		xml = xml.replaceAll("<Reinf:Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtFechamento/v1_03_02\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:Reinf=\"http://www.reinf.esocial.gov.br/schemas/evtFechamento/v1_03_02\">", "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtFechamento/" + verProc + "\">");
		xml = xml.replaceAll("<Reinf:Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtExclusao/v1_03_02\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:Reinf=\"http://www.reinf.esocial.gov.br/schemas/evtExclusao/v1_03_02\">", "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtExclusao/" + verProc + "\">");
		xml = xml.replaceAll("<Reinf:Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtReabreEvPer/v1_03_02\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:Reinf=\"http://www.reinf.esocial.gov.br/schemas/evtReabreEvPer/v1_03_02\">", "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtReabreEvPer/" + verProc + "\">");

		xml = xml.replaceAll("<Signature>", "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">");
		xml = xml.replaceAll("<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/envioLoteEventos/v1_03_02\">", "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/envioLoteEventos/" + verProc + "\">");
		return xml;
	}

	private Node getNodeFilho(Node node, String tag) {
		for (int index = 0; index < node.getChildNodes().getLength(); index++) {
			Node filho = node.getChildNodes().item(index);
			if (tag.equals(filho.getNodeName())) {
				return filho;			
			}
		}
		return null;
	}

	private String getNodeValor(Node node, String tag) {
		for (int index = 0; index < node.getChildNodes().getLength(); index++) {
			Node filho = node.getChildNodes().item(index);
			if (tag.equals(filho.getNodeName())) {
				return filho.getTextContent();			
			}
		}
		return null;
	}

}
