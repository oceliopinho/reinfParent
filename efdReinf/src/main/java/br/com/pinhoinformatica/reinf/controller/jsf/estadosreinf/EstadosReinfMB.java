package br.com.pinhoinformatica.reinf.controller.jsf.estadosreinf;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


import br.com.pinhoinformatica.reinf.entity.tabelas.EstadosReinfEntity;
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
		entity = br.com.pinhoinformatica.reinf.entity.tabelas.EstadosReinfEntity.class
		
	)
	


@PlcConfigForm (
	
	formPattern=FormPattern.Man,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/consulta/estadosreinf")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("estadosreinf")
@PlcHandleException
public class EstadosReinfMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("estadosreinf")
	public EstadosReinfEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new EstadosReinfEntity();
              this.newEntity();
        }
        return (EstadosReinfEntity)this.entityPlc;     	
	}
		
}
