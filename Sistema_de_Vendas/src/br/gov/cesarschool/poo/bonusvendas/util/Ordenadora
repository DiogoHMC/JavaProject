package br.gov.cesarschool.poo.bonusvendas.util;

public class Ordenadora {
	
	// Construtor privado para evitar instanciação da classe
    private Ordenadora() {}

    public static void ordenar(Object[] lista, Comparador comp) {
    	
    	for (int i = 0; i < lista.length - 1; i++) {
        
    		for (int k = i + 1; k < lista.length; k++) {
            
        		if (comp.comparar(lista[i], lista[k]) > 0) {
                
        			
                    Object temp = lista[i];
                    lista[i] = lista[k];
                    lista[k] = temp;
                }
            }
        }
    }
}
