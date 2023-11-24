package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class LancamentoBonusDAO {
	private DAOGenerico<LancamentoBonus> dao;
	
	public LancamentoBonusDAO() {
        this.dao = new DAOGenerico<>(LancamentoBonus.class);
    }
	
	public void incluir(LancamentoBonus lancamento) throws ExcecaoObjetoJaExistente {
		String idUnico = lancamento.getIdUnico();
		LancamentoBonus lancamentoBusca = buscar(idUnico);  
		
		if (lancamentoBusca != null) { 
			throw new ExcecaoObjetoJaExistente();
		} else {
			dao.incluir(lancamento);
		}		 
	}
	public void alterar(LancamentoBonus lancamento) {
		String idUnico = lancamento.getIdUnico();
		LancamentoBonus lancamentoBusca = buscar(idUnico);
		
		if (lancamentoBusca == null) {
		
		} else {
			dao.alterar(lancamento);
		}		
	}
	public LancamentoBonus buscar(String codigo) {
		LancamentoBonus lancamento = dao.buscar(codigo);
		
		if (lancamento == null) {
			
		}
		
		return lancamento;
	}
	public LancamentoBonus[] buscarTodos() {
		return dao.buscarTodos();
	} 

}
