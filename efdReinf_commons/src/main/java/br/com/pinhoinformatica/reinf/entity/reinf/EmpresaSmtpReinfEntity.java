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
@Table(name="TB_EMPRESA_SMTP", schema="bridge")
@SequenceGenerator(name="SE_EMPRESA_SMTP", sequenceName="BRIDGE.SE_EMPRESA_SMTP")
@Access(AccessType.FIELD)


@NamedQueries({
	@NamedQuery(name="EmpresaSmtpReinfEntity.querySelLookup", query="select id as id, smtpServer as smtpServer from EmpresaSmtpReinfEntity where id = ? order by id asc")
})
public class EmpresaSmtpReinfEntity extends EmpresaSmtpReinf {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public EmpresaSmtpReinfEntity() {
    }
	@Override
	public String toString() {
		return getSmtpServer();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaSmtpReinf other = (EmpresaSmtpReinf) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
