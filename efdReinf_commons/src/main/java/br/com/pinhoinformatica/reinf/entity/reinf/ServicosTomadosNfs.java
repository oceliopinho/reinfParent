package br.com.pinhoinformatica.reinf.entity.reinf;

import java.math.BigDecimal;
import java.util.Date;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ForeignKey;
import javax.validation.constraints.NotNull;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;
import javax.persistence.Transient;


@MappedSuperclass
public abstract class ServicosTomadosNfs extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_SERVICOS_TOMADOS_NFS")
	@Column(name="ID_SERVICOS_TOMADOS_NFS")
	private Long id;
	
	@ManyToOne (targetEntity = ServicosTomadosEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOSNFS_SERVICOSTOMADOS")
	@NotNull
	private ServicosTomados servicosTomados;

	
	@NotNull
	@PlcValRequiredIf(dependentfield="serie",targetField="numDocto")
	@Size(max = 5)
	private String serie;
	
	@NotNull
	@Size(max = 9)
	private String numDocto;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="dtEmissaoNF",targetField="numDocto")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtEmissaoNF;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="vlrBruto",targetField="numDocto")
	@Digits(integer=8, fraction=2)
	private BigDecimal vlrBruto;
	
	@Size(max = 5)
	private String obs;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie=serie;
	}

	public String getNumDocto() {
		return numDocto;
	}

	public void setNumDocto(String numDocto) {
		this.numDocto=numDocto;
	}

	public Date getDtEmissaoNF() {
		return dtEmissaoNF;
	}

	public void setDtEmissaoNF(Date dtEmissaoNF) {
		this.dtEmissaoNF=dtEmissaoNF;
	}

	public BigDecimal getVlrBruto() {
		return vlrBruto;
	}

	public void setVlrBruto(BigDecimal vlrBruto) {
		this.vlrBruto=vlrBruto;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs=obs;
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
