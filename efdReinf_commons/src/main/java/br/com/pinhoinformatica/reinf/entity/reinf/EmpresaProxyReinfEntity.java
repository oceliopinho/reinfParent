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
@Table(name="TB_EMPRESA_PROXY", schema="bridge")
@SequenceGenerator(name="SE_EMPRESA_PROXY", sequenceName="BRIDGE.SE_EMPRESA_PROXY")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="EmpresaProxyReinfEntity.querySelLookup", query="select id as id, proxyServer as proxyServer from EmpresaProxyReinfEntity where id = ? order by id asc")
})
public class EmpresaProxyReinfEntity extends EmpresaProxyReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public EmpresaProxyReinfEntity() {
    }
	@Override
	public String toString() {
		return getProxyServer();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaProxyReinf other = (EmpresaProxyReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
