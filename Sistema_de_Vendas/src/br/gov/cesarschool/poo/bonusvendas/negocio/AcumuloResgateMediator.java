package br.gov.cesarschool.poo.bonusvendas.negocio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.gov.cesarschool.poo.bonusvendas.dao.CaixaDeBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.dao.LancamentoBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusCredito;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusDebito;
import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;

public class AcumuloResgateMediator {
	private static final String CAIXA_DE_BONUS_INEXISTENTE = "Caixa de bonus inexistente";
	private static final String VALOR_MENOR_OU_IGUAL_A_ZERO = "Valor menor ou igual a zero";
	private static AcumuloResgateMediator instancia;
	public static AcumuloResgateMediator getInstancia() {
		if (instancia == null) {
			instancia = new AcumuloResgateMediator();
		}
		return instancia;
	}
	private CaixaDeBonusDAO repositorioCaixaDeBonus;
	private LancamentoBonusDAO repositorioLancamento;
	private AcumuloResgateMediator() {
		repositorioCaixaDeBonus = new CaixaDeBonusDAO();
		repositorioLancamento = new LancamentoBonusDAO();
	}
	public long gerarCaixaDeBonus(Vendedor vendedor) {
		LocalDate dataAtual = LocalDate.now();
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		long numero = Long.parseLong(vendedor.getCpf().substring(0, 9) + 
				dataAtual.format(customFormatter));
		CaixaDeBonus caixa = new CaixaDeBonus(numero);
		boolean ret = repositorioCaixaDeBonus.incluir(caixa);
		if (ret) {
			return numero;
		} else {
			return 0;
		}		 
	}
	public String acumularBonus(long numeroCaixaDeBonus, double valor) {
		if (valor <= 0) {
			return VALOR_MENOR_OU_IGUAL_A_ZERO; 
		} 
		CaixaDeBonus caixa = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
		if (caixa == null) {
			return CAIXA_DE_BONUS_INEXISTENTE;
		} 
		caixa.creditar(valor);
		repositorioCaixaDeBonus.alterar(caixa);
		LancamentoBonusCredito lancamento = new LancamentoBonusCredito(numeroCaixaDeBonus, valor, LocalDateTime.now());
		repositorioLancamento.incluir(lancamento);
		return null;
	}
	public String resgatar(long numeroCaixaDeBonus, double valor, TipoResgate tipoResgate) {
		if (valor <= 0) {
			return VALOR_MENOR_OU_IGUAL_A_ZERO; 
		} 
		CaixaDeBonus caixa = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
		if (caixa == null) {
			return CAIXA_DE_BONUS_INEXISTENTE;
		} 
		if (caixa.getSaldo() < valor) {
			return "Saldo insuficiente";
		}
		caixa.debitar(valor);
		repositorioCaixaDeBonus.alterar(caixa);
		LancamentoBonusDebito lancamento = new LancamentoBonusDebito(numeroCaixaDeBonus, valor, LocalDateTime.now(), tipoResgate);
		repositorioLancamento.incluir(lancamento);
		return null;
	}
}
