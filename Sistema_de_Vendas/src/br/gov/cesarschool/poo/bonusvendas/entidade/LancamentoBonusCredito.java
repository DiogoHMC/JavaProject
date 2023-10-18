package br.gov.cesarschool.poo.bonusvendas.entidade;
import java.time.LocalDateTime;

public class LancamentoBonusCredito extends LancamentoBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LancamentoBonusCredito(long numeroCaixaDeBonus, double valor, LocalDateTime dataHoraLancamento) {
		
		super(numeroCaixaDeBonus, valor, dataHoraLancamento);
	}
	
}