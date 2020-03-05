package com.thomasmarshall.scraping;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ScraperTest {

    @org.junit.Test
    public void getUnitPrice() {
        BigDecimal expectedPrice = new BigDecimal("1.99");
        String sampleScrapedPriceString = "£1.99/unit £16.67/kg";
        BigDecimal actualPrice = Util.getUnitPrice(sampleScrapedPriceString);
        assertEquals(expectedPrice, actualPrice);
    }
}