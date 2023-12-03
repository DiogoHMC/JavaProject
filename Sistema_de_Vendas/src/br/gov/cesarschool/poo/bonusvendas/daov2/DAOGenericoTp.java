package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;
import java.lang.reflect.Array;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;

public class DAOGenericoTp<T extends Registro> {
	private static final String BRANCO = "";
    private CadastroObjetos cadastro;
    private String nomeEntidade;
    private Class<T> tipo;
    public DAOGenericoTp(Class<T> tipo, String nomeEntidade) {
        super();
        this.tipo = tipo;
        this.cadastro = new CadastroObjetos(tipo);
        this.nomeEntidade = nomeEntidade;
    }

    public void incluir(T reg) throws ExcecaoObjetoJaExistente {
        try {
            buscar(reg.getIdUnico());
            throw new ExcecaoObjetoJaExistente(nomeEntidade + " ja existente");
        } catch (ExcecaoObjetoNaoExistente e) {
            cadastro.incluir(reg, BRANCO + reg.getIdUnico());
        }
    }

    public void alterar(T reg) throws ExcecaoObjetoNaoExistente {
        T regBusca = buscar(reg.getIdUnico());
        if (regBusca == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        } else {
            cadastro.alterar(reg, BRANCO + reg.getIdUnico());
        }
    }

    public T buscar(String id) throws ExcecaoObjetoNaoExistente {
        T result = tipo.cast(cadastro.buscar(BRANCO + id));
        if (result == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        }
        return result;
    }

    public T[] buscarTodos() {
        Serializable[] rets = cadastro.buscarTodos(Registro.class);
        T[] reg = (T[])Array.newInstance(tipo, rets.length);
        for (int i = 0; i < rets.length; i++) {
            reg[i] = tipo.cast(reg[i]);
        }
        return reg;
    }
}