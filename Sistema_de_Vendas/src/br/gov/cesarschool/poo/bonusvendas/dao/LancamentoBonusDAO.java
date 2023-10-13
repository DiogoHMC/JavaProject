package br.gov.cesarschool.poo.bonusvendas.dao;


import br.gov.cesarschool.poo.bonusvendas.entidade.*;
import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class LancamentoBonusDAO {
	
	private static final String BRANCO = "";
	private CadastroObjetos cadastro = new CadastroObjetos(LancamentoBonus.class); 
	
	public boolean incluir(LancamentoBonus prod) {
		
		long timestamp = prod.getDataHoraLancamento().toEpochSecond(java.time.ZoneOffset.UTC);
		LancamentoBonus prodBusca = buscar(timestamp); 
		
		if (prodBusca != null) { 
			
			return false;
		} 
		
		else {
			cadastro.incluir(prod, BRANCO + timestamp);
			return true;
		}		 
	}
	
	public boolean excluir(LancamentoBonus prod) {
		
		long timestamp = prod.getDataHoraLancamento().toEpochSecond(java.time.ZoneOffset.UTC);
		LancamentoBonus prodBusca = buscar(timestamp);
    	
    	 if (prodBusca == null) {
    		 
	        return false;
	    }
    	 else {
    		 
	        cadastro.excluir(BRANCO + timestamp);
	        return true;
	    }
    }
	
	public boolean alterar(LancamentoBonus prod) {
		
		long timestamp = prod.getDataHoraLancamento().toEpochSecond(java.time.ZoneOffset.UTC);
		LancamentoBonus prodBusca = buscar(timestamp);
		
		if (prodBusca == null) {
			
			return false;
		} 
		
		else {
			
			cadastro.alterar(prod, BRANCO + timestamp);
			return true;
		}		
	}
	public LancamentoBonus buscar(long timestamp) {
		
		return (LancamentoBonus)cadastro.buscar(BRANCO + timestamp);
	}
	
	public LancamentoBonus[] buscarTodos() {
		
		Serializable[] rets = cadastro.buscarTodos(LancamentoBonus.class);
		LancamentoBonus[] prods = new LancamentoBonus[rets.length];
		
		for(int i=0; i<rets.length; i++) {
			
			prods[i] = (LancamentoBonus)rets[i];
		}
		
		return prods;
	}    
}
