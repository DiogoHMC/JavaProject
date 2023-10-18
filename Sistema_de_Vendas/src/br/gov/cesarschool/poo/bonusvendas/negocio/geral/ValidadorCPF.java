package br.gov.cesarschool.poo.bonusvendas.negocio.geral;

public class ValidadorCPF {
    private ValidadorCPF() {
        // Construtor privado para evitar instância da classe
    }

    public static boolean ehCpfValido(String cpf) {
    	
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        int soma = 0;
        
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        
        int penultimoDigito = 11 - (soma % 11);

        if (penultimoDigito >= 10) {
            penultimoDigito = 0;
        }

        soma = 0;
        
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        
        int ultimoDigito = 11 - (soma % 11);

        if (ultimoDigito >= 10) {
            ultimoDigito = 0;
        }

        return (cpf.charAt(9) - '0' == penultimoDigito && cpf.charAt(10) - '0' == ultimoDigito);
    }
}
