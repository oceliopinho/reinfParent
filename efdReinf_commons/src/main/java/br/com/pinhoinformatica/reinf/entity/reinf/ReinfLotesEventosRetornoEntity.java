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
@Table(name="TB_REINF_LOTES_EVENTOS_RETORNO", schema="reinf")
@SequenceGenerator(name="SE_REINF_LOTES_EVENTOS_RETORNO", sequenceName="REINF.SE_REINF_LOTES_EVENTOS_RETORNO")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ReinfLotesEventosRetornoEntity.querySelLookup", query="select id as id, localizacaoErroAviso as localizacaoErroAviso from ReinfLotesEventosRetornoEntity where id = ? order by id asc")
})
public class ReinfLotesEventosRetornoEntity extends ReinfLotesEventosRetorno {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ReinfLotesEventosRetornoEntity() {
    }
	@Override
	public String toString() {
		return getLocalizacaoErroAviso();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReinfLotesEventosRetorno other = (ReinfLotesEventosRetorno) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
