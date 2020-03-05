package com.thomasmarshall.scraping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thomasmarshall.scraping.model.Product;
import com.thomasmarshall.scraping.model.ShoppingList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Scraper {
    ShoppingList shoppingList;

    public static void main(String[] arg) {
        Scraper scraper = new Scraper();
        scraper.shoppingList = new ShoppingList();
        scraper.shoppingList.setProducts(scraper.getListOfProducts());
        scraper.shoppingList.calculateTotal();
        Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String s = g.toJson(scraper.shoppingList);
        System.out.println(s);
    }

    public ArrayList<Product> getListOfProducts() {
        try {
            Document doc = Jsoup.connect(Constants.PAGE_TO_SCRAPE).get();
            Elements products = doc.select(Constants.UL_PRODUCT_LISTER).select(Constants.DIV_PRODUCT);
            ArrayList<Product> prods = new ArrayList<>();
            products.forEach(s -> {
                Product prod = new Product();
                prod.setTitle(s.select(Constants.DIV_PRODUCT_INFO).eachText().get(0));
                prod.setUnitPrice(Util.getUnitPrice(s.select(Constants.DIV_PRICING).eachText().get(0)));
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
            productDoc = Jsoup.connect(getProductPageURL(s.select(Constants.DIV_PRODUCT_INFO))).get();
            prod.setDescription(getFirstLineOfDescription(productDoc));
            prod.setKcal_per_100g(Util.getKcalFromPageSource(productDoc.text()));
        } catch (IOException e) {
            System.out.println("Error loading product page");
        }
    }

    private String getProductPageURL(Elements select) {
        return (select.select(Constants.A_HREF).attr(Constants.ABS_HREF));
    }

    private String getFirstLineOfDescription(Document productPage) {
        Elements descriptors = productPage.select(Constants.DIV_PRODUCT_TEXT);
        if (descriptors.get(0).select(Constants.DIV_MEMO).eachText().isEmpty()) {
            return descriptors.get(0).select(Constants.DIV_MEMO).eachText().get(0);
        } else {
            return descriptors.eachText().get(0);
        }
    }



}
