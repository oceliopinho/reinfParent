package br.com.pinhoinformatica.reinf.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;

import br.com.pinhoinformatica.reinf.persistence.jpa.periodoapuracaoreinf.PeriodoApuracaoReinfDAO;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.FecharConexao;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.SqlConnectionJdbc;

public class PeriodoApuracaoReinfRepository extends FecharConexao {

	@Inject private PeriodoApuracaoReinfDAO periodoApuracaoReinfDAO;

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
					periodoApuracaoReinfDAO.inserirManifestoNfe(conn, dataCompetencia, strApur, "Fechado");
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


}
