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
public abstract class PaisesReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "")
	@Column(name="COD_PAIS")
	private Long id;


	
	@NotNull
	@Size(max = 2)
	private String siglaPais;	
	
	@NotNull
	@Size(max = 60)
	private String nomePais;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getSiglaPais() {
		return siglaPais;
	}

	public void setSiglaPais(String siglaPais) {
		this.siglaPais=siglaPais;
	}

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais=nomePais;
	}

}
