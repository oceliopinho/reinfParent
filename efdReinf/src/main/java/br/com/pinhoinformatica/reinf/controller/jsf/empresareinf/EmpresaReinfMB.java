package br.com.pinhoinformatica.reinf.controller.jsf.empresareinf;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


import br.com.pinhoinformatica.reinf.entity.reinf.EmpresaReinfEntity;
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
		entity = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaReinfEntity.class

		,details = { 		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaPopReinfEntity.class,
								collectionName = "empresaPopReinf", numNew = 1,onDemand = false)
			
			,
		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaProxyReinfEntity.class,
								collectionName = "empresaProxyReinf", numNew = 1,onDemand = false)
			
			,
		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaSmtpReinfEntity.class,
								collectionName = "empresaSmtpReinf", numNew = 1,onDemand = false)
		,
		@com.powerlogic.jcompany.config.aggregation.PlcConfigDetail(clazz = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaContatoReinfEntity.class,
		collectionName = "empresaContatoReinf", numNew = 1,onDemand = false)
			

		}
	)
	



@PlcConfigForm (
	
	formPattern=FormPattern.Mdt,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/mestreDetalhes/empresareinf")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("empresareinf")
@PlcHandleException
public class EmpresaReinfMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("empresareinf")
	public EmpresaReinfEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new EmpresaReinfEntity();
              this.newEntity();
        }
        return (EmpresaReinfEntity)this.entityPlc;     	
	}
		
}
