package br.com.infnet;

import junit.framework.TestCase;

public class ProductTest extends TestCase {

    public void testProductCreation() {
        Product product = new Product(1, "Notebook", 2500.00);
        
        assertEquals(1, product.id);
        assertEquals("Notebook", product.name);
        assertEquals(2500.00, product.price, 0.01);
    }

    public void testProductCreationWithZeroPrice() {
        Product product = new Product(2, "Produto Grátis", 0.0);
        
        assertEquals(2, product.id);
        assertEquals("Produto Grátis", product.name);
        assertEquals(0.0, product.price, 0.01);
    }

    public void testProductCreationWithNegativePrice() {
        Product product = new Product(3, "Produto com Preço Negativo", -100.0);
        
        assertEquals(3, product.id);
        assertEquals("Produto com Preço Negativo", product.name);
        assertEquals(-100.0, product.price, 0.01);
    }
}

