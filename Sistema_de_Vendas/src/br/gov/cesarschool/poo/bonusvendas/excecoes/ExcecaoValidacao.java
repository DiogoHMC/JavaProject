package br.gov.cesarschool.poo.bonusvendas.excecoes;

import java.util.ArrayList;
import java.util.List;

public class ExcecaoValidacao extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ErroValidacao> errosValidacao;
	
	// Construtor que aceita uma mensagem
    public ExcecaoValidacao(String mensagem) {
        super(mensagem);
        this.errosValidacao = new ArrayList<>();
    }

    // Construtor que aceita uma lista de erros
    public ExcecaoValidacao(List<ErroValidacao> errosValidacao) {
        super("Erros na validação");
        this.errosValidacao = (errosValidacao != null) ? new ArrayList<>(errosValidacao) : new ArrayList<>();
    }

    // Construtor que aceita uma mensagem e uma causa (exceção anterior)
    public ExcecaoValidacao(String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.errosValidacao = new ArrayList<>();
    }

    public List<ErroValidacao> getErrosValidacao() {
        return errosValidacao;
    }
}