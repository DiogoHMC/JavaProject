package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class DAOGenerico<T extends Registro> {
    private CadastroObjetos cadastro;
    private String nomeEntidade; // Novo atributo privado

    // Novo construtor com a String como parâmetro adicional
    public DAOGenerico(Class<T> tipo, String nomeEntidade) {
        this.cadastro = new CadastroObjetos(tipo);
        this.nomeEntidade = nomeEntidade;
    }

    public void incluir(T reg) throws ExcecaoObjetoJaExistente {
        String idUnico = reg.getIdUnico();
        T regBusca = buscar(idUnico);
        if (regBusca != null) {
            throw new ExcecaoObjetoJaExistente(nomeEntidade + " ja existente");
        } else {
            cadastro.incluir(reg, idUnico);
        }
    }

    public void alterar(T reg) throws ExcecaoObjetoNaoExistente {
        String idUnico = reg.getIdUnico();
        T regBusca = buscar(idUnico);
        if (regBusca == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        } else {
            cadastro.alterar(reg, idUnico);
        }
    }

    public T buscar(String id) throws ExcecaoObjetoNaoExistente {
        T objeto = (T) cadastro.buscar(id);
        if (objeto == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        }
        return objeto;
    }

    public T[] buscarTodos() {
        Object[] rets = cadastro.buscarTodos();
        T[] regs = (T[]) java.lang.reflect.Array.newInstance(cadastro.getTipo(), rets.length);
        for (int i = 0; i < rets.length; i++) {
            regs[i] = (T) rets[i];
        }
        return regs;
    }
}
