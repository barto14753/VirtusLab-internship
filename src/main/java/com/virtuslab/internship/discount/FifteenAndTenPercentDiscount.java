package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public class FifteenAndTenPercentDiscount implements Discount{
    @Override
    public Receipt apply(Receipt receipt) {
        FifteenPercentDiscount discount15 = new FifteenPercentDiscount();
        TenPercentDiscount discount10 = new TenPercentDiscount();

        receipt = discount15.apply(receipt);
        receipt = discount10.apply(receipt);
        return receipt;
    }
}
