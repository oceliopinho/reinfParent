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

import com.powerlogic.jcompany.domain.validation.PlcValSimpleFormat;
import com.powerlogic.jcompany.domain.validation.PlcValSimpleFormat.SimpleFormat;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;


@MappedSuperclass
public abstract class EstadosReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "")
	@Column(name="COD_ESTADO")
	private Long id;

	

	@ManyToOne (targetEntity = PaisesReinfEntity.class, fetch = FetchType.LAZY)	
	@ForeignKey(name="FK_ESTADOSREINF_CODPAIS")
	@NotNull
	private PaisesReinf codPais;
	
	@NotNull
	@Size(max = 2)
	@PlcValSimpleFormat(format=SimpleFormat.UPPER_CASE)
	private String siglaEstado;
	
	@NotNull
	@Size(max = 60)
	private String nomeEstado;
	
	@NotNull
	@Digits(integer=5, fraction=0)
	private Integer qtdeMunicipio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public PaisesReinf getCodPais() {
		return codPais;
	}

	public void setCodPais(PaisesReinf codPais) {
		this.codPais=codPais;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado=siglaEstado;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado=nomeEstado;
	}

	public Integer getQtdeMunicipio() {
		return qtdeMunicipio;
	}

	public void setQtdeMunicipio(Integer qtdeMunicipio) {
		this.qtdeMunicipio=qtdeMunicipio;
	}

}
