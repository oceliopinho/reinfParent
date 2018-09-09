package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum StatusApuracaoReinf {
    
	Aberto("{statusApuracaoReinf.Aberto}"),
	Gerado("{statusApuracaoReinf.Gerado}"),
	Fechado("{statusApuracaoReinf.Fechado}");

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
    
    private StatusApuracaoReinf(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
	
}
