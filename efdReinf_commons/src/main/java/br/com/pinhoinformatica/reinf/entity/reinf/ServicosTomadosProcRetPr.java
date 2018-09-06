package br.com.pinhoinformatica.reinf.entity.reinf;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.TipoProcesso;


@MappedSuperclass
public abstract class ServicosTomadosProcRetPr extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_SERVICOS_TOMADOS_PROC_RET_PR")
	@Column(name="ID_SERVICOS_TOMADOS_PROC_RET_PR")
	private Long id;
	
	@ManyToOne (targetEntity = ServicosTomadosEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOSPROCRETPR_SERVICOSTOMADOS")
	private ServicosTomados servicosTomados;

	@PlcValRequiredIf(dependentfield="tpProcRetPrinc",targetField="nrProcRetPrinc")
	@Digits(integer=5, fraction=0)
	private Long tpProcRetPrinc;
	
	@Enumerated(EnumType.STRING)
	@PlcValRequiredIf(dependentfield="tipoProcesso",targetField="nrProcRetPrinc")
	@Column(length=5)
	private TipoProcesso tipoProcesso;	

	@Size(max = 21)
	private String nrProcRetPrinc;	

	@PlcValRequiredIf(dependentfield="codSuspPrinc",targetField="nrProcRetPrinc")
	@Digits(integer=14, fraction=0)
	private Long codSuspPrinc;	

	@PlcValRequiredIf(dependentfield="valorPrinc",targetField="nrProcRetPrinc")
	@Digits(integer=12, fraction=2)
	private BigDecimal valorPrinc;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public Long getTpProcRetPrinc() {
		return tpProcRetPrinc;
	}

	public void setTpProcRetPrinc(Long tpProcRetPrinc) {
		this.tpProcRetPrinc=tpProcRetPrinc;
	}

	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso=tipoProcesso;
	}

	public String getNrProcRetPrinc() {
		return nrProcRetPrinc;
	}

	public void setNrProcRetPrinc(String nrProcRetPrinc) {
		this.nrProcRetPrinc=nrProcRetPrinc;
	}

	public Long getCodSuspPrinc() {
		return codSuspPrinc;
	}

	public void setCodSuspPrinc(Long codSuspPrinc) {
		this.codSuspPrinc=codSuspPrinc;
	}

	public BigDecimal getValorPrinc() {
		return valorPrinc;
	}

	public void setValorPrinc(BigDecimal valorPrinc) {
		this.valorPrinc=valorPrinc;
	}

	public ServicosTomados getServicosTomados() {
		return servicosTomados;
	}

	public void setServicosTomados(ServicosTomados servicosTomados) {
		this.servicosTomados=servicosTomados;
	}

	@Transient
	private String indExcPlc = "N";	

	public void setIndExcPlc(String indExcPlc) {
		this.indExcPlc = indExcPlc;
	}

	public String getIndExcPlc() {
		return indExcPlc;
	}

}
