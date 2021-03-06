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
@Table(name="TB_SERVICOS_TOMADOS", schema="reinf")
@SequenceGenerator(name="SE_SERVICOS_TOMADOS", sequenceName="REINF.SE_SERVICOS_TOMADOS")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ServicosTomadosEntity.queryMan", query="from ServicosTomadosEntity"),
	@NamedQuery(name="ServicosTomadosEntity.querySel", query="select obj.id as id, obj1.id as periodoApuracaoReinf_id , obj1.perApur as periodoApuracaoReinf_perApur, obj2.id as empresaRaizCnpj_id , obj2.cnpjRaiz as empresaRaizCnpj_cnpjRaiz, obj3.id as empresa_id , obj3.empCnpjCpf as empresa_empCnpjCpf from ServicosTomadosEntity obj left outer join obj.periodoApuracaoReinf as obj1 left outer join obj.empresaRaizCnpj as obj2 left outer join obj.empresa as obj3 order by obj.id asc"),
	@NamedQuery(name="ServicosTomadosEntity.querySelLookup", query="select id as id, perApur as perApur from ServicosTomadosEntity where id = ? order by id asc")
})
public class ServicosTomadosEntity extends ServicosTomados {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ServicosTomadosEntity() {
    }
	@Override
	public String toString() {
		return getPerApur();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicosTomados other = (ServicosTomados) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
