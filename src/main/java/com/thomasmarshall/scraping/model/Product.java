package com.thomasmarshall.scraping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    private String title;
    private BigDecimal unit_price;
    private int kcal_per_100g;
    private String description;
}
