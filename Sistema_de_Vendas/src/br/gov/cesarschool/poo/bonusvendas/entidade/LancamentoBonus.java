package br.gov.cesarschool.poo.bonusvendas.entidade;

import java.io.Serializable;

public class LancamentoBonus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
