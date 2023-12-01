package br.gov.cesarschool.poo.bonusvendas.daov2;

import java.io.Serializable;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;

public class DAOGenericoTp<T> {
    private String nomeEntidade;
    CadastroObjetos cadastro;

    public DAOGenericoTp(Class<?> tipo, String nomeEntidade) {
        cadastro = new CadastroObjetos(tipo);
        this.nomeEntidade = nomeEntidade;
    }

    public void incluir(T reg) throws ExcecaoObjetoJaExistente {
        String idUnico = ((Registro) reg).getIdUnico();
        try{
            buscar(idUnico);
            throw new ExcecaoObjetoJaExistente(nomeEntidade + " ja existente");
        } catch (ExcecaoObjetoNaoExistente e) {
            cadastro.incluir((Serializable) reg, idUnico);
        }
    }

    public boolean excluir(String id) throws ExcecaoObjetoNaoExistente {
        try {
            T regBusca = buscar(id);
            if (regBusca == null) { 
                return false;
            } else {
                cadastro.excluir(id);
                return true;
            }
        } catch (ExcecaoObjetoNaoExistente e) {
            return false;
        }
    }

    public void alterar(T reg) throws ExcecaoObjetoNaoExistente {
        String idUnico = ((Registro) reg).getIdUnico();
        buscar(idUnico);
        cadastro.alterar((Serializable) reg, idUnico);
    }

    public T buscar(String id) throws ExcecaoObjetoNaoExistente {
        T registroBusca = (T) cadastro.buscar(id);
        if(registroBusca == null) {
            throw new ExcecaoObjetoNaoExistente(nomeEntidade + " nao existente");
        } else {
            return registroBusca;
        }
    }

    public T[] buscarTodos() {
        Serializable[] regs = cadastro.buscarTodos(Registro.class);
        T[] regsRet = (T[]) new Registro[regs.length];
        for (int i = 0; i < regs.length; i++) {
            regsRet[i] = (T) regs[i];
        }
        return regsRet;
    }
    
}