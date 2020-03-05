package com.thomasmarshall.scraping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingList {
    ArrayList<Product> products;
    Total total;

    public void calculateTotal() {
        total = new Total();
        total.setGrossAndVAT(calculateGrossTotal());
    }

    private BigDecimal calculateGrossTotal() {
        return (products.stream().map(p -> p.getUnitPrice()).reduce((BigDecimal::add)).orElse(BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP);
    }
}
