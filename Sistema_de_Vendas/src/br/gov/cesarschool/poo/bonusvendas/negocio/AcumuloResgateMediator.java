package br.gov.cesarschool.poo.bonusvendas.negocio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.gov.cesarschool.poo.bonusvendas.dao.CaixaDeBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.dao.LancamentoBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusCredito;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusDebito;
import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.util.Ordenadora;

public class AcumuloResgateMediator {
	//Attributes
	private CaixaDeBonusDAO repositorioCaixaDeBonus;
	private LancamentoBonusDAO repositorioLancamento;
	
			//Singleton
	private static AcumuloResgateMediator instance;
	
	
	//Constructor
	public AcumuloResgateMediator() {
        this.repositorioCaixaDeBonus = new CaixaDeBonusDAO();
        this.repositorioLancamento = new LancamentoBonusDAO();
    }
	
			//Singleton
	public static AcumuloResgateMediator getInstancia() {
        if (instance == null) {
            instance = new AcumuloResgateMediator();
        }
        return instance;
    }

	
	//Methods
	public long gerarCaixaDeBonus(Vendedor vendedor) {
		if (vendedor == null) {
            return 0;
        }
		//format
		LocalDate dataAtual = LocalDate.now();
        int ano = dataAtual.getYear();
        int mes = dataAtual.getMonthValue();
        int dia = dataAtual.getDayOfMonth();
        String codigo = vendedor.getCpf().substring(0,9) + String.format("%04d%02d%02d", ano, mes, dia);
        long numCaixa = Long.parseLong(codigo);
        
		//create
		CaixaDeBonus caixa = new CaixaDeBonus(numCaixa);
	    boolean incluido = repositorioCaixaDeBonus.incluir(caixa);
	    System.out.println(incluido);
	    if (incluido) {
	        return numCaixa;
	    } else {
	        return 0;
	    }
	}
	
	public String acumularBonus(long numeroCaixaDeBonus, double valor) {
	    if (valor<=0) {
	        return "Valor menor ou igual a zero";
	    }
	    //
	    CaixaDeBonus caixa = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
	    if (caixa==null) {
	        return "Caixa de bonus inexistente";
	    }
	    //
	    caixa.creditar(valor);
	    repositorioCaixaDeBonus.alterar(caixa);
	    //
	    LancamentoBonusCredito lancamentoCredito = new LancamentoBonusCredito
	    												(numeroCaixaDeBonus, valor, LocalDateTime.now());
	    boolean incluido = repositorioLancamento.incluir(lancamentoCredito);
	    if (!incluido) {
	        return "[Erro] credito nao registrado";
	    }

	    return null;
	    
	}
	
	public String resgatar(long numeroCaixaDeBonus, double valor, TipoResgate tipo) {
	    if (valor<=0) {
	        return "Valor menor ou igual a zero";
	    }
	    //
	    CaixaDeBonus caixa = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
	    if (caixa==null) {
	        return "Caixa de bonus inexistente";
	    }
	    //
	    if (caixa.getSaldo()<valor) {
	        return "Saldo insuficiente";
	    }
	    //
	    caixa.debitar(valor);
	    repositorioCaixaDeBonus.alterar(caixa);
	    //
	    LancamentoBonusDebito lancamentoDebito = new LancamentoBonusDebito(numeroCaixaDeBonus, valor, LocalDateTime.now(), tipo);
	    boolean incluido = repositorioLancamento.incluir(lancamentoDebito);
	    if (!incluido) {
	        return "[Erro] debito nao registrado";
	    }
	    return null;
	}

	public CaixaDeBonus buscar(long numeroCaixaDeBonus) {
    	return repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
    }
	
	
	public CaixaDeBonus[] listaCaixaDeBonusPorSaldoMaior(double saldoInicial) {
        CaixaDeBonus[] caixas = repositorioCaixaDeBonus.buscarTodos();

        caixas = filtrarPorSaldoMaior(caixas, saldoInicial);

        Ordenadora.ordenar(caixas, ComparadorCaixaDeBonusSaldoDec.getInstance());

        return caixas;
    }

    private CaixaDeBonus[] filtrarPorSaldoMaior(CaixaDeBonus[] caixas, double saldoInicial) {

        int count = 0;
        for (CaixaDeBonus caixa : caixas) {
            if (caixa.getSaldo() >= saldoInicial) {
                count++;
            }
        }

        CaixaDeBonus[] result = new CaixaDeBonus[count];
        int index = 0;
        for (CaixaDeBonus caixa : caixas) {
            if (caixa.getSaldo() >= saldoInicial) {
                result[index++] = caixa;
            }
        }

        return result;
    }
    
    public LancamentoBonus[] listaLancamentosPorFaixaData(LocalDate d1, LocalDate d2) {
        LancamentoBonus[] todosLancamentos = repositorioLancamento.buscarTodos();
        List<LancamentoBonus> lancamentosFiltrados = new ArrayList<>();

        for (LancamentoBonus lancamento : todosLancamentos) {
            LocalDate dataLancamento = lancamento.getDataHoraLancamento().toLocalDate();
            if (!dataLancamento.isBefore(d1) && !dataLancamento.isAfter(d2)) {
                lancamentosFiltrados.add(lancamento);
            }
        }

        Collections.sort(lancamentosFiltrados, ComparadorLancamentoBonusDHDec.getInstance());

        return lancamentosFiltrados.toArray(new LancamentoBonus[lancamentosFiltrados.size()]);
    }

    
	
}