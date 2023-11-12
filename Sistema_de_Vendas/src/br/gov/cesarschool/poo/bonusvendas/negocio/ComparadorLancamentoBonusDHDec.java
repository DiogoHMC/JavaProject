package br.gov.cesarschool.poo.bonusvendas.negocio;

import java.util.Comparator;
import java.time.LocalDateTime;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;

public class ComparadorLancamentoBonusDHDec implements Comparator {
	private static final ComparadorLancamentoBonusDHDec instance = new ComparadorLancamentoBonusDHDec();

    // Construtor privado para Singleton
    private ComparadorLancamentoBonusDHDec() {}

    public static ComparadorLancamentoBonusDHDec getInstance() {
        return instance;
    }

    @Override
    public int compare(Object o1, Object o2) {
        LocalDateTime dataHora1 = ((LancamentoBonus) o1).getDataHoraLancamento();
        LocalDateTime dataHora2 = ((LancamentoBonus) o2).getDataHoraLancamento();

        // Compare em ordem DECRESCENTE de dataHoraLancamento
        return dataHora2.compareTo(dataHora1);
    }
}
