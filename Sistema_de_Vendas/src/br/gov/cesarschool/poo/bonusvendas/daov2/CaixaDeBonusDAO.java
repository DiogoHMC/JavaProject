package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class CaixaDeBonusDAO {

	private DAOGenerico<CaixaDeBonus> dao;

    public CaixaDeBonusDAO() {
        dao = new DAOGenerico<>(CaixaDeBonus.class);
    }

	public void incluir(CaixaDeBonus caixaBonus) throws ExcecaoObjetoJaExistente {
		String idUnico = caixaBonus.getIdUnico();
		CaixaDeBonus caixaBonusBusca = buscar(Long.parseLong(idUnico));  
		
		if (caixaBonusBusca != null) { 
			throw new ExcecaoObjetoJaExistente();
		} else {
			dao.incluir(caixaBonus);
		}		 
	}
	
	public void alterar(CaixaDeBonus caixaBonus) {
		String idUnico = caixaBonus.getIdUnico();
		CaixaDeBonus caixaBonusBusca = buscar(Long.parseLong(idUnico));
		
		if (caixaBonusBusca == null) {
			
		} else {
			dao.alterar(caixaBonus);
		}		
	}
	
	
	
	
	public CaixaDeBonus buscar(long codigo) {
		CaixaDeBonus caixa = dao.buscar(String.valueOf(codigo));
		
		if (caixa == null) {
			
		}
		
		return caixa;
	}
	
	public CaixaDeBonus[] buscarTodos() {
		return dao.buscarTodos();
	} 


}
