package br.gov.cesarschool.poo.bonusvendas.dao;


import br.gov.cesarschool.poo.bonusvendas.entidade.*;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class CaixaDeBonusDAO {
    private static final long BRANCO= 0;
    private CadastroObjetos cadastro = new CadastroObjetos(CaixaDeBonus.class);
    		public boolean incluir(CaixaDeBonus prod) {
		Produto prodBusca = buscar(prod.getNumeros()); 
		if (prodBusca != null) { 
			return false;
		} else {
			cadastro.incluir(prod, BRANCO + prod.getCodigo());
			return true;
		}		 
	}
}
