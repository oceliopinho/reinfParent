package br.com.pinhoinformatica.reinf.entity.tabelas;


import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.AccessType;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;
import javax.persistence.Access;
/**
 * Classe Concreta gerada a partir do assistente
 */
@SPlcEntity
@Entity
@Table(name="VW_PAISES", schema="bridge")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="PaisesReinfEntity.queryMan", query="from PaisesReinfEntity"),
	@NamedQuery(name="PaisesReinfEntity.querySel", query="select id as id, siglaPais as siglaPais, nomePais as nomePais from PaisesReinfEntity order by siglaPais asc"),
	@NamedQuery(name="PaisesReinfEntity.querySelLookup", query="select id as id, siglaPais as siglaPais from PaisesReinfEntity where id = ? order by id asc")
})
public class PaisesReinfEntity extends PaisesReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public PaisesReinfEntity() {
    }
	@Override
	public String toString() {
		return getSiglaPais();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaisesReinf other = (PaisesReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
