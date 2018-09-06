package br.com.pinhoinformatica.reinf.controller.jsf.classificacaotributaria;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.powerlogic.jcompany.commons.annotation.PlcUriIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcMB;
import com.powerlogic.jcompany.config.aggregation.PlcConfigAggregation;
import com.powerlogic.jcompany.config.collaboration.FormPattern;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm;
import com.powerlogic.jcompany.config.collaboration.PlcConfigFormLayout;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;

import br.com.pinhoinformatica.reinf.controller.jsf.AppMB;
import br.com.pinhoinformatica.reinf.entity.reinf.ClassificacaoTributariaEntity;

@PlcConfigAggregation(
		entity = br.com.pinhoinformatica.reinf.entity.reinf.ClassificacaoTributariaEntity.class
		
	)
	


@PlcConfigForm (
	
	formPattern=FormPattern.Man,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/crud/classificacaotributaria")
	
	
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("classificacaotributaria")
@PlcHandleException
public class ClassificacaoTributariaMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("classificacaotributaria")
	public ClassificacaoTributariaEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new ClassificacaoTributariaEntity();
              this.newEntity();
        }
        return (ClassificacaoTributariaEntity)this.entityPlc;     	
	}
		
}
