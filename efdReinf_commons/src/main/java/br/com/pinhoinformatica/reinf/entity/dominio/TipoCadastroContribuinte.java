package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum TipoCadastroContribuinte {
    
	A("{tipoCadastroContribuinte.A}"),
	I("{tipoCadastroContribuinte.I}"),
	E("{tipoCadastroContribuinte.E}");

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
    
    private TipoCadastroContribuinte(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
	
}
