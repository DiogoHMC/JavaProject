package br.gov.cesarschool.poo.bonusvendas.excecoes;

public class ExcecaoObjetoNaoExistente extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcecaoObjetoNaoExistente() {
        super("Objeto nao existente");
    }

    public ExcecaoObjetoNaoExistente(String mensagem) {
        super(mensagem);
    }
}
