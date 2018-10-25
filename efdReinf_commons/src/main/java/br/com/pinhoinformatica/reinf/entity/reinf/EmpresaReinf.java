package br.com.pinhoinformatica.reinf.entity.reinf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

import com.powerlogic.jcompany.domain.validation.PlcValDuplicity;
import com.powerlogic.jcompany.domain.validation.PlcValMultiplicity;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.TipoCertificado;
import br.com.pinhoinformatica.reinf.entity.tabelas.CidadesReinf;
import br.com.pinhoinformatica.reinf.entity.tabelas.CidadesReinfEntity;
import br.com.pinhoinformatica.reinf.entity.tabelas.EstadosReinf;
import br.com.pinhoinformatica.reinf.entity.tabelas.EstadosReinfEntity;
import br.com.pinhoinformatica.reinf.entity.tabelas.ModeloDocReinf;
import br.com.pinhoinformatica.reinf.entity.tabelas.ModeloDocReinfEntity;
import br.com.pinhoinformatica.reinf.entity.tabelas.PaisesReinf;
import br.com.pinhoinformatica.reinf.entity.tabelas.PaisesReinfEntity;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbiente;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbienteEntity;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoEmpresa;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoEmpresaEntity;


@MappedSuperclass
public abstract class EmpresaReinf extends AppBaseEntity {


	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaContatoReinfEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="empresaReinf")
	@ForeignKey(name="FK_EMPRESACONTATOREINF_EMPRESAREINF")
	@PlcValDuplicity(property="cpfContato")
	@PlcValMultiplicity(referenceProperty="cpfContato",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.EmpresaContatoReinfEntity}")
	@Valid
	private List<EmpresaContatoReinf> empresaContatoReinf;


	
	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaSmtpReinfEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="empresaReinf")
	@ForeignKey(name="FK_EMPRESASMTPREINF_EMPRESAREINF")
	@PlcValDuplicity(property="smtpServer")
	@PlcValMultiplicity(referenceProperty="smtpServer",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.EmpresaSmtpReinfEntity}")
	@Valid
	private List<EmpresaSmtpReinf> empresaSmtpReinf;


	
	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaProxyReinfEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="empresaReinf")
	@ForeignKey(name="FK_EMPRESAPROXYREINF_TBEMPRESA")
	@PlcValDuplicity(property="proxyServer")
	@PlcValMultiplicity(referenceProperty="proxyServer",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.EmpresaProxyReinfEntity}")
	@Valid
	private List<EmpresaProxyReinf> empresaProxyReinf;


	
	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.EmpresaPopReinfEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="empresaReinf")
	@ForeignKey(name="FK_EMPRESAPOPREINF_TBEMPRESA")
	@PlcValDuplicity(property="popServer")
	@PlcValMultiplicity(referenceProperty="popServer",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.EmpresaPopReinfEntity}")
	@Valid
	private List<EmpresaPopReinf> empresaPopReinf;


	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_EMPRESA")
	@Column(name="ID_EMPRESA")
	private Long id;

	

	@ManyToOne (targetEntity = CnpjEmpresaRaizReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAREINF_EMPRESARAIZCNPJ")
	@JoinColumn(name="ID_EMPRESA_RAIZ_CNPJ")
	@NotNull
	private CnpjEmpresaRaizReinf empresaRaizCnpj;
	
	@ManyToOne (targetEntity = TipoEmpresaEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAREINF_TIPOEMPRESA")
	@NotNull
	private TipoEmpresa tipoEmpresa;
	
	@ManyToOne (targetEntity = TipoAmbienteEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAREINF_TIPOAMBIENTE")
	@NotNull
	private TipoAmbiente tipoAmbiente;
	
	@ManyToOne (targetEntity = ModeloDocReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAREINF_MODELODOC")
	@NotNull
	private ModeloDocReinf modeloDoc;
	
	@NotNull
	@Size(max = 14)
	private String empCnpjCpf;
	
	@NotNull
	@Size(max = 60)
	private String empXnome;
	
	@Size(max = 60)
	private String empXfantasia;
	
	@NotNull
	@Size(max = 60)
	private String empXlgr;	
	
	@NotNull
	@Size(max = 10)
	private String empNro;
	
	@Size(max = 60)
	private String empXcpl;
	
	@NotNull
	@Size(max = 30)
	private String empXbairro;
	
	@ManyToOne (targetEntity = PaisesReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAREINF_PAISES")
	@NotNull
	private PaisesReinf paises;
	
	@ManyToOne (targetEntity = EstadosReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAREINF_ESTADOS")
	@NotNull
	private EstadosReinf estados;
	
	@ManyToOne (targetEntity = CidadesReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_EMPRESAREINF_CIDADES")
	@NotNull
	private CidadesReinf cidades;
	
	@NotNull
	@Size(max = 8)
	private String empCep;
	
	@NotNull
	@Size(max = 15)
	private String empFone;
	
	@NotNull
	@Size(max = 14)
	private String empIe;
	
	@Size(max = 14)
	private String empIeSt;
	
	@Size(max = 15)
	private String empIm;
	
	@NotNull
	@Size(max = 7)
	private String empCnae;
	
	@NotNull
	@Digits(integer=3, fraction=0)
	private Integer empSerie;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=1)
	private TipoCertificado tipoCertificado;	
	
	@NotNull
	@Size(max = 30)
	private String modeloCertificado;
	
	@NotNull
	@Size(max = 50)
	private String nomeCertificado;
	
	@NotNull
	@Size(max = 30)
	private String senhaCertificado;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date validadeCert;
	
	@NotNull
	@Size(max = 30)
	private String empDiretorio;
	
	@NotNull
	@Digits(integer=5, fraction=0)
	private Integer tempoAtivarCont;
	
	@NotNull
	@Digits(integer=5, fraction=2)
	private BigDecimal aliqIcms;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public CnpjEmpresaRaizReinf getEmpresaRaizCnpj() {
		return empresaRaizCnpj;
	}

	public void setEmpresaRaizCnpj(CnpjEmpresaRaizReinf empresaRaizCnpj) {
		this.empresaRaizCnpj=empresaRaizCnpj;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa=tipoEmpresa;
	}

	public TipoAmbiente getTipoAmbiente() {
		return tipoAmbiente;
	}

	public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
		this.tipoAmbiente=tipoAmbiente;
	}

	public ModeloDocReinf getModeloDoc() {
		return modeloDoc;
	}

	public void setModeloDoc(ModeloDocReinf modeloDoc) {
		this.modeloDoc=modeloDoc;
	}

	public String getEmpCnpjCpf() {
		return empCnpjCpf;
	}

	public void setEmpCnpjCpf(String empCnpjCpf) {
		this.empCnpjCpf=empCnpjCpf;
	}

	public String getEmpXnome() {
		return empXnome;
	}

	public void setEmpXnome(String empXnome) {
		this.empXnome=empXnome;
	}

	public String getEmpXfantasia() {
		return empXfantasia;
	}

	public void setEmpXfantasia(String empXfantasia) {
		this.empXfantasia=empXfantasia;
	}

	public String getEmpXlgr() {
		return empXlgr;
	}

	public void setEmpXlgr(String empXlgr) {
		this.empXlgr=empXlgr;
	}

	public String getEmpNro() {
		return empNro;
	}

	public void setEmpNro(String empNro) {
		this.empNro=empNro;
	}

	public String getEmpXcpl() {
		return empXcpl;
	}

	public void setEmpXcpl(String empXcpl) {
		this.empXcpl=empXcpl;
	}

	public String getEmpXbairro() {
		return empXbairro;
	}

	public void setEmpXbairro(String empXbairro) {
		this.empXbairro=empXbairro;
	}

	public PaisesReinf getPaises() {
		return paises;
	}

	public void setPaises(PaisesReinf paises) {
		this.paises=paises;
	}

	public EstadosReinf getEstados() {
		return estados;
	}

	public void setEstados(EstadosReinf estados) {
		this.estados=estados;
	}

	public CidadesReinf getCidades() {
		return cidades;
	}

	public void setCidades(CidadesReinf cidades) {
		this.cidades=cidades;
	}

	public String getEmpCep() {
		return empCep;
	}

	public void setEmpCep(String empCep) {
		this.empCep=empCep;
	}

	public String getEmpFone() {
		return empFone;
	}

	public void setEmpFone(String empFone) {
		this.empFone=empFone;
	}

	public String getEmpIe() {
		return empIe;
	}

	public void setEmpIe(String empIe) {
		this.empIe=empIe;
	}

	public String getEmpIeSt() {
		return empIeSt;
	}

	public void setEmpIeSt(String empIeSt) {
		this.empIeSt=empIeSt;
	}

	public String getEmpIm() {
		return empIm;
	}

	public void setEmpIm(String empIm) {
		this.empIm=empIm;
	}

	public String getEmpCnae() {
		return empCnae;
	}

	public void setEmpCnae(String empCnae) {
		this.empCnae=empCnae;
	}

	public Integer getEmpSerie() {
		return empSerie;
	}

	public void setEmpSerie(Integer empSerie) {
		this.empSerie=empSerie;
	}

	public TipoCertificado getTipoCertificado() {
		return tipoCertificado;
	}

	public void setTipoCertificado(TipoCertificado tipoCertificado) {
		this.tipoCertificado=tipoCertificado;
	}

	public String getModeloCertificado() {
		return modeloCertificado;
	}

	public void setModeloCertificado(String modeloCertificado) {
		this.modeloCertificado=modeloCertificado;
	}

	public String getNomeCertificado() {
		return nomeCertificado;
	}

	public void setNomeCertificado(String nomeCertificado) {
		this.nomeCertificado=nomeCertificado;
	}

	public String getSenhaCertificado() {
		return senhaCertificado;
	}

	public void setSenhaCertificado(String senhaCertificado) {
		this.senhaCertificado=senhaCertificado;
	}

	public Date getValidadeCert() {
		return validadeCert;
	}

	public void setValidadeCert(Date validadeCert) {
		this.validadeCert=validadeCert;
	}

	public String getEmpDiretorio() {
		return empDiretorio;
	}

	public void setEmpDiretorio(String empDiretorio) {
		this.empDiretorio=empDiretorio;
	}

	public Integer getTempoAtivarCont() {
		return tempoAtivarCont;
	}

	public void setTempoAtivarCont(Integer tempoAtivarCont) {
		this.tempoAtivarCont=tempoAtivarCont;
	}

	public BigDecimal getAliqIcms() {
		return aliqIcms;
	}

	public void setAliqIcms(BigDecimal aliqIcms) {
		this.aliqIcms=aliqIcms;
	}	

	public List<EmpresaPopReinf> getEmpresaPopReinf() {
		return empresaPopReinf;
	}

	public void setEmpresaPopReinf(List<EmpresaPopReinf> empresaPopReinf) {
		this.empresaPopReinf=empresaPopReinf;
	}

	public List<EmpresaProxyReinf> getEmpresaProxyReinf() {
		return empresaProxyReinf;
	}

	public void setEmpresaProxyReinf(List<EmpresaProxyReinf> empresaProxyReinf) {
		this.empresaProxyReinf=empresaProxyReinf;
	}

	public List<EmpresaSmtpReinf> getEmpresaSmtpReinf() {
		return empresaSmtpReinf;
	}

	public void setEmpresaSmtpReinf(List<EmpresaSmtpReinf> empresaSmtpReinf) {
		this.empresaSmtpReinf=empresaSmtpReinf;
	}

	public List<EmpresaContatoReinf> getEmpresaContatoReinf() {
		return empresaContatoReinf;
	}

	public void setEmpresaContatoReinf(List<EmpresaContatoReinf> empresaContatoReinf) {
		this.empresaContatoReinf = empresaContatoReinf;
	}	
}
