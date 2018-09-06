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
import br.com.pinhoinformatica.reinf.entity.dominio.SimNao;
import br.com.pinhoinformatica.reinf.entity.dominio.StatusLoteReinf;
import br.com.pinhoinformatica.reinf.entity.dominio.TipoInscricao;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbiente;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbienteEntity;


@MappedSuperclass
public abstract class ServicosTomados extends AppBaseEntity {


	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosProcRetPrEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="servicosTomados")
	@ForeignKey(name="FK_SERVICOSTOMADOSPROCRETPR_SERVICOSTOMADOS")
	@PlcValDuplicity(property="nrProcRetPrinc")
	@PlcValMultiplicity(referenceProperty="nrProcRetPrinc",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.ServicosTomadosProcRetPrEntity}")
	@Valid
	private List<ServicosTomadosProcRetPr> servicosTomadosProcRetPr;

	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosProcRetAdEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="servicosTomados")
	@ForeignKey(name="FK_SERVICOSTOMADOSPROCRETAD_SERVICOSTOMADOS")
	@PlcValDuplicity(property="nrProcRetAdic")
	@PlcValMultiplicity(referenceProperty="nrProcRetAdic",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.ServicosTomadosProcRetAdEntity}")
	@Valid
	private List<ServicosTomadosProcRetAd> servicosTomadosProcRetAd;

	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.ServicosTomadosNfsEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="servicosTomados")
	@ForeignKey(name="FK_SERVICOSTOMADOSNFS_SERVICOSTOMADOS")
	@PlcValDuplicity(property="numDocto")
	@PlcValMultiplicity(referenceProperty="numDocto",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.ServicosTomadosNfsEntity}")
	@Valid
	private List<ServicosTomadosNfs> servicosTomadosNfs;

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_SERVICOS_TOMADOS")
	@Column(name="ID_SERVICOS_TOMADOS")
	private Long id;

	@ManyToOne (targetEntity = PeriodoApuracaoReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOS_PERIODOAPURACAOREINF")
	@NotNull
	private PeriodoApuracaoReinf periodoApuracaoReinf;
	
	@NotNull
	@Size(max = 7)
	private String perApur;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=1)
	private SimNao indRetif;

	@Size(max = 54)
	private String nrRecibo;

	@ManyToOne (targetEntity = TipoAmbienteEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOS_TIPOAMBIENTE")
	@NotNull
	private TipoAmbiente tipoAmbiente;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=5)
	private TipoInscricao tpInsc;

	@ManyToOne (targetEntity = CnpjEmpresaRaizReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOS_EMPRESARAIZCNPJ")
	@NotNull
	private CnpjEmpresaRaizReinf empresaRaizCnpj;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=5)
	private TipoInscricao tpInscEstab;

	@ManyToOne (targetEntity = EmpresaReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOS_EMPRESA")
	@NotNull
	private EmpresaReinf empresa;

	@NotNull
	@Digits(integer=1, fraction=0)
	private Long indObra;	

	@NotNull
	@Size(max = 14)
	private String cnpjPrestador;

	@NotNull
	@Size(max = 60)
	private String nomePrestador;

	@NotNull
	@Digits(integer=12, fraction=2)
	private BigDecimal vlrTotalBruto;

	@NotNull
	@Digits(integer=12, fraction=2)
	private BigDecimal vlrTotalBaseRet;

	@NotNull
	@Digits(integer=12, fraction=2)
	private BigDecimal vlrTotalRetPrinc;


	@Digits(integer=12, fraction=2)
	private BigDecimal vlrTotalRetAdic;


	@Digits(integer=12, fraction=2)
	private BigDecimal vlrTotalNRetPrinc;


	@Digits(integer=12, fraction=2)
	private BigDecimal vlrTotalNRetAdic;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=1)
	private SimNao contribuinteCPRB;

	@Enumerated(EnumType.STRING)
	@Column(length=5)
	private StatusLoteReinf statusLoteReinf;

	@Digits(integer=12, fraction=2)
	private BigDecimal valorBaseRetornoReceita;

	@Digits(integer=12, fraction=2)
	private BigDecimal valorImpostoRetornoReceita;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataReciboReceita;

	public List<ServicosTomadosProcRetPr> getServicosTomadosProcRetPr() {
		return servicosTomadosProcRetPr;
	}

	public void setServicosTomadosProcRetPr(List<ServicosTomadosProcRetPr> servicosTomadosProcRetPr) {
		this.servicosTomadosProcRetPr = servicosTomadosProcRetPr;
	}

	public List<ServicosTomadosProcRetAd> getServicosTomadosProcRetAd() {
		return servicosTomadosProcRetAd;
	}

	public void setServicosTomadosProcRetAd(List<ServicosTomadosProcRetAd> servicosTomadosProcRetAd) {
		this.servicosTomadosProcRetAd = servicosTomadosProcRetAd;
	}

	public List<ServicosTomadosNfs> getServicosTomadosNfs() {
		return servicosTomadosNfs;
	}

	public void setServicosTomadosNfs(List<ServicosTomadosNfs> servicosTomadosNfs) {
		this.servicosTomadosNfs = servicosTomadosNfs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PeriodoApuracaoReinf getPeriodoApuracaoReinf() {
		return periodoApuracaoReinf;
	}

	public void setPeriodoApuracaoReinf(PeriodoApuracaoReinf periodoApuracaoReinf) {
		this.periodoApuracaoReinf = periodoApuracaoReinf;
	}

	public SimNao getIndRetif() {
		return indRetif;
	}

	public void setIndRetif(SimNao indRetif) {
		this.indRetif = indRetif;
	}

	public String getNrRecibo() {
		return nrRecibo;
	}

	public void setNrRecibo(String nrRecibo) {
		this.nrRecibo = nrRecibo;
	}

	public TipoAmbiente getTipoAmbiente() {
		return tipoAmbiente;
	}

	public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
		this.tipoAmbiente = tipoAmbiente;
	}

	public TipoInscricao getTpInsc() {
		return tpInsc;
	}

	public void setTpInsc(TipoInscricao tpInsc) {
		this.tpInsc = tpInsc;
	}

	public CnpjEmpresaRaizReinf getEmpresaRaizCnpj() {
		return empresaRaizCnpj;
	}

	public void setEmpresaRaizCnpj(CnpjEmpresaRaizReinf empresaRaizCnpj) {
		this.empresaRaizCnpj = empresaRaizCnpj;
	}

	public TipoInscricao getTpInscEstab() {
		return tpInscEstab;
	}

	public void setTpInscEstab(TipoInscricao tpInscEstab) {
		this.tpInscEstab = tpInscEstab;
	}

	public EmpresaReinf getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaReinf empresa) {
		this.empresa = empresa;
	}

	public Long getIndObra() {
		return indObra;
	}

	public void setIndObra(Long indObra) {
		this.indObra = indObra;
	}

	public String getCnpjPrestador() {
		return cnpjPrestador;
	}

	public void setCnpjPrestador(String cnpjPrestador) {
		this.cnpjPrestador = cnpjPrestador;
	}

	public String getNomePrestador() {
		return nomePrestador;
	}

	public void setNomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}

	public BigDecimal getVlrTotalBruto() {
		return vlrTotalBruto;
	}

	public void setVlrTotalBruto(BigDecimal vlrTotalBruto) {
		this.vlrTotalBruto = vlrTotalBruto;
	}

	public BigDecimal getVlrTotalBaseRet() {
		return vlrTotalBaseRet;
	}

	public void setVlrTotalBaseRet(BigDecimal vlrTotalBaseRet) {
		this.vlrTotalBaseRet = vlrTotalBaseRet;
	}

	public BigDecimal getVlrTotalRetPrinc() {
		return vlrTotalRetPrinc;
	}

	public void setVlrTotalRetPrinc(BigDecimal vlrTotalRetPrinc) {
		this.vlrTotalRetPrinc = vlrTotalRetPrinc;
	}

	public BigDecimal getVlrTotalRetAdic() {
		return vlrTotalRetAdic;
	}

	public void setVlrTotalRetAdic(BigDecimal vlrTotalRetAdic) {
		this.vlrTotalRetAdic = vlrTotalRetAdic;
	}

	public BigDecimal getVlrTotalNRetPrinc() {
		return vlrTotalNRetPrinc;
	}

	public void setVlrTotalNRetPrinc(BigDecimal vlrTotalNRetPrinc) {
		this.vlrTotalNRetPrinc = vlrTotalNRetPrinc;
	}

	public BigDecimal getVlrTotalNRetAdic() {
		return vlrTotalNRetAdic;
	}

	public void setVlrTotalNRetAdic(BigDecimal vlrTotalNRetAdic) {
		this.vlrTotalNRetAdic = vlrTotalNRetAdic;
	}

	public SimNao getContribuinteCPRB() {
		return contribuinteCPRB;
	}

	public void setContribuinteCPRB(SimNao contribuinteCPRB) {
		this.contribuinteCPRB = contribuinteCPRB;
	}

	public StatusLoteReinf getStatusLoteReinf() {
		return statusLoteReinf;
	}

	public void setStatusLoteReinf(StatusLoteReinf statusLoteReinf) {
		this.statusLoteReinf = statusLoteReinf;
	}

	public BigDecimal getValorBaseRetornoReceita() {
		return valorBaseRetornoReceita;
	}

	public void setValorBaseRetornoReceita(BigDecimal valorBaseRetornoReceita) {
		this.valorBaseRetornoReceita = valorBaseRetornoReceita;
	}

	public BigDecimal getValorImpostoRetornoReceita() {
		return valorImpostoRetornoReceita;
	}

	public void setValorImpostoRetornoReceita(BigDecimal valorImpostoRetornoReceita) {
		this.valorImpostoRetornoReceita = valorImpostoRetornoReceita;
	}

	public Date getDataReciboReceita() {
		return dataReciboReceita;
	}

	public void setDataReciboReceita(Date dataReciboReceita) {
		this.dataReciboReceita = dataReciboReceita;
	}

	public String getPerApur() {
		return perApur;
	}

	public void setPerApur(String perApur) {
		this.perApur = perApur;
	}	
}
