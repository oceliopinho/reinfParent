package br.com.pinhoinformatica.reinf.persistence.jpa.reinflotesefdreceita;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEfdReceitaEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.powerlogic.jcompany.commons.PlcException;
import com.powerlogic.jcompany.commons.annotation.PlcAggregationDAOIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcDataAccessObject;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryService;

/**
 * Classe de Persistência gerada pelo assistente
 */

@PlcAggregationDAOIoC(ReinfLotesEfdReceitaEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class ReinfLotesEfdReceitaDAO extends AppJpaDAO  {
	
	
	public void inserirLoteEfd(Connection conn, String statusLoteEfd, String evento, Long idPeriodo, Long idCnpjRaiz, Long attribute1, String login) {		
		String sql1 = " INSERT INTO reinf.tb_reinf_lotes_efd_receita(id_reinf_lotes_efd_receita, status_lote_reinf, num_lote, evento, periodo_apuracao_reinf, cnpj_empresa_raiz_reinf, attribute1, usuario_criacao, data_criacao, usuario_ult_alteracao) VALUES (nextval('reinf.se_reinf_lotes_efd_receita'), '" + statusLoteEfd + "' ,'0', '" + evento + "', " + idPeriodo + ", " + idCnpjRaiz + ", " + attribute1 + ", '" + login + "', now(), '" + login + "') ";		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
		} catch (PlcException plcE) {
			throw plcE;
		} catch (Exception e) {
			throw new PlcException("ReinfLotesEfdReceitaDAO", "inserirManifestoNfe", e, log,"");
		} finally {
			try {
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
