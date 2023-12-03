package br.gov.cesarschool.poo.bonusvendas.negociov2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorVendedorNome;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorVendedorRenda;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.StringUtil;
import br.gov.cesarschool.poo.bonusvendas.negocio.geral.ValidadorCPF;
import br.gov.cesarschool.poo.bonusvendas.util.Ordenadora;

import br.gov.cesarschool.poo.bonusvendas.daov2.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ErroValidacao;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoValidacao;

public class VendedorMediator {
	
	//Attributes
	private VendedorDAO repositorioVendedor;
    private AcumuloResgateMediator caixaDeBonusMediator;
    
    		//Singleton
    private static VendedorMediator instance;
    
    //Constructor
    public VendedorMediator() {
        this.repositorioVendedor = new VendedorDAO();
        this.caixaDeBonusMediator = AcumuloResgateMediator.getInstancia();
    }
    
    		//Singleton
    public static VendedorMediator getInstancia() {
        if (instance == null) {
            instance = new VendedorMediator();
        }
        return instance;
    }
    
    //Methods
    public long incluir(Vendedor vendedor) throws ExcecaoObjetoJaExistente, ExcecaoValidacao {
        validar(vendedor);
        repositorioVendedor.incluir(vendedor);
        long retornoCaixaDeBonus = caixaDeBonusMediator.gerarCaixaDeBonus(vendedor);
        return retornoCaixaDeBonus;
    }
    
    public void alterar(Vendedor vendedor) throws ExcecaoObjetoNaoExistente, ExcecaoValidacao {
        validar(vendedor);
        repositorioVendedor.alterar(vendedor);
    }
    
    public Vendedor buscar(String cpf) throws ExcecaoObjetoNaoExistente {
    	return repositorioVendedor.buscar(cpf);
    }
    
    private void validar(Vendedor vendedor) throws ExcecaoValidacao {
        List<ErroValidacao> erros = new ArrayList<>();
    	
        if (StringUtil.ehNuloOuBranco(vendedor.getCpf())) {
            erros.add(new ErroValidacao(101, "CPF nao informado"));
        } else if (!ValidadorCPF.ehCpfValido(vendedor.getCpf())) {
            erros.add(new ErroValidacao(102, "CPF invalido"));
        }
        
        if (StringUtil.ehNuloOuBranco(vendedor.getNomeCompleto())) {
            erros.add(new ErroValidacao(103, "Nome completo nao informado"));
        }
        
        if (vendedor.getSexo() == null) {
            erros.add(new ErroValidacao(104, "Sexo nao informado"));
        }
        
        if (vendedor.getDataNascimento() == null) {
            erros.add(new ErroValidacao(105, "Data de nascimento nao informada"));
        } else {
            LocalDate dataAtual = LocalDate.now();
            if (!vendedor.getDataNascimento().isBefore(dataAtual.minusYears(18))) {
                erros.add(new ErroValidacao(106, "Data de nascimento invalida"));
            }
        }
        
        if (vendedor.getRenda() < 0) {
            erros.add(new ErroValidacao(107, "Renda menor que zero"));
        }
        
        Endereco endereco = vendedor.getEndereco();
        if (endereco == null) {
            erros.add(new ErroValidacao(108, "Endereco nao informado"));
        } else {
            if (StringUtil.ehNuloOuBranco(endereco.getLogradouro())) {
                erros.add(new ErroValidacao(109, "Logradouro nao informado"));
            } else if (endereco.getLogradouro().length() < 4) {
                erros.add(new ErroValidacao(110, "Logradouro tem menos de 04 caracteres"));
            }
            if (endereco.getNumero() < 0) {
                erros.add(new ErroValidacao(111, "Numero menor que zero"));
            }
            if (StringUtil.ehNuloOuBranco(endereco.getCidade())) {
                erros.add(new ErroValidacao(112, "Cidade nao informada"));
            }
            if (StringUtil.ehNuloOuBranco(endereco.getEstado())) {
                erros.add(new ErroValidacao(113, "Estado nao informado"));
            }
            if (StringUtil.ehNuloOuBranco(endereco.getPais())) {
                erros.add(new ErroValidacao(114, "Pais nao informado"));
            }
        }
        
        if (!erros.isEmpty()) {
            throw new ExcecaoValidacao(erros);
        }
    }
    
    public Vendedor[] gerarListagemClienteOrdenadaPorRenda() {
        Vendedor[] vendedores = repositorioVendedor.buscarTodos();


        Ordenadora.ordenar(vendedores, ComparadorVendedorRenda.getInstance());

        return vendedores;
    }
    public Vendedor[] gerarListagemClienteOrdenadaPorNome() {
    	
        Vendedor[] vendedores = repositorioVendedor.buscarTodos();
        Ordenadora.ordenar(vendedores, ComparadorVendedorNome.getInstance());
        
        return vendedores;
    }
    
    
}