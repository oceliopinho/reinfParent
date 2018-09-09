package br.com.pinhoinformatica.reinf.entity.reinf;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.IndSituacaoPessoaJuridica;
import br.com.pinhoinformatica.reinf.entity.dominio.SimNao;
import br.com.pinhoinformatica.reinf.entity.dominio.SituacaoPessoaJuridica;
import br.com.pinhoinformatica.reinf.entity.dominio.TipoCadastroContribuinte;
import br.com.pinhoinformatica.reinf.entity.dominio.TipoInscricao;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbiente;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.ForeignKey;
import javax.validation.constraints.NotNull;
import javax.persistence.EnumType;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import br.com.pinhoinformatica.reinf.entity.tabelas.TipoAmbienteEntity;
import javax.persistence.Id;




@MappedSuperclass
public abstract class InformacoesContribuinte extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_INFORMACOES_CONTRIBUINTE")
	@Column(name="ID_INFORMACOES_CONTRIBUINTE")
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=1)
	private TipoCadastroContribuinte tipoCadastroContribuinte;
	
	@ManyToOne (targetEntity = TipoAmbienteEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_INFORMACOESCONTRIBUINTE_TIPOAMBIENTE")
	@NotNull
	private TipoAmbiente tipoAmbiente;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=5)
	private TipoInscricao tipoInscricao;
	
	@ManyToOne (targetEntity = CnpjEmpresaRaizReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_INFORMACOESCONTRIBUINTE_EMPRESARAIZCNPJ")
	@NotNull
	private CnpjEmpresaRaizReinf empresaRaizCnpj;
	
	@ManyToOne (targetEntity = PeriodoApuracaoReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_INFORMACOESCONTRIBUINTE_PERIODOAPURACAOREINF")
	@NotNull
	private PeriodoApuracaoReinf periodoApuracaoReinf;
	
	@NotNull
	@Size(max = 7)
	private String perApur;
	
	@ManyToOne (targetEntity = ClassificacaoTributariaEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_INFORMACOESCONTRIBUINTE_CLASSIFICACAOTRIBUTARIA")
	@NotNull
	private ClassificacaoTributaria classificacaoTributaria;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=1)
	private SimNao indEscrituracao;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=1)
	private SimNao indDesoneracao;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=1)
	private SimNao indAcordoIsenMulta;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(length=6)
	private IndSituacaoPessoaJuridica indSituacaoPessoaJuridica;
	
	@ManyToOne (targetEntity = EmpresaReinfEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_SERVICOSTOMADOS_EMPRESA")
	@NotNull
	private EmpresaReinf empresaContatoReinf;	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public TipoAmbiente getTipoAmbiente() {
		return tipoAmbiente;
	}

	public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
		this.tipoAmbiente=tipoAmbiente;
	}

	public TipoInscricao getTipoInscricao() {
		return tipoInscricao;
	}

	public void setTipoInscricao(TipoInscricao tipoInscricao) {
		this.tipoInscricao=tipoInscricao;
	}

	public CnpjEmpresaRaizReinf getEmpresaRaizCnpj() {
		return empresaRaizCnpj;
	}

	public void setEmpresaRaizCnpj(CnpjEmpresaRaizReinf empresaRaizCnpj) {
		this.empresaRaizCnpj=empresaRaizCnpj;
	}

	public PeriodoApuracaoReinf getPeriodoApuracaoReinf() {
		return periodoApuracaoReinf;
	}

	public void setPeriodoApuracaoReinf(PeriodoApuracaoReinf periodoApuracaoReinf) {
		this.periodoApuracaoReinf=periodoApuracaoReinf;
	}

	public String getPerApur() {
		return perApur;
	}

	public void setPerApur(String perApur) {
		this.perApur=perApur;
	}

	public ClassificacaoTributaria getClassificacaoTributaria() {
		return classificacaoTributaria;
	}

	public void setClassificacaoTributaria(ClassificacaoTributaria classificacaoTributaria) {
		this.classificacaoTributaria=classificacaoTributaria;
	}

	public SimNao getIndEscrituracao() {
		return indEscrituracao;
	}

	public void setIndEscrituracao(SimNao indEscrituracao) {
		this.indEscrituracao=indEscrituracao;
	}

	public SimNao getIndDesoneracao() {
		return indDesoneracao;
	}

	public void setIndDesoneracao(SimNao indDesoneracao) {
		this.indDesoneracao=indDesoneracao;
	}

	public SimNao getIndAcordoIsenMulta() {
		return indAcordoIsenMulta;
	}

	public void setIndAcordoIsenMulta(SimNao indAcordoIsenMulta) {
		this.indAcordoIsenMulta=indAcordoIsenMulta;
	}

	public EmpresaReinf getEmpresaContatoReinf() {
		return empresaContatoReinf;
	}

	public void setEmpresaContatoReinf(EmpresaReinf empresaContatoReinf) {
		this.empresaContatoReinf = empresaContatoReinf;
	}

	public TipoCadastroContribuinte getTipoCadastroContribuinte() {
		return tipoCadastroContribuinte;
	}

	public void setTipoCadastroContribuinte(TipoCadastroContribuinte tipoCadastroContribuinte) {
		this.tipoCadastroContribuinte = tipoCadastroContribuinte;
	}

	public IndSituacaoPessoaJuridica getIndSituacaoPessoaJuridica() {
		return indSituacaoPessoaJuridica;
	}

	public void setIndSituacaoPessoaJuridica(IndSituacaoPessoaJuridica indSituacaoPessoaJuridica) {
		this.indSituacaoPessoaJuridica = indSituacaoPessoaJuridica;
	}	
}