package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;

public class VendedorDAO {
	private CadastroObjetos cadastro = new CadastroObjetos(Vendedor.class); 
	public boolean incluir(Vendedor vend) {
		Vendedor vendBusca = buscar(vend.getCpf());  
		if (vendBusca != null) { 
			return false;
		} else {
			cadastro.incluir(vend, vend.getCpf());
			return true;
		}		 
	}
	public boolean alterar(Vendedor vend) {
		Vendedor vendBusca = buscar(vend.getCpf());
		if (vendBusca == null) {
			return false;
		} else {
			cadastro.alterar(vend, vend.getCpf());
			return true;
		}		
	}
	public Vendedor buscar(String cpf) {
		// Esta operação entre () vai ter significado mais à frente! 
		return (Vendedor)cadastro.buscar(cpf);
	}
	public Vendedor[] buscarTodos() {
		Serializable[] rets = cadastro.buscarTodos(Vendedor.class);
		Vendedor[] vends = new Vendedor[rets.length];
		for(int i=0; i<rets.length; i++) {
			// Esta operação entre () vai ter significado mais à frente! 
			vends[i] = (Vendedor)rets[i];
		}
		return vends;
	} 

}
