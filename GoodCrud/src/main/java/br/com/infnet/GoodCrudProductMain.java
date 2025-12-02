package br.com.infnet;

import br.com.infnet.controller.ProductController;
import br.com.infnet.repository.ProductRepository;
import br.com.infnet.service.ProductService;

import java.util.Scanner;

/**
 * Classe principal que inicia a aplicação CRUD.
 * Agora utiliza a estrutura refatorada com Controller, Service e Repository.
 */
public class GoodCrudProductMain {

    public static void main(String[] args) {
        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService(repository);
        ProductController controller = new ProductController(service, new Scanner(System.in));
        
        controller.run();
    }
}
