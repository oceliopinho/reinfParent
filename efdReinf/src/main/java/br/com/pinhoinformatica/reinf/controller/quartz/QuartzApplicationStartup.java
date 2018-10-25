package br.com.pinhoinformatica.reinf.controller.quartz;

import java.net.Inet4Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.pinhoinformatica.bridge.commons.facade.IEmpFacade;
import br.com.pinhoinformatica.bridge.controller.quartz.JobTrigger;
import br.com.pinhoinformatica.reinf.persistence.jpa.util.SqlConnectionJdbc;

import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefaultLiteral;
import com.powerlogic.jcompany.commons.util.cdi.PlcCDIUtil;
import com.powerlogic.jcompany.controller.util.PlcIocControllerFacadeUtil;

public class QuartzApplicationStartup implements ServletContextListener {

	Logger log = Logger.getLogger(this.getClass().getCanonicalName());
	public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";
	private static Class<? extends Job> classeJob = null;
	private StdSchedulerFactory factory = null;
	private String jobCron = null;
	private String nomeServidor = null;
	private String nomeClasse;	

	public void contextDestroyed(ServletContextEvent event) {
		try {
			factory.getDefaultScheduler().shutdown();
		} catch (SchedulerException ex) {
			log.info("catch");
		}
	}


	public void contextInitialized(ServletContextEvent sce) {		
		log.info("------- Initializing ----------------------");
		String grupoNome = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		IEmpFacade facade = null;
		String nomeServidor = null;
		Map<String, JobTrigger> mapJobTrigger = new HashMap<String, JobTrigger>();		
		ServletContext ctx = sce.getServletContext();
		try {
			conn = SqlConnectionJdbc.getConnection();
			conn.setAutoCommit(false);
			nomeServidor = Inet4Address.getLocalHost().getCanonicalHostName();
			String 	sql1  = " update bridge.tb_quartz_cron_aplicacao q set qt_servidor = '" + nomeServidor + "' Where qt_nome_job = 'Teste' ";
			pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

			System.out.println(" S E R V I D O R - " + nomeServidor);

			log.info("------- Scheduling Jobs -------------------");			

			sql1  = "";
			sql1  = " Select q.sit_historico_plc, ";
			sql1 += "        q.qt_nome_job, ";
			sql1 += "        q.qt_cron_scheduler, ";
			sql1 += "        q.qt_servidor, ";
			sql1 += "        q.qt_nome_classe, ";
			sql1 += "        rpad(q.qt_nome_job,30,' ') as nome, ";
			sql1 += "        rpad(q.qt_cron_scheduler,30,' ') as cron, ";
			sql1 += "        qt_nome_grupo, rpad(q.qt_servidor,30,' ') as servidor, ";
			sql1 += "        q.qt_nome_classe  ";
			sql1 += " From bridge.tb_quartz_cron_aplicacao q, ";
			sql1 += "      bridge.tb_aplicacao a ";
			sql1 += " Where q.sit_historico_plc = 'A' ";
			sql1 += " and   a.id_aplicacao = q.aplicacao ";
			sql1 += " and   a.nome_aplicacao = 'efdReinf' ";

			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				       grupoNome   = rs.getString("QT_NOME_GRUPO");
				String jobNome     = rs.getString("QT_NOME_JOB");
				String jobCron     = rs.getString("QT_CRON_SCHEDULER");
				String jobServidor = rs.getString("QT_SERVIDOR");
				String nomeClasse  = rs.getString("QT_NOME_CLASSE");

				if (nomeServidor.equals(jobServidor)) {
					Class cls   = Class.forName(nomeClasse);

					System.out.println(" Nome do Job - " + rs.getString("NOME") + " Cron - " + rs.getString("CRON") + " Servidor - " + rs.getString("SERVIDOR") + " Classe - " + nomeClasse);
					classeJob = (Class<? extends Job>) Class.forName(cls.getName(), true, cls.getClassLoader());
					JobDetail job = JobBuilder.newJob(classeJob).withIdentity(jobNome, grupoNome).build();
					Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobNome, grupoNome).withSchedule(CronScheduleBuilder.cronSchedule(jobCron)).build();
					mapJobTrigger.put(nomeClasse, new JobTrigger((JobDetail) job, trigger));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		try {			
			if (mapJobTrigger.size() > 0) {
				facade = PlcCDIUtil.getInstance().getInstanceByType(PlcIocControllerFacadeUtil.class, QPlcDefaultLiteral.INSTANCE).getFacade(IEmpFacade.class);
				factory = new StdSchedulerFactory();
				ctx.setAttribute(QUARTZ_FACTORY_KEY, factory);
				Scheduler scheduler= factory.getScheduler();
				log.info("------- Initialization Complete -----------");
				scheduler.start();				
				for (String chave : mapJobTrigger.keySet()) {
					JobTrigger objetoAuxiliar = mapJobTrigger.get(chave);
					scheduler.scheduleJob(objetoAuxiliar.getJobDetail(), objetoAuxiliar.getTrigger());
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			String assunto = "Erro Start - QuartzApplicationStartup";
			String msg = " Nome do Servidor " + nomeServidor;
	//		String email = facade.enviaEmailPadrao(assunto, msg);
		}
	}	
}
