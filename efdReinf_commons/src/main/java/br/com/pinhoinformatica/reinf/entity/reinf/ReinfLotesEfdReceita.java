package br.com.pinhoinformatica.reinf.entity.reinf;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.StatusLoteReinf;

import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.validation.constraints.Digits;
import com.powerlogic.jcompany.config.domain.PlcReference;
import javax.persistence.Id;
import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;
import javax.persistence.Transient;




@MappedSuperclass
public abstract class ReinfLotesEfdReceita extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_REINF_LOTES_EFD_RECEITA")
	@Column(name="ID_REINF_LOTES_EFD_RECEITA")
	private Long id;
	

	
	@NotNull
	@PlcValRequiredIf(dependentfield="evento",targetField="numLote")
	@Size(max = 6)
	private String evento;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=5)
	private StatusLoteReinf statusLoteReinf;
	
	@ManyToOne (targetEntity = CnpjEmpresaRaizReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTESEFDRECEITA_CNPJEMPRESARAIZREINF")
	@NotNull
	@PlcValRequiredIf(dependentfield="cnpjEmpresaRaizReinf",targetField="numLote")
	private CnpjEmpresaRaizReinf cnpjEmpresaRaizReinf;
	
	@ManyToOne (targetEntity = EmpresaReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTESEFDRECEITA_EMPRESAREINF")
	@PlcValRequiredIf(dependentfield="empresaReinf",targetField="numLote")
	private EmpresaReinf empresaReinf;
	
	@ManyToOne (targetEntity = PeriodoApuracaoReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTESEFDRECEITA_PERIODOAPURACAOREINF")
	@NotNull
	@PlcValRequiredIf(dependentfield="periodoApuracaoReinf",targetField="numLote")
	private PeriodoApuracaoReinf periodoApuracaoReinf;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="attribute1",targetField="numLote")
	@Digits(integer=5, fraction=0)
	private Long attribute1;
	
	@NotNull
	@Size(max = 36)
	@PlcReference(testDuplicity=true)
	private String numLote;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="dataCriacao",targetField="numLote")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="usuarioCriacao",targetField="numLote")
	@Size(max = 30)
	private String usuarioCriacao;
	

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRetornoReceita;
	
	@Size(max = 30)
	private String usuarioRetornoReceita;
	

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataExclusaoLote;
	
	@Size(max = 30)
	private String usuarioExclusaoLote;
	
	@Size(max = 50)
	private String numeroRecibo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento=evento;
	}

	public CnpjEmpresaRaizReinf getCnpjEmpresaRaizReinf() {
		return cnpjEmpresaRaizReinf;
	}

	public void setCnpjEmpresaRaizReinf(CnpjEmpresaRaizReinf cnpjEmpresaRaizReinf) {
		this.cnpjEmpresaRaizReinf=cnpjEmpresaRaizReinf;
	}

	public EmpresaReinf getEmpresaReinf() {
		return empresaReinf;
	}

	public void setEmpresaReinf(EmpresaReinf empresaReinf) {
		this.empresaReinf=empresaReinf;
	}

	public PeriodoApuracaoReinf getPeriodoApuracaoReinf() {
		return periodoApuracaoReinf;
	}

	public void setPeriodoApuracaoReinf(PeriodoApuracaoReinf periodoApuracaoReinf) {
		this.periodoApuracaoReinf=periodoApuracaoReinf;
	}

	public Long getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(Long attribute1) {
		this.attribute1=attribute1;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String numLote) {
		this.numLote=numLote;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao=dataCriacao;
	}

	public String getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(String usuarioCriacao) {
		this.usuarioCriacao=usuarioCriacao;
	}

	public Date getDataRetornoReceita() {
		return dataRetornoReceita;
	}

	public void setDataRetornoReceita(Date dataRetornoReceita) {
		this.dataRetornoReceita=dataRetornoReceita;
	}

	public String getUsuarioRetornoReceita() {
		return usuarioRetornoReceita;
	}

	public void setUsuarioRetornoReceita(String usuarioRetornoReceita) {
		this.usuarioRetornoReceita=usuarioRetornoReceita;
	}

	public Date getDataExclusaoLote() {
		return dataExclusaoLote;
	}

	public void setDataExclusaoLote(Date dataExclusaoLote) {
		this.dataExclusaoLote=dataExclusaoLote;
	}

	public String getUsuarioExclusaoLote() {
		return usuarioExclusaoLote;
	}

	public void setUsuarioExclusaoLote(String usuarioExclusaoLote) {
		this.usuarioExclusaoLote=usuarioExclusaoLote;
	}

	public String getNumeroRecibo() {
		return numeroRecibo;
	}

	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo=numeroRecibo;
	}

	@Transient
	private String indExcPlc = "N";	

	public void setIndExcPlc(String indExcPlc) {
		this.indExcPlc = indExcPlc;
	}

	public String getIndExcPlc() {
		return indExcPlc;
	}

	public StatusLoteReinf getStatusLoteReinf() {
		return statusLoteReinf;
	}

	public void setStatusLoteReinf(StatusLoteReinf statusLoteReinf) {
		this.statusLoteReinf = statusLoteReinf;
	}
}
