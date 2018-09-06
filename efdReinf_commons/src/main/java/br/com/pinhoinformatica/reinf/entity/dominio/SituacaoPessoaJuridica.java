package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum SituacaoPessoaJuridica {
    
	SPJ0("{situacaoPessoaJuridica.SPJ0}"),
	SPJ1("{situacaoPessoaJuridica.SPJ1}"),
	SPJ2("{situacaoPessoaJuridica.SPJ2}"),
	SPJ3("{situacaoPessoaJuridica.SPJ3}");

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
    
    private SituacaoPessoaJuridica(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
	
}
