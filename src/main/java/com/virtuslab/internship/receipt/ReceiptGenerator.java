package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceiptGenerator {

    private HashMap<Product, Integer> countProducts(Basket basket)
    {
        HashMap<Product, Integer> products = new HashMap<>();
        for (Product product: basket.getProducts())
        {
            if (products.containsKey(product)) {
                products.replace(product, products.get(product) + 1);
            }
            else {
                products.put(product, 1);
            }
        }
        return products;
    }

    private List<ReceiptEntry> getReceiptEntries(HashMap<Product, Integer> products)
    {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        products.forEach((product, quantity) -> {
            receiptEntries.add(new ReceiptEntry(product, quantity));
        });

        return receiptEntries;
    }

    public Receipt generate(Basket basket) {
        HashMap<Product, Integer> products = countProducts(basket);
        List<ReceiptEntry> receiptEntries = getReceiptEntries(products);


        return new Receipt(receiptEntries);
    }
}
