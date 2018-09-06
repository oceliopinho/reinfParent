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
@Table(name="TB_REINF_MENSAGENS", schema="reinf")
@SequenceGenerator(name="SE_REINF_MENSAGENS", sequenceName="REINF.SE_REINF_MENSAGENS")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ReinfMensagensEntity.queryMan", query="from ReinfMensagensEntity"),
	@NamedQuery(name="ReinfMensagensEntity.querySel", query="select id as id, codigoMensagem as codigoMensagem from ReinfMensagensEntity order by codigoMensagem asc"),
	@NamedQuery(name="ReinfMensagensEntity.querySelLookup", query="select id as id, codigoMensagem as codigoMensagem from ReinfMensagensEntity where id = ? order by id asc")
})
public class ReinfMensagensEntity extends ReinfMensagens {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ReinfMensagensEntity() {
    }
	@Override
	public String toString() {
		return getCodigoMensagem();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReinfMensagens other = (ReinfMensagens) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
