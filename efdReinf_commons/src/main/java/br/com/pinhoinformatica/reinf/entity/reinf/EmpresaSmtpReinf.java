package br.com.pinhoinformatica.reinf.entity.reinf;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import javax.persistence.GenerationType;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.ForeignKey;
import javax.validation.constraints.Digits;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;
import javax.persistence.Transient;


@MappedSuperclass
public abstract class EmpresaSmtpReinf extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_EMPRESA_SMTP")
	private Long id;
	
	@ManyToOne (targetEntity = EmpresaReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESASMTPREINF_EMPRESAREINF")
	@JoinColumn(name="ID_EMPRESA")
	@NotNull
	private EmpresaReinf empresaReinf;

	

	@NotNull
	@Size(max = 50)
	private String smtpServer;

	@PlcValRequiredIf(dependentfield="smtpUsuario",targetField="smtpServer")
	@Size(max = 50)
	private String smtpUsuario;
	

	@PlcValRequiredIf(dependentfield="smtpUsuarioNome",targetField="smtpServer")
	@Size(max = 30)
	private String smtpUsuarioNome;
	

	@PlcValRequiredIf(dependentfield="smtpSenha",targetField="smtpServer")
	@Size(max = 30)
	private String smtpSenha;
	

	@PlcValRequiredIf(dependentfield="smtpPorta",targetField="smtpServer")
	@Digits(integer=5, fraction=0)
	private Integer smtpPorta;	
	

	@PlcValRequiredIf(dependentfield="smtpCharset",targetField="smtpServer")
	@Size(max = 20)
	private String smtpCharset;
	

	@PlcValRequiredIf(dependentfield="smtpProtocolo",targetField="smtpServer")
	@Size(max = 10)
	private String smtpProtocolo;
	
	private boolean smtpHtml;
	private boolean smtpStarttls;
	private boolean smtpAuth;
	private boolean smtpSsl;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer=smtpServer;
	}

	public String getSmtpUsuario() {
		return smtpUsuario;
	}

	public void setSmtpUsuario(String smtpUsuario) {
		this.smtpUsuario=smtpUsuario;
	}

	public String getSmtpUsuarioNome() {
		return smtpUsuarioNome;
	}

	public void setSmtpUsuarioNome(String smtpUsuarioNome) {
		this.smtpUsuarioNome=smtpUsuarioNome;
	}

	public String getSmtpSenha() {
		return smtpSenha;
	}

	public void setSmtpSenha(String smtpSenha) {
		this.smtpSenha=smtpSenha;
	}

	public Integer getSmtpPorta() {
		return smtpPorta;
	}

	public void setSmtpPorta(Integer smtpPorta) {
		this.smtpPorta=smtpPorta;
	}

	public String getSmtpCharset() {
		return smtpCharset;
	}

	public void setSmtpCharset(String smtpCharset) {
		this.smtpCharset=smtpCharset;
	}

	public String getSmtpProtocolo() {
		return smtpProtocolo;
	}

	public void setSmtpProtocolo(String smtpProtocolo) {
		this.smtpProtocolo=smtpProtocolo;
	}

	public boolean getSmtpHtml() {
		return smtpHtml;
	}

	public void setSmtpHtml(boolean smtpHtml) {
		this.smtpHtml=smtpHtml;
	}

	public boolean getSmtpStarttls() {
		return smtpStarttls;
	}

	public void setSmtpStarttls(boolean smtpStarttls) {
		this.smtpStarttls=smtpStarttls;
	}

	public boolean getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth=smtpAuth;
	}

	public boolean getSmtpSsl() {
		return smtpSsl;
	}

	public void setSmtpSsl(boolean smtpSsl) {
		this.smtpSsl=smtpSsl;
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
