package br.com.pinhoinformatica.reinf.controller.jsf.tipoempresa;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


import br.com.pinhoinformatica.reinf.entity.tabelas.TipoEmpresaEntity;
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
		entity = br.com.pinhoinformatica.reinf.entity.tabelas.TipoEmpresaEntity.class
		
	)
	


@PlcConfigForm (
	
	formPattern=FormPattern.Man,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/consulta/tipoempresa")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("tipoempresa")
@PlcHandleException
public class TipoEmpresaMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("tipoempresa")
	public TipoEmpresaEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new TipoEmpresaEntity();
              this.newEntity();
        }
        return (TipoEmpresaEntity)this.entityPlc;     	
	}
		
}
