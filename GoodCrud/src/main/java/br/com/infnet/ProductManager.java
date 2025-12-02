package br.com.infnet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Responsável pelo ciclo de vida dos produtos.
 * Mantém o estado isolado e expõe operações previsíveis para o console ou serviços futuros.
 */
class ProductManager {

    private final Map<Integer, Product> products = new LinkedHashMap<>();
    private int nextId = 1;

    public Product createNewProduct(String productName, double productPrice) {
        Product newProduct = new Product(nextId, productName, productPrice);
        products.put(newProduct.getId(), newProduct);
        nextId++;
        return newProduct;
    }

    public List<Product> listAllProducts() {
        return List.copyOf(products.values());
    }

    public Optional<Product> getProductById(int id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product updateProduct(int id, String newName, double newPrice) {
        Product existing = products.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado.");
        }
        Product updated = existing.update(newName, newPrice);
        products.put(id, updated);
        return updated;
    }

    public boolean deleteProduct(int id) {
        return products.remove(id) != null;
    }

    /**
     * Método auxiliar visível para testes para resetar o estado.
     */
    void clear() {
        products.clear();
        nextId = 1;
    }
}