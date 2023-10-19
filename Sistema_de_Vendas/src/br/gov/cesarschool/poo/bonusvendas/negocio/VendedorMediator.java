package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.ValidadorCPF;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.StringUtil;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;

import java.time.LocalDate;
import java.time.Period;

public class VendedorMediator {

    
	private static VendedorMediator instance;
    private VendedorDAO vendedorCons;

    // Construtor privado para evitar criação direta
    private VendedorMediator() {
        vendedorCons = new VendedorDAO();
    }

    /* Método público para obter a instância do Singleton */
    public static VendedorMediator getInstancia() {
        // Se a instância ainda não existe, cria-a
        if (instance == null) {
            instance = new VendedorMediator();
        }
        // Retorna a instância existente
        return instance;
    }
    
    /* Método para validar dados do vendedor recebido no objeto */
    public ResultadoInclusaoVendedor incluir(Vendedor vendedor) {
    	
    	Vendedor vendedorExistente = buscar(vendedor.getCpf());
    	
    	/* vendedor já existe */
    	if (vendedorExistente != null) {
    		return new ResultadoInclusaoVendedor(0, "Vendedor ja existente");
        }
    	
    	/* validacao campos */
    	String validacao = validar(vendedor);
    	if (validacao != null) {
    		return new ResultadoInclusaoVendedor(0, validacao);
    	}
    	
    	/* Falha ao incluir vendedor */
    	if (!vendedorCons.incluir(vendedor)) {
            return new ResultadoInclusaoVendedor(0, "Falha ao incluir vendedor");
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
   
    public String alterar(Vendedor vendedor) {
        Vendedor vendedorExistente = buscar(vendedor.getCpf());
        
        if (vendedorExistente == null) {
            return "Vendedor inexistente";
        }

    	//String validacao = validar(vendedor);
        if (validar(vendedor) != null) {
        	return validar(vendedor);
        }
    	  	
       
        try {
            vendedorCons.alterar(vendedor);
            return null; 
        } 
        catch (Exception e) {
            return "Erro ao atualizar o vendedor: " + e.getMessage();
        }
    }
    
    private String validar(Vendedor vendedor) {
    	  	  
	  	  if (vendedor.getCpf() == null) {
	            return "CPF nao informado";
	      }
	        
	      if (StringUtil.ehNuloOuBranco(vendedor.getCpf())) {
	              return "CPF nao informado";
	      }

	      if (!ValidadorCPF.ehCpfValido(vendedor.getCpf())) {
	          return "CPF invalido";
	          }
	 
	          if (vendedor.getNomeCompleto()  == null) {
	              return "Nome completo nao informado";
	      }
	      
	      if (StringUtil.ehNuloOuBranco(vendedor.getNomeCompleto())) {
	          return "Nome completo nao informado";
	      }
	      
	
	      if (vendedor.getSexo() == null) {
	          return "Sexo nao informado";
	      }
	      
	      if (vendedor.getDataNascimento() == null) {
	          return "Data de nascimento nao informada";
	      } else {
	          LocalDate dataAtual = LocalDate.now();
	          LocalDate dataNascimento = vendedor.getDataNascimento();
	          Period idade = Period.between(dataNascimento, dataAtual);
	          if (idade.getYears() < 18) {
	              return "Data de nascimento invalida";
	          }
	      }
	      
	      if (vendedor.getRenda() < 0) {
	          return "Renda menor que zero";
	      }
	      
	      if (vendedor.getEndereco() == null) {
	          return "Endereco nao informado";
	      } else {
	          
	    	  if (vendedor.getEndereco().getLogradouro() == null) {
	              return "Logradouro nao informado";
	          }
	    	  
	          if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getLogradouro())) {
	              return "Logradouro nao informado";
	          }
	          if (vendedor.getEndereco().getLogradouro().length() < 4) {
	              return "Logradouro tem menos de 04 caracteres";
	          }
	          
	          if (vendedor.getEndereco().getNumero() < 0) {
	              return "Numero menor que zero";
	          }
	          
	          if (vendedor.getEndereco().getCidade() == null) {
	              return "Cidade nao informada";
	          }
	          
	          if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getCidade())) {
	              return "Cidade nao informada";
	          }
	          
	          if (vendedor.getEndereco().getEstado() == null) {
	              return "Estado nao informado";
	          }
	          
	          if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getEstado())) {
	              return "Estado nao informado";
	              }
	  
	              if (vendedor.getEndereco().getPais() == null) {
	                  return "Pais nao informado";
	          }    
	          
	          if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getPais())) {
	              return "Pais nao informado";
	          }   
              

              
          }
		return null;
    }
    
    // Método buscar
    public Vendedor buscar(String cpf) {
    	
    	return vendedorCons.buscar(cpf);
    }


}