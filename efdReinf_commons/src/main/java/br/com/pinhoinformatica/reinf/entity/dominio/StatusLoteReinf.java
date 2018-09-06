package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum StatusLoteReinf {
    
	A("{statusLoteReinf.A}"),
	V("{statusLoteReinf.V}"),
	EV("{statusLoteReinf.EV}"),
	AR("{statusLoteReinf.AR}"),
	EAR("{statusLoteReinf.EAR}"),
	RCR("{statusLoteReinf.RCR}");

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
    
    private StatusLoteReinf(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
	
}
