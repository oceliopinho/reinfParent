package br.com.pinhoinformatica.reinf.facade;

import java.io.File;
import java.io.IOException;

import com.powerlogic.jcompany.commons.facade.IPlcFacade;

public interface IAppFacade extends IPlcFacade {

	Integer removerArquivos(File f) throws IOException;
	
	String criarPeriodoReinf();

	String gerarPeriodoReinf(IAppFacade facade, String login, Long idPeriodo);

	void enviarEmailException(String subject, String bodyMail, Boolean emailAttachment);

	String enviaLoteEventoReInfSefaz(IAppFacade facade, String nomeServidor, String email);
	
}
