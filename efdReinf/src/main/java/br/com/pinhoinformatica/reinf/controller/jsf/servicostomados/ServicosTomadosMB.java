package br.com.pinhoinformatica.reinf.controller.jsf.servicostomados;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


import br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosEntity;
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
		entity = br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosEntity.class

		,details = { 		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosNfsEntity.class,
								collectionName = "servicosTomadosNfs", numNew = 1,onDemand = false)
			
			,
		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosProcRetAdEntity.class,
								collectionName = "servicosTomadosProcRetAd", numNew = 1,onDemand = false)
			
			,
		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosProcRetPrEntity.class,
								collectionName = "servicosTomadosProcRetPr", numNew = 1,onDemand = false)
			

		}
	)
	



@PlcConfigForm (
	
	formPattern=FormPattern.Mdt,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/mestreDetalhes/servicostomados")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("servicostomados")
@PlcHandleException
public class ServicosTomadosMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("servicostomados")
	public ServicosTomadosEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new ServicosTomadosEntity();
              this.newEntity();
        }
        return (ServicosTomadosEntity)this.entityPlc;     	
	}
		
}
