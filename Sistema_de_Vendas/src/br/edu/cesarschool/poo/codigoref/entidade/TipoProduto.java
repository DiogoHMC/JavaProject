package br.edu.cesarschool.poo.codigoref.entidade;

import java.lang.String;
/**
 * Pesquisar sobre "enum" em Java, ler sobre e se inteirar do assunto
 */
public enum TipoProduto {
	
	ALIMENTO(1, "Alimento"), 
	LIMPEZA(2, "Limpeza"), 
	ELETRO(3,"Eletro");  
	
	private int codigo;
	private String descricao;
	
	private TipoProduto(int codigo, String descricao) {
		
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		
		return codigo;
	}

	public String getDescricao() {
		
		return descricao;
	}

	
	public static TipoProduto obterPorCodigo(int codigo) {		
//		for (int i=0; i<tiposProdutos.length; i++) {
//			if (tiposProdutos[i].getCodigo() == codigo) {
//				return tiposProdutos[i];
//			}
//		}
//		return null;
		TipoProduto[] tiposProdutos = TipoProduto.values();
		for (TipoProduto tipoProduto : tiposProdutos) {
			if (tipoProduto.getCodigo() == codigo) {
				return tipoProduto;
			}
		}
		return null;
	}
}