package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenTenPercentDiscountTest {
    @Test
    void shouldApply15and10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 10));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenAndTenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply((BigDecimal.valueOf(10)))
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85))
                .multiply(BigDecimal.valueOf(0.9));
        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply10andNot15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var pork = productDb.getProduct("Pork"); // no grains
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(pork, 10));
        receiptEntries.add(new ReceiptEntry(steak, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenAndTenPercentDiscount();
        var expectedTotalPrice = pork.price().multiply((BigDecimal.valueOf(10)))
                .add(steak.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.9));
        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply15AndNot10PercentDiscountWithPriceBelow50() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 10)); // totalPrice = 50

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenAndTenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply((BigDecimal.valueOf(10)))
                .multiply(BigDecimal.valueOf(0.85));
        // When
        var receiptAfterDiscount = discount.apply(receipt); // cannot apply 10% because totalPrice < 50

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
    @Test
    void shouldNotApply15Nor10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var potato = productDb.getProduct("Potato");
        var banana = productDb.getProduct("Banana");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(potato, 5));
        receiptEntries.add(new ReceiptEntry(banana, 5));


        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenAndTenPercentDiscount();
        var expectedTotalPrice = potato.price().multiply((BigDecimal.valueOf(5)))
                .add(banana.price().multiply(BigDecimal.valueOf(5)));
        // When
        var receiptAfterDiscount = discount.apply(receipt); // no grains and price below 50

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

}
