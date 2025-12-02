package br.com.infnet;

import java.util.List;
import java.util.Scanner;

/**
 * Interface simples de console para demonstrar o CRUD.
 * Continua deliberadamente minimalista, porém agora com validações básicas.
 */
public class BadProductCrudExample {

    private final Scanner scanner;
    private final ProductManager manager;

    public BadProductCrudExample() {
        this(new Scanner(System.in), new ProductManager());
    }

    BadProductCrudExample(Scanner scanner, ProductManager manager) {
        this.scanner = scanner;
        this.manager = manager;
    }

    public static void main(String[] args) {
        new BadProductCrudExample().run();
    }

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

    private void create() {
        String name = readLine("Nome do Produto: ");
        double price = safeReadDouble("Preço do Produto: ");
        try {
            Product product = manager.createNewProduct(name, price);
            System.out.println("Produto criado. ID: " + product.getId());
        } catch (IllegalArgumentException ex) {
            System.out.println("Erro ao criar produto: " + ex.getMessage());
        }
    }

    private void listProducts() {
        List<Product> products = manager.listAllProducts();
        if (products.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }
        System.out.println("--- Lista de Produtos ---");
        products.forEach(product ->
                System.out.printf("ID: %d, Nome: %s, Preço: R$%.2f%n",
                        product.getId(), product.getName(), product.getPrice()));
    }

    private void update() {
        int id = safeReadInt("ID do Produto a atualizar: ");
        String newName = readLine("Novo Nome: ");
        double newPrice = safeReadDouble("Novo Preço: ");
        try {
            manager.updateProduct(id, newName, newPrice);
            System.out.println("Produto ID " + id + " atualizado.");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void delete() {
        int id = safeReadInt("ID do Produto a deletar: ");
        if (manager.deleteProduct(id)) {
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