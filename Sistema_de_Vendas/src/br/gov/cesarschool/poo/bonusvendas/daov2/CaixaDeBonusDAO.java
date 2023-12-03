package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;


import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;

public class CaixaDeBonusDAO {
	DAOGenerico dao;
	
	public CaixaDeBonusDAO() {
        this.dao = new DAOGenerico(CaixaDeBonus.class, "Caixa");
    }
	
	public void incluir(CaixaDeBonus caixa) throws ExcecaoObjetoJaExistente {
        dao.incluir(caixa);
    }

    public void alterar(CaixaDeBonus caixa) throws ExcecaoObjetoNaoExistente {
        dao.alterar(caixa);
    }
    
    public CaixaDeBonus buscar(long numero) throws ExcecaoObjetoNaoExistente {
        String id = Long.toString(numero);
        return (CaixaDeBonus) dao.buscar(id);
    }
	
	public CaixaDeBonus[] buscarTodos() {
		Registro[] regs = dao.buscarTodos();
		CaixaDeBonus[] caixa = new CaixaDeBonus[regs.length];
		
		for (int i = 0; i < regs.length; i++) {
		    if (regs[i] instanceof CaixaDeBonus) {
		        caixa[i] = (CaixaDeBonus) regs[i];
		    } else {
		        System.out.println("errou");
		    }
		}
		
		return caixa;
	} 

}