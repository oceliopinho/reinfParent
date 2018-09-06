package br.com.pinhoinformatica.reinf.entity.reinf;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.StatusApuracaoReinf;


@MappedSuperclass
public abstract class PeriodoApuracaoReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_PERIODO_APURACAO_REINF")
	@Column(name="ID_PERIODO_APURACAO_REINF")
	private Long id;


	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date competencia;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=20)
	private StatusApuracaoReinf statusApuracaoReinf;
	
	@NotNull
	@Size(max = 7)
	private String perApur;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public Date getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Date competencia) {
		this.competencia=competencia;
	}

	public StatusApuracaoReinf getStatusApuracaoReinf() {
		return statusApuracaoReinf;
	}

	public void setStatusApuracaoReinf(StatusApuracaoReinf statusApuracaoReinf) {
		this.statusApuracaoReinf=statusApuracaoReinf;
	}

	public String getPerApur() {
		return perApur;
	}

	public void setPerApur(String perApur) {
		this.perApur = perApur;
	}	
}
