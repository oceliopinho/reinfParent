package br.com.pinhoinformatica.reinf.controller.jsf.reinfmensagens;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


import br.com.pinhoinformatica.reinf.entity.reinf.ReinfMensagensEntity;
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
		entity = br.com.pinhoinformatica.reinf.entity.reinf.ReinfMensagensEntity.class
		
	)
	


@PlcConfigForm (
	
	formPattern=FormPattern.Man,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/crud/reinfmensagens")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("reinfmensagens")
@PlcHandleException
public class ReinfMensagensMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("reinfmensagens")
	public ReinfMensagensEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new ReinfMensagensEntity();
              this.newEntity();
        }
        return (ReinfMensagensEntity)this.entityPlc;     	
	}
		
}
