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
@Table(name="TB_REINF_LOTES", schema="reinf")
@SequenceGenerator(name="SE_REINF_LOTES", sequenceName="REINF.SE_REINF_LOTES")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ReinfLotesEntity.querySelLookup", query="select id as id, numLote as numLote from ReinfLotesEntity where id = ? order by id asc")
})
public class ReinfLotesEntity extends ReinfLotes {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ReinfLotesEntity() {
    }
	@Override
	public String toString() {
		return getNumLote();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReinfLotes other = (ReinfLotes) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
