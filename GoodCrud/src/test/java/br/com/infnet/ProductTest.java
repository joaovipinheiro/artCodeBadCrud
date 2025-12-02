package br.com.infnet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("Criação válida mantém valores e aplica trim no nome")
    void shouldCreateProduct() {
        Product product = new Product(1, "  Notebook  ", 2500.00);

        assertEquals(1, product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals(2500.00, product.getPrice());
    }

    @Test
    @DisplayName("Preço zero é permitido, desde que não seja negativo")
    void shouldAllowZeroPrice() {
        Product product = new Product(2, "Produto Grátis", 0.0);

        assertEquals(0.0, product.getPrice());
    }

    @Test
    @DisplayName("Lança exceção para preço negativo")
    void shouldRejectNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product(3, "Produto", -100.0));
    }

    @Test
    @DisplayName("Lança exceção para nomes inválidos")
    void shouldRejectInvalidNames() {
        assertThrows(IllegalArgumentException.class, () -> new Product(1, null, 10));
        assertThrows(IllegalArgumentException.class, () -> new Product(1, "   ", 10));
    }

    @Test
    @DisplayName("Atualização cria nova instância imutável")
    void updateShouldCreateNewInstance() {
        Product original = new Product(1, "Original", 10);
        Product updated = original.update("Novo", 20);

        assertNotEquals(original, updated);
        assertEquals(1, updated.getId());
        assertEquals("Novo", updated.getName());
        assertEquals(20, updated.getPrice());
    }
}

