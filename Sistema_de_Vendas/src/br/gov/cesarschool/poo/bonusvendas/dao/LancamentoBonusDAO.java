package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;

public class LancamentoBonusDAO {
	private DAOGenerico<LancamentoBonus> dao;
	
	public LancamentoBonusDAO() {
        this.dao = new DAOGenerico<>(LancamentoBonus.class);
    }
	
	public boolean incluir(LancamentoBonus lancamento) {
		String idUnico = getIdUnico(lancamento);
		LancamentoBonus lancamentoBusca = buscar(idUnico);  
		if (lancamentoBusca != null) { 
			return false;
		} else {
			cadastro.incluir(lancamento, idUnico);
			return true;
		}		 
	}
	public boolean alterar(LancamentoBonus lancamento) {
		String idUnico = obterIdUnico(lancamento);
		LancamentoBonus lancamentoBusca = buscar(idUnico);
		if (lancamentoBusca == null) {
			return false;
		} else {
			cadastro.alterar(lancamento, idUnico);
			return true;
		}		
	}
	public LancamentoBonus buscar(String codigo) {
		// Esta operacao entre () vai ter significado mais a frente! 
		return (LancamentoBonus)cadastro.buscar(codigo);
	}
	public LancamentoBonus[] buscarTodos() {
		Serializable[] rets = cadastro.buscarTodos(LancamentoBonus.class);
		LancamentoBonus[] lancamentos = new LancamentoBonus[rets.length];
		for(int i=0; i<rets.length; i++) {
			// Esta operacao entre () vai ter significado mais a frente! 
			lancamentos[i] = (LancamentoBonus)rets[i];
		}
		return lancamentos;
	} 

}
