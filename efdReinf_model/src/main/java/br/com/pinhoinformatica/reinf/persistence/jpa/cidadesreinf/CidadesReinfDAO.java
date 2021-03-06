package br.com.pinhoinformatica.reinf.persistence.jpa.cidadesreinf;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.tabelas.CidadesReinfEntity;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import br.com.pinhoinformatica.reinf.entity.tabelas.EstadosReinf;

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

@PlcAggregationDAOIoC(CidadesReinfEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class CidadesReinfDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<CidadesReinfEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="estados", expression="obj1 = :estados") EstadosReinf estados,
			@PlcQueryParameter(name="nomeCidade", expression="obj.nomeCidade like :nomeCidade || '%' ") String nomeCidade
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="estados", expression="obj1 = :estados") EstadosReinf estados,
			@PlcQueryParameter(name="nomeCidade", expression="obj.nomeCidade like :nomeCidade || '%' ") String nomeCidade
	);
	
}
