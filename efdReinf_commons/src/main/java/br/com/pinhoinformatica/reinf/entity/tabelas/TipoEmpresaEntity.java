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
@Table(name="VW_TIPO_EMPRESA", schema="bridge")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="TipoEmpresaEntity.queryMan", query="from TipoEmpresaEntity"),
	@NamedQuery(name="TipoEmpresaEntity.querySel", query="select id as id, descCrt as descCrt from TipoEmpresaEntity order by id asc"),
	@NamedQuery(name="TipoEmpresaEntity.querySelLookup", query="select id as id, descCrt as descCrt from TipoEmpresaEntity where id = ? order by id asc")
})
public class TipoEmpresaEntity extends TipoEmpresa {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public TipoEmpresaEntity() {
    }
	@Override
	public String toString() {
		return getDescCrt();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoEmpresa other = (TipoEmpresa) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
