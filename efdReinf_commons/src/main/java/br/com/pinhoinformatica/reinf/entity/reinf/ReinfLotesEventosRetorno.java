package br.com.pinhoinformatica.reinf.entity.reinf;

import br.com.pinhoinformatica.reinf.entity.AppBaseEntity;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import com.powerlogic.jcompany.domain.validation.PlcValGroupEntityList;
import javax.persistence.Id;
import com.powerlogic.jcompany.domain.validation.PlcValRequiredIf;
import javax.persistence.Transient;


@MappedSuperclass
public abstract class ReinfLotesEventosRetorno extends AppBaseEntity {

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_REINF_LOTES_EVENTOS_RETORNO")
	@Column(name="ID_REINF_LOTES_EVENTOS_RETORNO")
	private Long id;
	
	@ManyToOne (targetEntity = ReinfLotesEventosEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTESEVENTOSRETORNO_REINFLOTESEVENTOS")
	@NotNull
	private ReinfLotesEventos reinfLotesEventos;

	

	@ManyToOne (targetEntity = ReinfMensagensEntity.class, fetch = FetchType.LAZY)
	@ForeignKey(name="FK_REINFLOTESEVENTOSRETORNO_REINFLOTESMENSAGENS")
	@NotNull(groups=PlcValGroupEntityList.class)
	@PlcValRequiredIf(dependentfield="reinfLotesMensagens",targetField="localizacaoErroAviso")
	private ReinfMensagens reinfLotesMensagens;
	
	@NotNull(groups=PlcValGroupEntityList.class)
	@Size(max = 100)
	private String localizacaoErroAviso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public ReinfMensagens getReinfLotesMensagens() {
		return reinfLotesMensagens;
	}

	public void setReinfLotesMensagens(ReinfMensagens reinfLotesMensagens) {
		this.reinfLotesMensagens=reinfLotesMensagens;
	}

	public String getLocalizacaoErroAviso() {
		return localizacaoErroAviso;
	}

	public void setLocalizacaoErroAviso(String localizacaoErroAviso) {
		this.localizacaoErroAviso=localizacaoErroAviso;
	}

	public ReinfLotesEventos getReinfLotesEventos() {
		return reinfLotesEventos;
	}

	public void setReinfLotesEventos(ReinfLotesEventos reinfLotesEventos) {
		this.reinfLotesEventos=reinfLotesEventos;
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
