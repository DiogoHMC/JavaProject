package br.gov.cesarschool.poo.bonusvendas.entidade.geral;
import java.lang.String;

public enum Sexo {

	MASCULINO(1, "masculino"), 
	FEMININO(2, "feminino");
	
	int codigo;
	String descricao;
	
	private Sexo(int codigo, String descricao) {
		
		this.codigo = codigo;
		this.descricao = descricao;
		
	}
	
    public int getCodigo() {
    	
        return codigo;
    }

    public String getDescricao() {
    	
        return descricao;
    }
	
}
