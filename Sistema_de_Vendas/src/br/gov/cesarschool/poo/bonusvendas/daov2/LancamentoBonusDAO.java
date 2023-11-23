package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;

public class LancamentoBonusDAO {
	private DAOGenerico<LancamentoBonus> dao;
	
	public LancamentoBonusDAO() {
        this.dao = new DAOGenerico<>(LancamentoBonus.class);
    }
	
	public boolean incluir(LancamentoBonus lancamento) {
		String idUnico = lancamento.getIdUnico();
		LancamentoBonus lancamentoBusca = buscar(idUnico);  
		if (lancamentoBusca != null) { 
			return false;
		} else {
			dao.incluir(lancamento);
			return true;
		}		 
	}
	public boolean alterar(LancamentoBonus lancamento) {
		String idUnico = lancamento.getIdUnico();
		LancamentoBonus lancamentoBusca = buscar(idUnico);
		if (lancamentoBusca == null) {
			return false;
		} else {
			dao.alterar(lancamento);
			return true;
		}		
	}
	public LancamentoBonus buscar(String codigo) {
		return dao.buscar(codigo);
	}
	public LancamentoBonus[] buscarTodos() {
		return dao.buscarTodos();
	} 

}
