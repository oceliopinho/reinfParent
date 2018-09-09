/*  																													
	    				   jCompany Developer Suite																		
			    		Copyright (C) 2008  Powerlogic																	
	 																													
	    Este programa é licenciado com todos os seus códigos fontes. Você pode modificá-los e							
	    utilizá-los livremente, inclusive distribuí-los para terceiros quando fizerem parte de algum aplicativo 		
	    sendo cedido, segundo os termos de licenciamento gerenciado de código aberto da Powerlogic, definidos			
	    na licença 'Powerlogic Open-Source Licence 2.0 (POSL 2.0)'.    													
	  																													
	    A Powerlogic garante o acerto de erros eventualmente encontrados neste código, para os clientes licenciados, 	
	    desde que todos os códigos do programa sejam mantidos intactos, sem modificações por parte do licenciado. 		
	 																													
	    Você deve ter recebido uma cópia da licença POSL 2.0 juntamente com este programa.								
	    Se não recebeu, veja em <http://www.powerlogic.com.br/licencas/posl20/>.										
	 																													
	    Contatos: plc@powerlogic.com.br - www.powerlogic.com.br 														
																														
 */ 
 /** ************************* META-DADOS GLOBAIS DA APLICAÇÃO ******************************
  ********************** Configurações padrão para toda a aplicação *************************
  ************ Obs: configurações corporativas devem estar no nível anterior,****************
  ************              preferencialmente na camada Bridge               ****************
  *******************************************************************************************/

@PlcConfigApplication(
	definition=@PlcConfigApplicationDefinition(name="efdReinf",acronym="efdReinf",version=1,release=0),
	classesDiscreteDomain={br.com.pinhoinformatica.reinf.entity.dominio.StatusLoteReinf.class,br.com.pinhoinformatica.reinf.entity.dominio.TipoProcesso.class,br.com.pinhoinformatica.reinf.entity.dominio.TipoInscricao.class,br.com.pinhoinformatica.reinf.entity.dominio.SimNao.class,br.com.pinhoinformatica.reinf.entity.dominio.SimNao.class,br.com.pinhoinformatica.reinf.entity.dominio.SimNao.class,br.com.pinhoinformatica.reinf.entity.dominio.SituacaoPessoaJuridica.class,br.com.pinhoinformatica.reinf.entity.dominio.StatusApuracaoReinf.class,br.com.pinhoinformatica.reinf.entity.dominio.TipoCertificado.class,br.com.pinhoinformatica.reinf.entity.dominio.TipoCadastroContribuinte.class},
	classesLookup={br.com.pinhoinformatica.reinf.entity.reinf.ReinfMensagensEntity.class,br.com.pinhoinformatica.reinf.entity.reinf.PeriodoApuracaoReinfEntity.class,br.com.pinhoinformatica.reinf.entity.reinf.ClassificacaoTributariaEntity.class,br.com.pinhoinformatica.reinf.entity.reinf.EmpresaContatoReinfEntity.class,br.com.pinhoinformatica.reinf.entity.reinf.CnpjEmpresaRaizReinfEntity.class,br.com.pinhoinformatica.reinf.entity.tabelas.TipoEmpresaEntity.class,br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbienteEntity.class,br.com.pinhoinformatica.reinf.entity.tabelas.PaisesReinfEntity.class,br.com.pinhoinformatica.reinf.entity.tabelas.EstadosReinfEntity.class,br.com.pinhoinformatica.reinf.entity.tabelas.CidadesReinfEntity.class}
)

package com.powerlogic.jcompany.config.app;

import com.powerlogic.jcompany.config.application.PlcConfigApplication;
import com.powerlogic.jcompany.config.application.PlcConfigApplicationDefinition;
import com.powerlogic.jcompany.config.application.PlcConfigModule;

