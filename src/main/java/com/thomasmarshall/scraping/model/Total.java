package com.thomasmarshall.scraping.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Total {

    @Getter
    private BigDecimal gross;

    @Getter
    private BigDecimal vat;

    public void setGrossAndVAT(BigDecimal gross) {
        this.gross = gross;
        this.vat = gross.divide(new BigDecimal(6), 2, RoundingMode.HALF_UP);
    }
}
