package br.edu.cesarschool.poo.codigoref.dao;

import java.io.Serializable;

import br.edu.cesarschool.next.po.persistenciaobjetos.CadastroObjetos;
import br.edu.cesarschool.poo.codigoref.entidade.Produto;

public class ProdutoDAO {
	private static final String BRANCO = "";
	private CadastroObjetos cadastro = new CadastroObjetos(Produto.class); 
	public boolean incluir(Produto prod) {
		Produto prodBusca = buscar(prod.getCodigo()); 
		if (prodBusca != null) { 
			return false;s
		} else {
			cadastro.incluir(prod, BRANCO + prod.getCodigo());
			return true;
		}		 
	}
	public boolean alterar(Produto prod) {
		Produto prodBusca = buscar(prod.getCodigo());
		if (prodBusca == null) {
			return false;
		} else {
			cadastro.alterar(prod, BRANCO + prod.getCodigo());
			return true;
		}		
	}
	public Produto buscar(long codigo) {
		// Esta operação entre () vai ter significado mais à frente! 
		return (Produto)cadastro.buscar(BRANCO + codigo);
	}
	public Produto[] buscarTodos() {
		Serializable[] rets = cadastro.buscarTodos(Produto.class);
		Produto[] prods = new Produto[rets.length];
		for(int i=0; i<rets.length; i++) {
			// Esta operação entre () vai ter significado mais à frente! 
			prods[i] = (Produto)rets[i];
		}
		return prods;
	} 

}