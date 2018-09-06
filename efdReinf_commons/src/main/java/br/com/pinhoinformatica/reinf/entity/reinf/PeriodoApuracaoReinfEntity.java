package br.com.pinhoinformatica.reinf.entity.reinf;


import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import br.com.pinhoinformatica.reinf.entity.dominio.StatusApuracaoReinf;
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
@Table(name="TB_PERIODO_APURACAO_REINF", schema="reinf")
@SequenceGenerator(name="SE_PERIODO_APURACAO_REINF", sequenceName="REINF.SE_PERIODO_APURACAO_REINF")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="PeriodoApuracaoReinfEntity.queryMan", query="from PeriodoApuracaoReinfEntity"),
	@NamedQuery(name="PeriodoApuracaoReinfEntity.querySel", query="select id as id, perApur as perApur from PeriodoApuracaoReinfEntity order by id asc"),
	@NamedQuery(name="PeriodoApuracaoReinfEntity.querySelLookup", query="select id as id, perApur as perApur from PeriodoApuracaoReinfEntity where id = ? order by id asc")
})
public class PeriodoApuracaoReinfEntity extends PeriodoApuracaoReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public PeriodoApuracaoReinfEntity() {
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
		PeriodoApuracaoReinf other = (PeriodoApuracaoReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
