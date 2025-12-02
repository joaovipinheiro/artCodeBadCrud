package br.com.infnet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.infnet.model.Product;
import br.com.infnet.repository.ProductRepository;

class ProductServiceTest {

    private ProductService service;
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProductRepository();
        service = new ProductService(repository);
    }

    @Test
    @DisplayName("Cria produto com nome e preço válidos")
    void shouldCreateProduct() {
        Product product = service.createProduct("Notebook", 2500.0);

        assertEquals("Notebook", product.getName());
        assertEquals(2500.0, product.getPrice());
        assertTrue(product.getId() > 0);
    }

    @Test
    @DisplayName("Lança exceção ao criar produto com nome vazio")
    void shouldThrowExceptionWhenCreatingWithEmptyName() {
        assertThrows(IllegalArgumentException.class, 
                () -> service.createProduct("", 100.0));
        assertThrows(IllegalArgumentException.class, 
                () -> service.createProduct("   ", 100.0));
        assertThrows(IllegalArgumentException.class, 
                () -> service.createProduct(null, 100.0));
    }

    @Test
    @DisplayName("Lança exceção ao criar produto com preço negativo")
    void shouldThrowExceptionWhenCreatingWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, 
                () -> service.createProduct("Produto", -100.0));
    }

    @Test
    @DisplayName("Lista todos os produtos")
    void shouldListAllProducts() {
        service.createProduct("Produto 1", 10.0);
        service.createProduct("Produto 2", 20.0);

        List<Product> products = service.listAllProducts();

        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("Busca produto por ID existente")
    void shouldGetProductById() {
        Product created = service.createProduct("Notebook", 2500.0);

        Optional<Product> found = service.getProductById(created.getId());

        assertTrue(found.isPresent());
        assertEquals(created, found.get());
    }

    @Test
    @DisplayName("Retorna Optional vazio para ID inexistente")
    void shouldReturnEmptyForNonExistentId() {
        Optional<Product> found = service.getProductById(999);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Atualiza produto existente")
    void shouldUpdateProduct() {
        Product created = service.createProduct("Original", 100.0);
        Product updated = service.updateProduct(created.getId(), "Atualizado", 120.0);

        assertEquals(created.getId(), updated.getId());
        assertEquals("Atualizado", updated.getName());
        assertEquals(120.0, updated.getPrice());
    }

    @Test
    @DisplayName("Lança exceção ao atualizar produto inexistente")
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, 
                () -> service.updateProduct(999, "Novo", 10.0));
    }

    @Test
    @DisplayName("Lança exceção ao atualizar com nome vazio")
    void shouldThrowExceptionWhenUpdatingWithEmptyName() {
        Product created = service.createProduct("Original", 100.0);
        
        assertThrows(IllegalArgumentException.class, 
                () -> service.updateProduct(created.getId(), "", 120.0));
        assertThrows(IllegalArgumentException.class, 
                () -> service.updateProduct(created.getId(), "   ", 120.0));
    }

    @Test
    @DisplayName("Lança exceção ao atualizar com preço negativo")
    void shouldThrowExceptionWhenUpdatingWithNegativePrice() {
        Product created = service.createProduct("Original", 100.0);
        
        assertThrows(IllegalArgumentException.class, 
                () -> service.updateProduct(created.getId(), "Atualizado", -120.0));
    }

    @Test
    @DisplayName("Remove produto existente")
    void shouldDeleteProduct() {
        Product created = service.createProduct("Produto", 20.0);
        
        boolean deleted = service.deleteProduct(created.getId());
        
        assertTrue(deleted);
        assertTrue(service.getProductById(created.getId()).isEmpty());
    }

    @Test
    @DisplayName("Retorna falso ao deletar produto inexistente")
    void shouldReturnFalseWhenDeletingNonExistentProduct() {
        assertFalse(service.deleteProduct(999));
    }
}

