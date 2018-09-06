package br.com.pinhoinformatica.reinf.persistence.jpa.classificacaotributaria;

import java.util.List;

import com.powerlogic.jcompany.commons.PlcBaseContextVO;
import com.powerlogic.jcompany.commons.annotation.PlcAggregationDAOIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcDataAccessObject;
import com.powerlogic.jcompany.persistence.jpa.PlcQuery;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryFirstLine;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryLineAmount;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryOrderBy;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryService;

import br.com.pinhoinformatica.reinf.entity.reinf.ClassificacaoTributariaEntity;
import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
/**
 * Classe de Persistência gerada pelo assistente
 */

@PlcAggregationDAOIoC(ClassificacaoTributariaEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class ClassificacaoTributariaDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<ClassificacaoTributariaEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="descClassificacao", expression="descClassificacao like :descClassificacao || '%' ") String descClassificacao
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="descClassificacao", expression="descClassificacao like :descClassificacao || '%' ") String descClassificacao
	);
	
}
