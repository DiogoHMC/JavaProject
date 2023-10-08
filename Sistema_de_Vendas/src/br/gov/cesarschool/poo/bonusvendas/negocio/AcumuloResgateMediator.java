package br.gov.cesarschool.poo.bonusvendas.negocio;

import java.time.LocalDate;

import br.gov.cesarschool.poo.bonusvendas.dao.*;
import br.gov.cesarschool.poo.bonusvendas.entidade.*;

public class AcumuloResgateMediator {
    private CaixaDeBonusDAO repositorioCaixaDeBonus;
    private LancamentoBonusDAO repositorioLancamento;
    
    public AcumuloResgateMediator() {
    	this.repositorioCaixaDeBonus = new CaixaDeBonusDAO();
    	this.repositorioLancamento = new LancamentoBonusDAO();
    }
    
    public long gerarCaixaDeBonus(Vendedor vend) {
    	String cpf = vend.getCpf();
    	
    	
    	LocalDate dataAtual = LocalDate.now();
    	
    	String anoAtual = String.format("%0" + 4 + "%d", dataAtual.getYear());
    	String mesAtual = String.format("%0" + 2 + "%d", dataAtual.getMonthValue());
    	String diaAtual = String.format("%0" + 2 + "%d", dataAtual.getDayOfMonth());
    	
    	Long numero = Long.parseLong(cpf + anoAtual + mesAtual + diaAtual);

        
    	
    	return 00000;
    }
}
