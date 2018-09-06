package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum TipoProcesso {
    
	TP1("{tipoProcesso.TP1}");

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
    
    private TipoProcesso(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
	
}
