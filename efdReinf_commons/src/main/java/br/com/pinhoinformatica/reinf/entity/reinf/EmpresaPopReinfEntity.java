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
@Table(name="TB_EMPRESA_POP", schema="bridge")
@SequenceGenerator(name="SE_EMPRESA_POP", sequenceName="BRIDGE.SE_EMPRESA_POP")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="EmpresaPopReinfEntity.querySelLookup", query="select id as id, popServer as popServer from EmpresaPopReinfEntity where id = ? order by id asc")
})
public class EmpresaPopReinfEntity extends EmpresaPopReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public EmpresaPopReinfEntity() {
    }
	@Override
	public String toString() {
		return getPopServer();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaPopReinf other = (EmpresaPopReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
