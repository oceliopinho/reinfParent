package br.com.pinhoinformatica.reinf.facade;

import java.io.File;
import java.io.IOException;

import com.powerlogic.jcompany.commons.facade.IPlcFacade;

public interface IAppFacade extends IPlcFacade {

	Integer removerArquivos(File f) throws IOException;
	
	String criarPeriodoReinf();
	
}
