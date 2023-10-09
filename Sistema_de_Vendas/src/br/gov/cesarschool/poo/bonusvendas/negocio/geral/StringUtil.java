package br.gov.cesarschool.poo.bonusvendas.negocio.geral;

public class StringUtil {
    private StringUtil() {
        // Construtor privado para evitar instância da classe
    }

    public static boolean ehNuloOuBranco(String str) {
        return str == null || str.trim().isEmpty();
    }
}
