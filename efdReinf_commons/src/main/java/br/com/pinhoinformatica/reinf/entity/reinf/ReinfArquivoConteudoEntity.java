package br.com.pinhoinformatica.reinf.entity.reinf;



import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;
import com.powerlogic.jcompany.commons.file.PlcBaseMapFileContent;

@SuppressWarnings("serial")
@SPlcEntity
@Entity
@Table(name="TB_REINF_ARQUIVO_CONTEUDO",schema="reinf")
@SequenceGenerator(name="SE_ARQUIVO_CONTEUDO_PLC",sequenceName="REINF.SE_REINF_ARQUIVO_CONTEUDO_PLC")
public class ReinfArquivoConteudoEntity extends PlcBaseMapFileContent {	

}
