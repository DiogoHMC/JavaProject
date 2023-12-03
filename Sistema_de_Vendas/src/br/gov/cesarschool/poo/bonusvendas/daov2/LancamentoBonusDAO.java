package br.gov.cesarschool.poo.bonusvendas.daov2;


import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;

public class LancamentoBonusDAO {
	DAOGenerico dao;
	
	public LancamentoBonusDAO() {
        this.dao = new DAOGenerico(LancamentoBonus.class, "Lancamento");
    }
	
	public void incluir(LancamentoBonus lanc) throws ExcecaoObjetoJaExistente {
        dao.incluir(lanc);
    }

    public void alterar(LancamentoBonus lanc) throws ExcecaoObjetoNaoExistente {
        dao.alterar(lanc);
    }

    public LancamentoBonus buscar(String id) throws ExcecaoObjetoNaoExistente {
        return (LancamentoBonus) dao.buscar(id);
    }
	
	public LancamentoBonus[] buscarTodos() {
		Registro[] regs = dao.buscarTodos();
		LancamentoBonus[] lanc = new LancamentoBonus[regs.length];
		
		for (int i = 0; i < regs.length; i++) {
		    if (regs[i] instanceof LancamentoBonus) {
		        lanc[i] = (LancamentoBonus) regs[i];
		    } else {
		        System.out.println("errou");
		    }
		}
		
		return lanc;
	} 
	
	
}