package br.com.pinhoinformatica.reinf.persistence.jpa.reinflotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.powerlogic.jcompany.commons.PlcBaseContextVO;
import com.powerlogic.jcompany.commons.annotation.PlcAggregationDAOIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcDataAccessObject;
import com.powerlogic.jcompany.persistence.jpa.PlcQuery;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryFirstLine;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryLineAmount;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryOrderBy;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryService;

import br.com.pinhoinformatica.reinf.entity.dominio.StatusLoteReinf;
import br.com.pinhoinformatica.reinf.entity.reinf.CnpjEmpresaRaizReinf;
import br.com.pinhoinformatica.reinf.entity.reinf.PeriodoApuracaoReinf;
import br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEntity;
import br.com.pinhoinformatica.reinf.facade.IAppFacade;
import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
/**
 * Classe de Persistência gerada pelo assistente
 */

@PlcAggregationDAOIoC(ReinfLotesEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class ReinfLotesDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<ReinfLotesEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   

			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="cnpjEmpresaRaizReinf", expression="obj1 = :cnpjEmpresaRaizReinf") CnpjEmpresaRaizReinf cnpjEmpresaRaizReinf,
			@PlcQueryParameter(name="statusLoteReinf", expression="obj.statusLoteReinf = :statusLoteReinf") StatusLoteReinf statusLoteReinf,
			@PlcQueryParameter(name="periodoApuracaoReinf", expression="obj2 = :periodoApuracaoReinf") PeriodoApuracaoReinf periodoApuracaoReinf,
			@PlcQueryParameter(name="cnpjPrestador", expression="obj.cnpjPrestador like :cnpjPrestador || '%' ") String cnpjPrestador
			);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,

			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="cnpjEmpresaRaizReinf", expression="obj1 = :cnpjEmpresaRaizReinf") CnpjEmpresaRaizReinf cnpjEmpresaRaizReinf,
			@PlcQueryParameter(name="statusLoteReinf", expression="obj.statusLoteReinf = :statusLoteReinf") StatusLoteReinf statusLoteReinf,
			@PlcQueryParameter(name="periodoApuracaoReinf", expression="obj2 = :periodoApuracaoReinf") PeriodoApuracaoReinf periodoApuracaoReinf,
			@PlcQueryParameter(name="cnpjPrestador", expression="obj.cnpjPrestador like :cnpjPrestador || '%' ") String cnpjPrestador
			);

	public Long retornaIdLoteReinf(Connection conn, String numLote, String cnpjPrestador, Long idPeriodoApuracao) throws SQLException {
		Long ret = (long) 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query ="";

		query = " Select l.id_reinf_lotes From reinf.tb_reinf_lotes l where l.num_lote = '" + numLote + "' and cnpj_prestador = '" + cnpjPrestador + "' and periodo_apuracao_reinf = " + idPeriodoApuracao + " " ;
		pstmt = conn.prepareStatement(query);

		rs = pstmt.executeQuery();
		while (rs.next()) {
			ret = rs.getLong("id_reinf_lotes");				
		}

		if (ret == 0) {
			query = " Select nextval('reinf.se_reinf_lotes') as num " ;
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("num");				
			}
		}
		pstmt.close();
		rs.close();
		return ret;
	}
	
	public Long retornaIdEvento(Connection conn) throws SQLException {
		Long ret = (long) 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query ="";

		query = " Select nextval('reinf.se_reinf_lotes_eventos') as num " ;
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			ret = rs.getLong("num");				
		}		
		pstmt.close();
		rs.close();
		return ret;
	}
	
	public String confGerarRegistro(Connection conn, Long idCnpjRaiz, Long idPeriodoApuracao, String registro) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ret = "N";
		try {
			String 	sql1 = " Select rler.evento "; 
			sql1        += " From reinf.tb_reinf_lotes_efd_receita rler "; 
			sql1        += " Where rler.cnpj_empresa_raiz_reinf = " + idCnpjRaiz + " "; 
			sql1        += " and   rler.periodo_apuracao_reinf = " + idPeriodoApuracao + " "; 
			sql1        += " and   rler.evento = '" + registro + "' ";
			sql1        += " and   rler.status_lote_reinf in('EWS') "; 
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = "S";				
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public String selecionaHoraGeracao(Connection conn) throws SQLException {
		String cod = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "Select to_char(current_timestamp, 'HH24:MI:SS') as horaGeracao" ;
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			cod = rs.getString("horaGeracao");				
		}

		pstmt.close();
		return cod;
	}
	
	public int retornaQtdeLotes(Connection conn, Long idCnpjRaiz, Long idPeriodoApuracao) throws SQLException {
		int cod = 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = " Select distinct count(cnpj_prestador)  as cnpj_prestador ";
		sql1       += "       From reinf.tb_servicos_tomados st ";
		sql1       += " 	  Where st.empresa_raiz_cnpj = " + idCnpjRaiz + " ";
		sql1       += "       and   st.periodo_apuracao_reinf = " + idPeriodoApuracao + " ";  
		pstmt = conn.prepareStatement(sql1);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			cod = rs.getInt("cnpj_prestador");				
		}
		pstmt.close();
		return cod;
	}

	public String updateReinfEventos(Connection conn, IAppFacade facade, String statusReinf, Long confEvento) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " Update reinf.tb_reinf_lotes_eventos e Set e.status_reinf_evento = '" + statusReinf + "', data_ult_alteracao = sysdate, versao = versao+1 Where id_reinf_lotes_eventos = " + confEvento + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventos ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
		//	facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	public Long confReinfLote(Connection conn, String numLote, String cnpjPrestador, String strPerApur) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long ret = (long) 0;
		try {
			String sql1 = " Select rl.id_reinf_lotes ";
			sql1       += " From reinf.tb_reinf_lotes rl, ";
			sql1       += "      reinf.tb_periodo_apuracao_reinf par ";
			sql1       += " Where rl.num_lote = '" + numLote + "' "; 
			sql1       += " and   cnpj_prestador = '" + cnpjPrestador + "' "; 
			sql1       += " and   per_apur = '" + strPerApur + "' ";
			sql1       += " and   par.id_periodo_apuracao_reinf = periodo_apuracao_reinf ";			
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("id_reinf_lotes");				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ret;
	}
	

	public Long confReinfEventoLote(Connection conn, String IdLoteRaiz) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long ret = (long) 0;
		try {
			String 	sql1 = " select le.id_reinf_lotes_eventos from reinf.tb_reinf_lotes_eventos le Where le.lote_evento = '" + IdLoteRaiz + "' "; 
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("id_reinf_lotes_eventos");				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}
		
		return ret;
	}	
	
	public Long confReinfArquivo(Connection conn, String vArq) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long ret = (long) 0;
		File fileXml = new File(vArq);
		try {
			String nomeArquivo = fileXml.getName();
			String 	sql1 = " Select id "; 
			sql1        += " From reinf.tb_reinf_arquivo ra "; 
			sql1        += " Where ra.nome = '" + nomeArquivo + "' "; 
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("id");				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	public String inserirReinfEvento(Connection conn, IAppFacade facade, Long idEventoReinf, Long idReinf, String statusReinf, String loteEvento, String codigoEvento, String nomeEvento, Integer seqChave, String nomeArquivo, Long idServicosTomados) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " INSERT INTO reinf.tb_reinf_lotes_eventos(id_reinf_lotes_eventos, reinf_lotes, status_reinf_evento, lote_evento, codigo_evento, nome_evento, seq_evento, nome_arquivo, id_servicos_tomados) VALUES ( " + idEventoReinf + ", " + idReinf + ", '" + statusReinf + "', '" + loteEvento + "', '" + codigoEvento + "', '" + nomeEvento + "', " + seqChave + ", '" + nomeArquivo + "', " + idServicosTomados + ") ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventos ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
		//	facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	public String inserirReinfLotes(Connection conn, IAppFacade facade, Long idReinf, Long idCnpjRaiz, Long idPeriodoApuracao, String statusReinf, Integer tipoAmbiente, String loteEvento, String cnpjRaiz) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " INSERT INTO reinf.tb_reinf_lotes(id_reinf_lotes, cnpj_empresa_raiz_reinf, periodo_apuracao_reinf   , status_lote_reinf    , tipo_ambiente, num_lote, cnpj_prestador, data_geracao) VALUES (" + idReinf + ", " + idCnpjRaiz + "    , " + idPeriodoApuracao + ", '" + statusReinf + "', " + tipoAmbiente + ", '" + loteEvento + "', '" + cnpjRaiz + "', to_char(current_date,'dd/MM/yyyy') ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventos ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
		//	facade.enviarEmailException(subject, bodyMail, emailAttachment);
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