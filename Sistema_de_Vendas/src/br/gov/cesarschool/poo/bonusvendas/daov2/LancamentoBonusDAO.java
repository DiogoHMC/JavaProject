package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class LancamentoBonusDAO {
	private DAOGenerico<LancamentoBonus> dao;
	
	public LancamentoBonusDAO() {
        this.dao = new DAOGenerico<>(LancamentoBonus.class, "Lancamento");
    }
	
	public void incluir(LancamentoBonus lancamento) throws ExcecaoObjetoJaExistente, ExcecaoObjetoNaoExistente {
		String idUnico = lancamento.getIdUnico();
		LancamentoBonus lancamentoBusca = buscar(idUnico);  
		
		if (lancamentoBusca != null) { 
			throw new ExcecaoObjetoJaExistente();
		} else {
			dao.incluir(lancamento);
		}		 
	}
	public void alterar(LancamentoBonus lancamento) throws ExcecaoObjetoNaoExistente {
		String idUnico = lancamento.getIdUnico();
		LancamentoBonus lancamentoBusca = buscar(idUnico);
		
		if (lancamentoBusca == null) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			dao.alterar(lancamento);
		}		
	}
	public LancamentoBonus buscar(String codigo) throws ExcecaoObjetoNaoExistente {
		LancamentoBonus lancamento = dao.buscar(codigo);
		
		if (lancamento == null) {
			throw new ExcecaoObjetoNaoExistente();
		}
		
		return lancamento;
	}
	public LancamentoBonus[] buscarTodos() {
		return dao.buscarTodos();
	} 

}
