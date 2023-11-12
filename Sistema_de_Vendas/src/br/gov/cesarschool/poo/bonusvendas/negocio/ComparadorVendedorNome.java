package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.util.Comparador;

public class ComparadorVendedorNome implements Comparador {
	
    private static final ComparadorVendedorNome instance = new ComparadorVendedorNome();

    // Construtor privado para Singleton
    private ComparadorVendedorNome() {}

    public static ComparadorVendedorNome getInstance() {
        return instance;
    }

    @Override
    public int comparar(Object o1, Object o2) {

        int resultado = ((Vendedor) o1).getNomeCompleto().compareTo(((Vendedor) o2).getNomeCompleto());

        if (resultado > 0) {
        	
            return 1;
        } 
        
        else if (resultado == 0) {
            
        	return 0;
        } 
        
        else {
        
        	return -1;
        }
    }
}
