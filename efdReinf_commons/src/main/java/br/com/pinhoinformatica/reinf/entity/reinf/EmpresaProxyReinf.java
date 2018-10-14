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
public abstract class EmpresaProxyReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_EMPRESA_PROXY")
	@Column(name="ID_EMPRESA_PROXY")
	private Long id;
	
	@ManyToOne (targetEntity = EmpresaReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAPROXYREINF_EMPRESAREINF")
	@JoinColumn(name="ID_EMPRESA")
	@NotNull
	private EmpresaReinf empresaReinf;

	

	@NotNull
	@Size(max = 50)
	private String proxyServer;
	
	@Size(max = 50)
	private String proxyUrl;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="proxyUser",targetField="proxyServer")
	@Size(max = 30)
	private String proxyUser;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="proxyPassword",targetField="proxyServer")
	@Size(max = 30)
	private String proxyPassword;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="proxyPorta",targetField="proxyServer")
	@Digits(integer=5, fraction=0)
	private Integer proxyPorta;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getProxyServer() {
		return proxyServer;
	}

	public void setProxyServer(String proxyServer) {
		this.proxyServer=proxyServer;
	}

	public String getProxyUrl() {
		return proxyUrl;
	}

	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl=proxyUrl;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser=proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword=proxyPassword;
	}

	public Integer getProxyPorta() {
		return proxyPorta;
	}

	public void setProxyPorta(Integer proxyPorta) {
		this.proxyPorta=proxyPorta;
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
