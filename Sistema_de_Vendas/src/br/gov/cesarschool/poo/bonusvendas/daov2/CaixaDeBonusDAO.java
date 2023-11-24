package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class CaixaDeBonusDAO {

	private DAOGenerico<CaixaDeBonus> dao;

    public CaixaDeBonusDAO() {
        dao = new DAOGenerico<>(CaixaDeBonus.class, "Caixa");
    }

	public void incluir(CaixaDeBonus caixaBonus) throws ExcecaoObjetoJaExistente, ExcecaoObjetoNaoExistente {
		String idUnico = caixaBonus.getIdUnico();
		CaixaDeBonus caixaBonusBusca = buscar(Long.parseLong(idUnico));  
		
		if (caixaBonusBusca != null) { 
			throw new ExcecaoObjetoJaExistente();
		} else {
			dao.incluir(caixaBonus);
		}		 
	}
	
	public void alterar(CaixaDeBonus caixaBonus) throws ExcecaoObjetoNaoExistente {
		String idUnico = caixaBonus.getIdUnico();
		CaixaDeBonus caixaBonusBusca = buscar(Long.parseLong(idUnico));
		
		if (caixaBonusBusca == null) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			dao.alterar(caixaBonus);
		}		
	}
	
	
	
	
	public CaixaDeBonus buscar(long codigo) throws ExcecaoObjetoNaoExistente {
		CaixaDeBonus caixa = dao.buscar(String.valueOf(codigo));
		
		if (caixa == null) {
			throw new ExcecaoObjetoNaoExistente();
		}
		
		return caixa;
	}
	
	public CaixaDeBonus[] buscarTodos() {
		return dao.buscarTodos();
	} 


}
