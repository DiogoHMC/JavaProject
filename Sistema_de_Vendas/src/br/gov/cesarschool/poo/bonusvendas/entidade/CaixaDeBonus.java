package br.gov.cesarschool.poo.bonusvendas.entidade;
import java.time.LocalDateTime;
import java.io.Serializable;


public class CaixaDeBonus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* attribute  */
	private long numero;
	private double saldo;
	private LocalDateTime dataHoraAtualizacao;
	
	/* constructor */ 
	public CaixaDeBonus(long numeros) {
			
		this.numero = numeros;
		this.saldo = 0.0;
		this.dataHoraAtualizacao = LocalDateTime.now();
	}

	public long getNumero() {
		
        return numero;
    }

    public double getSaldo() {
    	
        return saldo;
    }
    
    public LocalDateTime getDataHoraAtualizacao() {
    	
        return dataHoraAtualizacao;
    }   
	    
	public void creditar(double valor) {
		
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