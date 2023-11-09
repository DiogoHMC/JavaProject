package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.DAOGenerico;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;

public class CaixaDeBonusDAO {
	private static final String BRANCO = "";

	private DAOGenerico<CaixaDeBonus> dao;

    public CaixaDeBonusDAO() {
        dao = new DAOGenerico<>(CaixaDeBonus.class);
    }

	public boolean incluir(CaixaDeBonus caixaBonus) {
		CaixaDeBonus caixaBonusBusca = buscar(caixaBonus.getNumero());  
		if (caixaBonusBusca != null) { 
			return false;
		} else {
			cadastro.incluir(caixaBonus, BRANCO + caixaBonus.getNumero());
			return true;
		}		 
	}
	public boolean alterar(CaixaDeBonus caixaBonus) {
		CaixaDeBonus caixaBonusBusca = buscar(caixaBonus.getNumero());
		if (caixaBonusBusca == null) {
			return false;
		} else {
			cadastro.alterar(caixaBonus, BRANCO + caixaBonus.getNumero());
			return true;
		}		
	}
	public CaixaDeBonus buscar(long codigo) {
		// Esta operacao entre () vai ter significado mais a frente! 
		return (CaixaDeBonus)cadastro.buscar(BRANCO + codigo);
	}
	public CaixaDeBonus[] buscarTodos() {
		Serializable[] rets = cadastro.buscarTodos(CaixaDeBonus.class);
		CaixaDeBonus[] caixaBonus = new CaixaDeBonus[rets.length];
		for(int i=0; i<rets.length; i++) {
			// Esta operacao entre () vai ter significado mais a frente! 
			caixaBonus[i] = (CaixaDeBonus)rets[i];
		}
		return caixaBonus;
	} 


}
