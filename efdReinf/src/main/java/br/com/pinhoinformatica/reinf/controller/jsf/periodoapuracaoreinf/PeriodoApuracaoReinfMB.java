package br.com.pinhoinformatica.reinf.controller.jsf.periodoapuracaoreinf;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.powerlogic.jcompany.commons.annotation.PlcUriIoC;
import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefaultLiteral;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcMB;
import com.powerlogic.jcompany.commons.util.cdi.PlcCDIUtil;
import com.powerlogic.jcompany.config.aggregation.PlcConfigAggregation;
import com.powerlogic.jcompany.config.collaboration.FormPattern;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm;
import com.powerlogic.jcompany.config.collaboration.PlcConfigFormLayout;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;
import com.powerlogic.jcompany.controller.util.PlcIocControllerFacadeUtil;
import com.powerlogic.jcompany.controller.util.PlcMsgUtil;

import br.com.pinhoinformatica.reinf.controller.jsf.AppMB;
import br.com.pinhoinformatica.reinf.entity.reinf.PeriodoApuracaoReinf;
import br.com.pinhoinformatica.reinf.entity.reinf.PeriodoApuracaoReinfEntity;
import br.com.pinhoinformatica.reinf.facade.IAppFacade;

@PlcConfigAggregation(
		entity = br.com.pinhoinformatica.reinf.entity.reinf.PeriodoApuracaoReinfEntity.class
		
	)
	


@PlcConfigForm (
	
	formPattern=FormPattern.Man,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/crud/periodoapuracaoreinf")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("periodoapuracaoreinf")
@PlcHandleException
public class PeriodoApuracaoReinfMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("periodoapuracaoreinf")
	public PeriodoApuracaoReinfEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new PeriodoApuracaoReinfEntity();
              this.newEntity();
        }
        return (PeriodoApuracaoReinfEntity)this.entityPlc;     	
	}
		
	@Override
	public void handleButtonsAccordingFormPattern() {	
		contextUtil.getRequest().setAttribute("criarPeriodo", "S");
		super.handleButtonsAccordingFormPattern();
	}
	
	public void criarPeriodo() {
		try {
			IAppFacade facade = PlcCDIUtil.getInstance().getInstanceByType(PlcIocControllerFacadeUtil.class, QPlcDefaultLiteral.INSTANCE).getFacade(IAppFacade.class);
			msgUtil = PlcCDIUtil.getInstance().getInstanceByType(PlcMsgUtil.class, QPlcDefaultLiteral.INSTANCE);
			PeriodoApuracaoReinfEntity periodoApuracaoReinf = (PeriodoApuracaoReinfEntity) this.getEntityPlc();
			
			String periodoApuracao = periodoApuracaoReinf.getPerApur();
			String ret = facade.criarPeriodoReinf();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
