package br.com.pinhoinformatica.reinf.entity.dominio;

/**
 * Enum de domínio discreto gerada automaticamente pelo assistente do jCompany.
 */
public enum TipoInscricao {
    
	Um("{tipoInscricao.Um}",1),
	Dois("{tipoInscricao.Dois}",2);

	
    /**
     * @return Retorna o codigo.
     */
     
	private String label;
	private Integer tpInsc;
	
    private TipoInscricao(String label, Integer tpInsc) {
    	this.label = label;
    	this.tpInsc = tpInsc;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getTpInsc() {
		return tpInsc;
	}

	public void setTpInsc(Integer tpInsc) {
		this.tpInsc = tpInsc;
	}	
}

