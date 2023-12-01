package br.gov.cesarschool.poo.bonusvendas.testes;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.dao.CaixaDeBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.dao.DAOGenerico;
import br.gov.cesarschool.poo.bonusvendas.dao.LancamentoBonusDAO;
import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusCredito;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusDebito;
import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Registro;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;

public class TesteDAO extends TesteGeral {
	private static final String NOME_CAMPO_DAO = "dao";
	private static final String SUF_NEW = "_new";
	private static final String DIR_DAOS = IND_CUR_DIR + FILE_SEP + "src" + 
		FILE_SEP + "br" + FILE_SEP + "gov" + FILE_SEP + "cesarschool" + 
		FILE_SEP + "poo" + FILE_SEP + "bonusvendas" + FILE_SEP + "dao" + FILE_SEP;
	
	@Test
	public void testIdVendedor() {
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		Assertions.assertTrue(vend instanceof Registro);
		Assertions.assertEquals(CPF_VALIDO,vend.getIdUnico());
	}
	@Test
	public void testIdCaixaDeBonus() {
		CaixaDeBonus cb = new CaixaDeBonus(NUMERO_VALIDO);
		Assertions.assertTrue(cb instanceof Registro);
		Assertions.assertEquals("" + NUMERO_VALIDO,cb.getIdUnico());
	}
	@Test
	public void testIdLancamento() {
		LocalDateTime dh1 = LocalDateTime.parse("2023-10-11T13:22:33.2");
		LancamentoBonus lcb = new LancamentoBonusCredito(1, 100.0, dh1);
		Assertions.assertTrue(lcb instanceof Registro);
		Assertions.assertEquals("120231011132233", lcb.getIdUnico());
	}
	private void testDaoAux(Class<?> clazz, String nomeArqJava) {				
		try {
			Field campoDao = clazz.getDeclaredField(NOME_CAMPO_DAO);
			Assertions.assertEquals(DAOGenerico.class, campoDao.getType());
		} catch (Exception e) {
			Assertions.fail(e);
		}
		File arqDao = new File(DIR_DAOS + nomeArqJava); 
		Assertions.assertTrue(arqDao.exists());
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(arqDao, "r");
			byte[] contByte = new byte[(int)raf.length()];
			raf.read(contByte);
			String conteudo = new String(contByte);
			Assertions.assertTrue(conteudo.indexOf("dao.incluir") >= 0);
			Assertions.assertTrue(conteudo.indexOf("dao.alterar") >= 0);
			Assertions.assertTrue(conteudo.indexOf("dao.buscar") >= 0);
			Assertions.assertTrue(conteudo.indexOf("dao.buscarTodos") >= 0);
		} catch (Exception e) {
			Assertions.fail(e);
		} finally {
			try {
				raf.close();
			} catch (Exception e) {}
		}
	}	
	@Test
	public void testDaoVendedor() {		
		excluirVendedoresCaixasBonusLancamentos();
		testDaoAux(VendedorDAO.class, "VendedorDAO.java"); 
		Vendedor vend1 = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		Vendedor vend2 = new Vendedor(OUTRO_CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 200.00,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 2, COMPL_VALIDO + SUF_NEW, CEP_VALIDO + SUF_NEW, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));			
		VendedorDAO dao = new VendedorDAO();
		cadastroVend.incluir(vend1, vend1.getIdUnico());
		cadastroVend.incluir(vend2, vend2.getIdUnico());
		Vendedor[] vends = dao.buscarTodos();
		Assertions.assertNotNull(vends);
		Assertions.assertEquals(2, vends.length);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend1, vends[1]));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend2, vends[0]));		
	}
	@Test
	public void testDaoCaixaDeBonus() {		
		excluirVendedoresCaixasBonusLancamentos();
		testDaoAux(CaixaDeBonusDAO.class, "CaixaDeBonusDAO.java");
		CaixaDeBonus cb1 = new CaixaDeBonus(1);
		CaixaDeBonus cb2 = new CaixaDeBonus(2);
		CaixaDeBonusDAO dao = new CaixaDeBonusDAO();
		cadastroCaixaBonus.incluir(cb1, cb1.getIdUnico());
		cadastroCaixaBonus.incluir(cb2, cb2.getIdUnico());
		CaixaDeBonus[] cbs = dao.buscarTodos();
		Assertions.assertNotNull(cbs);
		Assertions.assertEquals(2, cbs.length);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(cb1, cbs[0]));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(cb2, cbs[1]));		
	}	
	@Test
	public void testDaoLancamentoBonus() {		
		excluirVendedoresCaixasBonusLancamentos();
		testDaoAux(LancamentoBonusDAO.class, "LancamentoBonusDAO.java");
		LocalDateTime dh1 = LocalDateTime.parse("2011-01-01T14:24:11.1");
		LocalDateTime dh2 = LocalDateTime.parse("2019-03-22T11:13:33.1");
		LancamentoBonus lc1 = new LancamentoBonusCredito(1, 100.00, dh1);
		LancamentoBonus lc2 = new LancamentoBonusDebito(2, 100.00, dh2, TipoResgate.CASH);
		LancamentoBonusDAO dao = new LancamentoBonusDAO();
		cadastroLanc.incluir(lc1, lc1.getIdUnico());
		cadastroLanc.incluir(lc2, lc2.getIdUnico());
		LancamentoBonus[] lancs = dao.buscarTodos();
		Assertions.assertNotNull(lancs);
		Assertions.assertEquals(2, lancs.length);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(lc1, lancs[0]));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(lc2, lancs[1]));		
	}		
	@Test
	public void testInclusaoOkGenerica() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		DAOGenerico daoVend = new DAOGenerico(Vendedor.class);
		boolean res = daoVend.incluir(vend);
		Assertions.assertTrue(res);
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);		
		Assertions.assertEquals(1, qtdArqsVendedor);		
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend, vendGravado));								
	}
	@Test
	public void testInclusaoNaoOkGenerica() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vend, CPF_VALIDO);
		Vendedor vend1 = new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 100.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 100, COMPL_VALIDO + SUF_NEW, CEP_VALIDO + SUF_NEW, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));			
		DAOGenerico daoVend = new DAOGenerico(Vendedor.class);
		boolean res = daoVend.incluir(vend1);
		Assertions.assertFalse(res);
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);		
		Assertions.assertEquals(1, qtdArqsVendedor);		
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend, vendGravado));						
	}
	@Test
	public void testAlteracaoOkGenerica() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vend, CPF_VALIDO);
		Vendedor vend1 = new Vendedor(CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 100.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 100, COMPL_VALIDO + SUF_NEW, CEP_VALIDO + SUF_NEW, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));			
		DAOGenerico daoVend = new DAOGenerico(Vendedor.class);
		boolean res = daoVend.alterar(vend1);
		Assertions.assertTrue(res);
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);		
		Assertions.assertEquals(1, qtdArqsVendedor);		
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend1, vendGravado));				
	}
	@Test
	public void testAlteracaoNaoOkGenerica() {
		excluirVendedoresCaixasBonusLancamentos();
		Vendedor vend = new Vendedor(CPF_VALIDO, NOME_VALIDO, 
				Sexo.MASCULINO, DATA_NASC_VALIDA, RENDA_VALIDA,
		        new Endereco(LOGR_VALIDO, NUMERO_VALIDO, COMPL_VALIDO, CEP_VALIDO, 
		        		CIDADE_VALIDA, ESTADO_VALIDO, PAIS_VALIDO));
		cadastroVend.incluir(vend, CPF_VALIDO);
		Vendedor vend1 = new Vendedor(OUTRO_CPF_VALIDO, NOME_VALIDO + SUF_NEW, 
				Sexo.FEMININO, DATA_NASC_VALIDA, RENDA_VALIDA + 100.0,
		        new Endereco(LOGR_VALIDO + SUF_NEW, NUMERO_VALIDO + 100, COMPL_VALIDO + SUF_NEW, CEP_VALIDO + SUF_NEW, 
		        		CIDADE_VALIDA + SUF_NEW, ESTADO_VALIDO + SUF_NEW, PAIS_VALIDO + SUF_NEW));			
		DAOGenerico daoVend = new DAOGenerico(Vendedor.class);
		boolean res = daoVend.alterar(vend1);
		Assertions.assertFalse(res);
		int qtdArqsVendedor = obterQtdArquivosDir(DIR_VENDEDOR);		
		Assertions.assertEquals(1, qtdArqsVendedor);		
		Vendedor vendGravado = (Vendedor)cadastroVend.buscar(CPF_VALIDO);		
		Assertions.assertNotNull(vendGravado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(vend, vendGravado));						
	}
	@Test
	public void testBuscaPorIdOkGenerica() {
		excluirVendedoresCaixasBonusLancamentos();
		CaixaDeBonus cb = new CaixaDeBonus(1);
		cadastroCaixaBonus.incluir(cb, cb.getIdUnico());
		DAOGenerico dao = new DAOGenerico(CaixaDeBonus.class);
		Registro reg = dao.buscar("1");
		Assertions.assertNotNull(reg);
		Assertions.assertTrue(reg instanceof CaixaDeBonus);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(reg, cb));		
	}
	@Test
	public void testBuscaPorIdNaoOkGenerica() {
		excluirVendedoresCaixasBonusLancamentos();
		CaixaDeBonus cb = new CaixaDeBonus(1);
		cadastroCaixaBonus.incluir(cb, cb.getIdUnico());
		DAOGenerico dao = new DAOGenerico(CaixaDeBonus.class);
		Registro reg = dao.buscar("2");
		Assertions.assertNull(reg);
	}
	@Test
	public void testBuscaGeralGenerica() {
		excluirVendedoresCaixasBonusLancamentos();
		LocalDateTime dh1 = LocalDateTime.parse("2001-01-01T00:00:00.0");
		LocalDateTime dh2 = LocalDateTime.parse("2022-01-01T11:00:00.0");
		LocalDateTime dh3 = LocalDateTime.parse("2023-01-01T12:00:00.0");
		LancamentoBonusCredito lbc1 = new LancamentoBonusCredito(1, 100.0, dh1);
		LancamentoBonusDebito lbc2 = new LancamentoBonusDebito(2, 200.0, dh2, TipoResgate.SERVICO);
		LancamentoBonusCredito lbc3 = new LancamentoBonusCredito(3, 300.0, dh3);		
		cadastroLanc.incluir(lbc1, lbc1.getIdUnico());
		cadastroLanc.incluir(lbc2, lbc2.getIdUnico());
		cadastroLanc.incluir(lbc3, lbc3.getIdUnico());
		DAOGenerico daoLanc = new DAOGenerico(LancamentoBonus.class);
		Registro[] regs = daoLanc.buscarTodos();
		List<Registro> listRegs = new ArrayList<Registro>();
		for (Registro registro : regs) {
			listRegs.add(registro);
		}
		Collections.sort(listRegs,new Comparator<Registro>() {
			@Override
			public int compare(Registro o1, Registro o2) {
				LancamentoBonus l1 = (LancamentoBonus)o1;
				LancamentoBonus l2 = (LancamentoBonus)o2;
				return l1.getDataHoraLancamento().compareTo(l2.getDataHoraLancamento());
			}
			
		});
		Assertions.assertEquals(3, regs.length);
		Assertions.assertTrue(listRegs.get(0) instanceof LancamentoBonusCredito); 
		Assertions.assertTrue(listRegs.get(1) instanceof LancamentoBonusDebito);
		Assertions.assertTrue(listRegs.get(2) instanceof LancamentoBonusCredito);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(listRegs.get(0), lbc1));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(listRegs.get(1), lbc2));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(listRegs.get(2), lbc3));
	}	
}
