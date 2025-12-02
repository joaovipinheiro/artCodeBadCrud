package br.com.infnet.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.infnet.model.Product;
import br.com.infnet.repository.ProductRepository;
import br.com.infnet.service.ProductService;

class ProductControllerTest {

    private ProductController controller;
    private ProductService service;
    private ProductRepository repository;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        repository = new ProductRepository();
        service = new ProductService(repository);
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Cria produto através do controller")
    void shouldCreateProduct() {
        String input = "Produto Teste\n100.0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new ProductController(service, scanner);

        controller.create();

        List<Product> products = service.listAllProducts();
        assertEquals(1, products.size());
        assertEquals("Produto Teste", products.get(0).getName());
        assertEquals(100.0, products.get(0).getPrice());
    }

    @Test
    @DisplayName("Lista produtos através do controller")
    void shouldListProducts() {
        service.createProduct("Produto 1", 10.0);
        service.createProduct("Produto 2", 20.0);
        
        Scanner scanner = new Scanner(new ByteArrayInputStream("".getBytes()));
        controller = new ProductController(service, scanner);
        controller.listProducts();

        String output = outputStream.toString();
        assertTrue(output.contains("Produto 1") || output.contains("Lista de Produtos"));
    }

    @Test
    @DisplayName("Atualiza produto através do controller")
    void shouldUpdateProduct() {
        Product created = service.createProduct("Original", 100.0);
        
        String input = created.getId() + "\nAtualizado\n120.0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new ProductController(service, scanner);

        controller.update();

        Optional<Product> updated = service.getProductById(created.getId());
        assertTrue(updated.isPresent());
        assertEquals("Atualizado", updated.get().getName());
        assertEquals(120.0, updated.get().getPrice());
    }

    @Test
    @DisplayName("Remove produto através do controller")
    void shouldDeleteProduct() {
        Product created = service.createProduct("Produto", 20.0);
        
        String input = created.getId() + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new ProductController(service, scanner);

        controller.delete();

        assertTrue(service.getProductById(created.getId()).isEmpty());
    }
}

