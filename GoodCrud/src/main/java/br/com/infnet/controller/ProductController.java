package br.com.infnet.controller;

import br.com.infnet.model.Product;
import br.com.infnet.service.ProductService;

import java.util.List;
import java.util.Scanner;

/**
 * Controller responsável pela interface de console e coordenação das operações.
 * Recebe input do usuário e delega as operações para o serviço.
 */
public class ProductController {

    private final ProductService service;
    private final Scanner scanner;

    public ProductController(ProductService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    /**
     * Inicia o loop principal do menu.
     */
    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = safeReadInt("Escolha uma opção: ");
            switch (choice) {
                case 1 -> create();
                case 2 -> listProducts();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> {
                    System.out.println("Saindo...");
                    running = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n--- Menu CRUD ---");
        System.out.println("1. Criar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("3. Atualizar Produto");
        System.out.println("4. Deletar Produto");
        System.out.println("5. Sair");
    }

    /**
     * Cria um novo produto.
     */
    public void create() {
        String name = readLine("Nome do Produto: ");
        double price = safeReadDouble("Preço do Produto: ");
        try {
            Product product = service.createProduct(name, price);
            System.out.println("Produto criado. ID: " + product.getId());
        } catch (IllegalArgumentException ex) {
            System.out.println("Erro ao criar produto: " + ex.getMessage());
        }
    }

    /**
     * Lista todos os produtos.
     */
    public void listProducts() {
        List<Product> products = service.listAllProducts();
        if (products.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }
        System.out.println("--- Lista de Produtos ---");
        products.forEach(product ->
                System.out.printf("ID: %d, Nome: %s, Preço: R$%.2f%n",
                        product.getId(), product.getName(), product.getPrice()));
    }

    /**
     * Atualiza um produto existente.
     */
    public void update() {
        int id = safeReadInt("ID do Produto a atualizar: ");
        String newName = readLine("Novo Nome: ");
        double newPrice = safeReadDouble("Novo Preço: ");
        try {
            service.updateProduct(id, newName, newPrice);
            System.out.println("Produto ID " + id + " atualizado.");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Remove um produto.
     */
    public void delete() {
        int id = safeReadInt("ID do Produto a deletar: ");
        if (service.deleteProduct(id)) {
            System.out.println("Produto ID " + id + " deletado.");
        } else {
            System.out.println("Produto com ID " + id + " não encontrado para deletar.");
        }
    }

    private String readLine(String message) {
        System.out.print(message);
        String line = scanner.nextLine();
        while (line != null && line.trim().isEmpty()) {
            System.out.print("Informe um valor válido. " + message);
            line = scanner.nextLine();
        }
        return line;
    }

    private int safeReadInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value <= 0) {
                    throw new NumberFormatException();
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Digite um número inteiro positivo.");
            }
        }
    }

    private double safeReadDouble(String message) {
        while (true) {
            System.out.print(message);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0) {
                    throw new NumberFormatException();
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }
}

