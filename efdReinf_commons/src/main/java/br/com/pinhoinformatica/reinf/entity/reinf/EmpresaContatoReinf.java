package br.com.pinhoinformatica.reinf.entity.reinf;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;


@MappedSuperclass
public abstract class EmpresaContatoReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_EMPRESA_CONTATO")
	@Column(name="ID_EMPRESA_CONTATO")
	private Long id;
	
	@ManyToOne (targetEntity = EmpresaReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESACONTATOREINF_EMPRESAREINF")
	@NotNull
	private EmpresaReinf empresaReinf;

	
	
	@NotNull
	@Size(max = 11)
	private String cpfContato;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="nomeContato",targetField="cpfContato")
	@Size(max = 70)
	private String nomeContato;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="foneContato",targetField="cpfContato")
	@Size(max = 13)
	private String foneContato;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="celularContato",targetField="cpfContato")
	@Size(max = 13)
	private String celularContato;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="emailContato",targetField="cpfContato")
	@Size(max = 60)
	private String emailContato;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getCpfContato() {
		return cpfContato;
	}

	public void setCpfContato(String cpfContato) {
		this.cpfContato=cpfContato;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato=nomeContato;
	}

	public String getFoneContato() {
		return foneContato;
	}

	public void setFoneContato(String foneContato) {
		this.foneContato=foneContato;
	}

	public String getCelularContato() {
		return celularContato;
	}

	public void setCelularContato(String celularContato) {
		this.celularContato=celularContato;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato=emailContato;
	}

	public EmpresaReinf getEmpresaReinf() {
		return empresaReinf;
	}

	public void setEmpresaReinf(EmpresaReinf empresaReinf) {
		this.empresaReinf=empresaReinf;
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
