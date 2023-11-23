package br.gov.cesarschool.poo.bonusvendas.testes;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.gov.cesarschool.poo.bonusvendas.entidade.CaixaDeBonus;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;

public class TesteEntidades {
	@Test
	public void testCalcularIdade() {
		LocalDate dataNasc = LocalDate.parse("2001-01-01");
		Vendedor vend = new Vendedor("12345678901", "Carlos Calmon", Sexo.MASCULINO, 
		        dataNasc,
		        2500.0,
		        new Endereco("RUA A", 101, "BL B", "51001001", "RECIFE", "PE", "BRASIL"));
		int idadeEsp = (int)ChronoUnit.YEARS.between(dataNasc, LocalDate.now());
		int idade = vend.calcularIdade();
		Assertions.assertEquals(idadeEsp, idade);		
	}
	@Test
	public void testCreditarDebitar() {
		CaixaDeBonus cb = new CaixaDeBonus(12345678920231109L);
		LocalDateTime dtRef = LocalDateTime.parse("2001-01-01T00:00:00");
		try {
			Class<?> c = CaixaDeBonus.class;		
			Field field = c.getDeclaredField("dataHoraAtualizacao");
			field.setAccessible(true);			
			field.set(cb, dtRef);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		cb.creditar(100.0);
		Assertions.assertEquals(100.0, cb.getSaldo());
		cb.creditar(200.0);
		Assertions.assertEquals(300.0, cb.getSaldo());
		cb.debitar(120.0);
		Assertions.assertEquals(180.0, cb.getSaldo());
		Assertions.assertTrue(dtRef.isBefore(cb.getDataHoraAtualizacao()));
	}
}