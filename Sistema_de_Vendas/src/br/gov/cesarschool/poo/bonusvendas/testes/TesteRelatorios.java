package br.gov.cesarschool.poo.bonusvendas.testes;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusCredito;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonusDebito;
import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorCaixaDeBonusSaldoDec;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorLancamentoBonusDHDec;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorVendedorNome;
import br.gov.cesarschool.poo.bonusvendas.negocio.ComparadorVendedorRenda;
import br.gov.cesarschool.poo.bonusvendas.negocio.VendedorMediator;
import br.gov.cesarschool.poo.bonusvendas.util.Comparador;
import br.gov.cesarschool.poo.bonusvendas.util.Ordenadora;
public class TesteRelatorios extends TesteGeral {
	private static final String ACUMULO_RESGATE_MEDIATOR_JAVA = "AcumuloResgateMediator.java";
	private static final double VALOR_MIL = 1000.0;
	private static final String VEND_MEDIATOR_JAVA = "VendedorMediator.java";
	private static final String STR_ORDENADORA = "Ordenadora.ordenar";
	private static final String DIR_MEDIATORS = IND_CUR_DIR + FILE_SEP + "src" + 
			FILE_SEP + "br" + FILE_SEP + "gov" + FILE_SEP + "cesarschool" + 
			FILE_SEP + "poo" + FILE_SEP + "bonusvendas" + FILE_SEP + "negocio" + FILE_SEP;
	private static final Vendedor[] VENDS = new Vendedor[] {
		new Vendedor("1", "EDUARDO", Sexo.MASCULINO, null, 2000.0, null),
		new Vendedor("2", "CARLA", Sexo.FEMININO, null, 3000.0, null),
		new Vendedor("3", "ZORO", Sexo.MASCULINO, null, 1500.0, null),
		new Vendedor("4", "BABI", Sexo.FEMININO, null, 8000.0, null)};
	
	@Test
	public void testComparadoresImplementors() {
		Assertions.assertTrue(ComparadorVendedorNome.getInstance() 
				instanceof Comparador);
		Assertions.assertTrue(ComparadorVendedorRenda.getInstance() 
				instanceof Comparador); 
		Assertions.assertTrue(ComparadorCaixaDeBonusSaldoDec.getInstance() 
				instanceof Comparador); 
		Assertions.assertTrue(ComparadorLancamentoBonusDHDec.getInstance() 
				instanceof Comparator); 
	}
	@Test
	public void testSingletonConstrutorPrivado() {		
		testSingletonConstrutorAux(ComparadorLancamentoBonusDHDec.class); 
		testSingletonConstrutorAux(ComparadorVendedorNome.class);
		testSingletonConstrutorAux(ComparadorVendedorRenda.class); 
		testSingletonConstrutorAux(ComparadorCaixaDeBonusSaldoDec.class); 
	}
	@Test
	public void testOrdenacaoGeral() {
		EntTesteOrdenacao[] lista = new EntTesteOrdenacao[4];
		lista[0] = new EntTesteOrdenacao(2, "BRUNO");
		lista[1] = new EntTesteOrdenacao(1, "AMERICO");
		lista[2] = new EntTesteOrdenacao(4, "ZORO");
		lista[3] = new EntTesteOrdenacao(3, "SERGEI");
		Comparador comp = new Comparador() {			
			@Override
			public int comparar(Object o1, Object o2) {
				return ((EntTesteOrdenacao)o1).at2.compareTo(
						((EntTesteOrdenacao)o2).at2);
			}
		};
		Ordenadora.ordenar(lista, comp);
		Assertions.assertEquals("AMERICO", lista[0].at2);
		Assertions.assertEquals("BRUNO", lista[1].at2);
		Assertions.assertEquals("SERGEI", lista[2].at2);
		Assertions.assertEquals("ZORO", lista[3].at2);		
	}
	@Test
	public void testOrdenacaoVendedor() {		
		Vendedor[] vends = new Vendedor[VENDS.length];
		System.arraycopy(VENDS, 0, vends, 0, VENDS.length);
		Ordenadora.ordenar(vends, ComparadorVendedorNome.getInstance());
		assertVendedorNome(vends);
		vends = new Vendedor[VENDS.length];
		System.arraycopy(VENDS, 0, vends, 0, VENDS.length);		
		Ordenadora.ordenar(vends, ComparadorVendedorRenda.getInstance());
		assertVendedorRenda(vends);		
	}
	@Test
	public void testOrdenacaoCaixaDeBonus() {
		CaixaDeBonus[] caixas = new CaixaDeBonus[4];
		caixas[0] = new CaixaDeBonus(1);
		caixas[1] = new CaixaDeBonus(2);
		caixas[2] = new CaixaDeBonus(3);
		caixas[3] = new CaixaDeBonus(4);
		caixas[0].creditar(10000.0);
		caixas[1].creditar(1000.0);
		caixas[2].creditar(3000.0);
		caixas[3].creditar(2000.0);
		Ordenadora.ordenar(caixas, ComparadorCaixaDeBonusSaldoDec.getInstance());
		Assertions.assertEquals(1, caixas[0].getNumero());
		Assertions.assertEquals(3, caixas[1].getNumero());
		Assertions.assertEquals(4, caixas[2].getNumero());
		Assertions.assertEquals(2, caixas[3].getNumero());
	}	
	@Test
	public void testOrdenacaoLancamentoBonus() {
		List lancs = new ArrayList();
		LocalDateTime dh1 = LocalDateTime.parse("2001-01-01T00:00:00");
		LocalDateTime dh2 = LocalDateTime.parse("2001-01-01T00:02:01");
		LocalDateTime dh3 = LocalDateTime.parse("2012-10-11T22:33:11");
		LocalDateTime dh4 = LocalDateTime.parse("2023-06-03T00:00:01");
		lancs.add(new LancamentoBonusCredito(1, VALOR_MIL, dh4));
		lancs.add(new LancamentoBonusDebito(2, VALOR_MIL, dh1, TipoResgate.CASH));
		lancs.add(new LancamentoBonusCredito(3, VALOR_MIL, dh3));
		lancs.add(new LancamentoBonusDebito(4, VALOR_MIL, dh2, TipoResgate.PRODUTO));
		Collections.sort(lancs, ComparadorLancamentoBonusDHDec.getInstance());
		Assertions.assertEquals(1, ((LancamentoBonus)lancs.get(0)).getNumeroCaixaDeBonus());
		Assertions.assertEquals(3, ((LancamentoBonus)lancs.get(1)).getNumeroCaixaDeBonus());
		Assertions.assertEquals(4, ((LancamentoBonus)lancs.get(2)).getNumeroCaixaDeBonus());
		Assertions.assertEquals(2, ((LancamentoBonus)lancs.get(3)).getNumeroCaixaDeBonus());
	}
	@Test
	public void testRelatoriosVendedorNome() {
		excluirVendedoresCaixasBonusLancamentos();
		for (Vendedor vendedor : VENDS) {
			cadastroVend.incluir(vendedor, vendedor.getIdUnico());
		}		
		Vendedor[] vendsOrdNome = VendedorMediator.getInstancia().gerarListagemClienteOrdenadaPorNome();
		Assertions.assertNotNull(vendsOrdNome);
		Assertions.assertEquals(4, vendsOrdNome.length);				
		assertVendedorNome(vendsOrdNome);
		testUsoComparadoresAux(VEND_MEDIATOR_JAVA, "ComparadorVendedorNome.getInstance()", STR_ORDENADORA);
	}
	@Test
	public void testRelatoriosVendedorRenda() {
		excluirVendedoresCaixasBonusLancamentos();
		for (Vendedor vendedor : VENDS) {
			cadastroVend.incluir(vendedor, vendedor.getIdUnico());
		}		
		Vendedor[] vendsOrdRenda = VendedorMediator.getInstancia().gerarListagemClienteOrdenadaPorRenda();
		Assertions.assertNotNull(vendsOrdRenda);
		Assertions.assertEquals(4, vendsOrdRenda.length);						
		assertVendedorRenda(vendsOrdRenda);		
		testUsoComparadoresAux(VEND_MEDIATOR_JAVA, "ComparadorVendedorRenda.getInstance()", STR_ORDENADORA);
	}
	
	@Test
	public void testRelatorioCaixaDeBonus() {
		excluirVendedoresCaixasBonusLancamentos(); 
		CaixaDeBonus[] caixas = new CaixaDeBonus[6];
		caixas[0] = new CaixaDeBonus(1);
		caixas[1] = new CaixaDeBonus(2);
		caixas[2] = new CaixaDeBonus(3);
		caixas[3] = new CaixaDeBonus(4);
		caixas[4] = new CaixaDeBonus(5);
		caixas[5] = new CaixaDeBonus(6);
		caixas[0].creditar(8000.0);
		caixas[1].creditar(2000.0);
		caixas[2].creditar(3000.0);
		caixas[3].creditar(10000.0);
		caixas[4].creditar(1000.0);				
		caixas[5].creditar(4000.0);
		for (CaixaDeBonus caixaDeBonus : caixas) {
			cadastroCaixaBonus.incluir(caixaDeBonus, caixaDeBonus.getIdUnico());
		}	
		CaixaDeBonus[] caixasRet = AcumuloResgateMediator.getInstancia().listaCaixaDeBonusPorSaldoMaior(3000.0);
		Assertions.assertNotNull(caixasRet);
		Assertions.assertEquals(4, caixasRet.length);		
		Assertions.assertEquals(4, caixasRet[0].getNumero());
		Assertions.assertEquals(1, caixasRet[1].getNumero());
		Assertions.assertEquals(6, caixasRet[2].getNumero());
		Assertions.assertEquals(3, caixasRet[3].getNumero());
		testUsoComparadoresAux(ACUMULO_RESGATE_MEDIATOR_JAVA, "ComparadorCaixaDeBonusSaldoDec.getInstance()", STR_ORDENADORA);
	}
	@Test
	public void testRelatorioLancamento() {
		excluirVendedoresCaixasBonusLancamentos(); 
		List lancs = new ArrayList();
		LocalDateTime dh1 = LocalDateTime.parse("2001-01-01T23:59:59");
		LocalDateTime dh2 = LocalDateTime.parse("2020-12-01T11:02:01");
		LocalDateTime dh3 = LocalDateTime.parse("2020-12-02T22:33:11");
		LocalDateTime dh4 = LocalDateTime.parse("2021-02-04T08:21:44");
		LocalDateTime dh5 = LocalDateTime.parse("2022-06-07T19:10:58");
		LocalDateTime dh6 = LocalDateTime.parse("2023-06-03T06:34:01");
		LocalDate d1 = LocalDate.parse("2020-12-01");
		LocalDate d2 = LocalDate.parse("2022-06-07");
		lancs.add(new LancamentoBonusDebito(5, VALOR_MIL, dh5, TipoResgate.SERVICO));
		lancs.add(new LancamentoBonusCredito(4, VALOR_MIL, dh4));
		lancs.add(new LancamentoBonusDebito(1, VALOR_MIL, dh1, TipoResgate.CASH));
		lancs.add(new LancamentoBonusCredito(3, VALOR_MIL, dh3));
		lancs.add(new LancamentoBonusCredito(6, VALOR_MIL, dh6));
		lancs.add(new LancamentoBonusDebito(2, VALOR_MIL, dh2, TipoResgate.PRODUTO));		
		for (Object object : lancs) {			
			LancamentoBonus lanc = (LancamentoBonus)object;
			cadastroLanc.incluir(lanc, lanc.getIdUnico());
		}
		LancamentoBonus[] lancsRet = AcumuloResgateMediator.getInstancia().listaLancamentosPorFaixaData(d1, d2);
		Assertions.assertNotNull(lancsRet);
		Assertions.assertEquals(4, lancsRet.length);
		Assertions.assertEquals(5, lancsRet[0].getNumeroCaixaDeBonus());				
		Assertions.assertEquals(4, lancsRet[1].getNumeroCaixaDeBonus());
		Assertions.assertEquals(3, lancsRet[2].getNumeroCaixaDeBonus());
		Assertions.assertEquals(2, lancsRet[3].getNumeroCaixaDeBonus());
		testUsoComparadoresAux(ACUMULO_RESGATE_MEDIATOR_JAVA, "ComparadorLancamentoBonusDHDec.getInstance()", "Collections.sort");
	}
	private void testSingletonConstrutorAux(Class clazz) {		
		Constructor[] consts = clazz.getDeclaredConstructors();
		Assertions.assertEquals(1, consts.length);
		Assertions.assertEquals(2, consts[0].getModifiers());
	}	
	private void testUsoComparadoresAux(String nomeArqJava, String nomeComparador, String nomeMetodoOrdenador) {				
		File arqDao = new File(DIR_MEDIATORS + nomeArqJava); 
		Assertions.assertTrue(arqDao.exists());
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(arqDao, "r");
			byte[] contByte = new byte[(int)raf.length()];
			raf.read(contByte);
			String conteudo = new String(contByte);
			Assertions.assertTrue(conteudo.indexOf(nomeComparador) >= 0);
			Assertions.assertTrue(conteudo.indexOf(nomeMetodoOrdenador) >= 0);
		} catch (Exception e) {
			Assertions.fail(e);
		} finally {
			try {
				raf.close();
			} catch (Exception e) {}
		}
	}
	private void assertVendedorRenda(Vendedor[] vends) {
		Assertions.assertEquals("3", vends[0].getCpf());
		Assertions.assertEquals("1", vends[1].getCpf());
		Assertions.assertEquals("2", vends[2].getCpf());
		Assertions.assertEquals("4", vends[3].getCpf());
	}
	private void assertVendedorNome(Vendedor[] vends) {
		Assertions.assertEquals("4", vends[0].getCpf());
		Assertions.assertEquals("2", vends[1].getCpf());
		Assertions.assertEquals("1", vends[2].getCpf());
		Assertions.assertEquals("3", vends[3].getCpf());
	}
	private static class EntTesteOrdenacao {
		int at1;
		String at2;
		public EntTesteOrdenacao(int at1, String at2) {
			this.at1 = at1;
			this.at2 = at2;
		}
	}
}
