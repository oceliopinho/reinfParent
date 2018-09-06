package br.com.pinhoinformatica.reinf.entity.reinf;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import br.com.pinhoinformatica.reinf.entity.dominio.StatusLoteReinf;
import java.util.List;
import javax.validation.Valid;
import javax.persistence.CascadeType;
import com.powerlogic.jcompany.domain.validation.PlcValMultiplicity;
import javax.persistence.OneToMany;
import com.powerlogic.jcompany.domain.validation.PlcValDuplicity;


@MappedSuperclass
public abstract class ReinfLotesEventos extends AppBaseEntity {

	
	@OneToMany (targetEntity = br.com.pinhoinformatica.reinf.entity.reinf.ReinfLotesEventosRetornoEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="reinfLotesEventos")
	@ForeignKey(name="FK_REINFLOTESEVENTOSRETORNO_REINFLOTESEVENTOS")
	@PlcValDuplicity(property="localizacaoErroAviso")
	@PlcValMultiplicity(referenceProperty="localizacaoErroAviso",  message="{jcompany.aplicacao.mestredetalhe.multiplicidade.ReinfLotesEventosRetornoEntity}")
	@Valid
	private List<ReinfLotesEventosRetorno> reinfLotesEventosRetorno;


	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_REINF_LOTES_EVENTOS")
	@Column(name="ID_REINF_LOTES_EVENTOS")
	private Long id;
	
	@ManyToOne (targetEntity = ReinfLotesEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTESEVENTOS_REINFLOTES")
	@NotNull
	private ReinfLotes reinfLotes;

	
	
	@NotNull
	@PlcValRequiredIf(dependentfield="nomeEvento",targetField="loteEvento")
	@Size(max = 20)
	private String nomeEvento;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="codigoEvento",targetField="loteEvento")
	@Size(max = 6)
	private String codigoEvento;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="seqEvento",targetField="loteEvento")
	@Digits(integer=5, fraction=0)
	private Integer seqEvento;
	
	@NotNull
	@Size(max = 36)
	private String loteEvento;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="dhProcess",targetField="loteEvento")
	@Size(max = 20)
	private String dhProcess;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@PlcValRequiredIf(dependentfield="statusReinfEvento",targetField="loteEvento")
	@Column(length=5)
	private StatusLoteReinf statusReinfEvento;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="statusRetorno",targetField="loteEvento")
	@Size(max = 20)
	private String statusRetorno;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="numProtocolo",targetField="loteEvento")
	@Size(max = 30)
	private String numProtocolo;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="nomeArquivo",targetField="loteEvento")
	@Size(max = 100)
	private String nomeArquivo;
	
	@NotNull
	@PlcValRequiredIf(dependentfield="idServicosTomados",targetField="loteEvento")
	@Digits(integer=5, fraction=0)
	private Long idServicosTomados;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento=nomeEvento;
	}

	public String getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(String codigoEvento) {
		this.codigoEvento=codigoEvento;
	}

	public Integer getSeqEvento() {
		return seqEvento;
	}

	public void setSeqEvento(Integer seqEvento) {
		this.seqEvento=seqEvento;
	}

	public String getLoteEvento() {
		return loteEvento;
	}

	public void setLoteEvento(String loteEvento) {
		this.loteEvento=loteEvento;
	}

	public String getDhProcess() {
		return dhProcess;
	}

	public void setDhProcess(String dhProcess) {
		this.dhProcess=dhProcess;
	}

	public StatusLoteReinf getStatusReinfEvento() {
		return statusReinfEvento;
	}

	public void setStatusReinfEvento(StatusLoteReinf statusReinfEvento) {
		this.statusReinfEvento=statusReinfEvento;
	}

	public String getStatusRetorno() {
		return statusRetorno;
	}

	public void setStatusRetorno(String statusRetorno) {
		this.statusRetorno=statusRetorno;
	}

	public String getNumProtocolo() {
		return numProtocolo;
	}

	public void setNumProtocolo(String numProtocolo) {
		this.numProtocolo=numProtocolo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo=nomeArquivo;
	}

	public Long getIdServicosTomados() {
		return idServicosTomados;
	}

	public void setIdServicosTomados(Long idServicosTomados) {
		this.idServicosTomados=idServicosTomados;
	}

	public ReinfLotes getReinfLotes() {
		return reinfLotes;
	}

	public void setReinfLotes(ReinfLotes reinfLotes) {
		this.reinfLotes=reinfLotes;
	}

	@Transient
	private String indExcPlc = "N";	

	public void setIndExcPlc(String indExcPlc) {
		this.indExcPlc = indExcPlc;
	}

	public String getIndExcPlc() {
		return indExcPlc;
	}

	public List<ReinfLotesEventosRetorno> getReinfLotesEventosRetorno() {
		return reinfLotesEventosRetorno;
	}

	public void setReinfLotesEventosRetorno(List<ReinfLotesEventosRetorno> reinfLotesEventosRetorno) {
		this.reinfLotesEventosRetorno=reinfLotesEventosRetorno;
	}

}
