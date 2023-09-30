package br.edu.cesar.poo.projeto.produto;

import java.lang.String;

public class TipoProdutoClasse {
	
	public static final TipoProdutoClasse ALIMENTO = new TipoProdutoClasse(1, "Alimento");
	public static final TipoProdutoClasse LIMPEZA = new TipoProdutoClasse(2, "Limpeza");
	public static final TipoProdutoClasse ELETRO = new TipoProdutoClasse(3, "Eletro");
	private static final TipoProdutoClasse[] ELEMENTOS = {ALIMENTO, LIMPEZA, ELETRO};
	private int codigo;
	private String descricao;
	
	private TipoProdutoClasse(int codigo, String descricao) {
		
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		
		return codigo;
	}

	public String getDescricao() {
		
		return descricao;
	}
	public static TipoProdutoClasse obterPorCodigo(int codigo) {
		
		for (TipoProdutoClasse tipoProduto : ELEMENTOS) {
			
			if (tipoProduto.getCodigo() == codigo) {
				
				return tipoProduto;
			}
		}
		
		return null;
	}
}