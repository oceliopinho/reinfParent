package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum IndSituacaoPessoaJuridica {
    
	Zero("{indSituacaoPessoaJuridica.Zero}"),
	Um("{indSituacaoPessoaJuridica.Um}"),
	Dois("{indSituacaoPessoaJuridica.Dois}"),
	Tres("{indSituacaoPessoaJuridica.Tres}"),
	Quatro("{indSituacaoPessoaJuridica.Quatro}");

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
    
    private IndSituacaoPessoaJuridica(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
	
}
