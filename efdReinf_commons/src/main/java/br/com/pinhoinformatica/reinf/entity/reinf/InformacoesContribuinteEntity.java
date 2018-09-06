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
@Table(name="TB_INFORMACOES_CONTRIBUINTE", schema="reinf")
@SequenceGenerator(name="SE_INFORMACOES_CONTRIBUINTE", sequenceName="REINF.SE_INFORMACOES_CONTRIBUINTE")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="InformacoesContribuinteEntity.queryMan", query="from InformacoesContribuinteEntity"),
	@NamedQuery(name="InformacoesContribuinteEntity.querySel", query="select obj.id as id, obj1.id as tipoAmbiente_id , obj1.tipoAmbienteDescricao as tipoAmbiente_tipoAmbienteDescricao, obj2.id as empresaRaizCnpj_id , obj2.cnpjRaiz as empresaRaizCnpj_cnpjRaiz, obj3.id as periodoApuracaoReinf_id , obj3.perApur as periodoApuracaoReinf_perApur from InformacoesContribuinteEntity obj left outer join obj.tipoAmbiente as obj1 left outer join obj.empresaRaizCnpj as obj2 left outer join obj.periodoApuracaoReinf as obj3 order by obj.id asc"),
	@NamedQuery(name="InformacoesContribuinteEntity.querySelLookup", query="select id as id, perApur as perApur from InformacoesContribuinteEntity where id = ? order by id asc")
})
public class InformacoesContribuinteEntity extends InformacoesContribuinte {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public InformacoesContribuinteEntity() {
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
		InformacoesContribuinte other = (InformacoesContribuinte) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
