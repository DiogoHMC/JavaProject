package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;

public class VendedorDAO {
	
    private static final String BRANCO = "";
    private CadastroObjetos cadastro = new CadastroObjetos(Vendedor.class);
    
    public boolean incluir (Vendedor cpf) {
    	
    	Vendedor prodBusca = buscar(cpf.getCpf()); 
    	
		if (prodBusca != null) { 
			
			return false;
		} 
		else {
			
			cadastro.incluir(cpf, BRANCO + cpf.getCpf());
			return true;
		}
    }
    
    public boolean alterar(Vendedor cpf) {
    	
    	Vendedor prodBusca = buscar(cpf.getCpf());
		
		if (prodBusca == null) {
			return false;
		} 
		else {
			
			cadastro.alterar(cpf, BRANCO + cpf.getCpf());
			return true;
		}		
	}
    
	public Vendedor buscar(String cpf) {
		
		return (Vendedor)cadastro.buscar(BRANCO + cpf);
	}
	
	public Vendedor[] buscarTodos() {
		
		Serializable[] rets = cadastro.buscarTodos(Vendedor.class);
		
		Vendedor[] prods = new Vendedor[rets.length];
		
		for(int i=0; i<rets.length; i++) {

			prods[i] = (Vendedor)rets[i];
		}
		
		return prods;
	} 
}
