package br.com.pinhoinformatica.reinf.entity.reinf;

import java.math.BigDecimal;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.TipoProcesso;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.ForeignKey;
import javax.validation.constraints.NotNull;
import javax.persistence.EnumType;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Digits;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;
import javax.persistence.Transient;


@MappedSuperclass
public abstract class ServicosTomadosProcRetAd extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_SERVICOS_TOMADOS_PROC_RET_AD")
	@Column(name="ID_SERVICOS_TOMADOS_PROC_RET_AD")
	private Long id;
	
	@ManyToOne (targetEntity = ServicosTomadosEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOSPROCRETAD_SERVICOSTOMADOS")
	private ServicosTomados servicosTomados;	

	@PlcValRequiredIf(dependentfield="tpProcRetAdic",targetField="nrProcRetAdic")
	@Digits(integer=5, fraction=0)
	private Long tpProcRetAdic;
	
	@Enumerated(EnumType.STRING)
	@PlcValRequiredIf(dependentfield="tipoProcesso",targetField="nrProcRetAdic")
	@Column(length=5)
	private TipoProcesso tipoProcesso;
	
	@Size(max = 21)
	private String nrProcRetAdic;
	
	@PlcValRequiredIf(dependentfield="codSuspAdic",targetField="nrProcRetAdic")
	@Digits(integer=14, fraction=0)
	private Long codSuspAdic;	

	@PlcValRequiredIf(dependentfield="valorAdic",targetField="nrProcRetAdic")
	@Digits(integer=12, fraction=2)
	private BigDecimal valorAdic;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public Long getTpProcRetAdic() {
		return tpProcRetAdic;
	}

	public void setTpProcRetAdic(Long tpProcRetAdic) {
		this.tpProcRetAdic=tpProcRetAdic;
	}

	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso=tipoProcesso;
	}

	public String getNrProcRetAdic() {
		return nrProcRetAdic;
	}

	public void setNrProcRetAdic(String nrProcRetAdic) {
		this.nrProcRetAdic=nrProcRetAdic;
	}

	public Long getCodSuspAdic() {
		return codSuspAdic;
	}

	public void setCodSuspAdic(Long codSuspAdic) {
		this.codSuspAdic=codSuspAdic;
	}

	public BigDecimal getValorAdic() {
		return valorAdic;
	}

	public void setValorAdic(BigDecimal valorAdic) {
		this.valorAdic=valorAdic;
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
