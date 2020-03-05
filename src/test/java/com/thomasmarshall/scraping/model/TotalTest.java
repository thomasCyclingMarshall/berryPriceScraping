package com.thomasmarshall.scraping.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class TotalTest {
    @Test
    public void testVATisCalculatedCorrectly() {
        BigDecimal gross = new BigDecimal(12).setScale(2, RoundingMode.HALF_UP);
        Total total = new Total();
        total.setGrossAndVAT(gross);
        assertEquals(new BigDecimal(2.00).setScale(2, RoundingMode.HALF_UP), total.getVat());
    }
}
