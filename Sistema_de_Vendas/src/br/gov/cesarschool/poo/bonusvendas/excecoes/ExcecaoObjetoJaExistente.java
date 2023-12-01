package br.gov.cesarschool.poo.bonusvendas.excecoes;

public class ExcecaoObjetoJaExistente extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcecaoObjetoJaExistente() {
        super("Objeto ja existente");
    }

    public ExcecaoObjetoJaExistente(String mensagem) {
        super(mensagem);
    }
}
