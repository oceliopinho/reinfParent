package br.com.pinhoinformatica.reinf.persistence.jpa.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SqlConnectionJdbc {

	private static final String STR_DRIVER = "org.postgresql.Driver";
	private static DataSource ds;

	private static SqlConnectionJdbc instance = new SqlConnectionJdbc();  


	static {   
		try {   
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/efdReinf");
			Class.forName(STR_DRIVER);   
		}   
		catch(Exception e) {   
			System.out.println("Erro ao carregar driver JDBC: " + STR_DRIVER);   
			e.printStackTrace();   
		}   
	}   

	private SqlConnectionJdbc() {}   

	public static SqlConnectionJdbc getInstance() {   
		return instance;   
	}       

	public static Connection getConnection()   
			throws Exception   
			{   
		return getConnectionManual();   
			}   

	private static Connection getConnectionManual()   
			throws Exception   
			{   
		Connection conn = null;   

		try {			
			conn = ds.getConnection();
		}catch(Exception e) {   
			throw new Exception("Erro ao obter conexÃƒÂ£o via DriverManager: " + conn);   
		}   

		return conn;   
			}   

	public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException   
	{   
		//Fecha o resultset aberto   
		if(rs != null)   
		{     
			rs.close();   
		}   

		//Fecha o preparedstatement aberto   
		if(pstmt != null)   
		{   
			pstmt.close();   
		}   

		//Fecha a conexÃƒÂ£o aberta   
		if(conn != null)   
		{     
			if(!conn.isClosed())   
			{   
				conn.close();   
			}   
		}   
	}
}  





