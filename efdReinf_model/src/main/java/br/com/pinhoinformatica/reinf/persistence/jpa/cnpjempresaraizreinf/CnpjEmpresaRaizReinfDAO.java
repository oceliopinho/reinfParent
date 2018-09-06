package br.com.pinhoinformatica.reinf.persistence.jpa.cnpjempresaraizreinf;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.reinf.CnpjEmpresaRaizReinfEntity;
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

@PlcAggregationDAOIoC(CnpjEmpresaRaizReinfEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class CnpjEmpresaRaizReinfDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<CnpjEmpresaRaizReinfEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="cnpjRaiz", expression="cnpjRaiz like :cnpjRaiz || '%' ") String cnpjRaiz,
			@PlcQueryParameter(name="nomeFantasia", expression="nomeFantasia like :nomeFantasia || '%' ") String nomeFantasia
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="id = :id") Long id,
			@PlcQueryParameter(name="cnpjRaiz", expression="cnpjRaiz like :cnpjRaiz || '%' ") String cnpjRaiz,
			@PlcQueryParameter(name="nomeFantasia", expression="nomeFantasia like :nomeFantasia || '%' ") String nomeFantasia
	);
	
}
