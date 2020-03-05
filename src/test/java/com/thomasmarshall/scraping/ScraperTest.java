package com.thomasmarshall.scraping;

import com.thomasmarshall.scraping.model.Total;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class ScraperTest {

    @Test
    public void getUnitPrice() {
        BigDecimal expectedPrice = new BigDecimal("1.99");
        String sampleScrapedPriceString = "£1.99/unit £16.67/kg";
        BigDecimal actualPrice = Util.getUnitPrice(sampleScrapedPriceString);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void getKcalFromPageSource() {
        String pageSource = "</thead><tr class=\"tableRow1\"><th scope=\"row\" class=\"rowHeader\" rowspan=\"2\">Energy</th><td class=\"tableRow1\">300kJ</td><td class=\"tableRow1\">-</td></tr><tr class=\"tableRow0\"><td class=\"tableRow0\">71kcal</td><td class=\"tableRow0\">4%</td></tr><tr class=\"tableRow1\"><th scope=\"row\" class=\"rowHeader\">Fat</th><td class=\"tableRow1\">&lt;0.5g</td><td class=\"tableRow1\">-</td></tr><tr class=\"tableRow0\"><th scope=\"row\" class=\"rowHeader\">Saturates</th><td class=\"tableRow0\">&lt;0.1g</td><td class=\"tableRow0\">-</td></tr><tr class=\"tableRow1\"><th scope=\"row\" class=\"rowHeader\">Carbohydrate</th><td class=\"tableRow1\">13.8g</td><td class=\"tableRow1\">5%</td></tr><tr class=\"tableRow0\"><th scope=\"row\" class=\"rowHeader\">Total Sugars</th><td class=\"tableRow0\">7.4g</td><td class=\"tableRow0\">8%</td></tr><tr class=\"tableRow1\"><th scope=\"row\" class=\"rowHeader\">Starch</th><td class=\"tableRow1\">6.4g</td><td class=\"tableRow1\">-</td></tr><tr class=\"tableRow0\"><th scope=\"row\" class=\"rowHeader\">Fibre</th><td class=\"tableRow0\">4.3g</td><td class=\"tableRow0\">-</td></tr><tr class=\"tableRow1\"><th scope=\"row\" class=\"rowHeader\">Protein</th><td class=\"tableRow1\">1.4g</td><td class=\"tableRow1\">3%</td></tr><tr class=\"tableRow0\"><th scope=\"row\" class=\"rowHeader\">Salt</th><td class=\"tableRow0\">&lt;0.01g</td><td class=\"tableRow0\">-</td></tr></table></div><p>RI= Reference Intakes of an average adult (8400kJ / 2000kcal)</p></div>";

        assertEquals(71, Util.getKcalFromPageSource(pageSource));
    }

    @Test
    public void getKcalFromPageSourceWhenNothingToFind() {
        String pageSource = "</thead><tr class=\"tableRow1\"><th scope=\"row\" class=\"rowHeader\" rowspan=\"2\">Energy</th";
        assertEquals(0, Util.getKcalFromPageSource(pageSource));
    }

    @Test
    public void testVATisCalculatedCorrectly() {
        BigDecimal gross = new BigDecimal(12).setScale(2, RoundingMode.HALF_UP);
        Total total = new Total();
        total.setGross(gross);
        assertEquals(new BigDecimal(2.00).setScale(2, RoundingMode.HALF_UP), total.getVat());
    }
}