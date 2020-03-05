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

import static com.thomasmarshall.scraping.Constants.PAGE_TO_SCRAPE;

public class Scraper {
    ShoppingList shoppingList;
    String pageToScrape;

    public Scraper(String pageToScrape) {
        this.pageToScrape = pageToScrape;
    }

    public static void main(String[] arg) {
        Scraper scraper = new Scraper(PAGE_TO_SCRAPE);
        scraper.shoppingList = new ShoppingList();
        Document document;
        try {
            document = Jsoup.connect(scraper.pageToScrape).get();
            scraper.shoppingList.setProducts(scraper.getListOfProducts(document));
            scraper.shoppingList.calculateTotal();
            Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String s = g.toJson(scraper.shoppingList);
            System.out.println(s);
        } catch (IOException e) {
            System.out.println("Error connecting to network");
        }
    }

    public ArrayList<Product> getListOfProducts(Document doc) {
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


    }

    private void populateDetailsFromProductPage(Element s, Product prod) {
        Document productDoc;
        try {
            productDoc = Jsoup.connect(getProductPageURL(s.select(Constants.DIV_PRODUCT_INFO))).get();
            prod.setDescription(getFirstLineOfProductDescription(productDoc));
            prod.setKcal_per_100g(Util.getKcalFromPageSource(productDoc.text()));
        } catch (IOException e) {
            System.out.println("Error loading product page");
        } catch (IllegalArgumentException e) {
            System.out.println("Product page URL badly formed");
        }
    }

    private String getProductPageURL(Elements select) {
        return (select.select(Constants.A_HREF).attr(Constants.ABS_HREF));
    }

    private String getFirstLineOfProductDescription(Document productPage) {
        Elements descriptors = productPage.select(Constants.DIV_PRODUCT_TEXT);
        if (!descriptors.get(0).select(Constants.DIV_MEMO).eachText().isEmpty()) {
            return descriptors.get(0).select(Constants.DIV_MEMO).eachText().get(0);
        } else {
            return descriptors.eachText().get(0);
        }
    }


}
