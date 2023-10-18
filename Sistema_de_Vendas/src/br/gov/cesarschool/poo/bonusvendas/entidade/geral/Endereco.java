package br.gov.cesarschool.poo.bonusvendas.entidade.geral;

import java.lang.String;
import java.io.Serializable;

public class Endereco implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* attribute  */
	private String logradouro;
	private int numero;
	private String complemento;
	private String cep;
	private String cidade;
	private String estado;
	private String pais;
	
	/* constructor */ 
	public Endereco(String logradouro, int numero, String complemento, String cep, String cidade, String estado, String pais) {
		
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
	}
	
    public String getLogradouro() {
        return logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCep() {
        return cep;
    }
    
    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getPais() {
        return pais;
    }
}