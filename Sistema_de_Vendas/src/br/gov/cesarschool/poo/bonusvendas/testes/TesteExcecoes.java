package br.gov.cesarschool.poo.bonusvendas.testes;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.gov.cesarschool.poo.bonusvendas.daov2.DAOGenerico;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusCredito;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusDebito;
import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ErroValidacao;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoJaExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoObjetoNaoExistente;
import br.gov.cesarschool.poo.bonusvendas.excecoes.ExcecaoValidacao;
import br.gov.cesarschool.poo.bonusvendas.negocio.ResultadoInclusaoVendedor;
import br.gov.cesarschool.poo.bonusvendas.negociov2.VendedorMediator;
import br.gov.cesarschool.poo.bonusvendas.negociov2.AcumuloResgateMediator;
import x.y.z.w.k.Glosb;

public class TesteExcecoes extends TesteGeral {
	private static final String SUF_NEW = "_NEW";
	private static final String CAIXA_NAO_EXISTENTE = "Caixa nao existente";
	private static final int NUMERO_CB_1 = 1;
	private static final double SALDO_MIL = 1000.00;
	private static final String OK = "OK";
	private static final String[] MSGS_ESPERADAS_01 = {
			"CPF nao informado",
			"Nome completo nao informado",
			"Sexo nao informado",
			"Data de nascimento nao informada",
			"Renda menor que zero",
			"Endereco nao informado"
	};
	private static final String[] MSGS_ESPERADAS_02 = {
			"CPF invalido",
			"Data de nascimento invalida",
			"Logradouro nao informado",
			"Numero menor que zero",
			"Cidade nao informada",
			"Estado nao informado",
			"Pais nao informado"
	};
	private static final String[] MSGS_ESPERADAS_03 = {
			"Logradouro tem menos de 04 caracteres"
	};

	@Test
	public void testEstruturaExcecaoValidacao() {
		ExcecaoValidacao ex = new ExcecaoValidacao((List)null);
		Assertions.assertNotNull(ex.getErrosValidacao());
		ex = new ExcecaoValidacao(OK);
		Assertions.assertNotNull(ex.getErrosValidacao());
		Assertions.assertEquals(OK, ex.getMessage());
		List lista = new ArrayList();
		lista.add(new ErroValidacao(1, "Erro 1"));
		lista.add(new ErroValidacao(2, "Erro 2"));
		ex = new ExcecaoValidacao(lista);
		Assertions.assertNotNull(ex.getErrosValidacao());
		Assertions.assertEquals(2,ex.getErrosValidacao().size());
	}
	@Test
	public void testEstruturaExcecaoObjetoJaExistente() {
		ExcecaoObjetoJaExistente eoe = new ExcecaoObjetoJaExistente();
		Assertions.assertEquals("Objeto ja existente", eoe.getMessage());
		eoe = new ExcecaoObjetoJaExistente("boo");
		Assertions.assertEquals("boo", eoe.getMessage());
	}
	@Test
	public void testEstruturaExcecaoObjetoNaoExistente() {
		ExcecaoObjetoNaoExistente ene = new ExcecaoObjetoNaoExistente();
		Assertions.assertEquals("Objeto nao existente", ene.getMessage());
		ene = new ExcecaoObjetoNaoExistente("foo");
		Assertions.assertEquals("foo", ene.getMessage());		
	}
	@Test
	public void testDAOGenericoIncluir() {
		excluirVendedoresCaixasBonusLancamentos();
		DAOGenerico dao = new DAOGenerico(CaixaDeBonus.class, "CB");
		CaixaDeBonus cb = new CaixaDeBonus(NUMERO_CB_1); 
		try {
			dao.incluir(cb);
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.fail(e);
		}
		try {
			dao.incluir(cb);
			Assertions.fail("ExcecaoObjetoJaExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoJaExistente e) {			
			Assertions.assertEquals("CB ja existente",e.getMessage());
		} 
	}
	@Test
	public void testDAOGenericoAlterarBuscar() {
		excluirVendedoresCaixasBonusLancamentos();
		DAOGenerico dao = new DAOGenerico(CaixaDeBonus.class, "CB");
		CaixaDeBonus cb = new CaixaDeBonus(NUMERO_CB_1);
		cadastroCaixaBonus.incluir(cb, NUMERO_CB_1 + BRANCO);
		CaixaDeBonus cb1 = new CaixaDeBonus(NUMERO_CB_1);
		cb1.creditar(SALDO_MIL);
		try {
			dao.alterar(cb1);			
		} catch (ExcecaoObjetoNaoExistente e) {			
			Assertions.fail(e);
		}
		try {
			CaixaDeBonus cbBusc = (CaixaDeBonus)dao.buscar(BRANCO + NUMERO_CB_1);
			Assertions.assertNotNull(cbBusc);
			Assertions.assertEquals(SALDO_MIL, cbBusc.getSaldo());
		} catch (ExcecaoObjetoNaoExistente e) {			
			Assertions.fail(e);
		}
		try {
			dao.buscar("2");
			Assertions.fail("ExcecaoObjetoNaoExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoNaoExistente e) {			
			Assertions.assertEquals("CB nao existente",e.getMessage());
		} 				
	}
	
	@Test
	public void testGerarCaixaDeBonus() {
		excluirVendedoresCaixasBonusLancamentos();
		long numeroRet = 0;
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		long numeroGerado = Glosb.gluarfsh(OUTRO_CPF_VALIDO);
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		Vendedor vend = new Vendedor(OUTRO_CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		try {
			numeroRet = AcumuloResgateMediator.getInstancia().gerarCaixaDeBonus(vend);
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.fail(e);
		}
		Assertions.assertEquals(numeroGerado,  numeroRet);
		CaixaDeBonus cbRef = new CaixaDeBonus(numeroRet);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		Assertions.assertEquals(2, qtdArqsCaixaDeBonus);
		Assertions.assertEquals(0, qtdArqsLancamento);
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), cb.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), cb.getSaldo());		
		CaixaDeBonus caixaBonusNova = (CaixaDeBonus)cadastroCaixaBonus.buscar(numeroGerado + BRANCO);
		Assertions.assertNotNull(caixaBonusNova);
		Assertions.assertEquals(caixaBonusNova.getNumero(), cbRef.getNumero());
		Assertions.assertEquals(caixaBonusNova.getSaldo(), cbRef.getSaldo());	
		Assertions.assertNotNull(caixaBonusNova.getDataHoraAtualizacao());
		testeNaoGerarCaixaDeBonus();
	}
	private void testeNaoGerarCaixaDeBonus() {		
		excluirVendedoresCaixasBonusLancamentos();
		long numeroRet = 0;
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		try {
			numeroRet = AcumuloResgateMediator.getInstancia().gerarCaixaDeBonus(vend);
			Assertions.fail("ExcecaoObjetoJaExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.assertEquals("Caixa ja existente", e.getMessage());
		}
		Assertions.assertEquals(0,  numeroRet);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), cb.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), cb.getSaldo());	
	}
	@Test
	public void testAcumuloBonus() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = 1;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 1000.0;
		double valor = 2000.0;
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		try {
			AcumuloResgateMediator.getInstancia().acumularBonus(numero, valor);
		} catch (Exception e) {
			Assertions.fail(e);
		} 
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsLancamento);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaLida = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertEquals(caixaLida.getNumero(), numero); 
		Assertions.assertEquals(caixaLida.getSaldo(), 3000.0);
		Serializable[] lancamentos = cadastroLanc.buscarTodos();
		Assertions.assertTrue(lancamentos[0] instanceof LancamentoBonusCredito);
		LancamentoBonusCredito lbc = (LancamentoBonusCredito)lancamentos[0];
		Assertions.assertEquals(numero, lbc.getNumeroCaixaDeBonus()); 
		Assertions.assertEquals(valor, lbc.getValor());		
	}
	@Test
	public void testAcumuloBonusInsucesso() {
		excluirVendedoresCaixasBonusLancamentos();		
		AcumuloResgateMediator mediator = AcumuloResgateMediator.getInstancia();
		long numero = 1;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 1000.0;		
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		try {
			mediator.acumularBonus(numero, -100.0);
			Assertions.fail("1 - ExcecaoValidacao deveria ter sido lançada!");
		} catch (ExcecaoValidacao e) {
			Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, e.getMessage());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		try {
			mediator.acumularBonus(numero, 0.0);
			Assertions.fail("2 - ExcecaoValidacao deveria ter sido lançada!");
		} catch (ExcecaoValidacao e) {
			Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, e.getMessage());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		try {
			mediator.acumularBonus(2, 100.0);
			Assertions.fail("3- ExcecaoObjetoNaoExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals(CAIXA_NAO_EXISTENTE, e.getMessage());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(0, qtdArqsLancamento);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaLida = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(caixaLida, cb));		
	} 	
	@Test
	public void testResgateSucesso() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = 1;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 3000.0;
		double valor = 2000.0;
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		try {
			AcumuloResgateMediator.getInstancia().resgatar(numero, valor, TipoResgate.CASH);
		} catch (Exception e) {
			Assertions.fail(e);
		}
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsLancamento);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaLida = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertEquals(caixaLida.getNumero(), numero); 
		Assertions.assertEquals(caixaLida.getSaldo(), 1000.0);
		Serializable[] lancamentos = cadastroLanc.buscarTodos();
		Assertions.assertTrue(lancamentos[0] instanceof LancamentoBonusDebito);
		LancamentoBonusDebito lbd = (LancamentoBonusDebito)lancamentos[0];
		Assertions.assertEquals(numero, lbd.getNumeroCaixaDeBonus()); 
		Assertions.assertEquals(valor, lbd.getValor());		
		Assertions.assertEquals(TipoResgate.CASH, lbd.getTipoResgate());
	}
	@Test
	public void testResgateInsucesso() {		
		excluirVendedoresCaixasBonusLancamentos();
		AcumuloResgateMediator mediator = AcumuloResgateMediator.getInstancia();
		long numero = 1;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 1000.0;
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		
		try {
			mediator.resgatar(numero, -100.0, TipoResgate.CASH);
			Assertions.fail("4 - ExcecaoValidacao deveria ter sido lançada!");
		} catch (ExcecaoValidacao e) {
			Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, e.getMessage());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		try {
			mediator.resgatar(numero, 0.0, TipoResgate.SERVICO);
			Assertions.fail("5 - ExcecaoValidacao deveria ter sido lançada!");
		} catch (ExcecaoValidacao e) {
			Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, e.getMessage());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		try {
			mediator.resgatar(2, 100.0, TipoResgate.PRODUTO);
			Assertions.fail("6- ExcecaoObjetoNaoExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals(CAIXA_NAO_EXISTENTE, e.getMessage());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		try {
			mediator.resgatar(numero, 1004.0, TipoResgate.CASH);
			Assertions.fail("7- ExcecaoValidacao deveria ter sido lançada!");
		} catch (ExcecaoValidacao e) {
			Assertions.assertEquals("Saldo insuficiente", e.getMessage());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(0, qtdArqsLancamento);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaLida = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(caixaLida, cb));
	}
	@Test
	public void testAlteracaoVendedorInvalido() {		
		Vendedor vend1 = new Vendedor(null, " ", null, null, -10.0, null);
		Vendedor vend2 = new Vendedor("12345678901", "Carlos", Sexo.MASCULINO, LocalDate.parse("2022-01-01"), 10.0, 
				new Endereco("", -1, null, null, "     ", null, " "));
		Vendedor vend3 = new Vendedor(CPF_VALIDO, "Maura", Sexo.FEMININO, LocalDate.parse("1998-01-01"), 1000.0, 
				new Endereco("ABC", 12, "BL B", "51020101", "Recife","PE", "BR"));
		testAlteracaoVendedorInvalidoAux(vend1, MSGS_ESPERADAS_01);
		testAlteracaoVendedorInvalidoAux(vend2, MSGS_ESPERADAS_02);
		testAlteracaoVendedorInvalidoAux(vend3, MSGS_ESPERADAS_03);
	}
	@Test
	public void testInclusaoVendedorInvalido() {		
		Vendedor vend1 = new Vendedor(null, " ", null, null, -10.0, null);
		Vendedor vend2 = new Vendedor("12345678901", "Carlos", Sexo.MASCULINO, LocalDate.parse("2022-01-01"), 10.0, 
				new Endereco("", -1, null, null, "     ", null, " "));
		Vendedor vend3 = new Vendedor(CPF_VALIDO, "Maura", Sexo.FEMININO, LocalDate.parse("1998-01-01"), 1000.0, 
				new Endereco("ABC", 12, "BL B", "51020101", "Recife","PE", "BR"));
		testInclusaoVendedorInvalidoAux(vend1, MSGS_ESPERADAS_01);
		testInclusaoVendedorInvalidoAux(vend2, MSGS_ESPERADAS_02);
		testInclusaoVendedorInvalidoAux(vend3, MSGS_ESPERADAS_03);
	}
	@Test
	public void testInclusaoVendedorJaExistente() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vendOri, CPF_VALIDO);
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);				
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		long numeroCaixaBonus = 0;
		Vendedor vendInc = new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 1000.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 2, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));		
		try {
			numeroCaixaBonus = VendedorMediator.getInstancia().incluir(vendInc);
			Assertions.fail("10- ExcecaoObjetoJaExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.assertEquals("Vendedor ja existente", e.getMessage());
			verificarIgualdadeSistemaArquivosVendedor(vendOri, numero, caixaBonusOri);
			Assertions.assertEquals(0, numeroCaixaBonus);
		} catch (Exception e) {
			Assertions.fail("11- ExcecaoObjetoJaExistente deveria ter sido lançada!");
		}
	}
	@Test
	public void testInclusaoVendedorSucesso() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		long numeroCaixaBonus = 0;
		try {			
			long numero = Glosb.gluarfsh(CPF_VALIDO);
			CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
			numeroCaixaBonus = VendedorMediator.getInstancia().incluir(vendOri);
			Assertions.assertEquals(numero, numeroCaixaBonus);
			int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
			int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
			Assertions.assertEquals(1, qtdArqsVendedor);
			Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
			Vendedor vendInc = (Vendedor)cadastroVend.buscar(CPF_VALIDO);
			Assertions.assertNotNull(vendInc);
			Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vendOri, vendInc));
			CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
			Assertions.assertNotNull(caixaBonusGravada);
			Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
			Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());		
			
		} catch (Exception e) {
			Assertions.fail("14- Excecao nao deveria ter sido lançada!");
		}
	}
	@Test
	public void testAlteracaoVendedorSucesso() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vendOri, CPF_VALIDO);
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		Vendedor vendNew = new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 200.00,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 5, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));		
		try {			
			VendedorMediator.getInstancia().alterar(vendNew);			
			int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
			int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
			Assertions.assertEquals(1, qtdArqsVendedor);
			Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
			Vendedor vendAlt = (Vendedor)cadastroVend.buscar(CPF_VALIDO);
			Assertions.assertNotNull(vendAlt);
			Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vendNew, vendAlt));
			CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
			Assertions.assertNotNull(caixaBonusGravada);
			Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
			Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());					
		} catch (Exception e) {
			Assertions.fail("15- Excecao nao deveria ter sido lançada!");
		}
	}

	@Test
	public void testAlteracaoVendedorNaoExistente() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vendOri, CPF_VALIDO);
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);				
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);		
		Vendedor vendAlt = new Vendedor(OUTRO_CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 1000.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 2, COMPL_VALIDO + SUF_NEW, CEP_VALIDO, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));		
		try {
			VendedorMediator.getInstancia().alterar(vendAlt);
			Assertions.fail("12- ExcecaoObjetoNaoExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals("Vendedor nao existente", e.getMessage());
			verificarIgualdadeSistemaArquivosVendedor(vendOri, numero, caixaBonusOri);			
		} catch (Exception e) {
			Assertions.fail("13- ExcecaoObjetoNaoExistente deveria ter sido lançada!");
		}
	}
	@Test
	public void testBuscaVendedorNaoExistente() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vendOri, CPF_VALIDO);
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		try {
			VendedorMediator.getInstancia().buscar(OUTRO_CPF_VALIDO);
			Assertions.fail("16- ExcecaoObjetoNaoExistente deveria ter sido lançada!");
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals("Vendedor nao existente", e.getMessage());
		}
	}
	public void testBuscaVendedorExistente() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vendOri, CPF_VALIDO);
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);
		try {
			Vendedor vend = VendedorMediator.getInstancia().buscar(OUTRO_CPF_VALIDO);
			Assertions.assertNotNull(vend);
			Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend, vendOri));
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.fail("17- ExcecaoObjetoNaoExistente não deveria ter sido lançada!");
		}
	}
	private void testInclusaoVendedorInvalidoAux(Vendedor vend, String[] msgsEsperadas) {
		excluirVendedoresCaixasBonusLancamentos();
		List erros = null;
		long numeroCaixaBonus = 0;
		try {
			numeroCaixaBonus = VendedorMediator.getInstancia().incluir(vend);
			Assertions.fail("8- ExcecaoValidacao deveria ter sido lançada!");
		} catch (ExcecaoValidacao e) {
			erros = e.getErrosValidacao();
			Assertions.assertNotNull(erros);
			Assertions.assertEquals(msgsEsperadas.length, erros.size());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		Assertions.assertEquals(0, numeroCaixaBonus);
		Map<String, ErroValidacao> mapErros = new HashMap<String, ErroValidacao>();
		for (Object object : erros) {
			ErroValidacao erro = (ErroValidacao)object;
			mapErros.put(erro.getMensagem(), erro);
		}
		for (String msg : msgsEsperadas) {
			if (!mapErros.containsKey(msg)) {
				Assertions.fail("Mensagem " + msg + " deveria estar na lista de erros de validação");
			}
		}
		Assertions.assertTrue(diretorioVazio(DIR_VENDEDOR));
		Assertions.assertTrue(diretorioVazio(DIR_CAIXA_DE_BONUS));					
	}	
	private void testAlteracaoVendedorInvalidoAux(Vendedor vend, String[] msgsEsperadas) {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vendOri = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vendOri, CPF_VALIDO);
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus caixaBonusOri = new CaixaDeBonus(numero);		
		cadastroCaixaBonus.incluir(caixaBonusOri, numero + BRANCO);		
		List erros = null;		
		try {
			VendedorMediator.getInstancia().alterar(vend);
			Assertions.fail("9- ExcecaoValidacao deveria ter sido lançada!");
		} catch (ExcecaoValidacao e) {
			erros = e.getErrosValidacao();
			Assertions.assertNotNull(erros);
			Assertions.assertEquals(msgsEsperadas.length, erros.size());
		} catch (Exception e) {
			Assertions.fail(e);
		}		
		Map<String, ErroValidacao> mapErros = new HashMap<String, ErroValidacao>();
		for (Object object : erros) {
			ErroValidacao erro = (ErroValidacao)object;
			mapErros.put(erro.getMensagem(), erro);
		}
		for (String msg : msgsEsperadas) {
			if (!mapErros.containsKey(msg)) {
				Assertions.fail("Mensagem " + msg + " deveria estar na lista de erros de validação");
			}
		}
		verificarIgualdadeSistemaArquivosVendedor(vendOri, numero, caixaBonusOri);				
	}
	private void verificarIgualdadeSistemaArquivosVendedor(Vendedor vendOri, long numero, CaixaDeBonus caixaBonusOri) {
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsVendedor);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vendOri, vendGravado));		
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), caixaBonusOri.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), caixaBonusOri.getSaldo());
	}	
	private boolean diretorioVazio(String caminhoDir) {
		File[] files = (new File(caminhoDir)).listFiles();
		return files == null || files.length == 0;  
	}
}