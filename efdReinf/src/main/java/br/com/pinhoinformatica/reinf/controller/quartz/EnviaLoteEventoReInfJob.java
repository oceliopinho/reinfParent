package br.com.pinhoinformatica.reinf.controller.quartz;

import java.net.Inet4Address;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefaultLiteral;
import com.powerlogic.jcompany.commons.util.cdi.PlcCDIUtil;
import com.powerlogic.jcompany.controller.util.PlcIocControllerFacadeUtil;

import br.com.pinhoinformatica.reinf.facade.IAppFacade;

public class EnviaLoteEventoReInfJob implements Job {

	private Logger log = Logger.getLogger(this.getClass().getCanonicalName());	
	private static boolean executando = false;

	public void execute(JobExecutionContext p2) throws JobExecutionException {		
		IAppFacade facade = PlcCDIUtil.getInstance().getInstanceByType(PlcIocControllerFacadeUtil.class, QPlcDefaultLiteral.INSTANCE).getFacade(IAppFacade.class);
		if (!executando) {
			try {	
				executando = true;
				String nomeServidor = Inet4Address.getLocalHost().getCanonicalHostName();
				String login = "ocelio";
				String email = "oceliopinho@gmail.com";
				String ret = facade.enviaLoteEventoReInfSefaz(facade, nomeServidor, email);
				log.info("Finalizou EnviaLoteEventoReInfJob");
			} catch (Exception e) {			
				e.printStackTrace();
			} finally {
				executando = false;
			}
		}
	}
}	