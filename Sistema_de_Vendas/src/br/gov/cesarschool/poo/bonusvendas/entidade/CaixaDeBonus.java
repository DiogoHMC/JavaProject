package br.gov.cesarschool.poo.bonusvendas.entidade;
import java.time.LocalDateTime;

public class CaixaDeBonus {
	
	/* attribute  */
	private long numeros;
	private double saldo;
	private LocalDateTime dataHoraAtualizacao;
	
	/* constructor */ 
	public CaixaDeBonus(long numeros, double saldo) {
			
		this.numeros = numeros;
		this.saldo = 0;
		this.dataHoraAtualizacao = LocalDateTime.now();
	}

	public long getNumeros() {
		
        return numeros;
    }

    public double getSaldo() {
    	
        return saldo;
    }
    
    public LocalDateTime getDataHoraAtualizacao() {
    	
        return dataHoraAtualizacao;
    }   
	    
	void creditar(double valor) {
		
			saldo = saldo + valor;
		}


    public void debitar(double valor) {
    	
        if (valor <= saldo) {
        	
            saldo -= valor;
            dataHoraAtualizacao = LocalDateTime.now();
        } 
        
        else {
        	
            System.out.println("Saldo insuficiente para debitar.");
        }
    }
    
}