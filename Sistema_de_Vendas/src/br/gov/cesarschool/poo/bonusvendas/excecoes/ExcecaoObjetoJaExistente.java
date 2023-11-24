package br.gov.cesarschool.poo.bonusvendas.excecoes;

public class ExcecaoObjetoJaExistente extends Exception {

	public ExcecaoObjetoJaExistente() {
        super("Objeto ja existente");
    }

    public ExcecaoObjetoJaExistente(String mensagem) {
        super(mensagem);
    }
}
