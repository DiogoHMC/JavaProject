package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class VendedorDAO {
	
	private DAOGenerico dao;

    public VendedorDAO() {
        this.dao = new DAOGenerico(Vendedor.class, "Vendedor");

	}

	public void incluir(Vendedor vend) throws ExcecaoObjetoJaExistente, ExcecaoObjetoNaoExistente {
		String idUnico = vend.getIdUnico();
		
		try { 
			buscar(idUnico); 
			throw new ExcecaoObjetoJaExistente();
		} catch (ExcecaoObjetoJaExistente e) {
			dao.incluir(vend);
		}		 
	}
	
	public void alterar(Vendedor vend) throws ExcecaoObjetoNaoExistente {
		String idUnico = vend.getIdUnico();
		
		try {
			buscar(idUnico);
			throw new ExcecaoObjetoNaoExistente();
		} catch (ExcecaoObjetoNaoExistente e) {
			dao.alterar(vend);
		}		
	}
	
	public Vendedor buscar(String cpf) throws ExcecaoObjetoNaoExistente {
		Vendedor vendedor = (Vendedor) dao.buscar(cpf);
		
		if (vendedor == null) {
			throw new ExcecaoObjetoNaoExistente();
		}
		
		return vendedor;
	}
	public Vendedor[] buscarTodos() {
		return (Vendedor[]) dao.buscarTodos();
	}
	
	 public boolean existeVendedor(String cpf) {
        try {
            buscar(cpf);
            return true;
        } catch (ExcecaoObjetoNaoExistente e) {
            return false;
        }
	 }
}