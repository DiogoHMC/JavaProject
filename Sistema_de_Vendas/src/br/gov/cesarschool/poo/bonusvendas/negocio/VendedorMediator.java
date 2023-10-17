package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.Endereco;
import br.gov.cesarschool.poo.bonusvendas.persistencia.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.util.ValidadorCPF;
import br.gov.cesarschool.poo.bonusvendas.util.StringUtil;

import java.time.LocalDate;
import java.time.Period;
import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.StringUtil;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.ValidadorCPF;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;

public class VendedorMediator {

    
    private static VendedorMediator instance;
    private VendedorDAO vendedorCons;
    private AcumuloResgateMediator acumuloResgateCons;

    // Construtor privado para evitar criação direta
    private VendedorMediator() {
    	vendedorCons = new VendedorDAO();
    	acumuloResgateCons = AcumuloResgateMediator.getInstancia();
    }

    // Método público para obter a instância do Singleton
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
        } else {
        	/* vendedor inexistente */
        	
        	/* validação CPF */
        	if (StringUtil.ehNuloOuBranco(vendedor.getCpf())){
        		return new ResultadoInclusaoVendedor(0,"CPF nao informado");
        	}
        	
        	if (!ValidadorCPF.ehCpfValido(vendedor.getCpf())) {
        		return new ResultadoInclusaoVendedor(0, "CPF invalido");
        	}
        	
        	/* validação do nome completo */
        	if (StringUtil.ehNuloOuBranco(vendedor.getNomeCompleto())){
        		return new ResultadoInclusaoVendedor(0, "Nome completo nao informado");
        	}
        	
        	/* validação sexo */
        	if (vendedor.getSexo() == null) {
        		return new ResultadoInclusaoVendedor(0,"Sexo nao informado");
        	}
        	
        	/* validação data de nascimento */
        	if (vendedor.getDataNascimento() == null) {
        		return new ResultadoInclusaoVendedor(0,"Data de nascimento nao informada");
        	} else {
        		LocalDate dataAtual = LocalDate.now();
        		LocalDate dataNascimento = vendedor.getDataNascimento();
        		Period idade = Period.between(dataNascimento, dataAtual);
        		if (idade.getYears() < 17) {
        			return new ResultadoInclusaoVendedor(0, "Data de nascimento invalida");
        		}
        	}
        	
        	/* validação da renda */
        	if (vendedor.getRenda() < 0) {
        		return new ResultadoInclusaoVendedor(0, "Renda menor que zero");
        	}
        	
        	/* validação do Endereço */
        	if (vendedor.getEndereco() == null) {
        		return new ResultadoInclusaoVendedor(0, "Endereco nao informado");
        	} else {
        		
        		/* validacao do logradouro */
        		if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getLogradouro())) {
        			return new ResultadoInclusaoVendedor(0, "Logradouro nao informado");
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
    
    
    public String alterar(Vendedor vendedor) {
        
        if (StringUtil.ehNuloOuBranco(vendedor.getCpf())) {
            return "CPF nao informado";
        }
        
        if (!ValidadorCPF.ehCpfValido(vendedor.getCpf())) {
            return "CPF invalido";
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
            if (idade.getYears() < 17) {
                return "Data de nascimento invalida";
            }
        }
        
        if (vendedor.getRenda() < 0) {
            return "Renda menor que zero";
        }
        
        if (vendedor.getEndereco() == null) {
            return "Endereco nao informado";
        } else {
            
            if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getLogradouro())) {
                return "Logradouro nao informado";
            }
            if (vendedor.getEndereco().getLogradouro().length() < 4) {
                return "Logradouro tem menos de 04 caracteres";
            }
            
            if (vendedor.getEndereco().getNumero() < 0) {
                return "Numero menor que zero";
            }
            
            if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getCidade())) {
                return "Cidade nao informada";
            }
            
            if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getEstado())) {
                return "Estado nao informado";
            }
            
            if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getPais())) {
                return "Pais nao informado";
            }       
            
        }

        Vendedor vendedorExistente = buscar(vendedor.getCpf());
        
        if (vendedorExistente == null) {
            return "Vendedor inexistente";
        }

       
        try {
            vendedorCons.alterar(vendedor);
            return null; 
        } 
        catch (Exception e) {
            return "Erro ao atualizar o vendedor: " + e.getMessage();
        }
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
