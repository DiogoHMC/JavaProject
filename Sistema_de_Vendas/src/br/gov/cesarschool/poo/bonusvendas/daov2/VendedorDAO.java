package br.gov.cesarschool.poo.bonusvendas.daov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;

public class VendedorDAO {
	
	private DAOGenerico<Vendedor> dao;

    public VendedorDAO() {
        dao = new DAOGenerico<>(Vendedor.class);

	}

	public boolean incluir(Vendedor vend) {
		String idUnico = vend.getIdUnico();
		Vendedor vendBusca = buscar(idUnico);  
		if (vendBusca != null) { 
			return false;
		} else {
			dao.incluir(vend);
			return true;
		}		 
	}
	public boolean alterar(Vendedor vend) {
		String idUnico = vend.getIdUnico();
		Vendedor vendBusca = buscar(idUnico);
		if (vendBusca == null) {
			return false;
		} else {
			dao.alterar(vend);
			return true;
		}		
	}
	public Vendedor buscar(String cpf) {
		return dao.buscar(cpf);
	}
	public Vendedor[] buscarTodos() {
		return dao.buscarTodos();
	}
}
