package br.com.pinhoinformatica.reinf.persistence.jpa.informacoescontribuinte;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.reinf.InformacoesContribuinteEntity;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbiente;
import br.com.pinhoinformatica.reinf.entity.reinf.CnpjEmpresaRaizReinf;
import br.com.pinhoinformatica.reinf.entity.reinf.PeriodoApuracaoReinf;

import java.util.List;

import com.powerlogic.jcompany.persistence.jpa.PlcQuery;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryLineAmount;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryOrderBy;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryFirstLine;
import com.powerlogic.jcompany.commons.annotation.PlcAggregationDAOIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcDataAccessObject;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryService;
import com.powerlogic.jcompany.commons.PlcBaseContextVO;
/**
 * Classe de Persistência gerada pelo assistente
 */

@PlcAggregationDAOIoC(InformacoesContribuinteEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class InformacoesContribuinteDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<InformacoesContribuinteEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="tipoAmbiente", expression="obj1 = :tipoAmbiente") TipoAmbiente tipoAmbiente,
			@PlcQueryParameter(name="empresaRaizCnpj", expression="obj2 = :empresaRaizCnpj") CnpjEmpresaRaizReinf empresaRaizCnpj,
			@PlcQueryParameter(name="periodoApuracaoReinf", expression="obj3 = :periodoApuracaoReinf") PeriodoApuracaoReinf periodoApuracaoReinf
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="tipoAmbiente", expression="obj1 = :tipoAmbiente") TipoAmbiente tipoAmbiente,
			@PlcQueryParameter(name="empresaRaizCnpj", expression="obj2 = :empresaRaizCnpj") CnpjEmpresaRaizReinf empresaRaizCnpj,
			@PlcQueryParameter(name="periodoApuracaoReinf", expression="obj3 = :periodoApuracaoReinf") PeriodoApuracaoReinf periodoApuracaoReinf
	);
	
}
