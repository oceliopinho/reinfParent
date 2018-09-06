package br.com.pinhoinformatica.reinf.persistence.jpa.empresareinf;

import br.com.pinhoinformatica.reinf.persistence.jpa.AppJpaDAO;
import br.com.pinhoinformatica.reinf.entity.reinf.EmpresaReinfEntity;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import br.com.pinhoinformatica.reinf.entity.reinf.CnpjEmpresaRaizReinf;

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

@PlcAggregationDAOIoC(EmpresaReinfEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class EmpresaReinfDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<EmpresaReinfEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="empresaRaizCnpj", expression="obj1 = :empresaRaizCnpj") CnpjEmpresaRaizReinf empresaRaizCnpj,
			@PlcQueryParameter(name="empCnpjCpf", expression="obj.empCnpjCpf like :empCnpjCpf || '%' ") String empCnpjCpf,
			@PlcQueryParameter(name="empXnome", expression="obj.empXnome like :empXnome || '%' ") String empXnome
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="empresaRaizCnpj", expression="obj1 = :empresaRaizCnpj") CnpjEmpresaRaizReinf empresaRaizCnpj,
			@PlcQueryParameter(name="empCnpjCpf", expression="obj.empCnpjCpf like :empCnpjCpf || '%' ") String empCnpjCpf,
			@PlcQueryParameter(name="empXnome", expression="obj.empXnome like :empXnome || '%' ") String empXnome
	);
	
}
