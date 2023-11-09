package br.gov.cesarschool.poo.bonusvendas.entidade.geral;

import java.io.Serializable;

public enum Sexo implements Serializable {
	FEMININO (1,"Feminino"), 
	MASCULINO (2,"Masculino");
	private int codigo;
	private String nome;
	private Sexo(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	public int getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public static Sexo obterSexo(int codigo) {
		for (Sexo sexo : Sexo.values()) {
			if (sexo.codigo == codigo) {
				return sexo;
			}
		}
		return null;
	}
}
