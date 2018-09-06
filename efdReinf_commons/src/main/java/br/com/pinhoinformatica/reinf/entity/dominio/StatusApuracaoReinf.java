package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum StatusApuracaoReinf {
    
	Aberto("{statusApuracaoReinf.Aberto}"),
	CargaR2010Realizada("{statusApuracaoReinf.CargaR2010Realizada}"),
	CargaR2010Revertida("{statusApuracaoReinf.CargaR2010Revertida}"),
	CargaR2099Realizada("{statusApuracaoReinf.CargaR2099Realizada}"),
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
