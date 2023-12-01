package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class CaixaDeBonusDAO {
	private DAOGenericoTp<CaixaDeBonus> dao; 

    public CaixaDeBonusDAO() {
        this.dao = new DAOGenericoTp<>(CaixaDeBonusDAO.class, "Caixa");
    }

    public void incluir(CaixaDeBonus caixa) throws ExcecaoObjetoJaExistente {
    	dao.incluir(caixa);
    }

    public void alterar(CaixaDeBonus caixa) throws ExcecaoObjetoNaoExistente {
        dao.alterar(caixa);
    }

    public void excluir(long numero) throws ExcecaoObjetoNaoExistente {
    	dao.excluir(numero + "");
    }

    public CaixaDeBonus buscar(long numero) throws ExcecaoObjetoNaoExistente {
    	return dao.buscar(numero + "");
    }

    public CaixaDeBonus[] buscarTodos() {
        CaixaDeBonus[] rets = dao.buscarTodos();
        CaixaDeBonus[] caixas = new CaixaDeBonus[rets.length];
        for(int i=0; i<rets.length; i++) {
            caixas[i] = rets[i];
        }
        return caixas;
    } 
}