package com.thomasmarshall.scraping;

import com.thomasmarshall.scraping.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ScraperTest {
    @Test
    public void testGetListOfProductsFindsABoxOfStrawberries() {
        Document document = Jsoup.parse(CannedPageSource.CANNED_PAGE_SOURCE);
        Scraper scraper = new Scraper("dummyurl");
        ArrayList<Product> listOfProducts = scraper.getListOfProducts(document);
        assertEquals(1, listOfProducts.size());

        Product expectedProduct = new Product("Sainsbury's Strawberries 400g", new BigDecimal(1.75), 33, "by Sainsbury's strawberries");
        assertTrue(expectedProduct.equals(listOfProducts.get(0)));

    }
}