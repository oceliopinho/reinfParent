package br.com.pinhoinformatica.reinf.entity.reinf;


import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
@Table(name="TB_EMPRESA_RAIZ_CNPJ", schema="bridge")
@SequenceGenerator(name="SE_EMPRESA_RAIZ_CNPJ", sequenceName="BRIDGE.EMPRESA_RAIZ_CNPJ")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="CnpjEmpresaRaizReinfEntity.queryMan", query="from CnpjEmpresaRaizReinfEntity"),
	@NamedQuery(name="CnpjEmpresaRaizReinfEntity.querySel", query="select id as id, cnpjRaiz as cnpjRaiz, nomeFantasia as nomeFantasia from CnpjEmpresaRaizReinfEntity order by cnpjRaiz asc"),
	@NamedQuery(name="CnpjEmpresaRaizReinfEntity.querySelLookup", query="select id as id, cnpjRaiz as cnpjRaiz, nomeFantasia as nomeFantasia from CnpjEmpresaRaizReinfEntity where id = ? order by id asc")
})
public class CnpjEmpresaRaizReinfEntity extends CnpjEmpresaRaizReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public CnpjEmpresaRaizReinfEntity() {
    }
	@Override
	public String toString() {
		return getCnpjRaiz() + " - " + getNomeFantasia();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CnpjEmpresaRaizReinf other = (CnpjEmpresaRaizReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
