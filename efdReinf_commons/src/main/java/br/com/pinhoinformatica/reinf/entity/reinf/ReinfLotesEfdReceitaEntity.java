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
@Table(name="TB_REINF_LOTES_EFD_RECEITA", schema="reinf")
@SequenceGenerator(name="SE_REINF_LOTES_EFD_RECEITA", sequenceName="REINF.SE_REINF_LOTES_EFD_RECEITA")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="ReinfLotesEfdReceitaEntity.queryMan", query="from ReinfLotesEfdReceitaEntity"),
	@NamedQuery(name="ReinfLotesEfdReceitaEntity.querySelLookup", query="select id as id, numLote as numLote from ReinfLotesEfdReceitaEntity where id = ? order by id asc")
})
public class ReinfLotesEfdReceitaEntity extends ReinfLotesEfdReceita {

	private transient String periodoApuracaoReinfAuxLookup;


	private transient String empresaReinfAuxLookup;


	private transient String cnpjEmpresaRaizReinfAuxLookup;


	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public ReinfLotesEfdReceitaEntity() {
    }
	@Override
	public String toString() {
		return getNumLote();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReinfLotesEfdReceita other = (ReinfLotesEfdReceita) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}


	public void setCnpjEmpresaRaizReinfAuxLookup(String cnpjEmpresaRaizReinfAuxLookup) {
		this.cnpjEmpresaRaizReinfAuxLookup=cnpjEmpresaRaizReinfAuxLookup;
	}


	public void setEmpresaReinfAuxLookup(String empresaReinfAuxLookup) {
		this.empresaReinfAuxLookup=empresaReinfAuxLookup;
	}


	public void setPeriodoApuracaoReinfAuxLookup(String periodoApuracaoReinfAuxLookup) {
		this.periodoApuracaoReinfAuxLookup=periodoApuracaoReinfAuxLookup;
	}

}
