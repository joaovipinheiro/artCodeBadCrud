package br.com.infnet;

// CLASSE ENTIDADE MAL FEITA
class Product {
    public int id;
    public String name;
    public double price;

    // Construtor sem sentido e com repetição
    public Product(int i, String n, double p) {
        id = i;
        name = n;
        price = p;
    }
}
