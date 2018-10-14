package br.com.pinhoinformatica.reinf.entity.reinf;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.StatusLoteReinf;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbiente;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbienteEntity;
import java.util.List;
import javax.validation.Valid;
import javax.persistence.CascadeType;
import com.powerlogic.jcompany.domain.validation.PlcValMultiplicity;
import javax.persistence.OneToMany;
import com.powerlogic.jcompany.domain.validation.PlcValDuplicity;


@MappedSuperclass
public abstract class ReinfLotes extends AppBaseEntity {

	
	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEventosEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="reinfLotes")
	@ForeignKey(name="FK_REINFLOTESEVENTOS_REINFLOTES")
	@PlcValDuplicity(property="loteEvento")
	@PlcValMultiplicity(referenceProperty="loteEvento",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.ReinfLotesEventosEntity}")
	@Valid
	private List<ReinfLotesEventos> reinfLotesEventos;


	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_REINF_LOTES")
	@Column(name="ID_REINF_LOTES")
	private Long id;


	
	@ManyToOne (targetEntity = CnpjEmpresaRaizReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTES_CNPJEMPRESARAIZREINF")
	@JoinColumn(name="ID_EMPRESA_RAIZ_CNPJ")
	@NotNull
	private CnpjEmpresaRaizReinf cnpjEmpresaRaizReinf;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=5)
	private StatusLoteReinf statusLoteReinf;
	
	@ManyToOne (targetEntity = PeriodoApuracaoReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTES_PERIODOAPURACAOREINF")
	@NotNull
	private PeriodoApuracaoReinf periodoApuracaoReinf;
	
	@ManyToOne (targetEntity = TipoAmbienteEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTES_TIPOAMBIENTE")
	@NotNull
	private TipoAmbiente tipoAmbiente;
	
	@NotNull
	@Size(max = 36)
	private String numLote;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataGeracao;
	
	@NotNull
	@Size(max = 14)
	private String cnpjPrestador;
	
	@NotNull
	@Digits(integer=13, fraction=2)
	private BigDecimal valorBaseInss;
	
	@NotNull
	@Digits(integer=13, fraction=2)
	private BigDecimal valorImpostoInss;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public CnpjEmpresaRaizReinf getCnpjEmpresaRaizReinf() {
		return cnpjEmpresaRaizReinf;
	}

	public void setCnpjEmpresaRaizReinf(CnpjEmpresaRaizReinf cnpjEmpresaRaizReinf) {
		this.cnpjEmpresaRaizReinf=cnpjEmpresaRaizReinf;
	}

	public StatusLoteReinf getStatusLoteReinf() {
		return statusLoteReinf;
	}

	public void setStatusLoteReinf(StatusLoteReinf statusLoteReinf) {
		this.statusLoteReinf=statusLoteReinf;
	}

	public PeriodoApuracaoReinf getPeriodoApuracaoReinf() {
		return periodoApuracaoReinf;
	}

	public void setPeriodoApuracaoReinf(PeriodoApuracaoReinf periodoApuracaoReinf) {
		this.periodoApuracaoReinf=periodoApuracaoReinf;
	}

	public TipoAmbiente getTipoAmbiente() {
		return tipoAmbiente;
	}

	public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
		this.tipoAmbiente=tipoAmbiente;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String numLote) {
		this.numLote=numLote;
	}

	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao=dataGeracao;
	}

	public String getCnpjPrestador() {
		return cnpjPrestador;
	}

	public void setCnpjPrestador(String cnpjPrestador) {
		this.cnpjPrestador=cnpjPrestador;
	}

	public BigDecimal getValorBaseInss() {
		return valorBaseInss;
	}

	public void setValorBaseInss(BigDecimal valorBaseInss) {
		this.valorBaseInss=valorBaseInss;
	}

	public BigDecimal getValorImpostoInss() {
		return valorImpostoInss;
	}

	public void setValorImpostoInss(BigDecimal valorImpostoInss) {
		this.valorImpostoInss=valorImpostoInss;
	}

	public List<ReinfLotesEventos> getReinfLotesEventos() {
		return reinfLotesEventos;
	}

	public void setReinfLotesEventos(List<ReinfLotesEventos> reinfLotesEventos) {
		this.reinfLotesEventos=reinfLotesEventos;
	}

}
