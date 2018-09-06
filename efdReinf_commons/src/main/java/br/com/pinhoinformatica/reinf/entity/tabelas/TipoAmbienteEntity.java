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
@Table(name="VW_TIPO_AMBIENTE", schema="bridge")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="TipoAmbienteEntity.queryMan", query="from TipoAmbienteEntity"),
	@NamedQuery(name="TipoAmbienteEntity.querySel", query="select id as id, tipoAmbienteDescricao as tipoAmbienteDescricao from TipoAmbienteEntity order by id asc"),
	@NamedQuery(name="TipoAmbienteEntity.querySelLookup", query="select id as id, tipoAmbienteDescricao as tipoAmbienteDescricao from TipoAmbienteEntity where id = ? order by id asc")
})
public class TipoAmbienteEntity extends TipoAmbiente {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public TipoAmbienteEntity() {
    }
	@Override
	public String toString() {
		return getTipoAmbienteDescricao();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoAmbiente other = (TipoAmbiente) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
