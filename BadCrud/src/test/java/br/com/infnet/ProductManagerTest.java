package br.com.infnet;

import junit.framework.TestCase;

public class ProductManagerTest extends TestCase {

    private ProductManager manager;

    protected void setUp() {
        // Limpa a lista antes de cada teste
        ProductManager.productList.clear();
        ProductManager.nextId = 1;
        manager = new ProductManager();
    }

    public void testCreateNewProduct() {
        manager.createNewProduct("Mouse", 50.00);
        
        assertEquals(1, ProductManager.productList.size());
        Product product = ProductManager.productList.get(0);
        assertEquals(1, product.id);
        assertEquals("Mouse", product.name);
        assertEquals(50.00, product.price, 0.01);
    }

    public void testCreateMultipleProducts() {
        manager.createNewProduct("Teclado", 150.00);
        manager.createNewProduct("Monitor", 800.00);
        manager.createNewProduct("Webcam", 200.00);
        
        assertEquals(3, ProductManager.productList.size());
        assertEquals(3, ProductManager.nextId);
    }

    public void testListAllProductsWhenEmpty() {
        manager.listAllProducts();
        // Não deve lançar exceção e a lista deve estar vazia
        assertTrue(ProductManager.productList.isEmpty());
    }

    public void testListAllProductsWithItems() {
        manager.createNewProduct("Produto 1", 100.00);
        manager.createNewProduct("Produto 2", 200.00);
        
        manager.listAllProducts();
        assertEquals(2, ProductManager.productList.size());
    }

    public void testGetProductByIdExists() {
        manager.createNewProduct("Notebook", 2500.00);
        manager.createNewProduct("Tablet", 1200.00);
        
        Product found = manager.getProductById(1);
        assertNotNull(found);
        assertEquals(1, found.id);
        assertEquals("Notebook", found.name);
    }

    public void testGetProductByIdNotExists() {
        manager.createNewProduct("Produto", 100.00);
        
        Product found = manager.getProductById(999);
        assertNull(found);
    }

    public void testUpdateProductExists() {
        manager.createNewProduct("Produto Original", 100.00);
        
        manager.updateProduct(1, "Produto Atualizado", 150.00);
        
        Product updated = manager.getProductById(1);
        assertNotNull(updated);
        assertEquals("Produto Atualizado", updated.name);
        assertEquals(150.00, updated.price, 0.01);
    }

    public void testUpdateProductNotExists() {
        manager.createNewProduct("Produto", 100.00);
        
        manager.updateProduct(999, "Novo Nome", 200.00);
        
        // Produto original não deve ser alterado
        Product original = manager.getProductById(1);
        assertEquals("Produto", original.name);
        assertEquals(100.00, original.price, 0.01);
    }

    public void testDeleteProductExists() {
        manager.createNewProduct("Produto 1", 100.00);
        manager.createNewProduct("Produto 2", 200.00);
        
        manager.deleteProduct(1);
        
        assertEquals(1, ProductManager.productList.size());
        assertNull(manager.getProductById(1));
        assertNotNull(manager.getProductById(2));
    }

    public void testDeleteProductNotExists() {
        manager.createNewProduct("Produto", 100.00);
        
        manager.deleteProduct(999);
        
        // Produto original deve permanecer
        assertEquals(1, ProductManager.productList.size());
        assertNotNull(manager.getProductById(1));
    }

    public void testDeleteAllProducts() {
        manager.createNewProduct("Produto 1", 100.00);
        manager.createNewProduct("Produto 2", 200.00);
        manager.createNewProduct("Produto 3", 300.00);
        
        manager.deleteProduct(1);
        manager.deleteProduct(2);
        manager.deleteProduct(3);
        
        assertTrue(ProductManager.productList.isEmpty());
    }

    public void testNextIdIncrement() {
        assertEquals(1, ProductManager.nextId);
        
        manager.createNewProduct("Produto 1", 100.00);
        assertEquals(2, ProductManager.nextId);
        
        manager.createNewProduct("Produto 2", 200.00);
        assertEquals(3, ProductManager.nextId);
    }
}

