package br.com.infnet;
import java.util.Scanner;
public class BadProductCrudExample {

    // Scanner global - má prática
    private static Scanner scanner = new Scanner(System.in);
    private static ProductManager manager = new ProductManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Menu CRUD (RUIM) ---");
            System.out.println("1. Criar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Deletar Produto");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            // Sem tratamento de exceção para entrada (Ex: letras)
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (choice) {
                case 1:
                    create();
                    break;
                case 2:
                    manager.listAllProducts();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // LÓGICA DE INTERFACE DE USUÁRIO MISTURADA COM A LÓGICA DE NEGÓCIO
    private static void create() {
        System.out.print("Nome do Produto: ");
        String name = scanner.nextLine();
        System.out.print("Preço do Produto: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        manager.createNewProduct(name, price);
    }

    private static void update() {
        System.out.print("ID do Produto a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Product existing = manager.getProductById(id);
        if (existing == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Novo Nome (" + existing.name + "): ");
        String newName = scanner.nextLine();
        System.out.print("Novo Preço (" + existing.price + "): ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        manager.updateProduct(id, newName, newPrice);
    }

    private static void delete() {
        System.out.print("ID do Produto a deletar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        manager.deleteProduct(id);
    }
}