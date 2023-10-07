package br.gov.cesarschool.poo.bonusvendas.testes;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.negocio.ResultadoInclusaoVendedor;
import br.gov.cesarschool.poo.bonusvendas.negocio.VendedorMediator;
import x.y.z.w.k.Glosb;

public class TesteVendedorMediator extends TesteGeral {
	
	private static final String NUMERO_MENOR_QUE_ZERO = "Numero menor que zero";
	private static final String LOGRADOURO_TEM_MENOS_DE_04_CARACTERES = "Logradouro tem menos de 04 caracteres";
	private static final String ENDERECO_NAO_INFORMADO = "Endereco nao informado";
	private static final String RENDA_MENOR_QUE_ZERO = "Renda menor que zero";
	private static final String DATA_DE_NASCIMENTO_INVALIDA = "Data de nascimento invalida";
	private static final String DATA_DE_NASCIMENTO_NAO_INFORMADA = "Data de nascimento nao informada";
	private static final String SEXO_NAO_INFORMADO = "Sexo nao informado";
	private static final String CPF_INVALIDO = "CPF invalido";	
	private static final String SUF_NEW = "_NEW";
	private static final int NUMERO_CAIXA_BONUS_NAO_GERADA = 0;
	private static final String PAIS_NAO_INFORMADO = "Pais nao informado";
	private static final String ESTADO_NAO_INFORMADO = "Estado nao informado";
	private static final String CIDADE_NAO_INFORMADA = "Cidade nao informada";
	private static final String NOME_COMPLETO_NAO_INFORMADO = "Nome completo nao informado";
	private static final String LOGRADOURO_NAO_INFORMADO = "Logradouro nao informado";
	private static final String CPF_NAO_INFORMADO = "CPF nao informado";
	private static final LocalDate OUTRA_DATA_NASC_VALIDA = LocalDate.parse("2003-05-12");	
	
	private static final Vendedor[] VENDS_INVALIDOS = {
			new Vendedor(null, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor("  ", NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor("12345678901", NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, null, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, "   ", 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					null, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, null, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, LocalDate.parse("2022-01-01"), RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, -3000.11,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0, null),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(null, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(BRANCO, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco("ABC", NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, -1, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		null, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(BRANCO, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, null, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, BRANCO, PAIS_VALIDO + SUF_NEW)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, null)),
			new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
					Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 2000.0,
			        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
			        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, "   "))
	};
	private static final String[] MSGS_VENDS_INVALIDOS = {
			CPF_NAO_INFORMADO,
			CPF_NAO_INFORMADO,
			CPF_INVALIDO,
			NOME_COMPLETO_NAO_INFORMADO,
			NOME_COMPLETO_NAO_INFORMADO,
			SEXO_NAO_INFORMADO,
			DATA_DE_NASCIMENTO_NAO_INFORMADA,
			DATA_DE_NASCIMENTO_INVALIDA,
			RENDA_MENOR_QUE_ZERO,
			ENDERECO_NAO_INFORMADO,
			LOGRADOURO_NAO_INFORMADO,
			LOGRADOURO_NAO_INFORMADO,
			LOGRADOURO_TEM_MENOS_DE_04_CARACTERES,
			NUMERO_MENOR_QUE_ZERO,
			CIDADE_NAO_INFORMADA,
			CIDADE_NAO_INFORMADA,
			ESTADO_NAO_INFORMADO,
			ESTADO_NAO_INFORMADO,
			PAIS_NAO_INFORMADO,
			PAIS_NAO_INFORMADO			
	};
	
	private VendedorMediator mediator = VendedorMediator.getInstancia();
	
	@Test
	public void testCpfNaoPreenchido() {
		Vendedor vend = new Vendedor(null, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, CPF_NAO_INFORMADO);		
		vend = new Vendedor("  ", NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));	
		assertInclusaoNaoRealizada(vend, CPF_NAO_INFORMADO);
	}	
	@Test
	public void testCpfInvalido() {
		Vendedor vend = new Vendedor("12345678901", NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, CPF_INVALIDO);
	}
	@Test
	public void testNomeCompletoNaoInformado() {
		Vendedor vend = new Vendedor(CPF_VALIDO, null, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, NOME_COMPLETO_NAO_INFORMADO);
		vend = new Vendedor(CPF_VALIDO, "   ", 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, NOME_COMPLETO_NAO_INFORMADO);
	}
	@Test
	public void testSexoNaoInformado() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				null, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, SEXO_NAO_INFORMADO);
	}
	@Test
	public void testDataNascimentoNaoInformada() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, null, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, DATA_DE_NASCIMENTO_NAO_INFORMADA);
	}
	@Test
	public void testDataNascimentoInvalida() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, LocalDate.parse("2022-01-01"), RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, DATA_DE_NASCIMENTO_INVALIDA);
	}
	@Test
	public void testRendaInvalida() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, -3000.11,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, RENDA_MENOR_QUE_ZERO);
	}
	@Test
	public void testEnderecoNaoInformado() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA, null);
		assertInclusaoNaoRealizada(vend, ENDERECO_NAO_INFORMADO);
	}
	@Test
	public void testLogradouroNaoInformado() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(null, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, LOGRADOURO_NAO_INFORMADO);
		vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(BRANCO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, LOGRADOURO_NAO_INFORMADO);
	}
	@Test
	public void testLogradouroInvalido() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco("ABC", NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, LOGRADOURO_TEM_MENOS_DE_04_CARACTERES);
	}
	@Test
	public void testNumeroInvalido() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, -1, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, NUMERO_MENOR_QUE_ZERO);
	}
	@Test
	public void testCidadeNaoInformada() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		null, ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, CIDADE_NAO_INFORMADA);
		vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		" ", ESTADO_VALIDO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, CIDADE_NAO_INFORMADA);
	}
	@Test
	public void testEstadoNaoInformado() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, null, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, ESTADO_NAO_INFORMADO);
		vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, BRANCO, PAIS_VALIDO));
		assertInclusaoNaoRealizada(vend, ESTADO_NAO_INFORMADO);
	}
	@Test
	public void testPaisNaoInformado() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, null));
		assertInclusaoNaoRealizada(vend, PAIS_NAO_INFORMADO);
		vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, "   "));
		assertInclusaoNaoRealizada(vend, PAIS_NAO_INFORMADO);
	}
	@Test
	public void testIncluirChaveDuplicada() {		
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		long numero = Glosb.gluarfsh(vend.getCpf());
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		cadastroVend.incluir(vend, CPF_VALIDO);
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		Vendedor vend1 = new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 100.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 100, COMPL_VALIDO + SUF_NEW, CEP_VALIDO + SUF_NEW, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));				
		ResultadoInclusaoVendedor res = mediator.incluir(vend1);
		Assertions.assertNotNull(res);
		Assertions.assertEquals("Vendedor ja existente", res.getMensagemErroValidacao());
		Assertions.assertEquals(NUMERO_CAIXA_BONUS_NAO_GERADA, res.getNumeroCaixaDeBonus());
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsVendedor);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend, vendGravado));		
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());
	}
	@Test
	public void testIncluirSucesso() {		
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));		
		long numero = Glosb.gluarfsh(vend.getCpf());
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		ResultadoInclusaoVendedor res = mediator.incluir(vend);
		Assertions.assertNotNull(res);
		Assertions.assertNull(res.getMensagemErroValidacao());
		Assertions.assertEquals(numero, res.getNumeroCaixaDeBonus());
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsVendedor);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		Vendedor vendInc = (Vendedor)cadastroVend.buscar(CPF_VALIDO);
		Assertions.assertNotNull(vendInc);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend, vendInc));
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());		
	}
	@Test
	public void testAlterarChaveNaoExistente() {		
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		long numero = Glosb.gluarfsh(vend.getCpf());
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		cadastroVend.incluir(vend, CPF_VALIDO);
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		Vendedor vend1 = new Vendedor(OUTRO_CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 100.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 100, COMPL_VALIDO + SUF_NEW, CEP_VALIDO + SUF_NEW, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));				
		String msg = mediator.alterar(vend1);
		Assertions.assertEquals("Vendedor inexistente", msg);		
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsVendedor);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend, vendGravado));		
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());
	}
	@Test
	public void testAlterarDadosInvalidos() {
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		for (int i=0; i< VENDS_INVALIDOS.length; i++) {
			assertAlteracaoNaoRealizada(vendOri, VENDS_INVALIDOS[i], MSGS_VENDS_INVALIDOS[i]);
		}
	}
	@Test
	public void testAlterarSucesso() {		
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		long numero = Glosb.gluarfsh(vend.getCpf());
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		cadastroVend.incluir(vend, CPF_VALIDO);
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		Vendedor vend1 = new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, OUTRA_DATA_NASC_VALIDA, RENDA_VALIDA + 100.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 100, COMPL_VALIDO + SUF_NEW, CEP_VALIDO + SUF_NEW, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));				
		String msg = mediator.alterar(vend1);
		Assertions.assertNull(msg);		
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsVendedor);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		Vendedor vendAlterado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendAlterado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend1, vendAlterado));		
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());
	}
	
	private boolean diretorioVazio(String caminhoDir) {
		File[] files = (new File(caminhoDir)).listFiles();
		return files == null || files.length == 0;  
	}

//	private String callValidar(Vendedor vend) {
//		try {
//			Class<?> clazz = mediator.getClass();
//			Method privateMethod = clazz.getDeclaredMethod("validar", Vendedor.class);
//			privateMethod.setAccessible(true);
//			return (String)privateMethod.invoke(mediator, vend);
//		} catch (Exception e) {
//			e.printStackTrace();	
//			throw new RuntimeException(e);
//		}		
//	}
	private void assertInclusaoNaoRealizada(Vendedor vend, String msgErro) {
		excluirVendedoresCaixasBonusLancamentos();
		ResultadoInclusaoVendedor res = mediator.incluir(vend);
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.getMensagemErroValidacao());
		Assertions.assertEquals(NUMERO_CAIXA_BONUS_NAO_GERADA, res.getNumeroCaixaDeBonus());
		Assertions.assertEquals(msgErro, res.getMensagemErroValidacao());
		Assertions.assertTrue(diretorioVazio(DIR_VENDEDOR));
		Assertions.assertTrue(diretorioVazio(DIR_CAIXA_DE_BONUS));					
	}
	private void assertAlteracaoNaoRealizada(Vendedor vendOri, Vendedor vend, String msgErro) {
		excluirVendedoresCaixasBonusLancamentos();
		cadastroVend.incluir(vendOri, vendOri.getCpf());
		long numero = Glosb.gluarfsh(vendOri.getCpf());
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		String msg = mediator.alterar(vend);
		Assertions.assertNotNull(msg);
		Assertions.assertNotNull(msgErro, msg);
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsVendedor);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(vendOri.getCpf());		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vendOri, vendGravado));		
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());		
	}
}

