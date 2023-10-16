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
    	String cpf = vendedor.getCpf().replaceAll("[^0-9]", "");
    	cpf = cpf.substring(0, cpf.length() - 2);
    	
    	
    	Date dataAtual = new Date();
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String strDate = dateFormat.format(dataAtual);

        long numeros = Long.parseLong(cpf + strDate);
        
        CaixaDeBonus caixaDeBonus = new CaixaDeBonus(numeros);
        boolean incluir = repositorioCaixaDeBonus.incluir(caixaDeBonus);
        
        if (incluir) {
        	return numeros;
        }
        
        return 0L;
       
    }
    
    public String acumularBonus(long numeroCaixaDeBonus, double valor) {
    	if (valor <= 0) {
            return "Valor menor ou igual a zero";
        }
    	
    	
    	CaixaDeBonus caixaDeBonus = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
    	
    	if (caixaDeBonus == null) {
            return "Caixa de bonus inexistente";
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
            return "Valor menor ou igual a zero";
        }
    	
    	CaixaDeBonus caixaDeBonus = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
    	
    	if (caixaDeBonus == null) {
            return "Caixa de bonus inexistente";
        }
    	
    	if (caixaDeBonus.getSaldo() < valor) {
            return "Saldo insuficiente";
        }
    	   	
    	caixaDeBonus.debitar(valor);
    	
    	repositorioCaixaDeBonus.alterar(caixaDeBonus);
    	
    	LocalDateTime dataHoraAtual = LocalDateTime.now();
    	LancamentoBonus lancamentoDebito = new LancamentoBonusDebito(caixaDeBonus.getNumeros(), valor, dataHoraAtual, tipo);
        repositorioLancamento.incluir(lancamentoDebito);
    	
        return null;
    }
    
    /* Metodo para buscar saldo da caixa de bonus para ser usado na Tela Acumulo Resgate */
    public double buscarSaldoCaixaDeBonus(long numeroCaixaDeBonus) {
        CaixaDeBonus caixaDeBonus = repositorioCaixaDeBonus.buscar(numeroCaixaDeBonus);
        if (caixaDeBonus != null) {
            return caixaDeBonus.getSaldo();
        }
        return -1; // Ou outro valor de erro adequado, caso a caixa não seja encontrada
    }


}
