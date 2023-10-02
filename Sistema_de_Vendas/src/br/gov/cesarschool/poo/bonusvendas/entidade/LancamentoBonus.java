package br.gov.cesarschool.poo.bonusvendas.entidade;

public class LancamentoBonus {
	
	private long numeroCaixaDeBonus;
	private double valor;
	private java.time.LocalDateTime dataHoraLancamento;
	
	public LancamentoBonus(long numeroCaixaDeBonus, double valor, java.time.LocalDateTime dataHoraLancamento) {
		
		super();
		this.numeroCaixaDeBonus = numeroCaixaDeBonus;
		this.valor = valor;
		this.dataHoraLancamento = dataHoraLancamento;
	}
	
	public long getNumeroCaixaDeBonus() {
		
		return numeroCaixaDeBonus;
	}
	
/*	public void setNumeroCaixaDeBonus(long numeroCaixaDeBonus) {
		
		this.numeroCaixaDeBonus = numeroCaixaDeBonus;
	} 
*/
	
	public double getValor() {
		
		return valor;
	}
	
/*	public void setValor(double valor) {
		
		this.valor = valor;
	}
*/
	
	public java.time.LocalDateTime getDataHoraLancamento() {
		
		return dataHoraLancamento;
	}
	
/*	public void SetdataHoraLancamento(java.time.LocalDateTime dataHoraLancamento) {
		
		this.dataHoraLancamento = dataHoraLancamento;
	}
*/

}
