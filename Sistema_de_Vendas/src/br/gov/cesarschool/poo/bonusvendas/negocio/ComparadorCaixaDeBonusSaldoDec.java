package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.util.Comparador;

public class ComparadorCaixaDeBonusSaldoDec implements Comparador {

   
    private static final ComparadorCaixaDeBonusSaldoDec instance = new ComparadorCaixaDeBonusSaldoDec();

    
    private ComparadorCaixaDeBonusSaldoDec() {}

    
    public static ComparadorCaixaDeBonusSaldoDec getInstance() {
        return instance;
    }

    // Implementação do método comparar para comparar CaixasDeBonus por saldo em ordem decrescente
    @Override
    public int comparar(Object o1, Object o2) {
        
        if (!(o1 instanceof CaixaDeBonus) || !(o2 instanceof CaixaDeBonus)) {
            throw new IllegalArgumentException("Os objetos devem ser instâncias da classe CaixaDeBonus");
        }

        CaixaDeBonus caixa1 = (CaixaDeBonus) o1;
        CaixaDeBonus caixa2 = (CaixaDeBonus) o2;

        // Lógica de comparação invertida para ordem decrescente de saldo
        if (caixa1.getSaldo() > caixa2.getSaldo()) {
            return -1; 
        } else if (caixa1.getSaldo() == caixa2.getSaldo()) {
            return 0;
        } else {
            return 1; 
        }
    }
}
