package br.gov.cesarschool.poo.bonusvendas.negocio.geral;

public class StringUtil {
    
	private StringUtil() {
		
	}
	
	public static boolean ehNuloOuBranco(String str) {
		
		String minhaString = str.replaceAll("\\s+", "");
		
		return minhaString == null || minhaString.equals("");
		
	}
}
