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
public abstract class CnpjEmpresaRaizReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_EMPRESA_RAIZ_CNPJ")
	@Column(name="ID_EMPRESA_RAIZ_CNPJ")
	private Long id;

	
	@NotNull
	@Size(max = 8)
	private String cnpjRaiz;
	
	@NotNull
	@Size(max = 50)
	private String nomeFantasia;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getCnpjRaiz() {
		return cnpjRaiz;
	}

	public void setCnpjRaiz(String cnpjRaiz) {
		this.cnpjRaiz=cnpjRaiz;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia=nomeFantasia;
	}

}
