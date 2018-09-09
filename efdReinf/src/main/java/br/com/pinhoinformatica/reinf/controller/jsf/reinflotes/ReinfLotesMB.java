package br.com.pinhoinformatica.reinf.controller.jsf.reinflotes;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


import br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEntity;
import br.com.pinhoinformatica.reinf.controller.jsf.AppMB;

import com.powerlogic.jcompany.commons.annotation.PlcUriIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcMB;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;
import com.powerlogic.jcompany.config.collaboration.FormPattern;

import com.powerlogic.jcompany.config.collaboration.PlcConfigFormLayout;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm.ExclusionMode;



import com.powerlogic.jcompany.config.aggregation.PlcConfigAggregation;

@PlcConfigAggregation(
		entity = br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEntity.class

		,details = { 		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEventosEntity.class,
								collectionName = "reinfLotesEventos", numNew = 1,onDemand = false,
								subDetail = @com.powerlogic.jcompany.config.aggregation.PlcConfigSubDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEventosRetornoEntity.class,
																collectionName = "reinfLotesEventosRetorno", numNew = 1)
)
			

		}
	)



@PlcConfigForm (
	
	formPattern=FormPattern.Mds,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/mestreDetalhesSub/reinflotes")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("reinflotes")
@PlcHandleException
public class ReinfLotesMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("reinflotes")
	public ReinfLotesEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new ReinfLotesEntity();
              this.newEntity();
        }
        return (ReinfLotesEntity)this.entityPlc;     	
	}
		
}
