package br.gov.cesarschool.poo.bonusvendas.testes;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class SimuladorProbabilidade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] jogo6 = {5, 22, 56, 40, 20, 13};
		int[] jogoMult = {6, 11, 44, 33, 7, 2, 58, 23, 21, 39}; // 210x	
		int fatorChance = 210;
		int sena6 = 0;
		int senaMult = 0;
		int quina6 = 0;
		int quinaMult = 0;
		int quadra6 = 0;
		int quadraMult = 0;		
		for (int i=0; i<200000000;i++) {
			int[] numerosSorteados = new int[6];
			sortear(numerosSorteados);			
			for (int k=0; k<fatorChance;k++) {
				if (i % 28 == 0) {
					int acertosMult = conferir(numerosSorteados, jogoMult);
					if (acertosMult == 4) {
						quadraMult++;
					} else if (acertosMult == 5) {
						quinaMult++;
					} else if (acertosMult == 6) {
						senaMult++;
					}
				}
			}
			int acertos6 = conferir(numerosSorteados, jogo6);
			if (acertos6 == 4) {
				quadra6++;
			} else if (acertos6 == 5) {
				quina6++;
			} else if (acertos6 == 6) {
				sena6++;
			}			
			if (i % 10000000 == 0) {
				System.out.println(LocalDateTime.now() + " iteracao " + i);
			}
		} 
		System.out.println("sena6: " + sena6);
		System.out.println("sena" + jogoMult.length + ": " + senaMult);
		System.out.println("quina6: " + quina6);
		System.out.println("quina " + jogoMult.length + ": " +  quinaMult);
		System.out.println("quadra6: " + quadra6);
		System.out.println("quadra " + jogoMult.length + ": " +  quadraMult);		
	}
	private static void sortear(int[] resultado) {
		for (int i=0; i<6; i++) {
			int randomNum = 0;
			do {
				randomNum = ThreadLocalRandom.current().nextInt(1, 61);				
			} while(existe(resultado, randomNum));
			resultado[i] = randomNum;
		}
	}
	private static boolean existe(int[] numerosSorteados, int numero) {
		for (int num : numerosSorteados) {
			if (num == numero) {
				return true;
			}
		}
		return false; 
	}
	private static int conferir(int[] numerosSorteados, int[] numerosJogados) {
		int acertos = 0;
		for (int numeroJogado : numerosJogados) {
			for (int k=0; k < numerosSorteados.length; k++) {
				if (numeroJogado == numerosSorteados[k]) {
					acertos++;
					break;
				}
			}
		}
		return acertos; 
	}
}
