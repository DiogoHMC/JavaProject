package br.gov.cesarschool.poo.bonusvendas.entidade.geral;

public class Vendedor {
	
	private String cpf;
	private String nomeCompleto;
	private br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo sexo;
	private java.time.LocalDate dataNascimento;
	private double renda;
	private br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco endereco;
	
	public Vendedor(String cpf, String nomeCompleto, br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo sexo, java.time.LocalDate dataNascimento, double renda, br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco endereco) {
		
		this.setCpf(cpf);
		this.nomeCompleto = nomeCompleto;
		this.setSexo(sexo);
		this.setDataNascimento(dataNascimento);
		this.setRenda(renda);
		this.setEndereco(endereco);
	}

	public String getCpf() {
		return cpf;
	}

	private void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo getSexo() {
		return sexo;
	}

	public void setSexo(br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo sexo) {
		this.sexo = sexo;
	}

	public java.time.LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(java.time.LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public double getRenda() {
		return renda;
	}

	public void setRenda(double renda) {
		this.renda = renda;
	}

	public br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco endereco) {
		this.endereco = endereco;
	}
	
}
