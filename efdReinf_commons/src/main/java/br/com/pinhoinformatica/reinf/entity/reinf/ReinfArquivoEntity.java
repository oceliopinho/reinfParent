package br.com.pinhoinformatica.reinf.entity.reinf;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.powerlogic.jcompany.commons.PlcFileContent;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;
import com.powerlogic.jcompany.commons.file.PlcBaseMapFile;


@SuppressWarnings("serial")
@SPlcEntity
@Entity
@Table(name="TB_REINF_ARQUIVO", schema="reinf")
@SequenceGenerator(name="SE_ARQUIVO_PLC",sequenceName="REINF.SE_REINF_ARQUIVO_PLC")
public class ReinfArquivoEntity extends PlcBaseMapFile {
	
	@OneToOne(targetEntity=ReinfArquivoConteudoEntity.class, fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    public PlcFileContent getBinaryContent() {
    	return( conteudo );
    }
}	
