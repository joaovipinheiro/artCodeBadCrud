package br.com.infnet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductManagerTest {

    private ProductManager manager;

    @BeforeEach
    void setUp() {
        manager = new ProductManager();
    }

    @Test
    @DisplayName("Criação retorna produtos com IDs incrementais")
    void shouldCreateProductsWithIncrementalIds() {
        Product first = manager.createNewProduct("Mouse", 50.0);
        Product second = manager.createNewProduct("Teclado", 150.0);

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    @DisplayName("Listagem retorna snapshot imutável")
    void listShouldReturnSnapshot() {
        manager.createNewProduct("Monitor", 800.0);
        List<Product> products = manager.listAllProducts();

        assertEquals(1, products.size());
        assertThrows(UnsupportedOperationException.class,
                () -> products.add(new Product(99, "Outro", 1)));
    }

    @Test
    @DisplayName("Busca por ID retorna Optional preenchido quando existe")
    void shouldFindById() {
        Product created = manager.createNewProduct("Notebook", 2500.0);

        Product found = manager.getProductById(created.getId()).orElseThrow();
        assertEquals(created, found);
    }

    @Test
    @DisplayName("Busca por ID inexistente retorna Optional vazio")
    void shouldReturnEmptyWhenIdNotFound() {
        assertTrue(manager.getProductById(999).isEmpty());
    }

    @Test
    @DisplayName("Atualização substitui produto mantendo o mesmo ID")
    void shouldUpdateExistingProduct() {
        Product created = manager.createNewProduct("Original", 100.0);
        Product updated = manager.updateProduct(created.getId(), "Atualizado", 120.0);

        assertEquals(created.getId(), updated.getId());
        assertEquals("Atualizado", updated.getName());
        assertEquals(120.0, updated.getPrice());
    }

    @Test
    @DisplayName("Atualizar produto inexistente lança IllegalArgumentException")
    void shouldNotUpdateMissingProduct() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.updateProduct(999, "Novo", 10));
    }

    @Test
    @DisplayName("Deleção remove produto e retorna verdadeiro")
    void shouldDeleteProduct() {
        Product created = manager.createNewProduct("Produto", 20);
        assertTrue(manager.deleteProduct(created.getId()));
        assertTrue(manager.listAllProducts().isEmpty());
    }

    @Test
    @DisplayName("Deletar produto inexistente retorna falso")
    void shouldReturnFalseWhenDeletingMissingProduct() {
        assertFalse(manager.deleteProduct(999));
    }
}

