package br.com.infnet.repository;

import br.com.infnet.model.Product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório responsável pela persistência dos produtos.
 * Mantém o estado isolado e expõe operações de acesso a dados.
 */
public class ProductRepository {

    private final Map<Integer, Product> products = new LinkedHashMap<>();
    private int nextId = 1;

    /**
     * Salva um novo produto no repositório.
     */
    public Product save(Product product) {
        Product newProduct = new Product(nextId, product.getName(), product.getPrice());
        products.put(newProduct.getId(), newProduct);
        nextId++;
        return newProduct;
    }

    /**
     * Salva um novo produto usando apenas nome e preço (ID será gerado automaticamente).
     */
    public Product save(String name, double price) {
        Product newProduct = new Product(nextId, name, price);
        products.put(newProduct.getId(), newProduct);
        nextId++;
        return newProduct;
    }

    /**
     * Retorna todos os produtos cadastrados.
     */
    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    /**
     * Busca um produto pelo ID.
     */
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Atualiza um produto existente.
     */
    public Product update(Product product) {
        Product existing = products.get(product.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Produto com ID " + product.getId() + " não encontrado.");
        }
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Remove um produto pelo ID.
     */
    public boolean deleteById(int id) {
        return products.remove(id) != null;
    }

    /**
     * Verifica se existe um produto com o ID informado.
     */
    public boolean existsById(int id) {
        return products.containsKey(id);
    }

    /**
     * Método auxiliar visível para testes para resetar o estado.
     */
    void clear() {
        products.clear();
        nextId = 1;
    }
}

