package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;

public class CaixaDeBonusDAO {

	private DAOGenerico<CaixaDeBonus> dao;

    public CaixaDeBonusDAO() {
        dao = new DAOGenerico<>(CaixaDeBonus.class);
    }

	public boolean incluir(CaixaDeBonus caixaBonus) {
		String idUnico = caixaBonus.getIdUnico();
		CaixaDeBonus caixaBonusBusca = buscar(Long.parseLong(idUnico));  
		if (caixaBonusBusca != null) { 
			return false;
		} else {
			dao.incluir(caixaBonus);
			return true;
		}		 
	}
	public boolean alterar(CaixaDeBonus caixaBonus) {
		String idUnico = caixaBonus.getIdUnico();
		CaixaDeBonus caixaBonusBusca = buscar(Long.parseLong(idUnico));
		if (caixaBonusBusca == null) {
			return false;
		} else {
			dao.alterar(caixaBonus);
			return true;
		}		
	}
	
	
	
	
	public CaixaDeBonus buscar(long codigo) {
		return dao.buscar(String.valueOf(codigo));
	}
	
	public CaixaDeBonus[] buscarTodos() {
		return dao.buscarTodos();
	} 


}
