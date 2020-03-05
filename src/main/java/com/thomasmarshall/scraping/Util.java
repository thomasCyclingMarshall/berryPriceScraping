package com.thomasmarshall.scraping;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static BigDecimal getUnitPrice(String priceString) {
        Pattern pattern = Pattern.compile("(\\d)+.(\\d)+(?=/unit)");
        Matcher matcher = pattern.matcher(priceString);
        if (matcher.find())
        {
            return new BigDecimal(matcher.group(0));
        }

        return BigDecimal.ZERO;
    }
}
