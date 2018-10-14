package br.com.pinhoinformatica.reinf.entity.reinf;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;


@MappedSuperclass
public abstract class EmpresaPopReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_EMPRESA_POP")
	@Column(name="ID_EMPRESA_POP")
	private Long id;
	
	@ManyToOne (targetEntity = EmpresaReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAPOPREINF_EMPRESAREINF")
	@JoinColumn(name="ID_EMPRESA")
	@NotNull
	private EmpresaReinf empresaReinf;

	

	@NotNull
	@Size(max = 50)
	private String popServer;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="popSenha",targetField="popServer")
	@Size(max = 30)
	private String popSenha;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="popPorta",targetField="popServer")
	@Digits(integer=5, fraction=0)
	private Integer popPorta;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="popSsl",targetField="popServer")
	private boolean popSsl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getPopServer() {
		return popServer;
	}

	public void setPopServer(String popServer) {
		this.popServer=popServer;
	}

	public String getPopSenha() {
		return popSenha;
	}

	public void setPopSenha(String popSenha) {
		this.popSenha=popSenha;
	}

	public Integer getPopPorta() {
		return popPorta;
	}

	public void setPopPorta(Integer popPorta) {
		this.popPorta=popPorta;
	}

	public boolean getPopSsl() {
		return popSsl;
	}

	public void setPopSsl(boolean popSsl) {
		this.popSsl=popSsl;
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
