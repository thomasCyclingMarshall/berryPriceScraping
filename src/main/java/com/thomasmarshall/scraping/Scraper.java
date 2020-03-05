package com.thomasmarshall.scraping;

import com.thomasmarshall.scraping.model.Product;
import com.thomasmarshall.scraping.model.Total;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Scraper {
    ArrayList<Product> products;
    Total total;

    public static void main(String[] arg) {
        Scraper scraper = new Scraper();
        scraper.products = scraper.getListOfProducts();
        scraper.products.forEach(System.out::println);
        scraper.total = new Total();

        scraper.total.setGross(scraper.calculateGrossTotal().setScale(2, RoundingMode.HALF_UP));
        System.out.println(scraper.total);

    }

    public ArrayList<Product> getListOfProducts() {
        try {
            Document doc = Jsoup.connect("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html").get();
            Elements products = doc.select("ul.productLister").select("div.product");
            ArrayList<Product> prods = new ArrayList<>();
            products.forEach(s -> {
                Product prod = new Product();
                prod.setTitle(s.select("div.productInfo").eachText().get(0));
                prod.setUnitPrice(Util.getUnitPrice(s.select("div.pricing").eachText().get(0)));
                populateDetailsFromProductPage(s, prod);
                prods.add(prod);
            });
            return prods;

        } catch (IOException e) {
            System.out.println("Error connecting to network");
        }
        return new ArrayList<>();
    }

    private void populateDetailsFromProductPage(Element s, Product prod) {
        Document productDoc;
        try {
            productDoc = Jsoup.connect(getProductPageURL(s.select("div.productInfo"))).get();
            prod.setDescription(getFirstLineOfDescription(productDoc));
            prod.setKcal_per_100g(Util.getKcalFromPageSource(productDoc.text()));
        } catch (IOException e) {
            System.out.println("Error loading product page");
        }
    }

    private String getProductPageURL(Elements select) {
        return (select.select("a[href]").attr("abs:href"));
    }

    private String getFirstLineOfDescription(Document productPage) {
        Elements descriptors = productPage.select("div.productText");
        if (descriptors.get(0).select("div.memo").eachText().size() > 0) {
            return descriptors.get(0).select("div.memo").eachText().get(0);
        } else {
            return descriptors.eachText().get(0);
        }
    }

    private BigDecimal calculateGrossTotal() {
        return products.stream().map(p -> p.getUnitPrice()).reduce((BigDecimal::add)).orElse(BigDecimal.ZERO);
    }

}
