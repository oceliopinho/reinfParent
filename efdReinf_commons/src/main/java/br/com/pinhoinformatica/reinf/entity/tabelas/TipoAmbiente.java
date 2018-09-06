package br.com.pinhoinformatica.reinf.entity.tabelas;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;


@MappedSuperclass
public abstract class TipoAmbiente extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "")
	@Column(name = "TIPO_AMBIENTE")
	private Long id;

	
	@NotNull
	@Size(max = 30)
	private String tipoAmbienteDescricao;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getTipoAmbienteDescricao() {
		return tipoAmbienteDescricao;
	}

	public void setTipoAmbienteDescricao(String tipoAmbienteDescricao) {
		this.tipoAmbienteDescricao=tipoAmbienteDescricao;
	}

}
