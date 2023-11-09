package br.gov.cesarschool.poo.bonusvendas.dao;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;

public class DAOGenerico<T extends Registro> {
    private CadastroObjetos cadastro;

    public DAOGenerico(Class<T> tipo) {
        this.cadastro = new CadastroObjetos(tipo);
    }

    public boolean incluir(T reg) {
        String idUnico = reg.getIdUnico();
        T regBusca = buscar(idUnico);
        if (regBusca != null) {
            return false;
        } else {
            cadastro.incluir(reg, idUnico);
            return true;
        }
    }

    public boolean alterar(T reg) {
        String idUnico = reg.getIdUnico();
        T regBusca = buscar(idUnico);
        if (regBusca == null) {
            return false;
        } else {
            cadastro.alterar(reg, idUnico);
            return true;
        }
    }

    public T buscar(String id) {
        return (T) cadastro.buscar(id);
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
