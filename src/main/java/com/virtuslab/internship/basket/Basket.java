package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private final List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(Product product, int quantity)
    {
        for (int q = 0; q < quantity; q++) {
            addProduct(product);
        }
    }
    public List<Product> getProducts() {
        return products;
    }
}
