package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum TipoCertificado {
    
	A("{tipoCertificado.A}"),
	C("{tipoCertificado.C}"),
	T("{tipoCertificado.T}"),
	W("{tipoCertificado.W}");

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
    
    private TipoCertificado(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
	
}
