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
@Table(name="VW_CIDADES", schema="bridge")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="CidadesReinfEntity.queryMan", query="from CidadesReinfEntity"),
	@NamedQuery(name="CidadesReinfEntity.querySel", query="select obj.id as id, obj1.id as estados_id , obj1.siglaEstado as estados_siglaEstado, obj.nomeCidade as nomeCidade from CidadesReinfEntity obj left outer join obj.estados as obj1 order by obj.nomeCidade asc"),
	@NamedQuery(name="CidadesReinfEntity.querySelLookup", query="select id as id, nomeCidade as nomeCidade from CidadesReinfEntity where id = ? order by id asc")
})
public class CidadesReinfEntity extends CidadesReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public CidadesReinfEntity() {
    }
	@Override
	public String toString() {
		return getNomeCidade();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CidadesReinf other = (CidadesReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
