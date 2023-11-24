package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;

public class VendedorDAO {
	
	private DAOGenerico<Vendedor> dao;

    public VendedorDAO() {
        dao = new DAOGenerico<>(Vendedor.class);

	}

	public void incluir(Vendedor vend) throws ExcecaoObjetoJaExistente {
		String idUnico = vend.getIdUnico();
		Vendedor vendBusca = buscar(idUnico);  
		
		if (vendBusca != null) { 
			throw new ExcecaoObjetoJaExistente();
		} else {
			dao.incluir(vend);
		}		 
	}
	public void alterar(Vendedor vend) {
		String idUnico = vend.getIdUnico();
		Vendedor vendBusca = buscar(idUnico);
		
		if (vendBusca == null) {
			
		} else {
			dao.alterar(vend);
		}		
	}
	public Vendedor buscar(String cpf) {
		Vendedor vendedor = dao.buscar(cpf);
		
		if (vendedor == null) {
			
		}
		
		return vendedor;
	}
	public Vendedor[] buscarTodos() {
		return dao.buscarTodos();
	}
}
