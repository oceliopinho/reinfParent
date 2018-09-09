package br.com.pinhoinformatica.reinf.persistence.jpa.periodoapuracaoreinf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.powerlogic.jcompany.commons.PlcBaseContextVO;
import com.powerlogic.jcompany.commons.PlcException;
import com.powerlogic.jcompany.commons.annotation.PlcAggregationDAOIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcDataAccessObject;
import com.powerlogic.jcompany.persistence.jpa.PlcQuery;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryFirstLine;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryLineAmount;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryOrderBy;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryService;

import br.com.pinhoinformatica.reinf.entity.reinf.PeriodoApuracaoReinfEntity;
import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
/**
 * Classe de Persistência gerada pelo assistente
 */

@PlcAggregationDAOIoC(PeriodoApuracaoReinfEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class PeriodoApuracaoReinfDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<PeriodoApuracaoReinfEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="perApur", expression="perApur like :perApur || '%' ") String perApur
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="perApur", expression="perApur like :perApur || '%' ") String perApur
	);
	
	public Integer retornaAnoVirgente(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = " Select Extract('Year' From Now()) ";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			Integer ret = null;
			while (rs.next()) {
				ret = rs.getInt("date_part");					
			}
			pstmt.close();
			rs.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String retornaDataCompetencia(Connection conn, Integer Ano, Integer Mes) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			if (Mes == 12) {
			sql = " SELECT 31 as date_part ";
			} else {
				sql = " SELECT  EXTRACT(DAY FROM ((" + Ano + "||'/'||(" + Mes + " + 1)||'/01'):: DATE - 1)) ";
			}

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String ret = null;
			while (rs.next()) {
				ret = rs.getString("date_part");					
			}
			pstmt.close();
			rs.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void inserirStatusPeriodoApuracao(Connection conn, String dataCompetencia, String strApur, String status) {		
		String sql1 = " INSERT INTO reinf.tb_periodo_apuracao_reinf(id_periodo_apuracao_reinf, competencia, per_apur, status_apuracao_reinf) VALUES (nextval('reinf.se_periodo_apuracao_reinf'), '" + dataCompetencia + "', '" + strApur + "', '" + status + "'); ";		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
		} catch (PlcException plcE) {
			throw plcE;
		} catch (Exception e) {
			throw new PlcException("PeriodoApuracaoReinfDAO", "inserirStatusPeriodoApuracao", e, log,"");
		} finally {
			try {
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Boolean confPeridoReinf(Connection conn, String strApur) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = " Select id_periodo_apuracao_reinf From reinf.tb_periodo_apuracao_reinf Where per_apur = '" + strApur + "' ";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			Boolean ret = false;
			while (rs.next()) {
				Long teste = rs.getLong("id_periodo_apuracao_reinf");		
				ret = true;
			}
			pstmt.close();
			rs.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateStatusPeriodoApuracao(Connection conn, Long idPeriodo, String status) {		
		String sql1 = " Update reinf.tb_periodo_apuracao_reinf ";
		sql1       += " set status_apuracao_reinf = '" + status + "', ";
		sql1       += "     versao = versao +1 ";
		sql1       += " Where id_periodo_apuracao_reinf = " + idPeriodo + "";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
		} catch (PlcException plcE) {
			throw plcE;
		} catch (Exception e) {
			throw new PlcException("PeriodoApuracaoReinfDAO", "inserirManifestoNfe", e, log,"");
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
