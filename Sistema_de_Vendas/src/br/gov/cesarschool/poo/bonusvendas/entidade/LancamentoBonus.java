package br.gov.cesarschool.poo.bonusvendas.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LancamentoBonus implements Serializable {
	private long numeroCaixaDeBonus;
	private double valor; 
	private LocalDateTime dataHoraLancamento;
	public LancamentoBonus(long numeroCaixaDeBonus, double valor, LocalDateTime dataHoraLancamento) {
		super();
		this.numeroCaixaDeBonus = numeroCaixaDeBonus;
		this.valor = valor;
		this.dataHoraLancamento = dataHoraLancamento;
	}
	public long getNumeroCaixaDeBonus() {
		return numeroCaixaDeBonus;
	}
	public double getValor() {
		return valor;
	}
	public LocalDateTime getDataHoraLancamento() {
		return dataHoraLancamento;
	}
}
