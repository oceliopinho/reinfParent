package br.com.pinhoinformatica.reinf.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;

import br.com.pinhoinformatica.reinf.facade.IAppFacade;
import br.com.pinhoinformatica.reinf.persistence.jpa.periodoapuracaoreinf.PeriodoApuracaoReinfDAO;
import br.com.pinhoinformatica.reinf.persistence.jpa.reinflotesefdreceita.ReinfLotesEfdReceitaDAO;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.FecharConexao;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.SqlConnectionJdbc;

public class PeriodoApuracaoReinfRepository extends FecharConexao {

	@Inject private PeriodoApuracaoReinfDAO periodoApuracaoReinfDAO;
	@Inject private ReinfLotesEfdReceitaDAO lotesEfdReceitaDAO;

	public String criarPeriodoReinf() {
		String ret = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = SqlConnectionJdbc.getConnection();
			conn.setAutoCommit(false);			

			Integer anoVigente = periodoApuracaoReinfDAO.retornaAnoVirgente(conn);
			for (int i = 1; i <= 12; i++) {
				String mes = String.format("%02d", i);
				String strApur = String.valueOf(anoVigente) + "/" + mes;
				System.out.println(strApur);

				Boolean confReinf = periodoApuracaoReinfDAO.confPeridoReinf(conn, strApur);
				if (confReinf == false) {
					String ultimoDia = periodoApuracaoReinfDAO.retornaDataCompetencia(conn, anoVigente, i);
					String dataCompetencia = ultimoDia + "/" + mes + "/"  + String.valueOf(anoVigente);
					periodoApuracaoReinfDAO.inserirStatusPeriodoApuracao(conn, dataCompetencia, strApur, "Fechado");
					conn.commit();
	//			} else {
		//			System.out.println("Periodo já incluido: " + strApur);
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fechaConnection(conn, pstmt, rs);
		}
		return ret;
	}

	public String gerarPeriodoReinf(IAppFacade facade, String login, Long idPeriodo) {
		String ret = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = SqlConnectionJdbc.getConnection();
			conn.setAutoCommit(false);
			
			gerarR1000(conn, facade, login, idPeriodo);
			gerarR2010(conn, facade, login, idPeriodo);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fechaConnection(conn, pstmt, rs);
		}
		return ret;
	}

	private String gerarR1000(Connection conn, IAppFacade facade, String login, Long idPeriodo) {
		String ret = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String evento = "1000";
		try {
			String sql1 = " Select distinct  ";
			sql1       += "        empresa_raiz_cnpj, ";
			sql1       += "        id_informacoes_contribuinte as attribute1 ";
			sql1       += " From reinf.tb_informacoes_contribuinte ic, ";
			sql1       += "      reinf.tb_periodo_apuracao_reinf par ";
			sql1       += " Where ic.id_informacoes_contribuinte not in(Select attribute1 From reinf.tb_reinf_lotes_efd_receita Where cnpj_empresa_raiz_reinf = empresa_raiz_cnpj and attribute1 = id_informacoes_contribuinte) ";
			sql1       += " and   par.id_periodo_apuracao_reinf = " + idPeriodo + " ";
			sql1       += " and   status_apuracao_reinf = 'Aberto' ";
			
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long idCnpjRaiz      = rs.getLong("empresa_raiz_cnpj");
				Long attribute1      = rs.getLong("attribute1");
				String statusLoteEfd = "N";
				
				lotesEfdReceitaDAO.inserirLoteEfd(conn, statusLoteEfd, evento, idPeriodo, idCnpjRaiz, attribute1, login);
				
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fechaSql(conn, pstmt, rs);
		}
		return ret;		
	}
	
	private String gerarR2010(Connection conn, IAppFacade facade, String login, Long idPeriodo) {
		String ret = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String evento = "2010";
		try {
			String sql1 = " Select distinct  ";
			sql1       += "        empresa_raiz_cnpj, ";
			sql1       += "        id_servicos_tomados as attribute1 ";
			sql1       += " From reinf.tb_servicos_tomados st, ";
			sql1       += "      reinf.tb_periodo_apuracao_reinf par ";
			sql1       += " Where st.id_servicos_tomados not in(Select attribute1 From reinf.tb_reinf_lotes_efd_receita Where empresa_reinf = empresa and attribute1 = id_servicos_tomados) ";
			sql1       += " and   par.id_periodo_apuracao_reinf = " + idPeriodo + " ";
			sql1       += " and   status_apuracao_reinf = 'Aberto' ";
			
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long idCnpjRaiz      = rs.getLong("empresa_raiz_cnpj");
				Long attribute1      = rs.getLong("attribute1");
				String statusLoteEfd = "N";
				
				lotesEfdReceitaDAO.inserirLoteEfd(conn, statusLoteEfd, evento, idPeriodo, idCnpjRaiz, attribute1, login);
				periodoApuracaoReinfDAO.updateStatusPeriodoApuracao(conn, idPeriodo, "Gerado");
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fechaSql(conn, pstmt, rs);
		}
		return ret;		
	}

}
