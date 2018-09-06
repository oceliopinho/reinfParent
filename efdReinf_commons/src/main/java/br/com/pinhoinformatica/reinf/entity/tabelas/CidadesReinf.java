package br.com.pinhoinformatica.reinf.entity.tabelas;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;


@MappedSuperclass
public abstract class CidadesReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "")
	@Column(name="COD_CIDADE")
	private Long id;	

	@ManyToOne (targetEntity = EstadosReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_CIDADESREINF_ESTADOS")
	@NotNull
	private EstadosReinf estados;
	
	@NotNull
	@Size(max = 60)
	private String nomeCidade;
	
	@NotNull
	@Digits(integer=7, fraction=0)
	private Integer codIbge;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public EstadosReinf getEstados() {
		return estados;
	}

	public void setEstados(EstadosReinf estados) {
		this.estados=estados;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade=nomeCidade;
	}

	public Integer getCodIbge() {
		return codIbge;
	}

	public void setCodIbge(Integer codIbge) {
		this.codIbge=codIbge;
	}

}
