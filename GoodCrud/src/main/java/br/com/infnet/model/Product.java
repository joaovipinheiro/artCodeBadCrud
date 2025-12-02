package br.com.infnet.model;

import java.util.Objects;

/**
 * Representa um produto simples com validações básicas.
 * A classe é imutável para facilitar o raciocínio e a testabilidade.
 */
public final class Product {

    private final int id;
    private final String name;
    private final double price;

    public Product(int id, String name, double price) {
        this.id = validateId(id);
        this.name = validateName(name);
        this.price = validatePrice(price);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Retorna uma nova instância com o mesmo ID e os dados atualizados.
     */
    public Product update(String newName, double newPrice) {
        return new Product(this.id, newName, newPrice);
    }

    private static int validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo.");
        }
        return id;
    }

    private static String validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        return trimmed;
    }

    private static double validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id
                && Double.compare(product.price, price) == 0
                && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}

