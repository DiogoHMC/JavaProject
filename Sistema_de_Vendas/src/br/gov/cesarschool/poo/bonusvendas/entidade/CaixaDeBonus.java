package br.gov.cesarschool.poo.bonusvendas.entidade;
import java.time.LocalDateTime;

import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
public class CaixaDeBonus extends Registro {
	private long numero; 
	private double saldo; 
	private LocalDateTime dataHoraAtualizacao;
	public int length;

	@Override
	public String getIdUnico() {
		return String.valueOf(numero);
	}

	public CaixaDeBonus(long numero) {
		super();
		this.numero = numero;
		dataHoraAtualizacao = LocalDateTime.now();
	}
	public long getNumero() {
		return numero;
	}
	public double getSaldo() {
		return saldo;
	}
	public LocalDateTime getDataHoraAtualizacao() {
		return dataHoraAtualizacao;
	}
	public void creditar(double valor) {
		saldo = saldo + valor;
		dataHoraAtualizacao = LocalDateTime.now();
	}
	public void debitar(double valor) {
		saldo = saldo - valor;
		dataHoraAtualizacao = LocalDateTime.now();
	}

}
