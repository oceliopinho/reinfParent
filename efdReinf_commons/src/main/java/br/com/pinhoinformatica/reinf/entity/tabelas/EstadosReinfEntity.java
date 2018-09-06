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
@Table(name="VW_ESTADOS", schema="bridge")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="EstadosReinfEntity.queryMan", query="from EstadosReinfEntity"),
	@NamedQuery(name="EstadosReinfEntity.querySel", query="select obj.id as id, obj1.id as codPais_id , obj1.siglaPais as codPais_siglaPais, obj.siglaEstado as siglaEstado from EstadosReinfEntity obj left outer join obj.codPais as obj1 order by obj.siglaEstado asc"),
	@NamedQuery(name="EstadosReinfEntity.querySelLookup", query="select id as id, siglaEstado as siglaEstado from EstadosReinfEntity where id = ? order by id asc")
})
public class EstadosReinfEntity extends EstadosReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public EstadosReinfEntity() {
    }
	@Override
	public String toString() {
		return getSiglaEstado();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadosReinf other = (EstadosReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
