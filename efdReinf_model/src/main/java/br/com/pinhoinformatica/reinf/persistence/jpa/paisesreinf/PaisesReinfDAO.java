package br.com.pinhoinformatica.reinf.persistence.jpa.paisesreinf;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.tabelas.PaisesReinfEntity;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;

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

@PlcAggregationDAOIoC(PaisesReinfEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class PaisesReinfDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<PaisesReinfEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="siglaPais", expression="siglaPais like :siglaPais || '%' ") String siglaPais
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="siglaPais", expression="siglaPais like :siglaPais || '%' ") String siglaPais
	);
	
}
