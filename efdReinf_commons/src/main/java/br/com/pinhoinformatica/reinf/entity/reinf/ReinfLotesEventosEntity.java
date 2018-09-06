package br.com.pinhoinformatica.reinf.entity.reinf;


import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.AccessType;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;
import javax.persistence.Access;
/**
 * Classe Concreta gerada a partir do assistente
 */
@SPlcEntity
@Entity
@Table(name="TB_REINF_LOTES_EVENTOS", schema="reinf")
@SequenceGenerator(name="SE_REINF_LOTES_EVENTOS", sequenceName="REINF.SE_REINF_LOTES_EVENTOS")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ReinfLotesEventosEntity.querySelLookup", query="select id as id, loteEvento as loteEvento from ReinfLotesEventosEntity where id = ? order by id asc")
})
public class ReinfLotesEventosEntity extends ReinfLotesEventos {

	private transient String estadoSubDetalhePlc="E"; // E-expandido, R-retraido


	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ReinfLotesEventosEntity() {
    }
	@Override
	public String toString() {
		return getLoteEvento();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReinfLotesEventos other = (ReinfLotesEventos) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	public String getEstadoSubDetalhePlc() {
		return estadoSubDetalhePlc;
	}

	public void setEstadoSubDetalhePlc(String estadoSubDetalhePlc) {
		this.estadoSubDetalhePlc=estadoSubDetalhePlc;
	}

}
