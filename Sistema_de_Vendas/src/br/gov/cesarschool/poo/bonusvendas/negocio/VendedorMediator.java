package br.gov.cesarschool.poo.bonusvendas.negocio;

import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;

public class VendedorMediator {
    // A instância única do Singleton
    private static VendedorMediator instance;

    private VendedorDAO vendedorCons;
    private AcumuloResgateMediator acumuloResgateCons;

    // Construtor privado para evitar criação direta
    private VendedorMediator(VendedorDAO vendedorCons, AcumuloResgateMediator acumuloResgateCons) {
        this.vendedorCons = vendedorCons;
        this.acumuloResgateCons = acumuloResgateCons;
    }

    // Método público para obter a instância do Singleton
    public static VendedorMediator getInstance(VendedorDAO vendedor, AcumuloResgateMediator acumuloResgate) {
        // Se a instância ainda não existe, cria-a
        if (instance == null) {
            instance = new VendedorMediator(vendedor, acumuloResgate);
        }
        // Retorna a instância existente
        return instance;
    }
    
    public Vendedor buscar(String cpf) {
        try {
            
            return vendedorCons.buscar(cpf);
        } catch (Exception e) {
            
            return null; // Retorne null em caso de erro
        }
    }
}
