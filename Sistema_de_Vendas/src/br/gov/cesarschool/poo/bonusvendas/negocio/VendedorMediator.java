package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.*;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.*;
import java.time.*;


public class VendedorMediator {

    
    private static VendedorMediator instance;

    private VendedorDAO vendedorCons;
    private AcumuloResgateMediator acumuloResgateCons;

    
    private VendedorMediator(VendedorDAO vendedorCons, AcumuloResgateMediator acumuloResgateCons) {
        this.vendedorCons = vendedorCons;
        this.acumuloResgateCons = acumuloResgateCons;
    }

    // Método público para obter a instância do Singleton
    public static VendedorMediator getInstance(VendedorDAO vendedor, AcumuloResgateMediator acumuloResgate) {
        // Se a instância ainda não existe, cria-a
        if (instance == null) {
            instance = new VendedorMediator(vendedor, acumuloResgate);
        }
        // Retorna a instância existente
        return instance;
    }
    private String validar(Vendedor vendedor) {
    	
    	//Validar CPF
    	String cpfAtual = vendedor.getCpf();
    	if (StringUtil.ehNuloOuBranco(cpfAtual) == true)  {
    	    return "CPF não informado";
    	} else if (ValidadorCPF.ehCpfValido(cpfAtual) == false){
    		return "CPF invalido";
    	}
    	
    	// Validar Nome
    	String nomeAtual = vendedor.getNomeCompleto();
    	if(StringUtil.ehNuloOuBranco(nomeAtual) == true) {
    		return "Nome completo nao informado";
    	}
    	//Validar Sexo
    	Sexo sexoAtual = vendedor.getSexo();
    	if(sexoAtual.getCodigo() != 1 && sexoAtual.getCodigo() != 2) {
    		return "Sexo nao informado";
    	}
    	//Validar idade
    	LocalDate dataNascimento = vendedor.getDataNascimento();
    	if (dataNascimento == null) {
    	    return "Data de nascimento nao informada";
    	}
    	LocalDate dataAtual = LocalDate.now();
    	Period periodo = Period.between(dataNascimento, dataAtual);
    	int idade = periodo.getYears();

    	 if (idade < 18) {
    	    return "Data de nascimento invalida";
    	}
    	 //Validar Renda
    	 double rendaAtual = vendedor.getRenda();
    	 if (rendaAtual < 0) {
     	    return "Renda menor que zero";
     	}
    	 
    	 
    	// Validar Endereco
    	 Endereco enderecoAtual = vendedor.getEndereco();
    	 if (enderecoAtual == null) {
    	     return "Endereco nao informado";
    	 }

    	 // Validar Cidade
    	 if (StringUtil.ehNuloOuBranco(enderecoAtual.getCidade()) == true) {
    	     return "Cidade nao informada";
    	 }

    	 // Validar Estado
    	 if (StringUtil.ehNuloOuBranco(enderecoAtual.getEstado()) == true) {
    	     return "Estado nao informado";
    	 }

    	 // Validar logradouro
    	 if (StringUtil.ehNuloOuBranco(enderecoAtual.getLogradouro()) == true) {
    	     return "Logradouro nao informado";
    	 } else if (enderecoAtual.getLogradouro().length() < 4) {
    	     return "Logradouro tem menos de 04 caracteres";
    	 }

    	 // Validar pais
    	 if (StringUtil.ehNuloOuBranco(enderecoAtual.getPais()) == true) {
    	     return "Pais nao informado";
    	 }

    	 // Validar numero
    	 if (enderecoAtual.getNumero() < 0) {
    	     return "Numero menor que zero";
    	 }

    	 
    	
    	return null;
    }
    
    
    // Método buscar
    public Vendedor buscar(String cpf) {
    	
    	Vendedor vendedorEncontrado = this.vendedorCons.buscar(cpf);
    	
    	if (vendedorEncontrado != null) {
            return vendedorEncontrado;
        }
    	
    	return null;
    }

}
