package br.gov.cesarschool.poo.bonusvendas.negocio;

import java.time.LocalDate;
import java.time.Period;
import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.*;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.*;
import java.time.*;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;


public class VendedorMediator {

    
    private static VendedorMediator instance;
    //private java.time.LocalDate dataNascimento;
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

    /* Método para validar dados do vendedor recebido no objeto */
    public ResultadoInclusaoVendedor incluir(Vendedor vendedor) {
    	Vendedor vendedorExistente = buscar(vendedor.getCpf());
    	
    	/* vendedor já existe */
    	if (vendedorExistente != null) {
    		return new ResultadoInclusaoVendedor(0, "Vendedor ja existente");
        } else {
        	/* vendedor inexistente */
        	
        	/* validação VPF */
        	if (StringUtil.ehNuloOuBranco(vendedor.getCpf())){
        		return new ResultadoInclusaoVendedor(0,"CPF não informado");
        	}
        	
        	if (!ValidadorCPF.ehCpfValido(vendedor.getCpf())) {
        		return new ResultadoInclusaoVendedor(0, "CPF inválido");
        	}
        	
        	/* validação do nome completo */
        	if (StringUtil.ehNuloOuBranco(vendedor.getNomeCompleto())){
        		return new ResultadoInclusaoVendedor(0, "Nome completo não informado");
        	}
        	
        	/* validação sexo */
        	if (vendedor.getSexo() == null) {
        		return new ResultadoInclusaoVendedor(0,"Sexo não informado");
        	}
        	
        	/* validação data de nascimento */
        	if (vendedor.getDataNascimento() == null) {
        		return new ResultadoInclusaoVendedor(0,"Data de nascimento não informado");
        	} else {
        		LocalDate dataAtual = LocalDate.now();
        		LocalDate dataNascimento = vendedor.getDataNascimento();
        		Period idade = Period.between(dataNascimento, dataAtual);
        		if (idade.getYears() < 17) {
        			return new ResultadoInclusaoVendedor(0, "Data de nascimento menor ou igual à data atual menos 17 anos");
        		}
        	}
        	
        	/* validação da renda */
        	if (vendedor.getRenda() < 0) {
        		return new ResultadoInclusaoVendedor(0, "Renda menor que zero");
        	}
        	
        	/* validação do Endereço */
        	if (vendedor.getEndereco() == null) {
        		return new ResultadoInclusaoVendedor(0, "Endereço não informado");
        	} else {
        		
        		/* validacao do logradouro */
        		if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getLogradouro())) {
        			return new ResultadoInclusaoVendedor(0, "Logradouro não informado");
        		}
        		if (vendedor.getEndereco().getLogradouro().length() < 4) {
        			return new ResultadoInclusaoVendedor(0, "Logradouro tem menos de 04 caracteres");
        		}
        		
        		/* validacao do número */
        		if (vendedor.getEndereco().getNumero() < 0) {
        			return new ResultadoInclusaoVendedor(0, "Numero menor que zero");
        		}
        		
        		/* validacao da cidade */ 
        		if(StringUtil.ehNuloOuBranco(vendedor.getEndereco().getCidade())) {
        			return new ResultadoInclusaoVendedor(0, "Cidade nao informada");
        		}
        		
        		/* validacao do estado */
        		if(StringUtil.ehNuloOuBranco(vendedor.getEndereco().getEstado())) {
        			return new ResultadoInclusaoVendedor(0,"Estado nao informado");
        		}
        		
        		/* validacao do pais */
        		if(StringUtil.ehNuloOuBranco(vendedor.getEndereco().getPais())) {
        			return new ResultadoInclusaoVendedor(0, "Pais nao informado");
        		}       		
        	}
        	
        	/* gerar caixa de bonus vendedor */
        	AcumuloResgateMediator acumuloResgateMediator = new AcumuloResgateMediator();
        	long numeroCaixaDeBonus = acumuloResgateMediator.gerarCaixaDeBonus(vendedor);
        	if (numeroCaixaDeBonus == 0) {
        		return new ResultadoInclusaoVendedor(0,"Caixa de bonus nao foi gerada");
        	} else {
        		return new ResultadoInclusaoVendedor(numeroCaixaDeBonus, null);
        	}
			
		}
    }
    

    public Vendedor buscar(String cpf) {
    	
    	Vendedor vendedorEncontrado = this.vendedorCons.buscar(cpf);
    	
    	if (vendedorEncontrado != null) {
            return vendedorEncontrado;
        }
    	
    	return null;
    }
    
    public String alterar(Vendedor vendedor) {
        
        if (vendedor == null) {
            return "Vendedor nulo não pode ser alterado.";
        }

        
        if (vendedor.getNomeCompleto() == null || vendedor.getNomeCompleto().isEmpty()) {
            return "Nome do vendedor é obrigatório.";
        }

        if (vendedor.getCpf() == null || vendedor.getCpf().isEmpty()) {
            return "CPF do vendedor é obrigatório.";
        }

      
        Vendedor vendedorExistente = buscar(vendedor.getCpf());
        
        if (vendedorExistente == null) {
            return "Vendedor com CPF " + vendedor.getCpf() + " não encontrado no repositório.";
        }

       
        try {
            vendedorCons.alterar(vendedor);
            return null; 
        } 
        catch (Exception e) {
            return "Erro ao atualizar o vendedor: " + e.getMessage();
        }
    }

}
