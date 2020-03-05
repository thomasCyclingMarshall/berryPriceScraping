package com.thomasmarshall.scraping;

import com.thomasmarshall.scraping.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Scraper {
    public static void main(String[] arg) {
        try {
            Document doc = Jsoup.connect("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html").get();
            Elements products = doc.select("ul.productLister").select("div.product");

            ArrayList<Product> prods = new ArrayList<>();
            products.forEach(s -> {
                Product prod = new Product();
                prod.setTitle(s.select("div.productInfo").eachText().get(0));
                prod.setUnitPrice(Util.getUnitPrice(s.select("div.pricing").eachText().get(0)));
                prod.setDescription(getFirstLineOfDescription(getProductPageURL(s.select("div.productInfo"))));
//                System.out.println(prod);
                prods.add(prod);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getProductPageURL(Elements select) {
        return (select.select("a[href]").attr("abs:href"));
    }

    public static String getFirstLineOfDescription(String pageURL) {
        try {
            Document doc = Jsoup.connect(pageURL).get();
            Elements descriptors = doc.select("div.productText");
            if (descriptors.get(0).select("div.memo").eachText().size() > 0) {
                return descriptors.get(0).select("div.memo").eachText().get(0);
            } else {
                return descriptors.eachText().get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

}
