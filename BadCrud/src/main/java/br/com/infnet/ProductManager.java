package br.com.infnet;
import java.util.ArrayList;
import java.util.List;

class ProductManager {
    // VARIÁVEL PÚBLICA E ESTÁTICA - PÉSSIMA PRÁTICA!
    public static List<Product> productList = new ArrayList<>();

    // VARIÁVEL DE CONTROLE PÚBLICA
    public static int nextId = 1;

    // MÉTODO RUIM PARA CRIAR
    public void createNewProduct(String productName, double productPrice) {
        Product newProduct = new Product(nextId, productName, productPrice);
        productList.add(newProduct);
        nextId++;
        System.out.println("Produto criado. ID: " + newProduct.id);
    }

    // MÉTODO RUIM PARA LER TODOS
    public void listAllProducts() {
        if (productList.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }
        System.out.println("--- Lista de Produtos ---");
        for (Product p : productList) {
            System.out.println("ID: " + p.id + ", Nome: " + p.name + ", Preço: R$" + p.price);
        }
    }

    // MÉTODO RUIM PARA BUSCAR POR ID
    public Product getProductById(int id) {
        for (Product p : productList) {
            if (p.id == id) {
                return p;
            }
        }
        return null; // Retorna null sem tratamento adequado
    }

    // MÉTODO RUIM PARA ATUALIZAR
    public void updateProduct(int id, String newName, double newPrice) {
        Product p = getProductById(id);
        if (p != null) {
            p.name = newName; // Acesso direto ao campo PÚBLICO
            p.price = newPrice;
            System.out.println("Produto ID " + id + " atualizado.");
        } else {
            System.out.println("Produto com ID " + id + " não encontrado para atualização.");
        }
    }

    // MÉTODO RUIM PARA DELETAR
    public void deleteProduct(int id) {
        Product p = getProductById(id);
        if (p != null) {
            productList.remove(p);
            System.out.println("Produto ID " + id + " deletado.");
        } else {
            System.out.println("Produto com ID " + id + " não encontrado para deletar.");
        }
    }
}