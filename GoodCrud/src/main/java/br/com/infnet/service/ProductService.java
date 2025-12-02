package br.com.infnet.service;

import br.com.infnet.model.Product;
import br.com.infnet.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Serviço que contém a lógica de negócio para produtos.
 * Coordena as operações entre o controller e o repositório.
 */
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria um novo produto.
     */
    public Product createProduct(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        return repository.save(name, price);
    }

    /**
     * Lista todos os produtos.
     */
    public List<Product> listAllProducts() {
        return repository.findAll();
    }

    /**
     * Busca um produto pelo ID.
     */
    public Optional<Product> getProductById(int id) {
        return repository.findById(id);
    }

    /**
     * Atualiza um produto existente.
     */
    public Product updateProduct(int id, String newName, double newPrice) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if (newPrice < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        
        Product existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + id + " não encontrado."));
        
        Product updated = existing.update(newName, newPrice);
        return repository.update(updated);
    }

    /**
     * Remove um produto.
     */
    public boolean deleteProduct(int id) {
        return repository.deleteById(id);
    }
}

