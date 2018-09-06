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
@Table(name="VW_MODELO_DOC", schema="bridge")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ModeloDocReinfEntity.queryMan", query="from ModeloDocReinfEntity"),
	@NamedQuery(name="ModeloDocReinfEntity.querySel", query="select id as id, descModelo as descModelo from ModeloDocReinfEntity order by id asc"),
	@NamedQuery(name="ModeloDocReinfEntity.querySelLookup", query="select id as id, descModelo as descModelo from ModeloDocReinfEntity where id = ? order by id asc")
})
public class ModeloDocReinfEntity extends ModeloDocReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ModeloDocReinfEntity() {
    }
	@Override
	public String toString() {
		return getDescModelo();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModeloDocReinf other = (ModeloDocReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
