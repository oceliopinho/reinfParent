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

	public Long retornaIdLoteReinf(Connection conn, String numLote, String cnpjPrestador, String periodoApuracao) throws SQLException {
		Long ret = (long) 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query ="";

		query = " Select l.id_reinf_lotes From reinf.tb_reinf_lotes l where l.num_lote = '" + numLote + "' and cnpj_prestador = '" + cnpjPrestador + "' and periodo_apuracao = '" + periodoApuracao + "' " ;
		pstmt = conn.prepareStatement(query);

		rs = pstmt.executeQuery();
		while (rs.next()) {
			ret = rs.getLong("id_reinf_lotes");				
		}

		if (ret == 0) {
			query = " Select reinf.SE_REINF_LOTES.nextval as num from dual " ;
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

	public int retornaQtdeLotes(Connection conn, Long idCnpjRaiz, Long idPeriodoApuracao) throws SQLException {
		int cod = 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = " Select count(cnpj_prestador) cnpj_prestador ";
		sql1       += " From ( ";
		sql1       += "       Select distinct st.cnpj_prestador ";
		sql1       += "       From controladoria.tb_r2010_servicos_tomados st ";
		sql1       += " 	  Where st.id_cnpj_raiz_oracle = " + idCnpjRaiz + " ";
		sql1       += "       and   st.id_periodo_apuracao_efd_reinf = " + idPeriodoApuracao + ") ";  

		pstmt = conn.prepareStatement(sql1);
		rs = pstmt.executeQuery();
		pstmt = conn.prepareStatement(sql1);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			cod = rs.getInt("cnpj_prestador");				
		}
		pstmt.close();
		return cod;
	}

	public Long confReinfLote(Connection conn, String numLote, String cnpjPrestador, String strPerApur) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long ret = (long) 0;
		try {
			String 	sql1 = " select l.id_reinf_lotes From reinf.reinf.tb_reinf_lotes l where l.num_lote = '" + numLote + "' and cnpj_prestador = '" + cnpjPrestador + "' and periodo_apuracao = '" + strPerApur + "' "; 
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("id_reinf_lotes");				
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {

		}		
		return ret;
	}

	public Long confReinfEvento(Connection conn, Long idReinf, String IdLoteRaiz) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long ret = (long) 0;
		try {
			String 	sql1 = " Select re.id_reinf_eventos "; 
			sql1        += " From tb_reinf_eventos re "; 
			sql1        += " Where re.reinf_lotes = " + idReinf + " "; 
			sql1        += " and   re.lote_evento = '" + IdLoteRaiz + "' "; 
			//			sql1        += " and   re.nome_evento = '" + tagRaiz + "' "; 
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("id_reinf_eventos");				
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



	public Long confReinfArquivo(Connection conn, String vArq) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long ret = (long) 0;
		File fileXml = new File(vArq);
		try {
			String nomeArquivo = fileXml.getName();
			String 	sql1 = " Select id "; 
			sql1        += " From tb_reinf_arquivo ra "; 
			sql1        += " Where ra.nome = '" + nomeArquivo + "' "; 
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("id");				
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


	public String updateReinfEventos(Connection conn, IAppFacade facade, String statusReinf, Long confEvento) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " Update tb_reinf_eventos e Set e.status_reinf_evento = '" + statusReinf + "', data_ult_alteracao = sysdate, versao = versao+1 Where id_reinf_eventos = " + confEvento + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;

			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventos ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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

	public String updateReinfLotes(Connection conn, IAppFacade facade, Long idReinf, String statusReinf) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " Update reinf.tb_reinf_lotes Set status_reinf = '" + statusReinf + "', data_ult_alteracao = sysdate, versao = versao+1 Where id_reinf_lotes = " + idReinf + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;

			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfLotes ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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

	public String updateReinfEventosValidacao(Connection conn, IAppFacade facade, String statusReinf, Long confEvento, String retValidacao) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " Update tb_reinf_eventos e Set e.status_reinf_evento = '" + statusReinf + "', data_ult_alteracao = sysdate, versao = versao+1, substr(desc_retorno = '" + retValidacao + "'),1,99) Where id_reinf_eventos = " + confEvento + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;

			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventosValidacao ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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

	public String inserirReinfEventos(Connection conn, IAppFacade facade, Long idEventoReinf, Long idReinf, String IdLoteRaiz, String statusReinf, String nomeArquivo, String nomeEvento, Integer seqChave, String codigoEvento, Long idLoteEfd, Long idServicosTomados) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " insert into tb_reinf_eventos (id_reinf_eventos, reinf_lotes, lote_evento, nome_arquivo, status_reinf_evento, nome_evento, seq_evento, tp_ev, id_eventos_controladoria, id_tomador_servico) values (" + idEventoReinf + ", " + idReinf + ", '" + IdLoteRaiz + "', '" + nomeArquivo + "', '" + statusReinf + "', '" + nomeEvento + "', " + seqChave + ", '" + codigoEvento + "', " + idLoteEfd + ", " + idServicosTomados + ") ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;

			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro inserirReinfEventos ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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

	public Long retornaIdEvento(Connection conn) throws SQLException {
		Long ret = (long) 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = " Select SE_REINF_EVENTOS.nextval as num from dual " ;
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			ret = rs.getLong("num");		
		}
		pstmt.close();
		rs.close();
		return ret;
	}

	public String inserirReinfEventosRetorno(Connection conn, IAppFacade facade, Long confEvento) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " Insert into tb_reinf_eventos_retorno(id_reinf_eventos_retorno, reinf_eventos, reinf_lotes_mensagens, localizacao_erro_aviso) values (se_reinf_eventos_retorno.nextval, " + confEvento + ", 1, '.') ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro inserirReinfEventosRetorno ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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

	public String inserirReinfLotes(Connection conn, IAppFacade facade, Long idReinf, String cnpjRaiz, String numLote, String statusReinf, String cnpjPrestador, String strPerApur) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " insert into reinf.tb_reinf_lotes(id_reinf_lotes, cnpj_raiz, data_geracao, num_lote, status_reinf, cnpj_prestador, periodo_apuracao) values (" + idReinf + ", '" + cnpjRaiz + "', sysdate, '" + numLote + "', '" + statusReinf + "', '" + cnpjPrestador + "', '" + strPerApur + "') ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;

			pstmt.executeUpdate(sqlInsert); 			
		} catch (Exception e) {
			String subject = "Erro inserirReinfLotes ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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

	public Long selIdArquivo(Connection conn) throws SQLException {

		Long cod = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "Select se_reinf_arquivo_plc.nextval as seq from dual" ;
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			cod = rs.getLong("seq");				
		}

		pstmt.close();
		return cod;
	}

	public Long selIdArquivoConteudo(Connection conn) throws SQLException {

		Long cod = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "Select se_reinf_arquivo_conteudo_plc.nextval as seq from dual" ;
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			cod = rs.getLong("seq");				
		}

		pstmt.close();
		rs.close();
		return cod;
	}

	public void insertArquivoConteudo(Long IdArquivoConteudo, String vArq, Connection conn) throws SQLException, FileNotFoundException {

		PreparedStatement pstmt = null;	
		FileInputStream fis;
		File fileXml = new File(vArq);

		int tamImagem = (int) fileXml.length();
		fis = new FileInputStream(fileXml);

		String query = "insert into tb_reinf_arquivo_conteudo(id, conteudo_binario) values (?, ?) " ;
		pstmt = conn.prepareStatement(query);
		pstmt.setLong(1, IdArquivoConteudo);
		pstmt.setBinaryStream(2, fis, tamImagem);
		pstmt.executeUpdate();			
		pstmt.close();
	}

	public void insertArquivo(Long idArquivo, String vArq, String tipo, String usu_seguranca, Long IdArquivoConteudo, Connection conn) throws SQLException {

		PreparedStatement pstmt = null;
		FileInputStream fis = null;
		File fileXml = new File(vArq);

		String query = "insert into tb_reinf_arquivo(id, tamanho, nome, tipo, usu_seguranca, versao, binary_content) values (?, ?, ?, ?, ?, ?, ?) " ;

		pstmt = conn.prepareStatement(query);

		int tamArq = (int) fileXml.length();

		pstmt.setLong(1, idArquivo);
		pstmt.setInt(2, tamArq);
		pstmt.setString(3, fileXml.getName());
		pstmt.setString(4, tipo);
		pstmt.setString(5, usu_seguranca);
		pstmt.setInt(6, 0);
		pstmt.setLong(7, IdArquivoConteudo);		
		pstmt.executeUpdate();
		pstmt.close();
	}


	public void insertReinfLoteArquivo(Long idReinf, Long idArquivo, Connection conn) throws SQLException {

		Long cod = null;		
		PreparedStatement pstmt = null;

		String query = "insert into reinf.tb_reinf_lotes_reinf_arquivo(reinf.tb_reinf_lotes, reinf_arquivo) values (?, ?) " ;
		pstmt = conn.prepareStatement(query);
		pstmt.setLong(1, idReinf);
		pstmt.setLong(2, idArquivo);
		pstmt.executeUpdate();
		pstmt.close();			
		pstmt.close();
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
	
	public String updateReinfEventosErros(Connection conn, IAppFacade facade, Long idReinf, String numProtocolo, String statusReinf, String descRetorno) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " update reinf.tb_reinf_eventos re Set re.status_reinf_evento = '" + statusReinf + "', re.status_retorno = '" + descRetorno + "', re.num_protocolo = '" + numProtocolo + "' Where re.reinf_lotes = " + idReinf + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventosRetornoErros";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	public String updateLotesControladoriaErroRetorno(Connection conn, IAppFacade facade, String statusEfd, String numProtocolo, Long idCnpjRaiz, Long idPeriodoApuracao, Long idReinf) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		String sqlInsert = "";
		try {
			conn.setAutoCommit(false);
			sqlInsert = " Update controladoria.tb_lote_eventos_efd_reinf leer ";
			sqlInsert       += " Set leer.data_retorno_receita = to_date(sysdate,'dd/MM/yyyy hh24:mi:ss'), ";
			sqlInsert       += "     leer.usuario_retorno_receita = 'tbmWebServices', ";
			sqlInsert       += "     leer.status = '" + statusEfd + "',  ";
			sqlInsert       += "     leer.numero_recibo = '" + numProtocolo + "' ";
			sqlInsert       += " Where leer.id_cnpj_raiz_oracle = " + idCnpjRaiz + " ";
			sqlInsert       += " and   leer.id_periodo_apuracao_efd_reinf = " + idPeriodoApuracao + " ";
			sqlInsert       += " and   leer.id_lote_eventos_efd_reinf in(Select re.id_eventos_controladoria From tb_reinf_eventos re Where re.reinf_lotes = " + idReinf + " and id_eventos_controladoria > 0) ";

			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateLotesControladoriaErroRetorno";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;	
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	
	public Long selecionaIdLoteEfd(Connection conn, String numLote) throws SQLException {
		Long cod = (long) 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = " Select re.id_eventos_controladoria seq ";
		sql1       += " From reinf.tb_reinf_eventos re ";
		sql1       += " Where re.lote_evento = '" + numLote + "' ";
		pstmt = conn.prepareStatement(sql1);
		rs = pstmt.executeQuery();
		pstmt = conn.prepareStatement(sql1);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			cod = rs.getLong("seq");				
		}
		pstmt.close();
		return cod;
	}	
	
	public Long retornaIdMensagem(Connection conn, String codResp) throws SQLException {
		Long ret = (long) 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query ="";

		query = " Select m.id_reinf_mensagens From tb_reinf_mensagens m Where m.codigo_mensagem = '" + codResp + "' " ;
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			ret = rs.getLong("id_reinf_mensagens");				
		}			
		pstmt.close();
		rs.close();
		return ret;
	}	
	
	public Long confReinfEventoRetorno(Connection conn, Long idReinf, String numLote) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long ret = (long) 0;
		try {
			String 	sql1 = " Select re.id_reinf_eventos "; 
			sql1        += " From reinf.tb_reinf_eventos re "; 
			sql1        += " Where re.reinf_lotes = " + idReinf + " "; 
			sql1        += " and   re.lote_evento = '" + numLote + "' "; 
			pstmt = conn.prepareStatement(sql1);
			System.out.println("confReinfEventoRetorno" + sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getLong("id_reinf_eventos");				
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
	
	public String updateReinfEventosReceita(Connection conn, IAppFacade facade, String statusReinf, String tpEv, String idEv, String dhProcess, String descRetorno, String numProtocolo) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " update reinf.tb_reinf_eventos re Set tp_ev = '" + tpEv + "', dh_process = '" + dhProcess + "', re.status_retorno = '" + descRetorno + "', re.num_protocolo = '" + numProtocolo + "', re.status_reinf_evento = '" + statusReinf + "' Where lote_evento = '" + idEv + "' and nome_evento <> 'Reinf' ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventosReceita";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	public String updateReinfEventosRetornoReceita(Connection conn, IAppFacade facade, Long confEventoRetorno, Long idMensagem, String localErroAviso) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " update reinf.tb_reinf_eventos_retorno set reinf_lotes_mensagens = " + idMensagem + ", localizacao_erro_aviso = '" + localErroAviso + "' Where reinf_eventos = " + confEventoRetorno + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateReinfEventosRetornoReceita";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	public String updateLotesControladoriaRetorno(Connection conn, IAppFacade facade, Long idEventoReinf, String numProtocolo, String statusEfd, String dhProcess, String idEv, Long idLoteEfd) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		String sqlInsert = "";
		try {
			conn.setAutoCommit(false);
			if (dhProcess == null) {
				sqlInsert = " update controladoria.tb_lote_eventos_efd_reinf leer Set leer.data_retorno_receita = sysdate, leer.usuario_retorno_receita = 'tbmWebServices', leer.status = '" + statusEfd + "', leer.numero_recibo = '" + numProtocolo + "', leer.id_reinf_eventos_ws_tbm = " + idEventoReinf + " Where leer.id_lote_eventos_efd_reinf = " + idLoteEfd + " ";
			} else {
				sqlInsert = " update controladoria.tb_lote_eventos_efd_reinf leer Set leer.data_retorno_receita = to_date('" + dhProcess + "','dd/MM/yyyy hh24:mi:ss'), leer.usuario_retorno_receita = 'tbmWebServices', leer.status = '" + statusEfd + "', leer.numero_recibo = '" + numProtocolo + "', leer.id_reinf_eventos_ws_tbm = " + idEventoReinf + " Where leer.id_lote_eventos_efd_reinf = " + idLoteEfd + " ";
			}

			pstmt = conn.prepareStatement(sqlInsert);
			
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateLotesControladoriaRetorno ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;	
			System.out.println(bodyMail);
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	public String updatePeriodoControladoria(Connection conn, IAppFacade facade, Long idPeriodoApuracao) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " update controladoria.tb_periodo_apuracao_efd_reinf paer set paer.status = 'Fechado' Where paer.id_periodo_apuracao_efd_reinf = " + idPeriodoApuracao + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updatePeriodoControladoria ";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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
	
	public Long selecionaIdServicosTomados(Connection conn, String numLote) throws SQLException {
		Long cod = (long) 0;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = " Select re.id_tomador_servico seq ";
		sql1       += " From tb_reinf_eventos re ";
		sql1       += " Where re.lote_evento = '" + numLote + "' ";
		pstmt = conn.prepareStatement(sql1);
		rs = pstmt.executeQuery();
		pstmt = conn.prepareStatement(sql1);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			cod = rs.getLong("seq");				
		}
		pstmt.close();
		return cod;
	}
	
	public String updateServicosTomadosReceita(Connection conn, IAppFacade facade, Long idServicosTomados, String statusEfd, String numProtocolo, String dhProcess, String vlrTotalBaseRet, String vlrCRTom) throws Exception {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlErro = null;
		try {
			conn.setAutoCommit(false);
			String sqlInsert = " update controladoria.tb_r2010_servicos_tomados st set st.status_recibo_receita = '" + statusEfd + "',  st.nr_recibo = '" + numProtocolo + "', data_recibo_receita = to_date('" + dhProcess + "','dd/MM/yyyy hh24:mi:ss'), valor_base_retorno_receita = '" + vlrTotalBaseRet + "', valor_imposto_retorno_receita = '" + vlrCRTom + "' Where id_r2010_servicos_tomados = " + idServicosTomados + " ";
			pstmt = conn.prepareStatement(sqlInsert);
			sqlErro = sqlInsert;
			
			pstmt.executeUpdate(sqlInsert); 				
		} catch (Exception e) {
			String subject = "Erro updateServicosTomadosReceita";
			String bodyMail = sqlErro + " - " + e.getMessage();
			Boolean emailAttachment = false;			
			facade.enviarEmailException(subject, bodyMail, emailAttachment);
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