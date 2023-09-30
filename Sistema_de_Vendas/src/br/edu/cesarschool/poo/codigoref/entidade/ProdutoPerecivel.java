package br.edu.cesarschool.poo.codigoref.entidade;

import java.lang.String;


public class ProdutoPerecivel extends Produto {
	
	private int prazoValidade;

	public ProdutoPerecivel(long codigo, String nome, double preco, TipoProduto tipo, int prazoValidade) {
		
		super(codigo, nome, preco, tipo);
		this.prazoValidade = prazoValidade;		
	}

	public int getPrazoValidade() {
		
		return prazoValidade;
	}

	public void setPrazoValidade(int prazoValidade) {
		
		this.prazoValidade = prazoValidade;
	} 
}