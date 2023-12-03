package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class DAOGenerico {
    private static final String BRANCO = "";
    private CadastroObjetos cadastro;
    private String nomeEntidade;

    public DAOGenerico(Class<?> tipo, String nomeEntidade) {
        super();
        this.cadastro = new CadastroObjetos(tipo);
        this.nomeEntidade = nomeEntidade;
    }

    public void incluir(Registro reg) throws ExcecaoObjetoJaExistente {
        try {
            buscar(reg.getIdUnico());
            throw new ExcecaoObjetoJaExistente(nomeEntidade + " ja existente");
        } catch (ExcecaoObjetoNaoExistente e) {
            cadastro.incluir(reg, BRANCO + reg.getIdUnico());
        }
    }

    public void alterar(Registro reg) throws ExcecaoObjetoNaoExistente {
        Registro regBusca = buscar(reg.getIdUnico());
        if (regBusca == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        } else {
            cadastro.alterar(reg, BRANCO + reg.getIdUnico());
        }
    }

    public Registro buscar(String id) throws ExcecaoObjetoNaoExistente {
        Registro result = (Registro)cadastro.buscar(BRANCO + id);
        if (result == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        }
        return result;
    }

    public Registro[] buscarTodos() {
        Serializable[] rets = cadastro.buscarTodos(Registro.class);
        Registro[] reg = new Registro[rets.length];
        for (int i = 0; i < rets.length; i++) {
            reg[i] = (Registro)rets[i];
        }
        return reg;
    }
}