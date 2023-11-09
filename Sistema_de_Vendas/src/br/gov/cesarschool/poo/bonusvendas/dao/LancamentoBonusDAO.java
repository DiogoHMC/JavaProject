package br.gov.cesarschool.poo.bonusvendas.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.gov.cesarschool.poo.bonusvendas.entidade.LancamentoBonus;

public class LancamentoBonusDAO {
	private CadastroObjetos cadastro = new CadastroObjetos(LancamentoBonus.class); 
	public boolean incluir(LancamentoBonus lancamento) {
		String idUnico = obterIdUnico(lancamento);
		LancamentoBonus lancamentoBusca = buscar(idUnico);  
		if (lancamentoBusca != null) { 
			return false;
		} else {
			cadastro.incluir(lancamento, idUnico);
			return true;
		}		 
	}
	public boolean alterar(LancamentoBonus lancamento) {
		String idUnico = obterIdUnico(lancamento);
		LancamentoBonus lancamentoBusca = buscar(idUnico);
		if (lancamentoBusca == null) {
			return false;
		} else {
			cadastro.alterar(lancamento, idUnico);
			return true;
		}		
	}
	public LancamentoBonus buscar(String codigo) {
		// Esta operação entre () vai ter significado mais à frente! 
		return (LancamentoBonus)cadastro.buscar(codigo);
	}
	public LancamentoBonus[] buscarTodos() {
		Serializable[] rets = cadastro.buscarTodos(LancamentoBonus.class);
		LancamentoBonus[] lancamentos = new LancamentoBonus[rets.length];
		for(int i=0; i<rets.length; i++) {
			// Esta operação entre () vai ter significado mais à frente! 
			lancamentos[i] = (LancamentoBonus)rets[i];
		}
		return lancamentos;
	} 
	private String obterIdUnico(LancamentoBonus lancamento) {
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return lancamento.getNumeroCaixaDeBonus() + 
				lancamento.getDataHoraLancamento().format(customFormatter);
	}	

}
