package br.gov.cesarschool.poo.bonusvendas.excecoes;

import java.util.ArrayList;
import java.util.List;

public class ExcecaoValidacao extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ErroValidacao> errosValidacao = new ArrayList<>();

    public ExcecaoValidacao(String message) {
        super(message);
    }

    public ExcecaoValidacao(List<ErroValidacao> errosValidacao) {
        if (errosValidacao != null) {
            this.errosValidacao = errosValidacao;
        }
    }

    public List<ErroValidacao> getErrosValidacao() {
        return errosValidacao;
    }
}