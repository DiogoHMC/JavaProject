package br.gov.cesarschool.poo.bonusvendas.testes;

import java.io.Serializable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusCredito;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusDebito;
import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;
import x.y.z.w.k.Glosb;

public class TesteAcumuloResgateMediator extends TesteGeral {
	
	private static final long NUMERO_CX_BONUS = 12345678920230101L;
	private static final String CAIXA_DE_BONUS_INEXISTENTE = "Caixa de bonus inexistente";
	private static final String VALOR_MENOR_OU_IGUAL_A_ZERO = "Valor menor ou igual a zero";
	protected CadastroObjetos cadastroLanc = new CadastroObjetos(LancamentoBonus.class);
	private AcumuloResgateMediator mediator = AcumuloResgateMediator.getInstancia();
	@Test
	public void testNaoGerarNumeroCaixaBonus() {
		excluirVendedoresCaixasBonusLancamentos();
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		long numeroRet = mediator.gerarCaixaDeBonus(vend);
		Assertions.assertEquals(0,  numeroRet);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaBonusGravada = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertNotNull(caixaBonusGravada);
		Assertions.assertEquals(caixaBonusGravada.getNumero(), cb.getNumero());
		Assertions.assertEquals(caixaBonusGravada.getSaldo(), cb.getSaldo());		

	}
	@Test
	public void testGerarNumeroCaixaBonus() {
		excluirVendedoresCaixasBonusLancamentos();
		long numero = Glosb.gluarfsh(CPF_VALIDO);
		long numeroGerado = Glosb.gluarfsh(OUTRO_CPF_VALIDO);
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		Vendedor vend = new Vendedor(OUTRO_CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		long numeroRet = mediator.gerarCaixaDeBonus(vend);		
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
		// A data hora atualização deve ser a data hora atual, atribuída ao atributo dataHoraAtualizacao
		// da CaixaDeBonus no construtor desta.  Não é um atributo controlado pelo usuário nem pelo mediator. 
		Assertions.assertNotNull(caixaBonusNova.getDataHoraAtualizacao());
	}
	
	@Test
	public void testAcumuloValorNegativoZero() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = NUMERO_CX_BONUS;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 1000.0;		
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		String msg = mediator.acumularBonus(numero, -100.0);
		Assertions.assertNotNull(msg);
		Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, msg);
		msg = mediator.acumularBonus(numero, 0.0);
		Assertions.assertNotNull(msg);
		Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, msg);
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(0, qtdArqsLancamento);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaLida = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(caixaLida, cb));
	}
	@Test
	public void testAcumuloCaixaBonusNaoExistente() {
		excluirVendedoresCaixasBonusLancamentos();		
		//long numero = obterNumeroCaixaBonus(CPF_VALIDO);
		long numero = NUMERO_CX_BONUS;
		String msg = mediator.acumularBonus(numero, 100.0);
		Assertions.assertNotNull(msg);
		Assertions.assertEquals(CAIXA_DE_BONUS_INEXISTENTE, msg);
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(0, qtdArqsLancamento);
		Assertions.assertEquals(0, qtdArqsCaixaDeBonus);
	}
	@Test
	public void testResgateValorNegativoZero() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = NUMERO_CX_BONUS;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 1000.0;
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		String msg = mediator.resgatar(numero, -100.0, TipoResgate.CASH);
		Assertions.assertNotNull(msg);
		Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, msg);
		msg = mediator.acumularBonus(numero, 0.0);
		Assertions.assertNotNull(msg);
		Assertions.assertEquals(VALOR_MENOR_OU_IGUAL_A_ZERO, msg);
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(0, qtdArqsLancamento);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaLida = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(caixaLida, cb));
	}
	@Test
	public void testResgateSaldoInsuficiente() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = NUMERO_CX_BONUS;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 1000.0;
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		String msg = mediator.resgatar(numero, 1004.0, TipoResgate.CASH);
		Assertions.assertNotNull(msg);
		Assertions.assertEquals("Saldo insuficiente", msg);
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(0, qtdArqsLancamento);
		Assertions.assertEquals(1, qtdArqsCaixaDeBonus);
		CaixaDeBonus caixaLida = (CaixaDeBonus)cadastroCaixaBonus.buscar(numero + BRANCO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(caixaLida, cb));
	}	
	@Test
	public void testResgateCaixaBonusNaoExistente() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = NUMERO_CX_BONUS;		
		String msg = mediator.resgatar(numero, 100.0, TipoResgate.CASH);
		Assertions.assertNotNull(msg);
		Assertions.assertEquals(CAIXA_DE_BONUS_INEXISTENTE, msg);
		int qtdArqsLancamento = obterQtdArquivosDir(DIR_LANCAMENTOS);
		int qtdArqsCaixaDeBonus = obterQtdArquivosDir(DIR_CAIXA_DE_BONUS);
		Assertions.assertEquals(0, qtdArqsLancamento);
		Assertions.assertEquals(0, qtdArqsCaixaDeBonus);
	}
	@Test
	public void testAcumuloValorNumeroOk() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = NUMERO_CX_BONUS;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 1000.0;
		double valor = 2000.0;
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		String msg = mediator.acumularBonus(numero, valor);
		Assertions.assertNull(msg);		
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
	public void testResgateValorNumeroOk() {
		excluirVendedoresCaixasBonusLancamentos();		
		long numero = NUMERO_CX_BONUS;		
		CaixaDeBonus cb = new CaixaDeBonus(numero);
		double saldoAtu = 3000.0;
		double valor = 2000.0;
		cb.creditar(saldoAtu);
		cadastroCaixaBonus.incluir(cb, numero + BRANCO);
		String msg = mediator.resgatar(numero, valor, TipoResgate.CASH);
		Assertions.assertNull(msg);		
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
}
