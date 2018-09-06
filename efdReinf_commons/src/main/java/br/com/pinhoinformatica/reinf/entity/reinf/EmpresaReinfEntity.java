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
@Table(name="TB_EMPRESA", schema="bridge")
@SequenceGenerator(name="SE_EMPRESA", sequenceName="BRIDGE.SE_EMPRESA")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="EmpresaReinfEntity.queryMan", query="from EmpresaReinfEntity"),
	@NamedQuery(name="EmpresaReinfEntity.querySel", query="select obj.id as id, obj1.id as empresaRaizCnpj_id , obj1.cnpjRaiz as empresaRaizCnpj_cnpjRaiz, obj.empCnpjCpf as empCnpjCpf, obj.empXnome as empXnome from EmpresaReinfEntity obj left outer join obj.empresaRaizCnpj as obj1 order by obj.empCnpjCpf asc"),
	@NamedQuery(name="EmpresaReinfEntity.querySelLookup", query="select id as id, empCnpjCpf as empCnpjCpf from EmpresaReinfEntity where id = ? order by id asc")
})
public class EmpresaReinfEntity extends EmpresaReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public EmpresaReinfEntity() {
    }
	@Override
	public String toString() {
		return getEmpCnpjCpf();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaReinf other = (EmpresaReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
