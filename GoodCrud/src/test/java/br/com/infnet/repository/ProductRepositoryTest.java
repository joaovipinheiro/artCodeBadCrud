package br.com.infnet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.infnet.model.Product;

class ProductRepositoryTest {

    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProductRepository();
    }

    @Test
    @DisplayName("Salvar produto retorna produto com ID incremental")
    void shouldSaveProductWithIncrementalIds() {
        Product saved1 = repository.save("Mouse", 50.0);
        Product saved2 = repository.save("Teclado", 150.0);

        assertEquals(1, saved1.getId());
        assertEquals(2, saved2.getId());
    }

    @Test
    @DisplayName("Listar todos retorna snapshot imutável")
    void findAllShouldReturnSnapshot() {
        repository.save("Monitor", 800.0);
        
        List<Product> products = repository.findAll();

        assertEquals(1, products.size());
        assertThrows(UnsupportedOperationException.class,
                () -> products.add(new Product(99, "Outro", 1)));
    }

    @Test
    @DisplayName("Busca por ID retorna Optional preenchido quando existe")
    void shouldFindById() {
        Product created = repository.save("Notebook", 2500.0);

        Product found = repository.findById(created.getId()).orElseThrow();
        assertEquals(created, found);
    }

    @Test
    @DisplayName("Busca por ID inexistente retorna Optional vazio")
    void shouldReturnEmptyWhenIdNotFound() {
        assertTrue(repository.findById(999).isEmpty());
    }

    @Test
    @DisplayName("Atualização substitui produto mantendo o mesmo ID")
    void shouldUpdateExistingProduct() {
        Product created = repository.save("Original", 100.0);
        
        Product updated = new Product(created.getId(), "Atualizado", 120.0);
        Product result = repository.update(updated);

        assertEquals(created.getId(), result.getId());
        assertEquals("Atualizado", result.getName());
        assertEquals(120.0, result.getPrice());
    }

    @Test
    @DisplayName("Atualizar produto inexistente lança IllegalArgumentException")
    void shouldNotUpdateMissingProduct() {
        Product product = new Product(999, "Novo", 10);
        assertThrows(IllegalArgumentException.class, () -> repository.update(product));
    }

    @Test
    @DisplayName("Deleção remove produto e retorna verdadeiro")
    void shouldDeleteProduct() {
        Product created = repository.save("Produto", 20);
        
        assertTrue(repository.deleteById(created.getId()));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Deletar produto inexistente retorna falso")
    void shouldReturnFalseWhenDeletingMissingProduct() {
        assertFalse(repository.deleteById(999));
    }

    @Test
    @DisplayName("ExistsById retorna true quando produto existe")
    void shouldReturnTrueWhenProductExists() {
        Product created = repository.save("Produto", 20);
        
        assertTrue(repository.existsById(created.getId()));
        assertFalse(repository.existsById(999));
    }

    @Test
    @DisplayName("Clear reseta o estado do repositório")
    void shouldClearRepository() throws Exception {
        repository.save("Produto", 20);
        
        // Usar reflexão para acessar o método clear
        Method clearMethod = ProductRepository.class.getDeclaredMethod("clear");
        clearMethod.setAccessible(true);
        clearMethod.invoke(repository);
        
        assertTrue(repository.findAll().isEmpty());
        
        // Verificar que o próximo ID foi resetado
        Product saved = repository.save("Novo", 30);
        assertEquals(1, saved.getId());
    }
}

