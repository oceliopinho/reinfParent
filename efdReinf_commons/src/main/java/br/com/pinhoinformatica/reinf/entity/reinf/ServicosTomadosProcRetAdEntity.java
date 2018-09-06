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
@Table(name="TB_SERVICOS_TOMADOS_PROC_RET_AD", schema="reinf")
@SequenceGenerator(name="SE_SERVICOS_TOMADOS_PROC_RET_AD", sequenceName="REINF.SE_SERVICOS_TOMADOS_PROC_RET_AD")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ServicosTomadosProcRetAdEntity.querySelLookup", query="select id as id, nrProcRetAdic as nrProcRetAdic from ServicosTomadosProcRetAdEntity where id = ? order by id asc")
})
public class ServicosTomadosProcRetAdEntity extends ServicosTomadosProcRetAd {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ServicosTomadosProcRetAdEntity() {
    }
	@Override
	public String toString() {
		return getNrProcRetAdic();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicosTomadosProcRetAd other = (ServicosTomadosProcRetAd) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
