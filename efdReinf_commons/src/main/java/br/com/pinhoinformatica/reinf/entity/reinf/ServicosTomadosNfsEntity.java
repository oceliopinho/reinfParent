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
@Table(name="TB_SERVICOS_TOMADOS_NFS", schema="reinf")
@SequenceGenerator(name="SE_SERVICOS_TOMADOS_NFS", sequenceName="REINF.SE_SERVICOS_TOMADOS_NFS")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ServicosTomadosNfsEntity.querySelLookup", query="select id as id, numDocto as numDocto from ServicosTomadosNfsEntity where id = ? order by id asc")
})
public class ServicosTomadosNfsEntity extends ServicosTomadosNfs {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ServicosTomadosNfsEntity() {
    }
	@Override
	public String toString() {
		return getNumDocto();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicosTomadosNfs other = (ServicosTomadosNfs) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
