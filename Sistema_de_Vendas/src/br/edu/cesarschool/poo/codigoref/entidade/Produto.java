package br.edu.cesarschool.poo.codigoref.entidade;

import java.lang.String;
import java.io.Serializable;

public class Produto implements Serializable {
	
	private long codigo;
	private String nome;
	private double preco;
	private TipoProduto tipo;
	
	public Produto(long codigo, String nome, double preco, TipoProduto tipo) {
		
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.tipo = tipo;
	}
	
	public String getNome() {
		
		return nome;
	}
	
	public void setNome(String nome) {
		
		this.nome = nome;
	}
	
	public double getPreco() {
		
		return preco;
	}
	
	public void setPreco(double preco) {
		
		this.preco = preco;
	}
	
	public TipoProduto getTipo() {
		
		return tipo;
	}
	
	public void setTipo(TipoProduto tipo) {
		
		this.tipo = tipo;
	}
	public long getCodigo() {
		
		return codigo;
	}
}