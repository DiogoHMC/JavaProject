package br.gov.cesarschool.poo.bonusvendas.entidade;

import java.io.Serializable;

public enum TipoResgate implements Serializable {
	PRODUTO (1,"Produto"), 
	SERVICO (2,"Serviço"), 
	CASH (3,"Cash");
	private int codigo;
	private String nome;
	private TipoResgate(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	public int getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public static TipoResgate obterSexo(int codigo) {
		for (TipoResgate tipoResgate : TipoResgate.values()) {
			if (tipoResgate.codigo == codigo) {
				return tipoResgate;
			}
		}
		return null;
	}

}
