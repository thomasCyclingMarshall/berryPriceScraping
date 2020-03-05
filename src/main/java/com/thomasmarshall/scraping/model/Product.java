package com.thomasmarshall.scraping.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private String title;
    private BigDecimal unitPrice;
    private Integer kcal_per_100g;
    private String description;
}
