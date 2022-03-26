package com.virtuslab.internship.receipt;

import com.virtuslab.internship.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record Receipt(
        List<ReceiptEntry> entries,
        List<String> discounts,
        BigDecimal totalPrice) {

    public Receipt(List<ReceiptEntry> entries) {
        this(entries,
                new ArrayList<>(),
                entries.stream()
                        .map(ReceiptEntry::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    public boolean hasProducts(Product.Type type, int quantity)
    {
        int count = 0;
        for (ReceiptEntry entry: entries)
        {
            if (entry.product().type() == type)
            {
                count += entry.quantity();
                if (count >= quantity) return true;
            }
        }
        return false;
    }

    public boolean appliedDiscount(String discount)
    {
        for (String d: discounts)
        {
            if (d.equals(discount)) return true;
        }
        return false;
    }
}