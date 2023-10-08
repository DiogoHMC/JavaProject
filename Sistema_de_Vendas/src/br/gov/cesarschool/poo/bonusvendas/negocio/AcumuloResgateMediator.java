package br.gov.cesarschool.poo.bonusvendas.negocio;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import br.gov.cesarschool.poo.bonusvendas.dao.*;
import br.gov.cesarschool.poo.bonusvendas.entidade.*;

public class AcumuloResgateMediator {
	private static AcumuloResgateMediator instance;
    private CaixaDeBonusDAO repositorioCaixaDeBonus;
    private LancamentoBonusDAO repositorioLancamento;
    
    public AcumuloResgateMediator() {
    	this.repositorioCaixaDeBonus = new CaixaDeBonusDAO();
    	this.repositorioLancamento = new LancamentoBonusDAO();
    }
    
    public static AcumuloResgateMediator getInstancia() {
        if (instance == null) {
            instance = new AcumuloResgateMediator();
        }
        return instance;
    }
    
    
    public long gerarCaixaDeBonus(Vendedor vendedor) {
    	String cpf = vendedor.getCpf();
    	
    	
    	Date dataAtual = new Date();
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String strDate = dateFormat.format(dataAtual);

        long numeros = Long.parseLong(cpf + strDate);
        
        CaixaDeBonus caixaDeBonus = new CaixaDeBonus(numeros, 0);
        boolean incluir = repositorioCaixaDeBonus.incluir(caixaDeBonus);
        
        if (incluir) {
        	return numeros;
        }
        
        return 0L;
       
    }
    
    public String acumularBonus(long numeroCaixaDeBonus, double valor) {
    	if (valor <= 0) {
            return "Erro: O valor deve ser maior que zero.";
        }
    	
    	
    	CaixaDeBonus caixaDeBonus = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
    	
    	if (caixaDeBonus == null) {
            return "Erro: Caixa de bônus não encontrada.";
        }
    	
    	
    	caixaDeBonus.creditar(valor);
    	
    	repositorioCaixaDeBonus.alterar(caixaDeBonus);
    	
    	LocalDateTime dataHoraAtual = LocalDateTime.now();
    	LancamentoBonus lancamentoCredito = new LancamentoBonusCredito(caixaDeBonus.getNumeros(), valor, dataHoraAtual);
        repositorioLancamento.incluir(lancamentoCredito);
        
        return null;
    }
    
    public String resgatar(long numeroCaixaDeBonus, double valor, TipoResgate tipo) {
    	if (valor <= 0) {
            return "Erro: O valor deve ser maior que zero.";
        }
    	
    	CaixaDeBonus caixaDeBonus = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
    	
    	if (caixaDeBonus == null) {
            return "Erro: Caixa de bônus não encontrada.";
        }
    	
    	if (caixaDeBonus.getSaldo() < valor) {
            return "Erro: Saldo insuficiente para o resgate.";
        }
    	
    	caixaDeBonus.debitar(valor);
    	
    	repositorioCaixaDeBonus.alterar(caixaDeBonus);
    	
    	LocalDateTime dataHoraAtual = LocalDateTime.now();
    	LancamentoBonus lancamentoDebito = new LancamentoBonusDebito(caixaDeBonus.getNumeros(), valor, dataHoraAtual, tipo);
        repositorioLancamento.incluir(lancamentoDebito);
    	
        return null;
    }
}
