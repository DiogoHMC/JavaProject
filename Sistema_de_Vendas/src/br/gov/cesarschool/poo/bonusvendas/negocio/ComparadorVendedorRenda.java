package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.util.Comparador;

public class ComparadorVendedorRenda implements Comparador {
	private static final ComparadorVendedorRenda instance = new ComparadorVendedorRenda();

    /* Constructor privado para Singleton */
    private ComparadorVendedorRenda() {}

    public static ComparadorVendedorRenda getInstance() {
        return instance;
    }

    @Override // método está sendo sobrescrito da classe pai
    public int comparar(Object o1, Object o2) {
        double renda1 = ((Vendedor) o1).getRenda();
        double renda2 = ((Vendedor) o2).getRenda();

        if (renda1 > renda2) {
            return 1;
        } else if (renda1 < renda2) {
            return -1;
        } else {
            return 0;
        }
    }
}
