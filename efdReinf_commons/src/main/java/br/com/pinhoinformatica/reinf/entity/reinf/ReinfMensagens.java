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
public abstract class ReinfMensagens extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_REINF_MENSAGENS")
	@Column(name="ID_REINF_MENSAGENS")
	private Long id;

	
	@NotNull
	@Size(max = 20)
	private String codigoMensagem;
	
	@NotNull
	@Size(max = 800)
	private String descricaoMensagem;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getCodigoMensagem() {
		return codigoMensagem;
	}

	public void setCodigoMensagem(String codigoMensagem) {
		this.codigoMensagem=codigoMensagem;
	}

	public String getDescricaoMensagem() {
		return descricaoMensagem;
	}

	public void setDescricaoMensagem(String descricaoMensagem) {
		this.descricaoMensagem=descricaoMensagem;
	}

}
