package br.gov.cesarschool.poo.bonusvendas.negocio;

public class ResultadoInclusaoVendedor {
    private long numeroCaixaDeBonus;
    private String mensagemErroValidacao;
    
    public ResultadoInclusaoVendedor(long caixa, String mensagem) {
    	this.numeroCaixaDeBonus = caixa;
    	this.mensagemErroValidacao = mensagem;
    }
    
    
    public long getNumeroCaixaDeBonus() {
    	return this.numeroCaixaDeBonus;
    }
    
    public String getMensagemErroValidacao() {
    	return this.mensagemErroValidacao;
    }
    
}
