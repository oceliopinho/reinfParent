package br.com.pinhoinformatica.reinf.persistence.jpa.servicostomados;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosEntity;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import br.com.pinhoinformatica.reinf.entity.reinf.EmpresaReinf;

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

@PlcAggregationDAOIoC(ServicosTomadosEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class ServicosTomadosDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<ServicosTomadosEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="empresa", expression="obj1 = :empresa") EmpresaReinf empresa,
			@PlcQueryParameter(name="perApur", expression="obj.perApur like :perApur || '%' ") String perApur,
			@PlcQueryParameter(name="nrInscEstab", expression="obj.nrInscEstab like :nrInscEstab || '%' ") String nrInscEstab
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="empresa", expression="obj1 = :empresa") EmpresaReinf empresa,
			@PlcQueryParameter(name="perApur", expression="obj.perApur like :perApur || '%' ") String perApur,
			@PlcQueryParameter(name="nrInscEstab", expression="obj.nrInscEstab like :nrInscEstab || '%' ") String nrInscEstab
	);
	
}
