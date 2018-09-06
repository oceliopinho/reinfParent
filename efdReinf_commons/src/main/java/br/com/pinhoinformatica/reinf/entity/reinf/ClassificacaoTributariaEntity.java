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
@Table(name="TB_CLASSIFICACAO_TRIBUTARIA", schema="reinf")
@SequenceGenerator(name="SE_CLASSIFICACAO_TRIBUTARIA", sequenceName="REINF.SE_CLASSIFICACAO_TRIBUTARIA")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ClassificacaoTributariaEntity.queryMan", query="from ClassificacaoTributariaEntity"),
	@NamedQuery(name="ClassificacaoTributariaEntity.querySel", query="select id as id, descClassificacao as descClassificacao from ClassificacaoTributariaEntity order by id asc"),
	@NamedQuery(name="ClassificacaoTributariaEntity.querySelLookup", query="select id as id, descClassificacao as descClassificacao from ClassificacaoTributariaEntity where id = ? order by id asc")
})
public class ClassificacaoTributariaEntity extends ClassificacaoTributaria {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ClassificacaoTributariaEntity() {
    }
	@Override
	public String toString() {
		return getDescClassificacao();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassificacaoTributaria other = (ClassificacaoTributaria) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
