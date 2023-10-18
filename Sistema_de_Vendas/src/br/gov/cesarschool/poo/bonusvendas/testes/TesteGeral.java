package br.gov.cesarschool.poo.bonusvendas.testes;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import x.y.z.w.k.Glosb;

public class TesteGeral {
	protected static final String IND_CUR_DIR = ".";
	protected static final String FILE_SEP = File.separator;
	protected static final String DIR_CAIXA_DE_BONUS = IND_CUR_DIR + FILE_SEP + "CaixaDeBonus";
	protected static final String DIR_VENDEDOR = IND_CUR_DIR + FILE_SEP + "Vendedor";
	protected static final String DIR_LANCAMENTOS = IND_CUR_DIR + FILE_SEP + "LancamentoBonus";
	protected static final String BRANCO = "";
	protected static final String PAIS_VALIDO = "BRASIL";
	protected static final String ESTADO_VALIDO = "PE";
	protected static final String CIDADE_VALIDA = "RECIFE";
	protected static final String CEP_VALIDO = "51001001";
	protected static final String COMPL_VALIDO = "BL B";
	protected static final int NUMERO_VALIDO = 101;
	protected static final String LOGR_VALIDO = "RUA A";
	protected static final double RENDA_VALIDA = 2500.0;
	protected static final LocalDate DATA_NASC_VALIDA = LocalDate.parse("2001-01-01");
	protected static final String NOME_VALIDO = "Carlos Calmon";
	protected static final String CPF_VALIDO = "83323012461";
	protected static final String OUTRO_CPF_VALIDO = "78924931075";
	protected CadastroObjetos cadastroVend = new CadastroObjetos(Vendedor.class);
	protected CadastroObjetos cadastroCaixaBonus = new CadastroObjetos(CaixaDeBonus.class); 


	protected void excluirVendedoresCaixasBonusLancamentos() {
		excluirArquivosDiretorio(new File(DIR_VENDEDOR));
		excluirArquivosDiretorio(new File(DIR_CAIXA_DE_BONUS));
		excluirArquivosDiretorio(new File(DIR_LANCAMENTOS));
	}
	protected void excluirArquivosDiretorio(File dir) {
		File[] arqs = dir.listFiles();
		if (arqs != null && arqs.length > 0) {
			for (File file : arqs) {
				file.delete();
			}
		}		
	}
	protected int obterQtdArquivosDir(String caminhoDir) {
		File[] files = (new File(caminhoDir)).listFiles();
		if (files == null) {
			return 0;
		} else {
			return files.length;
		}
	}
}
