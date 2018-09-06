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
@Table(name="TB_EMPRESA_CONTATO", schema="bridge")
@SequenceGenerator(name="SE_EMPRESA_CONTATO", sequenceName="BRIDGE.SE_EMPRESA_CONTATO")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="EmpresaContatoReinfEntity.querySelLookup", query="select id as id, cpfContato as cpfContato from EmpresaContatoReinfEntity where id = ? order by id asc")
})
public class EmpresaContatoReinfEntity extends EmpresaContatoReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public EmpresaContatoReinfEntity() {
    }
	@Override
	public String toString() {
		return getCpfContato();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaContatoReinf other = (EmpresaContatoReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
