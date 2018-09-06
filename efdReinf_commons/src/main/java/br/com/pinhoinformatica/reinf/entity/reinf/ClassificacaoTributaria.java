package br.com.pinhoinformatica.reinf.entity.reinf;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;


@MappedSuperclass
public abstract class ClassificacaoTributaria extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_CLASSIFICACAO_TRIBUTARIA")
	@Column(name="ID_CLASSIFICACAO_TRIBUTARIA")
	private Long id;
	
	@NotNull
	@Size(max = 150)
	private String descClassificacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getDescClassificacao() {
		return descClassificacao;
	}

	public void setDescClassificacao(String descClassificacao) {
		this.descClassificacao=descClassificacao;
	}

}
