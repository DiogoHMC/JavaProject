package br.gov.cesarschool.poo.bonusvendas.negociov2;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.ValidadorCPF;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.StringUtil;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;
import br.gov.cesarschool.poo.bonusvendas.util.Ordenadora;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorVendedorNome;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorVendedorRenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VendedorMediator {
	private static VendedorMediator instancia;
	public static VendedorMediator getInstancia() {
		if (instancia == null) {
			instancia = new VendedorMediator();
		}
		return instancia;
	}
	private VendedorDAO repositorioVendedor;
	private AcumuloResgateMediator caixaDeBonusMediator;
	private VendedorMediator() {
		repositorioVendedor = new VendedorDAO();
		caixaDeBonusMediator = AcumuloResgateMediator.getInstancia();
	}
	
	public ResultadoInclusaoVendedor incluir(Vendedor vendedor) {
		long numeroCaixaBonus = 0;
		String msg = validar(vendedor);
		if (msg == null) {
			boolean ret = repositorioVendedor.incluir(vendedor);
			if (!ret) {
				msg = "Vendedor ja existente";
			} else {
				numeroCaixaBonus = caixaDeBonusMediator.gerarCaixaDeBonus(vendedor);
				if (numeroCaixaBonus == 0) {
					msg = "Caixa de bonus nao foi gerada";					
				}
			}
		}
		return new ResultadoInclusaoVendedor(numeroCaixaBonus, msg);
	}
	
	/* New Methods */ 
	public Vendedor[] gerarListagemClienteOrdenadaPorNome() {
        /* Chama o método buscarTodos do DAO para obter todos os vendedores */ 
        Vendedor[] vendedores = repositorioVendedor.buscarTodos();

        /* Verifica se há vendedores para ordenar */
        if (vendedores != null && vendedores.length > 0) {
            /* Ordena por nome vendedor utilizando as classes Ordenadora e ComparadorVendedorNome. */
        	ComparadorVendedorNome comparadorNome = ComparadorVendedorNome.getInstance();
        	Ordenadora.ordenar(vendedores, comparadorNome);

            /*Retorna a lista ordenada */
            return vendedores;
        } else {
            /* retorna um array vazio se não houver vendedores */
            return new Vendedor[0];
        }
    }
	
	public Vendedor[] gerarListagemClienteOrdenadaPorRenda() {
        // Chama o método buscarTodos do DAO para obter todos os vendedores
        Vendedor[] vendedores = repositorioVendedor.buscarTodos();

        /* Verifica se há vendedores para ordenar */
        if (vendedores != null && vendedores.length > 0) {
            /* Ordena os vendedores por renda, utilizando o ComparadorVendedorRenda */
            ComparadorVendedorRenda comparadorRenda = ComparadorVendedorRenda.getInstance();
            Ordenadora.ordenar(vendedores, comparadorRenda);

            /* Retorna a lista ordenada */
            return vendedores;
        } else {
        	 /* retorna um array vazio se não houver vendedores */
            return new Vendedor[0];
        }
    }
	
	public String alterar(Vendedor vendedor) {
		String msg = validar(vendedor);
		if (msg == null) {
			boolean ret = repositorioVendedor.alterar(vendedor);
			if (!ret) {
				msg = "Vendedor inexistente";
			}
		}
		return msg;
	}
	private String validar(Vendedor vendedor) {
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
		}
		if (dataNascimentoInvalida(vendedor.getDataNascimento())) {
			return "Data de nascimento invalida";
		}
		if (vendedor.getRenda() < 0) {
			return "Renda menor que zero";			
		}
		if (vendedor.getEndereco() == null) {
			return "Endereco nao informado";
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
		if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getCidade())) {
			return "Cidade nao informada";
		}
		if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getEstado())) {
			return "Estado nao informado";
		}		
		if (StringUtil.ehNuloOuBranco(vendedor.getEndereco().getPais())) {
			return "Pais nao informado";
		}					
		return null;
	}
	private boolean dataNascimentoInvalida(LocalDate dataNasc) {
		long yearsDifference = ChronoUnit.YEARS.between(dataNasc, LocalDate.now());
		return yearsDifference < 17;
	}

}
