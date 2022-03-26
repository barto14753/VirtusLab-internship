package com.virtuslab.internship.controllers;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/products")
public class ReceiptFromProductsController {
    // Endpoints receive product names (from ProductDb) and their quantities as HashMaps<Name, Quantity>

    private Basket generateBasket(HashMap<String, Integer> productQuantity)
    {
        ProductDb productDb = new ProductDb();
        Basket basket = new Basket();
        productQuantity.forEach((productName, quantity) -> {
            for (int i = 0; i < quantity; i++) {
                if (productDb.exist(productName))
                {
                    basket.addProduct(productDb.getProduct(productName));
                }
            }
        });
        return basket;
    }

    private Receipt generateReceiptFromProducts(HashMap<String, Integer> productQuantity,
                                                boolean apply15,
                                                boolean apply10)
    {
        Basket basket = generateBasket(productQuantity);
        return ReceiptFromBasketController.generateReceipt(basket, apply15, apply10);

    }

    @PostMapping("/fifteenAndTenDiscount")
    @ResponseBody
    public Receipt fifteenAndTen(@RequestBody HashMap<String, Integer> productQuantity) {
        return generateReceiptFromProducts(productQuantity, true, true);
    }

    @PostMapping("/fifteenDiscount")
    @ResponseBody
    public Receipt fifteen(@RequestBody HashMap<String, Integer> productQuantity)  {
        return generateReceiptFromProducts(productQuantity, true, false);
    }

    @PostMapping("/tenDiscount")
    @ResponseBody
    public Receipt ten(@RequestBody HashMap<String, Integer> productQuantity) {
        return generateReceiptFromProducts(productQuantity, false, true);
    }

    @PostMapping("/noneDiscount")
    @ResponseBody
    public Receipt none(@RequestBody HashMap<String, Integer> productQuantity) {
        return generateReceiptFromProducts(productQuantity, false, false);
    }

}
