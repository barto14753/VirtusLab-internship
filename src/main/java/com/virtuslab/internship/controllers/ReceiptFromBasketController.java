package com.virtuslab.internship.controllers;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/basket")
public class ReceiptFromBasketController {

    public static Receipt generateReceipt(Basket basket, boolean apply15, boolean apply10)
    {
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();
        Receipt receipt = receiptGenerator.generate(basket);

        if (apply15)
        {
            FifteenPercentDiscount discount15 = new FifteenPercentDiscount();
            receipt = discount15.apply(receipt);
        }
        if (apply10)
        {
            TenPercentDiscount discount10 = new TenPercentDiscount();
            receipt = discount10.apply(receipt);
        }

        return receipt;
    }

    @PostMapping("/fifteenAndTenDiscount")
    @ResponseBody
    public Receipt fifteenAndTen(@RequestBody Basket basket) {
        return generateReceipt(basket, true, true);
    }

    @PostMapping("/fifteenDiscount")
    @ResponseBody
    public Receipt fifteen(@RequestBody Basket basket)  {
        return generateReceipt(basket, true, false);
    }

    @PostMapping("/tenDiscount")
    @ResponseBody
    public Receipt ten(@RequestBody Basket basket) {
        return generateReceipt(basket, false, true);
    }

    @PostMapping("/noneDiscount")
    @ResponseBody
    public Receipt none(@RequestBody Basket basket) {
        return generateReceipt(basket, false, false);
    }


}
