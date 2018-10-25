package br.com.pinhoinformatica.reinf.model.run;

import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import br.com.pinhoinformatica.bridge.model.utils.UtilsBridge;
import br.com.pinhoinformatica.pinhoNfeCert.model.ConfiguracoesIniciaisNfe;
import br.com.pinhoinformatica.pinhoNfeCert.model.certificado.Certificado;
import br.com.pinhoinformatica.pinhoNfeCert.model.certificado.CertificadoService;
import br.com.pinhoinformatica.pinhoNfeCert.model.certificado.TipoCertificadoA3;
import br.com.pinhoinformatica.pinhoNfeCert.model.utils.ConstantesUtil;
import br.com.pinhoinformatica.pinhoNfeCert.model.utils.Estados;
import br.com.pinhoinformatica.pinhoNfeCert.model.utils.exception.NfeException;

public class StartConfiguracoes {

	private static Provider provider;
	private static UtilsBridge utilsXml;

	public static ConfiguracoesIniciaisNfe iniciaConfigurações(Integer tipoAmbiente, String modDoc, String siglaUf, String tipoCertificado, String nomeCertificado, String senhaCertificado, String modeloCertificado, String caminhoCertificado, String dirEmp, String pastaSchemas, String caminhoCacerts, String versaoXml, String versaoNfe, Integer tempoTimeOut, URL urlWebService, boolean ativaLog) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, NfeException {
		Certificado certificado = null;
		String ambiente = null;
		Estados estado = Estados.valueOf(siglaUf);

		String arqConfigA3 = null;
		if (tipoCertificado.equals("A")) {
			try {
				certificado = CertificadoService.certificadoPfx(caminhoCertificado, senhaCertificado);
			} catch (Exception e) {				
				e.printStackTrace();
			}
		} else {
			if (tipoCertificado.equals("C") || tipoCertificado.equals("T")) {
				String nomeModeloCert = null;
				if (modeloCertificado.equals("TOKEN_ALADDIN")) {
					nomeModeloCert = "eToken";
					arqConfigA3 = utilsXml.retornaCaminhoCertificadoA3(dirEmp, tipoCertificado, nomeModeloCert);					
				} else {
					if (modeloCertificado.equals("LEITOR_SCR3310")) {
						nomeModeloCert = "SafeWeb";
						arqConfigA3 = utilsXml.retornaCaminhoCertificadoA3(dirEmp, tipoCertificado, nomeModeloCert);					
					} else {
						if (modeloCertificado.equals("LEITOR_GEMPC_PERTO")) {
							nomeModeloCert = "SmartCard";
							arqConfigA3 = utilsXml.retornaCaminhoCertificadoA3(dirEmp, tipoCertificado, nomeModeloCert);					
						} else {
							if (modeloCertificado.equals("OBERTHUR")) {
								nomeModeloCert = "Oberthur";
								arqConfigA3 = utilsXml.retornaCaminhoCertificadoA3(dirEmp, tipoCertificado, nomeModeloCert);	
							}
						}
					}
				}

				provider = new sun.security.pkcs11.SunPKCS11(arqConfigA3);     
				Security.addProvider(provider);    
				char[] pin = senhaCertificado.toCharArray();    
				KeyStore ks = KeyStore.getInstance("pkcs11", provider);    
				ks.load(null, pin);

				String alias = "";     
				Enumeration<String> aliasesEnum = ks.aliases();     
				while (aliasesEnum.hasMoreElements()) {     
					alias = (String) aliasesEnum.nextElement();     
					if (ks.isKeyEntry(alias)) 
						break;     
				}				
				
				TipoCertificadoA3 certificadoA3 = null;
				if (modeloCertificado.equals("LEITOR_SCR3310")) {
					certificadoA3 = TipoCertificadoA3.LEITOR_SCR3310;	
				} else {
					if (modeloCertificado.equals("LEITOR_GEMPC_PERTO")) {
						certificadoA3 = TipoCertificadoA3.LEITOR_GEMPC_PERTO;	
					} else {
						if (modeloCertificado.equals("OBERTHUR")) {
							certificadoA3 = TipoCertificadoA3.OBERTHUR;	
						} else {
							if (modeloCertificado.equals("TOKEN_ALADDIN")) {
								certificadoA3 = TipoCertificadoA3.TOKEN_ALADDIN;								
							}
						}
					}
				}				
				
				X509Certificate certificate = (X509Certificate) ks.getCertificate(alias); 
							
				String marca = certificadoA3.getMarca();						
				String dll   = certificadoA3.getDll();
				String senha = senhaCertificado;
				certificado  = CertificadoService.certificadoA3(marca, dll, senha, nomeCertificado);
			}
		}


		if (tipoAmbiente == 1) {
			ambiente = ConstantesUtil.AMBIENTE.PRODUCAO;
		} else {
			if (tipoAmbiente == 2) {
				ambiente = ConstantesUtil.AMBIENTE.HOMOLOGACAO;
			}
		}
		 
		return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, ambiente, modDoc, tipoCertificado, certificado, caminhoCertificado, senhaCertificado, arqConfigA3, caminhoCacerts, versaoXml, versaoNfe, pastaSchemas, urlWebService, tempoTimeOut, ativaLog);
	}
}