package br.com.pinhoinformatica.reinf.facade;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefault;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcFacade;
import com.powerlogic.jcompany.facade.PlcFacadeImpl;

import br.com.pinhoinformatica.bridge.model.utils.UtilsBridge;
import br.com.pinhoinformatica.reinf.model.repository.PeriodoApuracaoReinfRepository;

@QPlcDefault
@SPlcFacade
public class AppFacadeImpl extends PlcFacadeImpl implements IAppFacade {
	
	@Inject private UtilsBridge utilsBridge;
	@Inject private PeriodoApuracaoReinfRepository periodoApuracaoReinfRepository;
	
	public Integer removerArquivos(File f) throws IOException {
		return utilsBridge.removerArquivos(f);
	}
	
	public String criarPeriodoReinf() {
		return periodoApuracaoReinfRepository.criarPeriodoReinf();
	}
	
	public String gerarPeriodoReinf(IAppFacade facade, String login, Long idPeriodo) {
		return periodoApuracaoReinfRepository.gerarPeriodoReinf(facade, login, idPeriodo);
	}
	
	public void enviarEmailException(String subject, String bodyMail, Boolean emailAttachment) {
		
	}
}
