package br.com.pinhoinformatica.reinf.model.repository;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import br.com.pinhoinformatica.pinhoNfeCert.model.run.Assinar;
import br.com.pinhoinformatica.pinhoNfeCert.model.run.Validar;
import br.com.pinhoinformatica.reinf.facade.IAppFacade;
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
	private static String versaoReinf = "v1_03_02";
	
	
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
			sql1 += " Where erc.id_empresa_raiz_cnpj = ic.empresa_raiz_cnpj ";  
			sql1 += " and   par.id_periodo_apuracao_reinf = ic.periodo_apuracao_reinf ";
			sql1 += " and   e.empresa_raiz_cnpj = ic.empresa_raiz_cnpj ";
			sql1 += " and   ec.empresa_reinf = e.id ";
			sql1 += " and   rler.evento = '1000' ";
			sql1 += " and   rler.periodo_apuracao_reinf = ic.periodo_apuracao_reinf ";
			sql1 += " and   rler.cnpj_empresa_raiz_reinf = ic.empresa_raiz_cnpj ";
    
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
				

				String pesquisa = "<evtInfoContri id=\"ID\" xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + versaoReinf + "\">";
				String alteracao = "<Reinf xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + versaoReinf + "\"><evtInfoContri id=\"" + Id + "\" xmlns=\"http://www.reinf.esocial.gov.br/schemas/evtInfoContribuinte/" + versaoReinf + "\">";
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

				String arqXml = Assinar.assinaNfe(xml, Assinar.REINF_INFO_CONTRIB, Id);
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


}
