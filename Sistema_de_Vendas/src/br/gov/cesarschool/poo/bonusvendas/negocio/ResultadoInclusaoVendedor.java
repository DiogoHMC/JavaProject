package br.gov.cesarschool.poo.bonusvendas.negocio;

public class ResultadoInclusaoVendedor {
	private long numeroCaixaDeBonus;
	private String mensagemErroValidacao;
	public ResultadoInclusaoVendedor(long numeroCaixaDeBonus, String mensagemErroValidacao) {
		super();
		this.numeroCaixaDeBonus = numeroCaixaDeBonus;
		this.mensagemErroValidacao = mensagemErroValidacao;
	}
	public long getNumeroCaixaDeBonus() {
		return numeroCaixaDeBonus;
	}
	public String getMensagemErroValidacao() {
		return mensagemErroValidacao;
	}
}
