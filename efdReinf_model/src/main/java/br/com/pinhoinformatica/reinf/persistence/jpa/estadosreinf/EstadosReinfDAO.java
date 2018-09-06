package br.com.pinhoinformatica.reinf.persistence.jpa.estadosreinf;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.tabelas.EstadosReinfEntity;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import br.com.pinhoinformatica.reinf.entity.tabelas.PaisesReinf;

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

@PlcAggregationDAOIoC(EstadosReinfEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class EstadosReinfDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<EstadosReinfEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="codPais", expression="obj1 = :codPais") PaisesReinf codPais,
			@PlcQueryParameter(name="siglaEstado", expression="obj.siglaEstado like :siglaEstado || '%' ") String siglaEstado
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="codPais", expression="obj1 = :codPais") PaisesReinf codPais,
			@PlcQueryParameter(name="siglaEstado", expression="obj.siglaEstado like :siglaEstado || '%' ") String siglaEstado
	);
	
}
